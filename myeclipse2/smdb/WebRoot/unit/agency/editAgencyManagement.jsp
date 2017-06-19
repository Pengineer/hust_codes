<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="managementInfo" style="display:none">
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
			<td class="table_td2">部门地址：</td>
			<td class="table_td3"><s:textfield name="agency.saddress" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">部门邮编：</td>
			<td class="table_td3"><s:textfield name="agency.spostcode" cssClass="input_css" /></td>
			<td class="table_td4"></td>
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
