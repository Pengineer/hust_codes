<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>账号信息</title>
			<s:include value="/innerBase.jsp" />
		</head>
	
		<body>
			<div class="link_bar">
				当前位置：我的通行证
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="title_bar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td1" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td1" width="64" align="right">用户名：</td>
								<td class="title_bar_td1" width="" colspan="7">
								<s:property value="#session.loginer.passport.name" />
								<sec:authorize ifAllGranted="ROLE_BASE_ACCOUNT_SELF_MODIFY">
									<span id="view_mod">[<a href="" title="点击修改通行证">修改通行证</a>]</span>
									<span id="view_bind_email">[<a href="" title="点击关联邮箱">绑定邮箱</a>]</span>
									<span id="view_bind_phone">[<a href="" title="点击关联手机号">绑定手机</a>]</span>
								</sec:authorize>
								</td>
							</tr>
						</table>
					</div>
					
					<div class="main_content">
						<div class="title_bar">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<s:iterator value="accounts" status="stat">
<%--									<s:if test="#stat.index == 0">--%>
<%--										<tr>--%>
<%--											<td class="title_bar_td1" align="right" colspan="2">账号<s:property value="#stat.index + 1" />：</td>--%>
<%--											<td class="title_bar_td1" colspan="7"></td>--%>
<%--										</tr>--%>
<%--									</s:if>--%>
<%--									<s:else>--%>
<%--										<tr>--%>
<%--											<td class="title_bar_td1 dashedTop" align="right" colspan="2">账号<s:property value="#stat.index + 1" />：</td>--%>
<%--											<td class="title_bar_td1 dashedTop" colspan="7"></td>--%>
<%--										</tr>--%>
<%--									</s:else>--%>
									<tr>
										<td class="title_bar_td1" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
										<td class="title_bar_td1" width="64" align="right">账号类型：</td>
										<td class="title_bar_td1">
											<s:if test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR ) == 0">系统管理员账号</s:if>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@MINISTRY ) == 0">部级账号</s:elseif>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@PROVINCE ) == 0">省级账号</s:elseif>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY) == 0">部属高校账号</s:elseif>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@LOCAL_UNIVERSITY ) == 0">地方高校账号</s:elseif>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@DEPARTMENT) == 0">院系账号</s:elseif>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@INSTITUTE) == 0">基地账号</s:elseif>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@EXPERT ) == 0">专家账号</s:elseif>
											<s:elseif test="accounts[#stat.index][0].compareWith(@csdc.tool.bean.AccountType@TEACHER ) == 0">教师账号</s:elseif>
											<s:else>未知类型账号</s:else>
										</td>
										<td class="title_bar_td1" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
										<td class="title_bar_td1" align="right">有效期限：</td>
										<td class="title_bar_td1"><s:date name="accounts[#stat.index][2]" format="yyyy-MM-dd" /></td>
										<td class="title_bar_td1" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
										<td class="title_bar_td1" align="right">项目状态：</td>
										<td class="title_bar_td1">
											<s:if test="accounts[#stat.index][1] == 1">启用</s:if>
											<s:else>停用</s:else>
										</td>
									</tr>
								</s:iterator>
							</table>
						</div>
					</div>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#view_mod").bind("click", function() {
					window.location.href = basePath + "selfspace/toModifySelfAccount.action";
					return false;
				});
				$("#view_bind_email").bind("click", function() {
					window.location.href = basePath + "selfspace/toBindEmail.action";
					return false;
				});
				$("#view_bind_phone").bind("click", function() {
					window.location.href = basePath + "selfspace/toBindPhone.action";
					return false;
				});
			});
		</script>
	
</html>