package com.fmc.edu.crypto.impl;

import com.fmc.edu.crypto.IEncryptService;
import com.fmc.edu.exception.EncryptException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yu on 5/29/2015.
 */
@Component("passwordEncryptService")
public class PasswordEncryptService implements IEncryptService {
    @Override
    public byte[] encrypt(byte[] pSrc) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pSrc);
            byte encodeByte[] = md.digest();
            return encodeByte;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }

    public String encrypt(String pSrc) {
        byte[] encodeByte = encrypt(pSrc.getBytes());
        StringBuffer buf = new StringBuffer(encodeByte.length * 2);
        for (int i = 0; i < encodeByte.length; i++) {
            if (((int) encodeByte[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toHexString((int) encodeByte[i] & 0xff));
        }
        return buf.toString();
    }

    @Override
    public byte[] decrypt(byte[] pSrc) throws EncryptException {
        return new byte[0];
    }
}
