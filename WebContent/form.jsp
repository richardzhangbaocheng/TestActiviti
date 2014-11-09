<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript">
			window.onload=function(){
				$.post("getStartForm.do",
					function (data, textStatus){
					   jQuery('#main').html(data);
					}
				);
			}
		</script>
		<title>用户任务</title>
		<script type="text/javascript" src="js/jquery-1.11.1.js"></script>
	</head>
	<body>
		<center><h3>请假流程</h3></center>
		<div id="main"></div>
	</body>
</html>