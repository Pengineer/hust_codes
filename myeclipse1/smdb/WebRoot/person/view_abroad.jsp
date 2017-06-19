<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="abroad">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<table id="list_abroad" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<thead>
				<tr class="table_title_tr3">
			        <td width="140">时间</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="100">国家或地区</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="160">机构</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td>目的</td>
				</tr>
			</thead>
			<tbody>
			{for item in person_abroad}
				<tr>
					<td>${item.startDate}至${item.endDate}</td>
					<td></td>
					<td>${item.countryRegion}</td>
					<td></td>
					<td>${item.workUnit}</td>
					<td></td>
					<td>${item.purpose}</td>
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