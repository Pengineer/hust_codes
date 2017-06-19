<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>发文管理</title>
		<base href="<%=basePath%>" />
		<link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
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
		<div id="wrap">
			<div class="content">
				<div class="panel panel-success">
					<div class="panel-heading">拟稿列表</div>
					<table id="documentSenderList" class="table">
						<thead>
							<tr>
								<th>发文标题</th>
								<th>发文编号</th>
								<th>发文类型</th>
								<th>紧急程度</th>
								<th>拟搞时间</th>
								<th>拟稿人</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
			        </table>
		        </div>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="javascript/oa/document/document_listOne.js"></script>
	</body>
</html>
