<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="basicInfo">
	<textarea id="view_basicInfo_template" style="display:none;">
		<table class="table_edit" cellspacing="0" cellpadding="0">
			<tr><th class="thhead" colspan="4">基本信息</th></tr>
			<tr><td class="wd17">项目名称</td>
				<td colspan="3">${project.projectName}</td>
			</tr>
			<tr><td class="wd17">项目类型</td>
				<td class="wd18">${project.projectType}</td>
				<td class="wd17">研究类型</td>
				<td class="wd18">${project.researchType}</td>
			</tr>
			<tr><td class="wd17">学科类型</td>
				<td class="wd18">${project.disciplineType}</td>
				<td class="wd17">依托学科</td>
				<td class="wd18">${project.discipline}</td>
			</tr>
			<tr><td class="wd17">高校代码</td>
				<td class="wd18">${project.universityCode}</td>
				<td class="wd17">高校名称</td>
				<td class="wd18">${universityName}</td>
			</tr>
			<tr>
				<td class="wd17">最终成果类型</td>
				<td class="wd18" colspan="3">${project.finalResultType}</td>
			</tr>
			<s:if test="listType==5">
				<tr><td class="wd17">项目主题</td>
					<td class="wd21" colspan="3">${project.topic}</td>
				</tr>
			</s:if>
			<tr><th class="thhead" colspan="4">负责人信息</th></tr>
			<tr>
				<td class="wd17">姓名</td>
				<td class="wd18">${project.director}</td>
				<td class="wd17">性别</td>
				<td class="wd18">${project.gender}</td>
			</tr>
			<tr>
				<td class="wd17">高校</td>
				<td class="wd18">${project.directorUniversity}</td>
				<td class="wd17">出生日期</td>
				<td class="wd18">${project.birthday}</td>
			</tr>
			<tr>
				<td class="wd17">职称</td>
				<td class="wd18">${project.title}</td>
				<td class="wd17">职务</td>
				<td class="wd18">${project.job}</td>
			</tr>
			<tr>
				<td class="wd17">项目成员</td>
				<td class="wd18" colspan="3">${project.members}</td>
			</tr>
		</table>
	</textarea>
	<div id="view_basicInfo"></div>
</div>