<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="granted">
	<s:hidden name="granted.id" />
	<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span class="table_title5">批准号：</span></td>
			<td class="table_td3"><s:textfield name="granted.number" cssClass="input_css" /></td>
			<td class="table_td4" width="90"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>课题类别：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="granted.subtype.id" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMap('projectType', '08')}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>批准时间：</span></td>
			<td class="table_td3">
				<input type="text" id="approveDate" name="granted.approveDate" cssClass="FloraDatepick" readonly="true" value="<s:date name='%{granted.approveDate}' format='yyyy-MM-dd' />" />
				</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5">批准经费（万）：</span></td>
			<td class="table_td3"><s:textfield name="granted.approveFee" id="approveFee" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>