/*******************************************************************************
 * 功能：厂商信息 日期：2016-3-23 13:41:09
 ******************************************************************************/
var appendcontent = '<div id="dgSmsInfo"></div>'
    + '<div id="tbdgSmsInfo" style="padding:5px 10px;">'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editSmsFunc(\'添加通知设置\',\'icon-add\',\'insertSms\')">添加</a>'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editSmsFunc(\'修改通知设置\',\'icon-edit\',\'updateSms\')">修改</a>'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delSmsFunc()">删除</a>'
    + '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchDeviceName" data-options="onClickButton:function(){searchSmsFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
    + '</div>';
addPanel("通知设置", appendcontent);

//区域
var smsAreaData = {};
var smsAreaValue = null;
//监测物
var smsThingData = {};
var smsThingValue = null;
//报警类型
var smsFualtTypeData = {};
var smsFualtTypeValue = null;
//接收人
var smsUserData = [];
var userIdGroup = "";
//是否生效
var smsEffectiveFlagData = {
    "0": "否",
    "1": "是"
};
var smsEffectiveFlagValue = "0";
//通知类型
var smsReceiveFlagData = {
    "1": "全部",
    "2": "邮件",
    "3": "短信"
};
var smsReceiveFlagValue = "2";
$.ajax({
    url: "../UserController/queryUsers",
    type: "post",
    dataType: "json",
    async: false,
    success: function (json) {
        for (var i = 0; i < json.total; i++) {
            smsUserData.push({"userId": json.rows[i].userId, "userName": json.rows[i].userName});
        }
    }
});
/* 初始化列表 */
$("#dgSmsInfo").datagrid({
    view: myview,
    fit: true,
    border: false,
    pagination: true,
    fitColumns: true,
    pageList: [10, 50, 100, 150, 200, 250, 300],
    url: "../SmsController/querySms",
    pageSize: 10,
    autoRowHeight: false,
    rownumbers: true,
    toolbar: "#tbdgSmsInfo",
    columns: [[{
        field: 'smsId',
        checkbox: true
    }, {
        field: 'areaId',
        title: '区域ID',
        width: 120,
        halign: 'center',
        align: 'center',
        hidden: true
    }, {
        field: 'areaName',
        title: '区域名称',
        width: 150,
        halign: 'center',
        align: 'center'
    }, {
        field: 'deviceId',
        title: '设备ID',
        width: 120,
        halign: 'center',
        align: 'center',
        hidden: true
    }, {
        field: 'deviceCode',
        title: '设备编号',
        width: 120,
        halign: 'center',
        align: 'center'
    }, {
        field: 'deviceName',
        title: '设备名称',
        width: 120,
        halign: 'center',
        align: 'center'
    }, {
        field: 'statusCode',
        title: '报警类型编码',
        width: 120,
        halign: 'center',
        align: 'center',
        hidden: true
    }, {
        field: 'statusName',
        title: '报警类型',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'thingCode',
        title: '监测物编码',
        width: 100,
        halign: 'center',
        align: 'center',
        hidden: true
    }, {
        field: 'thingName',
        title: '监测物名称',
        width: 80,
        halign: 'center',
        align: 'center'
    }, {
        field: 'beginTime',
        title: '开始时间',
        width: 150,
        halign: 'center',
        align: 'center'
    }, {
        field: 'endTime',
        title: '结束时间',
        width: 150,
        halign: 'center',
        align: 'center'
    }, {
        field: 'effectiveFlag',
        title: '生效',
        width: 60,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.effectiveFlag) {
                return "是";
            } else {
                return "否";

            }
        }
    }, {
        field: 'receiveFlag',
        title: '通知类型',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.receiveFlag === "2") {
                return "邮件";
            } else if(row.receiveFlag === "3") {
                return "短信";
            }else if(row.receiveFlag === "1") {
                return "全部";
            }else{
                return "未知";
            }
        }
    }, {
        field: 'optUserName',
        title: '操作者',
        width: 80,
        halign: 'center',
        align: 'center'
    }, {
        field: 'optTime',
        title: '操作时间',
        width: 90,
        halign: 'center',
        align: 'center'
    }]],
    singleSelect: false,
    selectOnCheck: true,
    checkOnSelect: true
}).datagrid('doCellTip', {cls: {'max-width': '500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgSmsInfo").datagrid("getPager").pagination({
    total: 0
});

/* 查询厂商 */
function searchSmsFunc() {
    $("#dgSmsInfo").datagrid("load", {
        "deviceName": $("#searchDeviceName").val()
    });
}

/* 删除通知设置 */
function delSmsFunc() {
    var selectrow = $("#dgSmsInfo").datagrid("getChecked");
    if (selectrow.length < 1) {
        $.messager.alert("提示", "请选择删除的记录！", "warning");
        return false;
    }
    var idArry = [];
    for (var i = 0; i < selectrow.length; i++) {
        idArry.push(selectrow[i].smsId);
    }
    $.messager.confirm("提示", '通知设置数据将被清除，确定删除选定记录吗？', function (r) {
        if (r) {
            ajaxLoading();
            $.ajax({
                url: "../SmsController/deleteSms",
                type: "post",
                dataType: "json",
                data: {
                    "list": idArry
                },
                error: function (json) {
                    ajaxLoadEnd();
                },
                success: function (json) {
                    ajaxLoadEnd();
                    if (json.result) {
                        $.messager.alert("提示", "删除成功！", "info");
                        $("#dgSmsInfo").datagrid('reload');
                    } else {
                        $.messager.alert("错误", json.detail, "error");
                    }
                }
            });
        }
    });
}

/* 添加、修改通知设置 */
function editSmsFunc(title, icon, action) {
    if (title == "修改通知设置") {
        var selectrow = $("#dgSmsInfo").datagrid("getSelections");
        if (selectrow.length != 1) {
            $.messager.alert("提示", "请选择一条记录进行修改！", "info");
            return false;
        }
    }
    initSmsParam($("#dgSmsInfo").datagrid("getSelected"));
    $("#dialogModel").dialog({
        width: 450,
        height: 500,
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
                    var statusCode = $('#statusCode').combobox('getValue');
                    var thingCode = $('#thingCode').combobox('getValue');
                    if (statusCode == "NT" && (thingCode == null || thingCode == "")) {
                        $.messager.alert("提示", "请选择监测物质！", "error");
                        return false;
                    }
                    var userIds = $('#userIds').combobox('getValues');
                    if (userIds == "") {
                        $.messager.alert("提示", "请至少选择一个接收人！", "error");
                        return false;
                    }
                    var formdataArray = $("#frmdialogModel").serializeArray();// 将表单数据序列化创建一个json数组
                    formdataArray.push({name: "list", value: userIds});
                    var formdataJosn = getFormJson(formdataArray);// 转换成json数组
                    ajaxLoading();
                    $.ajax({
                        url: "../SmsController/" + action,
                        type: "post",
                        dataType: "json",
                        data: formdataJosn,
                        error: function (json) {
                            ajaxLoadEnd();
                            $.messager.alert("提示", "连接服务器操作类失败！", "info");
                        },
                        success: function (json) {
                            ajaxLoadEnd();
                            if (json.result) {
                                $("#dialogModel").dialog("close");
                                $.messager.alert("提示", "操作成功！", "info");
                                $("#dgSmsInfo").datagrid('reload');
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
    $("#frmdialogModel").html(function () {
        var htmlArr = [];
        htmlArr.push(createValidatebox({
            name: "smsId",
            title: "通知设置ID",
            ishiden: true,
            value: "-1"
        }));
        htmlArr.push(createComboboxEdit({
            name: "areaId",
            title: "所属区域",
            data: smsAreaData,
            value: smsAreaValue,
            noBlank: true
        }));
        htmlArr.push(createCombobox({
            name: "deviceId",
            title: "监控设备",
            noBlank: true
        }));
        htmlArr.push(createComboboxEdit({
            name: "statusCode",
            title: "故障类型",
            data: smsFualtTypeData,
            value: smsFualtTypeValue,
            noBlank: true
        }));
        htmlArr.push(createComboboxEdit({
            name: "thingCode",
            title: "监测物质",
            data: smsThingData,
            value: smsThingValue
        }));
        htmlArr.push(createComboboxEdit({
            name: "userIds",
            title: "接收人",
            noBlank: true
        }));
        htmlArr.push(createDatetimebox({
            name: "beginTime",
            title: "开始时间",
            showSeconds: true,
            noBlank: true
        }));
        htmlArr.push(createDatetimebox({
            name: "endTime",
            title: "结束时间",
            showSeconds: true,
            noBlank: true
        }));
        htmlArr.push(createCombobox({
            name: "effectiveFlag",
            title: "是否生效",
            data: smsEffectiveFlagData,
            valueField: 'code',
            textField: 'name',
            value: smsEffectiveFlagValue
        }));
        htmlArr.push(createCombobox({
            name: "receiveFlag",
            title: "通知类型",
            data: smsReceiveFlagData,
            valueField: 'code',
            textField: 'name',
            value: smsReceiveFlagValue
        }));

        return htmlArr.join("");
    });
    $("#areaId").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值
            var allData = $(this).combobox("getData");   //获取combobox所有数据
            var result = true;      //为true说明输入的值在下拉框数据中不存在
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }
        }
    });
    $("#deviceId").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值
            var allData = $(this).combobox("getData");   //获取combobox所有数据
            var result = true;      //为true说明输入的值在下拉框数据中不存在
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }
        }
    });
    $("#thingCode").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值
            var allData = $(this).combobox("getData");   //获取combobox所有数据
            var result = true;      //为true说明输入的值在下拉框数据中不存在
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }
        }
    });
    $("#statusCode").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值
            var allData = $(this).combobox("getData");   //获取combobox所有数据
            var result = true;      //为true说明输入的值在下拉框数据中不存在
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }
        }
    });
    $("#userIds").combobox({
        onHidePanel: function () {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue");  //当前combobox的值
            var allData = $(this).combobox("getData");   //获取combobox所有数据
            var result = true;      //为true说明输入的值在下拉框数据中不存在
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                $(this).combobox("clear");
            }
        }
    });
    var dataCount = 0;
    $("#userIds").combobox({
        data: smsUserData,
        valueField: 'userId',
        textField: 'userName',
        multiple: true,
        formatter: function (row) {
            var opts = $(this).combobox('options');
            return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
        },
        onShowPanel: function () {
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');
            $.map(values, function (value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            });
            // 动态调整高度
            if (dataCount < 10) {
                $(this).combobox('panel').height("auto");
            } else {
                $(this).combobox('panel').height(200);
            }
        },
        onLoadSuccess: function (data) {
            var opts = $(this).combobox('options');
            var target = this;
            var values = $(target).combobox('getValues');
            $.map(values, function (value) {
                var el = opts.finder.getEl(target, value);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            });
            dataCount = data.length;
        },
        onSelect: function (row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', true);
        },
        onUnselect: function (row) {
            var opts = $(this).combobox('options');
            var el = opts.finder.getEl(this, row[opts.valueField]);
            el.find('input.combobox-checkbox')._propAttr('checked', false);
        }
    });
    /* 重绘窗口 */
    $.parser.parse("#dialogModel");
    $("#dialogModel").dialog("open");
    if (title == "修改通知设置") {
        var selectrow = $("#dgSmsInfo").datagrid("getSelected");
        $("#smsId").val(selectrow.smsId);
        initDeviceCombox(selectrow.areaId, selectrow.deviceId);
        $('#areaId').combobox({
            onSelect: function () {
                var areaId = $("#areaId").combobox('getValue');
                initDeviceCombox(areaId, null);
            }
        });
        $("#beginTime").datetimebox('setValue', selectrow.beginTime);
        $("#endTime").datetimebox('setValue', selectrow.endTime);
        if (userIdGroup != null && userIdGroup != "") {
            $('#userIds').combobox('setValues', userIdGroup.split(","));
        }
        if (selectrow.effectiveFlag) {
            $('#effectiveFlag').combobox('setValue', "1");
        } else {
            $('#effectiveFlag').combobox('setValue', "0");
        }
        $('#receiveFlag').combobox('setValue', selectrow.receiveFlag);
    } else {
        var areadatacombox = $("#areaId").combobox('getValues');
        initDeviceCombox(areadatacombox[0], null);
        $('#areaId').combobox({
            onSelect: function () {
                var areaId = $("#areaId").combobox('getValue');
                initDeviceCombox(areaId, null);
            }
        });
    }
}

/* 初始化区域、站点、监测物以及报警类型下拉框 */
function initSmsParam(selectRow) {
//	console.log(JSON.stringify(selectRow));
    $.ajax({
        url: "../DeviceController/queryDeviceAreaDropDown",
        type: "post",
        dataType: "json",
        data: {
            "id": "-1",
            "levelFlag": "4"
        },
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    smsAreaData[json.rows[i].id] = json.rows[i].name;
                }
                if (selectRow != null) {
                    smsAreaValue = selectRow.areaId;
                }
            }
        }
    });
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
                if (json[i].code != "N") {
                    smsFualtTypeData[json[i].code] = json[i].name;
                }
                if (selectRow != null) {
                    smsFualtTypeValue = selectRow.statusCode;
                } else {
                    smsFualtTypeValue = "NT";
                }
            }
        }
    });
    $.ajax({
        url: "../MonitorStorageController/getAthorityMonitors",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.length > 0) {
                for (var i = 0; i < json.length; i++) {
                    smsThingData[json[i].code] = json[i].name;
                }
            }
            if (selectRow != null) {
                smsThingValue = selectRow.thingCode;
            }
        }
    });
    var smsId = "-1";
    if (selectRow != null) {
        smsId = selectRow.smsId;
    }
    $.ajax({
        url: "../SmsController/querySmsUser",
        type: "post",
        dataType: "json",
        data: {
            "smsId": smsId
        },
        async: false,
        success: function (json) {
            if (json.total > 0) {
                userIdGroup = "";
                for (var i = 0; i < json.total; i++) {
                    if (json.rows[i] != null && json.rows[i] != "") {
                        if (userIdGroup == "") {
                            userIdGroup = json.rows[i];
                        } else {
                            userIdGroup += "," + json.rows[i];
                        }
                    }
                }
            }
        }
    });

}


/* 获取设备 */
function initDeviceCombox(id, selectId) {
    if (id == null || id == '') {
        $('#deviceId').combobox("clear");
        return;
    }
    $.ajax({
        url: "../DeviceController/queryDevice",
        type: "post",
        dataType: "json",
        data: {
            "areaId": id
        },
        async: false,
        success: function (json) {
            var smsDeviceData = [];
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    smsDeviceData.push({"id": json.rows[i].deviceId, "name": json.rows[i].deviceName});
                }
            }
            $('#deviceId').combobox({
                data: smsDeviceData,
                valueField: 'id',
                textField: 'name'
            });
            if (selectId != null) {
                $('#deviceId').combobox('setValue', selectId);
            } else if (smsDeviceData.length > 0) {
                $('#deviceId').combobox('setValue', smsDeviceData[0].id);
            }
        }

    });
}