/*******************************************************************************
 * 功能：监测视频设备信息 日期：2019-6-28 09:06:28
 ******************************************************************************/
var appendcontent = '<div id="dgDeviceVideoInfo"></div>'
    + '<div id="tbdgDeviceVideoInfo" style="padding:5px 10px;">'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-add\',plain:true" onclick="editDeviceVideoFunc(\'添加视频设备\',\'icon-add\',\'insertDeviceVideo\')">添加</a>'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true" onclick="editDeviceVideoFunc(\'修改视频设备\',\'icon-edit\',\'updateDeviceVideo\')">修改</a>'
    + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-cut\',plain:true" onclick="delDeviceVideoFunc()">删除</a>'
    + '&nbsp;<input id="searchDeviceVideoAreaId" class="easyui-combobox" style="width:200px;height:24px;" >'
    + '&nbsp;&nbsp;&nbsp;<input class="easyui-textbox" id="searchVideoName" data-options="onClickButton:function(){searchDeviceVideoFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
    + '</div>';

addPanel("设置视频监测设备", appendcontent);

var videoFlagData = {
    "1": "是",
    "2": "否"
};
var videoFlagDataValue = "2";

var videoTypeData = {
    "1": "有固定IP",
    "2": "无固定IP"
};
var videoTypeDataValue = "1";

var videoBrandData = {
    "1": "海康摄像头",
    "2": "大华摄像头"
};
var videoBrandDataValue = "1";

/* 初始化列表,表头 */
$("#dgDeviceVideoInfo").datagrid({
    view: myview,
    fit: true,
    border: false,
    pagination: true,
    fitColumns: false,
    singleSelect: true,
    pageList: [10, 50, 100, 150, 200, 250, 300],
    url: "../DeviceVideoController/queryDeviceVideo",
    pageSize: 10,
    autoRowHeight: false,
    rownumbers: true,
    toolbar: "#tbdgDeviceVideoInfo",
    columns: [[{
        field: 'videoId',
        checkbox: true
    }, {
        field: 'videoCode',
        title: '视频编号',
        width: 120,
        halign: 'center',
        align: 'center'
    }, {
        field: 'videoName',
        title: '视频名称',
        width: 120,
        halign: 'center',
        align: 'center'
    }, {
        field: 'videoBrand',
        title: '设备品牌',
        width: 120,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.videoBrand == '1') {
                return "海康威视";
            } else if (row.videoBrand == '2') {
                return "大华";
            } else {
                return "未知品牌";
            }
        }
    }, {
        field: 'videoType',
        title: '视频类型',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.videoType == '1') {
                return "有固定IP";
            } else if (row.videoType == '2') {
                return "无固定IP";
            } else {
                return "未知类型";
            }
        }
    }, {
        field: 'deviceCode',
        title: '所属设备',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            return row.deviceName
        }
    }, {
        field: 'userCode',
        title: '用户名',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'userPassword',
        title: '用户密码',
        width: 100,
        halign: 'center',
        align: 'center'
    }, {
        field: 'videoIp',
        title: '视频IP',
        width: 120,
        halign: 'center',
        align: 'center'
    }, {
        field: 'videoPort',
        title: '视频端口',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.videoPort == -1) {
                row.videoPort = null
            }
            return row.videoPort
        }
    }, {
        field: 'videoUrl',
        title: '直播URL',
        width: 200,
        halign: 'center',
        align: 'center'
    }, {
        field: 'videoRec',
        title: '回访URL',
        width: 200,
        halign: 'center',
        align: 'center'
    }, {
        field: 'videoToken',
        title: '视频Token',
        width: 200,
        halign: 'center',
        align: 'center'
    },
    // {
    //     field: 'videoX',
    //     title: '视频经度',
    //     width: 100,
    //     halign: 'center',
    //     align: 'center',
    //     formatter: function (value, row, index) {
    //         if (row.videoX == -1) {
    //             row.videoX = null
    //         }
    //         return row.videoX
    //     }
    // }, {
    //     field: 'videoY',
    //     title: '视频纬度',
    //     width: 100,
    //     halign: 'center',
    //     align: 'center',
    //     formatter: function (value, row, index) {
    //         if (row.videoY == -1) {
    //             row.videoY = null
    //         }
    //         return row.videoY
    //     }
    // },
    {
        field: 'videoFlag',
        title: '超标抓拍',
        width: 80,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.videoFlag == '1') {
                return "是";
            } else {
                return "否";

            }
        }
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
$("#dgDeviceVideoInfo").datagrid("getPager").pagination({
    total: 0
});

/* 查询设备 */
function searchDeviceVideoFunc() {
    $("#dgDeviceVideoInfo").datagrid("load", {
        "areaId": $("#searchDeviceVideoAreaId").combobox("getValue"),
        "videoName": $("#searchVideoName").val()
    });
}

/* 删除设备 */
function delDeviceVideoFunc() {
    var selectrow = $("#dgDeviceVideoInfo").datagrid("getChecked");
    var idArry = [];
    for (var i = 0; i < selectrow.length; i++) {
        idArry.push(selectrow[i].videoId);
    }
    if (selectrow.length < 1) {
        $.messager.alert("提示", "请选择删除的记录！", "warning");
        return false;
    }
    //是否确认进行记录删除使用
    $.messager.confirm("提示", '该视频设备将被清除，确定删除选定记录吗？', function (r) {
        if (r) {
            ajaxLoading();
            $.ajax({
                url: "../DeviceVideoController/deleteDeviceVideo",
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
                        $("#dgDeviceVideoInfo").datagrid('reload');
                    } else {
                        $.messager.alert("错误", json.detail, "error");
                    }
                }
            });
        }
    });
}

// 设备
var deviceCodeData = {};
var deviceValue = null;

/* 添加、修改视频设备 */
function editDeviceVideoFunc(title, icon, action) {
    initConfigDeiveParam();
    if (title == "修改视频设备") {
        var selectrow = $("#dgDeviceVideoInfo").datagrid("getSelections");
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
        content: '<form id="frmdialogModel" class="config-form"  style="width:400px;"></form>',
        buttons: [{
            text: "确定",
            iconCls: "icon-ok",
            handler: function () {
                if ($("#frmdialogModel").form("validate")) {
                    // var staFlow = $("#staFlow").combobox("getValue");
                    // if (staFlow == true) {
                    //     if ($("#pipeArea").val() == null || $("#pipeArea").val() == ""
                    //         || $("#pipeArea").val() <= 0) {
                    //         $.messager.alert("提示", "烟筒面积必须大于0！", "info");
                    //         return false;
                    //     }
                    // }
                    var formdataArray = $("#frmdialogModel").serializeArray();// 将表单数据序列化创建一个json数组
                    var formdataJosn = getFormJson(formdataArray);// 转换成json数组
                    ajaxLoading();
                    $.ajax({
                        url: "../DeviceVideoController/" + action,
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
                                $("#dgDeviceVideoInfo").datagrid('reload');
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
            name: "videoId",
            title: "视频ID",
            ishiden: true,
            value: "-1"
        }));
        htmlArr.push(createValidatebox({
            name: "videoCode",
            title: "视频编号",
            noBlank: true,
            type: 'maxLength[100]'
        }));
        htmlArr.push(createValidatebox({
            name: "videoName",
            title: "视频名称",
            noBlank: true,
            type: 'maxLength[100]'
        }));
        htmlArr.push(createComboboxEdit({
            name: "deviceCode",
            title: "所属设备",
            data: deviceCodeData,
            value: deviceValue,
            noBlank: true
        }));
        htmlArr.push(createCombobox({
            name: "videoBrand",
            title: "设备品牌",
            data: videoBrandData,
            valueField: 'code',
            textField: 'name',
            value: videoBrandDataValue
        }));
        htmlArr.push(createCombobox({
            name: "videoType",
            title: "设备类型",
            data: videoTypeData,
            valueField: 'code',
            textField: 'name',
            value: videoTypeDataValue
        }));
        htmlArr.push(createValidatebox({
            name: "videoIp",
            title: "视频IP",
            type: 'ip'
        }));
        htmlArr.push(createValidatebox({
            name: "videoPort",
            title: "视频端口",
            value: "8080"
        }));
        htmlArr.push(createValidatebox({
            name: "userCode",
            title: "登陆账号"
        }));
        htmlArr.push(createValidatebox({
            name: "userPassword",
            title: "账号密码"
        }));
        htmlArr.push(createCombobox({
            name: "videoFlag",
            title: "超标抓拍",
            data: videoFlagData,
            valueField: 'code',
            textField: 'name',
            value: videoFlagDataValue
        }));
        htmlArr.push(createValidatebox({
            name: "videoUrl",
            title: "直播URL"
        }));
        htmlArr.push(createValidatebox({
            name: "videoRec",
            title: "回访URL"
        }));
        htmlArr.push(createValidatebox({
            name: "videoToken",
            title: "视频Token"
        }));
        // htmlArr.push(createValidatebox({
        //         //     name: "videoX",
        //         //     title: "视频经度",
        //         //     noBlank: true,
        //         //     type: 'intOrFloat[73.55,135.083333]'
        //         // }));
        //         // htmlArr.push(createValidatebox({
        //         //     name: "videoY",
        //         //     title: "视频纬度",
        //         //     noBlank: true,
        //         //     type: 'intOrFloat[3.85,53.55]'
        //         // }));
        return htmlArr.join("");
    });
    /* 重绘窗口 */
    $.parser.parse("#dialogModel");
    $("#dialogModel").dialog("open");
    // $("#areaId").combobox({
    //     onHidePanel: function () {
    //         var valueField = $(this).combobox("options").valueField;
    //         var val = $(this).combobox("getValue");  //当前combobox的值
    //         var allData = $(this).combobox("getData");   //获取combobox所有数据
    //         var result = true;      //为true说明输入的值在下拉框数据中不存在
    //         for (var i = 0; i < allData.length; i++) {
    //             if (val == allData[i][valueField]) {
    //                 result = false;
    //                 break;
    //             }
    //         }
    //         if (result) {
    //             $(this).combobox("clear");
    //         }
    //     }
    // });
    if (title == "修改视频设备") {
        var selectrow = $("#dgDeviceVideoInfo").datagrid("getSelected");
        $("#frmdialogModel").form("reset");
        $("#frmdialogModel").form("load", selectrow);
    }
}

/* 初始化所属设备下拉框 */
function initConfigDeiveParam() {
    $.ajax({
        url: "../DeviceController/queryDevice",
        type: "post",
        dataType: "json",
        async: false,
        success: function (json) {
            if (json.total > 0) {
                for (var i = 0; i < json.total; i++) {
                    deviceCodeData[json.rows[i].deviceCode] = json.rows[i].deviceName;
                }
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
        "levelFlag": "4" //第四级别
    },
    async: true,
    success: function (json) {
        if (json.total > 0) {
            $("#searchDeviceVideoAreaId").combobox({
                valueField: 'id',
                textField: 'name',
                data: json.rows,
                prompt: '所属区域...'
            });
        }
    }
});