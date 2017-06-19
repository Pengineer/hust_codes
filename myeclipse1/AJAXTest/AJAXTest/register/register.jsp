<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>注册</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<script type="text/javascript" src="register.js"></script>
  </head>
  
  <body>
    <center>
    <form action="" enctype="application/x-www-form-urlencoded">
    	<h3>请填写用户注册信息</h3>
    	<table border=1>
    		<tr>
    			<td>用户名：</td>
    			<td>
    				<input type="text" name="username" value="" id="username"/>
    				<div id="divcheck"></div>
    				<input type="button" name="checkusername" value="查看用户名" id="checkusername"/>
    			</td>
    		</tr>
    		<tr>
    			<td>密码：</td>
    			<td>
    				<input type="password" name="psw" value="" />
    			</td>
    		</tr>
    		<tr>
    			<td>确认密码：</td>
    			<td>
    				<input type="password" name="confpsw" value="" />
    			</td>
    		</tr>
    		<tr>
    			<td>出生日期：</td>
    			<td>
    				<input type="text" name="birthday" value="" />
    			</td>
    		</tr>
    	</table>
    </form>
    </center>
  </body>
</html>
