<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>
<!-- 各个高校得分 -->
<div id="view_score_container" style="display:none;"></div>
<textarea id="view_score" style="display:none;">
	<div class="p_box_body">
		<table id="list_score" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<thead id="list_head">
				<tr class="first">
					<td width="10%" style = "text-align:center;"><s:text name="序号" /></td>
					<td width="45%" style = "text-align:center;"><s:text name="高校名称" /></td>
					<td width="45%" style = "text-align:center;"><s:text name="总得分" /></td>
				</tr>
			</thead>
			<tbody>
				{for item in scoreList}
					<tr class="even">
						<td>${item_index}</td>
						<td>
							${item[0]}
						</td>
						<td>
							${item[1]}
						</td>
					</tr>
				{forelse}	
					<tr class="even">
						<td align="center" colspan="4"><s:text name="i18n_NoRecords" /></td>
					</tr>
				{/for}
			</tbody>
		</table>
	</div>
</textarea>
