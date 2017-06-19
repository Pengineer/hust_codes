<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>hello.jsp</title>
  </head>
  
  <body>
  
  	<%-- 或则：action="<%=request.getContextPath()%>/test1/formparam.action" --%>
     <form action="${pageContext.request.contextPath }/test1/formparam" method="post">
     	id:<input type="text" name="id"/><br/>
     	name:<input type="text" name="name"/><br/>
     	personID：<input type="text" name="person.id"/><br/>
     	personName:<input type="text" name="person.name"/><br/>
     	personAge:<input type="text" name="person.age"/><br/>
     	<input type="submit" value="提交"><br/>
     </form>   
  </body>
</html>