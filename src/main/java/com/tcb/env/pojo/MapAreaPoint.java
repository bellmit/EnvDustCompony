package com.tcb.env.pojo;

import java.sql.Timestamp;
/**
 * 
 * <p>[功能描述]：覆盖区域的坐标点pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王坤
 * @version	1.0, 2018年8月31日  下午 16:11:21
 * @since	EnvDust 1.0.0
 *
 */
public class MapAreaPoint extends BasePojo {
	private long pointId;
	private int maId;
	private double lat;
	private double lng;
	private int optUser;
	private Timestamp optTime;
	public long getPointId() {
		return pointId;
	}
	public void setPointId(long pointId) {
		this.pointId = pointId;
	}
	public int getMaId() {
		return maId;
	}
	public void setMaId(int maId) {
		this.maId = maId;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public int getOptUser() {
		return optUser;
	}
	public void setOptUser(int optUser) {
		this.optUser = optUser;
	}
	public Timestamp getOptTime() {
		return optTime;
	}
	public void setOptTime(Timestamp optTime) {
		this.optTime = optTime;
	}
	@Override
	public String toString() {
		return "MapAreaPoint [pointId=" + pointId + ", maId=" + maId + ", lat="
				+ lat + ", lng=" + lng + ", optUser=" + optUser + ", optTime="
				+ optTime + "]";
	}
	
}
