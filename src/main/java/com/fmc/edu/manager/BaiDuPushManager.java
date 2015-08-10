package com.fmc.edu.manager;

import com.fmc.edu.model.app.AppSetting;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.push.MessageNotificationBasicStyle;
import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.model.push.PushMessageParameter;
import com.fmc.edu.model.relationship.ParentStudentRelationship;
import com.fmc.edu.push.IBaiDuPushNotification;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * The service class used to push message to app.
 */
@Service(value = "baiduPushManager")
public class BaiDuPushManager {
    private static final Logger LOG = Logger.getLogger(BaiDuPushManager.class);

    @Resource(name = "AndroidPushNotification")
    private IBaiDuPushNotification mBaiDuAndroidPushNotification;

    @Resource(name = "IOSPushNotification")
    private IBaiDuPushNotification mBaiDuIOSPushNotification;

    @Resource(name = "pushMessageManager")
    private PushMessageManager mPushMessageManager;

    @Resource(name = "myAccountManager")
    private MyAccountManager mMyAccountManager;

    @Resource(name = "studentManager")
    private StudentManager mStudentManager;

    public boolean pushNotificationMsg(final int studentId, final PushMessageParameter pMsg) throws Exception {
        List<ParentStudentRelationship> parentStudentRelationships = getStudentManager().queryParentStudentRelationshipByStudentId(Integer.valueOf(studentId));
        if (CollectionUtils.isEmpty(parentStudentRelationships)) {
            LOG.debug("Can not find parent student relationship for student:" + studentId);
            return false;
        }
        Set<BaseProfile> parentList = new HashSet<BaseProfile>();
        BaseProfile parent = null;
        for (ParentStudentRelationship psr : parentStudentRelationships) {
            if (psr == null) {
                continue;
            }
            //TODO can refactor for this query: batch query
            parent = getMyAccountManager().findUserById(String.valueOf(psr.getParentId()));
            parentList.add(parent);
        }
        if (CollectionUtils.isEmpty(parentList)) {
            LOG.warn("pushNotificationMsg(): Cannot find parent for student: " + studentId);
            return false;
        }

        Iterator<BaseProfile> parentIterator = parentList.iterator();
        List<PushMessage> pushMessageList = new ArrayList<PushMessage>(parentList.size());
        PushMessage pushMessage;
        while (parentIterator.hasNext()) {
            parent = parentIterator.next();
            if (parent == null) {
                continue;
            }
            //TODO if parent off line app, if we should send notification?
            if (!parent.isOnline()) {
                LOG.info("pushNotificationMsg(): User off line status:" + parent);
                return false;
            }
            AppSetting appSetting = getMyAccountManager().queryAppSetting(parent.getId());
            if (appSetting != null) {
                if (appSetting.isIsBel() && appSetting.isIsVibra()) {
                    pMsg.setNotification_basic_style(MessageNotificationBasicStyle.BEL_VIBRA_ERASIBLE);
                } else if (appSetting.isIsBel() && !appSetting.isIsVibra()) {
                    pMsg.setNotification_basic_style(MessageNotificationBasicStyle.BEL_ERASIBLE);
                } else if (appSetting.isIsVibra() && !appSetting.isIsBel()) {
                    pMsg.setNotification_basic_style(MessageNotificationBasicStyle.VIBRA_ERASIBLE);
                } else {
                    pMsg.setNotification_basic_style(MessageNotificationBasicStyle.ERASIBLE);
                }
            }
            boolean isSuccess = pushNotificationMsg(parent.getDeviceType(), new String[]{parent.getChannelId()}, pMsg);
            LOG.debug("pushNotificationMsg(): Pushed message to parent:[[" + parent.getName() + "]] SUCCESS.");
            pushMessage = new PushMessage();
            pushMessage.setProfileId(parent.getId());
            pushMessage.setTitle(pMsg.getTitle());
            pushMessage.setContent(pMsg.getDescription());
            pushMessage.setPushDeviceType(parent.getDeviceType());
            pushMessage.setPushType(2);
            pushMessage.setMessageType(Integer.parseInt(pMsg.getCustom_content().get(PushMessageParameter.MSG_TYPE).toString()));
            pushMessage.setPushStatus(isSuccess);
            pushMessageList.add(pushMessage);
        }
        if (CollectionUtils.isEmpty(pushMessageList)) {
            LOG.info("pushNotificationMsg(): No any push message need to be saved.");
            return false;
        }
        try {
            return getPushMessageManager().insertPushMessages(pushMessageList);
        } catch (Exception ex) {
            LOG.error(ex);
        }
        return false;
    }

    private boolean pushNotificationMsg(final int pDevice, final String[] pChannelIds, final PushMessageParameter pMsg) throws Exception {
        LOG.debug("push notification message: device type is:" + DeviceType.toString(pDevice));
        LOG.debug("push notification message: push message is:" + pMsg.toString());

        boolean isPushSuccess = false;
        if (DeviceType.ANDROID == pDevice) {
            isPushSuccess = getBaiDuAndroidPushNotification().pushMsg(pChannelIds, null, pMsg);
        }
        if (DeviceType.IOS == pDevice) {
            isPushSuccess = getBaiDuIOSPushNotification().pushMsg(pChannelIds, null, pMsg);
        }

        LOG.debug("push notification message: is success? " + DeviceType.toString(pDevice));
        return isPushSuccess;
    }

    public boolean pushNotificationMsgToAll(final int pDevice, final PushMessageParameter pMsg) throws Exception {
        LOG.debug("push notification message: device type is:" + DeviceType.toString(pDevice));
        LOG.debug("push notification message: push message is:" + pMsg.toString());

        boolean isPushSuccess = false;
        if (DeviceType.ANDROID == pDevice) {
            isPushSuccess = getBaiDuAndroidPushNotification().pushMsgToAll(pMsg);
        }
        if (DeviceType.IOS == pDevice) {
            isPushSuccess = getBaiDuIOSPushNotification().pushMsgToAll(pMsg);
        }
        LOG.debug("push notification message: is success? " + DeviceType.toString(pDevice));
        return isPushSuccess;
    }

    public IBaiDuPushNotification getBaiDuAndroidPushNotification() {
        return mBaiDuAndroidPushNotification;
    }

    public void setBaiDuAndroidPushNotification(IBaiDuPushNotification pBaiDuAndroidPushNotification) {
        mBaiDuAndroidPushNotification = pBaiDuAndroidPushNotification;
    }

    public IBaiDuPushNotification getBaiDuIOSPushNotification() {
        return mBaiDuIOSPushNotification;
    }

    public void setBaiDuIOSPushNotification(IBaiDuPushNotification pBaiDuIOSPushNotification) {
        mBaiDuIOSPushNotification = pBaiDuIOSPushNotification;
    }

    public PushMessageManager getPushMessageManager() {
        return mPushMessageManager;
    }

    public void setPushMessageManager(PushMessageManager pPushMessageManager) {
        mPushMessageManager = pPushMessageManager;
    }

    public MyAccountManager getMyAccountManager() {
        return mMyAccountManager;
    }

    public void setMyAccountManager(MyAccountManager pMyAccountManager) {
        mMyAccountManager = pMyAccountManager;
    }

    public StudentManager getStudentManager() {
        return mStudentManager;
    }

    public void setStudentManager(StudentManager pStudentManager) {
        mStudentManager = pStudentManager;
    }
}
