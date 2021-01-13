package com.tcb.env.model;

import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：长链接查询返回结构映射</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月8日下午4:18:40
 * @since	EnvDust 1.0.0
 *
 */
public class ResultAjaxPushModel {

	private String select;
	private Object result;
	private Object alarmLine;
	private int normalCount;
	private int overLineCount;
	private int outLinkCount;
	private int noCountCount;
	private int otherCount;
	private int rowTotal;
	private int page = DefaultArgument.INT_DEFAULT;
	private int rows = DefaultArgument.INT_DEFAULT;

	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}

	/**
	 * @param select the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the alarmLine
	 */
	public Object getAlarmLine() {
		return alarmLine;
	}

	/**
	 * @param alarmLine the alarmLine to set
	 */
	public void setAlarmLine(Object alarmLine) {
		this.alarmLine = alarmLine;
	}

	/**
	 * @return the normalCount
	 */
	public int getNormalCount() {
		return normalCount;
	}

	/**
	 * @param normalCount the normalCount to set
	 */
	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}

	/**
	 * @return the overLineCount
	 */
	public int getOverLineCount() {
		return overLineCount;
	}

	/**
	 * @param overLineCount the overLineCount to set
	 */
	public void setOverLineCount(int overLineCount) {
		this.overLineCount = overLineCount;
	}

	/**
	 * @return the outLinkCount
	 */
	public int getOutLinkCount() {
		return outLinkCount;
	}

	/**
	 * @param outLinkCount the outLinkCount to set
	 */
	public void setOutLinkCount(int outLinkCount) {
		this.outLinkCount = outLinkCount;
	}

	/**
	 * @return the noCountCount
	 */
	public int getNoCountCount() {
		return noCountCount;
	}

	/**
	 * @param noCountCount the noCountCount to set
	 */
	public void setNoCountCount(int noCountCount) {
		this.noCountCount = noCountCount;
	}

	/**
	 * @return the otherCount
	 */
	public int getOtherCount() {
		return otherCount;
	}

	/**
	 * @param otherCount the otherCount to set
	 */
	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}

	/**
	 * @return the rowTotal
	 */
	public int getRowTotal() {
		return rowTotal;
	}

	/**
	 * @param rowTotal the rowTotal to set
	 */
	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "ResultAjaxPushModel [select=" + select + ", result=" + result
				+ ", alarmLine=" + alarmLine + ", normalCount=" + normalCount
				+ ", overLineCount=" + overLineCount + ", outLinkCount="
				+ outLinkCount + ", noCountCount=" + noCountCount
				+ ", otherCount=" + otherCount + ", rowTotal=" + rowTotal
				+ ", page=" + page + ", rows=" + rows + "]";
	}
}
