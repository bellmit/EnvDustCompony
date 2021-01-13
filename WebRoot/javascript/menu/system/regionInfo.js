/************************************
 * 功能：区域信息 
 * 日期：2016-3-22 13:28:09
 ************************************/
// 获取父类区域
var parentdata = {};
var parentvalue = -1;
// 获取级别
var leveldata = {};
var levelvalue = -1;

var appendcontent = '<div id="dgRegionInfo"></div>'
		+ '<div id="tbdgRegionInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editRegionFunc(\'添加区域\',\'icon-add\',\'insertAreas\')">添加</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editRegionFunc(\'修改区域\',\'icon-edit\',\'updateAreas\')">修改</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delRegionFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchregionName" data-options="onClickButton:function(){searchRegionFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'区域名称...\'" style="width:200px;height:24px;">'
		+ '</div>';
addPanel("区域设置", appendcontent);

/* 初始化列表 */
$("#dgRegionInfo").datagrid({
	view:myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : true,
	singleSelect : true,
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../AreaController/queryAreas",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgRegionInfo",
	columns : [ [ {
		field : 'areaId',
		title : '区域ID',
		width : 180,
		checkbox : true
	}, {
		field : 'areaName',
		title : '区域名称',
		width : 180,
		halign : 'center',
		align : 'center'
	}, {
		field : 'parentId',
		title : '父类ID',
		width : 160,
		hidden : true
	}, {
		field : 'levelId',
		title : '级别id',
		width : 80,
		hidden : true
	}, {
		field : 'areaPath',
		title : '区域路径名称',
		width : 180,
		halign : 'center',
		align : 'center'
	}, {
		field : 'cityId',
		title : '天气城市编码',
		width : 80,
		halign : 'center',
		align : 'center'
	},{
		field : 'optUserName',
		title : '操作者',
		width : 120,
		halign : 'center',
		align : 'center'
	}, {
		field : 'optTime',
		title : '操作时间',
		width : 120,
		halign : 'center',
		align : 'center'
	} ] ],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip',{cls:{'max-width':'500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgRegionInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询区域 */
function searchRegionFunc() {
	$("#dgRegionInfo").datagrid("load", {
		"areaName" : $("#searchregionName").val()
	});
}

/* 删除区域 */
function delRegionFunc() {
	var selectrow = $("#dgRegionInfo").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		idArry.push(selectrow[i].areaId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示",'确定删除选定记录吗？', function(r){
		if (r){
			ajaxLoading();
			$.ajax({
				url : "../AreaController/deleteAreas",
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
						$("#dgRegionInfo").datagrid('reload');
						initTreeNoe("mytree",
								'../TreeController/getAreaTree',null,false);
					} else {
						$.messager.alert("错误", json.detail, "error",false);
					}
				}
			});
		}
	});
}

/* 添加、修改区域 */
function editRegionFunc(title, icon, action) {
	if (title == "修改区域") {
		var selectrow = $("#dgRegionInfo").datagrid("getSelections");
		if (selectrow.length != 1) {
			$.messager.alert("提示", "请选择一条记录进行修改！", "info");
			return false;
		}
	}
	initRegionParam();
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
		content : '<form id="frmdialogModel" class="config-form" style="width:400px;"></form>'
			+'<a href="../files/china-city-list.txt" target="_blank" style="margin-left:35px;width:360px;">备注：天气城市编码可用来查询当地天气情况</a>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				if ($("#frmdialogModel").form("validate")) {
					var formdataArray = $("#frmdialogModel").serializeArray();
					formdataArray.push({"name":"evelFlag","value": $("#levelId ").combobox('getValue')})
					var formdataJosn = getFormJson(formdataArray);//转换成json数组
					ajaxLoading();
					$.ajax({// 发送ajax请求
						url : "../AreaController/"+ action,
						type : "post",
						dataType : "json",
						data : formdataJosn,
						error : function(json) {
							ajaxLoadEnd();
							$.messager.alert("提示", json.detail, "warning");
						},
						success : function(json) {
							ajaxLoadEnd();
							if (json.result) {
								$("#dialogModel").dialog("close");
								$.messager.alert("提示", "操作成功！", "info");
								$("#dgRegionInfo").datagrid('reload');
								initTreeNoe("mytree",
										'../TreeController/getAreaTree',null,false);
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
	// 初始化表单
	$("#frmdialogModel").html(function() {
		var htmlArr = [];
		htmlArr.push(createValidatebox({
			name : "areaId",
			title : "区域ID",
			ishiden : true,
			value : "-1"
		}));
		htmlArr.push(createValidatebox({
			name : "areaName",
			title : "区域名称",
			noBlank : true
		}));
		htmlArr.push(createCombobox({
			name : "levelId",
			title : "级别",
			data : leveldata,
			value : levelvalue
		}));
		htmlArr.push(createCombobox({
			name : "parentId",
			title : "上级区域"
		}));
		htmlArr.push(createValidatebox({
			name : "cityId",
			title : "天气城市编码"
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if (title == "修改区域") {
		$('#levelId').combobox({"readonly":true});//级别ID只读
		var selectrow = $("#dgRegionInfo").datagrid("getSelected");
		initParentRegion(selectrow.levelId);
		$('#levelId').combobox({//绑定事件
			onSelect : function() {
				var level = $("#levelId").combobox('getValue');
				initParentRegion(level);
			}
		});
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
	}else{
		var leveldatacombox = $("#levelId").combobox('getValues');
		initParentRegion(leveldatacombox[0]);
		$('#levelId').combobox({
			onSelect : function() {
				var level = $("#levelId").combobox('getValue');
				initParentRegion(level);
			}
		});
	}
}

/* 初始化级别 */
function initRegionParam() {
	$.ajax({
		url : "../AreaController/queryAreaLevelDropDown",
		type : "post",
		dataType : "json",
		data : {
			"id" : "-1"
		},
		async : false,
		success : function(json) {
			if (json.total <= 0) {
				leveldata["-1"] = "------无------";
				levelvalue = "-1";
			} else {
				for (var i = 0; i < json.total; i++) {
					leveldata[json.rows[i].id] = json.rows[i].name;
				}
				levelvalue = json.rows[0].id;
			}
		}
	});
}

/* 获取上级区域 */
function initParentRegion(id) {
	$.ajax({
		url : "../AreaController/queryAreaDropDown",
		type : "post",
		dataType : "json",
		data : {
			"id" : "-1",
			"levelFlag" : id
		},
		async : false,
		success : function(json) {
			var parentRegion = [];
			if (json.total<= 0) {
				parentRegion.push({"id" : -1,"name" : "------无------"});
			} else {
				for (var i = 0; i < json.total; i++) {
					parentRegion.push({"id" : json.rows[i].id,"name" : json.rows[i].name});
				}
			}
			$('#parentId').combobox({
				data : parentRegion,
				valueField : 'id',
				textField : 'name',
				onShowPanel: function () {
				    // 动态调整高度 
				    if (parentRegion.length < 10) {
				        $(this).combobox('panel').height("auto");  
				    }else{
				        $(this).combobox('panel').height(300);
				    }
				}
			});
			$('#parentId').combobox('setValue',parentRegion[0].id);
		}
	});
}