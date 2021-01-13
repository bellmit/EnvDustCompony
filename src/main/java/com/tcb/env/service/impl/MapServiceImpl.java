package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IMapDao;
import com.tcb.env.model.MapModel;
import com.tcb.env.service.IMapService;

/**
 * <p>[功能描述]：查询id-name形式操作类实现</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月23日下午2:17:53
 * @since EnvDust 1.0.0
 */
@Service("mapService")
class MapServiceImpl implements IMapService {

    /**
     * 声明数据库操作对象
     */
    @Resource
    private IMapDao mapDao;

    @Override
    public List<MapModel> getAreaMap(int id, int levelFlag) {
        return mapDao.getAreaMap(id, levelFlag);
    }

    @Override
    public List<MapModel> getAreaLevelFlag(int id) {
        return mapDao.getAreaLevelFlag(id);
    }

    @Override
    public List<Integer> getAreaIdFromFlag(Integer id) {
        return mapDao.getAreaIdFromFlag(id);
    }


    @Override
    public List<MapModel> getAreaLevelId(int id, int levelflag) {
        return mapDao.getAreaLevelId(id, levelflag);
    }

    @Override
    public List<MapModel> getDeviceMfrCode(String mfrCode) {

        return mapDao.getDeviceMfrCode(mfrCode);
    }

    @Override
    public List<MapModel> getDeviceStatusCode(String statusCode) {
        return mapDao.getDeviceStatusCode(statusCode);
    }

    @Override
    public List<MapModel> getDeviceprinciple(int devicePrinciple) {

        return mapDao.getDeviceprinciple(devicePrinciple);
    }

    @Override
    public List<MapModel> getDeviceOversightUnit(int orgId) {

        return mapDao.getDeviceOversightUnit(orgId);
    }

    @Override
    public List<MapModel> getAuthorityMonitors(String usercode, int thingid,
                                               String thingname, List<String> deviceCodeList) {
        return mapDao.getAuthorityMonitors(usercode, thingid, thingname, deviceCodeList);
    }

    @Override
    public List<MapModel> getStatus(String status, String nostatus, String statusType) {
        return mapDao.getStatus(status, nostatus, statusType);
    }

    @Override
    public List<MapModel> getAuthorityDeviceMonitors(String usercode,
                                                     String devicecode, int thingid, String thingname) {
        return mapDao.getAuthorityDeviceMonitors(usercode, devicecode, thingid, thingname);
    }

    @Override
    public List<MapModel> getSubAreaMap(int id) {
        return mapDao.getSubAreaMap(id);
    }

}
