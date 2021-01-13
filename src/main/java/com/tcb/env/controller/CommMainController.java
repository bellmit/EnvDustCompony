package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.CommResultModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.service.ICommMainService;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * 
 * <p>[功能描述]：任务计划控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月17日下午12:37:20
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/CommMainController")
public class CommMainController {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "CommMainController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(CommMainController.class);
	
	/**
	 * 声明计划任务服务
	 */
	@Resource
	private ICommMainService commMainService;
	
	/**
	 * 
	 * <p>[功能描述]：查询任务计划结果</p>
	 * 
	 * @author	王垒, 2018年1月17日下午1:39:57
	 * @since	EnvDust 1.0.0
	 *
	 * @param commId
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/getCommResult", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<CommResultModel> getCommResult(String commId, HttpSession httpsession) {
		ResultListModel<CommResultModel> resultListModel = new ResultListModel<CommResultModel>();
		try {
			if (SessionManager.isSessionValidate(httpsession,DefaultArgument.LOGIN_USER)) {
				return resultListModel;
			}
			if (commId != null && !commId.isEmpty()) {
				int count = commMainService.getCommResultCount(commId);
				if(count > 0){
					List<CommResultModel> listModel = new ArrayList<CommResultModel>();
					listModel = commMainService.getCommResult(commId);
					resultListModel.setTotal(count);
					resultListModel.setRows(listModel);
					resultListModel.setResult(true);
					resultListModel.setDetail("查询任务计划结果成功！");
				}else{
					resultListModel.setResult(false);
					resultListModel.setDetail("无任务计划结果！");
				}
			}
		} catch (Exception e) {
			logger.error(LOG + "：查询任务计划结果失败，原因：" + e.getMessage());
		}
		return resultListModel;
	}
	
	
}
