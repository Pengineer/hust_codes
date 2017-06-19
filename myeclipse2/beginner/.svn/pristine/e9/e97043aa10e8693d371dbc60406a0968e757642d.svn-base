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
			<title><s:text name="i18n_SelfInfo" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/user/user.css" />
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
									<span class="text_red"><s:text name="i18n_SelfInfo" /></span>
								</s:if>
								<s:else>
									<s:text name="i18n_Selfspace" />&nbsp;&gt;&gt;
									<span class="text_red"><s:text name="i18n_SelfInfo" /></span>
								</s:else>
							</td>
							<td class="wd_right">
								<s:if test="selflabel == 0">
									<a href="user/loadUser.action?userid=${user.id}&userstatus=${userstatus}">
									<img title="<s:text name='i18n_Modify' />" src="image/modifyEntry.gif" /></a>&nbsp;
 								<a href="user/deleteUser.action?userid=${user.id}&userstatus=${userstatus}" onclick='return confirm("您确定要删除此记录吗？");'>
									<img title="<s:text name='i18n_Delete'/>" src="image/deleteEntry.gif" /></a>&nbsp;
								<a href="user/prevUser.action?userid=${user.id}&userstatus=${userstatus}">
									<img title="<s:text name='i18n_PrevRecord' />" src="image/prevRecord.gif" /></a>&nbsp;
								<a href="user/nextUser.action?userid=${user.id}&userstatus=${userstatus}">
									<img title="<s:text name='i18n_NextRecord' />" src="image/nextRecord.gif" /></a>&nbsp;
								<a href="user/listUser.action?userstatus=${userstatus}">
									<img title="<s:text name='i18n_Return' />" src="image/return.gif" /></a>&nbsp;
								</s:if>
								<s:else>
									<a href="selfspace/loadInfo.action">
										<img title="<s:text name="i18n_Modify" />" src="image/modifyEntry.gif" /></a>&nbsp;
								</s:else>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<table class="table_edit" cellspacing="0" cellpadding="0">
						<tr>
							<th class="thhead" colspan="3">
								<s:text name="i18n_BasicInfo" />
							</th>
						</tr>
						<tr>
							<td class="wd14">
								<s:text name="i18n_Account" />
							</td>
							<td class="wd15">
								<s:property value="user.username" />
							</td>
							<td rowspan="7" class="showpic">
								<s:if test="user.photofile != null">
									<img src="${applicationScope.UserPictureUploadPath}/<s:property value='user.photofile'/>" />
								</s:if>
								<s:else>
									<img src="image/photo.gif" />
								</s:else>
							</td>
						</tr>
						<tr>
							<td class="wd14">
								<s:text name="i18n_ChineseName" />
							</td>
							<td class="wd15">
								<s:property value="user.chinesename" />
							</td>
						</tr>
						<tr>
							<td class="wd14">
								<s:text name="i18n_Gender" />
							</td>
							<td class="wd15">
								<s:property value="user.gender.name" />
							</td>
						</tr>
						<tr>
							<td class="wd14">
								<s:text name="i18n_Birthday" />
							</td>
							<td class="wd15">
								<s:if test="#session.locale == 'zh_CN'">
									<s:date name="user.birthday" format="yyyy-MM-dd" />
								</s:if>
								<s:elseif test="#session.locale == 'en_US'">
									<s:date name="user.birthday" format="dd/MM/yyyy" />
								</s:elseif>
							</td>
						</tr>
						<tr>
							<td class="wd14">
								<s:text name="i18n_Ethnic" />
							</td>
							<td class="wd15">
								<s:property value="user.ethnic.name" />
							</td>
						</tr>
						<tr>
							<td class="wd14 bgcolor1">
								<s:text name="i18n_Email" />
							</td>
							<td class="wd15">
								<s:property value="user.email" />
							</td>
						</tr>
						<tr>
							<td class="wd14 bgcolor1">
								<s:text name="i18n_MobilePhone" />
							</td>
							<td class="wd15">
								<s:property value="user.mobilephone" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</body>
	</s:i18n>
</html>