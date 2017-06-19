<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="student">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="80">所在高校：</td>
					<td class="value" width="180"><a id="${agency.id}" class="linkA" href="">${agency.name}</a></td>
					<td class="key" width="80">所在{if department != null}院系{else}研究基地{/if}：</td>
					<td class="value" colspan="3">
						{if department != null}
							<a id="${department.id}" class="linkD" href="">${department.name}</a>
						{else}
							<a id="${institute.id}" class="linkI" href="">${institute.name}</a>
						{/if}
					</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">学生类别：</td>
					<td class="value">${student.type}</td>
					<td class="key" width="80">学生状态：</td>
					<td class="value" width="180">${student.status}</td>
					<td class="key" width="80">学生证号：</td>
					<td class="value" >${student.studentCardNumber}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">入学时间：</td>
					<td class="value">${student.startDate}</td>
					<td class="key">毕业时间：</td>
					<td class="value" colspan="3">${student.endDate}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">导师：</td>
					<td class="value" colspan="5">{if student.tutor != null}<a id="${student.tutor.id}" class="linkP" href="">${student.tutor.name}</a>{/if}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">参与项目：</td>
					<td class="value" colspan="5">${student.project}</td>
				</tr>
			</table>
		</div>
	</div>
	</textarea>
</div>