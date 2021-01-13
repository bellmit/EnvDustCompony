package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.HeWeather;

/**
 * 
 * <p>[功能描述]：区域数据库操作接口</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年3月23日上午10:06:21
 * @since	EnvDust 1.0.0
 *
 */
public interface IAreaDao {
	
	/**
	 * 
	 * <p>[功能描述]：查询层级权限区域</p>
	 * 
	 * @author	王垒, 2016年3月23日上午10:09:38
	 * @since	EnvDust 1.0.0
	 *
	 * @param userCode
	 * @param levelFlag
	 * @return
	 */
	List<Area> getBottomAuthorityAreas(@Param("userCode")String userCode,@Param("levelFlag")String levelFlag);

	List<Area> getAuthorityAreas(@Param("userCode")String userCode);

	/**
	 * 
	 * <p>[功能描述]：查询结果个数（国家）</p>
	 * 
	 * @author	王垒, 2016年3月23日上午10:08:36
	 * @since	EnvDust 1.0.0
	 *
	 * @param area
	 * @return
	 */
	int getCountryCount(@Param("area")Area area);
	
	/**
	 * 
	 * <p>[功能描述]：查询结果个数（不包括国家）</p>
	 * 
	 * @author	王垒, 2016年3月23日上午10:08:36
	 * @since	EnvDust 1.0.0
	 *
	 * @param area
	 * @return
	 */
	int getCount(@Param("area")Area area);
	
	/**
	 * 
	 * <p>[功能描述]：查询Area数据，包括所有国家</p>
	 * 
	 * @author	王垒, 2016年3月23日上午10:09:38
	 * @since	EnvDust 1.0.0
	 *
	 * @param area
	 * @param ignorecountry
	 * @return
	 */
	List<Area> getAreas(@Param("area")Area area,@Param("ignorecountry")boolean ignorecountry);
	
	/**
	 * 
	 * <p>[功能描述]：新增Area数据</p>
	 * 
	 * @author	王垒, 2016年3月23日上午10:11:05
	 * @since	EnvDust 1.0.0
	 *
	 * @param area
	 * @return
	 */
	int insertAreas(@Param("area")Area area);
	
	/**
	 * 
	 * <p>[功能描述]：更新Area数据</p>
	 * 
	 * @author	王垒, 2016年3月23日上午10:11:57
	 * @since	EnvDust 1.0.0
	 *
	 * @return
	 */
	int updateAreas(@Param("listarea")List<Area> listarea);
	
	/**
	 * 
	 * <p>[功能描述]：物理删除User数据</p>
	 * 
	 * @author	王垒, 2016年3月21日下午2:24:56
	 * @since	EnvDust 1.0.0
	 *
	 * @param listid
	 * @return
	 */
	int deleteAreas(@Param("listid")List<Integer> listid);
	
	
	/**
	 * 
	 * <p>[功能描述]：获取区域路径名称</p>
	 * 
	 * @author	王垒, 2016年3月24日上午8:43:55
	 * @since	EnvDust 1.0.0
	 *
	 * @param areaid
	 * @return
	 */
	String getAreaPath(@Param("areaid")int areaid);
	
	/**
	 * 
	 * <p>[功能描述]：获取区域名称</p>
	 * 
	 * @author	王垒, 2016年3月24日上午8:43:55
	 * @since	EnvDust 1.0.0
	 *
	 * @param areaid
	 * @return
	 */
	String getAreaName(@Param("areaid")int areaid);

	/**
	 * 获取区域级别
	 * @param areaid
	 * @return
	 */
	String getAreaLevelFlag(@Param("areaid")int areaid);
	
	/**
	 * 
	 * <p>[功能描述]：查询是否存在同名区域 </p>
	 * 
	 * @author	王垒, 2016年3月24日上午11:14:08
	 * @since	EnvDust 1.0.0
	 *
	 * @param areaid
	 * @param areaname
	 * @return
	 */
	int getAreaExist(@Param("areaid")int areaid,@Param("areaname")String areaname);
	
	/**
	 * 
	 * <p>[功能描述]：插入区域天气信息</p>
	 * 
	 * @author	王垒, 2016年5月20日上午10:27:33
	 * @since	EnvDust 1.0.0
	 *
	 * @param weather
	 * @return
	 */
	int insertAreaWeather(@Param("weather")HeWeather weather);
	
	/**
	 * 
	 * <p>[功能描述]：查询区域天气信息个数</p>
	 * 
	 * @author	王垒, 2016年5月20日上午11:39:20
	 * @since	EnvDust 1.0.0
	 *
	 * @param weather
	 * @return
	 */
	int getAreaWeatherCount(@Param("weather")HeWeather weather);
	
	/**
	 * 
	 * <p>[功能描述]：查询区域天气信息</p>
	 * 
	 * @author	王垒, 2016年5月20日上午11:39:20
	 * @since	EnvDust 1.0.0
	 *
	 * @param weather
	 * @return
	 */
	List<HeWeather> getAreaWeather(@Param("weather")HeWeather weather);
	
	/**
	 * 
	 * <p>[功能描述]：获取用户权限区域ID</p>
	 * 
	 * @author	王垒, 2017年9月7日下午2:09:13
	 * @since	EnvDust 1.0.0
	 *
	 * @param userCode
	 * @return
	 */
	List<Integer> getAuthorityAreaId(@Param("userCode")String userCode);
	
	/**
	 * 
	 * <p>[功能描述]：获取区域平均数据</p>
	 * 
	 * @author	王垒, 2017年9月8日下午3:52:11
	 * @since	EnvDust 1.0.0
	 * 
	 * @param dbName
	 * @param dbOldName
	 * @param listAreaId
	 * @param dataType
	 * @param thingCode
	 * @param beginTime
	 * @return
	 */
	double getAreaAvgValue(
			@Param("dbName") String dbName,
			@Param("dbOldName") String dbOldName,
			@Param("listAreaId")List<Integer> listAreaId,
			@Param("dataType")String dataType,
			@Param("thingCode")String thingCode,
			@Param("beginTime")Timestamp beginTime);
	
	/**
	 * 
	 * <p>[功能描述]：查询地方/城市ID</p>
	 * 
	 * @author	王垒, 2016年5月20日上午11:39:20
	 * @since	EnvDust 1.0.0
	 *
	 * @return
	 */
	List<String> getAreaCityId();
	
	/**
	 * 
	 * <p>[功能描述]：存在关联区域或设备个数</p>
	 * 
	 * @author	王垒, 2018年1月29日下午3:39:53
	 * @since	EnvDust 1.0.0
	 *
	 * @param areaId
	 * @return
	 */
	int getRelationCount(@Param("areaId") int areaId);

	/**
	 * 获取第四级区域信息
	 * @return
	 */
	List<Map<String, String>> queryFourthAreaInfo();
}
