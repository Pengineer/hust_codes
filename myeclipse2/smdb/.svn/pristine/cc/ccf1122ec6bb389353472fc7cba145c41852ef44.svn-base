<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
	<tr class="table_main_tr">
		<td align="right">
			<span class="choose_bar">
				<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="0" headerValue="--%{getText('不限')}--"
					list="#{'1':getText('单位代码'),'2':getText('单位名称'),'3':getText('单位负责人')}" />
			</span><s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
			<s:hidden id="list_pagenumber" name="pageNumber" />
			<s:hidden id="list_sortcolumn" name="sortColumn" />
			<s:hidden id="list_pagesize" name="pageSize" />
		</td>
		<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
		<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2"/></td>
	</tr>
</table>