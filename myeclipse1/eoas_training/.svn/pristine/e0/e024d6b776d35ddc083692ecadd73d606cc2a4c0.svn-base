<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>邮件管理</title>
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
						<li class="active">邮件管理</li>
						<li class="active">列表</li>
					</ol>
				</div>
				<div>
					<table id="mailList" class="table table-striped table-bordered" cellspacing="0" width="100%">
						<thead>
							<tr>
							  	<th>发件人</th>
							    <th>邮件主题</th>
							    <th>发送时间</th>
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
		<script type="text/javascript" src="javascript/oa/mail/mail_list.js"></script>
		<script>
			$("#add_btn").click(function(){
				window.location.href = "mail/toAdd.action";
			});
		</script>
	</body>
</html>