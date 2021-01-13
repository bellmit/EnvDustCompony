package com.tcb.env.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UploadFileModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.UploadFile;
import com.tcb.env.service.IUploadFileService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
/**
 * 
 * <p>[功能描述]：文件操作控制器（查询、上传、下载）</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年3月14日上午9:12:04
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/FileOperateController")
public class FileOperateController {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "FileOperateController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(FileOperateController.class);
	
	/**
	 * 声明gson对象
	 */
	private Gson gson = new Gson();
	
	/**
	 * 配置文件服务类
	 */
	@Resource
	private Dom4jConfig dom4jConfig;
	
	/**
	 * 声明文件操作服务
	 */
	@Resource
	private IUploadFileService uploadFileService;
	
	/**
	 * 
	 * <p>[功能描述]：获取所有上传文件信息</p>
	 * 
	 * @author	王垒, 2017年4月1日上午11:43:47
	 * @since	EnvDust 1.0.0
	 *
	 * @param fileId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryAllUploadFile", method = { RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody ResultListModel<UploadFileModel> queryAllUploadFile(
			@RequestParam(value="fileType",required=false) String fileType,
			 HttpServletRequest request,HttpServletResponse response) {
		ResultListModel<UploadFileModel> resultListModel = new ResultListModel<UploadFileModel>();
		List<UploadFileModel> uploadFileModelList = new ArrayList<UploadFileModel>();        
    	UploadFile uploadFile = new UploadFile();
    	if(fileType != null && !fileType.isEmpty()){
    		uploadFile.setFileType(fileType);
    	}
    	int count = uploadFileService.getUploadFileCount(uploadFile);
    	if(count>0){
    		List<UploadFile> uploadFileList = uploadFileService.getUploadFile(uploadFile);
    		for (UploadFile temp : uploadFileList) {
    			UploadFileModel uploadFileModel = ConvertUploadFileModel(temp,request.getRequestURL().toString());
    			if(uploadFileModel != null){
    				uploadFileModelList.add(uploadFileModel);
    			}
			}
    		resultListModel.setRows(uploadFileModelList);
    	}
    	resultListModel.setTotal(count);
    	resultListModel.setResult(true);
		return resultListModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：单个获取上传文件信息</p>
	 * 
	 * @author	王垒, 2017年4月1日上午11:43:47
	 * @since	EnvDust 1.0.0
	 *
	 * @param fileId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryUploadFile", method = { RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody UploadFileModel queryUploadFile(
			@RequestParam("fileId") String fileId,
			 HttpServletRequest request,HttpServletResponse response) {
		UploadFileModel uploadFileModel = new UploadFileModel();
        if(fileId != null && !fileId.isEmpty()){
			UploadFile uploadFile = uploadFileService.getUploadFileById(Integer.valueOf(fileId));
			uploadFileModel = ConvertUploadFileModel(uploadFile,request.getRequestURL().toString());
        }
		return uploadFileModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：是否存在相同的文件名称</p>
	 * 
	 * @author	王垒, 2018年12月6日上午9:21:10
	 * @since	EnvDust 1.0.0
	 *
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/queryExsitFile", method = { RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody ResultModel queryExsitFile(
			@RequestParam("fileType") String fileType,@RequestParam("fileName") String fileName) {
		ResultModel resultModel = new ResultModel();
        if(fileType != null && fileName != null){
			int count = uploadFileService.existUploadFileCount(fileType, fileType+"-"+fileName);
			if(count>0){
				resultModel.setResult(true);
				resultModel.setDetail("存在相同的文件名称！");
			}else{
				resultModel.setResult(false);
				resultModel.setDetail("不存在相同的文件名称！");
			}
        }
		return resultModel;
	}
	
   /**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file" name="myfiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    @RequestMapping(value="/fileUpload")
    public void fileUpload(@RequestParam("fileType") String fileType,
    		@RequestParam("myFiles") MultipartFile[] myFiles, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	ResultModel resultModel = new ResultModel();
    	//可以在上传文件的同时接收其它参数
        System.out.println("收到类型["+fileType+"]的文件上传请求");
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        String realPath = request.getSession().getServletContext().getRealPath("/"+DefaultArgument.UPLOAD_FOLDER);
        //设置响应给前台内容的数据格式
        response.setContentType("text/plain; charset=UTF-8");
        //设置响应给前台内容的PrintWriter对象
        PrintWriter out = response.getWriter();
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        if(myFiles != null && myFiles.length>0){
        	for(MultipartFile myFile : myFiles){
                if(myFile.isEmpty()){
                	resultModel.setResult(false);
                	resultModel.setDetail("请选择文件后上传");
                    out.print(gson.toJson(resultModel));
                    out.flush();
                    return;
                }else{
                    originalFilename = fileType+"-"+myFile.getOriginalFilename();
                    System.out.println("文件名称: " + myFile.getOriginalFilename());
                    System.out.println("文件长度: " + myFile.getSize());
                    try {
                        //这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                        //此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                        FileUtils.copyInputStreamToFile(myFile.getInputStream(), new File(realPath, originalFilename));
                        //更新数据库记录
                        if(fileType != null){
                        	//赋值上传文件
                            UploadFile uploadFile = new UploadFile();
                            uploadFile.setFileName(originalFilename);
                            uploadFile.setFilePath(realPath);
                            uploadFile.setFileType(fileType);
                            uploadFile.setUploadTime(DateUtil.GetSystemDateTime(0));
                    		UserModel loginUser = (UserModel) request.getSession().getAttribute(DefaultArgument.LOGIN_USER);
                    		int optUserId = loginUser.getUserId();
                    		uploadFile.setOptUser(optUserId);
                    		uploadFileService.insertUploadFile(uploadFile);
                        }
                    } catch (IOException e) {
                        logger.error(LOG+"：文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                        e.printStackTrace();
                        resultModel.setResult(false);
                        resultModel.setDetail("文件上传失败，请重试！");
                    	out.print(gson.toJson(resultModel));
                        out.flush();
                    } catch (Exception e) {
                    	logger.error(LOG+"：文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                        e.printStackTrace();
                        resultModel.setResult(false);
                        resultModel.setDetail("文件上传失败，请重试！");
                     	out.print(gson.toJson(resultModel));
                        out.flush();
    				}
                }
            }
            resultModel.setResult(true);
            resultModel.setDetail("文件上传成功！");
        }else{
        	 resultModel.setResult(false);
             resultModel.setDetail("文件上传失败！");
        }
        System.out.println("文件位置: " + request.getContextPath() + "/upload/" + originalFilename);
    	out.print(gson.toJson(resultModel));
        out.flush();
    }
    
    /**
     * 
     * <p>[功能描述]：删除任务上传文件</p>
     * 
     * @author	王垒, 2017年4月10日下午2:02:28
     * @since	EnvDust 1.0.0
     *
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/deleteUploadFile", method = { RequestMethod.POST })
    public @ResponseBody ResultModel deleteUploadFile(String fileId){
    	ResultModel resultModel = new ResultModel();
		if (fileId != null && !fileId.isEmpty()) {
			try {
				resultModel = uploadFileService.deleteUploadFile(Integer.valueOf(fileId));
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除文件失败！");
				logger.error(LOG + "：删除文件失败，信息为:" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除文件失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
    }
 
    /**
     * 
     * <p>[功能描述]：将UploadFile转换为UploadFileModel</p>
     * 
     * @author	王垒, 2017年4月1日下午12:28:12
     * @since	EnvDust 1.0.0
     *
     * @param uploadFile
     * @param requestUrl
     * @return
     */
    public UploadFileModel ConvertUploadFileModel(UploadFile uploadFile,String requestUrl){
    	UploadFileModel uploadFileModel = new UploadFileModel();
    	if(uploadFile != null){
    		uploadFileModel.setFileId(String.valueOf(uploadFile.getFileId()));
			String fileName = uploadFile.getFileName();
			uploadFileModel.setFileName(fileName);
			uploadFileModel.setFilePath(uploadFile.getFilePath());
			if(fileName != null && fileName.contains(".")){
				//设置文件类型
				int beginIndex = fileName.lastIndexOf(".")+1;
				int endIndex = fileName.length();
				String fileType = fileName.substring(beginIndex, endIndex);
				uploadFileModel.setFileType(fileType);
				//设置文件相对路径
				String sysUrl = dom4jConfig.getDeDevConfig().getSysUrl();
				if(sysUrl != null && !sysUrl.isEmpty()){
					requestUrl = sysUrl;
				}else{
    				if(requestUrl != null && requestUrl.contains("/")){
    					requestUrl = requestUrl.substring(0,requestUrl.indexOf("/", 8)+1);
    				}
				}
				String relativePath = requestUrl+DefaultArgument.UPLOAD_FOLDER+"/"+fileName;
				uploadFileModel.setFileRelativePath(relativePath);
			}
    	}
    	return uploadFileModel;
    }

}
