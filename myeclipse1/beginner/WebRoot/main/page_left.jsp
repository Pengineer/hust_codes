<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Main">
		<head>
		  	<base href="<%=basePath%>" />
		    <title><s:text name="i18n_SystemName" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/main/page_left.css" />
		</head>
  
		<body>
			<div id="container">
				<div class="div_nav1"></div>
				
				<s:if test="(#session.visitor.userRight.contains(\"用户管理\")) || (#session.visitor.userRight.contains(\"角色管理\")) || (#session.visitor.user.issuperuser == 1)">
					<div class="firstmenu" onclick="showmenu('systemmanage');">
						<img src="image/menu/sysmanage.gif" />
						<s:text name="i18n_SystemManage" />
					</div>
				</s:if>
				<s:if test="(#session.visitor.userRight.contains(\"用户管理\")) || (#session.visitor.user.issuperuser == 1)">
					<div id="usermanage" onclick="showmenu('usermanage');" style="display:none;">
						<img src="image/menu/usermanage.gif" />
						<s:text name="i18n_UserManagement" />
					</div>
					<div id="user" style="display:none;">
						<ul>
							<li style="margin-bottom:8px;">
								<img src="image/menu/activeuser.gif" />
								<a href="user/listUser.action?listLabel=1&userstatus=1" target="right"><s:text name="i18n_ActiveUser" /></a>
							</li>
							<li style="margin-bottom:8px;">
								<img src="image/menu/inactiveuser.gif" />
								<a href="user/listUser.action?listLabel=1&userstatus=-1" target="right"><s:text name="i18n_InactiveUser" /></a>
							</li>
							<li style="margin-bottom:8px;">
								<img src="image/menu/unapproveduser.gif" />
								<a href="user/listUser.action?listLabel=1&userstatus=0" target="right"><s:text name="i18n_UnapprovedUser" /></a>
							</li>
						</ul>
					</div>
				</s:if>
				
				<s:if test="(#session.visitor.userRight.contains(\"角色管理\")) || (#session.visitor.user.issuperuser == 1)">
					<div id="rolerightmanage" onclick="showmenu('rolerightmanage');" style="display:none;">
						<img src="image/menu/rolemanage.gif" />
						<s:text name="i18n_RoleRightManage" />
					</div>
					<div id="roleright" style="display:none;">
						<ul>
							<li style="margin-bottom:8px;">
								<img src="image/menu/listroles.gif" />
								<a href="role/listRole.action?listLabel=1" target="right"><s:text name="i18n_RoleList" /></a>
							</li>
							<s:if test="(#session.visitor.user.issuperuser == 1)">
								<li style="margin-bottom:8px;">
									<img src="image/menu/listright.gif" />
									<a href="right/listRight.action?listLabel=1" target="right"><s:text name="i18n_RightList" /></a>
								</li>
							</s:if>
						</ul>
					</div>
				</s:if>
				
				<s:if test="(#session.visitor.userRight.contains(\"基本权限\")) || (#session.visitor.user.issuperuser == 1)">
					<div class="firstmenu" onclick="showmenu('self');">
						<img src="image/menu/spacemanage.gif" />
						<s:text name="i18n_Selfspace" />
					</div>
					<div id="self" style="display:none">
						<ul>
							<li style="margin-bottom:8px;">
								<img src="image/menu/selfinfor.gif" />
								<a href="selfspace/viewInfo.action" target="right"><s:text name="i18n_SelfInfo" /></a>
							</li>
							<li style="margin-bottom:8px;">
								<img src="image/menu/modifypsw.gif" />
								<a href="selfspace/toModifyPassword.action" target="right"><s:text name="i18n_ModifyPassword" /></a>
							</li>
						</ul>
					</div>
				</s:if>
				
				
			</div>
		</body>
		<script type="text/javascript" src="javascript/main/page_left.js"></script>
	</s:i18n>
</html>