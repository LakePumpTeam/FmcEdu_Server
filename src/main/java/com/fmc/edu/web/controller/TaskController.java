package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.manager.MyAccountManager;
import com.fmc.edu.manager.NewsManager;
import com.fmc.edu.manager.ProfileManager;
import com.fmc.edu.manager.TaskManager;
import com.fmc.edu.model.news.Comments;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.profile.ProfileType;
import com.fmc.edu.model.relationship.TaskStudentsRelationship;
import com.fmc.edu.model.task.Task;
import com.fmc.edu.util.DateUtils;
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
        ResponseBean responseBean = new ResponseBean();
        try {
            Pagination pagination = buildPagination(pRequest);
            String userIdStr = decodeInput(pRequest.getParameter("userId"));
            String statusStr = decodeInput(pRequest.getParameter("status"));
            String filterStr = decodeInput(pRequest.getParameter("filter"));

            BaseProfile baseProfile = getMyAccountManager().findUserById(userIdStr);
            if (baseProfile == null) {
                responseBean.addBusinessMsg(MyAccountManager.ERROR_NOT_FIND_USER);
                return output(responseBean);
            }
            Map<String, Object> taskList = new HashMap<String, Object>();
            if (baseProfile.getProfileType() == ProfileType.PARENT.getValue()) {
                taskList = getTaskManager().queryTaskListByParentId(baseProfile.getId(), filterStr, Integer.valueOf(statusStr), pagination);
            } else if (baseProfile.getProfileType() == ProfileType.TEACHER.getValue()) {
                taskList = getTaskManager().queryTaskListByTeacherId(baseProfile.getId(), filterStr, Integer.valueOf(statusStr), pagination);
            } else {
                responseBean.addBusinessMsg("未知用户类型.");
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
        ResponseBean responseBean = new ResponseBean();
        TransactionStatus txStatus = ensureTransaction();
        try {
            String userIdStr = decodeInput(userId);
            String titleStr = decodeInput(title);
            String taskStr = decodeInput(task);
            String deadlineStr = decodeInput(deadline);
            String[] decodeStudents = decodeArrayInput(students);
            Task taskObj = new Task();
            taskObj.setAvailable(true);
            taskObj.setCreationDate(DateUtils.getDaysLater(0));
            taskObj.setDeadline(new java.sql.Timestamp(DateUtils.convertStringToDate(deadlineStr).getTime()));
            taskObj.setPublishUserId(Integer.valueOf(userIdStr));
            taskObj.setTask(taskStr);
            taskObj.setTitle(titleStr);
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
        } catch (IOException e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
            txStatus.setRollbackOnly();
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
        ResponseBean responseBean = new ResponseBean();
        TransactionStatus txStatus = ensureTransaction();
        try {
            String taskIdStr = decodeInput(taskId);
            String userIdStr = decodeInput(userId);
            String studentIdStr = decodeInput(studentId);
            if (!getTaskManager().deleteTask(Integer.valueOf(taskIdStr), Integer.valueOf(userIdStr), Integer.valueOf(studentIdStr))) {
                responseBean.addBusinessMsg("删除任务失败.");
                return output(responseBean);
            }
        } catch (IOException e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
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
        ResponseBean responseBean = new ResponseBean();
        try {
            String taskIdStr = decodeInput(taskId);
            String studentIdStr = decodeInput(studentId);
            Task task = getTaskManager().queryTaskDetail(Integer.valueOf(taskIdStr));
            if (task == null) {
                responseBean.addBusinessMsg("获取任务详情失败.");
            }
            getResponseBuilder().buildTaskDetail(task, studentIdStr, responseBean);
        } catch (IOException e) {
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
        ResponseBean responseBean = new ResponseBean();
        TransactionStatus txStatus = ensureTransaction();
        try {
            String taskIdStr = decodeInput(taskId);
            String userIdStr = decodeInput(userId);
            String commentStr = decodeInput(comment);
            Comments comments = new Comments();
            comments.setComment(commentStr);
            comments.setProfileId(Integer.valueOf(userIdStr));
            comments.setRefId(Integer.valueOf(taskIdStr));
            comments.setAvailable(true);
            comments.setCreationDate(DateUtils.getDaysLater(0));
            if (!getNewsManager().insertComment(comments)) {
                responseBean.addBusinessMsg("添加评论失败.");
                return output(responseBean);
            }

            Map<String, Object> commentMap = getResponseBuilder().buildComment(comments, null);
            responseBean.addData(commentMap);
        } catch (IOException e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
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
        ResponseBean responseBean = new ResponseBean();
        TransactionStatus txStatus = ensureTransaction();
        try {
            String commentIdStr = decodeInput(commentId);
            String userIdStr = decodeInput(userId);
            if (!getNewsManager().deleteComment(Integer.valueOf(commentIdStr))) {
                responseBean.addBusinessMsg("删除评论失败.");
                return output(responseBean);
            }
        } catch (IOException e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
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
        ResponseBean responseBean = new ResponseBean();
        TransactionStatus txStatus = ensureTransaction();
        try {
            String taskIdStr = decodeInput(taskId);
            String userIdStr = decodeInput(userId);
            String taskStr = decodeInput(task);
            Task taskObj = new Task();
            taskObj.setId(Integer.valueOf(taskIdStr));
            taskObj.setTask(taskStr);
            if (!getTaskManager().editTask(taskObj)) {
                responseBean.addBusinessMsg("修改任务失败.");
                return output(responseBean);
            }
        } catch (IOException e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
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
        ResponseBean responseBean = new ResponseBean();
        TransactionStatus txStatus = ensureTransaction();
        try {
            String studentIdStr = decodeInput(studentId);
            String taskIdStr = decodeInput(taskId);
            if (!getTaskManager().submitTask(Integer.valueOf(taskIdStr), Integer.valueOf(studentIdStr))) {
                responseBean.addBusinessMsg("提交任务失败.");
                return output(responseBean);
            }
        } catch (IOException e) {
            txStatus.setRollbackOnly();
            responseBean.addErrorMsg(e);
            LOG.error(e);
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
