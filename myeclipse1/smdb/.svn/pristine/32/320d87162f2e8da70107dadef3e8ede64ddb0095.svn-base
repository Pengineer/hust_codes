<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="managementInfo" style="display:none">
<s:textfield type = "hidden" name="agency.saddressIds" cssClass="input_css" />
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="120"><span class="">部门名称：</span></td>
			<td class="table_td3"><s:textfield name="agency.sname" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">部门负责人：</td>
			<td class="table_td3">
				<input type="button" id="select_sDirector_btn" class="btn1 select_btn" value="选择"/>
				<div id="sDirectorName" class="choose_show"><s:property value="agency.sdirector.name"/></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">部门联系人：</td>
			<td class="table_td3">
				<input type="button" id="select_sLinkman_btn" class="btn1 select_btn" value="选择"/>
				<div id="sLinkmanName" class="choose_show"><s:property value="agency.slinkman.name"/></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2" width="100">部门地址：</td>
			<td class="table_td3" colspan="2">
				<table id="agency-saddr-table" >
					<tbody>
						<tr>
							<td colspan="5"><input id="add-agency-saddr" class="btn1" type="button" value="添加"></td>
							<td></td>
						</tr>
						<s:iterator value="subjectionAddress" status="stat">
						<tr>
							<td width="40">地址：</td>
							<td>
								<s:textfield name="%{'subjectionAddress['+#stat.index+'].address'}" value="%{subjectionAddress[#stat.index].address}" size = "21"  />
								<s:textfield type = "hidden" name="%{'subjectionAddress['+#stat.index+'].id'}" value="%{subjectionAddress[#stat.index].id}"  />
							</td>
							<td width="40">邮编：</td>
							<td>
								<s:textfield name="%{'subjectionAddress['+#stat.index+'].postCode'}" value="%{subjectionAddress[#stat.index].postCode}" size="6" maxlength="6"  />
							</td>
							<td width = "80">
								<label>是否默认：<input type = "checkbox" name = "" value = ""></label>
								<s:textfield type = "hidden" name="%{'subjectionAddress['+#stat.index+'].isDefault'}" filedValue="%{subjectionAddress[#stat.index].isDefault}"  />
							</td>
							<td><input class="delete_row btn1" type="button" value="删除" name=""></td><td class="comb-error"></td>
						</tr>
						</s:iterator>
						<tr id="tr_subjection_addr" class = "address" style="display:none;"><td width="40">地址：</td>
						<td>
							<input name="subjectionAddress[].address" type="text" size = "21" />
							<input type = "hidden" name="subjectionAddress[].id" type="text" />
						</td>
						<td width="40">邮编：</td><td><input 	name="subjectionAddress[].postCode" type="text" size="6" maxlength="6" /></td>
						<td width = "80">
							<label>是否默认：<input type="checkbox" name="subjectionAddress[].isDefault" value = "0"/></label>
							<input type="hidden" name="subjectionAddress[].isDefault" class="" value = "0"/>
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
		<td class="table_td3"><s:textfield name="agency.sphone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
		<td class="table_td4"></td>
	</tr>
	<tr class="table_tr2">
		<td class="table_td2">传真：</td>
		<td class="table_td3"><s:textfield name="agency.sfax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
		<td class="table_td4"></td>
	</tr>
	<tr class="table_tr2">
		<td class="table_td2">邮箱：</td>
		<td class="table_td3"><s:textfield name="agency.semail" cssClass="input_css" /></td>
		<td class="table_td4"></td>
	</tr>
	<tr class="table_tr2">
		<td class="table_td2">部门主页：</td>
		<td class="table_td3"><s:textfield name="agency.shomepage" cssClass="input_css" /></td>
		<td class="table_td4"></td>
	</tr>
</table>
</div>