/*******************************************************************************
 * 功能：数据修约 日期：2016-6-3 09:28:09
 ******************************************************************************/
var appendcontent = '<div id="dgUpdOriginalDataInfo"></div>'
		+ '<div id="tbdgUpdOriginalDataInfo" style="padding:5px 10px;border-bottom:1px solid #ddd;">'
		+'监控站点：<span id="monitorStationUpdOriginal" style="width:150px;">无</span>'
		+'&nbsp;&nbsp;&nbsp'
		+'监控物质：<input id="monitorThingsUpdOriginal" class="easyui-combobox" style="width:150px;">'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterUpdOrgDeviceMonitors()" title="筛选监控点监控物"></a>'
		+ '&nbsp;&nbsp;&nbsp;数据类型：<input class="easyui-combobox" name="updOrnCnCodeCombox" id="updOrnCnCodeCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;录入时间范围：<input class="easyui-datetimebox" id="dtUpdOrnBeginTime" style="width:143px;"/>'
		+ '<span>&nbsp;&nbsp;&nbsp;至：</span>'
		+ '<input class="easyui-datetimebox" id="dtUpdOrnEndTime" style="width:143px;"/>'
		+ '<br><a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchUpdOrnDataFunc()" ">列表</a>'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchUpdOrnChartFunc()">图像</a>'
		+ '&nbsp;&nbsp;&nbsp'
//		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delRldDataFunc()">删除</a>'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" style="margin:0px 10px;"  onclick="exportUpdOrgFunction()">导出</a>'
		+ '</div>'
		+'<div data-options="region:\'center\',border:false" style="height:80%" id="centerUpdOrnChartContent">'
		+'<div id="searchUpdOrnChartContent"></div>'
		+'</div>';
addPanel("数据修约", appendcontent);
var initEndTime = formatterDate(new Date());
$("#dtUpdOrnBeginTime").datetimebox('setValue', initEndTime);
$("#dtUpdOrnEndTime").datetimebox('setValue', initEndTime + " 23:59:59");
var comboboxJsonOriginalUpd = [];
//监控物
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonOriginalUpd = json;
	}
});
$("#monitorThingsUpdOriginal").combobox({
	  data:comboboxJsonOriginalUpd,
	  method:'post',
	  valueField:'code',
	  textField:'describe',
	  panelHeight:'auto',
	  value:comboboxJsonOriginalUpd[0].code
});
//筛选设备监测物
function filterUpdOrgDeviceMonitors(){
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
			$("#monitorThingsUpdOriginal").combobox('clear');
			comboboxJsonOriginalUpd = [];
			if(json.length>0){
				comboboxJsonOriginalUpd = json;
				$("#monitorThingsUpdOriginal").combobox('loadData',comboboxJsonOriginalUpd);
				if(comboboxJsonOriginalUpd.length>0){
					$("#monitorThingsUpdOriginal").combobox('setValues',comboboxJsonOriginalUpd[0].code);
				}
			}else{
				$("#monitorThingsUpdOriginal").combobox('loadData',comboboxJsonOriginalUpd);
			}
		}
	});
}
var ornCnCodeValue = "2061";
$("#updOrnCnCodeCombox").combobox({
	data : ornCnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto',
	value : ornCnCodeValue
});

function initUpdOriginalDataGridFunc(){
	/* 初始化列表,表头 */
	$("#searchUpdOrnChartContent").datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
		url : "",
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'storageId',
			hidden : true
		},{field:"operate1",title:"删除",halign:"center",align : 'center',
	    	formatter: function(value,row,index){
	    		var str = '<a href="#this" title="删除数据" class="easyui-tooltip" ' 
	    				+ 'onclick="originalDataDel('+index+');">'
	    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/cut.png" class="operate-button"></a>';
	    		return str;
	    	}
	    },{field:"operate2",title:"修改",halign:"center",align : 'center',
	    	formatter: function(value,row,index){
	    		var str = '<a href="#this" title="修改数据" class="easyui-tooltip" ' 
	    				+ 'onclick="originalDataUpd('+index+');">'
	    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/pencil.png" class="operate-button"></a>';
	    		return str;
	    	}
	    },{field:"operate3",title:"更多",halign:"center",align : 'center',
	    	formatter: function(value,row,index){
	    		var str = '<a href="#this" title="更多监控物数据" class="easyui-tooltip" ' 
	    				+ 'onclick="moreMonitorThingDataUpd('+index+');">'
	    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/things.png" class="operate-button"></a>';
	    		return str;
	    	}
	    }, {
			field : 'deviceCode',
			title : '设备编号',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'deviceMn',
			title : '设备MN号',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'deviceName',
			title : '设备名称',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingCode',
			title : '监测物编码',
			width : 100,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'thingName',
			title : '监测物名称',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'updateType',
			title : '数据类型编码',
			width : 100,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'updateTypeName',
			title : '数据类型',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingRtd',
			title : '实时值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingAvg',
			title : '平均值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingMin',
			title : '最小值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingMax',
			title : '最大值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'rtdTime',
			title : '实时数据上报时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'statusName',
			title : '实时数据标识',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'beginTime',
			title : '开始采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'endTime',
			title : '结束采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'updateTime',
			title : '系统录入时间',
			width : 150,
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
	$("#searchUpdOrnChartContent").datagrid("getPager").pagination({
		total : 0
	});
}


/* 查询获取数据 */
function searchUpdOrnDataFunc() {
	initUpdOriginalDataGridFunc();
	var treeid = -1;
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
	var beginTime = $("#dtUpdOrnBeginTime").datetimebox('getValue');
	var endTime = $("#dtUpdOrnEndTime").datetimebox('getValue');
	var cnCode = $("#updOrnCnCodeCombox").combobox("getValue");
	if(beginTime == null || beginTime == ''){
		$.messager.alert("提示", "请填写开始时间！", "warning");
		return false;
	}
	if(endTime == null || endTime == ''){
		$.messager.alert("提示", "请填写结束时间！", "warning");
		return false;
	}
	if(cnCode == null || cnCode == ''){
		$.messager.alert("提示", "请选择数据类型！", "warning");
		return false;
	}
	var dtBegin = new Date(Date.parse(beginTime));
	var dtEnd= new Date(Date.parse(endTime));
	var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
	if(cnCode == '2011' && diffDay>2){
		$.messager.alert("提示", "只能查询2天内的实时数据！", "warning");
		return false;
	}
	if(cnCode == '2051' && diffDay>7){
		$.messager.alert("提示", "只能查询7天内的分钟数据！", "warning");
		return false;
	}
	$("#monitorStationUpdOriginal").html(station.text);
	var thingValue = $("#monitorThingsUpdOriginal").combobox('getValue');
	//清空图标内容的处理，暂时这样处理
	var dom = document.getElementById("searchUpdOrnChartContent");
	dom.innerHTML = "";
	var url = "../MonitorStorageController/getOriginalData";
	$('#searchUpdOrnChartContent').datagrid('options').url = url;  
	$("#searchUpdOrnChartContent").datagrid("load", {
		"deviceCode": treeid,
		"beginTime":beginTime,
		"endTime":endTime,
		"updateType":cnCode,
		"select":treeid,
		"thingCode":thingValue,
        "projectId":$("#deviceProjectId").combobox('getValue')
	});
	if(cnCode == '2011'){
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'thingRtd');
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'rtdTime');
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'statusName');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'thingAvg');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'thingMax');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'thingMin');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'beginTime');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'endTime');
	}else{
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'thingAvg');
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'thingMax');
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'thingMin');
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'beginTime');
		$("#searchUpdOrnChartContent").datagrid('showColumn', 'endTime');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'thingRtd');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'rtdTime');
		$("#searchUpdOrnChartContent").datagrid('hideColumn', 'statusName');
	}
}

/* 删除数据 */
function originalDataDel(index){
	var record = $("#searchUpdOrnChartContent").datagrid("getRows")[index];
	if (record == null || record == undefined) {
		$.messager.alert("提示", "请选择一条记录进行删除！", "info");
		return false;
	}
	$.messager.confirm("提示",'删除后将不能恢复，确定永久删除选定记录吗？', function(r){
		if (r){
			ajaxLoading();
			$.ajax({
				url : "../MonitorStorageController/deleteOriginalData",
				type : "post",
				dataType : "json",
				data : {
					"storageId":record.storageId,
					"deviceCode":record.deviceCode,
					"deviceName":record.deviceName,
					"thingCode":record.thingCode,
					"thingName":record.thingName,
					"thingRtd":record.thingRtd,
					"thingAvg":record.thingAvg,
					"thingMax":record.thingMax,
					"thingMin":record.thingMin,
					"updateType":record.updateType,
					"updateTypeName":record.updateTypeName,
					"rtdTime":record.rtdTime,
					"beginTime":record.beginTime,
					"endTime":record.endTime
					},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#searchUpdOrnChartContent").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 显示修改画面 */
function originalDataUpd(index) {
	var record = $("#searchUpdOrnChartContent").datagrid("getRows")[index];
	if (record == null || record == undefined) {
		$.messager.alert("提示", "请选择一条记录进行修改！", "info");
		return false;
	}
	$("#dialogModel").dialog({
		width : 450,
		height : 350,
		title : "数据修改",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		iconCls : "icon-edit",
		resizable : true,
		closed : true,
		content : '<form id="frmUpdOrgDialog" class="config-form"></form>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				if ($("#frmUpdOrgDialog").form("validate")) {
					var formdataArray = $("#frmUpdOrgDialog").serializeArray();
					var formdataJosn = getFormJson(formdataArray);//转换成json数组
					ajaxLoading();
					$.ajax({// 发送ajax请求
						url : "../MonitorStorageController/updateOriginalData",
						type : "post",
						dataType : "json",
						data : formdataJosn,
						error : function(json) {
							ajaxLoadEnd();
							$.messager.alert("提示", json.detail, "info");
						},
						success : function(json) {
							ajaxLoadEnd();
							if (json.result) {
								$("#dialogModel").dialog("close");
								$.messager.alert("提示", "操作成功！", "info");
								$("#searchUpdOrnChartContent").datagrid('reload');
							} else {
								$.messager.alert("错误", json.detail, "error");
							}
						}
					});
				}
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	/* 初始化表单 */
	$("#frmUpdOrgDialog").html(function() {
		var htmlArr = [];
		htmlArr.push(createValidatebox({
			name : "storageId",
			title : "数据存储ID",
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "deviceCode",
			title : "设备编码",
			noBlank : true,
			readonly : true,
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "deviceName",
			title : "设备名称",
			noBlank : true,
			readonly : true
		}));
		htmlArr.push(createValidatebox({
			name : "deviceMn",
			title : "设备MN号",
			noBlank : true,
			readonly : true
		}));
		htmlArr.push(createValidatebox({
			name : "thingCode",
			title : "监测物编码",
			noBlank : true,
			readonly : true,
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "thingName",
			title : "监测物名称",
			readonly : true
		}));
		htmlArr.push(createValidatebox({
			name : "updateType",
			title : "数据类型编码",
			noBlank : true,
			readonly : true,
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "rtdTime",
			title : "实时时间",
			readonly : true,
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "beginTime",
			title : "开始时间",
			readonly : true,
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "updateTypeName",
			title : "数据类型名称",
			readonly : true
		}));
		if(record.updateType == "2011"){
			htmlArr.push(createValidatebox({
				name : "thingRtd",
				title : "实时值",
				type : 'intOrFloat[0,100000000]'
			}));
		}else{
			htmlArr.push(createValidatebox({
				name : "thingAvg",
				title : "平均值",
				type : 'intOrFloat[0,100000000]'
			}));
			htmlArr.push(createValidatebox({
				name : "thingMin",
				title : "最小值",
				type : 'intOrFloat[0,100000000]'
			}));
			htmlArr.push(createValidatebox({
				name : "thingMax",
				title : "最大值",
				type : 'intOrFloat[0,100000000]'
			}));
		}
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$("#frmUpdOrgDialog").form("reset");
	$("#frmUpdOrgDialog").form("load", record);
}

/* 显示更多物质 */
function moreMonitorThingDataUpd(index) {
	var record = $("#searchUpdOrnChartContent").datagrid("getRows")[index];
	var deviceCode = record.deviceCode;
	var beginTime = record.updateTime;
	var endTime = record.updateTime;
	var updateType = record.updateType;
	var select = "moreMonitorThing";
	$("#dialogModel").dialog({
		width : 800,
		height : 400,
		title : "监测物数据信息",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : true,
		iconCls : 'icon-listtable',
		resizable : true,
		closed : true,
		content : '<div id="dgMoreMonitorThingDataUpd" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	$("#dgMoreMonitorThingDataUpd").datagrid({
		view:myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : "../MonitorStorageController/getOriginalData",
		queryParams: {
			"deviceCode": deviceCode,
			"beginTime":beginTime,
			"endTime":endTime,
			"updateType":updateType,
			"select":select
		},
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [{
			field : 'deviceName',
			title : '设备名称',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingName',
			title : '监测物名称',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'updateTypeName',
			title : '数据类型',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingRtd',
			title : '实时值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingAvg',
			title : '平均值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingMin',
			title : '最小值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingMax',
			title : '最大值',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'rtdTime',
			title : '实时数据上报时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'statusName',
			title : '实时数据标识',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'beginTime',
			title : '开始采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'endTime',
			title : '结束采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		} ] ]
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
	/* 定义分页器的初始显示默认值 */
	$("#dgMoreMonitorThingDataUpd").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if(updateType == '2011'){
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'thingRtd');
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'rtdTime');
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'statusName');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'thingAvg');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'thingMax');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'thingMin');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'beginTime');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'endTime');
	}else{
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'thingAvg');
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'thingMax');
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'thingMin');
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'beginTime');
		$("#dgMoreMonitorThingDataUpd").datagrid('showColumn', 'endTime');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'thingRtd');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'rtdTime');
		$("#dgMoreMonitorThingDataUpd").datagrid('hideColumn', 'statusName');
	}
}

/* 删除获取数据计划 */
function delRldDataFunc() {
	$.messager.alert("提示", "暂时不提供前台删除数据功能！", "warning");
	return false;
	var selectrow = $("#searchUpdOrnChartContent").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		var beginTime = $("#dtUpdOrnBeginTime").datetimebox('getValue');
		var endTime = $("#dtUpdOrnEndTime").datetimebox('getValue');
		var dtBegin = new Date(Date.parse(beginTime));
		var dtEnd= new Date(Date.parse(endTime));
		var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
		if (diffDay<365) {
			$.messager.alert("提示", "不能删除一年以内的数据！", "warning");
			return false;
		}
		idArry.push(selectrow[i].commId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示", '确定删除选定记录吗？', function(r) {
		if (r) {
			ajaxLoading();
			$.ajax({
				url : "",
				type : "post",
				dataType : "json",
				data : {
					"list" : idArry
				},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#searchUpdOrnChartContent").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}


/*查询图表信息*/
function searchUpdOrnChartFunc() {
	var treeid = -1;
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
	var beginTime = $("#dtUpdOrnBeginTime").datetimebox('getValue');
	var endTime = $("#dtUpdOrnEndTime").datetimebox('getValue');
	var cnCode = $("#updOrnCnCodeCombox").combobox("getValue");
	var timelist = {};
	if(beginTime == null || beginTime == ''){
		$.messager.alert("提示", "请填写开始时间！", "warning");
		return false;
	}
	if(endTime == null || endTime == ''){
		$.messager.alert("提示", "请填写结束时间！", "warning");
		return false;
	}
	if(cnCode == null || cnCode == ''){
		$.messager.alert("提示", "请选择数据类型！", "warning");
		return false;
	}
	var dtBegin = new Date(Date.parse(beginTime));
	var dtEnd= new Date(Date.parse(endTime));
	var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
	if(cnCode == '2011' && diffDay>2){
		$.messager.alert("提示", "只能查询2天内的实时数据！", "warning");
		return false;
	}
	if(cnCode == '2051' && diffDay>7){
		$.messager.alert("提示", "只能查询7天内的分钟数据！", "warning");
		return false;
	}
	ajaxLoading();
	var thingValue = $("#monitorThingsUpdOriginal").combobox('getValue');
	$("#monitorStationUpdOriginal").html(station.text);
	var centercontent = $("#centerUpdOrnChartContent");
	centercontent.html("");
	$("#centerUpdOrnChartContent").append('<div id="searchUpdOrnChartContent" style=""></div>');
	$.ajax({
		url : "../../../MonitorStorageController/getOriginalChartData",
		type : "post",
		dataType : "json",
		async : true,
		data : {
			"deviceCode": treeid,
			"beginTime":beginTime,
			"endTime":endTime,
			"updateType":cnCode,
			"select":treeid,
			"thingCode":thingValue
		},
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			var legendData = [];
			var seriesData = [];
			if (json.time != undefined) {
				var timeArry = json["time"];
				var max = null;
				for ( var index in json) {
					if (index != "time") {
						legendData.push(index);
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
								}
							}
						});
					} else {
						timelist[index] = json[index];
					}
				}
				initUpdOriginalChart(timelist, legendData, seriesData, '数值');
			} else {
				//$.messager.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
				initUpdOriginalChart(timelist, legendData, seriesData, '数值');
			}
			ajaxLoadEnd();
		}
	});
}

/*初始化表格*/
function initUpdOriginalChart(timelist,legendData,seriesData,yname){
	var dom = document.getElementById("searchUpdOrnChartContent");
	dom.style.cssText = "height:100%";
	mychart = echarts.init(document.getElementById("searchUpdOrnChartContent"));
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
	mychart.clear();
	mychart.setOption(option,true);
	mychart.resize(); 
}

/*导出*/
function exportUpdOrgFunction() {
	var treeid = -1;
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
	var beginTime = $("#dtUpdOrnBeginTime").datetimebox('getValue');
	var endTime = $("#dtUpdOrnEndTime").datetimebox('getValue');
	var cnCode = $("#updOrnCnCodeCombox").combobox("getValue");
	if(beginTime == null || beginTime == ''){
		$.messager.alert("提示", "请填写开始时间！", "warning");
		return false;
	}
	if(endTime == null || endTime == ''){
		$.messager.alert("提示", "请填写结束时间！", "warning");
		return false;
	}
	if(cnCode == null || cnCode == ''){
		$.messager.alert("提示", "请选择数据类型！", "warning");
		return false;
	}
	var dtBegin = new Date(Date.parse(beginTime));
	var dtEnd= new Date(Date.parse(endTime));
	var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
	if(cnCode == '2011' && diffDay>2){
		$.messager.alert("提示", "只能查询2天内的实时数据！", "warning");
		return false;
	}
	if(cnCode == '2051' && diffDay>7){
		$.messager.alert("提示", "只能查询7天内的分钟数据！", "warning");
		return false;
	}
	var thingValue = $("#monitorThingsUpdOriginal").combobox('getValue');
	location.href = "../ExportController/exportOriginalData?deviceCode="+treeid+"&beginTime="+beginTime+"&endTime="+endTime+"&updateType="+cnCode+"&thingCode="+thingValue+"";  
}