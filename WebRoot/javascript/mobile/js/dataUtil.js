var myChart1 = null;
function mainlogOutFunc(){
    $.ajax({
        url : "./../../../UserController/toLogout",
        type : "post",
        dataType : "json",
        error : function(json) {
            $.messager.confirm("提示", '退出请求未响应，是否强制退出？', function(r) {
                if (r) {
                    window.location.href = "../../loginPhone.html";
                }
            });
        },
        success : function(json) {
            if (json.result) {
                window.location.href = "../../loginPhone.html";
            } else {
                $.messager.alert("错误", json.detail, "error");
            }
        }
    });
}
function initLayout() {
    var h = document.documentElement.clientHeight - 90;
    $('#mapDiv').height(h);
}

/**
 * 给左侧弹出菜单赋值
 */
function initSidebarMenu(id){
    var htmlArry = [];
    htmlArry.push('<li ><a href="indexWeb.html"> <i class="fa fa-home"></i> <span>首页</span></a></li>');
    htmlArry.push('<li><a href="mapMonitorWeb.html"> <i class="fa fa-map-marker"></i> <span>地图</span></a></li>');
    htmlArry.push('<li><a href="dataQueryWeb.html"><i class="fa fa-list"></i> <span>数据</span></a></li>');
    htmlArry.push('<li><a href="chartQueryWeb.html"><i class="fa fa-line-chart"></i> <span>趋势</span></a></li>');
    htmlArry.push('<li><a href="alarmQueryWeb.html"><i class="fa fa-warning"></i> <span>报警</span></a></li>');
    htmlArry.push('<li><a href="#" onclick="mainlogOutFunc()"> <i class="fa fa-sign-out"></i> <span>注销</span></a></li>');
    $("#"+id).append(htmlArry.join(""));
    $('.sidebar-menu').removeClass("selected");
    if(id=="sideBarMenuIndex"){
        $('.sidebar-menu li:nth-child(1)').addClass("selected");
    }else if(id == "sideBarMenuMap"){
        $('.sidebar-menu li:nth-child(2)').addClass("selected");
    }else if(id == "sideBarMenuData"){
        $('.sidebar-menu li:nth-child(3)').addClass("selected");
    }else if(id == "sideBarMenuChart"){
        $('.sidebar-menu li:nth-child(4)').addClass("selected");
    }else if(id == "sideBarMenuAlarm"){
        $('.sidebar-menu li:nth-child(5)').addClass("selected");
    }
}

//根据经纬极值计算绽放级别。
function getZoom (maxLng, minLng, maxLat, minLat) {
    var zoom = ["50","100","200","500","1000","2000","5000","10000","20000","25000","50000","100000","200000","500000","1000000","2000000"]//级别18到3。
    var pointA = new BMap.Point(maxLng,maxLat);  // 创建点坐标A
    var pointB = new BMap.Point(minLng,minLat);  // 创建点坐标B
    var distance = map.getDistance(pointA,pointB).toFixed(1);  //获取两点距离,保留小数点后两位
    for (var i = 0,zoomLen = zoom.length; i < zoomLen; i++) {
        if(zoom[i] - distance > 0){
            return 18-i+3;//之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
        }
    };
}

//根据原始数据计算中心坐标和缩放级别，并为地图设置中心坐标和缩放级别。
function setZoom(points){
    if(points.length>0){
        var maxLng = points[0].lng;
        var minLng = points[0].lng;
        var maxLat = points[0].lat;
        var minLat = points[0].lat;
        var res;
        for (var i = points.length - 1; i >= 0; i--) {
            res = points[i];
            if(res.lng > maxLng) maxLng =res.lng;
            if(res.lng < minLng) minLng =res.lng;
            if(res.lat > maxLat) maxLat =res.lat;
            if(res.lat < minLat) minLat =res.lat;
        };
        var cenLng =(parseFloat(maxLng)+parseFloat(minLng))/2;
        var cenLat = (parseFloat(maxLat)+parseFloat(minLat))/2;
        var zoom = getZoom(maxLng, minLng, maxLat, minLat);
        map.centerAndZoom(new BMap.Point(cenLng,cenLat), zoom);
    }else{
        //没有坐标，显示全中国
        map.centerAndZoom(new BMap.Point(103.388611,35.563611), 5);
    }
}