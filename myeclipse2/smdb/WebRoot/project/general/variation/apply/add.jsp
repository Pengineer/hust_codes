<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
			<script type="text/javascript" src="javascript/project/project_share/handlers_variation.js"></script>
		</head>
		
		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_GeneralProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_AddVariationApply" />
			</div>
			<div class="main">
				<s:include value="/validateError.jsp" />
				<s:include value="/project/general/variation/apply/editBasic.jsp" />
				<div class="main_content">
					<s:hidden name = "flag"></s:hidden>
					<s:form id="form_apply" action="add" namespace="/project/general/variation/apply" method="post" theme="simple" enctype="multipart/form-data">
						<s:include value="/project/general/variation/apply/editTable.jsp" />
						<s:hidden id="deadline" name="deadline" />
						<s:hidden id="varStatus" name="varStatus" />
						<s:hidden id="editFlag" name="editFlag" value="1"/>
					</s:form>
					<div class="btn_bar2">
						<input id="test_save" type="button" class="btn1 j_editSave" value="<s:text name='i18n_Save' />" />
						<input id="submit" type="button" class="btn1 j_editSubmit" value="<s:text name='i18n_Submit' />" />
						<input id="cancel" type="button" class="btn1" value="<s:text name='i18n_Cancel' />"  onclick="history.back();"/>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/variation/edit.js','javascript/project/project_share/variation/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>