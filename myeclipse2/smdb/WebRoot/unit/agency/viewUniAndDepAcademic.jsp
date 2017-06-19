<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="academic">
	<textarea id="view_template_academic" style="display:none;">
		<div class="p_box_t">
			<div class="p_box_t_t">博士点信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<table id="list_doctorial" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td><s:text name="名称" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="40"><s:text name="代码" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80"><s:text name="建立时间" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="120"><s:text name="学科代码" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="60"><s:text name="是否重点" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="120"><s:text name="简介" /></td>
					</tr>
				</thead>
				<tbody>
				{for item in doctorial}
					<tr>
						<td class="index">${item_index}</td>
						<td></td>
						<td class="table_txt_td">${item[1]}</td>
						<td></td>
						<td class="table_txt_td">${item[2]}</td>
						<td></td>
						<td class="table_txt_td">${item[3]}</td>
						<td></td>
						<td class="table_txt_td">${item[4]}</td>
						<td></td>
						<td class="table_txt_td">{if item[5] == 0}否{else}是{/if}</td>
						<td></td>
						<td class="table_txt_td">${item[6]}</td>
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
		</div>
		<div class="p_box_t">
			<div class="p_box_t_t">重点学科信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<table id="list_discipline" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td><s:text name="名称" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="40"><s:text name="代码" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="80"><s:text name="建立时间" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="120"><s:text name="学科代码" /></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="160"><s:text name="简介" /></td>
					</tr>
				</thead>
				<tbody>
				{for item in discipline}
					<tr>
						<td class="index">${item_index}</td>
						<td></td>
						<td class="table_txt_td">${item[1]}</td>
						<td></td>
						<td class="table_txt_td">${item[2]}</td>
						<td></td>
						<td class="table_txt_td">${item[3]}</td>
						<td></td>
						<td class="table_txt_td">${item[4]}</td>
						<td></td>
						<td class="table_txt_td">${item[5]}</td>
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
		</div>
	</textarea>
	<div id="view_container_academic" style="display:none; clear:both;"></div>
</div>