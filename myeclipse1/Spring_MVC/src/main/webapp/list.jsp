<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户列表</title>
  </head>
  
  <body>
  	<s:debug></s:debug>
    <s:iterator value="users" id="item">
    	<s:property value="#item.username"/>  <!-- OGNL表达式 -->
    	${item.username }  <!-- EL表达式 -->
    </s:iterator>
    
    <br><br>
    
    <s:iterator value="users">
    	<s:property value="username"/>
    </s:iterator>
    
    <!-- 如果是获取action中某个对象的属性值：<s:property value="user.username"/> -->
  </body>
</html>
