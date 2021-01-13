/*******************************************************************************
 * 功能：上传文件列表 日期：2018-12-03 10:44:09
 ******************************************************************************/
var appendFileListcontent =
	    '<div style="padding:20px 10px;"><div id="btnlist1" >'
		+ '&nbsp;&nbsp;&nbsp;<a href="#" id="allid" class="easyui-linkbutton c1  selected" data-options="" style="font-size: 14px;padding:2px 7px;" onclick="getFilesList()" >全部</a>'
		+ '&nbsp;&nbsp;&nbsp;<a href="#" id="dayid" class="easyui-linkbutton c1  " data-options="plain:true" style="font-size: 14px;padding:2px 7px;" onclick="getFilesList(\'day\')">日报</a>'
		+ '&nbsp;&nbsp;&nbsp;<a href="#" id="monthid" class="easyui-linkbutton c1 " data-options="plain:true" style="font-size: 14px;padding:2px 7px;" onclick="getFilesList(\'month\')">月报</a>'
		+ '&nbsp;&nbsp;&nbsp;<a href="#" id="yearid" class="easyui-linkbutton c1 " data-options="plain:true" style="font-size: 14px;padding:2px 7px;" onclick="getFilesList(\'year\')">年报</a>'
		+ '&nbsp;&nbsp;&nbsp;<a href="#" id="otherid" class="easyui-linkbutton c1 " data-options="plain:true" style="font-size: 14px;padding:2px 7px;" onclick="getFilesList(\'other\')">其他</a>'
		+ '<a href="#" id="uploadid" class="easyui-linkbutton c1 a" data-options="plain:true,iconCls:\'icon-upload\'" style="float:right;margin-left:15px;" onclick="uplodDialog()">上传</a>'
		+'</div>'
		+'<div id="fileslist1" class="fileslistclass" style="width: 100%;height: auto;margin-top:20px;"></div></div>';

addPanel("文件上传下载列表", appendFileListcontent);
$("#btnlist1 a").click(function(e){
	if("uploadid"!=$(this).attr('id')){
		$("#btnlist1 .selected").removeClass("selected");
		$(this).addClass("selected");
	}
})

var selectedFileListTab;
getFilesList();

/**
 * 根据报告类型查询文件列表
 * @param reporttype 报告类型
 * "0":全部
 * "1":日报
 * "2":月报
 * "3":年报
 * "4":其他
 */
function getFilesList(reportType){
	//testdata是文件列表数据需要ajax请求数据，这里是模拟
//	var testdata = [{"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"兰州市空气质量分析与研判日报（2018.1.8）.pdf"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.doc","text":"兰州市空气质量分析与研判日报（2018.1.8）.doc"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"测试报告3"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"测试报告1"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"测试报告1"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"兰州市空气质量分析与研判日报（2018.1.8）.pdf"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"兰州市空气质量分析与研判日报（2018.1.8）.pdf"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"兰州市空气质量分析与研判日报（2018.1.8）.pdf"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"兰州市空气质量分析与研判日报（2018.1.8）.pdf"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"兰州市空气质量分析与研判日报（2018.1.8）.pdf"},
//	            {"url":"/doc/兰州市空气质量分析与研判日报（2018.1.8）.pdf","text":"兰州市空气质量分析与研判日报（2018.1.8）.pdf"}];
//	createFileslist($("#fileslist1"),testdata);//测试用的
	selectedFileListTab = reportType;
	$.ajax({
		url : "../FileOperateController/queryAllUploadFile",
		type : "post",
		dataType : "json",
		data : {"fileType":reportType},
		success : function(json) {
			ajaxLoadEnd();
			if (json.result) {
				$("#fileslist1").html("");
				if(json.rows.length>0){
					createFileslist($("#fileslist1"),json.rows);//创建文件列表
				}
				
			} else {
				$.messager.alert("错误", json.detail, "error");
			}
		}
	});
}


/**
 * 上传文件的筛选条件弹出框
 */
function uplodDialog(){
	if(selectedFileListTab == null || selectedFileListTab == ""){
		selectedFileListTab = "day";
	}
	// 调用获取数据的一些方法
	$("<div></div>").dialog({
		id:"fileUploadfilterDialog",
		width :420,
		height : 230,
		title : "上传文件",
		//iconCls : icon,
	    modal: true,
		maximized : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		cache : false,
		resizable : true,
		closed : true,
		content : '<form id="frmdialogModel" class="config-task" style="padding:15px 10px;"></form>',
	    onClose : function() {
	         $(this).dialog('destroy');
	    },	
	    buttons : [ {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#fileUploadfilterDialog").dialog("destroy");
			}
		} ]
	}).dialog('center');
	// 添加任务表单
	$("#frmdialogModel").html(function() {
		var htmlArr = [];
		htmlArr.push('<div>');
		htmlArr.push(createCombobox({
			name : "fileType",
			title : "文件类型：",
			data:{"day":"日报","month":"月报","year":"年报","other":"其他"},
		    value:selectedFileListTab,
			noBlank : true
		}));
		htmlArr.push('</div>');
		htmlArr.push('<div style="margin-top:10px;"><label>选择文件：</label><input  type="file" id="myFiles" name="myFiles" /> <input type="button" value="上传" style="width:60px;" onclick="ajaxFileUpload();" /></div>');
		return htmlArr.join("");
	});
	/* 重绘窗口 */
	$.parser.parse("#fileUploadfilterDialog");
	$("#fileUploadfilterDialog").dialog("open");
}

/**
 * 上传文件
 * @returns {Boolean}
 */
function ajaxFileUpload() {
	var fileType = $("#fileType").combobox('getValue');
	var fileTypeName;
	if(fileType == "month"){
		fileTypeName = "月报类型文档";
	}else if (fileType == "year"){
		fileTypeName = "年报类型文档";
	}else if (fileType == "other"){
		fileTypeName = "其他类型文档";
	}else{
		fileTypeName = "日报类型文档";
	}
	if(!confirm("上传后将不能删除此文档，确认继续此'"+fileTypeName+"'上传吗？")){
		return;
	}
	//判读上传文件是否是指定类型
	var file = $("#myFiles")[0].value;
    if (file == null||file == ""){
         alert("请选择要上传的文件!");
         return false;
    }
    if (file.lastIndexOf('.')==-1){    //如果不存在"."  
        alert("路径不正确!");
        return false;
    }
    var AllImgExt=".docx|.doc|.pdf|.xlsx|.xls|";
    var extName = file.substring(file.lastIndexOf(".")).toLowerCase();//（把路径中的所有字母全部转换为小写）        
    if(AllImgExt.indexOf(extName+"|")==-1)        
    {
        ErrMsg="该文件类型不允许上传。请上传 "+AllImgExt+" 类型的文件，当前文件类型为"+extName;
        alert(ErrMsg);
        return false;
    }
    var fileName = file.substring(file.lastIndexOf("\\")+1);
    if(fileName<=0){
    	alert("未找到上传文件名称，可以切换到chrome浏览器尝试！");
        return false;
    }
    //判断是否存在相同的上传文件
    var existFile = false;
    $.ajax({
		url : "../FileOperateController/queryExsitFile",
		type : "post",
		dataType : "json",
		async:false,
		data : {
			"fileType" : fileType,
			"fileName" : fileName
		},
		error : function(data) {
			//输出到前台
			console.log(JSON.stringify(data));
			return false;
		},
		success : function(json) {
			if(json.result){
				existFile = confirm("已存在相同名称的文件，是否覆盖？");
			}else{
				existFile = true;
			}
		}
	});
    if(existFile){
    	ajaxLoading();
    	//上传文件的ajax请求
        $.ajaxFileUpload({  
                url: './../FileOperateController/fileUpload?fileType='+fileType, //用于文件上传的服务器端请求地址  
                secureuri: false, //是否需要安全协议，一般设置为false  
                fileElementId: 'myFiles', //文件上传域的ID  
                dataType: 'json', //返回值类型 一般设置为json  
                type: 'post',  
                data:{"fileType":fileType},
                success: function(data)  //服务器成功响应处理函数  
                {  
                	ajaxLoadEnd();
                	if(data.result){
            		   var file = $("#myFiles");
                       file.after(file.clone().val(""));
                       file.remove();
                   	   $("#fileUploadfilterDialog").dialog("destroy");
                	   $.messager.alert("提示", data.detail, "info");
                	   getFilesList(fileType);
                	   if(fileType==""){
                		   fileType = "allid";
                	   }
                	   $("#"+fileType+"id").trigger("click");
                	}else{
                		$.messager.alert("错误", data.detail, "error");
                	}
                },  
                error: function (data, status, e)//服务器响应失败处理函数  
                {  
                	ajaxLoadEnd();
                	$.messager.alert("错误", "服务器连接失败或超出上传限制（10M）", "error");
                }  
      
            });
        return false;
	}else{
		return false;
	}
}

/**
 * 创建文件列表控件
 * @param domId 节点id
 * @param data	列表数据
 */
function createFileslist(domId,data){
	var htmlArr =[];
    for(var i=0;i<data.length;i++){
    	var extName = data[i].fileRelativePath.substring(data[i].fileRelativePath.lastIndexOf(".")).toLowerCase();//（把路径中的所有字母全部转换为小写）  
        var pic = getFileTypePic(extName);
        var text = data[i].fileName;
        var index=data[i].fileName.indexOf("-");
        text=text.substring(index+1,text.length);
    	htmlArr.push('<div  style="width:90px; float:left; padding-right:27px">');
    	htmlArr.push('<div class="img" style="width: 55px;height: 95px; border: 0px solid #d3d3d3;cursor: pointer;">');
    	htmlArr.push('<a style="color: #4b4b4b;text-decoration: none;" href="'+data[i].fileRelativePath+'" target="_blank" class="zshowbox">');
    	htmlArr.push('<img src="'+pic+'" width="95px" height="95px">');
    	htmlArr.push('</a>');
    	htmlArr.push('</div>');
    	htmlArr.push('<div class="text_center" title="'+text+'" style="height:36px; line-height: 18px;text-align: center;">');
    	htmlArr.push('<a style="color: #4b4b4b;text-decoration: none;margin:0px;" href="'+data[i].fileRelativePath+'" target="_blank"><span style="display: -webkit-box;overflow: hidden;text-overflow: ellipsis;word-wrap: break-word;white-space: normal !important;-webkit-line-clamp: 2;-webkit-box-orient: vertical;">'+text+'<span></a>');
    	htmlArr.push('</div>');
    	htmlArr.push('</div>');
    }
    domId.append(htmlArr.join(""));
}

/**
 * 判断显示文件类型的图片
 * @param extName
 * @returns {String}
 */
function getFileTypePic(extName){
	if(".pdf".indexOf(extName)>-1){
		return "./../images/sitereport.png";
	}else if(".xlsx".indexOf(extName)>-1){
		return "./../images/xlsreport.png";
	}else{
		return "./../images/wordreport.png";
	}
}