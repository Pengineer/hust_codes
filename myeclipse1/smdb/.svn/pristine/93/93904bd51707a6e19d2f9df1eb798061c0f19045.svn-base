<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="contact">
<s:textfield type = "hidden" name="person.homeAddressIds" cssClass="input_css" />
<s:textfield type = "hidden" name="person.officeAddressIds" cssClass="input_css" />
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		 <tr class="table_tr2">
			<td class="table_td2" width="100">住宅地址：</td>
			<td class="table_td3" colspan="2">
				<table id="home-addr-table" >
				<tbody>
					<tr >
						<td colspan="5">
						<input id="add-home-addr" class="btn1" type="button" value="添加">
						
						</td>
						<td></td>
					</tr>
					<s:iterator value="homeAddress" status="stat">
					<tr>
						<td width="40">地址：</td>
						<td>
							<s:textfield name="%{'homeAddress['+#stat.index+'].address'}" value="%{homeAddress[#stat.index].address}" size = "21"  />
							<s:textfield type = "hidden" name="%{'homeAddress['+#stat.index+'].id'}" value="%{homeAddress[#stat.index].id}"  />
						</td>
						<td width="40">邮编：</td>
						<td>
							<s:textfield name="%{'homeAddress['+#stat.index+'].postCode'}" value="%{homeAddress[#stat.index].postCode}" size="6" maxlength="6"  />
						</td>
						<td width = "80"> 
							<label>是否默认：<input type = "checkbox" name = "" value = ""></label>
							<s:textfield type = "hidden" name="%{'homeAddress['+#stat.index+'].isDefault'}" filedValue="%{homeAddress[#stat.index].isDefault}"  />
						</td>
						<td><input class="delete_row btn1" type="button" value="删除" name=""></td><td class="comb-error"></td>
					</tr>
					</s:iterator>
					<tr id="tr_home_addr" class = "address" style="display:none;"><td width="40">地址：</td>
						<td >
							<input name="homeAddress[].address" type="text" size = "21"/>
							<input type = "hidden" name="homeAddress[].id" type="text" />
						</td>
						<td width="40">邮编：</td><td><input 	name="homeAddress[].postCode" type="text" size="6" maxlength="6" /></td>
						<td width = "80"> 
						<label>是否默认：<input type="checkbox" name="homeAddress[].isDefault" value = "0"/></label>
						<input type="hidden" name="homeAddress[].isDefault" class="" value = "0"/>
						</td>
						<td><input class="delete_row btn1" type="button" value="删除" name=""></td>
						<td class="comb-error"></td>
					</tr>
				</tbody>
				</table>
			</td>
			
			<!-- <td class="table_td4"></td> -->
		</tr>  

		<tr class="table_tr2">
			<td class="table_td2">住宅电话：</td>
			<td class="table_td3"><s:textfield name="person.homePhone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
			<td class="table_td4"></td>
		</tr>
		
		<tr class="table_tr2">
			<td class="table_td2" width="100">办公地址：</td>
			<td class="table_td3" colspan="2">
			<table id="office-addr-table" >
			<tbody>
				<tr >
					<td colspan="5">
					<input id="add-office-addr" class="btn1" type="button" value="添加">
					
					</td>
					<td></td>
				</tr>
				<s:iterator value="officeAddress" status="stat">
				<tr>
					<td width="40">地址：</td>
					<td >
						<s:textfield name="%{'officeAddress['+#stat.index+'].address'}" value="%{officeAddress[#stat.index].address}" size = "21" />
						<s:textfield type = "hidden" name="%{'officeAddress['+#stat.index+'].id'}" value="%{officeAddress[#stat.index].id}"  />
					</td>
					<td width="40">邮编：</td>
					<td><s:textfield name="%{'officeAddress['+#stat.index+'].postCode'}" value="%{officeAddress[#stat.index].postCode}" size="6" maxlength="6" /></td>
					<td width = "80"> 
					<label>是否默认：<input type = "checkbox" name = "" value = "" ></label>
					<s:textfield type = "hidden" name="%{'officeAddress['+#stat.index+'].isDefault'}" filedValue="%{officeAddress[#stat.index].isDefault}"  />
					</td>
					<td><input class="delete_row btn1" type="button" value="删除" name=""></td><td class="comb-error"></td>
				</tr>
				</s:iterator>
				<tr id="tr_office_addr"  style="display:none;">
					<td width="40">地址：</td>
					<td>
						<input name="officeAddress[].address" type="text" size = "21"/>
						<input type = "hidden" name="officeAddress[].id" type="text" />
					</td>
					<td width="40">邮编：</td><td><input 	name="officeAddress[].postCode" type="text" size="6" maxlength="6" /></td>
					<td width = "80"> 
					<label>是否默认：<input type="checkbox" name="officeAddress[].isDefault" class="" value = "0"/></label>
					<input type="hidden" name="officeAddress[].isDefault" class="" value = "0"/>
					</td>
					<td><input class="delete_row btn1" type="button" value="删除" name=""></td><td class="comb-error"></td>
				</tr>
			</tbody>
			</table>
			
		</td>
		
		<!-- <tr class="table_tr2">
			<td class="table_td2">办公地址：</td>
			<td class="table_td3"><s:textfield name="person.officeAddress" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">办公邮编：</td>
			<td class="table_td3"><s:textfield name="person.officePostcode" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr> -->
		<tr class="table_tr2">
			<td class="table_td2">办公电话：</td>
			<td class="table_td3"><s:textfield name="person.officePhone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">办公传真：</td>
			<td class="table_td3"><s:textfield name="person.officeFax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">手机：</td>
			<td class="table_td3"><s:textfield name="person.mobilePhone" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title2">邮箱：</span></td>
			<td class="table_td3"><s:textfield name="person.email" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">QQ：</td>
			<td class="table_td3"><s:textfield name="person.qq" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">MSN：</td>
			<td class="table_td3"><s:textfield name="person.msn" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">个人主页：</td>
			<td class="table_td3"><s:textfield name="person.homepage" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
