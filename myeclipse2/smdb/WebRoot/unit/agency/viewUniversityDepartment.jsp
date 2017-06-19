<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="department">
	<textarea id="view_template_department" style="display:none;">
		<div style="margin-bottom:5px;">
			相关院系
			<span id="rel_total">${deptNum}</span>
			<span style="margin-right:20px;">个</span>
		</div>
		
		<table id="list_department" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<thead id="list_head">
				<tr class="table_title_tr">
					<td width="30">序号</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td><s:text name="院系名称" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="60"><s:text name="院系代码" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="80"><s:text name="院系负责人" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100"><s:text name="院系电话" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100"><s:text name="院系传真" /></td>
				</tr>
			</thead>
			<tbody>
			{for item in deptList}
				<tr>
					<td class="">${item[7]}</td>
					<td></td>
					<td class=""><a id="${item[0] }" class="linkD" href="">${item[1]}</a></td>
					<td></td>
					<td class="">${item[2]}</td>
					<td></td>
					<td class=""><a id="${item[3] }" class="link5" href="">${item[4]}</a></td>
					<td></td>
					<td class="">${item[5]}</td>
					<td></td>
					<td class="">${item[6]}</td>
				</tr>
			{forelse}
				<tr>
					<td align="center">暂无符合条件的记录</td>
				</tr>
			{/for}
			</tbody>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr2">
				<td></td>
			</tr>
		</table>
	</textarea>
	<div id="view_container_department" style="display:none; clear:both;"></div>
</div>
