package com.tcb.env.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * [功能描述]：SHAA加密类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年5月4日上午8:49:05
 * @since EnvDust 1.0.0
 *
 */
public class SHAUtil {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "SHAUtil";

	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(SHAUtil.class);

	/**
	 * 
	 * <p>
	 * [功能描述]：SHA加密 生成40位SHA码
	 * </p>
	 * 
	 * @author 王垒, 2016年5月4日上午8:49:48
	 * @since EnvDust 1.0.0
	 *
	 * @param inStr
	 *            ：待加密字符串
	 * @return 返回40位SHA码
	 * @throws Exception
	 */
	public static String shaEncode(String inStr) throws Exception {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			logger.error(LOG + "：加密失败，信息为：" + e.getMessage());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = sha.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
