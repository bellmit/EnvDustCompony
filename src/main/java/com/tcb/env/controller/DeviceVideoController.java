package com.tcb.env.controller;

import com.tcb.env.model.*;
import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.DeviceVideo;
import com.tcb.env.service.IDeviceVideoService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 设备视频控制器
 * @Date: Create in 2019/6/21 14:18
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/DeviceVideoController")
public class DeviceVideoController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DeviceVideoController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeviceVideoController.class);

    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;

    /**
     * 声明设备视频服务
     */
    @Resource
    private IDeviceVideoService deviceVideoService;

    /**
     * 查询设备视频个数
     *
     * @param deviceVideoModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/queryDeviceVideoCount", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceVideoModel> queryDeviceVideoCount(DeviceVideoModel deviceVideoModel, HttpSession httpSession) {
        ResultListModel<DeviceVideoModel> resultListModel = new ResultListModel<DeviceVideoModel>();
        DeviceVideo deviceVideo = ConvertDeviceVideo(deviceVideoModel, httpSession);
        int count = deviceVideoService.getDeviceVideoCount(deviceVideo);
        resultListModel.setResult(true);
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * 查询设备视频信息
     *
     * @param deviceVideoModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/queryDeviceVideo", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceVideoModel> queryDeviceVideo(DeviceVideoModel deviceVideoModel, HttpSession httpSession) {
        ResultListModel<DeviceVideoModel> resultListModel = new ResultListModel<DeviceVideoModel>();
        List<DeviceVideoModel> dvmList = new ArrayList<DeviceVideoModel>();
        List<DeviceVideo> dvList = new ArrayList<DeviceVideo>();
        DeviceVideo deviceVideo = ConvertDeviceVideo(deviceVideoModel, httpSession);
        int count = deviceVideoService.getDeviceVideoCount(deviceVideo);
        if (count > 0) {
            dvList = deviceVideoService.getDeviceVideo(deviceVideo);
            for (DeviceVideo temp : dvList) {
                DeviceVideoModel dvmTemp = ConvertDeviceVideoModel(temp);
                if (dvmTemp != null) {
                    dvmList.add(dvmTemp);
                }
            }
            resultListModel.setRows(dvmList);
        }
        resultListModel.setResult(true);
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * 查询报警抓拍
     * @param videoId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/queryVideoAlarmImage", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<VideoImageModel> queryVideoAlarmImage(String videoId, HttpSession httpSession) {
        ResultListModel<VideoImageModel> resultListModel = new ResultListModel<VideoImageModel>();
        List<VideoImageModel> viList = new ArrayList<VideoImageModel>();
        if (!StringUtils.isEmpty(videoId)) {
            resultListModel.setResult(false);
            resultListModel.setDetail("查询参数不能为空！");
        } else {
            viList = deviceVideoService.getVideoAlarmImage(videoId);
            resultListModel.setRows(viList);
            resultListModel.setTotal(viList.size());
            resultListModel.setResult(true);
        }
        return resultListModel;
    }

    /**
     * 新增视频设备
     *
     * @param deviceVideoModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/insertDeviceVideo", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertDeviceVideo(DeviceVideoModel deviceVideoModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deviceVideoModel != null) {
            try {
                DeviceVideo deviceVideo = new DeviceVideo();
                deviceVideo.setVideoCode(deviceVideoModel.getVideoCode());
                if (deviceVideoService.getDeviceVideoCount(deviceVideo) == 0) {
                    deviceVideo = ConvertDeviceVideo(deviceVideoModel, httpSession);
                    int intResult = deviceVideoService.insertDeviceVideo(deviceVideo);
                    if (intResult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增视频设备失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此视频设备，请使用其他登录账号！");
                }

            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增视频设备失败！");
                logger.error(LOG + "：新增视频设备失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新视频设备
     *
     * @param deviceVideoModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateDeviceVideo", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDeviceVideo(DeviceVideoModel deviceVideoModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deviceVideoModel != null) {
            try {
                if (deviceVideoService.getDeviceVideoExist(deviceVideoModel.getVideoId(), deviceVideoModel.getVideoCode()) == 0) {
                    DeviceVideo deviceVideo = ConvertDeviceVideo(deviceVideoModel, httpSession);
                    int intResult = deviceVideoService.updateDeviceVideo(deviceVideo);
                    if (intResult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新视频设备失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此视频设备，请使用其他登录账号！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新视频设备失败！");
                logger.error(LOG + "：更新视频设备失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * 删除视频设备
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/deleteDeviceVideo", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteDeviceVideo(@RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                int intresult = deviceVideoService.deleteDeviceVideo(list);
                if (intresult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("删除视频设备成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除视频设备失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除视频设备失败！");
                logger.error(LOG + "：删除视频设备失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除视频设备失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * 将DeviceVideo转换为DeviceVideoModel
     *
     * @param deviceVideo
     * @return
     */
    private DeviceVideoModel ConvertDeviceVideoModel(DeviceVideo deviceVideo) {
        DeviceVideoModel deviceVideoModel = new DeviceVideoModel();
        if (deviceVideo != null) {
            deviceVideoModel.setVideoId(String.valueOf(deviceVideo.getVideoId()));
            deviceVideoModel.setVideoCode(deviceVideo.getVideoCode());
            deviceVideoModel.setVideoName(deviceVideo.getVideoName());
            deviceVideoModel.setVideoIp(deviceVideo.getVideoIp());
            deviceVideoModel.setVideoPort(String.valueOf(deviceVideo.getVideoPort()));
            deviceVideoModel.setVideoUrl(deviceVideo.getVideoUrl());
            deviceVideoModel.setVideoRec(deviceVideo.getVideoRec());
            deviceVideoModel.setVideoToken(deviceVideo.getVideoToken());
            deviceVideoModel.setKitToken(deviceVideo.getKitToken());
            if (deviceVideo.getDevice() != null) {
                deviceVideoModel.setDeviceCode(deviceVideo.getDevice().getDeviceCode());
                deviceVideoModel.setDeviceName(deviceVideo.getDevice().getDeviceName());
            }
            deviceVideoModel.setUserCode(deviceVideo.getUserCode());
            deviceVideoModel.setUserPassword(deviceVideo.getUserPassword());
            deviceVideoModel.setVideoX(String.valueOf(deviceVideo.getVideoX()));
            deviceVideoModel.setVideoY(String.valueOf(deviceVideo.getVideoY()));
            deviceVideoModel.setVideoFlag(String.valueOf(deviceVideo.getVideoFlag()));
            deviceVideoModel.setVideoType(String.valueOf(deviceVideo.getVideoType()));
            deviceVideoModel.setVideoBrand(String.valueOf(deviceVideo.getVideoBrand()));
            // 操作者
            deviceVideoModel.setOptUserName(userService.getUserNameById(deviceVideo.getOptUser(), null));
            deviceVideoModel.setOptTime(DateUtil.TimestampToString(deviceVideo.getOptTime(), DateUtil.DATA_TIME));
        }
        return deviceVideoModel;
    }

    /**
     * 将DeviceVideoModel转换为DeviceVideo
     *
     * @param deviceVideoModel
     * @param httpSession
     * @return
     */
    private DeviceVideo ConvertDeviceVideo(DeviceVideoModel deviceVideoModel, HttpSession httpSession) {
        DeviceVideo deviceVideo = new DeviceVideo();
        if (deviceVideoModel != null) {
            if (!StringUtils.isEmpty(deviceVideoModel.getVideoId())) {
                deviceVideo.setVideoId(Integer.valueOf(deviceVideoModel.getVideoId()));
            }
            deviceVideo.setVideoCode(deviceVideoModel.getVideoCode());
            deviceVideo.setVideoName(deviceVideoModel.getVideoName());
            deviceVideo.setVideoIp(deviceVideoModel.getVideoIp());
            deviceVideo.setVideoPort(deviceVideoModel.getVideoPort());
            deviceVideo.setVideoUrl(deviceVideoModel.getVideoUrl());
            deviceVideo.setVideoRec(deviceVideoModel.getVideoRec());
            deviceVideo.setVideoToken(deviceVideoModel.getVideoToken());
            deviceVideo.setKitToken(deviceVideoModel.getKitToken());
            Device device = new Device();
            device.setDeviceCode(deviceVideoModel.getDeviceCode());
            if (!StringUtils.isEmpty(deviceVideoModel.getAreaId())) {
                Area area = new Area();
                area.setAreaId(Integer.valueOf(deviceVideoModel.getAreaId()));
                device.setArea(area);
            }
            deviceVideo.setDevice(device);
            deviceVideo.setUserCode(deviceVideoModel.getUserCode());
            deviceVideo.setUserPassword(deviceVideoModel.getUserPassword());
            if (!StringUtils.isEmpty(deviceVideoModel.getVideoX())) {
                deviceVideo.setVideoX(Double.valueOf(deviceVideoModel.getVideoX()));
            }
            if (!StringUtils.isEmpty(deviceVideoModel.getVideoY())) {
                deviceVideo.setVideoY(Double.valueOf(deviceVideoModel.getVideoY()));
            }
            if (!StringUtils.isEmpty(deviceVideoModel.getVideoFlag())) {
                deviceVideo.setVideoFlag(Integer.valueOf(deviceVideoModel.getVideoFlag()));
            }
            if (!StringUtils.isEmpty(deviceVideoModel.getVideoType())) {
                deviceVideo.setVideoType(Integer.valueOf(deviceVideoModel.getVideoType()));
            }
            if (!StringUtils.isEmpty(deviceVideoModel.getVideoBrand())) {
                deviceVideo.setVideoBrand(Integer.valueOf(deviceVideoModel.getVideoBrand()));
            }
            // 获取操作者
            UserModel loginUser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginUser != null) {
                deviceVideo.setOptUser(loginUser.getUserId());
            }
            deviceVideo.setRowCount(deviceVideoModel.getRows());
            deviceVideo.setRowIndex((deviceVideoModel.getPage() - 1) * deviceVideoModel.getRows());
        }
        return deviceVideo;
    }

}
