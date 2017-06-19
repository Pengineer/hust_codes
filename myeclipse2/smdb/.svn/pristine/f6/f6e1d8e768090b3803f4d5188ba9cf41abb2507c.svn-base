<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info list_edit" id="education">
	<table id="table_education" class="table_edit" width="100%" border="0" cellspacing="0" cellpadding="2">
		<thead>
			<tr>
				<th width="70">入学时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="70">学位授予时间</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th>学历</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th>学位</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="50">毕业国家或地区</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="75">毕业高校</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="75">毕业院系</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="75">专业</th>
				<th width="2"><img src="image/table_line.gif" width="2" height="24" /></th>
				<th width="75"><input type="button" class="btn2" id="add_education" value="添加一条" /></th>
			</tr>
		</thead>
		<tbody>
		<s:iterator value="ebs" status="stat">
		<tr class="tr_valid" >
			<td>
				<s:textfield name="%{'ebs['+#stat.index+'].startDate'}" cssClass="FloraDatepick input_css">
					<s:param name="value">
						<s:date name="%{ebs[#stat.index].startDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td>&nbsp;</td>
			<td>
				<s:textfield name="%{'ebs['+#stat.index+'].endDate'}" cssClass="FloraDatepick input_css">
					<s:param name="value">
						<s:date name="%{ebs[#stat.index].endDate}" format="yyyy-MM-dd" />
					</s:param>
				</s:textfield>
			</td>
			<td>&nbsp;</td>
			<td>
				<s:select cssClass="select" headerKey="-1" headerValue="%{getText('请选择')}" list="%{baseService.getSystemOptionMapAsName('educationBackground', null)}" name="%{'ebs['+#stat.index+'].education'}" value="%{ebs[#stat.index].education}" />
			</td>
			<td>&nbsp;</td>
			<td>
				<s:select cssClass="select" headerKey="-1" headerValue="%{getText('请选择')}" list="%{baseService.getSystemOptionMapAsName('degree', null)}" name="%{'ebs['+#stat.index+'].degree'}" value="%{ebs[#stat.index].degree}" />
			</td>
			<td>&nbsp;</td>
			<td><s:textfield name="%{'ebs['+#stat.index+'].countryRegion'}" value="%{ebs[#stat.index].countryRegion}" cssClass="input_css" /></td>
			<td>&nbsp;</td>
			<td><s:textfield name="%{'ebs['+#stat.index+'].university'}" value="%{ebs[#stat.index].university}" cssClass="input_css" /></td>
			<td>&nbsp;</td>
			<td><s:textfield name="%{'ebs['+#stat.index+'].department'}" value="%{ebs[#stat.index].department}" cssClass="input_css" /></td>
			<td>&nbsp;</td>
			<td><s:textfield name="%{'ebs['+#stat.index+'].major'}" value="%{ebs[#stat.index].major}" cssClass="input_css" /></td>
			<td>&nbsp;</td>
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

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
	<tr class="tr_valid" id="tr_education" style="display:none;">
		<td><input type="text" name="ebs[].startDate" class="add_date input_css" /></td>
		<td>&nbsp;</td>
		<td><input type="text" name="ebs[].endDate" class="add_date input_css" /></td>
		<td>&nbsp;</td>
		<td><s:select cssClass="select" headerKey="-1" headerValue="%{getText('请选择')}" list="%{baseService.getSystemOptionMapAsName('educationBackground', null)}" name="ebs[].education" /></td>
		<td>&nbsp;</td>
		<td><s:select cssClass="select" headerKey="-1" headerValue="%{getText('请选择')}" list="%{baseService.getSystemOptionMapAsName('degree', null)}" name="ebs[].degree" /></td>
		<td>&nbsp;</td>
		<td><s:textfield name="ebs[].countryRegion" cssClass="input_css" /></td>
		<td>&nbsp;</td>
		<td><s:textfield name="ebs[].university" cssClass="input_css" /></td>
		<td>&nbsp;</td>
		<td><s:textfield name="ebs[].department" cssClass="input_css" /></td>
		<td>&nbsp;</td>
		<td><s:textfield name="ebs[].major" cssClass="input_css" /></td>
		<td>&nbsp;</td>
		<td><input type="button" class="delete_row btn1" value="删除" /></td>
	</tr>
</table>
