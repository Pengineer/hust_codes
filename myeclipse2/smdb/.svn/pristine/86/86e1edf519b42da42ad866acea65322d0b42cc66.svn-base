<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>修改账号</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：<s:text name="修改通行证" />
			</div>

			<s:include value="/security/passport/modifyPassport.jsp">
			</s:include>
			<script type="text/javascript">
				seajs.use('javascript/security/passport/edit.js', function(modify) {
					$(function(){
						modify.init();
					})
				});
			</script>
		</body>
	
</html>