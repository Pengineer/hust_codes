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
			<title><s:text name="i18n_ActiveUser" /></title>
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
								<s:text name="i18n_UserManagement" />&nbsp;&gt;&gt;
								<span class="text_red"><s:text name="i18n_ActiveUser" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid">
					<table class="table_bar">
						<tr>
							<td>
								<s:text name="i18n_TotalNumber" /><s:property value="#session.userPage.totalRows" /><s:text name="i18n_Tiao" />
							</td>
							<td class="wd_right">
								<s:form theme="simple" id="form_user" action="simpleSearch" onsubmit='return valid("form_user_keyword")' namespace="/user">
									<s:select name="search_type" cssClass="select" headerKey="0" headerValue="--请选择--"
										list="#{'1':getText('i18n_Account'),'2':getText('i18n_ChineseName')}" />
									<s:textfield name="keyword" cssClass="input wd2" />
									<input type="hidden" name="userstatus" value="1" />
									<input type="submit" value="<s:text name='i18n_Search' />" class="btn" />
								</s:form>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="bottom">
					<table class="table_list" cellspacing="0" cellpadding="0">
						<tr class="tr_list1">
							<td class="wd0 border0">
								<input type="checkbox" name="check" title="<s:text name='i18n_SelectAllOrNot' />"
									onclick="checkAll(this.checked, 'userids')" value="false" />
							</td>
							<td class="wd1 border0">
								<s:text name="i18n_Number" />
							</td>
							<td class="wd11 border0">
								<a href="user/sortUser.action?columnLabel=0&userstatus=1"
									title="<s:text name='i18n_SortByAccount' />"><s:text name="i18n_Account" /></a>
								<s:if test="#session.userPage.hql.contains(\"user.username desc\")">↓</s:if>
								<s:elseif test="#session.userPage.hql.contains(\"user.username asc\")">↑</s:elseif>
							</td>
							<td class="wd11 border0">
								<a href="user/sortUser.action?columnLabel=1&userstatus=1" 
									title="<s:text name='i18n_SortByChineseName' />"><s:text name="i18n_ChineseName" /></a>
								<s:if test="#session.userPage.hql.contains(\"user.chinesename desc\")">↓</s:if>
								<s:elseif test="#session.userPage.hql.contains(\"user.chinesename asc\")">↑</s:elseif>
							</td>
							<td class="wd11 border0">
								<a href="user/sortUser.action?columnLabel=2&userstatus=1"
									title="<s:text name='i18n_SortByRegisterTime' />"><s:text name="i18n_RegisterTime" /></a>
								<s:if test="#session.userPage.hql.contains(\"user.registertime desc\")">↓</s:if>
								<s:elseif test="#session.userPage.hql.contains(\"user.registertime asc\")">↑</s:elseif>
							</td>
							<td class="wd11 border0">
								<a href="user/sortUser.action?columnLabel=3&userstatus=1"
									title="<s:text name='i18n_SortByApproveTime' />"><s:text name="i18n_ApproveTime" /></a>
								<s:if test="#session.userPage.hql.contains(\"user.approvetime desc\")">↓</s:if>
								<s:elseif test="#session.userPage.hql.contains(\"user.approvetime asc\")">↑</s:elseif>
							</td>
							<td class="wd11 border0">
								<a href="user/sortUser.action?columnLabel=4&userstatus=1"
									title="<s:text name='i18n_SortByExpireTime' />"><s:text name="i18n_ExpireTime" /></a>
								<s:if test="#session.userPage.hql.contains(\"user.expiretime desc\")">↓</s:if>
								<s:elseif test="#session.userPage.hql.contains(\"user.expiretime asc\")">↑</s:elseif>
							</td>
							<td class="wd_auto border1">
								<s:text name="i18n_RoleAssignment" />
							</td>
						</tr>
						
						<s:form name="selectedUsers" action="groupDeleteUser" theme="simple" namespace="/user">
							<input type="hidden" name="userstatus" value="1" />
							<s:iterator value="pageList" status="stat">
								<tr class="tr_list2">
									<td class="wd0">
										<s:checkbox name="userids"
											fieldValue="%{pageList[#stat.index][0]}" value="false"
											theme="simple" />
									</td>
									<td class="wd1">
										<s:property value="#stat.index+#session.userPage.startRow+1" />
									</td>
									<td class="wd11">
										<a href='user/viewUser.action?userid=<s:property value="pageList[#stat.index][0]" />&userstatus=1'
											title="<s:text name='i18n_ViewDetails' />">
											<s:property value="pageList[#stat.index][1]" />
										</a>
									</td>
									<td class="wd11">
										<a href='user/viewUser.action?userid=<s:property value="pageList[#stat.index][0]" />&userstatus=1'
											>
											<s:property value="pageList[#stat.index][2]" />
										</a>
									</td>
									<td class="wd11">
										<s:date name="pageList[#stat.index][3]" format="yyyy-MM-dd" />
									</td>
									<td class="wd11">
										<s:date name="pageList[#stat.index][4]" format="yyyy-MM-dd" />
									</td>
									<td class="wd11">
										<s:date name="pageList[#stat.index][5]" format="yyyy-MM-dd" />
									</td>
									<td class="wd_auto">
										<a href="role/viewUserRole.action?userid=<s:property value='pageList[#stat.index][0]' />&
											username=<s:property value='pageList[#stat.index][1]' />&userstatus=1" 
											title="<s:text name='i18n_RoleAssignment' />">
											<s:if test="roles.get(pageList[#stat.index][0]) != ''">
												<s:property value="roles.get(pageList[#stat.index][0])" />
											</s:if>
											<s:else>[<s:text name="i18n_AssignmentState" />]</s:else></a>
									</td>
								</tr>
							</s:iterator>
						</s:form>
						
						<tr class="tr_list1">
							<td class="border1" colspan="4">
								<input type="button" value="<s:text name="i18n_Delete" />"
									onclick="delSelected('userids', 'selectedUsers');" class="btn" />
								<input type="button" value="<s:text name="i18n_Inactive" />"
									onclick="aprovSelected('userids', 'selectedUsers', -1);" class="btn" />
							</td>
							<td class="wd_right border1" colspan="4">
								<s:if test="#session.userPage.currentPage == #session.userPage.firstPage">
									<img title="<s:text name='i18n_FirstPage' />" src="image/firstPage.gif" />&nbsp;
									<img title="<s:text name='i18n_PrevPage' />" src="image/prevPage.gif" />&nbsp;
								</s:if>
								<s:else>
									<a href="user/listUser.action?pageNumber=<s:property value='#session.userPage.firstPage' />&userstatus=1">
										<img title="<s:text name='i18n_FirstPage' />" src="image/firstPage.gif" /></a>&nbsp;
									<a href="user/listUser.action?pageNumber=<s:property value='#session.userPage.previousPage' />&userstatus=1">
										<img title="<s:text name='i18n_PrevPage' />" src="image/prevPage.gif" /></a>&nbsp;
								</s:else>
								<s:select theme="simple" cssClass="select" value="%{#session.userPage.currentPage}" list="#session.userPage.getPagelist()"
									onchange="document.location.href='user/listUser.action?userstatus=1&pageNumber='+this.value" />&nbsp;
								<s:if test="#session.userPage.currentPage == #session.userPage.lastPage">
									<img title="<s:text name='i18n_NextPage' />" src="image/nextPage.gif" />&nbsp;
									<img title="<s:text name='i18n_LastPage' />" src="image/lastPage.gif" />&nbsp;
								</s:if>
								<s:else>
									<a href="user/listUser.action?pageNumber=<s:property value='#session.userPage.nextPage' />&userstatus=1">
										<img title="<s:text name='i18n_NextPage' />" src="image/nextPage.gif" /></a>&nbsp;
									<a href="user/listUser.action?pageNumber=<s:property value='#session.userPage.lastPage' />&userstatus=1">
										<img title="<s:text name='i18n_LastPage' />" src="image/lastPage.gif" /></a>&nbsp;
								</s:else>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/common.js"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/common_en.js"></script>
		</s:if>
		<script type="text/javascript" src="javascript/user/user.js"></script>
	</s:i18n>
</html>