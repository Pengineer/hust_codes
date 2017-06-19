<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>考勤管理</title>
	<base href="<%=basePath%>" />
	<link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/css/demo_page.css" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/examples/examples_support/themes/smoothness/jquery-ui-1.8.4.custom.css" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/css/demo_table_jui.css" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/css/jquery.dataTables_themeroller.css" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/css/jquery.dataTables.css" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/images/back_enabled_hover.png" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/images/forward_enabled.png" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/images/sort_asc.png" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/images/sort_desc.png" />
	<link type="text/css" rel="stylesheet" href="tool/dataTables/images/Sorting icons.psd" />
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
</head>
<body>
	<div id="wrap">
		<div class="content">
			<div class="panel panel-success">
				<div class="panel-heading">签到签退表</div>
				<div><s:hidden id="tag" name="tag" /></div>
				<table id="signInOutList" class="table">
					<thead>
					  <tr>
					  	<th>部门</th>
					    <th>姓名</th>
					    <!-- <th>职务</th> -->
					    <th>签到</th>
					    <th>签退</th>
					    <th>操作</th>
					  </tr>
					</thead>
					<tbody>
						<input id = "addThree" name = "btn" value = "新申请" onclick = "javascript:location.href = 'attendance/toAdd.action?tag=3'" type = "button" />
					</tbody>
		        </table>
	        </div>
		</div>
	</div>
	<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="javascript/oa/attendance/attendance_listOneTwo.js"></script>
</body>
</html>
