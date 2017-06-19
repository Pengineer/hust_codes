<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="province_officer_affiliation">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="100"><span class="table_title2">所在机构：</span></td>
			<td class="table_td3">
				<input type="button" id="select_unitName_btn" class="btn1 select_btn" value="选择"/>
				<div id="unitName" class="choose_show"><s:property value="%{unitName}"/></div>
				<s:hidden id="unitId" name="unitId" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title2">人员类型：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="officer.type" headerKey="" headerValue="--%{getText('请选择')}--" list="#{'专职人员':'专职人员','兼职人员':'兼职人员','离职人员':'离职人员'}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">定职时间：</td>
			<td class="table_td3">
				<s:textfield name="officer.startDate" cssClass="input_css FloraDatepick">
					<s:param name="value">
						<s:date name="%{officer.startDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">离职时间：</td>
			<td class="table_td3">
				<s:textfield name="officer.endDate" cssClass="input_css FloraDatepick">
					<s:param name="value">
						<s:date name="%{officer.endDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">职务：</td>
			<td class="table_td3"><s:textfield name="officer.position" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">工作证号：</td>
			<td class="table_td3"><s:textfield name="officer.staffCardNumber" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
