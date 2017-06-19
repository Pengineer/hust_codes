<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>attrmessage.jsp</title>
  </head>
  
  <%--通过EL表达式获取属性值：XXXXScope --%>
  <body>
  	方式1： <br>
     ${applicationScope.attrname1 }<br>
     ${sessionScope.attrname2 }<br>
     ${requestScope.attrname3 }<br>
     <hr>   
     
     <c:forEach var="name" items="${attrnames }">
     	${name }<br>
     </c:forEach>
     
     <hr>
             方式2：<br>
      ${requestScope.attr1 }<br>
      ${sessionScope.attr2 }<br>
      ${applicationScope.attr3 }<br>
  </body>
</html>
