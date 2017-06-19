<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>添加</title>
			<s:include value="/swfupload.jsp" />
			<script type="text/javascript" src="sea.js"></script>
			<script type="text/javascript" src="sea_config.js"></script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科信息发布&nbsp;&gt;&nbsp;通知&nbsp;&gt;&nbsp;添加
			</div>

			<s:form id="form_notice" action="add" namespace="/notice/inner" theme="simple">
<%--				<s:hidden name = "flag"></s:hidden>--%>
				<s:include value="/system/notice/inner/table.jsp" />
			</s:form>
			<script type="text/javascript">
				seajs.use('javascript/system/notice/inner/edit.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	
</html>