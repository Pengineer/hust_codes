<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
	</head>
	<body>
		<div class="link_bar">
			当前位置：<s:text name="我的通行证" />&nbsp;&gt;&nbsp;<s:text name="绑定手机" />
		</div>
		
		<s:form id="modifyPassword" action="bindPhone" namespace="/selfspace" theme="simple">
			<div class="main">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr class="table_tr2">
								<td class="table_td2" width="100" ><span class="table_title2"><s:text name="手机号" />：</span></td>
								<td class="table_td3"><s:textfield name="bindPhone" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title2"><s:text name="原密码" />：</span></td>
								<td class="table_td3"><input class="input_css" name="password" type="password" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div> 
				<s:include value="/submit.jsp" />
			</div>
		</s:form>
	</body>
</html>