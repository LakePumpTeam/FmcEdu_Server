package com.fmc.edu.crypto;

import com.fmc.edu.exception.EncryptException;

/**
 * Created by dylanyu on 5/5/2015.
 */
public interface IEncryptService {
	
	byte[] encrypt(final byte[] pSrc);

	byte[] decrypt(byte[] pSrc) throws EncryptException;
}
