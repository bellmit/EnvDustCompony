/************************************
 * 功能：IP信息
 * 日期：2016-4-5 13:41:09
 ************************************/
var appendcontent = '<div id="dgIpInfo"></div>'
		+ '<div id="tbdgIpInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editIpFunc(\'添加IP\',\'icon-add\',\'insertIp\')">添加</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editIpFunc(\'修改IP信息\',\'icon-edit\',\'updateIp\')">修改</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delIpFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchIpRemark" data-options="onClickButton:function(){searchIpFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'IP备注名称...\'" style="width:200px;height:24px;">'
		+ '<span id="ddddgIpInfo" style=" float:right" >'
		+'除此之外全部限制：<input type="radio" name="iplimit" value="1">'
		+'&nbsp;&nbsp;除此之外全部允许：<input type="radio" name="iplimit" value="2">'
		+'&nbsp;&nbsp;无限制：<input type="radio" name="iplimit" value="3">'
		+'&nbsp;&nbsp;&nbsp;<input type="button" id="" value="应用" onclick="appl()"/>'
		+ '</span></div>';
addPanel("设置IP", appendcontent);
/* 单选框按钮方法 */
function appl() {
	var ipradiovalue
	ipradiovalue = $("input[name='iplimit']:checked").val(); 
	//把值所选的值发送给后台
	$.ajax({
		url : "../SysflagController/updateSysflag",
		type : "post",
		dataType : "json",
		data : {
			"sysFlagCode" : "ip",
			"sysValue" : ipradiovalue
		},
		success : function(json) {
			if (json.result == true) {
				$.messager.alert("提示", "设置成功！", "warning");		
			}
		}
	});
}

//发送ajax数据获取sysflag对象中对应的值
$.ajax({
	url : "../SysflagController/querySysflag",
	type : "post",
	dataType : "json",
	data : {
		"sysFlagCode" : "ip"
	},
	success : function(json) {
		if (json.total > 0) {
			var radioValue;
			radioValue = json.rows[0].sysValue;
			//初始化给radio赋值
			$("input[name='iplimit'][value="+radioValue+"]").attr("checked",true); 
		}
	}
});

/* 初始化列表 */
$("#dgIpInfo").datagrid({
	view:myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : true,
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../IpController/queryIp",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgIpInfo",
	columns : [ [ {
		field : 'ipId',
		checkbox : true
	}, {
		field : 'ipAddress',
		title : 'IP地址',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'remark',
		title : '备注',
		width : 280,
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
		width : 100,
		halign : 'center',
		align : 'center'
	} ] ],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip',{cls:{'max-width':'500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgIpInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询ip备注*/
function searchIpFunc() {
	$("#dgIpInfo").datagrid("load", {
		"remark" : $("#searchIpRemark").val()
	});
}

/* 删除ip*/
function delIpFunc() {
	var selectrow = $("#dgIpInfo").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		idArry.push(selectrow[i].ipId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示",'确定删除选定记录吗？', function(r){
		if (r){
			ajaxLoading();
			$.ajax({
				url : "../IpController/deleteIp",
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
						$("#dgIpInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 添加、修改ip信息 */
function editIpFunc(title, icon, action) {
	if (title == "修改IP信息") {
		var selectrow = $("#dgIpInfo").datagrid("getSelections");
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
						url : "../IpController/" + action,
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
								$("#dgIpInfo").datagrid('reload');
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
			name : "ipId",
			title : "IPID",
			ishiden : true,
			value : "-1"
		}));
		htmlArr.push(createValidatebox({
			name : "ipAddress",
			title : "IP地址",
			noBlank : true,
			type : 'ip'
		}));
		
		htmlArr.push(createValidatebox({
			name : "remark",
			title : "备注",
			type : 'maxLength[100]'
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if (title == "修改IP信息") {
		var selectrow = $("#dgIpInfo").datagrid("getSelected");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
	}
}