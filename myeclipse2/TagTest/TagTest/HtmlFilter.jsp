<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/mytaglib" prefix="cf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>创建转义标签（应用：论坛留言必用）</title>
  </head>
  
  <body>
  	<%--1,自定义标签实现转义 --%>
    <cf:filter>
    	<a href="www.sina.com.cn">超链接</a>
    </cf:filter><br>
    
    <%--2,sun公司的核心标签库来实现转义 --%>
    <c:out value="<a href='www.sina.com.cn'>超链接</a>" escapeXml="true"></c:out><br>
    
    <%--3,sun公司的函数库来实现转义 --%>
    ${fn:escapeXml("<a href='www.sina.com.cn'>超链接</a>") }
  </body>
</html>
