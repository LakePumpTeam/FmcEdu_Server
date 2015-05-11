package com.fmc.crypt;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.crypto.Base64CryptoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * Created by silly on 2015/5/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/spring-*.xml")
public class TestmBase64CryptoService {
    @Resource(name = "base64CryptoService")
    private Base64CryptoService mBase64CryptoService;

    @Test
    public void testCrypto() {
        String test1 = "1";
        String encrypto = mBase64CryptoService.encrypt(test1);
        System.out.println("testEncrypto:" + encrypto);
        System.out.println("decrypto:" + mBase64CryptoService.decrypt(encrypto));
        System.out.println("decrypto:" + mBase64CryptoService.decrypt(encrypto.replaceAll("=", "")));
        String test2 = "123123123sdfsdfsdfs";
        encrypto = mBase64CryptoService.encrypt(test2);
        System.out.println("testEncrypto:" + encrypto);
        System.out.println("decrypto:" + mBase64CryptoService.decrypt(encrypto));
        System.out.println("decrypto:" + mBase64CryptoService.decrypt(encrypto.replaceAll("=", "")));
        String test3 = "中文测试";
        byte[] test3Byte = mBase64CryptoService.encrypt(test3.getBytes(Charset.forName(GlobalConstant.CHARSET_UTF8)));
        encrypto = new String(test3Byte, 0, test3Byte.length, Charset.forName(GlobalConstant.CHARSET_UTF8));
        System.out.println("testEncrypto:" + encrypto);
        System.out.println("decrypto:" + mBase64CryptoService.decrypt(encrypto));
        System.out.println("decrypto:" + mBase64CryptoService.decrypt(encrypto.replaceAll("=", "")));
    }
}
