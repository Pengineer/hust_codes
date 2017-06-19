<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="contact">
	<textarea id="view_template_contact" style="display:none;">
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100">通信地址：</td>
						<td class="value address" width="280">
						{for item in commonAddress}
							${item.address }；
						{/for}</td>
						<td class="key" width="100">邮政编码：</td>
						<td class="value address">
						{for item in commonAddress}
							${item.postCode }；
						{/for}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">电话：</td>
						<td class="value">${institute.phone}</td>
						<td class="key">传真：</td>
						<td class="value">${institute.fax}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">邮箱：</td>
						<td class="value"><a href="mailto:${institute.email}">${institute.email}</a></td>
						<td class="key">联系人：</td>
						<td class="value"><a id="${linkmanId}" class="link2"">${linkmanName}</a></td>
					</tr>
					<tr class="table_tr7">
						<td class="key">主页：</td>
						<td colspan="3" class="value"><a href="${institute.homepage}" target="_blank">${institute.homepage}</a></td>
					</tr>
				</table>
			</div>
		</div>
	</textarea>
	<div id="view_container_contact" style="display:none; clear:both;"></div>
</div>