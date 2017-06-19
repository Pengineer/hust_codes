<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>rqd2.jsp</title>
  </head>
  
  <body>
  
  
  	$(data )
  	
  	<% 
  		String data = (String)request.getAttribute("data"); //获取request域中的数据
  		out.write(data);
  	%>
  	
  
   
  </body>
</html>
