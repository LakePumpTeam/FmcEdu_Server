package com.fmc.edu.web.controller;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.manager.*;
import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.model.clockin.ClockInType;
import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.push.PushMessageParameter;
import com.fmc.edu.model.push.PushMessageType;
import com.fmc.edu.model.relationship.PersonCarMagneticRelationship;
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
import org.springframework.transaction.TransactionStatus;
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
        if (RepositoryUtils.idIsValid(studentId)) {
            queryParentOrStudentClockInRecord(pRequest, studentId, responseBean);
        } else if (RepositoryUtils.idIsValid(classId)) {
            queryClassClockInRecord(pRequest, classId, responseBean);
        } else {
            responseBean.addBusinessMessage("Error parameters.");
        }
        return output(responseBean);
    }

    private void queryParentOrStudentClockInRecord(final HttpServletRequest pRequest, String studentId, ResponseBean responseBean) {
        List<MagneticCard> magneticCards = getMagneticCardManager().queryMagneticCardsByStudentId(Integer.valueOf(studentId));

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
        List<MagneticCard> magneticCards = getMagneticCardManager().queryMagneticCardsByStudentId(studentIdList);
        obtainClockInRecords(pRequest, responseBean, magneticCards);
    }

    private void obtainClockInRecords(final HttpServletRequest pRequest, ResponseBean responseBean, List<MagneticCard> pMagneticCards) {
        if (CollectionUtils.isEmpty(pMagneticCards)) {
            LOG.warn(">>>>>>>>无有效持卡<<<<<<<<<<<");
            return;
        }
        String pageIndex = pRequest.getParameter("pageIndex");

        List<Integer> magneticCardIds = new ArrayList<Integer>();
        Set<Integer> cardType = new HashSet<Integer>();
        for (MagneticCard magneticCard : pMagneticCards) {
            magneticCardIds.add(magneticCard.getId());
            cardType.add(magneticCard.getCardType());
        }
        if (cardType.size() != 1) {
            LOG.error("错误卡类型:" + cardType.size() + " cardType:" + cardType);
            responseBean.addBusinessMessage("错误类型卡!");
            return;
        }
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("type", cardType.iterator().next());
        parameter.put("magneticCardIds", magneticCardIds);
        // parameter.put("attendanceFlag", null);
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
        if (getStudentManager().queryStudentById(Integer.valueOf(studentId)) == null) {
            responseBean.addBusinessMsg("学生不存在:" + studentId);
            return output(responseBean);
        }
        PushMessageParameter pushMessage = new PushMessageParameter();
        pushMessage.addCustomContents(PushMessageType.TYPE_TEACHER_NOTIFY_PARENT_NORTH_DELTA.getValue());
        pushMessage.setTitle("提醒");
        pushMessage.setDescription(getResourceManager().getMessage(pRequest, ResourceManager.BAIDU_PUSH_MESSAGE_NOTIFY_NORTH_DELTA));
        try {
            getBaiDuPushManager().pushNotificationMsg(Integer.valueOf(studentId), pushMessage);
        } catch (Exception e) {
            LOG.debug(e.getMessage());
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

    @RequestMapping("/updateClockInRecord")
    @ResponseBody
    public String updateClockInRecord(HttpServletRequest pRequest,
                                      HttpServletResponse pResponse) {
        ResponseBean responseBean = new ResponseBean(pRequest);

        String cardId = pRequest.getParameter("cardId");
        if (StringUtils.isBlank(cardId)) {
            responseBean.addBusinessMessage("Magnetic card id is blank.");
            return output(responseBean);
        }

        String dateTime = pRequest.getParameter("dateTime");
        if (StringUtils.isBlank(dateTime) || DateUtils.convertStringToDateTime(dateTime) == null) {

            responseBean.addBusinessMessage("DateTime field is invalid format:" + dateTime);
            return output(responseBean);
        }
        //1: in 2:out 3:send 4:take back 5:lost card reuse
        String clockInType = pRequest.getParameter("clockInType");
        if (!BeanUtils.isValidClockInType(clockInType)) {
            responseBean.addBusinessMessage("Clock in type is invalid value: " + clockInType);
            return output(responseBean);
        }

        MagneticCard magneticCard = getMagneticCardManager().queryMagneticCardByCardNo(cardId);
        if (magneticCard == null) {
            responseBean.addBusinessMessage("Can not find magnetic card for card id: " + cardId);
            return output(responseBean);
        }
        PersonCarMagneticRelationship personCarMagneticRelationship = getMagneticCardManager().queryPersonMagneticCardRelationship(magneticCard.getId());
        if (personCarMagneticRelationship == null) {
            responseBean.addBusinessMessage("Can not find any relationship with person for the card: " + cardId);
            return output(responseBean);
        }
        if (!WebConfig.isDevelopment()) {
            if (personCarMagneticRelationship.isAvailable() == -1) {//marked lost card
                clockInType = "5";
            }
        }
        switch (clockInType) {
            case "1": {
                saveStudentClockInInRecord(pRequest, pResponse, magneticCard, personCarMagneticRelationship, responseBean);
                break;
            }
            case "2": {
                saveStudentClockInOutRecord(pRequest, pResponse, magneticCard, personCarMagneticRelationship, responseBean);
                break;
            }
            case "3": {
                saveParentSendChildRecord(pRequest, pResponse, magneticCard, personCarMagneticRelationship, responseBean);
                break;
            }
            case "4": {
                saveParentTakeBackChildRecord(pRequest, pResponse, magneticCard, personCarMagneticRelationship, responseBean);
                break;
            }
            case "5": {
                lostCardReuseNotify(pRequest, pResponse, magneticCard, personCarMagneticRelationship, responseBean);
                break;
            }
        }

        return output(responseBean);
    }

    private void saveStudentClockInInRecord(HttpServletRequest pRequest,
                                            HttpServletResponse pResponse,
                                            MagneticCard magneticCard,
                                            PersonCarMagneticRelationship personCarMagneticRelationship,
                                            ResponseBean pResponseBean) {
        String dateTime = pRequest.getParameter("dateTime");
        Date attendanceDate;
        attendanceDate = DateUtils.convertStringToDateTime(dateTime);
        if (attendanceDate == null) {
            attendanceDate = DateUtils.getDaysLater(0);
        }
        TransactionStatus txStatus = ensureTransaction();
        ClockInRecord clockInRecord = createClockInRecord(ClockInType.STUDENT_CLOCK_IN, magneticCard.getId(), personCarMagneticRelationship.getStudentId(), 0, attendanceDate);
        boolean isSuccess = false;
        try {
            getClockInRecordManager().insertClockInRecord(clockInRecord);
            isSuccess = true;
        } catch (Exception ex) {
            pResponseBean.addErrorMsg(ex);
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }

        if (!isSuccess) {
            LOG.error("saveStudentClockInInRecord(): Save clock in record failed.");
            return;
        }
        PushMessageParameter pushMessage = new PushMessageParameter();
        pushMessage.addCustomContents(PushMessageType.TYPE_CLOCK_IN_STUDENT_IN.getValue());
        pushMessage.setTitle("提示");
        pushMessage.setDescription(getResourceManager().getMessage(pRequest, ResourceManager.BAIDU_PUSH_MESSAGE_STUDENT_TO_SCHOOL,
                new String[]{clockInRecord.getClockInPersonName(), DateUtils.convertDateToDateTimeString(clockInRecord.getAttendanceDate())}));
        try {
            getBaiDuPushManager().pushNotificationMsg(personCarMagneticRelationship.getStudentId(), pushMessage);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    private void saveStudentClockInOutRecord(HttpServletRequest pRequest,
                                             HttpServletResponse pResponse,
                                             MagneticCard magneticCard,
                                             PersonCarMagneticRelationship personCarMagneticRelationship,
                                             ResponseBean pResponseBean) {
        String dateTime = pRequest.getParameter("dateTime");
        Date attendanceDate;
        attendanceDate = DateUtils.convertStringToDateTime(dateTime);
        if (attendanceDate == null) {
            attendanceDate = DateUtils.getDaysLater(0);
        }
        TransactionStatus txStatus = ensureTransaction();
        ClockInRecord clockInRecord = createClockInRecord(ClockInType.STUDENT_CLOCK_IN, magneticCard.getId(), personCarMagneticRelationship.getStudentId(), 1, attendanceDate);
        boolean isSuccess = false;
        try {
            getClockInRecordManager().insertClockInRecord(clockInRecord);
            isSuccess = true;
        } catch (Exception ex) {
            pResponseBean.addErrorMsg(ex);
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }

        if (!isSuccess) {
            LOG.error("saveStudentClockInOutRecord(): Save clock in record failed.");
            return;
        }
        PushMessageParameter pushMessage = new PushMessageParameter();
        pushMessage.addCustomContents(PushMessageType.TYPE_CLOCK_IN_STUDENT_OUT.getValue());
        pushMessage.setTitle("提示");
        pushMessage.setDescription(getResourceManager().getMessage(pRequest, ResourceManager.BAIDU_PUSH_MESSAGE_STUDENT_LEFT_SCHOOL,
                new String[]{clockInRecord.getClockInPersonName(), DateUtils.convertDateToDateTimeString(clockInRecord.getAttendanceDate())}));
        try {
            getBaiDuPushManager().pushNotificationMsg(personCarMagneticRelationship.getStudentId(), pushMessage);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    private void saveParentSendChildRecord(HttpServletRequest pRequest,
                                           HttpServletResponse pResponse,
                                           MagneticCard magneticCard,
                                           PersonCarMagneticRelationship personCarMagneticRelationship,
                                           ResponseBean pResponseBean) {
        String dateTime = pRequest.getParameter("dateTime");
        Date attendanceDate;
        attendanceDate = DateUtils.convertStringToDateTime(dateTime);
        if (attendanceDate == null) {
            attendanceDate = DateUtils.getDaysLater(0);
        }
        TransactionStatus txStatus = ensureTransaction();
        ClockInRecord clockInRecord = createClockInRecord(ClockInType.PARENT_CLOCK_IN, magneticCard.getId(), personCarMagneticRelationship.getStudentId(), -1, attendanceDate);
        boolean isSuccess = false;
        try {
            getClockInRecordManager().insertClockInRecord(clockInRecord);
            isSuccess = true;
        } catch (Exception ex) {
            pResponseBean.addErrorMsg(ex);
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }

        if (!isSuccess) {
            LOG.error("saveParentSendChildRecord(): Save clock in record failed.");
            return;
        }
        Student student = getStudentManager().queryStudentById(personCarMagneticRelationship.getStudentId());
        if (student == null) {
            LOG.error("saveParentSendChildRecord():Can not find student: " + personCarMagneticRelationship.getStudentId());
            return;
        }
        PushMessageParameter pushMessage = new PushMessageParameter();
        pushMessage.addCustomContents(PushMessageType.TYPE_PARENT_SEND_CHILD.getValue());
        pushMessage.setTitle("提示");
        pushMessage.setDescription(getResourceManager().getMessage(pRequest, ResourceManager.BAIDU_PUSH_MESSAGE_SENT_CHILD,
                new String[]{clockInRecord.getClockInPersonName(), student.getName()}));
        try {
            getBaiDuPushManager().pushNotificationMsg(personCarMagneticRelationship.getStudentId(), pushMessage);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    private void saveParentTakeBackChildRecord(HttpServletRequest pRequest,
                                               HttpServletResponse pResponse,
                                               MagneticCard magneticCard,
                                               PersonCarMagneticRelationship personCarMagneticRelationship,
                                               ResponseBean pResponseBean) {
        String dateTime = pRequest.getParameter("dateTime");
        Date attendanceDate;
        attendanceDate = DateUtils.convertStringToDateTime(dateTime);
        if (attendanceDate == null) {
            attendanceDate = DateUtils.getDaysLater(0);
        }
        TransactionStatus txStatus = ensureTransaction();
        ClockInRecord clockInRecord = createClockInRecord(ClockInType.PARENT_CLOCK_IN, magneticCard.getId(), personCarMagneticRelationship.getStudentId(), -1, attendanceDate);
        boolean isSuccess = false;
        try {
            getClockInRecordManager().insertClockInRecord(clockInRecord);
            isSuccess = true;
        } catch (Exception ex) {
            pResponseBean.addErrorMsg(ex);
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }

        if (!isSuccess) {
            LOG.error("saveParentSendChildRecord(): Save clock in record failed.");
            return;
        }
        Student student = getStudentManager().queryStudentById(personCarMagneticRelationship.getStudentId());
        if (student == null) {
            LOG.error("saveParentSendChildRecord():Can not find student: " + personCarMagneticRelationship.getStudentId());
            return;
        }
        PushMessageParameter pushMessage = new PushMessageParameter();
        pushMessage.addCustomContents(PushMessageType.TYPE_PARENT_NORTH_DELTA.getValue());
        pushMessage.setTitle("提示");
        pushMessage.setDescription(getResourceManager().getMessage(pRequest, ResourceManager.BAIDU_PUSH_MESSAGE_NORTH_DELTA,
                new String[]{clockInRecord.getClockInPersonName(), student.getName()}));
        try {
            getBaiDuPushManager().pushNotificationMsg(personCarMagneticRelationship.getStudentId(), pushMessage);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    private void lostCardReuseNotify(HttpServletRequest pRequest,
                                     HttpServletResponse pResponse,
                                     MagneticCard magneticCard,
                                     PersonCarMagneticRelationship personCarMagneticRelationship,
                                     ResponseBean pResponseBean) {
        String dateTime = pRequest.getParameter("dateTime");
        Date attendanceDate;
        attendanceDate = DateUtils.convertStringToDateTime(dateTime);
        if (attendanceDate == null) {
            attendanceDate = DateUtils.getDaysLater(0);
        }
        TransactionStatus txStatus = ensureTransaction();
        ClockInRecord clockInRecord = createClockInRecord(-1, magneticCard.getId(), -1, -1, attendanceDate);
        boolean isSuccess = false;
        try {
            getClockInRecordManager().insertClockInRecord(clockInRecord);
            isSuccess = true;
        } catch (Exception ex) {
            pResponseBean.addErrorMsg(ex);
            LOG.error(ex);
            txStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(txStatus);
        }

        if (!isSuccess) {
            LOG.error("saveParentSendChildRecord(): Save clock in record failed.");
            return;
        }
        BaseProfile parent = getMyAccountManager().findUserById(String.valueOf(personCarMagneticRelationship.getParentId()));
        if (parent == null) {
            LOG.error("createClockInRecord(): Cannot find parent: " + personCarMagneticRelationship.getParentId());
            return;
        }
        Student student = getStudentManager().queryStudentById(personCarMagneticRelationship.getStudentId());
        if (student == null) {
            LOG.error("saveParentSendChildRecord():Can not find student: " + personCarMagneticRelationship.getStudentId());
            return;
        }
        PushMessageParameter pushMessage = new PushMessageParameter();
        pushMessage.addCustomContents(PushMessageType.TYPE_WARNING_LOST_CARD.getValue());
        pushMessage.setTitle("警告!!!");
        pushMessage.setDescription(getResourceManager().getMessage(pRequest, ResourceManager.BAIDU_PUSH_MESSAGE_WARNING_USING_LOST_CARD,
                new String[]{DateUtils.convertDateToDateTimeString(clockInRecord.getAttendanceDate()),
                        student.getName(),
                        String.valueOf(magneticCard.getCardNo()),
                        parent.getName()}));
        try {
            getBaiDuPushManager().pushNotificationMsg(personCarMagneticRelationship.getStudentId(), pushMessage);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    private ClockInRecord createClockInRecord(int pClockInType, int cardId, int clockInPersonId, int attendanceFlag, Date attendanceDate) {

        ClockInRecord clockInRecord = new ClockInRecord();
        clockInRecord.setAttendanceDate(attendanceDate);
        clockInRecord.setAttendanceFlag(attendanceFlag);
        clockInRecord.setClockInPersonId(clockInPersonId);
        if (pClockInType == ClockInType.STUDENT_CLOCK_IN) {
            Student student = getStudentManager().queryStudentById(clockInPersonId);
            if (student == null) {
                LOG.error("createClockInRecord(): Cannot find student: " + clockInPersonId);
            } else {
                clockInRecord.setClockInPersonName(student.getName());
            }
        } else if (pClockInType == ClockInType.PARENT_CLOCK_IN) {
            BaseProfile parent = getMyAccountManager().findUserById(String.valueOf(clockInPersonId));
            if (parent == null) {
                LOG.error("createClockInRecord(): Cannot find parent: " + clockInPersonId);
            } else {
                clockInRecord.setClockInPersonName(parent.getName());
            }
        } else {
            clockInRecord.setClockInPersonName("[非法操作]");
        }
        clockInRecord.setCreationDate(DateUtils.getDaysLater(0));
        clockInRecord.setMagneticCardId(cardId);
        clockInRecord.setType(pClockInType);

        return clockInRecord;
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
