<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>流程管理</title>
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
						<li class="active">运行中的流程管理</li>
						<li class="active">列表</li>
					</ol>
				</div>
				<div>
					<table id="runningWorkflowList" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>申请人</th>
								<th>申请时间</th>
								<th>项目名称</th>
								<th>当前节点</th>
								<th>流程状态</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
			        </table>
		        </div>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		
		<script type="text/javascript" src="tool/dataTables/js/jquery.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="javascript/oa/workflow/runningWorkflow_list.js"></script>
		<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>

		<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
		<script src="tool/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
		<script src="tool/html/jquery.outerhtml.js" type="text/javascript"></script>

	</body>
</html>