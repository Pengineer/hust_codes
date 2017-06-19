<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Main">
		<head>
			<base href="<%=basePath%>" />
		    <title><s:text name="i18n_SystemName" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		</head>
		<frameset cols="*,960px,*" frameborder="no" border="0" framespaceing="0">
			<frame scrolling="no" noresize="noresize" />
			<frameset rows="92px,*,16px">
				<frame id="top" name="top" src="main/page_top.action" scrolling="no" noresize="noresize" />
				<frameset id="body" rows="*" cols="160px,*">
					<frame id="left" name="left" src="main/page_left.action" scrolling="auto" noresize="noresize"/>
					<frame id="right" name="right" src="main/page_right.action" scrolling="yes" noresize="noresize" />
				</frameset>
				<frame id="bottom" name="bottom" src="main/page_bottom.action" scrolling="no" noresize="noresize" />
			</frameset>
			<frame scrolling="no" noresize="noresize" />
			<noframes>
				<body></body>
			</noframes>
		</frameset>
	</s:i18n>
</html>