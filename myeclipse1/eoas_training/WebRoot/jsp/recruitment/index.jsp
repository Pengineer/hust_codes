<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<title>招聘</title>
	<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="css/recruitment/index.css">
  </head>
  
  <body>
    <div class="container">
		<div class="nav">
			<ul>
				<li class="ent-logo">EOAS</li>
				<li class="social-link"><a href="javascript:void(0);">社会招聘</a></li>
				<li class="campus-link"><a href="javascript:void(0);">校园招聘</a></li>
				<li class="intern-link"><a href="javascript:void(0);">实习生招聘</a></li>
			</ul>
			<div class="entry">
				<a href="jsp/recruitment/account/login.jsp" class="btn btn-success" role="button">登录</a>
				<a href="jsp/recruitment/account/login.jsp" class="btn btn-primary" role="button">注册</a>
			</div>
		</div>
	</div>
	<div class="footer">
		©2014 公司|<a href="person/toIndex.action">关于公司</a>|<a href="${pageContext.request.contextPath}/jsp/index.jsp">OA入口</a>
	</div>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
  </body>
</html>
