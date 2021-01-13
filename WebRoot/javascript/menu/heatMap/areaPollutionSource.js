/**
 * 区域污染溯源
 * @author hiYuzu
 * @version V1.0
 * @date 2019/9/2 15:24
 */
var areaPollutionSourceContent = '<div class="easyui-layout" data-options="fit:true">'

    + '<div data-options="region:\'north\', border:false" style="height:5%; padding-top: 10px;">'
    + '&emsp;&emsp;区域名称：<input class="easyui-combobox" name="mapAreaCombox" id="mapAreaCombox" style="width:180px;"/>'
    + '&emsp;选择半径：<select class="easyui-combobox" name="radiusCombox" id="radiusCombox" style="width:100px; height: auto;">'
    + '<option value="100">100米</option>'
    + '<option value="300">300米</option>'
    + '<option value="500">500米</option>'
    + '<option value="700">700米</option>'
    + '<option value="900">900米</option>'
    + '<option value="1100">1100米</option>'
    + '<option value="1300">1300米</option>'
    + '<option value="1500">1500米</option>'
    + '</select>'
    + '&emsp;<input id="pointCoordinate" type="text" style="width: 150px;" />'
    + '&emsp;<input class="easyui-datetimebox" id="time" data-options="required:true, showSeconds:false" style="width:130px;" />'
    + '&emsp;<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="source" onclick="getSource()">溯源分析</a>'
    + '</div>'

    + '<div data-options="region:\'center\',border:false">'
    + '<div id="pollutionSourceMap" style="height:100%;width:100%;"></div>'
    + '</div>'

    + '<div style="height: 100px;width: 90px; position: absolute; top: 55px; left: 15px;">'
    + '<img id="windArrow" src="../../../images/wind/wind_0.png" style="display: none;">'
    + '</div>'

    + '<div id="infoPanel" class="easyui-panel" data-options="style:{position:\'absolute\',right:0,top:42}"></div>'

    + '</div>';

addPanel("区域污染溯源", areaPollutionSourceContent);

loadMap();

var sourceMap;
var markers = {};
var lng, lat;
var clickMarker = null;
var clickCircle = null;
var statusCodes = {};
var panelFlag = false;

/* 加载地图 */
function loadMap() {
    $.getScript("http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js");
    $.getScript("../javascript/jquery-easyui-1.4.4/TextIconOverlay_min.js");
    $.getScript("../javascript/jquery-easyui-1.4.4/MarkerClusterer_min.js");
    sourceMap = new BMap.Map("pollutionSourceMap"); // 创建Map实例
    var point = new BMap.Point(117.1786889372559, 39.10762965106183); // 创建点坐标
    sourceMap.centerAndZoom(point, 12);
    sourceMap.enableScrollWheelZoom();
    sourceMap.enableInertialDragging();
    sourceMap.enableContinuousZoom();
    sourceMap.setDefaultCursor("Default");
    sourceMap.addEventListener("click", function (e) {
        if (clickMarker != null && clickCircle != null) {
            sourceMap.removeOverlay(clickMarker);
            sourceMap.removeOverlay(clickCircle)
        }
        lng = e.point.lng;
        lat = e.point.lat;
        $("#pointCoordinate").val(lng + "," + lat);

        var clickPoint = new BMap.Point(lng, lat);

        clickMarker = new BMap.Marker(clickPoint);
        sourceMap.addOverlay(clickMarker);

        clickCircle = new BMap.Circle(clickPoint, $("#radiusCombox").combobox('getValue'), {strokeWeight: 1});
        sourceMap.addOverlay(clickCircle);
    });
    initAreaCombox();
}

function initAreaCombox() {//初始化下拉框
    var url = "../AreaController/queryBottomAreas";
    $.ajax({
        url: url,
        type: "post",
        dataType: "json",
        async: true,
        success: function (json) {
            $("#mapAreaCombox").combobox({
                data: json.rows,
                method: 'post',
                valueField: 'areaId',
                textField: 'areaName',
                onShowPanel: function () {
                    // 动态调整高度
                    if (json.length < 20) {
                        $(this).combobox('panel').height("auto");
                    } else {
                        $(this).combobox('panel').height(300);
                    }
                },
                onChange: function (n, o) {
                    queryDevice(n);
                }
            });
        }
    });
}

function queryDevice(areaId) {//查询区域
    var mapData = null;
    var url = "../DeviceController/getDeviceMapData";
    $.ajax({
        url: url,
        type: "post",
        data: {
            "projectId": "",
            "list": [areaId],
            "levelflag": 4,
            "nostatus": "",
            "select": areaId,
            "maxsize": 5000
        },
        dataType: "json",
        async: false,
        error: function (json) {
            ajaxLoadEnd();
        },
        success: function (json) {
            if (json.result != null) {
                mapData = json.result;
                var mapCount = mapData.length;
                for (var i = 0; i < mapCount; i++) {
                    var point = new BMap.Point(mapData[i].deviceX,
                        mapData[i].deviceY);
                    //储存marker信息
                    storeMarker(point, mapData[i]);
                }
                //描点
                $.each(markers, function (e, marker) {
                    sourceMap.addOverlay(marker);
                });
                var pointArray = [];
                for (var i = 0; i < mapCount; i++) {
                    var jsonPoint = {"lng": mapData[i].deviceX, "lat": mapData[i].deviceY};
                    pointArray = pointArray.concat(jsonPoint);
                }
                var view = sourceMap.getViewport(pointArray); //获取最佳视角
                var zoom = view.zoom; //获取最佳视角的缩放层级
                if (zoom <= 4) {
                    zoom = sourceMap.getZoom();
                    sourceMap.setViewport(pointArray, {"zoomFactor": (zoom - 2)});
                } else {
                    sourceMap.setViewport(pointArray);
                }
            }
        }
    });
}

function storeMarker(point, data) {//储存marker信息
    var title = $('#mytab').tabs('getSelected').panel('options').title;
    var deviceCode = data.deviceCode;
    var myIcon = new BMap.Icon("../../images/pointlink.png", new BMap.Size(32, 32));
    /*
    if (data != undefined) {
        if (data.statusCode == "N") {//蓝
            myIcon = new BMap.Icon("../../images/pointlink.png", new BMap.Size(32,
                32));
        } else if (data.statusCode == "NT") {//红
            myIcon = new BMap.Icon("../../images/pointalarm3.png", new BMap.Size(32,
                32));
        } else if (data.statusCode == "O" || data.statusCode == "Z") {//灰
            myIcon = new BMap.Icon("../../images/pointunlink.png", new BMap.Size(
                32, 32));
        } else {//黄
            myIcon = new BMap.Icon("../../images/pointfault.png", new BMap.Size(
                32, 32));
        }
    }
    */
    if (title == "区域污染溯源") {
        var marker = new BMap.Marker(point, {
            icon: myIcon
        });
        markers[deviceCode] = marker; // 将标记存储
        statusCodes[deviceCode] = data.statusCode;
        bindMarkerEvent(marker, data);
    }
}

/* 显示站点详细信息 */
function bindMarkerEvent(marker, data) {
    var userName = data.userName == null ? "未知" : data.userName;
    var userTel = data.userTel == null ? "未知" : data.userTel;
    marker.setTitle(data.deviceName + "\n负责人：" + userName + "\n负责人电话：" + userTel);
}

function getSource() {
    var coordinate = $("#pointCoordinate").val().split(",");
    if (coordinate.length === 2 && $("#mapAreaCombox").combobox('getValue') !== "" && $("#time").datetimebox('getValue') !== "") {
        $.ajax({
            url: "../SourceAnalysisController/analyzeSource",
            type: "post",
            dataType: "json",
            async: false,
            data: {
                "lng": coordinate[0],
                "lat": coordinate[1],
                "areaId": $("#mapAreaCombox").combobox('getValue'),
                "radius": $("#radiusCombox").combobox('getValue'),
                "time": $("#time").datetimebox('getValue')
            },
            success: function (json) {
                if (json.result) {
                    changeMarkerIcon(json.data.pollutionSources);
                    openPanel($("#pointCoordinate").val(), json.data.pollutionSources, json.data.windRoseData);
                    showWindArrow(json.data.windDegree, json.data.windSpeed, json.data.windSc);
                } else {
                    alert(json.detail);
                }
            }
        });
    } else {
        alert("输入参数缺失！");
    }
}

var DiffusionIcon = {
    N: new BMap.Icon("../../images/pointlink.gif", new BMap.Size(130, 130)),
    NT: new BMap.Icon("../../images/pointalarm1.gif", new BMap.Size(130, 130)),
    O: new BMap.Icon("../../images/pointunlink.gif", new BMap.Size(130, 130)),
    Z: new BMap.Icon("../../images/pointunlink.gif", new BMap.Size(130, 130)),
    null: new BMap.Icon("../../images/fault1.gif", new BMap.Size(130, 130))
};

var DiffusionIcon_alarm = {
    N: new BMap.Icon("../../images/pointalarm3.gif", new BMap.Size(130, 130)),
    NT: new BMap.Icon("../../images/pointalarm3.gif", new BMap.Size(130, 130)),
    O: new BMap.Icon("../../images/pointalarm3.gif", new BMap.Size(130, 130)),
    Z: new BMap.Icon("../../images/pointalarm3.gif", new BMap.Size(130, 130)),
    null: new BMap.Icon("../../images/pointalarm3.gif", new BMap.Size(130, 130))
};

var icons = {};

function changeMarkerIcon(data) {//改变marker图标
    $.each(icons, function (e, icon) {
        markers[e].setIcon(icon);
    });
    icons = {};
    for (var i = 0; i < data.length; i++) {
        var marker = markers[data[i].deviceCode];
        icons[data[i].deviceCode] = marker.getIcon();
        marker.setIcon(DiffusionIcon_alarm[statusCodes[data[i].deviceCode]]);
    }
}

/* 打开panel，显示溯源信息 */
function openPanel(coordinate, data, windRoseData) {
    $("#infoPanel").panel(
        {
            collapsible: true,
            collapsed: true,
            closable: true,
            fit: false,
            title: "坐标点：" + coordinate,
            tools: [{
                iconCls: 'icon-influence',
                handler: function () {
                    showPercentPie(data);
                }
            }, {
                iconCls: 'icon-rose',
                handler: function() {
                    showRoseWind(windRoseData);
                }
            }],
            content: '<div class="easyui-layout" data-options="fit:true">'
            + '<div data-options="region:\'north\',border:false" id="centerContentMap" style="height:45%">'
            + '<div id="northContent" style="background:yellow;width:100%;"></div>'
            + '</div>'
            + '<hr/>'
            + '<div data-options="region:\'center\',border:false" id="centerContentMap">'
            + '<div id="centerContent" style="background:yellow;width:100%;"></div>'
            + '</div>'
            + '</div>'
        }).panel("open");
    /* 重绘窗口 */
    $.parser.parse("#infoPanel");
    $("#infoPanel").panel("refresh").panel("expand").panel('resize', {
        width: 500,
        height: 800
    });
    if (!panelFlag) {
        showPercentPie(data);   // 显示影响占比饼图
    } else {
        showRoseWind(windRoseData); //显示风力玫瑰图
    }
    showSourceInfo(data);	// 显示溯源信息
}

function showPercentPie(data) {// 显示影响占比饼图
    panelFlag = false;
    var legendData = [];
    var seriesData = [];
    var selectData = {};
    for (var i = 0; i < data.length; i++) {
        legendData.push(data[i].deviceName);
        seriesData.push({value: data[i].density.toFixed(6), name: data[i].deviceName});
        selectData[data[i].deviceName] = i < 10;
    }
    var dom = document.getElementById("northContent");
    dom.style.cssText = "height:98%";
    var myChart = echarts.init(dom);
    myChart.dispose();
    myChart = echarts.init(dom);
    var option = {
        title: {
            text: '设备影响占比',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{b}<br/>{d}% ({c}mg/m³)"
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            data: legendData,
            selected: selectData
        },
        series: [
            {
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: seriesData,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart.setOption(option);
}

function showRoseWind(data) {//显示风力玫瑰图
    panelFlag = true;
    var dom = document.getElementById("northContent");
    dom.style.cssText = "height:98%";
    dom.innerHTML = '';
    var myChart = echarts.init(dom);
    myChart.dispose();
    myChart = echarts.init(dom);
    var legendName = ["0级", "1级", "2级", "3级", "4级", "5级", "6级", "6级以上"];
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "风力{a}：</br>{c}小时"
        },
        angleAxis: {
            type: 'category',
            data: [
                {value: '北风'},
                {value: '东北风'},
                {value: '东风'},
                {value: '东南风'},
                {value: '南风'},
                {value: '西南风'},
                {value: '西风'},
                {value: '西北风'}
            ],
            z: 10,
            boundaryGap: false,//标签和数据点都会在两个刻度之间的带(band)中间
            axisTick: {
                show: true//是否显示坐标轴刻度
            },
            show: true
        },
        radiusAxis: {
            axisLabel: {
                formatter: '',
                margin:-25,
                rich: {}
            },
            zlevel:3,
            axisTick: {
                show: false//是否显示坐标轴刻度
            },
            axisLine:{
                show:false//是否显示坐标轴轴线
            }
        },
        polar: {},
        series: [{
            barCategoryGap: 0,
            type: 'bar',
            data: data[0],
            coordinateSystem: 'polar',
            name: legendName[0],
            stack: 'a',
            itemStyle: {
                borderColor: 'black',
                borderWidth: 1
            }
        },
            {
                barCategoryGap: 0,
                type: 'bar',
                data: data[1],
                coordinateSystem: 'polar',
                name: legendName[1],
                stack: 'a'
            }, {
                barCategoryGap: 0,
                type: 'bar',
                data: data[2],
                coordinateSystem: 'polar',
                name: legendName[2],
                stack: 'a'
            }, {
                barCategoryGap: 0,
                type: 'bar',
                data: data[3],
                coordinateSystem: 'polar',
                name: legendName[3],
                stack: 'a'
            }, {
                barCategoryGap: 0,
                type: 'bar',
                data: data[4],
                coordinateSystem: 'polar',
                name: legendName[4],
                stack: 'a'
            }, {
                barCategoryGap: 0,
                type: 'bar',
                data: data[5],
                coordinateSystem: 'polar',
                name: legendName[5],
                stack: 'a'
            }, {
                barCategoryGap: 0,
                type: 'bar',
                data: data[6],
                coordinateSystem: 'polar',
                name: legendName[6],
                stack: 'a'
            }, {
                barCategoryGap: 0,
                type: 'bar',
                data: data[7],
                coordinateSystem: 'polar',
                name: legendName[7],
                stack: 'a'
            }],
        legend: {
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            data: legendName
        }
    };
    myChart.setOption(option);
}

function showSourceInfo(data) {// 显示溯源信息
    $("#centerContent").datagrid({
        view: myView,
        fit: true,
        border: false,
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageList: [10, 50, 100, 150, 200, 250, 300],
        pageSize: 10,
        autoRowHeight: false,
        rownumbers: true,
        columns: [[{
            field: "deviceName",
            title: "设备名",
            width: 150,
            halign: 'center',
            align: 'center'
        }, {
            field: "density",
            title: "浓度影响",
            width: 150,
            halign: 'center',
            align: 'center',
            formatter: function (value, row, index) {
                return value.toFixed(6) + "mg/m³";
            }
        }, {
            field: "percent",
            title: "影响占比",
            width: 148,
            halign: 'center',
            align: 'center',
            formatter: function (value, row, index) {
                return value.toFixed(2) + "%";
            }
        }]],
        data: data,
        onHeaderContextMenu: function (e, field) {
            e.preventDefault();
            if (!cmenu) {
                createColumnMenu("centerContent");
            }
            cmenu.menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });
}

function showWindArrow(windDegree, windSpeed, windSc) {
    var windArrow = document.getElementById("windArrow");
    windArrow.src="../../../images/wind/wind_"+ windSc +".png";
    windArrow.style.transform = "rotate(" + windDegree + "deg)";
    windArrow.title = "风速：" + windSpeed + "km/h";
    windArrow.style.display = "block";
}

var myView = $.extend({}, $.fn.datagrid.defaults.view, {
    onAfterRender: function (target) {
        $.fn.datagrid.defaults.view.onAfterRender.call(this, target);
        var opts = $(target).datagrid('options');
        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
        vc.children('div.datagrid-empty').remove();
        if (!$(target).datagrid('getRows').length) {
            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || '没有查询到相应的数据！').appendTo(vc);
            d.css({
                position: 'absolute',
                left: 0,
                top: 50,
                width: '100%',
                textAlign: 'center'
            });
        }
    }
});