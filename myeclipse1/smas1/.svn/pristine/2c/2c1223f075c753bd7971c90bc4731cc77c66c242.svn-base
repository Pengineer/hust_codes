<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>SMAS</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/main/page.css" />
	</head>
	<style>
		.login-table{margin:20px;}
		.login-table tr {height:30px;}
		.login-table tr td {padding:5px 10px;}
	</style>
  
  <body>
    <div id="top">
		系统管理&nbsp;&gt;&gt;&nbsp;系统配置&nbsp;&gt;&gt;&nbsp;<span class="text_red">同步数据</span>
	</div>
	
	<div id="dataManage-login" style="width: 300px;margin:20px auto;">
		<s:form action="dataManageLogin" namespace="/main" theme="simple" method="post">
			<s:include value="/validateError.jsp" />
			<table class="login-table" cellspacing="0" cellpadding="0">
				<tr>
					<td width="150" style="text-align:right;">用户名：</td>
					<td width="150"><input type="text" name="userName" /></td>
				</tr>
				<tr>
					<td width="150" style="text-align:right;">密&nbsp;码：</td>
					<td width="150"><input type="text" name="password" /></td>
				</tr>
			</table>
			<div class="btn_div">
				<input type="submit" class="btn" value="确定" />&nbsp;&nbsp;
				<input type="button" class="btn" value="取消" onclick="history.back();" />
			</div>
		</s:form>
	</div>
  </body>
</html>
