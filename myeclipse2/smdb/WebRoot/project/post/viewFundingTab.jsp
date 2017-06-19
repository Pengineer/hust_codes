<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_fund_template" style="display:none;">
	<div class="p_box_body">
		<table id="project_found" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style="text-align:center;">
			<thead id="list_head">
				<tr class="table_title_tr3">
					<td width="30">序号</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100">拨款时间</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100">拨款金额（万元）</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100">经办人</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100"><s:text name="拨款类型" /></td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td>备注</td>
				</tr>
			</thead>
			<tbody>
			{for item in fundList}
				<tr>
					<td>${item[0]}</td>
					<td></td>
					<td>${item[2]}</td>
					<td></td>
					<td>${item[3]}</td>
					<td></td>
					<td>${item[4]}</td>
					<td></td>
					<td>
						{if item[6] == 0}
						{elseif item[6] == 1}<s:text name="立项拨款" />
						{elseif item[6] == 2}<s:text name="中检拨款" />
						{elseif item[6] == 3}<s:text name="结项拨款" />
						{else}
						{/if}
					</td>
					<td></td>
					<td>${item[5]}</td>
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
<div id="view_fund" style="display:none;"></div>