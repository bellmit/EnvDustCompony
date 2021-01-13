$(function() {
	logoutFunc();
});
function logoutFunc() {
	// clearCookie('vimmsusername');
	/* 发送ajax请求注销用户 */
	$.ajax({
		url : "../UserController/toLogout",
		type : "post",
		dataType : "json",
		async:false,
		error : function(json) {
			$.messager.confirm("提示", '退出请求未响应，是否强制退出？', function(r) {
				if (r) {
					window.location.href = "../login.html";
				}
			});
		},
		success : function(json) {
			if (json.result) {
				window.location.href = "../login.html";
			} else {
				$.messager.alert("错误", json.detail, "error");
			}
		}
	});

};