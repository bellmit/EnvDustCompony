package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：百度地图覆盖区域坐标点</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王坤
 * @version	1.0, 2018年8月31日 上午 8:40:46
 * @since	EnvDust 1.0.0
 *
 */
public class MapAreaPointModel extends BaseModel {
	private String pointId; //坐标点
	private String maId;    //区域id
	private String lat;     //x坐标
	private String lng;     //y坐标
	public String getPointId() {
		return pointId;
	}
	
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
	public String getMaId() {
		return maId;
	}
	
	public void setMaId(String maId) {
		this.maId = maId;
	}
	
	public String getLat() {
		return lat;
	}
	
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getLng() {
		return lng;
	}
	
	public void setLng(String lng) {
		this.lng = lng;
	}
}
