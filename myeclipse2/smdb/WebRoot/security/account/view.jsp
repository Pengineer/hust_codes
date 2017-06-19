<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="view_content" class="main" style="display:none;">
	<s:form id="view" action="" theme="simple">
		<s:hidden id="entityId" name="entityId" value="%{entityId}" />
		<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
		<s:hidden id="update" name="update" />
		<s:hidden id="datepick" name="validity" />
		<s:hidden id="roleIds" name="roleIds" />
		<s:hidden id="pageInfo" name="pageInfo" />
		<input id="accountType" type="hidden" value="<s:property value="#session.loginer.currentType" />" />
	</s:form>

	<textarea id="view_choose_bar_template" style="display:none;">
		<s:include value="/security/account/viewChooseBar.jsp" />
	</textarea>
	
	<div id="view_choose_bar" style="clear:both;"></div>
	
	<div class="main_content">
		<s:include value="/security/account/viewCommon.jsp" />
		<div id="view_common" style="clear:both;"></div>
		<div class="main_content">
			<div id="tabs" class="p_box_bar">
				<ul>
					<li><a href="#basic">基本信息</a></li>
					<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">
						<li><a href="#log">日志信息</a></li>
					</s:if>
				</ul>
			</div>

			<div class="p_box">
				<div id="basic">
					<s:include value="/security/account/viewBasic.jsp" />
				</div>
				<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">
					<div id="log">
						<s:include value="/security/account/viewLog.jsp" />
					</div>
				</s:if>
			</div>
		</div>
	</div>
</div>