/*******************************************************************************
 * 功能：监测设备信息 日期：2016-3-23 13:41:09
 ******************************************************************************/
var appendcontent = '<div id="dgdeviceInfo"></div>'
    + '<div id="tbdgdeviceInfo" style="padding:5px 10px;">'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editDeviceFunc(\'添加设备\',\'icon-add\',\'insertDevice\')">添加</a>'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editDeviceFunc(\'修改设备信息\',\'icon-edit\',\'updateDevice\')">修改</a>'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delDeviceFunc()">删除</a>'
    + '&nbsp;<input id="searchDeviceAreaId" class="easyui-combobox" style="width:200px;height:24px;" >'
    + '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchDeviceName" data-options="onClickButton:function(){searchDeviceFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
    + '</div>';

addPanel("设置监测设备", appendcontent);

var replyFlagData = {
    "2005": "2005协议",
    "2017": "2017协议"
};
var replyFlagDataValue = "2017";

var forceReplyData = {
    "0": "否",
    "1": "是"
};
var forceReplyDataValue = "0";

var staFlowData = {
    "0": "否",
    "1": "是"
};
var staFlowDataValue = "0";

/* 初始化列表,表头 */
$("#dgdeviceInfo").datagrid({
    view: myview,
    fit: true,
    border: false,
    pagination: true,
    fitColumns: false,
    singleSelect: true,
    pageList: [10, 50, 100, 150, 200, 250, 300],
    url: "../DeviceController/queryDevice",
    pageSize: 10,
    autoRowHeight: false,
    rownumbers: true,
    toolbar: "#tbdgdeviceInfo",
    columns: [[{
        field: 'deviceId',
        checkbox: true
    }, {
        field: 'deviceCode',
        title: '设备编号',
        width: 120,
        halign: 'center',
        align: 'center'
    }, {
        field: 'deviceMn',
        title: '设备MN号',
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
        field: 'mfrCode',
        title: '所属厂商',
        width: 180,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.mfrName
        }
    }, {
        field: 'areaId',
        title: '所属区域',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.areaName
        }
    }, {
        field: 'statusCode',
        title: '设备状态',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.statusName
        }
    }, {
        field: 'deviceIp',
        title: '设备IP',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'devicePort',
        title: '设备端口',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.devicePort == -1) {
                row.devicePort = null
            }
            return row.devicePort
        }
    }, {
        field: 'devicePwd',
        title: '设备访问密码',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'deviceX',
        title: '设备经度',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.deviceX == -1) {
                row.deviceX = null
            }
            return row.deviceX
        }
    }, {
        field: 'deviceY',
        title: '设备纬度',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.deviceY == -1) {
                row.deviceY = null
            }
            return row.deviceY
        }
    }, {
        field: 'deviceKm',
        title: '监测范围',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'projectId',
        title: '项目类型',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.projectName
        }
    }, {
        field: 'orgId',
        title: '监督单位',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.orgName
        }
    }, {
        field: 'buildFirm',
        title: '施工单位',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'userId',
        title: '负责人',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.userName
        }
    }, {
        field: 'deviceAddress',
        title: '设备地址',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'inspectTime',
        title: '巡检时间',
        width: 100,
        halign: 'center',
        align: 'center',
    }, {
        field: 'systemVersion',
        title: '系统版本',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'staMinute',
        title: '统计分钟',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.staMinute) {
                return "是";
            } else {
                return "否";

            }
        }
    }, {
        field: 'staHour',
        title: '统计小时',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.staHour) {
                return "是";
            } else {
                return "否";

            }
        }
    }, {
        field: 'staDay',
        title: '统计每日',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.staDay) {
                return "是";
            } else {
                return "否";

            }
        }
    }, {
        field: 'replyFlag',
        title: '协议版本',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.replyFlag == "2017") {
                return "2017协议";
            } else if (row.replyFlag == "2005") {
                return "2005协议";

            } else {
                return "不强制";
            }
        }
    }, {
        field: 'hourCount',
        title: '小时内实时数',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'forceReply',
        title: '强制回复标识',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.forceReply) {
                return "是";
            } else {
                return "否";

            }
        }
    }, {
        field: 'staFlow',
        title: '标态流量统计',
        width: 80,
        halign: 'center',
        hidden: notRequireStaFlow,
        align: 'center',
        formatter: function (value, row, index) {
            if (row.staFlow) {
                return "是";
            } else {
                return "否";
            }
        }
    }, {
        field: 'pipeArea',
        title: '烟筒面积(米)',
        width: 100,
        hidden: notRequireStaFlow,
        halign: 'center',
        align: 'center'
    }, {
        field: 'optUserName',
        title: '操作者',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'optTime',
        title: '操作时间',
        width: 130,
        halign: 'center',
        align: 'center'
    }]],
    singleSelect: false,
    selectOnCheck: true,
    checkOnSelect: true
}).datagrid('doCellTip', {cls: {'max-width': '500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgdeviceInfo").datagrid("getPager").pagination({
    total: 0
});

/* 查询设备 */
function searchDeviceFunc() {
    /*  */
    $("#dgdeviceInfo").datagrid("load", {
        "areaId": $("#searchDeviceAreaId").combobox("getValue"),
        "deviceName": $("#searchDeviceName").val()
    });
}

/* 删除设备 */
function delDeviceFunc() {
    var selectrow = $("#dgdeviceInfo").datagrid("getChecked");
    var idArry = [];
    for (var i = 0; i < selectrow.length; i++) {
        idArry.push(selectrow[i].deviceId);
    }
    if (selectrow.length < 1) {
        $.messager.alert("提示", "请选择删除的记录！", "warning");
        return false;
    }
    //是否确认进行记录删除使用    comfirm
    $.messager.confirm("提示", '该设备监测数据也将被清除，确定删除选定记录吗？', function (r) {
        if (r) {
            ajaxLoading();
            $.ajax({
                url: "../DeviceController/deleteDevice",
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
                        $("#dgdeviceInfo").datagrid('reload');
                        initTreeNoe("mytree", '../TreeController/getAreaTree', null, false);
                    } else {
                        $.messager.alert("错误", json.detail, "error");
                    }
                }
            });
        }
    });
}

var mfrCodeData = {};
var mfrCodeValue = null;
// 状态
var statusCodeData = {};
var statusValue = null;
// 区域ID
var areaIdData = {};
var areaValue = null;
// 负责人
var userIdData = {};
var userValue = null;
// 监督单位
var orgIdData = {};
var orgValue = null;
// 项目类型ID
var projectIdData = {};
var projectValue = null;

/* 添加、修改设备信息 */
function editDeviceFunc(title, icon, action) {
    initConfigDeiveParam();
    if (title == "修改设备信息") {
        var selectrow = $("#dgdeviceInfo").datagrid("getSelections");
        if (selectrow.length != 1) {
            $.messager.alert("提示", "请选择一条记录进行修改！", "info");
            return false;
        }
    }
    $("#dialogModel").dialog({
        width: 460,
        height: 520,
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
        content: '<form id="frmdialogModel" class="config-form"  style="width:400px;"></form>'
            + '<div style="margin-left:35px;width:360px;">平台统计数据'
            + '<label><input type="checkbox" id="staMinute" name="staMinute" style="margin-left:80px;vertical-align:middle;"><span style="vertical-align:middle;">分钟</span></label>'
            + '<label><input type="checkbox" id="staHour" name="staHour" style="margin-left:10px;vertical-align:middle;"><span style="vertical-align:middle;">小时</span></label>'
            + '<label><input type="checkbox" id="staDay" name="staDay" style="margin-left:10px;vertical-align:middle;"><span style="vertical-align:middle;">每日</span></label>'
            + '</div><br>',
        buttons: [{
            text: "确定",
            iconCls: "icon-ok",
            handler: function () {
                if ($("#frmdialogModel").form("validate")) {
                    var staFlow = $("#staFlow").combobox("getValue");
                    if (staFlow == true) {
                        if ($("#pipeArea").val() == null || $("#pipeArea").val() == ""
                            || $("#pipeArea").val() <= 0) {
                            $.messager.alert("提示", "烟筒面积必须大于0！", "info");
                            return false;
                        }
                    }
                    var formdataArray = $("#frmdialogModel").serializeArray();// 将表单数据序列化创建一个json数组
                    formdataArray.push({name: "staMinute", value: $('#staMinute').is(':checked')});
                    formdataArray.push({name: "staHour", value: $('#staHour').is(':checked')});
                    formdataArray.push({name: "staDay", value: $('#staDay').is(':checked')});
                    var formdataJosn = getFormJson(formdataArray);// 转换成json数组
                    ajaxLoading();
                    $.ajax({
                        url: "../DeviceController/" + action,
                        type: "post",
                        dataType: "json",
                        data: formdataJosn,
                        error: function (json) {
                            ajaxLoadEnd();
                            $.messager.alert("提示", json.detail, "info");
                        },
                        success: function (json) {
                            ajaxLoadEnd();
                            if (json.result) {
                                $("#dialogModel").dialog("close");
                                $.messager.alert("提示", "操作成功！", "info");
                                $("#dgdeviceInfo").datagrid('reload');
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
            name: "deviceId",
            title: "设备ID",
            ishiden: true,
            value: "-1"
        }));
        htmlArr.push(createValidatebox({
            name: "deviceCode",
            title: "设备编号",
            noBlank: true,
            type: 'devicecode'
        }));
        htmlArr.push(createValidatebox({
            name: "deviceMn",
            title: "设备MN号",
            noBlank: true,
            type: 'maxLength[27]'
        }));
        htmlArr.push(createValidatebox({
            name: "deviceName",
            title: "设备名称",
            noBlank: true,
            type: 'maxLength[100]'
        }));
        htmlArr.push(createCombobox({
            name: "mfrCode",
            title: "所属厂商",
            data: mfrCodeData,
            value: mfrCodeValue,
            noBlank: true
        }));
        htmlArr.push(createCombobox({
            name: "statusCode",
            title: "设备状态",
            data: statusCodeData,
            value: statusValue,
            noBlank: true
        }));
        htmlArr.push(createValidatebox({
            name: "deviceIp",
            title: "设备IP",
            type: 'ip'
        }));
        htmlArr.push(createValidatebox({
            name: "devicePort",
            title: "设备端口",
            value: "1000",
            type: 'devicevalid'
        }));
        htmlArr.push(createValidatebox({
            name: "devicePwd",
            title: "设备访问密码",
            noBlank: true,
            type: 'maxLength[6]'
        }));
        htmlArr.push(createValidatebox({
            name: "deviceX",
            title: "设备经度",
            noBlank: true,
            type: 'intOrFloat[73.55,135.083333]'
        }));
        htmlArr.push(createValidatebox({
            name: "deviceY",
            title: "设备纬度",
            noBlank: true,
            type: 'intOrFloat[3.85,53.55]'
        }));
        htmlArr.push(createComboboxEdit({
            name: "areaId",
            title: "所属区域",
            data: areaIdData,
            value: areaValue,
            noBlank: true
        }));
        htmlArr.push(createValidatebox({
            name: "deviceKm",
            title: "监测范围"
        }));
        htmlArr.push(createComboboxEdit({
            name: "projectId",
            title: "项目类型",
            data: projectIdData,
            value: projectValue,
            noBlank: true
        }));
        htmlArr.push(createComboboxEdit({
            name: "orgId",
            title: "监督单位",
            data: orgIdData,
            value: orgValue
        }));
        htmlArr.push(createValidatebox({
            name: "buildFirm",
            title: "施工单位",
            type: 'maxLength[100]'
        }));
        htmlArr.push(createComboboxEdit({
            name: "userId",
            title: "负责人",
            data: userIdData,
            value: userValue
        }));
        htmlArr.push(createValidatebox({
            name: "deviceAddress",
            title: "设备地址",
            type: 'maxLength[100]'
        }));
        htmlArr.push(createDatetimebox({
            name: "inspectTime",
            title: "巡检时间",
            showSeconds: true
        }));
        htmlArr.push(createValidatebox({
            name: "systemVersion",
            title: "系统版本",
            type: 'maxLength[25]'
        }));
        htmlArr.push(createCombobox({
            name: "replyFlag",
            title: "协议版本",
            data: replyFlagData,
            valueField: 'code',
            textField: 'name',
            value: replyFlagDataValue
        }));
        htmlArr.push(createValidatebox({
            name: "hourCount",
            title: "小时内实时数",
            noBlank: true,
            type: 'positiveInteger',
            value: 60
        }));
        htmlArr.push(createCombobox({
            name: "forceReply",
            title: "强制回复标识",
            data: forceReplyData,
            valueField: 'code',
            textField: 'name',
            value: forceReplyDataValue
        }));
        htmlArr.push(createCombobox({
            name: "staFlow",
            title: "标态流量统计",
            data: staFlowData,
            valueField: 'code',
            textField: 'name',
            value: staFlowDataValue
        }));
        htmlArr.push(createValidatebox({
            name: "pipeArea",
            title: "烟筒直径(米)",
            noBlank: true,
            ishiden: notRequireStaFlow,
            type: 'intOrFloat[0,10000]',
            value: 0
        }));
        return htmlArr.join("");
    });
    /* 重绘窗口 */
    $.parser.parse("#dialogModel");
    $("#dialogModel").dialog("open");
    if (notRequireStaFlow) {
        $("#staFlow_div").css("display", "none");//隐藏
    }
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
    $("#orgId").combobox({
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
    $("#userId").combobox({
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
    if (title == "修改设备信息") {
        var selectrow = $("#dgdeviceInfo").datagrid("getSelected");
        $("#frmdialogModel").form("reset");
        $("#frmdialogModel").form("load", selectrow);
        $("#deviceCode").attr("readonly", "readonly");
        if (selectrow != null) {
            if (selectrow.staMinute) {
                $("#staMinute").attr("checked", "checked");
            }
            if (selectrow.staHour) {
                $("#staHour").attr("checked", "checked");
            }
            if (selectrow.staDay) {
                $("#staDay").attr("checked", "checked");
            }
        }
    } else {
        $("#deviceCode").removeAttr("readonly");
    }
}

/* 初始化厂商ID、设备状态、区域ID,项目类型下拉框 */
function initConfigDeiveParam() {
    $.ajax({
        url: "../DeviceController/queryDevicemfrCodeDropDown",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    mfrCodeData[json.rows[i].code] = json.rows[i].name;
                }
                mfrCodeValue = json.rows[0].code;
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDevicestatusCodeDropDown",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    statusCodeData[json.rows[i].code] = json.rows[i].name;
                }
                statusValue = json.rows[0].code;
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDeviceAreaDropDown",
        type: "post",
        dataType: "json",
        data: {
            "id": "-1",
            "levelFlag": "-1"
        },
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    areaIdData[json.rows[i].id] = json.rows[i].name;
                }
                areaValue = json.rows[0].id;
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDevicePrincipleDropDown",
        type: "post",
        dataType: "json",
        data: {
            "devicePrinciple": "-1"
        },
        async: false,
        success: function (json) {
//			userIdData[0] = "---请选择---";
//			userValue = "0";
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    userIdData[json.rows[i].id] = json.rows[i].name;
                }
            }
        }
    });
    $.ajax({
        url: "../DeviceController/queryDeviceOversightUnit",
        type: "post",
        dataType: "json",
        data: {
            "orgId": "-1"
        },
        async: false,
        success: function (json) {
//		    orgIdData[0] = "---请选择---";// orgID对应的键值
//		    orgValue = 0; //orgdata默认的值
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    orgIdData[json.rows[i].id] = json.rows[i].name;
                }
            }

        }
    });
    $.ajax({
        url: "../DeviceProjectController/queryDeviceProject",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    projectIdData[json.rows[i].projectId] = json.rows[i].projectName;
                }
                projectValue = json.rows[0].projectId;
            }
        }
    });
}

//combobox的实现，导航处；实现   所属区域
$.ajax({
    url: "../DeviceController/queryDeviceAreaDropDown",
    type: "post",
    dataType: "json",
    data: {
        "id": "-1",
        "levelFlag": "-1"
    },
    async: true,
    success: function (json) {
        if (json.total > 0) {
            for (var i = 0; i < json.total; i++) {
                areaIdData[json.rows[i].id] = json.rows[i].name;
            }
            areaValue = json.rows[0].id;
            $("#searchDeviceAreaId").combobox({
                valueField: 'id',
                textField: 'name',
                data: json.rows,
                prompt: '所属区域...'
            });
        }
    }
});