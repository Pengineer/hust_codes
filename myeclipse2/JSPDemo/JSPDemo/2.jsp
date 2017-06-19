<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>jsp:setProperty标签和getProperty标签</title>
  </head>
  
  <body>
  	
  	<jsp:useBean id="person" class="edu.hust.domain.Person" scope="page"/>
    
    <!-- 手动为bean属性赋值 -->
    <jsp:setProperty name="person" property="name" value="abc"/>  
    <%=person.getName() %><br/>
    
    <!-- 用请求参数给bean的属性赋值,将请求参数中name的值付给person.name:http://localhost:8080/JSPDemo/2.jsp?name=cba -->
    <jsp:setProperty name="person" property="name" param="name"/>
	<%=person.getName() %> <br/>   
	
	<!-- 将多个请求参数赋给bean的属性:http://localhost:8080/JSPDemo/2.jsp?name=cba&age=12 -->
    <jsp:setProperty name="person" property="name" param="name"/>
    <jsp:setProperty name="person" property="age" param="age"/>  <!--支持8种基本数据类型的转换 -->
	<%=person.getName() %> <br/> 
	<%=person.getAge() %> <br/>
	
	<!-- 使用getProperty标签获取person对象的属性值 -->
	<jsp:getProperty property="name" name="person" />
	<jsp:getProperty property="age" name="person" />
    
  </body>
</html>
