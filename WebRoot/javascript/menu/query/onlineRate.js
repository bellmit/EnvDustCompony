/************************************
 * 功能：在线率分析
 * 日期：2017-9-26 09:43:09
 ************************************/
var appendcontent = '<div id="onlineRateTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="日在线率"  selected="true" style="padding:10px" id="onlineDay"></div>'
	+ '<div title="月在线率"  style="padding:10px" id="onlineMonth"></div>'
	+ '<div title="年在线率"  style="padding:10px" id="onlineYear"></div>'
	+ '</div>';
addPanel("在线率分析", appendcontent);
var timeOrRateChartBtn = true;

tabContent("日在线率","onlineDay");
tabContent("月在线率","onlineMonth");
tabContent("年在线率","onlineYear");

function getContent(title,id){
	var contents = "";
	if("日在线率"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="orTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'查询范围：<input class="easyui-datebox" id="dtDayTime'+id+'" data-options="required:true" style="width:104px;"/>'	
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchTimeOrRateFunc(\'onlineDay\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchOnlineChart(\'onlineDay\')">图像</a>'
//				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" style="margin:0px 10px 0px 10px;" onclick="">导出</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" onclick="getOnlieRateRanking(\'onlineDay\')">在线率排名</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("月在线率"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="orTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'查询范围：<input class="easyui-datebox" id="dtMonthTime'+id+'" data-options="required:true" style="width:104px;"/>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchTimeOrRateFunc(\'onlineMonth\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchOnlineChart(\'onlineMonth\')">图像</a>'
//				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="">导出</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" onclick="getOnlieRateRanking(\'onlineMonth\')">在线率排名</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("年在线率"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="orTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'查询范围：<input class="easyui-combobox" id="orDtYearTime" data-options="required:true" style="width:104px;"/>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchTimeOrRateFunc(\'onlineYear\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchOnlineChart(\'onlineYear\')">图像</a>'
//				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="">导出</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" onclick="getOnlieRateRanking(\'onlineYear\')">在线率排名</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
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
	if(title=="日在线率"){
		$("#dtDayTime"+id).datebox('setValue',formatterDate(new Date()));
	}else if(title=="月在线率"){
		createDateboxByYYMM("dtMonthTime"+id);
		$("#dtMonthTime"+id).datebox('setValue',formatterDate(new Date()));
	}else if(title=="年在线率"){
		var datatimejson = [];
		var myDate = new Date();
		//获取当前年份(4位)
		var nowYear = myDate.getFullYear();
		for (var i = 0; i < 10; i++) {
			datatimejson.push({"id" : nowYear-i,"name" : nowYear-i});
		}
		$('#orDtYearTime').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#orDtYearTime').combobox('setValue',nowYear);
	}
}

/*查询列表信息*/
function searchTimeOrRateFunc(id) {
	initOnlineDataGridFunc("#searchContent_"+id);
	timeOrRateChartBtn = false;
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		$.messager.alert("提示", "请选择一个监控站点进行查询！", "error");
		return false;
	}else{
		if(station.isDevice){
			treeid = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	$("#monitorStation_"+id).html(station.text);
	//清空图标内容的处理，暂时这样处理
	var dom = document.getElementById("searchContent_"+id);
	dom.innerHTML = "";
	loadOnlineGridData(id,treeid);
}

function loadOnlineGridData(id,deviceCode) {
	
    var options = $("#searchContent_"+id).datagrid('options');
	var url = '';
	var paramValue = '';
	if(id=="onlineDay"){
		url = "../OnlineRateController/queryHourOnlineRate";
		paramValue = $('#dtDayTime'+id).datebox("getValue");
		options.url = url;
		options.queryParams = { "deviceCode": deviceCode, "dayTime": paramValue };
	}else if(id=="onlineMonth"){
		url = "../OnlineRateController/queryDayOnlineRate"
		paramValue = $('#dtMonthTime'+id).datebox("getValue");
		options.url = url;
		options.queryParams = { "deviceCode": deviceCode, "monthTime": paramValue };
	}else if(id=="onlineYear"){
		url = "../OnlineRateController/queryMonthOnlineRate"
		paramValue = $("#orDtYearTime").combobox("getValue");
		options.url = url;
		options.queryParams = { "deviceCode": deviceCode, "yearTime": paramValue };
	}
    $("#searchContent_"+id).datagrid(options);
}

/*初始化列表*/
function initOnlineDataGridFunc(id){
	/* 初始化列表,表头 */
	$(id).datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
		url : '',
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'deviceCode',
			title : '设备编号',
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceName',
			title : '设备名称',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'onlineTime',
			title : '统计时间',
			width : 150,
			halign : 'center',
			align : 'center'
		},{
			field : 'onlineRate',
			title : '在线率(%)',
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
	/* 定义分页器的初始显示默认值 */
	$(id).datagrid("getPager").pagination({
		total : 0
	});
}

/* 显示在线率排名列表 */
function getOnlieRateRanking(id) {
	var station = $('#mytree').tree('getSelected');
	var levelFlag;
	var mapTreeList = [];
	if (station == null || station == undefined) {
		mapTreeList.push(-1);
	} else {
		levelFlag = station.levelFlag;
		mapTreeList.push(station.id);
		if (mapTreeList.length == 0) {
			mapTreeList.push("0");
		}
	}
	var url = '';
	var time = '';
	if(id=="onlineDay"){
		url = "../OnlineRateController/queryDayOnlineRateRanking";
		time = $('#dtDayTime'+id).datebox("getValue");
	}else if(id=="onlineMonth"){
		url = "../OnlineRateController/queryMonthOnlineRateRanking";
		time = $('#dtMonthTime'+id).datebox("getValue");
	}else if(id=="onlineYear"){
		url = "../OnlineRateController/queryYearOnlineRateRanking";
		time = $("#orDtYearTime").combobox("getValue");
	}
	$("#dialogModel").dialog({
		width : 600,
		height : 424,
		title : "在线率排名信息",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : true,
		iconCls : 'icon-listtable',
		resizable : true,
		closed : true,
		content : '<div id="dgOnlieRateRanking" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	$("#dgOnlieRateRanking").datagrid({
		view:myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : true,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : url,
		queryParams: {
			list:mapTreeList,
			levelFlag:levelFlag,
			time:time
		},
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [{
			field : 'deviceCode',
			title : '设备编号',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceName',
			title : '站点名称',
			width : 150,
			halign : 'center',
			align : 'center'
		},{
			field : 'onlineRate',
			title : '在线率(%)',
			width : 100,
			halign : 'center',
			align : 'center'
		} ] ]
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
	/* 定义分页器的初始显示默认值 */
	$("#dgOnlieRateRanking").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
}

/*图表*/
function searchOnlineChart(id){
	var treeid = -1;
	var treename = "";
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		$.messager.alert("提示", "请选择一个监控站点进行查询！", "error");
		return false;
	}else{
		if(station.isDevice){
			treeid = station.id;
			treename = station.text;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	var centercontent = $("#centerContent_" + id);
	centercontent.html("");
	$("#centerContent_" + id).append('<div id="searchContent_' + id + '" style=""></div>');
	var url = '';
	var queryParams = {};
	if(id=="onlineDay"){
		url = "../OnlineRateController/queryHourOnlineRate";
		var paramValue = $('#dtDayTime'+id).datebox("getValue");
		queryParams = { "deviceCode": treeid, "dayTime": paramValue,"page":-1,"rows":-1 };
	}else if(id=="onlineMonth"){
		url = "../OnlineRateController/queryDayOnlineRate";
		var paramValue = $('#dtMonthTime'+id).datebox("getValue");
		queryParams = { "deviceCode": treeid, "monthTime": paramValue,"page":-1,"rows":-1 };
	}else if(id=="onlineYear"){
		url = "../OnlineRateController/queryMonthOnlineRate"
		var paramValue = $("#orDtYearTime").combobox("getValue");
		queryParams = { "deviceCode": treeid, "yearTime": paramValue,"page":-1,"rows":-1 };
	}
	var yname = "在线率(%)";//图表y轴名称
	var legendData = [];
	var xAxisData = [];
	var seriesData = [];
	ajaxLoading();
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		data :  queryParams,
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			if(json.result){
				legendData.push(treename);
				var count = json.rows.length;
				var yDataList = [];
				for (var i = 0; i < count; i++) {
					var xData = json.rows[i]["onlineTime"];
					var yData = json.rows[i]["onlineRate"];
					xAxisData.push(xData);
					yDataList.push(yData);
				}
				seriesData.push({
		            "name":treename,
		            "type":'line',
		            "data":yDataList,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        });
				initOnlineChart(xAxisData,legendData,seriesData,id,yname);
			}else{
				
			}
		}
	});
}

/*初始化表格*/
function initOnlineChart(xAxisData,legendData,seriesData,id,yname){
	var dom = document.getElementById("searchContent_"+id);
	dom.style.cssText = "height:100%";
	var myChart = echarts.init(document.getElementById("searchContent_"+id));
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
		    	name: '时间',
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
		    	name: yname,
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

