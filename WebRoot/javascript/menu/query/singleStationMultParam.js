/************************************
 * 功能：单站点多物质查询
 * 日期：2016-3-24 09:43:09
 ************************************/
var appendcontent = '<div id="querySingleTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="按分钟统计"  selected="true" style="padding:10px" id="perminute"></div>'
	+ '<div title="按小时统计"  style="padding:10px" id="perhour"></div>'
	+ '<div title="按日统计"  style="padding:10px" id="perday"></div>'
	+ '<div title="按月统计"  style="padding:10px" id="permonth"></div>'
	+ '<div title="按季度统计"  style="padding:10px" id="perquarter"></div>'
	+ '</div>';
addPanel("单站点多物质查询", appendcontent);
var singlecurrTab =$('#mytab').tabs('getSelected'); 
var singletitle = singlecurrTab.panel('options').title;
/*if(singletitle=="单站多参数查询"){
	$("#mytree").tree({
		checkbox:false
	});
}*/
var singleMonitorList = [];
var comboboxJson = [];
var preStationSingle = null;
var singleChartBtn = true;
//存储监控物的范围值
var alarmRangeSingle = {};
var getthingNameJson = {};
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJson = json;
		for(var i=0;i<json.length;i++){
			getthingNameJson[json[i].code] = json[i].name;
			singleMonitorList.push(json[i].code);
		}
	}
});
//筛选设备监测物
function filterSingleStationMonitors(){
	var currTab =$('#querySingleTab').tabs('getSelected'); 
	var title = currTab.panel('options').title;
	var id="perminute";
	if(title=="按分钟统计"){
		id="perminute";
	}else if(title=="按小时统计"){
		id="perhour";
	}else if(title=="按日统计"){
		id="perday";
	}else if(title=="按月统计"){
		id="permonth";
	}else{
		id="perquarter";
	}
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
			$("#monitorThings"+id+"").combobox('clear');
			comboboxJson = [];
			getthingNameJson = {};
			singleMonitorList = [];
			if(json.length>0){
				comboboxJson = json;
				for(var i=0;i<json.length;i++){
					getthingNameJson[json[i].code] = json[i].name;
					singleMonitorList.push(json[i].code);
					$("#monitorThings"+id+"").combobox('loadData',comboboxJson);
				}
			}else{
				$("#monitorThings"+id+"").combobox('loadData',comboboxJson);
			}
		}
	});
}
/*获取报警线*/
function getSingleMonitorAlarmLine(){
	var stationlist = [] ;
	var selectedStation = $('#mytree').tree('getSelected');
	preStationSingle = selectedStation.id;
	if(selectedStation!=null && selectedStation!=undefined){
		stationlist.push(selectedStation.id);
	}else{
		stationlist.push(-1);
	}
	if(singleMonitorList!=undefined && singleMonitorList.length<=0){
		singleMonitorList.push(-1);
	}
	if(singleMonitorList!=undefined){
		$.ajax({
			url : "../DeviceAlarmSetController/getDeviceAlarmLineThgName",
			type : "post",
			dataType : "json",
			async:false,
			data:{"listdev":stationlist,"listthg":singleMonitorList},
			success : function(json) {
				if(json){
					alarmRangeSingle = json[selectedStation.id];
				}
			}
		});
	}
}
tabContent("按分钟统计","perminute");
tabContent("按小时统计","perhour");
tabContent("按日统计","perday");
tabContent("按月统计","permonth");
tabContent("按季度统计","perquarter");

function getContent(title,id){
	var contents = "";
	if("按分钟统计"==title || "按小时统计"==title || "按日统计"==title ||"按月统计"==title){
		contents ='<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
			    +'<div id="tbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation'+id+'"  style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'监控物质：<input id="monitorThings'+id+'" class="easyui-combobox" style="width:150px;">'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterSingleStationMonitors()" title="筛选监控点监控物"></a>'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtStartTime'+id+'" data-options="required:true" style="width:104px;"/>'
				+'<span>&nbsp;至&nbsp;</span>'
				+'<input class="easyui-datebox" id="dtEndTime'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchSingleBtnFunction()" id="singleList'+id+'">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartFunction()" id="singleChart'+id+'">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" style="margin:0px 10px;"  onclick="exportFunction()">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent'+id+'">'
				+'<div id="searchContent'+id+'"></div>'
			+'</div>'
		+'</div>'
	}else if("按季度统计"==title){
		contents ='<div class="easyui-layout" data-options="fit:true" >'
			+'<div data-options="region:\'north\',border:false">'
			+'<div id="tbQuery'+id+'" style="padding:5px 8px;">'
			+'监控站点：<label id="monitorStation'+id+'" style="width:150px;">无</label>'
			+'&nbsp;&nbsp;&nbsp'
			+'监控物质：<input id="monitorThings'+id+'" class="easyui-combobox" style="width:150px;">'
			+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterSingleStationMonitors()" title="筛选监控点监控物"></a>'
			+'&nbsp;&nbsp;&nbsp;查询范围：&nbsp;&nbsp;<input class="easyui-combobox" id="dtStartTime'+id+'" data-options="required:true"  style="width:70px;"/>'
			+ '&nbsp;&nbsp;&nbsp;季度&nbsp;<input class="easyui-combobox" id="startperquarterdt" data-options="required:true"  style="width:80px;"/>'
			+'<span>&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</span>'
			+'&nbsp;&nbsp<input class="easyui-combobox" id="dtEndTime'+id+'" data-options="required:true" style="width:70px;"/>'	
			+'&nbsp;&nbsp;&nbsp;季度&nbsp;<input class="easyui-combobox" id="endperquarterdt" data-options="required:true"  style="width:80px;"/>'
            +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchSingleBtnFunction()" id="singleList'+id+'">列表</a>'
			+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartFunction()" id="singleChart'+id+'">图像</a>'
			+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="exportFunction()">导出</a>'
			+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent'+id+'">'
			+'<div id="searchContent'+id+'"></div>'
			+'</div>'
			+'</div>';
	}
	return contents;
}

/*tab页内容*/
function tabContent(title,id){
	var contents = getContent(title,id);
	$('#'+id).append(contents);
	$.parser.parse('#'+id);
	var dataCount = 0;
	$("#monitorThings"+id+"").combobox({
		  data:comboboxJson,
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
		      });
		      // 动态调整高度  
		      if (dataCount < 10) {  
		    	  $(this).combobox('panel').height("auto");  
		      }else{
		    	  $(this).combobox('panel').height(300);
		      }
		  },
		  onLoadSuccess: function (data) {
		      var opts = $(this).combobox('options');
			  var target = this;
			  var values = $(target).combobox('getValues');
			  $.map(values, function (value) {
			      var el = opts.finder.getEl(target, value);
			      el.find('input.combobox-checkbox')._propAttr('checked', true);
			      });
		      dataCount = data.length;
			  if(data != null && data.length>0){
			      $('#monitorThings'+id).combobox('setValue',data[0].code);
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
	if(title=="按月统计"){
		createDateboxByYYMM("dtStartTime"+id);
		createDateboxByYYMM("dtEndTime"+id);
	}else if(title=="按季度统计"){
		var datatimejson = [];
		var myDate = new Date();
		var perquarterJson = [{"id":1,"name":"第一季度"},
		                      {"id":2,"name":"第二季度"},
		                      {"id":3,"name":"第三季度"},
		                      {"id":4,"name":"第四季度"}];
		//获取当前年份(2位)
		for (var i = myDate.getYear()-100; i >0; i--) {
			datatimejson.push({"id" : 2000+i,"name" : 2000+i});
		}
		$('#dtStartTimeperquarter').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#startperquarterdt').combobox({
			data:perquarterJson,
			valueField : 'id',
			textField : 'name',
			panelHeight:'auto'
		});
		$('#endperquarterdt').combobox({
			data:perquarterJson,
			valueField : 'id',
			textField : 'name'
		});
		$('#dtEndTimeperquarter').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name',
			panelHeight:'auto'
		});
		$('#dtStartTimeperquarter').combobox('setValue',datatimejson[0].id);
		$('#dtEndTimeperquarter').combobox('setValue',datatimejson[0].id);
		$('#startperquarterdt').combobox('setValue',perquarterJson[0].id);
		$('#endperquarterdt').combobox('setValue',perquarterJson[0].id);
	}
	if(title!="按季度统计"){
			$("#dtStartTime"+id).datebox('setValue',formatterDate(new Date()));
			$("#dtEndTime"+id).datebox('setValue',formatterDate(new Date()));
	}
    setCheckTitle();
}
var myChart = null;
/*查询列表信息*/
function searchSingleBtnFunction(id) {
	singleChartBtn = false;
	var treeid = -1;
	var id = "perhour";
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		treeid = -1;
		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
		return false;
	}else{
		if(station.isDevice){
			treeid = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	var currTab =$('#querySingleTab').tabs('getSelected'); 
	var title = currTab.panel('options').title;
	var id="perminute";
	if(title=="按分钟统计"){
		id="perminute";
	}else if(title=="按小时统计"){
		id="perhour";
	}else if(title=="按日统计"){
		id="perday";
	}else if(title=="按月统计"){
		id="permonth";
	}else{
		id="perquarter";
	}
	$("#monitorStation"+id).html(station.text);
	//清空图标内容的处理，暂时这样处理
	var dom = document.getElementById("searchContent"+id);
	dom.innerHTML = "";
	var monitorThings = $('#monitorThings'+id).combobox('getValues');
	if(monitorThings==""){
		$.messager.alert("提示", "请选择一个监控物！！", "error");
		return false;
	}
	initsingleStationList(id);
	var startTime = null;
	var endTime = null;
	var monitorThingsTexts = ($('#monitorThings'+id).combobox('getText')).split(",");
	if(id=="perquarter"){
		startTime = $("#dtStartTimeperquarter").combobox("getValue") + "-0" +  $("#startperquarterdt").combobox("getValue");
		endTime = $("#dtEndTimeperquarter").combobox("getValue") + "-0" + $("#endperquarterdt").combobox("getValue");
	}else{
		startTime = $('#dtStartTime'+id).datebox("getValue");
		endTime = $('#dtEndTime'+id).datebox("getValue");
	}
	if(preStationSingle!=station.id){
		getSingleMonitorAlarmLine();
	}
	var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	$.ajax({
		url : "../MonitorStorageController/getPerStatisticsData",
		type : "post",
		dataType : "json",
		data : {
			"devicecode": treeid,
			"list": monitorThings,
			"starttime":startTime,
			"endtime":endTime,
			"freque":id,
			"zsFlag":zsFlag
		},
		success : function(datajson) {
			var test = [];
			for(var i=0;i<monitorThings.length;i++){
				var name = getthingNameJson[monitorThings[i]];
				var mytitle = name;
				var alarmValue = null;
				if(alarmRangeSingle[name]!=undefined && alarmRangeSingle[name].max!=undefined){
					alarmValue = alarmRangeSingle[name].max;
					/*if(alarmValue != null){
						mytitle = name +"(报警上限"+alarmValue+")";
					}*/
				}
				test.push({field : name,title :mytitle,width:200,halign : 'center',align : 'center',
						formatter: function(value, row, index){
							if(value!=undefined){
                                var zvalue = (row[(this.field + "-zs")] == null) ? "---" : (row[(this.field + "-zs")]);
                                return tableShowHandler(value, this.field, zsFlag, zvalue,alarmRangeSingle);
							}else{
								return "---";
							}
						}
				});
			}
			if(datajson.length>0){
				/* 初始化列表 */
				$("#searchContent"+id).datagrid({
					view:myview,
					fit : true,
					border : true,
					pagination : true,
					fitColumns : true,
					singleSelect : true,
					pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
					pageSize : 50,
					autoRowHeight : false,
					rownumbers : true,
					frozenColumns:[[{field : 'time',title : '时间',width : 160,halign : 'center',align : 'center'}]],
					columns : [test],
					data:datajson.slice(0,50)
				}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
				var pager = $("#searchContent"+id).datagrid("getPager");  
				pager.pagination({  
					total:datajson.length,  
					onSelectPage:function (pageNo, pageSize) {  
						var start = (pageNo - 1) * pageSize;  
						var end = start + pageSize;  
						$("#searchContent"+id).datagrid("loadData",datajson.slice(start, end));  
						pager.pagination('refresh', {  
							total:datajson.length,  
							pageNumber:pageNo  
						});  
					}
				});
			}else{
				$("#searchContent"+id).datagrid('loadData', { total: 0, rows: [] });  
			}
			singleChartBtn = true;
		}
	});
	
}

function filterSearchFunc(monitorThingsTexts){
	var flag = true;
	var strend = monitorThingsTexts[0].substring(monitorThingsTexts[0].length-3);
	for(var i=1;i<monitorThingsTexts.length;i++){
		var value = monitorThingsTexts[i].substring(monitorThingsTexts[i].length-3);
		if(strend==value){
			flag = true;
		}else{
			flag = false;
			break;
		}
	}
	return flag;
}

/*查询图表信息*/
function searchChartFunction() {
	if(!singleChartBtn){
		return false;
	}
	var datajson = [];
	var treeid = -1;
	var id = "perhour";
	var startTime = null;
	var endTime = null;
	var station = $('#mytree').tree('getSelected');
	var currTab = $('#querySingleTab').tabs('getSelected');
	var title = currTab.panel('options').title;
	if (title == "按分钟统计") {
		id = "perminute";
	} else if (title == "按小时统计") {
		id = "perhour";
	} else if (title == "按日统计") {
		id = "perday";
	} else if (title == "按月统计") {
		id = "permonth";
	} else {
		id = "perquarter";
	}
	$("#singleChart"+id).attr("disabled","disabled");
	var Centercontent = $("#centerContent" + id);
	Centercontent.html("");
	$("#centerContent" + id).append(
			'<div id="searchContent' + id + '" style="height:400px;"></div>');
	var monitorThingsTexts = ($('#monitorThings' + id).combobox('getText'))
			.split(",");
	var monitorThings = $('#monitorThings' + id).combobox('getValues');
	if (station == null || station == undefined) {
		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
		ajaxLoadEnd();
		return false;
	}
	if (monitorThings == "") {
		$.messager.alert("提示", "请选择一个监控物！！", "error");
		ajaxLoadEnd();
		return false;
	}
	$("#monitorStation"+id).html(station.text);
	var yname = "";// 图表y轴名称
	var units = "";// 监控物单位
	if (!filterSearchFunc(monitorThingsTexts)) {
		$.messager.alert("提示", "当前监控物为多种类，请勾选同一类监控物进行查询！", "warning");
		ajaxLoadEnd();
		return false;
	} else {
		if (monitorThingsTexts.length > 1) {
			yname = monitorThingsTexts[0].substring(monitorThingsTexts[0]
					.indexOf('('), monitorThingsTexts[0].length);
		} else {
			yname = monitorThingsTexts[0];
		}
	}
	if (id == "perquarter") {
		startTime = $("#dtStartTimeperquarter").combobox("getValue") + "-0"
				+ $("#startperquarterdt").combobox("getValue");
		endTime = $("#dtEndTimeperquarter").combobox("getValue") + "-0"
				+ $("#endperquarterdt").combobox("getValue");
	} else {
		startTime = $('#dtStartTime' + id).datebox("getValue");
		endTime = $('#dtEndTime' + id).datebox("getValue");
	}
	if (station == null || station == undefined) {
		treeid = -1;
	} else {
		if(station.isDevice){
			treeid = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	var timelist = {};
	var legendData = [];
	var seriesData = [];
	if(preStationSingle!=station.id){
		getSingleMonitorAlarmLine();
	}
	var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	ajaxLoading();
	$.ajax({
		url : "../MonitorStorageController/getSingleDeviceStatisticsChartData",
		type : "post",
		dataType : "json",
		async : true,
		data : {
			"devicecode" : treeid,
			"list" : monitorThings,
			"starttime" : startTime,
			"endtime" : endTime,
			"freque" : id,
			"zsFlag":zsFlag
		},
		error : function(json) {
			ajaxLoadEnd();
			$("#singleList"+id).removeAttr("disabled");
		},
		success : function(json) {
			ajaxLoadEnd();
			if (json.time != undefined) {
				var timeArry = json["time"];
				var max = null;
				for ( var index in json) {
					if (index != "time") {
						legendData.push(index);
						if (alarmRangeSingle[index] != undefined) {
							max = getMax(alarmRangeSingle[index]);
						}else{
							max = 0;
						}
						seriesData.push({
							"name" : index,
							"type" : 'line',
							"data" : json[index],
							markPoint : {
								data : [ {
									type : 'max',
									name : '最大值'
								},{
									type : 'min',
									name : '最小值'
								} ]
							},
							markLine : {
								itemStyle : {
									normal : {
										lineStyle : {
											width : 2
										}
									}
								},
								data : [ [ {
									name : '报警线',
									value : max,
									xAxis : 0,
									yAxis : max
								}, {
									name : '报警线',
									xAxis : timeArry[timeArry.length - 1],
									yAxis : max
								}] ]
							}
						});
					} else {
						timelist[index] = json[index];
					}
				}
				initChart(timelist, legendData, seriesData, id, yname);
			} else {
				$.messager.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
			}
			$("#singleList"+id).removeAttr("disabled");
		}
	});
}

/*初始化表格*/
function initChart(timelist,legendData,seriesData,id,yname){
	var dom = document.getElementById("searchContent"+id);
	dom.style.cssText = "height:100%";
	myChart = echarts.init(document.getElementById("searchContent"+id));
	var option = {
		    title: {
		        text: '图表',
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
	myChart.setOption(option);
    window.onresize=function() {
        if(myChart!=null){
            myChart.resize();
        }
    };
}

/*界面初始时用*/
function initsingleStationList(id){
	$("#searchContent"+id).datagrid({
     view:myview,
   	 fit : true,
   	 border : true,
   	 pagination : true,
   	 fitColumns : true,
   	 singleSelect : true,
   	 pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
   	 pageSize : 50,
   	 autoRowHeight : false,
   	 rownumbers : false,
     frozenColumns:[[{field : 'time',title : '时间',width : 160,halign : 'center',align : 'center'}]],
   	 columns : [],
   	 onLoadSuccess:function (){
   		$("#searchContent"+id).datagrid("loaded");
	 }
    }).datagrid('loading');
}

/*导出*/
function exportFunction() {
	var datajson = [];
	var devicecode = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
		return false;
	}
	var currTab =$('#querySingleTab').tabs('getSelected'); 
	var title = currTab.panel('options').title;
	var id="perminute";
	if(title=="按分钟统计"){
		id="perminute";
	}else if(title=="按小时统计"){
		id="perhour";
	}else if(title=="按日统计"){
		id="perday";
	}else if(title=="按月统计"){
		id="permonth";
	}else{
		id="perquarter";
	}
	var thingcode = $('#monitorThings'+id).combobox('getValues');
	if(thingcode.length<=0){
		$.messager.alert("提示", "请选择一个监控物！！", "error");
		return false;
	}
	var startTime = null;
	var endTime = null;
	var thingcodeTexts = ($('#monitorThings'+id).combobox('getText')).split(",");
	if(id=="perquarter"){
		startTime = $("#dtStartTimeperquarter").combobox("getValue") + "-0" +  $("#startperquarterdt").combobox("getValue");
		endTime = $("#dtEndTimeperquarter").combobox("getValue") + "-0" + $("#endperquarterdt").combobox("getValue");
	}else{
		startTime = $('#dtStartTime'+id).datebox("getValue");
		endTime = $('#dtEndTime'+id).datebox("getValue");
	}
    var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	if(station==null || station==undefined){
		devicecode = -1;
	}else{
		if(station.isDevice){
			devicecode = station.id
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	location.href = "../ExportController/exportMultiThings?devicecode="+devicecode+"&thingcode="+thingcode+"&starttime="+startTime+"&endtime="+endTime+"&freque="+id+"&zsFlag="+zsFlag;
}