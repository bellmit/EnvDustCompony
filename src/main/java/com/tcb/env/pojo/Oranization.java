package com.tcb.env.pojo;



import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：组织pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年3月15日下午2:29:44
 * @since	EnvDust 1.0.0
 *
 */
public class Oranization extends BasePojo{
	private int orgId = DefaultArgument.INT_DEFAULT;
	private int orgIdParent = DefaultArgument.INT_DEFAULT;
	private String orgName;
	private String levelType;
	private String orgAddress;
	private String orgPath;
	private String orgTelephone;
	private String orgFax;
	private String orgLiaison;
	/**
	 * @return the org_id
	 */
	public int getOrgId() {
		return orgId;
	}
	/**
	 * @return the orgTelephone
	 */
	public String getOrgTelephone() {
		return orgTelephone;
	}
	/**
	 * @param orgTelephone the orgTelephone to set
	 */
	public void setOrgTelephone(String orgTelephone) {
		this.orgTelephone = orgTelephone;
	}
	/**
	 * @return the orgFax
	 */
	public String getOrgFax() {
		return orgFax;
	}
	/**
	 * @param orgFax the orgFax to set
	 */
	public void setOrgFax(String orgFax) {
		this.orgFax = orgFax;
	}
	/**
	 * @param org_id the org_id to set
	 */
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the orgIdParent
	 */
	public int getOrgIdParent() {
		return orgIdParent;
	}
	/**
	 * @param orgIdParent the orgIdParent to set
	 */
	public void setOrgIdParent(int orgIdParent) {
		this.orgIdParent = orgIdParent;
	}
	/**
	 * @return the org_name
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param org_name the org_name to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the levelType
	 */
	public String getLevelType() {
		return levelType;
	}
	/**
	 * @param levelType the levelType to set
	 */
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	/**
	 * @return the orgAddress
	 */
	public String getOrgAddress() {
		return orgAddress;
	}
	/**
	 * @param orgAddress the orgAddress to set
	 */
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	/**
	 * @return the orgPath
	 */
	public String getOrgPath() {
		return orgPath;
	}
	/**
	 * @param orgPath the orgPath to set
	 */
	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}
	
	/**
	 * @return the orgLiaison
	 */
	public String getOrgLiaison() {
		return orgLiaison;
	}
	/**
	 * @param orgLiaison the orgLiaison to set
	 */
	public void setOrgLiaison(String orgLiaison) {
		this.orgLiaison = orgLiaison;
	}
	
}
