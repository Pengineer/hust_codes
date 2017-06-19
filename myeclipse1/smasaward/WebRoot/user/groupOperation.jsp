<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<s:head/>
			<title>账号设置</title>
			<s:include value="/innerBase.jsp" />
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
								用户管理&nbsp;&gt;&gt;
								<s:if test="userstatus == -1">
									未启用用户&nbsp;&gt;&gt;
								</s:if>
								<s:elseif test="userstatus == 0">
									未审批用户&nbsp;&gt;&gt;
								</s:elseif>
								<span class="text_red">账号设置</span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:form action="groupOperation" id="form_user_label" namespace="/user"
						theme="simple">
						<s:include value="/validateError.jsp" />
						<input type="hidden" name="userstatus" value="${userstatus}" />
						<table class="table_basic" cellspacing="0" cellpadding="0">
							<tr>
								<td class="wd23">
									账号有效期至
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
											leftTitle="%{'未分配角色'}"
											rightTitle="%{'已分配角色'}"
											allowSelectAll="false" allowUpDownOnLeft="false" allowUpDownOnRight="false"
											list="#session.noroles" listKey="id" listValue="name" 
											doubleList="#session.roles" doubleListKey="id" doubleListValue="name" 
											doubleName="rolesid" 
											cssClass="optelement" doubleCssClass="optelement">
										</s:optiontransferselect>
									</td>
								</tr>
							</s:if>
						</table>
						<s:include value="/submit.jsp" />
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"zh_CN\")">
				<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/user_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"en_US\")">
				<script type="text/javascript" src="javascript/user/user_validate_en.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
		</body>
</html>