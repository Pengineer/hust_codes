<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<div id="bank">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<table id="list_bank" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<thead>
				<tr class="table_title_tr3">
			        <td width="140">开户银行</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="100">银行支行</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td width="160">银联行号</td>
			        <td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
			        <td>银行户名</td>
				</tr>
			</thead>
			<tbody>
			{for item in person_bankAccount}
				<tr>
					<td>${item.bankName}</td>
					<td></td>
					<td>${item.bankCupNumber}</td>
					<td></td>
					<td>${item.accountNumber}</td>
					<td></td>
					<td>${item.accountName}</td>
				</tr>
			{forelse}
				<tr>
					<td align="center">暂无符合条件的记录</td>
				</tr>
			{/for}
			</tbody>
		</table>
	</div>
	</textarea>
</div>