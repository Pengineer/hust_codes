<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>信息发布管理</title>
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
						<li class="active">信息发布管理</li>
						<li class="active">企业新闻列表</li>
					</ol>
				</div>
				<div>
					<table id="articleList" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
							  	<th>新闻标题</th>
							    <th>作者</th>
							    <th>查看数量</th>
							    <th>发布时间</th>
							    <th>操作</th>
							</tr>
						</thead>
			            <tbody>
			            </tbody>
			        </table>
			        <table>
			        	<button id = "add_btn" type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off">
			        		添加
						</button>
			        </table>
		        </div>
			</div>
		</div>
		<script type="text/javascript" src="tool/dataTables/js/jquery.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="javascript/oa/article/article_list.js"></script>
		<script>
			$("#add_btn").click(function(){
				window.location.href = "article/toAdd.action";
			});
		</script>
	</body>
</html>
