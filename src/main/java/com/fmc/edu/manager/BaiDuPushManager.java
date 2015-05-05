package com.fmc.edu.manager;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.push.IBaiDuPushNotification;
import com.fmc.edu.push.PushDeviceType;
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

    @Resource
    private WebConfig mWebConfig;

    public boolean pushNotificationMsg(final int pDevice, final long pChannelId, final String pUserId, final String pMsg) throws Exception {
        LOG.debug("push notification message: device type is:" + PushDeviceType.toString(pDevice));

        boolean isPushSuccess = false;
        if (PushDeviceType.ANDROID == pDevice) {
            isPushSuccess = getBaiDuAndroidPushNotification().pushMsg(pChannelId, pUserId, pMsg);
        }
        if (PushDeviceType.IOS == pDevice) {
            isPushSuccess = getBaiDuIOSPushNotification().pushMsg(pChannelId, pUserId, pMsg);
        }
        LOG.debug("push notification message: is success? " + PushDeviceType.toString(pDevice));
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
}
