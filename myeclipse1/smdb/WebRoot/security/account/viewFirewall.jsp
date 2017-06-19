<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_firewall_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="90">最大连接数：</td>
					<td class="value">${passport.maxSession}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">允许登录IP：</td>
					<td class="value">${passport.allowedIp}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">拒绝登录IP：</td>
					<td class="value">${passport.refusedIp}</td>
				</tr>
			</table>
		</div>
	</div>
</textarea>
<div id="view_firewall"></div>