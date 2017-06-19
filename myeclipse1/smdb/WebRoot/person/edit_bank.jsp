<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="edit_info list_edit" id="bank">
<s:textfield type = "hidden" name="person.bankIds" cssClass="input_css" />
<table id="table_bank" class="table_edit" width="100%" border="0" cellspacing="0" cellpadding="2">
		<thead>
			<tr>
				<th width>开户银行（包含支行的完整名称）</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="150">银联行号</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="150">银行账号</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th>银行户名</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">是否默认</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70"><input type="button" class="btn2" id="add_bank" value="添加一条" /></th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="bankList" status="stat"><!-- bankList is short for bankaccount -->
		<tr class="tr_valid" >
			<td>
			<s:textfield name="%{'bankList['+#stat.index+'].bankName'}" value="%{bankList[#stat.index].bankName}" cssClass="input_css" />
			<s:textfield type = "hidden" name="%{'bankList['+#stat.index+'].id'}" value="%{bankList[#stat.index].id}" cssClass="input_css" />
			</td>
			<td></td>
			<td><s:textfield name="%{'bankList['+#stat.index+'].bankCupNumber'}" value="%{bankList[#stat.index].bankCupNumber}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'bankList['+#stat.index+'].accountNumber'}" value="%{bankList[#stat.index].accountNumber}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'bankList['+#stat.index+'].accountName'}" value="%{bankList[#stat.index].accountName}" cssClass="input_css" /></td>
			<td></td>
			<td>
			<input type = "checkbox" name = "" value = "" class="input_css">
			<s:textfield type = "hidden" name="%{'bankList['+#stat.index+'].isDefault'}" filedValue="%{bankList[#stat.index].isDefault}" cssClass="input_css" />
			</td>
			<td></td>
			<td><input type="button" class="btn1 delete_row" value="删除" /></td>
		</tr>
		</s:iterator>
		<tr class="tr_error">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		</tbody>
	</table>
</div>

<table width="100%" border="0" cellspacing="0" cellpadding="2" style="display:none;">
	<tr class="tr_valid" id="tr_bank" style="display:none;">
		<td>
		<input type="text" name="bankList[].bankName" class=" input_css"/>
		<input type="hidden" name="bankList[].id" class=" input_css"/>
		</td>
		<td></td>
		<td><input type="text" name="bankList[].bankCupNumber" class=" input_css"/></td>
		<td></td>
		<td><s:textfield name="bankList[].accountNumber" cssClass="input_css" /></td>
		<td></td>
		<td><s:textfield name="bankList[].accountName" cssClass="input_css" /></td>
		<td></td>
		<td>
		<input type="checkbox" name="bankList[].isDefault" class="input_css" value = "0"/>
		<input type="hidden" name="bankList[].isDefault" class="input_css" value = "0"/>
		</td>
		<td></td>
		<td><input type="button" class="btn1 delete_row" value="删除" /></td>
	</tr>
</table>
