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

import com.tcb.env.model.CollectDeviceModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.CollectDevice;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.ICollectDeviceService;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：超标采样设备控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日下午2:09:57
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/CollectDeviceController")
public class CollectDeviceController {
	
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "CollectDeviceController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(CollectDeviceController.class);
	
	/**
	 * 声明采样设备服务
	 */
	@Resource
	private ICollectDeviceService collectDeviceService;
	
	/**
	 * 声明User服务
	 */
	@Resource
	private IUserService userService;
	
	/**
	 * 声明device对象</p>
	 */
	@Resource
	private IDeviceService deviceService;
	
	/**
	 * 
	 * <p>[功能描述]：查询超标采样设备</p>
	 * 
	 * @author	王垒, 2018年7月25日下午2:38:53
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/queryCollectDevice", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<CollectDeviceModel> queryCollectDevice(
			CollectDeviceModel collectDeviceModel, HttpSession httpsession) {
		// 创建类
		ResultListModel<CollectDeviceModel> resultListModel = new ResultListModel<CollectDeviceModel>();
		List<CollectDeviceModel> cdbmList = new ArrayList<CollectDeviceModel>();
		List<CollectDevice> cdbList = new ArrayList<CollectDevice>();
		CollectDevice cdb = ConvertCollectDevice(collectDeviceModel,httpsession);
		int count = collectDeviceService.getCollectDeviceCount(cdb);
		if (count > 0) {
			cdbList = collectDeviceService.getCollectDevice(cdb);
			// 依次转换成model
			for (CollectDevice temp : cdbList) {
				CollectDeviceModel cdbm = ConvertCollectDeviceModel(temp);
				if (cdbm != null) {
					cdbmList.add(cdbm);
				}
			}
			resultListModel.setRows(cdbmList);
		}
		resultListModel.setTotal(count);
		resultListModel.setResult(true);
		return resultListModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：新增采样设备数据</p>
	 * 
	 * @author	王垒, 2018年7月24日下午4:01:27
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/insertCollectDevice", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel insertCollectDevice(
			CollectDeviceModel collectDeviceModel, HttpSession httpsession) {
		// 创建返回result类
		ResultModel resultModel = new ResultModel();
		Device device = new Device();
		if (collectDeviceModel != null) {
			try {
				device.setDeviceCode(collectDeviceModel.getCdCode());
				device.setDeviceMn(collectDeviceModel.getCdMn());
				if (deviceService.getCount(device) == 0) {
					// 创建表
					boolean flag = deviceService.createSampleTable(device);
					// 插入数据
					int result = 0;
					if (flag) {
						CollectDevice cd = ConvertCollectDevice(collectDeviceModel,httpsession);
						result = collectDeviceService.insertCollectDevice(cd);
					}
					if (result > 0) {
						resultModel.setResult(true);
						resultModel.setDetail("新增采样设备成功！");
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增采样设备失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此采样设备编码，请使用其他编码！");
				}
			} catch (Exception e) {
				// 删除表
				try {
					deviceService.dropStorageTable(device);
				} catch (Exception e1) {
					logger.error("删除新建表失败，原因：" + e1.getMessage());
				} finally {
					resultModel.setResult(false);
					resultModel.setDetail("新增采样设备失败!");
					logger.error(LOG + ":" + e.getMessage());
				}
			}
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：更新采样设备</p>
	 * 
	 * @author	王垒, 2018年7月30日下午3:28:30
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/updateCollectDevice", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel updateCollectDevice(CollectDeviceModel collectDeviceModel,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		if (collectDeviceModel != null) {
			try {
				int codeExist = deviceService.getDeviceCodeExist(
						Integer.valueOf(collectDeviceModel.getCdId()), collectDeviceModel.getCdCode());
				int mnExist = deviceService.getDeviceMnExist(
						Integer.valueOf(collectDeviceModel.getCdId()), collectDeviceModel.getCdMn());
				// 如果两个都等于0说明不存在则可以修改
				if (codeExist == 0 && mnExist == 0) {
					CollectDevice collectDevice = ConvertCollectDevice(collectDeviceModel, httpsession);
					int intresult = collectDeviceService.updateCollectDevice(collectDevice);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新采样设备失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此设备编号或MN号，请使用其他设备编号或MN号！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新采样设备失败!");
				logger.error(LOG + ":" + e.getMessage());
			}
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：删除采样设备</p>
	 * 
	 * @author	王垒, 2018年7月30日下午3:35:33
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/deleteCollectDevice", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel deleteCollectDevice(
			@RequestParam(value = "list[]") List<String> list) {
		ResultModel resultModel = new ResultModel();
		if (list != null && list.size() > 0) {
			try {
				List<String> deviceCodeList = new ArrayList<String>();
				for (String id : list) {
					String deviceCode = deviceService.getDeviceCode(id);
					if(deviceCode != null && !deviceCode.isEmpty()){
						deviceCodeList.add(deviceCode);
					}
				}
				int intresult = collectDeviceService.deleteCollectDevice(list);
				if (intresult > 0) {
					for (String deviceCode : deviceCodeList) {
						try {
							deviceService.dropSampleTable(deviceCode);
						} catch (Exception ed) {
							logger.error(LOG + "：删除采样设备表失败，设备表为：" + deviceCode);
							continue;
						}
					}
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除采样设备失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除采样设备失败！");
				logger.error(LOG + ":" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除采样设备失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：CollectDeviceModel转换成CollectDevice</p>
	 * 
	 * @author	王垒, 2018年7月24日下午2:32:41
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceModel
	 * @param httpsession
	 * @return
	 */
	private CollectDevice ConvertCollectDevice(CollectDeviceModel collectDeviceModel,
			HttpSession httpsession) {
		CollectDevice collectDevice = new CollectDevice();
		if (collectDeviceModel != null) {
			if(collectDeviceModel.getCdId() != null && !collectDeviceModel.getCdId().isEmpty()){
				collectDevice.setCdId(Long.valueOf(collectDeviceModel.getCdId()));
			}
			collectDevice.setCdCode(collectDeviceModel.getCdCode());
			collectDevice.setCdMn(collectDeviceModel.getCdMn());
			collectDevice.setCdName(collectDeviceModel.getCdName());
			collectDevice.setCdIp(collectDeviceModel.getCdIp());
			collectDevice.setCdPort(collectDeviceModel.getCdPort());
			collectDevice.setCdPwd(collectDeviceModel.getCdPwd());
			// 获取当前操作者并设置
			UserModel loginUser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginUser != null) {
				collectDevice.setOptUser(loginUser.getUserId());
			}
			collectDevice.setRowCount(collectDeviceModel.getRows());
			collectDevice.setRowIndex((collectDeviceModel.getPage() - 1) * collectDeviceModel.getRows());
		}
		return collectDevice;
	}

	/**
	 * 
	 * <p>[功能描述]：CollectDevice转换成CollectDeviceModel</p>
	 * 
	 * @author	王垒, 2018年7月24日下午3:13:08
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDevice
	 * @return
	 */
	private CollectDeviceModel ConvertCollectDeviceModel(CollectDevice collectDevice) {
		CollectDeviceModel collectDeviceModel = new CollectDeviceModel();
		if (collectDevice != null) {
			collectDeviceModel.setCdId(String.valueOf(collectDevice.getCdId()));
			collectDeviceModel.setCdCode(collectDevice.getCdCode());
			collectDeviceModel.setCdMn(collectDevice.getCdMn());
			collectDeviceModel.setCdName(collectDevice.getCdName());
			collectDeviceModel.setCdIp(collectDevice.getCdIp());
			collectDeviceModel.setCdPort(collectDevice.getCdPort());
			collectDeviceModel.setCdPwd(collectDevice.getCdPwd());
			// 将操作者ID转换成名字字符串
			collectDeviceModel.setOptUserName(userService.getUserNameById(collectDevice.getOptUser(), null));
			collectDeviceModel.setOptTime(DateUtil.TimestampToString(collectDevice.getOptTime(), DateUtil.DATA_TIME));
		}
		return collectDeviceModel;
	}

}
