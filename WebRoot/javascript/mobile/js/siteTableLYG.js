var deviceTypeId = "";
//获取站点信息
function getSiteTable(id){
   // $('#'+id).append(siteTable);
    getMonitorList("siteSelectLYG");
    deviceTypeId = $("#siteSelectLYG li:first").attr("id");
    deviceTypeId = deviceTypeId.trim();
}

/**
 * 获取监控物质
 */
function  getMonitorList(id){
    $("#" + id).html("");
    $.ajax({
        url: "./../../../MonitorStorageController/getAthorityMonitors",
        type: "post",
        async:false,
        dataType: "json",
        success: function (json) {
            var htmlArr = [];
            for (var i = 0; i < json.length; i++) {
                var code = json[i].code;
                var describe = json[i].describe;
                if(i==0){
                    htmlArr.push("<li id="+code+" class='active'><a id="+code+"  title="+describe+" href='javascript:void(0)' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;color:#fff;cursor:pointer;' onclick=\"onClickSite(this)\">"+describe+"</a></li>");
                    $("#hiddenNameParam").val(json[i].name);
                    $("#hiddenCodeParam").val(json[i].code);
                    $("#dropdownMenu1 .thingSpan").text(describe);
                }else{
                    htmlArr.push("<li id="+code+"><a id="+code+"  title="+describe+" href='javascript:void(0)' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;color:#fff;cursor:pointer;' onclick=\"onClickSite(this)\">"+describe+"</a></li>");
                }
            }
            $("#" + id).html(htmlArr.join(""));
        }
    })
}

// 站点点击事件
function onClickSite(e) {
    var self = $(e);
    $("#siteSelectLYG li").removeClass("active");		//取消其他监控站点的选中状态
    self.parent("li").addClass("active");
    $("#hiddenNameParam").val(self.text());
    $("#hiddenCodeParam").val(self.attr("id"));
    loadMapDate(self.attr("id"));
}

/*function searchMapData(){
    var thingCode = $("#hiddenCodeParam").val();
    $("#dropdownMenu1 .thingSpan").text($("#hiddenNameParam").val());
    $('.fa-angle-down').toggleClass('ihidden');
    $('.fa-angle-up').toggleClass('ihidden');
    $("._1BGO").removeAttr("style");
    loadMapDate(thingCode);
    $('#dropdownMenuYear').slideToggle();
}*/
