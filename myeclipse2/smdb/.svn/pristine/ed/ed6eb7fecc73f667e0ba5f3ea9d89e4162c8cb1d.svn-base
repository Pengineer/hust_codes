<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<div style=" color: #555555; z-index:100; position:absolute; background:white; display:none; margin-left: 200px;margin-top: 280px;" id="j_view"></div>
<textarea id="view_log_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="90">登录次数：</td>
					<td class="value">${account.loginCount}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">上次登录时间：</td>
					<td class="value">${account.lastLoginDate}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">上次登录IP：</td>
					<td class="value">${passport.lastLoginIp}</td>
				</tr>
			</table>
		</div>
	</div>
</textarea>
<div id="view_log"></div>

<textarea id="list_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<thead id="list_head">
					<tr class="table_title_tr3">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="200"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按操作时间排序">操作时间</a></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td width="100"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按操作时间排序">操作地点</a></td>
						<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
						<td><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="点击按事件描述排序">事件描述</a></td>
					</tr>
				</thead>
				<tbody>
				{for item in root}
					<tr>
						<td>${item.num}</td>
						<td></td>
						<td><a id="${item.laData[0]}" class="link2" href="" title="点击查看详细信息">${item.laData[1]}</a></td>
						<td></td>
						<td class="j_viewDetail" id="${item.laData[2]}">${item.laData[2]}</td>
						<td></td>
						<td>${item.laData[3]}</td>
					</tr>
				{forelse}
					<tr>
						<td align="center">暂无符合条件的记录</td>
					</tr>
				{/for}
				</tbody>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr class="table_main_tr3">
					<td></td>
				</tr>
			</table>
		</div>
	</div>
</textarea>
<s:form id="search" theme="simple" action="list" namespace="/logEmbed">
	<s:hidden id="list_pagenumber" name="pageNumber" value="1" />
	<s:hidden id="list_sortcolumn" name="sortColumn" />
	<s:hidden id="list_pagesize" name="pageSize" value="10" />
	<s:hidden id="entityId" name="entityId" />
	<s:hidden id="accountId" name="accountId" />
	<s:hidden id="update" name="update" value="1" />
	<div id="list_container" style="display:none;"></div>
</s:form>
