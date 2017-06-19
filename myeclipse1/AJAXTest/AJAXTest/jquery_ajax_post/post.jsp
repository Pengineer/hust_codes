<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    
<%
 	//读取load传递的json参数数据(json数据的读取可参看json7.jsp,这里是将json数据作为参数进行传递)
 	System.out.println(request.getParameter("username"));
  	System.out.println(request.getParameter("psw"));
	out.println("服务器响应ajax请求的响应数据");
%>
