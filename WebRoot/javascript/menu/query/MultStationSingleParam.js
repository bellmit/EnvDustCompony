/************************************
 * 功能：多站点单物质查询
 * 日期：2016-3-24 09:43:09
 ************************************/
var appendcontentMuls = '<div id="queryMultTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="按分钟统计" selected="true" style="padding:10px" id="Mulperminute"></div>'
	+ '<div title="按小时统计" style="padding:10px" id="Mulperhour"></div>'
	+ '<div title="按日统计"  style="padding:10px" id="Mulperday"></div>'
	+ '<div title="按月统计"  style="padding:10px" id="Mulpermonth"></div>'
	+ '<div title="按季度统计"  style="padding:10px" id="Mulperquarter"></div>'
	+ '</div>';
addPanel("多站点单物质查询", appendcontentMuls);
var MultcurrTab =$('#mytab').tabs('getSelected'); 
var Multitle = MultcurrTab.panel('options').title;
var comboboxJsonMul = [];
//存储监控物的范围值
var alarmRangeStationMUl = {};
var getthingNameMULJson = {};
var muLMonitorList = [];
var mulChartBtn = true;
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonMul = json;
		for(var i=0;i<json.length;i++){
			//alarmRangeMUl[json[i].name] = {"max":json[i].max,"min":json[i].min};
			getthingNameMULJson[json[i].code] = json[i].name;
			muLMonitorList.push(json[i].code);
		}
	}
});
//筛选设备监测物
function filterMulStationMonitors(){
	var currTab =$('#queryMultTab').tabs('getSelected'); 
	var title = currTab.panel('options').title;
	var id="Mulperminute";
	if(title=="按分钟统计"){
		id="Mulperminute";
	}else if(title=="按小时统计"){
		id="Mulperhour";
	}else if(title=="按日统计"){
		id="Mulperday";
	}else if(title=="按月统计"){
		id="Mulpermonth";
	}else{
		id="Mulperquarter";
	}
	var devicecode = "";
	var station = $('#mytree').tree('getChecked');
	if(station != null || station != undefined){
		for(var i=0;i<station.length;i++){
			if(station[i] != null && station[i].isDevice){
				devicecode = station[i].id;
				break;
			}
		}
		if(devicecode == null || devicecode == ""){
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
			$("#MulmonitorThings"+id+"").combobox('clear');
			comboboxJsonMul = [];
			if(json.length>0){
				comboboxJsonMul = json;
				for(var i=0;i<json.length;i++){
					getthingNameMULJson[json[i].code] = json[i].name;
					muLMonitorList.push(json[i].code);
				}
				$("#MulmonitorThings"+id+"").combobox('loadData',comboboxJsonMul);
				if(comboboxJsonMul.length>0){
					$("#MulmonitorThings"+id+"").combobox('setValues',comboboxJsonMul[0].code);
				}
			}else{
				$("#MulmonitorThings"+id+"").combobox('loadData',comboboxJsonMul);
			}
		}
	});
}
/*获取报警线*/
function getMULMonitorAlarmLine(mulstationlist){
	if(muLMonitorList!=undefined && muLMonitorList.length<=0){
		muLMonitorList.push(-1);
	}
	if(muLMonitorList!=undefined){
		$.ajax({
			url : "../DeviceAlarmSetController/getDeviceAlarmLineDevName",
			type : "post",
			dataType : "json",
			async:false,
			data:{"listdev":mulstationlist,"listthg":muLMonitorList},
			success : function(json) {
				if(json){
					alarmRangeStationMUl = json;
				}
			}
		});
	}
}

tabContentMuls("按分钟统计","Mulperminute");
tabContentMuls("按小时统计","Mulperhour");
tabContentMuls("按日统计","Mulperday");
tabContentMuls("按月统计","Mulpermonth");
tabContentMuls("按季度统计","Mulperquarter");

function getContentMuls(title,id){
	var contents = "";
	if("按分钟统计"==title || "按小时统计"==title || "按日统计"==title ||"按月统计"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="MultbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控物质：<input id="MulmonitorThings'+id+'" class="easyui-combobox" style="width:150px;">'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterMulStationMonitors()" title="筛选监控点监控物"></a>'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtStartTime'+id+'" data-options="required:true" style="width:104px;"/>'
				+'<span>&nbsp;至&nbsp;</span>'
				+'<input class="easyui-datebox" id="dtEndTime'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchBtnMulsFunction()">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchChartMulFunction()">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="exportMulFunction()">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent'+id+'">'
				+'<div id="searchContent'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("按季度统计"==title){
		contents = '<div class="easyui-layout" data-options="fit:true">'
		+'<div data-options="region:\'north\',border:false">'
		+'<div id="MultbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
		+'监控物质：<input id="MulmonitorThings'+id+'" class="easyui-combobox" style="width:150px;">'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterMulStationMonitors()" title="筛选监控点监控物"></a>'
		+'&nbsp;&nbsp;&nbsp;查询范围：&nbsp;&nbsp;<input class="easyui-combobox" id="dtStartTime'+id+'" data-options="required:true"  style="width:70px;"/>'
		+ '&nbsp;&nbsp;&nbsp;季度&nbsp;<input class="easyui-combobox" id="startMulperquarterdt" data-options="required:true"  style="width:80px;"/>'
		+'<span>&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;</span>'
		+'&nbsp;&nbsp<input class="easyui-combobox" id="dtEndTime'+id+'" data-options="required:true" style="width:70px;"/>'	
		+'&nbsp;&nbsp;&nbsp;季度&nbsp;<input class="easyui-combobox" id="endMulperquarterdt" data-options="required:true"  style="width:80px;"/>'
			+'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchBtnMulsFunction()">列表</a>'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartMulFunction()">图像</a>'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="exportMulFunction()">导出</a>'
		+'</div>'
		+'</div>'
		+'<div data-options="region:\'center\',border:false" id="centerContent'+id+'">'
		+'<div id="searchContent'+id+'"></div>'
		+'</div>'
		+'</div>';
	}
	return contents;
}

//tab页内容
function tabContentMuls(title,id){
	var contents = getContentMuls(title,id);
	$('#'+id).append(contents);
	$.parser.parse('#'+id);
	$("#MulmonitorThings"+id+"").combobox({
		  data:comboboxJsonMul,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  panelHeight:'auto',
		  value:comboboxJsonMul[0].code,
		  onShowPanel: function () {
		      // 动态调整高度  
		      if (comboboxJsonMul.length < 20) {
		          $(this).combobox('panel').height("auto");  
		      }else{
		          $(this).combobox('panel').height(300);
		      }
		  }
    });
   /* if(getthingNameMULJson[comboboxJsonMul[0].code]!=undefined){
    	var max = alarmRangeMUl[getthingNameMULJson[comboboxJsonMul[0].code]].max;
    	$("#MulmonitorThingsAlarm"+id).html(max);
	}*/
	if(title=="按月统计"){
		createDateboxByYYMM("dtStartTime"+id);
		createDateboxByYYMM("dtEndTime"+id);
	}else if(title=="按季度统计"){
		var datatimejson = [];
		var myDate = new Date();
		var MulperquarterJson = [{"id":1,"name":"第一季度"},
		                      {"id":2,"name":"第二季度"},
		                      {"id":3,"name":"第三季度"},
		                      {"id":4,"name":"第四季度"}];
		//获取当前年份(2位)
		for (var i = myDate.getYear()-100; i >0; i--) {
			datatimejson.push({"id" : 2000+i,"name" : 2000+i});
		}
		$('#dtStartTime'+id).combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#startMulperquarterdt').combobox({
			data:MulperquarterJson,
			valueField : 'id',
			textField : 'name',
			panelHeight:'auto'
		});
		$('#dtEndTimeMulperquarter').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#endMulperquarterdt').combobox({
			data:MulperquarterJson,
			valueField : 'id',
			textField : 'name',
			panelHeight:'auto'
		});
		$('#dtStartTime'+id).combobox('setValue',datatimejson[0].id);
		$('#startMulperquarterdt').combobox('setValue',MulperquarterJson[0].id);
		$('#dtEndTimeMulperquarter').combobox('setValue',datatimejson[0].id);
		$('#endMulperquarterdt').combobox('setValue',MulperquarterJson[0].id);
	}
	if(title!="按季度统计"){
			$("#dtStartTime"+id).datebox('setValue',formatterDate(new Date()));
			$("#dtEndTime"+id).datebox('setValue',formatterDate(new Date()));
	}
    setCheckTitle();
}

/*查询数据*/
function searchBtnMulsFunction(id) {
	mulChartBtn = false;
	var treeid = [];
	var station = $('#mytree').tree('getChecked');
	if(id==undefined || id==null){
		if(station==null || station==undefined || station==""){
			$.messager.alert("提示", "请勾选站点信息！", "error");
		    mulChartBtn = true;
			return false;
		}
		var currTab =$('#queryMultTab').tabs('getSelected'); 
		var title = currTab.panel('options').title;
		var id="Mulperminute";
		if(title=="按分钟统计"){
			id="Mulperminute";
		}else if(title=="按小时统计"){
			id="Mulperhour";
		}else if(title=="按日统计"){
			id="Mulperday";
		}else if(title=="按月统计"){
			id="Mulpermonth";
		}else{
			id="Mulperquarter";
		}
	}
	if(station==null || station==undefined || station==""){
		treeid = ["-1"];
	}else{
		for(var i=0;i<station.length;i++){
			if(station[i].isDevice){//判断是监控点
				treeid.push(station[i].id);
			}
		}
	}
	if(treeid.length>10){
		$.messager.alert("提示", "最多查询10个站点的监控物含量！", "warning");
		//$("#searchContent"+id).datagrid("loaded");
	    mulChartBtn = true;
		return false;
	}
	//清空图标内容的处理，暂时这样处理
	var dom = document.getElementById("searchContent"+id);
	dom.innerHTML = "";
	var MulmonitorThings = $('#MulmonitorThings'+id).combobox('getValue');
	if(MulmonitorThings==""){
		$.messager.alert("提示", "请选择一个监控物！！", "error");
		return false;
	}
	initPagelistMUl(id);
	var startTime = null;
	var endTime = null;
	var MulmonitorThingsTexts = $('#MulmonitorThings'+id).combobox('getText');
	if(id=="Mulperquarter"){
		startTime = $("#dtStartTime"+id).combobox("getValue") + "-0" +  $("#startMulperquarterdt").combobox("getValue");
		endTime = $("#dtEndTime"+id).combobox("getValue") + "-0" + $("#endMulperquarterdt").combobox("getValue");
	}else{
		startTime = $('#dtStartTime'+id).datebox("getValue");
		endTime = $('#dtEndTime'+id).datebox("getValue");
	}
	getMULMonitorAlarmLine(treeid);
    var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	$.ajax({
		url : "../MonitorStorageController/getSingleThingStatisticsData",
		type : "post",
		dataType : "json",
		data : {
			"list": treeid,
			"thingcode": MulmonitorThings,
			"starttime":startTime,
			"endtime":endTime,
			"freque":id.substring(3, id.length),
			"zsFlag":zsFlag
		},
		success : function(datajson) {
			var max = null;
			var mycolumns = [];
			for(var i=0;i<station.length;i++){
				if(station[i].isDevice){//判断是监控点
					var mytitle = station[i].text;
					var parentOne = $('#mytree').tree('getParent', station[i].target);
					if(parentOne != null && parentOne != undefined){
						mytitle = mytitle+"-"+parentOne.text;
					}
					mycolumns.push({field : station[i].id,title :mytitle,width:200,halign : 'center',align : 'center',
						formatter: function(value, row, index){
							if(value!=undefined){
                                var zvalue = (row[(this.field + "-zs")] == null) ? "---" : (row[(this.field + "-zs")]);
								if(this.title!="" && this.title!=undefined && alarmRangeStationMUl[this.title]!=undefined){
									var stationThingAlarmValue = alarmRangeStationMUl[this.title];
									// if(stationThingAlarmValue != null && stationThingAlarmValue != undefined && JSON.stringify(stationThingAlarmValue) != "{}"){
									 return tableShowHandler(value, this.field, zsFlag, zvalue,stationThingAlarmValue,MulmonitorThings);
									// }
								}
							}else{
								return "---";
							}
						}
					});
				}
			}
			// 初始化列表 
			$("#searchContent"+id).datagrid({
				view:myview,
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				singleSelect : true,
				pageList : [ 10,50, 100, 150, 200, 250, 300 ],
				pageSize : 50,
				autoRowHeight : false,
				rownumbers : true,
				columns : [mycolumns],
				frozenColumns:[[{field : 'time',title : '时间',width : 140,halign : 'center',align : 'center'}]],
				data: datajson.slice(0,50)
			}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
			var pager = $("#searchContent"+id).datagrid("getPager");  
		    pager.pagination({  
		        total:datajson.length,  
		        onSelectPage:function (pageNo, pageSize) {  
		            var start = (pageNo - 1) * pageSize;  
		            var end = start + pageSize;  
		            $("#searchContent"+id).datagrid("loadData", datajson.slice(start, end));  
		            pager.pagination('refresh', {  
		                total:datajson.length,  
		                pageNumber:pageNo  
		            });  
		        }  
		    }); 
		    mulChartBtn = true;
		}
	});
}

/*初始界面加载假表格用于显示*/
function initPagelistMUl(id){
	var MulmonitorThingsTexts = $('#MulmonitorThings'+id).combobox('getText');
	var test = [{field : 'thingcode',title : MulmonitorThingsTexts,width : 120,halign : 'center',align : 'center'},
	            {field : 'time',title : '时间',width : 120,halign : 'center',align : 'center'}];
	$("#searchContent"+id).datagrid({
	    view:myview,
	   	 fit : true,
	   	 border : false,
	   	 pagination : false,
	   	 fitColumns : true,
	   	 singleSelect : true,
	   	 pageList : [  50, 100, 150, 200, 250, 300 ],
	   	 pageSize : 50,
	   	 autoRowHeight : false,
	   	 rownumbers : false,
	   	 columns : [],
	   	 onLoadSuccess:function (){
	   		$("#searchContent"+id).datagrid("loaded");
		 }
	    }).datagrid('loading');
}

/*查询图表*/
function searchChartMulFunction(id) {
	if(!mulChartBtn){//判断图表按钮是否可用，防止列表查询文查询完就点击图表
		return false;
	}
	ajaxLoading();
	var datajson = [];
	var treeid = [];
	var station = $('#mytree').tree('getChecked');
	var currTab = $('#queryMultTab').tabs('getSelected');
	var title = currTab.panel('options').title;
	if (id == undefined || id == null) {
		if (station == null || station == undefined || station == "") {
			$.messager.alert("提示", "请勾选站点信息！", "error");
			ajaxLoadEnd();
			return false;
		}
		var id = "Mulperminute";
		if (title == "按分钟统计") {
			id = "Mulperminute";
		} else if (title == "按小时统计") {
			id = "Mulperhour";
		} else if (title == "按日统计") {
			id = "Mulperday";
		} else if (title == "按月统计") {
			id = "Mulpermonth";
		} else {
			id = "Mulperquarter";
		}
	}
	if (station == null || station == undefined || station == "") {
		treeid = [ "-1" ];
	} else {
		for (var i = 0; i < station.length; i++) {
			if(station[i].isDevice){//判断是监控点
				treeid.push(station[i].id);
			}
		}
	}
	if(treeid.length>10){
		$.messager.alert("提示", "最多查看10个站点的监控物含量图像！", "warning");
		ajaxLoadEnd();
		return false;
	}
	var Centercontent = $("#centerContent" + id);
	Centercontent.html("");
	$("#centerContent" + id).append(
			'<div id="searchContent' + id + '" style="height:400px;"></div>');
	var MulmonitorThings = $('#MulmonitorThings' + id).combobox('getValue');
	if(MulmonitorThings==""){
		$.messager.alert("提示", "请选择一个监控物！！", "error");
		return false;
	}
	var startTime = null;
	var endTime = null;
	var MulmonitorThingsTexts = $('#MulmonitorThings' + id).combobox('getText');
	if (id == "Mulperquarter") {
		startTime = $("#dtStartTime" + id).combobox("getValue") + "-0"
				+ $("#startMulperquarterdt").combobox("getValue");
		endTime = $("#dtEndTime" + id).combobox("getValue") + "-0"
				+ $("#endMulperquarterdt").combobox("getValue");
	} else {
		startTime = $('#dtStartTime' + id).datebox("getValue");
		endTime = $('#dtEndTime' + id).datebox("getValue");
	}
	
	var timelist = {};
	var legendData = [];
	var seriesData = [];
    var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	getMULMonitorAlarmLine(treeid);
	$.ajax({
				url : "../MonitorStorageController/getSingleThingStatisticsChartData",
				type : "post",
				dataType : "json",
				async : true,
				data : {
					"list" : treeid,
					"thingcode" : MulmonitorThings,
					"starttime" : startTime,
					"endtime" : endTime,
					"freque" : id.substring(3, id.length),
					"zsFlag":zsFlag
				},
				error : function(json) {
					ajaxLoadEnd();
				},
				success : function(json) {
					if (json.time != undefined) {
						var max = null;
						var timeArry = json["time"];
						for ( var index in json) {
							if (index != "time") {
								legendData.push(index);
								var alarmRangeMUl = alarmRangeStationMUl[index];
								if (alarmRangeMUl!= undefined) {
									if (alarmRangeMUl[MulmonitorThings] != undefined){
											max = getMax(alarmRangeMUl[MulmonitorThings]);
									}
								}
								seriesData.push({
									"name" : index,
									"type" : 'line',
									"data" : json[index],
									markPoint : {
										data : [{
													type : 'max',
													name : MulmonitorThings+ '最大值'
												},
												{
													type : 'min',
													name : MulmonitorThings+ '最小值'
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
						initChart(timelist, legendData, seriesData, id,
								MulmonitorThings, MulmonitorThingsTexts);
					} else {
						$.messager
								.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
					}
					ajaxLoadEnd();
				}
			});
}

/*初始化表格*/
function initChart(timelist,legendData,seriesData,id,MulmonitorThings,MulmonitorThingsTexts){
	var dom = document.getElementById("searchContent"+id);
	dom.style.cssText = "height:100%";
	var myChart = echarts.init(document.getElementById("searchContent"+id));
	var option = {
		    title: {
		        text: '图表',
	        	textStyle: {
	                  fontSize: 18,
	                  fontWeight: 'bolder',
	                  color: '#333'          // 主标题文字颜色
	            }
		    },
			grid:{
				left:'2%',
				right:'16%',
				bottom:'1%',
				containLabel:true
			},
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
                type: 'scroll',
                orient: 'vertical',
                right: "1%",
                "top":'8%',
		        data:legendData,
                formatter: function (name) {
                    if (!name) return '';
                    if (name.length > 9) {
                        name =  name.slice(0,9) + '...';
                    }
                    return name;
                },
                tooltip: {
                    show: true
                }
		    },
		    toolbox: {
		        show : true,
		        orient:	'vertical',
				x:"left",
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
		    	name: '时间',
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
	            },
		        splitLine : {
	                show:true,
	                lineStyle: {
	                    color: '#483d8b',
	                    type: 'dashed',
	                    width: 1
	                }
	            }
		    },
		    yAxis: {
		    	name: MulmonitorThingsTexts,
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
}

/*导出*/
function exportMulFunction() {
	var datajson = [];
	var treeid = "";
	var station = $('#mytree').tree('getChecked');
	if(station==null || station==undefined || station==""){
		$.messager.alert("提示", "请勾选站点信息！", "error");
		return false;
	}
	var currTab =$('#queryMultTab').tabs('getSelected'); 
	var title = currTab.panel('options').title;
	var id="Mulperminute";
	if(title=="按分钟统计"){
		id="Mulperminute";
	}else if(title=="按小时统计"){
		id="Mulperhour";
	}else if(title=="按日统计"){
		id="Mulperday";
	}else if(title=="按月统计"){
		id="Mulpermonth";
	}else{
		id="Mulperquarter";
	}
	var MulmonitorThings = $('#MulmonitorThings'+id).combobox('getValue');
	var startTime = null;
	var endTime = null;
	var MulmonitorThingsTexts = $('#MulmonitorThings'+id).combobox('getText');
	if(id=="Mulperquarter"){
		startTime = $("#dtStartTime"+id).combobox("getValue") + "-0" +  $("#startMulperquarterdt").combobox("getValue");
		endTime = $("#dtEndTime"+id).combobox("getValue") + "-0" + $("#endMulperquarterdt").combobox("getValue");
	}else{
		startTime = $('#dtStartTime'+id).datebox("getValue");
		endTime = $('#dtEndTime'+id).datebox("getValue");
	}
	if(station==null || station==undefined || station==""){
		treeid = "-1";
	}else{
		for(var i=0;i<station.length;i++){
			if(station[i].isDevice){//判断是监控点
				treeid = treeid + "," + station[i].id;
			}
		}
		if(treeid!=""){
			treeid = treeid.substr(1,treeid.length);
		}
	}
    var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	var len = treeid.split(",").length;
	if(len>10){
		$.messager.alert("提示", "最多导出10个站点的监控物含量！", "warning");
		return false;
	}
	location.href = "../ExportController/exportMultiDevices?devicecode="+treeid+"&thingcode="+MulmonitorThings+"&starttime="+startTime+"&endtime="+endTime+"&freque="+id.substr(3,id.length-1)+"&zsFlag="+zsFlag;
}