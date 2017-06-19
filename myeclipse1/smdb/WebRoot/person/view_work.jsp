<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="work">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<table id="list_work" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<thead>
				<tr class="table_title_tr3">
			        <td>时间</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="120">单位</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="170">部门</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="100">职务</td>
				</tr>
			</thead>
			<tbody>
			{for item in person_work}
				<tr>
					<td>${item.startDate}至${item.endDate}</td>
					<td></td>
					<td>${item.unit}</td>
					<td></td>
					<td>${item.department}</td>
					<td></td>
					<td>${item.position}</td>
				</tr>
			{forelse}
				<tr>
					<td align="center">暂无符合条件的记录</td>
				</tr>
			{/for}
			</tbody>
		</table>
	</div>
	</textarea>
</div>