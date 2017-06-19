<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>DMSS</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="plugins/uploadify/css/uploadify.css"> 

<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
		    	//请求方式为get5.
		    	 type:"GET",
		    	 url:"fm/version/resetUpload",
		    	 dataType: "json"
		});

		$("#checkin").uploadifyExt({
			uploadLimitExt : 1,
			fileSizeLimit : '1000MB',
			fileTypeDesc : '附件'
		});
			
		Ext.create("Ext.Button", {
        	renderTo: "checkin-button",
        	text: "上传",
        	allowDepress: true,     //是否允许按钮被按下的状态
        	enableToggle: true,     //是否允许按钮在弹起和按下两种状态中切换
        	id: "bt2"
    	});

	});	
</script>

</head>
<body>
	<div id="test1" />
	<input type="file" id="checkin" />
</body>
</html>
