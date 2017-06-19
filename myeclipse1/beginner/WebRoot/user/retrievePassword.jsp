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
			<title><s:text name="i18n_RetrievePassword" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
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
					<div class="retrievepassword">
						<strong><s:text name="i18n_ToRetrievePassword" /></strong>
					</div>
					<div id="inputtip">
						<s:property value="#request.tip" />
						<s:fielderror />
						<s:actionerror />
					</div>
					<div class="center">
						<s:form action="retrievePassword" id="form_user_retrievePassword" namespace="/user"
							theme="simple">
							<table class="table_center">
								<tr>
									<td class="td_right">
										<span class="text_red">*</span>&nbsp;
										<s:text name="i18n_Account" />
									</td>
									<td class="td_value">
										<div class="input0">
											<s:textfield name="username" cssClass="input2" />
										</div>
									</td>
								</tr>
							</table>
							<br/>
							<table class="table_center">
								<tr>
									<td class="th_button th_center">
										<input type="submit" value="<s:text name="i18n_Ok" />" class="btn" />
									</td>
									<td class="th_button th_center">
										<div class="input0">
											<input type="button" value="<s:text name="i18n_Cancel" />"
												onclick="history.go(-1);" class="btn" />
										</div>
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
		<script type="text/javascript" src="javascript/user/userValidate.js"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/user/userValidate_en.js"></script>
		</s:if>
	</s:i18n>
</html>