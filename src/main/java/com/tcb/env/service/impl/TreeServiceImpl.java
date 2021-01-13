package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IAreaDao;
import com.tcb.env.dao.ITreeDao;
import com.tcb.env.model.TreeModel;
import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.AreaLevel;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>
 * [功能描述]：Tree操作服务类实现
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月25日上午9:16:14
 * @since EnvDust 1.0.0
 */
@Service("treeService")
class TreeServiceImpl implements ITreeService {

    @Resource
    private ITreeDao treeDao;

    @Resource
    private IAreaDao areaDao;

    @Override
    public ArrayList<TreeModel> getAreaChildrenTree(String parentid, String manuName, int levelFlag) {
        return treeDao.getAreaChildrenTree(parentid, manuName, levelFlag);
    }

    @Override
    public int getSubItemCount(String parentid) {
        return treeDao.getSubItemCount(parentid);
    }

    @Override
    public int getAuthorityDeviceCount(String usercode, String projectid, int areaid,
                                       String devicename, String status, String nostatus) {
        return treeDao.getAuthorityDeviceCount(usercode, projectid, areaid, devicename, status, nostatus);
    }

    @Override
    public ArrayList<TreeModel> getAllAreaDevices() {
        return treeDao.getAllAreaDevices();
    }

    @Override
    public ArrayList<TreeModel> getAuthorityDevices(String usercode, String projectid,
                                                    int areaid, String devicename, String status, String nostatus) {
        return treeDao.getAuthorityDevice(usercode, projectid, areaid, devicename, status, nostatus);
    }

    @Override
    public int getAuthorityDeviceCountByAhrCode(String ahrCode, int areaid,
                                                String devicename) {
        return treeDao.getAuthorityDeviceCountByAhrCode(ahrCode, areaid,
                devicename);
    }

    @Override
    public ArrayList<TreeModel> getAuthorityDeviceByAhrCode(String ahrCode,
                                                            int areaid, String devicename) {
        return treeDao.getAuthorityDeviceByAhrCode(ahrCode, areaid, devicename);
    }

    ;

    @Override
    public int getAllDevicesCount(String ahrcode, int areaid, String devicename) {
        return treeDao.getAllDeviceCount(ahrcode, areaid, devicename);
    }

    @Override
    public ArrayList<TreeModel> getAllDevices(String ahrcode, int areaid,
                                              String devicename) {
        return treeDao.getAllDevice(ahrcode, areaid, devicename);
    }

    @Override
    public ArrayList<TreeModel> getAllMonitor(String ahrCode) {
        return treeDao.getAllMonitor(ahrCode);
    }

    @Override
    public ArrayList<TreeModel> getAllAuthority(String ahrcode) {
        return treeDao.getAllAuthority(ahrcode);
    }

    @Override
    public ArrayList<TreeModel> getUserByAhrCode(String ahrcode) {
        return treeDao.getUserByAhrCode(ahrcode);
    }

    @Override
    public ArrayList<TreeModel> getModule(String ahrcode) {
        return treeDao.getModule(ahrcode);
    }

    @Override
    public ArrayList<TreeModel> getPage(String ahrcode, String moduleid) {
        return treeDao.getPage(ahrcode, moduleid);
    }

    @Override
    public ArrayList<TreeModel> getControl(String ahrcode, String pageid) {
        return treeDao.getControl(ahrcode, pageid);
    }

    @Override
    public List<Integer> getAuthorityBottomArea(String projectId, List<Integer> listAreaId,
                                                int parentAreaId, String usercode) {
        Area area = new Area();
        AreaLevel areaLevel = new AreaLevel();
        area.setAreaLevel(areaLevel);
        area.setAreaId(parentAreaId);
        List<Area> listarea = areaDao.getAreas(area, false);
        if (listarea != null && listarea.size() > 0) {
            for (Area areaTemp : listarea) {
                String levelFlag = String.valueOf(areaTemp.getAreaLevel()
                        .getLevelFlag());
                if (DefaultArgument.BOTTOM_LEVEL_FALG.equals(levelFlag)) {
                    int count = getAuthorityDeviceCount(usercode, projectId,
                            areaTemp.getAreaId(), null, null, null);
                    if (count > 0) {
                        listAreaId.add(areaTemp.getAreaId());
                    }
                } else {
                    getAuthorityBottomChildArea(projectId, listAreaId, areaTemp.getAreaId(), usercode);
                }
            }
        }
        return listAreaId;
    }

    private List<Integer> getAuthorityBottomChildArea(String projectId, List<Integer> listAreaId,
                                                      int parentAreaId, String usercode) {
        Area area = new Area();
        AreaLevel areaLevel = new AreaLevel();
        area.setAreaLevel(areaLevel);
        area.setParentId(parentAreaId);
        List<Area> listarea = areaDao.getAreas(area, true);
        if (listarea != null && listarea.size() > 0) {
            for (Area areaTemp : listarea) {
                String levelFlag = String.valueOf(areaTemp.getAreaLevel()
                        .getLevelFlag());
                if (DefaultArgument.BOTTOM_LEVEL_FALG.equals(levelFlag)) {
                    int count = getAuthorityDeviceCount(usercode, projectId,
                            areaTemp.getAreaId(), null, null, null);
                    if (count > 0) {
                        listAreaId.add(areaTemp.getAreaId());
                    }
                } else {
                    getAuthorityBottomChildArea(projectId, listAreaId, areaTemp.getAreaId(), usercode);
                }
            }
        }
        return listAreaId;
    }

    @Override
    public List<Integer> getAuthorityAreaId(String userCode) {
        return areaDao.getAuthorityAreaId(userCode);
    }

    @Override
    public TreeModel getDeviceFirst(String userCode, String deviceName,List<String> statusCodeList) {
        List<TreeModel> treeModelList = treeDao.getDeviceFirst(userCode, deviceName,statusCodeList);
        if (treeModelList != null && treeModelList.size() > 0) {
            return treeModelList.get(0);
        } else {
            return null;
        }
    }

}
