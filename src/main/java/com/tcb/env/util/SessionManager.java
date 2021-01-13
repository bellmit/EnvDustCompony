package com.tcb.env.util;

import javax.servlet.http.HttpSession;

/**
 * 
 * <p>
 * [功能描述]：session管理
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月29日下午3:35:39
 * @since EnvDust 1.0.0
 *
 */
public class SessionManager {

	/**
	 * 
	 * <p>
	 * [功能描述]：判断session是否过期
	 * </p>
	 * 
	 * @author 王垒, 2016年4月29日下午3:39:15
	 * @since EnvDust 1.0.0
	 *
	 * @param httpsession
	 * @param username
	 * @return
	 */
	public static boolean isSessionValidate(HttpSession httpsession, String key) {
		boolean flag = true;
		try {
			if (httpsession != null && httpsession.getId() != null
					&& httpsession.getAttribute(key) != null) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			flag = true;
		}
		return flag;
	}
}
