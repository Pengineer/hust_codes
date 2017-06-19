<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="teacher_affiliation_modify">
	<table id="table_affiliation" class="table_edit" width="100%" border="0" cellspacing="0" cellpadding="2">
		<thead>
			<tr>
				<th width="70">定职时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">离职时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th colspan="2">所在高校与院系或研究基地</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">职务</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="60">每年工作时间(月)</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">工作证号</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">人员类型</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70"><input type="button" class="btn2" id="add_affiliation" value="添加一条" /></th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="teachers" status="stat">
		<tr class="tr_valid" >
			<td>
				<s:textfield name="%{'teachers['+#stat.index+'].startDate'}" cssClass="input_css FloraDatepick">
					<s:param name="value">
						<s:date name="%{teachers[#stat.index].startDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td></td>
			<td>
				<s:textfield name="%{'teachers['+#stat.index+'].endDate'}" cssClass="input_css FloraDatepick">
					<s:param name="value">
						<s:date name="%{teachers[#stat.index].endDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td></td>
			<td>
				<s:select style="width:70px;" cssClass="select" name="%{'unitType['+#stat.index+']'}" value="%{unitType[#stat.index]}" headerKey="-1" headerValue="--请选择--" list="#{'0':'院系','1':'基地'}" />
			</td>
			<td>
				<s:textfield name="%{'DIName_subjectionName['+#stat.index+']'}" value="%{DIName_subjectionName[#stat.index]}" readonly="1" cssClass="input_css" />
				<s:hidden name="%{'teachers['+#stat.index+'].department.id'}" value="%{teachers[#stat.index].department.id}" />
				<s:hidden name="%{'teachers['+#stat.index+'].institute.id'}" value="%{teachers[#stat.index].institute.id}" />
			</td>
			<td></td>
			<td><s:textfield name="%{'teachers['+#stat.index+'].position'}" value="%{teachers[#stat.index].position}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'teachers['+#stat.index+'].workMonthPerYear'}" value="%{teachers[#stat.index].workMonthPerYear}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'teachers['+#stat.index+'].staffCardNumber'}" value="%{teachers[#stat.index].staffCardNumber}" cssClass="input_css" /></td>
			<td></td>
			<td>
				<s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="#{'专职人员':'专职人员','兼职人员':'兼职人员','离职人员':'离职人员'}" name="%{'teachers['+#stat.index+'].type'}" value="%{teachers[#stat.index].type}" />
			</td>
			<td></td>
			<td><input type="button" class="delete_row btn1" value="删除" /></td>
			<s:hidden name="%{'teachers['+#stat.index+'].id'}" />
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
	<tr class="tr_valid" id="tr_affiliation" style="display:none;">
		<td><input type="text" name="teachers[].startDate" class="add_date input_css"/></td>
		<td></td>
		<td><input type="text" name="teachers[].endDate" class="add_date input_css"/></td>
		<td></td>
		<td>
			<s:select style="width:70px;" cssClass="select" name="unitType[]"  headerKey="-1" headerValue="--请选择--" list="#{'0':'院系','1':'基地'}" />
		</td>
		<td><s:textfield name="DIName_subjectionName[]" cssClass="input_css" readonly="1"/></td>
				<s:hidden name="teachers[].department.id"  />
				<s:hidden name="teachers[].institute.id"  />
		<td></td>
		<td><s:textfield name="teachers[].position" cssClass="input_css" /></td>
		<td></td>
		<td><s:textfield name="teachers[].workMonthPerYear" cssClass="input_css" /></td>
		<td></td>
		<td><s:textfield name="teachers[].staffCardNumber" cssClass="input_css" /></td>
		<td></td>
		<td>
			<s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="#{'专职人员':'专职人员','兼职人员':'兼职人员','离职人员':'离职人员'}"  name="teachers[].type" />
		</td>
		<td></td>
		<td><input type="button" class="btn1 delete_row" value="删除" /></td>
	</tr>
</table>
