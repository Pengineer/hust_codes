<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
		<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/lib/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#binEmail").live("click", function() {
					if (confirm('您确定要给本账号绑定邮箱吗？ 绑定成功后，请注意查收邮件通过指定的链接确认！')) {
						$("#modifyPassword").submit();
					}
				});
			});
		</script>
	</head>
	<body>
		<div class="link_bar">
			当前位置：<s:text name="我的通行证" />&nbsp;&gt;&nbsp;<s:text name="绑定邮箱" />
		</div>
		
		<s:form id="modifyPassword" action="bindEmail" namespace="/selfspace" theme="simple">
			<div class="main">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr class="table_tr2">
								<td class="table_td2" width="100" ><span class="table_title2"><s:text name="邮箱" />：</span></td>
								<td class="table_td3"><s:textfield name="bindEmail" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title2"><s:text name="原密码" />：</span></td>
								<td class="table_td3"><input class="input_css" name="password" type="password" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div> 
			</div>
		</s:form>
		<div class="btn_bar2">
			<input id="binEmail" class="btn1" type="button" value="确定" />
			<input id="cancel" class="btn1" type="button" value="取消" onclick="history.back();" />
		</div>
	</body>
</html>