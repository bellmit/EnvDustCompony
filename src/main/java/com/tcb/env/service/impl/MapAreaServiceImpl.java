package com.tcb.env.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IMapAreaDao;
import com.tcb.env.pojo.MapArea;
import com.tcb.env.pojo.MapAreaPoint;
import com.tcb.env.service.IMapAreaService;

import javax.annotation.Resource;

/**
 * 
 * <p>[功能描述]：百度地图覆盖区域确定 ServiceImpl </p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王坤
 * @version	1.0, 2018年8月31日 上午9:40:56
 * @since	EnvDust 1.0.0
 *
 */
@Service("mapAreaService")
@Transactional(rollbackFor = Exception.class)
class MapAreaServiceImpl implements IMapAreaService {

    @Resource
    private IMapAreaDao mapAreaDao;
	
	@Override
	public List<MapArea> getMapArea(MapArea target) {
		return this.mapAreaDao.getMapArea(target);
	}

	@Override
	public int getMapAreaPointCount(int maId) {
		return this.mapAreaDao.getMapAreaPointCount(maId);
	}

	@Override
	public List<MapAreaPoint> getMapAreaPoint(MapArea mapArea) {
		return this.mapAreaDao.getMapAreaPoint(mapArea);
	}

	@Override
	public int addMapArea(MapArea mapArea) {
		int n = this.mapAreaDao.addMapArea(mapArea);
		if(n > 0){
			return mapArea.getMaId();
		}
		return -1;
	}

	@Override
	public boolean editMapArea(MapArea mapArea) {
		int n = this.mapAreaDao.editMapArea(mapArea);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteMapArea(int maId) {
		int n = this.mapAreaDao.deleteArea(maId);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean addMapAreaPoint(MapAreaPoint mapAreaPoint) {
		int n = this.mapAreaDao.addMapAreaPoint(mapAreaPoint);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteMapAreaPoint(int pointId) {
		int n = this.mapAreaDao.deleteMapAreaPoint(pointId);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean addBatchMapAreaPoint(List<MapAreaPoint> mapAreaPointList) {
		int n = this.mapAreaDao.addBatchMapAreaPoint(mapAreaPointList);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<MapArea> getAllMapAreaPoint() {
		return this.mapAreaDao.getAllMapAreaPoint();
	}
	
}
