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

import com.tcb.env.model.CollectDeviceBoxVaseModel;
import com.tcb.env.model.NetSamplePlanModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.model.VaseDataModel;
import com.tcb.env.pojo.CollectDeviceBox;
import com.tcb.env.pojo.CollectDeviceBoxVase;
import com.tcb.env.pojo.CommCn;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommStatus;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.ICollectDeviceBoxVaseService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * 
 * <p>[功能描述]：采样记录控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月26日下午4:00:21
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/CollectDeviceBoxVaseController")
public class CollectDeviceBoxVaseController {
	
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "CollectDeviceBoxVaseController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(CollectDeviceBoxVaseController.class);
			
	/**
	 * 声明采集箱服务
	 */
	@Resource
	private ICollectDeviceBoxVaseService collectDeviceBoxVaseService;
	
	/**
	 * 声明User服务
	 */
	@Resource
	private IUserService userService;
	
	/**
	 * 
	 * <p>[功能描述]：查询采样记录数据</p>
	 * 
	 * @author	王垒, 2018年7月24日下午3:32:37
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxVaseModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/queryCollectDeviceBoxVase", method = { RequestMethod.POST })
	@ResponseBody
	public ResultListModel<CollectDeviceBoxVaseModel> queryCollectDeviceBoxVase(
			CollectDeviceBoxVaseModel collectDeviceBoxVaseModel, HttpSession httpsession) {
		// 创建类
		ResultListModel<CollectDeviceBoxVaseModel> resultListModel = new ResultListModel<CollectDeviceBoxVaseModel>();
		
		List<CollectDeviceBoxVaseModel> cdbvmList = new ArrayList<CollectDeviceBoxVaseModel>();
		List<CollectDeviceBoxVase> cdbvList = new ArrayList<CollectDeviceBoxVase>();
		CollectDeviceBoxVase cdbv = ConvertCollectDeviceBoxVase(collectDeviceBoxVaseModel,httpsession);
		int count = collectDeviceBoxVaseService.getCollectDeviceBoxVaseCount(cdbv);
		if (count > 0) {
			cdbvList = collectDeviceBoxVaseService.getCollectDeviceBoxVase(cdbv);
			// 依次转换成model
			for (CollectDeviceBoxVase temp : cdbvList) {
				CollectDeviceBoxVaseModel cdbvm = ConvertCollectDeviceBoxVaseModel(temp);
				if (cdbvm != null) {
					cdbvmList.add(cdbvm);
				}
			}
			resultListModel.setRows(cdbvmList);
		}
		resultListModel.setTotal(count);
		resultListModel.setResult(true);
		return resultListModel;
		
	}
	
	/**
	 * 
	 * <p>
	 * [功能描述]：获取实时数据计划
	 * </p>
	 * 
	 * @author 王垒, 2016年6月7日上午8:43:07
	 * @since EnvDust 1.0.0
	 *
	 * @param dataIntervalModel
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/queryNetSample", method = { RequestMethod.POST })
	@ResponseBody
	public ResultListModel<NetSamplePlanModel> queryNetSample(
			NetSamplePlanModel netSamplePlanModel, HttpSession httpsession) {
		ResultListModel<NetSamplePlanModel> resultListModel = new ResultListModel<NetSamplePlanModel>();
		try {
			if (netSamplePlanModel != null) {
				CommMain commMain = ConvertCommMain(netSamplePlanModel, httpsession);
				List<String> listDeviceCode = new ArrayList<String>();
				listDeviceCode.add(commMain.getDevice().getDeviceCode());
				List<String> listCnCode = new ArrayList<String>();
				if (netSamplePlanModel.getCnCode() == null
						|| netSamplePlanModel.getCnCode().isEmpty()) {
					listCnCode.add(DefaultArgument.PRO_NET_SAMPLE);
				} else {
					listCnCode.add(netSamplePlanModel.getCnCode());
				}
				int count = collectDeviceBoxVaseService.getNetSampleCount(commMain, listDeviceCode, listCnCode);
				if (count > 0) {
					List<NetSamplePlanModel> listModel = new ArrayList<NetSamplePlanModel>();
					List<CommMain> list = collectDeviceBoxVaseService.getNetSample(commMain, listDeviceCode, listCnCode);
					for (CommMain commMainTemp : list) {
						NetSamplePlanModel metSamplePlanModelTemp = ConvertNetSamplePlanModel(commMainTemp);
						if (metSamplePlanModelTemp != null) {
							listModel.add(metSamplePlanModelTemp);
						}
					}
					resultListModel.setTotal(count);
					resultListModel.setRows(listModel);
				}
			}
		} catch (Exception e) {
			logger.error(LOG + "：查询采样指令失败，原因：" + e.getMessage());
		}
		return resultListModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：插入采样指令</p>
	 * 
	 * @author	王垒, 2018年7月26日下午5:25:04
	 * @since	EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param cdCode
	 * @param cdMn
	 * @param setExcuteTime
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/insertNetSample", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel insertNetSample(String cdCode,HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		try {
			if (SessionManager.isSessionValidate(httpsession,DefaultArgument.LOGIN_USER)) {
				resultModel.setDetail("登录超时，请重新登陆后操作！");
				return resultModel;
			}
			String cnCode = DefaultArgument.PRO_NET_SAMPLE;
			String setExcuteTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
			if (cnCode != null && !cnCode.isEmpty() && cdCode != null && !cdCode.isEmpty()) {
				int userid;
				UserModel loginUser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
				if (loginUser != null) {
					userid = loginUser.getUserId();
				} else {
					resultModel.setDetail("登录超时，请重新登陆后操作！");
					return resultModel;
				}
				int count = collectDeviceBoxVaseService.insertNetSample(cnCode, cdCode,setExcuteTime, userid);
				if (count > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setDetail("插入采样指令失败！");
					return resultModel;
				}
			} else {
				resultModel.setDetail("无操作数据！");
			}
		} catch (Exception e) {
			logger.error(LOG + "：插入采样指令失败，原因：" + e.getMessage());
			resultModel.setDetail("插入采样指令失败！");
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：</p>
	 * 
	 * @author	王垒, 2018年7月30日上午11:08:33
	 * @since	EnvDust 1.0.0
	 *
	 * @param deviceCode
	 * @param vaseNo
	 * @return
	 */
	@RequestMapping(value = "/queryVaseDataModel", method = { RequestMethod.POST })
	@ResponseBody
	public ResultModel queryVaseDataModel(String deviceCode, String vaseNo) {
		ResultModel resultModel = new ResultModel();
		if(deviceCode != null && !deviceCode.isEmpty() && vaseNo != null && !vaseNo.isEmpty()){
			List<VaseDataModel> vdmList = collectDeviceBoxVaseService.getVaseDataModel(deviceCode, vaseNo);
			String info = "";
			if(vdmList != null && vdmList.size()>0){
				for (VaseDataModel vaseDataModel : vdmList) {
					info += vaseDataModel.getThingName()+":"+vaseDataModel.getThingValue()+"("+vaseDataModel.getThingLimit()+")\n";
					//数据标志
					if("N".equals(vaseDataModel.getThingFlag())){
						info += "正常\n";
					}else if("T".equals(vaseDataModel.getThingFlag())){
						info += "超标\n";
					}
					//采样模式
					if("0".equals(vaseDataModel.getSimpleMode())){
						info += "平台<br/>";
					}else if("1".equals(vaseDataModel.getSimpleMode())){
						info += "手动采样<br/>";
					}else if("2".equals(vaseDataModel.getSimpleMode())){
						info += "在线监测触发<br/>";
					}else if("3".equals(vaseDataModel.getSimpleMode())){
						info += "开关量模拟量触发<br/>";
					}
				}
			}
			if(info != null && !info.isEmpty()){
				resultModel.setDetail(info);
			}else{
				resultModel.setDetail("无数据");
			}
			resultModel.setResult(true);
		}else{
			resultModel.setResult(false);
			resultModel.setDetail("无操作数据！");
		}
		
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：CollectDeviceBoxVaseModel转换成CollectDeviceBoxVase</p>
	 * 
	 * @author	王垒, 2018年7月24日下午2:32:41
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxVaseModel
	 * @param httpsession
	 * @return
	 */
	private CollectDeviceBoxVase ConvertCollectDeviceBoxVase(CollectDeviceBoxVaseModel collectDeviceBoxVaseModel,
			HttpSession httpsession) {
		CollectDeviceBoxVase collectDeviceBoxVase = new CollectDeviceBoxVase();
		if (collectDeviceBoxVaseModel != null) {
			if(collectDeviceBoxVaseModel.getVaseId() != null && !collectDeviceBoxVaseModel.getVaseId().isEmpty()){
				collectDeviceBoxVase.setVaseId(Long.valueOf(collectDeviceBoxVaseModel.getVaseId()));
			}
			collectDeviceBoxVase.setVaseNo(collectDeviceBoxVaseModel.getVaseNo());
			collectDeviceBoxVase.setVaseTime(collectDeviceBoxVaseModel.getVaseTime());
			collectDeviceBoxVase.setVaseQn(collectDeviceBoxVaseModel.getVaseNo());
			CollectDeviceBox collectDeviceBox = new CollectDeviceBox();
			if(collectDeviceBoxVaseModel.getBoxId() != null && !collectDeviceBoxVaseModel.getBoxId().isEmpty()){
				collectDeviceBox.setBoxId(Long.valueOf(collectDeviceBoxVaseModel.getBoxId()));
			}
			collectDeviceBox.setBoxNo(collectDeviceBoxVaseModel.getBoxNo());
			collectDeviceBox.setBoxName(collectDeviceBoxVaseModel.getBoxName());
			collectDeviceBox.setBoxStatus(collectDeviceBoxVaseModel.getBoxStatus());
			collectDeviceBoxVase.setCollectDeviceBox(collectDeviceBox);
			// 获取当前操作者并设置
			UserModel loginUser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginUser != null) {
				collectDeviceBoxVase.setOptUser(loginUser.getUserId());
			}
			collectDeviceBoxVase.setRowCount(collectDeviceBoxVaseModel.getRows());
			collectDeviceBoxVase.setRowIndex((collectDeviceBoxVaseModel.getPage() - 1) * collectDeviceBoxVaseModel.getRows());
		}
		return collectDeviceBoxVase;
	}

	/**
	 * 
	 * <p>[功能描述]：CollectDeviceBoxVase转换成CollectDeviceBoxVaseModel</p>
	 * 
	 * @author	王垒, 2018年7月24日下午3:13:08
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxVase
	 * @return
	 */
	private CollectDeviceBoxVaseModel ConvertCollectDeviceBoxVaseModel(CollectDeviceBoxVase collectDeviceBoxVase) {
		CollectDeviceBoxVaseModel collectDeviceBoxVaseModel = new CollectDeviceBoxVaseModel();
		if (collectDeviceBoxVase != null) {
			collectDeviceBoxVaseModel.setVaseId(String.valueOf(collectDeviceBoxVase.getVaseId()));
			collectDeviceBoxVaseModel.setVaseNo(collectDeviceBoxVase.getVaseNo());
			collectDeviceBoxVaseModel.setVaseTime(collectDeviceBoxVase.getVaseTime());
			collectDeviceBoxVaseModel.setVaseQn(collectDeviceBoxVase.getVaseQn());
			CollectDeviceBox collectDeviceBox = collectDeviceBoxVase.getCollectDeviceBox();
			if(collectDeviceBox != null){
				collectDeviceBoxVaseModel.setBoxId(String.valueOf(collectDeviceBox.getBoxId()));
				collectDeviceBoxVaseModel.setBoxNo(collectDeviceBox.getBoxNo());
				collectDeviceBoxVaseModel.setBoxName(collectDeviceBox.getBoxName());
				collectDeviceBoxVaseModel.setBoxStatus(collectDeviceBox.getBoxStatus());
			}
			// 将操作者ID转换成名字字符串
			collectDeviceBoxVaseModel.setOptUserName(userService.getUserNameById(collectDeviceBoxVase.getOptUser(), null));
			collectDeviceBoxVaseModel.setOptTime(DateUtil.TimestampToString(collectDeviceBoxVase.getOptTime(), DateUtil.DATA_TIME));
		}
		return collectDeviceBoxVaseModel;
	}
	
	/**
	 * 
	 * <p>
	 * [功能描述]：将NetSamplePlanModel转换成CommMain
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:54:40
	 * @since EnvDust 1.0.0
	 *
	 * @param netSamplePlanModel
	 * @param httpsession
	 * @return
	 */
	private CommMain ConvertCommMain(NetSamplePlanModel netSamplePlanModel,
			HttpSession httpsession) {
		CommMain commMain = new CommMain();
		if (netSamplePlanModel != null) {
			CommCn commCn = new CommCn();
			commCn.setCnCode(netSamplePlanModel.getCnCode());
			commMain.setCommCn(commCn);
			if(netSamplePlanModel.getVaseQn() != null && !netSamplePlanModel.getVaseQn().isEmpty()){
				commMain.setQn(netSamplePlanModel.getVaseQn());
			}
			Device device = new Device();
			device.setDeviceCode(netSamplePlanModel.getCdCode());
			device.setDeviceName(netSamplePlanModel.getCdName());
			commMain.setDevice(device);
			commMain.setRowCount(netSamplePlanModel.getRows());
			commMain.setRowIndex((netSamplePlanModel.getPage() - 1) * netSamplePlanModel.getRows());
		}
		return commMain;
	}
	
	/**
	 * 
	 * <p>
	 * [功能描述]：将CommMain转换成netSamplePlanModel
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午10:00:54
	 * @since EnvDust 1.0.0
	 *
	 * @param commMain
	 * @return
	 */
	private NetSamplePlanModel ConvertNetSamplePlanModel(CommMain commMain) {
		NetSamplePlanModel netSamplePlanModel = new NetSamplePlanModel();
		if (commMain != null) {
			netSamplePlanModel.setCommId(String.valueOf(commMain.getCommId()));
			Device device = commMain.getDevice();
			if (device != null) {
				netSamplePlanModel.setCdCode(device.getDeviceCode());
				netSamplePlanModel.setCdMn(device.getDeviceMn());
				netSamplePlanModel.setCdName(device.getDeviceName());
			}
			CommCn commCn = commMain.getCommCn();
			if (commCn != null) {
				netSamplePlanModel.setCnCode(commCn.getCnCode());
				netSamplePlanModel.setCnName(commCn.getCnName());
			}
			CommStatus commStatus = commMain.getCommStatus();
			if (commStatus != null) {
				netSamplePlanModel.setStatusName(commStatus.getStatusName());
			}
			netSamplePlanModel.setVaseQn(commMain.getQn());
			netSamplePlanModel.setExcuteTime(DateUtil.TimestampToString(
					commMain.getExcuteTime(), DateUtil.DATA_TIME));
			netSamplePlanModel.setOptUserName(userService.getUserNameById(commMain.getOptUser(), null));
			netSamplePlanModel.setOptTime(DateUtil.TimestampToString(commMain.getOptTime(), DateUtil.DATA_TIME));
		}
		return netSamplePlanModel;
	}
	

}
