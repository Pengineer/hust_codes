<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div class="title_bar">
	{if topicSelection.finalAuditStatus == 3 && topicSelection.finalAuditResult == 2 && application != null}<!-- 选题已关联项目 -->
		{if application.finalAuditStatus == 3 && application.finalAuditResult == 2 && granted != null}<!-- 已立项 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">项目名称：</td>
					<td class="title_bar_td" colspan="7">${granted.name}</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">英文名称：</td>
					<td class="title_bar_td" colspan="7">${granted.englishName}</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">研究类型：</td>
					<td class="title_bar_td" width="120">${researchTypeNameNew}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">项目年度：</td>
					<td class="title_bar_td" >${application.year}</td>
				</tr>
				<tr>
					<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">首席专家：</td>
					<td class="title_bar_td"><s:hidden id="directors" name="${granted.applicantName}" value="${granted.applicantId}" cssClass="directors"/></td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">依托高校：</td>
					<td class="title_bar_td"><a id="${universityIdNew}" class="linkUni universityId" href="">${universityNameNew}</a></td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right" width="90">依托研究基地：</td>
					{if departmentIdNew !="" && departmentIdNew != "null"}
						<td class="title_bar_td" ><a id="${departmentIdNew }" class="linkDep" href="">${granted.divisionName}</a></td>
						<input type = "hidden" id = "departmentOrInstitute" value = "department">
					{elseif instituteIdNew != "" && instituteIdNew != "null"}
						<td class="title_bar_td" ><a id="${instituteIdNew }" class="linkIns" href="">${granted.divisionName}</a></td>
						<input type = "hidden" id = "departmentOrInstitute" value = "institute">
					{else}
						<td class="title_bar_td" >${granted.divisionName}</td>
						<input type = "hidden" id = "departmentOrInstitute" value = "">
					{/if}
				</tr>
			</table>
		{else}<!-- 未立项 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">项目名称：</td>
					<td class="title_bar_td" colspan="7">${application.name}</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">英文名称：</td>
					<td class="title_bar_td" colspan="7">${application.englishName}</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">研究类型：</td>
					<td class="title_bar_td" width="120">${researchTypeNameOld}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">项目年度：</td>
					<td class="title_bar_td" >${application.year}</td>
				</tr>
				<tr>
					<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">申请人</td>
					<td class="title_bar_td"><s:hidden id="directors" name="${application.applicantName}" value="${application.applicantId}" cssClass="directors"/></td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">依托高校：</td>
					<td class="title_bar_td"><a id="${universityIdOld }" class="linkUni universityId" href="">${application.agencyName}</a></td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right" width="90">依托研究基地：</td>
					{if departmentIdOld !="" && departmentIdOld != "null"}
						<td class="title_bar_td" ><a id="${departmentIdOld }" class="linkDep" href="">${application.divisionName}</a></td>
						<input type = "hidden" id = "departmentOrInstitute" value = "department">
					{elseif instituteIdOld != "" && instituteIdOld != "null"}
						<td class="title_bar_td" ><a id="${instituteIdOld }" class="linkIns" href="">${application.divisionName}</a></td>
						<input type = "hidden" id = "departmentOrInstitute" value = "institute">
					{else}
						<td class="title_bar_td" >${application.divisionName}</td>
						<input type = "hidden" id = "departmentOrInstitute" value = "">
					{/if}
				</tr>
			</table>
		{/if}
	{else}<!-- 未投标 -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">暂无项目记录</td>
			</tr>
		</table>
	{/if}
</div>