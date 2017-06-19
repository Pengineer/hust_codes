<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户注册</title>
  </head>
  
  <body>
    	注册成功. <br>
    	
    	<form action="list/list.action" method="post">
    		<input type="submit" value="显示列表" />
    	</form>
  </body>
</html>
