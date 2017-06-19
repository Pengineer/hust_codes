<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info list_edit" id="abroad">
	<table id="table_abroad" class="table_edit" width="100%" border="0" cellspacing="0" cellpadding="2">
		<thead>
			<tr>
				<th width="70">开始时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">结束时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="100">国家或地区</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="100">机构</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th>目的</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70"><input type="button" class="btn2" id="add_abroad" value="添加一条" /></th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="aes" status="stat">
		<tr class="tr_valid" >
			<td>
				<s:textfield name="%{'aes['+#stat.index+'].startDate'}" cssClass="FloraDatepick input_css">
					<s:param name="value">
						<s:date name="%{aes[#stat.index].startDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td></td>
			<td>
				<s:textfield name="%{'aes['+#stat.index+'].endDate'}" cssClass="FloraDatepick input_css">
					<s:param name="value">
						<s:date name="%{aes[#stat.index].endDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td></td>
			<td><s:textfield name="%{'aes['+#stat.index+'].countryRegion'}" value="%{aes[#stat.index].countryRegion}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'aes['+#stat.index+'].workUnit'}" value="%{aes[#stat.index].workUnit}" cssClass="input_css" /></td>
			<td></td>
			<td><s:textfield name="%{'aes['+#stat.index+'].purpose'}" value="%{aes[#stat.index].purpose}" cssClass="input_css" /></td>
			<td></td>
			<td><input type="button" class="btn1 delete_row" value="删除" /></td>
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
	<tr class="tr_valid" id="tr_abroad" style="display:none;">
		<td><input type="text" name="aes[].startDate" class="add_date input_css"/></td>
		<td></td>
		<td><input type="text" name="aes[].endDate" class="add_date input_css"/></td>
		<td></td>
		<td><s:textfield name="aes[].countryRegion" cssClass="input_css" /></td>
		<td></td>
		<td><s:textfield name="aes[].workUnit" cssClass="input_css" /></td>
		<td></td>
		<td><s:textfield name="aes[].purpose" cssClass="input_css" /></td>
		<td></td>
		<td><input type="button" class="btn1 delete_row" value="删除" /></td>
	</tr>
</table>
