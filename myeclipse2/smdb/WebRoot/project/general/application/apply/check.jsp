<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar" id="view_project_general">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_GeneralProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_ApplyData" />&nbsp;&gt;&nbsp;<s:text name="申报核算" />
			</div>

			<div class="main">
				<s:include value="/project/general/application/apply/viewCommon.jsp" />
				<div class="p_box_t">
					<div class="p_box_t_b">
						<input id="clear" class="btn2" type="button" value="<s:text name="清空核算" />" />
						<input id="export" class="btn2" type="button" value="<s:text name="导出核算" />" />
					</div>
				</div>
				<div class="p_box_body">
					<div class="main_content" id="tabcontent">
						<div id="tabs" class="p_box_bar">
							<ul>
								<li><a href="#main"><s:text name="限项申报核算表" /></a></li>
							</ul>
						</div>
	
						<div class="p_box">
							<s:include value="/project/general/application/apply/viewMain.jsp" />
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/application/apply/check.js','javascript/project/general/application/apply/validate.js'], function(check, validate) {
					validate.valid();
					check.init();
				});
			</script>
		</body>
	</s:i18n>
</html>