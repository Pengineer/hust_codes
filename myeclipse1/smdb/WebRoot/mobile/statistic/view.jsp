<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>统计分析</title>
		<link href="/smdb/css/mobile/view.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<div id="listHoder"></div>
		<div id="highchartHolder"></div>
		<s:hidden name="jsonMap" id="jsonMapData" />
		
		 
		<script type="text/javascript" src="/smdb/tool/jquery.ui/js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="/smdb/tool/highcharts/highcharts.js"></script>
		<script type="text/javascript" src="/smdb/javascript/mobile/statistic/statistic.js"></script>
	</body>
</html>