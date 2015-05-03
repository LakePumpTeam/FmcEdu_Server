package com.fmc.edu.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by YW on 2015/5/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestWebConfig {
    @Resource
    private WebConfig webConfig;

    @Test
    public void printWebConfig() throws Exception {
        System.out.println("deployStatus:" + webConfig.deployStatus());
        System.out.println("Is development:" + webConfig.isDevelopment());
        System.out.println("Is production:" + webConfig.isProduction());
        System.out.println("apiKey:" + webConfig.getApiKey());
        System.out.println("secretKey:" + webConfig.getSecretKey());
    }
}
