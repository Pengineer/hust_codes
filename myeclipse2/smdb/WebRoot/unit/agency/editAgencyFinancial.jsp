<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="financialInfo" style="display:none">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="120"><span class="">部门名称：</span></td>
			<td class="table_td3"><s:textfield name="agency.fname" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">部门负责人：</td>
			<td class="table_td3"><s:textfield name="agency.fdirector" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">部门联系人：</td>
			<td class="table_td3"><s:textfield name="agency.flinkman" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">部门地址：</td>
			<td class="table_td3"><s:textfield name="agency.faddress" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">部门邮编：</td>
			<td class="table_td3"><s:textfield name="agency.fpostcode" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="">电话：</span></td>
			<td class="table_td3"><s:textfield name="agency.fphone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">传真：</td>
			<td class="table_td3"><s:textfield name="agency.ffax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">邮箱：</td>
			<td class="table_td3"><s:textfield name="agency.femail" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">开户银行：</td>
			<td class="table_td3"><s:select cssClass="select" name="agency.fbank" value="%{agency.fbank}" headerKey="" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('bank', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银行支行：</td>
			<td class="table_td3"><s:textfield name="agency.fbankBranch" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银联行号：</td>
			<td class="table_td3"><s:textfield name="agency.fcupNumber" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银行户名：</td>
			<td class="table_td3"><s:textfield name="agency.fbankAccountName" cssClass="input_css" /><span class="tip">请谨慎填写！</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">银行账号：</td>
			<td class="table_td3"><s:textfield name="agency.fbankAccount" cssClass="input_css" /><span class="tip">请谨慎填写！</span></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
