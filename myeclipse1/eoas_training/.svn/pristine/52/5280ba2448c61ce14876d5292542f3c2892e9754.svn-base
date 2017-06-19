<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>解决方案管理</title>
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
					<div><input type = "button" value = "创建薪酬记录" onclick = "javascript:location.href = 'salary/toAdd.action'" /></div>
					<div class="panel-heading">解决方案列表</div>
					<table id="solutionList" class="table">
						<thead>
							<tr>
								<th>标题</th>
								<th>发布时间</th>
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
		<script type="text/javascript" src="javascript/oa/solution/solution_list.js"></script>
	</body>
</html>
