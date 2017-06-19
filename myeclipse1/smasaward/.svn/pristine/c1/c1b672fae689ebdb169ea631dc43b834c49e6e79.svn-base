<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>社科管理咨询服务中心</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="css/index.css" />
		<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
	</head>

	<body>
		<div class="login_header">
			<span>社科管理咨询服务系统</span>
		</div>
		<div class="login_center">
			<div class="login_center_pic"></div>
			<div class="login_center_form">
				<h3>登录</h3>
				<br />
				<s:form id="form_login" action="login" theme="simple" namespace="/user">
					<table class="login_table">
						<tr>
							<td><label>账号:</label></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="3"><s:textfield name="username" cssClass="input_login" tabIndex="1" /></td>
							<td></td>
						</tr>
						<tr>
							<td width="100px"><label>密码:</label></td>
							<td width="150px" colspan="2" class="linkText"><a href="user/toRetrievePassword.action"  target="blank" tabIndex="6">忘记登录密码？</a></td>
							<td></td>
						</tr>
						<tr>
							<td width="250px" colspan="3"><s:password name="password" cssClass="input_login2" tabIndex="2" /></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="4"><label>验证码:</label></td>
						</tr>
						<tr>
							<td style="width:95px;;" colspan="3">
								<s:textfield name="rand" cssClass="input_login1" tabIndex="3" />
								<img class="rand_img" width="80px" height="25px" src="main/rand.action" title="点击刷新" onclick="this.src='main/rand.action?time='+Math.random();" /></td>	
							<td></td>
							
						</tr>
						<tr class="errorInfo">
							<td colspan="4"><s:property value="#request.errorInfo" /><s:fielderror /></td>
						</tr>
						<tr>
							<td colspan="3">
								<input class="new_btn" type="submit" value="登  入" tabIndex="4" />
								<s:url id="toRegister" action="toRegister" namespace="/user" />
							</td>
						</tr>
						<tr>
							<td colspan="3" class="linkText">还没账号？马上<a href="user/toRegister.action" target="blank" tabIndex="5">注册</a>一个！</td>
						</tr>
					<%-- 	</tr>
							<td></td>
							<td colspan="2">
								<input class="new_btn" type="button" value="注册" onclick="document.location.href='<s:property value="toRegister" />'" />
							</td>
						<tr>
							<td></td>
							<td colspan="2">
								<s:url id="toFindPassword" action="toRetrievePassword" namespace="/user" />
								<input class="new_btn" type="button" value="找回密码" onclick="document.location.href='<s:property value="toFindPassword" />'" />
							</td>
						</tr> --%>
					</table>
				</s:form>
			</div>
		</div>
		<div class="login_footer">
			<div class="footer_r_txt">制作单位：中国高校社会科学数据中心
				<br/>Copyright&copy; 2009-2013 All Rights Reserved.&nbsp; 系统版本：1.0.<%=application.getAttribute("systemVersion")%>					
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/user/user_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>