<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<div id="simple_search">
	<s:form id="search4unit" theme="simple">
		<s:hidden id="entityId" name="entityId" value="%{entityId}" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:select cssClass="select" name="searchType4unit" id="search_type_4unit" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
					list="#{'1':getText('单位名称'),'2':getText('所在省'),'3':getText('是否部署高校')}" />
				</span><s:textfield id="keyword4unit" name="keyword4unit" cssClass="keyword" size="10" />
				</td>
				<td width="60"><input id="button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
			</tr>
		</table>
	</s:form>
</div>