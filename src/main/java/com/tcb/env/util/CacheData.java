package com.tcb.env.util;

import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * 
 * <p>[功能描述]：缓存数据类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月13日下午1:05:13
 * @since	EnvDust 1.0.0
 *
 */
public class CacheData {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "CacheData";

	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(CacheData.class);
	/**
	 * 缓存列表
	 */
	private static HashMap<String,Object> cacheMap = new HashMap<String, Object>();

	/**
	 * 
	 * <p>[功能描述]：获取缓存数据</p>
	 * 
	 * @author	王垒, 2016年4月13日下午1:06:20
	 * @since	EnvDust 1.0.0
	 *
	 * @return
	 */
	public static HashMap<String,Object> getCacheMap()
	{
		return cacheMap;
	}
	
	/**
	 * 
	 * <p>[功能描述]：插入缓存数据</p>
	 * 
	 * @author	王垒, 2016年4月13日下午1:06:31
	 * @since	EnvDust 1.0.0
	 *
	 * @param Key
	 * @param object
	 * @return
	 */
	public static boolean addCacheMap(String Key,Object object)
	{
		boolean flag = false;
		try
		{
			synchronized (cacheMap) {
				cacheMap.put(Key,object);
			}
			flag = true;
		}
		catch(OutOfMemoryError e)
		{
			logger.error(LOG+":内存溢出，信息为："+e.getMessage());
			cacheMap.clear();
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
			return flag;
		}
		return flag;
	}
	
	/**
	 * 
	 * <p>[功能描述]：删除缓存数据</p>
	 * 
	 * @author	王垒, 2016年4月13日下午1:06:51
	 * @since	EnvDust 1.0.0
	 *
	 * @param Key
	 * @return
	 */
	public static boolean removeCacheTreeMap(String Key)
	{
		boolean flag = false;
		try
		{
			synchronized (cacheMap) {
				cacheMap.remove(Key);
			}
			flag = true;
		}
		catch(Exception e){
			e.printStackTrace();
			return flag;
		}
		return flag;
	}


}
