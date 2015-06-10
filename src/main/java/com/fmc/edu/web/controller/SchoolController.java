package com.fmc.edu.web.controller;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.exception.ProfileException;
import com.fmc.edu.manager.ResourceManager;
import com.fmc.edu.manager.SchoolManager;
import com.fmc.edu.manager.StudentManager;
import com.fmc.edu.manager.TeacherManager;
import com.fmc.edu.model.course.Course;
import com.fmc.edu.model.course.TimeTable;
import com.fmc.edu.model.profile.TeacherProfile;
import com.fmc.edu.model.school.FmcClass;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.util.pagenation.Pagination;
import com.fmc.edu.web.RequestParameterBuilder;
import com.fmc.edu.web.ResponseBean;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
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
 * Created by Yu on 2015/5/10.
 */
@Controller
@RequestMapping("/school")
public class SchoolController extends BaseController {
    private static final Logger LOG = Logger.getLogger(SchoolController.class);

    public static int[] WEEK_LIST = {1, 2, 3, 4, 5, 6, 7};

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    @Resource(name = "teacherManager")
    private TeacherManager mTeacherManager;

    @Resource(name = "requestParameterBuilder")
    private RequestParameterBuilder mParameterBuilder;

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    @RequestMapping("/requestSchools")
    @ResponseBody
    public String requestSchools(final HttpServletRequest pRequest,
                                 final HttpServletResponse pResponse,
                                 final String cityId,
                                 final String filterKey) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(cityId)) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_LOCATION_CITY_ID_ERROR);
            return output(responseBean);
        }
        try {
            Pagination pagination = buildPagination(pRequest);

            Map<String, Object> schools = getSchoolManager().querySchoolsPage(pagination, Integer.valueOf(cityId), filterKey);
            responseBean.addData(schools);
        } catch (IOException e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } catch (Exception ex) {
            responseBean.addErrorMsg(ex);
            LOG.error(ex);
        }
        return responseBean.toString();
    }

    @RequestMapping("/requestClasses")
    @ResponseBody
    public String requestClasses(final HttpServletRequest pRequest,
                                 final HttpServletResponse pResponse,
                                 final String schoolId,
                                 final String filterKey) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(schoolId)) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_LOCATION_SCHOOL_ID_ERROR);
            return output(responseBean);
        }
        try {
            String school = schoolId;
            String filter = filterKey;
            Pagination pagination = buildPagination(pRequest);

            Map<String, Object> classses = getSchoolManager().queryClassesPage(pagination, Integer.valueOf(school), filter);
            responseBean.addData(classses);
        } catch (IOException e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
        } catch (Exception ex) {
            responseBean.addErrorMsg(ex);
            LOG.error(ex);
        }
        return responseBean.toString();
    }


    @RequestMapping("/requestHeadTeacher")
    @ResponseBody
    public String requestHeadmaster(final HttpServletRequest pRequest,
                                    final HttpServletResponse pResponse,
                                    final String classId) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(classId)) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_LOCATION_CLASS_ID_ERROR);
            return output(responseBean);
        }

        try {
            String correspondingClass = classId;

            Map<String, Object> headmaster = getSchoolManager().queryHeadmasterPage(Integer.valueOf(correspondingClass));
            responseBean.addData(headmaster);
        } catch (Exception ex) {
            responseBean.addErrorMsg(ex);
            LOG.error(ex);
        }
        return responseBean.toString();
    }

    @RequestMapping("/requestTeacherInfo")
    @ResponseBody
    public String requestTeacherInfo(final HttpServletRequest pRequest,
                                     final HttpServletResponse pResponse,
                                     final String teacherId) throws IOException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(teacherId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_TEACHER_ID_ERROR, teacherId);
            return output(responseBean);
        }

        int tid = Integer.valueOf(teacherId);
        TeacherProfile teacher = getTeacherManager().queryTeacherById(tid);
        if (teacher == null) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_TEACHER_UNKNOWN, teacherId);
            return output(responseBean);
        }
        responseBean.addData("teacherName", teacher.getName());
        responseBean.addData("teacherBirth", DateUtils.ConvertDateToString(teacher.getBirth()));
        responseBean.addData("cellPhone", teacher.getPhone());
        responseBean.addData("course", teacher.getCourse());
        responseBean.addData("resume", teacher.getResume());
        responseBean.addData("teacherSex", teacher.isMale());
        return output(responseBean);
    }

    @RequestMapping(value = "/requestModifyTeacherInfo" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestModifyTeacherInfo(HttpServletRequest pRequest,
                                           final HttpServletResponse pResponse,
                                           final String teacherId)
            throws IOException, ParseException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(teacherId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_TEACHER_ID_ERROR, teacherId);
            return output(responseBean);
        }
        TransactionStatus status = ensureTransaction();
        try {
            TeacherProfile teacher = getParameterBuilder().buildTeacher(pRequest);
            getTeacherManager().updateTeacher(teacher);
        } catch (ProfileException ex) {
            responseBean.addBusinessMsg(ex.getMessage(), ex.getArgs());
            status.setRollbackOnly();
        } catch (Exception e) {
            responseBean.addErrorMsg(e);
            LOG.error(e);
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
            return responseBean.toString();
        }
    }

    @RequestMapping(value = "/requestStudentList" + GlobalConstant.URL_SUFFIX)
    @ResponseBody
    public String requestStudentList(HttpServletRequest pRequest,
                                     final HttpServletResponse pResponse,
                                     final String teacherId)
            throws IOException, ParseException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(teacherId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_TEACHER_ID_ERROR, teacherId);
            return output(responseBean);
        }
        try {
            int decodeTeacherId = Integer.valueOf(teacherId);
            Map<String, Object> studentMap = getStudentManager().queryStudentListByTeacherId(decodeTeacherId);
            responseBean.addData(studentMap);
        } catch (Exception e) {
            LOG.error(e);
            responseBean.addErrorMsg(e);
        }
        return output(responseBean);
    }

    @RequestMapping("/requestClassCourseList")
    @ResponseBody
    public String requestClassCourseList(HttpServletRequest pRequest,
                                         final HttpServletResponse pResponse,
                                         final String classId)
            throws IOException, ParseException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (!RepositoryUtils.idIsValid(classId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_SCHOOL_CLASS_ID_ERROR, classId);
            return output(responseBean);
        }
        FmcClass fmcClass = getTeacherManager().queryClassById(Integer.valueOf(classId));
        if (fmcClass == null) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FOND_CLASS, classId);
            return output(responseBean);
        }
        responseBean.addData("classId", fmcClass.getId());
        List<Map<String, Object>> courseList = new ArrayList<Map<String, Object>>();
        responseBean.addData("courseList", courseList);
        List<Course> courses;
        Map<String, Object> courseListItem;
        List<Map<String, Object>> courseListMap;
        Map<String, Object> courseDetail;
        for (int week : WEEK_LIST) {
            courseListItem = new HashMap<String, Object>();
            courseListItem.put("week", week);
            courseListMap = new ArrayList<Map<String, Object>>();
            courseListItem.put("courses", courseListMap);
            courses = getSchoolManager().queryCourseListByClassId(Integer.valueOf(classId), week);
            if (courses != null) {
                for (Course course : courses) {
                    courseDetail = new HashMap<String, Object>(6);
                    courseDetail.put("courseId", course.getId());
                    courseDetail.put("order", course.getOrder());
                    courseDetail.put("orderName", course.getOrderName());
                    courseDetail.put("startTime", DateUtils.convertTimeToString(course.getStartTime()));
                    courseDetail.put("endTime", DateUtils.convertTimeToString(course.getEndTime()));
                    courseListMap.add(courseDetail);
                }
            }
            courseList.add(courseListItem);
        }
        return output(responseBean);
    }


    @RequestMapping("/submitClassCourse")
    @ResponseBody
    public String submitClassCourse(HttpServletRequest pRequest,
                                    final HttpServletResponse pResponse,
                                    String course)
            throws IOException, ParseException {
        ResponseBean responseBean = new ResponseBean(pRequest);
        if (StringUtils.isBlank(course)) {
            return output(responseBean);
        }
        JSONObject jsonCourse = new JSONObject(course);
        if (jsonCourse == null || jsonCourse.length() == 0) {
            return output(responseBean);
        }
        int week;
        int classId;
        JSONArray courseArray;
        JSONObject jsonObject;
        int courseId;
        int order;
        String orderName;
        String courseName;
        String startTime;
        String endTime;
        Course courseBean;
        TimeTable timeTable;
        TransactionStatus txStatus = ensureTransaction();
        try {
            courseBean = new Course();
            week = jsonCourse.getInt("week");
            classId = jsonCourse.getInt("classId");
            courseBean.setWeek(week);
            courseArray = jsonCourse.getJSONArray("courses");
            if (courseArray == null || courseArray.length() == 0) {
                return output(responseBean);
            }
            for (int i = 0; i < courseArray.length(); i++) {
                jsonObject = courseArray.getJSONObject(i);
                if (jsonObject == null) {
                    continue;
                }
                if (jsonObject.keySet().contains("courseId")) {
                    courseId = jsonObject.getInt("courseId");
                    courseBean.setId(courseId);
                }
                order = jsonObject.getInt("order");
                orderName = jsonObject.getString("orderName");
                courseName = jsonObject.getString("courseName");
                startTime = jsonObject.getString("startTime");
                endTime = jsonObject.getString("endTime");
                if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
                    LOG.debug("NOT SET startTime Or endTime, SKIP THIS DATA.");
                    continue;
                }
                courseBean.setOrder(order);
                courseBean.setOrderName(orderName);
                courseBean.setCourseName(courseName);
                courseBean.setStartTime(DateUtils.convertStringToTime(startTime));
                courseBean.setEndTime(DateUtils.convertStringToTime(endTime));

                if (!RepositoryUtils.idIsValid(courseBean.getId())) {
                    timeTable = new TimeTable();
                    timeTable.setClassId(classId);
                    getSchoolManager().insertTimeTable(timeTable);
                    courseBean.setTimeTableId(timeTable.getId());
                    getSchoolManager().insertCourse(courseBean);
                } else {
                    getSchoolManager().updateCourse(courseBean);
                }
            }
        } catch (Exception e) {
            txStatus.setRollbackOnly();
            LOG.error(e);
        } finally {
            getTransactionManager().commit(txStatus);
        }
        return output(responseBean);
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(SchoolManager pSchoolManager) {
        this.mSchoolManager = pSchoolManager;
    }

    public RequestParameterBuilder getParameterBuilder() {
        return mParameterBuilder;
    }

    public void setParameterBuilder(final RequestParameterBuilder pParameterBuilder) {
        mParameterBuilder = pParameterBuilder;
    }

    public TeacherManager getTeacherManager() {
        return mTeacherManager;
    }

    public void setTeacherManager(final TeacherManager pTeacherManager) {
        mTeacherManager = pTeacherManager;
    }

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }
}
