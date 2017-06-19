<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="basicInfo">
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr class="table_tr2">
			<td class="table_td2" width="120"><span class="table_title3">单位名称：</span></td>
			<td class="table_td3"><s:textfield id="name" name="agency.name" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">英文名称：</td>
			<td class="table_td3"><s:textfield name="agency.englishName" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">名称缩写：</td>
			<td class="table_td3"><s:textfield name="agency.abbr" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">单位代码：</td>
			<td class="table_td3"><s:textfield name="agency.code" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title3">所在省份：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="agency.province.id" value="%{agency.province.id}" id="province" headerKey="-1" headerValue="--请选择省--" list="%{unitService.getProvinceList()}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">所在市：</td>
			<td class="table_td3"><s:select cssClass="select" name="agency.city.id" id="city" list="#{'':'--请选择市--'}" />
			<s:hidden id ="cityId" value="%{agency.city.id}" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">单位负责人：</td>
			<td class="table_td3">
				<input type="button" id="select_director_btn" class="btn1 select_btn" value="选择"/>
				<div id="directorName" class="choose_show"><s:property value="directorName"/></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">单位类型：</td>
			<s:if test = "agency.type==1"><td class="table_td3">部级</td></s:if>
			<s:elseif test = "agency.type==2"><td class="table_td3">省级</td></s:elseif>
			<s:elseif test="agency.type == 3"><td class="table_td3">部属高校</td></s:elseif>
			<s:else><td class="table_td3">地方高校</td></s:else>
			<s:hidden name="agency.type" value="%{agency.type}"/>
			<td class="table_td4"></td>
		</tr>
		<s:if test = "(agency.type == 3 || agency.type == 4)">
			<tr class="table_tr2">
				<td class="table_td2">举办者：</td>
				<td class="table_td3"><s:textfield name="univOrganizer" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">举办者代码：</td>
				<td class="table_td3"><s:textfield name="univOrganizerCode" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">上级管理机构：</td>
				<td class="table_td3">
					<input type="button" id="select_subjection_btn" class="btn1 select_btn" value="选择"/>
					<div id="subjectionName" class="choose_show"><s:property value="subjectionName"/></div>
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">高校性质：</td>
				<td class="table_td3"><s:select cssClass="select" name="agency.category" value="%{agency.category}" id="category" headerKey="" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('universityCategory', null)}"/></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">办学类型：</td>
				<td class="table_td3"><s:select cssClass="select" name="agency.style" value="%{agency.style}" id="style" headerKey="" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('universityStyle', null)}" /></td>
				<td class="table_td4"></td>
			</tr>
		</s:if>
		<tr class="table_tr2">
			<td class="table_td2">简介：</td>
			<td class="table_td3"><s:textarea name="agency.introduction" rows="6" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
