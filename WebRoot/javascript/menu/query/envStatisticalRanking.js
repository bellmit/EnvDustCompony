/************************************
 * 功能：站点排名统计报表
 * 日期：2018-5-4 13:41:09
 ************************************/
var appendcontent = '<div id="envStatisticalRankingTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="时排名"  selected="true" style="padding:10px" id="esgHour"></div>'
	+ '<div title="日排名"  style="padding:10px" id="esgDay"></div>'
	+ '<div title="月排名"  style="padding:10px" id="esgMonth"></div>'
	+ '<div title="年排名"  style="padding:10px" id="esgYear"></div>'
	+ '<div title="时间段排名"  style="padding:10px" id="esgTimes"></div>'
	+ '</div>';
addPanel("站点排名统计报表", appendcontent);
var orderArrayEsg = [ {
	"id" : "asc",
	"name" : "由低到高"
}, {
	"id" : "desc",
	"name" : "由高到低"
} ];
var dataArrayEsg = [ {
	"id" : "avg",
	"name" : "平均值"
}, {
	"id" : "min",
	"name" : "最小值"
}, {
	"id" : "max",
	"name" : "最大值"
}];
var comboboxJsonEsg = [];

$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonEsg = json;
	}
});

tabContent("时排名","esgHour");
tabContent("日排名","esgDay");
tabContent("月排名","esgMonth");
tabContent("年排名","esgYear");
tabContent("时间段排名","esgTimes");

function getContent(title,id){
	var contents = "";
	if("时排名"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esgTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物：<input id="esgMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;类型：<input class="easyui-combobox" id="esgDataCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;顺序：<input class="easyui-combobox" id="esgOrderCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtHourTime'+id+'" data-options="required:true" style="width:104px;"/>'
				+'&nbsp;&nbsp;<input class="easyui-combobox" id="esgDtHourHour" data-options="required:true" style="width:50px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsgFunc(\'esgHour\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaEsgExcel(\'esgHour\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("日排名"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esgTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物：<input id="esgMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;类型：<input class="easyui-combobox" id="esgDataCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;顺序：<input class="easyui-combobox" id="esgOrderCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTime'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsgFunc(\'esgDay\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaEsgExcel(\'esgDay\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("月排名"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esgTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物：<input id="esgMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;类型：<input class="easyui-combobox" id="esgDataCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;顺序：<input class="easyui-combobox" id="esgOrderCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtMonthTime'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsgFunc(\'esgMonth\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaEsgExcel(\'esgMonth\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("年排名"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esgTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物：<input id="esgMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;类型：<input class="easyui-combobox" id="esgDataCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;顺序：<input class="easyui-combobox" id="esgOrderCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-combobox" id="esgDtYearTime" data-options="required:true" style="width:104px;"/>'
			    +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsgFunc(\'esgYear\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaEsgExcel(\'esgYear\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("时间段排名"==title){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="esgTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;'
				+'监控物：<input id="esgMonitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;类型：<input class="easyui-combobox" id="esgDataCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;顺序：<input class="easyui-combobox" id="esgOrderCombox_'+id+'" style="width:80px;"/>'
				+'&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTimeBegin'+id+'" data-options="required:true" style="width:102px;"/>'
				+'至<input class="easyui-datebox" id="dtDayTimeEnd'+id+'" data-options="required:true" style="width:102px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchEsgFunc(\'esgTimes\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvStaEsgExcel(\'esgTimes\')">导出</a>'
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
	$("#esgMonitorThings_"+id+"").combobox({
		  data:comboboxJsonEsg,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  value:comboboxJsonEsg[0].code,
		  onShowPanel: function () {
		      // 动态调整高度  
		      if (comboboxJsonEsg.length < 20) {
		          $(this).combobox('panel').height("auto");  
		      }else{
		          $(this).combobox('panel').height(300);
		      }
		  }
	});
	$("#esgOrderCombox_"+id+"").combobox({
		data : orderArrayEsg,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value:orderArrayEsg[0].id
	});
	$("#esgDataCombox_"+id+"").combobox({
		data : dataArrayEsg,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value:dataArrayEsg[0].id
	});
	if(title=="时排名"){
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
		$('#esgDtHourHour').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		if(nowHour > 0){
			$('#esgDtHourHour').combobox('setValue',nowHour-1);
			$("#dtHourTime"+id).datebox('setValue',formatterDate(new Date()));
		}else{
			$('#esgDtHourHour').combobox('setValue',23);
			$("#dtHourTime"+id).datebox('setValue',GetDateStr(-1,0));
		}
	}else if(title=="日排名"){
		$("#dtDayTime"+id).datebox('setValue',GetDateStr(-1,0));
	}else if(title=="月排名"){
		createDateboxByYYMM("dtMonthTime"+id);
		$("#dtMonthTime"+id).datebox('setValue',GetMonthStr(-1,0));
	}else if(title=="年排名"){
		var datatimejson = [];
		var myDate = new Date();
		//获取当前年份(4位)
		var nowYear = myDate.getFullYear();
		for (var i = 0; i < 10; i++) {
			datatimejson.push({"id" : nowYear-i,"name" : nowYear-i});
		}
		$('#esgDtYearTime').combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#esgDtYearTime').combobox('setValue',nowYear-1);
	}else if(title=="时间段排名"){
		$("#dtDayTimeBegin"+id).datebox('setValue',GetDateStr(-7,0));
		$("#dtDayTimeEnd"+id).datebox('setValue',GetDateStr(-1,0));
	}
    setCheckTitle();
}

/*查询列表信息*/
function searchEsgFunc(id) {
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
	if(id=="esgTimes"){
		initEsgTimesDataGridFunc("#searchContent_"+id);
	}else{
		initEsgDataGridFunc(id);
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
	var cnCode = $('#esgMonitorThings_'+id).combobox("getValue");
	if(cnCode == null || cnCode == ""){
		$.messager.alert("提示", "请选择一个监测物！", "warning");
		return false;
	}
	var orderType = $('#esgOrderCombox_'+id).combobox("getValue");
	var dataType = $('#esgDataCombox_'+id).combobox("getValue");
	var projectId = $("#deviceProjectId").combobox('getValue');
    var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	if(id=="esgHour"){
		var url = "../EnvStatisticalRankingController/getEsgData";
		options.url = url;
		var paramValue = $('#dtHourTime'+id).datebox("getValue")+" "+$('#esgDtHourHour').combobox("getValue");;
		options.queryParams = {"projectId":projectId, "list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "hour", "orderType":orderType, "dataType":dataType,"zsFlag":zsFlag };
	}else if(id=="esgDay"){
		var url = "../EnvStatisticalRankingController/getEsgData";
		options.url = url;
		var paramValue = $('#dtDayTime'+id).datebox("getValue");
		options.queryParams = { "projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "day", "orderType":orderType, "dataType":dataType,"zsFlag":zsFlag };
	}else if(id=="esgMonth"){
		var url = "../EnvStatisticalRankingController/getEsgData";
		options.url = url;
		var paramValue = $('#dtMonthTime'+id).datebox("getValue");
		options.queryParams = {"projectId":projectId, "list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "month", "orderType":orderType, "dataType":dataType ,"zsFlag":zsFlag};
	}else if(id=="esgYear"){
		var url = "../EnvStatisticalRankingController/getEsgData";
		options.url = url;
		var paramValue = $('#esgDtYearTime').combobox("getValue");
		options.queryParams = {"projectId":projectId, "list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "year", "orderType":orderType, "dataType":dataType ,"zsFlag":zsFlag};
	}else if(id=="esgTimes"){
		var url = "../EnvStatisticalRankingController/getEsgDataTimes";
		options.url = url;
		var beginTime = $('#dtDayTimeBegin'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd'+id).datebox("getValue");
		options.queryParams = {"projectId":projectId, "list": treeList, "levelFlag": levelFlag, "thingCode": cnCode, "beginTime": beginTime,"endTime":endTime,"orderType":orderType, "dataType":dataType,"zsFlag":zsFlag};
	}
    $("#searchContent_"+id).datagrid(options);
}

/*初始化列表（站点排名统计报表）*/
function initEsgDataGridFunc(id){
	/* 初始化列表,表头 */
	$("#searchContent_"+id).datagrid({
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
            formatter:function(value,row,index){
                if($('#zVauleChekbox'+id).is(':checked')){
                    return row.thingZsMax;
                }else{
                	return value;
				}
			}
		},{
			field : 'thingMin',
			title : '最小值',
			width : 100,
			halign : 'center',
			align : 'center',
            formatter:function(value,row,index){
                if($('#zVauleChekbox'+id).is(':checked')){
                    return row.thingZsMin;
                }else{
                    return value;
                }
            }
		},{
			field : 'thingAvg',
			title : '平均值',
			width : 100,
			halign : 'center',
			align : 'center',
            formatter:function(value,row,index){
				if($('#zVauleChekbox'+id).is(':checked')){
					return row.thingZsAvg;
				}else{
					return value;
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
function initEsgTimesDataGridFunc(id){
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
			width : 150,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceName',
			title : '设备名称',
			width : 150,
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
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'endTime',
			title : '结束时间',
			width : 120,
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

function outPutEnvStaEsgExcel(id){
	var station = $('#mytree').tree('getSelected');
	var selected = "";
	var levelFlag = "";
	if (station == null || station == undefined) {
		selected = "-1";
	} else {
		selected = station.id;
		levelFlag = station.levelFlag;
	}
	var url = "../ReportController/downEnvStaEsgExcel";
	var cnCode = $('#esgMonitorThings_'+id).combobox("getValue");
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(cnCode == null || cnCode == ""){
		$.messager.alert("提示", "请选择一个监测物！", "warning");
		return false;
	}
	var orderType = $('#esgOrderCombox_'+id).combobox("getValue");
	var dataType = $('#esgDataCombox_'+id).combobox("getValue");
    var zsFlag = false;
    if($('#zVauleChekbox'+id).is(':checked')){
        zsFlag = true;
    }
	if(id=="esgHour"){
		var paramValue = $('#dtHourTime'+id).datebox("getValue")+" "+$('#esgDtHourHour').combobox("getValue");;
		params = { "projectId":projectId,"selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "hour", "orderType":orderType, "dataType":dataType,"zsFlag":zsFlag };
	}else if(id=="esgDay"){
		var paramValue = $('#dtDayTime'+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "day", "orderType":orderType, "dataType":dataType ,"zsFlag":zsFlag};
	}else if(id=="esgMonth"){
		var paramValue = $('#dtMonthTime'+id).datebox("getValue");
		params = { "projectId":projectId,"selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "month", "orderType":orderType, "dataType":dataType,"zsFlag":zsFlag };
	}else if(id=="esgYear"){
		var paramValue = $('#esgDtYearTime').combobox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "selectTime": paramValue, "selectType": "year", "orderType":orderType, "dataType":dataType,"zsFlag":zsFlag };
	}else if(id=="esgTimes"){
		url = "../ReportController/downEnvStaTimesEgsExcel";
		var beginTime = $('#dtDayTimeBegin'+id).datebox("getValue");
		var endTime = $('#dtDayTimeEnd'+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": cnCode, "beginTime": beginTime,"endTime":endTime,"orderType":orderType, "dataType":dataType,"zsFlag":zsFlag};
	}
	outPutEnvStaEsgData(params,url);
}

/**
 * 导出统计报表(站点排名统计)
 */
function outPutEnvStaEsgData(params,url){
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

