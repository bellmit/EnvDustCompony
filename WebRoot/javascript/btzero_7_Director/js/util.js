function getStatusIcon(statusCode){
	var htmlstr = '<span class="sm-st-icon st-green"><i class="fa fa-check-square-o"></i></span>';
	if("N"== statusCode){//N//NT超标//O断链//Z无数据
		htmlstr = '<span class="sm-st-icon st-green"><i class="fa fa-check-square-o"></i></span>';
	}else if("NT" == statusCode){
		htmlstr = '<span class="sm-st-icon st-red"><i class="fa fa-exclamation-triangle"></i></span>';
	}else {
		htmlstr = '<span class="sm-st-icon st-gray"><i class="fa fa fa-chain-broken"></i></span>';
	}
	return htmlstr;
}

/**
 * 确定提示框
 * @param msg
 */
function confirm(msg){
	var htmlstr =	'<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'
	    +'<div class="modal-dialog">'
	    +'<div class="modal-content">'
	    +'<div class="modal-header">'  
	    +'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'          
	    +'<h4 class="modal-title"><i class="fa fa fa-warning " style="color:red"></i>提示</h4>'
	    +'</div>'       
	    +'<div class="modal-body">'  
	    + msg
	    +'</div>'
	    +'<div class="modal-footer">'      
	    +'<button data-dismiss="modal" class="btn btn-success" type="button">取消</button>'      
	    +'<button class="btn btn-success" type="button" onclick="handleFunc()">确定</button>'      
	    +'</div>'
	    +'</div>'
	    +'</div>'
	    +'</div>';
	 $(htmlstr).appendTo("body").css({	
		"display":"block",
		"z-index":9999,
		"width":"100%",
		"height":$(window).height()});
	 $("#myModal2").modal();
}

function loading(){
	if($("#myModal3")[0]==undefined){
		var htmlstr = '<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'
		    +'<div class="loading">'
		      +'<span></span>'
		      +'<span></span>'
		      +'<span></span>'
		      +'<span></span>'
			  +'<span></span>'
			  +'</div></div>';
			 $(htmlstr).appendTo("body").css({	
					"display":"block",
					"z-index":9999,
					"width":"100%",
					"height":$(window).height()});
			 $("#myModal3").modal();
	}else{
		$("#myModal3").modal('show');
	}
}
function endLoading(){
	$("#myModal3").modal('hide');
}
function handleFunc(){
	window.location.href="./../../index.html";
	$('#myModal2').modal('hide')
}

/**
 * 确定提示框
 * @param msg
 */
function mydialog(msg){
	var htmlstr =	'<div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="right: -320px;  -webkit-transition: opacity 0.3s linear, right 0.3s ease-out;  -moz-transition: opacity 0.3s linear, right 0.3s ease-out;   -o-transition: opacity 0.3s linear, right 0.3s ease-out;  transition: opacity 0.3s linear, right 0.3s ease-out;">'
	    +'<div class="modal-dialog">'
	    +'<div class="modal-content">'
	    +'<div class="modal-header">'  
	    +'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'          
	    +'<h4 class="modal-title"><i class="fa fa fa-warning " style="color:red"></i>提示</h4>'
	    +'</div>'       
	    +'<div class="modal-body">'  
	    + msg
	    +'</div>'
	    +'<div class="modal-footer">'      
	    +'<button data-dismiss="modal" class="btn btn-success" type="button">取消</button>'      
	    +'<button class="btn btn-success" type="button" onclick="handleFunc()">确定</button>'      
	    +'</div>'
	    +'</div>'
	    +'</div>'
	    +'</div>';
	 $(htmlstr).appendTo("body").css({	
		"display":"block",
		"z-index":9999,
		"width":"100%",
		"height":$(window).height()});
	 $("#myModal2").modal();
}