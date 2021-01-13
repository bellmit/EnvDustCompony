var filterData = {"deviceCode":'',"thingCode":'',"select":'',"updateType":'2061'};;
var currentTab = 0;
getStationPhone();
getMonitorPhone();
getDataTypePhone();
$(function() {
	var monitorstationPhone = "";
	var monitorCode = "";
	var dataTypePhone = "";
	var updateTypeCode= "";

	/**筛选**/
	$("#headerFiltrate").click(function() {
		$("#btnlist").css('display',"none");
		$("#myModal4").modal({backdrop: 'static', keyboard: false});
	});
	/*选择监控站点*/
	var stationPhoneHeight, stationPhoneListHeight, stationPhone = $(".filtrate-stationPhone-list li.active>a").html();
	$(".filtrate-stationPhone-list li a").click(function() {
		$(".filtrate-stationPhone-list li").removeClass("active");		//取消其他监控站点的选中状态
		$(this).parent("li").addClass("active");						//给被点击监控站点标记选中状态
		$(this).next("ul").find("ul").hide();						//关闭被点击监控站点的孙监控站点
		$(this).parent("li").siblings("li").children("ul").hide();	//关闭父监控站点其他同等级类的子监控站点
		$(this).next("ul").show();									//显示被点击监控站点的子监控站点
		stationPhone = $(this).html();									//获取被选中的监控站点值
		stationPhoneHeight = parseInt($(".filtrate-stationPhone").css("height"));			//获取监控站点盒子的高度
		stationPhoneListHeight = parseInt($(".filtrate-stationPhone-list").css("height"));//获取监控站点列表的高度
		
	});
	/*选择监控物*/
	$(".filtrate-monitorstationPhone li a").click(function() {
		var prev = $(".filtrate-monitorstationPhone ul li.active")[0].id;
		var monitorstationPhoneLi = $(this).parent("li");
		if(prev!=undefined && monitorstationPhoneLi[0].id!=prev){
			var monitorstationPhoneState = monitorstationPhoneLi.hasClass("active");
			if (monitorstationPhoneState == true) {
				monitorstationPhoneLi.removeClass("active");
				monitorstationPhone = "";
				monitorCode = "";
			} else {
				monitorstationPhoneLi.addClass("active").siblings("li").removeClass("active");
				monitorstationPhone = $(this).html();
				monitorCode =  $(this).attr('id');
				
			}
		}
	});
	/*收起&展开监控物*/
	$("#monitorstationPhone a").click(function() {
		var categoryState = $(this).find("span").html();
		var monitorstationPhoneHeight = $(".filtrate-monitorstationPhone ul").css("height");
		if (categoryState != "收起") {//展开监控物
			$("#monitorstationPhone").addClass("show");
			$("#monitorstationPhone a span").html("收起");
			$(".filtrate-monitorstationPhone").animate({height: monitorstationPhoneHeight});
		} else {//监控监控物
			$("#monitorstationPhone").removeClass("show");
			if (monitorstationPhone == "") {//未选监控物
				monitorstationPhone = "全部";
				monitorCode = "";
			}
			$("#monitorstationPhone a span").html(monitorstationPhone);
			$(".filtrate-monitorstationPhone").animate({height: 0});
		}
	});

	/*选择数据类型*/
	$(".filtrate-dataTypePhone li label").click(function() {
		var prev = $(".filtrate-dataTypePhone ul li.active")[0].id;
		var dataTypePhoneLi = $(this).parent("li");
		if(prev!=undefined && dataTypePhoneLi[0].id!=prev){
			var conditionState = dataTypePhoneLi.hasClass("active");
			if (conditionState == true) {
				dataTypePhoneLi.removeClass("active");
				dataTypePhone = "";
				updateTypeCode = "";
			} else {
				dataTypePhoneLi.addClass("active").siblings("li").removeClass("active");
				dataTypePhone = $(this).html();
				updateTypeCode = $(this).attr('id');
			}
		}
	});

	/*清空筛选条件*/
	$(".filtrate-reset").click(function() {
		/*清空监控站点*/
		$("#stationPhone").removeClass("show");
		$("#stationPhone a span").html("展开");
		stationPhone = $(".filtrate-stationPhone-list li.active>a").html();
		$(".filtrate-stationPhone-list li").removeClass("active");
		$(".filtrate-stationPhone-list>li").addClass("active");
		$(".filtrate-stationPhone-list li ul li ul").hide();
		/*收起监控站点*/
		stationPhoneHeight = parseInt($(".filtrate-stationPhone").css("height"));	
		if (stationPhoneHeight > 0) {
			$(".filtrate-stationPhone").animate({height: 0});
		}
		
		/*清空监控站点*/
		$("#monitorstationPhone").removeClass("show");
		$("#monitorstationPhone a span").html("展开");
		monitorstationPhone = "";
		/*监控物质*/
		var monitorstationPhoneBoxHeight = parseInt($(".filtrate-monitorstationPhone ul").css("height"));
		if (monitorstationPhoneBoxHeight > 0) {
			$(".filtrate-monitorstationPhone").animate({height: 0});
		}
		/*清空已选监控站点*/
		$(".filtrate-monitorstationPhone ul li").removeClass("active");
		/*清空数据类型*/
		$(".filtrate-dataTypePhone ul li").removeClass("active");
	});
	/*从筛选返回主体页面*/
	$("#filtrateBackContains, .filtrate-submit").click(function() {
		filterData = {"deviceCode":'02',"thingCode":'1',"select":'02',"updateType":'2011'};
		$("#header").show();
		$("#myModal4").modal('hide');
		$("#btnlist").css('display',"");
		if($('#treeview-checkable').treeview('getSelected')[0]==undefined){
			alert("请至少选择一个监测站点");
			return;
		}
		var deviceCode = $('#treeview-checkable').treeview('getSelected')[0].id;
		var thingCode = $(".filtrate-monitorstationPhone ul li.active")[0].id;
		var select = deviceCode;
		var updateType = $(".filtrate-dataTypePhone ul li.active")[0].id;
		var data = {"deviceCode":deviceCode,"thingCode":thingCode,"select":select,"updateType":updateType};
		filterData = data;
		if(currentTab ==1){
			searchChartFunction(data);
		}else{
			queryStationInfoGrid(data);
		}
	});
	/*从筛选返回主体页面*/
	$("#filtrateBackContains, .filtrate-cancle").click(function() {
		$("#header").show();
		$("#myModal4").modal('hide');
		$("#btnlist").css('display',"");
	});
});

//获取监控物质
function getMonitorPhone(){
	//监控物
	$.ajax({
		url : "./../../../MonitorStorageController/getAthorityMonitors",
		type : "post",
		dataType : "json",
		async:false,
		success : function(json) {
			$("#monitorPhone").html("");
			var htmlArr = [];
			for(var i=0;i<json.length;i++){
				if(i==0){
					htmlArr.push("<li id="+json[i].code+" class='active'><a id="+json[i].code+" href='javascript:void(0)' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;'>"+json[i].describe+"</a></li>");
				}else{
					htmlArr.push("<li id="+json[i].code+"><a id="+json[i].code+" href='javascript:void(0)' style='width:100%;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:block;'>"+json[i].describe+"</a></li>");
				}
			}
			$("#monitorPhone").html(htmlArr.join(""));
		}
	});
}  
//获取数据类型
function getDataTypePhone(){
	var htmlArr = [];
	$("#dataTypePhoneCombox").html("");
	for(var i=0;i<ornCnCode.length;i++){
		if(ornCnCode[i].id=="2061"){
			htmlArr.push("<li id="+ornCnCode[i].id+" class='active'><label id="+ornCnCode[i].id+">"+ornCnCode[i].name+"</label></li>");
		}else{
			htmlArr.push("<li id="+ornCnCode[i].id+"><label id="+ornCnCode[i].id+">"+ornCnCode[i].name+"</label></li>");
		}
	}
	$("#dataTypePhoneCombox").html(htmlArr.join(""));
}

//获取站点信息
function getStationPhone(){
	$.ajax({
		url : "./../../../AreaController/queryBottomAreas",
		type : "post",
		dataType : "json",
		success : function(json) {
			if(json!=null && json!=undefined){
				var data = json.rows;
				var parentArr = [];
			    for(var i=0;i<data.length;i++){
					parentArr.push({'id':data[i].areaId,'text':data[i].areaName,'levelFlag':data[i].levelFlag,'nodes': []});
				}
				$('#treeview-checkable').treeview({
			          data: parentArr,
			          showIcon: false,
			          multiSelect:false,
			          showCheckbox: false,
			          onNodeChecked: function(event, node) {
			            $('#checkable-output').prepend('<p>' + node.text + ' was checked</p>');
			          },
			          onNodeExpanded:function(event, data) {
			              if(data.nodes!=undefined && data.nodes.length==0){
			            	  $.ajax({
		                          type: "Post",
		                          url: "./../../../DeviceController/queryAreaAuthDevice",
		                          data:{"areaId":data.id},
		                          dataType: "json",
		                          success: function (json) {
		                        	 if(json == null){
		                        		 alert("系统超时，请重新登录!");
		                        		 window.location.href = "/loginPhone.html";
		                        	 }
		                          	 var chliddata = json.rows;
		                               for (var index = 0; index < chliddata.length; index++) {
		                                  var item = chliddata[index];
		                                  $("#treeview-checkable").treeview("addNode",
		                                      [
		                                          data.nodeId,
		                                          { node: {'id':item.deviceCode,'text':item.deviceName,'levelFlag':item.levelFlag}, silent: true }
		                                      ]);
		                              } 
		                          }
		                      });
			              }
			          }
	                        
		        });
			}
		}
	});
}