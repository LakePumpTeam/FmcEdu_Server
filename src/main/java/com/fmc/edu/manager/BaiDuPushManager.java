package com.fmc.edu.manager;

import com.fmc.edu.model.app.DeviceType;
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

	public boolean pushNotificationMsg(final int pDevice, final long pChannelId, final String pUserId, final String pMsg) throws Exception {
		LOG.debug("push notification message: device type is:" + DeviceType.toString(pDevice));

		boolean isPushSuccess = false;
		if (DeviceType.ANDROID == pDevice) {
			isPushSuccess = getBaiDuAndroidPushNotification().pushMsg(pChannelId, pUserId, pMsg);
		}
		if (DeviceType.IOS == pDevice) {
			isPushSuccess = getBaiDuIOSPushNotification().pushMsg(pChannelId, pUserId, pMsg);
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
}
