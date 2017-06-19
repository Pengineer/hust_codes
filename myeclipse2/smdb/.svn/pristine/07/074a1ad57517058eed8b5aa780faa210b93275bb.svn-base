<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="bank">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="100">开户银行：</td>
			<td class="table_td3"><s:select cssClass="select" name="person.bankName" value="%{person.bankName}" headerKey="" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('bank', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银行支行：</td>
			<td class="table_td3"><s:textfield name="person.bankBranch" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银联行号：</td>
			<td class="table_td3"><s:textfield name="person.cupNumber" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银行户名：</td>
			<td class="table_td3"><s:textfield name="person.bankAccountName" cssClass="input_css" /><span class="tip">请谨慎填写！</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银行账号：</td>
			<td class="table_td3"><s:textfield name="person.bankAccount" cssClass="input_css" /><span class="tip">请谨慎填写！</span></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
