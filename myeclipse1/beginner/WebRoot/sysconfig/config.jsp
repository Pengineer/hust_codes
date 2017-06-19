<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Main">
		<head>
			<base href="<%=basePath%>" />
		    <title><s:text name="i18n_SystemName" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/main/page.css" />
  		</head>
  
		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<span class="text_red"><s:text name="i18n_SysConfig" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<div align="center" style="line-height:30px;">
						<ul>
						<!-- 
							<li>
								<a href="main/toConfigUpload.action"><s:text name="i18n_ConfigFileUpload" /></a>
							</li>
						 -->
							<li>
								<a href="main/toConfigPageSize.action"><s:text name="i18n_ConfigPageSize" /></a>
							</li>
						<!--  
							<li>
								<a href="main/toConfigSelect.action"><s:text name="i18n_ConfigSelect" /></a>
							</li>
						-->
						</ul>
					</div>
				</div>
			</div>
		</body>
	</s:i18n>
</html>