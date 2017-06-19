<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>
<!-- 访问记录相关 -->
<div id="view_person_container" style="display:none;"></div>
<textarea id="view_person" style="display:none;">
	<div class="p_box_body">
		<table id="list_person" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<thead id="list_head">
				<tr class="first">
					<td width="4%" style = "text-align:center;">序号</td>
					<td width="8%" style = "text-align:center;">学校名称</td>
					<td width="10%" style = "text-align:center;">社科人员总数</td>
					<td style = "text-align:center;">社科人员详情(职称：人数)</td>
				</tr>
			</thead>
			<tbody>
				{for item in personlaData}
					<tr class="even">
						<td>
							${item_index}
						</td>
						<td>
							${item[0][0]}
						</td>
						<td>
							${item[0][1]}
						</td>
						<td style="text-align: left;">
							${item[0][2]}
						</td>
					</tr>
				{forelse}		
					<tr class="even">
						<td align="center" colspan="3">暂无符合条件的记录</td>
					</tr>
				{/for}
			</tbody>
		</table>
	</div>
</textarea>
<div id="container1"></div>

