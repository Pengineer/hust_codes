<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>My JSP 'load01.jsp' starting page</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <%
   		//读取load传递的json参数数据(json数据的读取可参看json7.jsp,这里是将json数据作为参数进行传递)
   		System.out.println(request.getParameter("username"));
    	System.out.println(request.getParameter("psw"));
		out.println("服务器响应ajax请求的响应数据");
	%>
  </body>
  
</html>
