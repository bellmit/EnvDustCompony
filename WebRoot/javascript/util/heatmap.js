alert("热力图");
var heatamap;
var heatheatamapOverlay;
var heatamaptimer = null;
var alarmList = [];
var isInit = true;
var heatamaptreeList = [];
var treelevelflag = "";
var heatamapselected = "";
var points = {};
var heatamapajaxconn = 0;
var mylabel = null;
var ply = null;
var temMarkers = [];
var myValue = "";
var openHeatheatamap = false;
initheatamap();

function playprevFunc(){
	alert("开始");
}
function playFunc(){
	alert("开始");
}
function playnextFunc(){
	alert("开始");
}
/* 异步加载地图 */
/*function heatloadJScript() {
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?v=2.0&ak=BFUNsobNP2ugfU0ExUnPVwvt&callback=initheatamap";
	document.body.appendChild(script);
}*/

/* 初始地图 */
function initheatamap() {
	openHeatheatamap = false;
	heatamap = new BMap.Map("heatallmap"); // 创建Map实例
	var point = new BMap.Point(117.1786889372559, 39.10762965106183); // 创建点坐标
	heatamap.centerAndZoom(point, 10);
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
	var size = new BMap.Size(10, 20); // 启用滚轮放大缩小
	
	//addToolbar();
	heatamap.addControl(new BMap.CityListControl({// 城市列表
		anchor : BMAP_ANCHOR_TOP_RIGHT,
		offset : size
	}));
	
	//搜索框
	function search() {
		this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
		this.defaultOffset = new BMap.Size(100, 20);
	}
	search.prototype = new BMap.Control();
	search.prototype.initialize = function(heatamap) {
		return createSearchCtrl();
	}
	var mySearchCtrl = new search();	// 创建控件
	heatamap.addControl(mySearchCtrl);	// 添加到地图当中

	//自动完成检索
	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
		    {"input" : "searchmapaddrText"
		    ,"location" : heatamap
		});
	ac.addEventListener("onconfirm", function(e) { 
		var _value = e.item.value;
	    myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		//鼠标点击下拉列表后的事件
	    setPlace();
	});
	document.getElementById("searchmapaddrText").value = "搜索地点";
	// 右键菜单
	var menu = new BMap.ContextMenu();
	var txtMenuItem = [ {
		text : '添加监控站点',
		callback : function(e) {
			var point = new BMap.Point(e.lng, e.lat);
			var pointarry = [];
			pointarry.push(point)
			addMonitorStation(point, pointarry);
		}
	} ];
	for (var i = 0; i < txtMenuItem.length; i++) {
		menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,
				txtMenuItem[i].callback, 100));
	}
	heatamap.addContextMenu(menu);

	
	
	/*
	 * heatamap.addEventListener("mousemove",function(e){//坐标拾取 if(mylabel!=null){
	 * heatamap.removeOverlay(mylabel); } var point = new
	 * BMap.Point(e.point.lng,e.point.lat); var opts = { position : point, //
	 * 指定文本标注所在的地理位置 offset : new BMap.Size(5, 20) //设置文本偏移量 } mylabel = new
	 * BMap.Label(e.point.lng+","+e.point.lat, opts); // 创建文本标注对象
	 * mylabel.setStyle({ color : "red", fontSize : "12px", height : "20px",
	 * lineHeight : "20px", fontFamily:"微软雅黑" }); map.addOverlay(mylabel); });
	 */
	selectedHeatSearchData();
	//heatamaptimer = setInterval(selectedSearchDataConn, 20000);
}


function openHeatMapFunc(heatmappoints){
	$.getScript("http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js");
	heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
	heatamap.addOverlay(heatmapOverlay);
	heatmapOverlay.setDataSet({data:heatmappoints,max:100});
    if(!isSupportCanvas()){
    	alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
    }
}

function isSupportCanvas(){
	var elem = document.createElement('canvas');
    return !!(elem.getContext && elem.getContext('2d'));
}

/**
 * 添加工具条层
 * @returns {ZoomControl}
 */
function addToolbar(){

	// 定义一个控件类,即function
	function ZoomControl(){
	  // 默认停靠位置和偏移量
	  this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
	  this.defaultOffset = new BMap.Size(10, 10);
	}

	// 通过JavaScript的prototype属性继承于BMap.Control
	ZoomControl.prototype = new BMap.Control();

	// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
	// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
	ZoomControl.prototype.initialize = function(heatamap){
	  // 创建一个DOM元素
	  var div = document.createElement("div");
	  // 添加文字说明
	  div.appendChild(document.createTextNode("热力图"));
	  // 设置样式
	  div.style.cursor = "pointer";
	  div.style.border = "1px solid #CCC";
	  div.style.backgroundColor = "white";
	  div.style.margin = "10px 60px";
	  div.style.height="24px;"
	  div.style.padding = " 5px 10px 5px 10px";
	  div.style.float="left";
	  div.style.display="inline-block";
	  div.style.lineHeight = "24px;"

	  // 绑定事件,点击一次放大两级
	  div.onclick = function(e){
		if(openHeatheatamap){
			openHeatheatamap.hide();
			openHeatMap = false;
		}else{
			heatmapOverlay.show();
			openHeatheatamap = true;
		}
	  }
	  // 添加DOM元素到地图中
	  heatamap.getContainer().appendChild(div);
	  // 将DOM元素返回
	  return div;
	}
	// 创建控件
	var myZoomCtrl = new ZoomControl();
	heatamap.addControl(myZoomCtrl);
}
function closeHeatmap(){
    heatmapOverlay.hide();
}
// 创建搜搜索框
function createSearchCtrl() {
	var inDiv = document.createElement("div");
	// 输入框
	var inText = document.createElement("input");
	inText.setAttribute("type", "text");
	inText.id = "searchmapaddrText";
	inText.className = "input";
	inText.value = "搜索地点";
	inDiv.appendChild(inText);
	// 输入框的删除按钮
	var a = document.createElement("a");
	a.href = "#";
	a.id = "a";
	a.className = "clear";
	a.onclick = function() {
		clearInput();
	}
	inDiv.appendChild(a);
	// 搜索按钮
	var inButton = document.createElement("input");
	inButton.setAttribute("type", "button");
	inButton.id = "searchmapaddrBtn";
	inButton.onclick = function() {
		getBoundary();
	};
	inText.onfocus = function() {
		clearTitle();
	};
	inText.onkeyup = function() {
		var value = document.getElementById("searchmapaddrText").value;
		var dispay = document.getElementById("a").style.display;
		if (value != "" && dispay == "none") {
			document.getElementById("a").style.display = "inline";// a标签显示
		} else if (value == "" && dispay == "inline") {
			document.getElementById("a").style.display = "none";// a标签显示
		}
	};
	inDiv.appendChild(inButton);
	var listDiv = document.createElement("div");
	listDiv.id = "r-result";
	inDiv.appendChild(listDiv);
	heatamap.getContainer().appendChild(inDiv);
	document.getElementById("a").style.display = "none";// a标签隐藏
	return inDiv;
}

// 获取省、直辖市或县位置
function getBoundary() {
	var bdary = new BMap.Boundary();
	var name = document.getElementById("searchmapaddrText").value;
	if (ply != null) {
		heatamap.removeOverlay(ply);
	}
	if (temMarkers.length > 0) {
		for (var i = 0; i < temMarkers.length; i++) {
			heatamap.removeOverlay(temMarkers[i]);
		}
	}
	bdary.get(name, function(rs) { // 获取行政区域
		var count = rs.boundaries.length; // 行政区域的点有多少个
		if (count != 0) {
			for (var i = 0; i < count; i++) {
				ply = new BMap.Polygon(rs.boundaries[i], {
					strokeWeight : 2,
					strokeColor : "#ff0000"
				}); // 建立多边形覆盖物
				// map.addOverlay(ply); // 添加覆盖物
				heatamap.setViewport(ply.getPath()); // 调整视野
			}
		} else {
			var local = new BMap.LocalSearch(heatamap, {
				renderOptions : {
					map : heatamap,
					panel : "r-result"
				},
				pageCapacity : 5
			});
			local.search(name);
			local.setMarkersSetCallback(function(pois) {
				for (var i = pois.length; i--;) {
					// pois[i].marker.hide();
					temMarkers.push(pois[i].marker);
				}
			});
		}

	});
}
// 鼠标获取焦点时的处理
function clearTitle()
{
    var value = document.getElementById("searchmapaddrText").value;
    if(value=="搜索地点"){
    	document.getElementById("searchmapaddrText").value = "";
    }
}
function clearInput(){
	document.getElementById("searchmapaddrText").value = "";
	getBoundary();
	document.getElementById("searchmapaddrText").value = "搜索地点";
	document.getElementById("a").style.display = "none";//a标签显示
}

function setPlace(){// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint(myValue, function(point){
	  if (point) {
	    heatamap.centerAndZoom(point, 16);
	    heatamap.addOverlay(new BMap.Marker(point));
	  }
	}, "北京");
}



var singleclick = false;
/* 点击站点后查询 */
function selectedHeatSearchData() {
	var heatmapdata = null;
	var station = $('#mytree').tree('getSelected');
	var gpspoints = [];
	var levelflag;
	heatmaptreeList = [];
	treelevelflag = "";
	singleclick = true;
	var alarmInfo = "";
	if (station == null || station == undefined) {
		heatmaptreeList.push(-1);
		heatmapselected = "-1";
	} else {
		heatmapselected = station.id;
		levelflag = station.levelFlag;
		treelevelflag = station.levelFlag;
		heatmaptreeList.push(station.id);
		if (heatmaptreeList.length == 0) {
			heatmaptreeList.push("0");
		}
	}
	$.ajax({
		url : "../DeviceController/getThermodynamicData",
		type : "post",
		dataType : "json",
		data : {
			"list" : heatmaptreeList,
			"levelflag" : levelflag,
			"nostatus" : "",
			"select" : heatmapselected,
			"dataType":"2061",
			"thingCode":"PM25",
			"maxsize":150
		},
		success : function(json) {
			if (json.result != null) {
				var isRefresh = false;// 是否更新表格
				if (json.select == heatmapselected) {
					isRefresh = true;
				}
				if (isRefresh) {
					heatmapdata = json.result;
					alarmList = [];
					var heatmapCount = heatmapdata.length;
					for (var i = 0; i < heatmapCount; i++) {
						var  deviceCode = heatmapdata[i].deviceCode;
						var point = new BMap.Point(heatmapdata[i].deviceX,
								heatmapdata[i].deviceY);
						gpspoints.push(point);
						//addMarker(point, heatmapdata[i]);
						if (heatmapdata[i].statusCode != "N") {
							var value = displayAlarmInfo(heatmapdata[i].deviceName,
									heatmapdata[i].statusInfo);
							if (value != "") {
								if (alarmInfo == "") {
									alarmInfo = value;
								} else {
									alarmInfo = alarmInfo + "；" + value;
								}
								alarmList.push(heatmapdata[i].deviceCode);
							}
							if (alarmInfo != "") {
								alarmInfo = alarmInfo.substr(0, 100);
							}
						}
					}
					if(alarmInfo!=""){
						$("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/emergency.gif");
					}else{
						$("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/alarm.png");
					}
					$("#alarminfos").html(alarmInfo.trim());
					if (alarmList.length > 0) {
						$("#morebtn").css('display', '');
					} else {
						$("#morebtn").css('display', 'none');
					}
					openHeatMapFunc(gpspoints);
				}
			} else {
				heatmap.clearOverlays();
			}
		}
	});
}

/* 实时获取地图点位数据 */
/*function selectedHeatSearchDataConn() {
	var station = null;
	var heatmapdata = null;
	var gpspoints = [];
	var alarmInfo = "";
	if (heatmapajaxconn > 0) {// 始终保持一个连接
		return false;
	}
	heatmapajaxconn++;// 发起连接，连接数加1
	$.ajax({
		url : "../DeviceController/getMapMonitorDataConn",
		type : "post",
		dataType : "json",
		timeout : 600000,
		async:true,
		data : {
			"list" : heatmaptreeList,
			"nostatus" : "",
			"select" : heatmapselected,
			"maxsize":150
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			heatmapajaxconn--;
		},
		success : function(json) {
			heatmapdata = null;
			if (json.result != null) {
				var isRefresh = false;// 是否更新表格
				if (json.select == heatmapselected) {
					isRefresh = true;
				}
				if (isRefresh) {
					heatmapdata = json.result;
					alarmList = [];
					var heatmapCount = heatmapdata.length;
					for (var i = 0; i < heatmapCount; i++) {
						var point = new BMap.Point(heatmapdata[i].deviceX,
								heatmapdata[i].deviceY);
						gpspoints.push(point);
						if (heatmapdata[i].statusCode != "N") {
							var value = displayAlarmInfo(heatmapdata[i].deviceName,
									heatmapdata[i].statusInfo);
							if (value != "") {
								if (alarmInfo == "") {
									alarmInfo = value;
								} else {
									alarmInfo = alarmInfo + "；" + value;
								}
								alarmList.push(heatmapdata[i].deviceCode);
							}
							if (alarmInfo != "") {
								alarmInfo = alarmInfo.substr(0, 100);
							}
						}
					}
					if(alarmInfo!=""){
						$("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/emergency.gif");
					}else{
						$("#logoimg").attr("src", "../javascript/jquery-easyui-1.4.4/themes/icons/alarm.png");
					}
					$("#alarminfos").html(alarmInfo);
					if (alarmList.length > 0) {
						$("#morebtn").css('display', '');
					} else {
						$("#morebtn").css('display', 'none');
					}
					var length = Math.ceil(gpspoints.length / 10);
					var mode = gpspoints.length % 10;
					for (var i = 0; i < length; i++) {
						var gpspointsf = [];
						var heatmapdataf = [];
						if (i == length - 1 && mode != 0) {
							gpspointsf = gpspoints.slice(i * 10,
									(i * 10 + mode));
							heatmapdataf = heatmapdata.slice(i * 10, (i * 10 + mode));
						} else {
							gpspointsf = gpspoints.slice(i * 10, (i + 1) * 10);
							heatmapdataf = heatmapdata.slice(i * 10, (i + 1) * 10);
						}
						gpsIntoBaiduPoint(heatmapdataf, gpspointsf);
					}
				}
			}
			if(heatmapajaxconn>0){
				heatmapajaxconn--; // 当连接关闭、连接数减1
			}
			if (heatmapajaxconn == 0) { // 如果连接数少于1 则发起新的连接
				selectedHeatSearchDataConn();
			}
		}
	});
}
*/

/* 获取设备信息 */
function getDeviceInfo(deviceCode) {
	var datajson = null;
	$.ajax({
		url : "../DeviceController/queryDevice",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			"deviceCode" : deviceCode
		},
		success : function(json) {
			datajson = json;
		}
	});
	return datajson;
}

// 存储监控物的范围值
var alarmheatamapRange = {};
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async : false,
	success : function(json) {
		for (var i = 0; i < json.length; i++) {
			alarmheatamapRange[json[i].name] = {
				"max" : json[i].max,
				"min" : json[i].min
			};
		}
	}
});

/* 查询设备24小时内的监控数据 */
function searchheatamapDeviceDataFuc(deviceCode) {
	var colums = [];// 存储列内容
	var monitors = {};// 监控物
	var datajson = [];// 存储列表数据
	$.ajax({
		url : "../MonitorStorageController/getTimelyMonitorData",
		data : {
			"devicecode" : deviceCode,
			"isrepeat" : false
		},
		async : true,
		type : "post",
		dataType : "json",
		success : function(data) {
			var isRefresh = false;// 是否更新表格
			if (data.select == deviceCode) {
				isRefresh = true;
			}
			if (data.result != null) { // 请求成功
				isrepeatFlag = true;
				for ( var key in data.result) {
					var list = data.result[key];
					for ( var name in list) {// 获取监控物集合
						if (monitors[name] == undefined) {
							monitors[name] = name;
						}
					}
					list["time"] = key;
					datajson.push(list);
				}
				colums.push({
					field : 'time',
					title : '时间',
					width : 130,
					halign : 'center',
					align : 'center'
				});
				for ( var name in monitors) {
					colums.push({
							field : name,
							title : name,
							width : 130,
							halign : 'center',
							align : 'center',
							formatter : function(value, row, index) {
								if (value != undefined) {
									var max = 1000;
									if (this.field != ""
											&& this.field != undefined) {
										max = alarmheatamapRange[this.field].max;
									}
									if (alarmheatamapRange[name] != undefined
											&& value > max) {
										return '<font color="red">'
												+ value + '</font>';
									} else {
										return '<font color="black">'
												+ value + '</font>';
									}
								} else {
									return "---";
								}
							}
					});
				}
				if (isRefresh) {
					$("#searchContentheatamap").datagrid({
						view : myview,
						fit : true,
						border : false,
						pagination : false,
						fitColumns : true,
						singleSelect : true,
						pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
						pageSize : 10,
						autoRowHeight : false,
						rownumbers : true,
						columns : [ colums ],
						data : datajson
					});
				}
			}
		}
	});
}



function displayAlarmInfo(deviceCode, statusCode) {
	return deviceCode + "：" + statusCode + "";
}

var mfrCodeDataMap = {};
var mfrCodeValueMap = null;
// 状态
var statusCodeDataMap = {};
var statusValueMap = null;
// 区域ID
var areaIdDataMap = {};
var areaValueMap = null;
// 负责人
var userIdDataMap = {};
var userValueMap = null;
// 监督单位
var orgIdDataMap = {};
var orgValueMap = null;

/* 初始化厂商ID、设备状态、区域ID,下拉框 */
function initParamMap() {
	$.ajax({
		url : "../DeviceController/queryDevicemfrCodeDropDown",
		type : "post",
		dataType : "json",
		async : false,
		success : function(json) {
			if (json.total > 0) {
				for (var i = 0; i < json.total; i++) {
					mfrCodeDataMap[json.rows[i].code] = json.rows[i].name;
				}
				mfrCodeValueMap = json.rows[0].code;
			}
		}
	});
	$.ajax({
		url : "../DeviceController/queryDevicestatusCodeDropDown",
		type : "post",
		dataType : "json",
		async : false,
		success : function(json) {
			if (json.total > 0) {
				for (var i = 0; i < json.total; i++) {
					statusCodeDataMap[json.rows[i].code] = json.rows[i].name;
				}
				statusValueMap = json.rows[0].code;
			}
		}
	});
	$.ajax({
		url : "../DeviceController/queryDeviceAreaDropDown",
		type : "post",
		dataType : "json",
		data : {
			"id" : "-1",
			"levelFlag" : "-1"
		},
		async : false,
		success : function(json) {
			if (json.total > 0) {
				for (var i = 0; i < json.total; i++) {
					areaIdDataMap[json.rows[i].id] = json.rows[i].name;
				}
				areaValueMap = json.rows[0].id;
			}
		}
	});
	$.ajax({
		url : "../DeviceController/queryDevicePrincipleDropDown",
		type : "post",
		dataType : "json",
		data : {
			"devicePrinciple" : "-1"
		},
		async : false,
		success : function(json) {
			userIdDataMap[0] = "---请选择---";
			userValueMap = "0";
			if (json.total > 0) {
				for (var i = 0; i < json.total; i++) {
					userIdDataMap[json.rows[i].id] = json.rows[i].name;
				}
			}
		}
	});
	$.ajax({
		url : "../DeviceController/queryDeviceOversightUnit",
		type : "post",
		dataType : "json",
		data : {
			"orgId" : "-1"
		},
		async : false,
		success : function(json) {
			orgIdDataMap[0] = "---请选择---";// orgID对应的键值
			orgValueMap = 0; // orgdata默认的值
			if (json.total > 0) {
				for (var i = 0; i < json.total; i++) {
					orgIdDataMap[json.rows[i].id] = json.rows[i].name;
				}
			}

		}
	});
}

//实时监控存储监控物的范围值
var alarmRange = {};
//实时监控存储监控物带单位
var monitorsThingJson = {};
var monitorsnetStautsThingJson = {};
//实时监控
var monitorslist = [];
//实时监控存储之前站点
var preStation = null;
var initMonitorsflag = true;
//记录监控状态当前页
var netStatuspageNumber = 1;
var netStatuspageSize = 10;
//记录监控数据当前页
var netDatapageNumber = 1;
var netDatapageSize = 10;

$(function() {
/*	window.onload = heatloadJScript; // 异步加载地图
 * 
*/	var  comboboxJsonMul = [];
	$.ajax({
		url : "../MonitorStorageController/getAthorityMonitors",
		type : "post",
		dataType : "json",
		async:false,
		success : function(json) {
			comboboxJsonMul = json;
		}
	});
	$("#tabMonitorthingCombox").combobox({
		  data:comboboxJsonMul,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  panelHeight:'auto',
		  value:comboboxJsonMul[0].code
    });
	
	var ornCnCodeValue = "2061";
	$("#tabornCnCodeCombox").combobox({
		data : ornCnCode,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value : ornCnCodeValue
	});
	
	$.playBar.addBar($('.test'),1000*60);//第一个参数是需要显示播放器的容器，第二个参数为时间，单位毫秒
	$.playBar.changeBarColor("#72dfff");//设置进度条颜色
	$("#mytree")
			.tree(
					{
						checkbox : false,
						onSelect : function(node) {
							var currTab = $('#mytab').tabs('getSelected');
							var title = currTab.panel('options').title;
							if (title == "网络状态") {
								searchNetStatusSelected();
							} else if (title == "网络数据"){
								searchNetDataSelected();
							} else if (title == "地图") {
								selectedHeatSearchData();
							}
						}
					});
});