package com.fmc.edu.crypto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by dylanyu on 5/5/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestCryptoService {
	@Resource(name = "DESEncryptService")
	private IEncryptService desCryptoService;

	@Test
	public void testDESScrypto() throws Exception {
		String message = "This message used to test DES crypto service";
		byte[] encryptData = desCryptoService.encrypt(message.getBytes());
		System.out.println("encryptData:" + new String(encryptData));
		System.out.println("decryptData:" + new String(desCryptoService.decrypt(encryptData)));
	}
}
