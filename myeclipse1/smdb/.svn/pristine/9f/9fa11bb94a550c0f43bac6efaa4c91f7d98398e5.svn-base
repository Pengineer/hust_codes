<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>修改</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div class="link_bar">
				当前位置：账号管理&nbsp;&gt;&nbsp;院系管理账号&nbsp;&gt;&nbsp;子账号&nbsp;&gt;&nbsp;修改
			</div>

			<s:include value="/security/account/modify.jsp">
				<s:param name="type">院系子账号</s:param>
			</s:include>
			<script type="text/javascript">
				seajs.use('javascript/security/account/department/sub/edit.js', function(modify) {
					$(function(){
						modify.init();
					})
				});
			</script>
		</body>
	
</html>