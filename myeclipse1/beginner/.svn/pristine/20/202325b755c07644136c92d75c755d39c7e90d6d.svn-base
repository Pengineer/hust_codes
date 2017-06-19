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
			<title><s:text name="i18n_RoleRightManage" /></title>
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
								<span class="text_red"><s:text name="i18n_RoleList" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid">
					<table class="table_bar">
						<tr>
							<td>
								<s:text name="i18n_TotalNumber" /><s:property value="#session.rolePage.totalRows" /><s:text name="i18n_Tiao" />
							</td>
							<td class="wd_right">
								<s:form theme="simple" id="form_roleright" action="simpleSearch" onsubmit="return valid('form_roleright_keyword')" namespace="/role">
									<s:select name="search_type" cssClass="select" headerKey="0" headerValue="--请选择--"
										list="#{'1':getText('i18n_RoleName'),'2':getText('i18n_RoleDescription')}" />
									<s:textfield name="keyword" cssClass="input wd2" />
									<input type="submit" value="<s:text name='i18n_Search' />"
										class="btn" />
								</s:form>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="bottom">
					<table class="table_list" cellspacing="0" cellpadding="0">
						<tr class="tr_list1">
							<td class="wd0 border0">
								<input type="checkbox" name="check" title="<s:text name='i18n_SelectAllRolesOrNot' />"
									onclick="checkAll(this.checked, 'roleids')" value="false" />
							</td>
							<td class="wd1 border0">
								<s:text name="i18n_Number" />
							</td>
							<td class="wd11 border0">
								<a href="role/sortRole.action?columnLabel=0" 
									title="<s:text name='i18n_SortByRoleName' />"><s:text name="i18n_RoleName" /></a>
								<s:if test="#session.rolePage.hql.contains(\"role.name desc\")">↓</s:if>
								<s:elseif test="#session.rolePage.hql.contains(\"role.name asc\")">↑</s:elseif>
							</td>
							<td class="wd_auto border1">
								<a href="role/sortRole.action?columnLabel=1" 
									title="<s:text name='i18n_SortByRoleDesc' />"><s:text name="i18n_RoleDescription" /></a>
								<s:if test="#session.rolePage.hql.contains(\"role.description desc\")">↓</s:if>
								<s:elseif test="#session.rolePage.hql.contains(\"role.description asc\")">↑</s:elseif>
							</td>
						</tr>
						
						<s:form name="selectedRoles" action="groupDeleteRoles" theme="simple" namespace="/role">
							<s:iterator value="pageList" status="stat">
								<tr class="tr_list2">
									<td class="wd0">
										<s:checkbox name="roleids" theme="simple"
											fieldValue="%{pageList[#stat.index][0]}" value="false" />
									</td>
									<td class="wd1">
										<s:property value="#stat.index+#session.rolePage.startRow+1" />
									</td>
									<td class="wd11">
										<a href="role/viewRole.action?roleid=<s:property value='pageList[#stat.index][0]' />"
											title="<s:text name='i18n_ViewDetails' />"><s:property value="pageList[#stat.index][1]" /></a>
									</td>
									<td class="wd_auto">
										<s:property value="pageList[#stat.index][2]" />
									</td>
								</tr>
							</s:iterator>
						</s:form>
						
						<tr class="tr_list1">
							<td class="border1" colspan="3">
								<s:url id="add" action="toAddRole" namespace="/role" />
								<input type="button" value="<s:text name='i18n_Add' />"
									onclick="document.location.href='<s:property value="add" />'" class="btn" />
								<input type="button" value="<s:text name='i18n_Delete' />"
									onclick="delSelected('roleids', 'selectedRoles');" class="btn" />
							</td>
							<td class="wd_right border1">
								<s:if test="#session.rolePage.currentPage == #session.rolePage.firstPage">
									<img title="<s:text name='i18n_FirstPage' />" src="image/firstPage.gif" />&nbsp;
									<img title="<s:text name='i18n_PrevPage' />" src="image/prevPage.gif" />&nbsp;
								</s:if>
								<s:else>
									<a href="role/listRole.action?pageNumber=<s:property value='#session.rolePage.firstPage' />">
										<img title="<s:text name='i18n_FirstPage' />" src="image/firstPage.gif" /></a>&nbsp;
									<a href="role/listRole.action?pageNumber=<s:property value='#session.rolePage.previousPage' />">
										<img title="<s:text name='i18n_PrevPage' />" src="image/prevPage.gif" /></a>&nbsp;
								</s:else>
								<s:select theme="simple" cssClass="select" value="%{#session.rolePage.currentPage}" list="#session.rolePage.getPagelist()"
									onchange="document.location.href='role/listRole.action?pageNumber='+this.value" />&nbsp;
								<s:if test="#session.rolePage.currentPage == #session.rolePage.lastPage">
									<img title="<s:text name='i18n_NextPage' />" src="image/nextPage.gif" />&nbsp;
									<img title="<s:text name='i18n_LastPage' />" src="image/lastPage.gif" />&nbsp;
								</s:if>
								<s:else>
									<a href="role/listRole.action?pageNumber=<s:property value='#session.rolePage.nextPage' />">
										<img title="<s:text name='i18n_NextPage' />" src="image/nextPage.gif" /></a>&nbsp;
									<a href="role/listRole.action?pageNumber=<s:property value='#session.rolePage.lastPage' />">
										<img title="<s:text name='i18n_LastPage' />" src="image/lastPage.gif" /></a>&nbsp;
								</s:else>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/common.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.js"></script>
		<script type="text/javascript" src="javascript/roleright/roleright.js"></script>
		<script type="text/javascript" src="javascript/roleright/rolerightValidation.js"></script>
		<s:if test="#session.locale.equals(\"zh_CN\")">
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_CN.js"></script>
		</s:if>
		<s:elseif test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/common_en.js"></script>
			<script type="text/javascript" src="javascript/roleright/rolerightValidation_en.js"></script>
		</s:elseif>
	</s:i18n>
</html>