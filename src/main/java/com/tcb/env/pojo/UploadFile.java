package com.tcb.env.pojo;

import java.sql.Timestamp;

/**
 * 
 * <p>
 * [功能描述]：上传文件pojo
 * </p>
 * <p>
 * Copyright (c) 1998-2018 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2018年12月13日上午10:14:26
 * @since EnvDust 1.0.0
 *
 */
public class UploadFile extends BasePojo {
	
	private Integer fileId;
	private String fileType;
	private String fileName;
	private String filePath;
	private Timestamp uploadTime;
	
	/**
	 * @return the fileId
	 */
	public Integer getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the uploadTime
	 */
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

}
