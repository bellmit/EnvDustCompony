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
import com.tcb.env.model.MonitorModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Monitor;
import com.tcb.env.service.IMonitorService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>
 * [功能描述]：监控物控制
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 * 
 * @author 任崇彬
 * @version 1.0, 2016年3月30日下午2:09:51
 * @since EnvDust 1.0.0
 * 
 */
@Controller
@RequestMapping("/MonitorController")
public class MonitorController {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "MonitorController";

	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(MonitorController.class);
	/**
	 * 声明gson对象
	 */
	Gson gson = new Gson();
	/**
	 * 声明User服务
	 */
	@Resource
	private IUserService userService;
	/**
	 * 声明device对象</p>
	 */
	@Resource
	private IMonitorService monitorService;

	/**
	 * <p>
	 * [功能描述]：查询方法
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月30日下午3:18:01
	 * @since EnvDust 1.0.0
	 *
	 * @param monitorModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/queryMonitor", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<MonitorModel> queryMonitor(
			MonitorModel monitorModel, HttpSession httpsession) {
		// 创建类
		ResultListModel<MonitorModel> resultListModel = new ResultListModel<MonitorModel>();
		List<MonitorModel> listMonitorModel = new ArrayList<MonitorModel>();
		List<Monitor> listMonitor = new ArrayList<Monitor>();
		// 转换成组织
		Monitor monitor = ConvertMonitor(monitorModel, httpsession);
		int count = monitorService.getCount(monitor);
		if (count > 0) {
			// 查询数据库得到所有组织
			listMonitor = monitorService.getMonitor(monitor);
			// 依次转换成model
			for (Monitor temp : listMonitor) {
				MonitorModel monitorModelt = ConvertMonitorModel(temp);
				if (monitorModelt != null) {
					listMonitorModel.add(monitorModelt);
				}
			}
			resultListModel.setRows(listMonitorModel);
		}
		resultListModel.setTotal(count);
		return resultListModel;
	}

	/**
	 * <p>
	 * [功能描述]：新增数据
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月30日下午3:19:18
	 * @since EnvDust 1.0.0
	 *
	 * @param monitorModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/insertMonitor", method = { RequestMethod.POST })
	public @ResponseBody ResultModel insertMonitor(MonitorModel monitorModel,
			HttpSession httpsession) {
		// 创建返回result类
		ResultModel resultModel = new ResultModel();
		// 如果传入了类正常
		if (monitorModel != null) {
			try {
				// 看要添加组织的名称
				Monitor monitor = new Monitor();
				monitor.setThingCode(monitorModel.getThingCode());
				// 如果新增的组织名称在数据库中不存在，则返回0.则添加
				if (monitorService.getCount(monitor) == 0
						&& monitorService.getMonitorExist(
								monitorModel.getThingId(), null,
								monitorModel.getThingName()) == 0) {
					Monitor monitorInsert = ConvertMonitor(monitorModel, httpsession);
					// 插入组织数据，如果成功则返回添加个数1
					int intresult = monitorService.insertMonitor(monitorInsert);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增监测物失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此监测物编号或名称，请使用其他监测物编号或名称！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("新增监测物失败！");
				logger.error(LOG + "：新增监测物失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}

	/**
	 * <p>
	 * [功能描述]：删除数据
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月30日下午3:58:02
	 * @since EnvDust 1.0.0
	 *
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/deleteMonitor", method = { RequestMethod.POST })
	public @ResponseBody ResultModel deleteMonitor(
			@RequestParam(value = "list[]") List<Integer> list) {
		ResultModel resultModel = new ResultModel();
		// 传入list表示要删除多少条数据
		// 如果条数大于0
		if (list != null && list.size() > 0) {
			try {
				List<Integer> monitorid = new ArrayList<Integer>();
				for (Integer userid : list) {
					monitorid.add(userid);
				}
				// 删除oranization数据
				int intresult = monitorService.deleteMonitor(monitorid);
				// 如果返回值大于0则删除用户成功
				if (intresult > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除监测物失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除监测物失败！");
				logger.error(LOG + "：删除监测物失败，信息为：" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除监测物失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
	}

	@RequestMapping(value = "/updatetMonitor", method = { RequestMethod.POST })
	public @ResponseBody ResultModel updatetMonitor(MonitorModel monitorModel,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		// 如果不为空
		if (monitorModel != null) {
			try {
				if (monitorService.getMonitorExist(monitorModel.getThingId(),
						monitorModel.getThingCode(), null) == 0
						&& monitorService.getMonitorExist(
								monitorModel.getThingId(), null,
								monitorModel.getThingName()) == 0) {
					List<Monitor> listmonitor = new ArrayList<Monitor>();
					listmonitor.add(ConvertMonitor(monitorModel, httpsession));
					int intresult = monitorService.updateMonitor(listmonitor);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新监测物用户失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此监测物编号或名称，请使用其他监测物编号或名称！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新监测物数据失败！");
				logger.error(LOG + "：更新监测物数据失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}

	/**
	 * <p>
	 * [功能描述]：model转换device
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月30日下午2:11:55
	 * @since EnvDust 1.0.0
	 *
	 * @param monitorModel
	 * @param httpsession
	 * @return
	 */
	private Monitor ConvertMonitor(MonitorModel monitorModel,
			HttpSession httpsession) {
		Monitor monitor = new Monitor();
		if (monitorModel != null) {
			monitor.setThingId(monitorModel.getThingId());
			monitor.setThingCode(monitorModel.getThingCode());
			monitor.setThingName(monitorModel.getThingName());
			monitor.setThingUnit(monitorModel.getThingUnit());
			if(monitorModel.getThingOrder() != null){
				monitor.setThingOrder(Integer.valueOf(monitorModel.getThingOrder()));
			}

			// 获取操作者
			UserModel loginuser = (UserModel) httpsession
					.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginuser != null) {
				monitor.setOptUser(loginuser.getUserId());
			} else {
				monitor.setOptUser(monitorModel.getThingId());
			}
			monitor.setRowCount(monitorModel.getRows());
			monitor.setRowIndex((monitorModel.getPage() - 1)
					* monitorModel.getRows());
		}
		return monitor;
	}

	/**
	 * <p>
	 * [功能描述]：monitor转换成model
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月30日下午2:34:24
	 * @since EnvDust 1.0.0
	 *
	 * @param monitor
	 * @return
	 */
	private MonitorModel ConvertMonitorModel(Monitor monitor) {
		MonitorModel monitorModel = new MonitorModel();
		if (monitor != null) {
			monitorModel.setThingId(monitor.getThingId());
			monitorModel.setThingCode(monitor.getThingCode());
			monitorModel.setThingName(monitor.getThingName());
			monitorModel.setThingUnit(monitor.getThingUnit());
			monitorModel.setThingOrder(String.valueOf(monitor.getThingOrder()));
			// 操作者
			monitorModel.setOptUserName(userService.getUserNameById(
					monitor.getOptUser(), null));
			monitorModel.setOptTime(DateUtil.TimestampToString(
					monitor.getOptTime(), DateUtil.DATA_TIME));
		}
		return monitorModel;
	}
}
