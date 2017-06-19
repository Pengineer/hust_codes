<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="academic">
	<textarea class="view_template" style="display:none;">
	{if academic != null}
	<div class="p_box_t">
		<div class="p_box_t_t">概况</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>

	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="100">最后学历：</td>
					<td class="value" width="200">${academic.lastEducation}</td>
					<td class="key" width="100">最后学位：</td>
					<td class="value">${academic.lastDegree}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">国家或地区：</td>
					<td class="value">${academic.countryRegion}</td>
					<td class="key">学位授予时间：</td>
					<td class="value">${academic.degreeDate}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">博士后：</td>
					<td class="value">${postdoctor}</td>
					<td class="key">专家类别：</td>
					<td class="value">${expertType}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">专业职称：</td>
					<td class="value">${academic.specialityTitle}</td>
					<td class="key">岗位等级：</td>
					<td class="value">${academic.positionLevel}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">导师类型：</td>
					<td class="value">${academic.tutorType}</td>
					<td class="key">人才类型：</td>
					<td class="value" colspan="3">${academic.talent}</td>
				</tr>
			</table>
		</div>
	</div>

	<div class="p_box_t">
		<div class="p_box_t_t">语言和计算机</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>

	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="100">民族语言：</td>
					<td class="value">${academic.ethnicLanguage}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key" width="100">外语语种：</td>
					<td class="value">${academic.language}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">计算机操作水平：</td>
					<td class="value">${computerLevel}</td>
				</tr>
			</table>
		</div>
	</div>

	<div class="p_box_t">
		<div class="p_box_t_t">专业</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>

	<div class="p_box_body">
		<div class="p_box_body_t">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<tr class="table_tr7">
					<td class="key" width="100">学科门类：</td>
					<td class="value" width="300">${academic.disciplineType}</td>
					<td class="key" width="100">相关学科：</td>
					<td class="value">${academic.relativeDiscipline}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key" width="100">学科：</td>
					<td class="value" colspan="3">${academic.discipline}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">所属专业：</td>
					<td class="value">${academic.major}</td>
					<td class="key">研究方向：</td>
					<td class="value">${academic.researchField}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">学术特长：</td>
					<td class="value">${academic.researchSpeciality}</td>
					<td class="key">学术兼职：</td>
					<td class="value">${academic.parttimeJob}</td>
				</tr>
				<tr class="table_tr7">
					<td class="key">进修情况：</td>
					<td class="value" colspan="3">${academic.furtherEducation}</td>
				</tr>
			</table>
		</div>
	</div>
	{else}
	<div class="p_box_body" style="text-align:center;">无学术信息</div>
	{/if}
	</textarea>
	<s:include value="/auxiliary/basicAuxiliary/researchRecommend.jsp"></s:include>
</div>


