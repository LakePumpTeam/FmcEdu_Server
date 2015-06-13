package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.*;
import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.relationship.TaskStudentsRelationship;
import com.fmc.edu.model.task.Task;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.ResponseBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yu on 5/31/2015.
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {
    private static final Logger LOG = Logger.getLogger(ProfileController.class);

    @Resource(name = "taskManager")
    private TaskManager mTaskManager;

    @Resource(name = "profileManager")
    private ProfileManager mProfileManager;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "newsManager")
    private NewsManager mNewsManager;

    @Resource(name = "responseBuilder")
    private ResponseBuilder mResponseBuilder;

    @RequestMapping(value = "/requestTaskList" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String requestTaskList(final HttpServletRequest pRequest,
                                   final HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            Pagination pagination = buildPagination(pRequest);
            String userIdStr = pRequest.getParameter("userId");
            String statusStr = pRequest.getParameter("status");
            String filterStr = pRequest.getParameter("filter");

            BaseProfile baseProfile = getMyAccountManager().findUserById(userIdStr);
            if (baseProfile == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER, userIdStr);
                return output(responseBean);
            }
            Map<String, Object> taskList = new HashMap<String, Object>();
            Integer status = StringUtils.isEmpty(statusStr) ? null : Integer.valueOf(statusStr);
            if (baseProfile.getProfileType() == ProfileType.PARENT.getValue()) {
                taskList = getTaskManager().queryTaskListByParentId(baseProfile.getId(), filterStr, status, pagination);
            } else if (baseProfile.getProfileType() == ProfileType.TEACHER.getValue()) {
                taskList = getTaskManager().queryTaskListByTeacherId(baseProfile.getId(), filterStr, status, pagination);
            } else {
                responseBean.addBusinessMsg(ResourceManager.ERROR_USER_TYPE_UNKNOWN);
            }
            responseBean.addData(taskList);
        } catch (IOException e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/publishTask" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String publishTask(final HttpServletRequest pRequest,
                               final HttpServletResponse pResponse,
                               final String userId,
                               final String[] students,
                               final String deadline,
                               final String title,
                               final String task) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {
            String[] decodeStudents = students;

            Task taskObj = new Task();
            taskObj.setAvailable(true);
            taskObj.setCreationDate(DateUtils.getDaysLater(0));
            taskObj.setDeadline(new java.sql.Timestamp(DateUtils.convertStringToDate(deadline).getTime()));
            taskObj.setPublishUserId(Integer.valueOf(userId));
            taskObj.setTask(task);
            taskObj.setTitle(title);
            TaskStudentsRelationship taskStudentsRelationship;
            List<TaskStudentsRelationship> taskStudentsRelationships = new ArrayList<TaskStudentsRelationship>();
            if (getTaskManager().publishTask(taskObj)) {
                for (String studentId : decodeStudents) {
                    taskStudentsRelationship = new TaskStudentsRelationship();
                    taskStudentsRelationship.setCreationDate(DateUtils.getDaysLater(0));
                    taskStudentsRelationship.setCompleted(false);
                    taskStudentsRelationship.setStudentId(Integer.valueOf(studentId));
                    taskStudentsRelationship.setTaskId(taskObj.getId());
                    taskStudentsRelationship.setLastUpdateDate(DateUtils.getDaysLater(0));
                    taskStudentsRelationships.add(taskStudentsRelationship);
                }
                getTaskManager().insertTaskStudentRelationship(taskStudentsRelationships);
            }
        } catch (ParseException e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            txStatus.setRollbackOnly();
        } catch (Exception ex) {
            LOG.error(ex);
            responseBean.addErrorMsg(ex);
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/deleteTask" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String deleteTask(final HttpServletRequest pRequest,
                              final HttpServletResponse pResponse,
                              final String taskId,
                              final String userId,
                              final String studentId) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {

            if (!getTaskManager().deleteTask(Integer.valueOf(taskId), Integer.valueOf(userId), Integer.valueOf(studentId))) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_DELETE_TASK_FAILED);
                return output(responseBean);
            }
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/requestTaskDetail" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String requestTaskDetail(final HttpServletRequest pRequest,
                                     final HttpServletResponse pResponse,
                                     final String taskId,
                                     final String studentId) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        try {
            Task task = getTaskManager().queryTaskDetail(Integer.valueOf(taskId));
            if (task == null) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_OBTAIN_TASK_DETAIL_FAILED);
            }
            getResponseBuilder().buildTaskDetail(task, studentId, responseBean);
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/addComment" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String addComment(final HttpServletRequest pRequest,
                              final HttpServletResponse pResponse,
                              final String taskId,
                              final String userId,
                              final String comment) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {
            Comments comments = new Comments();
            comments.setComment(comment);
            comments.setProfileId(Integer.valueOf(userId));
            comments.setRefId(Integer.valueOf(taskId));
            comments.setAvailable(true);
            comments.setCreationDate(DateUtils.getDaysLater(0));
            if (!getNewsManager().insertComment(comments)) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_ADD_COMMENT_FAILED);
                return output(responseBean);
            }

            Map<String, Object> commentMap = getResponseBuilder().buildComment(comments, null);
            responseBean.addData(commentMap);
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/deleteComment" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String deleteComment(final HttpServletRequest pRequest,
                                 final HttpServletResponse pResponse,
                                 final String commentId,
                                 final String userId) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {
            if (!getNewsManager().deleteComment(Integer.valueOf(commentId))) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_DELETE_COMMENT_FAILED);
                return output(responseBean);
            }
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/editTask" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String editTask(final HttpServletRequest pRequest,
                            final HttpServletResponse pResponse,
                            final String taskId,
                            final String userId,
                            final String task) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {
            Task taskObj = new Task();
            taskObj.setId(Integer.valueOf(taskId));
            taskObj.setTask(task);
            if (!getTaskManager().editTask(taskObj)) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_MODIFY_TASK_FAILED);
                return output(responseBean);
            }
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    @RequestMapping(value = "/submitTask" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    private String submitTask(final HttpServletRequest pRequest,
                              final HttpServletResponse pResponse,
                              final String studentId,
                              final String taskId) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        TransactionStatus txStatus = ensureTransaction();
        try {
            if (!getTaskManager().submitTask(Integer.valueOf(taskId), Integer.valueOf(studentId))) {
                responseBean.addBusinessMsg(ResourceManager.ERROR_NEWS_SUBMIT_TASK_FAILED);
                return output(responseBean);
            }
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }


    public TaskManager getTaskManager() {
        return mTaskManager;
    }

    public void setTaskManager(TaskManager pTaskManager) {
        mTaskManager = pTaskManager;
    }

    public ProfileManager getProfileManager() {
        return mProfileManager;
    }

    public void setProfileManager(ProfileManager pProfileManager) {
        mProfileManager = pProfileManager;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }

    public NewsManager getNewsManager() {
        return mNewsManager;
    }

    public void setNewsManager(NewsManager pNewsManager) {
        mNewsManager = pNewsManager;
    }

    public ResponseBuilder getResponseBuilder() {
        return mResponseBuilder;
    }

    public void setResponseBuilder(ResponseBuilder pResponseBuilder) {
        mResponseBuilder = pResponseBuilder;
    }
}
