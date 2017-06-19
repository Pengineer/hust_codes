<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="110"><span class="table_title3">权限名称：</span></td>
				<td class="table_td3"><s:textfield name="right.name" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title3">权限代码：</span></td>
				<td class="table_td3"><s:textfield name="right.code" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title3">权限描述：</span></td>
				<td class="table_td3"><s:textarea name="right.description" rows="6" cssClass="textarea_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title3">权限节点值：</span></td>
				<td class="table_td3"><s:textfield name="right.nodevalue" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
		</table>
	</div> 
	<s:include value="/submit.jsp" />
</div>