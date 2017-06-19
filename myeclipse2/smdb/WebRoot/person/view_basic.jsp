<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="basic">
	<textarea class="view_template" style="display:none;">
	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key">英文名：</td>
					<td class="value">${person.englishName}</td>
					<td class="key">曾用名：</td>
					<td class="value">${person.usedName}</td>
					<td id="photo" rowspan="4" style="width:120px; padding-top:5px;">
					{if person.photoFile == null}
						<img src="image/photo.png" />
					{else}
						<img src="${person.photoFile}" />
					{/if}
					</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">出生日期：</td>
					<td class="value">${person.birthday}</td>
					<td class="key">政治面貌：</td>
					<td class="value">${person.membership}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">证件类型：</td>
					<td class="value">${person.idcardType}</td>
					<td class="key">证件号：</td>
					<td class="value">${person.idcardNumber}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key" width="100">国家或地区：</td>
					<td class="value" width="200">${person.countryRegion}</td>
					<td class="key" width="100">民族：</td>
					<td class="value">${person.ethnic}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key" width="100">籍贯：</td>
					<td class="value" colspan="4">${person.birthplace}</td>
				</tr>
			</table>
		</div>
	</div>
	</textarea>
</div>
