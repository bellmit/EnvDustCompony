/************************************
 * 功能：网络站点最新数据实时上报
 * 日期：2016-3-24 09:43:09
 ************************************/
var appendcontent = '<div id="tbNetDataQuery" style="padding:15px 8px;font-size:13px;">'
	+'<span style="float:left;">'
	+ '<a href="#" class="easyui-linkbutton" onclick="clickDataStatus(\'all\')">站点：<label id="stationNetDataStatus">0个</label></a>'
	+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-link\',plain:true" onclick="clickDataStatus(\'normal\')">正常：<label id="linkeNetDataStatus">0个</label></a>'
	+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-overmonitor\',plain:true" onclick="clickDataStatus(\'over\')">超出预警线：<label id="overmonitorNetDataStatus">0个</a>'
	+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-unlink\',plain:true" onclick="clickDataStatus(\'disconn\')">断开：<label id="unlikeNetDataStatus">0个</a>'
	+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-unmonitor\',plain:true" onclick="clickDataStatus(\'none\')">无监测数据：<label id="unmonitorNetDataStatus">0个</a>'
	+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-othermonitor\',plain:true" onclick="clickDataStatus(\'other\')" style="margin-right:20px;">其他状态：<label id="othermonitorNetDataStatus">0个</a>'
	+'</span>'
	+ '&nbsp;<span style="float:left;"><span id="dataStatusName" style="float:left;margin-top:3px;color:gray;font-weight:bold">当前显示-全部</span>'
	+ '&nbsp;<img src="" id="dataStatusPic" style="margin-top:4px;"></span>'
	+'<div id="netDataCountDownTimer" style="width: 200px; height: 50px;float:right;display:inline;margin-top:-15px;" title="本次监控时长(刷新频率：一分钟)"></div>'
	+ '</div><div id="searchContentNetData"></div>';
addPanel("网络数据", appendcontent);
//选择查询状态
var dataStatusCode = "";
//存储监控物的范围值
var alarmRangeNetData = {};
var alarmRangeNetDescript = {};
function getnetDataMonitorThings(deviceCodeList){
    $.ajax({
        url : "../MonitorStorageController/getAthorityMonitors",
        type : "post",
        dataType : "json",
        async:false,
		data:{"deviceCodeList":deviceCodeList},
        success : function(json) {
            for(var i=0;i<json.length;i++){
                //alarmRangeNetData[json[i].name] = {"max":json[i].max,"min":json[i].min};
                alarmRangeNetDescript[json[i].name] = json[i].describe;
            }
        }
    });
}

//定时器
if($("#netDataCountDownTimer").TimeCircles() != null){
	$("#netDataCountDownTimer").TimeCircles().destroy();
}
$("#netDataCountDownTimer").TimeCircles();
$("#netDataCountDownTimer").TimeCircles({count_past_zero: true}).addListener(countdownComplete);
function countdownComplete(unit, value, total){
	if(unit=='Seconds' && value==0){
		searchNetDataFunc();
	}
}

/*网络状态列表*/
function searchNetDataFunc() {
	var colums = [];//存储列内容
	var monitors = {};//监控物
	var datajson = [];//存储列表数据
	var stationNum = 0;	   //监控的站点总数
	var linkNum = 0;       //连接的站点树
	var unlinkNum = 0;	   //断开站点数
	var unOlineNum = 0;    //未上报站点
	var levelFlag = "";
	var station = $('#mytree').tree('getSelected');

    if(station==null){
        levelFlag = "";
    }else{
        levelFlag = station.levelFlag;
    }
	var projectId = $("#deviceProjectId").combobox('getValue');
	$.ajax({
		url : "../MonitorStorageController/getNetMonitorData",
		data: {"projectId" : projectId,"list": netDataList,"statusCode":dataStatusCode,"select":netDataSelected,"rows":netDatapageSize,"page":netDatapageNumber,"levelFlag":levelFlag},
		type : "post",
		dataType : "json",
		error : function(json) {
		},
		success : function(data) {
			var isRefresh = false;//是否更新表格
			if(data.select!=null 
					&& data.select==netDataSelected
					&& data.page ==netDatapageNumber 
					&& data.rows == netDatapageSize ){
				 isRefresh = true;
		    }
			if (data.result!= null) { // 请求成功
				colums.push( {field :'deviceCode',title : "设备编号",width : 130,halign : 'center',align : 'center'});
				colums.push( {field :'deviceMn',title : "设备Mn编号",width : 130,halign : 'center',align : 'center'});
				colums.push( {field :'deviceName',title : "站点名称",width : 130,halign : 'center',align : 'center'});
				colums.push( {field :'areaName',title : "所属区域",width : 160,halign : 'center',align : 'center'});
				colums.push( {field :'deviceStatus',title : "状态",width : 60,halign : 'center',align : 'center',
					formatter: function(value,row,index){
						var str = "";
						if(row.deviceStatus=="N"){
							str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/link.png" class="operate-button">';
						}else if(row.deviceStatus=="Z"){
							str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/unmonitor.png" class="operate-button">';
						}else if(row.deviceStatus=="NT"){
							str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/overmonitor1.png" class="operate-button">';
						}
						else if(row.deviceStatus=="O"){
							str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/unlink.png" class="operate-button">';
						}else{
							str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/othermonitor.png" class="operate-button">';
						}
						return str;
					}});
				for(var name in alarmRangeNetDescript){
					var mytitle = alarmRangeNetDescript[name];
					colums.push( {field :name,title : mytitle,width : 180,halign : 'center',align : 'center',
						formatter: function(value,row,index){
							if(value==undefined && value!=""){
								return "---";
							}else{
								return '<font color="black">' + value + '</font>';
							}
						}});
				}
				if(isRefresh){
				var mygrid = $("#searchContentNetData").datagrid({
						view:myview,
						fit : true,
						nowrap:false,
						border : false,
						pagination : true,
						fitColumns : false,
						singleSelect : true,
						pageList : [ 10, 50, 100, 300 ],
						pageSize : 10,
						autoRowHeight : false,
						rownumbers : true,
						toolbar : "#tbNetDataQuery",
						frozenColumns:[[{field :'Id',title : "详细",width : 50,halign : 'center',align : 'center',
							formatter: function(value,row,index){
								return '<a href="#" onclick="selNdRealData(' + index + ')">详细</a>';  
							}
						}]],
						columns : [ colums ],
						data : data.result
					});
					$("#stationNetDataStatus").html(data.rowTotal+"个");
					$("#linkeNetDataStatus").html(data.normalCount+"个");
					$("#overmonitorNetDataStatus").html(data.overLineCount+"个");
					$("#unlikeNetDataStatus").html(data.outLinkCount+"个");
					$("#unmonitorNetDataStatus").html(data.noCountCount+"个");
					$("#othermonitorNetDataStatus").html(data.otherCount+"个");
					$('#searchContentNetData').datagrid('getPager').pagination({
				    total:data.rowTotal,
				    pageNumber:netDatapageNumber,
				    pageSize : netDatapageSize,
				    onSelectPage: function(pageNumber, pageSize) { 
				    	mygrid.datagrid('loading');
			    	    netDatapageNumber = pageNumber;
			    	    netDatapageSize = pageSize;
			    	    getNetDataGrid(netDataList,netDataSelected,pageSize,pageNumber,levelFlag,false);
			        },
			        onChangePageSize:function(pageSize)
			        {
			        	mygrid.datagrid('loading');
			    	    $('#searchContentNetData').datagrid('getPager').pagination('refresh', {  
							total:data.rowTotal,  
							pageNumber:1,
							pageSize : pageSize
						});  
			        }  
				    });
				}
			}else{
				$('#searchContentNetData').datagrid('loadData', { total: 0, rows: [] });  
			}
		}
	});
}
/*点击时左侧树的查询*/
function searchNetDataSelected() {
	initnetDataList();
	var colums = [];   //存储列内容
	var monitors = {}; //监控物
	var datajson = []; //存储列表数据
	var treeList = [];//选择的站点
	netDataList = [];
	netDataSelected = "";
	var selected ="";
	var stationNum = 0;	   //监控的站点总数
	var linkNum = 0;       //连接的站点树
	var unlinkNum = 0;	   //断开站点数
	var unOlineNum = 0;    //未上报站点
	var levelFlag = "";
    alarmRangeNetDescript = {};//监控物质
	var deviceCodeList = [];
	var station = $('#mytree').tree('getSelected');
    if( $('#mytree').tree('getRoots').length>0){
        if (station == null || station == undefined) {
            treeList.push(-1);
            selected = "-1";
        } else {
            selected = station.id;
            treeList.push(station.id);
        }
        levelFlag = station.levelFlag;
        if(station.isDevice){
            deviceCodeList.push(station.id);
		}
    }else{
        selected = "";
        treeList = [];
        levelFlag = "";
	}
    getnetDataMonitorThings(deviceCodeList);
	netDataList = treeList;
	netDataSelected = selected;
	var opts =$("#searchContentNetData").datagrid('options');  
    var _pageNumber =opts.pageNumber;  
    var _pageSize =opts.pageSize;  
    netDatapageNumber = _pageNumber;
    netDatapageSize = _pageSize;
    dataStatusCode = "";
    $("#dataStatusName").html("");
    $("#dataStatusPic").hide();
    getNetDataGrid(treeList,selected,_pageSize,_pageNumber,levelFlag,true);
}

function getNetDataGrid(trList,selected,_pageSize,_pageNumber,levelFlag,netDataflag)
{
	var colums =[];
	var station = $('#mytree').tree('getSelected');
	var projectId = $("#deviceProjectId").combobox('getValue');
	 $.ajax({
			url : "../MonitorStorageController/getNetMonitorData",
			data: {"projectId":projectId,"list": trList,"statusCode":dataStatusCode,"select":selected,"rows":_pageSize,"page":_pageNumber,"levelFlag":levelFlag},
			type : "post",
			dataType : "json",
			async:false,
			error : function(json) {
				ajaxLoadEnd();
			},
			success : function(data) {
				var total = data.rowTotal;
				if(data!=null && data!=undefined && data.result!=null && data.result!=undefined)
			    {
					var isRefresh = false;//是否更新表格
					if(data.select!=null && data.select==selected){
						 isRefresh = true;
				    }
					if(isRefresh){
						if(netDataflag){//判断不是翻页
							$("#stationNetDataStatus").html(total+"个");
							$("#linkeNetDataStatus").html(data.normalCount+"个");
							$("#overmonitorNetDataStatus").html(data.overLineCount+"个");
							$("#unlikeNetDataStatus").html(data.outLinkCount+"个");
							$("#unmonitorNetDataStatus").html(data.noCountCount+"个");
							$("#othermonitorNetDataStatus").html(data.otherCount+"个");
							colums.push( {field :'deviceCode',title : "设备编号",width : 130,halign : 'center',align : 'center'});
							colums.push( {field :'deviceMn',title : "设备Mn编号",width : 130,halign : 'center',align : 'center'});
							colums.push( {field :'deviceName',title : "站点名称",width : 130,halign : 'center',align : 'center'});
							colums.push( {field :'areaName',title : "所属区域",width : 160,halign : 'center',align : 'center'});
							colums.push( {field :'deviceStatus',title : "状态",width : 60,halign : 'center',align : 'center',
								formatter: function(value,row,index){
									var str = "";
									if(row.deviceStatus=="N"){
										str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/link.png" class="operate-button">';
									}else if(row.deviceStatus=="Z"){
										str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/unmonitor.png" class="operate-button">';
									}else if(row.deviceStatus=="NT"){
										str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/overmonitor1.png" class="operate-button">';
									}
									else if(row.deviceStatus=="O"){
										str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/unlink.png" class="operate-button">';
									}else{
										str = '<img src="../javascript/jquery-easyui-1.4.4/themes/icons/othermonitor.png" class="operate-button">';
									}
									return str;
								}});
							for(var name in alarmRangeNetDescript){
								var mytitle = alarmRangeNetDescript[name];
								colums.push( {field :name,title : mytitle,width : 180,halign : 'center',align : 'center',
									formatter: function(value,row,index)
									{
										if(value==undefined && value!=""){
											return "---";
										}
										else
										{
											return '<font color="black">' + value + '</font>';
										}
									}
								});
							}
						var mygrid = $("#searchContentNetData").datagrid({
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
								toolbar : "#tbNetDataQuery",
								frozenColumns:[[{field :'Id',title : "详细",width : 50,halign : 'center',align : 'center',
									formatter: function(value,row,index){
										return '<a href="#" onclick="selNdRealData(' + index + ')">详细</a>';  
									}
								}]],
								columns : [colums],
								data:data.result,
								onLoadSuccess:function (){
									$('#searchContentNetData').datagrid('loaded');   
								}
							});
							
						   $('#searchContentNetData').datagrid('getPager').pagination({
								    total:total,
								    onSelectPage: function(pageNumber, pageSize) { 
								    	mygrid.datagrid('loading');
							    	    netDatapageNumber = pageNumber;
							    	    netDatapageSize = pageSize;
								    	getNetDataGrid(trList,netDataSelected,pageSize,pageNumber,levelFlag,false);
							        },
							        onChangePageSize:function(pageSize)
							        {
							        	netDataflag = false;
							        	mygrid.datagrid('loading');
							    	    $('#searchContentNetData').datagrid('getPager').pagination('refresh', {  
											total:total,  
											pageNumber:1,
											pageSize : pageSize
										});  
							        }	
						    });
						}else
						{//点击下一页按钮，跟新数据，更新当前页
							$("#searchContentNetData").datagrid("loadData",data.result);
				    	    $('#searchContentNetData').datagrid('getPager').pagination('refresh', {  
								total:total,  
								pageNumber:netDatapageNumber  
							});  
						}
						
					}
			    }
			    else
			    {
					$('#searchContentNetData').datagrid('loadData', { total: 0, rows: [] });  
				}
			}
    });
}

/*界面初始时用*/
function initnetDataList(){
    $("#searchContentNetData").datagrid({
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

function clickDataStatus(statusCode){
	switch (statusCode) {
	case 'normal':
		dataStatusCode = "N";
		$("#dataStatusName").html("列表显示");
		$("#dataStatusPic").show();
		$("#dataStatusPic").attr("src","../javascript/jquery-easyui-1.4.4/themes/icons/link.png");
		break;
	case 'over':
		dataStatusCode = "NT";
		$("#dataStatusName").html("列表显示");
		$("#dataStatusPic").show();
		$("#dataStatusPic").attr("src","../javascript/jquery-easyui-1.4.4/themes/icons/overmonitor1.png");
		break;
	case 'disconn':
		dataStatusCode = "O";
		$("#dataStatusName").html("列表显示");
		$("#dataStatusPic").show();
		$("#dataStatusPic").attr("src","../javascript/jquery-easyui-1.4.4/themes/icons/unlink.png");
		break;
	case 'none':
		dataStatusCode = "Z";
		$("#dataStatusName").html("列表显示");
		$("#dataStatusPic").show();
		$("#dataStatusPic").attr("src","../javascript/jquery-easyui-1.4.4/themes/icons/unmonitor.png");
		break;
	case 'other':
		dataStatusCode = "other";
		$("#dataStatusName").html("列表显示");
		$("#dataStatusPic").show();
		$("#dataStatusPic").attr("src","../javascript/jquery-easyui-1.4.4/themes/icons/othermonitor.png");
		break;
	default:
		dataStatusCode = "";
		$("#dataStatusName").html("");
		$("#dataStatusPic").hide();
		break;
	}
	searchNetDataFunc();
}


function selNdRealData (index) {  
    $('#searchContentNetData').datagrid('selectRow', index);  
    var data = $('#searchContentNetData').datagrid('getSelected');  
    if (data.deviceCode != null && data.deviceCode !== undefined){
	    var turlvalue = "";
		var deviceData = getDeviceInfo(data.deviceCode);
		$.ajax({
			url : "./../DeviceController/getVideoDeviceProxyUrlByMn",
			type : "post",
			dataType : "json",
			async:false,
			data : {
				"deviceMn" : data.deviceMn
			},
			success : function(json) {
				if(json.result){
					turlvalue = json.detail;
				}
			}
		});
		getNdMonitorThings();
		getNdMonitorAlarmLine(data.deviceCode);
		$("#dialogModel").dialog(
		{
			width : 680,
			height : 410,
			title : data.deviceName,
			inline : true,
			modal : true,
			iconCls : 'icon-onlineorgan',
			maximized : false,
			collapsible : false,
			minimizable : false,
			maximizable : false,
			resizable : true,
			closed : true,
			content : '<div id="queryTab" class="easyui-tabs" data-options="fit:true,tabPosition:\'bottom\'">'
					+ '<div title="概况"  selected="true" style="padding:10px;overflow:hidden" id="deviceInfo">'
					+ '<form id="frmdialogModel" class="config-form"></form>'
					+ '</div>'
				
					+ '<div title="数据"  style="padding:10px;overflow:auto" id="deviceDatas">'
					+ '<div class="easyui-layout" data-options="fit:true">'
					+ '<div data-options="region:\'north\',border:false">'
					+ '<div id="tbQueryMap" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
					+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;"'
					+ 'onclick="searchNdDeviceDataFuc(\''
					+ data.deviceCode
					+ '\')">列表</a>'
					+ '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchNdChartFunction(\''
					+ data.deviceCode
					+ '\')">图像</a>'
					+ '</div>'
					+ '</div>'
					+ '<div data-options="region:\'center\',border:false" id="centerContentNd">'
					+ '<div id="searchContentNd"  style="background:yellow;"></div>'
					+ '</div>'
					+ '</div>'
					+ '</div>'
					
					+ '<div title="实时监控"  style="padding:2px;overflow:hidden;background:#d3e0fc" id="deviceVideo">'
					+'<iframe  id="videopage" src="../../video.html" frameborder="0" height="100%"  width="100%" scrolling="no" style="top:0;left:0;" />'
					+ '</div>'
					
					+ '</div>'
		}).dialog('center');
		/* 初始化表单 */
		$("#frmdialogModel").html(function() {
			var htmlArr = [];
			htmlArr.push(createValidatebox({
				name : "areaName",
				title : "所属区域",
				readonly : true
			}));
			htmlArr.push(createValidatebox({
				name : "deviceKm",
				title : "面积",
				readonly : true
			}));
			htmlArr.push(createValidatebox({
				name : "buildFirm",
				title : "施工单位",
				readonly : true
			}));
			htmlArr.push(createValidatebox({
				name : "userName",
				title : "现场负责人",
				readonly : true
			}));
			htmlArr.push(createValidatebox({
				name : "userTel",
				title : "负责人电话",
				readonly : true
			}));
			htmlArr.push(createValidatebox({
				name : "userRemark",
				title : "负责人备注",
				readonly : true
			}));
			htmlArr.push(createValidatebox({
				name : "orgName",
				title : "监督单位",
				readonly : true
			}));
			htmlArr.push(createValidatebox({
				name : "orgLiaison",
				title : "监督人",
				readonly : true
			}));
			return htmlArr.join("");
		});
		var tab_option = $('#queryTab').tabs('getTab',"实时监控").panel('options').tab;  
		
		/* 重绘窗口 */
		$.parser.parse("#dialogModel");
		$("#frmdialogModel").form("reset");
		$("#frmdialogModel").form("load", deviceData.rows[0]);
		searchNdDeviceDataFuc(data.deviceCode);// 查询数据
		
		if(turlvalue!=""){
			tab_option.show();  
		}else{
			tab_option.hide();
		}
		$("#videopage")[0].contentWindow.hellobaby=turlvalue;//项video.html传递url
		
		$("#dialogModel").dialog("open");
    }
}

/* 查询设备24小时内的监控数据 */
function searchNdDeviceDataFuc(deviceCode) {
	var colums = [];// 存储列内容
	var monitors = {};// 监控物
	var datajson = [];// 存储列表数据
	ajaxLoading();
	$.ajax({
		url : "../MonitorStorageController/getTimelyMonitorData",
		data : {
			"devicecode" : deviceCode,
			"isrepeat" : false
		},
		async : true,
		type : "post",
		dataType : "json",
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(data) {
			ajaxLoadEnd();
			var isRefresh = false;// 是否更新表格
			if (data.select == deviceCode) {
				isRefresh = true;
			}
			if (data.result != null) { // 请求成功
				isrepeatFlag = true;
				for ( var key in data.result) {
					var list = data.result[key];
					for ( var name in list) {// 获取监控物集合
						if (monitors[name] == undefined) {
							monitors[name] = name;
						}
					}
					list["time"] = key;
					datajson.push(list);
				}
				colums.push({
					field : 'time',
					title : '时间',
					width : 200,
					halign : 'center',
					align : 'center'
				});
     			for(var name in monitors){
     				var title = name;
     				colums.push( {field :name,title : title,width : 130,halign : 'center',align : 'center',
     					formatter: function(value, row, index){
     						if(value!=undefined){
     							var zsFlag = false;
                                var zvalue = "---";
                                return tableShowHandler(value, this.field, zsFlag, zvalue,alarmMapRange);
     						}else{
     							return "---";
     						}
     					}
 					});
     			}
				if (isRefresh) {
					$("#searchContentNd").datagrid({
						view : myview,
						fit : true,
						border : false,
						pagination : false,
						fitColumns : true,
						singleSelect : true,
						pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
						pageSize : 10,
						autoRowHeight : false,
						rownumbers : true,
						columns : [ colums ],
						data : datajson
					});
				}
			}
		}
	});
}

/* 图表 */
function searchNdChartFunction(deviceCode) {
	var datajson = [];
	var centerContentNd = $("#centerContentNd");
	centerContentNd.html("");
	$("#centerContentNd").append('<div id="searchContentNd" style="height:400px;"></div>');
	var yname = "";// 图表y轴名称
	var units = "";// 监控物单位
	var timelist = {};
	var legendData = [];
	var seriesData = [];
	ajaxLoading();
	$.ajax({
		url : "../MonitorStorageController/getTimelyMonitorChartData",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			"devicecode" : deviceCode
		},
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			if (json.time != undefined) {
				var timeArry = json["time"];
				var max = null;
				for ( var index in json) {
					if (index != "time") {
						legendData.push(index);
						if (alarmMapRange[index] != undefined) {
							max = alarmMapRange[index].max;
						}
						seriesData.push({
							"name" : index,
							"type" : 'line',
							"data" : json[index],
							markPoint : {
								data : [ {
									type : 'max',
									name : '最大值'
								}, {
									type : 'min',
									name : '最小值'
								} ]
							},
							 markLine: {
								 itemStyle: {
						                normal: {lineStyle : {width : 2}}
						          },
				                 data: [
				                    [
				                        {name: '报警线', value:max, xAxis: timeArry[0], yAxis: max},      
				                        {name: '报警线', xAxis: timeArry[timeArry.length-1], yAxis:max},
				                    ]
				                ]
				            }
						});
					} else {
						timelist[index] = json[index];
					}
				}
				initNdChart(timelist, legendData, seriesData, yname);
			} else {
				$.messager.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
			}
		}
	});
}
/* 初始化表格 */
function initNdChart(timelist, legendData, seriesData, yname) {
	var dom = document.getElementById("searchContentNd");
	dom.style.cssText = "height:100%";
	var myChart = echarts.init(document.getElementById("searchContentNd"));
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
	myChart.setOption(option);
}


/*获取监控物*/
function getNdMonitorThings(){
	$.ajax({
		url : "../MonitorStorageController/getAthorityMonitors",
		type : "post",
		dataType : "json",
		async:false,
		success : function(json) {
			for(var i=0;i<json.length;i++){
				monitorsThingJson[json[i].name] = json[i].describe;
				monitorslist.push(json[i].code);
			}
		}
	});
	if(monitorslist.length<=0){
		monitorslist.push(-1);
	}else{
		monitorslist = 	monitorslist;
	}
}

/*获取报警线*/
function getNdMonitorAlarmLine(deviceCode){
	var stationlist = [] ;
	var selectedStation = $('#mytree').tree('getSelected');
	if(selectedStation!=null && selectedStation!=undefined){
		stationlist.push(deviceCode);
	}else{
		stationlist.push(-1);
	}
	if(monitorslist!=undefined){
		$.ajax({
			url : "../DeviceAlarmSetController/getDeviceAlarmLineThgName",
			type : "post",
			dataType : "json",
			async:false,
			data:{"listdev":stationlist,"listthg":monitorslist},
			success : function(json) {
				if(json){
					alarmMapRange = json[deviceCode];
				}
			}
		});
	}
}