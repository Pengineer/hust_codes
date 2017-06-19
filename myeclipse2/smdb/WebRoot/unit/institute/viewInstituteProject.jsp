<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="project">
	<textarea id="view_template_project" style="display:none;">
		<div class="p_box_body">
			<table id="list_project" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td>项目名称</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="50">批准号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="50">负责人</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="90">依托高校</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="55">项目类型</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="55">学科门类</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="55">项目年度</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="55">项目状态</td>
					</tr>
				</thead>
				<tbody>
				{for item in institute_project}
					<tr>
						<td class="index">${item_index}</td>
						<td></td>
						<td class="table_txt_td">${item[3]}</td>
						<td></td>
						<td>${item[2]}</td>
						<td></td>
						<td>
							{if item[13]==null || item[13]==""}${item[14]}
							{else}<a id="${item[13]}" class="link2" href="" title="点击查看详细信息">${item[14]}</a>
							{/if}
						</td>
						<td></td>
						<td>
							{if item[6]==null || item[6]==""}${item[7]}
							{else}<a id="${item[6]}" name="${item[6]}" class="linkA" href="" title="点击查看详细信息">${item[7]}</a>
							{/if}
						</td>
						<td></td>
						<td>${item[8]}</td>
						<td></td>
						<td>${item[9]}</td>
						<td></td>
						<td>${item[10]}</td>
						<td></td>
						<td>
							{if item[12] == 0}
							{elseif null == item[12] || item[12] == ""}未立项
							{elseif item[12] == 1}在研
							{elseif item[12] == 3}已中止
							{elseif item[12] == 2}已结项
							{elseif item[12] == 4}已撤项
							{else}
							{/if}
						</td>
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
	<div id="view_container_project" style="display:none; clear:both;"></div>
</div>