package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.UploadFile;

/**
 * 
 * <p>[功能描述]：上传文件数据库接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年12月3日下午1:34:01
 * @since	EnvDust 1.0.0
 *
 */
public interface IUploadFileDao {
	
	/**
	 * 
	 * <p>[功能描述]：查询上传文件个数</p>
	 * 
	 * @author	王垒, 2018年12月3日下午1:33:49
	 * @since	EnvDust 1.0.0
	 *
	 * @param uploadFile
	 * @return
	 */
	int getUploadFileCount(@Param("uploadFile")UploadFile uploadFile);
	
	/**
	 * 
	 * <p>[功能描述]：查询上传文件明细</p>
	 * 
	 * @author	王垒, 2018年12月3日下午1:33:56
	 * @since	EnvDust 1.0.0
	 *
	 * @param uploadFile
	 * @return
	 */
	List<UploadFile> getUploadFile(@Param("uploadFile")UploadFile uploadFile);
	
	/**
	 * 
	 * <p>[功能描述]：新增上传文件</p>
	 * 
	 * @author	王垒, 2018年12月3日下午1:35:40
	 * @since	EnvDust 1.0.0
	 *
	 * @param uploadFile
	 * @return
	 */
	int insertUploadFile(@Param("uploadFile")UploadFile uploadFile);
	
	/**
	 * 
	 * <p>[功能描述]：删除上传文件</p>
	 * 
	 * @author	王垒, 2018年12月3日下午1:36:53
	 * @since	EnvDust 1.0.0
	 *
	 * @param fileId
	 * @return
	 */
	int deleteUploadFile(@Param("fileId")Integer fileId);
	
	/**
	 * 
	 * <p>[功能描述]：删除上传文件</p>
	 * 
	 * @author	王垒, 2018年12月3日下午1:36:53
	 * @since	EnvDust 1.0.0
	 *
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	int deleteUploadFileByItem(
			@Param("fileType")String fileType,
			@Param("fileName")String fileName);
	
	/**
	 * 
	 * <p>[功能描述]：通过ID获取上传文件</p>
	 * 
	 * @author	王垒, 2018年12月5日上午9:28:59
	 * @since	EnvDust 1.0.0
	 *
	 * @param fileId
	 * @return
	 */
	UploadFile getUploadFileById(@Param("fileId")Integer fileId);
	
	/**
	 * 
	 * <p>[功能描述]：存在上传文件个数</p>
	 * 
	 * @author	王垒, 2018年12月3日下午1:36:53
	 * @since	EnvDust 1.0.0
	 *
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	int existUploadFileCount(
			@Param("fileType")String fileType,
			@Param("fileName")String fileName);

}
