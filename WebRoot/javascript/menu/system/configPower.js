/************************************
 * 功能：权限信息
 * 日期：2016-4-6 13:41:09
 ************************************/
var appendcontent = '<div id="dgAuthorityInfo"></div>'
		+ '<div id="tbdgAuthorityInfo" style="padding:5px 10px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editAuthorityFunc(\'添加权限信息\',\'icon-add\',\'insertAuthority\')">添加</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editAuthorityFunc(\'修改权限信息\',\'icon-edit\',\'updateAuthority\')">修改</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delAuthorityFunc()">删除</a>'
		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchAuthorityName" data-options="onClickButton:function(){searchAuthorityFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'权限组名称...\'" style="width:200px;height:24px;">'
		+ '</div>'
		+'<div id="mymenu" class="easyui-menu" style="width:120px;height:30px;">'
		+'<div  onclick="removeFunc()" data-options="iconCls:\'icon-remove\'">Remove</div>'
		+'</div>';
addPanel("设置权限", appendcontent);
var ahrHave = [ {
	"id" : "1",
	"name" : "未添加"
}, {
	"id" : "2",
	"name" : "已添加"
} ];
var ahrHaveValue = "1";
var ahrMfrData = [];
var areaValue = null;
var areaIdData = [];
/*移除权限组中的权限*/
function removeFunc(){
	$.messager.confirm("提示",'从此权限组中移除用户吗？', function(r){
		if (r){
			var selectedstation = $('#mypowerusertree').tree('getSelected');
			var arryList = [];
			if(selectedstation==null){
				arryList.push("-1");
			}else{
				arryList.push(selectedstation.id+"");
			}
			var title = $('#dialogModel').panel('options').title;
			var ahrcode = title.substring(title.indexOf('(')+1, title.indexOf(')'));
			ajaxLoading();
			$.ajax({
				url : "../UserAhrController/deleteUserAhrs",
				type : "post",
				dataType : "json",
				data : {
					"ahrCode":ahrcode,
					"list" : arryList
				},
				error : function(json) {
					ajaxLoadEnd();
				},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						initTreeNoe("mypowerusertree",'../TreeController/getAllAhrUserTree',{"ahrCode":ahrcode},false);
						$("#dgUserList").datagrid('reload',{
							"ahrCode":ahrcode,
							"userName": $("#searchUserPowerName1").val()
						});
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}
/* 初始化列表 */
$("#dgAuthorityInfo").datagrid({
	view:myview,
	fit : true,
	border : false,
	pagination : true,
	fitColumns : true,
	pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
	url : "../AuthorityController/queryAuthority",
	pageSize : 10,
	autoRowHeight : false,
	rownumbers : true,
	toolbar : "#tbdgAuthorityInfo",
	columns : [ [ {
		field : 'authorityId',
		checkbox : true
	}, {
		field : 'authorityCode',
		title : '权限组编号',
		width : 100,
		halign : 'center',
		align : 'center'
	},{
		field : 'authorityName',
		title : '权限组名称',
		width : 120,
		halign : 'center',
		align : 'center'
	},{
		field : 'remark',
		title : '备注',
		width : 120,
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
	},{field:"operate",title:"配置监控站点",halign:"center",align : 'center',
    	formatter: function(value,row,index){
    		var str = '<a href="#this" title="配置可控监的站点" class="easyui-tooltip" ' 
    				+ 'onclick="configMonitorStation('+index+');">' 
    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/stationsetting.png" class="operate-button"></a>';
    		return str;
    	}
    },{field:"operate1",title:"配置监控物质",halign:"center",align : 'center',
    	formatter: function(value,row,index){
    		var str = '<a href="#this" title="配置监控物质" class="easyui-tooltip" ' 
    				+ 'onclick="configMonitorThings('+index+');">' 
    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/things.png" class="operate-button"></a>';
    		return str;
    	}
    },{field:"operate2",title:"配置模块权限",halign:"center",align : 'center',
    	formatter: function(value,row,index){
    		var str = '<a href="#this" title="配置模块功能权限" class="easyui-tooltip" ' 
    				+ 'onclick="configFunctionPower('+index+');">' 
    				+ '<img src="../images/configpowergroup.png" class="operate-button"></a>';
    		return str;
    	}
    },{field:"operate3",title:"添加权限用户",halign:"center",align : 'center',
    	formatter: function(value,row,index){
    		var str = '<a href="#this" title="添加用户到权限组" class="easyui-tooltip" ' 
    				+ 'onclick="addUserToPowerGroup('+index+');">' 
    				+ '<img src="../images/addUser.png" class="operate-button"></a>';
    		return str;
    	}
    }  ] ],
	singleSelect : false,
	selectOnCheck : true,
	checkOnSelect : true
}).datagrid('doCellTip',{cls:{'max-width':'500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgAuthorityInfo").datagrid("getPager").pagination({
	total : 0
});

/* 查询权限名称备注*/
function searchAuthorityFunc() {
	$("#dgAuthorityInfo").datagrid("load", {
		"authorityName" : $("#searchAuthorityName").val()
	});
}

/* 删除权限*/
function delAuthorityFunc() {
	var selectrow = $("#dgAuthorityInfo").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		idArry.push(selectrow[i].authorityId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示",'确定删除选定记录吗？', function(r){
		if (r){
			ajaxLoading();
			$.ajax({
				url : "../AuthorityController/deleteAuthority",
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
						$("#dgAuthorityInfo").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}

/* 添加、修改权限信息 */
function editAuthorityFunc(title, icon, action) {
	if (title == "修改权限信息") {
		var selectrow = $("#dgAuthorityInfo").datagrid("getSelections");
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
						url : "../AuthorityController/" + action,
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
								$("#dgAuthorityInfo").datagrid('reload');
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
			name : "authorityId",
			title : "权限ID",
			ishiden : true,
			value : "-1"
		}));
		htmlArr.push(createValidatebox({
			name : "authorityCode",
			title : "权限组编号",
			noBlank : true,
			type : 'maxLength[50]'
		}));
		htmlArr.push(createValidatebox({
			name : "authorityName",
			title : "权限组名称",
			noBlank : true,
			type : 'maxLength[75]'
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
	if (title == "修改权限信息") {
		var selectrow = $("#dgAuthorityInfo").datagrid("getSelected");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", selectrow);
	}
}

/*配置权限组权限*/
function configMonitorStation(index){
	//ajaxLoading();
	initAhrDeiveParam();
	var record = $("#dgAuthorityInfo").datagrid("getRows")[index];
	$("#dialogModel").dialog({
		width : 1000,
		height : 440,
		title : "配置"+record.authorityName+"可控监的站点",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : true,
		closed : true,
		content : '<div class="easyui-layout" data-options="fit:true">'
    	    +'<div data-options="region:\'west\',title:\'已监控的站点\'" style="width:230px;">'
    	    +'<div class="easyui-layout" data-options="fit:true" >'
			+'<div data-options="region:\'center\',border:false">'
			+'<ul class="easyui-tree" id="mystationpowertree"></ul>'
			+'</div>'
			+'</div>'	
    	    +'</div>'
    	    +'<div data-options="region:\'east\'" style="width:750px;">'
    	    +'<div id="dgAhrDeviceList"></div>'
    		+'<div id="tbdgAhrDeviceList" style="padding:5px 10px;">'
    		+ '&nbsp;&nbsp;&nbsp;权限状态：<input class="easyui-combobox" name="ahrHaveCombox" id="ahrHaveCombox" style="width:80px;"/>'
    		+ '&nbsp;&nbsp;&nbsp;生产厂商：<input class="easyui-combobox" name="ahrMfrCombox" id="ahrMfrCombox" style="width:120px;"/>'
    		+ '&nbsp;&nbsp;&nbsp;所属区域：<input class="easyui-combobox" name="ahrAreaCombox" id="ahrAreaCombox" style="width:150px;"/>'
    		+ '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchAhrDeviceName" data-options="onClickButton:function(){searchAhrDeviceList(\''+record.authorityCode+'\')},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:150px;height:24px;">'
    		+ '</div></div>'
    		+ '</div>',
		buttons : [ {
			text : "添加",
			iconCls : "icon-ok",
			handler : function() {
				var checkedAhrDeviceList = $("#dgAhrDeviceList").datagrid('getChecked');
				if(checkedAhrDeviceList.length<=0){
					$.messager.alert("警告", "请先勾选设备！", "warning");
					return false;
				}
				var arryList = [];
				for(var i=0;i<checkedAhrDeviceList.length;i++){
					if(checkedAhrDeviceList[i].haveAhr == "1"){
						arryList.push(checkedAhrDeviceList[i].deviceCode);
					}
				}
				if(arryList.length<=0){
					$.messager.alert("警告", "请选择'未添加'的设备！", "warning");
					return false;
				}
				ajaxLoading();
				$.ajax({
					url : "../AccessOperatorController/insertAccessDevice",
					type : "post",
					dataType : "json",
					async:true,
					data : {
						"ahrCode": record.authorityCode,
						"list": arryList,
					},error : function(json) {
						ajaxLoadEnd();
					},
					success : function(json) {
						ajaxLoadEnd();
						if(json.result){
							searchAhrDeviceList(record.authorityCode);
							initTreeNoe("mystationpowertree",'../TreeController/getAhrAreaTree',{"parentid":"","ahrcode":record.authorityCode},false);
							$.messager.alert("提示", "配置可控监的站点成功！", "info");
						}else{
							$.messager.alert("错误", json.detail, "error");
						}
					}
				});
			}
		},{
			text : "删除",
			iconCls : "icon-cut",
			handler : function() {
				var checkedAhrDeviceList = $("#dgAhrDeviceList").datagrid('getChecked');
				if(checkedAhrDeviceList.length<=0){
					$.messager.alert("警告", "请先勾选设备！", "warning");
					return false;
				}
				var arryList = [];
				for(var i=0;i<checkedAhrDeviceList.length;i++){
					if(checkedAhrDeviceList[i].haveAhr == "2"){
						arryList.push(checkedAhrDeviceList[i].deviceCode);
					}
				}
				if(arryList.length<=0){
					$.messager.alert("警告", "请选择'已添加'的设备！", "warning");
					return false;
				}
				ajaxLoading();
				$.ajax({
					url : "../AccessOperatorController/deleteAccessDevice",
					type : "post",
					dataType : "json",
					async:true,
					data : {
						"ahrCode": record.authorityCode,
						"list": arryList,
					},error : function(json) {
						ajaxLoadEnd();
					},
					success : function(json) {
						ajaxLoadEnd();
						if(json.result){
							searchAhrDeviceList(record.authorityCode);
							initTreeNoe("mystationpowertree",'../TreeController/getAhrAreaTree',{"parentid":"","ahrcode":record.authorityCode},false);
							$.messager.alert("提示", "配置可控监的站点成功！", "info");
						}else{
							$.messager.alert("错误", json.detail, "error");
						}
					}
				});
			}
		}, {
			text : "清空",
			iconCls : "icon-reload",
			handler : function() {
				$.messager.confirm("提示",'确定清空权限组所有设备吗？', function(r){
					if(r){
						var arryList = [-1];
						ajaxLoading();
						$.ajax({
							url : "../AccessOperatorController/deleteAccessDevice",
							type : "post",
							dataType : "json",
							async:true,
							data : {
								"ahrCode": record.authorityCode,
								"list": arryList,
							},error : function(json) {
								ajaxLoadEnd();
							},
							success : function(json) {
								ajaxLoadEnd();
								if(json.result){
									searchAhrDeviceList(record.authorityCode);
									$.messager.alert("提示", "配置可控监的站点成功！", "info");
									initTreeNoe("mystationpowertree",'../TreeController/getAhrAreaTree',{"parentid":"","ahrcode":record.authorityCode},false);
								}else{
									$.messager.alert("错误", json.detail, "error");
								}
							}
						});
					}
				})
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	$("#dialogModel").dialog("open");
	initAhrCombox();
	searchAhrDeviceList(record.authorityCode);
	$.parser.parse("#dialogModel");
	initTreeNoe("mystationpowertree",'../TreeController/getAhrAreaTree',{"parentid":"","ahrcode":record.authorityCode},false);
	$('#searchAhrDeviceName').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	searchAhrDeviceList(record.authorityCode);
        }
    });
}

/*配置监控物质*/
function configMonitorThings(index){
	var record = $("#dgAuthorityInfo").datagrid("getRows")[index];
	$("#dialogModel").dialog({
		width : 390,
		height : 440,
		title : "配置监控物质",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : true,
		closed : true,
		content : '<Form id="" class="config-form"><label>可监控的物质</label><ul class="easyui-tree" id="mythingspowertree"></ul></Form>',
		buttons : [ {
			text : "保存",
			iconCls : "icon-ok",
			handler : function() {
				var mythingspowertree = [];
				var selectionthings = $("#mythingspowertree").tree('getChecked');
				for(var i=0;i<selectionthings.length;i++){
					mythingspowertree.push(selectionthings[i].id);
				}
				if(mythingspowertree.length==0){
					mythingspowertree = [-1];
				}
				ajaxLoading();
				$.ajax({//更新权限组监测物数据
					url : "../AccessOperatorController/insertAccessMonitor",
					type : "post",
					dataType : "json",
					async:false,
					data : {
						"ahrCode": record.authorityCode,
						"list": mythingspowertree,
					},
					error : function(json) {
						ajaxLoadEnd();
					},
					success : function(json) {
						ajaxLoadEnd();
						if(json.result){
							$("#dialogModel").dialog("close");
							$.messager.alert("提示","配置监控物质成功！","info");
						}else{
							$.messager.alert("错误", json.detail, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	initTreeNoe("mythingspowertree",'../TreeController/getMonitorTreeAllChecked',{"ahrCode":record.authorityCode},true);
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
}

/*配置模块功能权限*/
function configFunctionPower(index){
	var record = $("#dgAuthorityInfo").datagrid("getRows")[index];
	$("#dialogModel").dialog({
		width : 390,
		height : 440,
		title : "配置模块功能权限",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : true,
		closed : true,
		content : '<Form id="" class="config-form"><label>模块功能</label><ul class="easyui-tree" id="myModulepowertree"></ul></Form>',
		buttons : [ {
			text : "保存",
			iconCls : "icon-ok",
			handler : function() {
				var myModulepowertree = [];
				var liststr = "";
				var selectionindeterminatethings = $("#myModulepowertree").tree('getChecked','indeterminate');
				for(var i=0;i<selectionindeterminatethings.length;i++){
					myModulepowertree.push({"id":selectionindeterminatethings[i].id,"levelFlag":selectionindeterminatethings[i].levelFlag,"checkedStatus":'indeterminate'});
				}
				var selectionthings = $('#myModulepowertree').tree('getChecked');
				for(var i=0;i<selectionthings.length;i++){
					myModulepowertree.push({"id":selectionthings[i].id,"levelFlag":selectionthings[i].levelFlag,"checkedStatus":"checked"});
				}
				if(myModulepowertree.length==0){
					/*myModulepowertree.push({"id":-1,"levelFlag":1,"checkedstr":"true"});*/
					liststr = "";
				}else{
					liststr = JSON.stringify(myModulepowertree);
				}
				ajaxLoading();
				$.ajax({//更新功能控件权限
					url : "../AuthorityDetailController/updateAuthorityDetail",
					type : "post",
					dataType : "json",
					async:false,
					data : {
						"ahrCode": record.authorityCode,
						"list":liststr,
					},
					error : function(json) {
						ajaxLoadEnd();
					},
					success : function(json) {
						ajaxLoadEnd();
						if(json.result){
							$("#dialogModel").dialog("close");
							$.messager.alert("提示","配置模块权限成功！","info");
							initTreeNoe("myModulepowertree",'../TreeController/getAhAhrControlAllChecked',{"ahrCode":record.authorityCode+"","levelFlag":"2"},true);
						}else{
							$.messager.alert("错误", json.detail, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	initTreeNoe("myModulepowertree",'../TreeController/getAhAhrControlAllChecked',{"ahrCode":record.authorityCode+"","levelFlag":"2"},true);
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
}

/*添加用户到权限组*/
function addUserToPowerGroup(index){
	var record = $("#dgAuthorityInfo").datagrid("getRows")[index];
	$("#dialogModel").dialog({
		width : 850,
		height : 424,
		title : "添加用户到权限组("+record.authorityCode+")",
		inline : true,
		modal : true,
		iconCls : "icon-add",
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		resizable : true,
		closed : true,
		content :'<div class="easyui-layout" data-options="fit:true">'
    	    +'<div data-options="region:\'west\',title:\'权限组中的用户 (右击删除)\'" style="width:180px;">'
    	    +'<div class="easyui-layout" data-options="fit:true" >'
			+'<div data-options="region:\'center\',border:false">'
			+'<ul id="mypowerusertree" oncontextmenu="return false"></ul>'
			+'</div>'
			+'</div>'	
    	    +'</div>'
    	    +'<div data-options="region:\'east\'" style="width:650px;">'
    	    +'<div id="dgUserList"></div>'
    		+'<div id="tbdgUserList" style="padding:5px 10px;">'
    		+'<input class="easyui-textbox" id="searchUserPowerName1" data-options="onClickButton:function(){searchUserList(\''+record.authorityCode+'\')},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'用户名称...\'" style="width:200px;height:24px;">'
    		+'<label style="color:red;font-size:9px;display: inline;margin-top:8px;margin-left:15px;">提示：勾选用户，点击添加可将用户添加到权限组</label>'
    		+'</div></div>'
    		+'</div>',
		buttons : [ {
			text : "添加",
			iconCls : "icon-ok",
			handler : function() {
				var checkedUserList = $("#dgUserList").datagrid('getChecked');
				if(checkedUserList.length<=0){
					$.messager.alert("警告", "请先勾选用户！", "warning");
					return false;
				}
				var arryList = [];
				for(var i=0;i<checkedUserList.length;i++){
					arryList.push(checkedUserList[i].userCode);
				}
				ajaxLoading();
				$.ajax({
					url : "../UserAhrController/insertUserAhrs",
					type : "post",
					dataType : "json",
					data : {
						"ahrCode":record.authorityCode,
						"list" : arryList
					},
					error : function(json) {
						ajaxLoadEnd();
					},
					success : function(json) {
						ajaxLoadEnd();
						if (json.result) {
							$.messager.alert("提示", "添加成功！", "info");
							initTreeNoe("mypowerusertree",'../TreeController/getAllAhrUserTree',{"ahrCode":record.authorityCode},false);
							$("#dgUserList").datagrid('reload',{
								"ahrCode":record.authorityCode,
								"userName": $("#searchUserPowerName1").val()
							});
						} else {
							$.messager.alert("错误", json.detail, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	initTreeNoe("mypowerusertree",'../TreeController/getAllAhrUserTree',{"ahrCode":record.authorityCode},false);
	$('#mypowerusertree').tree({//右键删除
		onContextMenu: function(e, node){
			e.preventDefault();
			var selectedstation = $('#mypowerusertree').tree('select', node.target);
			if($('#mypowerusertree').tree('isLeaf',node.target)){
				$('#mymenu').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}
		}
	});
	searchUserList(record.authorityCode);
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	$('#searchUserPowerName1').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	searchUserList(record.authorityCode);
        }
    });
}

/*查询未分配权限的用户*/
function searchUserList(code){
	$("#dgUserList").datagrid({
		fit : true,
		border : false,
		pagination : true,
		fitColumns : true,
		pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
		url : "../UserAhrController/queryOtherUserAhrs",
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		toolbar : '#tbdgUserList',
		queryParams: {
			"userName": $("#searchUserPowerName1").val(),
			"ahrCode":code
		},
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
		} ] ],
		singleSelect : false,
		selectOnCheck : true,
		checkOnSelect : true
	});	
}

/*查询设备*/
function searchAhrDeviceList(code){
//	alert($("#ahrAreaCombox").combobox('getValue'));
	$("#dgAhrDeviceList").datagrid({
		fit : true,
		border : false,
		pagination : true,
		fitColumns : true,
		pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
		url : "../AccessOperatorController/queryAhrDevice",
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		toolbar : '#tbdgAhrDeviceList',
		queryParams: {
			"deviceName": $("#searchAhrDeviceName").val(),
			"mfrCode" : $("#ahrMfrCombox").combobox('getValue'),
			"haveAhr" : $("#ahrHaveCombox").combobox('getValue'),
			"areaId" : $("#ahrAreaCombox").combobox('getValue'),
			"ahrCode": code
		},
		columns : [ [ {
			field : 'deviceId',
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
			width : 130,
			halign : 'center',
			align : 'center'
		}, {
			field : 'deviceName',
			title : '设备名称',
			width : 130,
			halign : 'center',
			align : 'center'
		}, {
			field : 'mfrCode',
			title : '所属厂商',
			width : 180,
			halign : 'center',
			align : 'center',
			formatter : function(value, row, index) {
				return row.mfrName
			}
		}, {
			field : 'areaId',
			title : '所属区域',
			width : 100,
			halign : 'center',
			align : 'center',
			formatter : function(value, row, index) {
				return row.areaName
			}
		}, {
			field : 'statusCode',
			title : '设备状态',
			width : 100,
			halign : 'center',
			align : 'center',
			formatter : function(value, row, index) {
				return row.statusName
			}
		}, {
			field : 'haveAhr',
			title : '权限状态',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'haveAhrInfo',
			title : '权限状态',
			width : 100,
			halign : 'center',
			align : 'center'
		} ] ],
		singleSelect : false,
		selectOnCheck : true,
		checkOnSelect : true
	});
	$("#dgAhrDeviceList").datagrid('hideColumn', 'haveAhr');
}

/*初始化下拉框*/
function initAhrCombox(){
	//权限状态
	$("#ahrHaveCombox").combobox({
		data : ahrHave,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value : ahrHaveValue
	});
	//厂商
	$("#ahrMfrCombox").combobox({
		data : ahrMfrData,
		method : 'post',
		valueField : 'code',
		textField : 'name',
		panelHeight : 'auto'
	});
	//所属区域
	$("#ahrAreaCombox").combobox({
		data : areaIdData,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		onShowPanel : function () {
            // 动态调整高度
            if (areaIdData.length < 20) {
                $(this).combobox('panel').height("auto");
            }else{
                $(this).combobox('panel').height(300);
            }
        }
	})
}
/* 初始化厂商下拉框数据 初始化区域下拉框数据 */
function initAhrDeiveParam() {
	$.ajax({
		url : "../DeviceController/queryDevicemfrCodeDropDown",
		type : "post",
		dataType : "json",
		async : false,
		success : function(json) {
			if (json.total > 0) {
				ahrMfrData = json.rows;
			}
		}
	});
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
			if (json.total > 0) {
				for (var i = 0; i < json.total; i++) {
					areaIdData = json.rows;
				}
			}
		}
	});
}