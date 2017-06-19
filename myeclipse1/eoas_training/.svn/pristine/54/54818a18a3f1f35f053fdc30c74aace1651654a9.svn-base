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
					<ol class="breadcrumb mybreadcrumb">
						<li>当前位置：</li>
						<li class="active">任务受理列表</li>
						<li class="active">列表</li>
					</ol>
				</div>
				<div>
					<table id="taskList" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr id="0" tid="0">
								<th>申请人</th>
								<th>项目名称</th>
								<th>项目负责人</th>
								<th>当前节点</th>
								<th>任务创建时间</th>
								<th>流程状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
			        </table>
		        </div>
			</div>
		</div>
<!-- 		<div id="handleDialog" title="办理">
			<p>你将如何办理呢？</p>
		</div> -->
		
		<!-- 下面是每个节点的模板，用来定义每个节点显示的内容 -->
		<!-- 使用DIV包裹，每个DIV的ID以节点名称命名，如果不同的流程版本需要使用同一个可以自己扩展（例如：在DIV添加属性，标记支持的版本） -->
	
		<!-- 专家审批 -->
<%-- 		<div id="expertAudit" style="display: none">
			<!-- table用来显示信息，方便办理任务 -->
			<%@include file="view-form.jsp" %>
		</div>

		<!-- 管理员审批 -->
		<div id="adminAudit" style="display: none">
			<!-- table用来显示信息，方便办理任务 -->
			<%@include file="view-form.jsp" %>
		</div> --%>

		
		<script type="text/javascript" src="tool/dataTables/js/jquery.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="javascript/oa/project/task_list.js"></script>
		<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>

	</body>
</html>