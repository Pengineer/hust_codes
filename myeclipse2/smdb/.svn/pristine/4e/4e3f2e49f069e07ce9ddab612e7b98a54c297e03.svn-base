<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="common">
	<textarea id="view_template_common" style="display:none;">
		<div class="title_bar">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">单位名称：</td>
					<td class="title_bar_td" id="unitName">${agency.name}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="90" align="right">上级管理机构：</td>
					<td class="title_bar_td" ><a name="${subjectionId}" id="subjectionId" href="" class="linkA">${subjectionName}</a></td>
				</tr>
				<tr>
					<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">通行证：</td>
					<td class="title_bar_td" id="accountDisp">{if (account != null && passport != null)}${passport.name}</td>
					<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">单位主账号：</td>
					<td class="title_bar_td"><a href="" class ="view_account" id ="${account.id}">${passport.name}</a></td>
					{else}<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_ADD">[<a href="" id="view_addAccount" title="点击创建通行证">创建</a>]</sec:authorize></td>
					{/if}
				</tr>				
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="80" align="right">单位代码：</td>
					<td class="title_bar_td" width="280">${agency.code}</td>
				</tr>
			</table>
		</div>
	</textarea>
	<div id="view_container_common" style="display:none; clear:both;"></div>
</div>