<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'MyTestJsp.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script language="javascript" src="/EnvDust/javascript/jquery-easyui-1.4.4/jquery.min.js"></script>

<script language="javascript">
	function ajaxTest() {
		var data1 = {
			"id" : "1",
			"name" : "2erw"
		};
		$.ajax({
			data : data1,
			type : "post",
			dataType : "json",
			url : "/EnvDust/TestController/toTest",
			error : function(data) {
				alert("出错了！！:" + data.msg);
			},
			success : function(data) {
				$.each(data, function(n, value) {
					alert("Data Saved: " + value.id + "--" + value.name);
				});
			}
		});
	}

	function testajax() {
		$.ajax({
			data : {
				"strTest1" : "123",
				"strTest2" : "456",
			},
			type : "post",
			url : "/EnvDust/TestController/toGson",
			dataType : "json",
			error : function(data) {
				alert("出错了！！:" + data);
			},
			success : function(data) {
				/*
				for (i = 0; i < data.length; i++) {
					alert("id:" + data[i].id + ",name:"
							+ data[i].name + "");
				}
				 */
				$.each(data, function(n, value) {
					alert("Data Saved: " + value.id + "--" + value.name);
				});

			}
		});

	}
</script>
</head>

<body>
	<h1>从服务器返回springmvc字符串->${message}</h1>
	<button id="testajax" onclick="testajax()">测试AJAX接受</button>
	<button id="testajax1" onclick="ajaxTest()">测试AJAX传递参数</button>
</body>
</html>
