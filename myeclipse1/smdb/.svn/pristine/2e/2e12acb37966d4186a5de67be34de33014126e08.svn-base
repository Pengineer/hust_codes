<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="contactInfo">
<s:textfield type = "hidden" name="agency.addressIds" cssClass="input_css" />
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
		<td class="table_td2" width="100">部门地址：</td>
		<td class="table_td3" colspan="2">
			<table id="agency-caddr-table" >
				<tbody>
					<tr>
						<td colspan="5"><input id="add-agency-caddr" class="btn1" type="button" value="添加"></td>
						<td></td>
					</tr>
					<s:iterator value="commonAddress" status="stat">
						<tr>
						<td width="40">地址：</td>
						<td>
							<s:textfield name="%{'commonAddress['+#stat.index+'].address'}" value="%{commonAddress[#stat.index].address}" size = "21"  />
							<s:textfield type = "hidden" name="%{'commonAddress['+#stat.index+'].id'}" value="%{commonAddress[#stat.index].id}"  />
						</td>
						<td width="40">邮编：</td>
						<td>
							<s:textfield name="%{'commonAddress['+#stat.index+'].postCode'}" value="%{commonAddress[#stat.index].postCode}" size="6" maxlength="6"  />
						</td>
						<td width = "80">
							<label>是否默认：<input type = "checkbox" name = "" value = ""></label>
							<s:textfield type = "hidden" name="%{'commonAddress['+#stat.index+'].isDefault'}" filedValue="%{commonAddress[#stat.index].isDefault}"  />
						</td>
						<td><input class="delete_row btn1" type="button" value="删除" name=""></td><td class="comb-error"></td>
					</tr>
					</s:iterator>
					<tr id="tr_common_addr" class = "address" style="display:none;"><td width="40">地址：</td>
					<td>
						<input name="commonAddress[].address" type="text" size = "21" />
						<input type = "hidden" name="commonAddress[].id" type="text" />
					</td>
					<td width="40">邮编：</td><td><input 	name="commonAddress[].postCode" type="text" size="6" maxlength="6" /></td>
					<td width = "80">
						<label>是否默认：<input type="checkbox" name="commonAddress[].isDefault" value = "0"/></label>
						<input type="hidden" name="commonAddress[].isDefault" class="" value = "0"/>
					</td>
					<td><input class="delete_row btn1" type="button" value="删除" name=""></td>
					<td class="comb-error"></td>
				</tr>
			</tbody>
		</table>
	</td>
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
