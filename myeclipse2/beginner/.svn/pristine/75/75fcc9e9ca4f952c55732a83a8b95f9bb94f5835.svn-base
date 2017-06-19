<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_RoleRight">
		<head>
			<base href="<%=basePath%>" />
			<title><s:text name="i18n_AddRight" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/roleright/roleright.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<s:text name="i18n_RoleRightManage" />&nbsp;&gt;&gt;
								<span class="text_red"><s:text name="i18n_AddRight" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="addRight" id="form_roleright" namespace="/right" theme="simple">
						<div id="inputtip">
							<s:property value="#request.tip" />
							<s:fielderror />
							<s:actionerror />
						</div>
						<table class="table_edit" cellspacing="0" cellpadding="0">
							<tr>
								<th class="thhead" colspan="2">
									<s:text name="i18n_BaseInfo" />
								</th>
							</tr>
							<tr>
								<td class="wd12">
									<s:text name="i18n_RightName" />
								</td>
								<td class="wd13">
									<s:textfield name="right.name" cssClass="input"  size="40" />
								</td>
							</tr>
							<tr>
								<td class="wd12">
									<s:text name="i18n_RightDescription" />
								</td>
								<td class="wd13">
									<s:textfield name="right.description" cssClass="input"  size="40" />
								</td>
							</tr>
							<tr>
								<th class="thhead" colspan="2">
									<s:text name="i18n_RightURLMap" />
								</th>
							</tr>
						</table>
						<table id="groupaction" class="table_add" cellspacing="0" cellpadding="0">
							<tr>
								<th class="wd14">
									<s:text name="i18n_Number" />
								</th>
								<th class="wd15">
									<s:text name="i18n_ActionName" />
								</th>
								<th class="wd16">
									<s:text name="i18n_ActionDescription" />
								</th>
								<th class="wd14" onclick="addaction();">
									<img src="image/addItem.gif" />
								</th>
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
		<script type="text/javascript" src="javascript/common.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/roleright/roleright.js"></script>
		<script type="text/javascript" src="javascript/roleright/rolerightValidate.js"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/common_en.js"></script>
			<script type="text/javascript" src="javascript/roleright/rolerightValidate_en.js"></script>
		</s:if>
	</s:i18n>
</html>