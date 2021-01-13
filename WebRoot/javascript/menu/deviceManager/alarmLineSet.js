/*******************************************************************************
 * 功能：设备信息 日期：2016-5-31 09:42:09
 ******************************************************************************/
var appendcontent = '<div id="dgalarmLineInfo"></div>'
    + '<div id="tbdgalarmLineInfo" style="padding:5px 10px;">'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="setAlarmLineFunc(\'设置报警门限\',\'icon-edit\',\'setAlarmLine\')">设置报警门限</a>'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delAlarmLineFunc()">删除</a>'
    + '&nbsp;&nbsp;&nbsp;所属区域：<input class="easyui-combobox" name="alsAreaCombox" id="alsAreaCombox" style="width:150px;"/>'
    + '&nbsp;&nbsp;&nbsp;项目类型：<input class="easyui-combobox" name="projectTypeCombox" id="projectTypeCombox" style="width:150px;"/>'
    + '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchAlsDeviceName" data-options="onClickButton:function(){searchAlarmLineFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
    + '</div>';
addPanel("设置报警门限", appendcontent);

initAlsAreaCombox();
initProjectTypeCombox("projectTypeCombox");
var alarmthingData = {};
var alarmthingValue = null;
var dataFlagData = {
    "1": "实测值",
    "2": "折算值"
};
var dataFlagValue = "1";
var levelNoData = {
    "1": "第一级",
    "2": "第二级",
    "3": "第三级"
};
var levelNoValue = "1";
/* 初始化列表,表头 */
$("#dgalarmLineInfo").datagrid({
    view: myview,
    fit: true,
    border: false,
    pagination: true,
    fitColumns: false,
    pageList: [10, 50, 100, 150, 200, 250, 300],
    url: "../DeviceAlarmSetController/getDeviceAlarmSet",
    pageSize: 10,
    autoRowHeight: false,
    rownumbers: true,
    queryParams: {
        projectId: $("#projectTypeCombox").combobox('getValue')
    },
    toolbar: "#tbdgalarmLineInfo",
    columns: [[{
        field: 'dasId',
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
        width: 130,
        halign: 'center',
        align: 'center'
    }, {
        field: 'projectName',
        title: '项目类型',
        width: 130,
        halign: 'center',
        align: 'center'
    }, {
        field: 'areaName',
        title: '所属区域',
        width: 180,
        halign: 'center',
        align: 'center'
    }, {
        field: 'thingCode',
        title: '监测物名称',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.thingName
        }
    }, {
        field: 'maxValue',
        title: '报警上限',
        width: 100,
        halign: 'center',
        align: 'center',
    }, {
        field: 'minValue',
        title: '报警下限',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'levelNo',
        title: '报警等级',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (value == "1") {
                return "第一级";
            } else if (value == "2") {
                return "第二级";
            } else if (value == "3") {
                return "第三级";
            } else {
                return "未定义";
            }
        }
    }, {
        field: 'dataFlag',
        title: '数据标识',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (value == "2") {
                return "折算值";
            } else {
                return "实测值";
            }
        }
    }, {
        field: 'cnName',
        title: '计划类型',
        width: 150,
        halign: 'center',
        align: 'center'
    }, {
        field: 'statusName',
        title: '执行状态',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'excuteTime',
        title: '计划执行时间',
        width: 130,
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
}).datagrid('doCellTip', {
    cls: {
        'max-width': '500px'
    }
});

/* 定义分页器的初始显示默认值 */
$("#dgalarmLineInfo").datagrid("getPager").pagination({
    total: 0
});

/* 查询设备 */
function searchAlarmLineFunc() {
    $("#dgalarmLineInfo").datagrid("reload", {
        "deviceName": $("#searchAlsDeviceName").val(),
        "areaId": $("#alsAreaCombox").combobox('getValue'),
        "projectId": $("#projectTypeCombox").combobox('getValue')
    });
}

/* 删除设备 */
function delAlarmLineFunc() {
    var selectrow = $("#dgalarmLineInfo").datagrid("getChecked");
    var idArry = [];
    var alertContent = "确定删除选定记录吗？";
    for (var i = 0; i < selectrow.length; i++) {
        var excuteTime = selectrow[i].excuteTime;
        if (excuteTime != null && excuteTime != '') {
            var start = new Date(excuteTime.replace("-", "/").replace("-", "/"));
            var nowTime = new Date();
            if (nowTime > start) {
                alertContent = "已执行此计划，确认删除吗？";
                //$.messager.alert("提示", "超过计划执行时间，不能删除！", "warning");
                //return false;
            }
        } else {
//				$.messager.alert("提示", "超过计划执行时间，不能删除！", "warning");
//				return false;  
            alertContent = "已执行此计划，确认删除吗？";
        }
        idArry.push(selectrow[i].dasId);
    }
    if (selectrow.length < 1) {
        $.messager.alert("提示", "请选择删除的记录！", "warning");
        return false;
    }
    $.messager.confirm("提示", alertContent, function (r) {
        if (r) {
            ajaxLoading();
            $.ajax({
                url: "../DeviceAlarmSetController/deleteDeviceAlarmSet",
                type: "post",
                dataType: "json",
                data: {"list": idArry},
                success: function (json) {
                    ajaxLoadEnd();
                    if (json.result) {
                        $.messager.alert("提示", "删除成功！", "info");
                        $("#dgalarmLineInfo").datagrid('reload');
                    } else {
                        $.messager.alert("错误", json.detail, "error");
                    }
                }
            });
        }
    });
}

/* 初始化所属区域下拉框 */
function initAlsAreaCombox() {
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
            $("#alsAreaCombox").combobox({
                data: json.rows,
                method: 'post',
                valueField: 'id',
                textField: 'name',
                onShowPanel: function () {
                    // 动态调整高度
                    if (json.rows.length < 20) {
                        $(this).combobox('panel').height("auto");
                    } else {
                        $(this).combobox('panel').height(300);
                    }
                }
            });
        }
    });
}

/* 设置报警门限 */
function setAlarmLineFunc() {
    initAlarmLineParam();
    $("#dialogModel")
        .dialog(
            {
                width: 700,
                height: 424,
                title: "设置报警门限",
                inline: true,
                modal: true,
                iconCls: "icon-edit",
                maximized: false,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                resizable: true,
                closed: true,
                content: '<div class="easyui-layout" data-options="fit:true">'
                    + '<div data-options="region:\'west\',title:\'可监控的站点\'" style="width:240px;">'
                    + '<div class="easyui-layout" data-options="fit:true" >'
                    + '<div data-options="region:\'center\',border:false">'
                    + '<div style="margin-top:4px;">项目类型：<input name="projectTypeDCombox" id="projectTypeDCombox" style="width:150px;"/></div>'
                    + '<ul id="alarmLineDeviceTree" oncontextmenu="return false"></ul>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '<div data-options="region:\'center\',fit:true">'
                    + '<form id="frmdialogModel" class="config-form">'
                    + '<div id="dgAlarmLineSet"></div>'
                    + '<div id="tbdgAlarmLineSet" style="padding:5px 10px;">'
                    + '<label style="color:red;font-size:9px;display: inline;margin-top:8px;margin-left:15px;vertical-align:middle;"><span style="vertical-align:middle;">提示：未选择仅平台处理，保存后信息会下发至设备，下发失败不影响系统报警</span></label>'
                    + '</div>' + '</form>' + '</div>' + '</div>',
                buttons: [
                    {
                        text: "保存",
                        iconCls: "icon-ok",
                        handler: function () {
                            if ($("#frmdialogModel").form(
                                "validate")) {
                                var alarmLineDeviceTreeSelectId = [];
                                var liststr = "";
                                var selections = $("#alarmLineDeviceTree").tree('getChecked');
                                for (var i = 0; i < selections.length; i++) {
                                    if (selections[i].isDevice) {// 判断是监控点
                                        alarmLineDeviceTreeSelectId.push(selections[i].id);
                                    }
                                }
                                if (alarmLineDeviceTreeSelectId.length == 0) {
                                    $.messager.alert("错误", "请至少选择一个站点！", "error");
                                    return;
                                } else {
                                    liststr = alarmLineDeviceTreeSelectId;
                                }
                                ajaxLoading();
                                $.ajax({
                                    url: "../DeviceAlarmSetController/insertDeviceAlarmSet",
                                    type: "post",
                                    dataType: "json",
                                    async: false,
                                    data: {
                                        "thingCode": $("#thingCode").combobox('getValue'),
                                        "maxValue": $("#maxValue").val(),
                                        "minValue": $("#minValue").val(),
                                        "dataFlag": $("#dataFlag").combobox('getValue'),
                                        "levelNo": $("#levelNo").combobox('getValue'),
                                        "excuteTime": $("#excuteTime").datetimebox('getValue'),
                                        "noDown": $('#noDown').is(':checked'),
                                        "list": liststr
                                    },
                                    error: function (json) {
                                        ajaxLoadEnd();
                                    },
                                    success: function (json) {
                                        ajaxLoadEnd();
                                        if (json.result) {
                                            $("#dialogModel").dialog("close");
                                            $.messager.alert("提示", "设置门限报警成功！", "info");
                                            searchAlarmLineFunc();
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
    initProjectTypeCombox("projectTypeDCombox");
    /*var pojectId = $("#projectTypeDCombox").combobox("getValue");
	initTreeNoe("alarmLineDeviceTree", '../TreeController/getAreaTree', {"parentId":"","projectId":pojectId},
			true);*/

    /* 初始化表单 */
    $("#dgAlarmLineSet").html(function () {
        var htmlArr = [];
        htmlArr.push(createCombobox({
            name: "thingCode",
            title: "所属物质",
            data: alarmthingData,
            value: alarmthingValue,
            noBlank: true
        }));
        htmlArr.push(createValidatebox({
            name: "maxValue",
            title: "报警上限",
            noBlank: true,
            type: "intOrFloat[-32768,32767]"
        }));
        htmlArr.push(createValidatebox({
            name: "minValue",
            title: "报警下限",
            noBlank: true,
            type: "intOrFloat[-32768,32767]"
        }));
        htmlArr.push(createCombobox({
            name: "dataFlag",
            title: "数据标识",
            data: dataFlagData,
            value: dataFlagValue,
            noBlank: true
        }));
        htmlArr.push(createCombobox({
            name: "levelNo",
            title: "报警等级",
            data: levelNoData,
            value: levelNoValue,
            noBlank: true
        }));
        htmlArr.push(createDatetimebox({
            name: "excuteTime",
            title: "计划执行时间",
            showSeconds: true,
            noBlank: true
        }));
        htmlArr.push(createCheckbox({
            name: "noDown",
            title: "仅平台处理，无指令下发到设备",
            showSeconds: true,
            checked: true
        }));
        return htmlArr.join("");
    });
    /* 重绘窗口 */
    $.parser.parse("#dialogModel");
    $("#dialogModel").dialog("open");
    $("#excuteTime").datetimebox('setValue', formatterDateHMS(new Date()));

    /* 初始化监测物 */
    function initAlarmLineParam() {
        $.ajax({
            url: "../MonitorStorageController/getAthorityMonitors",
            type: "post",
            dataType: "json",
            async: false,
            success: function (json) {
                if (json.length > 0) {
                    for (var i = 0; i < json.length; i++) {
                        alarmthingData[json[i].code] = json[i].name;
                    }
                    alarmthingValue = json[0].code;
                }
            }
        });
    }

}
