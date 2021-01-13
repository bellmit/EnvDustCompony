/*******************************************************************************
 * 功能：网络视频 日期：2016-3-23 13:41:09
 ******************************************************************************/
var appendcontent = '<div id="dgVideoMonitorInfo"></div>'
    + '<div id="tbdgVideoMonitorInfo" style="padding:5px 10px;">'
    + '&nbsp;<input id="searchMonitorVideoAreaId" class="easyui-combobox" style="width:200px;height:24px;" >'
    + '&nbsp<input class="easyui-textbox" id="searchMonitorVideoName" data-options="onClickButton:function(){searchVideoMonitorFunc()},buttonText:\'查询\',buttonIcon:\'icon-search\',prompt:\'设备名称...\'" style="width:200px;height:24px;">'
    + '</div>';
addPanel("网络视频", appendcontent);

/* 初始化列表,表头 */
$("#dgVideoMonitorInfo").datagrid({
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
    toolbar: "#tbdgVideoMonitorInfo",
    columns: [[{
        field: 'videoId',
        hidden: true
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
        field: 'videoX',
        title: '视频经度',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.videoX == -1) {
                row.videoX = null
            }
            return row.videoX
        }
    }, {
        field: 'videoY',
        title: '视频纬度',
        width: 100,
        halign: 'center',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.videoY == -1) {
                row.videoY = null
            }
            return row.videoY
        }
    }, {
        field: "operateImage", title: "图像抓拍", halign: "center", align: 'center', width: '9%',
        formatter: function (value, row, index) {
            var str = '<a href="#this" title="查看图片" class="easyui-tooltip" '
                + 'onclick="searchImage(' + index + ');">'
                + '<img src="../images/video.png" class="operate-button"></a>';
            return str;
        }
    }, {
        field: "operateVideo", title: "视频监控", halign: "center", align: 'center', width: '9%',
        formatter: function (value, row, index) {
            var str = '<a href="#this" title="查看视频" class="easyui-tooltip" '
                + 'onclick="searchImage(' + index + ');">'
                + '<img src="../images/video.png" class="operate-button"></a>';
            return str;
        }
    }
    // , {
    //     field: 'optUserName',
    //     title: '操作者',
    //     width: 100,
    //     halign: 'center',
    //     align: 'center'
    // }, {
    //     field: 'optTime',
    //     title: '操作时间',
    //     width: 150,
    //     halign: 'center',
    //     align: 'center'
    // }
    ]]
}).datagrid('doCellTip', {cls: {'max-width': '500px'}});

/* 定义分页器的初始显示默认值 */
$("#dgVideoMonitorInfo").datagrid("getPager").pagination({
    total: 0
});

function searchImage(index) {

}

// function searchVideo(index) {
//     var record = $("#dgVideoMonitorInfo").datagrid("getRows")[index];
//     $.ajax({
//         url: "../DeviceController/getVideoDeviceUrlByMn",
//         type: "post",
//         dataType: "json",
//         data: {
//             "deviceMn": record.deviceMn
//         },
//         error: function (json) {
//             ajaxLoadEnd();
//         },
//         success: function (json) {
//             if (json.detail) {
//                 var tempwindow = window.open();
//                 tempwindow.location = json.detail;
//             } else {
//                 $.messager.alert("提示", "无法找到视频资源，请检查网络！", "warning");
//             }
//
//         }
//     });
// }

/* 查询设备 */
function searchVideoMonitorFunc() {
    $("#dgVideoMonitorInfo").datagrid("load", {
        "areaId": $("#searchMonitorVideoAreaId").combobox("getValue"),
        "videoName": $("#searchMonitorVideoName").val()
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
            $("#searchMonitorVideoAreaId").combobox({
                valueField: 'id',
                textField: 'name',
                data: json.rows,
                prompt: '所属区域...'
            });
        }
    }
});
