/*******************************************************************************
 * 功能：报警数据 日期：2016-4-28 13:28:09
 ******************************************************************************/
var appendAlarmcontent = '<div id="dgalarmData"></div>'
    + '<div id="dgAlarmData" style="padding:5px 10px;">'
    + '<div>'
    + '站点名称：<input class="easyui-textbox" id="deviceName"  style="width:118px;"/>'
    + '&nbsp;&nbsp;&nbsp;报警处理状态：<input class="easyui-combobox" name="alarmStatus" id="alarmStatus" style="width:108px;"/>'
    + '&nbsp;&nbsp;&nbsp;报警类型：<input class="easyui-combobox" name="alarmType" id="alarmType" style="width:143px;"/>'
    + '</div>'
    + '报警时间范围：<input class="easyui-datetimebox" id="dtAlarmStartTime" style="width:143px;"/>'
    + '<span>&nbsp;&nbsp;&nbsp;至：</span>'
    + '<input class="easyui-datetimebox" id="dtAlarmEndTime" style="width:143px;"/>'
    + '&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-search\',plain:true" onclick="searchAlarmDataFunc()">查询</a>'
    + '&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editAlarmDataFunc(\'报警处理\',\'icon-edit\',\'updatetOranization\')">处理</a>'
    + '&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delAlarmDataFunc()">删除</a>'
    + '&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-download\',plain:true" onclick="exportAlarmDataFunc()">导出</a>'
    + '</div>';
addPanel("报警数据", appendAlarmcontent);

var endTime = formatterDate(new Date());
$("#dtAlarmStartTime").datetimebox('setValue', formatterDate(new Date()));
$("#dtAlarmEndTime").datetimebox('setValue', endTime + " 23:59:59");

var alarmTypeData = [];
var alarmTypeJSONData = {};
var alarmStatus = [{
    "id": "1",
    "name": "未解决"
}, {
    "id": "2",
    "name": "已解决"
}];
var alarmStatusJSON = {
    "1": "未解决",
    "2": "已解决"
};
$.ajax({
    url: "../AlarmController/getStatus",
    type: "post",
    dataType: "json",
    data: {
        "statusType": "0"
    },
    async: false,
    success: function (json) {
        alarmTypeData = json;
        for (var i = 0; i < json.length; i++) {
            alarmTypeJSONData[json[i].code] = json[i].name;
        }
    }
});
$("#alarmStatus").combobox({
    data: alarmStatus,
    method: 'post',
    valueField: 'id',
    textField: 'name',
    panelHeight: 'auto'
});
$("#alarmType").combobox({
    data: alarmTypeData,
    method: 'post',
    valueField: 'code',
    textField: 'name',
    panelHeight: 'auto'
});
$("#dgalarmData").datagrid({
    view: myview,
    fit: true,
    border: false,
    pagination: true,
    fitColumns: true,// 是否适拖动栏
    pageList: [50, 100, 150, 200, 250, 300],
    pageSize: 50,
    autoRowHeight: false,
    rownumbers: true,
    singleSelect: false,
    selectOnCheck: true,
    checkOnSelect: true,
    queryParams: {
        "beginAlarmTime": $("#dtAlarmStartTime").datetimebox('getValue'),
        "endAlarmTime": $("#dtAlarmEndTime").datetimebox('getValue'),
        "projectId": $("#deviceProjectId").combobox('getValue')
    },
    toolbar: "#dgAlarmData",
    url: "../AlarmController/queryAlarms",
    columns: [[{
        field: 'alarmId',
        title: '报警ID',
        width: 60,
        halign: 'center',
        align: 'center',
        checkbox: true
    }, {
        field: 'deviceName',
        title: '站点名称',
        width: 160,
        halign: 'center',
        align: 'center'
    }, {
        field: 'deviceCode',
        title: '站点编号',
        width: 120,
        halign: 'center',
        align: 'center',
        hidden: true
    }, {
        field: 'areaName',
        title: '所属区域',
        width: 200,
        halign: 'center',
        align: 'center'
    }, {
        field: 'alarmType',
        title: '报警类型',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.alarmTypeName
        }
    }, {
        field: 'alarmInfo',
        title: '报警信息',
        width: 180,
        halign: 'center',
        align: 'center',
        formatter: function (value, row) {
            var myBackground = "#fff";
            if(value!=null){
                if(row.alarmType=="NT"){
                    if(row.levelNo!=null){
                        var myBackground = getLevelColor(row.levelNo);
                        return '<span style="color:#fff;display: inline-block;padding:0px 5px;' +
                            'background:' + myBackground + '">' + value + '</span>';
                    }else{
                        return '<span style="color:black;display: inline-block;">' + value + '</span>';
                    }
                }else{
                    return '<span style="color:black;display: inline-block;">' + value + '</span>';
                }
            }else{
                return "";
            }

        }
    }, {
        field: 'dataTime',
        title: '数据时间',
        width: 160,
        halign: 'center',
        align: 'center'
    }, {
        field: 'alarmTime',
        title: '报警时间',
        width: 160,
        halign: 'center',
        align: 'center'
    }, {
        field: 'sendFlag',
        title: '通知状态',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.sendFlag) {
                return "已通知";
            } else {
                return "未通知";
            }
        }
    }, {
        field: 'alarmStatus',
        title: '处理状态',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.alarmStatusInfo
        }
    }, {
        field: 'actionTime',
        title: '处理时间',
        width: 160,
        halign: 'center',
        align: 'center'
    }, {
        field: 'alarmAction',
        title: '报警处理',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'actionUser',
        title: '处理人',
        width: 80,
        halign: 'center',
        align: 'center'
    }]]
}).datagrid('doCellTip', {
    cls: {
        'max-width': '500px'
    }
});

// 定义分页器的初始显示默认值
$("#dgalarmData").datagrid("getPager").pagination({
    total: 0
});

/* 查询报警信息 */
function searchAlarmDataFunc() {
    $("#dgalarmData").datagrid("load", {
        "alarmType": $("#alarmType").combobox('getValue'),
        "deviceName": $("#deviceName").val(),
        "alarmStatus": $("#alarmStatus").combobox('getValue'),
        "beginAlarmTime": $("#dtAlarmStartTime").datetimebox('getValue'),
        "endAlarmTime": $("#dtAlarmEndTime").datetimebox('getValue'),
        "projectId": $("#deviceProjectId").combobox('getValue')
    });
}

/* 删除报警信息 */
function delAlarmDataFunc() {
    var selectrow = $("#dgalarmData").datagrid("getChecked");
    var idArry = [];
    for (var i = 0; i < selectrow.length; i++) {
        idArry.push(selectrow[i].alarmId);
    }
    if (selectrow.length < 1) {
        $.messager.alert("提示", "请选择删除的记录！", "warning");
        return false;
    }
    $.messager.confirm("提示", '确定删除选定记录吗？', function (r) {
        if (r) {
            $.ajax({
                url: "../AlarmController/deleteAlarms",
                type: "post",
                dataType: "json",
                data: {
                    "list": idArry
                },
                success: function (json) {
                    if (json.result) {
                        $.messager.alert("提示", "删除成功！", "info");
                        $("#dgalarmData").datagrid('reload');
                    } else {
                        $.messager.alert("错误", json.detail, "error");
                    }
                }
            });
        }
    });
}

/* 添加、报警处理 */
function editAlarmDataFunc(title, icon, action) {
    var selectrow = $("#dgalarmData").datagrid("getSelections");
    if (selectrow.length <= 0) {
        $.messager.alert("提示", "请选择至少一条记录进行操作！", "info");
        return false;
    }
    var idArry = [];
    for (var i = 0; i < selectrow.length; i++) {
        idArry.push(selectrow[i].alarmId);
    }
    $("#dialogModel").dialog({
        width: 450,
        height: 424,
        title: title,
        inline: true,
        modal: true,
        maximized: false,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        iconCls: icon,
        resizable: true,
        closed: true,
        content: '<form id="frmdialogModel" class="config-form"></form>',
        buttons: [{
            text: "确定",
            iconCls: "icon-ok",
            handler: function () {
                if ($("#frmdialogModel").form("validate")) {
                    var formdataArray = $("#frmdialogModel").serializeArray();
                    formdataArray.push({name: "list", value: idArry});
                    var formdataJosn = getFormJson(formdataArray);// 转换成json数组
                    $.ajax({// 发送ajax请求
                        url: "../AlarmController/updateAlarms",
                        type: "post",
                        dataType: "json",
                        data: formdataJosn,
                        error: function (json) {
                            $.messager.alert("提示", json.detail, "info");
                        },
                        success: function (json) {
                            if (json.result) {
                                $("#dialogModel").dialog("close");
                                $.messager.alert("提示", "操作成功！", "info");
                                $("#dgalarmData").datagrid('reload');
                            } else {
                                $.messager.alert("错误", json.detail, "error");
                            }
                        }
                    });
                }
            }
        }, {
            text: "取消",
            iconCls: "icon-cancel",
            handler: function () {
                $("#dialogModel").dialog("close");
            }
        }]
    }).dialog('center');

    /* 初始化表单 */
    if (idArry.length == 1) {
        $("#frmdialogModel").html(function () {
            var htmlArr = [];
            htmlArr.push(createValidatebox({
                name: "alarmId",
                title: "报警ID",
                ishiden: true
            }));
            htmlArr.push(createValidatebox({
                name: "deviceCode",
                title: "站点编号",
                hidden: true
            }));
            htmlArr.push(createValidatebox({
                name: "deviceName",
                title: "站点名称",
                readonly: true
            }));
            htmlArr.push(createCombobox({
                name: "alarmType",
                title: "报警类型",
                data: alarmTypeJSONData,
                readonly: true
            }));
            htmlArr.push(createValidatebox({
                name: "alarmInfo",
                title: "报警信息",
                readonly: true
            }));
            htmlArr.push(createCombobox({
                name: "alarmStatus",
                data: alarmStatusJSON,
                title: "处理状态",
                value: "1"
            }));
            htmlArr.push(createDatetimebox({
                name: "actionTime",
                title: "处理时间",
                showSeconds: true,
                noBlank: true
            }));
            htmlArr.push(createValidatebox({
                name: "alarmAction",
                title: "报警处理"
            }));
            htmlArr.push(createValidatebox({
                name: "actionUser",
                title: "报警处理人"
            }));
            htmlArr.push(createCheckbox({
                name: "executeUpdate",
                title: "更新设备为'正常'状态",
                checked: false
            }));
            return htmlArr.join("");
        });
    } else {
        $("#frmdialogModel").html(function () {
            var htmlArr = [];
            htmlArr.push(createCombobox({
                name: "alarmStatus",
                data: alarmStatusJSON,
                title: "处理状态",
                value: "1"
            }));
            htmlArr.push(createDatetimebox({
                name: "actionTime",
                title: "处理时间",
                showSeconds: true,
                noBlank: true
            }));
            htmlArr.push(createValidatebox({
                name: "alarmAction",
                title: "报警处理"
            }));
            htmlArr.push(createValidatebox({
                name: "actionUser",
                title: "报警处理人"
            }));
            htmlArr.push(createCheckbox({
                name: "executeUpdate",
                title: "更新设备为'正常'状态",
                checked: false
            }));
            return htmlArr.join("");
        });
    }
    /* 重绘窗口 */
    $.parser.parse("#dialogModel");
    $("#dialogModel").dialog("open");
    if (title == "报警处理") {
        var selectrow = $("#dgalarmData").datagrid("getSelected");
        $("#frmdialogModel").form("reset");
        $("#frmdialogModel").form("load", selectrow);
    }
}

/* 导出数据 */
function exportAlarmDataFunc() {
    var alarmType = $("#alarmType").combobox('getValue');
    var deviceName = $("#deviceName").val();
    var alarmStatus = $("#alarmStatus").combobox('getValue');
    var beginAlarmTime = $("#dtAlarmStartTime").datetimebox('getValue');
    var endAlarmTime = $("#dtAlarmEndTime").datetimebox('getValue');
    location.href = "../ExportController/exportAlarms?alarmtype=" + alarmType
        + "&devicename=" + deviceName + "&alarmstatus=" + alarmStatus
        + "&startalarmtime=" + beginAlarmTime + "&endalarmtime="
        + endAlarmTime + "";
}
