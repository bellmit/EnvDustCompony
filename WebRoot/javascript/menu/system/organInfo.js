/************************************
 * 功能：组织机构信息
 * 日期：2016-3-22 13:28:09
 ************************************/
var appendcontent = '<div id="dgorganInfo"></div>'
	+'<div id="tborganInfo" style="padding:5px 10px;">'
	+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editOrganFunc(\'添加组织机构\',\'icon-add\',\'insertOranization\')">添加</a>'
	+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editOrganFunc(\'修改组织机构\',\'icon-edit\',\'updatetOranization\')">修改</a>'
	+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delOrganFunc()">删除</a>'
	+'&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchorganName" data-options="onClickButton:function(){searchOrganFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'组织名称...\'" style="width:200px;height:24px;">'
	+'</div>';
addPanel("组织机构信息",appendcontent);

$("#dgorganInfo").datagrid({
	view:myview,
	fit: true,
	border: false,   
    pagination: true,
    fitColumns: false,//是否适拖动栏
    pageList:[50,100,150,200,250,300],
	pageSize:50,
	autoRowHeight:false,
	rownumbers:true,
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true,
    toolbar: "#tborganInfo",
    url:"../OranizationController/queryOranization",
    columns:[[
        {field:'orgId',title:'机构ID',width:60,halign:'center',align:'center',checkbox : true},
        {field:'orgName',title:'组织机构名称',width:180,halign:'center',align:'center'},
        {field:'orgIdParent',title:'上级机构',width:120,halign:'center',align:'center',hidden:true},
        {field:'orgAddress',title:'组织地址',width:180,halign:'center',align:'center'},
        {field:'levelType',title:'组织类型',width:100,halign:'center',align:'center'},
        {field:'orgPath',title:'组织机构路径',width:280,halign:'center',align:'center'},
        {field:'orgTelephone',title:'联系电话',width:100,halign:'center',align:'center'},
        {field:'orgFax',title:'传真',width:100,halign:'center',align:'center'},
        {field:'orgLiaison',title:'联系人',width:80,halign:'center',align:'center'},
        {field:'optUser',title:'操作人',width:80,halign:'center',align:'center',
        	formatter: function(value,row,index){
    			return row.optUserName
    		}},
        {field:'optTime',title:'操作时间',width:130,halign:'center',align:'center'}
    ]]
}).datagrid('doCellTip',{cls:{'max-width':'500px'}});

//定义分页器的初始显示默认值
$("#dgorganInfo").datagrid("getPager").pagination({total:0});

/* 查询用户 */
function searchOrganFunc() {
	$("#dgorganInfo").datagrid("load", {
		"orgName" : $("#searchorganName").val()
	});
}

/* 删除组织机构 */
function delOrganFunc() {
	var selectrow = $("#dgorganInfo").datagrid("getChecked");
	var idArry = [];
	for(var i=0;i<selectrow.length;i++){
		idArry.push(selectrow[i].orgId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示", '确定删除选定记录吗？', function(r) {
		if (r) {
			ajaxLoading();
			$.ajax({
				url : "../OranizationController/deleteOranization",
				type : "post",
				dataType : "json",
				data : {
					"list" : idArry
				},
				error : function(json) {
					ajaxLoadEnd();
					$.messager.alert("提示", json.detail, "info");
				},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#dgorganInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

//组织机构
var organdatas = {};
var orgvalues = 0;
//组织负责人
var orgPringdata = {};
var orgPringvalue = -1;

/* 添加、修改组织机构 */
function editOrganFunc(title, icon, action) {
	initOrganParam();
	if (title == "修改组织机构") {
		var selectrow = $("#dgorganInfo").datagrid("getSelections");
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
					var formdataArray = $("#frmdialogModel").serializeArray();
					var formdataJosn = getFormJson(formdataArray);//转换成json数组
					ajaxLoading();
					$.ajax({// 发送ajax请求
						url : "../OranizationController/"+action,
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
								$("#dgorganInfo").datagrid('reload');
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
			name : "orgId",
			title : "组织机构ID",
			ishiden : true,
			value:"-1"
		}));
		htmlArr.push(createValidatebox({
			name : "orgName",
			title : "组织机构名称",
			noBlank : true,
			type : 'maxLength[100]'
		}));
		htmlArr.push(createCombobox({
			name : "orgIdParent",
			title : "上级组织机构",
			data : organdatas,
			value : orgvalues
		}));
		htmlArr.push(createValidatebox({
			name : "levelType",
			title : "组织类型",
			noBlank : true,
			type : 'maxLength[25]'
		}));
		htmlArr.push(createValidatebox({
			name : "orgAddress",
			title : "组织地址",
			type : 'maxLength[100]'
		}));
		htmlArr.push(createValidatebox({
			name : "orgTelephone",
			title : "联系电话",
			type : 'maxLength[50]'
		}));
		htmlArr.push(createValidatebox({
			name : "orgFax",
			title : "传真",
			type : 'maxLength[50]'
		}));
		htmlArr.push(createValidatebox({
			name : "orgLiaison",
			title : "联系人",
			type : 'maxLength[50]'
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if (title == "修改组织机构") {
		var selectrow = $("#dgorganInfo").datagrid("getSelected");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
	}
}

/* 初始一些参数 */
function initOrganParam(){
	$.ajax({
		url : "../OranizationController/queryOranization",
		type : "post",
		dataType : "json",
		async : false,
		success : function(json) {
			organdatas["0"] = "------无------";
			organvalue = "0";
			for (var i = 0; i < json.total; i++) {
				organdatas[json.rows[i].orgId] = json.rows[i].orgName;
			}
		}
	});
	$.ajax({
		url : "../UserController/queryUsers",
		type : "post",
		dataType : "json",
		async : false,
		success : function(json) {
			for (var i = 0; i < json.total; i++) {
				orgPringdata[json.rows[i].userId] = json.rows[i].userName;
			}
			orgPringvalue = json.rows[0].userId;
		}
	});
}