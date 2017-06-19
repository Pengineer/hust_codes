<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>修改</title>
			<s:include value="/swfupload.jsp" />
			<script type="text/javascript" src="javascript/lib/jquery/jquery.js"></script>
			<script type="text/javascript" src="tool/jquery.datepick/jquery.datepick.js"></script>
			<script type="text/javascript" src="tool/jquery.datepick/jquery.datepick-zh-CN.js"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.datepick.self.js"></script>
			<script type="text/javascript" src="javascript/common.js"></script>
			<script type="text/javascript" src="javascript/lib/jquery/jquery.validate.js"></script> 
			<script type="text/javascript" src="javascript/selfspace/memo/validate.js"></script>
			<script type="text/javascript" src="javascript/selfspace/memo/edit.js"></script>
		</head>

		<body>
			<div class="link_bar">
				当前位置:我的备忘录&nbsp;&gt;&nbsp;修改
			</div>

			<s:form id="form_memo" action="modify" namespace="/selfspace/memo" theme="simple">
				<s:include value="/selfspace/memo/table.jsp" />
			</s:form>
		</body>
	
</html>
