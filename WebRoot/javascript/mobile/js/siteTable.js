var siteTable = "<div  id=\"siteTable\" style='padding-right: 0px;padding-left: 0px;'>\n" +
    "            <!--站点选择-->\n" +
    "           <ul class=\"col-md-12\" id=\"siteSelect\" style='list-style-type: none;'>\n" +
    "            </ul>\n" +
    "            <div class=\"col-md-12\"   style=\"position: relative;padding: 5px;text-align: right;background:#fff;border-top:1px solid #f6f6f6;\">" +
    "                  <button type=\"button\" class=\"btn btn-info btn-sm\" onclick=\"searchMapData()\">确 定</button>\n" +
    "            </div>"+

    "        </div>";

var deviceTypeId = "";
//获取站点信息
function getSiteTable(id){
    $('#'+id).append(siteTable);
    getMonitorList("siteSelect");
    deviceTypeId = $("#siteSelect li:first").attr("id");
    deviceTypeId = deviceTypeId.trim();
}

function drowdownSiteTable(){
    $('#dropdownMenuYear').slideToggle();
    $('.fa-angle-down').toggleClass('ihidden');
    $('.fa-angle-up').toggleClass('ihidden');
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
                    htmlArr.push("<li id="+code+" class='active'><a id="+code+"  title="+describe+" href='javascript:void(0)' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;color:black;cursor:pointer;' onclick=\"onClickSite(this)\">"+describe+"</a></li>");
                    $("#hiddenNameParam").val(json[i].name);
                    $("#hiddenCodeParam").val(json[i].code);
                    $("#dropdownMenu1 .thingSpan").text(describe);
                }else{
                    htmlArr.push("<li id="+code+"><a id="+code+"  title="+describe+" href='javascript:void(0)' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;color:black;cursor:pointer;' onclick=\"onClickSite(this)\">"+describe+"</a></li>");
                }
         /*       if (i == 0) {
                    btnlist = '<div class="col-md-6"><a class=\" siteSelect deviceTypeclass\" id=\"' + code + '\" style=\"background-color: #e89042;color: #272727;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;\" onclick=\"onClickSite(this)\">'+deviceName +'</a></div>';

                } else {
                    btnlist = btnlist + '<div class="col-md-6"><a  class=\" siteSelect deviceTypeclass\" id=\"' + code + '\" style=\"background-color: #fefefe;color: #272727;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;\"  onclick=\"onClickSite(this)\">'+deviceName +'</a></div>\n';
                }*/
            }
            $("#" + id).html(htmlArr.join(""));
        }
    })
}

// 站点点击事件
function onClickSite(e) {
    var self = $(e);
   // $(".siteSelect").removeAttr("style");
   // self.attr("style","background-color: #e89042;color: #272727;padding:5px 10px;");
    $("#siteSelect li").removeClass("active");		//取消其他监控站点的选中状态
    self.parent("li").addClass("active");
    $("#hiddenNameParam").val(self.text());
    $("#hiddenCodeParam").val(self.attr("id"));
   // $("#dropdownMenu1").text(self.text());
}

function searchMapData(){
    var thingCode = $("#hiddenCodeParam").val();
    $("#dropdownMenu1 .thingSpan").text($("#hiddenNameParam").val());
    $('.fa-angle-down').toggleClass('ihidden');
    $('.fa-angle-up').toggleClass('ihidden');
    $("._1BGO").removeAttr("style");
    loadMapDate(thingCode);
    $('#dropdownMenuYear').slideToggle();
}
