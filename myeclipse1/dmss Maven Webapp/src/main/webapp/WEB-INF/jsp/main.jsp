<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>文档管理系统</title>
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all-neptune-debug.css">
		<link rel="stylesheet" type="text/css" href="resources/css/app.css">
		<link rel="stylesheet" type="text/css" href="plugins/mine/css/mine-all.css">
		<script type="text/javascript" charset="utf-8" src="extjs/ext-all-debug-w-comments.js"></script>
		<script type="text/javascript" charset="utf-8" src="extjs/locale/ext-lang-zh_CN.js"></script>
		
		
		<script type="text/javascript" charset="utf-8" src="app/components/validate.js"></script>		
		<!--文件上传-->
		<script type="text/javascript" src="plugins/jquery/jquery-1.8.3.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/mine/js/jquery-mine-all.js"></script>
		<script type="text/javascript" src="plugins/jquery/jquery.cookie.js"></script>
		<script type="text/javascript" src="plugins/uploadify/js/jquery.uploadify.js"></script>
		<script type="text/javascript" src="plugins/uploadify/js/jquery.uploadify-ext.js"></script>
		<script type="text/javascript" charset="utf-8" src="app.js"></script>
		<script type="text/javascript">
			var username= "${username}";
		</script>
		
	</head>
	<body>
		<div id="fi-form"></div>
		<div id="panelA"></div>
	</body>
</html>