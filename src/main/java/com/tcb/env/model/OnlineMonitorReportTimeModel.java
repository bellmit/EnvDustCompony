package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：在线监测报表列表model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年5月17日上午8:46:02
 * @since	EnvDust 1.0.0
 *
 */
public class OnlineMonitorReportTimeModel {
	
	private String time;
	private String thingValue;
	private String thingCou;
	private String standardFlow;
	private String flowSpeed;
	private String temperature;
	private String humidity;
	private String memo;
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the thingValue
	 */
	public String getThingValue() {
		return thingValue;
	}
	public double convertThingValueDouble() {
		if(thingValue != null && !thingValue.isEmpty()){
			return Double.valueOf(thingValue);
		}else{
			return 0;
		}
	}
	/**
	 * @param thingValue the thingValue to set
	 */
	public void setThingValue(String thingValue) {
		this.thingValue = thingValue;
	}
	/**
	 * @return the thingCou
	 */
	public String getThingCou() {
		return thingCou;
	}
	public double convertThingCouDouble() {
		if(thingCou != null && !thingCou.isEmpty()){
			return Double.valueOf(thingCou);
		}else{
			return 0;
		}
	}
	/**
	 * @param thingCou the thingCou to set
	 */
	public void setThingCou(String thingCou) {
		this.thingCou = thingCou;
	}
	/**
	 * @return the standardFlow
	 */
	public String getStandardFlow() {
		return standardFlow;
	}
	public double convertStandardFlowDouble() {
		if(standardFlow != null && !standardFlow.isEmpty()){
			return Double.valueOf(standardFlow);
		}else{
			return 0;
		}
	}
	/**
	 * @param standardFlow the standardFlow to set
	 */
	public void setStandardFlow(String standardFlow) {
		this.standardFlow = standardFlow;
	}
	/**
	 * @return the flowSpeed
	 */
	public String getFlowSpeed() {
		return flowSpeed;
	}
	public double convertFlowSpeedDouble() {
		if(flowSpeed != null && !flowSpeed.isEmpty()){
			return Double.valueOf(flowSpeed);
		}else{
			return 0;
		}
	}
	/**
	 * @param flowSpeed the flowSpeed to set
	 */
	public void setFlowSpeed(String flowSpeed) {
		this.flowSpeed = flowSpeed;
	}
	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}
	public double convertTemperatureDouble() {
		if(temperature != null && !temperature.isEmpty()){
			return Double.valueOf(temperature);
		}else{
			return 0;
		}
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}
	public double convertHumidityDouble() {
		if(humidity != null && !humidity.isEmpty()){
			return Double.valueOf(humidity);
		}else{
			return 0;
		}
	}
	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
