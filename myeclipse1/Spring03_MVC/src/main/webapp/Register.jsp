<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="st"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户注册</title>
  </head>
  
  <body>
    <form action="<st:url value="/register22"/>" > <!-- 使用spring自带的标签库解决url路径问题（前面不用带项目名） -->
    	<!--
    	1:${requestScope.count }<br>
    	2:${sessionScope.count }<br>
    	3:${requestScope.title }<br>
    	4:${sessionScope.title }<br>
    	5:${requestScope.desc }<br>
    	6:${sessionScope.desc }<br>
    	-->
    	<!-- 
    	1:${requestScope.class }<br>
    	2:${sessionScope.class }<br>
    	3:${requestScope.school }<br>
    	4:${sessionScope.school }<br>
    	5:${requestScope.stu }<br>
    	6:${sessionScope.stu }<br>
    	
    	${class }
    	-->
    	<!-- 
    	1:${sessionScope.country }<br>
    	-->
    	<!--
    	${userr.name }
    	-->
    	name:<input type="text" name="username"/><br/>
    	secondname:<input type="text" name="secondname"/><br/>
    	tijiao:<input type="submit" value="提交" />
    	</form>
  </body>
</html>
