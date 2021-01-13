package com.tcb.env.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.MapArea;
import com.tcb.env.pojo.MapAreaPoint;

/**
 * 
 * <p>[功能描述]：百度地图覆盖区域确定 Service </p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王坤
 * @version	1.0, 2018年8月31日 上午9:38:56
 * @since	EnvDust 1.0.0
 *
 */
public interface IMapAreaService {
	
	/**
	 * 
	 * @author 王坤
	 * @since 2018/8/31
	 * @return 返回所有覆盖区域的信息
	 * 
	 */
	List<MapArea> getMapArea(MapArea target);
	
	/**
	 * 
	 * @author 王坤
	 * @since 2018/9/3 上午8:28
	 * @return 获某覆盖区域所有的坐标点的个数
	 * 
	 */
	int getMapAreaPointCount(int maId);
	
	/**
	 * 
	 * @author 王坤
	 * @since 2018/9/3 上午8:28
	 * @return 获某覆盖区域所有的坐标点的信息
	 * 
	 */
	List<MapAreaPoint> getMapAreaPoint(MapArea mapArea);
	
	/**
	 * 
	 * @author 王坤
	 * @since 2018/9/5  下午13:13
	 * @return 获所有覆盖区域所有的坐标点的信息
	 * 
	 */
	List<MapArea> getAllMapAreaPoint();
	
	/**
	 * 添加坐标区域
	 * 
	 * @author 王坤
	 * @since 2018/9/3  上午10:28
	 * 
	 */
	int addMapArea(@Param("mapArea") MapArea mapArea);
	
	/**
	 * 修改坐标区域
	 * 
 	 * @author 王坤
	 * @since 2018/9/3 上午10:30
	 * 
	 */
	boolean editMapArea(@Param("mapArea") MapArea mapArea);
	
	/**
	 * 删除坐标区域
	 * 
	 * @author 王坤
	 * @since 2018/9/3 上午10:32
	 */
	boolean deleteMapArea(@Param("maId") int maId);
	
	/**
	 * 添加某坐标区域点
	 * 
	 * @author 王坤
	 * @since 2018/9/3  上午10:34
	 * 
	 */
	boolean addMapAreaPoint(@Param("mapAreaPoint") MapAreaPoint mapAreaPoint);
	
	/**
	 * 批量进行区域添加
	 * @param mapAreaPoints
	 */
	boolean addBatchMapAreaPoint(@Param("mapAreaPointList") List<MapAreaPoint> mapAreaPointList);
	
	/**
	 * 删除某坐标区域点
	 * 
	 * @author 王坤
	 * @since 2018/9/3  上午10:38
	 */
	boolean deleteMapAreaPoint(@Param("pointId") int pointId);
}
