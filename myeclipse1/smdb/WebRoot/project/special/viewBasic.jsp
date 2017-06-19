<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_base_template" style="display:none;">
	<div class="main_content">
		<div class="title_bar">
			{if application.finalAuditStatus == 3 && application.finalAuditResult == 2 && granted != null}<!-- 已立项 -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">项目名称：</td>
						<td class="title_bar_td" colspan="7">${granted.name}</td>
					</tr>
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">英文名：</td>
						<td class="title_bar_td" colspan="7">${granted.englishName}</td>
					</tr>
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">项目子类：</td>
						<td class="title_bar_td" width="120">${subTypeNameNew}</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">项目年度：</td>
						<td class="title_bar_td" >${application.year}</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">项目状态：</td>
						<td class="title_bar_td" width="120">{if granted.status == 1}在研 {elseif granted.status == 2}已结项
						{elseif granted.status == 3}已中止{elseif granted.status == 4}已撤项{/if}  </td>
						</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">负责人：</td>
						<td class="title_bar_td"><s:hidden id="directors" name="${granted.applicantName}" value="${granted.applicantId}" cssClass="directors"/></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">依托高校：</td>
						<td class="title_bar_td"><a id="${universityIdNew}" class="linkUni universityId" href="">${universityNameNew}</a></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">依托院系或研究基地：</td>
						{if departmentIdNew !="" && departmentIdNew != "null"}
							<td class="title_bar_td" ><a id="${departmentIdNew }" class="linkDep" href="">${granted.divisionName}</a></td>
							<input type = "hidden" id = "departmentOrInstitute" value = "department">
						{elseif instituteIdNew != "" && instituteIdNew != "null"}
							<td class="title_bar_td" ><a id="${instituteIdNew }" class="linkIns" href="">${granted.divisionName}</a></td>
							<input type = "hidden" id = "departmentOrInstitute" value = "insitute">
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
						<td class="title_bar_td" width="64" align="right">英文名：</td>
						<td class="title_bar_td" colspan="7">${application.englishName}</td>
					</tr>
					<tr>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">项目子类：</td>
						<td class="title_bar_td" width="120">${subTypeNameOld}</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">项目年度：</td>
						<td class="title_bar_td" >${application.year}</td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="64" align="right">项目状态：</td>
						<td class="title_bar_td" width="120">{if application.finalAuditResult == 0}未审核{elseif application.finalAuditResult == 1}不同意{/if}</td>
					</tr>
					<tr>
						<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">申请人：</td>
						<td class="title_bar_td"><s:hidden id="directors" name="${application.applicantName}" value="${application.applicantId}" cssClass="directors"/></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">依托高校：</td>
						<td class="title_bar_td"><a id="${universityIdOld}" class="linkUni universityId" href="">${application.agencyName}</a></td>
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" align="right">依托院系或研究基地：</td>
						{if departmentIdOld !="" && departmentIdOld != "null"}
							<td class="title_bar_td" ><a id="${departmentIdOld }" class="linkDep" href="">${application.divisionName}</a></td>
							<input type = "hidden" id = "departmentOrInstitute" value = "department">
						{elseif instituteIdOld != "" && instituteIdOld != "null"}
							<td class="title_bar_td" ><a id="${instituteIdOld }" class="linkIns" href="">${application.divisionName}</a></td>
							<input type = "hidden" id = "departmentOrInstitute" value = "insitute">
						{else}
							<td class="title_bar_td" >${application.divisionName}</td>
							<input type = "hidden" id = "departmentOrInstitute" value = "">
						{/if}
					</tr>
				</table>
			{/if}
		</div>
	</div>
</textarea>
<div id="view_base" style="display:none; clear:both;"></div>
