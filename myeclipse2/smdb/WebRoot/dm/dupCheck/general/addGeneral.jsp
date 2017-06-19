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
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_DataManagement" />&nbsp;&gt;&nbsp;<s:text name="i18n_DupCheckGeneral" />
			</div>
			<div class="main">
				<div class="main_content">
					<s:form>
						<p>查重条件：</p>
						<p>1、四类项目（一般，基地，重大攻关，后期资助）：在研和撤项（撤项三年内不得申报）。</p>
						<p>2、一般项目专项任务在研。</p>
						<p>3、国家自然科学基金（2010年及之后立项视为在研）。</p>
						<p>4、国家社会科学基金（2006年及之后立项的在研数据）。</p>
						<p>5、国家社会科学基金单列学科教育学年度课题类别包含“国家”的在研数据；单列学科艺术学的在研数据。</p>
						<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_DELETE">
							<input id="reset" type="button" class="btn1" value="清除标记" />
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_DUP_CHECK_GENERAL_ADD">
							<input id="set" type="button" class="btn1" value="添加标记" />
						</sec:authorize>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/dm/dupCheck/general/addGeneral.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>