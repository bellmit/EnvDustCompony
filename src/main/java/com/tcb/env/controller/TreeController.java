package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.service.IAlarmService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IAreaService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.EnumUtil;

@Controller
@RequestMapping("/TreeController")
public class TreeController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "TreeController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(TreeController.class);
    /**
     * 声明gson类
     */
    private Gson gson = new Gson();

    /**
     * 声明树服务接口
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明Alarm服务
     */
    @Resource
    private IAlarmService alarmService;

    /**
     * <p>
     * [功能描述]：获取区域树型结构数据（只显示权限设备数据）
     * </p>
     *
     * @param parentId
     * @param projectId
     * @return
     * @author 王垒, 2016年3月25日上午9:52:47
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAreaTree", method = {RequestMethod.GET,
            RequestMethod.POST})
    public @ResponseBody
    String getAreaTree(
            @RequestParam(value = "parentId") String parentId,
            @RequestParam(value = "projectId", required = false) String projectId,
            String manuName,
            HttpSession httpsession) {
        String strtree = "";
        String state = EnumUtil.TreeStatus.open.toString();
        String sel = "";
        String usercode = "";
        boolean isDevice = false;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        List<TreeModel> list = new ArrayList<TreeModel>();
        createAreaTree("", projectId, state, sel, list, usercode, isDevice, manuName, -1);
        strtree = gson.toJson(list);

        return strtree;
    }

    /**
     * <p>
     * [功能描述]：创建区域树型结构（只显示权限设备数据）
     * </p>
     *
     * @param parentid
     * @param state
     * @param sel
     * @param listnew
     * @param usercode
     * @return
     * @author 王垒, 2016年3月25日上午9:53:09
     * @since EnvDust 1.0.0
     */
    private List<TreeModel> createAreaTree(String parentid, String projectid, String state,
                                           String sel, List<TreeModel> listnew, String usercode,
                                           boolean isDevice, String manuName, int levelFlag) {
        try {
            ArrayList<TreeModel> al = getPrivateSubItem(parentid, manuName, levelFlag);
            if (al != null && !al.isEmpty()) {
                int n = al.size();
                for (int i = 0; i < n; i++) {
                    TreeModel o = (TreeModel) al.get(i);
                    o.setState(state);
                    o.setDevice(isDevice);
                    // 不包含子菜单
                    if (!isHaveSubItem(o.getId())) {
                        List<TreeModel> children = treeService
                                .getAuthorityDevices(usercode, projectid, Integer.parseInt(o.getId()), null, null, null);
                        if (children != null && children.size() > 0) {
                            for (TreeModel treeModel : children) {
                                if (treeModel.getState().equals("N")) {
                                    treeModel.setIconCls(DefaultArgument.DEV_NORMAL);
                                } else if (treeModel.getState().equals("NT")) {
                                    //判断超标等级
                                    String levelNo = alarmService.getAlarmLevel(treeModel.getId());
                                    if (!StringUtils.isEmpty(levelNo)) {
                                        switch (levelNo) {
                                            case "1": {
                                                treeModel.setIconCls(DefaultArgument.DEV_ALARM1);
                                                break;
                                            }
                                            case "2": {
                                                treeModel.setIconCls(DefaultArgument.DEV_ALARM2);
                                                break;
                                            }
                                            case "3": {
                                                treeModel.setIconCls(DefaultArgument.DEV_ALARM3);
                                                break;
                                            }
                                            default: {
                                                treeModel.setIconCls(DefaultArgument.DEV_ALARM);
                                            }
                                        }
                                    } else {
                                        treeModel.setIconCls(DefaultArgument.DEV_ALARM);
                                    }
                                } else if (treeModel.getState().equals("O") || treeModel.getState().equals("Z")) {
                                    treeModel.setIconCls(DefaultArgument.DEV_UNLINK);
                                } else {
                                    treeModel.setIconCls(DefaultArgument.DEV_FAULT);
                                }
                                treeModel.setDevice(true);
                            }
                            o.setChildren(children);
                        }
                    } else {
                        // 是否存在设备结点
                        if (!isHaveAuthorityDevice(o, projectid, usercode)) {
                            continue;
                        } else {
                            List<TreeModel> listchildren = new ArrayList<TreeModel>();
                            o.setChildren(createAreaTree(o.getId(), projectid, state, sel, listchildren, usercode, isDevice, manuName, Integer.parseInt(o.getLevelFlag()) + 1));
                        }
                    }
                    if (o.getChildren() != null) {
                        listnew.add(o);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：获取权限组数失败（只显示权限设备），信息为：" + e.getMessage());
        }
        return listnew;
    }

    /**
     * <p>
     * [功能描述]：获取区域树型结构数据（只显示权限设备数据,权限编码作为参数）
     * </p>
     *
     * @param parentid
     * @return
     * @author 王垒, 2016年3月25日上午9:52:47
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAhrAreaTree", method = {RequestMethod.GET,
            RequestMethod.POST})
    public @ResponseBody
    String getAhrAreaTree(
            @RequestParam(value = "parentid") String parentid,
            @RequestParam(value = "ahrcode") String ahrcode,
            HttpSession httpsession) {
        String strtree = "";
        String state = EnumUtil.TreeStatus.open.toString();
        String sel = "";
        boolean isDevice = false;
        List<TreeModel> list = new ArrayList<TreeModel>();
        createAhrAreaTree("", state, sel, list, ahrcode, isDevice);
        strtree = gson.toJson(list);

        return strtree;
    }

    /**
     * <p>
     * [功能描述]：创建区域树型结构（只显示权限设备数据,权限编码作为参数）
     * </p>
     *
     * @param parentid
     * @param state
     * @param sel
     * @param listnew
     * @param ahrcode
     * @return
     * @author 王垒, 2016年3月25日上午9:53:09
     * @since EnvDust 1.0.0
     */
    private List<TreeModel> createAhrAreaTree(String parentid, String state,
                                              String sel, List<TreeModel> listnew, String ahrcode,
                                              boolean isDevice) {
        try {
            ArrayList<TreeModel> al = getPrivateSubItem(parentid, null, -1);
            if (al != null && !al.isEmpty()) {
                int n = al.size();
                for (int i = 0; i < n; i++) {
                    TreeModel o = (TreeModel) al.get(i);
                    o.setState(state);
                    o.setDevice(isDevice);
                    // 不包含子菜单
                    if (!isHaveSubItem(o.getId())) {
                        List<TreeModel> children = treeService
                                .getAuthorityDeviceByAhrCode(ahrcode,
                                        Integer.parseInt(o.getId()), null);
                        if (children != null && children.size() > 0) {
                            for (TreeModel treeModel : children) {
                                treeModel.setIconCls(DefaultArgument.DEV_NORMAL);
                                treeModel.setDevice(true);
                            }
                            o.setChildren(children);
                        }
                    } else {
                        // 是否存在设备结点
                        if (!isHaveAuthorityDeviceByAhrCode(o, ahrcode)) {
                            continue;
                        } else {
                            List<TreeModel> listchildren = new ArrayList<TreeModel>();
                            o.setChildren(createAhrAreaTree(o.getId(), state, sel,
                                    listchildren, ahrcode, isDevice));
                        }
                    }
                    if (o.getChildren() != null) {
                        listnew.add(o);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：获取权限组数失败（只显示权限设备），信息为：" + e.getMessage());
        }
        return listnew;
    }

    /**
     * <p>
     * [功能描述]：获取树型结构数据（显示所有设备，用户权限勾选）
     * </p>
     *
     * @param parentid
     * @return
     * @author 王垒, 2016年3月25日上午9:52:47
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAreaTreeAllChecked", method = {
            RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getAreaTreeAllChecked(
            @RequestParam(value = "parentid") String parentid,
            @RequestParam(value = "ahrcode") String ahrcode,
            HttpSession httpsession) {
        String strtree = "";
        String state = EnumUtil.TreeStatus.open.toString();
        String sel = "";
        boolean isDevice = false;
        List<TreeModel> list = new ArrayList<TreeModel>();
        createAreaTreeAllChecked("", state, sel, list, ahrcode, isDevice);
        strtree = gson.toJson(list);

        return strtree;
    }

    /**
     * <p>
     * [功能描述]：创建树型结构（显示所有设备，权限组权限勾选）
     * </p>
     *
     * @param parentid
     * @param state
     * @param sel
     * @param listnew
     * @return
     * @author 王垒, 2016年3月25日上午9:53:09
     * @since EnvDust 1.0.0
     */
    private List<TreeModel> createAreaTreeAllChecked(String parentid,
                                                     String state, String sel, List<TreeModel> listnew, String ahrCode,
                                                     boolean isDevice) {
        try {
            ArrayList<TreeModel> al = getPrivateSubItem(parentid, null, -1);
            if (al != null && !al.isEmpty()) {
                int n = al.size();
                for (int i = 0; i < n; i++) {
                    TreeModel o = (TreeModel) al.get(i);
                    o.setState(state);
                    // 不包含子菜单
                    if (!isHaveSubItem(o.getId())) {
                        List<TreeModel> children = treeService.getAllDevices(
                                ahrCode, Integer.parseInt(o.getId()), null);
                        if (children != null && children.size() > 0) {
                            for (TreeModel treeModel : children) {
                                treeModel.setIconCls(DefaultArgument.DEV_NORMAL);
                                treeModel.setDevice(true);
                            }
                            o.setChildren(children);
                        }
                    } else {
                        // 是否存在设备结点
                        if (!isHaveBottomItem(o, ahrCode)) {
                            continue;
                        } else {
                            List<TreeModel> listchildren = new ArrayList<TreeModel>();
                            o.setChildren(createAreaTreeAllChecked(o.getId(),
                                    state, sel, listchildren, ahrCode, isDevice));
                        }
                    }
                    if (o.getChildren() != null) {
                        listnew.add(o);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：获取权限组数失败（显示所有设备，权限组权限勾选），信息为："
                    + e.getMessage());
        }
        return listnew;
    }

    /**
     * <p>
     * [功能描述]：获取监测物树形结构（显示所有监测物，用户权限勾选）
     * </p>
     *
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月7日上午10:04:18
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getMonitorTreeAllChecked", method = {
            RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getMonitorTreeAllChecked(String ahrCode,
                                    HttpSession httpsession) {
        String strtree = "";
        String state = EnumUtil.TreeStatus.open.toString();
        ArrayList<TreeModel> al = treeService.getAllMonitor(ahrCode);
        for (TreeModel treeModel : al) {
            treeModel.setState(state);
        }
        strtree = gson.toJson(al);
        return strtree;
    }

    /**
     * <p>
     * [功能描述]：获取控件树数据（显示符合级别树，权限组权限勾选）
     * </p>
     *
     * @param ahrCode
     * @param levelFlag ：1（模块），2（页面），3（控件）
     * @return
     * @author 王垒, 2016年4月7日上午10:04:18
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAhAhrControlAllChecked", method = {
            RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    String getAhAhrControlAllChecked(String ahrCode,
                                     String levelFlag) {
        String strtree = "";
        String state = EnumUtil.TreeStatus.open.toString();
        ArrayList<TreeModel> al = new ArrayList<TreeModel>();
        switch (levelFlag) {
            case DefaultArgument.MODULE: {
                // 模块结点
                al = treeService.getModule(ahrCode);
                for (TreeModel treeModel : al) {
                    treeModel.setState(state);
                }
            }
            break;
            case DefaultArgument.PAGE: {
                // 模块结点
                al = treeService.getModule(ahrCode);
                for (TreeModel treeModel : al) {
                    treeModel.setState(state);
                    // 页面结点
                    List<TreeModel> children = treeService.getPage(ahrCode,
                            treeModel.getId());
                    if (children != null && children.size() > 0) {
                        for (TreeModel treeChild : children) {
                            treeChild.setState(state);
                        }
                        treeModel.setChildren(children);
                    }
                }
            }
            break;
            case DefaultArgument.CONTROL: {
                // 模块结点
                al = treeService.getModule(ahrCode);
                for (TreeModel treeModel : al) {
                    treeModel.setState(state);
                    // 页面结点
                    List<TreeModel> children = treeService.getPage(ahrCode,
                            treeModel.getId());
                    if (children != null && children.size() > 0) {
                        for (TreeModel treeChild : children) {
                            treeChild.setState(state);
                            // 控件结点
                            List<TreeModel> children2 = treeService.getControl(
                                    ahrCode, treeChild.getId());
                            if (children2 != null && children2.size() > 0) {
                                for (TreeModel treeChild2 : children2) {
                                    treeChild2.setState(state);
                                }
                                treeChild.setChildren(children2);
                            }
                        }
                        treeModel.setChildren(children);
                    }
                }
            }
            break;
            default:
                break;
        }

        strtree = gson.toJson(al);
        return strtree;
    }

    /**
     * <p>
     * [功能描述]：获取所有权限组用户树
     * </p>
     *
     * @return
     * @author 王垒, 2016年4月15日下午4:07:40
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAllAhrUserTree", method = {RequestMethod.GET,
            RequestMethod.POST})
    public @ResponseBody
    String getAllAhrUserTree(String ahrCode) {
        String strtree = "";
        String state = EnumUtil.TreeStatus.open.toString();
        ArrayList<TreeModel> al = treeService.getAllAuthority(ahrCode);
        for (TreeModel treeModel : al) {
            treeModel.setState(state);
            ArrayList<TreeModel> node = treeService.getUserByAhrCode(treeModel
                    .getId());
            if (node != null && node.size() > 0) {
                treeModel.setChildren(node);
            }
        }
        strtree = gson.toJson(al);
        return strtree;
    }

    /**
     * <p>
     * [功能描述]：获取子类数据
     * </p>
     *
     * @param parentid
     * @return
     * @author 王垒, 2016年3月25日上午9:53:33
     * @since EnvDust 1.0.0
     */
    private ArrayList<TreeModel> getPrivateSubItem(String parentid, String manuName, int levelFlag) {
        ArrayList<TreeModel> al = new ArrayList<TreeModel>();
        al = treeService.getAreaChildrenTree(parentid, manuName, levelFlag);

        return al;
    }

    /**
     * <p>
     * [功能描述]：获取子类数据
     * </p>
     *
     * @param areaid
     * @param devicename
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月25日上午9:53:33
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAuthorityDevices", method = {
            RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    private String getAuthorityDevices(int areaid, String devicename,
                                       @RequestParam(value = "projectid", required = false) String projectid,
                                       HttpSession httpsession) {
        ArrayList<TreeModel> al = new ArrayList<TreeModel>();
        // 获取用户code
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession
                .getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (usercode != null && !usercode.isEmpty()) {
            al = treeService.getAuthorityDevices(usercode, projectid, areaid, devicename, null, null);
            for (TreeModel treeModel : al) {
                treeModel.setDevice(true);
                if (treeModel.getState().equals("N")) {
                    treeModel.setIconCls(DefaultArgument.DEV_NORMAL);
                } else if (treeModel.getState().equals("NT")) {
                    //判断超标等级
                    String levelNo = alarmService.getAlarmLevel(treeModel.getId());
                    if (!StringUtils.isEmpty(levelNo)) {
                        switch (levelNo) {
                            case "1": {
                                treeModel.setIconCls(DefaultArgument.DEV_ALARM1);
                                break;
                            }
                            case "2": {
                                treeModel.setIconCls(DefaultArgument.DEV_ALARM2);
                                break;
                            }
                            case "3": {
                                treeModel.setIconCls(DefaultArgument.DEV_ALARM3);
                                break;
                            }
                            default: {
                                treeModel.setIconCls(DefaultArgument.DEV_ALARM);
                            }
                        }
                    } else {
                        treeModel.setIconCls(DefaultArgument.DEV_ALARM);
                    }
                } else if (treeModel.getState().equals("O") || treeModel.getState().equals("Z")) {
                    treeModel.setIconCls(DefaultArgument.DEV_UNLINK);
                } else {
                    treeModel.setIconCls(DefaultArgument.DEV_FAULT);
                }
            }
        }

        return gson.toJson(al);
    }

    /**
     * <p>
     * [功能描述]：判断是否存在子类
     * </p>
     *
     * @param parentid
     * @return
     * @author 王垒, 2016年3月25日上午9:53:48
     * @since EnvDust 1.0.0
     */
    private boolean isHaveSubItem(String parentid) {
        int count = treeService.getSubItemCount(parentid);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>
     * [功能描述]：判断是否存在权限设备结点
     * </p>
     *
     * @param treeModel
     * @param projectId
     * @param userCode
     * @return
     * @author 王垒, 2016年3月28日上午10:06:27
     * @since EnvDust 1.0.0
     */
    private boolean isHaveAuthorityDevice(TreeModel treeModel, String projectId, String userCode) {
        boolean returnFlag = false;
        ArrayList<TreeModel> al = treeService.getAreaChildrenTree(treeModel
                .getId(), null, -1);
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
        return returnFlag;
    }

    /**
     * <p>
     * [功能描述]：判断是否存在权限设备结点(通过权限组编码)
     * </p>
     *
     * @param treeModel
     * @param ahrCode
     * @return
     * @author 王垒, 2016年3月28日上午10:06:27
     * @since EnvDust 1.0.0
     */
    private boolean isHaveAuthorityDeviceByAhrCode(TreeModel treeModel, String ahrCode) {
        boolean returnFlag = false;
        ArrayList<TreeModel> al = treeService.getAreaChildrenTree(treeModel
                .getId(), null, -1);
        for (TreeModel temp : al) {
            int count = treeService.getAuthorityDeviceCountByAhrCode(ahrCode,
                    Integer.parseInt(temp.getId()), null);
            if (count > 0) {
                return true;
            } else {
                boolean innerFlag = isHaveAuthorityDeviceByAhrCode(temp, ahrCode);
                if (innerFlag) {
                    returnFlag = innerFlag;
                }
            }
        }
        return returnFlag;
    }

    /**
     * <p>
     * [功能描述]：判断是否存在设备结点
     * </p>
     *
     * @param treeModel
     * @param ahrCode
     * @return
     * @author 王垒, 2016年3月25日上午10:20:18
     * @since EnvDust 1.0.0
     */
    private boolean isHaveBottomItem(TreeModel treeModel, String ahrCode) {
        boolean returnFlag = false;
        ArrayList<TreeModel> al = treeService.getAreaChildrenTree(treeModel.getId(), null, -1);
        for (TreeModel temp : al) {
            int count = treeService.getAllDevicesCount(ahrCode,
                    Integer.parseInt(temp.getId()), null);
            if (count > 0) {
                return true;
            } else {
                boolean innerFlag = isHaveBottomItem(temp, ahrCode);
                if (innerFlag) {
                    returnFlag = innerFlag;
                }
            }
        }
        return returnFlag;
    }

}
