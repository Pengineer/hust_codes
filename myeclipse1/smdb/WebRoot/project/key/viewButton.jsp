<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored ="true"%>
<div class="choose_bar">
	<ul>
		<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
		<s:if test="listType!=6 && listType!=9">
			<s:if test="listType!=7">
				<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
				<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
			</s:if>
			<s:if test="listType==12 && #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_DATA_DELETE">
					<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
				</sec:authorize>
			</s:if>
			<s:elseif test="listType==1 && #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_APPLICATION_APPLY_DELETE">
					<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
				</sec:authorize>
			</s:elseif>
			<s:elseif test="listType==2">
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_APPLICATION_GRANTED_DELETE">
					<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
				</sec:authorize>
			</s:elseif>
			<s:if test="listType==12 && #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_DATA_MODIFY">
					<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_DATA_ADD">
					<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
				</sec:authorize>
			</s:if>
			<s:elseif test="listType==1 && #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_APPLICATION_APPLY_MODIFY">
					<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_APPLICATION_APPLY_ADD">
					<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
				</sec:authorize>
			</s:elseif>
			<s:elseif test="listType==2">
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_APPLICATION_GRANTED_MODIFY">
					<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
				</sec:authorize>
				<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_APPLICATION_GRANTED_ADD">
					<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
				</sec:authorize>
			</s:elseif>
		</s:if>
	</ul>
</div>