var content = '<div class="easyui-layout" data-options="fit:true">'
+'<div data-options="region:\'center\',border:false" style="width:230px;">'
	+'<div id="realheatmap" style="height:100%;width:100%;"></div>'
+'</div>'
+'<div data-options="region:\'south\'," style="height:52px;">'
	+'<div class="playbar1" style="min-height:20px;padding:10px;background-color: #f5f5f5;border: 1px solid #e3e3e3;border-radius:4px;-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.05);box-shadow: inset 0 1px 1px rgba(0,0,0,.05);">'
	+'<span>&nbsp;&nbsp;&nbsp;监控物：<input class="easyui-combobox"  editable=true name="realmapMonitorthingCombox" id="realmapMonitorthingCombox" style="width:150px;"/>'
	+'<a href="#"  class="easyui-linkbutton" data-options="toggle:true,group:\'g1\',iconCls:\'icon-play\',plain:true" style="margin:0px 10px;" onclick="startBtn_Click()" id="realStartId">开始</a>'
	+'<a href="#" id="closerealRecordId" class="easyui-linkbutton" data-options="toggle:true,group:\'g1\',iconCls:\'icon-closeBar\',plain:true" style="margin:0px 10px;" onclick="closeBtnreal_Click()">停止</a>'
	+'<span>&nbsp;&nbsp;&nbsp;云量等级：<input class="easyui-combobox"  editable=false name="realmapCloudCombox" id="realmapCloudCombox" style="width:150px;"/>'
	+'<span>&nbsp;&nbsp;&nbsp;扩散时间：<input class="easyui-numberbox" name="realmapTimeTextbox" id="realmapTimeTextbox" prompt="输入分钟整数" style="width:100px;"/>'
	+'<a href="#"  class="easyui-linkbutton" data-options="toggle:true,iconCls:\'icon-tip\',plain:true" style="margin:0px 10px;" onclick="startSpread()" id="realSpreadId">扩散趋势</a>'
	+'</div>'
+'</div>'
+'</div>';
addPanel("实时监控热力图",content);
var cloudStatus = [ {
	"id" : "1",
	"name" : "全天少云/当前少云"
}, {
	"id" : "2",
	"name" : "全天多云/当前少云"
}, {
	"id" : "3",
	"name" : "全天乌云/当前少云"
}, {
	"id" : "4",
	"name" : "全天多云/当前多云"
} , {
	"id" : "5",
	"name" : "全天乌云/当前乌云"
} ];
var realheatMap;
var realheatmapOverlay;
initrealheatamap();

/**
 * 初始地图
 */
function initrealheatamap() {
	openHeatheatamap = false;
	realheatMap = new BMap.Map("realheatmap"); // 创建Map实例
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
	realheatMap.setMapStyle({"styleJson":styleJson});
	var point = new BMap.Point(117.380925, 38.991727); // 创建点坐标
	realheatMap.centerAndZoom(point, 13);
	//根据浏览器定位（通过上面坐标点定位更准确，可以取消下面的代码）
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var mk = new BMap.Marker(r.point);
			realheatMap.panTo(r.point);
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
	realheatMap.addControl(top_left_control);
	realheatMap.addControl(top_left_navigation);
	realheatMap.addControl(overViewOpen); // 鹰眼
	realheatMap.enableScrollWheelZoom();
	realheatMap.enableInertialDragging();
	realheatMap.enableContinuousZoom();
	realheatMap.setDefaultCursor("Default"); // 设置地图默认的鼠标指针样式
	$.getScript("http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js");
}

//初始化云量等级
$("#realmapCloudCombox").combobox({
    data : cloudStatus,
    method : 'post',
    valueField : 'id',
    textField : 'name',
    panelHeight : 'auto',
    value:cloudStatus[0].id
});

/**
 * 加载热力图
 * @param heatmappoints
 */
function openHeatRealMapFunc(heatmappoints){
    realheatmapOverlay = new BMapLib.HeatmapOverlay({"radius":50});
	realheatMap.addOverlay(realheatmapOverlay);
	var thingvalue = $("#realmapMonitorthingCombox").combobox('getValue');
	var maxValue = alarmheatamapRange[thingvalue].maxValue;
	realheatmapOverlay.setDataSet({data:heatmappoints,max:maxValue});
    if(!isSupportCanvasReal()){
    	alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~');
    }
    realheatMap.setViewport(heatmappoints);
}

function isSupportCanvasReal(){
	var elem = document.createElement('canvas');
    return !!(elem.getContext && elem.getContext('2d'));
}

function startBtn_Click(){
	isReturnData = true;
	if(realheatMaptimer!=null){
		clearInterval(realheatMaptimer);
	}
	searchRealHeatMapFunc();
	realheatMaptimer = setInterval(searchRealHeatMapFunc,60000);
}

function closeBtnreal_Click(){
	clearInterval(realheatMaptimer);
}

//扩散趋势
function startSpread(){
	closeBtnreal_Click();
	searchSpreadHeatMapFunc();
}

/*查询扩散信息*/
function searchSpreadHeatMapFunc() {
	var timeCount = $("#realmapTimeTextbox").val();
	if(timeCount == '' || !/(^[1-9]\d*$)/.test(timeCount)){
		$.messager.alert("提示", "请输入分钟整数！", "warning");
		return false;
	}
	var station = $('#mytree').tree('getSelected');
	var gpspoints = [];
	var levelflag;
	var heatmaptreeList = [];
	var heatmapselected  = null;
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
	var dataFilter = 
		{"list" : heatmaptreeList,
			"levelflag" : levelflag,
			"nostatus" : "",
			"select" : heatmapselected,
			"dataType":"2011",
			"thingCode":$("#realmapMonitorthingCombox").combobox('getValue'),
			"cloud":$("#realmapCloudCombox").combobox('getValue'),
			"count":timeCount
		};
		$.ajax({
			url : "../DeviceController/getThermSpread",
			data : dataFilter,
			type : "post",
			dataType : "json",
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			success : function(data) {
				realheatMap.clearOverlays();
				if(data!=null && data!=undefined){
					var points = [];
					for(var key in data){
						points = data[key];
					}
					if(points.length>0){
						openHeatRealMapFunc(points)
					}
				}
			}
		});
}

//定时发送消息
var realheatMaptimer = null ;
//连接数
var connects = 0 ;
//是否有返回值
var isReturnData = false;
/*查询列表信息*/
function searchRealHeatMapFunc() {
	var station = $('#mytree').tree('getSelected');
	var gpspoints = [];
	var levelflag ="";
	var heatmaptreeList = [];
	var heatmapselected  = null;
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
	var dataFilter = 
		{
			"projectId":$("#deviceProjectId").combobox('getValue'),
			"list" : heatmaptreeList,
			"levelflag" : levelflag,
			"nostatus" : "",
			"select" : heatmapselected,
			"dataType":"2011",
			"thingCode":$("#realmapMonitorthingCombox").combobox('getValue')
		};
	if(isReturnData){
		$.ajax({
			url : "../DeviceController/getThermRecently",
			data : dataFilter,
			type : "post",
			dataType : "json",
			timeout : 600000,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				isReturnData = false;
			},
			success : function(data) {
				realheatMap.clearOverlays();
				if(data!=null && data!=undefined){
					isReturnData = true;
					var points = [];
					for(var key in data){
						points = data[key];
					}
					if(points.length>0){
						openHeatRealMapFunc(points)
					}
				}else{
					isReturnData = false;
				}
			}
		});
	}
}



var alarmheatamapRange = {};
//$(function() {
	var  realcomboboxJsonMul = [];
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
					realcomboboxJsonMul.push(json[i]);
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
	$("#realmapMonitorthingCombox").combobox({
		  data:realcomboboxJsonMul,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  value:realcomboboxJsonMul[0].code,
		  onShowPanel: function () {
		      // 动态调整高度  
		      if (realcomboboxJsonMul.length < 20) {
		          $(this).combobox('panel').height("auto");
		      }else{
		          $(this).combobox('panel').height(300);
		      }
		  }
    });
	/*$("#mytree")
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
								selectedSearchData();
							}
						}
					});*/
//});
