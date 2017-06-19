<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<s:head/>
			<title>修改密码</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/selfspace/selfspace.css" />
			<link rel="stylesheet" type="text/css" href="css/selfspace/validate.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					个人空间&nbsp;&gt;&gt;&nbsp;<span class="text_red">修改密码</span>
				</div>
				
				<div id="bottom">
					<s:form action="modifyPassword" id="form_selfspace" namespace="/selfspace" theme="simple">
						<s:include value="/validateError.jsp" />
						<table class="table_edit table_edit1" cellspacing="0" cellpadding="0">
							<tr>
								<td class="tdpassname"><span class="table_title" style="padding-left:0;">旧密码</span></td>
								<td class="tdpassvalue"><s:password name="opassword" cssClass="input" /></td>
							</tr>
							<tr>
								<td class="tdpassname"><span class="table_title" style="padding-left:0;">新密码</span></td>
								<td class="tdpassvalue"><s:password name="password" cssClass="input" /></td>
							</tr>
							<tr>
								<td class="tdpassname"><span class="table_title" style="padding-left:0;">重复密码</span></td>
								<td class="tdpassvalue"><s:password name="repassword" cssClass="input" /></td>
							</tr>
							<tr>
								<td></td>
								<td class="td_sub"><input type="submit" class ="btn" value = "确定" /><input type="button" class="btn" value="取消" onclick="history.back();" /></td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/selfspace/selfspace_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"en_US\")">
				<script type="text/javascript" src="javascript/selfspace/selfspace_validate_en.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
		</body>
</html>