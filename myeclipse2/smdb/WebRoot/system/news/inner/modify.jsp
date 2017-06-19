<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>修改</title>
			<s:include value="/swfupload.jsp" />
			<script type="text/javascript" src="sea.js"></script>
			<script type="text/javascript" src="sea_config.js"></script>
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科信息发布&nbsp;&gt;&nbsp;新闻&nbsp;&gt;&nbsp;修改
			</div>

			<s:form id="form_news" action="modify" namespace="/news/inner" theme="simple">
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