<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="choose_bar">
	<ul>
		<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
		<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
		<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
	{if account.type == 'MINISTRY' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'PROVINCE' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'DEPARTMENT' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'INSTITUTE' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'MINISTRY' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'PROVINCE' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'DEPARTMENT' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'INSTITUTE' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'EXPERT'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'TEACHER'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'STUDENT'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_DELETE">
		<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
	</sec:authorize>
	{/if}
	
	{if account.type == 'MINISTRY' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'PROVINCE' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'DEPARTMENT' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'INSTITUTE' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'MINISTRY' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'PROVINCE' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'DEPARTMENT' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'INSTITUTE' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'EXPERT'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'TEACHER'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'STUDENT'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_MODIFY">
		<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
	</sec:authorize>
	{/if}
	
	{if account.type == 'MINISTRY' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_MAIN_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'PROVINCE' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_MAIN_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_MAIN_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'DEPARTMENT' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_MAIN_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'INSTITUTE' && account.isPrincipal == 1}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_MAIN_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'MINISTRY' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_MINISTRY_SUB_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'PROVINCE' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_PROVINCE_SUB_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if (account.type == 'MINISTRY_UNIVERSITY' || account.type == 'LOCAL_UNIVERSITY') && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_UNIVERSITY_SUB_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'DEPARTMENT' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_DEPARTMENT_SUB_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'INSTITUTE' && account.isPrincipal == 0}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_INSTITUTE_SUB_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'EXPERT'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_EXPERT_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'TEACHER'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_TEACHER_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	{if account.type == 'STUDENT'}
	<sec:authorize ifAllGranted="ROLE_SECURITY_ACCOUNT_STUDENT_ADD">
		<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
	</sec:authorize>
	{/if}
	</ul>
</div>