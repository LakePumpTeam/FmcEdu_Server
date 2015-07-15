package com.fmc.edu.push.impl;

import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.model.app.PushMessageType;
import com.fmc.edu.model.push.PushMessage;
import com.fmc.edu.push.IBaiDuPushNotification;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by YW on 2015/5/3.
 */
@Component("AndroidPushNotification")
public class BaiDuAndroidPushNotification implements IBaiDuPushNotification {
    private static final Logger LOG = Logger.getLogger(BaiDuAndroidPushNotification.class);

    @Resource(name = "baiduPushClient")
    private BaiDuPushClient mBaiDuPushClient;

    @Override
    public boolean pushMsg(String[] pChannelIds, String pUserId, PushMessage pMsg) throws Exception {

        PushKeyPair pair = new PushKeyPair(WebConfig.getApiKey(), WebConfig.getSecretKey());

        LOG.debug("Pushing message:" + pMsg);
        try {
            // 3. create request object
            PushBatchUniMsgRequest request = new PushBatchUniMsgRequest().
                    addChannelIds(pChannelIds).
                    addMsgExpires(WebConfig.getBaiDuMsgExpires()).
                    addMessageType(PushMessageType.NOTIFYCATION).
                    addMessage(pMsg.toString()).
                    addDeviceType(DeviceType.ANDROID);

            // Invoking the API to push message.
            PushBatchUniMsgResponse response = getBaiDuPushClient().pushBatchUniMsg(request);

            // Checking the amount of pushing success message.
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

    public boolean pushMsgToAll(PushMessage pMsg) throws Exception {
        try {
            //specify request arguments
            PushMsgToAllRequest request = new PushMsgToAllRequest()
                    .addMsgExpires(WebConfig.getBaiDuMsgExpires())
                    .addMessageType(PushMessageType.NOTIFYCATION)
                    .addMessage(pMsg.toString())
                    .addDeviceType(DeviceType.ANDROID);
            // http request
            PushMsgToAllResponse response = getBaiDuPushClient().pushMsgToAll(request);
            System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
                    + response.getSendTime() + ",timerId: "
                    + response.getTimerId());
        } catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
            return false;
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
            return false;
        }
        return true;
    }

    public BaiDuPushClient getBaiDuPushClient() {
        return mBaiDuPushClient;
    }

    public void setBaiDuPushClient(BaiDuPushClient pBaiDuPushClient) {
        mBaiDuPushClient = pBaiDuPushClient;
    }
}
