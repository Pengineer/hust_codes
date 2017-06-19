<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div class="title_bar">
	{if application.finalAuditStatus == 3 && application.finalAuditResult == 2 && granted != null}<!-- 已立项 -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ProjectName" />：</td>
				<td class="title_bar_td" colspan="7">${granted.name}</td>
			</tr>
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_EnglishName" />：</td>
				<td class="title_bar_td" colspan="7">${granted.englishName}</td>
			</tr>
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_IssueType" />：</td>
				<td class="title_bar_td" width="120">${subTypeNameNew}</td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ProjectTopic" />：</td>
				<td class="title_bar_td" width="120">${topicName}</td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ProjectYear" />：</td>
				<td class="title_bar_td" >${application.year}</td>
			</tr>
			<tr>
				<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" align="right"><s:text name="i18n_Director" />：</td>
				<td class="title_bar_td"><s:hidden id="directors" name="${granted.applicantName}" value="${granted.applicantId}" cssClass="directors"/></td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" align="right"><s:text name="i18n_University" />：</td>
				<td class="title_bar_td"><a id="${universityIdNew}" class="linkUni" href="">${universityNameNew}</a></td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" align="right"><s:text name="i18n_DeptInst" />：</td>
				{if departmentIdNew !="" && departmentIdNew != "null"}
					<td class="title_bar_td" ><a id="${departmentIdNew}" class="linkDep" href="">${granted.divisionName}</a></td>
				{elseif instituteIdNew != "" && instituteIdNew != "null"}
					<td class="title_bar_td" ><a id="${instituteIdNew}" class="linkIns" href="">${granted.divisionName}</a></td>
				{else}
					<td class="title_bar_td" >${granted.divisionName}</td>
				{/if}
			</tr>
		</table>
	{else}<!-- 未立项 -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ProjectName" />：</td>
				<td class="title_bar_td" colspan="7">${application.name}</td>
			</tr>
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_EnglishName" />：</td>
				<td class="title_bar_td" colspan="7">${application.englishName}</td>
			</tr>
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_IssueType" />：</td>
				<td class="title_bar_td" width="120">${subTypeNameOld}</td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ProjectTopic" />：</td>
				<td class="title_bar_td" width="120">${topicName}</td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="64" align="right"><s:text name="i18n_ProjectYear" />：</td>
				<td class="title_bar_td" >${application.year}</td>
			</tr>
			<tr>
				<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" align="right"><s:text name='i18n_Applicant'/>：</td>
				<td class="title_bar_td"><s:hidden id="directors" name="${application.applicantName}" value="${application.applicantId}" cssClass="directors"/></td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" align="right"><s:text name="i18n_University" />：</td>
				<td class="title_bar_td"><a id="${universityIdOld}" class="linkUni" href="">${application.agencyName}</a></td>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" align="right"><s:text name="i18n_DeptInst" />：</td>
				{if departmentIdOld !="" && departmentIdOld != "null"}
					<td class="title_bar_td" ><a id="${departmentIdOld }" class="linkDep" href="">${application.divisionName}</a></td>
				{elseif instituteIdOld != "" && instituteIdOld != "null"}
					<td class="title_bar_td" ><a id="${instituteIdOld }" class="linkIns" href="">${application.divisionName}</a></td>
				{else}
					<td class="title_bar_td" >${application.divisionName}</td>
				{/if}
			</tr>
		</table>
	{/if}
</div>