/************************************
 * 功能：气象数据值分析
 * 日期：2016-3-24 09:43:09
 ************************************/
var appendcontent = '<div id="weatherValueTab" class="easyui-layout" data-options="fit:true">'
	+'<div data-options="region:\'north\',border:false">'
	+'<div id="tbWeatherValueTab" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
	+'监控区域：<a id="weatherMonitorCompany"  style="width:180px;margin:8px" href="#" >暂无</a>'
	+'&nbsp;&nbsp;监控站点：<a id="weatherMonitorStation"  style="width:180px;margin:8px" href="#" >暂无</a>'
	+'&nbsp;&nbsp;监控物质：<input id="weatherMonitorThings" class="easyui-combobox" style="width:150px;">'
	+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterWeatherValueMonitors()" title="筛选监控点监控物"></a>'
	+'&nbsp;&nbsp;气象参数：<input id="weatherThings" class="easyui-combobox" style="width:150px;">'
	+'&nbsp;&nbsp;时间范围：<input class="easyui-datebox" id="dtWeatherDayTime" data-options="required:true" style="width:104px;"/>'	
//	+'&nbsp;&nbsp;<a id="reallist"  class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchRealBtnFunction()" href="#" >列表</a>'
	+'<a class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchWeatherValueChart()" href="#" >图表</a>'
	+'</div>'
	+'</div>'
	+'<div data-options="region:\'center\',border:false,fit:true" id="centerContentWeatherValue">'
	+'<div id="searchWeatherValue"></div>'
	+'</div>'
	+'</div>'
addPanel("气象数据值分析", appendcontent);
var localId = "perhour";

var singleWeatherMonitorList = [];
var wtComboboxJson = [];
var weatherEMonitorList = [];
var wwComboboxJson = [];

$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		wtComboboxJson = json;
		for(var i=0;i<json.length;i++){
			singleWeatherMonitorList.push(json[i].code);
		}
	}
});
//筛选设备监测物
function filterWeatherValueMonitors(){
	var devicecode = "";
	var station = $('#mytree').tree('getSelected');
	if(station != null || station != undefined){
		if(station.isDevice){
			devicecode = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
			return false;
		}
	}else{
		$.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
		return false;
	}
	ajaxLoading();
	$.ajax({
		url : "../MonitorStorageController/getAthorityDeviceMonitors",
		type : "post",
		dataType : "json",
		data:{
			"deviceCode":devicecode
		},
		error:function(){
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			$("#weatherMonitorThings").combobox('clear');
			wtComboboxJson = [];
			if(json.length>0){
				wtComboboxJson = json;
				for(var i=0;i<json.length;i++){
					singleWeatherMonitorList.push(json[i].code);
				}
				$("#weatherMonitorThings").combobox('loadData',wtComboboxJson);
				if(wtComboboxJson.length>0){
					$("#weatherMonitorThings").combobox('setValues',wtComboboxJson[0].code);
				}
			}else{
				$("#weatherMonitorThings").combobox('loadData',wtComboboxJson);
			}
		}
	});
}
$.ajax({
	url : "../MonitorStorageController/getHeWeatherMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		wwComboboxJson = json;
		for(var i=0;i<json.length;i++){
			weatherEMonitorList.push(json[i].code);
		}
	}
});

initWeatherPageData();

function initWeatherPageData(){
	$("#dtWeatherDayTime").datebox('setValue',formatterDate(new Date()));
	$("#weatherMonitorThings").combobox({
		  data:wtComboboxJson,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  panelHeight:'auto',
		  multiple:true,
		  formatter: function (row) {
		      var opts = $(this).combobox('options');
		  return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
		  },
		  onShowPanel: function () {
		      var opts = $(this).combobox('options');
		  var target = this;
		  var values = $(target).combobox('getValues');
		  $.map(values, function (value) {
		      var el = opts.finder.getEl(target, value);
		      el.find('input.combobox-checkbox')._propAttr('checked', true);
		      })
		  },
		  onLoadSuccess: function (data) {
		      var opts = $(this).combobox('options');
			  var target = this;
			  var values = $(target).combobox('getValues');
			  $.map(values, function (value) {
			      var el = opts.finder.getEl(target, value);
			      el.find('input.combobox-checkbox')._propAttr('checked', true);
			      });
			  if(data != null && data.length>0){
			       $("#weatherMonitorThings").combobox('setValue',data[0].code);
			  }
		  },
		  onSelect: function (row) {
		      var opts = $(this).combobox('options');
			  var el = opts.finder.getEl(this, row[opts.valueField]);
			  el.find('input.combobox-checkbox')._propAttr('checked', true);
		  },
		  onUnselect: function (row) {
		      var opts = $(this).combobox('options');
			  var el = opts.finder.getEl(this, row[opts.valueField]);
			  el.find('input.combobox-checkbox')._propAttr('checked', false);
		  }
	});
	$("#weatherThings").combobox({
		  data:wwComboboxJson,
		  method:'post',
		  valueField:'code',
		  textField:'name',
		  panelHeight:'auto',
		  multiple:true,
		  formatter: function (row) {
		      var opts = $(this).combobox('options');
		  return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
		  },
		  onShowPanel: function () {
		      var opts = $(this).combobox('options');
		  var target = this;
		  var values = $(target).combobox('getValues');
		  $.map(values, function (value) {
		      var el = opts.finder.getEl(target, value);
		      el.find('input.combobox-checkbox')._propAttr('checked', true);
		      })
		  },
		  onLoadSuccess: function (data) {
		      var opts = $(this).combobox('options');
		  var target = this;
		  var values = $(target).combobox('getValues');
		  $.map(values, function (value) {
		      var el = opts.finder.getEl(target, value);
		      el.find('input.combobox-checkbox')._propAttr('checked', true);
		      })
		       $("#weatherThings").combobox('setValue',data[0].code);
		  },
		  onSelect: function (row) {
		      var opts = $(this).combobox('options');
			  var el = opts.finder.getEl(this, row[opts.valueField]);
			  el.find('input.combobox-checkbox')._propAttr('checked', true);
		  },
		  onUnselect: function (row) {
		      var opts = $(this).combobox('options');
			  var el = opts.finder.getEl(this, row[opts.valueField]);
			  el.find('input.combobox-checkbox')._propAttr('checked', false);
		  }
	});
	
}

/*图表*/
function searchWeatherValueChart(){
	if(initMonitorsflag){
		initMonitorsflag = false;
	}
	stopajax = true;
	isChart = true;
	var monitorThings = $('#weatherMonitorThings').combobox('getValues');
	if (monitorThings == "") {
		$.messager.alert("提示", "请选择一个监控物！！", "error");
		ajaxLoadEnd();
		return false;
	}
	var weatherThings = $('#weatherThings').combobox('getValues');
	var datajson = [];
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		treeid = -1;
		$.messager.alert("提示", "请选择一个监测站点进行查询！", "error");
		return false;
	}else{
		if(station.isDevice){
			treeid = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	var parent=$('#mytree').tree('getParent',station.target);
	$("#weatherMonitorCompany").text(parent.text);
	$("#weatherMonitorStation").text(station.text);
//	if(station==null || station==undefined){
//		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
//		ajaxLoadEnd();
//		return false;
//	}
	if(station==null || station==undefined){
		treeid = -1;
	}else{
		treeid = station.id
	}
	var beginTime = $('#dtWeatherDayTime').datebox("getValue");
	var endTime = $('#dtWeatherDayTime').datebox("getValue");
	var yname = "";//图表y轴名称
	var units = "";//监控物单位
	var timelist = {};
	var legendData = [];
	var seriesData = [];
	ajaxLoading();
	$.ajax({
		url : "../MonitorStorageController/getSingleStationWeatherChartData",
		type : "post",
		dataType : "json",
		data : {
			"devicecode" : treeid,
			"things" : monitorThings,
			"weathers":weatherThings,
			"begintime" : beginTime,
			"endtime" : endTime,
			"freque" : localId
		},
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			var timeArry = json["time"];
			if(json.time!=undefined){
				for(var index in json){
					if(index!="time"){
						legendData.push(index);
						seriesData.push({
							"name":index,
							"type":'line',
							"data":json[index],
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            },
							 markLine: {
					                data : [
						                    {type : 'average', name: '平均值'}
						                ]
				            }
						});
					}else{
						timelist[index] = json[index];
					}
				}
				initChartWeatherValue(timelist,legendData,seriesData,yname);
			}else{
				$.messager.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
			}
		}
	});
}
var myChart = null;
/*初始化表格*/
function initChartWeatherValue(timelist,legendData,seriesData,yname){
	var dom = document.getElementById("searchWeatherValue");
	dom.style.cssText = "height:95%";
	if(myChart == null){
		myChart = echarts.init(document.getElementById("searchWeatherValue"));
	}
	var option = {
		    title: {
		        text: '气象参数不保证全天可用',
	        	textStyle: {
	                  fontSize: 18,
	                  fontWeight: 'bolder',
	                  color: '#333'          // 主标题文字颜色
	            }
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:legendData
		    },
		    toolbox: {
		        show : true,
		        orient:	'vertical',
		        y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            dataZoom: {},
		            saveAsImage : {show: true}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: timelist["time"],
		        axisLine : {    // 轴线
	                show: true,
	                lineStyle: {
	                    color: 'green',
	                    type: 'solid',
	                    width: 2
	                }
	            },
		        axisLabel : {
	                show:true,
	                textStyle: {
	                    color: 'green',
	                    fontFamily: 'sans-serif',
	                    fontSize: 13,
	                    fontWeight: 'bold'
	                }
	            }
		    },
		    yAxis: {
		    	name:yname,
		        type: 'value',
		        axisLine : {    // 轴线
	                show: true,
	                lineStyle: {
	                    color: 'green',
	                    type: 'solid',
	                    width: 2
	                }
	            },
		        axisLabel: {
		            formatter: '{value}',
		            textStyle: {
	                    color: 'green',
	                    fontFamily: 'sans-serif',
	                    fontSize: 13,
	                    fontWeight: 'bold'
	                }
		        }
		    },
		    series:seriesData
		};
	myChart.setOption(option,true);
}
