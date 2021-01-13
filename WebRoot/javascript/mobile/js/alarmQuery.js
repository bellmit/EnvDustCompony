/**
 * @author hiYuzu
 * @description
 * @version V1.0 报警查询页面
 * @date 2019/4/17 16:11
 */

$(function () {
    var currentDate = Date.parse(new Date());
    //初始化为查询一天
    $("#timePicker1").val(longToDay(currentDate));
    $("#timePicker2").val(longToDay(currentDate));
    btnClick();
});

function btnClick() {
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
        var deviceName = $("#deviceName").val();
        drawAlarmTable(deviceName, beginTime, endTime);
    } else {
        alert("结束时间必须晚于开始时间");
    }
}

function drawAlarmTable(deviceName, beginTime, endTime) {
    var url = "../../AlarmController/preQueryAlarms";
    var column = [];    //表单列内容
    column.push({
        field: 'num',
        title: '序号',
        align: "center",
        width: 50,
        formatter: function (value, row, index) {
            return index + 1;
        }
    }, {
        field: 'deviceName',
        title: '名称',
        align: 'center'
    }, {
        field: 'alarmTypeName',
        title: '类型',
        align: 'center'
    }, {
        field: 'alarmInfo',
        title: '报警信息',
        align: 'center'
    }, {
        field: 'alarmTime',
        title: '报警时间',
        align: 'center'
    }, {
        field: 'areaName',
        title: '区域',
        align: 'center'
    });
    //初始化monitorTable
    $("#alarmTable").bootstrapTable("destroy");
    $("#alarmTable").bootstrapTable({
        url: url,
        method: "post",
        striped: true,        //是否显示行间隔色
        showColumns: false,      //列显示控制
        pagination: true,     //是否显示分页（*）
        sidePagination: "server",     //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                //初始化加载第一页，默认第一页
        pageSize: 10,                 //每页的记录行数（*）
        // pageList: [5, 10, 15, 20],  //可供选择的每页的行数（*）
        columns: column,
        paramsType: "",
        queryParams: function (params) {
            return {
                rows: params.limit,
                page: (params.offset / params.limit) + 1,
                deviceName: deviceName,
                beginAlarmTime: beginTime,
                endAlarmTime: endTime
            };
        },
        formatNoMatches: function () {
            return "无数据";
        }
    });
    //隐藏正在加载
    $("#alarmDetailsTable").bootstrapTable("hideLoading");
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

function longToSec(longDate) {//   long型转换为日期,格式:"yyyy-mm-dd hh:mm:ss"
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