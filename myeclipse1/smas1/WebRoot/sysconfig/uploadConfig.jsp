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
				<s:form action="configUpload" id="form_sysconfig" namespace="/main" theme="simple">
					<s:include value="/validateError.jsp" />
					<table class="table_edit table_edit2" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wd12">照片上传路径</td>
							<td class="wd13"><s:textfield name="UserPictureUploadPath" value="%{#application.UserPictureUploadPath}" cssClass="input" size="40" /></td>
						</tr>
					</table>
					<s:include value="/submit.jsp" />
				</s:form>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/sysconfig/sysconfig_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/sysconfig/sysconfig_validate_en.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</s:if>
	</body>
</html>