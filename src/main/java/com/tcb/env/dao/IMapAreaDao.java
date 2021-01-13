package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.tcb.env.pojo.MapArea;
import com.tcb.env.pojo.MapAreaPoint;

/**
 * 
 * <p>[功能描述]：百度地图覆盖区域确定 Dao </p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王坤
 * @version	1.0, 2018年8月31日 上午9:42:56
 * @since	EnvDust 1.0.0
 *
 */
public interface IMapAreaDao {
	/**
	 * 获取所有的覆盖区域信息
	 */
	List<MapArea> getMapArea(@Param("addMapArea") MapArea addMapArea);
	
	/**
	 * 获取所有的覆盖区域信息
	 */
	List<MapArea> getAllMapAreaPoint();
	
	/**
	 * 得到某覆盖区域的坐标的数量
	 */
	int getMapAreaPointCount(@Param("maId") int maId);
	
	/**
	 * 得到某覆盖区域的所有坐标
	 * @param mapArea
	 */
	List<MapAreaPoint> getMapAreaPoint(@Param("mapArea") MapArea mapArea);
	
	/**
	 * 添加坐标区域
	 * 
	 * @param mapArea
	 */
	int addMapArea(@Param("addMapArea") MapArea addMapArea);
	
	/**
	 * 修改坐标区域
	 * 
	 * @param mapArea
	 */
	int editMapArea(@Param("mapArea") MapArea mapArea);
	
	/**
	 * 删除坐标区域
	 * 
	 * @param mapArea
	 */
	int deleteArea(@Param("maId") int maId);
	
	/**
	 * 批量进行区域添加
	 * @param mapAreaPoints
	 */
	int addBatchMapAreaPoint(@Param("mapAreaPointList") List<MapAreaPoint> mapAreaPointList);
	
	/**
	 * 添加某坐标区域点
	 * 
	 * @param mapAreaPoint
	 */
	int addMapAreaPoint(@Param("mapAreaPoint") MapAreaPoint mapAreaPoint);
	
	/**
	 * 删除某坐标区域点
	 * 
	 * @param mapAreaPoint
	 */
	int deleteMapAreaPoint(@Param("pointId") int pointId);
}
