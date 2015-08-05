package com.fmc.edu.push.impl;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.fmc.edu.configuration.WebConfig;
import org.springframework.stereotype.Service;

/**
 * Created by Yu on 7/13/2015.
 */
@Service("androidBaiduPushClient")
public class AndroidBaiDuPushClient extends BaiduPushClient {
    public AndroidBaiDuPushClient() throws Exception {
        super(new PushKeyPair(WebConfig.getAndroidApiKey(), WebConfig.getAndroidSecretKey()), BaiduPushConstants.CHANNEL_REST_URL);
        this.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                if (WebConfig.deployStatus() == WebConfig.DEPLOY_STATUS_DEVELOPER) {
                    System.out.println(event.getMessage());
                }
            }
        });
    }
}
