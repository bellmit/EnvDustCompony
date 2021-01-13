package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：和天气实况天气信息</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年11月29日上午10:29:34
 * @since	EnvDust 1.0.0
 *
 */
public class HeWeatherNowModel {

	private String cloud;//云量
	private String cond_code;//实况天气状况代码
	private String cond_txt;//实况天气状况
	private String fl;//体感温度，默认单位：摄氏度
	private String hum;//相对湿度
	private String pcpn;//降水量
	private String pres;//大气压强
	private String tmp;//温度，默认单位：摄氏度
	private String vis;//能见度，默认单位：公里
	private String wind_deg;//风向360角度
	private String wind_dir;//风向
	private String wind_sc;//风力
	private String wind_spd;//风速，公里/小时
	
	/**
	 * @return the cloud
	 */
	public String getCloud() {
		return cloud;
	}
	/**
	 * @param cloud the cloud to set
	 */
	public void setCloud(String cloud) {
		this.cloud = cloud;
	}
	/**
	 * @return the cond_code
	 */
	public String getCond_code() {
		return cond_code;
	}
	/**
	 * @param cond_code the cond_code to set
	 */
	public void setCond_code(String cond_code) {
		this.cond_code = cond_code;
	}
	/**
	 * @return the cond_txt
	 */
	public String getCond_txt() {
		return cond_txt;
	}
	/**
	 * @param cond_txt the cond_txt to set
	 */
	public void setCond_txt(String cond_txt) {
		this.cond_txt = cond_txt;
	}
	/**
	 * @return the fl
	 */
	public String getFl() {
		return fl;
	}
	/**
	 * @param fl the fl to set
	 */
	public void setFl(String fl) {
		this.fl = fl;
	}
	/**
	 * @return the hum
	 */
	public String getHum() {
		return hum;
	}
	/**
	 * @param hum the hum to set
	 */
	public void setHum(String hum) {
		this.hum = hum;
	}
	/**
	 * @return the pcpn
	 */
	public String getPcpn() {
		return pcpn;
	}
	/**
	 * @param pcpn the pcpn to set
	 */
	public void setPcpn(String pcpn) {
		this.pcpn = pcpn;
	}
	/**
	 * @return the pres
	 */
	public String getPres() {
		return pres;
	}
	/**
	 * @param pres the pres to set
	 */
	public void setPres(String pres) {
		this.pres = pres;
	}
	/**
	 * @return the tmp
	 */
	public String getTmp() {
		return tmp;
	}
	/**
	 * @param tmp the tmp to set
	 */
	public void setTmp(String tmp) {
		this.tmp = tmp;
	}
	/**
	 * @return the vis
	 */
	public String getVis() {
		return vis;
	}
	/**
	 * @param vis the vis to set
	 */
	public void setVis(String vis) {
		this.vis = vis;
	}
	/**
	 * @return the wind_deg
	 */
	public String getWind_deg() {
		return wind_deg;
	}
	/**
	 * @param wind_deg the wind_deg to set
	 */
	public void setWind_deg(String wind_deg) {
		this.wind_deg = wind_deg;
	}
	/**
	 * @return the wind_dir
	 */
	public String getWind_dir() {
		return wind_dir;
	}
	/**
	 * @param wind_dir the wind_dir to set
	 */
	public void setWind_dir(String wind_dir) {
		this.wind_dir = wind_dir;
	}
	/**
	 * @return the wind_sc
	 */
	public String getWind_sc() {
		return wind_sc;
	}
	/**
	 * @param wind_sc the wind_sc to set
	 */
	public void setWind_sc(String wind_sc) {
		this.wind_sc = wind_sc;
	}
	/**
	 * @return the wind_spd
	 */
	public String getWind_spd() {
		return wind_spd;
	}
	/**
	 * @param wind_spd the wind_spd to set
	 */
	public void setWind_spd(String wind_spd) {
		this.wind_spd = wind_spd;
	}
	
}
