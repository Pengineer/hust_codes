<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>帐号管理</title>
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
					<div class="panel-heading">新闻列表</div>
					<table id="personList" class="table">
						<thead>
						  <tr>
						  	<th>员工姓名</th>
						    <th>性别</th>
						    <th>工作部门</th>
						    <th>员工编号</th>
						    <th>手机号</th>
						    <th>入职时间</th>
						    <th>操作</th>
						  </tr>
						</thead>
			          <tbody>
			            
			          </tbody>
			        </table>
		        </div>
			</div>
		</div>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="javascript/oa/person/person_list.js"></script>
	</body>
</html>
