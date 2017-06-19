<%@ page language="java" contentType="text/html; charset=utf-8"
     pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->	
	<script type="text/javascript" src="plugins/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="plugins/highcharts/js/highcharts.js"></script>
	<script type="text/javascript" src="js/sm/cluster/monitor/tomcat.js"></script>
  </head>
  
  <body>
  	<input type="hidden" id="name" value="${name}">
  	<input type="hidden" id="total" value="${total}">
  	<input type="hidden" id="cpuSeries" value="${cpuSeries}">
  	<input type="hidden" id="memorySeries" value="${memorySeries}">
    <div id="cpuRate" style="min-width: 310px; height: 200px; margin: 0 auto"></div>
    <div id="memoryRate" style="min-width: 310px; height: 200px; margin: 0 auto"></div>
  </body>
</html>
