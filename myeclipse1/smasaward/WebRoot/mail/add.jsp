<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<title>添加邮件</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/mail/mail.css" />
	</head>
  
  <body>
    <div id="top">
		系统管理&nbsp;&gt;&gt;&nbsp;邮件管理&nbsp;&gt;&gt;&nbsp;<span class="text_red">添加邮件</span>
	</div>
	
	<div class="main" style="margin-top:10px;">
		<s:form id="form_mail" action="add" namespace="/mail" theme="simple">
			<div class="main">
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td class="wd14">模板邮件的名称：</td>
							<td class="wd15"><s:textfield name="mail.template" /></td>
						</tr>
						<tr>
							<td class="wd14">邮件正文：</td>
							<td class="wd15" colspan="2">
								<FCK:editor instanceName="mail.body" basePath="/tool/fckeditor" width="100%" height="450" toolbarSet="Default"></FCK:editor>
							</td>
						</tr>
					</table>
				</div>
				<s:include value="/submit.jsp" />
			</div>
		</s:form>
	</div>
	<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<%--		<script type="text/javascript" src="javascript/mail/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>--%>
  </body>
</html>
