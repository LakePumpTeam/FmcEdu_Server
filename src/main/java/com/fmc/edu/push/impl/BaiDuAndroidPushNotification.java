package com.fmc.edu.push.impl;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.model.app.PushMessageType;
import com.fmc.edu.push.IBaiDuPushNotification;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by YW on 2015/5/3.
 */
@Component("AndroidPushNotification")
public class BaiDuAndroidPushNotification implements IBaiDuPushNotification {
	private static final Logger LOG = Logger.getLogger(BaiDuAndroidPushNotification.class);

	@Override
	public boolean pushMsg(String[] pChannelIds, String pUserId, String pMsg) throws Exception {

		PushKeyPair pair = new PushKeyPair(WebConfig.getApiKey(), WebConfig.getSecretKey());

		//1. create BaiduChannelClient instance
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		//2.this used to open the log, and we could save this message to database or log file.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				if (WebConfig.deployStatus() == WebConfig.DEPLOY_STATUS_DEVELOPER) {
					System.out.println(event.getMessage());
				}
			}
		});

		LOG.debug("Pushing message:" + pMsg);
		try {
			// 3. create request object
			PushBatchUniMsgRequest request = new PushBatchUniMsgRequest().
					addChannelIds(pChannelIds).
					addMsgExpires(new Integer(3600)).
					addMessageType(PushMessageType.NOTIFYCATION).
					addMessage(pMsg).
					addDeviceType(DeviceType.ANDROID);

			// 4. Invoking the API to push message.
			PushBatchUniMsgResponse response = pushClient.pushBatchUniMsg(request);

			// 5. Checking the amount of pushing success message.
			LOG.debug(String.format("msgId: %s, sendTime: %d",
					response.getMsgId(), response.getSendTime()));

		} catch (PushClientException e) {
			LOG.error("Occur ChannelClientException exception:", e);
			return false;
		} catch (PushServerException e) {
			LOG.error(String.format("request_id: %d, error_code: %d, error_message: %s",
					e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			return false;
		}
		return true;
	}
}
