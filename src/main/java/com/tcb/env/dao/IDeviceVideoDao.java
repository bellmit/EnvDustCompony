package com.tcb.env.dao;

import com.tcb.env.message.CameraMessage;
import com.tcb.env.model.VideoImageModel;
import com.tcb.env.pojo.DeviceVideo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>[功能描述]：设备视频服务Dao</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2019年06月21日下午15:06:21
 * @since EnvDust 1.0.0
 */
public interface IDeviceVideoDao {

    /**
     * 查询设备视频信息个数
     *
     * @param deviceVideo
     * @return
     */
    int getDeviceVideoCount(@Param("deviceVideo") DeviceVideo deviceVideo);

    /**
     * 查询设备视频信息
     *
     * @param deviceVideo
     * @return
     */
    List<DeviceVideo> getDeviceVideo(@Param("deviceVideo") DeviceVideo deviceVideo);

    /**
     * 新增设备视频
     *
     * @param deviceVideo
     * @return
     */
    int insertDeviceVideo(@Param("deviceVideo") DeviceVideo deviceVideo);

    /**
     * 更新设备视频
     *
     * @param deviceVideo
     * @return
     */
    int updateDeviceVideo(@Param("deviceVideo") DeviceVideo deviceVideo);

    /**
     * 删除设备视频
     *
     * @param idList
     * @return
     */
    int deleteDeviceVideo(@Param("idList") List<String> idList);


    /**
     * 判断是否存在（非videoId的videoCode）
     *
     * @param videoId
     * @param videoCode
     * @return
     */
    int getDeviceVideoExist(@Param("videoId") String videoId, @Param("videoCode") String videoCode);

    /**
     * 查询报警抓拍
     *
     * @param videoId
     * @return
     */
    List<VideoImageModel> getVideoAlarmImage(@Param("videoId") String videoId);

    /**
     * 更新所有VideoToken
     *
     * @param videoBrand
     * @param videoToken
     * @return
     */
    int updateVideoToken(@Param("videoBrand") String videoBrand,@Param("videoToken") String videoToken);

    /**
     * 更新VideoKitToken
     * @param videoCode
     * @param kitToken
     * @return
     */
    int updateVideoKitToken(
            @Param("videoCode") String videoCode,
            @Param("kitToken") String kitToken);

    /**
     * 查询需要拍照的摄像头
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    List<CameraMessage> getPhotographCamera(
            @Param("beginTime") Timestamp beginTime,
            @Param("endTime") Timestamp endTime);

}
