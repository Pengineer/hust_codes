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
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/main/page.css" />
  		</head>
  
		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<span class="text_red"><s:text name="i18n_SysConfig" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="configPageSize" id="form_sysconfig" namespace="/main" theme="simple">
						<div id="inputtip">
							<s:property value="tip" />
							<s:fielderror />
							<s:actionerror />
						</div>
						<table class="table_edit" cellspacing="0" cellpadding="0">
							<tr>
								<td class="wd12">
									<s:text name="i18n_PageSize" />
								</td>
								<td class="wd13">
									<s:textfield name="DisplayNumberEachPage" value="%{#application.DisplayNumberEachPage}" cssClass="input"  size="40" />
								</td>
								<td class=""></td>
							</tr>
						</table>
						<table class="table_sub">
							<tr>
								<td>&nbsp;</td>
								<td class="td_sub">
									<input type="submit" class ="input" value = "<s:text name="i18n_Ok" />" />
								</td>
								<td class="td_sub">
									<input type="button" class="input" value="<s:text name="i18n_Cancel" />" onclick="history.back();" />
								</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/sysconfig/sysconfigValidate.js"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/sysconfig/sysconfigValidate_en.js"></script>
		</s:if>
	</s:i18n>
</html>