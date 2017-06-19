<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>输入标题</title>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:5px;">
			<tr>
				<td><span>请确定统计标题：</span></td>
			</tr>
		</table>
		<s:include value="/validateError.jsp" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="30px" width="100%">
				<td><s:textfield id="toCommonTitle" name="title" style="width:100%;height:20px;border:1px solid #aaaaaa;"></s:textfield></td>
			</tr>
		</table>
			
		<s:hidden name="statisticType" id="statisticType" value="%{#session.statisticType}"/>
		<div class="btn_div_view">
			<ul>
				<li><input id="toCommonSubmit" class="btn1" type="button" value="确定" /></li>
			</ul>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/statistic/titleCreate.js', function(titleCreate) {
				titleCreate.init();
			});
		</script>
	</body>
</html>