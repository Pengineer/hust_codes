<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="social">
	<textarea id="view_template_social" style="display:none;">
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100">部门名称：</td>
						<td class="value" width="280">${agency.sname}</td>
						<td class="key" width="100">部门负责人：</td>
						<td class="value"><a id="${sDirectorId}" class="link2" href="">${sDirectorName}</a></td>
					</tr>
					<tr class="table_tr7">
						<td class="key">部门联系人：</td>
						<td class="value"><a id="${sLinkmanId}" class="link2" href="">${sLinkmanName}</a></td>
						<td class="key">部门地址：</td>
						<td class="value address">
						{for item in subjectionAddress}
							${item.address }；
						{/for}
						</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">部门邮编：</td>
						<td class="value address">
						{for item in subjectionAddress}
							${item.postCode }；
						{/for}
						</td>
						<td class="key">电话：</td>
						<td class="value">${agency.sphone}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">传真：</td>
						<td class="value">${agency.sfax}</td>
						<td class="key">邮箱：</td>
						<td class="value"><a href="mailto:${agency.semail}">${agency.semail}</a></td>
					</tr>
					<tr class="table_tr7">
						<td class="key">部门主页：</td>
						<td class="value" colspan="3" ><a href="${agency.shomepage}" title="${agency.shomepage}" target="_blank">${agency.shomepage}</a></td>
					</tr>
				</table>
			</div>
		</div>
	</textarea>
	<div id="view_container_social" style="display:none; clear:both;"></div>
</div>
