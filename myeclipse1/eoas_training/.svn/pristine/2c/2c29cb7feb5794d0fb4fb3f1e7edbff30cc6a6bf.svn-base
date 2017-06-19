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
<%-- 		<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
		<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
		<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script> --%>
	</head>
	<body>
		<div id="wrap" class="g-wrapper">
			<div class="content">
				<div class="m-titleBar">
					<ol class="breadcrumb mybreadcrumb">当前位置：
						<li><a href="#"></a></li>
						<li class="active">流程管理</li>
						<li class="active">列表</li>
					</ol>
				</div>
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" class="pull-right">
								  		部署流程
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="panel-body">
								<fieldset id="deployFieldset">
									<legend>部署新流程</legend>
									<div><b>支持文件格式：</b>zip、bar、bpmn、bpmn20.xml</div>
										<form action="workflow/deploy.action" method="post" enctype="multipart/form-data">
											<input type="file" name="file" />
											<input type="submit" value="提交" />
										</form>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
				<div>
					<table id="workflowList" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>ProcessDefinitionId</th>
								<th>DeploymentId</th>
								<th>名称</th>
								<th>版本号</th>
								<th>XML</th>
								<th>图片</th>
								<th>部署时间</th>
								<th>是否挂起</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
			        </table>
			        <table>
			        	<button id = "add_btn" type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off">
			        		部署流程
						</button>
			        </table>
		        </div>
			</div>
		</div>
		<script type="text/javascript" src="tool/dataTables/js/jquery.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/dataTables.bootstrap.min.js"></script>
		<script type="text/javascript" src="javascript/oa/workflow/workflow_list.js"></script>
		<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
	</body>
</html>