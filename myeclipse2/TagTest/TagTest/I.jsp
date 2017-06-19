<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/simpletag" prefix="shust"  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>简单标签添加属性</title>
  </head>
  
  <body> 
     <shust:TagDemo5 count="3" date="<%=new Date() %>">
     	attribute<br>
     </shust:TagDemo5>
    	    
  </body>
</html>
