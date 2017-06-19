<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="table_main_tr_adv">
	<s:include value="/fundList/searchForUnitFundList.jsp"/>
</div>
<textarea id="view_unit_fund_template" style="display:none;">
<%--	<div class="p_box_body">--%>
		<table id="unit_fund" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style="text-align:center;">
			<thead id="list_head">
				<tr class="table_title_tr3">
					<td width="30"><s:text name="i18n_Number" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="90"><s:text name="单位名称" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="60"><s:text name="开户名" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="60"><s:text name="账号" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="90"><s:text name="开户行" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="70"><s:text name="所在省" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="70"><s:text name="所在地" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="70"><s:text name="是否部署高校" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="70"><s:text name="拨款金额(万元)" /></td>
				</tr>
			</thead>
			<tbody>
			{for item in unitFundList}
				<tr>
					<td>${item[0]}</td>
					<td></td>
					<td>${item[1]}</td>
					<td></td>
					<td>${item[2]}</td>
					<td></td>
					<td>${item[3]}</td>
					<td></td>
					<td>${item[4]}</td>
					<td></td>
					<td>${item[5]}</td>
					<td></td>
					<td>${item[6]}</td>
					<td></td>
					<td>
					{if item[7]==3}是
					{else}否
					{/if}
					</td>
					<td></td>
					<td>${item[10]}</td>
				</tr>
			{forelse}
				<tr>
					<td align="center"><s:text name="i18n_NoRecords" /></td>
				</tr>
			{/for}
			</tbody>
		</table>
<%--	</div>	--%>
</textarea>
<div id="view_unit_fund" style="display:none;"></div>