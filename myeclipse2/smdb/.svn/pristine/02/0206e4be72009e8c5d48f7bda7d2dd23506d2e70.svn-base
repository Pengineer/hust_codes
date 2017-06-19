<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="funding">
	<textarea id="view_template_funding" style="display:none;">
		<div class="p_box_body">
			<table id="list_funding" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="60">拨款时间</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="55">研究项目费</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="42">资料费</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="42">会议费</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="42">期刊费</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="42">网络费</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="42">数据库费</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="42">奖励费</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="55">合计经费（万）</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="42">经办人</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td>备注</td>
					</tr>
				</thead>
				<tbody>
				{for item in funding}
					<tr>
						<td class="index">${item_index}</td>
						<td></td>
						<td class=""><span class="date">${item[9]}</span></td>
						<td></td>
						<td class="">${item[1]}</td>
						<td></td>
						<td class="">${item[2]}</td>
						<td></td>
						<td class="">${item[3]}</td>
						<td></td>
						<td class="">${item[4]}</td>
						<td></td>
						<td class="">${item[5]}</td>
						<td></td>
						<td class="">${item[6]}</td>
						<td></td>
						<td class="">${item[7]}</td>
						<td></td>
						<td class="">${item[8]}</td>
						<td></td>
						<td class="">${item[10]}</td>
						<td></td>
						<td class="">${item[11]}</td>
					</tr>
				{forelse}
					<tr>
						<td align="center" colspan="19">暂无符合条件的记录</td>
					</tr>
				{/for}
				</tbody>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr class="table_main_tr2">
					<td width="4"></td>
				</tr>
			</table>
        </div>
	</textarea>
	<div id="view_container_funding" style="display:none; clear:both;"></div>
</div>
