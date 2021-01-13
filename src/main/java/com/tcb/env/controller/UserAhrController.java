package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Authority;
import com.tcb.env.pojo.User;
import com.tcb.env.pojo.UserAhr;
import com.tcb.env.service.IUserAhrService;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：用户权限组控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月18日上午10:40:58
 * @since EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/UserAhrController")
public class UserAhrController {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "UserAhrController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(UserAhrController.class);

	/**
	 * 声明权限组用户服务类
	 */
	@Resource
	private IUserAhrService userAhrService;

	/**
	 * 引用user控制器
	 */
	@Resource
	private UserController userController;

	/**
	 * 
	 * <p>
	 * [功能描述]：查询非权限组用户信息
	 * </p>
	 * 
	 * @author 王垒, 2016年3月18日下午1:31:31
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @return
	 */
	@RequestMapping(value = "/queryOtherUserAhrs", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<UserModel> queryOtherUserAhrs(
			String ahrCode, String userName, int rows, int page,
			HttpSession httpsession) {
		ResultListModel<UserModel> resultListModel = new ResultListModel<UserModel>();
		if (ahrCode != null && !ahrCode.isEmpty()) {
			List<UserModel> listusermodel = new ArrayList<UserModel>();
			Authority authority = new Authority();
//			authority.setAuthorityCode(ahrCode);//一个用户只能属于一个	权限组，所以不赋值
			User user = new User();
			user.setUserName(userName);
			UserAhr userAhr = new UserAhr();
			userAhr.setAuthority(authority);
			userAhr.setUser(user);
			userAhr.setRowCount(rows);
			userAhr.setRowIndex((Integer.valueOf(page) - 1)
					* Integer.valueOf(rows));
			int count = userAhrService.getOtherUserAhrCount(userAhr);
			if (count > 0) {
				List<User> listuser = userAhrService.getOtherUserAhr(userAhr);
				for (User temp : listuser) {
					UserModel userModel = userController.ConvertUserModel(temp);
					if (userModel != null) {
						listusermodel.add(userModel);
					}
				}
				resultListModel.setRows(listusermodel);
			}
			resultListModel.setTotal(count);
		} else {
			resultListModel.setTotal(0);
		}
		return resultListModel;
	}

	/**
	 * 
	 * <p>
	 * [功能描述]：插入权限组用户
	 * </p>
	 * 
	 * @author 王垒, 2016年4月18日上午10:50:55
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param list
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/insertUserAhrs", method = { RequestMethod.POST })
	public @ResponseBody ResultModel insertUserAhrs(String ahrCode,
			@RequestParam(value = "list[]") List<String> list,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		String returnString = "";
		if (ahrCode != null && !ahrCode.isEmpty() && list != null
				&& list.size() > 0) {
			try {
				for (String temp : list) {
					int result = userAhrService.getUserAhrCount(temp);
					if (result > 0) {
						returnString += temp + ",";
					}
				}
				if (returnString != null && !returnString.isEmpty()) {
					resultModel.setResult(false);
					resultModel.setDetail("已存在权限组用户："
							+ returnString.substring(0,
									returnString.length() - 1));
				} else {
					UserModel loginuser = (UserModel) httpsession
							.getAttribute(DefaultArgument.LOGIN_USER);
					List<UserAhr> listUserAhr = new ArrayList<UserAhr>();
					for (String userCode : list) {
						Authority authority = new Authority();
						authority.setAuthorityCode(ahrCode);
						User user = new User();
						user.setUserCode(userCode);
						UserAhr userAhr = new UserAhr();
						userAhr.setAuthority(authority);
						userAhr.setUser(user);
						if (loginuser != null) {
							userAhr.setOptUser(loginuser.getUserId());
						}
						listUserAhr.add(userAhr);
					}
					int intresult = userAhrService.insertUserAhr(listUserAhr);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增权限组用户失败！");
					}
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("新增权限组用户失败！");
				logger.error(LOG + "：新增权限组用户失败，信息为：" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("没有可以操作的数据！");
		}
		return resultModel;
	}

	/**
	 * 
	 * <p>
	 * [功能描述]：删除权限组用户
	 * </p>
	 * 
	 * @author 王垒, 2016年4月18日上午11:12:27
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param list
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/deleteUserAhrs", method = { RequestMethod.POST })
	public @ResponseBody ResultModel deleteUserAhrs(String ahrCode,
			@RequestParam(value = "list[]") List<String> list,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		if (list != null && list.size() > 0) {
			try {
				List<String> listUserCode = new ArrayList<String>();
				for (String userCode : list) {
					listUserCode.add(userCode);
				}
				int intresult = userAhrService.deleteUserAhr(ahrCode,
						listUserCode);
				if (intresult > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除权限组用户失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除权限组用户失败！");
				logger.error(LOG + "：删除权限组用户失败，信息为：" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("没有可以操作的数据！");
		}
		return resultModel;
	}

}
