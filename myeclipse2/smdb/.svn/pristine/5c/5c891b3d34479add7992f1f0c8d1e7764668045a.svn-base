<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored ="true"%>
<div class="choose_bar">
	<ul>
		<s:if test="listType==1 && #session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT,@csdc.tool.bean.AccountType@STUDENT)">
			<li id="view_back_mypro"><input class="btn1" type="button" value="<s:text name='i18n_Return' />" /></li>
		</s:if>
		<s:else>
			<li id="view_back"><input class="btn1" type="button" value="<s:text name='i18n_Return' />" /></li>
			<s:if test="listType!=6 && listType!=9">
				<s:if test="listType!=7">
					<li id="view_next"><input class="btn1" type="button" value="<s:text name='i18n_NextRecord' />" /></li>
					<li id="view_prev"><input class="btn1" type="button" value="<s:text name='i18n_PrevRecord' />" /></li>
				</s:if>
				<s:if test="listType==1 && #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
					<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="<s:text name='i18n_Delete' />" /></li>
					</sec:authorize>
				</s:if>
				<s:elseif test="listType==2">
					<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="<s:text name='i18n_Delete' />" /></li>
					</sec:authorize>
				</s:elseif>
				<s:if test="listType==1 && #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
					<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="<s:text name='i18n_Modify' />" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_ADD">
						<li id="view_add"><input class="btn1" type="button" value="<s:text name='i18n_Add' />" /></li>
					</sec:authorize>
				</s:if>
				<s:elseif test="listType==2">
					<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="<s:text name='i18n_Modify' />" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_ADD">
						<li id="view_add"><input class="btn1" type="button" value="<s:text name='i18n_Add' />" /></li>
					</sec:authorize>
				</s:elseif>
			</s:if>
		</s:else>
	</ul>
</div>