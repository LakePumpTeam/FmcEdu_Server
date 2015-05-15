package com.fmc.web;

import com.fmc.edu.web.ResponseBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Yu on 5/15/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestResoonseBean {

    @Test
    public void testAndroidPushNotificationMsg() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.addErrorMsg("error message");
        responseBean.addBusinessMsg("business message ");
        responseBean.addData("test", "test");
        responseBean.addData("test2", "test2");
        System.out.println(responseBean.toString());
    }
}
