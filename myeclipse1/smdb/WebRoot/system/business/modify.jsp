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
				当前位置：社科业务日程&nbsp;&gt;&nbsp;修改
			</div>

			<s:form id="form_business" action="modify" namespace="/business" theme="simple">
				<s:include value="/system/business/table.jsp" />
			</s:form>
			<script type="text/javascript">
				seajs.use(['javascript/system/business/edit.js','javascript/system/business/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
</html>