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
			当前位置：修改
		</div>

		<s:form id="form_universityVariation" action="modify" namespace="/dm/universityVariation" theme="simple">
			<s:include value="/dm/universityVariation/table.jsp" />
		</s:form>
		
		<div class="btn_bar2">
			<input class="btn1" type="button" value="确定" onclick="submitUniversityRename();"/>
			<input class="btn1" type="button" value="取消" onclick="history.back();" />
		</div>
		<script type="text/javascript">
			seajs.use('javascript/dm/universityVariation/edit.js', function(modify) {
				$(function(){
					modify.init();
				})
			});
		</script>
	</body>
</html>