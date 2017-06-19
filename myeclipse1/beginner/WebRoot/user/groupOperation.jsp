<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_User">
		<head>
			<s:head/>
			<base href="<%=basePath%>" />
			<title><s:text name="i18n_AccountSet" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
			<link rel="stylesheet" type="text/css" href="css/user/user.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<s:text name="i18n_UserManagement" />&nbsp;&gt;&gt;
								<s:if test="userstatus == -1">
									<s:text name="i18n_InactiveUser" />&nbsp;&gt;&gt;
								</s:if>
								<s:elseif test="userstatus == 0">
									<s:text name="i18n_UnapprovedUser" />&nbsp;&gt;&gt;
								</s:elseif>
								<span class="text_red"><s:text name="i18n_AccountSet" /></span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="groupOperation" id="form_user_label" namespace="/user"
						theme="simple">
						<div id="inputtip">
							<s:property value="#request.tip" />
							<s:fielderror />
							<s:actionerror />
						</div>
						<input type="hidden" name="userstatus" value="${userstatus}" />
						<table class="table_basic" cellspacing="0" cellpadding="0">
							<tr>
								<td class="wd23">
									<s:text name="i18n_AccountValidityTo" />
								</td>
								<td class="wd24">
									<s:textfield id="datepick" name="validity" value="" cssClass="input" />
								</td>
							</tr>
							<s:if test="userstatus == 0">
								<tr>
									<td class="wd24" colspan="2">
										<s:optiontransferselect name="norolesid"
											buttonCssClass="optbutton"
											leftTitle="%{getText('i18n_NotDistributeRole')}"
											rightTitle="%{getText('i18n_HasDistributedRole')}"
											allowSelectAll="false" allowUpDownOnLeft="false"
											allowUpDownOnRight="false" list="#session.noroles" listKey="id"
											listValue="name" doubleList="" doubleListKey=""
											doubleListValue="" doubleName="rolesid" cssClass="optelement"
											doubleCssClass="optelement">
										</s:optiontransferselect>
									</td>
								</tr>
							</s:if>
						</table>
						<table class="table_sub">
							<tr>
								<td><input type="submit" class ="input" value = "<s:text name="i18n_Ok" />" /></td>
								<td><input type="button" class="input" value="<s:text name="i18n_Cancel" />" onclick="history.back();" /></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
		</body>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.js"></script>
		<s:if test="#session.locale.equals(\"zh_CN\")">
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_CN.js"></script>
		</s:if>
		<script type="text/javascript" src="javascript/jquery/datepick.mine.js"></script>
		<script type="text/javascript" src="javascript/user/userValidate.js"></script>
		<s:if test="#session.locale.equals(\"en_US\")">
			<script type="text/javascript" src="javascript/user/userValidate_en.js"></script>
		</s:if>
	</s:i18n>
</html>