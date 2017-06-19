<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<img id="show_img" alt="钻取内容" src="image/close.gif" style="display: none;"/>
<div style="text-align: center;font-size:14px;font-weight:bold;color:#6B2553;" id="drill_head"></div>
<table width="100%" cellspacing="0" cellpadding="2">
	<tr class="table_title_tr" id="head"></tr>
</table>
<textarea id="list_template" style="display:none;">
	<table class="table_statistic drill_table" width="100%" cellspacing="0" cellpadding="2">
		<tbody>
		{for item in root}
			<tr>
				<td width="30">${item.num}</td>
				{for data in item.laData}
					<td>${data}</td>
				{/for}
			</tr>
		{forelse}
			<tr>
				<td align="center">暂无符合条件的记录</td>
			</tr>
		{/for}
		</tbody>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
		<tr class="table_main_tr2">
			<td width="4"></td>
		</tr>
	</table>
</textarea>
<s:form id="search" theme="simple" action="list" namespace="/statistic">
	<s:hidden id="list_pagenumber" name="pageNumber" value="1" />
	<s:hidden id="list_sortcolumn" name="sortColumn" />
	<s:hidden id="list_pagesize" name="pageSize" value="10" />
	<s:hidden id="update" name="update" value="1" />
	<div id="list_container" style="display:none; clear:both;"></div>
</s:form>