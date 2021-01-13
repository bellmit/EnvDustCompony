/************************************
 * 功能：同比环比统计报表
 * 日期：2018-5-4 13:41:09
 ************************************/
var appendcontent = '<div id="queryCompareTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="日对比"  selected="true" style="padding:10px" id="dayCompare"></div>'
	+ '<div title="月对比"  style="padding:10px" id="monthCompare"></div>'
	+ '</div>';
addPanel("同比环比统计报表", appendcontent);

var getThingNameJsonCompare = {};
var comboboxJsonCompare = [];
var compareMonitorList = [];

$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonCompare = json;
		for(var i=0;i<json.length;i++){
			getThingNameJsonCompare[json[i].code] = json[i].name;
			compareMonitorList.push(json[i].code);
		}
	}
});

tabContent("日对比","dayCompare");
tabContent("月对比","monthCompare");

function getContent(title,id){
	var contents = "";
	if("dayCompare"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esgTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物质：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtOriginalTime_'+id+'" data-options="required:true" style="width:104px;"/>'	
				+'<span>&nbsp;与&nbsp;</span>'
				+'<input class="easyui-datebox" id="dtCompareTime_'+id+'" data-options="required:true" style="width:104px;"/>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchDcFunc(\'dayCompare\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartDcFunction(\'dayCompare\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaDcExcel(\'dayCompare\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" style="height:80%" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("monthCompare"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esgTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtOriginalTime_'+id+'" data-options="required:true" style="width:104px;"/>'	
				+'<span>&nbsp;与&nbsp;</span>'
				+'<input class="easyui-datebox" id="dtCompareTime_'+id+'" data-options="required:true" style="width:104px;"/>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchDcFunc(\'monthCompare\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartDcFunction(\'monthCompare\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaDcExcel(\'monthCompare\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" style="height:80%" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}
	return contents;
}

//tab页内容
function tabContent(title,id){
	var contents = getContent(title,id);
	$('#'+id).append(contents);
	$.parser.parse('#'+id);
	var dataCount = 0;
	$("#monitorThings_"+id+"").combobox({
		data:comboboxJsonCompare,
		method:'post',
		valueField:'code',
		textField:'describe',
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
			$('#monitorThings_'+id).combobox('setValue',data[0].code);
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
	if(id=="dayCompare"){		
		$("#dtOriginalTime_"+id).datebox('setValue',GetDateStr(-2,0));
		$("#dtCompareTime_"+id).datebox('setValue',GetDateStr(-1,0));
	}else if(id=="monthCompare"){
		createDateboxByYYMM("dtOriginalTime_"+id);
		createDateboxByYYMM("dtCompareTime_"+id);
		$("#dtOriginalTime_"+id).datebox('setValue',GetMonthStr(-2,0));
		$("#dtCompareTime_"+id).datebox('setValue',GetMonthStr(-1,0));
	}
}

/*查询列表信息*/
function searchDcFunc(id) {
	var station = $('#mytree').tree('getSelected');
	if(station != null || station != undefined){
		$("#monitorStation_"+id).html(station.text);
	}else{
		$("#monitorStation_"+id).html("全部");
	}
	var originalTime = $('#dtOriginalTime_'+id).datebox("getValue");
	var compareTime = $('#dtCompareTime_'+id).datebox("getValue");
	initDcDataGridFunc("#searchContent_"+id,originalTime,compareTime);
	loadDcGridData(id);
}

function loadDcGridData(id) {
	var options = $("#searchContent_"+id).datagrid('options');
	var station = $('#mytree').tree('getSelected');
	var treeList = [];
	var levelFlag = "";
	if (station == null || station == undefined) {
		treeList.push(-1);
		selected = "-1";
	} else {
		selected = station.id;
		treeList.push(station.id);
		levelFlag = station.levelFlag;
	}
	var monitorThings = $('#monitorThings_'+id).combobox('getValues');
	if(monitorThings==""){
		$.messager.alert("提示", "请至少选择一个监控物！！", "error");
		return false;
	}
	var originalTime = $('#dtOriginalTime_'+id).datebox("getValue");
	var compareTime = $('#dtCompareTime_'+id).datebox("getValue");	
	var url = "../DataCompareController/getCompareData";
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(id=="dayCompare"){
		options.url = url;
		options.queryParams = { "projectId":projectId,"listDevice": treeList, "levelFlag": levelFlag, "listThing": monitorThings, "originalTime":originalTime,"compareTime":compareTime, "selectType": "day"};
	}else if(id=="monthCompare"){
		options.url = url;
		options.queryParams = { "projectId":projectId,"listDevice": treeList, "levelFlag": levelFlag, "listThing": monitorThings, "originalTime": originalTime,"compareTime":compareTime, "selectType": "month"};
	}
    $("#searchContent_"+id).datagrid(options);
}

/*初始化列表（数据对比列表）*/
function initDcDataGridFunc(id,originalTitle,compareTitel){
	/* 初始化列表,表头 */
	$(id).datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : false,
		fitColumns : false,
		pageList : [ 50, 100, 150, 200, 250, 300 ],
		url : '',
		pageSize : 50,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'areaName',
			title : '所属区域',
			width : 160,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceName',
			title : '设备名称',
			width : 160,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingName',
			title : '监测物',
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'originalValue',
			title : originalTitle,
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'compareValue',
			title : compareTitel,
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'addedRatio',
			title : '增长率',
			width : 100,
			halign : 'center',
			align : 'center',
			formatter : function(value, row, index) {
				if(row.addedRatio != "" && row.addedRatio != ""){
					return row.addedRatio+"%"
				}
			}
		}] ],
		singleSelect : true
	}).datagrid('doCellTip', {
		cls : {
			'max-width' : '500px'
		}
	});
}

/*初始化列表(时间段列表)*/
function initEsgTimesDataGridFunc(id){
	/* 初始化列表,表头 */
	$(id).datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : false,
		fitColumns : false,
		pageList : [ 50, 100, 150, 200, 250, 300 ],
		url : '',
		pageSize : 50,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'areaName',
			title : '所属区域',
			width : 160,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceName',
			title : '设备名称',
			width : 160,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingName',
			title : '监测物',
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'beginTime',
			title : '开始时间',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'endTime',
			title : '结束时间',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingMax',
			title : '最大值',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingMin',
			title : '最小值',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingAvg',
			title : '平均值',
			width : 100,
			halign : 'center',
			align : 'center'
		}] ],
		singleSelect : true
	}).datagrid('doCellTip', {
		cls : {
			'max-width' : '500px'
		}
	});
}



/*查询图表信息*/
function searchChartDcFunction(id) {
	var station = $('#mytree').tree('getSelected');
	var params = {};
	var treeList = [];
	var levelFlag = "";
	var station = $('#mytree').tree('getSelected');
	if (station == null || station == undefined) {
		treeList.push(-1);
		selected = "-1";
		$("#monitorStation_"+id).html("全部");
	} else {
		selected = station.id;
		treeList.push(station.id);
		levelFlag = station.levelFlag;
		$("#monitorStation_"+id).html(station.text);
	}
	var monitorThings = $('#monitorThings_'+id).combobox('getValues');
	if(monitorThings==""){
		$.messager.alert("提示", "请至少选择一个监控物！！", "error");
		return false;
	}
	var originalTime = $('#dtOriginalTime_'+id).datebox("getValue");
	var compareTime = $('#dtCompareTime_'+id).datebox("getValue");	
	var url = "../DataCompareController/getCompareData";
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(id=="dayCompare"){
		params = {"projectId":projectId, "listDevice": treeList, "levelFlag": levelFlag, "listThing": monitorThings, "originalTime":originalTime,"compareTime":compareTime, "selectType": "day"};
	}else if(id=="monthCompare"){
		params = {"projectId":projectId, "listDevice": treeList, "levelFlag": levelFlag, "listThing": monitorThings, "originalTime": originalTime,"compareTime":compareTime, "selectType": "month"};
	}
	ajaxLoading();
	var xAxis = [originalTime,compareTime];
	var centercontent = $("#centerContent_"+id);
	centercontent.html("");
	centercontent.append('<div id="searchContent_'+id+'" style=""></div>');
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		async : true,
		data : params,
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			var legendData = [];
			var seriesData = [];
			if (json != undefined) {
				for ( var index in json) {
					var max = null;
					legendData.push(json[index].deviceName);
					seriesData.push({
						"name" : json[index].deviceName,
						"type" : 'line',
						"data" : [json[index].originalValue,json[index].compareValue],
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
							}
						}
					});
				}
				initDcChart(id, xAxis, legendData, seriesData, '数值');
			} else {
				initDcChart(id, xAxis, legendData, seriesData, '数值');
			}
		}
	});
}

/*初始化表格*/
function initDcChart(id,xAxisData,legendData,seriesData,yname){
	var dom = document.getElementById("searchContent_"+id);
	dom.style.cssText = "height:100%";
	mychart = echarts.init(document.getElementById("searchContent_"+id));
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
		        data: xAxisData,
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
	mychart.clear();
	mychart.setOption(option,true);
	mychart.resize(); 
}

function outPutEnvStaDcExcel(id){
	var station = $('#mytree').tree('getSelected');
	var levelFlag = "";
	var selected = "";
	if (station == null || station == undefined) {
		selected = "-1";
	} else {
		selected = station.id;
		levelFlag = station.levelFlag;
	}
	var monitorThings = $('#monitorThings_'+id).combobox('getValues');
	if(monitorThings==""){
		$.messager.alert("提示", "请至少选择一个监控物！", "error");
		return false;
	}
	listThing = monitorThings.join(",");
	var originalTime = $('#dtOriginalTime_'+id).datebox("getValue");
	var compareTime = $('#dtCompareTime_'+id).datebox("getValue");	
	var url = "../ReportController/downEnvStaDcExcel";
	var params = {};
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(id=="dayCompare"){
		params = { "projectId":projectId, "selected": selected, "levelFlag": levelFlag, "listThing": listThing, "originalTime":originalTime,"compareTime":compareTime, "selectType": "day"};
	}else if(id=="monthCompare"){
		params = { "projectId":projectId, "selected": selected, "levelFlag": levelFlag, "listThing": listThing, "originalTime": originalTime,"compareTime":compareTime, "selectType": "month"};
	}
	outPutEnvStaDcData(params,url);
}

/**
 * 导出统计报表(年月日时)
 */
function outPutEnvStaDcData(params,url){
    var form=$("<form>");//定义一个form表单
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","post");
    form.attr("action",url);
    for(var key in params){
        var input=$("<input>");
        input.attr("type","hidden");
        input.attr("name",key);
        input.attr("value",params[key]);
        form.append(input);
    }
    $("body").append(form);//将表单放置在web中
    form.submit();//表单提交
}

