package com.tcb.env.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.MapAreaModel;
import com.tcb.env.model.MapAreaPointModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.MapArea;
import com.tcb.env.pojo.MapAreaPoint;
import com.tcb.env.service.IMapAreaService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：百度地图覆盖区域确定Controller</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王坤
 * @version	1.0, 2018年8月31日 上午8:57:56
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/MapAreaController")
public class MapAreaController {
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "MapAreaController";

	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(MapAreaController.class);
	
	/**
	 * 声明MapAreaService
	 */
	@Resource
	private IMapAreaService mapAreaService;
	
	/**
	 * 
	 * <p>[功能描述]：获取所有覆盖区域</p>
	 * 
	 * @author	王坤, 2018年8月31日 上午 9:01:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/getMapArea")
	@ResponseBody
	public List<MapAreaModel> getMapArea(MapAreaModel target,HttpSession httpSession){
		List<MapArea> mapAreaList = null;
		List<MapAreaModel> mapAreaModelList = null;
		try{
			
			mapAreaList = this.mapAreaService.getMapArea((target != null)?ConvertMapArea(target, httpSession): null);
			if(mapAreaList != null && mapAreaList.size() > 0){
				mapAreaModelList = new ArrayList<MapAreaModel>(mapAreaList.size());
				for(MapArea mapArea:mapAreaList){
					MapAreaModel mapAreaModel = ConvertMapAreaModel(mapArea);
					mapAreaModelList.add(mapAreaModel);
				}
			}
		}catch(Exception e){
			logger.error(LOG + "覆盖区域获取异常警报 :" + e.getMessage());
		}
		return mapAreaModelList;
	}
	
	/**
	 * 
	 * <p>[功能描述]：获取特定覆盖区域的坐标点信息</p>
	 * 
	 * @author	王坤, 2018年8月31日 上午 9:34:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/getMapAreaPoint")
	@ResponseBody
	public ResultListModel<MapAreaPointModel> getMapAreaPoint(MapAreaModel mapAreaModel,HttpSession httpSession){
		List<MapAreaPointModel> mapAreaPointList = null;
		ResultListModel<MapAreaPointModel> resultListModel = new ResultListModel<MapAreaPointModel>();
		int count = 0;
		try{
			if(mapAreaModel != null){
				count = this.mapAreaService.getMapAreaPointCount(Integer.valueOf(mapAreaModel.getMaId()));
			}
			if(count != 0){
				List<MapAreaPoint> mapAraePointList = this.mapAreaService.getMapAreaPoint(ConvertMapArea(mapAreaModel,httpSession));
				if(mapAraePointList.size() != 0){
					mapAreaPointList = new ArrayList<MapAreaPointModel>();
					for(MapAreaPoint mapAreaPoint:mapAraePointList){
						MapAreaPointModel mapAreaPointModel = ConvertMapAreaPointModel(mapAreaPoint);
						mapAreaPointList.add(mapAreaPointModel);
					}
				}
			}
			resultListModel.setTotal(count);
			resultListModel.setRows(mapAreaPointList);
		}catch(Exception e){
			logger.error(LOG + "特定覆盖区域点信息查询异常警报 :" + e.getMessage());
		}
		return resultListModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：获取所有覆盖区域的坐标点信息</p>
	 * 
	 * @author	王坤, 2018年9月5日 上午 11:23:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/getAllPoints")
	@ResponseBody
	public ResultListModel<MapAreaModel> getAllPoints(){
		ResultListModel<MapAreaModel> resultListModel = new ResultListModel<MapAreaModel>();
		List<MapAreaModel> mapAreaModelList = new ArrayList<MapAreaModel>();
		try{
			List<MapArea> mapAreaList = this.mapAreaService.getAllMapAreaPoint();
			if(mapAreaList != null && mapAreaList.size() > 0){
				for(MapArea mapArea:mapAreaList){
					MapAreaModel mapAreaModel = new MapAreaModel();
					mapAreaModel = ConvertMapAreaModel(mapArea);
					mapAreaModelList.add(mapAreaModel);
				}
			}
			resultListModel.setResult(true);
		}catch(Exception e){
			resultListModel.setDetail("获取所有区域的坐标异常");
			resultListModel.setResult(false);
			logger.error("获取所有区域的坐标异常警报  :" + e.getMessage());
		}
		resultListModel.setRows(mapAreaModelList);
		return resultListModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：添加覆盖区域信息</p>
	 * 
	 * @author	王坤, 2018年9月3日  上午 11:04:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/addMapArea")
	@ResponseBody
	public ResultModel addMapArea(MapAreaModel mapAreaModel,HttpSession httpSession){
		ResultModel resultModel = new ResultModel();
		try{
			MapArea mapArea = ConvertMapArea(mapAreaModel,httpSession);
			int success = this.mapAreaService.addMapArea(mapArea);
			if(success != -1){
				resultModel.setResult(true);
				resultModel.setDetail("覆盖区域添加成功!");
			}else{
				resultModel.setResult(false);
				resultModel.setDetail("覆盖区域添加失败!");
			}
			
		}catch(Exception e){
			resultModel.setResult(false);
			resultModel.setDetail("覆盖区域添加异常!");
			logger.error(LOG + "覆盖区域添加异常警报  :" + e.getMessage());
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：修改覆盖区域信息</p>
	 * 
	 * @author	王坤, 2018年9月3日  上午 11:06:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/editMapArea")
	@ResponseBody
	public ResultModel editMapArea(@RequestBody MapAreaModel mapAreaModel,HttpSession httpSession){
		ResultModel resultModel = new ResultModel();
		try{
			MapArea mapArea = ConvertMapArea(mapAreaModel,httpSession);
			boolean success = this.mapAreaService.editMapArea(mapArea);
			if(success){
				resultModel.setResult(true);
				resultModel.setDetail("覆盖区域修改成功!");
			}else{
				resultModel.setResult(false);
				resultModel.setDetail("覆盖区域修改失败!");
			}
		}catch(Exception e){
			resultModel.setResult(false);
			resultModel.setDetail("覆盖区域修改异常!");
			logger.error(LOG + "覆盖区域修改异常警报  :" + e.getMessage());
		}
		
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：删除覆盖区域信息</p>
	 * 
	 * @author	王坤, 2018年9月3日  上午 11:07:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/deleteMapArea")
	@ResponseBody
	public ResultModel deleteMapArea(int maId){
		ResultModel resultModel = new ResultModel();
		try{
			boolean success = this.mapAreaService.deleteMapArea(maId);
			if(success){
				resultModel.setResult(true);
				resultModel.setDetail("覆盖区域删除成功!");
			}else{
				resultModel.setResult(false);
				resultModel.setDetail("覆盖区域删除失败!");
			}
		}catch(Exception e){
			resultModel.setResult(false);
			resultModel.setDetail("覆盖区域删除异常!");
			logger.error(LOG + "覆盖区域删除异常警报  :" + e.getMessage());
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：添加特定覆盖区域坐标信息</p>
	 * 
	 * @author	王坤, 2018年9月3日  上午 11:09:25
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/addMapAreaPoint")
	@ResponseBody
	public ResultModel addMapAreaPoint(@RequestBody MapAreaModel mapAreaModel,
			HttpSession httpSession) {
		ResultModel resultModel = new ResultModel();
		try {
			// 查看区域是否存在
			List<MapArea> list = this.mapAreaService.getMapArea(ConvertMapArea(mapAreaModel, httpSession));
			int maId = 0;
			if (list.size() <= 0) {//不存在创建区域，获取id
				// 创建区域
				MapArea mapArea = ConvertMapArea(mapAreaModel, httpSession);
				maId = this.mapAreaService.addMapArea(mapArea);
			} else {//存在，获取id
				maId = list.get(0).getMaId();
			}
			boolean pointSuccess = true;
			if (maId > 0) {
				// 添加坐标点
				List<MapAreaPointModel> mapAreaPointList = mapAreaModel
						.getPoints();
				List<MapAreaPoint> mapAreaPoints = new ArrayList<MapAreaPoint>();
				if (mapAreaPointList != null && mapAreaPointList.size() > 0) {
					for (MapAreaPointModel mapAreaPointModel : mapAreaPointList) {
						MapAreaPoint mapAreaPoint = new MapAreaPoint();
						mapAreaPoint = ConvertMapAreaPoint(mapAreaPointModel,httpSession);
						mapAreaPoint.setMaId(maId);
						mapAreaPoints.add(mapAreaPoint);
					}
				}
				pointSuccess = this.mapAreaService
						.addBatchMapAreaPoint(mapAreaPoints);
			}
			if ( maId == -1 || !pointSuccess) {
				resultModel.setResult(false);
				resultModel.setDetail("覆盖区域坐标添加失败!");
				logger.error("覆盖区域坐标添加失败!");
			}else{
				resultModel.setResult(true);
			}
		} catch (Exception e) {
			resultModel.setResult(false);
			resultModel.setDetail("覆盖区域坐标添加异常!");
			logger.error(LOG + "覆盖区域坐标添加异常警报  :" + e.getMessage());
		}
		return resultModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：删除覆盖区域信息</p>
	 * 
	 * @author	王坤, 2018年9月3日  上午 11:07:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	@RequestMapping("/deleteMapAreaPoint")
	@ResponseBody
	public ResultModel deleteMapAreaPoint(int maId){
		ResultModel resultModel = new ResultModel();
		try{
			boolean success = this.mapAreaService.deleteMapAreaPoint(maId);
			if(success){
				resultModel.setResult(true);
				resultModel.setDetail("覆盖区域坐标删除成功!");
			}else{
				resultModel.setResult(false);
				resultModel.setDetail("覆盖区域坐标删除失败!");
			}
		}catch(Exception e){
			resultModel.setResult(false);
			resultModel.setDetail("覆盖区域坐标删除异常!");
			logger.error(LOG + "覆盖区域坐标删除异常警报  :" + e.getMessage());
		}
		return resultModel;
	}
	/**
	 * 
	 * <p>[功能描述]：mapArea.pojo向mapArea.model转换 </p>
	 * 
	 * @author	王坤, 2018年9月3日 上午 8:46:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	public MapAreaModel ConvertMapAreaModel(MapArea mapArea){
		MapAreaModel mapAreaModel = new MapAreaModel();
		mapAreaModel.setMaId(String.valueOf(mapArea.getMaId()));
		mapAreaModel.setMaVisible(mapArea.isMaVisible());
		mapAreaModel.setMaName(mapArea.getMaName());
		mapAreaModel.setMaUser(String.valueOf(mapArea.getOptUser()));
		/* 区域的所有坐标 */
		if(mapArea.getPointList() != null && mapArea.getPointList().size() > 0){
			List<MapAreaPointModel> mapAreaModels = new ArrayList<MapAreaPointModel>();
			for(MapAreaPoint mapAreaPoint : mapArea.getPointList()){
				MapAreaPointModel mapAreaPointModel = new MapAreaPointModel();
				mapAreaPointModel = ConvertMapAreaPointModel(mapAreaPoint);
				mapAreaModels.add(mapAreaPointModel);
			}
			mapAreaModel.setPoints(mapAreaModels);
		}
		return mapAreaModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：MapArea.Model 和 MapArea.Pojo 之间的转换 </p>
	 * 
	 * @author	王坤, 2018年9月3日 上午 8:50:25
	 * @since	EnvDust 1.0.0
	 *
	 */
	public MapArea ConvertMapArea(MapAreaModel mapAreaModel,HttpSession httpSession){
		MapArea mapArea = new MapArea();
		if(mapAreaModel.getMaId() != null && !mapAreaModel.getMaId().equals("")){
			mapArea.setMaId(Integer.valueOf(mapAreaModel.getMaId()));
		}
		mapArea.setMaName(mapAreaModel.getMaName());
		/* 设置操作者 */
		mapArea.setOptUser(((UserModel)httpSession.getAttribute(DefaultArgument.LOGIN_USER)).getUserId());
		/* 设置是否可见 */
		mapArea.setMaVisible(mapAreaModel.isMaVisible());
		/* 设置操作时间 */
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATA_TIME);
		mapArea.setOptTime(DateUtil.StringToTimestampSecond(sdf.format(new Date())));
		return mapArea;
	}
	
	/**
	 * 
	 * <p>[功能描述]：mapAreaPoint.pojo向mapAreaPoint.model转换 </p>
	 * 
	 * @author	王坤, 2018年9月3日 上午 9:00:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	public MapAreaPointModel ConvertMapAreaPointModel(MapAreaPoint mapAreaPoint){
		MapAreaPointModel mapAreaPointModel = new MapAreaPointModel();
		mapAreaPointModel.setMaId(String.valueOf(mapAreaPoint.getMaId()));
		mapAreaPointModel.setLat(String.valueOf(mapAreaPoint.getLat()));
		mapAreaPointModel.setLng(String.valueOf(mapAreaPoint.getLng()));
		return mapAreaPointModel;
	}
	
	/**
	 * 
	 * <p>[功能描述]：MapAreaPoint.Model 和 MapAreaPoint.Pojo 之间的转换 </p>
	 * 
	 * @author	王坤, 2018年9月3日 上午 9:09:35
	 * @since	EnvDust 1.0.0
	 *
	 */
	public MapAreaPoint ConvertMapAreaPoint(MapAreaPointModel mapAreaPointModel,HttpSession httpSession){
		MapAreaPoint mapAreaPoint = new MapAreaPoint();
		if( mapAreaPointModel.getMaId() != null && !mapAreaPointModel.equals("") ){
			mapAreaPoint.setMaId(Integer.valueOf(mapAreaPointModel.getMaId()));
		}
		if( mapAreaPointModel.getLat() != null && !mapAreaPointModel.getLng().equals("") ){
			mapAreaPoint.setLat(Double.valueOf(mapAreaPointModel.getLat()));
		}
		if(mapAreaPointModel.getLng() != null && !mapAreaPointModel.getLng().equals("")){
			mapAreaPoint.setLng(Double.valueOf(mapAreaPointModel.getLng()));
		}
		/* 设置操作者 */
		mapAreaPoint.setOptUser(((UserModel)httpSession.getAttribute(DefaultArgument.LOGIN_USER)).getUserId());
		/* 设置操作时间 */
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATA_TIME);
		mapAreaPoint.setOptTime(DateUtil.StringToTimestampSecond(sdf.format(new Date())));
		/* 设置分页数据 */
		mapAreaPoint.setRowCount(mapAreaPointModel.getRows());
		mapAreaPoint.setRowIndex((mapAreaPointModel.getPage()-1)*mapAreaPointModel.getRows());
		return mapAreaPoint;
	}
}
