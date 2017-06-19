<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>json2.jsp</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <script type="text/javascript">
  	var person={"info":[{"name":"zhangsan"},
  	                   {"name":"lisi"},
  	                   {"name":"wangwu"}]}
  	alert(person.info[1].name);
  </script>
  
  <body>
    This is my JSP page.. <br>
  </body>
   
</html>
