/************************************
 * 功能：实时数据
 * 日期：2016-3-24 09:43:09
 ************************************/
var appendRealcontent = '<div class="easyui-layout" data-options="fit:true">'
	+'<div data-options="region:\'north\',border:false">'
	+'<div id="tbRealTimeTab" style="padding:5px 8px;border-bottom:1px solid #ddd;height:40px;">'
	+'监控区域：<a id="realMonitorCompany"  style="width:180px;margin:8px" href="#" >暂无</a>'
	+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监控站点：<a id="realMonitorStation"  style="width:180px;margin:8px" href="#" >暂无</a>'
    +'<span style="margin-left:8px;"><input type="checkbox"  id="realzVauleChekbox" style="vertical-align:middle; margin-top:0;"/><span style="vertical-align:middle; margin-top:0;" class="vauleCheckTitle"></span></span>'
	+'&nbsp;&nbsp;<a id="reallist"  class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="searchRealBtnFunction()" href="#" >列表</a>'
	+'<a id="realchart"  class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="searchRealChartBtnFunction()" href="#" >图表</a>'
	+'<label style="margin-left:10px;color:red;font-size:12px;" id="infowarn">请选择一个监控站点</label>'
	+'<div id="realTimeCountDownTimer" style="width: 200px; height: 48px;float:right;margin-top:-5px;" title="本次监控时长(刷新频率：一分钟)"></div>'
	+'</div>'
	+'</div>'
	+'<div data-options="region:\'center\',border:false" id="centerContentRealTime">'
	+'<div id="realTimeQuery"></div>'
	+'</div>'
	+'</div>'
addPanel("实时数据", appendRealcontent);
setCheckTitle();
//是否从头加载
var isrepeatFlag = false;
//判断查看的是图表
var isChart = false;
//定时器
if($("#realTimeCountDownTimer").TimeCircles() != null){
	$("#realTimeCountDownTimer").TimeCircles().destroy();
}
$("#realTimeCountDownTimer").TimeCircles();
$("#realTimeCountDownTimer").TimeCircles({count_past_zero: true}).addListener(countdownComplete);
function countdownComplete(unit, value, total){
	if(unit=='Seconds' && value==0){
		if(isChart){
			searchRealChartBtnFunction();
		}else{
			searchRealBtnFunction();
		}
	}
}

/*获取监控物*/
function getMonitorThings(){
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
function getMonitorAlarmLine(){
	var stationlist = [] ;
	var selectedStation = $('#mytree').tree('getSelected');
	preStation = selectedStation.id;
	if(selectedStation!=null && selectedStation!=undefined){
		stationlist.push(selectedStation.id);
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
					alarmRange = json[selectedStation.id];
				}
			}
		});
	}
}

/* 点击时左侧树的查询 */
function searchRealtimeFunctionSelected() {
	if(initMonitorsflag){
		getMonitorThings();
		initMonitorsflag = false;
	}
	initList();
	var colums = [];//存储列内容
	var monitors ={};//监控物
	var datajson = [];//存储列表数据
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		$.messager.alert("提示", "请选择一个站点进行查询！", "error");
		return false;
	}
	if(station==null || station==undefined){
		realtreeid = -1;
	}else{
		realtreeid = station.id
	}
	if(preStation!=station.id || preStation == null){
		getMonitorAlarmLine();
	}
	var zsFlag = false;
    if($('#realzVauleChekbox').is(':checked')){
        zsFlag = true;
	}
	 $.ajax({
         url: "../MonitorStorageController/getTimelyMonitorData",
         data: {"devicecode": realtreeid,"isrepeat": false,"zsFlag":zsFlag},
         type : "post",
         dataType: "json",
         success: function (data) {
        	 var isRefresh = false;//是否更新表格
        	 if(data.select==station.id){
        		 isRefresh = true;
        	 }
             if (data.result!= null) {    // 请求成功
            	 isrepeatFlag = true;
     			for(var key in data.result){
 					var list = data.result[key];
 					for(var name in list){//获取监控物集合
 						if(monitors[name]==undefined){
 							monitors[name]=name;
 						}
 					}
 					list["time"] = key;
 					//风向显示备注
 					if(list["风向"] != undefined && list["风向"] != null){
 						if(list["风向"] == 0){
 	 						list["风向"] += "-正北风";
 						}else if(list["风向"] == 90){
 							list["风向"] += "-正东风";
 						}else if(list["风向"] == 180){
 							list["风向"] += "-正南风";
 						}else if(list["风向"] == 270){
 							list["风向"] += "-正西风";
 						}else if(list["风向"]>0 && list["风向"]<90){
 							list["风向"] += "-东北风";
 						}else if(list["风向"]>90 && list["风向"]<180){
 							list["风向"] += "-东南风";
 						}else if(list["风向"]>180 && list["风向"]<270){
 							list["风向"] += "-西南风";
 						}else if(list["风向"]>270 && list["风向"]<360){
 							list["风向"] += "-西北风";
 						}else{
 							list["风向"] += "-未知风向";
 						}
 					}
 					datajson.push(list);
     			}
     			colums.push( {field : 'time',title : '时间',width : 130,halign : 'center',align : 'center'});
     			for(var name in monitors){
     				var title = monitorsThingJson[name];
     			/*	if(alarmRange[name]!=undefined && monitorsThingJson[name]!=undefined && alarmRange[name].max!=undefined){
						title = title + "(报警上线"+alarmRange[name].max+")";
					}*/
					if(name.indexOf("zs")==-1){
                        colums.push( {field :name,title : title,width : 130,halign : 'center',align : 'center',
                            formatter: function(value, row, index){
                                var zvalue = (row[(this.field + "-zs")] == null) ? "---" : (row[(this.field + "-zs")]);
                                return tableShowHandler(value, this.field, zsFlag, zvalue,alarmRange);
                            }
                        });
					}

     			}
     			if(isRefresh){
     				$("#realTimeQuery").datagrid({
     					view:myview,
     					fit : true,
     					border : false,
     					pagination : false,
     					fitColumns : true,
     					singleSelect : true,
     					pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
     					pageSize : 10,
     					autoRowHeight : false,
     					rownumbers : true,
     					columns : [colums],
     					data: datajson
     				})
     			}else{
     				 $('#realTimeQuery').datagrid('loadData', { total: 0, rows: [] });  
     			}
             }else{
            	 $('#realTimeQuery').datagrid('loadData', { total: 0, rows: [] });  
             }
         }
     });
}

/*查询*/
function searchRealBtnFunction(){
	isChart = false;
	stopajax = false;
	var dom = document.getElementById("realTimeQuery");//清空图标内容的处理，暂时这样处理
	dom.innerHTML = "";
	var station = $('#mytree').tree('getSelected');
	if (station != null && station.isDevice) {
		var parent = $('#mytree').tree('getParent',station.target);
		if(parent!=null && parent!=undefined){
			$("#realMonitorCompany").text(parent.text);
			$("#realMonitorStation").text(station.text);
			if(myChart!=null){//清空图标内容的处理，暂时这样处理
				myChart.clear();
			}
			$("#infowarn").html("");
			searchRealtimeFunctionSelected();
		}else{
			$("#infowarn").html("请选择一个监控站点");
		}
	}else{
		$("#infowarn").html("请选择一个监控站点");
	}
}

/*图表*/
function searchRealChartBtnFunction(){
	if(initMonitorsflag){
		getMonitorThings();
		initMonitorsflag = false;
	}
	stopajax = true;
	isChart = true;
	var datajson = [];
	var station = $('#mytree').tree('getSelected');
	if(station==null || station==undefined){
		realtreeid = -1;
		$("#infowarn").html("请选择一个监控站点");
		return false;
	}else{
		if(station.isDevice){
			$("#infowarn").html("");
			realtreeid = station.id;
		}else{
			$("#infowarn").html("请选择一个监控站点");
			return false;
		}
	}
	var parent=$('#mytree').tree('getParent',station.target);
	$("#realMonitorCompany").text(parent.text);
	$("#realMonitorStation").text(station.text);
	var Centercontent = $("#centerContentRealTime");//清空centerContentRealTime的数据
	Centercontent.html("");
	$("#centerContentRealTime").append('<div id="realTimeQuery"></div>');
	var yname = "";//图表y轴名称
	var units = "";//监控物单位
	var timelist = {};
	var legendData = [];
	var seriesData = [];
	if(preStation!=station.id){
		getMonitorAlarmLine();
	}
    var zsFlag = false;
    if($('#realzVauleChekbox').is(':checked')){
        zsFlag = true;
    }
	ajaxLoading();
	$.ajax({
		url : "../MonitorStorageController/getTimelyMonitorChartData",
		type : "post",
		dataType : "json",
		data : {
			"devicecode": realtreeid,
			"isrepeat": false,
			"zsFlag":zsFlag
		},
		error : function(json) {
			ajaxLoadEnd();
		},
		success : function(json) {
			ajaxLoadEnd();
			var max = 0;
			var timeArry = json["time"];
			var selectedNum = 0;
			var selectedlegendData = {};
			if(json.time!=undefined){
				for(var index in json){
					if(index!="time"){
						legendData.push(index);
						if(alarmRange[index]!=undefined){
							max = getMax(alarmRange[index]);
						}
						seriesData.push({
							"name":index,
							"type":'line',
							"data":json[index],
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
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
						if(selectedNum<4){
							selectedlegendData[index] = true;
						}else{
                            selectedlegendData[index] = false;
						}
                        selectedNum++;

					}else{
						timelist[index] = json[index];
					}
				}
                console.info(selectedlegendData);
				initChartReal(timelist,legendData,selectedlegendData,seriesData,yname);
			}else{
				$.messager.alert("提示", "当前时间段内无数据，没有可查看的图表！", "warning");
			}
		}
	});
}
var myChart = null;
/*初始化表格*/
function initChartReal(timelist,legendData,selectedlegendData,seriesData,yname){
	var dom = document.getElementById("realTimeQuery");
	dom.style.cssText = "height:100%";
	myChart = echarts.init(document.getElementById("realTimeQuery"));
	var option = {
		    title: {
		        text: '图表',
	        	textStyle: {
	                  fontSize: 18,
	                  fontWeight: 'bolder',
	                  color: '#333'          // 主标题文字颜色
	            }
		    },
			grid:{
                left:'2%',
                right:'16%',
                bottom:'1%',
                containLabel:true
			},
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
                type: 'scroll',
                orient: 'vertical',
                right: "1%",
                "top":'8%',
		        data:legendData,
                selected:selectedlegendData
		    },
		    toolbox: {
		        show : true,
                orient:	'horizontal',
                x:'center',
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


/*界面初始时用*/
function initList(){
    $("#realTimeQuery").datagrid({
     view:myview,
   	 fit : true,
   	 border : false,
   	 pagination : false,
   	 fitColumns : true,
   	 singleSelect : true,
   	 pageList : [ 10, 50, 100, 150, 200, 250, 300 ],
   	 pageSize : 10,
   	 autoRowHeight : false,
   	 rownumbers : false,
   	 columns : [],
   	 onLoadSuccess:function (){
			$("#realTimeQuery").datagrid("loaded");
	 }
    }).datagrid('loading');
}