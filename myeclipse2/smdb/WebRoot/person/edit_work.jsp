<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info list_edit" id="work">
	<table id="table_work" class="table_edit" width="100%" border="0" cellspacing="0" cellpadding="2">
		<thead>
			<tr>
				<th width="70">定职时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">离职时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="200">单位</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th>部门</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">职务</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70"><input type="button" class="btn2" id="add_work" value="添加一条" /></th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="wes" status="stat">
		<tr class="tr_valid" >
			<td>
				<s:textfield name="%{'wes['+#stat.index+'].startDate'}" cssClass="FloraDatepick input_css">
					<s:param name="value">
						<s:date name="%{wes[#stat.index].startDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td></td>
			<td>
				<s:textfield name="%{'wes['+#stat.index+'].endDate'}" cssClass="FloraDatepick input_css">
					<s:param name="value">
						<s:date name="%{wes[#stat.index].endDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td></td>
			<td><s:textfield name="%{'wes['+#stat.index+'].unit'}" value="%{wes[#stat.index].unit}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'wes['+#stat.index+'].department'}" value="%{wes[#stat.index].department}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'wes['+#stat.index+'].position'}" value="%{wes[#stat.index].position}" cssClass="input_css" /></td>
			<td></td>
			<td><input type="button" class="delete_row btn1" value="删除" /></td>
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
	<tr class="tr_valid" id="tr_work" style="display:none;">
		<td><input type="text" name="wes[].startDate" class="add_date input_css"/></td>
		<td></td>
		<td><input type="text" name="wes[].endDate" class="add_date input_css"/></td>
		<td></td>
		<td><s:textfield name="wes[].unit" cssClass="input_css" /></td>
		<td></td>
		<td><s:textfield name="wes[].department" cssClass="input_css" /></td>
		<td></td>
		<td><s:textfield name="wes[].position" cssClass="input_css" /></td>
		<td></td>
		<td><input type="button" class="delete_row btn1" value="删除" /></td>
	</tr>
</table>
