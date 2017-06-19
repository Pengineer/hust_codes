<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_DM">
		<head>
			<title><s:text name="i18n_UniversityVariation" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_DataManagement" />&nbsp;&gt;&nbsp;<s:text name="i18n_UniversityVariation" />
			</div>
			<div class="main">
				<div class="main_content">					
					<s:form id="search" theme="simple" action="list" namespace="/dm/universityVariation">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr">
								<td align="right"><span class="choose_bar">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" value="-1"
										list="#{
											'0':getText('i18n_NameOld'),
											'1':getText('i18n_CodeOld'),
											'2':getText('i18n_NameNew'),
											'3':getText('i18n_CodeNew'),
											'4':getText('i18n_UrType')
										}"
									/>
								</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
								</td>
								<td width="60"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
							</tr>
						</table>
					</s:form>
					
					<textarea id="list_template" style="display:none;">
						<s:hidden id="universityVariationid" name="universityVariationid" />
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="5%"><input id="check" name="check" type="checkbox" /></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="5%"><s:text name="i18n_Number" /></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="20%"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByNameOld' />"><s:text name="i18n_NameOld" /></a></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="12%"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByCodeOld' />"><s:text name="i18n_CodeOld" /></a></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="20%"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByNameNew' />"><s:text name="i18n_NameNew" /></a></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="8%"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByCodeNew' />"><s:text name="i18n_CodeNew" /></a></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="8%"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUrType' />"><s:text name="i18n_UrType" /></a></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="8%"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='按照变更时间排序' />"><s:text name="变更时间" /></a></td>
									<td width="1%"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDescription' />"><s:text name="i18n_Description" /></a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[1]}</a></td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>${item.laData[3]}</td>
									<td></td>
									<td>${item.laData[4]}</td>
									<td></td>
									<td>{if item.laData[5] == 0}
									{elseif item.laData[5] == 1}<s:text name="i18n_Rename" />
									{elseif item.laData[5] == 2}<s:text name="i18n_Merge" />
									{/if}
									</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[6]}</td>
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
								<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_ADD">
									<td width="58" align="center"><input id="list_add" class="btn1" type="button" value="<s:text name='i18n_Add' />" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="<s:text name='i18n_Delete' />" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/dm/universityVariation">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="checkedIds" name="checkedIds" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/dm/universityVariation/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>