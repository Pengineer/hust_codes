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
				当前位置：社科信息发布&nbsp;&gt;&nbsp;新闻&nbsp;&gt;&nbsp;添加
			</div>

			<s:form id="form_news" action="add" namespace="/news/inner" theme="simple">
				<s:hidden name = "flag"></s:hidden>
				<s:include value="/system/news/inner/table.jsp" />
			</s:form>
			<script type="text/javascript">
				seajs.use('javascript/system/news/inner/edit.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>

</html>