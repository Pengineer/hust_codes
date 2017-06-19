<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/mytaglib" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>模仿if-else标签</title>
  </head>
  
  <body>
  	<%
  		session.setAttribute("login", false); //模拟代码
   	%>
    <c:choose>
    	<c:when login="${login }">aaa</c:when>
    	<c:otherwise>bbb</c:otherwise>
    </c:choose>
  </body>
</html>
