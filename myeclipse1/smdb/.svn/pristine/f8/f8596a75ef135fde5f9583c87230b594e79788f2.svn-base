<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>添加</title>
			<s:include value="/innerBase.jsp" />
			<link href="tool/tree/css/dhtmlxtree.css" rel="stylesheet" type="text/css" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统角色&nbsp;&gt;&nbsp;添加
			</div>

			<s:form id="form_role" action="add" namespace="/role" theme="simple">
				<s:include value="/security/role/table.jsp" />
			</s:form>
			<script type="text/javascript">
				seajs.use('javascript/security/role/edit.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	
</html>