<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>修改密码</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div>
				<div style="height:120px; overflow-y:hidden; overflow-x:hidden;">
					<s:form id="form_modify_password">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr style="height:35px;">
								<td style="width:80px;text-align:right"><span class="table_title2">新密码：</span></td>
								<td><s:password name="newPassword" id="newPassword" cssClass="input_css_self" size="20" theme="simple" /></td>
								<td></td>
							</tr>
							<tr style="height:35px;">
								<td style="width:80px;text-align:right"><span class="table_title2">重复密码：</span></td>
								<td><s:password name="rePassword" cssClass="input_css_self" size="20" theme="simple" /></td>
								<td></td>
							</tr>
						</table>
						<div class="btn_div">
							<input id="modifyPassword" type="button" class="confirm btn" value="确定" />
							<input type="button" class="cancel btn" value="取消" />
						</div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/popModifyPassword.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>