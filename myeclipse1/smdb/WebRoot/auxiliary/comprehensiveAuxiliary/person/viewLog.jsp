<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>
<!-- 访问记录相关 -->
<div id="view_visit_container" style="display:none;"></div>
<textarea id="view_visit" style="display:none;">
	<div class="p_box_body">
		<table id="list_visit" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<thead id="list_head">
				<tr class="first">
					<td width="30%" style = "text-align:center;">操作地点</td>
					<td width="40%" style = "text-align:center;">事件描述</td>
					<td style = "text-align:center;">操作次数</td>
				</tr>
			</thead>
			<tbody>
			{if visitList != null}
				{for item in visitList}
					<tr class="even">
						<td>
							${item[1]}
						</td>
						<td>${item[0]}</td>
						<td>${item[2]}</td>
					</tr>
				{/for}
			{else}
				<tr class="even">
					<td align="center" colspan="3">暂无符合条件的记录</td>
				</tr>
			{/if}
			</tbody>
		</table>
	</div>
</textarea>
<div id="container" style="height:300px;display:none;"></div>

