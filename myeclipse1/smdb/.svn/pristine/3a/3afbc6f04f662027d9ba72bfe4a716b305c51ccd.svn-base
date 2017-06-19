<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>系统选项表</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<style type="text/css" media="screen">
				table td,th {text-align:center;padding:5px;}
			</style>
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统配置
			</div>
			
			<div class="main" style="width:100%">
				<ul style="margin-left:33px;">
					<li class="config"><a href="system/config/toConfigLogin.action">配置登录限制</a></li>
					<li class="config"><a href="system/config/toConfigSearchCondition.action">配置列表显示</a></li>
					<li class="config"><a href="system/config/toConfigPath.action">配置上传路径</a></li><%--
					<li class="config"><a href="system/config/toConfigMailer.action">配置系统邮件</a></li>
					--%>
					<li class="config"><a href="system/config/toConfigRegister.action">配置系统注册</a></li>
				</ul>
			</div>
		</body>
	
</html>
