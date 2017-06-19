<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info main_content" id="student">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="120"><span class="table_title4">所在高校与院系或研究基地：</span></td>
			<td class="table_td3">
				<s:select cssClass="select" name="unitType" cssClass="select_btn" headerKey="-1" headerValue="--请选择--" list="#{'0':'院系','1':'基地'}" />
				<input type="button" id="select_unitName_btn" class="btn1 select_btn" value="选择"/>
			 	<div id="unitName" class="choose_show"><s:property value="%{DIName_subjectionName}"/></div>
				<s:hidden name="departmentId" />
				<s:hidden name="instituteId" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title4">学生类别：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="student.type" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('studentType', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title4">学生状态：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="student.status" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('studentStatus', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">学生证号：</td>
			<td class="table_td3"><s:textfield name="student.studentCardNumber" cssClass="input_css"  /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">入学时间：</td>
			<td class="table_td3">
				<s:textfield name="student.startDate" cssClass="input_css FloraDatepick">
					<s:param name="value">
						<s:date name="%{student.startDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">毕业时间：</td>
			<td class="table_td3">
				<s:textfield name="student.endDate" cssClass="input_css FloraDatepick">
					<s:param name="value">
						<s:date name="%{student.endDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">导师：</td>
			<td>
				<input type="button" id="select_tutorName_btn" class="btn1 select_btn" value="选择"/>
				<div id="tutorName" class="choose_show"><s:property value="%{tutorName}"/></div>
				<s:hidden name="student.tutor.id" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">参与项目：</td>
			<td class="table_td3"><s:textfield name="student.project" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
