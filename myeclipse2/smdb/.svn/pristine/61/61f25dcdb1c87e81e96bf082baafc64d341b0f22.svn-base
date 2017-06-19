<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_DM">
		<head>
			<title><s:text name="i18n_Add" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_Add" />
			</div>
			
			<s:form id="form_universityVariation" action="add" namespace="/dm/universityVariation" theme="simple">
				<s:include value="/dm/universityVariation/table.jsp" />
			</s:form>
			
			<div class="btn_bar2">
				<input class="btn1" type="button" value="<s:text name='i18n_Ok' />" onclick="submitUniversityRename();"/>
				<input class="btn1" type="button" value="<s:text name='i18n_Cancel' />" onclick="history.back();" />
			</div>
			<script type="text/javascript">
				seajs.use('javascript/dm/universityVariation/edit.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>