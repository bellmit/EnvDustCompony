/************************************
 * 功能：污染排放统计分析
 * 日期：2017-9-26 09:43:09
 ************************************/
var appendcontent = '<div id="dischargeTotalTab" class="easyui-tabs" data-options="fit:true">'
	+ '<div title="日排放统计"  selected="true" style="padding:10px" id="dischargeDay"></div>'
	+ '<div title="月排放统计"  style="padding:10px" id="dischargeMonth"></div>'
	+ '<div title="年排放统计"  style="padding:10px" id="dischargeYear"></div>'
	+ '<div title="时间段排放统计"  style="padding:10px" id="dischargeTimes"></div>'
	+ '</div>';
addPanel("污染排放统计分析", appendcontent);
var comboboxJsonDis = [];
//监控物
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		for(var i=0;i<json.length;i++){
			var str = json[i].describe;
			if(str != null && ((str.indexOf("mg/m3") > 0 || str.indexOf("mg/m³") > 0)
					|| (str.indexOf("ug/m3") > 0 || str.indexOf("ug/m³") > 0)
					|| (str.indexOf("ng/m3") > 0 || str.indexOf("ng/m³") > 0)) ){
				comboboxJsonDis.push(json[i]);
			}
		}
	}
});

tabContent("日排放统计","dischargeDay");
tabContent("月排放统计","dischargeMonth");
tabContent("年排放统计","dischargeYear");
tabContent("时间段排放统计","dischargeTimes");

function getContent(title,id){
	var contents = "";
	if("dischargeDay"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
			+'<div id="dtTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'监控物质：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtDayTime_'+id+'" data-options="required:true" style="width:104px;"/>'
           		 +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
				 +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchTimeDischargeFunc(\'dischargeDay\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchDisChargeChart(\'dischargeDay\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvDischargeExcel(\'dischargeDay\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("dischargeMonth"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="dtTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'监控物质：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtMonthTime_'+id+'" data-options="required:true" style="width:104px;"/>'
                +'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
			    +'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchTimeDischargeFunc(\'dischargeMonth\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchDisChargeChart(\'dischargeMonth\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvDischargeExcel(\'dischargeMonth\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("dischargeYear"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="dtTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'监控物质：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-combobox" id="dtYearTime_'+id+'" data-options="required:true" style="width:104px;"/>'
            	+'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchTimeDischargeFunc(\'dischargeYear\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchDisChargeChart(\'dischargeYear\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvDischargeExcel(\'dischargeYear\')">导出</a>'
				+'</div>'
			+'</div>'
			+'<div data-options="region:\'center\',border:false" id="centerContent_'+id+'">'
				+'<div id="searchContent_'+id+'"></div>'
			+'</div>'
			+'</div>';
	}else if("dischargeTimes"==id){
		contents =
			'<div class="easyui-layout" data-options="fit:true">'
			+'<div data-options="region:\'north\',border:false">'
				+'<div id="dtTbQuery'+id+'" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
				+'监控站点：<span id="monitorStation_'+id+'" style="width:150px;">无</span>'
				+'&nbsp;&nbsp;&nbsp'
				+'监控物质：<input id="monitorThings_'+id+'" class="easyui-combobox" style="width:150px;">'
				+'&nbsp;&nbsp;&nbsp;查询范围：<input class="easyui-datebox" id="dtBeginTime_'+id+'" data-options="required:true" style="width:104px;"/>'	
				+'至<input class="easyui-datebox" id="dtEndTime_'+id+'" data-options="required:true" style="width:104px;"/>'
            	+'<span style="margin-left:8px;"><input type="checkbox"  id="zVauleChekbox'+id+'" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 0px 0px 10px;" onclick="searchTimeDischargeFunc(\'dischargeTimes\')">列表</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px 0px 10px;" onclick="searchDisChargeChart(\'dischargeTimes\')">图像</a>'
				+'<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="outPutEnvDischargeExcel(\'dischargeTimes\')">导出</a>'
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
	$("#monitorThings_"+id).combobox({
		  data:comboboxJsonDis,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  value:comboboxJsonDis[0].code,
		  onShowPanel: function () {
		      // 动态调整高度  
		      if (comboboxJsonDis.length < 20) {
		          $(this).combobox('panel').height("auto");  
		      }else{
		          $(this).combobox('panel').height(300);
		      }
		  }
    });
	if(id=="dischargeDay"){
		$("#dtDayTime_"+id).datebox('setValue',GetDateStr(-1,0));
	}else if(id=="dischargeMonth"){
		createDateboxByYYMM("dtMonthTime_"+id);
		$("#dtMonthTime_"+id).datebox('setValue',GetMonthStr(-1,0));
	}else if(id=="dischargeYear"){
		var datatimejson = [];
		var myDate = new Date();
		//获取当前年份(4位)
		var nowYear = myDate.getFullYear();
		for (var i = 0; i < 10; i++) {
			datatimejson.push({"id" : nowYear-i,"name" : nowYear-i});
		}
		$('#dtYearTime_'+id).combobox({
			data:datatimejson,
			valueField : 'id',
			textField : 'name'
		});
		$('#dtYearTime_'+id).combobox('setValue',nowYear-1);
	}else if(id=="dischargeTimes"){
		$("#dtBeginTime_"+id).datebox('setValue',GetDateStr(-7,0));
		$("#dtEndTime_"+id).datebox('setValue',GetDateStr(-1,0));
	}
    setCheckTitle();
}


/*查询列表信息*/
function searchTimeDischargeFunc(id) {
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		$.messager.alert("提示", "请选择一个区域或监控站点进行查询！", "error");
		return;
	}
	var thingValue = $('#monitorThings_' + id).combobox('getValue');
	if(thingValue == null || thingValue==undefined){
		$.messager.alert("提示", "请选择一个监控物质！", "warning");
		return;
	}
	$("#monitorStation_"+id).html(station.text);
	if(id=="dischargeTimes"){
		initDataGridFunc(id,"开始统计时间","结束统计时间");
		$("#searchContent_"+id).datagrid('showColumn', 'endTime');
	}else{
		initDataGridFunc(id,"统计时间","统计时间");
		$("#searchContent_"+id).datagrid('hideColumn', 'endTime');
	}
	loadGridData(id,treeid);
}

function loadGridData(id,deviceCode) {
	var thingUtil;
	var thingUtilName = $('#monitorThings_' + id).combobox('getText');
	if(thingUtilName != null && thingUtilName != '' && (thingUtilName.indexOf("mg/m3")>=0 || thingUtilName.indexOf("mg/m³")>=0)){
		thingUtil = "mg/m3";
	}else if(thingUtilName != null && thingUtilName != '' && (thingUtilName.indexOf("ug/m3")>=0 || thingUtilName.indexOf("ug/m³")>=0)){
		thingUtil = "ug/m3";
	}else if(thingUtilName != null && thingUtilName != '' && (thingUtilName.indexOf("ng/m3")>=0 || thingUtilName.indexOf("ng/m³")>=0)){
		thingUtil = "ng/m3";
	}else{
		$.messager.alert("提示", "监测物单位必须为mg/m3、ug/m3或ng/m3！", "warning");
		return;
	}
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
	var url = '';
	var thingCode = $('#monitorThings_' + id).combobox('getValue');
	var selectTime = '';
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(id=="dischargeDay"){
		url = '../DischargeController/queryDeviceDischarge';
		selectTime = $("#dtDayTime_"+id).datebox("getValue");
		options.url = url;
		options.queryParams = {"projectId":projectId, "list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime":selectTime, "selectType":"day"};
	}else if(id=="dischargeMonth"){
		url = '../DischargeController/queryDeviceDischarge';
		selectTime = $("#dtMonthTime_"+id).datebox("getValue");
		options.url = url;
		options.queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime":selectTime, "selectType":"month"};
	}else if(id=="dischargeYear"){
		url = '../DischargeController/queryDeviceDischarge';
		selectTime = $("#dtYearTime_"+id).datebox("getValue");
		options.url = url;
		options.queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime":selectTime, "selectType":"year"};
	}else if(id=="dischargeTimes"){
		url = '../DischargeController/queryDeviceTimesDischarge';
		var beginTime = $("#dtBeginTime_"+id).datebox("getValue");
		var endTime = $("#dtEndTime_"+id).datebox("getValue");
		options.url = url;
		options.queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "beginTime":beginTime, "endTime":endTime};
	}
    $("#searchContent_"+id).datagrid(options);
}

/*初始化列表*/
function initDataGridFunc(id,beginTile,endTitle){
	/* 初始化列表,表头 */
	$("#searchContent_"+id).datagrid({
		view : myview,
		fit : true,
		border : false,
		pagination : true,
		fitColumns : true,
		pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
		url : '',
		pageSize : 10,
		autoRowHeight : false,
		rownumbers : true,
		columns : [ [ {
			field : 'areaName',
			title : '所属区域',
			width : 160,
			halign : 'center',
			align : 'center'
		},{
			field : 'deviceCode',
			title : '设备编号',
			width : 120,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'deviceName',
			title : '设备名称',
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingCode',
			title : '监测物编号',
			width : 120,
			halign : 'center',
			align : 'center',
			hidden:true
		},{
			field : 'thingName',
			title : '监测物名称',
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'beginTime',
			title : beginTile,
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'endTime',
			title : endTitle,
			width : 120,
			halign : 'center',
			align : 'center'
		},{
			field : 'thingCou',
			title : '排放量',
			width : 120,
			halign : 'center',
			align : 'center',
            formatter:function(value,row,index){
				var zCou = ((row.thingZsCou==null)?"---": row.thingZsCou);
                if($('#zVauleChekbox'+id).is(':checked')){
                    return value + "/" + zCou;
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
	/* 定义分页器的初始显示默认值 */
	$("#searchContent_"+id).datagrid("getPager").pagination({
		total : 0
	});
}


/*图表*/
function searchDisChargeChart(id){
	var treeid = -1;
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		$.messager.alert("提示", "请选择一个区域或监控站点进行查询！", "error");
		return;
	}
	var thingCode = $('#monitorThings_' + id).combobox('getValue');
	if(thingCode == null || thingCode==undefined){
		$.messager.alert("提示", "请选择一个监控物质！", "warning");
		return;
	}
	var thingName = $('#monitorThings_' + id).combobox('getText');
	var thingUtil;
	var thingUtilName = $('#monitorThings_' + id).combobox('getText');
	if(thingUtilName != null && thingUtilName != '' && (thingUtilName.indexOf("mg/m3")>=0 || thingUtilName.indexOf("mg/m³")>=0)){
		thingUtil = "mg/m3";
	}else if(thingUtilName != null && thingUtilName != '' && (thingUtilName.indexOf("ug/m3")>=0 || thingUtilName.indexOf("ug/m³")>=0)){
		thingUtil = "ug/m3";
	}else if(thingUtilName != null && thingUtilName != '' && (thingUtilName.indexOf("ng/m3")>=0 || thingUtilName.indexOf("ng/m³")>=0)){
		thingUtil = "ng/m3";
	}else{
		$.messager.alert("提示", "监测物单位必须为mg/m3、ug/m3或ng/m3！", "warning");
		return;
	}
	var centercontent = $("#centerContent_" + id);
	centercontent.html("");
	$("#centerContent_" + id).append('<div id="searchContent_' + id + '" style=""></div>');
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
	var url = '';
	var selectTime = '';
	var queryParams = {};
	var projectId = $("#deviceProjectId").combobox('getValue');
	if(id=="dischargeDay"){
		url = '../DischargeController/queryDeviceDischarge';
		selectTime = $("#dtDayTime_"+id).datebox("getValue");
		queryParams = {"projectId":projectId, "list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime":selectTime, "selectType":"day"};
	}else if(id=="dischargeMonth"){
		url = '../DischargeController/queryDeviceDischarge';
		selectTime = $("#dtMonthTime_"+id).datebox("getValue");
		queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime":selectTime, "selectType":"month"};
	}else if(id=="dischargeYear"){
		url = '../DischargeController/queryDeviceDischarge';
		selectTime = $("#dtYearTime_"+id).datebox("getValue");
		queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime":selectTime, "selectType":"year"};
	}else if(id=="dischargeTimes"){
		url = '../DischargeController/queryDeviceTimesDischarge';
		var beginTime = $("#dtBeginTime_"+id).datebox("getValue");
		var endTime = $("#dtEndTime_"+id).datebox("getValue");
		queryParams = {"projectId":projectId,"list": treeList, "levelFlag": levelFlag, "thingCode": thingCode, "beginTime":beginTime, "endTime":endTime};
	}
	var yname = "排放量";//图表y轴名称
	var legendData = [];
	var xAxisData = [];
	var seriesData = [];
	ajaxLoading();
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		data :  queryParams,
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			if(json.result){
				legendData.push(thingName);
                var count = json.rows.length;
				if($('#zVauleChekbox'+id).is(':checked')){
                    legendData.push(thingName+"折算");
                    var yDataListZs = [];
                    for (var i = 0; i < count; i++) {
                        var xData = json.rows[i]["deviceName"];
                        if(xData == "合计"){
                            continue;
                        }
                        var yDataZs = json.rows[i]["thingZsCou"];
                        xAxisData.push(xData);
                        yDataListZs.push(yDataZs);
                    }
                    seriesData.push({
                        "name":thingName+"折算",
                        "type":'line',
                        symbol: 'triangle',
                        symbolSize: 20,
                        "data":yDataListZs,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    });
                }

				var yDataList = [];
                xData = [];
				for (var i = 0; i < count; i++) {
					var xData = json.rows[i]["deviceName"];
					if(xData == "合计"){
						continue;
					}
					var yData = json.rows[i]["thingCou"];
					xAxisData.push(xData);
					yDataList.push(yData);
				}
				seriesData.push({
		            "name":thingName,
		            "type":'line',
                    symbol: 'triangle',
                    symbolSize: 20,
		            "data":yDataList,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        });
				initDisChargeChart(xAxisData,legendData,seriesData,id,yname);
			}else{
				
			}
		}
	});
}

/*初始化表格*/
function initDisChargeChart(xAxisData,legendData,seriesData,id,yname){
	var dom = document.getElementById("searchContent_"+id);
	dom.style.cssText = "height:100%";
	var myChart = echarts.init(document.getElementById("searchContent_"+id));
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
		    	name: '时间',
		        type: 'category',
		        boundaryGap: false,
		        data: xAxisData,
		        axisLine : {    // 轴线
                    interval:0,
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
	            },
		        splitLine : {
	                show:true,
	                lineStyle: {
	                    color: '#483d8b',
	                    type: 'dashed',
	                    width: 1
	                }
	            }
		    },
		    yAxis: {
		    	name: yname,
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
	myChart.setOption(option);
}

/*导出数据到Excel*/
function outPutEnvDischargeExcel(id){
	var station = $('#mytree').tree('getSelected');
	var selected = "";
	var levelFlag = "";
	if (station == null || station == undefined) {
		selected = "-1";
	} else {
		selected = station.id;
		levelFlag = station.levelFlag;
	}
	var thingCode = $('#monitorThings_' + id).combobox('getValue');
	var params = "";
	var url = "../ReportController/downEnvDischargeExcel";
	var projectId = $("#deviceProjectId").combobox('getValue');
	var convertType = "";
    if($('#zVauleChekbox'+id).is(':checked')){
        convertType = "zs";
    }
	if(id=="dischargeDay"){
		var selectTime = $("#dtDayTime_"+id).datebox("getValue");
		params = {"projectId":projectId,"selected": selected, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime":selectTime, "selectType":"day","convertType":convertType};
	}else if(id=="dischargeMonth"){
		var selectTime = $("#dtMonthTime_"+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime": selectTime, "selectType": "month","convertType":convertType };
	}else if(id=="dischargeYear"){
		var selectTime = $("#dtYearTime_"+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": thingCode, "selectTime": selectTime, "selectType": "year" ,"convertType":convertType};
	}else if(id=="dischargeTimes"){
		var beginTime = $("#dtBeginTime_"+id).datebox("getValue");
		var endTime = $("#dtEndTime_"+id).datebox("getValue");
		params = {"projectId":projectId, "selected": selected, "levelFlag": levelFlag, "thingCode": thingCode, "beginTime": beginTime,"endTime":endTime, "selectType": "times","convertType":convertType};
	}
    outPutEnvDischargeData(params,url);
}

/**
 * 导出统计报表(年月日时)
 */
function outPutEnvDischargeData(params,url){
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

