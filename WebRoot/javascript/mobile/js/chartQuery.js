/**
 * @author hiYuzu
 * @description 数据图像化页面
 * @version V1.0
 * @date 2019/4/16 11:55
 */
var updateType;
var firstFlag = false;

function getOriginalData(deviceCode, thingCode, updateType, beginTime, endTime) {//绘制报警详情表
    var url = "../../MonitorStorageController/getOriginalData";
    $.ajax({
        url: url,
        data: {
            "deviceCode": deviceCode,
            "thingCode": thingCode,
            "updateType": updateType,
            "beginTime": beginTime,
            "endTime": endTime,
            "select": deviceCode
        },
        type: "post",
        dataType: "json",
        success: function (json) {
            drawLineChart(json);
            drawBarChart(json);
        }
    });
}

function drawLineChart(json) {
    var thingName = $("#thingName").find("option:selected").text();
    var thingArray = [];
    var len = json.rows.length;
    if (updateType === 2011) {
        for (var i = 0; i < len; i++) {
            thingArray.push([i, json.rows[i].thingRtd]);
        }
    } else {
        for (var i = 0; i < len; i++) {
            thingArray.push([i, json.rows[i].thingAvg]);
        }
    }
    var lineArray = [];
    lineArray.push({
        data: thingArray,
        color: "#66ccff",
        label: thingName
    });
    var chartOptions = {
        grid: {
            hoverable: false,
            borderColor: "#f3f3f3",
            borderWidth: 1,
            tickColor: "#f3f3f3"
        },
        series: {
            shadowSize: 0,
            lines: {
                show: true
            },
            points: {
                show: false
            }
        },
        lines: {
            fill: false
        },
        yaxis: {
            show: true,
            //监测值负数无意义
            min: 0,
            //最小刻度尺寸
            minTickSize: 1,
            //小数点
            tickDecimals: 0
        },
        xaxis: {
            show: false
        }
    };
    $.plot("#lineChart", lineArray, chartOptions);
}

function drawBarChart(json) {
    var data = [];
    var thingName = $("#thingName").find("option:selected").text();
    var len = json.rows.length;
    if (updateType === 2011) {
        for (var i = 0; i < len; i++) {
            data.push([i, json.rows[i].thingRtd]);
        }
    } else {
        for (var i = 0; i < len; i++) {
            data.push([i, json.rows[i].thingAvg]);
        }
    }

    var bar_data = {
        data: data,
        color: "#66ccff",
        label: thingName
    };
    var chartOptions = {
        grid: {
            borderWidth: 1,
            borderColor: "#f3f3f3",
            tickColor: "#f3f3f3"
        },
        series: {
            bars: {
                show: true,
                barWidth: 0.5,
                align: "center",
                numbers: {
                    show: false,
                    xAlign: function (x) {
                        return x
                    },
                    yAlign: function (y) {
                        return y
                    }
                }
            }
        },
        yaxis: {
            show: true,
            //监测值负数无意义
            min: 0,
            //最小刻度尺寸
            minTickSize: 1,
            //小数点
            tickDecimals: 0
        },
        xaxis: {
            show: false
        }
    };
    $.plot("#barChart", [bar_data], chartOptions);
}

$(function () {
    $("#currUpdateType").html("数据类型：" + $("#updateType").val());
    var currentDate = Date.parse(new Date());
    //初始化为查询一天
    $("#timePicker1").val(longToDay(currentDate));
    $("#timePicker2").val(longToDay(currentDate));
    firstFlag = true;
    loadArea();
});

function btnClick() {//    点击查询
    firstFlag = false;
    $("#myModal").modal('hide');
    //获取时间值
    var beginTime = $("#timePicker1").val() + " 00:00:00";
    var endTime = $("#timePicker2").val() + " 23:59:59";
    var currentDate = Date.parse(new Date());
    var currentTime = longToSec(currentDate);

    if (beginTime > currentTime) {
        alert("开始时间不能晚于当前时间!");
        return false;
    }
    if (endTime > beginTime) {
        checkType();
        var deviceCode = $("#device").val();
        var thingCode = $("#thingName").val();
        getOriginalData(deviceCode, thingCode, updateType, beginTime, endTime);
    } else {
        alert("结束时间必须晚于开始时间");
    }
}

$("#areaName, #manufactor, #device, #thingName, #updateType").selectpicker({
    "width": "100%"
});

function loadArea() {
    $.ajax({
        type: 'post',
        async: false,
        url: '../../AreaController/queryBottomAreasUpperDropDown',
        dataType: "json",
        success: function (data) {
            if (data.total > 0) {
                $("#areaName").empty();
                for (var i = 0; i < data.rows.length; i++) {
                    if (i === 0) {
                        $("#areaName").append("<option value='" + data.rows[i].id + "' selected>" + data.rows[i].name + "</option>");
                    } else {
                        $("#areaName").append("<option value='" + data.rows[i].id + "'>" + data.rows[i].name + "</option>");
                    }
                }
                $("#areaName").selectpicker("refresh");
                loadManufactor(data.rows[0].id);
            }
        }
    });
}

function loadManufactor(id) {
    $.ajax({
        type: 'post',
        url: '../../AreaController/queryAreaDropDownSub',
        data: {
            "id": id
        },
        dataType: "json",
        success: function (data) {
            if (data.total > 0) {
                $("#manufactor").empty();
                for (var i = 0; i < data.rows.length; i++) {
                    if (i === 0) {
                        $("#manufactor").append("<option value='" + data.rows[i].id + "' selected>" + data.rows[i].name + "</option>");
                        $("#pointName").html($("#areaName").find("option:selected").text() + "--" + data.rows[i].name);
                    } else {
                        $("#manufactor").append("<option value='" + data.rows[i].id + "'>" + data.rows[i].name + "</option>");
                    }
                }
                $("#manufactor").selectpicker("refresh");
                loadDevice(data.rows[0].id);
            }
        }
    });
}

function loadDevice(areaId) {
    $.ajax({
        type: 'post',
        url: '../../DeviceController/queryAreaAuthDevice',
        data: {
            "areaId": areaId
        },
        dataType: "json",
        success: function (data) {
            if (data.total > 0) {
                $("#device").empty();
                for (var i = 0; i < data.rows.length; i++) {
                    if (i === 0) {
                        $("#device").append("<option value='" + data.rows[i].deviceCode + "' selected>" + data.rows[i].deviceName + "</option>");
                        $("#deviceName").html(data.rows[i].deviceName);
                    } else {
                        $("#device").append("<option value='" + data.rows[i].deviceCode + "'>" + data.rows[i].deviceName + "</option>");
                    }
                }
                $("#device").selectpicker("refresh");
                drawThingSelect(data.rows[0].deviceCode);
            }
        }
    });
}

$("#areaName").change(function () {
    loadManufactor($("#areaName").val());
    $("#pointName").html("");
    $("#pointName").html($("#areaName").find("option:selected").text() + "--" + $("#manufactor").find("option:selected").text());
});
$("#manufactor").change(function () {
    loadDevice($("#manufactor").val());
    $("#pointName").html("");
    $("#pointName").html($("#areaName").find("option:selected").text() + "--" + $("#manufactor").find("option:selected").text());
});
$("#device").change(function () {
    drawThingSelect($("#device").val());
    $("#deviceName").html("");
    $("#deviceName").html($("#device").find("option:selected").text());
});
$("#thingName").change(function () {
    $("#currThingName").html("");
    $("#currThingName").html("污染物：" + $("#thingName").find("option:selected").text());
});
$("#updateType").change(function () {
    $("#currUpdateType").html("");
    $("#currUpdateType").html("数据类型：" + $("#updateType").val());
});

function drawThingSelect(deviceCode) {//获取绘制报警物质（指标）
    var url = "../../MonitorStorageController/getAthorityDeviceMonitors";
    $.ajax({
        url: url,
        type: "post",
        data: {
            "deviceCode": deviceCode
        },
        async: false,
        dataType: "json",
        success: function (json) {
            var num = json.length;
            if (num > 0) {
                $("#thingName").empty();
                for (var t = 0; t < num; t++) {
                    if (t === 0) {
                        $("#thingName").append("<option value='" + json[t].code + "' selected>" + json[t].name + "</option>");
                        $("#currThingName").html("污染物:" + json[t].name);
                    } else {
                        $("#thingName").append("<option value='" + json[t].code + "'>" + json[t].name + "</option>");
                    }
                }
                $("#thingName").selectpicker("refresh");
            }
            if (firstFlag) {
                btnClick();
            }
        }
    });
}

function checkType() {
    //获取下拉框中的内容
    updateType = $("#updateType").val();
    if (updateType === "实时数据") {
        updateType = 2011;
    } else if (updateType === "分钟数据") {
        updateType = 2051;
    } else if (updateType === "小时数据") {
        updateType = 2061;
    } else if (updateType === "每日数据") {
        updateType = 2031;
    }
}

function longToDay(longDate) {//   long型转换为日期,格式:"yyyy-mm-dd"
    var d = new Date();
    d.setTime(longDate);
    var year = d.getFullYear();
    var month = (d.getMonth() + 1);
    if (d.getMonth() + 1 < 10) {
        month = "0" + month;
    }
    var day = d.getDate();
    if (d.getDate() < 10) {
        day = "0" + day;
    }
    return year + "-" + month + "-" + day;
}

function longToSec(longDate) {//   long型转换为日期,格式:"yyyy-mm-dd hh-mm-ss"
    var d = new Date();
    d.setTime(longDate);
    var year = d.getFullYear();
    var month = (d.getMonth() + 1);
    if (d.getMonth() + 1 < 10) {
        month = "0" + month;
    }
    var day = d.getDate();
    if (d.getDate() < 10) {
        day = "0" + day;
    }
    var hour = d.getHours();
    if (d.getHours() < 10) {
        hour = "0" + hour;
    }
    var minute = d.getMinutes();
    if (d.getMinutes() < 10) {
        minute = "0" + minute;
    }
    var second = d.getSeconds();
    if (d.getSeconds() < 10) {
        second = "0" + second;
    }
    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}