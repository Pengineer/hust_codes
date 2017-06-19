<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_common_template" style="display:none;">
	<div class="title_bar">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="70" align="right">账号所属：</td>
				{if account.isPrincipal == 1 && (account.type == 'MINISTRY' || account.type == 'PROVINCE' || account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY')}
				<td class="title_bar_td"><a id="${belongAgencyId}" class="linkA" href="" title="点击查看详细信息">${belongAgencyName}</a></td>
				{elseif account.isPrincipal == 1 && account.type == 'DEPARTMENT'}
				<td class="title_bar_td"><a id="${belongAgencyId}" class="linkA" href="" title="点击查看详细信息">${belongAgencyName}</a>&nbsp;<a id="${belongDepartmentId}" class="linkD" href="" title="点击查看详细信息">${belongDepartmentName}</a></td>
				{elseif account.isPrincipal == 1 && account.type == 'INSTITUTE'}
				<td class="title_bar_td"><a id="${belongAgencyId}" class="linkA" href="" title="点击查看详细信息">${belongAgencyName}</a>&nbsp;<a id="${belongInstituteId}" class="linkI" href="" title="点击查看详细信息">${belongInstituteName}</a></td>
				{elseif account.isPrincipal == 1 && (account.type == 'EXPERT' || account.type == 'TEACHER' || account.type == 'STUDENT')}
				<td class="title_bar_td"><a id="${belongPersonId}" class="linkP" href="" title="点击查看详细信息">${belongPersonName}</a></td>
				{elseif account.isPrincipal == 0 && (account.type == 'MINISTRY' || account.type == 'PROVINCE' || account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY')}
				<td class="title_bar_td"><a id="${belongAgencyId}" class="linkA" href="" title="点击查看详细信息">${belongAgencyName}</a>&nbsp;<a id="${belongPersonId}" class="linkP" href="" title="点击查看详细信息">${belongPersonName}</a></td>
				{elseif account.isPrincipal == 0 && account.type == 'DEPARTMENT'}
				<td class="title_bar_td">
					<a id="${belongAgencyId}" class="linkA" href="" title="点击查看详细信息">${belongAgencyName}</a>&nbsp;
					<a id="${belongDepartmentId}" class="linkD" href="" title="点击查看详细信息">${belongDepartmentName}</a>
					<a id="${belongPersonId}" class="linkP" href="" title="点击查看详细信息">${belongPersonName}</a>
				</td>
				{elseif account.isPrincipal == 0 && account.type == 'INSTITUTE'}
				<td class="title_bar_td">
					<a id="${belongAgencyId}" class="linkA" href="" title="点击查看详细信息">${belongAgencyName}</a>&nbsp;
					<a id="${belongInstituteId}" class="linkI" href="" title="点击查看详细信息">${belongInstituteName}</a>&nbsp;
					<a id="${belongPersonId}" class="linkP" href="" title="点击查看详细信息">${belongPersonName}</a>
				</td>
				{/if}
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="70" align="right">账号类型：</td>
				<td class="title_bar_td" width="200">
					{if account.type == 'MINISTRY'}部级账号
					{elseif account.type == 'PROVINCE'}省级账号
					{elseif account.type == 'MINISTRY_UNIVERSITY'}部属高校账号
					{elseif account.type == 'LOCAL_UNIVERSITY'}地方高校账号
					{elseif account.type == 'INSTITUTE'}研究基地账号
					{elseif account.type == 'DEPARTMENT'}高校院系账号
					{elseif account.type == 'EXPERT'}外部专家账号
					{elseif account.type == 'TEACHER'}教师账号
					{elseif account.type == 'STUDENT'}学生账号
					{elseif account.type == 'ADMINISTRATOR'}系统管理员账号
					{else}未知账号类型  UNDEFINED
					{/if}
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="70" align="right">通行证：</td>
				<td class="title_bar_td">
					<span><a id="${passport.id}" class="linkPcc" href="" title="点击查看详细信息" >${passport.name}</a></span>
				{if account.type == 'MINISTRY' && account.isPrincipal == 1}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'PROVINCE' && account.isPrincipal == 1}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 1}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'DEPARTMENT' && account.isPrincipal == 1}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'INSTITUTE' && account.isPrincipal == 1}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'MINISTRY' && account.isPrincipal == 0}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'PROVINCE' && account.isPrincipal == 0}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 0}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'DEPARTMENT' && account.isPrincipal == 0}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'INSTITUTE' && account.isPrincipal == 0}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'EXPERT'}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'TEACHER'}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				{if account.type == 'STUDENT'}
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_RETRIEVECODE">
					<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_MODIFYPASSWORD">
					<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</sec:authorize>
				{/if}
				</td>
			</tr>
		</table>
	</div>
</textarea>