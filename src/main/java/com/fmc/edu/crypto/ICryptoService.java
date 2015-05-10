package com.fmc.edu.crypto;

/**
 * Created by dylanyu on 5/5/2015.
 */
public interface ICryptoService {
	byte[] encrypt(final byte[] pSrc);

	byte[] decrypt(byte[] pSrc);
}
