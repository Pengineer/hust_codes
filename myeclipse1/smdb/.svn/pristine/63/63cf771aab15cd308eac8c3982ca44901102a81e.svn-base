<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>
<!-- 奖励相关 -->
<div id="view_award_container" style="display:none;"></div>
<textarea id="view_award" style="display:none;">
	<div class="p_box_body">
		<table id="list_award" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<thead id="list_head">
				<tr class="first">
					<td width="30%" style = "text-align:center;">奖励名称</td>
					<td width="30%" style = "text-align:center;">奖励类型</td>
					<td width="20%" style = "text-align:center;">获奖届次</td>
					<td style = "text-align:center;">获奖状态</td>
				</tr>
			</thead>
			<tbody>
			{if awardList != null}
				{for item in awardList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>
							${item[2]}
						</td>
						<td>
							${item[3]}
						</td>
						<td>
							${item[4]}
						</td>
					</tr>
				{/for}
			{else}
				<tr class="even">
					<td align="center" colspan="4">暂无符合条件的记录</td>
				</tr>
			{/if}
			</tbody>
		</table>
	</div>
</textarea>
