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
			<title><s:text name="i18n_RoleAssignment" /></title>
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
								<span class="text_red">
									<s:text name="i18n_Give" />
									<s:property value="username" />
									<s:text name="i18n_AssignRole" />
								</span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="userRole" namespace="/role" theme="simple">
						<input type="hidden" name="userid" value="${userid}" />
						<input type="hidden" name="username" value="${username}" />
						<input type="hidden" name="userstatus" value="${userstatus}" />
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="2" >
									<s:optiontransferselect  name="norolesid"
										buttonCssClass="optbutton"
										leftTitle="%{getText('i18n_NotDistributeRole')}"
										rightTitle="%{getText('i18n_HasDistributedRole')}"
										allowSelectAll="false" allowUpDownOnLeft="false"
										allowUpDownOnRight="false" list="noroles" listKey="id"
										listValue="name" doubleList="roles" doubleListKey="id"
										doubleListValue="name" doubleName="roleids"
										cssClass="optelement" doubleCssClass="optelement">
									</s:optiontransferselect>
								</td>
							</tr>
							<tr>
								<td class="wd_center">
									<input type="submit" value="<s:text name='i18n_Ok' />" class="btn" />
								</td>
								<td class="wd_center">
									<input type="button" value="<s:text name='i18n_Cancel' />"
										onclick="history.go(-1)" class="btn" />
								</td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
		</body>
	</s:i18n>
</html>