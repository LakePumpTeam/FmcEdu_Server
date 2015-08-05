package com.fmc.edu.web.controller;

import com.fmc.edu.manager.*;
import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.model.clockin.ClockInType;
import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.push.PushMessageParameter;
import com.fmc.edu.model.push.PushMessageType;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
import com.fmc.edu.util.BeanUtils;
import com.fmc.edu.util.DateUtils;
import com.fmc.edu.util.RepositoryUtils;
import com.fmc.edu.util.StringUtils;
import com.fmc.edu.web.ResponseBean;
import com.fmc.edu.web.ResponseBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Yu on 7/18/2015.
 */
@Controller
@RequestMapping("/clock/in")
public class ClockInController extends BaseController {
    private static final Logger LOG = Logger.getLogger(HomePageController.class);
    @Resource(name = "clockInRecordManager")
    private ClockInRecordManager mClockInRecordManager;

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    @Resource(name = "baiduPushManager")
    private BaiDuPushManager mBaiDuPushManager;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "magneticCardManager")
    private MagneticCardManager mMagneticCardManager;

    @Resource(name = "responseBuilder")
    private ResponseBuilder mResponseBuilder;

    @Resource(name = "schoolManager")
    private SchoolManager mSchoolManager;

    @RequestMapping("/clockInRecords")
    @ResponseBody
    public String clockInRecords(final HttpServletRequest pRequest) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String studentId = pRequest.getParameter("studentId");
        String classId = pRequest.getParameter("classId");
        String attendanceFlag = pRequest.getParameter("attendanceFlag");
        if (RepositoryUtils.idIsValid(studentId)) {
            queryParentOrStudentClockInRecord(pRequest, studentId, attendanceFlag, responseBean);
        } else if (RepositoryUtils.idIsValid(classId)) {
            queryClassClockInRecord(pRequest, classId, responseBean);
        } else {
            responseBean.addBusinessMessage("参数错误.");
        }
        return output(responseBean);
    }

    private void queryParentOrStudentClockInRecord(final HttpServletRequest pRequest, String studentId, String attendanceFlag, ResponseBean responseBean) {
        String type = pRequest.getParameter("type");
        List<MagneticCard> magneticCards = null;
        if (Integer.valueOf(type) == ClockInType.PARENT_CLOCK_IN) {
            magneticCards = getMagneticCardManager().queryMagneticByStudentIdForParent(Integer.valueOf(studentId));
        } else {
            magneticCards = getMagneticCardManager().queryMagneticByStudentIdForStudent(Integer.valueOf(studentId));
        }

        if (CollectionUtils.isEmpty(magneticCards)) {
            responseBean.addBusinessMessage("未绑定有效磁卡.");
            return;
        }
        obtainClockInRecords(pRequest, responseBean, magneticCards);
        return;
    }

    private void queryClassClockInRecord(final HttpServletRequest pRequest, String classId, ResponseBean responseBean) {
        Map<String, Object> studentMap = getStudentManager().queryStudentsByClassId(Integer.valueOf(classId));
        if (studentMap == null || studentMap.size() == 0) {
            return;
        }
        List<Map<String, Object>> studentList = (List<Map<String, Object>>) studentMap.get("studentList");

        if (CollectionUtils.isEmpty(studentList)) {
            return;
        }
        List<Integer> studentIdList = new ArrayList<Integer>();
        for (Map<String, Object> student : studentList) {
            if (student == null) {
                continue;
            }
            Integer studentId = (Integer) student.get("studentId");
            if (!RepositoryUtils.idIsValid(studentId)) {
                continue;
            }
            studentIdList.add(studentId);
        }
        List<MagneticCard> magneticCards = getMagneticCardManager().queryMagneticByStudentIdForStudents(studentIdList);
        obtainClockInRecords(pRequest, responseBean, magneticCards);
    }

    private void obtainClockInRecords(final HttpServletRequest pRequest, ResponseBean responseBean, List<MagneticCard> pMagneticCards) {
        if (CollectionUtils.isEmpty(pMagneticCards)) {
            LOG.warn(">>>>>>>>无有效持卡<<<<<<<<<<<");
            return;
        }
        String type = pRequest.getParameter("type");
        String pageIndex = pRequest.getParameter("pageIndex");

        List<Integer> magneticCardIds = new ArrayList<Integer>();
        for (MagneticCard magneticCard : pMagneticCards) {
            magneticCardIds.add(magneticCard.getId());
        }
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("type", type);
        parameter.put("magneticCardIds", magneticCardIds);
        parameter.put("attendanceFlag", null);
        //query records by week
        //Map<String, Date> currentOneWeekDate = DateUtils.getOneWeekDatePeriod(new Timestamp(new Date().getTime()));
        //Date currentWeekEndDate = currentOneWeekDate.get("endDate");
        // currentOneWeekDate = DateUtils.getOneWeekDatePeriod(new Timestamp(DateUtils.minusDays(currentWeekEndDate, 7 * (Integer.valueOf(pageIndex) - 1)).getTime()));
        Map<String, Date> currentOneWeekDate = new HashMap<String, Date>(2);
        Date endDate = DateUtils.getDaysLater((Integer.valueOf(pageIndex) - 1));
        currentOneWeekDate.put("startDate", DateUtils.getDateTimeStart(endDate));
        currentOneWeekDate.put("endDate", DateUtils.getDateTimeEnd(endDate));
        parameter.putAll(currentOneWeekDate);
        List<ClockInRecord> clockInRecords = getClockInRecordManager().queryClockInRecords(parameter);
        getResponseBuilder().buildAttendanceRecords(clockInRecords, responseBean);
    }

    @RequestMapping("/notifyParentNorthDelta")
    @ResponseBody
    public String notifyParentNorthDelta(final HttpServletRequest pRequest) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String studentId = pRequest.getParameter("studentId");
        if (!RepositoryUtils.idIsValid(studentId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_EMPTY);
            return output(responseBean);
        }
        Student student = getStudentManager().queryStudentById(Integer.valueOf(studentId));
        if (student == null) {
            responseBean.addBusinessMsg(ResourceManager.ERROR_NOT_FIND_USER);
            return output(responseBean);
        }
        List<ParentStudentRelationship> parentStudentRelationships = getStudentManager().queryParentStudentRelationshipByStudentId(Integer.valueOf(studentId));
        if (CollectionUtils.isEmpty(parentStudentRelationships)) {
            LOG.debug("Can not find parent student relationship for student:" + studentId);
            return output(responseBean);
        }
        for (ParentStudentRelationship psr : parentStudentRelationships) {
            if (psr == null) {
                continue;
            }
            BaseProfile parent = getMyAccountManager().findUserById(String.valueOf(psr.getParentId()));
            if (parent == null) {
                LOG.debug("Can not find parent for id:" + psr.getParentId());
                continue;
            }
            //TODO if parent off line app, if we should send notification?
            if (!parent.isOnline()) {
                LOG.info("User off line status:" + psr.getParentId());
                continue;
            }

            PushMessageParameter pushMessage = new PushMessageParameter();
            pushMessage.addCustomContents(PushMessageParameter.MSG_TYPE, PushMessageType.TYPE_TEACHER_NOTIFY_PARENT_NORTH_DELTA.getValue());
            pushMessage.setTitle("提醒");
            pushMessage.setDescription(getResourceManager().getMessage(pRequest, ResourceManager.BAIDU_PUSH_MESSAGE_NOTIFY_NORTH_DELTA));
            try {
                if (StringUtils.isBlank(parent.getChannelId())) {
                    LOG.debug("Can not find channel id for parent:" + psr.getParentId());
                    continue;
                }
                getBaiDuPushManager().pushNotificationMsg(parent.getDeviceType(), new String[]{parent.getChannelId()}, parent.getId(), pushMessage);
            } catch (Exception e) {
                LOG.debug(e.getMessage());
            }
        }
        return output(responseBean);

    }


    @RequestMapping("/queryClockInParent")
    @ResponseBody
    public String queryClockInParent(HttpServletRequest pRequest,
                                     HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String classId = pRequest.getParameter("classId");
        String pageIndexStr = pRequest.getParameter("pageIndex");

        if (!RepositoryUtils.idIsValid(classId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_SCHOOL_CLASS_ID_ERROR, classId);
            return output(responseBean);
        }
        int pageIndex = 1;
        if (BeanUtils.isNumber(pageIndexStr)) {
            pageIndex = Integer.valueOf(pageIndexStr);
        }
        Set<BaseProfile> allParent = getSchoolManager().queryAllParentByClassId(Integer.valueOf(classId));
        if (CollectionUtils.isEmpty(allParent)) {
            responseBean.addBusinessMessage("未找到任何家长信息.");
            return output(responseBean);
        }
        List<Integer> parentProfileIds = new ArrayList<Integer>(allParent.size());
        for (BaseProfile parent : allParent) {
            if (parent == null) {
                continue;
            }
            parentProfileIds.add(parent.getId());
        }
        if (CollectionUtils.isEmpty(parentProfileIds)) {
            responseBean.addBusinessMessage("未找到任何家长信息.");
            return output(responseBean);
        }

        List<Map> records = getClockInRecordManager().queryClockInRecordsAttendances(parentProfileIds, Integer.valueOf(classId), DateUtils.getDaysLater(1 - pageIndex), 0);
        responseBean.addData("records", records);
        responseBean.addData("parentCount", parentProfileIds.size());
        return output(responseBean);
    }


    @RequestMapping("/queryNotClockInParent")
    @ResponseBody
    public String queryNotClockInParent(HttpServletRequest pRequest,
                                        HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String classId = pRequest.getParameter("classId");
        if (!RepositoryUtils.idIsValid(classId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_SCHOOL_CLASS_ID_ERROR, classId);
            return output(responseBean);
        }
        Set<BaseProfile> allParent = getSchoolManager().queryAllParentByClassId(Integer.valueOf(classId));
        if (CollectionUtils.isEmpty(allParent)) {
            responseBean.addBusinessMessage("未找到任何家长信息.");
            return output(responseBean);
        }
        List<Integer> parentProfileIds = new ArrayList<Integer>(allParent.size());
        for (BaseProfile parent : allParent) {
            if (parent == null) {
                continue;
            }
            parentProfileIds.add(parent.getId());
        }
        Date currentDate = DateUtils.getDaysLater(0);
        int period;
        if (DateUtils.isMorning(currentDate)) {
            period = 1;//morning
        } else {
            period = 2;//afternoon
        }
        List<Map> records = getClockInRecordManager().queryClockInRecordsAttendances(parentProfileIds, Integer.valueOf(classId), new Timestamp(currentDate.getTime()), period);
        List<Integer> notAttendancesParents = new ArrayList<Integer>();
        List<Integer> attendancesParents = new ArrayList<Integer>(records.size());
        if (CollectionUtils.isEmpty(records)) {
            notAttendancesParents.addAll(parentProfileIds);
        } else {
            for (Map record : records) {
                attendancesParents.add((Integer) record.get("parentId"));
            }
            parentProfileIds.removeAll(attendancesParents);
            notAttendancesParents.addAll(parentProfileIds);
        }

        if (CollectionUtils.isEmpty(notAttendancesParents)) {
            return output(responseBean);
        }
        List<Map> notAttendancesParentsRecords = getClockInRecordManager().queryNotAttendancesRecords(notAttendancesParents, Integer.valueOf(classId));
        responseBean.addData("records", notAttendancesParentsRecords);
        responseBean.addData("parentCount", parentProfileIds.size());
        return output(responseBean);
    }

    public BaiDuPushManager getBaiDuPushManager() {
        return mBaiDuPushManager;
    }

    public void setBaiDuPushManager(BaiDuPushManager pBaiDuPushManager) {
        mBaiDuPushManager = pBaiDuPushManager;
    }

    public ClockInRecordManager getClockInRecordManager() {
        return mClockInRecordManager;
    }

    public void setClockInRecordManager(ClockInRecordManager pClockInRecordManager) {
        mClockInRecordManager = pClockInRecordManager;
    }

    public ResponseBuilder getResponseBuilder() {
        return mResponseBuilder;
    }

    public void setResponseBuilder(ResponseBuilder pResponseBuilder) {
        mResponseBuilder = pResponseBuilder;
    }

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }

    public MagneticCardManager getMagneticCardManager() {
        return mMagneticCardManager;
    }

    public void setMagneticCardManager(MagneticCardManager pMagneticCardManager) {
        mMagneticCardManager = pMagneticCardManager;
    }

    public SchoolManager getSchoolManager() {
        return mSchoolManager;
    }

    public void setSchoolManager(SchoolManager pSchoolManager) {
        mSchoolManager = pSchoolManager;
    }
}
