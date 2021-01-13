package com.tcb.env.service;

/**
 * <p>[功能描述]：设备视频操作服务类接口(弃用)</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年11月22日下午3:11:43
 * @since EnvDust 1.0.0
 */
public interface IVideoDeviceService {

    /**
     * <p>[功能描述]：获取设备摄像头IP</p>
     *
     * @param deviceMn:设备MN号
     * @return
     * @author 王垒, 2016年11月22日下午3:13:09
     * @since EnvDust 1.0.0
     */
    @Deprecated
    String getVideoDeviceIpByMn(String deviceMn);

}
