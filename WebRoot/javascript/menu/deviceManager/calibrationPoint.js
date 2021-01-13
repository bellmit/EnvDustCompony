/*******************************************************************************
 * 功能：校准点位  日期：2018-1-9 14:56:09
 ******************************************************************************/
var appendcontent = '<div id="dgdataCalibrationPoint"></div>'
		+ '<div id="tbdgdataCalibrationPoint" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="setCalibrationPoint(\'校准点位\',\'icon-edit\',\'setCalibrationPoint\')">校准点位</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delCalibrationPoint()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;操作类型：<input class="easyui-combobox" name="calCnCode" id="calCnCode" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchCalDeviceName" data-options="onClickButton:function(){searchCalDataFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("校准点位", appendcontent);
var calCnCodeData = {};
var calCnCodeData = null;
var calCnCode = [ {
	"id" : "4001",
	"name" : "设置"
}, {
	"id" : "4002",
	"name" : "读取"
} ];
$("#calCnCode").combobox({
	data : calCnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto'
});
/* 初始化列表,表头 */
$("#dgdataCalibrationPoint").datagrid({
	view : myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	queryParams : {
		"cnCode" : $("#calCnCode").combobox('getValue'),
		"deviceName" : $("#searchCalDeviceName").val()
	},
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../DataIntervalController/getDataInterval",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgdataCalibrationPoint",
	columns : [ [{
		field : 'commId',
		checkbox : true,
		rowspan:2
	}, {
		field : 'deviceCode',
		title : '设备编号',
		width : 120,
		halign : 'center',
		align : 'center',
		rowspan:2
	}, {
		field : 'deviceMn',
		title : '设备MN号',
		width : 120,
		halign : 'center',
		align : 'center',
		rowspan:2
	}, {
		field : 'deviceName',
		title : '设备名称',
		width : 120,
		halign : 'center',
		align : 'center',
		rowspan:2
	}, {
		field : 'cnName',
		title : '计划类型',
		width : 100,
		halign : 'center',
		align : 'center',
		rowspan:2
	},{
		field : 'statusName',
		title : '执行状态',
		width : 100,
		halign : 'center',
		align : 'center',
		rowspan:2
	},{
		field : 'xState',
		title : ' 湿度校正',
		width : 100,
		halign : 'center',
		align : 'center',
		rowspan:2
	},
	{title:'校准点0',order: 2,colspan:3},  
    {title:'校准点1',order: 2,colspan:3},
    {title:'校准点2',order: 2,colspan:3},
    {title:'校准点3',order: 2,colspan:3},
    {title:'校准点4',order: 2,colspan:3},
    {
		field : 'excuteTime',
		title : '计划执行时间',
		width : 130,
		halign : 'center',
		align : 'center',
		rowspan:2
	}, {
		field : 'optUserName',
		title : '操作者',
		width : 100,
		halign : 'center',
		align : 'center',
		rowspan:2
	}, {
		field : 'optTime',
		title : '操作时间',
		width : 130,
		halign : 'center',
		align : 'center',
		rowspan:2
	}],
	[{
		field : 'xP0RH',
		title : '相对湿度(%RH)',
		width : 140,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP0K',
		title : '斜率',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP0B',
		title : '截距',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP1RH',
		title : '相对湿度(%RH)',
		width : 140,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP1K',
		title : '斜率',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP1B',
		title : '截距',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP2RH',
		title : '相对湿度(%RH)',
		width : 140,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP2K',
		title : '斜率',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP2B',
		title : '截距',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP3RH',
		title : '相对湿度(%RH)',
		width : 140,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP3K',
		title : '斜率',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP3B',
		title : '截距',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP4RH',
		title : '相对湿度(%RH)',
		width : 140,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP4K',
		title : '斜率',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	},{
		field : 'xP4B',
		title : '截距',
		width : 100,
		rowspan:1,
		halign : 'center',
		align : 'center'
	}]],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip', {
	cls : {
		'max-width' : '500px'
	}
});

/* 定义分页器的初始显示默认值 */
$("#dgdataCalibrationPoint").datagrid("getPager").pagination({
	total : 0
});

/* 查询获取数据计划 */
function searchCalDataFunc() {
	$("#dgdataCalibrationPoint").datagrid("load", {
		"calCnCode" : $("#calCnCode").combobox('getValue'),
		"deviceName" : $("#searchCalDeviceName").val()
	});
}

/* 删除获取数据计划 */
function delCalibrationPoint(){
	var selectrow = $("#dgdataCalibrationPoint").datagrid("getChecked");
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
						$("#dgdataCalibrationPoint").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 设置实时数据上报间隔 */
function setCalibrationPoint(type) {
	initxCalDataParam();
	initxStateDataParam();
	$("#dialogModel")
			.dialog(
					{
						width : 782,
						height : 571,
						title : "校准点位",
						inline : true,
						modal : true,
						iconCls : "icon-edit",
						maximized : false,
						collapsible : false,
						minimizable : false,
						maximizable : false,
						resizable : false,
						closed : true,
						content : '<div class="easyui-layout" data-options="fit:true">'
									+ '<div data-options="region:\'west\',title:\'可监控的站点\'" style="width:240px;">'
									+ '<ul id="getCalDataTree" oncontextmenu="return false"></ul>'
									+ '</div>'
									+ '<div data-options="region:\'center\'">'
										+ '<form id="frmdialogModel" class="config-form">'
											+ '<div id="dgCalPointData"></div>'
											+ '<div id="tbdgCalPointData" style="padding:5px 10px;">'
											+ '<label style="color:red;font-size:9px;display: inline;margin-top:8px;margin-left:15px;">提示：勾选设备，点击保存可将数据间隔计划下发至设备</label>'
											+ '</div>'
										+ '</form>'
									+ '</div>'
								+ '</div>',
						buttons : [
								{
									text : "保存",
									iconCls : "icon-ok",
									handler : function() {
									if ($("#frmdialogModel").form(
											"validate")) {
										var alarmLineDeviceTreeSelectId = [];
										var liststr = "";
										var selections = $("#getCalDataTree").tree('getChecked');
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
										
										if($('#xP0').is(':checked')==false 
												&& $('#xP1').is(':checked')==false
												&& $('#xP2').is(':checked')==false 
												&& $('#xP3').is(':checked')==false
												&& $('#xP4').is(':checked')==false){
											$.messager.alert("错误","请至少勾选一个校准点并填写校准点的信息！", "error");
											return;
										}
										if($('#xP0').is(':checked')){
											if($("#xP0RH").val()=="" || $("#xP0K").val()=="" ||$("#xP0B").val()==""){
												$.messager.alert("错误","请填写完整校准点0的信息！", "error");
												$("#xP0RH").focus();
												return;
											}
										}
										if($('#xP1').is(':checked')){
											if($("#xP1RH").val()=="" || $("#xP1K").val()=="" ||$("#xP1B").val()==""){
												$.messager.alert("错误","请填写完整校准点1的信息！", "error");
												$("#xP1RH").focus();
												return;
											}
										}
										if($('#xP2').is(':checked')){
											if($("#xP2RH").val()=="" || $("#xP2K").val()=="" ||$("#xP2B").val()==""){
												$.messager.alert("错误","请填写完整校准点2的信息！", "error");
												$("#xP2RH").focus();
												return;
											}
										}
										if($('#xP3').is(':checked')){
											if($("#xP3RH").val()=="" || $("#xP3K").val()=="" ||$("#xP3B").val()==""){
												$.messager.alert("错误","请填写完整校准点3的信息！", "error");
												$("#xP3RH").focus();
												return;
											}
										}if($('#xP4').is(':checked')){
											if($("#xP4RH").val()=="" || $("#xP4K").val()=="" ||$("#xP4B").val()==""){
												$.messager.alert("错误","请填写完整校准点4的信息！", "error");
												$("#xP4RH").focus();
												return;
											}
										}
										ajaxLoading();
										$.ajax({
											url : "../DataIntervalController/insertDataInterval",
											type : "post",
											dataType : "json",
											async : false,
											data : {
												"xcalCnCode" : $("#xcalCnCode").combobox('getValue'),
												"xState" :  $("#xState").combobox('getValue'),
												"xP0RH" :  $("#xP0RH").val(),
												"xP0K": $("#xP0K").val(),
												"xP0B": $("#xP0B").val(),
												"xP1RH" :  $("#xP1RH").val(),
												"xP1K": $("#xP1K").val(),
												"xP1B": $("#xP1B").val(),
												"xP2RH" :  $("#xP2RH").val(),
												"xP2K": $("#xP2K").val(),
												"xP2B": $("#xP2B").val(),
												"xP3RH" :$("#xP3RH").val(),
												"xP3K": $("#xP3K").val(),
												"xP3B": $("#xP3B").val(),
												"xP4RH" :  $("#xP4RH").val(),
												"xP4K": $("#xP4K").val(),
												"xP4B": $("#xP4B").val(),
												"setExcuteTime" : $("#calExcuteTime").datetimebox('getValue'),
												"list" : liststr
											},
											error : function(json) {
												ajaxLoadEnd();
											},
											success : function(json) {
												ajaxLoadEnd();
												if (json.result) {
													$("#dialogModel").dialog("close");
													$.messager.alert("提示","设置校准点位操作成功！","info");
													searchCalDataFunc();
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
	initTreeNoe("getCalDataTree", '../TreeController/getAreaTree', null,
			true,"getCalDataTree");

	/* 初始化表单 */
	$("#dgCalPointData").html(function() {
		var htmlArr = [];
		htmlArr.push(createCombobox({
			name : "xcalCnCode",
			title : "操作类型",
			data : calCnCodeData,
			value :calCnCodeDataValue,
			noBlank : true
		}));
		htmlArr.push(createCombobox({
			name : "xState",
			title : "湿度校正状态",
			data : xStateCnCodeData,
			value :xStateCnCodeDataValue,
			noBlank : true
		}));
		htmlArr.push("<fieldset style='width:auto;margin:15px 15px'>");
		htmlArr.push("<legend style='border:0px;background-color:white;margin-left:5px;'>");
		htmlArr.push("<span><input type='checkbox'  id='xP0' name='xP0'  style='margin:5px;'/>校准点0</span>");
		htmlArr.push("</legend>");
		htmlArr.push(createValidatebox({
			name : "xP0RH",
			title : "相对湿度(%RH)",
			type:'positiveInteger'
		}));
		htmlArr.push(createValidatebox({
			name : "xP0K",
			title : "斜率",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push(createValidatebox({
			name : "xP0B",
			title : "截距",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push("</fieldset>");
		
		htmlArr.push("<fieldset style='width:auto;margin:15px 15px'>");
		htmlArr.push("<legend style='border:0px;background-color:white;margin-left:5px;'>");
		htmlArr.push("<span><input type='checkbox'  id='xP1' name='xP1'  style='margin:5px;'/>校准点1</span>");
		htmlArr.push("</legend>");
		htmlArr.push(createValidatebox({
			name : "xP1RH",
			title : "相对湿度(%RH)",
			type:'positiveInteger'
		}));
		htmlArr.push(createValidatebox({
			name : "xP1K",
			title : "斜率",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push(createValidatebox({
			name : "xP1B",
			title : "截距",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push("</fieldset>");
		
		htmlArr.push("<fieldset style='width:auto;margin:15px 15px'>");
		htmlArr.push("<legend style='border:0px;background-color:white;margin-left:5px;'>");
		htmlArr.push("<span><input type='checkbox'  id='xP2' name='xP2'  style='margin:5px;'/>校准点2</span>");
		htmlArr.push("</legend>");
		htmlArr.push(createValidatebox({
			name : "xP2RH",
			title : "相对湿度(%RH)",
			type:'positiveInteger'
		}));
		htmlArr.push(createValidatebox({
			name : "xP2K",
			title : "斜率",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push(createValidatebox({
			name : "xP2B",
			title : "截距",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push("</fieldset>");
		
		htmlArr.push("<fieldset style='width:auto;margin:15px 15px'>");
		htmlArr.push("<legend style='border:0px;background-color:white;margin-left:5px;'>");
		htmlArr.push("<span><input type='checkbox'  id='xP3' name='xP3' style='margin:5px;'/>校准点3</span>");
		htmlArr.push("</legend>");
		htmlArr.push(createValidatebox({
			name : "xP3RH",
			title : "相对湿度(%RH)",
			type:'positiveInteger'
		}));
		htmlArr.push(createValidatebox({
			name : "xP3K",
			title : "斜率",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push(createValidatebox({
			name : "xP3B",
			title : "截距",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push("</fieldset>");
		
		htmlArr.push("<fieldset style='width:auto;margin:15px 15px'>");
		htmlArr.push("<legend style='border:0px;background-color:white;margin-left:5px;'>");
		htmlArr.push("<span><input type='checkbox'  id='xP4' name='xP4'  style='margin:5px;'/>校准点4</span>");
		htmlArr.push("</legend>");
		htmlArr.push(createValidatebox({
			name : "xP4RH",
			title : "相对湿度(%RH)",
			type:'positiveInteger'
		}));
		htmlArr.push(createValidatebox({
			name : "xP4K",
			title : "斜率",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push(createValidatebox({
			name : "xP4B",
			title : "截距",
			type:'retainedDecimal[6]'
		}));
		htmlArr.push("</fieldset>");
		htmlArr.push(createDatetimebox({
			name : "calExcuteTime",
			title : "计划执行时间",
			showSeconds : true,
			noBlank : true
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$("#calExcuteTime").datetimebox('setValue', formatterDateHMS(new Date()));
	/* 初始化下发类型 */
	function initxCalDataParam() {
		var array = {"4001" : "设置","4002" : "读取"};
		calCnCodeData = array;
		calCnCodeDataValue = "4001";
	}
	/* 初始化下发类型 */
	function initxStateDataParam() {
		var array = {"0" : "否","1" : "是"};
		xStateCnCodeData = array;
		xStateCnCodeDataValue = "0";
	}
}
