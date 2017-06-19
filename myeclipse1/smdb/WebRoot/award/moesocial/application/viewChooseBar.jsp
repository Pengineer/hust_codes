<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_choose_bar_template" style="display:none;">
	<s:form id="view_award" theme="simple">
		<s:hidden name="listflag"/>
		<div class="choose_bar">
			<ul>
				<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
			<s:if test ="listflag != 5">
				<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
				<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
			 	<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@ADMINISTRATOR)>0 && listflag == 1">
			 		{if awardApplication.status == 1 && awardApplication.applicantSubmitStatus != 3 && awardApplication.finalAuditStatus != 3}
					<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_ADD">
						<li id="view_sub"><input class="btn1" type="button" value="提交" /></li>
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
					</sec:authorize>
					{/if}
					<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLY_ADD">
					<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@ADMINISTRATOR)>0">
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</s:if>
					</sec:authorize>
				</s:if>
			</s:if>
			</ul>
		</div>
	</s:form>
</textarea>
<div id="view_choose_bar" style="display:none;"></div>
