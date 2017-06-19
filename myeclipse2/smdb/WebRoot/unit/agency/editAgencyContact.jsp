<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="contactInfo">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="120">通信地址：</td>
			<td class="table_td3"><s:textfield name="agency.address" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">邮政编码：</td>
			<td class="table_td3"><s:textfield name="agency.postcode" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">电话：</td>
			<td class="table_td3"><s:textfield name="agency.phone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">传真：</td>
			<td class="table_td3"><s:textfield name="agency.fax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">邮箱：</td>
			<td class="table_td3"><s:textfield name="agency.email" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">主页：</td>
			<td class="table_td3"><s:textfield name="agency.homepage" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
