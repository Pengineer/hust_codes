<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_common_template" style="display:none;">
	<div class="title_bar">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="50" align="right">通行证：</td>
				<td class="title_bar_td" width="200">${passport.name}
				<span id="view_retri">[<a href="" title="点击重置密码">重置密码</a>]</span>
				<span id="view_modpass">[<a href="" title="点击修改密码">修改密码</a>]</span>
				</td>
				<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="70" align="right">登陆次数：</td>
				<td class="title_bar_td" width="200">${passport.loginCount}
				</td>
			</tr>
			<tr>
				<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="70" align="right">上次登录时间：</td>
				<td class="title_bar_td" width="200">${passport.lastLoginDate}
				</td>
				<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
				<td class="title_bar_td" width="70" align="right">上次登录IP：</td>
				<td class="title_bar_td" width="200">${passport.lastLoginIp}
				</td>
			</tr>
			<%--<tr>
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
			</tr>
		--%></table>
	</div>
</textarea>