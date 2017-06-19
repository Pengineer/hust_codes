<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>EOAS工作台</title>
	<s:include value="/jsp/innerBase.jsp" />
</head>
<frameset rows="76,*" framespacing=0 border=0 frameborder="0" name="topFrame">
	<frame noresize name="TopMenu" scrolling="no" src="jsp/oa/home/top.jsp"></frame>
	<frameset cols="250,*" id="resize">
 		<frame noresize name="menu" src="jsp/oa/home/left.jsp"></frame>
		<frame noresize name="right" scrolling="Auto" src="jsp/oa/home/right.jsp"></frame>
	</frameset>
</frameset>
</html>