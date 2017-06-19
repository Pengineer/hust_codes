<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Main">
		<head>
	    	<base href="<%=basePath%>" />
			<title><s:text name="i18n_SystemName" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/index.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div id="container">
				<div id="login">
					<s:form id="form_login" action="login" theme="simple" namespace="/user">
						<table class="login_table" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="3" class="wd6">
									<p style="font-size:13pt;margin:0;"><s:text name="i18n_SystemName" /></p>
								</td>
							</tr>
							<tr>
								<td class="wd0">
									<s:text name="i18n_Username" />:
								</td>
								<td class="wd1">
									<s:textfield name="username" cssClass="input_login" />
								</td>
								<td class="wd4"></td>
							</tr>
							<tr>
								<td class="wd0">
									<s:text name="i18n_Password" />:
								</td>
								<td class="wd1">
									<s:password name="password" cssClass="input_login" />
								</td>
								<td class="wd4"></td>
							</tr>
							<tr>
								<td class="wd0">
									<s:text name="i18n_AuthCode" />:
								</td>
								<td class="wd1">
									<s:textfield name="rand" cssClass="input_login1" />
									<img align="absmiddle" src="main/rand.action" title="<s:text name='i18n_ClickRefresh' />"
										onclick="this.src='main/rand.action?time='+Math.random();" />
								</td>
								<td class="wd4"></td>
							</tr>
						</table>
						<table class="login_table" style="margin-top:0;" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="3" style="text-align:center;color:red;">
								&nbsp;
									<s:property value="#request.tip" />
									<s:fielderror />
								</td>
							</tr>
							<tr>
								<td class="wd5">
									<input class="bnt1" type="submit" value="<s:text name='i18n_Login' />" />
								</td>
								<td class="wd5">
									<s:url id="toRegister" action="toRegister" namespace="/user" />
									<input class="bnt1" type="button" value="<s:text name="i18n_Register" />"
										onclick="document.location.href='<s:property value="toRegister" />'" />
								</td>
								<td class="wd5">
									<s:url id="toFindPassword" action="toRetrievePassword" namespace="/user" />
									<input class="bnt1" type="button" value="<s:text name="i18n_RetrievePassword" />"
										onclick="document.location.href='<s:property value="toFindPassword" />'" />
								</td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/user/userValidate.js"></script>
	</s:i18n>
</html>