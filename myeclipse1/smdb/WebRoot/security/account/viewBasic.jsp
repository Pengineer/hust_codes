<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_basic_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="70">账号状态：</td>
					<td class="value" width="150">
					{if account.status == 1}
						启用
						{if account.type == 'MINISTRY' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'PROVINCE' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'DEPARTMENT' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'INSTITUTE' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'MINISTRY' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'PROVINCE' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'DEPARTMENT' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'INSTITUTE' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'EXPERT'}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'TEACHER'}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'STUDENT'}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_STATUS">
							<span id="view_disable">[<a href="" title="点击停用账号">停用账号</a>]</span>
						</sec:authorize>
						{/if}
					{else}
						停用
						{if account.type == 'MINISTRY' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'PROVINCE' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'DEPARTMENT' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'INSTITUTE' && account.isPrincipal == 1}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'MINISTRY' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'PROVINCE' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'DEPARTMENT' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'INSTITUTE' && account.isPrincipal == 0}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'EXPERT'}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'TEACHER'}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
						{if account.type == 'STUDENT'}
						<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_STATUS">
							<span id="view_enable">[<a href="" title="点击启用账号">启用账号</a>]</span>
						</sec:authorize>
						{/if}
					{/if}
					</td>
					<td class="key" width="90">账号角色：</td>
					<td class="value">
					${rolename}
					<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_ASSIGNROLE">
						<span id="view_distr">[<a href="" title="点击分配角色">分配角色</a>]</span>
					</sec:authorize>
					</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">创建时间：</td>
					<td class="value">${account.startDate}</td>
					<td class="key">有效期限：</td>
					<td class="value">${account.expireDate}</td>
				</tr>
			</table>
		</div>
	</div>
</textarea>
<div id="view_basic"></div>