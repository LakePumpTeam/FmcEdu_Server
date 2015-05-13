package com.fmc.crypt;

import com.fmc.edu.crypto.impl.Base64EncryptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by silly on 2015/5/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestmBase64CryptoService {
    @Resource(name = "base64CryptoService")
    private Base64EncryptService mBase64EncryptService;

    @Test
    public void testCrypto() {
        String test1 = "1";
        String encrypto = mBase64EncryptService.encrypt(test1);
        System.out.println("testEncrypto:" + encrypto);
        System.out.println("decrypto:" + mBase64EncryptService.decrypt(encrypto));
        System.out.println("decrypto:" + mBase64EncryptService.decrypt(encrypto.replaceAll("=", "")));
        String test2 = "123123123sdfsdfsdfs";
        encrypto = mBase64EncryptService.encrypt(test2);
        System.out.println("testEncrypto:" + encrypto);
        System.out.println("decrypto:" + mBase64EncryptService.decrypt(encrypto));
        System.out.println("decrypto:" + mBase64EncryptService.decrypt(encrypto.replaceAll("=", "")));
        String test3 = "中文测试";
        encrypto = mBase64EncryptService.encrypt(test3);
        System.out.println("testEncrypto:" + encrypto);
        System.out.println("decrypto:" + mBase64EncryptService.decrypt(encrypto));
        System.out.println("decrypto:" + mBase64EncryptService.decrypt(encrypto.replaceAll("=", "")));
    }
}
