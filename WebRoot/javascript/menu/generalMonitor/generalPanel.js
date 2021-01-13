/************************************
 * 功能：实时统计
 * 日期：2016-3-24 09:43:09
 ************************************/
var appendRealPanelcontent = '<div class="easyui-layout" data-options="fit:true">'
	+'<div data-options="region:\'center\',border:false,fit:true" id="centerContentRealChart">'
		+'<div class="flex-container1">'
			+'<div id="tbgeneralPanel" style="padding:8px 8px;">'
				+'选择区域：<a id="selectGenTree"  style="width:180px;margin:8px" href="#" >暂无</a>'
				+'&nbsp;&nbsp;<a id="reallist"  class="easyui-linkbutton" data-options="iconCls:\'icon-listtable\',plain:true" style="margin:0px 10px;" onclick="initCharts()" href="#" >刷新</a>'
				+'</div>'
			+'</div>'
		+'<div>'
		+'<div class="flex-container" style="flex-wrap:wrap;justify-content:space-around">'
			+'<div id="box1" class="flex-item">'
				+'<div class="flex-itemfilter">连接状态'
//					+'<a id="realchart"  class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="queryGenaralMonitorCount()" href="#" >刷新</a>'
				+'</div>'
				+'<div id="chart_msc" class="flex-itemcontent" style="width:100%;"></div>'
			+'</div>'
			+'<div id="box2" class="flex-item" >'
				+'<div class="flex-itemfilter">站点状态</div>'
        		+'<div id="chart_msc2" class="flex-itemcontent" style="width:100%;"></div>'
        	+'</div>'
        	+'<div id="box3" class="flex-item">'
				+'<div class="flex-itemfilter">站点状态</div>'
        		+'<div id="chart_msc3" class="flex-itemcontent" style="width:100%;"></div>'
        	+'</div>'
			+'<div id="box4" class="flex-item">'
				+'<div class="flex-itemfilter">实时排名'
				+'<div style="float:right;"><input id="genMonitorThings" class="easyui-combobox" style="width:120px;">'
//				+ '&nbsp;&nbsp;&nbsp;<input class="easyui-combobox" id="genCnCodeCombox" style="width:80px;"/>'
				+ '&nbsp;&nbsp;&nbsp;<input class="easyui-combobox" id="genOrderCombox" style="width:80px;"/>'
				+ '&nbsp;&nbsp;&nbsp;<input class="easyui-combobox" id="genLimitCombox" style="width:50px;"/></div>'
//					+'<a id="realchart"  class="easyui-linkbutton" data-options="iconCls:\'icon-chart\',plain:true" style="margin:0px 10px;" onclick="queryGenaralMonitorValueRanking()" href="#" >刷新</a>'
				+'</div>'
				+'<div id="chart_mvr" class="flex-itemcontent" style="width:100%;"></div>'
			+'</div>'
		+'</div>'
	+'</div>'
	+'</div>'
addPanel("实时统计", appendRealPanelcontent);
var orderArrayGen = [ {
	"id" : "desc",
	"name" : "最高值"
}, {
	"id" : "asc",
	"name" : "最低值"
} ];
var limitArrayGen = [ {
	"id" : "10",
	"name" : "10"
}, {
	"id" : "20",
	"name" : "20"
},{
	"id" : "30",
	"name" : "30"
} ];
var comboboxJsonGen = [];
//监控物
$.ajax({
	url : "../MonitorStorageController/getAthorityMonitors",
	type : "post",
	dataType : "json",
	async:false,
	success : function(json) {
		comboboxJsonGen = json;
	}
});

initComboxs();
initCharts();

function initComboxs(){
	$("#genMonitorThings").combobox({
		  data:comboboxJsonGen,
		  method:'post',
		  valueField:'code',
		  textField:'describe',
		  panelHeight:'auto',
		  value:comboboxJsonGen[0].code,
		  onShowPanel: function () {
				// 动态调整高度  
				if (comboboxJsonGen.length < 20) {
					$(this).combobox('panel').height("auto");  
				}else{
					$(this).combobox('panel').height(300);
				}
			}
	});
	/*
	var cnCodeValueGen = "2061";
	$("#genCnCodeCombox").combobox({
		data : ornCnCode,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value : cnCodeValueGen
	});*/
	$("#genOrderCombox").combobox({
		data : orderArrayGen,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value:orderArrayGen[0].id
	});
	$("#genLimitCombox").combobox({
		data : limitArrayGen,
		method : 'post',
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		value:limitArrayGen[0].id
	});
}

function initCharts(){
	try
	{
		var station = $('#mytree').tree('getSelected');
		var levelFlag;
		var mapTreeList = [];
		if (station == null || station == undefined) {
			mapTreeList.push(-1);
		} else {
			levelFlag = station.levelFlag;
			mapTreeList.push(station.id);
			if (mapTreeList.length == 0) {
				mapTreeList.push("0");
			}
		}
		if(station != null && station.text != null && station.text != "" ){
			$("#selectGenTree").text(station.text);
		}else{
			$("#selectGenTree").text("暂无");
		}
		ajaxLoading();
		queryGenaralMonitorCount(mapTreeList,levelFlag);
		queryGenaralMonitorValueRanking(mapTreeList,levelFlag);
		ajaxLoadEnd();
	}
	catch(err){
		ajaxLoadEnd();
	}
}

function queryGenaralMonitorCount(mapTreeList,levelFlag){
	$.ajax({
		url : "../GeneralMonitorController/getGenaralMonitorCount",
		type : "post",
		dataType : "json",
		data : {
			"projectId":$("#deviceProjectId").combobox('getValue'),
			"list" : mapTreeList,
			"levelFlag" : levelFlag
		},
		error : function(json) {
		},
		success : function(json) {
			ajaxLoadEnd();
			if(json.result){
				initChartMonitorStatusCount1(json);
				initChartMonitorStatusCount2(json);
				initChartMonitorStatusCount3(json);
			}
		}
	});
}
var myChart1 = null;
var myChart2 = null;
var myChart3 = null;
var myChart4 = null;
function initChartMonitorStatusCount1(seriesData){
	echarts.dispose(document.getElementById("chart_msc"));
	myChart1 = echarts.init(document.getElementById("chart_msc"));
	var option = {
		 /*title: {
		     text: '连接状态',
		     left: 'center'
		 },*/
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'left',
	        data:['在线','离线']
	    },
	    series: [
	        {
	            name:'在线统计',
	            type:'pie',
	            radius: [0, '55%'],
	             label: {
	                normal: {
	                    //formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c} {per|{d}%}',
                        formatter: [
                            '{title|{b}}{abg|}',
                            '  {valueHead|数值}{rateHead|占比}',
                            '{hr|}',
                            '  {value|{c}}{rate|{d}%}'
                        ].join('\n'),
                        backgroundColor: '#eee',
                        borderColor: '#fff',
                        shadowColor: 'rgba(0, 0, 0, 0.4)',
                        shadowBlur: 10,
                        borderWidth: 1,
                        borderRadius: 4,
	                    rich: {
                            title: {
                                color: '#eee',
                                align: 'center'
                            },
                            abg: {
                                backgroundColor: '#4169e1',
                                width: '100%',
                                align: 'right',
                                height: 25,
                                borderRadius: [4, 4, 0, 0]
                            },
                            valueHead: {
                                color: '#333',
                                height: 24,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 10]

                            },
                            value: {
                                height: 30,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 0]
                            },
                            rateHead: {
                                color: '#333',
                                height:24,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 10]
                            },
                            rate: {
                                height: 30,
                                width: 30,
                                align: 'center',
                                padding: [0, 10, 0, 10]
                            },
                            hr: {
                                borderColor: '#fff',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            }
	                    }
	                }
	            },
	            data:[
	                {value:seriesData.onlineCount, name:'在线', itemStyle:{
	                	color : '#6edc8d'
						}},
	                {value:seriesData.offlineCount, name:'离线', itemStyle:{
	                	color : '#8d8d8d'
	                }}
	            ]
	        }
	    ]
	};
    myChart1.setOption(option,true);
}
function initChartMonitorStatusCount2(seriesData){
	echarts.dispose(document.getElementById("chart_msc2"));
	myChart2 = echarts.init(document.getElementById("chart_msc2"));
	var option = {
		/* title: {
		     text: '站点状态',
		     left: 'center'
		 },*/
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'left',
	        data:['正常','超标']
	    },
	    series: [
	        {
	            name:'站点状态',
	            type:'pie',
	            radius: [0, '55%'],
	            label: {
	            	align: 'left',
	                normal: {
                        formatter: [
                            '{title|{b}}{abg|}',
                            '  {valueHead|数值}{rateHead|占比}',
                            '{hr|}',
                            '  {value|{c}}{rate|{d}%}'
                        ].join('\n'),
                        backgroundColor: '#eee',
                        borderColor: '#fff',
                        shadowColor: 'rgba(0, 0, 0, 0.4)',
                        shadowBlur: 10,
                        borderWidth: 1,
                        borderRadius: 4,
                        rich: {
                            title: {
                                color: '#eee',
                                align: 'center'
                            },
                            abg: {
                                backgroundColor: '#4169e1',
                                width: '100%',
                                align: 'right',
                                height: 25,
                                borderRadius: [4, 4, 0, 0]
                            },
                            valueHead: {
                                color: '#333',
                                height: 24,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 10]

                            },
                            value: {
                                height: 30,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 0]
                            },
                            rateHead: {
                                color: '#333',
                                height:24,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 10]
                            },
                            rate: {
                                height: 30,
                                width: 30,
                                align: 'center',
                                padding: [0, 10, 0, 10]
                            },
                            hr: {
                                borderColor: '#fff',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            }
	                    }
	                }
	            },
	            data:[
              		  {value:seriesData.normalCount, name:'正常', itemStyle:{
              		  	color : '#6edc8d'
              		  }},
	                  {value:seriesData.overCount, name:'超标',itemStyle:{color : 'red'}}
                  ]
	        }
	    ]
	};
	myChart2.setOption(option,true);
}
function initChartMonitorStatusCount3(seriesData){
	echarts.dispose(document.getElementById("chart_msc3"));
	myChart3 = echarts.init(document.getElementById("chart_msc3"));
	var option = {
		/* title: {
		     text: '站点状态',
		     left: 'center'
		 },*/
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'left',
	        data:['无数据','断开连接','其他']
	    },
	    series: [
	        {
	            name:'站点状态',
	            type:'pie',
	            radius: [0,'55%'],
	            label: {
	                normal: {
                        formatter: [
                            '{title|{b}}{abg|}',
                            '  {valueHead|数值}{rateHead|占比}',
                            '{hr|}',
                            '  {value|{c}}{rate|{d}%}'
                        ].join('\n'),
                        backgroundColor: '#eee',
                        borderColor: '#fff',
                        shadowColor: 'rgba(0, 0, 0, 0.4)',
                        shadowBlur: 10,
                        borderWidth: 1,
                        borderRadius: 4,
                        rich: {
                            title: {
                                color: '#eee',
                                align: 'center'
                            },
                            abg: {
                                backgroundColor: '#4169e1',
                                width: '100%',
                                align: 'right',
                                height: 25,
                                borderRadius: [4, 4, 0, 0]
                            },
                            valueHead: {
                                color: '#333',
                                height: 24,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 10]

                            },
                            value: {
                                height: 30,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 0]
                            },
                            rateHead: {
                                color: '#333',
                                height:24,
                                width: 30,
                                align: 'center',
                                padding: [0, 20, 0, 10]
                            },
                            rate: {
                                height: 30,
                                width: 30,
                                align: 'center',
                                padding: [0, 10, 0, 10]
                            },
                            hr: {
                                borderColor: '#fff',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            }
	                    }
	                }
	            },
	            data:[
	                  {value:seriesData.nodataCount, name:'无数据', itemStyle:{
	                  	color : '#A9A9A9'
						  }},
	                  {value:seriesData.disconnectCount, name:'断开连接', itemStyle:{
	                  	color : '#696969'
						  }},
	                  {value:seriesData.otherCount, name:'其他', itemStyle:{
	                  	color : '#808080'
						  }}
                  ]
	        }
	    ]
	};
	myChart3.setOption(option,true);
}


function queryGenaralMonitorValueRanking(mapTreeList,levelFlag){
	var thingCode = $("#genMonitorThings").combobox('getValue');
	var thingName = $("#genMonitorThings").combobox('getText');
	//var dataType = $("#genCnCodeCombox").combobox('getValue');
	var dataType = '2011';//默认实时数据
	var order = $("#genOrderCombox").combobox('getValue');
	var limit = $("#genLimitCombox").combobox('getValue');
	var legendData = [];
	var xAxisData = [];
	var yAxisData = [];
	var seriesData = [];
	$.ajax({
		url : "../GeneralMonitorController/getGenaralMonitorValueRanking",
		type : "post",
		dataType : "json",
		data : {
            "projectId":$("#deviceProjectId").combobox('getValue'),
			"list" : mapTreeList,
			"thingCode" : thingCode,
			"dataType" : dataType,
			"order" : order,
			"limit" : limit,
			"levelFlag" : levelFlag
		},
		error : function(json) {
		},
		success : function(json) {
			if(json.result){
				legendData.push(thingName);
				if(json.rows != null && json.rows.length>0){
					for(var i=0;i<json.rows.length;i++){
						var xData = json.rows[i]["thingValue"];
						var yData = json.rows[i]["deviceName"];
						xAxisData.push(xData);
						yAxisData.push(yData);
					}
				}
				seriesData.push({
		            "name":thingName,
		            "type":'bar',
		            "data":xAxisData,
					"itemStyle":{color:'#4169E1'}
		        });
				initChartMonitorValueRanking(legendData,yAxisData,seriesData);
			}
		}
	});
}

function initChartMonitorValueRanking(legendData,yAxisData,seriesData){
	echarts.dispose(document.getElementById("chart_mvr"));
	myChart4 = echarts.init(document.getElementById("chart_mvr"));
	var option = {
	    title: {
	        text: '',
	        subtext: '在线设备统计',
		    left: 'left'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	        data: legendData
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01]
	    },
	    yAxis: {
	        type: 'category',
	        data: yAxisData
	    },
	    series: seriesData
	};
	myChart4.setOption(option,true);
}

window.onresize=function() {
    myChart1.resize();
    myChart2.resize();
    myChart3.resize();
    if(myChart4!=null){
        myChart4.resize();
    }
};