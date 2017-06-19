<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<s:head/>
			<title>添加角色</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/roleright/roleright.css" />
		</head>

		<body>
			<div id="top">
				系统管理&nbsp;&gt;&gt;&nbsp;角色权限&nbsp;&gt;&gt;&nbsp;角色列表&nbsp;&gt;&gt;&nbsp;<span class="text_red">添加角色</span>
			</div>
				
			<s:form id="form_role" action="add" namespace="/role" theme="simple">
				<s:include value="/security/role/table.jsp" />
			</s:form>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/security/role/validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>