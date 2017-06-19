<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_GrantedData" />&nbsp;&gt;&nbsp;<s:text name="i18n_Modify" />
			</div>

			<div class="main">
				<s:form id="application_form" action="modify" namespace="/project/entrust/application/granted">
					<s:hidden id = "appId" name="application1.id"/>
					<s:hidden id="addOrModify" value='2'/>
					<div id="info" style="display:none">
						<div class="main_content">
							<div id="procedure" class="step_css">
								<ul>
									<li class="proc" name="apply"><span class="left_step"></span><span class="right_step"><s:text name="i18n_ApplyInfo" /></span></li>
									<li class="proc" name="granted"><span class="left_step"></span><span class="right_step"><s:text name="i18n_GrantedInfo" /></span></li>
									<li class="proc" name="member"><span class="left_step"></span><span class="right_step"><s:text name="i18n_RelatedMember" /></span></li>
									<li class="step_oo" name="finish"><span class="left_step"></span><span class="right_step"><s:text name="i18n_Finish" /></span></li>
								</ul>
							</div>
						</div>

						<div class="main_content">
							<s:hidden name = "flag"></s:hidden>
							<s:include value="/validateError.jsp" />
							<s:include value="/project/entrust/application/granted/editApplyTab.jsp" />
							<s:include value="/project/entrust/application/granted/editGrantedTab.jsp" />
							<s:include value="/project/entrust/application/granted/editMemberTab.jsp" />
						</div>
					</div>
				</s:form>
				<s:include value="/project/entrust/application/granted/memberTemplate.jsp" />
				<div id="optr" class="btn_bar2">
					<input id="prev" class="btn2" type="button" value="<s:text name="i18n_PrevStep" />" />
					<input id="next" class="btn2" type="button" value="<s:text name="i18n_NextStep" />" />
					<input id="finish" class="btn1" type="button" value="<s:text name="i18n_Finish" />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name="i18n_Cancel" />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/entrust/application/granted/modify.js','javascript/project/project_share/application/validate.js'], function(modify, validate) {
					validate.valid();
					modify.init();
				});
			</script>
		</body>
	</s:i18n>
</html>