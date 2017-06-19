<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_related_template" style="display:none;">
	{if memberFlag == 1}
	<div class="p_box_t">
		<div class="p_box_t_t">负责人基本信息</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		{for dirPerson in dirPersons}
			{if dirPerson != null && dirPerson.id != null && dirPerson.id !=""}
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead class="head_title1">
						<tr>
							<td colspan="6"><span>首席专家</span></td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr7">
							<td class="key" width="80">姓名：</td>
							<td class="value" width="150"><a id="${dirPerson.id}" class="linkDirectors" href="">${dirPerson.name}</a></td>
							<td class="key" width="80">性别：</td>
							<td class="value" width="150">${dirPerson.gender}</td>
							<td class="key" width="80">出生年月：</td>
							<td class="value">${dirPerson.birthday}</td>
						</tr>
						<tr class="table_tr7">
							<td class="key">最后学位：</td>
							<td class="value">{if dirAcademics[dirPerson_index] != null}${dirAcademics[dirPerson_index].lastDegree}{/if}</td>
							<td class="key">最后学历：</td>
							<td class="value" colspan="3">{if dirAcademics[dirPerson_index] != null}${dirAcademics[dirPerson_index].lastEducation}{/if}</td>
						</tr>
						<tr class="table_tr7">
							<td class="key">联系电话：</td>
							<td class="value">${dirPerson.officePhone}</td>
							<td class="key">电子邮箱：</td>
							<td class="value" colspan="3">${dirPerson.email}</td>
						</tr>
					</tbody>
				</table>
			{else}
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr>
						<td align="center">暂无符合条件的记录</td>
					</tr>
				</table>
			{/if}
		{/for}
	</div>
	<div class="p_box_t">
		<div class="p_box_t_t">项目主要成员信息</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<table id="list_key_member" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style="text-align:center;">
			<thead>
				<tr class="table_title_tr2">
					<td width="50">姓名</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>职称</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>专业</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>所在单位</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>所在部门</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>每年工作时间（月）</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>分工情况</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>是否首席专家</td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td>是否子项目负责人</td>
				</tr>
			</thead>
			<tbody>
			{for item in memberList}
					<tr>
						<td>
						{if item[2]!="" && item[2] != null}
							<a id="${item[2]}" class="linkDirectors" href="">${item[3]}</a>
						{else}
							${item[3]}
						{/if}
						</td>
						<td></td>
						<td>
							{if item[4]!= -1}
								${item[4]}
							{/if}
						</td>
						<td></td>
						<td>${item[5]}</td>
						<td></td>
						<td>
							{if item[6]!= "" && item[6] != null}
								<a id="${item[6]}" class="linkUni" href="" title="点击查看详细信息">${item[7]}</a>
							{else}
								${item[7]}
							{/if}	
						</td>
						<td></td>
						<td>
							{if item[8]!= "" && item[8] != null}
								<a id="${item[8]}" class="linkDep" href="" title="点击查看详细信息">${item[10]}</a>
							{elseif item[9] != "" && item[9] != null}
								<a id="${item[9]}" class="linkIns" href="" title="点击查看详细信息">${item[10]}</a>
							{else}
								${item[10]}
							{/if}
						</td>
						<td></td>
						<td>${item[11]}</td>
						<td></td>
						<td>${item[12]}</td>
						<td></td>
						<td>{if item[1] == 1}是{else}否{/if}</td>
						<td></td>
						<td>{if item[13] == 1}是{else}否{/if}</td>
					</tr>
			{forelse}
				<tr>
					<td align="center">暂无符合条件的记录</td>
				</tr>
			{/for}
			</tbody>
		</table>
	</div>
	{else}
	<div class="p_box_body">
		<div style="text-align:center;">暂无符合条件的记录</div>
	</div>
	{/if}
</textarea>
<div id="view_related" style="display:none;"></div>
