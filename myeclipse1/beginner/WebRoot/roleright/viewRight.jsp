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
			<title><s:text name="i18n_RightInfo" /></title>
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
								<s:text name="i18n_RightList" />&nbsp;&gt;&gt;
								<span class="text_red"><s:text name="i18n_RightInfo" /></span>
							</td>
							<td class="wd_right">
								<a href="right/loadRight.action?rightid=${right.id}">
									<img title="<s:text name='i18n_Modify' />" src="image/modifyEntry.gif" /></a>&nbsp;
								<a href="right/deleteRight.action?rightid=${right.id}" 
									onclick="return confirm('您确定要删除此记录吗？');">
									<img title="<s:text name="i18n_Delete" />" src="image/deleteEntry.gif" /></a>&nbsp;
								<a href="right/prevRight.action?rightid=${right.id}">
									<img title="<s:text name='i18n_PrevRecord' />" src="image/prevRecord.gif" /></a>&nbsp;
								<a href="right/nextRight.action?rightid=${right.id}">
									<img title="<s:text name='i18n_NextRecord' />" src="image/nextRecord.gif" /></a>&nbsp;
								<a href="right/listRight.action">
									<img title="<s:text name='i18n_Return' />" src="image/return.gif" /></a>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
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
								<s:property value="right.name" />
							</td>
						</tr>
						<tr>
							<td class="wd12">
								<s:text name="i18n_RightDescription" />
							</td>
							<td class="wd13">
								<s:property value="right.description" />
							</td>
						</tr>
						<tr>
							<th class="thhead" colspan="2">
								<s:text name="i18n_RightURLMap" />
							</th>
						</tr>
					</table>
					<table class="table_view">
						<tr>
							<th>
								<s:text name="i18n_ActionName" />
							</th>
							<th>
								<s:text name="i18n_ActionDescription" />
							</th>
						</tr>
						<s:iterator value="pageList">
							<tr>
								<td>
									<s:property value="actionurl" />
								</td>
								<td>
									<s:property value="description" />
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>
		</body>
	</s:i18n>
</html>