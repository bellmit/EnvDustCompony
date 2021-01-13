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
import com.tcb.env.model.ManufacturerModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Manufacturer;
import com.tcb.env.service.IManufacturerService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月24日上午9:50:27
 * @since	EnvDust 1.0.0
 * 
 */
@Controller
@RequestMapping("/ManufacturerController")
public class ManufacturerController {
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "ManufacturerController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(ManufacturerController.class);

	/**
	 * 声明gson对象
	 */
	Gson gson = new Gson();
	/**
	 * 声明User服务
	 */
	@Resource
	private IUserService userService;
	@Resource
	private IManufacturerService manufacturerService;
	/**
	 * <p>[功能描述]：查询manufa数据</p>
	 * 
	 * @author	任崇彬, 2016年3月24日上午11:02:46
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranizationModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/queryManufacturer", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<ManufacturerModel> queryManufacturer(
			ManufacturerModel manufacturerModel, HttpSession httpsession) {
		//创建类
		ResultListModel<ManufacturerModel> resultListModel = new ResultListModel<ManufacturerModel>();
		List<ManufacturerModel> listManufacturerModel = new ArrayList<ManufacturerModel>();
		List<Manufacturer> listManufacturer = new ArrayList<Manufacturer>();
		//转换成组织
		Manufacturer manufacturer = ConvertManufacturer(manufacturerModel,httpsession);
		int count = manufacturerService.getCount(manufacturer, true);
		if (count > 0) {
			//查询数据库得到所有组织
			listManufacturer = manufacturerService.getManufacturer(manufacturer);
			//依次转换成model
			for (Manufacturer temp : listManufacturer) {
				ManufacturerModel manufacturerModelt = ConvertManufacturerModel(temp);
				if (manufacturerModelt != null) {
					listManufacturerModel.add(manufacturerModelt);					
				}
			}
			resultListModel.setRows(listManufacturerModel);
		}
		resultListModel.setTotal(count);
		return resultListModel;
	}
	/**
	 * <p>[功能描述]：新增厂商信息</p>
	 * 
	 * @author	任崇彬, 2016年3月24日上午11:57:00
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranizationModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/insertManufacturer", method = { RequestMethod.POST })
	public @ResponseBody ResultModel insertManufacturer(ManufacturerModel manufacturerModel,
			HttpSession httpsession) {
		//创建返回result类
		ResultModel resultModel = new ResultModel();
		//如果传入了类正常
		if (manufacturerModel != null) {
			try {
				//看要添加组织的名称
				Manufacturer manufacturer = new Manufacturer();
				manufacturer.setMfrCode(manufacturerModel.getMfrCode());
				//如果新增的组织名称在数据库中不存在，则返回0.则添加
				if (manufacturerService.getCount(manufacturer, false) == 0) {
					List<Manufacturer> listmanufacturer = new ArrayList<Manufacturer>();
					listmanufacturer.add(ConvertManufacturer(manufacturerModel, httpsession));		
					//插入组织数据，如果成功则返回添加个数1
					int intresult = manufacturerService.insertManufacturer(listmanufacturer);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增厂商失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此厂商编号，请使用其他厂商编号！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("新增厂商失败！");
				logger.error(LOG + "：新增厂商失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：删除厂商信息</p>
	 * 
	 * @author	任崇彬, 2016年3月24日下午2:16:43
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return 
	 */
	@RequestMapping(value = "/deleteManufacturer", method = { RequestMethod.POST })
	public @ResponseBody ResultModel deleteManufacturer(
			@RequestParam(value = "list[]") List<Integer> list) {
		ResultModel resultModel = new ResultModel();
		//传入list表示要删除多少条数据
		//如果条数大于0
		if (list != null && list.size() > 0) {
			try {
				List<Integer> manufacturerid = new ArrayList<Integer>();
				//list存的是整数类型，所以userid为局部变量用来表示每个整数类型。
				//用For循环来把list依次放入oranizationid中
				for (Integer userid : list) {
					manufacturerid.add(userid);
				}
				//删除oranization数据
				int intresult = manufacturerService.deleteManufacturer(manufacturerid);
				//如果返回值大于0则删除用户成功
				if (intresult > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除厂商用户失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除厂商失败！");
				logger.error(LOG + "：删除厂商失败，信息为：" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除厂商失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：更新厂商信息</p>
	 * 
	 * @author	任崇彬, 2016年3月24日下午3:43:21
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/updatetManufacturer", method = { RequestMethod.POST })
	public @ResponseBody ResultModel updatetManufacturer(ManufacturerModel manufacturerModel,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		//如果不为空
		if (manufacturerModel != null) {
			try {
				if (manufacturerService.getManufacturerExist(manufacturerModel.getMfrId(),
						manufacturerModel.getMfrCode()) == 0) {
					List<Manufacturer> listmanufacturer = new ArrayList<Manufacturer>();
					listmanufacturer.add(ConvertManufacturer(manufacturerModel, httpsession));
					int intresult = manufacturerService.updateManufacturer(listmanufacturer);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新厂商用户失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此厂商编号，请使用其他厂商编号！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新厂商数据失败！");
				logger.error(LOG + "：更新厂商数据失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}
	/**
	
 * <p>[功能描述]：model转成Manufacture</p>
	 * 
	 * @author	任崇彬, 2016年3月24日上午10:36:15
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerModel
	 * @param httpsession
	 * @return 
	 */
	private Manufacturer ConvertManufacturer(ManufacturerModel manufacturerModel, HttpSession httpsession){
		Manufacturer manufacturer = new Manufacturer();
		if (manufacturerModel != null) {
			manufacturer.setMfrId(manufacturerModel.getMfrId());
			manufacturer.setMfrCode(manufacturerModel.getMfrCode());
			manufacturer.setMfrName(manufacturerModel.getMfrName());
			manufacturer.setMfrAddress(manufacturerModel.getMfrAddress());
			manufacturer.setMfrRemark(manufacturerModel.getMfrRemark());					
	//获取当前操作者并设置
			UserModel loginuser = (UserModel) httpsession
					.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginuser != null) {
				manufacturer.setOptUser(loginuser.getUserId());
			} else {
				manufacturer.setOptUser(manufacturerModel.getMfrId());
			}
			//操作时间穿默认值就可以了
			manufacturer.setRowCount(manufacturerModel.getRows());
			manufacturer.setRowIndex((manufacturerModel.getPage()-1) * manufacturerModel.getRows());
		}
		return manufacturer;
	}
	/**
	 * <p>[功能描述]：manufacture转换成model</p>
	 * 
	 * @author	任崇彬, 2016年3月22日上午10:37:18
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranization
	 * @return 
	 */
	private ManufacturerModel ConvertManufacturerModel(Manufacturer manufacturer){
		ManufacturerModel manufacturerModel = new ManufacturerModel();
		if(manufacturer != null){
			manufacturerModel.setMfrId(manufacturer.getMfrId());
			manufacturerModel.setMfrCode(manufacturer.getMfrCode());
			manufacturerModel.setMfrName(manufacturer.getMfrName());
			manufacturerModel.setMfrAddress(manufacturer.getMfrAddress());
			manufacturerModel.setMfrRemark(manufacturer.getMfrRemark());
			manufacturerModel.setOptUser(manufacturer.getOptUser());
			manufacturerModel.setOptUserName(userService.getUserNameById(manufacturer.getOptUser(),null));
			manufacturerModel.setOptTime(DateUtil.TimestampToString(manufacturer.getOptTime(),
					DateUtil.DATA_TIME));
		}
		return manufacturerModel;
	}


}
