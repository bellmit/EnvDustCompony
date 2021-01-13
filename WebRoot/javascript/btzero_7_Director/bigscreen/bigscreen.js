    var myChart1 = null;
    var myChart2 = null;
    var myChart3 = null;
    var myChart4 = null;
    var chartSortLeft = null;
    var chart4Interval;
    var isOnline = true;//是否联网
    function loadJScript() {
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.src = "http://api.map.baidu.com/api?v=3.0&ak=dBcg7P5NZFB8dpvYcBNQ87WG6FlbUn20&callback=initMap";
        document.body.appendChild(script);
    }
    /*获取监控物*/
    function getMonitorThings(){
        $.ajax({
            url : "../../MonitorStorageController/getAthorityMonitors",
            type : "post",
            dataType : "json",
            async:false,
            success : function(json) {
                var htmlArr = [];
                for(var i=0;i<json.length;i++){
                    htmlArr.push("<option value="+json[i].code+">"+json[i].describe+"</option>");
                }
                $("#thingMonitor").append(htmlArr.join(" "));
            }
        });
    }

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
                                fontSize:size*0.75
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

    /**
     * 初始化地图
     */
    function initMap() {
        isOnline = true;
        if(isOnline) {
            $.getScript("./bigscreen/mapv.js", function () {
                // 百度地图API功能
                map = new BMap.Map("allmap");    // 创建Map实例
                map.centerAndZoom(new BMap.Point(105.403119, 38.028658), 17);  // 初始化地图,设置中心点坐标和地图级别
                map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放

                // 地图自定义样式
                map.setMapStyle({
                    styleJson: [{
                        "featureType": "water",
                        "elementType": "all",
                        "stylers": {
                            "color": "#044161"
                        }
                    }, {
                        "featureType": "land",
                        "elementType": "all",
                        "stylers": {
                            "color": "#091934"
                        }
                    }, {
                        "featureType": "boundary",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#064f85"
                        }
                    }, {
                        "featureType": "railway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "highway",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#004981"
                        }
                    }, {
                        "featureType": "highway",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#005b96",
                            "lightness": 1
                        }
                    }, {
                        "featureType": "highway",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "on"
                        }
                    }, {
                        "featureType": "arterial",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#004981",
                            "lightness": -39
                        }
                    }, {
                        "featureType": "arterial",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#00508b"
                        }
                    }, {
                        "featureType": "poi",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "green",
                        "elementType": "all",
                        "stylers": {
                            "color": "#056197",
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "subway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "manmade",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "local",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "arterial",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "boundary",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#029fd4"
                        }
                    }, {
                        "featureType": "building",
                        "elementType": "all",
                        "stylers": {
                            "color": "#1a5787"
                        }
                    }, {
                        "featureType": "label",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }, {
                        "featureType": "poi",
                        "elementType": "labels.text.fill",
                        "stylers": {
                            "color": "#ffffff"
                        }
                    }, {
                        "featureType": "poi",
                        "elementType": "labels.text.stroke",
                        "stylers": {
                            "color": "#1e1c1c"
                        }
                    }, {
                        "featureType": "administrative",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "on"
                        }
                    }, {
                        "featureType": "road",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "off"
                        }
                    }]
                });
                selectedSearchDataConn();
            })
        }
    }
    var mapajaxconn = 0;
    /* 实时获取地图点位数据 */
    function selectedSearchDataConn() {
        var mapdata = null;
        var alarmInfo = "";
        var alarmList = [];
        if (mapajaxconn > 0) {// 始终保持一个连接
            return false;
        }
        mapajaxconn++;// 发起连接，连接数加1
        $.ajax({
            url : "../../DeviceController/getDeviceMapData",
            type : "post",
            dataType : "json",
            async:true,
            data : {
                "projectId": "",
                "list" : [-1],
                "nostatus" : "",
                "select" : "-1",
                "maxsize":5000
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                mapajaxconn--;
            },
            success : function(json) {
                mapdata = null;
                if (json.result != null) {
                    var isRefresh = false;// 是否更新表格
                    if (json.select == "-1") {
                        isRefresh = true;
                    }
                    if (isRefresh) {
                        mapdata = json.result;
                        alarmList = [];
                        var mapCount = mapdata.length;
                      /*  for (var i = 0; i < mapCount; i++) {
                            if (mapdata[i].statusCode != "N") {
                                var value = displayAlarmInfo(mapdata[i].deviceName,
                                    mapdata[i].statusInfo);
                                if (value != "") {
                                    if (alarmInfo == "") {
                                        alarmInfo = value;
                                    } else {
                                        alarmInfo = alarmInfo + "；" + value;
                                    }
                                    alarmList.push("<li style='height:25px;padding-left:10px;'>"+value+"</li>");
                                }
                            }
                        }
                        $("#alarmInfolist").html(alarmList.join(""));
                        $("#scrollDiv").Scroll({ line: 1, speed: 500, timer: 2000 });
                        $("#alarmInfolistOther").html(alarmList.join(""));
                        $("#scrollDiv2").Scroll({ line: 1, speed: 500, timer: 2000 });*/
                        addMakerPonit(mapdata);//描点
                        var pointArray = [];
                        for(var i = 0; i < mapCount; i++) {

                            var jsonPoint = {"lng":mapdata[i].deviceX,"lat":mapdata[i].deviceY};
                            pointArray = pointArray.concat(jsonPoint);
                        }
                        if(isOnline){
                            map.setViewport(pointArray);
                        }
                    }
                }
                if(mapajaxconn>0){
                    mapajaxconn--; // 当连接关闭、连接数减1
                }
            }
        });
    }
    var label = null;
    /**
     * 描点
     * @param mapdata
     */
    function addMakerPonit(mapdata){
        if(isOnline){
            map.clearOverlays();
            var arr = [];
            var arrN = [];
            for (var i = 0; i < mapdata.length; i++) {

                var geoCoord = [mapdata[i].deviceX, mapdata[i].deviceY];
                if (mapdata[i].statusCode == "N") {
                    arrN.push({
                        geometry: {
                            type: 'Point',
                            coordinates: geoCoord
                        },
                        zIndex: 5,
                        time: Math.random() * 10,
                        deviceName:mapdata[i].deviceName,
                        fillStyle: 'rgba(70, 196, 49, 0.8)'
                    });
                } else if (mapdata[i].statusCode == "NT") {
                    arr.push({
                        geometry: {
                            type: 'Point',
                            coordinates: geoCoord,
                            zIndex:7
                        },
                        time: Math.random() * 10,
                        deviceName:mapdata[i].deviceName,
                        fillStyle: 'rgba(255, 50, 50, 0.8)',
                        zIndex:7
                    });
                } else {
                    arr.push({
                        geometry: {
                            type: 'Point',
                            coordinates: geoCoord
                        },
                        time: Math.random() * 10,
                        deviceName:mapdata[i].deviceName,
                        fillStyle: 'rgba(143,133,133,0.8)'
                    });
                }
            }
            var options = {
                size:8,
                strokeStyle: 'rgba(255,255, 255, 1)',
                lineWidth: 1, // 描边宽度
                shadowColor: 'rgba(255, 255, 255, 1)', // 投影颜色
                shadowBlur: 2,  // 投影模糊级数
                draw: 'bubble',
                methods: { // 一些事件回调函数
                    click: function (item) { // 点击事件，返回对应点击元素的对象值
                        console.log(item);
                    },
                    mousemove: function(item) { // 鼠标移动事件，对应鼠标经过的元素对象值
                        if(label!=null){
                            map.removeOverlay(label);
                        }
                        if(item!=null){
                            var point = new BMap.Point(item.geometry.coordinates[0],item.geometry.coordinates[1]);
                            var labelstr = "<div style='background:transparent;margin:-2px 8px 0px;font-weight:bold;'>"+item.deviceName+"</div>";
                            label = new BMap.Label(labelstr,{position:point});  // 创建文本标注对象
                            label.setStyle({
                                color : "#000",
                                maxWidth:"none",
                                backgroundColor:'rgba(255, 255, 255, 0.8)',//文本背景色
                                borderColor:'#fff',//文本框边框色
                                borderRadius:"2px",
                                fontSize : "1.2rem",
                                height : "28px",
                                lineHeight : "28px",
                                fontFamily:"微软雅黑"
                            });
                            map.addOverlay(label);
                        }
                    }
                }/*, animation: {
                type: 'time',
                stepsRange: {
                    start: 0,
                    end: 60
                },
                trails: 10,
                duration: 5
            }*/
            }

            var optionsN = {
                size:8,
                strokeStyle: 'rgba(255, 255, 255, 0.5)',
                lineWidth: 1, // 描边宽度
                shadowColor: 'rgba(70, 196, 49, 1)', // 投影颜色
                shadowBlur: 2,  // 投影模糊级数
                draw: 'bubble',
                animation: {
                    type: 'time',
                    stepsRange: {
                        start: 0,
                        end: 60
                    },
                    trails: 10,
                    duration: 5
                }
            }

            if(arr.length>0){
                var dataSet =  new mapv.DataSet(arr);
                var baiduMapLayer = new mapv.baiduMapLayer(map, dataSet, options);
            }
            if(arrN.length>0){
                var dataSetN = new mapv.DataSet(arrN);
                var baiduMapLayerO = new mapv.baiduMapLayer(map, dataSetN, optionsN);
            }
            /* if(arrNT.length>0){
                var dataSetNT = new mapv.DataSet(arrNT);
                var baiduMapLayerO = new mapv.baiduMapLayer(map, dataSetNT, optionsNT);
            }*/
        }
    }


    function queryGenaralMonitorCount(){
        if(myChart1!=null){
            echarts.dispose(document.getElementById("chart_msc"));
        }
        if(myChart2!=null){
            echarts.dispose(document.getElementById("chart_msc2"));
        }
        if(myChart3!=null){
            echarts.dispose(document.getElementById("chart_msc3"));
        }
        ajaxLoadingBigScreen("chart_msc");
        ajaxLoadingBigScreen("chart_msc2");
        ajaxLoadingBigScreen("chart_msc3");
        $.ajax({
            url : "../../GeneralMonitorController/getGenaralMonitorCount",
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
                ajaxLoadEndBigScreen();
                ajaxLoadEndBigScreen();
                ajaxLoadEndBigScreen();
                if(json.result){
                    var disconnectCount = parseFloat(json.disconnectCount);
                    var nodataCount = parseFloat(json.nodataCount);
                    var normalCount = parseFloat(json.normalCount);
                    var offlineCount = parseFloat(json.offlineCount);
                    var onlineCount= parseFloat(json.onlineCount);
                    var otherCount = parseFloat(json.otherCount);
                    var overCount= parseFloat(json.overCount);
                    var onlineRate = calRate(onlineCount,offlineCount,0);
                    var offlineRate = calRate(offlineCount,onlineCount,0);
                    var normalRate = calRate(normalCount,overCount,0);
                    var overRate = calRate(overCount,normalCount,0);
                    var disconnectRate = calRate(disconnectCount,nodataCount,otherCount);
                    var nodataRate = calRate(nodataCount,disconnectCount,otherCount);
                    var otherRate = calRate(otherCount,disconnectCount,nodataCount);
                    initChartMonitorStatusCount1(json);
                    $("#onlineData").html(onlineRate);
                    $("#outlineData").html(offlineRate);
                    initChartMonitorStatusCount2(json);
                    $("#normalStatus").html(normalRate);
                    $("#overStatus").html(overRate);
                    initChartMonitorStatusCount3(json);
                    $("#disconnectStatus").html(disconnectRate);
                    $("#nodataStatus").html(nodataRate);
                    $("#otherStatus").html(otherRate);
                }
            }
        });
    }

    function initChartMonitorStatusCount1(seriesData){
        echarts.dispose(document.getElementById("chart_msc"));
        myChart1 = echarts.init(document.getElementById("chart_msc"));
        var option = {
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series : [
                {
                    name: '监控站点在线率',
                    type: 'pie',
                    radius : '75%',
                    center: ['50%', '50%'],
                    data:[
                        {value:seriesData.onlineCount, name:'在线'},
                        {value:seriesData.offlineCount, name:'离线'}

                    ],
                    label: {
                        normal: {
                            fontSize:size*0.8
                        }
                    },
                    itemStyle: {
                        //通常情况下：
                        normal: {
                            //每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
                            color: function (params) {
                                var colorList = ['#00cbef', '#234591', '#f29e3c', '#c05bdd', '#7a65f2']; //每根柱子的颜色
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

    function initChartMonitorStatusCount2(seriesData){
        echarts.dispose(document.getElementById("chart_msc2"));
        myChart2 = echarts.init(document.getElementById("chart_msc2"));
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            series: [
                {
                    name:'站点状态',
                    type:'pie',
                    radius: [0,'75%'],
                    label: {
                        normal: {
                            fontSize:size*0.8
                        }
                    },
                    data:[
                        {value:seriesData.nodataCount, name:'无数据', itemStyle:{
                            color : '#404c76'
                        }},
                        {value:seriesData.disconnectCount, name:'断开连接', itemStyle:{
                            color : '#729dd1'
                        }},
                        {value:seriesData.otherCount, name:'其他', itemStyle:{
                            color : '#10d2e3'
                        }}
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
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            color:['red', '#234591'],
            series: [
                {
                    name:'监控站点超标率',
                    type: 'pie',
                    center: ['50%', '50%'], // 饼图的圆心坐标
                    radius: ['70%', '80%'],
                    avoidLabelOverlap: false,
                    hoverAnimation: false,
                    label: { //  饼图图形上的文本标签

                        normal: { // normal 是图形在默认状态下的样式
                            show: true,
                            position: 'center',
                            color: '#f8f9fa',
                            fontSize: size*1.2,
                            fontWeight: 'bold',
                            formatter: '{d}%\n{b}' // {b}:数据名； {c}：数据值； {d}：百分比
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:[{
                            value: seriesData.overCount,
                            name: "超标",
                            label: {
                                normal: {
                                    show: true
                                }
                            }
                        },
                        {
                            value: seriesData.normalCount,
                            name: '正常',
                            label: {
                                normal: {
                                    show: false
                                }
                            }
                        }
                    ]
                }
            ]
        };
        myChart3.setOption(option,true);
       /* var currentIndex = -1;
        setInterval(function() {
            var dataLength= option.series[0].data.length;
            // 取消之前高亮的图形
            myChart3.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            currentIndex = (currentIndex + 1) % dataLength;
            // 高亮当前图形
            myChart3.dispatchAction({
                type: 'highlight',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            // 显示 tooltip
            myChart3.dispatchAction({
                type: 'showTip',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
        }, 1000);*/
    }

    /**
     * 报警信息
     */
    function alarmInfoTable(status,noStatus){
        $("#alarmInfolist").html("");
        $("#alarmInfolistOther").html("");
        $.ajax({
            url:"../../DeviceController/getDeviceMapAlarmWithinDay",
            type:"post",
            dataType:"json",
            data: {
                list: [-1],
                status:status,
                noStatus: noStatus,
                page: -1,
                rows: -1
             },
            success:function(json){
                if(json.rows.length>0){
                   var displayTable =  displayAlarmInfo(json.rows);
                    if(status=="NT"){
                        $("#alarmInfolist").html(displayTable);
                        if(json.rows.length>4){
                            $("#scrollDiv").Scroll({ line: 1, speed: 5000, timer: 30000 });
                        }
                    }
                    if(noStatus=="NT"){
                        $("#alarmInfolistOther").html(displayTable);
                        if(json.rows.length>4){
                            $("#scrollDiv2").Scroll({ line: 1, speed: 5000, timer: 30000 });
                        }
                    }
                }
            },
            error:function(e){

            }
        });
    }
    function displayAlarmInfo(data) {
        var displayInfo = "";
        if(data.length == 0){
            displayInfo =   "<li style='list-style:none;width:100%'>" +
                "<table style='display: table;width:100%;text-align: center; border-collapse: collapse;border:1px dashed  #66afe9;box-shadow:inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(102,175,233,0.6);' cellspacing='0'  cellpadding='0'>" +
                "<tbody>"+
                "<tr width='100%' style='background:rgba(12,70,125,0.8);' colspan='5'>" +
                "<td width='20%'  style='text-align:center;word-break:break-all;word-wrap:break-word;'>设备运行正常</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>"+
                "</li>";
        }
        for(var i=0;i<data.length;i++){
            var userName = (data[i].userName==null)?"&nbsp;&nbsp;&nbsp;&nbsp;":data[i].userName;
            var userTel = (data[i].userTel==null)?"&nbsp;&nbsp;&nbsp;&nbsp;":data[i].userTel;
            displayInfo = displayInfo +
                "<li style='list-style:none;width:100%'>" +
                "<table style='display: table;width:100%;text-align: center; border-collapse: collapse;' cellspacing='0'  cellpadding='0'>" +
                "<tbody>"+
                "<tr width='100%'  style='background: #0d3270;'>"+
                "<td width='20%'  style='word-break:break-all;word-wrap:break-word;'>"+data[i].deviceMn+"</td>" +
                "<td width='20%'   style='word-break:break-all;word-wrap:break-word;'>"+data[i].deviceName+"</td>" +
                "<td width='30%'   style='word-break:break-all;word-wrap:break-word;'>"+data[i].statusInfo+"</td>" +
                "<td width='15%'   style='word-break:break-all;word-wrap:break-word;'>"+userName+"</td>" +
                "<td width='15%'   style='word-break:break-all;word-wrap:break-word;'>"+userTel+"</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>"+
                "</li>";
        }
        return displayInfo;
    }

    /*查询图表信息*/
    function searchChartAlarmRateFunction() {
        var url = "../../EnvStatisticalReportController/queryExcessTimesRateWithinDay";
        var params = {"projectId":"", "list": [-1], "levelFlag": "", "thingCode": "", "updateType": "2011","convertType":""};
        if(myChart4!=null){
            echarts.dispose(document.getElementById("chart_msc4"));
        }
        ajaxLoadingBigScreen("chart_msc4");
        $.ajax({
            url : url,
            type : "post",
            dataType : "json",
            data : params,
            error : function(json) {
                ajaxLoadEndBigScreen();
            },
            success : function(json) {
                ajaxLoadEndBigScreen();
                if(json.result){
                    if(json.rows != null){
                        deviceAlarmRateOfTotal(json.rows);
                    }
                }
            }
        });
    }


    var currentIndex = -1;

    /**
     *
     * 设备在总报警设备中的占比
     * @param legendData
     * @param yAxisData
     * @param seriesData
     */
    function deviceAlarmRateOfTotal(data){
        if(myChart4!=null){
            myChart4.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            myChart4.dispatchAction({
                type: 'hideTip'
            });
        }
        currentIndex = -1;
        if(chart4Interval!=null){
            clearInterval( chart4Interval);
        }
        myChart4 = echarts.init(document.getElementById("chart_msc4"));
        var arry = [];
        for(var i=0;i<data.length;i++){
            arry[i] = {value:data[i].alarmCount, name:data[i].deviceName};
        }
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: function(params,ticket,calback){
                    var str = '<div  style="text-align:center;width:5em;color:#fff;background:transparent;border-color:#0e335f;margin:-5px; transition: all 2s;z-index:100000000;" id="toolTipBox" >'+
                       '<p style="font-size:size*1.2;margin:0px;font-weight:bold;">'+params.percent+'%</p>'+
                        '<p style="font-size:size*0.8;margin:0px 0px 0px 0px; color:#fff">'+params.name+'</p></div>';
                    $("#stationAlarmTitle").html(params.name);
                    var percent = (params.percent!=""&&  params.percent!=null)?params.percent:"0";
                    $("#stationAlarmRate").html(params.value+"("+percent+"%)");
                   // $("#toolTipBox").parent().css("background","#0e335f")
                    return str;
                },
                position:function(point,params,dom,rect,size){
                    var marginW = Math.ceil(size.contentSize[0]/2);
                    var marginH = Math.ceil(size.contentSize[1]/2);
                    dom.style.marginLeft = "-" + marginW + 'px';
                    dom.style.marginTop = '-' + marginH + "px";
                    return ['50%','50%'];

                },
                alwaysShowContent:true,
                backgroundColor:"transparent",
                textStyle:{
                    fontSize:size,
                    color:'#0e335f'
                }
            },
            color:['#234591','#90ac45',"#24feb4",'#1bb2d8','#066660','#88b0bb',"#2b65bc","#75a0e1"],
            series: [
                {
                    name: '设备报警占比',
                    type: 'pie',
                    center: ['50%', '50%'], // 饼图的圆心坐标
                    radius: ['70%', '80%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: false,
                            textStyle: {
                                fontSize: 10,
                                color:"#0e335f"
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:arry.slice(0,15)
                }
             ]
        };
        myChart4.setOption(option,true);

        chart4Interval =  setInterval(function(){
            var dataLength= option.series[0].data.length;
            // 取消之前高亮的图形
            myChart4.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            currentIndex = (currentIndex + 1) % dataLength;
            // 高亮当前图形
            myChart4.dispatchAction({
                type: 'highlight',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            // 显示 tooltip
            myChart4.dispatchAction({
                type: 'showTip',
                seriesIndex: 0,
                dataIndex: currentIndex
            });

            if(currentIndex>dataLength){
                currentIndex = -1;
            }

            $("#toolTipBox").animate({},function(){
                $("#toolTipBox").css({'height':'38px','transform':'rotate(360deg)'});
            });
        }, 5000);
    }

    /**
     * 计算比率
     * @param data
     * @param totalData
     * @returns {string}
     */
     function calRate(data1,data2,data3){
	    var calResult = "";
	    if(data1!==0){
            var total = data1+data2+data3;
            var rate = ((data1/total)*100).toFixed(2);
            calResult = rate+"%";
        }else{
            calResult = "0%";
        }
	    return data1+"("+calResult+")";
     }

    /*
     *格式化当前日期（只有月日年）
     */
    function formatterDate(date){
        var day=date.getDate()>9 ? date.getDate() : "0"+date.getDate();
        var month=(date.getMonth()+1)>9 ? (date.getMonth()+1) : "0"+(date.getMonth()+1);
        return date.getFullYear()+"-"+month+"-"+day;
    }