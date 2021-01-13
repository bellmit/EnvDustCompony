package com.tcb.env.util;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

/**
 * 
 * <p>
 * [功能描述]：DES加密解密，不是通用DES，为了和C#兼容
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年5月4日下午1:57:41
 * @since EnvDust 1.0.0
 *
 */
public class DesUtil {
	
	/**
	 * 加解密方式
	 */
	private static String EncryptType = "DES";

	private static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	@SuppressWarnings("restriction")
	private static byte[] encrypt(byte[] arrB, String keyStr) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(keyStr.getBytes());

		Cipher encryptCipher = Cipher.getInstance(EncryptType);
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 
	 * <p>
	 * [功能描述]：加密字符串
	 * </p>
	 * 
	 * @author 王垒, 2016年5月4日下午2:00:50
	 * @since EnvDust 1.0.0
	 *
	 * @param strIn
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String strIn, String keyStr) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes(), keyStr));
	}

	@SuppressWarnings("restriction")
	private static byte[] decrypt(byte[] arrB, String keyStr) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(keyStr.getBytes());

		Cipher decryptCipher = Cipher.getInstance(EncryptType);
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 
	 * <p>
	 * [功能描述]：解密字符串
	 * </p>
	 * 
	 * @author 王垒, 2016年5月4日下午2:01:05
	 * @since EnvDust 1.0.0
	 *
	 * @param strIn
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String strIn, String keyStr) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn), keyStr));
	}

	/**
	 * 
	 * <p>
	 * [功能描述]：生成制定字符串密钥
	 * </p>
	 * 
	 * @author 王垒, 2016年5月4日下午2:01:40
	 * @since EnvDust 1.0.0
	 *
	 * @param arrBTmp
	 * @return
	 * @throws Exception
	 */
	private static Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
}
