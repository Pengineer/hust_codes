<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>jsp:setProperty标签</title>
  </head>
  
  <body>	
  	<!-- 首先得到与JSP相关联的java类 ,并创建一个类的实例对象person-->
  	<jsp:useBean id="person" class="edu.hust.domain.Person" scope="page"/> 
    <%=person.getName() %>
  </body>
</html>
