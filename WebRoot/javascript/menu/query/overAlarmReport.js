/************************************
 * 功能：数据超标统计报表
 * 日期：2018-5-4 13:41:09
 ************************************/
var appendcontent = '<div id="overAlarmTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="日统计"  selected="true" style="padding:10px" id="dayAlarm"></div>'
	+ '<div title="月统计"  style="padding:10px" id="monthAlarm"></div>'
	+ '<div title="年统计"  style="padding:10px" id="yearAlarm"></div>'
	+ '<div title="时间段统计"  style="padding:10px" id="timesAlarm"></div>'
	+ '</div>';
addPanel("数据超标统计报表", appendcontent);

var comboboxJsonOverAlarm = [];

$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonOverAlarm = json;
	}
});

tabContent("日统计","dayAlarm");
tabContent("月统计","monthAlarm");
tabContent("年统计","yearAlarm");
tabContent("时间段统计","timesAlarm");

function getContent(title,id){
	var contents = "";
	if("dayAlarm"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="oaTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;数据类型：<input class="easyui-combobox" name="oaCnCodeCombox_'+id+'" id="oaCnCodeCombox_'+id+'" style="width:80px;"/>'
                +'&nbsp;&nbsp;报警类型：<input class="easyui-combobox" name="alarmTypeCombox_'+id+'" id="alarmTypeCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;监控物质：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtSelectTime_'+id+'" data-options="required:true" style="width:104px;"/>'

                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOaFunc(\'dayAlarm\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartOaFunction(\'dayAlarm\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOaExcel(\'dayAlarm\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" style="height:80%" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("monthAlarm"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="oaTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;数据类型：<input class="easyui-combobox" name="oaCnCodeCombox_'+id+'" id="oaCnCodeCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;报警类型：<input class="easyui-combobox" name="alarmTypeCombox_'+id+'" id="alarmTypeCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;监控物：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtSelectTime_'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOaFunc(\'monthAlarm\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartOaFunction(\'monthAlarm\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOaExcel(\'monthAlarm\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" style="height:80%" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("yearAlarm"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="oaTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;数据类型：<input class="easyui-combobox" name="oaCnCodeCombox_'+id+'" id="oaCnCodeCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;报警类型：<input class="easyui-combobox" name="alarmTypeCombox_'+id+'" id="alarmTypeCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;监控物：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-combobox" id="dtOaYearTime" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOaFunc(\'yearAlarm\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartOaFunction(\'yearAlarm\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOaExcel(\'yearAlarm\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" style="height:80%" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("timesAlarm"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="oaTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;数据类型：<input class="easyui-combobox" name="oaCnCodeCombox_'+id+'" id="oaCnCodeCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;报警类型：<input class="easyui-combobox" name="alarmTypeCombox_'+id+'" id="alarmTypeCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;监控物：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTimeBegin_'+id+'" data-options="required:true" style="width:102px;"/>'
				+'至<input class="easyui-datebox" id="dtDayTimeEnd_'+id+'" data-options="required:true" style="width:102px;"/>'
            	+'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOaFunc(\'timesAlarm\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchChartOaFunction(\'timesAlarm\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOaExcel(\'timesAlarm\')">导出</a>'
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
	$("#oaCnCodeCombox_"+id+"").combobox({
		data : ornCnCode,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value : "2061"
	});
    $("#alarmTypeCombox_"+id+"").combobox({
        data : alarmTypeCode,
        method : 'post',
        valueField : 'levelNo',
        textField : 'name',
        panelHeight : 'auto',
        value : "3"
    });
	$("#monitorThings_"+id+"").combobox({
		  data:comboboxJsonOverAlarm,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  onShowPanel: function () {
		      // 动态调整高度  
		      if (comboboxJsonOverAlarm.length < 20) {
		          $(this).combobox('panel').height("auto");
		      }else{
		          $(this).combobox('panel').height(300);
		      }
		  }
	});
	if(id=="dayAlarm"){
		$("#dtSelectTime_"+id).datebox('setValue',GetDateStr(-1,0));
	}else if(id=="monthAlarm"){
		createDateboxByYYMM("dtSelectTime_"+id);
		$("#dtSelectTime_"+id).datebox('setValue',GetMonthStr(-1,0));
	}else if(id=="yearAlarm"){
		var datatimejson = [];
		var myDate = new Date();
		//获取当前年份(4位)
		var nowYear = myDate.getFullYear();
		for (var i = 0; i < 10; i++) {
			datatimejson.push({"id" : nowYear-i,"name" : nowYear-i});
		}
		$('#dtOaYearTime').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#dtOaYearTime').combobox('setValue',nowYear-1);
	}else if(id=="timesAlarm"){
		$("#dtDayTimeBegin_"+id).datebox('setValue',GetDateStr(-2,0));
		$("#dtDayTimeEnd_"+id).datebox('setValue',GetDateStr(-1,0));
	}
    setCheckTitle();
}

/*查询列表信息*/
function searchOaFunc(id) {
	var station = $('#mytree').tree('getSelected');
	if(station != null || station != undefined){
		$("#monitorStation_"+id).html(station.text);
	}else{
		$("#monitorStation_"+id).html("全部");
	}
	var updateType = $('#oaCnCodeCombox_'+id).combobox("getValue");
	var allTitle = "未知类型数据总量";
	if("2011" == updateType){
		allTitle = "实时数据总量";
	}else if("2031" == updateType){
		allTitle = "每日数据总量";
	}else if("2051" == updateType){
		allTitle = "分钟数据总量";
	}else if("2061" == updateType){
		allTitle = "小时数据总量";
	}else{
		allTitle = "未知类型数据总量";
	}
	initOaDataGridFunc("#searchContent_"+id,allTitle);
    var convertType = "";
    if($('#zVauleChekbox'+id).is(':checked')){
        convertType = "zs";
    }
	loadOaGridData(id,convertType);
}

function loadOaGridData(id,convertType) {
	var options = $("#searchContent_"+id).datagrid('options');
	var station = $('#mytree').tree('getSelected');
	var treeList = [];
	var levelFlag = "";
	if (station == null || station == undefined) {
		treeList.push(-1);
		selected = "-1";
		$.messager.alert("提示", "请选择一个监测站点或区域！", "error");
		return;
	} else {
		selected = station.id;
		treeList.push(station.id);
		levelFlag = station.levelFlag;
	}
	var thingCode = $('#monitorThings_'+id).combobox("getValue");
    var levelNo =  $("#alarmTypeCombox_"+id).combobox("getValue");//报警级别
	var updateType = $('#oaCnCodeCombox_'+id).combobox("getValue");
	if(updateType == null || updateType == undefined || updateType == "" ){
		$.messager.alert("提示", "请选择一个数据类型！", "error");
		return;
	}
	var url = "../EnvStatisticalReportController/queryAlarmRate";
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(id=="dayAlarm"){
		options.url = url;
		var selectTime = $('#dtSelectTime_'+id).datebox("getValue");
		options.queryParams = { "projectId":projectId,"list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType":updateType, "selectTime":selectTime, "dataType": "day",
			"convertType":convertType,"levelNo":levelNo};
	}else if(id=="monthAlarm"){
		options.url = url;
		var selectTime = $('#dtSelectTime_'+id).datebox("getValue");
		options.queryParams = { "projectId":projectId,"list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType":updateType, "selectTime": selectTime, "dataType": "month",
			"convertType":convertType, "levelNo":levelNo};
	}else if(id=="yearAlarm"){
		options.url = url;
		var selectTime = $('#dtOaYearTime').datebox("getValue");
		options.queryParams = { "projectId":projectId,"list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType":updateType, "selectTime": selectTime, "dataType": "year",
			"convertType":convertType, "levelNo":levelNo};
	}else if(id=="timesAlarm"){
		options.url = "../EnvStatisticalReportController/queryAlarmTimesRate";
		var beginTime = $('#dtDayTimeBegin_'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd_'+id).datebox("getValue");
		options.queryParams = { "projectId":projectId, "list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType":updateType, "beginTime": beginTime, "endTime":endTime,
			"convertType":convertType, "levelNo":levelNo};
	}
    $("#searchContent_"+id).datagrid(options);
}

/*初始化列表（数据对比列表）*/
function initOaDataGridFunc(id,allTitle){
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
		},{
			field : 'allCount',
			title : allTitle,
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'alarmCount',
			title : '超标数据总量',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'alarmRate',
			title : '超标百分比',
			width : 100,
			halign : 'center',
			align : 'center',
			formatter : function(value, row, index) {
				return row.alarmRate+"%"
			}
		}] ],
		singleSelect : true
	}).datagrid('doCellTip', {
		cls : {
			'max-width' : '500px'
		}
	});
}

/*查询图表信息*/
function searchChartOaFunction(id,convertType) {
	var station = $('#mytree').tree('getSelected');
	var params = {};
	var treeList = [];
	var levelFlag = "";
	var station = $('#mytree').tree('getSelected');
	if (station == null || station == undefined) {
		treeList.push(-1);
		selected = "-1";
		$("#monitorStation_"+id).html("全部");
		$.messager.alert("提示", "请选择一个监测站点或区域！", "error");
	} else {
		selected = station.id;
		treeList.push(station.id);
		levelFlag = station.levelFlag;
		$("#monitorStation_"+id).html(station.text);
	}
	var thingCode = $('#monitorThings_'+id).combobox("getValue");
	var thingName = $('#monitorThings_'+id).combobox("getText");
    var levelNo =  $("#alarmTypeCombox_"+id).combobox("getValue");//报警级别
	var updateType = $('#oaCnCodeCombox_'+id).combobox("getValue");
	if(updateType == null || updateType == undefined || updateType == "" ){
		$.messager.alert("提示", "请选择一个数据类型！", "error");
		return;
	}
	if(thingName == null || thingName == undefined || thingName == ""){
		thingName = "全部监测物";
	}
	var url = "../EnvStatisticalReportController/queryAlarmRate";
	var projectId = $("#deviceProjectId").combobox('getValue');
    var convertType = "";
    if($('#zVauleChekbox'+id).is(':checked')){
        convertType = "zs";
    }
	if(id=="dayAlarm"){
		var selectTime = $('#dtSelectTime_'+id).datebox("getValue");
		params = {"projectId":projectId, "list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType": updateType, "selectTime":selectTime,
			"dataType": "day","convertType":convertType,"levelNo":levelNo};
	}else if(id=="monthAlarm"){
		var selectTime = $('#dtSelectTime_'+id).datebox("getValue");
		params = { "projectId":projectId, "list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType": updateType, "selectTime": selectTime,
			"dataType": "month","convertType":convertType,"levelNo":levelNo};
	}else if(id=="yearAlarm"){
		var selectTime = $('#dtOaYearTime').datebox("getValue");
		params = { "projectId":projectId, "list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType": updateType, "selectTime": selectTime,
			"dataType": "year","convertType":convertType,"levelNo":levelNo};
	}else if(id=="timesAlarm"){
		url = "../EnvStatisticalReportController/queryAlarmTimesRate";
		var beginTime = $('#dtDayTimeBegin_'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd_'+id).datebox("getValue");
		params = { "projectId":projectId, "list": treeList, "levelFlag": levelFlag,
			"thingCode": thingCode, "updateType": updateType, "beginTime": beginTime,
			"endTime":endTime,"convertType":convertType,"levelNo":levelNo};
	}
	ajaxLoading();
	var centercontent = $("#centerContent_"+id);
	centercontent.html("");
	centercontent.append('<div id="searchContent_'+id+'" style=""></div>');
	var legendData = ['实时数据总量', '超标数据总量','超标占比'];
	var xAxisDataAll = [];
	var xAxisDataAlarm = [];
	var xAxisDataRate = [];
	var yAxisData = [];
	var seriesData = [];
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
			if(json.result){
				if(json.rows != null && json.rows.length>0){
					for(var i=0;i<json.rows.length;i++){
						var yData = json.rows[i]["deviceName"];
						if(yData == "合计"){
							continue;
						}
						var xDataAll = json.rows[i]["allCount"];
						var xDataAlarm = json.rows[i]["alarmCount"];
						var xDataRate = json.rows[i]["alarmRate"];
						xAxisDataAll.push(xDataAll);
						xAxisDataAlarm.push(xDataAlarm);
						xAxisDataRate.push(xDataRate);
						yAxisData.push(yData);
					}
				}
				seriesData.push(
				{
		            "name":'实时数据总量',
		            "type":'bar',
		            stack: '总量',
		            color:'rgb(0,0,139)',
		            label: {
		                normal: {
		                    show: false,
		                    position: 'insideRight'
		                }
		            },
		            "data":xAxisDataAll
		        },
		        {
		            "name":'超标数据总量',
		            "type":'bar',
		            stack: '总量',
		            color:'rgb(220,20,60)',
		            label: {
		                normal: {
		                    show: false,
		                    position: 'insideRight'
		                }
		            },
		            "data":xAxisDataAlarm
		        },{
		            "name":'超标占比',
		            "type":'bar',
		            stack: '总量',
		            color:'rgb(0,191,255)',
		            label: {
		                normal: {
		                    show: false,
		                    position: 'insideRight'
		                }
		            },
		            "data":xAxisDataRate
		        });
				initOaChart(id,legendData,yAxisData,seriesData);
			}
		}
	});
}

/*初始化表格*/
function initOaChart(id,legendData,yAxisData,seriesData){
	var dom = document.getElementById("searchContent_"+id);
	dom.style.cssText = "height:100%";
	mychart = echarts.init(document.getElementById("searchContent_"+id));
	option = {
	    title: {
	        text: '超标统计',
	        subtext: '超标百分比',
		    left: 'left',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        },
		    textStyle: {
		        color: '#eee'
		    }
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	        data: legendData
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01]
	    },
	    yAxis: {
	        type: 'category',
	        data: yAxisData
	    },
	    series: seriesData
	};
	mychart.setOption(option);
}

function outPutEnvStaOaExcel(id){
	var station = $('#mytree').tree('getSelected');
	var levelFlag = "";
	var selected = "";
	if (station == null || station == undefined) {
		selected = "-1";
	} else {
		selected = station.id;
		levelFlag = station.levelFlag;
	}
	var thingCode = $('#monitorThings_'+id).combobox("getValue");
	var updateType = $('#oaCnCodeCombox_'+id).combobox("getValue");
    var levelNo =  $("#alarmTypeCombox_"+id).combobox("getValue");//报警级别
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(updateType == null || updateType == undefined || updateType == "" ){
		$.messager.alert("提示", "请选择一个数据类型！", "error");
		return;
	}
	var url = "../ReportController/downEnvStaOaExcel";
    var convertType = "";
    if($('#zVauleChekbox'+id).is(':checked')){
        convertType = "zs";
    }
	var params = {};
	if(id=="dayAlarm"){
		var selectTime = $('#dtSelectTime_'+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": thingCode,
			"updateType": updateType, "selectTime":selectTime, "dataType": "day",
			"convertType":convertType,"levelNo":levelNo};
	}else if(id=="monthAlarm"){
		var selectTime = $('#dtSelectTime_'+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": thingCode,
			"updateType": updateType, "selectTime": selectTime, "dataType": "month",
			"convertType":convertType,"levelNo":levelNo};
	}else if(id=="yearAlarm"){
		var selectTime = $('#dtOaYearTime').datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": thingCode,
			"updateType": updateType, "selectTime": selectTime, "dataType": "year",
			"convertType":convertType,"levelNo":levelNo};
	}else if(id=="timesAlarm"){
		var beginTime = $('#dtDayTimeBegin_'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd_'+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": thingCode,
			"updateType": updateType, "beginTime": beginTime,"endTime":endTime,
			"dataType": "times","convertType":convertType,"levelNo":levelNo};
	}
	outPutEnvStaOaData(params,url);
}

/**
 * 导出统计报表(年月日时)
 */
function outPutEnvStaOaData(params,url){
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

