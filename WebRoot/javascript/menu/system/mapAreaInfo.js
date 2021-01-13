/*********************************
 * 功能：边框信息
 * 时间：2018-8-31 9:33:52
 ********************************/

var appendcontent = '<div class="easyui-layout" data-options="fit:true">'

				+ '<div data-options="region:\'center\',border:false">'
				+ '<div id="areaPointInfoMap" style="height:100%;width:100%;"></div>'
				+ '<div id="maDialogModel"></div>'
				+ '</div>'
				
				+ '<div data-options="region:\'south\',border:false" style="height:5%;">'
				+ '&emsp;&emsp;&emsp;&emsp;区域名称：<input class="easyui-combobox" name="mapAreaCombox" id="mapAreaCombox" style="width:150px;"/>'
				+ '&emsp;<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="getPol" onclick="getPol()">查询区域</a>'
				+ '&emsp;<a href="#" class="easyui-linkbutton" iconCls="icon-save" id="addPol" onclick="addPol()">添加区域</a>'
				+ '&emsp;<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="editPol()">修改区域</a>'
				+ '&emsp;<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="delPol" onclick="delPol()">删除区域</a>'
				+ '&emsp;&emsp;<a href="#" class="easyui-linkbutton" id="addPoints" onclick="addPoints()">手动输入坐标</a>'
				+ '&emsp;<a href="#" class="easyui-linkbutton" id="clear" onclick="clearPol()">清空当前区域</a>'
				+ '&emsp;&emsp;<label id="labelPointAy" for="pointAy">已选坐标点：</label>'
				+ '<textarea id="pointAy" disabled=true name="pointAy" cols="10" rows="1"></textarea>'
				+ '</div>'
				
				+ '</div>';
addPanel("边框设置", appendcontent);

var areaInfoMap;

initAreaInfoMap();//初始化地图

//隐藏标签
document.getElementById("labelPointAy").style.display = "none";
document.getElementById("pointAy").style.display = "none";

function initAreaInfoMap() {//初始化地图
	areaInfoMap = new BMap.Map("areaPointInfoMap"); // 创建Map实例
	var point = new BMap.Point(117.1786889372559, 39.10762965106183); // 创建点坐标
	areaInfoMap.centerAndZoom(point, 12);
	areaInfoMap.enableScrollWheelZoom();
	areaInfoMap.enableInertialDragging();
	areaInfoMap.enableContinuousZoom();
	areaInfoMap.setDefaultCursor("Default");
}

//创建坐标点相关方法
addClick();
//初始化下拉框
initMaCombox();

function addClick(){
	areaInfoMap.addEventListener("click",function(e){
		var str = e.point.lng+ "," +e.point.lat+ ";";
		$("#pointAy").append(str);
		autoAddPol();//自动添加
	});
}

function clearPoints(){//清空坐标点
	$("#pointAy").text("");
}

function addPoints() {//添加坐标点
	$.messager.prompt("输入坐标","经度,纬度(连续输入用\";\"分隔)",function(str){
		var reg = /^[-\+]?\d+(\.\d+)\,[-\+]?\d+(\.\d+)$/;
		if(reg.test(str)){
			$("#pointAy").append(str+ ";");
			autoAddPol();//自动添加
		}
		else{
			alert("格式错误，示例：116.413387,39.910924");
		}
	});
}

function getPol() {//查询区域
	var maId = $("#mapAreaCombox").combobox('getValue');
	var maName = $("#mapAreaCombox").combobox('getText');
	var url = "../MapAreaController/getMapAreaPoint";
	clearPol();
	if(maId != null && maId != ""){
		$.ajax({
			url : url,
			type : "post",
			data : {
				"maId": maId
			},
			dataType:"json",
			async:false,
			error : function(json) {
				ajaxLoadEnd();
			},
			success : function(json){
				ajaxLoadEnd();
				if (json.rows != null) {
					for(var i = 0;i < json.rows.length;i++) {
					$("#pointAy").append(json.rows[i].lng + "," + json.rows[i].lat + ";");
					}
				}
			}
		});
		dispPol(maName);//显示区域
	}
	else {
		$.messager.alert("提示","请选择一个区域！","info");
	}

}

function dispPol(name) {//显示区域
	var points=($("#pointAy").val()).split(";");
	if(points.length<4){
		$.messager.alert("提示","最少3个点!","info");
	}
	else {
		$("#pointAy").text('');
		var mapPoint=[];
		for(var i=0;i<points.length;i++){
			if(points[i]!=''){
				var x=parseFloat((points[i].split(','))[0]);
				var y=parseFloat((points[i].split(','))[1]);
				mapPoint.push(new BMap.Point(x,y));
			}
		}
		var polygon = new BMap.Polygon(mapPoint, {strokeColor:getRandomColor(), strokeWeight:2, strokeOpacity:0.5,fillColor:getRandomColor()});  //创建区域
		polygon.name=name;
		areaInfoMap.addOverlay(polygon);   //增加区域
        areaInfoMap.setViewport(mapPoint);	//调整视野
		polygon.enableEditing();
	}
}

function autoAddPol() {//自动添加区域
	var points = ($("#pointAy").val()).split(";");
	if(points.length > 3){
		areaInfoMap.clearOverlays();
		var mapPoint=[];
		for(var i = 0;i < points.length;i++){
			if(points[i] != ''){
				var x = parseFloat((points[i].split(','))[0]);
				var y = parseFloat((points[i].split(','))[1]);
				mapPoint.push(new BMap.Point(x,y));
			}
		}
		var polygon = new BMap.Polygon(mapPoint, {strokeColor:getRandomColor(), strokeWeight:2, strokeOpacity:0.5,fillColor:getRandomColor()});  //创建区域
		areaInfoMap.addOverlay(polygon);   //增加区域
		polygon.enableEditing();
	}
}

function addPol(){//添加区域
	var points = ($("#pointAy").val()).split(";");
	if (points.length < 4){
		$.messager.alert("提示","最少3个点!","info");
	}
	else {
		var mapPoints = [];
		for(var i = 0;i < points.length;i++){
			if(points[i] != ""){
				var x = parseFloat((points[i].split(','))[0]);
				var y = parseFloat((points[i].split(','))[1]);
				var point = {lng : x,lat : y};
				mapPoints.push(point);
			}
		}
		var jsonDataPoint = {};
		jsonDataPoint.points = mapPoints;
		$("#maDialogModel").dialog({
			title : "添加区域",
		    width : 400,
		    height : 200,
		    closed : true,
		    modal : true,
		    content : '<form id="frmMaDm" class="config-form" style="width:300px;"></form>',
		    buttons : [ {
				text : "确定",
				iconCls : "icon-ok",
				handler : function() {
					if ($("#frmMaDm").form("validate")) {
						jsonDataPoint.maName = $("#maName").val();
						var flag = true;
						var allMaName = $("#mapAreaCombox").combobox("getData");
						for(var i = 0;i < allMaName.length;i++){
							if(jsonDataPoint.maName == allMaName[i].maName){
								$.messager.alert("提示","区域名称重复","error");
								$("#maDialogModel").dialog("close");
								flag = false;
							}
						}
						if(flag){
							jsonDataPoint.maVisible = $("#maVisible").is(":checked");
							ajaxLoading();
							$.ajax({
								url : "../MapAreaController/addMapAreaPoint",
								type : "post",
								dataType : "json",
								contentType : "application/json;charset=utf-8",     //指定类型
								data : JSON.stringify(jsonDataPoint),
								error : function(json){
									$("#maDialogModel").dialog("close");
									ajaxLoadEnd();
									$.messager.alert("提示",json.detail,"error");
								},
								success : function(json) {
									$("#maDialogModel").dialog("close");
									ajaxLoadEnd();
									if (json.result) {
										$.messager.alert("提示","添加成功！","info");
										initMaCombox();
									} else {
										$.messager.alert("提示",json.detail,"error");
									}
								}
							});
						}
					}
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#maDialogModel").dialog("close");
				}
			} ]
		}).dialog("center");
		//初始化表单
		$("#frmMaDm").html(function() {
			var htmlArr = [];
			var htmlArr = [];
			htmlArr.push(createValidatebox({//防止回车刷新表单
				name : "hiddenText",
				title : "隐藏框",
				noBlank : false,
				ishiden : true
			}));
			htmlArr.push(createValidatebox({
				name : "maName",
				title : "区域名称",
				noBlank : true,
				ishiden : false
			}));
			htmlArr.push(createCheckbox({
				name : "maVisible",
				title : "可见",
				checked : true
			}));
			return htmlArr.join("");
		});
		/* 重绘窗口 */
		$.parser.parse("#maDialogModel");
		$("#maDialogModel").dialog("open");
	}
}

function editPol(){//修改区域信息
	var selectarea = $("#mapAreaCombox").combobox("getText");
	if (selectarea == null || selectarea == "") {
		$.messager.alert("提示", "请选择一个区域进行修改！", "info");
		return false;
	}
	var jsonDataPoint = {};
	$("#maDialogModel").dialog({
		title : "修改区域",
	    width : 400,
	    height : 200,
	    closed : true,
	    modal : true,
	    content : '<form id="frmMaDm" class="config-form" style="width:300px;"></form>',
	    buttons : [ {
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				if ($("#frmMaDm").form("validate")) {
					jsonDataPoint.maName = $("#maName").val();
					var flag = true;
					if(jsonDataPoint.maName != $("#mapAreaCombox").combobox("getText")){
						var allMaName = $("#mapAreaCombox").combobox("getData");
						for(var i = 0;i < allMaName.length;i++){
							if(jsonDataPoint.maName == allMaName[i].maName){
								$.messager.alert("提示","区域名称重复","error");
								$("#maDialogModel").dialog("close");
								flag = false;
							}
						}
					}
					if(flag){
						var maId = $("#mapAreaCombox").combobox("getValue");
						jsonDataPoint.maId = maId;
						jsonDataPoint.maVisible = $("#maVisible").is(":checked");
						ajaxLoading();
						$.ajax({
							url : "../MapAreaController/editMapArea",
							type : "post",
							dataType : "json",
							contentType : "application/json;charset=utf-8",     //指定类型
							data : JSON.stringify(jsonDataPoint),
							error : function(json){
								$("#maDialogModel").dialog("close");
								ajaxLoadEnd();
								$.messager.alert("提示",json.detail,"error");
							},
							success : function(json) {
								$("#maDialogModel").dialog("close");
								ajaxLoadEnd();
								if (json.result) {
									$.messager.alert("提示","修改成功！","info");
									initMaCombox();
								} else {
									$.messager.alert("提示",json.detail,"error");
								}
							}
						});
					}
				}
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#maDialogModel").dialog("close");
			}
		} ]
	}).dialog("center");
	//初始化表单
	$("#frmMaDm").html(function() {
		var htmlArr = [];
		htmlArr.push(createValidatebox({//防止回车刷新表单
			name : "hiddenText",
			title : "隐藏框",
			noBlank : false,
			ishiden : true
		}));
		htmlArr.push(createValidatebox({
			name : "maName",
			title : "区域名称",
			noBlank : true,
			ishiden : false
		}));
		htmlArr.push(createCheckbox({
			name : "maVisible",
			title : "可见",
			checked : true
		}));
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#maDialogModel");
	$("#maDialogModel").dialog("open");
	var selectma = $("#mapAreaCombox").combobox("getText");
	$("#frmMaDm").form("reset");
	$("#frmMaDm").form("load",{
		maName : selectma
	});
}

function delPol(){//删除区域
	var maId = $("#mapAreaCombox").combobox("getValue");
	var maName = $("#mapAreaCombox").combobox("getText");
	if(maId != null && maId != "") {
		$.messager.confirm("删除区域","确认删除区域" + maName + "？",function(r){
			if(r) {
				$.ajax({
					url : "../MapAreaController/deleteMapArea",
					type : "post",
					dataType : "json",
					data : {
						"maId" : maId
					},
					error : function(json){
							ajaxLoadEnd();
							$.messager.alert("提示",json.detail,"error");
						},
						success : function(json) {
							ajaxLoadEnd();
							if (json.result) {
								$.messager.alert("提示","删除成功！","info");
								initMaCombox();
								clearPol();
							} else {
								$.messager.alert("提示",json.detail,"error");
							}
						}
				});
			}
		});
	}
	else {
		$.messager.alert("提示","请选择一个区域！","info");
	}
}

function clearPol(){//清空当前所有区域
		clearPoints();
		areaInfoMap.clearOverlays();
}

/*
function getAllPoints(){//获取坐标点
	var flag=false;
	var name = prompt("请输入获取的地名:",""); 
	if (name != null){ 
		if(name==''){
			alert("未输入地名!"); 
		}else{
			var overlays=areaInfoMap.getOverlays();
			for(var i=0;i<overlays.length;i++){
				var overlay=overlays[i];
				if(overlay.name){
					if(name==overlay.name){
						var points=overlay.getPath(),pointStr='';
						for(var j=0;j<points.length;j++){
							pointStr+=points[j].lng+","+points[j].lat+";";
						}
						$("#staPoint").val(pointStr);
						flag=true;
						
					}
				}
			}
			if(!flag){
				alert("没有匹配的区域!"); 
			}
		}
	}
}
*/

function edit(flag){//启用or停用区域编辑
	var overlays=areaInfoMap.getOverlays();
	for(var i=0;i<overlays.length;i++){
		var overlay=overlays[i];
		if(overlay){
			if(flag=="start"){
				overlay.enableEditing();
			}else{
				overlay.disableEditing();
			}
		}
	}
}

function initMaCombox() {//初始化下拉框
	var url = "../MapAreaController/getMapArea";
	$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		async : true,
		success : function(json) {
			$("#mapAreaCombox").combobox({
				data : json,
				method : 'post',
				valueField : 'maId',
				textField : 'maName',
				onShowPanel: function () {
					// 动态调整高度  
					if (json.length < 20) {
					    $(this).combobox('panel').height("auto");  
					}else{
					    $(this).combobox('panel').height(300);
					}
				}
			});
		}
	});
}

var getRandomColor = function(){
  return '#'+(function(h){
    return new Array(7-h.length).join("0")+h
  })((Math.random()*0x1000000<<0).toString(16))
}