<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="granted">
	<s:hidden name="granted.id" />
	<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span class="table_title5"><s:text name="i18n_IsGranted" />：</span></td>
			<td class="table_td3">
				<input name="defaultIsGranted" type="hidden" value="<s:property value="application1.finalAuditResult" />" />
				<s:radio name="application1.finalAuditResult" value="1" list="#{'2':getText('i18n_Yes'),'1':getText('i18n_No')}"></s:radio>
			</td>
			<td class="table_td4" width="90"></td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="2" cellpadding="2" style="display:none;">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span class="table_title5"><s:text name="i18n_ProjectNumber" />：</span></td>
			<td class="table_td3"><s:textfield name="granted.number" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_ProjectSubtype" />：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="granted.subtype.id" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{baseService.getSystemOptionMap('projectType', '02')}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_ApproveDate" />：</span></td>
			<td class="table_td3">
				<input type="text" id="approveDate" name="granted.approveDate" class="input_css" readonly="true" value="<s:date name='%{granted.approveDate}' format='yyyy-MM-dd' />" />
				</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5"><s:text name="i18n_ApproveFee" />：</span></td>
			<td class="table_td3"><s:textfield name="granted.approveFee" id="approveFee" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>