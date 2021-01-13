/************************************
 * 功能：用户信息
 * 日期：2016-3-22 13:28:09
 ************************************/
var appendcontent = '<div id="dgUserInfo"></div>'
		+ '<div id="tbdgUserInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editUserFunc(\'添加用户\',\'icon-add\',\'insertUsers\')">添加</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editUserFunc(\'修改用户\',\'icon-edit\',\'updatetUsers\')">修改</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delUserFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;状态：<input id="dgUserStatus" class="easyui-combobox" style="width:100px;" editable="false"/>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchName" data-options="onClickButton:function(){searchUserFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'用户名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("用户信息", appendcontent);
initStatuscombox();
/* 初始化列表 */
$("#dgUserInfo").datagrid(
	{
		view:myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : "../UserController/queryUsers",
		queryParams:{"userDelete":$("#dgUserStatus").combobox('getValue')},
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		toolbar : "#tbdgUserInfo",
		columns : [ [ {
			field : 'userId',
			checkbox : true
		}, {
			field : 'userCode',
			title : '登录账号',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'userName',
			title : '用户名称',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'orgId',
			title : '组织机构',
			width : 120,
			halign : 'center',
			align : 'center',
			formatter: function(value,row,index){
				return row.orgName
			}
		},{
			field : 'statisticsTime',
			title : '数据统计时间',
			width : 100,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'userTel',
			title : '联系电话',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'userEmail',
			title : '邮件地址',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'userRemark',
			title : '用户备注',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'userDelete',
			title : '状态',
			width : 80,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'userDeleteName',
			title : '状态',
			width : 80,
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
        		var str = '<a href="#this" title="停用" class="easyui-tooltip"' 
		        		+ 'onclick="modifyStauts('+row.userId+',\'1\');">' 
		        		+ '<img src="./../javascript/jquery-easyui-1.4.4/themes/icons/disabled.png" class="operate-button"></a>'
	    				+'<a href="#this" title="正常" class="easyui-tooltip" style="margin-left:10px;"'
						+ 'onclick="modifyStauts('+row.userId+',\'0\');">' 
						+ '<img src="./../javascript/jquery-easyui-1.4.4/themes/icons/start.png" class="operate-button"></a>'
						+ '<a href="#this" title="重置密码" class="easyui-tooltip" style="margin-left:10px;"' 
        				+ 'onclick="restPWD('+row.userId+');">' 
        				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/reload.png" class="operate-button"></a>';
        		return str;
        	}
        } ] ],
		singleSelect : false,
		selectOnCheck : true,
		checkOnSelect : true
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgUserInfo").datagrid("getPager").pagination({
	total : 0
});

//组织机构
var organdata = {};
var organvalue = -1;

/* 查询用户 */
function searchUserFunc() {
	$("#dgUserInfo").datagrid("load", {
		"userName" : $("#searchName").val(),
		"userDelete" : $("#dgUserStatus").combobox('getValue')
	});
}

/* 删除用户 */
function delUserFunc() {
	var selectrow = $("#dgUserInfo").datagrid("getChecked");
	var idArry = [];
	for(var i=0;i<selectrow.length;i++){
		idArry.push(selectrow[i].userId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示",'确定删除选定记录吗？', function(r){
		if (r){
			ajaxLoading();
			$.ajax({
				url : "../UserController/deleteUsers",
				type : "post",
				dataType : "json",
				data : {"list":idArry},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dgUserInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 添加、修改用户 */
function editUserFunc(title, icon, action) {
	initOrganInfo();
	if (title == "修改用户") {
		var selectrow = $("#dgUserInfo").datagrid("getSelections");
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
						url : "../UserController/"+action,
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
								$("#dgUserInfo").datagrid('reload');
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
			name : "userId",
			title : "用户ID",
			ishiden : true,
			value:"-1"
		}));
		htmlArr.push(createValidatebox({
			name : "userCode",
			title : "登录账户",
			noBlank : true,
			type:'loginId'
		}));
		htmlArr.push(createValidatebox({
			name : "userName",
			title : "用户名",
			noBlank : true,
			type:'name'
		}));
		htmlArr.push(createCombobox({
			name : "orgId",
			title : "组织ID",
			data : organdata,
			value : organvalue
		}));
		htmlArr.push(createValidatebox({
			name : "statisticsTime",
			title : "数据统计时间",
			value:"300",
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "userTel",
			title : "联系电话"
		}));
		htmlArr.push(createValidatebox({
			name : "userEmail",
			title : "邮件地址",
			type:'email'
		}));
		htmlArr.push(createValidatebox({
			name : "userRemark",
			title : "用户备注"
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if (title == "修改用户") {
		var selectrow = $("#dgUserInfo").datagrid("getSelected");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
	}
}

/**
 * 改变用户账户状态
 * @param userId
 * @param status
 */
function modifyStauts(userId,status){
	var message = "确定是否停用当前账户？";
	if(status==0){
		message = "确定是否启用当前账户？";
	}
	$.messager.confirm("提示",message, function(r){
		if (r){
			$.ajax({// 发送ajax请求
				url : "../UserController/updateUserDelete",
				type : "post",
				dataType : "json",
				data : {"userId":userId,"userDelete":status},
				error : function(json) {
					$.messager.alert("提示", json.detail, "info");
				},
				success : function(json) {
					if (json.result) {
						$.messager.alert("提示", json.detail, "info");
						$("#dgUserInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/*重置密码*/
function restPWD(userid){
	$.messager.confirm("提示",'确定重置密码吗？', function(r){
		if (r){
			ajaxLoading();
			$.ajax({// 发送ajax请求
				url : "../UserController/resetUsersPwd",
				type : "post",
				dataType : "json",
				data : {"userId":userid},
				error : function(json) {
					ajaxLoadEnd();
					$.messager.alert("提示", json.detail, "info");
				},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "密码重置成功！", "info");
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/**
 * 初始化用户状态
 */
function initStatuscombox(){
	var data =[{"id":"0","text":"正常"},{"id":"1","text":"停用"}]
	$('#dgUserStatus').combobox({
		data : data,
		valueField : 'id',
		textField : 'text',
		onShowPanel : function(){
            $(this).combobox('panel').height("auto");
		}
	});
	// 设置默认显示值
	$('#dgUserStatus').combobox('setValue', data[0].id);
}

/*初始化组织机构*/
function initOrganInfo(){
	$.ajax({
		url : "../OranizationController/queryOranization",
		type : "post",
		dataType : "json",
		async:false,
		success : function(json) {
			   for(var i=0;i<json.total;i++){
				   organdata[json.rows[i].orgId] = json.rows[i].orgName;
			   }
			   organvalue = json.rows[0].orgId;
		}
	});
}