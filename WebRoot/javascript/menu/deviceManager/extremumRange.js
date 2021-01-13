/*******************************************************************************
 * 功能：设置极值范围 日期：2018-1-9 09:55:09
 ******************************************************************************/
var appendcontent = '<div id="dgExtremumRange"></div>'
		+ '<div id="tbdgExtremumRange" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="setErDataFunc(\'>设置极值范围\',\'icon-edit\',\'setErDataFunc\')">设置极值范围</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delErDataFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;计划类型：<input class="easyui-combobox" name="erCnCode" id="erCnCode" style="width:120px;"/>'
		+ '&nbsp;&nbsp;&nbsp;项目类型：<input  name="projectTypeExtCombox" id="projectTypeExtCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;所属区域：<input class="easyui-combobox" name="erAreaCombox" id="erAreaCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchErmDeviceName" data-options="onClickButton:function(){searchExtremumRangeFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("极值范围", appendcontent);

initErAreaCombox();
var erCnCodeData = {};
var erCnCodeDataValue = null;
var erMaxStateData = {};
var erMaxStateDataValue = null;
var erMinStateData = {};
var erMinStateDataValue = null;
var erCnCode = [ {
	"id" : "4005",
	"name" : "设置极值范围"
}, {
	"id" : "4006",
	"name" : "读取极值范围"
} ];
$("#erCnCode").combobox({
	data : erCnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto',
	value :"4005",
	editable:false
	
});
initProjectTypeCombox("projectTypeExtCombox");
/* 初始化列表,表头 */
$("#dgExtremumRange").datagrid({
	view : myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	queryParams : {
		"cnCode" : $("#erCnCode").combobox('getValue'),
		"deviceName" : $("#searchErmDeviceName").val(),
		"projectId" : $("#projectTypeExtCombox").combobox('getValue')
	},
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../ExtremumRangeController/getExtremumRange",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgExtremumRange",
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
        width : 120,
        halign : 'center',
        align : 'center'
    },{
		field : 'areaName',
		title : '所属区域',
		width : 150,
		halign : 'center',
		align : 'center'
	},{
		field : 'cnName',
		title : '计划类型',
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
		field : 'xMaxState',
		title : '最大值限制状态',
		width : 100,
		halign : 'center',
		align : 'center',
		formatter : function(value, row, index) {
			if (row.xMaxState=="1") {
				return "是";
			}else{
				return "否";
				
			}
		}
	},{
		field : 'xMinState',
		title : '最小值限制状态',
		width : 100,
		halign : 'center',
		align : 'center',
		formatter : function(value, row, index) {
			if (row.xMinState=="1") {
				return "是";
			}else{
				return "否";
				
			}
		}
	},{
		field : 'xMax',
		title : '最大限值(ppb)',
		width : 100,
		halign : 'center',
		align : 'center'
	},{
		field : 'xMin',
		title : '最小限值(ppb)',
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
	} ,{field:"operate",title:"操作",halign:"center",align : 'center',width:80,
    	formatter: function(value,row,index){
    		var str = '<a href="#this" title="查看结果" class="easyui-tooltip" style="margin-left:10px;"' 
    				+ 'onclick="searchErRangeResult('+row.commId+');">' 
    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/tip.png" class="operate-button"></a>';
    		return str;
    	}
    }] ],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip', {
	cls : {
		'max-width' : '500px'
	}
});

/* 定义分页器的初始显示默认值 */
$("#dgExtremumRange").datagrid("getPager").pagination({
	total : 0
});

setErRangeGridVisibleColumn();

function setErRangeGridVisibleColumn(){
	var cnCode = $("#erCnCode").combobox('getValue');
	if(cnCode == '4006'){
		$("#dgExtremumRange").datagrid('hideColumn', 'xMaxState');
		$("#dgExtremumRange").datagrid('hideColumn', 'xMinState');
		$("#dgExtremumRange").datagrid('hideColumn', 'xMax');
		$("#dgExtremumRange").datagrid('hideColumn', 'xMin');
		$("#dgExtremumRange").datagrid('showColumn', 'operate');
	}else{
		$("#dgExtremumRange").datagrid('showColumn', 'xMaxState');
		$("#dgExtremumRange").datagrid('showColumn', 'xMinState');
		$("#dgExtremumRange").datagrid('showColumn', 'xMax');
		$("#dgExtremumRange").datagrid('showColumn', 'xMin');
		$("#dgExtremumRange").datagrid('hideColumn', 'operate');
	}
}

/* 初始化所属区域下拉框 */
function initErAreaCombox(){
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
			$("#erAreaCombox").combobox({
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
/* 显示任务计划执行结果 */
function searchErRangeResult(commId) {
	$("<div></div>").dialog({
		id:"erRangeResultDialog",
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
		content : '<div id="dgErRangeResult" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
   			handler : function() {
   				$("#erRangeResultDialog").dialog("destroy");
   			}
		} ]
	}).dialog('center');
	$("#dgErRangeResult").datagrid({
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
	$("#dgErRangeResult").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#erRangeResultDialog");
	$("#erRangeResultDialog").dialog("open");
}

/* 查询获取数据计划 */
function searchExtremumRangeFunc() {
	$("#dgExtremumRange").datagrid("load", {
		"cnCode" : $("#erCnCode").combobox('getValue'),
		"deviceName" : $("#searchErmDeviceName").val(),
		"areaId" : $("#erAreaCombox").combobox('getValue'),
        "projectId" : $("#projectTypeExtCombox").combobox('getValue')
	});
	setErRangeGridVisibleColumn();
}

/* 删除获取数据计划 */
function delErDataFunc(){
	var selectrow = $("#dgExtremumRange").datagrid("getChecked");
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
				url : "../ExtremumRangeController/deleteExtremumRange",
				type : "post",
				dataType : "json",
				data : {"list":idArry},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dgExtremumRange").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 设置实时数据上报间隔 */
function setErDataFunc(type) {
	initErDataParam();
	$("#dialogModel")
			.dialog(
					{
						width : 700,
						height : 424,
						title : "x00001反控配置",
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
                                + '<div style="margin-top:4px;">项目类型：<input name="projectTypeExtDCombox" id="projectTypeExtDCombox" style="width:150px;"/></div>'
								+ '<ul id="getErDataTree" oncontextmenu="return false"></ul>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div data-options="region:\'center\',fit:true">'
								+ '<form id="frmdialogModel" class="config-form">'
								+ '<div id="dgErData"></div>'
								+ '<div id="tbdgErData" style="padding:5px 10px;">'
								+ '<label style="color:red;font-size:9px;display: inline;margin-top:8px;margin-left:15px;">提示：勾选设备，点击保存将极值范围计划下发至设备</label>'
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
										var selections = $("#getErDataTree").tree('getChecked');
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
										var cnCode = $("#xErCnCode").combobox('getValue');
										var xMaxState = $("#xMaxState").combobox('getValue');
										var xMinState = $("#xMinState").combobox('getValue');
										var xMax = $("#xMax").val();
										var xMin = $("#xMin").val();
										if(cnCode == "4005"){
											if(xMaxState == "1" && (xMax == "" || xMax == null)){
												$.messager.alert("错误","请填写最大限值！", "error");
												return;
											}
											if(xMinState == "1" && (xMin == "" || xMin == null)){
												$.messager.alert("错误","请填写最小限值！", "error");
												return;
											}
										}
										ajaxLoading();
										$.ajax({
											url : "../ExtremumRangeController/insertExtremumRangeSet",
											type : "post",
											dataType : "json",
											async : false,
											data : {
												"cnCode" : cnCode,
												"xMaxState" : xMaxState,
												"xMinState" :  xMinState,
												"xMax": xMax,
												"xMin": xMin,
												"setExcuteTime" : $("#erExcuteTime").datetimebox('getValue'),
												"list" : liststr
											},
											error : function(json) {
												ajaxLoadEnd();
											},
											success : function(json) {
												ajaxLoadEnd();
												if (json.result) {
													$("#dialogModel").dialog("close");
													$.messager.alert("提示","设置极值范围操作成功！","info");
													searchExtremumRangeFunc();
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
/*	initTreeNoe("getErDataTree", '../TreeController/getAreaTree', null,
			true);*/
    initProjectTypeCombox("projectTypeExtDCombox");
	/* 初始化表单 */
	$("#dgErData").html(function() {
		var htmlArr = [];
		htmlArr.push(createCombobox({
			name : "xErCnCode",
			title : "操作类型",
			data : erCnCodeData,
			value :erCnCodeDataValue,
			noBlank : true
		}));
		htmlArr.push(createCombobox({
			name : "xMaxState",
			title : "最大值限制状态",
			data : erMaxStateData,
			value :erMaxStateDataValue,
			noBlank : true
		}));
		htmlArr.push(createCombobox({
			name : "xMinState",
			title : "最小值限制状态",
			data : erMinStateData,
			value :erMinStateDataValue,
			noBlank : true
		}));
		htmlArr.push(createValidatebox({
			name : "xMax",
			title : "最大限值(ppm)",
			noBlank : false,
			type:'integer'
		}));
		htmlArr.push(createValidatebox({
			name : "xMin",
			title : "最小限值(ppm)",
			noBlank : false,
			type:'retainedDecimal[3]'
		}));
		htmlArr.push(createDatetimebox({
			name : "erExcuteTime",
			title : "计划执行时间",
			showSeconds : true,
			noBlank : true
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$("#erExcuteTime").datetimebox('setValue', formatterDateHMS(new Date()));
	/* 初始化下发类型 */
	function initErDataParam() {
		var array = {"4005" : "设置","4006" : "读取"};
		erCnCodeData = array;
		erCnCodeDataValue = "4005";
		erMaxStateData = {"0" : "否","1" : "是"};
		erMaxStateDataValue = "1";
		erMinStateData = {"0" : "否","1" : "是"};
		erMinStateDataValue = "1";
	}
}
