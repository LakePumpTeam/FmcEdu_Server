package com.fmc.push;

import com.fmc.edu.manager.BaiDuPushManager;
import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.model.push.MessageNotificationBasicStyle;
import com.fmc.edu.model.push.PushMessageParameter;
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

    @Resource(name = "baiduPushManager")
    private BaiDuPushManager mBaiDuPushManager;

    // @Test
    public void testAndroidPushNotificationMsg() {
        String testMsg = "Test android device push notification";
        try {
            getBaiDuAndroidPushNotification().pushMsg(new String[]{"123"}, "sd", getPushMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void tesIOSPushNotificationMsg() {
        String testMsg = "Test ios device push notification";
        try {
            getBaiDuIOSPushNotification().pushMsg(new String[]{"123"}, "sd", getPushMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void testMsg() {
        System.out.println(getPushMessage().toString());
    }

    public PushMessageParameter getPushMessage() {
        PushMessageParameter pushMessage = new PushMessageParameter();
        pushMessage.setTitle("title 12");
        pushMessage.setDescription("description ttttttttt");
        pushMessage.setNotification_basic_style(MessageNotificationBasicStyle.BEL_ERASIBLE);
        pushMessage.addCustomContents("key", "1");
        pushMessage.addCustomContents("type", "android");
        return pushMessage;
    }

    // @Test
    public void testAndroidPushAllNotificationMsg() {
        try {
            getBaiDuAndroidPushNotification().pushMsgToAll(getPushMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAndroidPushAllNotificationMsgByManager() {
        try {
            getBaiDuPushManager().pushNotificationMsgToAll(DeviceType.ANDROID, getPushMessage());
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

    public BaiDuPushManager getBaiDuPushManager() {
        return mBaiDuPushManager;
    }

    public void setBaiDuPushManager(BaiDuPushManager pBaiDuPushManager) {
        mBaiDuPushManager = pBaiDuPushManager;
    }
}
