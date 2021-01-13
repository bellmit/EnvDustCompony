/*******************************************************************************
 * 功能：零点量程反控配置 日期：2018-1-9 09:55:09
 ******************************************************************************/
var appendcontent = '<div id="dgDataTemperatureControl"></div>'
		+ '<div id="tbdgDataTemperatureControl" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="setTcDataFunc(\'温度控制\',\'icon-edit\',\'setTcDataFunc\')">设置温度控制</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delDataTcFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;计划类型：<input class="easyui-combobox" name="tcCnCode" id="tcCnCode" style="width:120px;"/>'
		+ '&nbsp;&nbsp;&nbsp;项目类型：<input  name="projectTypeTemptCombox" id="projectTypeTemptCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;所属区域：<input class="easyui-combobox" name="tcCnArea" id="tcCnArea" style="width:120px;height:24px;" >'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchTcDeviceName" data-options="onClickButton:function(){searchCalibrationPointFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("温度控制", appendcontent);
var tcCnCodeData = {};
var tcCnCodeDataValue = null;
var tcTemperatureControlData = {};
var tcTemperatureControlDataValue = null;
var tcCnCode = [ {
	"id" : "4003",
	"name" : "设置温控"
}, {
	"id" : "4004",
	"name" : "读取温控"
} ];
$("#tcCnCode").combobox({
	data : tcCnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto',
	value :"4003",
	editable:false
});
initProjectTypeCombox("projectTypeTemptCombox");
/* 初始化列表,表头 */
$("#dgDataTemperatureControl").datagrid({
	view : myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	queryParams : {
		"earaId" : $("#tcCnArea").combobox('getValue'),
		"cnCode" : $("#tcCnCode").combobox('getValue'),
		"deviceName" : $("#searchDiDeviceName").val(),
		"projectId" : $("#projectTypeTemptCombox").combobox('getValue')
	},
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../TemperatureControlController/getTemperatureControl",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgDataTemperatureControl",
	columns : [ [ {
		field : 'commId',
		checkbox : true
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
        field : 'projectName',
        title : '项目类型',
        width : 130,
        halign : 'center',
        align : 'center'
    },{
		field : 'areaName',
		title : '所属区域',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'cnName',
		title : '计划类型',
		width : 100,
		halign : 'center',
		align : 'center'
	}, {
		field : 'statusName',
		title : '执行状态',
		width : 100,
		halign : 'center',
		align : 'center'
	},{
		field : 'xTpCtrlEn',
		title : '温控是否打开',
		width : 100,
		halign : 'center',
		align : 'center',
		formatter : function(value, row, index) {
			if (row.xTpCtrlEn=="1") {
				return "是";
			}else{
				return "否";
				
			}
		}
	},{
		field : 'xTpCtrl',
		title : '目标温度',
		width : 100,
		halign : 'center',
		align : 'center'
	},{
		field : 'excuteTime',
		title : '计划执行时间',
		width : 130,
		halign : 'center',
		align : 'center'
	}, {
		field : 'optUserName',
		title : '操作者',
		width : 100,
		halign : 'center',
		align : 'center'
	}, {
		field : 'optTime',
		title : '操作时间',
		width : 130,
		halign : 'center',
		align : 'center'
	},{field:"operate",title:"操作",halign:"center",align : 'center',width:80,
    	formatter: function(value,row,index){
    		var str = '<a href="#this" title="查看结果" class="easyui-tooltip" style="margin-left:10px;"' 
    				+ 'onclick="searchTcResult('+row.commId+');">' 
    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/tip.png" class="operate-button"></a>';
    		return str;
    	}
    } ] ],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip', {
	cls : {
		'max-width' : '500px'
	}
});

/* 定义分页器的初始显示默认值 */
$("#dgDataTemperatureControl").datagrid("getPager").pagination({
	total : 0
});

setTcGridVisibleColumn();

function setTcGridVisibleColumn(){
	var cnCode = $("#tcCnCode").combobox('getValue');
	if(cnCode == '4004'){
		$("#dgDataTemperatureControl").datagrid('hideColumn', 'xTpCtrlEn');
		$("#dgDataTemperatureControl").datagrid('hideColumn', 'xTpCtrl');
		$("#dgDataTemperatureControl").datagrid('showColumn', 'operate');
	}else{
		$("#dgDataTemperatureControl").datagrid('showColumn', 'xTpCtrlEn');
		$("#dgDataTemperatureControl").datagrid('showColumn', 'xTpCtrl');
		$("#dgDataTemperatureControl").datagrid('hideColumn', 'operate');
	}
}

/* 显示任务计划执行结果 */
function searchTcResult(commId) {
	$("<div></div>").dialog({
		id:"tcResultDialog",
		width : 800,
		height : 400,
		title : "任务计划执行结果",
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : true,
		iconCls : 'icon-listtable',
		resizable : true,
		closed : true,
		onClose : function() {
	         $(this).dialog('destroy');
	    },
		content : '<div id="dgTcResult" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
   			handler : function() {
   				$("#tcResultDialog").dialog("destroy");
   			}
		} ]
	}).dialog('center');
	$("#dgTcResult").datagrid({
		view:myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : "../CommMainController/getCommResult",
		queryParams: {
			"commId": commId
		},
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [{
			field : 'mainCnName',
			title : '计划类型',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'resultQn',
			title : '请求编码',
			width : 150,
			halign : 'center',
			align : 'center'
		},{
			field : 'resultCp',
			title : '请求结果',
			width : 500,
			halign : 'center',
			align : 'center'
		} ] ]
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
	// 定义分页器的初始显示默认值 
	$("#dgTcResult").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#tcResultDialog");
	$("#tcResultDialog").dialog("open");
	
}

/* 查询获取数据计划 */
function searchCalibrationPointFunc() {
	$("#dgDataTemperatureControl").datagrid("load", {
		"earaId" : $("#tcCnArea").combobox('getValue'),
		"cnCode" : $("#tcCnCode").combobox('getValue'),
		"deviceName" : $("#searchTcDeviceName").val(),
        "projectId" : $("#projectTypeTemptCombox").combobox('getValue')
	});
	setTcGridVisibleColumn();
}

/* 删除获取数据计划 */
function delDataTcFunc(){
	var selectrow = $("#dgCalibrationPoint").datagrid("getChecked");
	var idArry = [];
	for(var i=0;i<selectrow.length;i++){
		var excuteTime = selectrow[i].excuteTime;
		if(excuteTime != null && excuteTime != ''){
			var start = new Date(excuteTime.replace("-", "/").replace("-", "/"));
			var nowTime=new Date();
			if(nowTime>start){  
				$.messager.alert("提示", "超过计划执行时间，不能删除！", "warning");
		        return false;
				} 
			}
			else{
				$.messager.alert("提示", "超过计划执行时间，不能删除！", "warning");
				return false;  
			}
			idArry.push(selectrow[i].commId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示",'确定删除选定记录吗？', function(r){
		if (r){
			ajaxLoading();
			$.ajax({
				url : "../TemperatureControlController/deleteTemperatureControl",
				type : "post",
				dataType : "json",
				data : {"list":idArry},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dgDataTemperatureControl").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 设置实时数据上报间隔 */
function setTcDataFunc(type) {
	initTcDataParam();
	$("#dialogModel")
			.dialog(
					{
						width : 700,
						height : 424,
						title : "零点量程",
						inline : true,
						modal : true,
						iconCls : "icon-edit",
						maximized : false,
						collapsible : false,
						minimizable : false,
						maximizable : false,
						resizable : true,
						closed : true,
						content : '<div class="easyui-layout" data-options="fit:true">'
								+ '<div data-options="region:\'west\',title:\'可监控的站点\'" style="width:240px;">'
								+ '<div class="easyui-layout" data-options="fit:true" >'
								+ '<div data-options="region:\'center\',border:false">'
						        + '<div style="margin-top:4px;">项目类型：<input name="projectTypeTemptDCombox" id="projectTypeTemptDCombox" style="width:150px;"/></div>'
								+ '<ul id="getTcDataTree" oncontextmenu="return false"></ul>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div data-options="region:\'center\',fit:true">'
								+ '<form id="frmdialogModel" class="config-form">'
								+ '<div id="dgTcData"></div>'
								+ '<div id="tbdgTcData" style="padding:5px 10px;">'
								+ '<label style="color:red;font-size:9px;display: inline;margin-top:8px;margin-left:15px;">提示：勾选设备，点击保存将零点量程计划下发至设备</label>'
								+ '</div>' + '</form>' + '</div>' + '</div>',
						buttons : [
								{
									text : "保存",
									iconCls : "icon-ok",
									handler : function() {
									if ($("#frmdialogModel").form(
											"validate")) {
										var alarmLineDeviceTreeSelectId = [];
										var liststr = "";
										var selections = $("#getTcDataTree").tree('getChecked');
										for (var i = 0; i < selections.length; i++) {
											if (selections[i].isDevice) {// 判断是监控点
												alarmLineDeviceTreeSelectId.push(selections[i].id);
											}
										}
										if (alarmLineDeviceTreeSelectId.length == 0) {
											$.messager.alert("错误","请至少选择一个站点！", "error");
											return;
										} else {
											liststr = alarmLineDeviceTreeSelectId;
										}
										var cnCode = $("#xTcCnCode").combobox('getValue');
										var xTpCtrlEn = $("#xTpCtrlEn").combobox('getValue');
										var xTpCtrl = $("#xTpCtrl").val();
										if(cnCode == "4003"){
											if(xTpCtrlEn == "1" && (xTpCtrl == "" || xTpCtrl == null)){
												$.messager.alert("错误","请填写目标温度！", "error");
												return;
											}
										}
										ajaxLoading();
										$.ajax({
											url : "../TemperatureControlController/insertTemperatureControlSet",
											type : "post",
											dataType : "json",
											async : false,
											data : {
												"cnCode" : cnCode,
												"xTpCtrlEn" : xTpCtrlEn,
												"xTpCtrl" : xTpCtrl,
												"setExcuteTime" : $("#tcExcuteTime").datetimebox('getValue'),
												"list" : liststr
											},
											error : function(json) {
												ajaxLoadEnd();
											},
											success : function(json) {
												ajaxLoadEnd();
												if (json.result) {
													$("#dialogModel").dialog("close");
													$.messager.alert("提示","设置温度控制操作成功！","info");
													searchCalibrationPointFunc();
												} else {
													$.messager.alert("错误",json.detail,"error");
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
	initProjectTypeCombox("projectTypeTemptDCombox");
	/* 初始化表单 */
	$("#dgTcData").html(function() {
		var htmlArr = [];
		htmlArr.push(createCombobox({
			name : "xTcCnCode",
			title : "计划类型",
			data : tcCnCodeData,
			value :tcCnCodeDataValue,
			noBlank : true
		}));
		htmlArr.push(createCombobox({
			name : "xTpCtrlEn",
			title : "温控是否打开",
			data : tcTemperatureControlData,
			value :tcTemperatureControlDataValue,
			noBlank : true
		}));
		htmlArr.push(createValidatebox({
			name : "xTpCtrl",
			title : "温控目标温度(℃)",
			noBlank : false,
			type:'intOrFloat[-32768,32767]'
		}));
		htmlArr.push(createDatetimebox({
			name : "tcExcuteTime",
			title : "计划执行时间",
			showSeconds : true,
			noBlank : true
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$("#tcExcuteTime").datetimebox('setValue', formatterDateHMS(new Date()));
	/* 初始化下发类型 */
	function initTcDataParam() {
		var array = {"4003" : "设置","4004" : "读取"};
		tcCnCodeData = array;
		tcCnCodeDataValue = "4003";
		tcTemperatureControlData = {"0" : "否","1" : "是"};
		tcTemperatureControlDataValue = "1";
	}
}
//combobox的实现，导航处；实现   所属区域
$.ajax({
	url : "../DeviceController/queryDeviceAreaDropDown",
	type : "post",
	dataType : "json",
	data : {
		"id" : "-1",
		"levelFlag" : "-1"
	},
	async : true,
	success : function(json) {
		if (json.total > 0) {
			$("#tcCnArea").combobox({
				 valueField:'id',
				 textField:'name',
				 data:json.rows,
				 prompt:'所属区域...',
				 onShowPanel: function () {
						// 动态调整高度  
						if (json.rows.length < 20) {
						    $(this).combobox('panel').height("auto");  
						}else{
						    $(this).combobox('panel').height(300);
						}
					}
			});
		}
	}
});
