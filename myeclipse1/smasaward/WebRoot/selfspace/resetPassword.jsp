<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<s:head/>
			<title>找回密码</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/user/register.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div class="container">
				<div class="head">
					<div class="logo"></div>
					<div class="menu">
						<a href="main/toIndex.action">首页</a>
					</div>
				</div>
				<div class="main">
					<s:include value="/validateError.jsp" />
					<div class="center">
						<s:form action="resetPassword" id="form_user_resetPassword" namespace="/selfspace"
							theme="simple">
							<table class="table_center">
								<tr>
									<td class="tdpassname">新密码</td>
									<td class="tdpassvalue"><s:password name="password" cssClass="input" /></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="tdpassname">重复密码</td>
									<td class="tdpassvalue"><s:password name="repassword" cssClass="input" /></td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<br/>
							<s:include value="/submit.jsp" />
						</s:form>
					</div>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/user_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"en_US\")">
				<script type="text/javascript" src="javascript/user/user_validate_en.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
		</body>
</html>