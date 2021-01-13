package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.AreaLevel;
import com.tcb.env.service.IAreaService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>
 * [功能描述]：Area控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月23日上午11:14:21
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/AreaController")
public class AreaController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AreaController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AreaController.class);
    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;

    /**
     * 声明Area服务
     */
    @Resource
    private IAreaService areaService;

    /**
     * 声明查询map服务
     */
    @Resource
    private IMapService mapService;
    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * <p>[功能描述]：查询所有最底层区域</p>
     *
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月22日上午10:32:32
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryBottomAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AreaModel> queryBottomAreas(HttpSession httpsession) {
        ResultListModel<AreaModel> resultListModel = new ResultListModel<AreaModel>();
        List<AreaModel> listAreaModel = new ArrayList<AreaModel>();
        List<Area> listArea = new ArrayList<Area>();
        String userCode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            userCode = loginuser.getUserCode();
        }
        listArea = areaService.getBottomAuthorityAreas(userCode, DefaultArgument.BOTTOM_LEVEL_FALG);
        for (Area temp : listArea) {
            AreaModel areaModel = ConvertAreaModel(temp);
            if (areaModel != null) {
                listAreaModel.add(areaModel);
            }
        }
        resultListModel.setRows(listAreaModel);
        resultListModel.setTotal(listAreaModel.size());
        return resultListModel;
    }

    /**
     * 获取用户最高级别区域
     *
     * @param httpsession
     * @return
     */
    @RequestMapping(value = "/queryTopAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AreaModel> queryTopAreas(HttpSession httpsession) {
        ResultListModel<AreaModel> resultListModel = new ResultListModel<>();
        List<AreaModel> listAreaModel = new ArrayList<>();
        Area area;
        String userCode = null;
        UserModel loginUser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginUser != null) {
            userCode = loginUser.getUserCode();
        }
        area = areaService.getTopAuthorityAreas(userCode);
        if (area != null) {
            AreaModel areaModel = ConvertAreaModel(area);
            if (areaModel != null) {
                listAreaModel.add(areaModel);
            }
        }
        resultListModel.setRows(listAreaModel);
        resultListModel.setTotal(listAreaModel.size());
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：查询区域信息
     * </p>
     *
     * @param areamodel
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月24日上午9:26:00
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AreaModel> queryAreas(AreaModel areamodel, HttpSession httpsession) {
        ResultListModel<AreaModel> resultListModel = new ResultListModel<AreaModel>();
        List<AreaModel> listareamodel = new ArrayList<AreaModel>();
        List<Area> listarea = new ArrayList<Area>();
        Area area = ConvertArea(areamodel, httpsession);
        boolean ignorecountry = false;
        int count = areaService.getCount(area, ignorecountry);
        if (count > 0) {
            listarea = areaService.getAreas(area, ignorecountry);
            for (Area temp : listarea) {
                AreaModel areaModel = ConvertAreaModel(temp);
                if (areaModel != null) {
                    listareamodel.add(areaModel);
                }
            }
            resultListModel.setRows(listareamodel);
        }
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：
     * </p>
     *
     * @param areamodel
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月24日上午9:50:55
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "insertAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertAreas(AreaModel areamodel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (areamodel != null) {
            try {
                if (areaService.getAreaExist(areamodel.getAreaId(), areamodel.getAreaName()) == 0) {
                    Area insertArea = new Area();
                    insertArea = ConvertArea(areamodel, httpsession);
                    int intresult = areaService.insertAreas(insertArea);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增区域失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此区域名称，请使用其他区域名称！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增区域失败！");
                logger.error(LOG + "：新增区域失败，信息为:" + e.getMessage());
            }

        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    @RequestMapping(value = "/updateAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateAreas(AreaModel areamodel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (areamodel != null) {
            try {
                if (areaService.getAreaExist(areamodel.getAreaId(), areamodel.getAreaName()) == 0) {
                    List<Area> listarea = new ArrayList<Area>();
                    listarea.add(ConvertArea(areamodel, httpsession));
                    int intresult = areaService.updateAreas(listarea);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新区域失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此区域，请使用其他区域名称！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新区域失败！");
                logger.error(LOG + "：更新区域失败，信息为:" + e.getMessage());
            }
        }
        return resultModel;
    }

    @RequestMapping(value = "/deleteAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteAreas(@RequestParam(value = "list[]") List<Integer> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listid = new ArrayList<Integer>();
                for (Integer areaid : list) {
                    listid.add(areaid);
                }
                int intresult = areaService.deleteAreas(listid);
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除区域失败,请检查是否存在关联区域或设备！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除区域失败！");
                logger.error(LOG + "：更新区域失败，信息为:" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除区域失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：获取上级区域下拉框数据
     * </p>
     *
     * @param id
     * @return
     * @author 王垒, 2016年3月23日下午2:34:03
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAreaDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryAreaDropDown(int id, int levelFlag) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        // 获取上级区域值
        listmapmodel = mapService.getAreaMap(id, levelFlag - 1);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);

        return resultListModel;
    }

    /**
     * 获取同级区域下拉框数据
     *
     * @param id
     * @param levelFlag
     * @param session
     * @return
     */
    @RequestMapping(value = "/queryAreaDropDownEqual", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryAreaDropDownEqual(int id, int levelFlag, HttpSession session) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> list = new ArrayList<>();
        List<MapModel> mapModelList = mapService.getAreaMap(id, levelFlag);
        UserModel userModel = (UserModel) session.getAttribute(DefaultArgument.LOGIN_USER);
        if (mapModelList != null && mapModelList.size() > 0) {
            for (MapModel mapModel : mapModelList) {
                TreeModel treeModel = new TreeModel();
                treeModel.setId(String.valueOf(mapModel.getId()));
                if (isHaveAuthorityDevice(treeModel, null, userModel.getUserCode())) {
                    list.add(mapModel);
                }
            }
        }
        resultListModel.setTotal(list.size());
        resultListModel.setRows(list);
        return resultListModel;
    }

    /**
     * 获取下级区域下拉框数据
     *
     * @param id
     * @return
     * @author hiYuzu
     */
    @RequestMapping(value = "/queryAreaDropDownSub", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryAreaDropDownSub(int id, HttpSession session) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> list = new ArrayList<>();
        List<MapModel> mapModelList = mapService.getSubAreaMap(id);
        UserModel userModel = (UserModel) session.getAttribute(DefaultArgument.LOGIN_USER);
        if (mapModelList != null && mapModelList.size() > 0) {
            for (MapModel mapModel : mapModelList) {
                TreeModel treeModel = new TreeModel();
                treeModel.setId(String.valueOf(mapModel.getId()));
                if (isHaveAuthorityDevice(treeModel, null, userModel.getUserCode())) {
                    list.add(mapModel);
                }
            }
        }
        resultListModel.setTotal(list.size());
        resultListModel.setRows(list);
        return resultListModel;
    }

    private boolean isHaveAuthorityDevice(TreeModel treeModel, String projectId, String userCode) {
        boolean returnFlag = false;
        ArrayList<TreeModel> al = treeService.getAreaChildrenTree(treeModel.getId(), null, -1);
        if (al != null && al.size() > 0) {
            for (TreeModel temp : al) {
                int count = treeService.getAuthorityDeviceCount(userCode, projectId, Integer.parseInt(temp.getId()), null, null, null);
                if (count > 0) {
                    return true;
                } else {
                    boolean innerFlag = isHaveAuthorityDevice(temp, projectId, userCode);
                    if (innerFlag) {
                        returnFlag = innerFlag;
                    }
                }
            }
        } else {
            int count = treeService.getAuthorityDeviceCount(userCode, projectId, Integer.parseInt(treeModel.getId()), null, null, null);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        }
        return returnFlag;
    }

    /**
     * 获取最底层区域的上级区域
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/queryBottomAreasUpperDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryBottomAreasUpperDropDown(HttpSession httpSession) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> mapModelList = new ArrayList<>();
        ResultListModel<AreaModel> areaModelList = queryBottomAreas(httpSession);
        if (areaModelList != null && areaModelList.getTotal() > 0) {
            for (AreaModel areaModel : areaModelList.getRows()) {
                //查询上级区域
                List<MapModel> listMapModel = mapService.getAreaMap(areaModel.getParentId(), areaModel.getLevelFlag() - 1);
                for (MapModel mapModel : listMapModel) {
                    if (mapModelList != null && mapModelList.size() > 0) {
                        boolean isExist = false;
                        int id = mapModel.getId();
                        for (MapModel temp : mapModelList) {
                            int idTemp = temp.getId();
                            if (idTemp == id) {
                                isExist = true;
                                break;
                            } else {
                                continue;
                            }
                        }
                        if (!isExist) {
                            mapModelList.add(mapModel);
                        }
                    } else {
                        mapModelList.add(mapModel);
                    }
                }
            }
        }
        resultListModel.setRows(mapModelList);
        resultListModel.setTotal(mapModelList.size());
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：获取区域级别下拉框数据
     * </p>
     *
     * @param id
     * @return
     * @author 王垒, 2016年3月23日下午2:34:22
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAreaLevelDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryAreaLevelDropDown(int id) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getAreaLevelFlag(id);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);

        return resultListModel;
    }

    /**
     * <p>[功能描述]：判断区域主风向</p>
     *
     * @param projectId
     * @param areaId
     * @param levelFlag
     * @param dataType
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param httpsession
     * @return
     * @author 王垒, 2017年9月11日下午3:54:48
     * @since envdust 1.0.0
     */
    @RequestMapping(value = "/queryAreaAvgValue", method = {RequestMethod.POST})
    @ResponseBody
    public AreaStatisticModel queryAreaAvgValue(
            @RequestParam(value = "projectId") String projectId,
            String areaId, String levelFlag, String dataType, String thingCode,
            String beginTime, String endTime, HttpSession httpsession) {
        AreaStatisticModel areaStatisticModel = new AreaStatisticModel();
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (areaId != null && !areaId.isEmpty()) {
            try {
                String mainWd = "";
                String areaName = areaService.getAreaName(Integer.valueOf(areaId));
                areaStatisticModel.setAreaId(areaId);
                areaStatisticModel.setAreaName(areaName);
                List<Integer> listAreaId = new ArrayList<Integer>();
                if (levelFlag != null && levelFlag.equals(DefaultArgument.BOTTOM_LEVEL_FALG)) {
                    listAreaId.add(Integer.valueOf(areaId));
                } else {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(Integer.valueOf(areaId));
                    listAreaId = treeService.getAuthorityBottomArea(projectId, list, Integer.valueOf(list.get(0)), usercode);
                }
                //TODO 添加areaId为空的情况
                if (listAreaId != null && listAreaId.size() > 0) {
                    double value = areaService.getAreaAvgValue(listAreaId, dataType, thingCode, beginTime, endTime);
                    areaStatisticModel.setAvgValue(String.valueOf(value));
                    //判断主风向
                    if (value >= 355 || value <= 5) {
                        mainWd = "南风";
                    } else if (value > 5 && value < 85) {
                        mainWd = "西南风";
                    } else if (value >= 85 && value <= 95) {
                        mainWd = "西风";
                    } else if (value > 95 && value < 175) {
                        mainWd = "西北风";
                    } else if (value >= 175 && value <= 185) {
                        mainWd = "北风";
                    } else if (value > 185 && value < 265) {
                        mainWd = "东北风";
                    } else if (value >= 265 && value <= 275) {
                        mainWd = "东风";
                    } else if (value > 275 && value < 355) {
                        mainWd = "东南风";
                    } else {
                        mainWd = "未知风向";
                    }
                    areaStatisticModel.setMainWd(mainWd);
                }
            } catch (Exception e) {
                logger.error(LOG + "：获取区域主风向失败，信息为:" + e.getMessage());
            }
        }

        return areaStatisticModel;
    }

    /**
     * 获取第四级区域信息
     *
     * @return list
     */
    @RequestMapping("/queryFourthAreaInfo")
    @ResponseBody
    public List<Map<String, String>> queryFourthAreaInfo() {
        return areaService.queryFourthAreaInfo();
    }

    /**
     * <p>
     * [功能描述]：将Area转换成AreaModel
     * </p>
     *
     * @param areaModel
     * @return
     * @author 王垒, 2016年3月23日上午11:48:07
     * @since EnvDust 1.0.0
     */
    private Area ConvertArea(AreaModel areaModel, HttpSession httpsession) {
        Area area = new Area();
        if (areaModel != null) {
            area.setAreaId(areaModel.getAreaId());
            area.setAreaName(areaModel.getAreaName());
            AreaLevel areaLevel = new AreaLevel();
            Area parentArea = new Area();
            parentArea.setAreaId(areaModel.getParentId());
            area.setParentId(areaModel.getParentId());
            areaLevel.setLevelId(mapService.getAreaLevelId(areaModel.getLevelId(), areaModel.getLevelFlag()).get(0).getId());
            area.setAreaLevel(areaLevel);
            area.setAreaPath(areaService.getAreaPath(areaModel.getParentId(), areaModel.getAreaName()));
            area.setCityId(areaModel.getCityId());
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                area.setOptUser(loginuser.getUserId());
            }
            area.setRowCount(areaModel.getRows());
            area.setRowIndex((areaModel.getPage() - 1) * areaModel.getRows());
        }
        return area;
    }

    /**
     * <p>
     * [功能描述]：将AreaModel转换成Area
     * </p>
     *
     * @param area
     * @return
     * @author 王垒, 2016年3月23日下午12:00:11
     * @since EnvDust 1.0.0
     */
    private AreaModel ConvertAreaModel(Area area) {
        AreaModel areaModel = new AreaModel();
        if (area != null) {
            areaModel.setAreaId(area.getAreaId());
            areaModel.setAreaName(area.getAreaName());
            areaModel.setParentId(area.getParentId());
            List<MapModel> listMapModel = mapService.getAreaMap(area.getParentId(), DefaultArgument.INT_DEFAULT);
            if (listMapModel != null && listMapModel.size() > 0) {
                areaModel.setParentName(listMapModel.get(0).getName());
            } else {
                areaModel.setParentName(null);
            }
            areaModel.setLevelId(area.getAreaLevel().getLevelId());
            areaModel.setLevelFlag(area.getAreaLevel().getLevelFlag());
            areaModel.setLevelName(area.getAreaLevel().getLevelName());
            areaModel.setAreaPath(area.getAreaPath());
            areaModel.setCityId(area.getCityId());
            areaModel.setOptUserName(userService.getUserNameById(area.getOptUser(), null));
            areaModel.setOptTime(DateUtil.TimestampToString(area.getOptTime(), DateUtil.DATA_TIME));
        }
        return areaModel;
    }

}
