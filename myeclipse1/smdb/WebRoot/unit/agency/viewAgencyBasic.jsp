<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="basic">
	<textarea id="view_template_basic" style="display:none;">
		<div class="p_box_t">
			<div class="p_box_t_t">概要信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100">英文名称：</td>
						<td class="value" width="280">${agency.englishName}</td>
						<td class="key">名称缩写：</td>
						<td class="value">${agency.abbr}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">所在省份：</td>
						<td class="value">${provinceName}</td>
						<td class="key">所在市：</td>
						<td class="value">${cityName}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">单位负责人：</td>
						<td class="value"><a id="${directorId}" class="link2" href="">${directorName}</a></td>
						<td class="key" width="100">单位类型：</td>
						<td class="value" id="typeName">${typeName}</td>
					</tr>
					{if agency.type != 1 && agency.type != 2}
					<tr class="table_tr7">
						<td class="key">高校性质：</td>
						<td class="value">${agency.category}</td>
						<td class="key">举办者：</td>
						<td class="value">${agency.organizer}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">办学类型：</td>
						<td class="value">${agency.style}</td>
						<td class="key">上级管理机构：</td>
						<td class="value" ><a name="${subjectionId}" id="subjectionId" href="" class="linkA">${subjectionName}</a></td>
					</tr>
					{/if}
					<tr class="table_tr7">
						<td class="key">简介：</td>
						<td class="value" colspan="3">${agency.introduction}</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="p_box_t">
			<div class="p_box_t_t">联系信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key">通信地址：</td>
						<td class="value address" colspan="3">
						{for item in commonAddress}
							${item.address }；
						{/for}
						</td>
					</tr>
					<tr class="table_tr7">
						<td class="key" width="100">邮政编码：</td>
						<td class="value address" width="280">
						{for item in commonAddress}
							${item.postCode}；
						{/for}
						</td>
						<td class="key" width="100">电话：</td>
						<td class="value">${agency.phone}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">传真：</td>
						<td class="value">${agency.fax}</td>
						<td class="key">邮箱：</td>
						<td class="value"><a href="mailto:${agency.email}">${agency.email}</a></td>
					</tr>
					<tr class="table_tr7">
						<td class="key">主页：</td>
						<td class="value" colspan="3"><a href="${agency.homepage}" title="${agency.homepage}" target="_blank">${agency.homepage}</a></td>
					</tr>
				</table>
			</div>
		</div>
	</textarea>
	<div id="view_container_basic" style="display:none; clear:both;"></div>
</div>