package com.fmc.edu.crypto.impl;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.crypto.IEncryptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by Yu on 2015/5/12.
 */
@Service("base64EncryptService")
public class Base64EncryptService implements IEncryptService {

    private static int BASS64_MIN_LENGTH = 4;

    public String encrypt(final String pMessage) {
        if (pMessage == null) {
            return StringUtils.EMPTY;
        }
        return (new BASE64Encoder()).encode(pMessage.getBytes(Charset.forName(GlobalConstant.CHARSET_UTF8)));
    }

    @Override
    public byte[] encrypt(byte[] pSrc) {
        try {
            return encrypt(new String(pSrc, Charset.forName(GlobalConstant.CHARSET_UTF8))).getBytes(GlobalConstant.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String pParameter) {
        if (StringUtils.isBlank(pParameter)) {
            return StringUtils.EMPTY;
        }
        String parameter = pParameter;
        try {
            int diffLength = BASS64_MIN_LENGTH - (parameter.length() % BASS64_MIN_LENGTH);
            if (diffLength > 0) {
                //as the min length of base64 encoded string is 4, so we need append "=" to right if the length less then 4.
                parameter = StringUtils.rightPad(parameter, parameter.length() + diffLength, '=');
            }
            return new String((new BASE64Decoder()).decodeBuffer(parameter), Charset.forName(GlobalConstant.CHARSET_UTF8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameter;
    }

    @Override
    public byte[] decrypt(byte[] pSrc) {
        try {
            return decrypt(new String(pSrc, Charset.forName(GlobalConstant.CHARSET_UTF8))).getBytes(GlobalConstant.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
