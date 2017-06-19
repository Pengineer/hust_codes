<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<s:head/>
			<title>角色分配</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/roleright/roleright.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								系统管理&nbsp;&gt;&gt;
								角色权限&nbsp;&gt;&gt;
								<span class="text_red">
									给
									<s:property value="userName" />
									分配角色
								</span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div id="bottom">
					<s:include value="/validateError.jsp" />
					<s:form action="userRole" namespace="/role" theme="simple">
						<input type="hidden" name="userId" value="${userId}" />
						<input type="hidden" name="userName" value="${userName}" />
						<input type="hidden" name="userstatus" value="${userstatus}" />
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="2" >
									<s:optiontransferselect  name="norolesid"
										buttonCssClass="optbutton"
										leftTitle="%{'未分配角色'}"
										rightTitle="%{'已分配角色'}"
										allowSelectAll="false" allowUpDownOnLeft="false"
										allowUpDownOnRight="false" list="#session.get('noroles')" listKey="id"
										listValue="name" doubleList="#session.get('roles')" doubleListKey="id"
										doubleListValue="name" doubleName="roleIds"
										cssClass="optelement" doubleCssClass="optelement">
									</s:optiontransferselect>
								</td>
							</tr>
							<tr>
								<td class="wd_center">
									<input type="submit" value="确定" class="btn" />
								</td>
								<td class="wd_center">
									<input type="button" value="取消" onclick="history.go(-1)" class="btn" />
								</td>
							</tr>
						</table>
					</s:form>
				</div>
			</div>
		</body>
</html>