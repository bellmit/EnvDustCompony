package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：和天气基础信息</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年11月29日上午10:29:34
 * @since	EnvDust 1.0.0
 *
 */
public class HeWeatherBasicModel {
	
	private String cid;//地区／城市ID
	private String location;//地区／城市名称
	private String parent_city;//该地区／城市的上级城市
	private String admin_area;//该地区／城市所属行政区域
	private String cnty;//该地区／城市所属国家名称
	private String lat;//地区／城市经度
	private String lon;//地区／城市纬度
	private String tz;//该地区／城市所在时区
	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the parent_city
	 */
	public String getParent_city() {
		return parent_city;
	}
	/**
	 * @param parent_city the parent_city to set
	 */
	public void setParent_city(String parent_city) {
		this.parent_city = parent_city;
	}
	/**
	 * @return the admin_area
	 */
	public String getAdmin_area() {
		return admin_area;
	}
	/**
	 * @param admin_area the admin_area to set
	 */
	public void setAdmin_area(String admin_area) {
		this.admin_area = admin_area;
	}
	/**
	 * @return the cnty
	 */
	public String getCnty() {
		return cnty;
	}
	/**
	 * @param cnty the cnty to set
	 */
	public void setCnty(String cnty) {
		this.cnty = cnty;
	}
	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * @return the lon
	 */
	public String getLon() {
		return lon;
	}
	/**
	 * @param lon the lon to set
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}
	/**
	 * @return the tz
	 */
	public String getTz() {
		return tz;
	}
	/**
	 * @param tz the tz to set
	 */
	public void setTz(String tz) {
		this.tz = tz;
	}
	
}
