<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户注册</title>
  </head>
  
  <body>
    <form action="register.do" method="post" >
    	用户名：<input type="text" name="username" /><br>
    	密    码：<input type="password" name="password" /><br>
    	提    交：<input type="submit" value="submit" />
		<input type="hidden" name="method" value="addUser" />
    </form>
  </body>
</html>
