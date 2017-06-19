<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>添加</title>
			<s:include value="/outerBase.jsp" />
			<script type="text/javascript" src="sea.js"></script>
			<script type="text/javascript" src="sea_config.js"></script>
			
		</head>

		<body style="background:#FFFFFF">
			<div style="width:700px;">
				<s:form id="form_message" action="add" namespace="/message/outer" theme="simple">
					<s:include value="/system/message/outer/table.jsp" />
				</s:form>
				<div class="btn_div_view">
					<input id="confirm" class="btn1" type="button" value="确定" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/message/outer/add.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	
</html>