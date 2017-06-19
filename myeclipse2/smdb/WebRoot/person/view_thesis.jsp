<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="thesis">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="150">学位论文题目：</td>
					<td class="value" width="350">${student.thesisTitle}</td>
					<td class="key" width="150">论文经费：</td>
					<td class="value">${student.thesisFee}万</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">是否优秀学位论文：</td>
					<td class="value">{if student.isExcellent == 1}是{else}否{/if}</td>
					<td class="key">优秀学位论文等级：</td>
					<td class="value">${student.excellentGrade}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">优秀学位论文评选年度：</td>
					<td class="value">${student.excellentYear}</td>
					<td class="key">优秀学位论文评选届次：</td>
					<td class="value">${student.excellentSession}</td>
				</tr>
			</table>
		</div>
	</div>
	</textarea>
</div>
