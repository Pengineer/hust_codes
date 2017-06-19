<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>专家顾问管理</title>
		<s:include value="/jsp/innerBase.jsp" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/css/dataTables.bootstrap.min.css" />
		<link href="tool/poplayer/css/ui-dialog.css" rel="stylesheet">
	</head>
	<body>
		<div id="wrap" class="g-wrapper">
			<div class="content">
				<div class="m-titleBar">
			<ol class="breadcrumb mybreadcrumb">当前位置：
			  <li><a href="#"></a></li>
			  <li class="active">专家顾问</li>
			  <li class="active">列表</li>
			</ol>
			</div>
				<div>
					<table id="expertList" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
						  	<tr>
						  		<th>序号</th>
							  	<th>姓名</th>
							    <th>性别</th>
							    <th>所在单位</th>
							    <th>所在部门</th>
							    <th>行政职务</th>
							    <th>专业职称</th>
							    <th>操作</th>
						  	</tr>
						</thead>
				        <tbody>
				        </tbody>
			        </table>
		        </div>
			</div>
		</div>
		<script type="text/javascript" src="tool/dataTables/js/jquery.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="javascript/oa/expert/expert_list.js"></script>
		<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
	</body>
</html>
