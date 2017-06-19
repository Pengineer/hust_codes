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
				系统管理&nbsp;&gt;&gt;&nbsp;系统配置&nbsp;&gt;&gt;&nbsp;<span class="text_red">邮件配置</span>
			</div>
			
			<div id="bottom" style="margin-top:10px;">
				<s:form action="configMail" id="form_sysconfig" namespace="/main" theme="simple">
					<s:include value="/validateError.jsp" />
					<table class="table_edit table_edit2" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wd12">一次发送邮件的数量</td>
							<td class="wd13"><s:textfield name="total" cssClass="input"  size="40" /></td>
							<td class=""></td>
						</tr>
						<tr>
							<td class="wd12">等待时间</td>
							<td class="wd13"><s:textfield name="waitTime" cssClass="input"  size="40" /></td>
							<td class=""></td>
						</tr>
						<tr>
							<td class="wd12">模板邮件的名称</td>
							<td class="wd13"><s:textfield name="template" cssClass="input"  size="40" /></td>
							<td class=""></td>
						</tr>
						<tr>
							<td></td>
							<td class="td_sub"><input type="submit" class ="btn" value = "确定" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn" value="取消" onclick="history.back();" /></td>
							<td></td>
						</tr>
					</table>
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
