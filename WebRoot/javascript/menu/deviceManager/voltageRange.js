/*******************************************************************************
 * 功能：零点量程反控配置 日期：2018-1-9 09:55:09
 ******************************************************************************/
var appendcontent = '<div id="dgVoltageRange"></div>'
		+ '<div id="tbdgVoltageRange" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="setVrDataFunc(\'零点量程\',\'icon-edit\',\'setVrDataFunc\')">设置零点量程</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delDataVrFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;计划类型：<input class="easyui-combobox" name="vrCnCode" id="vrCnCode" style="width:120px;"/>'
		+ '&nbsp;&nbsp;&nbsp;项目类型：<input  name="projectTypeVolCombox" id="projectTypeVolCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;所属区域：<input class="easyui-combobox" name="vrAreaCombox" id="vrAreaCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchVrDeviceName" data-options="onClickButton:function(){searchVoltageRangeFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("零点量程", appendcontent);

var vrCnCodeData = {};
var vrCnCodeDataValue = null;
var vrCnCode = [ {
	"id" : "4001",
	"name" : "设置零点量程"
}, {
	"id" : "4002",
	"name" : "读取零点量程"
} ];
$("#vrCnCode").combobox({
	data : vrCnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto',
	value :"4001",
	editable:false
});
initVrAreaCombox();
initProjectTypeCombox("projectTypeVolCombox");
/* 初始化列表,表头 */
$("#dgVoltageRange").datagrid({
	view : myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	queryParams : {
		"cnCode" : $("#vrCnCode").combobox('getValue'),
		"deviceName" : $("#searchVrDeviceName").val(),
		"projectId" : $("#projectTypeVolCombox").combobox('getValue')
	},
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../VoltageRangeController/getVoltageRange",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgVoltageRange",
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
    }, {
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
		field : 'xZeroVolt',
		title : '零点电压(mv)',
		width : 100,
		halign : 'center',
		align : 'center'
	},{
		field : 'xScaleVolt',
		title : '量程点电压(mv)',
		width : 100,
		halign : 'center',
		align : 'center'
	},{
		field : 'xScaleConc',
		title : '量程点浓度(ppm)',
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
    				+ 'onclick="searchVrRangeResult('+row.commId+');">' 
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

/* 初始化所属区域下拉框 */
function initVrAreaCombox(){
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
			$("#vrAreaCombox").combobox({
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
/* 定义分页器的初始显示默认值 */
$("#dgVoltageRange").datagrid("getPager").pagination({
	total : 0
});

setVrRangeGridVisibleColumn();

function setVrRangeGridVisibleColumn(){
	var cnCode = $("#vrCnCode").combobox('getValue');
	if(cnCode == '4002'){
		$("#dgVoltageRange").datagrid('hideColumn', 'xZeroVolt');
		$("#dgVoltageRange").datagrid('hideColumn', 'xScaleVolt');
		$("#dgVoltageRange").datagrid('hideColumn', 'xScaleConc');
		$("#dgVoltageRange").datagrid('showColumn', 'operate');
	}else{
		$("#dgVoltageRange").datagrid('showColumn', 'xZeroVolt');
		$("#dgVoltageRange").datagrid('showColumn', 'xScaleVolt');
		$("#dgVoltageRange").datagrid('showColumn', 'xScaleConc');
		$("#dgVoltageRange").datagrid('hideColumn', 'operate');
	}
}

/* 显示任务计划执行结果 */
function searchVrRangeResult(commId) {
	$("<div></div>").dialog({
		id:"vrRangeResultDialog",
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
		content : '<div id="dgVrRangeResult" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
   			handler : function() {
   				$("#vrRangeResultDialog").dialog("destroy");
   			}
		} ]
	}).dialog('center');
	$("#dgVrRangeResult").datagrid({
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
	$("#dgVrRangeResult").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#vrRangeResultDialog");
	$("#vrRangeResultDialog").dialog("open");
	
}

/* 查询获取数据计划 */
function searchVoltageRangeFunc() {
	$("#dgVoltageRange").datagrid("load", {
		"cnCode" : $("#vrCnCode").combobox('getValue'),
		"deviceName" : $("#searchVrDeviceName").val(),
		"areaId" : $("#vrAreaCombox").combobox('getValue'),
        "projectId" : $("#projectTypeVolCombox").combobox('getValue')
	});
	setVrRangeGridVisibleColumn();
}

/* 删除获取数据计划 */
function delDataVrFunc(){
	var selectrow = $("#dgVoltageRange").datagrid("getChecked");
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
				url : "../VoltageRangeController/deleteVoltageRange",
				type : "post",
				dataType : "json",
				data : {"list":idArry},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dgVoltageRange").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 设置实时数据上报间隔 */
function setVrDataFunc(type) {
	initVrDataParam();
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
                                + '<div style="margin-top:4px;">项目类型：<input name="projectTypeVolDCombox" id="projectTypeVolDCombox" style="width:150px;"/></div>'
								+ '<ul id="getVrDataTree" oncontextmenu="return false"></ul>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div data-options="region:\'center\',fit:true">'
								+ '<form id="frmdialogModel" class="config-form">'
								+ '<div id="dgVrData"></div>'
								+ '<div id="tbdgVrData" style="padding:5px 10px;">'
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
										var selections = $("#getVrDataTree").tree('getChecked');
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
										var cnCode = $("#xVrCnCode").combobox('getValue');
										var xZeroVolt = $("#xZeroVolt").val();
										var xScaleVolt = $("#xScaleVolt").val();
										var xScaleConc = $("#xScaleConc").val();
										if(cnCode == "4001" && (xZeroVolt=="" || xZeroVolt==null 
												|| xScaleVolt=="" || xScaleVolt==null
												|| xScaleConc=="" || xScaleConc==null)){
											$.messager.alert("错误","请填写设置数值！", "error");
											return;
										}
										ajaxLoading();
										$.ajax({
											url : "../VoltageRangeController/insertVoltageRangeSet",
											type : "post",
											dataType : "json",
											async : false,
											data : {
												"cnCode" : cnCode,
												"xZeroVolt" : xZeroVolt,
												"xScaleVolt" : xScaleVolt,
												"xScaleConc": xScaleConc,
												"setExcuteTime" : $("#vrExcuteTime").datetimebox('getValue'),
												"list" : liststr
											},
											error : function(json) {
												ajaxLoadEnd();
											},
											success : function(json) {
												ajaxLoadEnd();
												if (json.result) {
													$("#dialogModel").dialog("close");
													$.messager.alert("提示","设置零点量程操作成功！","info");
													searchVoltageRangeFunc();
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
	/*initTreeNoe("getVrDataTree", '../TreeController/getAreaTree', null,true);*/
    initProjectTypeCombox("projectTypeVolDCombox");
	/* 初始化表单 */
	$("#dgVrData").html(function() {
		var htmlArr = [];
		htmlArr.push(createCombobox({
			name : "xVrCnCode",
			title : "计划类型",
			data : vrCnCodeData,
			value :vrCnCodeDataValue,
			noBlank : true
		}));
		htmlArr.push(createValidatebox({
			name : "xZeroVolt",
			title : "零点电压(mv)",
			noBlank : false,
			type:'intOrFloat[-32768,32767]'
		}));
		htmlArr.push(createValidatebox({
			name : "xScaleVolt",
			title : "量程点电压(mv)",
			noBlank : false,
			type:'intOrFloat[-32768,32767]'
		}));
		htmlArr.push(createValidatebox({
			name : "xScaleConc",
			title : "量程点浓度(ppm)",
			noBlank : false,
			type:'intOrFloat[-32768,32767]'
		}));
		htmlArr.push(createDatetimebox({
			name : "vrExcuteTime",
			title : "计划执行时间",
			showSeconds : true,
			noBlank : true
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$("#vrExcuteTime").datetimebox('setValue', formatterDateHMS(new Date()));
	/* 初始化下发类型 */
	function initVrDataParam() {
		var array = {"4001" : "设置","4002" : "读取"};
		vrCnCodeData = array;
		vrCnCodeDataValue = "4001";
	}
}
