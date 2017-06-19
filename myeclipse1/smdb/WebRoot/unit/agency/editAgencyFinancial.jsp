<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="financialInfo" style="display:none">
<s:textfield type = "hidden" name="agency.faddressIds" cssClass="input_css" />
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
			<td class="table_td2" width="100">部门地址：</td>
			<td class="table_td3" colspan="2">
				<table id="agency-faddr-table" >
					<tbody>
						<tr>
							<td colspan="5"><input id="add-agency-faddr" class="btn1" type="button" value="添加"></td>
							<td></td>
						</tr>
						<s:iterator value="financeAddress" status="stat">
						<tr>
							<td width="40">地址：</td>
							<td>
								<s:textfield name="%{'financeAddress['+#stat.index+'].address'}" value="%{financeAddress[#stat.index].address}" size = "21"  />
								<s:textfield type = "hidden" name="%{'financeAddress['+#stat.index+'].id'}" value="%{financeAddress[#stat.index].id}"  />
							</td>
							<td width="40">邮编：</td>
							<td>
								<s:textfield name="%{'financeAddress['+#stat.index+'].postCode'}" value="%{financeAddress[#stat.index].postCode}" size="6" maxlength="6"  />
							</td>
							<td width = "80">
								<label>是否默认：<input type = "checkbox" name = "" value = ""></label>
								<s:textfield type = "hidden" name="%{'financeAddress['+#stat.index+'].isDefault'}" filedValue="%{financeAddress[#stat.index].isDefault}"  />
							</td>
							<td><input class="delete_row btn1" type="button" value="删除" name=""></td><td class="comb-error"></td>
						</tr>
						</s:iterator>
						<tr id="tr_finance_addr" class = "address" style="display:none;"><td width="40">地址：</td>
						<td>
							<input name="financeAddress[].address" type="text" size = "21" />
							<input type = "hidden" name="financeAddress[].id" type="text" />
						</td>
						<td width="40">邮编：</td><td><input 	name="financeAddress[].postCode" type="text" size="6" maxlength="6" /></td>
						<td width = "80">
							<label>是否默认：<input type="checkbox" name="financeAddress[].isDefault" value = "0"/></label>
							<input type="hidden" name="financeAddress[].isDefault" class="" value = "0"/>
						</td>
						<td><input class="delete_row btn1" type="button" value="删除" name=""></td>
						<td class="comb-error"></td>
					</tr>
				</tbody>
			</table>
		</td>
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
</table>
<div class="edit_info list_edit" id="bank">
	<s:textfield type = "hidden" name="agency.bankIds" cssClass="input_css" />
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
				<s:textfield type = "hidden" name="%{'bankList['+#stat.index+'].id'}" value="%{bankList[#stat.index].id}" cssClass="input_css" /></td>
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
</div>