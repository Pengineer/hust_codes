<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_DM">
		<head>
			<title><s:text name="i18n_DupCheck" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_DataManagement" />&nbsp;&gt;&nbsp;<s:text name="i18n_DupCheckInstp" />
			</div>
			<div class="main">
				<div class="main_content">
					<s:form>
						<p>查重条件：</p>
						<p>1、基地，重大攻关，后期资助项目：在研。</p>
						<p>2、国家社会科学基金重大项目：在研。</p>
						<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_DELETE">
							<input id="reset" type="button" class="btn1" value="清除标记" />
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_INSTP_ADD">
							<input id="set" type="button" class="btn1" value="添加标记" />
						</sec:authorize>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/dm/dupCheck/instp/addInstp.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>