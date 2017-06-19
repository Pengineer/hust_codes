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
			当前位置：社科机构数据&nbsp;&gt;&nbsp;社科管理机构&nbsp;&gt;&nbsp;省级管理机构&nbsp;&gt;&nbsp;添加
		</div>
		
		<s:hidden name="nameSpace" id="nameSpace" value="province" />
		<s:form id="form_agency" action="add" namespace="/unit/agency/province" theme="simple">
			<s:include value="/unit/agency/add.jsp">
				<s:param name="type" value="省级" />
			</s:include>
		</s:form>
		<script type="text/javascript">
			seajs.use('javascript/unit/agency/add.js', function(add) {
				$(function(){
					add.init();
				})
			});
		</script>
	</body>
</html>