<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="i18n_ViewMail" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>

	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="解决方案管理" /></li>
				<li class="text_red"><s:text name="查看解决方案" /></li>
			</ul>
		</div>
		<div class="solution_body">
			<s:form theme = "simple">
				<div class = "solution_title"><s:property value = "solution.title"/></div>
				<div class = "solution_bar">
					<span><s:text name = "创建时间" />：<s:date name = "solution.createDate" format="dd/MM/yyyy HH:mm:ss"/></span>
					<span><s:text name = "查看次数" />：<s:property value = "solution.viewCount"/></span>
				</div>
				<div class = "solution_txt">${solution.content}</div>
			</s:form>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
</html>