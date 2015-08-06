package com.fmc.edu.manager;

import com.fmc.edu.model.app.AppSetting;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.model.profile.BaseProfile;
import com.fmc.edu.model.push.MessageNotificationBasicStyle;
import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.model.push.PushMessageParameter;
import com.fmc.edu.push.IBaiDuPushNotification;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    public boolean pushNotificationMsg(final int pUserId, final PushMessageParameter pMsg) throws Exception {
        BaseProfile baseProfile = getMyAccountManager().findUserById(String.valueOf(pUserId));
        if (baseProfile == null) {
            LOG.info("pushNotificationMsg():Can not find user: " + pUserId);
            return false;
        }
        //TODO if parent off line app, if we should send notification?
        if (!baseProfile.isOnline()) {
            LOG.info("User off line status:" + pUserId);
            return false;
        }
        AppSetting appSetting = getMyAccountManager().queryAppSetting(pUserId);
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
        boolean isSuccess = pushNotificationMsg(baseProfile.getDeviceType(), new String[]{baseProfile.getChannelId()}, pMsg);
        PushMessage pushMessage = new PushMessage();
        pushMessage.setProfileId(pUserId);
        pushMessage.setTitle(pMsg.getTitle());
        pushMessage.setContent(pMsg.getDescription());
        pushMessage.setPushDeviceType(baseProfile.getDeviceType());
        pushMessage.setPushType(2);
        pushMessage.setMessageType(Integer.parseInt(pMsg.getCustom_content().get(PushMessageParameter.MSG_TYPE).toString()));
        pushMessage.setPushStatus(isSuccess);
        try {
            return getPushMessageManager().insertPushMessage(pushMessage);
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
}
