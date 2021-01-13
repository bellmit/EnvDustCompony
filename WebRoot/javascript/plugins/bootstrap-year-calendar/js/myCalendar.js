var pollutionCode = "";//监控物质
var selectedYear = 2019;//被点击年份
$(function () {
    getMonitorThingType("monitorTypeSelect","pollutionYearBtnGroup");
    pollutionCode = $("#pollutionYearBtnGroup .btn:first").attr("id");
    getSiteTable("dropdownMenuYear");
    $('#datepicker').parent().datepicker({
        "autoclose": true, "format": "yyyy-mm-dd", "language": "zh-CN"
    });

    /*
     * 点击年份时
     */
    $("#calendar_wr").on('renderEnd', function (e) {
      if (Number(selectedYear) != e.currentYear.toString()) {
            selectedYear = e.currentYear;
            getPollutionYearData();
       }
    });
    initCalendar();
    getPollutionYearData();
});

/**
 * 获取年度污染日历数据
 */
function getPollutionYearData(){
    var url = "/comprehensiveAnalysis/getSitePolluteYearDistributeData";
    var deviceCode =  "";
    if($("#previewFeedbackTable").bootstrapTable("getSelections").length>0){
        deviceCode = $("#previewFeedbackTable").bootstrapTable("getSelections")[0].deviceCode
    }
    ajaxLoading("正在处理数据，请稍候...");
    $.ajax({
        url: url,
        type: "post",
        dataType: "json",
        data: {
            "thingCode":pollutionCode,
            "deviceCode": deviceCode,//1234567890000a
            "year": selectedYear
        },
        success: function (json) {
            if(json.success){
               var mycalendarData = json.data;
                handleGetPollutionYearData(mycalendarData,pollutionCode);
            }else{
                $("#stationNameInfo").html("");
                showCalendar(null);
            }
            ajaxLoadEnd();
        },
        error:function (e) {
            ajaxLoadEnd();
            console.log("加载数据异常" + e);
        }
    });
}

/**
 * 解析数据
 */
function handleGetPollutionYearData(data,pollution){
    var aqiDataArry = [];
    var pollutiondayInfo ;
    if (data == null)
        return;
    for (var i = 0; i < data.length; i++) {
        var datestr = data[i].dataDate;
        var pydmData = data[i].pydmList;
        var quality1 = "";
        var color = "";
        var labelstr = '';
        for(var j=0;j<pydmData.length;j++){
            if(pollution==pydmData[j].thingCode){
                color = pydmData[j].levelColor;
                quality1 = pydmData[j].levelName;
            }

            if(j==0) {
                labelstr = '<hr style="margin:0px"/><span style="color:#333">' + pydmData[j].thingName + ': ' + (pydmData[j].thingAvg == "-99" || pydmData[j].thingAvg == null ? "--" : pydmData[j].thingAvg );
            }else{
                labelstr = labelstr + '<br/>'+pydmData[j].thingName+': ' + (pydmData[j].thingAvg == "-99" || pydmData[j].thingAvg == null ? '--' : Math.round(pydmData[j].thingAvg))
            }
        }
        labelstr = labelstr + '<br/>污染级别: <span style="color:' + color + '">' + quality1 + '</span></span>';
        var yy = data[i].dataYear;
        var mm = 0;
        if(data[i].dataMonth!="" && data[i].dataMonth!=undefined && data[i].dataMonth!=0){
            mm = (Number(data[i].dataMonth)-1);
        }
        var dd = data[i].dataDay;
        if(color==null){
            color = "gray";
        }
        aqiDataArry.push({
            name: datestr,
            location:labelstr,
            color: color,
            startDate: new Date(yy, mm, dd),
            endDate: new Date(yy, mm, dd)
        });
        pollutiondayInfo = data[0].levelMap;
    }
    showPollutionDays("day",pollutiondayInfo);
    var deviceName = $("#previewFeedbackTable").bootstrapTable("getSelections")[0].deviceName;
    $("#stationNameInfo").html(deviceName);
    showCalendar(aqiDataArry);
}

/**
 * 显示年度污染日历
 * @param data
 */
function showCalendar(data) {
    $('#calendar_wr'). mouseOnDay(function (e){
        if (e.events.length > 0) {
            var content = '';
            for (var i in e.events) {
                content += '<div class="event-tooltip-content">'
                    + '<div class="event-name" style="color:black">' + e.events[i].name + '</div>'
                    + '<div class="event-location">' + e.events[i].location + '</div>'
                    + '</div>';
            }

            $(e.element).popover({
                trigger: 'manual',
                container: 'body',
                html: true,
                content: content
            });

            $(e.element).popover('show');
        }
    });
    $('#calendar_wr').mouseOutDay(function (e) {
        if (e.events.length > 0) {
            $(e.element).popover('hide');
        }
    });
    $('#calendar_wr').dayContextMenu(function (e) {
        $(e.element).popover('hide');
    });

    $('#calendar_wr').data('calendar').setDataSource(data);
}

/**
 * 初始化日历插件
 */
function initCalendar(){
    $('#calendar_wr').calendar({
        enableContextMenu: true,
        enableRangeSelection: true,
        style: "background",
        language:"zh",
    });
}

