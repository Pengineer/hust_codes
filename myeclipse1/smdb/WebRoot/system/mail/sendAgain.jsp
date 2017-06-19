<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重发</title>
			<s:include value="/swfupload.jsp" />
			<script type="text/javascript" src="sea.js"></script>
			<script type="text/javascript" src="sea_config.js"></script>
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统邮件&nbsp;&gt;&nbsp;重发
			</div>

			<s:form id="form_mail" action="sendAgain" namespace="/mail" theme="simple">
				<s:include value="/system/mail/table.jsp" />
			</s:form>
			<script type="text/javascript">
				seajs.use('javascript/system/mail/edit.js', function(edit) {
					$(function(){
						edit.init();
					})
				});
			</script>
		</body>
	
</html>