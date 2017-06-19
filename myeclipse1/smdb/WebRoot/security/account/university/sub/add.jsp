<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>添加</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：账号管理&nbsp;&gt;&nbsp;校级管理账号&nbsp;&gt;&nbsp;子账号&nbsp;&gt;&nbsp添加
			</div>

			<s:include value="/security/account/add.jsp">
				<s:param name="type">校级子账号</s:param>
			</s:include>
			<script type="text/javascript">
				seajs.use('javascript/security/account/university/sub/edit.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	
</html>