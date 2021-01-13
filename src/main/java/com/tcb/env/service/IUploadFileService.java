package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.ResultModel;
import com.tcb.env.pojo.UploadFile;

/**
 * 
 * <p>[功能描述]：上传文件服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年12月3日下午2:05:28
 * @since	EnvDust 1.0.0
 *
 */
public interface IUploadFileService {

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
	int getUploadFileCount(UploadFile uploadFile);
	
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
	List<UploadFile> getUploadFile(UploadFile uploadFile);
	
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
	ResultModel insertUploadFile(UploadFile uploadFile) throws Exception;
	
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
	ResultModel deleteUploadFile(Integer fileId) throws Exception;
	
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
	UploadFile getUploadFileById(Integer fileId);
	
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
	int existUploadFileCount(String fileType,String fileName);
	
}
