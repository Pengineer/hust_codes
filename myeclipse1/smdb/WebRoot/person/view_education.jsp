<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="education">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<table id="list_edu" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<thead>
				<tr class="table_title_tr3">
			        <td>时间</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="50">学历</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="50">学位</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="100">毕业国家或地区</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="90">毕业高校</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="90">毕业高校</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="80">专业</td>
				</tr>
			</thead>
			<tbody>
			{for item in person_education}
				<tr>
					<td>${item.startDate}至${item.endDate}</td>
					<td></td>
					<td>${item.education}</td>
					<td></td>
					<td>${item.degree}</td>
					<td></td>
					<td>${item.countryRegion}</td>
					<td></td>
					<td>${item.university}</td>
					<td></td>
					<td>${item.department}</td>
					<td></td>
					<td>${item.major}</td>
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