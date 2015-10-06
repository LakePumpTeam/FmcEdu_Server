package com.fmc.edu.crypto.impl;

import com.fmc.edu.configuration.WebConfig;
import com.fmc.edu.crypto.IEncryptService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

@Component("DESEncryptService")
public class DESEncryptService implements IEncryptService {

	private static final Logger LOG = Logger.getLogger(DESEncryptService.class);

	private static final String DES_NAME = "DES";

	@Override
	public byte[] encrypt(byte[] pSrc) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(WebConfig.getDESPassword().getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_NAME);
			SecretKey secreTkey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance(DES_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, secreTkey, random);
			return cipher.doFinal(pSrc);
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public byte[] decrypt(byte[] pSrc) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(WebConfig.getDESPassword().getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_NAME);
			SecretKey secreTkey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance(DES_NAME);
			cipher.init(Cipher.DECRYPT_MODE, secreTkey, random);
			return cipher.doFinal(pSrc);
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

}
