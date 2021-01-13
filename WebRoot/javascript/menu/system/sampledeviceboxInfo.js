/*******************************************************************************
 * 功能：采样设备箱子信息 日期：2016-3-23 13:41:09
 ******************************************************************************/
var appendcontent = '<div id="sampledeviceboxInfo"></div>'
		+ '<div id="tbsampledeviceboxInfo" style="padding:5px 10px;">'
		+'采样设备：<input id="monitorBoxDevice" class="easyui-combobox" style="width:150px;">'
		+ '&nbsp;&nbsp;<input class="easyui-textbox" id="searchBoxName" data-options="onClickButton:function(){searchBoxFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'采样箱子名称...\'" style="width:200px;height:24px;">'
		+ '&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editCollectBoxFunc(\'添加采样箱子\',\'icon-add\',\'insertCollectDeviceBox\')">添加</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editCollectBoxFunc(\'修改采样箱子信息\',\'icon-edit\',\'updateCollectDeviceBox\')">修改</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delCollectBoxFunc()">删除</a>'
		+ '</div>';
addPanel("设置采样设备箱子", appendcontent);

var boxStatusData = {
		"0" : "就绪",
		"1" : "充满",
		"2" : "故障"
	};
var boxStatusDataValue = "0";
//采样设备
var comboboxJsonBoxSample = [];

//采样设备
$.ajax({
	url : "../CollectDeviceController/queryCollectDevice",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		if(json != null && json.result){
			comboboxJsonBoxSample = json.rows;
		}
	}
});
$("#monitorBoxDevice").combobox({
	  data:comboboxJsonBoxSample,
	  method:'post',
	  valueField:'cdId',
	  textField:'cdName',
	  panelHeight:'auto',
	  value:comboboxJsonBoxSample[0].cdId
});

/* 初始化列表,表头 */
$("#sampledeviceboxInfo").datagrid({
	view:myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : false,
	singleSelect : true,
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../CollectDeviceBoxController/queryCollectDeviceBox",
	queryParams: {
		"cdId": $("#monitorBoxDevice").combobox('getValue'),
		"boxName" : $("#searchBoxName").val()
	},
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbsampledeviceboxInfo",
	columns : [ [ {
		field : 'boxId',
		checkbox : true
	}, {
		field : 'boxNo',
		title : '箱子编号',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'boxName',
		title : '箱子名称',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'boxStatus',
		title : '箱子状态编码',
		width : 120,
		halign : 'center',
		align : 'center',
		hidden:true
	}, {
		field : 'boxStatusName',
		title : '箱子状态',
		width : 80,
		halign : 'center',
		align : 'center'
	},{
		field : 'cdId',
		title : '设备ID',
		width : 100,
		halign : 'center',
		align : 'center',
		hidden:true
	},{
		field : 'cdCode',
		title : '设备编码',
		width : 100,
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
$("#sampledeviceboxInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询箱子 */
function searchBoxFunc() {
	$("#sampledeviceboxInfo").datagrid("load", {
		"cdId": $("#monitorBoxDevice").combobox('getValue'),
		"boxName" : $("#searchBoxName").val()
	});
}

/* 删除箱子 */
function delCollectBoxFunc() {
	var selectrow = $("#sampledeviceboxInfo").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		idArry.push(selectrow[i].boxId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示", '该箱子采样数据也将被清除，确定删除选定记录吗？', function(r) {
		if (r) {
			ajaxLoading();
			$.ajax({
				url : "../CollectDeviceBoxController/deleteCollectDeviceBox",
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
						$("#sampledeviceboxInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 添加、修改采样箱子信息 */
function editCollectBoxFunc(title, icon, action) {
	if (title == "修改采样箱子信息") {
		var selectrow = $("#sampledeviceboxInfo").datagrid("getSelections");
		if (selectrow.length != 1) {
			$.messager.alert("提示", "请选择一条记录进行修改！", "info");
			return false;
		}
	}else{
		var cdCode = $("#monitorBoxDevice").combobox('getValue');
		if(cdCode == null || cdCode == undefined || cdCode == ""){
			$.messager.alert("警告", "请选择采样设备！", "warning");
			return;
		}
	}
	$("#dialogModel").dialog({
		width : 460,
		height : 260,
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
						url : "../CollectDeviceBoxController/" + action,
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
								$("#sampledeviceboxInfo").datagrid('reload');
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
			name : "boxId",
			title : "箱子ID",
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "boxNo",
			title : "箱子编号",
			noBlank : true,
			type : 'maxLength[50]'
		}));
		htmlArr.push(createValidatebox({
			name : "boxName",
			title : "箱子名称",
			noBlank : true,
			type : 'maxLength[27]'
		}));
		htmlArr.push(createCombobox({
			name : "boxStatus",
			title : "箱子状态",
			data : boxStatusData,
			valueField : 'code',
			textField : 'name',
			value:boxStatusDataValue
		}));
		htmlArr.push(createValidatebox({
			name : "cdId",
			title : "设备Id",
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "cdName",
			title : "设备名称"
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if (title == "修改采样箱子信息") {
		var selectrow = $("#sampledeviceboxInfo").datagrid("getSelected");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
	}else{
		 $("#cdId").val($("#monitorBoxDevice").combobox('getValue'));
		 $("#cdName").val($("#monitorBoxDevice").combobox('getText')); 
	}
	$("#cdName").attr("readonly","readonly");
}