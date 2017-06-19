<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_GrantedData" />
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/project/entrust/application/granted/search.jsp"/>
					</div>
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllProjectOrNot' />" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30"><s:text name="i18n_Number" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectName' />"><s:text name="i18n_ProjectName" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="60"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectNumber' />"><s:text name="i18n_ProjectNumber" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDirector' />"><s:text name="i18n_Director" /></a></td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUniversity' />"><s:text name="i18n_University" /></a></td>
								</s:if>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectSubtype' />"><s:text name="i18n_IssueType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDisciplineType' />"><s:text name="i18n_DisciplineType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectYear' />"><s:text name="i18n_ProjectYear" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectStatus' />"><s:text name="i18n_ProjectStatus" /></a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="<s:text name='i18n_ViewDetails' />" type="2">${item.laData[3]}</a></td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>
										{if item.laData[4]==""}${item.laData[5]}
										{else}<s:hidden id="directors" value="${item.laData[4]}" name="${item.laData[5]}" cssClass="directors" />
										{/if}
									</td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td></td>
									<td>
										{if item.laData[6]==""}${item.laData[7]}
										{else}<a id="${item.laData[6]}" class="view_university" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[7]}</a>
										{/if}
									</td>
								</s:if>
									<td></td>
									<td>${item.laData[8]}</td>
									<td></td>
									<td>${item.laData[9]}</td>
									<td></td>
									<td>${item.laData[10]}</td>
									<td></td>
									<td>
										{if item.laData[12] == 0}
										{elseif item.laData[12] == 1}<s:text name="i18n_InStudy" />
										{elseif item.laData[12] == 3}<s:text name="i18n_Suspend" />
										{elseif item.laData[12] == 2}<s:text name="i18n_Complete" />
										{elseif item.laData[12] == 4}<s:text name="i18n_Revoke" />
										{else}
										{/if}
									</td>
								</tr>
							{forelse}
								<tr>
									<td align="center"><s:text name="i18n_NoRecords" /></td>
								</tr>
							{/for}
							</tbody>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr2">
								<td width="4"></td>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_ADD">
									<td width="58"><input class="btn1" type="button" id="list_add" value="<s:text name='i18n_Add' />" />
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="<s:text name='i18n_Delete' />" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_DATA_EXPORT">
									<td width="58"><input id="list_export" type="button" class="btn1" value="导出" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_PUBLISH">
									<td width="58"><input id="list_publish" type="button" class="btn1" value="<s:text name='i18n_Publish' />" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_GRANTED_PUBLISH">
									<td width="70"><input id="list_notPublish" type="button" class="btn2" value="<s:text name='i18n_NotPublish' />" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/project/entrust/application/granted">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<div id="container" style="width:779px;height:300px;"></div>
			<script type="text/javascript">
				seajs.use('javascript/project/entrust/application/granted/list.js', function(list) {
					list.init();
				});
			</script>
		</body>
	</s:i18n>
</html>