package com.tcb.env.service.impl;

import com.tcb.env.dao.IDeviceVideoDao;
import com.tcb.env.message.CameraMessage;
import com.tcb.env.model.VideoImageModel;
import com.tcb.env.pojo.DeviceVideo;
import com.tcb.env.service.IDeviceVideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 设备视频服务接口实现类
 * @Date: Create in 2019/6/21 15:29
 * @Modify by WangLei
 */
@Service("deviceVideoService")
class DeviceVideoServiceImpl implements IDeviceVideoService {

    @Resource
    private IDeviceVideoDao deviceVideoDao;

    @Override
    public int getDeviceVideoCount(DeviceVideo deviceVideo) {
        return deviceVideoDao.getDeviceVideoCount(deviceVideo);
    }

    @Override
    public List<DeviceVideo> getDeviceVideo(DeviceVideo deviceVideo) {
        return deviceVideoDao.getDeviceVideo(deviceVideo);
    }

    @Override
    public int insertDeviceVideo(DeviceVideo deviceVideo) {
        return deviceVideoDao.insertDeviceVideo(deviceVideo);
    }

    @Override
    public int updateDeviceVideo(DeviceVideo deviceVideo) {
        return deviceVideoDao.updateDeviceVideo(deviceVideo);
    }

    @Override
    public int deleteDeviceVideo(List<String> idList) {
        return deviceVideoDao.deleteDeviceVideo(idList);
    }

    @Override
    public int getDeviceVideoExist(String videoId, String videoCode) {
        return deviceVideoDao.getDeviceVideoExist(videoId, videoCode);
    }

    @Override
    public List<VideoImageModel> getVideoAlarmImage(String videoId) {
        return deviceVideoDao.getVideoAlarmImage(videoId);
    }

    @Override
    public int updateVideoToken(String videoBrand, String videoToken) {
        return deviceVideoDao.updateVideoToken(videoBrand, videoToken);
    }

    @Override
    public int updateVideoKitToken(String videoCode, String kitToken) {
        return deviceVideoDao.updateVideoKitToken(videoCode,kitToken);
    }

    @Override
    public List<CameraMessage> getPhotographCamera(Timestamp beginTime, Timestamp endTime) {
        return deviceVideoDao.getPhotographCamera(beginTime, endTime);
    }

}
