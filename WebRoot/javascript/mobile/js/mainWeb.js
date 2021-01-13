/**
 * 实时在线率排名
 * @param mapTreeList
 * @param levelFlag
 */
function queryGenaralMonitorValueRankingbs(){
    var thingCode = $("#thingMonitor").val();
    var dataType = '2011';//默认实时数据
    var order = "desc";
    var xAxisData = [];
    var yAxisData = [];
    var orderDataArry = [];
    if(chartSortLeft!=null){
        echarts.dispose(document.getElementById("chartSortLeft"));
    }
    ajaxLoadingBigScreen("chartSortLeft");
    $.ajax({
        url : "../../GeneralMonitorController/getGenaralMonitorValueRanking",
        type : "post",
        dataType : "json",
        data : {
            projectId: "",
            list: [-1],
            thingCode: thingCode,
            dataType: dataType,
            order: 'desc',
            limit: 20
        },
        error : function(json) {
            ajaxLoadEndBigScreen();
        },
        success : function(json) {
            ajaxLoadEndBigScreen();
            if(json.result){
                if(json.rows != null && json.rows.length>0){
                    for(var i=0;i<json.rows.length;i++){
                        var xData = json.rows[i]["thingValue"];
                        var yData = json.rows[i]["deviceName"];
                        xAxisData.push(xData);
                        yAxisData.push(yData);
                        orderDataArry.push( json.rows[i]["dataOrder"]);
                    }
                }
                initLeftCahrt(yAxisData,xAxisData,orderDataArry);
            }
        }
    });
}

function initLeftCahrt(yAxisData,xAxisData,orderDataArry){
    echarts.dispose(document.getElementById("chartSortLeft"));
    chartSortLeft = echarts.init(document.getElementById("chartSortLeft"));
    var max = Math.max.apply(null,xAxisData);
    var tempData = [];
    for(var i=0;i<yAxisData.length;i++){
        tempData.push({"name":"","value":max,"dataOrder":orderDataArry[i]});
    }
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },formatter: function (params) {//提示框自定义
                return formatterTip(params);
            },
        },
        grid:{
            left:'6%',
            right:'6%'
        },
        xAxis: {
            type: 'value',
            show:false,
            splitLine:{
                show:false
            }
        },
        yAxis: {
            type: 'category',
            data: yAxisData,
            inverse: true,
            show:false,
            splitLine:{
                show:false
            }
        },
        series: [
            {
                type: 'bar',
                itemStyle: {
                    normal: {color: 'rgba(0,0,0,0.3)'}
                },
                barGap:'-100%',
                barCategoryGap:'60%',
                data: tempData,
                animation: false,
                label: {
                    normal: {
                        show: true,
                        position:[1,-14],
                        textStyle: { //数值样式
                            color: '#fff',
                            fontSize:14
                        },
                        formatter: function(params) {
                            var valueTxt = '';
                            if (params.name.length > 14) {
                                valueTxt = params.name.substring(0, 14) + '...';
                            }
                            else {
                                valueTxt = params.name;
                            }
                            var index = params.data.dataOrder;

                            return "NO." +index+" " + valueTxt ;
                        }
                    }
                },
            },
            {
                name: '实时排名',
                type: 'bar',
                barGap:'-100%',
                barCategoryGap:'90%',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0,1, 0,
                            [
                                {offset: 0, color: '#2a82cf'},
                                {offset: 0.5, color: '#15c1cd'},
                                {offset: 1, color: '#01facc'}
                            ]
                        )
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2378f7'},
                                {offset: 0.7, color: '#2378f7'},
                                {offset: 1, color: '#83bff6'}
                            ]
                        )
                    }
                },
                data: xAxisData
            }
        ]
    };
    chartSortLeft.setOption(option,true);
}

/**
 * 柱状图tootiple自定义
 * @param params
 * @returns {string}
 */
function formatterTip(params) {
    //移除重复的数据
    for (var i = 0; i < params.length; i++) {
        for (var j = params.length - 1; j > i; j--) {
            if (params[j].data == params[i].data) {
                params.splice(j, 1);
                break;
            }
        }
    }
    var tip = '';
    for (var i = 0; i < params.length; i++) {//这里是自己定义样式， params[i].marker 表示是否显示左边的那个小圆圈
        if ( params[i].seriesName=="实时排名") {
            var icon  =" <div style='background: #73aae5;height: 8px;width: 8px;border-radius: 8px;margin-top:7px;margin-right: 4px;float: left;display: inline-block'></div>";
            tip = tip + icon +  params[i].name + ' : ' + params[i].value + '<br/>';
        }
    }
    return tip;
}

function queryGenaralMonitorCount(){
    if(myChart1!=null){
        echarts.dispose(document.getElementById("myChart1"));
    }
    $.ajax({
        url : "../../../GeneralMonitorController/getGenaralMonitorCount",
        type : "post",
        dataType : "json",
        data : {
            "projectId":"",
            "list" : [-1],
            "levelFlag" : ""
        },
        error : function(json) {
        },
        success : function(json) {
            if(json.result){
                initChartMonitorStatusCount1(json);
            }
        }
    });
}

function initChartMonitorStatusCount1(seriesData){
    echarts.dispose(document.getElementById("myChart1"));
    myChart1 = echarts.init(document.getElementById("myChart1"));
    var option = {
        title : {
            text: '监控站点在线率',
            subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        series : [
            {
                name: '监控站点在线率',
                type: 'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:[
                    {value:seriesData.onlineCount, name:'在线'},
                    {value:seriesData.offlineCount, name:'离线'}

                ],
                label: {
                    normal: {
                        fontSize:14
                    }
                },
                itemStyle: {
                    //通常情况下：
                    normal: {
                        color: function (params) {
                            var colorList = ['#00cbef', '#234591', '#f29e3c', '#c05bdd', '#7a65f2'];
                            return colorList[params.dataIndex];
                        }
                    },
                    //鼠标悬停时：
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart1.setOption(option,true);
}