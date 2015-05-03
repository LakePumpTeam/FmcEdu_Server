package com.fmc.edu.push.impl;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.push.IBaiDuPushNotification;
import com.fmc.edu.push.PushDeviceType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by YW on 2015/5/3.
 */
@Component
public class BaiDuIOSPushNotification implements IBaiDuPushNotification {
    private static final Logger LOG = Logger.getLogger(BaiDuIOSPushNotification.class);

    @Resource
    private WebConfig webConfig;

    @Override
    public boolean pushMsg(long pChannelId, String pUserId, String pMsg) throws Exception {
          /*
         * @brief 推送单播通知(IOS APNS) message_type = 1 (默认为0)
         */
        ChannelKeyPair pair = new ChannelKeyPair(webConfig.getApiKey(), webConfig.getSecretKey());

        //1. create BaiduChannelClient instance
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        //2.this used to open the log, and we could save this message to database or log file.
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                if (webConfig.isDevelopment()) {
                    System.out.println(event.getMessage());
                }
            }
        });

        LOG.debug("Pushing message:" + pMsg);

        try {
            // 3. create request object
            PushUnicastMessageRequest request = new PushUnicastMessageRequest();
            request.setDeviceType(PushDeviceType.IOS);
            request.setDeployStatus(webConfig.deployStatus()); // DeployStatus => 1: Developer 2:
            // Production
            request.setChannelId(pChannelId);
            request.setUserId(pUserId);

            request.setMessageType(1);
            request.setMessage(pMsg);

            // 4. Invoking the API to push message.
            PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);

            // 5. Checking the amount of pushing success message.
            int pushSuccessAccount = response.getSuccessAmount();
            System.out.println("push amount : " + pushSuccessAccount);

        } catch (ChannelClientException e) {
            // client exception
            e.printStackTrace();
            return false;
        } catch (ChannelServerException e) {
            // server exception
            System.out.println(String.format("request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            return false;
        }
        return true;
    }
}
