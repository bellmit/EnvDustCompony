/*******************************************************************************
 * 功能：原始监测数据查询 日期：2016-6-3 09:28:09
 ******************************************************************************/
var appendcontent = '<div id="dgGetOriginalDataInfo"></div>'
		+ '<div id="tbdgGetOriginalDataInfo" style="padding:5px 10px;border-bottom:1px solid #ddd;">'
		+'监控站点：<span id="monitorStationOriginal" style="width:150px;">无</span>'
		+'&nbsp;&nbsp;&nbsp'
		+'监控物质：<input id="monitorThingsOriginal" class="easyui-combobox" style="width:150px;">'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterOrgDeviceMonitors()" title="筛选监控点监控物"></a>'
		+ '&nbsp;&nbsp;&nbsp;数据类型：<input class="easyui-combobox" name="ornCnCodeCombox" id="ornCnCodeCombox" style="width:150px;"/>'
		+ '&nbsp;&nbsp;&nbsp;采集时间范围：<input class="easyui-datetimebox" id="dtOrnBeginTime" style="width:143px;"/>'
		+ '<span>&nbsp;&nbsp;&nbsp;至：</span>'
		+ '<input class="easyui-datetimebox" id="dtOrnEndTime" style="width:143px;"/>'
		+ '<br>'
        +'<span style="margin-left:2px;"><input type="checkbox"  id="zVauleChekboxId" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
        +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchOrnDataFunc()" ">列表</a>'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchOrnChartFunc()">图像</a>'
		+ '&nbsp;&nbsp;&nbsp'
//		+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delRldDataFunc()">删除</a>'
		+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" style="margin:0px 10px;"  onclick="exportOrgFunction()">导出</a>'
		+ '</div>'
		+'<div data-options="region:\'center\',border:false" style="height:80%" id="centerOrnChartContent">'
		+'<div id="searchOrnChartContent"></div>'
		+'</div>';
addPanel("原始监测数据查询", appendcontent);
var initEndTime = formatterDate(new Date());
$("#dtOrnBeginTime").datetimebox('setValue', initEndTime);
$("#dtOrnEndTime").datetimebox('setValue', initEndTime + " 23:59:59");
setCheckTitle();
var comboboxJsonOriginal = [];
//监控物
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonOriginal = json;
	}
});
var dataCount1 = 0;
$("#monitorThingsOriginal").combobox({
	  data:comboboxJsonOriginal,
	  method:'post',
	  valueField:'code',
	  textField:'describe',
	  value:comboboxJsonOriginal[0].code,
      multiple:true,
	  formatter: function (row) {
		  var opts = $(this).combobox('options');
		  return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
	  },
	  onShowPanel: function () {
          var opts = $(this).combobox('options');
          var target = this;
          var values = $(target).combobox('getValues');
          $.map(values, function (value) {
              var el = opts.finder.getEl(target, value);
              el.find('input.combobox-checkbox')._propAttr('checked', true);
          });
	      // 动态调整高度  
	      if (comboboxJsonOriginal.length < 20) {
	          $(this).combobox('panel').height("auto");  
	      }else{
	          $(this).combobox('panel').height(300);
	      }
	  },
    onLoadSuccess: function (data) {
        var opts = $(this).combobox('options');
        var target = this;
        var values = $(target).combobox('getValues');
        $.map(values, function (value) {
            var el = opts.finder.getEl(target, value);
            el.find('input.combobox-checkbox')._propAttr('checked', true);
        });
        dataCount1 = data.length;
        if(data != null && data.length>0){
            $('#monitorThingsOriginal').combobox('setValue',data[0].code);
        }
    },
    onSelect: function (row) {
        var opts = $(this).combobox('options');
        var el = opts.finder.getEl(this, row[opts.valueField]);
        el.find('input.combobox-checkbox')._propAttr('checked', true);
    },
    onUnselect: function (row) {
        var opts = $(this).combobox('options');
        var el = opts.finder.getEl(this, row[opts.valueField]);
        el.find('input.combobox-checkbox')._propAttr('checked', false);
    }
});
//筛选设备监测物
function filterOrgDeviceMonitors(){
	var devicecode = "";
	var station = $('#mytree').tree('getSelected');
	if(station != null || station != undefined){
		if(station.isDevice){
			devicecode = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
			return false;
		}
	}else{
		$.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
		return false;
	}
	ajaxLoading();
	$.ajax({
		url : "../MonitorStorageController/getAthorityDeviceMonitors",
		type : "post",
		dataType : "json",
		data:{
			"deviceCode":devicecode
		},
		error:function(){
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			$("#monitorThingsOriginal").combobox('clear');
			comboboxJsonOriginal = [];
			if(json.length>0){
				comboboxJsonOriginal = json;
				$("#monitorThingsOriginal").combobox('loadData',comboboxJsonOriginal);
				if(comboboxJsonOriginal.length>0){
					$("#monitorThingsOriginal").combobox('setValues',comboboxJsonOriginal[0].code);
				}
			}else{
				$("#monitorThingsOriginal").combobox('loadData',comboboxJsonOriginal);
			}
		}
	});
}
var ornCnCodeValue = "2061";
$("#ornCnCodeCombox").combobox({
	data : ornCnCode,
	method : 'post',
	valueField : 'id',
	textField : 'name',
	panelHeight : 'auto',
	value : ornCnCodeValue
});

function initOriginalDataGridFunc(){
	/* 初始化列表,表头 */
	$("#searchOrnChartContent").datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : true,
        nowrap:false,
		fitColumns : false,
		pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
		url : "",
		pageSize : 50,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'storageId',
			checkbox : true
		},{field:"operate1",title:"更多",halign:"center",align : 'center',
	    	formatter: function(value,row,index){
	    		var str = '<a href="#this" title="更多监控物数据" class="easyui-tooltip" ' 
	    				+ 'onclick="moreMonitorThingData('+index+');">'
	    				+ '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/things.png" class="operate-button"></a>';
	    		return str;
	    	}
	    }, {
			field : 'deviceCode',
			title : '设备编号',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'deviceMn',
			title : '设备MN号',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'deviceName',
			title : '设备名称',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingCode',
			title : '监测物编码',
			width : 100,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'thingName',
			title : '监测物名称',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'updateType',
			title : '数据类型编码',
			width : 100,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'updateTypeName',
			title : '数据类型',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingRtd',
			title : '实时值',
			width : 140,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')) {
                    var zvalue = ((row.thingZsRtd == null) ? "---" : row.thingZsRtd);

                   /* var str = '<div>原始值：<span style="background:#00cb44;color:#fff;"><span style="padding:6px;">' + value + '</span></span></div><br>' +
                        '<div style="margin-top:3px;">折算值：<span style="background:#00cb44;color:#fff;"><span style="padding:6px;">' + zvalue + '</span></span></div>';*/
                    return value+"/"+zvalue;;
                }else{
                	return value
				}
   			 }
		}, {
			field : 'thingAvg',
			title : '平均值',
			width : 140,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')){
                    var zvalue = ((row.thingZsAvg == null)?"---":row.thingZsAvg);
                    return value+"/"+zvalue;
                }else{
					return value;
				}

            }
		}, {
			field : 'thingMin',
			title : '最小值',
			width : 140,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')){
                    var zvalue = ((row.thingZsMin == null)?"---":row.thingZsMin);
                    return value+"/"+zvalue;
                }else{
                    return value;
				}
            }
		}, {
			field : 'thingMax',
			title : '最大值',
			width : 140,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')){
                    var zvalue = ((row.thingZsMax == null)?"---":row.thingZsMax);
                    return value+"/"+zvalue;;
                }else{
					return value;
				}
            }
		}, {
			field : 'rtdTime',
			title : '实时数据采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'statusName',
			title : '数据标识',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'beginTime',
			title : '开始采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'endTime',
			title : '结束采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'updateTime',
			title : '系统录入时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}] ],
		singleSelect : false,
		selectOnCheck : true,
		checkOnSelect : true
	}).datagrid('doCellTip', {
		cls : {
			'max-width' : '500px'
		}
	});
	/* 定义分页器的初始显示默认值 */
	$("#searchOrnChartContent").datagrid("getPager").pagination({
		total : 0
	});
}


/* 查询获取数据 */
function searchOrnDataFunc() {
	initOriginalDataGridFunc();
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		treeid = -1;
		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
		return false;
	}else{
		if(station.isDevice){
			treeid = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	var beginTime = $("#dtOrnBeginTime").datetimebox('getValue');
	var endTime = $("#dtOrnEndTime").datetimebox('getValue');
	var cnCode = $("#ornCnCodeCombox").combobox("getValue");
	if(beginTime == null || beginTime == ''){
		$.messager.alert("提示", "请填写开始时间！", "warning");
		return false;
	}
	if(endTime == null || endTime == ''){
		$.messager.alert("提示", "请填写结束时间！", "warning");
		return false;
	}
	if(cnCode == null || cnCode == ''){
		$.messager.alert("提示", "请选择数据类型！", "warning");
		return false;
	}
	var dtBegin = new Date(Date.parse(beginTime));
	var dtEnd= new Date(Date.parse(endTime));
	var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
	if(cnCode == '2011' && diffDay>1){
		$.messager.alert("提示", "只能查询2天内的实时数据！", "warning");
		return false;
	}
	if(cnCode == '2051' && diffDay>6){
		$.messager.alert("提示", "只能查询7天内的分钟数据！", "warning");
		return false;
	}
	$("#monitorStationOriginal").html(station.text);
	var thingValue = $("#monitorThingsOriginal").combobox('getValues');
	//清空图标内容的处理，暂时这样处理
	var dom = document.getElementById("searchOrnChartContent");
	dom.innerHTML = "";
	var url = "../MonitorStorageController/getOriginalData";
	$('#searchOrnChartContent').datagrid('options').url = url;

	$("#searchOrnChartContent").datagrid("load", {
		"deviceCode": treeid,
		"beginTime":beginTime,
		"endTime":endTime,
		"updateType":cnCode,
		"select":treeid,
		"list":thingValue
	});
	if(cnCode == '2011'){
		$("#searchOrnChartContent").datagrid('showColumn', 'thingRtd');
		$("#searchOrnChartContent").datagrid('showColumn', 'rtdTime');
		$("#searchOrnChartContent").datagrid('showColumn', 'statusName');
		$("#searchOrnChartContent").datagrid('hideColumn', 'thingAvg');
		$("#searchOrnChartContent").datagrid('hideColumn', 'thingMax');
		$("#searchOrnChartContent").datagrid('hideColumn', 'thingMin');
		$("#searchOrnChartContent").datagrid('hideColumn', 'beginTime');
		$("#searchOrnChartContent").datagrid('hideColumn', 'endTime');
	}else{
		$("#searchOrnChartContent").datagrid('showColumn', 'thingAvg');
		$("#searchOrnChartContent").datagrid('showColumn', 'thingMax');
		$("#searchOrnChartContent").datagrid('showColumn', 'thingMin');
		$("#searchOrnChartContent").datagrid('showColumn', 'beginTime');
		$("#searchOrnChartContent").datagrid('showColumn', 'endTime');
		$("#searchOrnChartContent").datagrid('hideColumn', 'thingRtd');
		$("#searchOrnChartContent").datagrid('hideColumn', 'rtdTime');
		$("#searchOrnChartContent").datagrid('showColumn', 'statusName');
	}
}

/* 显示排放量排名列表 */
function moreMonitorThingData(index) {
	var record = $("#searchOrnChartContent").datagrid("getRows")[index];
	var deviceCode = record.deviceCode;
	var beginTime = record.updateTime;
	var endTime = record.updateTime;
	var updateType = record.updateType;
	var select = "more-data";
	$("#dialogModel").dialog({
		width : 800,
		height : 400,
		title : "监测物数据信息",
		inline : true,
		modal : true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : true,
		iconCls : 'icon-listtable',
		resizable : true,
		closed : true,
		content : '<div id="dgMoreMonitorThingData" class="config-form"></div>',
		buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				$("#dialogModel").dialog("close");
			}
		} ]
	}).dialog('center');
	$("#dgMoreMonitorThingData").datagrid({
		view:myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : false,
		singleSelect : true,
		pageList : [ 10,50, 100, 150, 200, 250, 300 ],
		url : "../MonitorStorageController/getOriginalData",
		queryParams: {
			"deviceCode": deviceCode,
			"beginTime":beginTime,
			"endTime":endTime,
			"updateType":updateType,
			"select":select
		},
		pageSize : 50,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [{
			field : 'deviceName',
			title : '设备名称',
			width : 120,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingName',
			title : '监测物名称',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'updateTypeName',
			title : '数据类型',
			width : 80,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingRtd',
			title : '实时值',
			width : 80,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')){
                    var zvalue = ((row.thingZsRtd == null)?"---":row.thingZsRtd);
                    return value+"/"+zvalue;
                }else{
                    return value;
                }
            }
		}, {
			field : 'thingAvg',
			title : '平均值',
			width : 80,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')){
                    var zvalue = ((row.thingZsAvg == null)?"":row.thingZsAvg);
                    return value+"/"+zvalue;
                }else{
                	return value;
				}
            }
		}, {
			field : 'thingMin',
			title : '最小值',
			width : 80,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')){
                    var zvalue =((row.thingZsMin == null)?"":row.thingZsMin);
                    return value+"/"+zvalue;
                }else{
                    return value;
				}
            }
		}, {
			field : 'thingMax',
			title : '最大值',
			width : 80,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekboxId').is(':checked')){
                    var zvalue = ((row.thingZsMax == null)?"":row.thingZsMax);
                    return value+"/"+zvalue;
                }else{
                    return value;
				}
            }
		}, {
			field : 'rtdTime',
			title : '实时数据采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'statusName',
			title : '数据标识',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'beginTime',
			title : '开始采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		}, {
			field : 'endTime',
			title : '结束采集时间',
			width : 150,
			halign : 'center',
			align : 'center'
		} ] ]
	}).datagrid('doCellTip',{cls:{'max-width':'500px'}});
	/* 定义分页器的初始显示默认值 */
	$("#dgMoreMonitorThingData").datagrid("getPager").pagination({
		total : 0
	});
	/* 重绘窗口 */
	$.parser.parse("#dialogModel");
	$("#dialogModel").dialog("open");
	if(updateType == '2011'){
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'thingRtd');
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'rtdTime');
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'statusName');
		$("#dgMoreMonitorThingData").datagrid('hideColumn', 'thingAvg');
		$("#dgMoreMonitorThingData").datagrid('hideColumn', 'thingMax');
		$("#dgMoreMonitorThingData").datagrid('hideColumn', 'thingMin');
		$("#dgMoreMonitorThingData").datagrid('hideColumn', 'beginTime');
		$("#dgMoreMonitorThingData").datagrid('hideColumn', 'endTime');
	}else{
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'thingAvg');
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'thingMax');
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'thingMin');
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'beginTime');
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'endTime');
		$("#dgMoreMonitorThingData").datagrid('hideColumn', 'thingRtd');
		$("#dgMoreMonitorThingData").datagrid('hideColumn', 'rtdTime');
		$("#dgMoreMonitorThingData").datagrid('showColumn', 'statusName');
	}
}

/* 删除获取数据计划 */
function delRldDataFunc() {
	$.messager.alert("提示", "暂时不提供前台删除数据功能！", "warning");
	return false;
	var selectrow = $("#searchOrnChartContent").datagrid("getChecked");
	var idArry = [];
	for (var i = 0; i < selectrow.length; i++) {
		var beginTime = $("#dtOrnBeginTime").datetimebox('getValue');
		var endTime = $("#dtOrnEndTime").datetimebox('getValue');
		var dtBegin = new Date(Date.parse(beginTime));
		var dtEnd= new Date(Date.parse(endTime));
		var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
		if (diffDay<365) {
			$.messager.alert("提示", "不能删除一年以内的数据！", "warning");
			return false;
		}
		idArry.push(selectrow[i].commId);
	}
	if (selectrow.length < 1) {
		$.messager.alert("提示", "请选择删除的记录！", "warning");
		return false;
	}
	$.messager.confirm("提示", '确定删除选定记录吗？', function(r) {
		if (r) {
			ajaxLoading();
			$.ajax({
				url : "",
				type : "post",
				dataType : "json",
				data : {
					"list" : idArry
				},
				success : function(json) {
					ajaxLoadEnd();
					if (json.result) {
						$.messager.alert("提示", "删除成功！", "info");
						$("#searchOrnChartContent").datagrid('reload');
					} else {
						$.messager.alert("错误", json.detail, "error");
					}
				}
			});
		}
	});
}


/*查询图表信息*/
function searchOrnChartFunc() {
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		treeid = -1;
		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
		return false;
	}else{
		if(station.isDevice){
			treeid = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	var beginTime = $("#dtOrnBeginTime").datetimebox('getValue');
	var endTime = $("#dtOrnEndTime").datetimebox('getValue');
	var cnCode = $("#ornCnCodeCombox").combobox("getValue");
	var timelist = {};
	if(beginTime == null || beginTime == ''){
		$.messager.alert("提示", "请填写开始时间！", "warning");
		return false;
	}
	if(endTime == null || endTime == ''){
		$.messager.alert("提示", "请填写结束时间！", "warning");
		return false;
	}
	if(cnCode == null || cnCode == ''){
		$.messager.alert("提示", "请选择数据类型！", "warning");
		return false;
	}
	var dtBegin = new Date(Date.parse(beginTime));
	var dtEnd= new Date(Date.parse(endTime));
	var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
	if(cnCode == '2011' && diffDay>2){
		$.messager.alert("提示", "只能查询2天内的实时数据！", "warning");
		return false;
	}
	if(cnCode == '2051' && diffDay>7){
		$.messager.alert("提示", "只能查询7天内的分钟数据！", "warning");
		return false;
	}
	var yname = "数值";// 图表y轴名称
	var thingValue = $("#monitorThingsOriginal").combobox('getValues');
	if(thingValue == "" || thingValue == null){
		$.messager.alert("提示", "请选择监测物质！", "warning");
		return false;
	}else{
		yname = $("#monitorThingsOriginal").combobox('getText');
	}
	ajaxLoading();
	$("#monitorStationOriginal").html(station.text);
	var centercontent = $("#centerOrnChartContent");
	centercontent.html("");
	$("#centerOrnChartContent").append('<div id="searchOrnChartContent" style=""></div>');
	var zsFlag = false;
	if($('#zVauleChekboxId').is(':checked')){
        zsFlag = true;
	}
	$.ajax({
		url : "../../../MonitorStorageController/getOriginalChartData",
		type : "post",
		dataType : "json",
		async : true,
		data : {
			"deviceCode": treeid,
			"beginTime":beginTime,
			"endTime":endTime,
			"updateType":cnCode,
			"select":treeid,
			"list":thingValue,
			"zsFlag":zsFlag
		},
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			var legendData = [];
			var seriesData = [];
			if (json.time != undefined) {
				var timeArry = json["time"];
				var max = null;
				for ( var index in json) {
					if (index != "time") {
						legendData.push(index);
						seriesData.push({
							"name" : index,
							"type" : 'line',
							"data" : json[index],
							markPoint : {
								data : [ {
									type : 'max',
									name : '最大值'
								},{
									type : 'min',
									name : '最小值'
								} ]
							},
							markLine : {
								itemStyle : {
									normal : {
										lineStyle : {
											width : 2
										}
									}
								}
							}
						});
					} else {
						timelist[index] = json[index];
					}
				}
				initOriginalChart(timelist, legendData, seriesData, yname);
			} else {
				//$.messager.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
				initOriginalChart(timelist, legendData, seriesData, yname);
			}
			ajaxLoadEnd();
		}
	});
}

/*初始化表格*/
function initOriginalChart(timelist,legendData,seriesData,yname){
	var dom = document.getElementById("searchOrnChartContent");
	dom.style.cssText = "height:100%";
	mychart = echarts.init(document.getElementById("searchOrnChartContent"));
	var option = {
		    title: {
		        text: '图表',
	        	textStyle: {
	                  fontSize: 18,
	                  fontWeight: 'bolder',
	                  color: '#333'          // 主标题文字颜色
	            }
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:legendData
		    },
		    toolbox: {
		        show : true,
		        orient:	'vertical',
		        y:'center',
		        feature : {
		            mark : {show: true},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            dataZoom: {},
		            saveAsImage : {show: true}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: timelist["time"],
		        axisLine : {    // 轴线
	                show: true,
	                lineStyle: {
	                    color: 'green',
	                    type: 'solid',
	                    width: 2
	                }
	            },
		        axisLabel : {
	                show:true,
	                textStyle: {
	                    color: 'green',
	                    fontFamily: 'sans-serif',
	                    fontSize: 13,
	                    fontWeight: 'bold'
	                }
	            }
		    },
		    yAxis: {
		    	name:yname,
		        type: 'value',
		        axisLine : {    // 轴线
	                show: true,
	                lineStyle: {
	                    color: 'green',
	                    type: 'solid',
	                    width: 2
	                }
	            },
		        axisLabel: {
		            formatter: '{value}',
		            textStyle: {
	                    color: 'green',
	                    fontFamily: 'sans-serif',
	                    fontSize: 13,
	                    fontWeight: 'bold'
	                }
		        }
		    },
		    series:seriesData
		};
	mychart.clear();
	mychart.setOption(option,true);
	mychart.resize(); 
}

/*导出*/
function exportOrgFunction() {
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		treeid = -1;
		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
		return false;
	}else{
		if(station.isDevice){
			treeid = station.id;
		}else{
			$.messager.alert("提示", "请选择一个监控站点进行查询！", "warning");
			return false;
		}
	}
	var beginTime = $("#dtOrnBeginTime").datetimebox('getValue');
	var endTime = $("#dtOrnEndTime").datetimebox('getValue');
	var cnCode = $("#ornCnCodeCombox").combobox("getValue");
	if(beginTime == null || beginTime == ''){
		$.messager.alert("提示", "请填写开始时间！", "warning");
		return false;
	}
	if(endTime == null || endTime == ''){
		$.messager.alert("提示", "请填写结束时间！", "warning");
		return false;
	}
	if(cnCode == null || cnCode == ''){
		$.messager.alert("提示", "请选择数据类型！", "warning");
		return false;
	}
	var dtBegin = new Date(Date.parse(beginTime));
	var dtEnd= new Date(Date.parse(endTime));
	var diffDay = parseInt((dtEnd.getTime() - dtBegin.getTime()) / (1000 * 60 * 60 * 24));
	if(cnCode == '2011' && diffDay>2){
		$.messager.alert("提示", "只能查询2天内的实时数据！", "warning");
		return false;
	}
	if(cnCode == '2051' && diffDay>7){
		$.messager.alert("提示", "只能查询7天内的分钟数据！", "warning");
		return false;
	}
    var zsFlag = false;
    if($('#zVauleChekboxId').is(':checked')){
        zsFlag = true;
    }
	var thingValue = $("#monitorThingsOriginal").combobox('getValues');
	location.href = "../ExportController/exportOriginalData?deviceCode="+treeid+"&beginTime="+beginTime+"&endTime="+endTime+"&updateType="+cnCode+"&list="+thingValue+"&zsFlag="+zsFlag;
}