/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
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

import com.google.gson.Gson;
import com.tcb.env.model.OranizationModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Oranization;
import com.tcb.env.service.IOranizationService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>
 * [功能描述]：处理前台传过来的数据控制器
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 * 
 * @author 任崇彬
 * @version 1.0, 2016年3月22日上午9:23:19
 * @since EnvDust 1.0.0
 * 
 */
@Controller
@RequestMapping("/OranizationController")
public class OranizationController {
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "OranizationController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger
			.getLogger(OranizationController.class);

	/**
	 * 声明gson对象
	 */
	Gson gson = new Gson();
	/**
	 * 声明Oranization服务
	 */
	@Resource
	private IOranizationService oranizationService;
	/**
	 * 声明User服务
	 */
	@Resource
	private IUserService userService;

	/**
	 * <p>
	 * [功能描述]：查询Oranization数据
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月22日上午10:42:05
	 * @since EnvDust 1.0.0
	 *
	 * @param usermodel
	 * @return
	 */
	@RequestMapping(value = "/queryOranization", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<OranizationModel> queryOranization(
			OranizationModel oranizationModel, HttpSession httpsession) {
		// 创建类
		ResultListModel<OranizationModel> resultListModel = new ResultListModel<OranizationModel>();
		List<OranizationModel> listoranizationModel = new ArrayList<OranizationModel>();
		List<Oranization> listoranization = new ArrayList<Oranization>();
		// 转换成组织
		Oranization oranization = ConvertOranization(oranizationModel,
				httpsession);
		int count = oranizationService.getCount(oranization, true);
		if (count > 0) {
			// 查询数据库得到所有组织
			listoranization = oranizationService.getOranization(oranization);
			// 依次转换成model
			for (Oranization temp : listoranization) {
				OranizationModel oranizationModelt = ConvertOranizationModel(temp);
				if (oranizationModelt != null) {
					listoranizationModel.add(oranizationModelt);
				}
			}
			resultListModel.setRows(listoranizationModel);
		}
		resultListModel.setTotal(count);
		return resultListModel;
	}

	/**
	 * <p>
	 * [功能描述]：新增组织数据
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月22日下午2:20:16
	 * @since EnvDust 1.0.0
	 *
	 * @param usermodel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/insertOranization", method = { RequestMethod.POST })
	public @ResponseBody ResultModel insertOranization(
			OranizationModel oranizationModel, HttpSession httpsession) {
		// 创建返回result类
		ResultModel resultModel = new ResultModel();
		// 如果传入了类正常
		if (oranizationModel != null) {
			try {
				// 看要添加组织的名称
				Oranization oranization = new Oranization();
				oranization.setOrgName(oranizationModel.getOrgName());
				// 如果新增的组织名称在数据库中不存在，则返回0.则添加
				if (oranizationService.getCount(oranization, false) == 0) {
					Oranization insertOranization = new Oranization();
					insertOranization = ConvertOranization(oranizationModel,
							httpsession);

					// 插入组织数据，如果成功则返回添加个数1
					int intresult = oranizationService
							.insertOranization(insertOranization);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增组织失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此组织名称，请使用其他组织名称！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("新增组织失败！");
				logger.error(LOG + "：新增组织失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}

	/**
	 * <p>
	 * [功能描述]：删除组织数据
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月22日下午2:49:26
	 * @since EnvDust 1.0.0
	 *
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/deleteOranization", method = { RequestMethod.POST })
	public @ResponseBody ResultModel deleteOranization(
			@RequestParam(value = "list[]") List<Integer> list) {
		ResultModel resultModel = new ResultModel();
		// 传入list表示要删除多少条数据
		// 如果条数大于0
		if (list != null && list.size() > 0) {
			try {
				List<Integer> oranizationid = new ArrayList<Integer>();
				// list存的是整数类型，所以userid为局部变量用来表示每个整数类型。
				// 用For循环来把list依次放入oranizationid中
				for (Integer userid : list) {
					oranizationid.add(userid);
				}
				// 删除oranization数据
				int intresult = oranizationService
						.deleteOranization(oranizationid);
				// 如果返回值大于0则删除用户成功
				if (intresult > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除组织用户失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除组织失败！");
				logger.error(LOG + "：删除组织失败，信息为：" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除组织失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
	}

	/**
	 * <p>
	 * [功能描述]：更改组织数据
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月22日下午3:44:16
	 * @since EnvDust 1.0.0
	 *
	 * @param usermodel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/updatetOranization", method = { RequestMethod.POST })
	public @ResponseBody ResultModel updatetOranization(
			OranizationModel oranizationModel, HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		// 如果不为空
		if (oranizationModel != null) {
			try {
				if (oranizationService.getOranizationExist(
						oranizationModel.getOrgId(),
						oranizationModel.getOrgName()) == 0) {
					List<Oranization> listoranization = new ArrayList<Oranization>();
					listoranization.add(ConvertOranization(oranizationModel,
							httpsession));
					int intresult = oranizationService
							.updateOranization(listoranization);

					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新组织用户失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此组织名称，请使用其他组织名称！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新组织数据失败！");
				logger.error(LOG + "：更新组织数据失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}

	/**
	 * <p>
	 * [功能描述]：orgnizationmodel转换成orgnization 接收页面传来的数据准备存入数据库
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月22日上午9:45:28
	 * @since EnvDust 1.0.0
	 *
	 * @param oranizationModel
	 * @return
	 */
	private Oranization ConvertOranization(OranizationModel oranizationModel,
			HttpSession httpsession) {
		Oranization oranization = new Oranization();
		if (oranizationModel != null) {
			oranization.setOrgId(oranizationModel.getOrgId());
			if (DefaultArgument.NONE_DEFAULT.equals(String
					.valueOf(oranizationModel.getOrgIdParent()))) {
				oranization.setOrgIdParent(DefaultArgument.INT_DEFAULT);
			} else {
				oranization.setOrgIdParent(oranizationModel.getOrgIdParent());
			}
			oranization.setOrgName(oranizationModel.getOrgName());
			oranization.setLevelType(oranizationModel.getLevelType());
			oranization.setOrgAddress(oranizationModel.getOrgAddress());
			// 设置为父类id对应路径名称加上组织名称
			oranization.setOrgPath(showOrgPath(oranizationService
					.getOranizationPathById(oranizationModel.getOrgIdParent()))
					+ oranizationModel.getOrgName());
			oranization.setOrgTelephone(oranizationModel.getOrgTelephone());
			oranization.setOrgFax(oranizationModel.getOrgFax());
			oranization.setOrgLiaison(oranizationModel.getOrgLiaison());

			// 获取当前操作者并设置
			UserModel loginuser = (UserModel) httpsession
					.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginuser != null) {
				oranization.setOptUser(loginuser.getUserId());
			} else {
				oranization.setOptUser(oranizationModel.getOrgId());
			}

			oranization.setRowCount(oranizationModel.getRows());
			oranization.setRowIndex((oranizationModel.getPage() - 1)
					* oranizationModel.getRows());
		}
		return oranization;
	}

	/**
	 * <p>
	 * [功能描述]：orgnization转换成orgnizationmodel
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月22日上午10:37:18
	 * @since EnvDust 1.0.0
	 *
	 * @param oranization
	 * @return
	 */
	private OranizationModel ConvertOranizationModel(Oranization oranization) {
		OranizationModel oranizationModel = new OranizationModel();
		if (oranization != null) {
			oranizationModel.setOrgId(oranization.getOrgId());
			if (DefaultArgument.INT_DEFAULT == oranization.getOrgIdParent()) {
				oranizationModel.setOrgIdParent(Integer
						.valueOf(DefaultArgument.NONE_DEFAULT));
			} else {
				oranizationModel.setOrgIdParent(oranization.getOrgIdParent());
			}
			oranizationModel.setOrgName(oranization.getOrgName());
			oranizationModel.setLevelType(oranization.getLevelType());
			oranizationModel.setOrgAddress(oranization.getOrgAddress());
			oranizationModel.setOrgPath(oranization.getOrgPath());
			oranizationModel.setOrgTelephone(oranization.getOrgTelephone());
			oranizationModel.setOrgFax(oranization.getOrgFax());
			oranizationModel.setOrgLiaison(oranization.getOrgLiaison());
			oranizationModel.setOptUser(oranization.getOptUser());
			// 将操作者ID转换成名字字符串
			oranizationModel.setOptUserName(userService.getUserNameById(
					oranization.getOptUser(), null));
			oranizationModel.setOptTime(DateUtil.TimestampToString(
					oranization.getOptTime(), DateUtil.DATA_TIME));
		}
		return oranizationModel;
	}

	/**
	 * <p>
	 * [功能描述]：显示怎么显示Path方法
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月25日上午10:44:32
	 * @since EnvDust 1.0.0
	 *
	 * @return
	 */
	private String showOrgPath(String str) {
		String show;
		if (str == null) {
			show = "";
		} else
			show = str + "/";
		return show;
	}

}
