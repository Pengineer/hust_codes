<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>错误页面</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="css/index.css" />
	</head>

	<body>
		<div id="error">
			<table id="table_error" cellspacing="0" cellpadding="0" border="1px">
				<tr id="errorbg">
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="wd2">
						<br/>
						<s:if test="#request.tip == null && filedErrors == null">
							有非法数据产生，请求终止。
						</s:if>
						<s:else>
							<s:property value="#request.tip" />
							<s:fielderror />
						</s:else>
						<br />
						<br />
						<a href="" onclick="history.go(-1);return false;">点击返回</a>
					</td>
				</tr>
			</table>
			<s:if test="#session.visitor.user.issuperuser == 1">
				<s:debug />
			</s:if>
		</div>
	</body>
</html>
