<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>" />
	<title>岗位列表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="script/jquery.js"></script>
	<script type="text/javascript" src="script/pageCommon.js" charset="utf-8"></script>
	<script type="text/javascript" src="script/PageUtils.js" charset="utf-8"></script>
	<link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
</head>
<body>

	<div id="Title_bar">
		<div id="Title_bar_Head">
			<div id="Title_Head"></div>
			<div id="Title">
				<!--页面标题-->
				<img border="0" width="13" height="13"
					src="style/images/title_arrow.gif" />
				人员管理
			</div>
			<div id="Title_End"></div>
		</div>
	</div>

	<div id="MainArea">
		<table cellspacing="0" cellpadding="0" class="TableStyle">

			<!-- 表头-->
			<thead>
				<tr align="CENTER" valign="MIDDLE" id="TableTitle">
					<td width="200px">职员姓名</td>
					<td width="100px">性别</td>
					<td width="200px">部门</td>
					<td width="200px">职务</td>
					<td width="300px">电子邮箱</td>
					<td width="300px">手机</td>
					<td width="100">职员状态</td>
					<td width="500">相关操作</td>
				</tr>
			</thead>

			<!--显示数据列表-->
			<tbody id="TableData" class="dataContainer" >
			<s:property value="personRoleDepartment[0].person.realname" />
				<s:iterator value="personRoleDepartment" status="stat">
				
					<s:hidden value="id"></s:hidden>
					<tr class="TableDetail1 template">
						<td><s:property value="personRoleDepartment[#stat.index].person.realname" />&nbsp;</td>
						<td align="center"><s:property value="personRoleDepartment[#stat.index].person.sex" />&nbsp;</td>
						<td><s:property value="personRoleDepartment[#stat.index].department.name" />&nbsp;</td>
						<td><s:property value="personRoleDepartment[#stat.index].role.name" />&nbsp;</td>
						<td><s:property value="personRoleDepartment[#stat.index].person.personemail" />&nbsp;</td>
						<td><s:property value="personRoleDepartment[#stat.index].person.mobilephone" />&nbsp;</td>
						<td align="center"><s:property value="personRoleDepartment[#stat.index].person.status" />&nbsp;</td>
						<td><s:a action="personAction_delete?id=%{id}"
								onclick="return delConfirm()">删除</s:a> 
							<s:a action="person/staffAction_addUI?id=%{id}">账号设置</s:a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

	
	</div>
	<!--说明-->	
	<div id="Description"> 
		<s:debug></s:debug>
	</div>
</body>
</html>
