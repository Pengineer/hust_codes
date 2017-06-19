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
			<title><s:text name="i18n_ModifyPassword" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/selfspace/selfspace.css" />
			<link rel="stylesheet" type="text/css" href="css/selfspace/validate.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<s:text name="i18n_Selfspace" />&nbsp;&gt;&gt;
								<span class="text_red"><s:text name="i18n_ModifyPassword" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="modifyPassword" id="form_selfspace" namespace="/selfspace" theme="simple">
						<div>
							<s:property value="#request.tip" />
							<s:fielderror />
							<s:actionerror />
						</div>
						<table class="table_edit" cellspacing="0" cellpadding="0">
							<tr>
								<td class="tdpassname">
									<s:text name="i18n_OldPassword" />
								</td>
								<td class="tdpassvalue">
									<s:password name="opassword" cssClass="input" />
								</td>
							</tr>
							<tr>
								<td class="tdpassname">
									<s:text name="i18n_NewPassword" />
								</td>
								<td class="tdpassvalue">
									<s:password name="password" cssClass="input" />
								</td>
							</tr>
							<tr>
								<td class="tdpassname">
									<s:text name="i18n_RePassword" />
								</td>
								<td class="tdpassvalue">
									<s:password name="repassword" cssClass="input" />
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
		<script type="text/javascript" src="javascript/selfspace/selfspaceValidate.js"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/selfspace/selfspaceValidate_en.js"></script>
		</s:if>
	</s:i18n>  
</html>