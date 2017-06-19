<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="account">

<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<table id="list_account" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<thead id="list_head">
				<tr class="table_title_tr">
					<td width="30">序号</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="200">账号所属</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td width="200">账号类型</td>
					<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
					<td>账号状态</td>
				</tr>
			</thead>
			<tbody>
			{for item in userList}
				<tr>
					<td class="index">${item_index}</td>
					<td></td>
				{if item[0] != 'ADMINISTRATOR'}
					{if item[1] == 1 && (item[0] == 'MINISTRY' || item[0] == 'PROVINCE' || item[0] == 'MINISTRY_UNIVERSITY' || item[0] == 'LOCAL_UNIVERSITY')}
					<td><a id="${item[6]}" class="linkA" href="" title="点击查看详细信息">${item[3]}</a></td>
					{elseif item[1] == 1 && item[0] == 'DEPARTMENT'}
					<td><a id="${item[8]}" class="linkA" href="" title="点击查看详细信息">${item[7]}</a>&nbsp;<a id="${item[6]}" class="linkD" href="" title="点击查看详细信息">${item[3]}</a></td>
					{elseif item[1] == 1 && item[0] == 'INSTITUTE'}
					<td><a id="${item[8]}" class="linkA" href="" title="点击查看详细信息">${item[7]}</a>&nbsp;<a id="${item[6]}" class="linkI" href="" title="点击查看详细信息">${item[3]}</a></td>
					{elseif item[1] == 1 && item[0] == 'EXPERT' }
					<td><a id="${item[6]}" class="linkP" type="4" href="" title="<s:text name='点击查看详细信息' />">${item[3]}</a></td>
					{elseif item[1] == 1 && (item[0] == 'TEACHER' || item[0] == 'STUDENT')}
					<td><a id="${item[6]}" class="linkP" type="5" href="" title="<s:text name='点击查看详细信息' />">${item[3]}</a></td>
					{elseif item[1] == 0 && (item[0] == 'MINISTRY' || item[0] == 'PROVINCE' || item[0] == 'MINISTRY_UNIVERSITY' || item[0] == 'LOCAL_UNIVERSITY')}
					<td><a type="1" id="${item[10]}" class="linkP" href="" title="<s:text name='点击查看详细信息' />">${item[3]}</a></td>
					{elseif item[1] == 0 && item[0] == 'DEPARTMENT'}
					<td>
					<a id="${item[8]}" class="linkA" href="" title="<s:text name='点击查看详细信息' />">${item[7]}</a>&nbsp;<a id="${item[10]}" type="2" class="linkP" href="" title="<s:text name='i18n_ViewDetails' />">${item[3]}</a>
					</td>
					{elseif item[1] == 0 && item[0] == 'INSTITUTE'}
					<td>
						<a id="${item[8]}" class="linkA" href="" title="<s:text name='点击查看详细信息' />">${item[7]}</a>&nbsp;<a id="${item[10]}" type="3" class="linkP" href="" title="<s:text name='i18n_ViewDetails' />">${item[3]}</a>
					</td>
					{/if}
					<td></td>
					<td>${item[4]}</td>
					<td></td>
					<td>
						{if item[5] == 0}停用
						{elseif item[5] == 1}启用
						{else}
						{/if}
					</td>
				{elseif item[0] == 'ADMINISTRATOR'}
					<td>${item[3]}</td>
					<td></td>
					<td>${item[4]}</td>
					<td></td>
					<td>
						{if item[5] == 0}停用
						{elseif item[5] == 1}启用
						{else}
						{/if}
					</td>
				{/if}
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