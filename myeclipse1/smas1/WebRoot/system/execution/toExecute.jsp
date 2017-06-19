<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Execution</title>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div id="top">
				系统管理&nbsp;&gt;&gt;&nbsp;系统配置&nbsp;&gt;&gt;&nbsp;<span class="text_red">execution</span>
			</div>
		<div id="bottom">
			<s:form action="execute" namespace="/execution">
				<span>Executin Bean Name: </span>
				<input name="executionBeanName" size="100" />
				<input type="submit" class="btn" value="Execute" />
			</s:form>
		</div>
	</body>
</html>
