<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：账号管理&nbsp;&gt;&nbsp;校级管理账号&nbsp;&gt;&nbsp;子账号&nbsp;&gt;&nbsp;查看
			</div>
			
			<s:include value="/security/account/view.jsp" />
			<script type="text/javascript">
				seajs.use('javascript/security/account/university/sub/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	
</html>