package com.tcb.env.model;

import java.util.List;

/**
 * 
 * <p>[功能描述]：树形页面传递类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年3月25日上午8:19:40
 * @since	EnvDust 1.0.0
 *
 */
public class TreeModel {

	private String id;
	private String text;
	private String iconCls;
	private String state;
	private String levelFlag;
	private boolean checked;
	private boolean isDevice;
	private String checkedStatus;
	private List<TreeModel> children;
	/**
	 * @return the id/code
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id/code the id/code to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the iconCls
	 */
	public String getIconCls() {
		return iconCls;
	}
	/**
	 * @param iconCls the iconCls to set
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the levelFlag
	 */
	public String getLevelFlag() {
		return levelFlag;
	}
	/**
	 * @param levelFlag the levelFlag to set
	 */
	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return the isDevice
	 */
	public boolean isDevice() {
		return isDevice;
	}
	/**
	 * @param isDevice the isDevice to set
	 */
	public void setDevice(boolean isDevice) {
		this.isDevice = isDevice;
	}
	/**
	 * @return the checkedStatus
	 */
	public String getCheckedStatus() {
		return checkedStatus;
	}
	/**
	 * @param checkedStatus the checkedStatus to set
	 */
	public void setCheckedStatus(String checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	/**
	 * @return the children
	 */
	public List<TreeModel> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}

}
