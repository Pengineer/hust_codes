<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<div id="affiliation">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="100">所在单位：</td>
					<td class="value" width="300">${expert.agencyName}</td>
					<td class="key" width="100">所在部门：</td>
					<td class="value">${expert.divisionName}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">人员类型：</td>
					<td class="value">${expert.type}</td>
					<td class="key">职务：</td>
					<td class="value">${expert.position}</td>
				</tr>
			</table>
		</div>
	</div>
	</textarea>
</div>
