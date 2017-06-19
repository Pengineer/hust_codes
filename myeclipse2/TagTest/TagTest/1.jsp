<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hust.edu.cn" prefix="hust" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用一个内部结束标签代替一段java代码</title>
</head>
  
  <body>
  	<%--1，含有java代码的输出 --%>
    <%
    	String IP = request.getRemoteAddr();
    	out.write("您的IP地址是："+IP);
     %>  
     
     <br><br><br>
     
     <%--2,用自定义标签实现输出 ,从而避免jsp文件中出现java代码--%> 
   	  您的IP地址是：<hust:viewIP/>        <%--参看jsp文件被翻译成的java文件，了解标签被调用的底层原理 --%>
  	

  </body>
</html>
