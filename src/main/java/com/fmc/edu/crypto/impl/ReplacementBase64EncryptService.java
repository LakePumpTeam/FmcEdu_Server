package com.fmc.edu.crypto.impl;

import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.crypto.IEncryptService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by Yove on 5/12/2015.
 */
@Service("replacementBase64EncryptService")
public class ReplacementBase64EncryptService implements IEncryptService {

    public static final String RELACEMENT = "~";

    public static final String EQUAL_MARK = "=";

    public String encrypt(final String pMessage) {
        if (pMessage == null) {
            return StringUtils.EMPTY;
        }
        return new String(Base64.encodeBase64(pMessage.getBytes(Charset.forName(GlobalConstant.CHARSET_UTF8))), Charset.forName(GlobalConstant.CHARSET_UTF8));
    }

    @Override
    public byte[] encrypt(byte[] pSrc) {
        return org.apache.commons.codec.binary.StringUtils.getBytesUtf8(encrypt(new String(pSrc, Charset.forName(GlobalConstant.CHARSET_UTF8))));
    }

    public String decrypt(String pParameter) {
        if (StringUtils.isBlank(pParameter)) {
            return StringUtils.EMPTY;
        }
        String parameter = pParameter.replaceAll(RELACEMENT, EQUAL_MARK);
        if (!Base64.isBase64(parameter)) {
            //throw new EncryptException("Input source is invalid base64 format.");
            return "";
        }
        return new String(Base64.decodeBase64(parameter), Charset.forName(GlobalConstant.CHARSET_UTF8));
    }


    @Override
    public byte[] decrypt(byte[] pSrc) {
        if (!Base64.isBase64(pSrc)) {
            // throw new EncryptException("Input source is invalid base64 format.");
            return new byte[0];
        }
        try {
            return decrypt(new String(pSrc, Charset.forName(GlobalConstant.CHARSET_UTF8))).getBytes(GlobalConstant.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
