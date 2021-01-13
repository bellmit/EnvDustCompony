/*******************************************************************************
 * 功能：监测物质信息 日期：2016-3-23 13:41:09
 ******************************************************************************/
var appendcontent = '<div id="dgMonitorInfo"></div>'
		+ '<div id="tbdgMonitorInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editMonitorFunc(\'添加监控物\',\'icon-add\',\'insertMonitor\')">添加</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editMonitorFunc(\'修改检测物信息\',\'icon-edit\',\'updatetMonitor\')">修改</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delMonitorFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchMonitorName" data-options="onClickButton:function(){searchManufacturerFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'检测物名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("设置监控物质", appendcontent);

/* 初始化列表 */
$("#dgMonitorInfo").datagrid({
	view:myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : true,
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../MonitorController/queryMonitor",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgMonitorInfo",
	columns : [ [ {
		field : 'thingId',
		checkbox : true
	}, {
		field : 'thingCode',
		title : '监测物编号',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'thingName',
		title : '监测物名称',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'thingUnit',
		title : '监测物单位',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'thingOrder',
		title : '显示顺序',
		width : 80,
		halign : 'center',
		align : 'center'
	},{
		field : 'optUserName',
		title : '操作者',
		width : 100,
		halign : 'center',
		align : 'center'
	}, {
		field : 'optTime',
		title : '操作时间',
		width : 100,
		halign : 'center',
		align : 'center'
	} ] ],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip',{cls:{'max-width':'500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgMonitorInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询厂商 */
function searchManufacturerFunc() {
	$("#dgMonitorInfo").datagrid("load", {
		"thingName" : $("#searchMonitorName").val()
	});
}

/* 删除厂商 */
function delMonitorFunc() {
	var selectrow = $("#dgMonitorInfo").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		idArry.push(selectrow[i].thingId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示", '该监测物数据也将被清除，确定删除选定记录吗？', function(r) {
		if (r) {
			ajaxLoading();
			$.ajax({
				url : "../MonitorController/deleteMonitor",
				type : "post",
				dataType : "json",
				data : {
					"list" : idArry
				},
				error : function(json) {
					ajaxLoadEnd();
				},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dgMonitorInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 添加、修改检测物信息 */
function editMonitorFunc(title, icon, action) {
	if (title == "修改检测物信息") {
		var selectrow = $("#dgMonitorInfo").datagrid("getSelections");
		if (selectrow.length != 1) {
			$.messager.alert("提示", "请选择一条记录进行修改！", "info");
			return false;
		}
	}
	$("#dialogModel").dialog({
		width : 450,
		height : 424,
		title : title,
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		iconCls : icon,
		resizable : true,
		closed : true,
		content : '<form id="frmdialogModel" class="config-form"></form>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				if ($("#frmdialogModel").form("validate")) {
					var formdataArray = $("#frmdialogModel").serializeArray();// 将表单数据序列化创建一个json数组
					var formdataJosn = getFormJson(formdataArray);// 转换成json数组
					ajaxLoading();
					$.ajax({
						url : "../MonitorController/" + action,
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
								$("#dgMonitorInfo").datagrid('reload');
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
	$("#frmdialogModel").html(function() {
		var htmlArr = [];
		htmlArr.push(createValidatebox({
			name : "thingId",
			title : "监测物编号",
			ishiden : true,
			value : "-1"
		}));
		htmlArr.push(createValidatebox({
			name : "thingCode",
			title : "监测物编号",
			noBlank : true,
			type : 'maxLength[50]'
		}));
		htmlArr.push(createValidatebox({
			name : "thingName",
			title : "监测物名称",
			noBlank : true,
			type : 'maxLength[100]'
		}));
		htmlArr.push(createValidatebox({
			name : "thingUnit",
			title : "监测物单位",
			noBlank : true,
			type : 'maxLength[25]'
		}));
		htmlArr.push(createValidatebox({
			name : "thingOrder",
			title : "显示顺序",
			noBlank : true,
			value:100,
			type : 'pointeger'
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if (title == "修改检测物信息") {
		var selectrow = $("#dgMonitorInfo").datagrid("getSelected");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
	}
}