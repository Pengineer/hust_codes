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
			<s:head/>
			<base href="<%=basePath%>" />
			<title><s:text name="i18n_ModifyRoleInfo" /></title>
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
								<s:text name="i18n_RoleList" />&nbsp;&gt;&gt;
								<s:text name="i18n_RoleInfo" />&nbsp;&gt;&gt;
								<span class="text_red"><s:text name="i18n_ModifyRoleInfo" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="modifyRole" id="form_roleright" namespace="/role" theme="simple">
						<div id="inputtip">
							<s:property value="#request.tip" />
							<s:fielderror />
							<s:actionerror />
						</div>
						<input type="hidden" name="roleid" value="${roleid}" />
						<table class="table_edit" cellspacing="0" cellpadding="0">
							<tr>
								<th class="thhead" colspan="2">
									<s:text name="i18n_BaseInfo" />
								</th>
							</tr>
							<tr>
								<td class="wd12">
									<s:text name="i18n_RoleName" />
								</td>
								<td class="wd13">
									<s:textfield name="role.name" value="%{role.name}" cssClass="input" size="40" />
								</td>
							</tr>
							<tr>
								<td class="wd12">
									<s:text name="i18n_RoleDescription" />
								</td>
								<td class="wd13">
									<s:textfield name="role.description" value="%{role.description}" cssClass="input" size="40" />
								</td>
							</tr>
							<tr>
								<th class="thhead" colspan="2">
									<s:text name="i18n_RightInfo" />
								</th>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<s:optiontransferselect name="norightsid"
										buttonCssClass="optbutton"
										leftTitle="%{getText('i18n_NotDistributedRight')}"
										rightTitle="%{getText('i18n_HasDistributedRight')}"
										allowSelectAll="false" allowUpDownOnLeft="false"
										allowUpDownOnRight="false" list="#session.norights" listKey="id"
										listValue="name" doubleList="#session.rights" doubleListKey="id"
										doubleListValue="name" doubleName="rightids"
										cssClass="optelement" doubleCssClass="optelement">
									</s:optiontransferselect>
								</td>
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