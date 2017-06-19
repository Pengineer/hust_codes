<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>弹出框管理</title>
	</head>
	<body>
		<s:hidden id="popTaskId" name="taskId"></s:hidden>
		<table class='view-info'>
			<tr>
				<td width="100" class="label">申请人：</td>
				<td name="${project.account.name }">${project.account.name }</td>
			</tr>
			
			<tr>
				<td class="label">申请理由：</td>
				<td name="${project.id}">${project.id }</td>
			</tr>
		</table>
	</body>
</html>