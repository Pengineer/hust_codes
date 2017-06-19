<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_related_template" style="display:none;">
	{if memberFlag == 1}
	<div class="p_box_t">
		<div class="p_box_t_t"><s:text name="i18n_BasicInfoOfDirector" /></div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		{for dirPerson in dirPersons}
			{if dirPerson != null && dirPerson.id != null && dirPerson.id !=""}
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead class="head_title1">
						<tr>
							<td colspan="6"><span><s:text name="i18n_On"/></span><span class="director_num">${dirPerson_index}</span><span><s:text name="i18n_Director"/></span></td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr7">
							<td class="key" width="80"><s:text name="i18n_Name" />：</td>
							<td class="value" width="150"><a id="${dirPerson.id}" class="linkDirectors" href="">${dirPerson.name}</a></td>
							<td class="key" width="80"><s:text name="i18n_Gender" />：</td>
							<td class="value" width="150">${dirPerson.gender}</td>
							<td class="key" width="80"><s:text name="i18n_Birthday" />：</td>
							<td class="value">${dirPerson.birthday}</td>
						</tr>
						<tr class="table_tr7">
							<td class="key"><s:text name="i18n_LastDegree" />：</td>
							<td class="value">{if dirAcademics[dirPerson_index] != null}${dirAcademics[dirPerson_index].lastDegree}{/if}</td>
							<td class="key"><s:text name="i18n_LastEducation" />：</td>
							<td class="value" colspan="3">{if dirAcademics[dirPerson_index] != null}${dirAcademics[dirPerson_index].lastEducation}{/if}</td>
						</tr>
						<tr class="table_tr7">
							<td class="key"><s:text name="i18n_Telephone" />：</td>
							<td class="value">${dirPerson.officePhone}</td>
							<td class="key"><s:text name="i18n_Email" />：</td>
							<td class="value" colspan="3">${dirPerson.email}</td>
						</tr>
					</tbody>
				</table>
			{else}
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr>
						<td align="center"><s:text name="i18n_NoRecords" /></td>
					</tr>
				</table>
			{/if}
		{/for}
	</div>
	<div class="p_box_t">
		<div class="p_box_t_t"><s:text name="i18n_MemberInfo" /></div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<table id="list_entrust_member" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style="text-align:center;">
			<thead>
				<tr class="table_title_tr2">
					<td width="60"><s:text name="i18n_Name" /></td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td><s:text name="i18n_SpecialistTitle" /></td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td><s:text name="i18n_Major" /></td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td><s:text name="i18n_LocalUnit" /></td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td><s:text name="i18n_LocalDeptInst" /></td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td><s:text name="i18n_WorkMonthPerYear" /></td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td><s:text name="i18n_WorkDivision" /></td>
					<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
					<td><s:text name="i18n_IsDirector" /></td>
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
								<a id="${item[6]}" class="linkUni" href="" title="<s:text name='i18n_ViewDetails' />">${item[7]}</a>
							{else}
								${item[7]}
							{/if}	
						</td>
						<td></td>
						<td>
							{if item[8]!= "" && item[8] != null}
								<a id="${item[8]}" class="linkDep" href="" title="<s:text name='i18n_ViewDetails' />">${item[10]}</a>
							{elseif item[9] != "" && item[9] != null}
								<a id="${item[9]}" class="linkIns" href="" title="<s:text name='i18n_ViewDetails' />">${item[10]}</a></td>
							{else}
								${item[10]}
							{/if}
						</td>
						<td></td>
						<td>${item[11]}</td>
						<td></td>
						<td>${item[12]}</td>
						<td></td>
						<td>{if item[1] == 1}<s:text name="i18n_Yes" />{else}<s:text name="i18n_No" />{/if}</td>
					</tr>
			{forelse}
				<tr>
					<td align="center"><s:text name="i18n_NoRecords" /></td>
				</tr>
			{/for}
			</tbody>
		</table>
	</div>
	{else}
	<div class="p_box_body">
		<div style="text-align:center;"><s:text name="i18n_NoRecords" /></div>
	</div>
	{/if}
</textarea>
<div id="view_related" style="display:none;"></div>
