<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司招聘</title>
		<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/recruitment/common.css">
	</head>
	<body>
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div id="wrap">
			<div class="content">
				<div class="panel panel-success">
					<div class="panel-heading">修改密码</div>
					<form class="form-signin" role="form" method="post">
						<div class="row center">
							<input type="email" name="email" class="form-control" placeholder="邮箱地址" required>
						</div>
						<div class="row center">
						    <input type="password" name="password" class="form-control" placeholder="当前密码" required>
						</div>
						<div class="row center">
							<input type="password" name="newPassword" class="form-control" placeholder="新密码" required>
						</div>
						<div class="row center">
						    <input type="password" name="newPassword-repeat" class="form-control" placeholder="确认新密码" required>
						</div>
						<div class="row center">
						    <button class="btn btn-success" type="submit">保存</button>
						</div>
					</form>
		        </div>
			</div>
		</div>
	</body>
</html>
