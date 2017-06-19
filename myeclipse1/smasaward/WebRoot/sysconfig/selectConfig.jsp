<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:head/>
		<title>SMAS</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/main/page.css" />
	</head>

	<body>
		<div id="container">
			<div id="top">
				<span class="text_red">系统配置</span>
			</div>
			
			<div id="mid"></div>
			
			<div id="bottom">
				<s:include value="/validateError.jsp" />
				<table class="table_edit table_edit2" cellspacing="0" cellpadding="0">
					<tr>
						<td class="wd12">请选择下拉框名称</td>
						<td class="wd13">
							<s:select theme="simple" cssClass="select" list="#session.sysmain" headerKey="" headerValue="请选择"
								listKey="description" listValue="name" onchange="document.location.href='main/buildSelect.action?optionname='+this.value" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>