<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>接口管理</title>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div class="link_bar">
			当前位置：接口管理&nbsp;&gt;&nbsp;社科网服务端&nbsp;&gt;&nbsp;申请结果接口
		</div>
	    <s:form action="applicationResultConfig" namespace="/system/interfaces/sinossServer">
		    <s:include value="/system/interfaces/sinossServer/tableConfig.jsp" />
		</s:form>
		<s:include value="/system/interfaces/sinossServer/bottom.jsp" />
	</body>
</html>