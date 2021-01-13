/************************************
 * 功能：年月日时统计报表
 * 日期：2018-5-4 13:41:09
 ************************************/
var appendcontent = '<div id="envStatisticalReportTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="时统计报表"  selected="true" style="padding:10px" id="esrHour"></div>'
	+ '<div title="日统计报表"  style="padding:10px" id="esrDay"></div>'
	+ '<div title="月统计报表"  style="padding:10px" id="esrMonth"></div>'
	+ '<div title="年统计报表"  style="padding:10px" id="esrYear"></div>'
	+ '<div title="时间段统计报表"  style="padding:10px" id="esrTimes"></div>'
	+ '</div>';
addPanel("年月日时统计报表", appendcontent);
var comboboxJsonEsr = [];

$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonEsr = json;
	}
});

tabContent("时统计报表","esrHour");
tabContent("日统计报表","esrDay");
tabContent("月统计报表","esrMonth");
tabContent("年统计报表","esrYear");
tabContent("时间段统计报表","esrTimes");

function getContent(title,id){
	var contents = "";
	if("时统计报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物质：<input id="esrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtHourTime'+id+'" data-options="required:true" style="width:104px;"/>'
				+'&nbsp;&nbsp;<input class="easyui-combobox" id="esrDtHourHour" data-options="required:true" style="width:50px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsrFunc(\'esrHour\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaExcel(\'esrHour\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("日统计报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物质：<input id="esrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTime'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsrFunc(\'esrDay\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaExcel(\'esrDay\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("月统计报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物质：<input id="esrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtMonthTime'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsrFunc(\'esrMonth\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaExcel(\'esrMonth\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("年统计报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物质：<input id="esrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-combobox" id="esrDtYearTime" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsrFunc(\'esrYear\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaExcel(\'esrYear\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("时间段统计报表"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esrTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物质：<input id="esrMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTimeBegin'+id+'" data-options="required:true" style="width:104px;"/>'
				+'至<input class="easyui-datebox" id="dtDayTimeEnd'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsrFunc(\'esrTimes\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaExcel(\'esrTimes\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}
	return contents;
}

//tab页内容
function tabContent(title,id){
	var contents = getContent(title,id);
	$('#'+id).append(contents);
	$.parser.parse('#'+id);
	$("#esrMonitorThings_"+id+"").combobox({
		  data:comboboxJsonEsr,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  onShowPanel: function () {
		      // 动态调整高度  
		      if (comboboxJsonEsr.length < 20) {
		          $(this).combobox('panel').height("auto");  
		      }else{
		          $(this).combobox('panel').height(300);
		      }
		  }
	});
	
	if(title=="时统计报表"){
		var datatimejson = [];
		var myDate = new Date();
		//获取小时时间
		var nowHour = myDate.getHours();
		for (var i = 0; i < 24; i++) {
			if(i<10){
				datatimejson.push({"id" : i,"name" : "0"+i});
			}else{
				datatimejson.push({"id" : i,"name" : i});
			}
		}
		$('#esrDtHourHour').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		if(nowHour > 0){
			$('#esrDtHourHour').combobox('setValue',nowHour-1);
			$("#dtHourTime"+id).datebox('setValue',formatterDate(new Date()));
		}else{
			$('#esrDtHourHour').combobox('setValue',23);
			$("#dtHourTime"+id).datebox('setValue',GetDateStr(-1,0));
		}
	}else if(title=="日统计报表"){
		$("#dtDayTime"+id).datebox('setValue',GetDateStr(-1,0));
	}else if(title=="月统计报表"){
		createDateboxByYYMM("dtMonthTime"+id);
		$("#dtMonthTime"+id).datebox('setValue',GetMonthStr(-1,0));
	}else if(title=="年统计报表"){
		var datatimejson = [];
		var myDate = new Date();
		//获取当前年份(4位)
		var nowYear = myDate.getFullYear();
		for (var i = 0; i < 10; i++) {
			datatimejson.push({"id" : nowYear-i,"name" : nowYear-i});
		}
		$('#esrDtYearTime').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#esrDtYearTime').combobox('setValue',nowYear-1);
	}else if(title=="时间段统计报表"){
		$("#dtDayTimeBegin"+id).datebox('setValue',GetDateStr(-7,0));
		$("#dtDayTimeEnd"+id).datebox('setValue',GetDateStr(-1,0));
	}
    setCheckTitle();
}

/*查询列表信息*/
function searchEsrFunc(id) {
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station != null || station != undefined){
		if(station.isDevice){
			treeid = station.id;
		}
		$("#monitorStation_"+id).html(station.text);
	}else{
		$("#monitorStation_"+id).html("全部");
	}
	if(id=="esrTimes"){
		initEsrTimesDataGridFunc("#searchContent_"+id);
	}else{
		initEsrDataGridFunc(id);
	}
	loadErsGridData(id,treeid);
}

function loadErsGridData(id,deviceCode) {
	var options = $("#searchContent_"+id).datagrid('options');
	var station = $('#mytree').tree('getSelected');
	var treeList = [];
	var levelFlag = "";
	if (station == null || station == undefined) {
		treeList.push(-1);
		selected = "-1";
	} else {
		selected = station.id;
		treeList.push(station.id);
		levelFlag = station.levelFlag;
	}
	var cnCode = $('#esrMonitorThings_'+id).combobox("getValue");
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(id=="esrHour"){
		var url = "../EnvStatisticalReportController/getEsrData";
		options.url = url;
		var paramValue = $('#dtHourTime'+id).datebox("getValue")+" "+$('#esrDtHourHour').combobox("getValue");
		options.queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "hour" };
	}else if(id=="esrDay"){
		var url = "../EnvStatisticalReportController/getEsrData";
		options.url = url;
		var paramValue = $('#dtDayTime'+id).datebox("getValue");
		options.queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "day" };
	}else if(id=="esrMonth"){
		var url = "../EnvStatisticalReportController/getEsrData";
		options.url = url;
		var paramValue = $('#dtMonthTime'+id).datebox("getValue");
		options.queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "month" };
	}else if(id=="esrYear"){
		var url = "../EnvStatisticalReportController/getEsrData";
		options.url = url;
		var paramValue = $('#esrDtYearTime').combobox("getValue");
		options.queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "year" };
	}else if(id=="esrTimes"){
		var url = "../EnvStatisticalReportController/getEsrDataTimes";
		options.url = url;
		var beginTime = $('#dtDayTimeBegin'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd'+id).datebox("getValue");
		options.queryParams = { "projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "beginTime": beginTime,"endTime":endTime};
	}
    $("#searchContent_"+id).datagrid(options);
}

/*初始化列表（年月日时列表）*/
function initEsrDataGridFunc(id){
	/* 初始化列表,表头 */
	$("#searchContent_"+id).datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : false,
		fitColumns : true,
		pageList : [ 50, 100, 150, 200, 250, 300 ],
		url : '',
		pageSize : 50,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'areaName',
			title : '所属区域',
			width : 160,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceName',
			title : '设备名称',
			width : 160,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingName',
			title : '监测物',
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'beginTime',
			title : '统计时间',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingMax',
			title : '最大值',
			width : 100,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekbox'+id).is(':checked')) {
                    var zvalue = ((row.thingZsMax == null) ? "---" : row.thingZsMax);
                    return value+"/"+zvalue;;
                }else{
                    return value
                }
            }
		},{
			field : 'thingMin',
			title : '最小值',
			width : 100,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekbox'+id).is(':checked')) {
                    var zvalue = ((row.thingZsMin == null) ? "---" : row.thingZsMin);
                    return value+"/"+zvalue;;
                }else{
                    return value
                }
            }
		},{
			field : 'thingAvg',
			title : '平均值',
			width : 100,
			halign : 'center',
			align : 'center',
            formatter: function(value,row,index){
                if($('#zVauleChekbox'+id).is(':checked')) {
                    var zvalue = ((row.thingZsAvg == null) ? "---" : row.thingZsAvg);
                    return value+"/"+zvalue;;
                }else{
                    return value
                }
            }
		}] ],
		singleSelect : true
	}).datagrid('doCellTip', {
		cls : {
			'max-width' : '500px'
		}
	});
}

/*初始化列表(时间段列表)*/
function initEsrTimesDataGridFunc(id){
	/* 初始化列表,表头 */
	$(id).datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : false,
		fitColumns : false,
		pageList : [ 50, 100, 150, 200, 250, 300 ],
		url : '',
		pageSize : 50,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'areaName',
			title : '所属区域',
			width : 160,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceName',
			title : '设备名称',
			width : 160,
			halign : 'center',
			align : 'center'
		}, {
			field : 'thingName',
			title : '监测物',
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'beginTime',
			title : '开始时间',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'endTime',
			title : '结束时间',
			width : 130,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingMax',
			title : '最大值',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingMin',
			title : '最小值',
			width : 100,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingAvg',
			title : '平均值',
			width : 100,
			halign : 'center',
			align : 'center'
		}] ],
		singleSelect : true
	}).datagrid('doCellTip', {
		cls : {
			'max-width' : '500px'
		}
	});
}

function outPutEnvStaExcel(id){
	var station = $('#mytree').tree('getSelected');
	var selected = "";
	var levelFlag = "";
	if (station == null || station == undefined) {
		selected = "-1";
	} else {
		selected = station.id;
		levelFlag = station.levelFlag;
	}
	var cnCode = $('#esrMonitorThings_'+id).combobox("getValue");
	var params = "";
	var projectId = $("#deviceProjectId").combobox('getValue');
	var url = "../ReportController/downEnvStaExcel";
    var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')) {
        zsFlag = true;
    }
	if(id=="esrHour"){
		var paramValue = $('#dtHourTime'+id).datebox("getValue")+" "+$('#esrDtHourHour').combobox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "hour","zsFlag":zsFlag};
	}else if(id=="esrDay"){
		var paramValue = $('#dtDayTime'+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "day" ,"zsFlag":zsFlag};
	}else if(id=="esrMonth"){
		var paramValue = $('#dtMonthTime'+id).datebox("getValue");
		params = { "projectId":projectId,"selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "month","zsFlag":zsFlag };
	}else if(id=="esrYear"){
		var paramValue = $('#esrDtYearTime').combobox("getValue");
		params = { "projectId":projectId,"selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "year" ,"zsFlag":zsFlag};
	}else if(id=="esrTimes"){
		url = "../ReportController/downEnvStaTimesExcel";
		var beginTime = $('#dtDayTimeBegin'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd'+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "beginTime": beginTime,"endTime":endTime,"zsFlag":zsFlag};
	}
    outPutEnvStaData(params,url);
}

/**
 * 导出统计报表(年月日时)
 */
function outPutEnvStaData(params,url){
    var form=$("<form>");//定义一个form表单
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","post");
    form.attr("action",url);
    for(var key in params){
        var input=$("<input>");
        input.attr("type","hidden");
        input.attr("name",key);
        input.attr("value",params[key]);
        form.append(input);
    }
    $("body").append(form);//将表单放置在web中
    form.submit();//表单提交
}

