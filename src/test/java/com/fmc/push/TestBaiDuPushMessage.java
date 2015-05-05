package com.fmc.push;

import com.fmc.edu.push.IBaiDuPushNotification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by YW on 2015/5/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestBaiDuPushMessage {
    @Resource(name = "AndroidPushNotification")
    private IBaiDuPushNotification mBaiDuAndroidPushNotification;

    @Resource(name = "IOSPushNotification")
    private IBaiDuPushNotification mBaiDuIOSPushNotification;

    // @Test
    public void testAndroidPushNotificationMsg() {
        String testMsg = "Test android device push notification";
        try {
            getBaiDuAndroidPushNotification().pushMsg(123, "sd", testMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tesIOSPushNotificationMsg() {
        String testMsg = "Test ios device push notification";
        try {
            getBaiDuIOSPushNotification().pushMsg(123, "sd", testMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IBaiDuPushNotification getBaiDuAndroidPushNotification() {
        return mBaiDuAndroidPushNotification;
    }

    public void setBaiDuAndroidPushNotification(IBaiDuPushNotification pBaiDuAndroidPushNotification) {
        this.mBaiDuAndroidPushNotification = pBaiDuAndroidPushNotification;
    }

    public IBaiDuPushNotification getBaiDuIOSPushNotification() {
        return mBaiDuIOSPushNotification;
    }

    public void setBaiDuIOSPushNotification(IBaiDuPushNotification pBaiDuIOSPushNotification) {
        this.mBaiDuIOSPushNotification = pBaiDuIOSPushNotification;
    }
}
