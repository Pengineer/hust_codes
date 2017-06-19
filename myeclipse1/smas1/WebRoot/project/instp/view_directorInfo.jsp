<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="directorInfo">
	<textarea id="view_directorInfo_template" style="display:none;">
		<table class="table_edit" cellspacing="0" cellpadding="0">
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
		</table>
	</textarea>
	<div id="view_directorInfo"></div>
</div>