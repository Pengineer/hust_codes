<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>修改账号</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：我的通行证&nbsp;&gt;&nbsp;修改通行证
			</div>

			<s:form id="modify_account" action="modifySelfAccount" namespace="/selfspace" theme="simple">
				<div class="main">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr class="table_tr2">
								<td class="table_td2" width="100">账号类型：</td>
								<td class="table_td3">
									<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">系统管理员账号</s:if>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@MINISTRY ) == 0">部级账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@PROVINCE ) == 0">省级账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY) == 0">部属高校账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@LOCAL_UNIVERSITY ) == 0">地方高校账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@DEPARTMENT) == 0">院系账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@INSTITUTE) == 0">基地账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@EXPERT ) == 0">专家账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@TEACHER ) == 0">教师账号</s:elseif>
									<s:elseif test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@STUDENT ) == 0">未知类型账号</s:elseif>
									<s:else>未知类型账号</s:else>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title2">用户名：</span></td>
								<td class="table_td3"><s:textfield name="accountname" value="%{#session.loginer.passport.name}" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title2">原密码：</span></td>
								<td class="table_td3"><s:password name="origpassword" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title2">新密码：</span></td>
								<td class="table_td3"><s:password name="password" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title2">重复密码：</span></td>
								<td class="table_td3"><s:password name="repassword" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div> 
					<s:include value="/submit.jsp" />
				</div>
			</s:form>
		</body>
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/lib/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/selfspace/validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#submit").bind("click", function() {
					$("#modify_account").submit();
				});
			});
		</script>
	
</html>