<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="thesis">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="140">学位论文题目：</td>
			<td class="table_td3"><s:textfield name="student.thesisTitle" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">论文经费（万）：</td>
			<td class="table_td3"><s:textfield name="student.thesisFee" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">是否优秀学位论文：</td>
			<td class="table_td3"><div id="is_excellent"><s:radio name="student.isExcellent" list="#{'1':'是','0':'否'}" /></div></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">优秀学位论文等级：</td>
			<td class="table_td3"><s:select cssClass="select" name="student.excellentGrade" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('excellentGrade', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">优秀学位论文评选年度：</td>
			<td class="table_td3"><s:textfield name="student.excellentYear" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">优秀学位论文评选届次：</td>
			<td class="table_td3"><s:textfield name="student.excellentSession" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
