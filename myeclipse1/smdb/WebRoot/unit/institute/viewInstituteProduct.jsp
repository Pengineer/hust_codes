<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="product">
	<textarea id="view_template_product" style="display:none;">
		<div class="p_box_body">
			<table id="list_product" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
				<tr class="table_title_tr">
					<td width="30">序号</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td>成果名称</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="60">成果形式</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="55">第一作者</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100">所属单位</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="100">学科门类</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="90">提交时间</td>
				</tr>
			</thead>
			<tbody>
			{for item in institute_product}
				<tr>
					<td class="index">${item_index}</td>
					<td></td>
					<td class="table_txt_td">${item[1]}</td>
					<td></td>
					<td>
						{if item[2] == 'paper'}论文
						{elseif item[2] == 'book'}著作
						{elseif item[2] == 'consultation'}研究咨询报告
						{/if}
					</td>
					<td></td>
					<td><a id="${item[4]}" class="link2" href="" title="点击查看详细信息" >${item[3]}</a></td>
					<td></td>
					<td><a id="${item[6]}" name= "${item[6]}" class="linkA" href="" title="点击查看详细信息" >${item[5]}</a></td>
					<td></td>
					<td>${item[7]}</td>
					<td></td>
					<td>${item[8]}</td>
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
					<td width="4"></td>
				</tr>
			</table>
		</div>
	</textarea>
	<div id="view_container_product" style="display:none; clear:both;"></div>
</div>