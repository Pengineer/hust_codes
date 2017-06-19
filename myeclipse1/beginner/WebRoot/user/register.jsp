<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_User">
		<head>
			<base href="<%=basePath%>" />
			<title><s:text name="i18n_Register" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
			<link rel="stylesheet" type="text/css" href="css/user/register.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div class="container">
				<div class="head">
					<div class="logo">
						
					</div>
					<div class="menu">
						<a href="main/toIndex.action"><s:text name="i18n_Index" /></a>
					</div>
				</div>
				<div class="main">
					<div class="steplogo"></div>
					<div id="inputtip">
						<s:property value="#request.tip" />
						<s:fielderror />
						<s:actionerror />
					</div>
					<div class="center">
						<s:form action="register" id="form_user" namespace="/user"
							enctype="multipart/form-data" theme="simple" method="post">
							<table class="table_center">
								<tr>
									<td class="td_right">
										<span class="text_red">*</span>&nbsp;
										<s:text name="i18n_Account" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:textfield name="user.username" id="username" cssClass="input2" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										<span class="text_red">*</span>&nbsp;
										<s:text name="i18n_Password" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:password name="user.password" id="password" cssClass="input2" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										<span class="text_red">*</span>&nbsp;
										<s:text name="i18n_RePassword" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:password name="repassword" id="repassword" cssClass="input2" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										<span class="text_red">*</span>&nbsp;
										<s:text name="i18n_ChineseName" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:textfield name="user.chinesename" cssClass="input2" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										&nbsp;<s:text name="i18n_Birthday" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:textfield name="user.birthday" id="birthday" cssClass="input2" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										&nbsp;
										<s:text name="i18n_Gender" />
									</td>
									<!-- 必选:headerKey="-1", 选填:headerKey="" by zhouzj -->
									<td class="td_value">
										<div class="input0">
											<s:select name="user.gender.id" headerKey="" headerValue="请选择"
												list="#application.gender" listKey="id" listValue="name" cssClass="select" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										&nbsp;<s:text name="i18n_Ethnic" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:select name="user.ethnic.id" headerKey="" headerValue="请选择" cssClass="select"
												list="#application.ethnic" listKey="id" listValue="name" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										&nbsp;<s:text name="i18n_MobilePhone" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:textfield name="user.mobilephone" cssClass="input2" />
										</div>
									</td>
								</tr>
								<tr>
									<td class="td_right">
										<span class="text_red">*</span>&nbsp;
										<s:text name="i18n_Email" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:textfield name="user.email" cssClass="input2" />
										</div>
									</td>
									<td class="td_tip"><!-- 显示后台验证信息 -->
										<s:property value="mailtip" />
									</td>
								</tr>
								<tr>
									<td class="td_right">
										&nbsp;<s:text name="i18n_PicUpload" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:file name="pic" cssClass="input1" />
										</div>
									</td>
								</tr>
							</table>
							<table class="table_center">
								<tr>
									<td class="th_button th_center">
										<input type="submit" value="<s:text name="i18n_Complete" />" class="btn" />
									</td>
									<td class="th_button th_center">
										<input type="reset" value="<s:text name="i18n_Reset" />" class="btn" />
									</td>
								</tr>
							</table>
						</s:form>
					</div>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.js"></script>
		<script type="text/javascript" src="javascript/user/userValidate.js"></script>
		<s:if test="#session.locale.equals(\"zh_CN\")">
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_CN.js"></script>
		</s:if>
		<script type="text/javascript" src="javascript/jquery/datepick.mine.js"></script>
	</s:i18n>
</html>