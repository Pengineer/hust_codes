<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<table class="table_edit" cellspacing="0" cellpadding="0">
			<tr><th class="thhead" colspan="2">基本信息</th></tr>
			<tr>
				<td class="wd12">角色名称</td>
				<td class="wd13"><s:textfield name="role.name" value="%{role.name}" cssClass="input" size="40" /></td>
			</tr>
			<tr>
				<td class="wd12">角色描述</td>
				<td class="wd13"><s:textfield name="role.description" value="%{role.description}" cssClass="input" size="40" /></td>
			</tr>
			<tr><th class="thhead" colspan="2">查看权限</th></tr>
			<tr>
				<td colspan="2" align="center">
					<s:optiontransferselect name="norightsid"
						buttonCssClass="optbutton"
						leftTitle="%{'未分配权限'}"
						rightTitle="%{'已分配权限'}"
						allowSelectAll="false" allowUpDownOnLeft="false" allowUpDownOnRight="false" 
						list="#session.norights" listKey="id" listValue="name" 
						doubleList="#session.rights" doubleListKey="id" doubleListValue="name" 
						doubleName="rightIds"
						cssClass="optelement" doubleCssClass="optelement">
					</s:optiontransferselect>
				</td>
			</tr>
		</table>
	</div>
	<s:include value="/submit.jsp" />
</div>