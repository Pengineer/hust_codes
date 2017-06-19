<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>" />
	<title>人员菜单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/DT_bootstrap.css" />
	<style type="text/css" title="currentStyle">
		@import "tool/dataTables/css/demo_page.css";
		@import "tool/dataTables/examples/examples_support/themes/smoothness/jquery-ui-1.8.4.custom.css";
		@import "tool/dataTables/css/demo_table_jui.css";
		@import "tool/dataTables/css/jquery.dataTables_themeroller.css";
		@import "tool/dataTables/css/jquery.dataTables.css";
		@import "tool/dataTables/images/back_enabled_hover.png";
		@import "tool/dataTables/images/forward_enabled.png";
		@import "tool/dataTables/images/sort_asc.png";
		@import "tool/dataTables/images/sort_desc.png";
		@import "tool/dataTables/images/Sorting icons.psd";
	</style>
</head>
<body>
	<div id="Title_bar">
		<div id="Title_bar_Head">
			<div id="Title_Head"></div>
			<div id="Title">
				<!--页面标题-->
				<img border="0" width="13" height="13" src="style/images/title_arrow.gif" />用户管理
			</div>
			<div id="Title_End"></div>
		</div>
	</div>

	<div id="MainArea">
		<table id="personList" cellspacing="0" cellpadding="0" class="TableStyle">

			<!-- 表头-->
			<thead>
				<tr align="center" valign="middle" id="TableTitle" class="TableStyle">
					<th>职员姓名</th>
					<th>性别</th>
					<th>手机号码</th>
					<th>办公号码</th>
					<th>电子邮箱</th>
					<th>职员状态</th>
					<th>相关操作</th>
				</tr>
			</thead>
		</table>


		<!-- 其他功能超链接 -->
		<div id="TableTail">
			<div id="TableTail_inside">
				<s:a action="personAction_addUI">
					<img
						src="style/images/createNew.png" />
				</s:a>
			</div>
		</div>
	</div>
	<!--说明-->	
	<div id="Description"> 
		说明：<br />
		1，点击职员姓名时，可以查看并编辑修改该职员对应的基本信息。<br />
		2，点击账号设置时，可以对职员的账号进行分配并设置Staff状态。
	</div>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="javascript/oa/systemPerson/personList.js"></script>
	<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
</body>
</html>
