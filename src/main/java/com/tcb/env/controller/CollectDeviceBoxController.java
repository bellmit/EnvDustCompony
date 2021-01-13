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

import com.tcb.env.model.CollectDeviceBoxModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.CollectDevice;
import com.tcb.env.pojo.CollectDeviceBox;
import com.tcb.env.service.ICollectDeviceBoxService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：采样箱子控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月24日上午11:01:45
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/CollectDeviceBoxController")
public class CollectDeviceBoxController {
	
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "CollectDeviceBoxController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(CollectDeviceBoxController.class);
	
	/**
	 * 声明采集箱服务
	 */
	@Resource
	private ICollectDeviceBoxService collectDeviceBoxService;
	
	/**
	 * 声明User服务
	 */
	@Resource
	private IUserService userService;
	
	/**
	 * 
	 * <p>[功能描述]：查询采集箱数据</p>
	 * 
	 * @author	王垒, 2018年7月24日下午3:32:37
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/queryCollectDeviceBox", method = { RequestMethod.POST })
	@ResponseBody
	public ResultListModel<CollectDeviceBoxModel> queryCollectDeviceBox(
			CollectDeviceBoxModel collectDeviceBoxModel, HttpSession httpsession) {
		// 创建类
		ResultListModel<CollectDeviceBoxModel> resultListModel = new ResultListModel<CollectDeviceBoxModel>();
		
		List<CollectDeviceBoxModel> cdbmList = new ArrayList<CollectDeviceBoxModel>();
		List<CollectDeviceBox> cdbList = new ArrayList<CollectDeviceBox>();
		CollectDeviceBox cdb = ConvertCollectDeviceBox(collectDeviceBoxModel,httpsession);
		int count = collectDeviceBoxService.getCollectDeviceBoxCount(cdb);
		if (count > 0) {
			cdbList = collectDeviceBoxService.getCollectDeviceBox(cdb);
			// 依次转换成model
			for (CollectDeviceBox temp : cdbList) {
				CollectDeviceBoxModel cdbm = ConvertCollectDeviceBoxModel(temp);
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
	 * <p>[功能描述]：新增采样箱数据</p>
	 * 
	 * @author	王垒, 2018年7月24日下午4:01:27
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/insertCollectDeviceBox", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel insertCollectDeviceBox(
			CollectDeviceBoxModel collectDeviceBoxModel, HttpSession httpsession) {
		// 创建返回result类
		ResultModel resultModel = new ResultModel();
		if (collectDeviceBoxModel != null) {
			try {
				CollectDeviceBox cdb = new CollectDeviceBox();
				CollectDevice cd = new CollectDevice();
				cd.setCdId(Long.valueOf(collectDeviceBoxModel.getCdId()));
				cdb.setCollectDevice(cd);
				cdb.setBoxNo(collectDeviceBoxModel.getBoxNo());
				if (collectDeviceBoxService.getCollectDeviceBoxCount(cdb) == 0) {
					cdb = ConvertCollectDeviceBox(collectDeviceBoxModel,httpsession);
					int result = collectDeviceBoxService.insertCollectDeviceBox(cdb);
					if (result > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增采样箱失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此采样箱编码，请使用其他编码！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("新增采样箱失败！");
				logger.error(LOG + "：新增采样箱失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：更新采样箱子信息</p>
	 * 
	 * @author	王垒, 2018年7月30日下午5:37:28
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/updateCollectDeviceBox", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel updateCollectDeviceBox(
			CollectDeviceBoxModel collectDeviceBoxModel, HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		if (collectDeviceBoxModel != null) {
			try {
				if (collectDeviceBoxService.getSampleBoxExist(
						collectDeviceBoxModel.getBoxId(),
						collectDeviceBoxModel.getBoxNo(),
						collectDeviceBoxModel.getCdId()) == 0) {
					CollectDeviceBox collectDeviceBox = ConvertCollectDeviceBox(collectDeviceBoxModel, httpsession);
					int intresult = collectDeviceBoxService.updateCollectDeviceBox(collectDeviceBox);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新采样箱子失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此采样箱子号码，请使用其他号码！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新采样箱子数据失败！");
				logger.error(LOG + "：更新采样箱子数据失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：删除采样箱数据</p>
	 * 
	 * @author	王垒, 2018年7月24日下午4:06:23
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/deleteCollectDeviceBox", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel deleteCollectDeviceBox(
			@RequestParam(value = "list[]") List<String> list) {
		ResultModel resultModel = new ResultModel();
		if (list != null && list.size() > 0) {
			try {
				int result = collectDeviceBoxService.deleteCollectDeviceBox(list);
				// 如果返回值大于0则删除用户成功
				if (result > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除采样箱失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除采样箱失败！");
				logger.error(LOG + "：删除采样箱失败，信息为：" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除采样箱失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：CollectDeviceBoxModel转换成CollectDeviceBox</p>
	 * 
	 * @author	王垒, 2018年7月24日下午2:32:41
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxModel
	 * @param httpsession
	 * @return
	 */
	private CollectDeviceBox ConvertCollectDeviceBox(CollectDeviceBoxModel collectDeviceBoxModel,
			HttpSession httpsession) {
		CollectDeviceBox collectDeviceBox = new CollectDeviceBox();
		if (collectDeviceBoxModel != null) {
			if(collectDeviceBoxModel.getBoxId() != null && !collectDeviceBoxModel.getBoxId().isEmpty()){
				collectDeviceBox.setBoxId(Long.valueOf(collectDeviceBoxModel.getBoxId()));
			}
			collectDeviceBox.setBoxNo(collectDeviceBoxModel.getBoxNo());
			collectDeviceBox.setBoxName(collectDeviceBoxModel.getBoxName());
			collectDeviceBox.setBoxStatus(collectDeviceBoxModel.getBoxStatus());
			CollectDevice collectDevice = new CollectDevice();
			if(collectDeviceBoxModel.getCdId() != null && !collectDeviceBoxModel.getCdId().isEmpty()){
				collectDevice.setCdId(Long.valueOf(collectDeviceBoxModel.getCdId()));
			}
			collectDevice.setCdCode(collectDeviceBoxModel.getCdCode());
			collectDevice.setCdMn(collectDeviceBoxModel.getCdMn());
			collectDevice.setCdName(collectDeviceBoxModel.getCdName());
			collectDeviceBox.setCollectDevice(collectDevice);
			// 获取当前操作者并设置
			UserModel loginUser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginUser != null) {
				collectDeviceBox.setOptUser(loginUser.getUserId());
			}
			collectDeviceBox.setRowCount(collectDeviceBoxModel.getRows());
			collectDeviceBox.setRowIndex((collectDeviceBoxModel.getPage() - 1) * collectDeviceBoxModel.getRows());
		}
		return collectDeviceBox;
	}

	/**
	 * 
	 * <p>[功能描述]：CollectDeviceBox转换成CollectDeviceBoxModel</p>
	 * 
	 * @author	王垒, 2018年7月24日下午3:13:08
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBox
	 * @return
	 */
	private CollectDeviceBoxModel ConvertCollectDeviceBoxModel(CollectDeviceBox collectDeviceBox) {
		CollectDeviceBoxModel collectDeviceBoxModel = new CollectDeviceBoxModel();
		if (collectDeviceBox != null) {
			collectDeviceBoxModel.setBoxId(String.valueOf(collectDeviceBox.getBoxId()));
			collectDeviceBoxModel.setBoxNo(collectDeviceBox.getBoxNo());
			collectDeviceBoxModel.setBoxName(collectDeviceBox.getBoxName());
			collectDeviceBoxModel.setBoxStatus(collectDeviceBox.getBoxStatus());
			if("0".equals(collectDeviceBox.getBoxStatus())){
				collectDeviceBoxModel.setBoxStatusName("就绪");
			}else if("1".equals(collectDeviceBox.getBoxStatus())){
				collectDeviceBoxModel.setBoxStatusName("充满");
			}else if("2".equals(collectDeviceBox.getBoxStatus())){
				collectDeviceBoxModel.setBoxStatusName("故障");
			}else{
				collectDeviceBoxModel.setBoxStatusName("未知");
			}
			CollectDevice collectDevice = collectDeviceBox.getCollectDevice();
			if(collectDevice != null){
				collectDeviceBoxModel.setCdId(String.valueOf(collectDevice.getCdId()));
				collectDeviceBoxModel.setCdCode(collectDevice.getCdCode());
				collectDeviceBoxModel.setCdMn(collectDevice.getCdMn());
				collectDeviceBoxModel.setCdName(collectDevice.getCdName());
			}
			// 将操作者ID转换成名字字符串
			collectDeviceBoxModel.setOptUserName(userService.getUserNameById(collectDeviceBox.getOptUser(), null));
			collectDeviceBoxModel.setOptTime(DateUtil.TimestampToString(collectDeviceBox.getOptTime(), DateUtil.DATA_TIME));
			//在线状态
			if(collectDeviceBox.getOptTime().compareTo(DateUtil.GetSystemDateTime(30*60*1000))<0){
				collectDeviceBoxModel.setNetStatus("失联");
			}else{
				collectDeviceBoxModel.setNetStatus("正常");
			}
		}
		return collectDeviceBoxModel;
	}

}
