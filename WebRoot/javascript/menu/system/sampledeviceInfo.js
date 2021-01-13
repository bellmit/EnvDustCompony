/*******************************************************************************
 * 功能：采样设备信息 日期：2016-3-23 13:41:09
 ******************************************************************************/
var appendcontent = '<div id="sampledeviceInfo"></div>'
		+ '<div id="tbsampledeviceInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editCollectDeviceFunc(\'添加设备\',\'icon-add\',\'insertCollectDevice\')">添加</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editCollectDeviceFunc(\'修改采样设备信息\',\'icon-edit\',\'updateCollectDevice\')">修改</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delCollectDeviceFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchCdName" data-options="onClickButton:function(){searchCdFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'采样设备名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("设置采样设备", appendcontent);

/* 初始化列表,表头 */
$("#sampledeviceInfo").datagrid({
	view:myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	singleSelect : true,
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../CollectDeviceController/queryCollectDevice",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbsampledeviceInfo",
	columns : [ [ {
		field : 'cdId',
		checkbox : true
	}, {
		field : 'cdCode',
		title : '设备编号',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'cdMn',
		title : '设备MN号',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'cdName',
		title : '设备名称',
		width : 120,
		halign : 'center',
		align : 'center'
	},{
		field : 'cdIp',
		title : '设备IP',
		width : 100,
		halign : 'center',
		align : 'center'
	}, {
		field : 'cdPort',
		title : '设备端口',
		width : 100,
		halign : 'center',
		align : 'center',
		formatter : function(value, row, index) {
			if (row.cdPort == -1) {
				row.cdPort = null
			}
			return row.cdPort
		}
	}, {
		field : 'cdPwd',
		title : '设备访问密码',
		width : 100,
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
		width : 130,
		halign : 'center',
		align : 'center'
	} ] ],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip',{cls:{'max-width':'500px'}});

/* 定义分页器的初始显示默认值 */
$("#sampledeviceInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询设备 */
function searchCdFunc() {
	$("#sampledeviceInfo").datagrid("load", {
		"cdName" : $("#searchCdName").val()
	});
}

/* 删除设备 */
function delCollectDeviceFunc() {
	var selectrow = $("#sampledeviceInfo").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		idArry.push(selectrow[i].cdId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示", '该设备采样数据也将被清除，确定删除选定记录吗？', function(r) {
		if (r) {
			ajaxLoading();
			$.ajax({
				url : "../CollectDeviceController/deleteCollectDevice",
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
						$("#sampledeviceInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 添加、修改采样设备信息 */
function editCollectDeviceFunc(title, icon, action) {
	if (title == "修改采样设备信息") {
		var selectrow = $("#sampledeviceInfo").datagrid("getSelections");
		if (selectrow.length != 1) {
			$.messager.alert("提示", "请选择一条记录进行修改！", "info");
			return false;
		}
	}
	$("#dialogModel").dialog({
		width : 460,
		height : 360,
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
		content : '<form id="frmdialogModel" class="config-form"  style="width:400px;"></form>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				if ($("#frmdialogModel").form("validate")) {
					var formdataArray = $("#frmdialogModel").serializeArray();// 将表单数据序列化创建一个json数组
					var formdataJosn = getFormJson(formdataArray);// 转换成json数组
					ajaxLoading();
					$.ajax({
						url : "../CollectDeviceController/" + action,
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
								$("#sampledeviceInfo").datagrid('reload');
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
			name : "cdId",
			title : "设备ID",
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "cdCode",
			title : "设备编号",
			noBlank : true,
			type : 'devicecode'
		}));
		htmlArr.push(createValidatebox({
			name : "cdMn",
			title : "设备MN号",
			noBlank : true,
			type : 'maxLength[27]'
		}));
		htmlArr.push(createValidatebox({
			name : "cdName",
			title : "设备名称",
			noBlank : true,
			type : 'maxLength[100]'
		}));
		htmlArr.push(createValidatebox({
			name : "cdIp",
			title : "设备IP",
			type : 'ip'
		}));
		htmlArr.push(createValidatebox({
			name : "cdPort",
			title : "设备端口",
			value : "1000",
			type : 'devicevalid'
		}));
		htmlArr.push(createValidatebox({
			name : "cdPwd",
			title : "设备访问密码",
			noBlank : true,
			type : 'maxLength[6]'
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if (title == "修改采样设备信息") {
		var selectrow = $("#sampledeviceInfo").datagrid("getSelected");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
		$("#cdCode").attr("readonly","readonly");
	}else{
		 $("#cdCode").removeAttr("readonly"); 
	}
}