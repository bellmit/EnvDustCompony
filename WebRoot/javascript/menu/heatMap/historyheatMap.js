var content = '<div class="easyui-layout" data-options="fit:true">'
+'<div data-options="region:\'center\',border:false" style="width:230px;">'
	+'<div id="heatallmap" style="height:100%;width:100%;"></div>'
+'</div>'
+'<div data-options="region:\'south\'," style="height:88px;">'
	+'<div class="playbar" style="min-height:20px;padding:10px;margin-bottom:5px;background-color: #f5f5f5;border: 1px solid #e3e3e3;border-radius:4px;-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.05);box-shadow: inset 0 1px 1px rgba(0,0,0,.05);">'
	+'<span><a href="#"  class="easyui-linkbutton" data-options="iconCls:\'icon-prev\',plain:true" style="margin:0px 10px;" onclick="playrevBtn_Click()"></a>'
	+'<a href="#" id="closeRecordId" class="easyui-linkbutton" data-options="iconCls:\'icon-closeBar\',plain:true" style="margin:0px 10px;" onclick="closeBtn_Click()"></a>'
	+'<a href="#" id="playRecordId" class="easyui-linkbutton" data-options="iconCls:\'icon-play\',plain:true" style="margin:0px 10px;" onclick="playBtn_Click()"></a>'
	+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-next\',plain:true" style="margin:0px 10px;" onclick="playnextBtn_Click()"></a></span>'
	+"<span class='test' style='width:350px;height:20px;'></span>"
	+'<span><a href="#" class="easyui-linkbutton" data-options="toggle:true,group:\'g1\',selected:true,plain:true" style="margin:0px 10px;" onclick="setspeed(1)">快速</a>'
	+'<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:\'g1\',plain:true" style="margin:0px 10px;" onclick="setspeed(2)">中速</a></span>'
	+'<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:\'g1\',plain:true" style="margin:0px 10px;" onclick="setspeed(3)">慢速</a>'
	+'</div>'
	+ '<div id="tbdgGetMapInfo" style="padding:5px 10px;">'
	+ '&nbsp;&nbsp;&nbsp;监控物：<input class="easyui-combobox"  editable=true name="tabMonitorthingCombox" id="tabMonitorthingCombox" style="width:150px;"/>'
	+ '&nbsp;&nbsp;&nbsp;数据类型：<input class="easyui-combobox" editable=false name="tabornCnCodeCombox" id="tabornCnCodeCombox" style="width:150px;"/>'
	+ '&nbsp;&nbsp;&nbsp;时间范围：<input class="easyui-datetimebox" id="dthistroyMapBeginTime" style="width:143px;"/>'
	+ '<span>&nbsp;&nbsp;&nbsp;至：</span>'
	+ '<input class="easyui-datetimebox" id="dthistroyMapEndTime" style="width:143px;"/>'
	+ '&nbsp;&nbsp;&nbsp;<input type="checkbox" id="ckHeatHistoryWds" name="ckHeatHistoryWds" style="vertical-align:middle;"/><span style="vertical-align:middle;">显示风标</span>'
//	+'<a href="#"  class="easyui-linkbutton" data-options="toggle:true,iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="startOrigin()" id="historyOriginId">排放分析</a>'
	+'</div>'
+'</div>'
+'</div>';
addPanel("历史监控热力图",content);

var heatamap;
var alarmheatamapRange = {};
var timej=0;
var timer = null;
var timeInterval = 2000;
var preFilterData = null;
initheatamap();

//开始按钮
function playBtn_Click(){
	selectedHeatSearchData("play");
}
//关闭按钮
function closeBtn_Click(){
	$.playBar.Stop();
	heatamap.clearOverlays();
	$('#playRecordId').linkbutton({
	    iconCls: 'icon-play'
	});
	clearInterval(timer);//清除定时器
	$.playBar.restTime(timeInterval*60,heatmapTimeList);
	timej = 0;
}

var prevBtnFlag = false;
var nextBtnFlag = false;

//上一步按钮
function playrevBtn_Click(){
	$.playBar.Stop();
	$('#playRecordId').linkbutton({
		iconCls: 'icon-play'
	});
	clearInterval(timer)//清除定时器
	heatamap.clearOverlays();
	selectedHeatSearchData("playprev");
	prevBtnFlag = true;
}
//下一步按钮
function playnextBtn_Click(){
	$('#playRecordId').linkbutton({
	    iconCls: 'icon-play'
	});
	clearInterval(timer)//清除定时器
	$.playBar.Stop();
	heatamap.clearOverlays();
	selectedHeatSearchData("playnext");
	nextBtnFlag = true;
}

//上一步
function playprevFunc(heatmapTimeList,heatmapdataArr){
	if(nextBtnFlag && timej<heatmapTimeList.length){
		timej = timej-2;
     }
	nextBtnFlag = false;
	if(timej==heatmapTimeList.length){
		timej = timej-2;
	}
	if(timej>0){
		$.playBar.prevStep(heatmapTimeList,heatmapdataArr,timeInterval*60);
		openHeatMapFunc(heatmapdataArr[timej],timej);
//		console.info(timej);
//		console.info(JSON.stringify(heatmapdataArr[timej]));
		timej--;
	}
}
//开始逻辑
function playFunc(heatmapTimeList,heatmapdataArr){
	if(heatmapdataArr.length>0){
		if(timej<(heatmapTimeList.length-1)){
			var start = $('#playRecordId').linkbutton("options").iconCls;
			if(start=="icon-play"){
				$('#playRecordId').linkbutton({
				    iconCls: 'icon-pause'
				});
			    if(prevBtnFlag){
			    	timej= timej+2;
	            	prevBtnFlag = false;
	            }
				heatMapTimer(heatmapdataArr);
				$.playBar.Begin(timeInterval,heatmapTimeList);
			}else{
				$('#playRecordId').linkbutton({
					iconCls: 'icon-play'
				});
				clearInterval(timer)//清除定时器
				$.playBar.Stop();
			}
//			console.info("timej"+timej);
		}else{
			
			$('#playRecordId').linkbutton({
			    iconCls: 'icon-pause'
			});
			timej = 0;
			clearInterval(timer)//清除定时器
			$.playBar.restTime(timeInterval*60,heatmapTimeList);
			heatMapTimer(heatmapdataArr);
			$.playBar.Begin(timeInterval,heatmapTimeList);
		}
	}
}

//下一步
function playnextFunc(heatmapTimeList,heatmapdataArr){
	if(timej==heatmapTimeList.length){
		timej = 0;
		$.playBar.restTime(timeInterval*60,heatmapTimeList);
	}
	if(prevBtnFlag){
      	timej = timej+2;
    }
	prevBtnFlag = false;
	$.playBar.nextStep(heatmapTimeList,heatmapdataArr,timeInterval*60);
	openHeatMapFunc(heatmapdataArr[timej],timej);
//	console.info(timej);
//	console.info(JSON.stringify(heatmapdataArr[timej]));
	timej++;
}

function setspeed(flag){
	if(flag==3){
		timeInterval = 6000;
	}else if(flag==2){
		timeInterval = 4000;
	}else{
		timeInterval = 2000;
	}
}


// 定时器函数
function heatMapTimer(heatmapdataArr){
	//console.info(heatmapdataArr.length);
	timer =setInterval(function(){
		if(timej>=heatmapdataArr.length){
			clearInterval(timer)//清除定时器
			$('#playRecordId').linkbutton({
				iconCls: 'icon-play'
			});
			return false;
		}
		//console.info(timej);
		heatamap.clearOverlays();
		if(heatmapdataArr[timej]!=undefined && heatmapdataArr[timej].length>0){
			openHeatMapFunc(heatmapdataArr[timej],timej);
		}
		timej++;
    },timeInterval);
    return timer;
};

/* 异步加载地图 */
/*function heatloadJScript() {
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=BFUNsobNP2ugfU0ExUnPVwvt&callback=initheatamap";
	document.body.appendChild(script);
}*/

/* 初始地图 */
function initheatamap() {
	heatamap = new BMap.Map("heatallmap"); // 创建Map实例
	var styleJson = [ {
		"featureType" : "road",
		"elementType" : "all",
		"stylers" : {
			"color" : "#f2f2f2ff",
			"hue" : "#ffffff",
			"weight" : "1",
			"lightness" : -8,
			"saturation" : 1,
			"visibility" : "on"
		}
	},  {
        "featureType": "water",
        "elementType": "all",
        "stylers": {
                  "visibility": "off"
        }
	}];
	heatamap.setMapStyle({"styleJson":styleJson});
	var point = new BMap.Point(117.380925, 38.991727); // 创建点坐标
	heatamap.centerAndZoom(point, 13);
	//根据浏览器定位（通过上面坐标点定位更准确，可以取消下面的代码）
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var mk = new BMap.Marker(r.point);
			heatamap.panTo(r.point);
		}     
	},{enableHighAccuracy: true})
	
	var top_left_control = new BMap.ScaleControl({
		anchor : BMAP_ANCHOR_BOTTOM_LEFT
	});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl(); // 左上角，添加默认缩放平移控件
	var overViewOpen = new BMap.OverviewMapControl({
		isOpen : true,
		anchor : BMAP_ANCHOR_BOTTOM_RIGHT
	});
	heatamap.addControl(top_left_control);
	heatamap.addControl(top_left_navigation);
	heatamap.addControl(overViewOpen); // 鹰眼
	heatamap.enableScrollWheelZoom();
	heatamap.enableInertialDragging();
	heatamap.enableContinuousZoom();
	heatamap.setDefaultCursor("Default"); // 设置地图默认的鼠标指针样式
}


function openHeatMapFunc(heatmappoints,timeJ){
	$.getScript("http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js");
	heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":70});
	heatamap.addOverlay(heatmapOverlay);
	var thingvalue = $("#tabMonitorthingCombox").combobox('getValue');
	var maxValue = alarmheatamapRange[thingvalue].maxValue;
	heatmapOverlay.setDataSet({data:heatmappoints,max:maxValue});
    if(!isSupportCanvas()){
    	alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
    	return;
    }
    //更新矢量图
    if(heatmappoints != null && heatmappoints.length>0){
    	for(var i=0;i<heatmappoints.length;i++){
        	var fillColor;
        	var windLevel;
        	if(heatmappoints[i].wd == null || heatmappoints[i].wd =="" 
        		|| heatmappoints[i].ws == null || heatmappoints[i].ws == ""){
        		continue;
        	}else if(heatmappoints[i].ws>10.7){
        		fillColor = "red";
        		windLevel = "大风";
        	}else if(heatmappoints[i].ws>3.3 && heatmappoints[i].ws<10.7){
        		fillColor = "blue"
    			windLevel = "清风";
        	}else{
        		fillColor = "lightgreen"
    			windLevel = "微风";
        	}
        	var pointTitle = getPointTitle(windLevel,heatmappoints[i].ws,heatmappoints[i].wd);
        	
            var point = new BMap.Point(heatmappoints[i].lng,heatmappoints[i].lat);
            var convertWd = parseFloat(heatmappoints[i].wd);
            if(convertWd<=180){
            	convertWd = convertWd+180;
            }else{
            	convertWd = convertWd-180;
            }
            var vectorFCArrow = new BMap.Marker(new BMap.Point(point.lng,point.lat-0.0003), {
            	  // 初始化方向向上的闭合箭头
            	  icon: new BMap.Symbol(BMap_Symbol_SHAPE_FORWARD_CLOSED_ARROW, {
            	    scale: 1.5,
            	    strokeWeight: 1,
            	    rotation: convertWd,
            	    fillColor: fillColor,
            	    fillOpacity: 0.5
            	  }),
            	  title:pointTitle
            	});
            heatamap.addOverlay(vectorFCArrow);
        }
    	heatamap.setViewport(heatmappoints);
    }
    //添加数值百分比画面
    $("#panelModel").panel({
		collapsible : true,
		collapsed : true,
		closable : true,
		fit : false,
		title : "污染排放统计",
		content : '<div class="easyui-layout" style="width:500px;height:400px;"data-options="fit:true">'
				+ '<div id="searchContentHistoryMap"  style="width:100%,height:100%"></div>'
				+ '</div>'
	}).panel("open");
	/* 重绘窗口 */
	$.parser.parse("#panelModel");
	$("#panelModel").panel("refresh").panel("expand").panel('resize',{
		width:$(window).width()*0.4,
		height:$(window).height()*0.5
	});	
	initHistoryMapChart(heatmappoints,timeJ);
}

function isSupportCanvas(){
	var elem = document.createElement('canvas');
    return !!(elem.getContext && elem.getContext('2d'));
}

var heatmapdataArr = [];
var heatmapTimeList = [];

/* 点击站点后查询 */
function selectedHeatSearchData(btnType) {
	var station = $('#mytree').tree('getSelected');
	var gpspoints = [];
	var levelflag = "";
	heatmaptreeList = [];
	var alarmInfo = "";
	var dataType = $("#tabornCnCodeCombox").combobox('getValue');
	var beginTime = $("#dthistroyMapBeginTime").datetimebox('getValue');
	var endTime = $("#dthistroyMapEndTime").datetimebox('getValue');
	var dtBegin = new Date(Date.parse(beginTime));
	var dtEnd= new Date(Date.parse(endTime));
	var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
	var diffHour = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60));
	if(dataType == '2051' && diffHour>1){
		$.messager.alert("提示", "只能查询2小时内的分钟数据热力图！", "warning");
		return false;
	}else if(dataType == '2061' && diffDay>1){
		$.messager.alert("提示", "只能查询2天内的小时数据热力图！", "warning");
		return false;
	}else if(dataType == '2031' && diffDay>6){
		$.messager.alert("提示", "只能查询7天内的每日数据热力图！", "warning");
		return false;
	}
	if (station == null || station == undefined) {
		heatmaptreeList.push(-1);
		heatmapselected = "-1";
		$.messager.alert("提示", "请选择一个监控区域或设备！", "warning");
		return false;
	} else {
		heatmapselected = station.id;
		levelflag = station.levelFlag;
		heatmaptreeList.push(station.id);
		if (heatmaptreeList.length == 0) {
			heatmaptreeList.push("0");
		}
	}
	//关闭占比
	$('#panelModel').panel("close");
	//开始业务
	var dataFilter = {
			"projectId":$("#deviceProjectId").combobox('getValue'),
			"list" : heatmaptreeList,
			"levelflag" : levelflag,
			"nostatus" : "",
			"select" : heatmapselected,
			"dataType":dataType,
			"thingCode":$("#tabMonitorthingCombox").combobox('getValue'),
			"beginTime":beginTime,
			"endTime":endTime,
			"wFlag":$("#ckHeatHistoryWds").is(':checked'),
			"currTime":formatterDate(new Date())
			};
	if(!isSameFilter(dataFilter)){
		heatmapdataArr = [];
		heatmapTimeList = [];
		$.ajax({
			url : "../DeviceController/getThermodynamicData",
			type : "post",
			dataType : "json",
	    	async:true,
			data :dataFilter,
			beforeSend:function() { 
				ajaxLoading("数据加载中，请稍后。。。");
			}, 
			complete:function(data) {
				ajaxLoadEnd();
			}, 
			success : function(json) {
				if (json!= null && json!=undefined) {
					for(var index in json){
						heatmapdataArr.push(json[index]);
						heatmapTimeList.push(index);
					}
					if(btnType=="play"){
						playFunc(heatmapTimeList,heatmapdataArr);
					}else if(btnType=="playnext"){
						playnextFunc(heatmapTimeList,heatmapdataArr);
					}else if(btnType=="playprev"){
						playprevFunc(heatmapTimeList,heatmapdataArr);
					}
				} else {
					heatmap.clearOverlays();
				}
				preFilterData = dataFilter;
			}
		});
	}else{
		preFilterData = dataFilter;
		if(btnType=="play"){
			playFunc(heatmapTimeList,heatmapdataArr);
		}else if(btnType=="playnext"){
			playnextFunc(heatmapTimeList,heatmapdataArr);
		}else if(btnType=="playprev"){
			playprevFunc(heatmapTimeList,heatmapdataArr);
		}
	}
}

/**
 * 用来判断是否是新的过滤条件
 * @param newFilterData
 * @returns {Boolean}
 */
function isSameFilter(newFilterData){
	if(preFilterData==null || preFilterData==undefined){
		return false;
	}else{
		var liststr = newFilterData.list.sort();
		var preliststr = preFilterData.list.sort();
//		console.info(newFilterData.currTime+"==="+preFilterData.currTime);
		if(newFilterData!=null && liststr.toString()==preliststr.toString()
				&& newFilterData.dataType==preFilterData.dataType
				&& newFilterData.thingCode==preFilterData.thingCode
				&& newFilterData.beginTime==preFilterData.beginTime
				&& newFilterData.endTime==preFilterData.endTime
				&& newFilterData.currTime==preFilterData.currTime
				&& newFilterData.wFlag==preFilterData.wFlag
			){
			/*timej = 0;*/
			return true;
		}else{
			if(preFilterData!=undefined && timej>=(heatmapTimeList.length-1)){
				timej = 0;
				clearInterval(timer)//清除定时器
				$.playBar.restTime(timeInterval*60,heatmapTimeList);
			}
			return false;
		}
	}
}

var historyComboboxJsonMul = [];
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		for(var i=0;i<json.length;i++){
			var str = json[i].describe;
			if(str != null && ((str.indexOf("mg/m3") > 0 || str.indexOf("mg/m³") > 0)
                	|| (str.indexOf("μg/m3") > 0 || str.indexOf("μg/m³") > 0)
					|| (str.indexOf("ng/m3") > 0 || str.indexOf("ng/m³") > 0)) ){
				historyComboboxJsonMul.push(json[i]);
			}
			if(json[i].maxValue==null){
				alarmheatamapRange[json[i].code] = {
						"maxValue" : "",
						"minValue" : json[i].minValue
					};
			}else{
				alarmheatamapRange[json[i].code] = {
						"maxValue" : json[i].maxValue,
						"minValue" : json[i].minValue
					};
			}
			
		}
	}
});
$("#tabMonitorthingCombox").combobox({
	  data:historyComboboxJsonMul,
	  method:'post',
	  valueField:'code',
	  textField:'describe',
	  value:historyComboboxJsonMul[0].code,
	  onShowPanel: function () {
	      // 动态调整高度  
	      if (historyComboboxJsonMul.length < 20) {
	          $(this).combobox('panel').height("auto");
	      }else{
	          $(this).combobox('panel').height(300);
	      }
	  }
});

var ornCnCodeValue = "2061";
$("#tabornCnCodeCombox").combobox({
	data : ornCnCode.slice(1, 4),
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto',
	value : ornCnCodeValue,
	onChange: function (newValue, oldValue) {

		cnChangeEvent(newValue,oldValue);

     }
});
$("#dthistroyMapBeginTime").datetimebox('setValue',GetDateStr(0,0));
$("#dthistroyMapEndTime").datetimebox('setValue',formatterNowDate("yyyy-MM-dd hh:mm:ss"));
/*	selectedHeatSearchData();*/
$.playBar.addBar($('.test'),timeInterval*60);
$.playBar.Stop();
$.playBar.changeBarColor("#72dfff");//设置进度条颜色
//	$("#mytree").tree(
//	{
//		checkbox : false,
//		onSelect : function(node) {
//			var currTab = $('#mytab').tabs('getSelected');
//			var title = currTab.panel('options').title;
//			if (title == "网络状态") {
//				searchNetStatusSelected();
//			} else if (title == "网络数据"){
//				searchNetDataSelected();
//			} else if (title == "地图") {
//				selectedSearchData();
//			}
//		}
//	});
//});

//排放分析
function startOrigin(){
	var station = $('#mytree').tree('getSelected');
	var levelflag = "";
	heatmaptreeList = [];
	if (station == null || station == undefined) {
		heatmaptreeList.push(-1);
		heatmapselected = "-1";
	} else {
		heatmapselected = station.id;
		levelflag = station.levelFlag;
		heatmaptreeList.push(station.id);
		if (heatmaptreeList.length == 0) {
			heatmaptreeList.push("0");
		}
	}
	var thingUtil;
	var thingUtilName = $("#tabMonitorthingCombox").combobox('getText');
	if(thingUtilName != null && thingUtilName != '' && thingUtilName.indexOf("mg/m3")>=0){
		thingUtil = "mg/m3";
	}else if(thingUtilName != null && thingUtilName != '' && thingUtilName.indexOf("ug/m3")>=0){
		thingUtil = "ug/m3";
	}else{
		$.messager.alert("提示", "监测物单位必须为mg/m3或ug/m3！", "warning");
		return false;
	}
	var dataFilter = {
			"projectId":$("#deviceProjectId").combobox('getValue'),
		    "list" : heatmaptreeList,
			"levelflag" : levelflag,
			"nostatus" : "",
			"select" : heatmapselected,
			"dataType" : $("#tabornCnCodeCombox").combobox('getValue'),
			"thingCode":$("#tabMonitorthingCombox").combobox('getValue'),
			"beginTime":$("#dthistroyMapBeginTime").datetimebox('getValue'),
			"endTime":$("#dthistroyMapEndTime").datetimebox('getValue'),
			"thingUtil":thingUtil
			};
	setAreaStatisticData(dataFilter);
}
/* 显示区域统计数据 */
function setAreaStatisticData(dataFilter) {
	$("#dialogModel").dialog({
		width : 680,
		height : 410,
		title : "排放分析",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		iconCls : 'icon-chart',
		resizable : true,
		closed : true,
		content : '<div id="dgAreaStatisticData" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	$("#dgAreaStatisticData").datagrid({
		view:myview,
		fit : true,
		border : false,
		pagination : false,
		fitColumns : true,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : "../DeviceController/getAreaStatisticData",
		queryParams: dataFilter,
		pageSize : 10,
		autoRowHeight : true,
		rownumbers : true,
		columns : [ [{
			field : 'areaId',
			title : '区域ID',
			width : 100,
			halign : 'center',
			align : 'center',
			hidden : 'true'
		},{
			field : 'areaName',
			title : '区域名称',
			width : 150,
			halign : 'center',
			align : 'center'
		},{
			field : 'disRatio',
			title : '排放比例值',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'avgValue',
			title : '平均排放值',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'totalValue',
			title : '排放总量',
			width : 100,
			halign : 'center',
			align : 'center'
		} ] ]
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
	/* 定义分页器的初始显示默认值 */
//	$("#dgAreaStatisticData").datagrid("getPager").pagination({
//		total : 0
//	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
}

/* 根据数据类型初始化时间控件 */
function cnChangeEvent(newValue,oldValue){
	if(newValue == "2051"){
		$("#dthistroyMapBeginTime").datetimebox('setValue',formatterNowDate("yyyy-MM-dd hh:00:00"));
		$("#dthistroyMapEndTime").datetimebox('setValue',formatterNowDate("yyyy-MM-dd hh:mm:ss"));
	}else if(newValue == "2061"){		
		$("#dthistroyMapBeginTime").datetimebox('setValue',GetDateStr(0,0));
		$("#dthistroyMapEndTime").datetimebox('setValue',formatterNowDate("yyyy-MM-dd hh:mm:ss"));
	}else if(newValue == "2031"){		
		$("#dthistroyMapBeginTime").datetimebox('setValue',GetDateStr(-6,0));
		$("#dthistroyMapEndTime").datetimebox('setValue',formatterNowDate("yyyy-MM-dd hh:mm:ss"));
	}
}

/* 根据数据赋值饼形图 */
function initHistoryMapChart(heatmappoints,timeJ){
	var dom = document.getElementById("searchContentHistoryMap");
	dom.style.cssText = "width:100%;height:100%";
	var myChart = echarts.init(dom);
	var option = null;
	var weatherIcons = {
	    'Sunny': 'http://echarts.baidu.com/data/asset/img/weather/sunny_128.png',
	    'Cloudy': 'http://echarts.baidu.com/data/asset/img/weather/cloudy_128.png',
	    'Showers': 'http://echarts.baidu.com/data/asset/img/weather/showers_128.png'
	};
	var data = genHistoryMapData(heatmappoints);
	option = {
		    title : {
		        text: '污染排放占比',
		        subtext: heatmapTimeList[timeJ],
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        type: 'scroll',
		        orient: 'vertical',
		        right: 10,
		        top: 20,
		        bottom: 20,
		        data: data.legendData,

		        selected: data.selected
		    },
		    series : [
		  		        {
				            name: '名称',
				            type: 'pie',
				            radius : '55%',
				            center: ['40%', '50%'],
				            data: data.seriesData,
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
		};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}
function genHistoryMapData(points) {
    var legendData = [];
    var seriesData = [];
    var selected = {};
    for (var i = 0; i < points.length; i++) {
        name = points[i].deviceName;
        legendData.push(name);
        seriesData.push({
            name: name,
            value: points[i].count
        });
        selected[name] = i < 10;
    }

    return {
        legendData: legendData,
        seriesData: seriesData,
        selected: selected
    };
}
//风向点title
function getPointTitle(level,ws,wd){
	var wdname = "";
	if(wd!= undefined && wd != null){
			if(wd == 0){
				wdname = "正北风";
			}else if(wd == 90){
				wdname = "正东风";
			}else if(wd == 180){
				wdname = "正南风";
			}else if(wd == 270){
				wdname = "正西风";
			}else if(wd>0 && wd<90){
				wdname = "东北风";
			}else if(wd>90 && wd<180){
				wdname = "东南风";
			}else if(wd>180 && wd<270){
				wdname = "西南风";
			}else if(wd>270 && wd<360){
				wdname = "西北风";
			}else{
				wdname = "未知风向";
			}
		}
	var title = "级别：" + level + "\n风速：" + ws + "m/s\n风向：" + wdname;
	return title;
}