var map;
var tempLabel = null;
/**
 * 加载地图
 */
function loadMap() {
    map = new BMap.Map("map", {enableMapClick: false});
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 15);  // 初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom();     //开启鼠标滚轮缩放
    var mapStyle = {
        features: ["road", "building", "water", "land"]//隐藏地图上的poi
    };
    map.setMapStyle(mapStyle);
    initZoomControl();
    // 创建控件实例
    var myZoomCtrl = new ZoomControl();
    // 添加到地图当中
    map.addControl(myZoomCtrl);
    geoLactionControl();
    var myGeoLactionCtrl = new getLocationControl();
    map.addControl(myGeoLactionCtrl);
    var mapType = new BMap.MapTypeControl(
        {
            mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP],
            anchor: BMAP_ANCHOR_TOP_LEFT,
            offset: new BMap.Size(10, 10) //进一步控制缩放按钮的水平竖直偏移量
        }
    );
    map.addControl(mapType);
    map.addEventListener("touchmove", function (e) {
        map.enableDragging();
    });
    // TODO: 触摸结束时触发次此事件  此时开启禁止拖动
    map.addEventListener("touchend", function (e) {
        map.disableDragging();
    });

    // 初始化地图 禁止拖动   注：虽禁止拖动，但是可以出发拖动事件
    map.disableDragging();
    map.addEventListener("click", mouseOutInfo);//隐藏label
}

/**
 * 加载地图上数据点
 * @param thindCode
 * @param levelNo
 */
function loadMapDate(thindCode, levelNo, levelName) {

    if (levelNo == undefined || levelNo == null) {
        levelNo = "";
        levelName = "全部";
    }
    //查询污染等级
    $.ajax({
        url: "./../../../MobileController/getThingLevel",
        type: "post",
        dataType: "json",
        data: {"thingCode": thindCode},
        async: false,
        success: function (result) {
            //处理监测物等级逻辑
            if (result != null && result.length > 0) {
                $("._3UFABottom").css("display", "inline");
            } else {
                levelName = "未分级";
                $("._3UFABottom").css("display", "none");
            }
        },
        error: function (e) {
            console.info("查询监测物等级异常：" + e);
        }
    });
    $("#mapSelectLevelNo").html(levelName);
    $.ajax({
        url: "./../../../MobileController/getMapPoint",
        type: "post",
        dataType: "json",
        data: {"thingCode": thindCode, "dataType": "2011", "levelNo": levelNo},
        success: function (result) {
            map.clearOverlays();
            if (result != null && result.length > 0) {
                var myPoints = [];
                for (var i = 0; i < result.length; i++) {
                    var point = new BMap.Point(result[i].pointLng, result[i].pointLat);
                    var content = ""/* '<div class="" style="width:400px;height: 360px">\n' +
                        ' <iframe src="/deviceDetail/'+result.data[i].deviceId+'/'+result.data[i].thingCode+'" id="'+result.data[i].deviceId+'Iframe"   width="100%" height="100%" frameborder="0">\n' +
                        ' </iframe>\n' +
                        '</div>\n'*/;

                    addRichMarker(result[i], point, content);
                    myPoints.push(point);
                }
                var v = map.getViewport(myPoints);//此类代表视野，不可实例化，通过对象字面量形式表示
                map.centerAndZoom(v.center, v.zoom);//设置地图中心点和视野级别
                //setZoom(myPoints)
            } else {
                console.info("查询地图上点位信息异常");
            }
        },
        error: function (e) {
            console.info("查询地图上点位信息异常" + e);
        }
    });

}

/**
 * 描点
 * @param data
 * @param point
 * @param content
 */
function addRichMarker(data, point, content) {
    var label = new BMap.Label(setLabelFunc(data), {
        offset: new BMap.Size(-26, 3), position: point

    });
    label.setStyle({
        display: "none",
        maxWidth: "none",
        border: "0",
        zIndex: 1000000000,
        borderRadius:"5px",
        padding:"0px"
    });
    map.addOverlay(label);
    var richMarkerStr = getRichMarker(data);
    var myRichMarker2 = new BMapLib.RichMarker(richMarkerStr, point, {
        "anchor": new BMap.Size(-17, -22),
        "enableDragging": false,
        "background":"transparent"
    });
    map.addOverlay(myRichMarker2);
    /* myRichMarker2.addEventListener("onmouseover", function () {
         mouseInfo(point, label, data.deviceId);
     }, false);
    myRichMarker2.addEventListener("onmouseout", function () {
        mouseOutInfo(point, label, data.deviceId);
     }, false);*/

    myRichMarker2.addEventListener("onclick", function () {
        mouseInfo(point, label, data.pointId);
    });
}

/**
 * 富标签
 * @param data
 * @returns {string}
 */
function getRichMarker(data) {
    var color = "#32CD32";
    if (data.pointStatus === "O" || data.pointStatus === "Z") {
        color = "#6e6e6e";
    } else if (data.pointLevelColor != null) {
        color = data.pointLevelColor;
    }
    var recentData = data.pointData;
    var strHtml = '<div class="leaflet-marker-icon leaflet-zoom-animated leaflet-interactive" tabindex="0" style="width: 35px; height: 22px; z-index: 600;background:transparent;">' +
        '<div class="_1AB" style="background:' + color + ';color:#fff;font-size:12px;">' +
        '<div class="_1UR" style="border-top-color:' + color + ';"></div>' + getInterNumber(recentData) +
        '</div>' +
        '</div>';
    return strHtml;
}

/**
 *设置地图上label
 */
function setLabelFunc(data) {
    var thingName = $("#hiddenNameParam").text();
    var recentData = data.pointData;
    var pointStatusName = (data.pointStatusName != undefined) ? data.pointStatusName : "";
    var levelName = (data.pointLevelName != undefined) ? data.pointLevelName : "";
    var deviceCode = data.pointCode;
    var deviceName = data.pointName;
    var thingUnit = (data.thingUnit != undefined) ? data.thingUnit : "";
    var color = "#32CD32";
    if (data.pointStatus === "O" || data.pointStatus === "Z") {
        color = "#6E6E6E";
    } else if (data.pointLevelColor != undefined && data.pointLevelColor != "") {
        color = data.pointLevelColor;
    }
    var labelstr = "<div id="+data.pointId+"  style='background:" + color + ";padding:10px 15px;border-radius:5px 5px 0px 0px;border:1px solid " + color + ";min-width: 200px;font-weight:bold;color:#fff;'>" + deviceName + "</div>" +
        "<div style='min-height: 60px;min-width: 200px;'>" +
        "<div  style='padding:6px 15px;'>状态：" + pointStatusName + "</div>" +
        "<div style='padding:6px 15px;'>数值：" + recentData + " " + thingUnit + "</div>" +
        "<div  style='padding:6px 15px;'>级别：" + levelName + "</div>" +
        "</div>";
    return labelstr;
}


/**
 *鼠标移入
 * @param point
 * @param label
 * @param deviceId
 */
function mouseInfo(point, label, deviceId) {
    if(tempLabel!=null){
        tempLabel.setStyle({
            display: "none"
        });
    }
    var obj = $("#" + deviceId);
    obj.css("z-index", "100000");
    var pobj = obj.parent();
    pobj.css("z-index", "100000");
    label.setStyle({
        display: "block"
    });
    tempLabel = label;
}

/**
 * 鼠标移出
 * @param point
 * @param label
 * @param deviceId
 */
function mouseOutInfo(e) {
/*    var obj = $("#" + deviceId);
    obj.css("z-index", "10");
    var pobj = obj.parent();
    pobj.css("z-index", "-9700000");*/
    if(tempLabel!=null){
        tempLabel.setStyle({
            display: "none"
        });
    }
}

// 定义一个控件类放大缩小按钮
function ZoomControl() {
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
    this.defaultOffset = new BMap.Size(10, 240);
}

function initZoomControl() {
    // 通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();
    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    ZoomControl.prototype.initialize = function (map) {
        // 创建一个DOM元素
        var mydiv = document.createElement("div");

        var divZoomIn = document.createElement("div");
        // 添加文字说明
        divZoomIn.appendChild(document.createTextNode("+"));
        // 设置样式
        divZoomIn.style.cursor = "pointer";
        divZoomIn.style.boxShadow = "4px 4px 8px #BDBDBD";
        divZoomIn.style.border = 'none';
        divZoomIn.style.borderRadius = '2px 0px 0px 0px';
        divZoomIn.style.backgroundColor = "#fefefe";
        divZoomIn.style.padding = " 2px 10px";
        divZoomIn.style.fontSize = "22px";
        divZoomIn.style.color = '#262626';
        // 绑定事件,点击一次放大两级
        divZoomIn.onclick = function (e) {
            map.setZoom(map.getZoom() + 1);
        };
        divZoomIn.addEventListener("touchstart", function () {
            this.style.background = "#3385ff";
        });
        divZoomIn.addEventListener("touchend", function () {
            this.style.background = "#fefefe";
        });
        //添加放大的img图标到div中
        mydiv.appendChild(divZoomIn);

        var divZoomOut = document.createElement("div");
        // 添加文字说明
        divZoomOut.appendChild(document.createTextNode("—"));
        // 设置样式
        divZoomOut.style.cursor = "pointer";
        divZoomOut.style.boxShadow = "4px 4px 5px #BDBDBD";
        divZoomOut.style.border = 'none';
        divZoomOut.style.borderTop = '1px solid #f0f0f0';
        divZoomOut.style.borderRadius = '0px 0px 2px 0px';
        divZoomOut.style.backgroundColor = "#fefefe";
        divZoomOut.style.padding = " 8px 10px";
        divZoomOut.style.fontSize = "14px";
        divZoomOut.style.fontWeight = "bold";
        divZoomOut.style.color = '#262626';

        // 绑定事件,点击一次放大两级
        divZoomOut.onclick = function (e) {
            map.setZoom(map.getZoom() - 1);
        };
        divZoomOut.addEventListener("touchstart", function () {
            this.style.background = "#3385ff";
        });
        divZoomOut.addEventListener("touchend", function () {
            this.style.background = "#fefefe";
        });
        //添加放大的img图标到div中
        mydiv.appendChild(divZoomOut);
        // 添加DOM元素到地图中
        map.getContainer().appendChild(mydiv);

        // 将DOM元素返回
        return mydiv;
    }
}

// 定义一个定位控件类
function getLocationControl() {
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
    this.defaultOffset = new BMap.Size(10, 60);
}

function geoLactionControl() {
    getLocationControl.prototype = new BMap.Control();
    getLocationControl.prototype.initialize = function (map) {

        var divGeolaction = document.createElement("div");
        divGeolaction.style.width = "35px";
        divGeolaction.style.height = "35px";
        divGeolaction.style.boxShadow = "4px 4px 5px #BDBDBD";
        // 创建一个DOM元素
        divGeolaction.style.backgroundColor = "#fefefe";
        divGeolaction.style.border = 'none';
        divGeolaction.style.borderRadius = '4px';
        //创建一个放大用的img
        var img_location = document.createElement("img");
        //设置img的src属性
        img_location.setAttribute("src", "./../mobile/img/location24.png");
        img_location.style.margin = "5px 3px 5px 5px"
        //为img设置点击事件
        img_location.onclick = function () {
            mapLocation();
        };
        divGeolaction.addEventListener("touchstart", function () {
            this.style.background = "#3385ff";
        });
        divGeolaction.addEventListener("touchend", function () {
            this.style.background = "#fefefe";
        });
        //添加放大的img图标到div中
        divGeolaction.appendChild(img_location);

        // 添加DOM元素到地图中
        map.getContainer().appendChild(divGeolaction);

        // 将DOM元素返回
        return divGeolaction;
    }
}

/**
 * 定位
 */
function mapLocation() {
    var thingCode = $("#hiddenCodeParam").val();
    loadMapDate(thingCode);
    $("._1BGO").removeAttr("style");
}

$("._1BGO").click(function (e) {
    var thingCode = $("#hiddenCodeParam").val();
    var self = $(this).children("span");
    var levelNo = self.attr("id");
    var levelName = self.text();
    $("._1BGO").removeAttr("style");
    clickClass(levelNo,$(this));
    loadMapDate(thingCode, levelNo, levelName);
});

/**
 * 数据取整
 */
function getInterNumber(number) {
    if (!isNaN(number)) {
        return Math.round(number);
    } else {
        return 0;
    }
}

/**
 * 点击污染级别的背景色变化
 * @param levelNo
 * @param obj
 */
function clickClass(levelNo,obj){
    obj.css("color","#fff");
    if(levelNo==1){
        obj.css("background","#32cd32");
    }else if(levelNo==2){
        obj.css("background","#efdc31");
    }else if(levelNo==3){
        obj.css("background"," #fa0");
    }else if(levelNo==4){
        obj.css("background","#ff401a");
    }else if(levelNo==5){
        obj.css("background","#d20040");
    }else if(levelNo==6){
        obj.css("background","#9c0a4e");
    }
}