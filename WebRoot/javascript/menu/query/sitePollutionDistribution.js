/************************************
 * 功能：污染物年分布分析
 * 日期：2020-4-23 09:43:09
 ************************************/
var appendcontentPollution = '<div id="pollutionDistributionTab" class="easyui-tabs" data-options="fit:true">'
    + '<div title="污染物年分布"  selected="true" style="padding:10px" id="yearDistribution"></div>'
    + '<div title="污染物月分布"  style="padding:10px" id="monthDistribution"></div>'
    + '<div title="污染物日分布"  style="padding:10px" id="dayDistribution"></div>'
    + '</div>';
addPanel("污染物时间分布", appendcontentPollution);
var singleMonitorListD = [];
var getthingNameJsonD = {};
var comboboxJson = [];
var dataYearPollutionJson = {"deviceCode":"","monitorThing":""};
var dataMonthPollutionJson = {"deviceCode":"","monitorThing":""};
var dataDayPollutionJson = {"deviceCode":"","monitorThing":""};
$.ajax({
    url : "../MonitorStorageController/getAthorityMonitors",
    type : "post",
    dataType : "json",
    async:false,
    success : function(json) {
        comboboxJson = json;
        for(var i=0;i<json.length;i++){
            getthingNameJsonD[json[i].code] = json[i].name;
            singleMonitorListD.push(json[i].code);
        }
    }
});
//筛选设备监测物
function filterSingleStationMonitors(){
    var currTab =$('#pollutionDistributionTab').tabs('getSelected');
    var title = currTab.panel('options').title;
    var id="yearDistribution";
    if(title=="污染物月分布"){
        id="monthDistribution";
    }else if(title=="污染物日分布"){
        id="dayDistribution";
    }else{
        id="yearDistribution";
    }
    var devicecode = "";
    var station = $('#mytree').tree('getSelected');
    if(station != null || station != undefined){
        if(station.isDevice){
            devicecode = station.id;
        }else{
            $.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
            return false;
        }
    }else{
        $.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
        return false;
    }
    ajaxLoading();
    $.ajax({
        url : "../MonitorStorageController/getAthorityDeviceMonitors",
        type : "post",
        dataType : "json",
        data:{
            "deviceCode":devicecode
        },
        error:function(){
            ajaxLoadEnd();
        },
        success : function(json) {
            ajaxLoadEnd();
            $("#monitorThings"+id+"").combobox('clear');
            comboboxJson = [];
            getthingNameJson = {};
            singleMonitorList = [];
            if(json.length>0){
                comboboxJson = json;
                for(var i=0;i<json.length;i++){
                    getthingNameJsonD[json[i].code] = json[i].name;
                    singleMonitorListD.push(json[i].code);
                    $("#monitorThings"+id+"").combobox('loadData',comboboxJson);
                }
            }else{
                $("#monitorThings"+id+"").combobox('loadData',comboboxJson);
            }
        }
    });
}
tabContent("污染物年分布","yearDistribution");
tabContent("污染物月分布","monthDistribution");
tabContent("污染物日分布","dayDistribution");
function tabContent(title,id) {
    var contents = getContent(title, id);
    $('#' + id).append(contents);
    $.parser.parse('#' + id);
    $("#monitorThings"+id+"").combobox({
        data:comboboxJson,
        method:'post',
        valueField:'code',
        textField:'describe',
        name:'name',
        panelHeight:'auto',
        value:comboboxJson[0].code,
        onShowPanel: function () {
            // 动态调整高度
            if (comboboxJson.length < 20) {
                $(this).combobox('panel').height("auto");
            }else{
                $(this).combobox('panel').height(300);
            }
        }
    });

    if(id !="yearDistribution"){
        $("#date"+id+"").datebox('setValue',formatterDate(new Date()));
    }
}

function getContent(title,id) {
    var contents = '';
    var id="yearDistribution";
    if(title=="污染物月分布"){
        id="monthDistribution";
        contents =  '<div class="easyui-layout" data-options="fit:true">'
            + '<div data-options="region:\'north\',border:false">'
            + '<div id="tbQuery' + id + '" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
            + '监控站点：<span id="monitorStation' + id + '"  style="width:150px;">无</span>'
            + '&nbsp;&nbsp;&nbsp'
            + '监控物质：<input id="monitorThings' + id + '" class="easyui-combobox" style="width:150px;">'
            + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterSingleStationMonitors()" title="筛选监控点监控物"></a>'
            + '<label for="datetimepicker" style="margin-left:10px;">时间：</label>'
            +'<input type="text" style="text-align: center;display:inline-block;width:120px;"  id="date' + id + '" class="easyui-datebox" required="required">'
            + '&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-search\',plain:true" onclick="searchMonthDistribution()">查询</a>'
            + '</div>'
            + '</div>'
            + '<div data-options="region:\'center\',border:false" style="overflow:hidden;">'
            + '<iframe id="monthCalendarFrame" src="../javascript/menu/sitePollutionMonth.html" frameborder=\'no\' border=\'0\' marginwidth=\'0\' marginheight=\'0\' scrolling=\'no\' allowtransparency=\'yes\' style=\'width:100%;height:100%;\'></iframe>'
            + '</div>'
            + '</div>';
    }else if(title=="污染物日分布"){
        id="dayDistribution";
        contents =  '<div class="easyui-layout" data-options="fit:true">'
            + '<div data-options="region:\'north\',border:false">'
            + '<div id="tbQuery' + id + '" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
            + '监控站点：<span id="monitorStation' + id + '"  style="width:150px;">无</span>'
            + '&nbsp;&nbsp;&nbsp'
            + '监控物质：<input id="monitorThings' + id + '" class="easyui-combobox" style="width:150px;">'
            + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterSingleStationMonitors()" title="筛选监控点监控物"></a>'
            + '<label  style="margin-left:10px;">时间：</label>'
            +'<input type="text" class="easyui-datebox" required="required" style="text-align: center;width:120px;"  id="date' + id + '">'
            + '&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-search\',plain:true" onclick="searchDayDistribution()">查询</a>'
            + '</div>'
            + '</div>'
            + '<div data-options="region:\'center\',border:false"  style="overflow:hidden;">'
            + '<iframe id="dayCalendarFrame" src="../javascript/menu/sitePollutionDay.html" frameborder=\'no\' border=\'0\' marginwidth=\'0\' marginheight=\'0\' scrolling=\'no\' allowtransparency=\'yes\' style=\'width:100%;height:100%;\'></iframe>'
            + '</div>'
            + '</div>';
    }else{
        id="yearDistribution";
        contents =  '<div class="easyui-layout" data-options="fit:true">'
            + '<div data-options="region:\'north\',border:false">'
            + '<div id="tbQuery' + id + '" style="padding:5px 8px;border-bottom:1px solid #ddd;">'
            + '监控站点：<span id="monitorStation' + id + '"  style="width:150px;">无</span>'
            + '&nbsp;&nbsp;&nbsp'
            + '监控物质：<input id="monitorThings' + id + '" class="easyui-combobox" style="width:150px;">'
            + '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" style="margin:0px 0px 0px 1px;" onclick="filterSingleStationMonitors()" title="筛选监控点监控物"></a>'
            + '&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-search\',plain:true" onclick="searchYearDistribution()">查询</a>'
            + '</div>'
            + '</div>'
            + '<div data-options="region:\'center\',border:false" style="overflow:hidden;">'
            + '<iframe id="yearCalendarFrame" src="../javascript/menu/sitePollutionYear.html" frameborder=\'no\' border=\'0\' marginwidth=\'0\' marginheight=\'0\' scrolling=\'no\' allowtransparency=\'yes\' style=\'width:100%;height:100%;\'></iframe>'
            + '</div>'
            + '</div>';
    }

    return contents;
}

function getYearParams(){
    var devicecode = "";
    var id="yearDistribution";
    var station = $('#mytree').tree('getSelected');
    if(station != null || station != undefined){
        if(station.isDevice){
            devicecode = station.id;
        }else{
            $.messager.alert("提示", "请选择一个监测站点进行筛选！", "error");
            return false;
        }
    }else{
        $.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
        return false;
    }
    $("#monitorStation"+id).html(station.text);
    var monitorThing = $('#monitorThings'+id).combobox('getValue');
    if(monitorThing==""){
        $.messager.alert("提示", "请选择一个监控物！！", "error");
        return false;
    }
    dataYearPollutionJson["deviceCode"] = devicecode;
    dataYearPollutionJson["monitorThing"] = monitorThing;
}

function searchYearDistribution(){
    getYearParams();
    $('#yearCalendarFrame').attr('src', $('#yearCalendarFrame').attr('src'));
}

$('#pollutionDistributionTab').tabs({
    onSelect: function (title, index) {
        if(title=="污染物年分布"){
            $('#mytree').tree({
                checkbox: false
            });
        }else{
            $('#mytree').tree({
                checkbox: true
            });
        }
    }
});

function getMonthParams(){
    var devicecode = "";
    var station = $('#mytree').tree('getChecked');
    if(station != null || station != undefined){
        for(var i=0;i<station.length;i++){
            if(station[i] != null && station[i].isDevice){
                devicecode = station[i].id;
                break;
            }
        }
        if(devicecode == null || devicecode == ""){
            $.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
            return false;
        }
    }else{
        $.messager.alert("提示", "请选择一个监测站点进行物质筛选！", "error");
        return false;
    }
    var id="monthDistribution";
    $("#monitorStation"+id).html(station.text);
    var monitorThing = $('#monitorThings'+id).combobox('getValue');
    if(monitorThing==""){
        $.messager.alert("提示", "请选择一个监控物！！", "error");
        return false;
    }
    var searchTime =  $("#date"+id).datebox('getValue');
    dataMonthPollutionJson["deviceCode"] = devicecode;
    dataMonthPollutionJson["monitorThing"] = monitorThing;
    dataMonthPollutionJson["searchTime"] = searchTime;
}

function searchMonthDistribution(){
    getMonthParams();
    $('#monthCalendarFrame').attr('src', $('#monthCalendarFrame').attr('src'));
}

function searchDayDistribution(){
    getMonthParams();
    $('#dayCalendarFrame').attr('src', $('#dayCalendarFrame').attr('src'));
}