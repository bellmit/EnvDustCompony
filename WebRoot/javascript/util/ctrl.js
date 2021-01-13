/************************************
 * 功能：生成各种UI控件的方法
 * 日期：2016-3-10
 ************************************/
var ornCnCode = [ {
	"id" : "2011",
	"name" : "实时数据"
}, {
	"id" : "2051",
	"name" : "分钟数据"
}, {
	"id" : "2061",
	"name" : "小时数据"
}, {
	"id" : "2031",
	"name" : "每日数据"
} ];
var alarmTypeCode = [{
	"levelNo":"1",
	"name":"普通报警"
},{
	"levelNo" : "2",
	"name" : "紧急报警"
},{
    "levelNo" : "3",
    "name" : "严重报警"
}];
/**
 * 函数功能：绘制ValidateBox
 **/
function createValidatebox(params){
	var tpl = '<div id="' + params.name +'_div" '
 			+ (params.ishiden ? 'style="display:none" ' : '' )
           + '>' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' 
			+ params.title 
			/*+ (params.help ? '<a title="' + params.help + '" class="easyui-tooltip">' 
			+ '<img src="easyui/themes/icons/tip.png" style="margin-bottom:-3px;margin-left:3px;"/></a>' : '') */
			+ '</label>'
			+ '<input class="easyui-validatebox input-border" '
			+ (params.isPwd ? 'type="password" ' : 'type="text" ' ) 
			+ 'id="' + params.name + '" name="' + params.name
			+ (params.value==undefined ? '' : '"value="' + params.value) 
			+ (params.readonly? '"readonly=true' :'"')
			+ ' title="'
			+ (params.tooltip ? params.tooltip : tooltipInfo(params.type))
			+ '" data-options="' 
			+ (params.noBlank ? 'required:true,' : '') 
			+ (params.type ? 'validType:\'' + params.type + '\'' : '')
			+ '" style="width:150px;" /></a></div>';
	return tpl;
}

/* ---------- 勾选框<begin> ---------- */
/**
 * 函数功能：绘制CheckBox
 **/
function createCheckbox(params){
	var tpl = '<div id="' + params.name +'_div">' 
			+ '<input type="checkbox" ' 
			+ (params.event ? 'onClick=' + params.event:'') 
			+ ' id="' + params.name + '" name="' + params.name 
			+ (params.checked ? '" checked="checked' : '')
			+ '" />' + params.title + '</div>';
	return tpl;
}
/**
 * 函数功能：序列化checkbox表单
 **/
function getCheckboxArray(domId){
	var arr = [];
	$(domId).find("input[type='checkbox']").each(function(){
		arr.push({
			"name" : $(this).attr("name"),
			"value" : $(this).attr("checked") ? "1" : "0"
		});
	});
	return arr;
}
/* ---------- 勾选框<end> ---------- */

/**
 * 函数功能：绘制NumberBox
 **/
function createNumberbox(params){
	var tpl = '<div id="' + params.name +'_div"' + (params.hidden ? 'style=display:none;' : '') + '>' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' 
			+ params.title 
		/*	+ (params.help ? '<a title="' + params.help + '" class="easyui-tooltip">' 
			+ '<img src="easyui/themes/icons/tip.png" style="margin-bottom:-3px;margin-left:3px;"/></a>' : '') */
			+ '</label>'
			+ '<input class="easyui-numberbox input-border" type="text" ' 
			+ 'id="' + params.name + '" name="' + params.name
			+ (params.value==undefined ? '' : '"value="' + params.value)
			+ (params.readonly ? '" readonly=true' : '"')
			+ ' title="'
			+ (params.tooltip ? params.tooltip : tooltipInfo(params.valid))
			+ '" data-options="' 
			+ (params.valid ? params.valid+',' : '')
			+ (params.noBlank ? 'required:true,' : '')
			+ (params.prefix ? ('prefix:\''+params.prefix+'\'') : '')
			+ '" style="width:150px;" /></a></div>';
	return tpl;
}

/**
 * 函数功能：绘制ComboBox
 **/
function createCombobox(params){
	var obj = params.data, strArr = [];
	for(var key in obj){
		strArr.push("{value:'"+key+"',text:'"+obj[key]+"'}");
		strArr.push(",");
	}
	strArr.pop();
	var tpl = '<div id="' + params.name +'_div" style="height:20px;">' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' 
			+ params.title 
			/*+ (params.help ? '<a title="' + params.help + '" class="easyui-tooltip">' 
			+ '<img src="easyui/themes/icons/tip.png" style="margin-bottom:-3px;margin-left:3px;"/></a>' : '') */
			+ '</label>'
			+ '<input class="easyui-combobox"' 
			+ 'id="' + params.name + '" name="' + params.name
			+ (params.value==undefined ? '' : '"value="' + params.value)
			+ (params.disabled ? '" disabled=true' : '"')
			+ ' panelHeight=' + (strArr.length > 9 ? '120' : 'auto')
			+ ' data-options="'
			+ (params.noBlank ? 'required:true,' : '')
			+ (params.isMulti ? 'multiple:true,' : '')
			+ 'data:[' + strArr.join("") + ']'
			+ '" style="width:154px;" editable=false /></div>';
	return tpl;
}

/**
 * 函数功能：绘制ComboBox
 **/
function createComboboxEdit(params){
	var obj = params.data, strArr = [];
	for(var key in obj){
		strArr.push("{value:'"+key+"',text:'"+obj[key]+"'}");
		strArr.push(",");
	}
	strArr.pop();
	var tpl = '<div id="' + params.name +'_div" style="height:20px;">' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' 
			+ params.title 
			/*+ (params.help ? '<a title="' + params.help + '" class="easyui-tooltip">' 
			+ '<img src="easyui/themes/icons/tip.png" style="margin-bottom:-3px;margin-left:3px;"/></a>' : '') */
			+ '</label>'
			+ '<input class="easyui-combobox"' 
			+ 'id="' + params.name + '" name="' + params.name
			+ (params.value==undefined ? '' : '"value="' + params.value)
			+ (params.disabled ? '" disabled=true' : '"')
			+ ' panelHeight=' + (strArr.length > 9 ? '120' : 'auto')
			+ ' data-options="'
			+ (params.noBlank ? 'required:true,' : '')
			+ (params.isMulti ? 'multiple:true,' : '')
			+ 'data:[' + strArr.join("") + ']'
			+ '" style="width:154px;" editable=true /></div>';
	return tpl;
}

/**
 * 函数功能：绘制DateTimeBox
 **/
function createDatetimebox(params){
	var tpl = '<div id="' + params.name +'_div">' 
			+ '<label for="' + params.name +'" style="font-size:12px;">' + params.title + '</label>'
			+ '<input class="easyui-datetimebox" type="text" ' 
			+ 'id="' + params.name + '" name="' + params.name
			+ '" data-options="showSeconds:' + (params.showSeconds ? true : false)
			+ (params.noBlank ? ',required:true' : '') 
			+ (params.type ? ',validType:\'' + params.type + '\'' : '')
			+ (params.value ? '"value=\'' + params.value + '\'"' : '')
			+ '" style="width:154px;" /></div>';
	return tpl;
}

/**
 * 函数功能:绘制label
 */
function createLabel(params){
	var tpl = '<div id="' + params.name +'_div" '
		+ (params.ishiden ? 'style="display:none" ' : '' )
    + '>' 
	+ '<label  style="font-size:12px;'
	+ (params.color ? 'color:' + params.color : 'black')
	+'">' 
	+ params.title 
	+ '</label>'
	+'</div>';
	return tpl;
}

/**
 * 函数功能：根据输入框的校验规则生成相应的提示信息
 **/
function tooltipInfo(valid){
	var info = "", res = [];
	if(valid){
		res = valid.match(/number\[(\d+),(\d+)\]/);
		if(res){
			info = "可输入范围在" + res[1] + "至" + res[2] + "之间的数字";
		}
		res = valid.match(/min:(-?\d+),max:(\d+)/);
		if(res){
			info = "可输入范围在" + res[1] + "至" + res[2] + "之间的数字";
		}
		res = valid.match(/loginId/);
		if(res){
			info = "不能输入中文字符及空格";
		}
	}
	return info;
}

/**
 * 函数功能：显示年月的控件
 * @param id
 */
function createDateboxByYYMM(id){
	 $('#'+id).datebox({
       onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
           span.trigger('click'); //触发click事件弹出月份层
           if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
               tds = p.find('div.calendar-menu-month-inner td');
               tds.click(function (e) {
                   e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                   var year = /\d{4}/.exec(span.html())[0]//得到年份
                   , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                   $('#'+id).datebox('hidePanel')//隐藏日期对象
                   .datebox('setValue', year + '-' + month); //设置日期的值
               });
           }, 0)
       },
       parser: function (s) {
           if (!s) return new Date();
           var arr = s.split('-');
           return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
       },
       formatter: function (d) {
    	   var MM = (d.getMonth()+1);
    	   if((d.getMonth()+1)<10){//补零处理
    		   MM = "0" + MM;
    	   }
    	   var datetime = d.getFullYear() + '-' + MM;
    	   return datetime;
       }
   });
   var p = $('#'+id).datebox('panel'), //日期选择对象
       tds = false, //日期选择对象中月份
       span = p.find('span.calendar-text'); //显示月份层的触发控件
}

/*
 * 函数功能：将表单数据转换成Json格式
 */
function getFormJson(arr){
	var obj = {};
	for(var i = 0, len = arr.length; i < len; i++){
		obj[arr[i].name] = arr[i].value;
	}
	return obj;
}

/*
 * 函数功能：使用loading效果
 */
function ajaxLoading(msg){
	$("<div class='datagrid-mask'></div>").appendTo("body").css({
		"display":"block",
		"z-index":9999,
		"width":"100%",
		"height":$(window).height()
	});
    $("<div class='datagrid-mask-msg'></div>").html(msg ? msg : "正在处理,请稍候...").appendTo("body").css({
    	"display":"block",
    	"z-index":99999,
    	"left":($(document.body).outerWidth(true) - 190) / 2,
    	"top":($(window).height() - 45) / 2
    });
}


/*
 * 函数功能：结束loading效果
 */
function ajaxLoadEnd(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();               
}

function ajaxPartLoading(id){
	if(id!="" && id!=undefined){
        $('<div id="ajaxloader4"> <div class="outer"></div> <div class="inner"></div> </div>').appendTo("#"+id).css({
            "display":"block",
            "z-index":99999,
            "left":($("#"+id).outerWidth(true)-60) / 2,
            "top":($("#"+id).height()+2) / 2
        });
	}
}
/*
 * 函数功能：结束loading效果
 */
function ajaxLoadPartEnd(){
    $("#ajaxloader4").remove();
}

/*
 * 函数功能：获取cookie
 */
function getCookieValue(name){  
    var name = escape(name);  
    var allcookies = document.cookie;  
    name += "=";  
    var pos = allcookies.indexOf(name);  
    if(pos != -1){  
        var start = pos + name.length;  
        var end = allcookies.indexOf(";",start);  
        if(end == -1){  
            end = allcookies.length;  
        }  
        var value = allcookies.substring(start,end);  
        return unescape(value);  
    } else{  
        return "";  
    }  
}  

//Ajax文件下载
$.download = function(url, data, method){
	if(url && data){
		data = typeof data == 'string' ? data : $.param(data);
		var inputs = '';
		$.each(data.split('&'), function(){
			var pair = this.split('=');
			inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
		});
		$('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>').appendTo('body').submit().remove();
	};
};

//生成GUID
function guid(){
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}
//二分法
function binarySearch(value,items,isFind){
	var startIndex = 0, stopIndex = items.length-1, middle = Math.floor(stopIndex/2);
	while(startIndex <= stopIndex){
		var startRes = value.localeCompare(items[startIndex].number),
			middleRes = value.localeCompare(items[middle].number),
			stopRes = value.localeCompare(items[stopIndex].number);
		if(startRes < 0){
			return isFind ? null : startIndex;
		}else if(startRes == 0){
			return startIndex;
		}else if(startRes == 1 && middleRes == -1){
			stopIndex = middle - 1;
		}else if(middleRes == 0){
			return middle;
		}else if(middleRes == 1 && stopRes == -1){
			startIndex = middle + 1;
		}else if(stopRes == 0){
			return stopIndex;
		}else if(stopRes == 1){
			return isFind ? null : stopIndex + 1;
		}
		middle = Math.floor((stopIndex + startIndex)/2);
	}
}

//日期转换为毫秒
function dateToMillisecond(date,precision){
	switch(precision){
		case 'second':
			var temp = date.match(/(\d+)-(\d+)-(\d+) (\d+):(\d+):(\d+)/);
			return new Date(temp[1],parseInt(temp[2])-1,temp[3],temp[4],temp[5],temp[6]).getTime();
		case 'millisecond':
			var temp = date.match(/(\d+)-(\d+)-(\d+) (\d+):(\d+):(\d+).(\d+)/);
			return new Date(temp[1],parseInt(temp[2])-1,temp[3],temp[4],temp[5],temp[6],temp[7]).getTime();
		default:
			break;
	}
}

//将毫秒转换成xx天xx小时xx分xx秒格式的字符串
function msecondTransform(msec){
	msec /= 1000;
	var day = Math.floor(msec / 3600 / 24), timeString = day ? day + '天' : '';
	msec -= 24 * 3600 * day;
	var timeObj = {"小时":Math.floor(msec / 3600),"分":Math.floor(msec / 60 % 60),"秒":msec % 60};
	for(var key in timeObj){
		if(timeObj[key]){
			timeString += timeObj[key] + key;
		}
	}
	return timeString;
}

/*
 * 格式化当前日期，含当前时分秒
 */
function formatterDateHMS(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1);
	var hour = date.getHours() > 9 ? date.getHours() : "0" + date.getHours();
	var minute = date.getMinutes() > 9 ? date.getMinutes() : "0"
			+ date.getMinutes();
	var second = date.getSeconds() > 9 ? date.getSeconds() : "0"
			+ date.getSeconds();
	return date.getFullYear() + "-" + month + "-" + day + " " + hour + ":"
			+ minute + ":" + second;
}

function GetDateStr(AddDayCount,flag) {
	var dd = new Date();
	var timestr = "";
	dd.setDate(dd.getDate() + AddDayCount);// 获取AddDayCount天后的日期
	var y = dd.getFullYear();
	var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd
			.getMonth() + 1);// 获取当前月份的日期，不足10补0
	var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate(); // 获取当前几号，不足10补0
	if(flag==1){
		timestr = y + "-" + m + "-" + d +" 23:59:59";
	}else{
		timestr = y + "-" + m + "-" + d +" 00:00:00";
	}
	return timestr
}

function GetMonthStr(AddMonthCount,flag) {
	var dd = new Date();
	var timestr = "";
	dd.setMonth(dd.getMonth() + AddMonthCount);// 获取AddDayCount月后的日期
	var y = dd.getFullYear();
	var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd
			.getMonth() + 1);// 获取当前月份的日期，不足10补0
	var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate(); // 获取当前几号，不足10补0
	if(flag==1){
		timestr = y + "-" + m + "-" + d +" 23:59:59";
	}else{
		timestr = y + "-" + m + "-" + d +" 00:00:00";
	}
	return timestr
}

/*
 *格式化当前日期（只有月日年）
 */
function formatterDate(date){
	var day=date.getDate()>9 ? date.getDate() : "0"+date.getDate();
	var month=(date.getMonth()+1)>9 ? (date.getMonth()+1) : "0"+(date.getMonth()+1);
	return date.getFullYear()+"-"+month+"-"+day;
}

/*
 *格式化当前日期（按格式进行）
 */
function formatterNowDate(fmt) {
    var currentTime = new Date(new Date().getTime())
//    console.log(currentTime) // Wed Jun 20 2018 16:12:12 GMT+0800 (中国标准时间)
    var o = {
      'M+': currentTime.getMonth() + 1, // 月份
      'd+': currentTime.getDate(), // 日
      'h+': currentTime.getHours(), // 小时
      'm+': currentTime.getMinutes(), // 分
      's+': currentTime.getSeconds(), // 秒
      'q+': Math.floor((currentTime.getMonth() + 3) / 3), // 季度
      'S': currentTime.getMilliseconds() // 毫秒
    }
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (currentTime.getFullYear() + '').substr(4 - RegExp.$1.length))
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
    }
    return fmt;
}

/**
 *实时表格数据显示处理
 * @param value
 * @param field
 * @param zsFlag
 * @param zvalue
 * @returns {*}
 */
function tableShowHandler(value, field, zsFlag, zvalue,alarmMapRange,mulmonitorThing) {
    var levelScopes = [];
    if(mulmonitorThing!=null && mulmonitorThing!=""){
        field = mulmonitorThing;
    }
    if (value != undefined) {
    if (field != "" && field != undefined && alarmMapRange[field] != undefined
    ) {
        levelScopes = alarmMapRange[field];
    } else {
        levelScopes = [];
    }
    var levelNo = getLevelNo(value,levelScopes);
    var myColor = null;
    var color = "#fff";
    if(levelNo!=""){
        myColor = getLevelColor(levelNo);//获取级别颜色
		color = "#fff";
    }else{
        color = "#000";
	}
    if (zsFlag) {//折算数据显示处理
        var zvaluecolor = "#fff";
        if (zvalue != "---" && levelNo!="") {
            zvaluecolor = myColor;
        } else {
            zvaluecolor = "#fff";
        }
        return '<span style="color:'+color+';display: inline-block;padding:0px 6px;width:60px;background:' + myColor + '">' + value + '</span>/' +
            '<span style="color:'+color+';display: inline-block;padding:0px 6px;width:60px;background:' + zvaluecolor + '">' + zvalue + '</span>';
    } else {
        return '<span style="color:'+color+';display: inline-block;padding:0px 6px;width:60px;background:' + myColor + '">' + value + '</span>';
    }
    } else {
        return "---";
    }
}

/**
 * 获取报警级别
 * @param value
 * @param levelScopes
 * @returns {string}
 */
function getLevelNo(value,levelScopes){
    var levelNo = "";
    for(var i=0;i<levelScopes.length;i++){
        var min = levelScopes[i].min;
        var max = levelScopes[i].max;
        if(value>=min && value<max){
            levelNo = levelScopes[i].lev;
            break;
        }
    }
    return levelNo;
}

/**
 * 获取最大值
 * @param levelScopes
 * @returns {*}
 */
function getMax(levelScopes){
    var max = 0;
	if(levelScopes.length>0){
        max = levelScopes[levelScopes.length-1].min;
	}
	return max;
}

/**
 * 获取级别颜色
 * @param levelNo
 * @returns {string}
 */
function getLevelColor(levelNo){
	var color = "#f64845";
	if(levelNo=="1"){
		color = "#ffde00"
	}else if(levelNo=="2"){
		color = "#ff7500"
	}else{
		color = "#f64845";
	}
	return color;
}