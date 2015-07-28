package com.fmc.edu.manager;

import com.fmc.edu.model.app.DeviceType;
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

    public boolean pushNotificationMsg(final int pDevice, final String[] pChannelIds, final int pUserId, final PushMessageParameter pMsg) throws Exception {
        boolean isSuccess = pushNotificationMsg(pDevice, pChannelIds, pMsg);
        PushMessage pushMessage = new PushMessage();
        pushMessage.setProfileId(pUserId);
        pushMessage.setTitle(pMsg.getTitle());
        pushMessage.setContent(pMsg.getDescription());
        pushMessage.setPushDeviceType(pDevice);
        pushMessage.setPushType(2);
        pushMessage.setPushStatus(isSuccess);
        return getPushMessageManager().insertPushMessage(pushMessage);
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
}
