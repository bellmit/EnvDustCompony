/************************************
 * 功能：采样设备监控
 * 日期：2018-7-25 09:51:00
 ************************************/
var appendcontent = '<div id="tbNetSampleQuery" style="padding:15px 8px;font-size:13px;border-bottom:1px solid #ddd;">'
		+'采样设备：<input id="monitorSampleDevice" class="easyui-combobox" style="width:150px;">'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchSampleDeviceStatus()" ">查询</a>'
		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-redo\',plain:true" style="margin:0px 10px;" onclick="netSample()" ">采样指令</a>'
		+'<div id="netSampleDeviceTimer" style="width: 200px; height: 50px;float:right;display:inline;margin-top:-15px;" title="本次监控时长(刷新频率：一分钟)"></div>'
		+ '</div><div id="searchContentSampleDevice"></div>';
addPanel("采样设备监控", appendcontent);
//记录监控状态当前页
var netSamplePageNumber = -1;
var netSamplePageSize = -1;
//采样设备
var comboboxJsonSample = [];
//采样设备编码
var sampleDeviceCode = "";
//定时器
if($("#netSampleDeviceTimer").TimeCircles() != null){
	$("#netSampleDeviceTimer").TimeCircles().destroy();
}
$("#netSampleDeviceTimer").TimeCircles();
$("#netSampleDeviceTimer").TimeCircles({count_past_zero: true}).addListener(countdownComplete);
function countdownComplete(unit, value, total){
	if(unit=='Seconds' && value==0){
		searchSampleStatusFunc();
	}
}

//采样设备
$.ajax({
	url : "../CollectDeviceController/queryCollectDevice",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		if(json != null && json.result){
			comboboxJsonSample = json.rows;
		}
	}
});
$("#monitorSampleDevice").combobox({
	  data:comboboxJsonSample,
	  method:'post',
	  valueField:'cdCode',
	  textField:'cdName',
	  panelHeight:'auto',
	  value:comboboxJsonSample[0].cdCode
});

/*界面初始时用*/
function initNetSampleList(){
    $("#searchContentSampleDevice").datagrid({
     view:myview,
   	 fit : true,
   	 border : false,
   	 pagination : false,
   	 fitColumns : true,
   	 singleSelect : true,
   	 pageList : [ 10, 50, 100, 300 ],
   	 pageSize : 10,
   	 autoRowHeight : false,
   	 rownumbers : false,
   	 columns : []
    }).datagrid('loading');
}

/*刷新列表*/
function searchSampleStatusFunc() {
	var colums = [];//存储列内容
	var datajson = [];//存储列表数据
	var sampleDeviceCode = $("#monitorSampleDevice").combobox('getValue');
	$.ajax({
		url : "../CollectDeviceBoxController/queryCollectDeviceBox",
		data: {"cdCode": sampleDeviceCode,"rows":netSamplePageSize,"page":netSamplePageNumber},
		type : "post",
		dataType : "json",
		error : function(json) {
		},
		success : function(data) {
			 if(!data.result)
			 {
				 return;
			 }
			if (data.result) { // 请求成功
				colums.push( {field :'cdCode',title : "设备编号",width : 100,halign : 'center',align : 'center'});
				colums.push( {field :'cdMn',title : "设备MN号",width : 130,halign : 'center',align : 'center'});
				colums.push( {field :'cdName',title : "设备名称",width : 130,halign : 'center',align : 'center'});
				colums.push( {field :'boxNo',title : "采样箱编码",width : 100,halign : 'center',align : 'center'});
				colums.push( {field :'boxName',title : "采样箱名称",width : 120,halign : 'center',align : 'center'});
				colums.push( {field :'boxStatus',title : "箱子状态",width : 60,halign : 'center',align : 'center',
					formatter: function(value,row,index){
					var str = "";
					if(row.boxStatus=="0"){
						str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/link.png" class="operate-button">';
					}else if(row.boxStatus=="1"){
						str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/othermonitor.png" class="operate-button">';
					}else if(row.boxStatus=="2"){
						str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/overmonitor1.png" class="operate-button">';
					}else{
						str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/unmonitor.png" class="operate-button">';
					}
	        		return str;
	        	}});
				colums.push( {field :'boxStatusName',title : "箱子状态信息",width : 100,halign : 'center',align : 'center'});
				colums.push( {field :'netStatus',title : "在线状态",width : 60,halign : 'center',align : 'center'});
				colums.push( {field :'optTime',title : "更新时间",width : 130,halign : 'center',align : 'center'});
					$("#searchContentSampleDevice").datagrid({
						view:myview,
						fit : true,
						border : false,
						nowrap:false,
						pagination : true,
						fitColumns : false,
						singleSelect : true,
						pageList : [ 10, 50, 100, 300 ],
						pageSize : 10,
						autoRowHeight : false,
						rownumbers : true,
						toolbar : "#tbNetSamepleQuery",
						frozenColumns:[[{field :'Id',title : "记录",width : 50,halign : 'center',align : 'center',
											formatter: function(value,row,index){
												return '<a href="#" onclick="selSampleData(' + index + ')">记录</a>';  
											}
										}]],
						columns : [colums],
						data : data.rows
					});
				}
			}
	});
}
/*点击查询按钮*/
function searchSampleDeviceStatus(pageNumber) {
	initnetStatusList();
	var colums = [];   //存储列内容
	var datajson = []; //存储列表数据
	sampleDeviceCode = $("#monitorSampleDevice").combobox('getValue');
    var opts =$("#searchContentSampleDevice").datagrid('options');
    getSampleStatusGrid(sampleDeviceCode,netSamplePageSize,netSamplePageNumber,true);
}

function getSampleStatusGrid(cdCode,_pageSize,_pageNumber,pageStatusflag)
{
	var colums =[];
	 $.ajax({
			url : "../CollectDeviceBoxController/queryCollectDeviceBox",
			data: {"cdCode": cdCode,"rows":_pageSize,"page":_pageNumber},
			type : "post",
			dataType : "json",
			async:true,
			error : function(json) {
			},
			success : function(data) {
				if(data!=null && data!=undefined && data.result!=null && data.result!=undefined)
			    {
					colums.push( {field :'cdCode',title : "设备编号",width : 100,halign : 'center',align : 'center'});
					colums.push( {field :'cdMn',title : "设备MN号",width : 130,halign : 'center',align : 'center'});
					colums.push( {field :'cdName',title : "设备名称",width : 130,halign : 'center',align : 'center'});
					colums.push( {field :'boxId',title : "采样箱ID",width : 130,halign : 'center',align : 'center',hidden:"true"});
					colums.push( {field :'boxNo',title : "采样箱编码",width : 100,halign : 'center',align : 'center'});
					colums.push( {field :'boxName',title : "采样箱名称",width : 120,halign : 'center',align : 'center'});
					colums.push( {field :'boxStatus',title : "箱子状态",width : 60,halign : 'center',align : 'center',
						formatter: function(value,row,index){
							var str = "";
							if(row.boxStatus=="0"){
								str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/link.png" class="operate-button">';
							}else if(row.boxStatus=="1"){
								str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/othermonitor.png" class="operate-button">';
							}else if(row.boxStatus=="2"){
								str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/overmonitor1.png" class="operate-button">';
							}else{
								str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/unmonitor.png" class="operate-button">';
							}
							return str;
						}});
					colums.push( {field :'boxStatusName',title : "箱子状态信息",width : 100,halign : 'center',align : 'center'});
					colums.push( {field :'netStatus',title : "在线状态",width : 60,halign : 'center',align : 'center'});
					colums.push( {field :'optTime',title : "更新时间",width : 130,halign : 'center',align : 'center'});
					$("#searchContentSampleDevice").datagrid({
						view:myview,
						fit : true,
						border : false,
						nowrap:false,
						pagination : false,
						fitColumns : false,
						singleSelect : true,
//						pageList : [ 10, 50, 100, 300 ],
//						pageSize : 10,
						autoRowHeight : false,
						rownumbers : true,
						toolbar : "#tbNetSamepleQuery",
						frozenColumns:[[{field:'Id',title : "记录",width : 50,halign : 'center',align : 'center',
											formatter: function(value,row,index){
												return '<a href="#" onclick="selSampleData(' + index + ')">记录</a>';  
											}
										}]],
						columns : [colums],
						data:data.rows,
						onLoadSuccess:function (){
							$("#searchContentSampleDevice").datagrid("loaded");
						}
					});
			    }
			    else
			    {
					$('#searchContentSampleDevice').datagrid('loadData', { total: 0, rows: [] });  
				}
			}
    });
}

/*界面初始时用*/
function initnetStatusList(){
    $("#searchContentSampleDevice").datagrid({
     view:myview,
   	 fit : true,
   	 border : false,
   	 pagination : false,
   	 fitColumns : true,
   	 singleSelect : true,
   	 pageList : [ 10, 50, 100, 300 ],
   	 pageSize : 10,
   	 autoRowHeight : false,
   	 rownumbers : false,
   	 columns : []
    }).datagrid('loading');
}

function selSampleData(index) {  
	var record = $("#searchContentSampleDevice").datagrid("getRows")[index];
	var boxId = record.boxId;
	var boxNo = record.boxNo;
	var boxStatus = record.boxStatus;
	$("#dialogModel").dialog({
		width : 650,
		height : 400,
		title : "设备采样记录",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : true,
		iconCls : 'icon-listtable',
		resizable : true,
		closed : true,
		content : '<div id="dgSampleHistoryData" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	$("#dgSampleHistoryData").datagrid({
		view:myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : "../CollectDeviceBoxVaseController/queryCollectDeviceBoxVase",
		queryParams: {
			"boxId":boxId,
			"boxNo": boxNo
		},
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		frozenColumns:[[{field:'Id',title : "数据",width : 50,halign : 'center',align : 'center',
			formatter: function(value,row,index){
				return '<a href="#" onclick="selSampleAlarmData(' + index + ')">数据</a>';  
			}
		}]],
		columns : [ [{
			field : 'vaseNo',
			title : '采样编码',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'vaseTime',
			title : '采样时间',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'boxNo',
			title : '箱子编码',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'boxName',
			title : '箱子名称',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'vaseQn',
			title : '指令编码',
			width : 130,
			halign : 'center',
			align : 'center'
		} ] ]
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
	/* 定义分页器的初始显示默认值 */
	$("#dgSampleHistoryData").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
}

//采样指令
function netSample(){
	var cdCode = $("#monitorSampleDevice").combobox('getValue');
	var cdName = $("#monitorSampleDevice").combobox('getText');
	if(cdCode == null || cdCode == undefined || cdCode == ""){
		$.messager.alert("警告", "请选择采样设备！", "warning");
		return;
	}
	$("#dialogModel").dialog({
		width : 900,
		height : 400,
		title : "采样指令记录-"+cdName,
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : true,
		iconCls : 'icon-listtable',
		resizable : true,
		closed : true,
		content : '<div id="dgNetSample" class="config-form"></div>',
		buttons : [ {
			text : "下发指令",
			iconCls : "icon-redo",
			handler : function() {
				$.ajax({
					url : "../CollectDeviceBoxVaseController/insertNetSample",
					type : "post",
					dataType : "json",
					data: {"cdCode":cdCode},
					async:false,
					success : function(json) {
						if(json != null){
							if(json.result){
								$.messager.alert("提示", "已插入采样指令，请等待执行！", "info");
								reloadNetSampleFunc();
							}else{
								$.messager.alert("错误", json.detail, "error");
							}
						}
					}
				});
			}
		},{
			text : "刷新",
			iconCls : "icon-reload",
			handler : function() {
				reloadNetSampleFunc();
			}
		} ]
	}).dialog('center');
	$("#dgNetSample").datagrid({
		view:myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : "../CollectDeviceBoxVaseController/queryNetSample",
		queryParams: {
			"cnCode" : "3015",
			"cdCode" : cdCode
		},
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [{
			field : 'cdCode',
			title : '设备编码',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'cdName',
			title : '设备名称',
			width : 110,
			halign : 'center',
			align : 'center'
		},{
			field : 'vaseQn',
			title : '指令编码',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'statusName',
			title : '执行状态',
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
		} ] ]
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
	/* 定义分页器的初始显示默认值 */
	$("#dgNetSample").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
}

/* 查询采样指令 */
function reloadNetSampleFunc() {
	var cdCode = $("#monitorSampleDevice").combobox('getValue');
	$("#dgNetSample").datagrid("load", {			
		"cnCode" : "3015",
		"cdCode" : cdCode
	});
}

//查询采样超标数据
function selSampleAlarmData(index){
	var record = $("#dgSampleHistoryData").datagrid("getRows")[index];
	var vaseNo = record.vaseNo;
	$.ajax({
		url : "../CollectDeviceBoxVaseController/queryVaseDataModel",
		type : "post",
		dataType : "json",
		data:{
			"deviceCode":sampleDeviceCode,
			"vaseNo": vaseNo
		},
		error:function(e){
			$.messager.alert(e,'error');
		},
		success : function(json) {
			if(json != null){
				$.messager.alert('采集数据详细信息',json.detail);
			}
		}
	});
}
