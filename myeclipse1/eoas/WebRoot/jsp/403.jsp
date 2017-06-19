<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>禁止访问</title>
<%--		<jsp:include page="/innerBase.jsp" />--%>
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#back").bind("click", function() {
					history.back(-1);
				});
			});
		</script>
	</head>

	<body>
		<div class="main">
			<div class="main_content" style="text-align:center;">
				<div><p>您无权进行此操作。</p></div>
			</div>
		</div>
	</body>
</html>