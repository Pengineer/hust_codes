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
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
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
</head>
<body>
	<div id="wrap">
		<div class="content">
			<div class="panel panel-success">
				<div class="panel-heading">请假申请</div>
				<div><s:hidden id="tag" name="tag" /></div>
				<table id="leaveList" class="table">
					<thead>
					  <tr>
					  	<th>申请日期</th>
					    <th>申请人</th>
					    <th>申请部门</th>
					    <th>天数</th>
					    <th>请假开始时间</th>
					    <th>请假结束时间</th>
					    <th>请假类型</th>
					    <th>审核状态</th>
					    <th>审核结果</th>
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
	<script type="text/javascript" src="javascript/oa/attendance/attendance_listThree.js"></script>
</body>
</html>
