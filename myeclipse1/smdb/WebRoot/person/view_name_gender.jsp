<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div id="name_gender" class="title_bar">
	<textarea class="view_template" style="display:none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="54" align="right" >姓名：</td>
				<td class="title_bar_td" width="100" id="personName" >
					${person.name}
				</td>
				<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="54" align="right">性别：</td>
				<td class="title_bar_td">${person.gender}</td>
				<sec:authorize ifAnyGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_ADD">
					<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="54" align="right">用户名：</td>
					<td class="title_bar_td" width="130" id="accountDisp">
						{if accountId == null || accountId == ""}
							<span>[<a id="view_addAccount" href="" title="点击分配账号" >分配账号</a>]</span>
						{else}
							<span><a id="${accountId }" class="linkAcc" href="" title="点击查看详细信息" >${accountName}</a></span>
						{/if}
					</td>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_SYSTEM_KEY,ROLE_SYSTEM_KEY_VIEW,ROLE_SYSTEM_KEY_MODIFY">
					<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="54" align="right" >
						标识：
					</td>
					<td class="title_bar_td" width="50" id="personName" >
						<a class="toggleKey" href="javascript:void(0)" alt="${person.id}" iskey="${person.isKey}">
						{if person.isKey == 1}<img src="image/person_on.png" title="重点人，点击切换至非重点人" />{else}<img src="image/person_off.png" title="非重点人，点击切换至重点人" />{/if}
						</a>
					</td>
				</sec:authorize>
			</tr>
		</table>
	</textarea>
</div>
