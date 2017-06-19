<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!-- 项目相关 -->
<div id="view_project_container" style="display:none;"></div>
<textarea id="view_project" style="display:none;">
	<div class="p_box_body">
		<table id="list_project" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_statistic">
			<thead id="list_head">
				<tr class="first">
					<td width="10%" style = "text-align:center;"><s:text name="序号" /></td>
					<td width="30%" style = "text-align:center;"><s:text name="高校名称" /></td>
					<td width="20%" style = "text-align:center;"><s:text name="申报数" /></td>
					<td width="20%" style = "text-align:center;"><s:text name="立项数" /></td>
					<td width="20%" style = "text-align:center;"><s:text name="立项率" /></td>
					<td width="20%" style = "text-align:center;"><s:text name="结项数" /></td>
					<td width="20%" style = "text-align:center;"><s:text name="结项率" /></td>
				</tr>
			</thead>
			<tbody>
				{for item in projectlaData}
					<tr class="even">
						<td>${item_index}</td>
						<td>
							${item[0]}
						</td>
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
						<td>
							${item[5]}
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
<div id="container2"></div>
