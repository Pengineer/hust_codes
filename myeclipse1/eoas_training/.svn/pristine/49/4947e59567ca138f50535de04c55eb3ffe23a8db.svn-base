<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>薪酬管理</title>
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
					<div><input type = "button" value = "创建薪酬记录" onclick = "javascript:location.href = 'salary/toAdd.action'" /></div>
					<div class="panel-heading">薪资列表</div>
					<table id="salaryList" class="table">
						<thead>
							<tr>
								<th>员工姓名</th>
								<th>固定工资</th>
								<th>奖金</th>
								<th>税金</th>
								<th>薪酬实发时间</th>
								<th>薪酬应发时间</th>
								<th>审核时间</th>
								<th>审核状态</th>
								<th>审核人</th>
								<th>备注</th>
							</tr>
						</thead>
			          <tbody> 
			          </tbody>
			        </table>
		        </div>
			</div>
		</div>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="javascript/oa/salary/salary_list.js"></script>
	</body>
</html>
