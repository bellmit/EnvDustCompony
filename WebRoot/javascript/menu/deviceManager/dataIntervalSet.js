/*******************************************************************************
 * 功能：设备信息 日期：2016-6-3 09:28:09
 ******************************************************************************/
var appendcontent = '<div id="dgdataIntervalInfo"></div>'
		+ '<div id="tbdgdataIntervalInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="setDataIntervalFunc(\'设置实时数据上报间隔\',\'icon-edit\',\'setDataIntervalFunc\')">设置数据上报间隔</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delDataIntervalFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;计划类型：<input class="easyui-combobox" name="cnCode" id="cnCode" style="width:150px;"/>'
        + '&nbsp;&nbsp;&nbsp;项目类型：<input  name="projectTypeIntervalCombox" id="projectTypeIntervalCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;所属区域：<input class="easyui-combobox" name="disAreaCombox" id="disAreaCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchDiDeviceName" data-options="onClickButton:function(){searchDataIntervalFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("设置数据上报间隔", appendcontent);

var cnCodeData = {};
var cnCodeDataValue = null;
var cnCode = [ {
	"id" : "1062",
	"name" : "实时数据间隔"
}, {
	"id" : "-1",
	"name" : "分钟数据间隔（未启用）"
} ];
$("#cnCode").combobox({
	data : cnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto'
});
initDisAreaCombox();
initProjectTypeCombox("projectTypeIntervalCombox");
/* 初始化列表,表头 */
$("#dgdataIntervalInfo").datagrid({
	view : myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	queryParams : {
		"cnCode" : $("#cnCode").combobox('getValue'),
		"deviceName" : $("#searchDiDeviceName").val(),
        "projectId" : $("#projectTypeIntervalCombox").combobox('getValue')
	},
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../DataIntervalController/getDataInterval",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgdataIntervalInfo",
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
		width : 130,
		halign : 'center',
		align : 'center'
	},{
        field : 'projectName',
        title : '项目类型',
        width : 130,
        halign : 'center',
        align : 'center'
    },{
		field : 'areaName',
		title : '所属区域',
		width : 180,
		halign : 'center',
		align : 'center'
	},{
		field : 'cnName',
		title : '计划类型',
		width : 150,
		halign : 'center',
		align : 'center'
	},{
		field : 'intervalValue',
		title : '间隔时间(分钟)',
		width : 100,
		halign : 'center',
		align : 'center'
	},{
		field : 'statusName',
		title : '执行状态',
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
$("#dgdataIntervalInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询获取数据计划 */
function searchDataIntervalFunc() {
	$("#dgdataIntervalInfo").datagrid("load", {
		"cnCode" : $("#cnCode").combobox('getValue'),
		"deviceName" : $("#searchDiDeviceName").val(),
		"areaId" : $("#disAreaCombox").combobox('getValue'),
        "projectId" : $("#projectTypeIntervalCombox").combobox('getValue')
	});
}

/* 删除获取数据计划 */
function delDataIntervalFunc(){
	var selectrow = $("#dgdataIntervalInfo").datagrid("getChecked");
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
				url : "../DataIntervalController/deleteDataInterval",
				type : "post",
				dataType : "json",
				data : {"list":idArry},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dgdataIntervalInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}
/* 初始化所属区域下拉框 */
function initDisAreaCombox(){
	$.ajax({
		url : "../DeviceController/queryDeviceAreaDropDown",
		type : "post",
		dataType : "json",
		data : {
			"id" : "-1",
			"levelFlag" : "-1"
		},
		async : false,
		success : function(json) {
			$("#disAreaCombox").combobox({
				data : json.rows,
				method : 'post',
				valueField : 'id',
				textField : 'name',
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
	});	
}
/* 设置实时数据上报间隔 */
function setDataIntervalFunc(type) {
	initDataIntervalParam();
	$("#dialogModel")
			.dialog(
					{
						width : 700,
						height : 424,
						title : "设置数据上报间隔",
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
                        		+ '<div style="margin-top:4px;">项目类型：<input name="projectTypeIntervalDCombox" id="projectTypeIntervalDCombox" style="width:150px;"/></div>'
								+ '<ul id="getDataIntervalTree" oncontextmenu="return false"></ul>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div data-options="region:\'center\',fit:true">'
								+ '<form id="frmdialogModel" class="config-form">'
								+ '<div id="dgDataIntervalData"></div>'
								+ '<div id="tbdgDataIntervalData" style="padding:5px 10px;">'
								+ '<label style="color:red;font-size:9px;display: inline;margin-top:8px;margin-left:15px;">提示：勾选设备，点击保存可将数据间隔计划下发至设备</label>'
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
										var selections = $("#getDataIntervalTree").tree('getChecked');
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
										ajaxLoading();
										$.ajax({
											url : "../DataIntervalController/insertDataInterval",
											type : "post",
											dataType : "json",
											async : false,
											data : {
												"riCnCode" : $("#riCnCode").combobox('getValue'),
												"riInterval" : $("#riInterval").val(),
												"riExcuteTime" : $("#riExcuteTime").datetimebox('getValue'),
												"list" : liststr
											},
											error : function(json) {
												ajaxLoadEnd();
											},
											success : function(json) {
												ajaxLoadEnd();
												if (json.result) {
													$("#dialogModel").dialog("close");
													$.messager.alert("提示","设置数据上报间隔操作成功！","info");
													searchDataIntervalFunc();
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
    initProjectTypeCombox("projectTypeIntervalDCombox");
/*	initTreeNoe("getDataIntervalTree", '../TreeController/getAreaTree', null,
			true);*/

	/* 初始化表单 */
	$("#dgDataIntervalData").html(function() {
		var htmlArr = [];
		htmlArr.push(createCombobox({
			name : "riCnCode",
			title : "数据类型",
			data : cnCodeData,
			value : cnCodeDataValue,
			noBlank : true
		}));
		htmlArr.push(createValidatebox({
			name : "riInterval",
			title : "间隔(分钟)",
			noBlank : true,
			type:'intOrFloat[1,9]'
		}));
		htmlArr.push(createDatetimebox({
			name : "riExcuteTime",
			title : "计划执行时间",
			showSeconds : true,
			noBlank : true
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$("#riExcuteTime").datetimebox('setValue', formatterDateHMS(new Date()));
	/* 初始化下发类型 */
	function initDataIntervalParam() {
		var array = {"1062":"实时数据间隔","-1":"分钟数据间隔（未启用）"};
		cnCodeData = array;
		cnCodeDataValue = "1062";
	}
}
