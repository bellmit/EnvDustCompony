package com.tcb.env.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.AuthorityDetail;
import com.tcb.env.service.IAuthorityDetailService;
import com.tcb.env.service.IUserAhrService;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：AuthorityDetail控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日下午2:39:53
 * @since EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/AuthorityDetailController")
public class AuthorityDetailController {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "AuthorityDetailController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(AuthorityDetailController.class);

	/**
	 * 声明明细服务类
	 */
	@Resource
	private IAuthorityDetailService authorityDetailService;

	/**
	 * 声明权限组用户服务类
	 */
	@Resource
	private IUserAhrService userAhrService;

	/**
	 * 声明gson引用
	 */
	private Gson gson = new Gson();

	/**
	 * 
	 * <p>
	 * [功能描述]：获取权限控件
	 * </p>
	 * 
	 * @author 王垒, 2016年4月19日下午1:38:14
	 * @since EnvDust 1.0.0
	 *
	 * @return
	 */
	@RequestMapping(value = "getAuthorityDetail", method = { RequestMethod.POST })
	public @ResponseBody HashMap<String, Boolean> getAuthorityDetail(
			HttpSession httpsession) {
		HashMap<String, Boolean> list = new HashMap<String, Boolean>();
		try {
			UserModel loginuser = (UserModel) httpsession
					.getAttribute(DefaultArgument.LOGIN_USER);
			String ahrCode = userAhrService.getUserAhrCode(loginuser
					.getUserCode());
			if (ahrCode != null && !ahrCode.isEmpty()) {
				List<AuthorityDetail> listDetail = authorityDetailService
						.getAuthorityDetail(ahrCode);
				if (listDetail != null && listDetail.size() > 0) {
					for (AuthorityDetail detail : listDetail) {
						if (detail.getDealType() == DefaultArgument.DEAL_TYPE) {
							list.put(detail.getControlName(), true);
						} else {
							list.put(detail.getControlName(), false);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(LOG + "：查询权限控件错误，信息为：" + e.getMessage());
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * [功能描述]：更新权限组控件
	 * </p>
	 * 
	 * @author 王垒, 2016年4月19日上午10:55:06
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "updateAuthorityDetail", method = { RequestMethod.POST })
	public @ResponseBody ResultModel updateAuthorityDetail(String ahrCode,
			String list, HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		if (ahrCode != null && !ahrCode.isEmpty()) {
			try {
				UserModel loginuser = (UserModel) httpsession
						.getAttribute(DefaultArgument.LOGIN_USER);
				List<TreeModel> listTree = gson.fromJson(list,
						new TypeToken<List<TreeModel>>() {
						}.getType());
				int intresult = authorityDetailService.updateAuthorityDetail(
						ahrCode, listTree, loginuser.getUserId());
				if (intresult > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("更新权限组控件失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新权限组控件失败！");
				logger.error(LOG + "：更新权限组控件失败，信息为：" + e.getMessage());
			}

		} else {
			resultModel.setResult(false);
			resultModel.setDetail("没有可以操作的数据！");
		}
		return resultModel;
	}
}
