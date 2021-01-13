// 获得级别
function getColorByClick(val, type) {
    console.info(val+"=="+type);
    var result = '';
    switch (type) {
        case 'aqi':
            result = getColorByIndex(getAQILevelIndex(val));
            break;
        case 'so2':
            result = getColorByIndex(getSO2LevelIndex(val, type));
            break;
        case 'no2':
            result = getColorByIndex(getNO2LevelIndex(val, type));
            break;
        case 'o3h8':
            result = getColorByIndex(getO3LevelIndex(val, type));
            break;
        case 'co':
            result = getColorByIndex(getCOLevelIndex(val, type));
            break;
        case 'pm25':
            result = getColorByIndex(getPM25LevelIndex(val));
            break;
        case 'pm10':
            result = getColorByIndex(getPM10LevelIndex(val));
            break;
        case 'o3':
            result = getColorByIndex(getO3LevelIndex(val, type));
            break;
    }
    return result;
}

 function convertTimeFormat(time) 
 {
   if (time != null) 
   {
	time = time.replace("-", "/");
	time = time.replace("-", "/");
	return new Date(time);
   }
   return null;
}

function replace(str,parms1,parms2){
	return str.replace(eval("/"+parms1+"/g"),parms2);
}

function formatStr(str) 
{
	if (str == null) 
	{
		return '无';
	} 
	else 
	{
		return str;
	}
}

function getWindDirectionStr(wd)
{
    var wdstr = '';
    if (isNaN(wd) || wd == null)
    {
        wdstr = '-';
    }
    else if(wd<=22.5 || wd>=337.5)
    {
        wdstr = '北风';
    }
    else if(wd>22.5 && wd<=67.5)
    {
        wdstr = '东北风';
    }
    else if(wd>67.5 && wd<=112.5)
    {
        wdstr = '东风';
    }
    else if(wd>112.5 && wd<=157.5)
    {
        wdstr = '东南风';
    }
    else if(wd>157.5 && wd<=202.5)
    {
        wdstr = '南风';
    }
    else if(wd>202.5 && wd<=247.5)
    {
        wdstr = '西南风';
    }
    else if(wd>247.5 && wd<=292.5)
    {
        wdstr = '西风';
    }
    else if(wd>292.5 && wd<=337.5)
    {
        wdstr = '西北风';
    }
    return wdstr;
  }

function getWindLevelByStr(wd)
{
    var wid = 0;
    switch(wd)
    {
        case '北风':wid = 0;break;
        case '东北风':wid = 1;break;
        case '东风':wid = 2;break;
        case '东南风':wid = 3;break;
        case '南风':wid = 4;break;
        case '西南风':wid = 5;break;
        case '西风':wid = 6;break;
        case '西北风':wid = 7;break;
    }
    return wid;
}

function toRad(d) 
{  
	return d * Math.PI / 180; 
}

function getDisance(lat1, lng1, lat2, lng2) { 
    var dis = 0;
    var radLat1 = toRad(lat1);
    var radLat2 = toRad(lat2);
    var deltaLat = radLat1 - radLat2;
    var deltaLng = toRad(lng1) - toRad(lng2);
    var dis = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(deltaLng / 2), 2)));
    return dis * 6378137;
}

function getWindLevel(speed) {
	if (isNaN(aqi) || speed < 1) {
		return 0;
	} else if (speed >= 1 && speed <= 5) {
		return 1;
	} else if (speed >= 6 && speed <= 11) {
		return 2;
	} else if (speed >= 12 && speed <= 19) {
		return 3;
	} else if (speed >= 20 && speed <= 28) {
		return 4;
	} else if (speed >= 29 && speed <= 38) {
		return 5;
	} else if (speed >= 39 && speed <= 49) {
		return 6;
	} else if (speed >= 50 && speed <= 61) {
		return 7;
	} else if (speed >= 62 && speed <= 74) {
		return 8;
	} else if (speed >= 75 && speed <= 88) {
		return 9;
	} else if (speed >= 89 && speed <= 102) {
		return 10;
	} else if (speed >= 103 && speed <= 117) {
		return 11;
	} else if (speed > 117) {
		return 12;
	}
}

function getAQIColor(aqi) {
    if (isNaN(aqi) || aqi <= 0) {
		color = "#f0f0f0";
	} else if (aqi <= 50) {
		color = "#43ce17";
	} else if (aqi <= 100) {
		color = "#efdc31";
	} else if (aqi <= 150) {
		color = "#fa0";
	} else if (aqi <= 200) {
		color = "#ff401a";
	} else if (aqi <= 300) {
		color = "#d20040";
	} else {
		color = "#9c0a4e";
	}
	return color;
}

function getAQIlevel(aqi) {
    if (isNaN(aqi) || aqi <= 0) {
		color = "无";
	} else if (aqi <= 50) {
		color = "优";
	} else if (aqi <= 100) {
		color = "良";
	} else if (aqi <= 150) {
		color = "轻度";
	} else if (aqi <= 200) {
		color = "中度";
	} else if (aqi <= 300) {
		color = "重度";
	} else {
		color = "严重";
	}
	return color;
}
function getPM25Level(pm2_5) {
    if (isNaN(pm2_5) || pm2_5 == 0) {
        color = "无";
    } else if (pm2_5 <= 35) {
        color = "优";
    } else if (pm2_5 <= 75) {
        color = "良";
    } else if (pm2_5 <= 115) {
        color = "轻度";
    } else if (pm2_5 <= 150) {
        color = "中度";
    } else if (pm2_5 <= 250) {
        color = "重度";
    } else {
        color = "严重";
    }
    return color;
}
function getPM10Level(pm10) {
    if (isNaN(pm10) || pm10 == 0) {
		color = "无";
	}else if (pm10 <= 50) {
		color = "优";
	} else if (pm10 <= 150) {
		 color = "良";
	} else if (pm10 <= 250) {
		color = "轻度";
	} else if (pm10 <= 350) {
		color = "中度";
	} else if (pm10 <= 420) {
		color = "重度";
	} else {
		color = "严重";
	}
	return color;
}
function getSO2Level(so2) {
    if (isNaN(so2) || so2 === 0) {
        color = "无";
    } else if (so2 <= 50) {
        color = "优";
    } else if (so2 <= 150) {
        color = "良";
    } else if (so2 <= 475) {
        color = "轻度";
    } else if (so2 <= 800) {
        color = "中度";
    } else {
        color = "重度";
    }
    return color;
}
function getNO2Level(no2) {
    if (isNaN(no2) || no2 === 0) {
        color = "无";
    } else if (no2 <= 40) {
        color = "优";
    } else if (no2 <= 80) {
        color = "良";
    } else if (no2 <= 180) {
        color = "轻度";
    } else if (no2 <= 280) {
        color = "中度";
    } else if (no2 <= 565) {
        color = "重度";
    } else {
        color = "严重";
    }
    return color;
}
function getO3Level(o3) {
    if (isNaN(o3) || o3 === 0) {
        color = "无";
    } else if (o3 <= 100) {
        color = "优";
    } else if (o3 <= 160) {
        color = "良";
    } else if (o3 <= 215) {
        color = "轻度";
    } else if (o3 <= 265) {
        color = "中度";
    } else if (o3 <= 800) {
        color = "重度";
    } else {
        color = "严重";
    }
    return color;
}
function getCOLevel(co) {
    if (isNaN(co) || co === 0) {
        color = "无";
    } else if (co <= 2) {
        color = "优";
    } else if (co <= 4) {
        color = "良";
    } else if (co <= 14) {
        color = "轻度";
    } else if (co <= 24) {
        color = "中度";
    } else if (co <= 36) {
        color = "重度";
    } else {
        color = "严重";
    }
    return color;
}

function getAQIStyle(aqi) {
    if (isNaN(aqi) || aqi <= 0 || aqi == null || aqi == '') {
		styles = 'background-color:#6E6E6E;color:white;';
	} else if (aqi <= 50) {
		styles = 'background-color:#43ce17;color:white;';
	} else if (aqi <= 100) {
		styles = 'background-color:#efdc31;color:white;';
	} else if (aqi <= 150) {
		styles = 'background-color:#fa0;color:white;';
	} else if (aqi <= 200) {
		styles = 'background-color:#ff401a;color:white;';
	} else if (aqi <= 300) {
		styles = 'background-color:#d20040;color:white;';
	} else {
		styles = 'background-color:#9c0a4e;color:white;';
	}
	return styles;
}

function getAQILevelIndex(aqi) {
	var level = 0;
	if (isNaN(aqi) || aqi <= 0 || aqi == null || typeof aqi == 'undefined') {
		level = 0;
	} else if (aqi <= 50) {
		level = 1;
	} else if (aqi <= 100) {
		level = 2;
	} else if (aqi <= 150) {
		level = 3;
	} else if (aqi <= 200) {
		level = 4;
	} else if (aqi <= 300) {
		level = 5;
	} else {
		level = 6;
	}
    console.info(level);
	return level;
}

function getColorByIndex(level) {
    console.info(level);
	var color = '#6E6E6E';
	if (isNaN(level) || level == 0) {
		color = "#6E6E6E";
	} else if (level == 1) {
		color = "#43ce17";
	} else if (level == 2) {
		color = "#efdc31";
	} else if (level == 3) {
		color = "#fa0";
	} else if (level == 4) {
		color = "#ff401a";
	} else if (level == 5) {
		color = "#d20040";
	} else {
		color = "#9c0a4e";
	}
	return color;
}

function getComplexIndexLevelIndex(complexindex) {
    var level;
    if (isNaN(level)) {
        level = 0;
    }
    else if (complexindex <= 5) {
	    level = 1;
	} else if (complexindex <= 6) {
	    level = 2;
	} else if (complexindex <= 8) {
	    level = 3;
	} else if (complexindex <= 9) {
	    level = 4;
	} else if (complexindex <= 10) {
	    level = 5;
	} else {
	    level = 6;
	}
	return level;
}

function getPM25LevelIndex(pm2_5) {
	var level;
	if (isNaN(pm2_5) || pm2_5 == 0) {
		level = 0;
	}else if (pm2_5 <= 35) {
		level = 1;
	} else if (pm2_5 <= 75) {
		level = 2;
	} else if (pm2_5 <= 115) {
		level = 3;
	} else if (pm2_5 <= 150) {
		level = 4;
	} else if (pm2_5 <= 250) {
		level = 5;
	} else {
		level = 6;
	}
	return level;
}

function getPM10LevelIndex(pm10) {
	var level;
	if (isNaN(pm10) || pm10 == 0) {
		level = 0;
	}else if (pm10 <= 50) {
		level = 1;
	} else if (pm10 <= 150) {
		level = 2;
	} else if (pm10 <= 250) {
		level = 3;
	} else if (pm10 <= 350) {
		level = 4;
	} else if (pm10 <= 420) {
		level = 5;
	} else {
		level = 6;
	}
	return level;
}

function getSO2LevelIndex(so2, type) {
    var level;
    if (type == 'day') {
        if (isNaN(so2) || so2 == 0 || so2 == null || typeof so2 == 'undefined') {
            level = 0;
        } else if (so2 <= 50) {
            level = 1;
        } else if (so2 <= 150) {
            level = 2;
        } else if (so2 <= 475) {
            level = 3;
        } else if (so2 <= 800) {
            level = 4;
        } else {
            level = 5;
        }
    } else {
        if (isNaN(so2) || so2 == 0 || so2 == null || typeof so2 == 'undefined') {
            level = 0;
        } else if (so2 <= 150) {
            level = 1;
        } else if (so2 <= 500) {
            level = 2;
        } else if (so2 <= 650) {
            level = 3;
        } else if (so2 <= 800) {
            level = 4;
        } else {
            level = 5;
        }
    }

    return level;
}

function getNO2LevelIndex(no2, type) {
    var level;
    if (type == 'day') {
        if (isNaN(no2) || no2 == 0 || no2 == null || typeof no2 == 'undefined') {
            level = 0;
        } else if (no2 <= 40) {
            level = 1;
        } else if (no2 <= 80) {
            level = 2;
        } else if (no2 <= 180) {
            level = 3;
        } else if (no2 <= 280) {
            level = 4;
        } else if (no2 <= 565) {
            level = 5;
        } else {
            level = 6;
        }
    } else {
        if (isNaN(no2) || no2 == 0 || no2 == null || typeof no2 == 'undefined') {
            level = 0;
        } else if (no2 <= 100) {
            level = 1;
        } else if (no2 <= 200) {
            level = 2;
        } else if (no2 <= 700) {
            level = 3;
        } else if (no2 <= 1200) {
            level = 4;
        } else if (no2 <= 2340) {
            level = 5;
        } else {
            level = 6;
        }
    }

    return level;
}

function getO3LevelIndex(o3, type) {
    var level;
    if (type == 'day') {
        if (isNaN(o3) || o3 == 0 || o3 == null || typeof o3 == 'undefined') {
            level = 0;
        } else if (o3 <= 100) {
            level = 1;
        } else if (o3 <= 160) {
            level = 2;
        } else if (o3 <= 215) {
            level = 3;
        } else if (o3 <= 265) {
            level = 4;
        } else if (o3 <= 800) {
            level = 5;
        } else {
            level = 6;
        }
    } else {
        if (isNaN(o3) || o3 == 0 || o3 == null || typeof o3 == 'undefined') {
            level = 0;
        } else if (o3 <= 160) {
            level = 1;
        } else if (o3 <= 200) {
            level = 2;
        } else if (o3 <= 300) {
            level = 3;
        } else if (o3 <= 400) {
            level = 4;
        } else if (o3 <= 800) {
            level = 5;
        } else {
            level = 6;
        }
    }
    return level;
}

function getCOLevelIndex(co, type) {
    var level;
    if (type == 'day') {
        if (isNaN(co) || co == 0 || co == null || typeof co == 'undefined') {
            level = 0;
        } else if (co <= 2) {
            level = 1;
        } else if (co <= 4) {
            level = 2;
        } else if (co <= 14) {
            level = 3;
        } else if (co <= 24) {
            level = 4;
        } else if (co <= 36) {
            level = 5;
        } else {
            level = 6;
        }
    } else {
        if (isNaN(co) || co == 0 || co == null || typeof co == 'undefined') {
            level = 0;
        } else if (co <= 5) {
            level = 1;
        } else if (co <= 10) {
            level = 2;
        } else if (co <= 35) {
            level = 3;
        } else if (co <= 60) {
            level = 4;
        } else if (co <= 90) {
            level = 5;
        } else {
            level = 6;
        }
    }

    return level;
}

function getIndexByPollName(val, type) {
  var level = 0;
  switch (type) {
    case 'so2':
      level = getSO2LevelIndex(val);
      break;
    case 'no2':
      level = getNO2LevelIndex(val);
      break;
    case 'co':
      level = getCOLevelIndex(val);
      break;
    case 'o3':
      level = getO3LevelIndex(val);
      break;
    case 'o3_8h':
      level = getO3LevelIndex(val);
      break;
    case 'pm2_5':
      level = getPM25LevelIndex(val);
      break;
    case 'pm10':
      level = getPM10LevelIndex(val);
      break;
    case 'aqi':
      level = getAQILevelIndex(val);
      break;
    case 'complexindex':
      level = getComplexIndexLevelIndex(val);
      break;
    default:
      level = 7;
  }
  return level;
}


function getIndex(so2,no2,co,o3,pm10,pm2_5){
	return so2/60 + no2/40 + co/4 + o3/160 + pm10/70 + pm2_5/35;
}

function fixSortBugFun(data,id)
			 {
			 	$.each(data,function(index,obj){
			 		if(obj.rankIndexForSort<0)
			 		{
			 			var rankIndexForSort=$("#"+id+" tbody tr:eq("+(index-1)+") td:eq(0)").text();
			 			$("#"+id+" tbody tr:eq("+index+") td:eq(0)").text(rankIndexForSort);
			 			obj.rankIndexForSort=0;
			 		}
			 	});
			 } 



function roundFourInSix(num,precision){
    precision = 0;
		var numstr=num+"";
		if(numstr.indexOf(".")==-1){//如果没有小数点,直接返回
			return num;
		}
		numstr=numstr.substr(numstr.indexOf("."));
		var basestr=Math.floor(num)+numstr.substr(0,precision+1);
		var numstr=numstr.substr(precision+1);//舍弃小数点
				
		var len=numstr.length;//获取小数位的长度
		
		for(var i=0;i<len;i++){
			var n=parseInt(numstr.substr(0,1));//获取第一个数字
			//console.info("aaa",n);
			if(n>5){
				return parseFloat(num.toFixed(precision)); 
			}else if(n<5){
				return parseFloat(basestr);
			}else{//如果==5 则继续
				numstr=numstr.substr(1);
			}
		}
		return parseFloat(basestr);
};

function getTootipe(data,aqi,bgcolor,primary_pollutant){
    var quality = getAQIlevel(data["aqi"]);
    console.info(bgcolor+":"+data["o3h8"]);
    var labelstr = '<hr style="margin:0px"/><span style="color:#333">AQI: ' + (aqi == "-99" || aqi == null ? "--" : aqi) + ' <span style="color:' + bgcolor + '">' + quality + '</span>'
        + '<br/>O3: ' + (data['o3'] == "-99" || data['o3'] == null ? '--' : Math.round(data['o3']))
        + '<br/>O3H8: ' + (data['o3h8'] == "-99" || data['o3h8'] == null ? '--' : Math.round(data['o3h8']))
        + '<br/>NO2: ' + (data['no2'] == "-99" || data['no2'] == null ? "--" : Math.round(data['no2']))
        + '<br/>SO2: ' + (data['so2'] == "-99" || data['so2'] == null ? "--" : Math.round(data['so2']))
        + '<br/>CO: ' + (data['co'] == "-99" || data['co'] == null ? "--" : parseFloat(data['co']).toFixed(1)) + '<br/>PM2.5: ' + (data['pm25'] == "-99" || data['pm25'] == null ? "--" : Math.round(data['pm25']))
        + '<br/>PM10: ' + (data['pm10'] == "-99" || data['pm10'] == null ? "--" : Math.round(data['pm10']))
        + '<br/>首要污染物: ' + primary_pollutant;

    labelstr = labelstr + '</span>';
    return labelstr;
}

/**
 * 显示全年污染天数
 */
function showPollutionDays(id,data){
    $("#"+id).html("");
    var str =  '  <label class="selectStationName" style="color:red;font-weight:normal;margin-right:10px;" id="stationNameInfo"></label><div id="day" style="float: right; color: #31708f;">全年污染等级统计：' +
        '优:<span style="color:#43ce17">'+(data['优'] == undefined || data['优']  == null ? '0' : data['优'])+' </span>'+
        '良:<span style="color:#efdc31">'+(data['良'] == undefined || data['良']  == null ? '0' : data['良'])+' </span>'+
        '轻度:<span style="color:#fa0">'+(data['轻度'] == undefined || data['轻度']  == null ? '0' : data['轻度'])+' </span>'+
        '中度<span style="color:#ff401a">'+(data['中度'] == undefined || data['中度']  == null ? '0' : data['中度'])+' </span>'+
        ' 重度:<span style="color:#d20040">'+(data['重度'] == undefined || data['重度']  == null ? '0' : data['重度'])+' </span>' +
        ' 严重:<span style="color:#9c0a4e">'+(data['严重'] == undefined || data['严重']  == null ? '0' : data['严重'])+' </span> (天) '+
        '</div>';
    $("#"+id).append(str);
}

/**
 * 获取监控物质
 * @param id
 * @param type 监控物质类型
 */
function getMonitorThingBtn(id,type){
    var url = "/thing/getSessionAuthorityThing";
    var btnlist = "";
    $("#"+id).html("");
    $.ajax({
        url:url ,
        type: "post",
        data:{"thingTypeId":type},
        dataType: "json",
        success: function (json) {
            if(json.success){
                for(var i=0;i<json.data.length;i++){
                    var thingCode = json.data[i].thingCode;
                    var thingName = json.data[i].thingName;
                    if(i==0){
                        btnlist =  '<button style="background-color: #0b93d5;color: white;" type="button"  class="btn btn-default monitorGas" id="'+thingCode+'"  >'+thingName+'</button>';
                    }else{
                        btnlist = btnlist + '<button type="button"  class="btn btn-default monitorGas" id="'+thingCode+'"  >'+thingName+'</button>';
                    }
                }
            }
            $("#"+id).append(btnlist);
            bindMonitorOnClick(id);
        }
    });
}

/**
 * 绑定监控物的onClick事件
 */
function bindMonitorOnClick(id){
    if(id=="pollutionYearBtnGroup"){//默认选中第一个时处理
        pollutionCode = $("#pollutionYearBtnGroup .btn:first").attr("id");
        getPollutionYearData();
    }else if(id=="thingGroupMonth"){
        pollutionMonthCode = $("#thingGroupMonth .btn:first").attr("id");
        pollutionMonthName = $("#thingGroupMonth .btn:first").text();
        getMonthData();
    }else if(id=="thingGroupDay"){
        pollutionDayCode = $("#thingGroupDay .btn:first").attr("id");
        pollutionDayName = $("#thingGroupDay .btn:first").text();
        getDayData();
    }else if(id=="selectChangeTredThingList"){
        trendThingCode =  $("#selectChangeTredThingList .btn:first").attr("id");
    }

    $("#"+id+" .btn").click(function(){
        $('.monitorGas').removeAttr('style');
        $(this).attr('style','background-color: #0b93d5;color: white;');
        if(id=="pollutionYearBtnGroup"){
            pollutionCode =  $(this).attr("id");
            getPollutionYearData();
        }else if(id=="thingGroupMonth"){
            pollutionMonthCode =  $(this).attr("id");
            pollutionMonthName = $(this).text();
            getMonthData();
        }else if(id=="thingGroupDay"){
            pollutionDayCode = $(this).attr("id");
            pollutionDayName = $(this).text();
            getDayData();
        }else if(id=="selectChangeTredThingList"){
            trendThingCode =  $(this).attr("id");
        }
    });
}



/**
 * 获取监控物类型
 */
function getMonitorThingType(id,monitorId){
    var url = "/thingType/getSessionAuthorityThingType";
    $("#"+id).html("");
    $.ajax({
        url:url ,
        type: "post",
        async:false,
        dataType: "json",
        success: function (json) {
            if(json.success){
                var type = "";
                if(json.data!=undefined && json.data.length>0){
                    var data = json.data;
                    for (var i = 0; i < data.length; i++) {
                        $("#"+id).append("<option value='"+data[i].ttId+"'>" + data[i].ttName+ "</option>");
                    }
                    $("#" + id + "option:first").attr("selected","selected");
                }
                type = $('#'+id+' option:selected').val();
               if(monitorId=="selectGasDiv" || monitorId=="selectThingList"){
                   getMonitorThingBtnList(monitorId,type);
                }else{
                   getMonitorThingBtn(monitorId,type);
                }


            }
        }
    });
}

/**
 * 绑定监控物类型的onchange事件
 * @param obj
 */
function monitorTypeOnchange(typeId,monitorGroupId){
    var type = $('#'+typeId+' option:selected').val();
    if(monitorGroupId=="selectGasDiv" || monitorGroupId=="selectThingList"){
        getMonitorThingBtnList(monitorGroupId,type);
    }else{
        getMonitorThingBtn(monitorGroupId,type);
    }
}