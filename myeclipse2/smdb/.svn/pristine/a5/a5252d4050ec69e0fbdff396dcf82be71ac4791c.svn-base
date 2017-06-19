<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="teacher_affiliation_add">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="120"><span class="table_title4">所在机构：</span></td>
			<td class="table_td3">
				<s:select cssClass="select" name="unitType" cssClass="select_btn" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'0':'院系','1':'基地'}" />
				<input type="button" id="select_unitName_btn" class="btn1 select_btn" value="选择"/>
				<div id="unitName" class="choose_show"><s:property value="%{DIName_subjectionName[0]}"/></div>
				<s:hidden name="teacher.department.id" />
				<s:hidden name="teacher.institute.id" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title4">人员类型：</span></td>
			<td class="table_td3">
				<s:select cssClass="select" name="teacher.type" headerKey="-1" headerValue="--%{getText('请选择')}--" list="#{'专职人员':'专职人员','兼职人员':'兼职人员','离职人员':'离职人员'}" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">定职时间：</td>
			<td class="table_td3">
				<s:textfield name="teacher.startDate" cssClass="input_css FloraDatepick" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">离职时间：</td>
			<td class="table_td3">
				<s:textfield name="teacher.endDate" cssClass="input_css FloraDatepick" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">职务：</td>
			<td class="table_td3">
				<s:textfield name="teacher.position" size="12" cssClass="input_css" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">工作证号：</td>
			<td class="table_td3">
				<s:textfield name="teacher.staffCardNumber" size="12" cssClass="input_css" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">每年工作时间（月）：</td>
			<td class="table_td3">
				<s:textfield name="teacher.workMonthPerYear" size="12" cssClass="input_css" />
			</td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
