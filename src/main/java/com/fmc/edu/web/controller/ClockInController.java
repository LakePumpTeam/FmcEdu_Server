package com.fmc.edu.web.controller;

import com.fmc.edu.manager.*;
import com.fmc.edu.model.app.AppSetting;
import com.fmc.edu.model.clockin.ClockInRecord;
import com.fmc.edu.model.clockin.ClockInType;
import com.fmc.edu.model.clockin.MagneticCard;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.push.MessageNotificationBasicStyle;
import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.model.student.Student;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/clockInRecords")
    @ResponseBody
    public String clockInRecords(final HttpServletRequest pRequest) {
        ResponseBean responseBean = new ResponseBean(pRequest);
        String type = pRequest.getParameter("type");
        String studentId = pRequest.getParameter("studentId");
        String attendanceFlag = pRequest.getParameter("attendanceFlag");
        String pageIndex = pRequest.getParameter("pageIndex");
        if (!RepositoryUtils.idIsValid(studentId)) {
            responseBean.addBusinessMsg(ResourceManager.VALIDATION_USER_USER_ID_EMPTY);
            return output(responseBean);
        }
        List<MagneticCard> magneticCards = null;
        if (Integer.valueOf(type) == ClockInType.PARENT_CLOCK_IN) {
            magneticCards = getMagneticCardManager().queryMagneticByStudentIdForParent(Integer.valueOf(studentId));
        } else {
            magneticCards = getMagneticCardManager().queryMagneticByStudentIdForStudent(Integer.valueOf(studentId));
        }

        if (CollectionUtils.isEmpty(magneticCards)) {
            responseBean.addBusinessMessage("未绑定有效磁卡.");
            return output(responseBean);
        }

        for (MagneticCard magneticCard : magneticCards) {
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("type", type);
            parameter.put("magneticCardId", magneticCard.getId());
            parameter.put("attendanceFlag", attendanceFlag);
            Map<String, Date> currentOneWeekDate = DateUtils.getOneWeekDatePeriod(new Timestamp(new Date().getTime()));
            Date currentWeekEndDate = currentOneWeekDate.get("endDate");
            currentOneWeekDate = DateUtils.getOneWeekDatePeriod(new Timestamp(DateUtils.minusDays(currentWeekEndDate, 7 * (Integer.valueOf(pageIndex) - 1)).getTime()));
            parameter.putAll(currentOneWeekDate);
            List<ClockInRecord> clockInRecords = getClockInRecordManager().queryClockInRecords(parameter);
            getResponseBuilder().buildAttendanceRecords(clockInRecords, responseBean);
        }
        return responseBean.toString();
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
            PushMessage pushMessage = new PushMessage();
            pushMessage.setTitle("接学生提醒");
            pushMessage.setDescription("您还未到校接学生!");
            try {
                if (StringUtils.isBlank(parent.getChannelId())) {
                    LOG.debug("Can not find channel id for parent:" + psr.getParentId());
                    continue;
                }

                AppSetting appSetting = getMyAccountManager().queryAppSetting(parent.getId());
                if (appSetting != null) {
                    if (appSetting.isIsBel() && appSetting.isIsVibra()) {
                        pushMessage.setNotification_basic_style(MessageNotificationBasicStyle.BEL_VIBRA_ERASIBLE);
                    } else if (appSetting.isIsBel() && !appSetting.isIsVibra()) {
                        pushMessage.setNotification_basic_style(MessageNotificationBasicStyle.BEL_ERASIBLE);
                    } else if (appSetting.isIsVibra() && !appSetting.isIsBel()) {
                        pushMessage.setNotification_basic_style(MessageNotificationBasicStyle.VIBRA_ERASIBLE);
                    } else {
                        pushMessage.setNotification_basic_style(MessageNotificationBasicStyle.ERASIBLE);
                    }
                }
                getBaiDuPushManager().pushNotificationMsg(parent.getDeviceType(), new String[]{parent.getChannelId()}, parent.getAppId(), pushMessage);
            } catch (Exception e) {
                LOG.debug(e.getMessage());
            }
        }
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
}
