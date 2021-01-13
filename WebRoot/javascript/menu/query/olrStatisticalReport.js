/************************************
 * 功能：连续监测统计报表
 * 日期：2018-5-4 13:41:09
 ************************************/
var appendcontent = '<div id="olrStatisticalReportTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="日报表"  style="padding:10px" id="olrDay"></div>'
	+ '<div title="月报表"  style="padding:10px" id="olrMonth"></div>'
	+ '<div title="年报表"  style="padding:10px" id="olrYear"></div>'
	+ '<div title="时间段报表"  style="padding:10px" id="olrTimes"></div>'
	+ '</div>';
addPanel("连续监测统计报表", appendcontent);
var comboboxJsonOlr = [];

$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		if(json.length>0){
			for(var i=0;i<json.length;i++){
				var str = json[i].describe;
				if(str != null && (str.indexOf("mg/m3") > 0 || str.indexOf("mg/m³") > 0)){
					comboboxJsonOlr.push(json[i]);
				}
			}
		}
	}
});

//筛选设备监测物
function filterDeviceMonitors(id){
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
			$("#olrMonitorThings_"+id+"").combobox('clear');
			comboboxJsonOlr = [];
			if(json.length>0){
				for(var i=0;i<json.length;i++){
					var str = json[i].describe;
					if(str != null && (str.indexOf("mg/m3") > 0 || str.indexOf("mg/m³") > 0)){
						comboboxJsonOlr.push(json[i]);
					}
				}
				$("#olrMonitorThings_"+id+"").combobox('loadData',comboboxJsonOlr);
				if(comboboxJsonOlr.length>0){
					$("#olrMonitorThings_"+id+"").combobox('setValues',comboboxJsonOlr[0].code);
				}
			}else{
				$("#olrMonitorThings_"+id+"").combobox('loadData',comboboxJsonOlr);
			}
		}
	});
}

tabContent("日报表","olrDay");
tabContent("月报表","olrMonth");
tabContent("年报表","olrYear");
tabContent("时间段报表","olrTimes");

function getContent(title,id){
	var contents = "";
	if("日报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="olrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控项目：<input id="olrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterDeviceMonitors(\'olrDay\')" title="筛选监控点监控物"></a>'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTime'+id+'" data-options="required:true" style="width:104px;"/>'	
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOlrFunc(\'olrDay\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOlrExcel(\'olrDay\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("月报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="olrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				
				+'&nbsp;&nbsp;'
				+'监控项目：<input id="olrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterDeviceMonitors(\'olrMonth\')" title="筛选监控点监控物"></a>'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtMonthTime'+id+'" data-options="required:true" style="width:104px;"/>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOlrFunc(\'olrMonth\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOlrExcel(\'olrMonth\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("年报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esOlrQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				
				+'&nbsp;&nbsp;'
				+'监控项目：<input id="olrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterDeviceMonitors(\'olrYear\')" title="筛选监控点监控物"></a>'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-combobox" id="olrDtYearTime" data-options="required:true" style="width:104px;"/>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOlrFunc(\'olrYear\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOlrExcel(\'olrYear\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	} else if("时间段报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="olrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控项目：<input id="olrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterDeviceMonitors(\'olrTimes\')" title="筛选监控点监控物"></a>'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTimeBegin'+id+'" data-options="required:true" style="width:102px;"/>'
				+'至<input class="easyui-datebox" id="dtDayTimeEnd'+id+'" data-options="required:true" style="width:102px;"/>'	
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchOlrFunc(\'olrTimes\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaOlrExcel(\'olrTimes\')">导出</a>'
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
	$("#olrMonitorThings_"+id+"").combobox({
		  data:comboboxJsonOlr,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  value:comboboxJsonOlr[0].code,
		  onShowPanel: function () {
		      // 动态调整高度  
		      if (comboboxJsonOlr.length < 20) {
		          $(this).combobox('panel').height("auto");  
		      }else{
		          $(this).combobox('panel').height(300);
		      }
		  }
	});
	
	if(title=="日报表"){
		$("#dtDayTime"+id).datebox('setValue',GetDateStr(-1,0));
	}else if(title=="月报表"){
		createDateboxByYYMM("dtMonthTime"+id);
		$("#dtMonthTime"+id).datebox('setValue',GetMonthStr(-1,0));
	}else if(title=="年报表"){
		var datatimejson = [];
		var myDate = new Date();
		//获取当前年份(4位)
		var nowYear = myDate.getFullYear();
		for (var i = 0; i < 10; i++) {
			datatimejson.push({"id" : nowYear-i,"name" : nowYear-i});
		}
		$('#olrDtYearTime').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#olrDtYearTime').combobox('setValue',nowYear-1);
	}else if(title=="时间段报表"){
		$("#dtDayTimeBegin"+id).datebox('setValue',GetDateStr(-7,0));
		$("#dtDayTimeEnd"+id).datebox('setValue',GetDateStr(-1,0));
	}
}

/*查询列表信息*/
function searchOlrFunc(id) {
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station != null || station != undefined){
		if(station.isDevice){
			treeid = station.id;
		}
		$("#monitorStation_"+id).html(station.text);
	}else{
		$("#monitorStation_"+id).html("全部");
	}
	var coutitle = 'kg/h';
	var flowtitle = 'm³/h';
	if(id=="olrDay"){
		coutitle = 'kg/h';
		flowtitle = 'm³/h';
	}else if(id=="olrMonth"){
		coutitle = 't/d';
		flowtitle = 'm³/d';
	}else if(id=="olrYear"){
		coutitle = 't/m';
		flowtitle = 'm³/m';
	}else if(id=="olrTimes"){
		coutitle = 't/d';
		flowtitle = 'm³/d';
	}
	initOlrDataGridFunc("#searchContent_"+id,coutitle,flowtitle);
	loadOlrGridData(id,treeid);
}

function loadOlrGridData(id,deviceCode) {
	var options = $("#searchContent_"+id).datagrid('options');
	var station = $('#mytree').tree('getSelected');
	var treeid = -1;
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
	var url = "../EnvStatisticalReportController/getOlrZsData";
	var cnCode = $('#olrMonitorThings_'+id).combobox("getValue");
	if(cnCode == null || cnCode == ""){
		$.messager.alert("提示", "请选择一个监测项目！", "warning");
		return false;
	}
	if(id=="olrDay"){
		options.url = url;
		var paramValue = $('#dtDayTime'+id).datebox("getValue");
		options.queryParams = { "deviceCode": treeid, "thingCode": cnCode, "selectTime": paramValue, "selectType": "day" };
	}else if(id=="olrMonth"){
		options.url = url;
		var paramValue = $('#dtMonthTime'+id).datebox("getValue");
		options.queryParams = { "deviceCode": treeid, "thingCode": cnCode, "selectTime": paramValue, "selectType": "month" };
	}else if(id=="olrYear"){
		options.url = url;
		var paramValue = $('#olrDtYearTime').combobox("getValue");
		options.queryParams = { "deviceCode": treeid, "thingCode": cnCode, "selectTime": paramValue, "selectType": "year" };
	}else if(id=="olrTimes"){
		options.url = "../EnvStatisticalReportController/getOlrZsDataTimes";
		var beginTime = $('#dtDayTimeBegin'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd'+id).datebox("getValue");
		options.queryParams = { "deviceCode": treeid, "thingCode": cnCode, "beginTime": beginTime, "endTime": endTime };
	}
    $("#searchContent_"+id).datagrid(options);
}

/*初始化列表（连续监测统计列表）*/
function initOlrDataGridFunc(id,coutitle,flowtitle){
	/* 初始化列表,表头 */
	$(id).datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : false,
		fitColumns:true,
		pageList : [ 50, 100, 150, 200, 250, 300 ],
		url : '',
		pageSize : 50,
		rownumbers : true,
		columns : [ [ {
			field : 'time',
			title : '时间',
			rowspan:2,
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'jjxm',
			title : '监测项目',
			colspan:2,
			width : 160,
			halign : 'center',
			align : 'center'
		}, {
			field : 'standardFlow',
			title : '标态流量 ×10^4 '+flowtitle,
			rowspan:2,
			width : 160,
			halign : 'center',
			align : 'center'
		},{
			field : 'flowSpeed',
			title : '流速m/s',
			rowspan:2,
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'temperature',
			title : '温度℃',
			rowspan:2,
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'humidity',
			title : '水分含量%',
			rowspan:2,
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'memo',
			title : '备注',
			rowspan:2,
			width : 100,
			halign : 'center',
			align : 'center'
		}],[{
			field : 'thingValue',
			title : 'mg/m³',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingCou',
			title : coutitle,
			width : 100,
			halign : 'center',
			align : 'center'
		}
	    ] ],
		singleSelect : true
	}).datagrid('doCellTip', {
		cls : {
			'max-width' : '500px'
		}
	});
}

function outPutEnvStaOlrExcel(id){
	var options = $("#searchContent_"+id).datagrid('options');
	var station = $('#mytree').tree('getSelected');
	var treeid = -1;
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
	var cnCode = $('#olrMonitorThings_'+id).combobox("getValue");
	if(cnCode == null || cnCode == ""){
		$.messager.alert("提示", "请选择一个监测项目！", "warning");
		return false;
	}
	var params = "";
	var url = "../ReportController/downEnvStaOlrExcel";
	if(id=="olrDay"){
		var paramValue = $('#dtDayTime'+id).datebox("getValue");
		params = { "deviceCode": treeid, "thingCode": cnCode, "selectTime": paramValue, "selectType": "day" };
	}else if(id=="olrMonth"){
		var paramValue = $('#dtMonthTime'+id).datebox("getValue");
		params = { "deviceCode": treeid, "thingCode": cnCode, "selectTime": paramValue, "selectType": "month" };
	}else if(id=="olrYear"){
		var paramValue = $('#olrDtYearTime').combobox("getValue");
		params = { "deviceCode": treeid, "thingCode": cnCode, "selectTime": paramValue, "selectType": "year" };
	}else if(id=="olrTimes"){
		url = "../ReportController/downEnvStaOlrTimesExcel";
		var beginTime = $('#dtDayTimeBegin'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd'+id).datebox("getValue");
		params = { "deviceCode": treeid, "thingCode": cnCode, "beginTime": beginTime, "endTime": endTime };
	}
	outPutEnvStaOlrData(params,url);
}

/**
 * 导出统计报表(连续监控)
 */
function outPutEnvStaOlrData(params,url){
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

