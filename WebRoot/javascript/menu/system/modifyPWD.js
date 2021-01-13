/*初始化窗口*/
$("#dialogModifyPWD").dialog({
	width: 400,
	height: 220,
    title: "修改密码",
    inline: true,
    modal: true,
    maximized: false,
    collapsible: false,
    minimizable: false,
    maximizable: false,
    resizable: false,
    content: '<form id="frmdialogModifyPWD" class="config-form"></form>',
    buttons: [{
	    text:"确定",
	    iconCls:"icon-ok",
	    handler : function() {
			if ($("#frmdialogModifyPWD").form("validate")) {
				if ($("#new_pwd").val() == $("#again_pwd").val()) {
					ajaxLoading();
					/* 发送ajax请求 */
					$.ajax({
						url : "../UserController/updateUsersPwd",
						type : "post",
						dataType : "json",
						data : {
							"oldPwd" : $("#old_pwd").val(),
							"newPwd" : $("#new_pwd").val()
						},
						error : function(json) {
							ajaxLoadEnd();
						},
						success : function(json) {
							ajaxLoadEnd();
							if (json.result) {
								$("#dialogModifyPWD").dialog("close");
								$.messager.alert("提示", "密码修改成功！", "info");
							} else {
								$.messager.alert("错误", json.detail, "error");
							}
						}
					});
				} else {
					$.messager.alert("提示", "两次密码输入不一致！", "info");
				}
			}
	    }
	},{
	    text:"取消",
	    iconCls:"icon-cancel",
	    handler:function(){
	        $("#dialogModifyPWD").dialog("close");
	    }
	}]
}).dialog('center');
/*初始化表单*/
$("#frmdialogModifyPWD").html(function(){
	var htmlArr = [];
	htmlArr.push(createValidatebox({
		name: "old_pwd",
		title: "原密码",
		isPwd: true,
		noBlank: true,
		type :'loginPwd'
	}));
	htmlArr.push(createValidatebox({
		name: "new_pwd",
		title: "新密码",
		isPwd: true,
		noBlank: true,
		type :'loginPwd'
	}));
	htmlArr.push(createValidatebox({
		name: "again_pwd",
		title: "确认密码",
		isPwd: true,
		noBlank: true,
		type :'loginPwd'
	}));
	return htmlArr.join("");
});
/*重绘窗口*/
$.parser.parse("#dialogModifyPWD");