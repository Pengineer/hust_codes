<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Product">
		<head>
			<title><s:text name="i18n_Select" /></title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/pop/select/select_publication.js', function(select) {
					$(function(){
						select.init();
					})
				});
			</script>
		</head>

		<body>
			<div style="width:430px;">
				<s:form id="search" theme="simple" action="list" namespace="/selectPublication">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr">
							<td align="right"><span class="choose_bar">
								<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
									list="#{'1':getText('i18n_PublicationName'),'2':getText('i18n_PublicationDescription')}" />
							</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
								<s:hidden id="list_pagenumber" name="pageNumber" />
								<s:hidden id="list_sortcolumn" name="sortColumn" />
								<s:hidden id="label" name="label" />
								<s:hidden name="publicationLevelId"/>
							</td>
							<td width="60"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
						</tr>
					</table>
				</s:form>
				
				<textarea id="list_template" style="display:none;">
					<table id="list_table" width="411" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr3">
								<td width="30"><s:text name="i18n_Select" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
								<td width="30"><s:text name="i18n_Number" /></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
								<td ><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDepartmentName' />"><s:text name="i18n_PublicationName" /></a></td>
								<td width="2"><img src="image/table_line.gif" width="2" height="21" /></td>
								<td ><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDepartmentCode' />"><s:text name="i18n_PublicationDescription" /></a></td>
							</tr>
						</thead>
						<tbody>
						{for item in root}
							<tr>
								<td><input type="radio" name="entityId" value="${item.laData[0]}" alt="${item.laData[1]}" class="radio_select" /></td>
								<td></td>
								<td>${item.num}</td>
								<td></td>
								<td>${item.laData[1]}</td>
								<td></td>
								<td>${item.laData[2]}</td>
							</tr>
						{forelse}
							<tr>
								<td align="center"><s:text name="i18n_NoRecords" /></td>
							</tr>
						{/for}
						</tbody>
					</table>
					<table width="411" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr3"><td></td></tr>
					</table>
				</textarea>
				<s:include value="/pop/select/radioBottom.jsp" />
			</div>
		</body>
	</s:i18n>
</html>