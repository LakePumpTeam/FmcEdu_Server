package com.fmc.edu.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by YW on 2015/5/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestWebConfig {

	@Test
	public void printWebConfig() throws Exception {
		System.out.println("deployStatus:" + WebConfig.deployStatus());
		System.out.println("Is development:" + WebConfig.isDevelopment());
		System.out.println("Is production:" + WebConfig.isProduction());
		System.out.println("apiKey:" + WebConfig.getApiKey());
		System.out.println("secretKey:" + WebConfig.getSecretKey());
	}
}
