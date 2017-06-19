<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="identifier1">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="100"><span class="table_title2">中文名：</span></td>
			<td class="table_td3"><s:textfield name="person.name" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="">证件类型：</span></td>
			<td class="table_td3"><s:select cssClass="select" headerKey="" headerValue="--%{getText('请选择')}--" list="%{baseService.getSystemOptionMapAsName('idcardType', null)}" name="person.idcardType" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="">证件号：</span></td>
			<td class="table_td3"><s:textfield name="person.idcardNumber" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
