<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="financial">
	<textarea id="view_template_financial" style="display:none;">
		<div class="p_box_t">
			<div class="p_box_t_t">部门信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100">部门名称：</td>
						<td class="value" width="280">${agency.fname}</td>
						<td class="key" width="100">部门负责人：</td>
						<td class="value">${fDirectorName}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">部门联系人：</td>
						<td class="value">${fLinkmanName}</td>
						<td class="key">部门地址：</td>
						<td class="value">${agency.faddress}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">部门邮编：</td>
						<td class="value">${agency.fpostcode}</td>
						<td class="key">电话：</td>
						<td class="value">${agency.fphone}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">传真：</td>
						<td class="value">${agency.ffax}</td>
						<td class="key">邮箱：</td>
						<td class="value" ><a href="mailto:${agency.femail}">${agency.femail}</a></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="p_box_t">
			<div class="p_box_t_t">银行信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100">开户银行：</td>
						<td class="value" width="280">${agency.fbank}</td>
						<td class="key" width="100">银行支行：</td>
						<td class="value">${agency.fbankBranch}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">银联行号：</td>
						<td class="value" colspan="3">${agency.fcupNumber}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">银行户名：</td>
						<td class="value" >${agency.fbankAccountName}</td>
						<td class="key">银行账号：</td>
						<td class="value" colspan="3">${agency.fbankAccount}</td>
					</tr>
				</table>
			</div>
		</div>
	</textarea>
	<div id="view_container_financial" style="display:none; clear:both;"></div>
</div>
