/*******************************************************************************
 * 功能：设备信息 日期：2016-6-3 09:28:09
 ******************************************************************************/
var appendcontent = '<div id="dggetRldDataInfo"></div>'
		+ '<div id="tbdggetRldDataInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="setRldDataFunc(\'设置站点实时数据\',\'icon-edit\',\'setRldDataFunc\')">设置站点实时数据</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delRldDataFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;计划类型：<input class="easyui-combobox" name="rldCnCodeCombox" id="rldCnCodeCombox" style="width:150px;"/>'
        + '&nbsp;&nbsp;&nbsp;项目类型：<input name="projectTypeRlCombox" id="projectTypeRlCombox" style="width:150px;"/>'
	    + '&nbsp;&nbsp;&nbsp;所在区域：<input class="easyui-combobox" name="rldCnAreaCombox" id="rldCnAreaCombox" style="width:150px;height:24px;"/>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchRdDeviceName" data-options="onClickButton:function(){searchRldDataFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("获取站点实时数据", appendcontent);
var rldCnCodeData = {};
var rldCnCodeDataValue = null;
var rldCnCode = [ {
	"id" : "2011",
	"name" : "取监测参数实时数据"
}, {
	"id" : "2012",
	"name" : "停止查看实时数据"
} ];
$("#rldCnCodeCombox").combobox({
	data : rldCnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto'
});
initProjectTypeCombox("projectTypeRlCombox");//初始化项目类型
/* 初始化列表,表头 */
$("#dggetRldDataInfo").datagrid({
	view : myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	queryParams : {
		"areaId" : $("#rldCnAreaCombox").combobox('getValue'),
		"cnCode" : $("#rldCnCodeCombox").combobox('getValue'),
		"deviceName" : $("#searchRdDeviceName").val(),
		"projectId" : $("#projectTypeRlCombox").combobox('getValue')
	},
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../ReviewDataController/getRldPlanData",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdggetRldDataInfo",
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
	},{
		field : 'deviceName',
		title : '设备名称',
		width : 120,
		halign : 'center',
		align : 'center'
	},{
        field : 'projectName',
        title : '项目类型',
        width : 120,
        halign : 'center',
        align : 'center'
    },{ //添加所属区域字段
		field : 'areaName',
		title : '所属区域',
		width : 160,
		halign : 'center',
		align : 'center'
	},{
		field : 'cnName',
		title : '计划类型',
		width : 130,
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
$("#dggetRldDataInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询获取数据计划 */
function searchRldDataFunc() {
	$("#dggetRldDataInfo").datagrid("load", {
		"areaId" : $("#rldCnAreaCombox").combobox('getValue'),
		"cnCode" : $("#rldCnCodeCombox").combobox('getValue'),
		"deviceName" : $("#searchRdDeviceName").val(),
        "projectId" :  $("#projectTypeRlCombox").combobox('getValue')
	});
}

/* 删除获取数据计划 */
function delRldDataFunc(){
	var selectrow = $("#dggetRldDataInfo").datagrid("getChecked");
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
				url : "../ReviewDataController/deleteReviewData",
				type : "post",
				dataType : "json",
				data : {"list":idArry},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dggetRldDataInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 获取站点实时数据 */
function setRldDataFunc(type) {
	initDeviceDataParam();
	$("#dialogModel")
			.dialog(
					{
						width : 700,
						height : 424,
						title : "获取站点实时数据",
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
						        +'<div style="margin-top:4px;">项目类型：<input name="projectTypeRlDCombox" id="projectTypeRlDCombox" style="width:150px;"/></div>'
								+ '<ul id="getRldDataTree" oncontextmenu="return false"></ul>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div data-options="region:\'center\',fit:true">'
								+ '<form id="frmdialogModel" class="config-form">'
								+ '<div id="dgGetRldData"></div>'
								+ '<div id="tbdgGetRldData" style="padding:5px 10px;">'
								+ '<label style="color:red;font-size:9px;display: inline;margin-top:8px;margin-left:15px;">提示：勾选设备，点击保存可将获取站点实时数据计划下发至设备</label>'
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
										var selections = $("#getRldDataTree").tree('getChecked');
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
											url : "../ReviewDataController/insertRldPlanData",
											type : "post",
											dataType : "json",
											async : false,
											data : {
												"rldCnCode" : $("#rldCnCode").combobox('getValue'),
												"rldExcuteTime" : $("#rldExcuteTime").datetimebox('getValue'),
												"list" : liststr
											},
											error : function(json) {
												ajaxLoadEnd();
											},
											success : function(json) {
												ajaxLoadEnd();
												if (json.result) {
													$("#dialogModel").dialog("close");
													$.messager.alert("提示","设置获取站点实时数据操作成功！","info");
													searchRldDataFunc();
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
    initProjectTypeCombox("projectTypeRlDCombox");
/*	initTreeNoe("getRldDataTree", '../TreeController/getAreaTree', null,
			true);*/

	/* 初始化表单 */
	$("#dgGetRldData").html(function() {
		var htmlArr = [];
		htmlArr.push(createCombobox({
			name : "rldCnCode",
			title : "计划类型",
			data : rldCnCodeData,
			value : rldCnCodeDataValue,
			noBlank : true
		}));
		htmlArr.push(createDatetimebox({
			name : "rldExcuteTime",
			title : "计划执行时间",
			showSeconds : true,
			noBlank : true
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$("#rldExcuteTime").datetimebox('setValue', formatterDateHMS(new Date()));
	/* 初始化下发类型 */
	function initDeviceDataParam() {
		
		var array = {"2011":"取监测参数实时数据","2012":"停止查看实时数据"};
		rldCnCodeData = array;
		rldCnCodeDataValue = "2011";
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
			$("#rldCnAreaCombox").combobox({
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
