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
						<td class="value" width="200">${institute.englishName}</td>
						<td class="key">名称缩写：</td>
						<td class="value">${institute.abbr}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">研究活动类型：</td>
						<td class="value">${researchActivityType}</td>
						<td class="key" width="100">是否是独立机构：</td>
						<td class="value">
							{if institute.isIndependent==1}是
							{else}否
							{/if}
						</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">单位负责人：</td>
						<td class="value"><a id="${directorId}" class="link2" href="">${directorName}</a></td>
						<td class="key">组成方式：</td>
						<td class="value">${institute.form}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">批准批次：</td>
						<td class="value">${institute.approveSession}</td>
						<td class="key">批准时间：</td>
						<td class="value">${institute.approveDate}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">简介：</td>
						<td class="value" colspan="3">${institute.introduction}</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="p_box_t">
			<div class="p_box_t_t">资源信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100">办公用房面积：</td>
						<td class="value" width="200">${institute.officeArea}</td>
						<td class="key" width="100">资料室面积：</td>
						<td class="value">${institute.dataroomArea}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">中文藏书数量：</td>
						<td class="value">${institute.chineseBookAmount}</td>
						<td class="key">中文报刊数量：</td>
						<td class="value">${institute.chinesePaperAmount}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key">外文藏书数量：</td>
						<td class="value">${institute.foreignBookAmount}</td>
						<td class="key">外文报刊数量：</td>
						<td class="value">${institute.foreignPaperAmount}</td>
					</tr>
				</table>
			</div>
		</div>
	</textarea>
	<div id="view_container_basic" style="display:none; clear:both;"></div>
</div>