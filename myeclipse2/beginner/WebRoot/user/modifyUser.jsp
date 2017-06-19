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
			<title><s:text name="i18n_BasicInfo" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
			<link rel="stylesheet" type="text/css" href="css/user/user.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<s:if test="selflabel == 0">
									<s:text name="i18n_UserManagement" />&nbsp;&gt;&gt;
									<s:if test="userstatus == 1">
										<s:text name="i18n_ActiveUser" />&nbsp;&gt;&gt;
									</s:if>
									<s:elseif test="userstatus == -1">
										<s:text name="i18n_InactiveUser" />&nbsp;&gt;&gt;
									</s:elseif>
									<s:elseif test="userstatus == 0">
										<s:text name="i18n_UnapprovedUser" />&nbsp;&gt;&gt;
									</s:elseif>
								</s:if>
								<s:else>
									<s:text name="i18n_Selfspace" />&nbsp;&gt;&gt;
								</s:else>
								<span class="text_red"><s:text name="i18n_ModifyInfo" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="modifyUser" id="form_user_modifyUser" namespace="/user"
						enctype="multipart/form-data" theme="simple" method="post">
						<div id="inputtip">
							<s:property value="#request.tip" />
							<s:fielderror />
							<s:actionerror />
						</div>
						<input type="hidden" name="selflabel" value="${selflabel}" />
						<input type="hidden" name="userstatus" value="${userstatus}" />
						<table class="table_center">
							<tr>
								<td class="td_right">
									<span class="text_red">*</span>&nbsp;
									<s:text name="i18n_ChineseName" />
								</td>
								<td class="td_value">
									<div class="input0">
										<s:textfield name="user.chinesename"
											value="%{user.chinesename}" cssClass="input2" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;<s:text name="i18n_Birthday" />
								</td>
								<td class="td_value">
									<div class="input0">
										<s:if test="#session.locale.equals(\"zh_CN\")">
											<input type="text" name="user.birthday" class="input2"
												id="datepick"
												value="<s:date name="user.birthday" format='yyyy-MM-dd' />" />
										</s:if>
										<s:elseif test="#session.locale.equals(\"en_US\")">
											<input type="text" name="user.birthday" class="input2"
												id="datepick"
												value="<s:date name="user.birthday" format='MM/dd/yyyy' />" />
										</s:elseif>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;
									<s:text name="i18n_Gender" />
								</td>
								<td class="td_value">
									<div class="input0">
										<s:select name="user.gender.id" value="%{user.gender.id}" headerKey=""
											headerValue="请选择" list="#application.gender" listKey="id"  listValue="name" cssClass="select" />
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="td_right">
									&nbsp;<s:text name="i18n_Ethnic" />
								</td>
								<td class="td_value">
									<div class="input0">
										<s:select name="user.ethnic.id" value="%{user.ethnic.id}" headerKey="" headerValue="请选择"
											list="#application.ethnic" listKey="id" listValue="name" cssClass="select" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;<s:text name="i18n_MobilePhone" />
								</td>
								<td class="td_value">
									<div class="input0">
										<s:textfield name="user.mobilephone"
											value="%{user.mobilephone}" cssClass="input2" />
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
										<s:textfield name="user.email" value="%{user.email}" cssClass="input2" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;<s:text name="i18n_PicUpload" />
								</td>
								<td class="td_value">
									<div class="input0">
										<s:file name="pic" cssClass="input2" />
									</div>
								</td>
							</tr>
						</table>
						<table class="table_sub">
							<tr>
								<td>&nbsp;</td>
								<td class="td_sub"><input type="submit" class ="input" value = "<s:text name="i18n_Ok" />" /></td>
								<td class="td_sub"><input type="button" class="input" value="<s:text name="i18n_Cancel" />" onclick="history.back();" /></td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.js"></script>
		<s:if test="#session.locale.equals(\"zh_CN\")">
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_CN.js"></script>
		</s:if>
		<script type="text/javascript" src="javascript/jquery/datepick.mine.js"></script>
		<script type="text/javascript" src="javascript/user/userValidate.js"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
		<script type="text/javascript" src="javascript/user/userValidate_en.js"></script>
		</s:if>
	</s:i18n>
</html>
