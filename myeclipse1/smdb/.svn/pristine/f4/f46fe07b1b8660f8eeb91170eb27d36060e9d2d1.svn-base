<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="contact">
	<textarea id="view_template_contact" style="display:none;">
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100">负责人：</td>
						<td class="value"><a id="${directorId}" href="" class="link2" >${directorName}</a></td>
						<td class="key" width="100">联系人：</td>
						<td class="value"><a id="${linkmanId}" href="" class="link2" >${linkmanName}</a></td>
					</tr>
					<tr class="table_tr7">
						<td class="key">通信地址：</td>
						<td class="value address">
						{for item in commonAddress}
							${item.address }；
						{/for}
						</td>
						<td class="key">邮政编码：</td>
						<td class="value address">
						{for item in commonAddress}
							${item.postCode}；
						{/for}
						</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">电话：</td>
						<td class="value">${department.phone}</td>
						<td class="key">传真：</td>
						<td class="value">${department.fax}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">邮箱：</td>
						<td class="value"><a href="mailto:${department.email}">${department.email}</a></td>
						<td class="key">主页：</td>
						<td class="value">{if department.homepage!=null}<a href="${department.homepage}" target="_blank">${department.homepage}</a>{/if}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">简介：</td>
						<td class="value" colspan="3">${department.introduction}</td>
					</tr>
				</table>
			</div>
		</div>
	</textarea>
	<div id="view_container_contact" style="display:none; clear:both;"></div>
</div>