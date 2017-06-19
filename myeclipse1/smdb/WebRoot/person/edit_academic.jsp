<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="academic">
<s:textfield type = "hidden" name="academic.id" cssClass="input_css" />
	<table width="100%" border="0" cellspacing="2" cellpadding="0">
		<tr><td colspan="3" style="font-size:11pt;">概况<hr /></td></tr>
		<tr class="table_tr2">
			<td class="table_td2" width="120">最后学历：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('educationBackground', null)}" name="academic.lastEducation" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">最后学位：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('degree', null)}" name="academic.lastDegree" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">学位授予国家或地区：</td>
			<td class="table_td3"><s:textfield name="academic.countryRegion" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">学位授予时间：</td>
			<td class="table_td3"><s:textfield id="datepick" name="academic.degreeDate" cssClass="input_css" ><s:param name="value">
						<s:date name="%{academic.degreeDate}" format="yyyy-MM-dd" />
					    </s:param></s:textfield></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">专家类别：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMap('expertType', null)}" name="academic.expertType.id" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">博士后：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="#{'0':'否','1':'在站','2':'出站'}" name="academic.postdoctor" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">专业职称：</td>
			<td class="table_td3"><s:select cssClass="select j_selectSubType" headerKey="-1" headerValue="--请选择--" id="title" list="%{baseService.getSystemOptionMap('GBT8561-2001', null)}" name="specialityTitleId" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">二级职称：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" id="subTitle" list="#{'-1':'--请选择二级职称--'}" name="academic.specialityTitle" /></td>
			<s:hidden value="%{academic.specialityTitle}" id="subTitleId"></s:hidden>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">岗位等级：</td>
			<td class="table_td3"><s:textfield name="academic.positionLevel" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">导师类型：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('tutorType', null)}" name="academic.tutorType" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">人才类型：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMapAsName('talentType', null)}" name="academic.talent" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr><td colspan="3" style="font-size:11pt;">语言和计算机<hr /></td></tr>
		<tr class="table_tr2">
			<td class="table_td2">民族语言：</td>
			<td class="table_td3">
				<input type="button" id="select_ethnicLanguage_btn" class="btn1 select_btn" value="选择"/>
				<div id="ethnicLanguage" class="choose_show"><s:property value="%{academic.ethnicLanguage}"/></div>
				<s:hidden name="academic.ethnicLanguage" />
			</td>
			
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">外语语种：</td>
			<td class="table_td3">
				<input type="button" id="select_language_btn" class="btn1 select_btn" value="选择"/>
				<div id="language" class="choose_show"><s:property value="%{academic.language}"/></div>
				<s:hidden name="academic.language" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">计算机操作水平：</td>
			<td class="table_td3"><s:select cssClass="select" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMap('computerLevel', null)}" name="academic.computerLevel.id" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr><td colspan="3" style="font-size:11pt;">专业<hr /></td></tr>
		<tr class="table_tr2">
			<td class="table_td2">学科门类：</td>
			<td class="table_td3">
				<input type="button" class="btn1 select_btn" id="selectDisciplineType" value="选择"/>
				<s:hidden name="academic.disciplineType" id="9527" value="%{academic.disciplineType}" />
				<div id="disptr" id="9528" class="choose_show" style="display:none"></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">学科：</td>
			<td class="table_td3">
				<input type="button" class="btn1 select_btn" id="selectDiscipline" value="选择" />
				<s:hidden name="academic.discipline" id="9529" value="%{academic.discipline}" />
				<div id="9530" class="choose_show" style="display:none" ></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">相关学科：</td>
			<td class="table_td3">
				<input type="button" class="btn1 select_btn" id="selectRelativeDiscipline" value="选择" />
				<s:hidden name="academic.relativeDiscipline" id="9531" value="%{academic.relativeDiscipline}" />
				<div id="9532" class="choose_show" style="display:none" ></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">所属专业：</td>
			<td class="table_td3"><s:textfield name="academic.major" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">研究方向：</td>
			<td class="table_td3"><s:textarea name="academic.researchField" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">学术特长：</td>
			<td class="table_td3"><s:textarea name="academic.researchSpeciality" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">进修情况：</td>
			<td class="table_td3"><s:textarea name="academic.furtherEducation" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">学术兼职：</td>
			<td class="table_td3"><s:textarea name="academic.parttimeJob" cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
	</table>
</div>
