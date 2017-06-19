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
		<link href="tool/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/recruitment/common.css">
	</head>
	<body>
	    <s:include value="/jsp/recruitment/topBottom.jsp" />
		<div id="wrap">
			<div class="content">
				<div class="panel panel-success">
					<div class="panel-heading">我的简历</div>
					<table class="table">
			          <thead>
			            <tr>
			              <th>序号</th>
			              <th>简历类型</th>
			              <th>创建时间</th>
			              <th>修改时间</th>
			              <th>操作</th>
			            </tr>
			          </thead>
			          <tbody>
			            
			          </tbody>
			        </table>
		        </div>
			</div>
		</div>
	</body>
</html>
