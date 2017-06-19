<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>

<div class="edit_info" id="apply">
	<s:hidden id="accountType" value="%{#session.loginer.currentType}"/>
	<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span><s:text name='i18n_ApplicationFile' />：</span></td>
				<s:if test="flag == 0"><%-- 添加 --%>
					<td class="table_td3">
						<input type="file" id="file_add" />
						<s:hidden name="file"/>
					</td>
				</s:if>
				<s:elseif test="flag == 1"><%-- 修改 --%>
					<td class="table_td3">
						<input type="file" id="file_${application1.id}" />
						<s:hidden name="file"/>
					</td>
				</s:elseif>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5"><s:text name="i18n_ProjectName" />：</span></td>
			<td class="table_td3"><s:textfield name="application1.name" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_EnglishName" />：</span></td>
			<td class="table_td3"><s:textfield name="application1.englishName" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@MINISTRY) >= 0 "><!-- 部级以上 -->
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5"><s:text name='i18n_LocalUnit'/>：</span></td>
				<td class="table_td3">
					<input type="button" id="select_dep_btn" class="btn1 select_btn" value="<s:text name='i18n_SelectDepartment'/>" />
					<input type="button" id="select_ins_btn" class="btn2 select_btn" value="<s:text name='i18n_SelectInstitute'/>" />
					<div id="unit" class="choose_show"><s:property value="application1.agencyName"/>&nbsp;<s:property value="application1.divisionName"/></div>
					<s:hidden id="deptInstFlag" name="deptInstFlag" /><!-- 1：研究机构，2:院系 -->
					<s:hidden id="instituteId" name="application1.institute.id" />
					<s:hidden id="departmentId" name="application1.department.id"/>
					<s:hidden id="unitName" name="application1.agencyName"/>
				</td>
				<td class="table_td4"></td>
			</tr>
		</s:if>
		<s:else>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title5"><s:text name="所在单位" />：</span></td>
				<td class="table_td3"><s:select cssClass="select" name = "typeFlag" id="typeFlag" headerKey="" headerValue="--选择单位--"
								list="#{'1':getText('院系'),'2':getText('研究基地')}" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2" style="display:none;" id ="dep">
				<td class="table_td2"><span class="table_title5"><s:text name="所在院系" />：</span></td>
				<td class="table_td3"><s:select cssClass="select" name="application1.department.id" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{baseService.getLocalUnitMap()}" /></td>
				<td class="table_td4"></td>
				<s:hidden id="deptInstFlag" name="teInstFlag" />
			</tr>
			<tr class="table_tr2" style="display:none;"id ="ins">
				<td class="table_td2"><span class="table_title5"><s:text name="所在基地" />：</span></td>
				<td class="table_td3"><s:select cssClass="select" name="application1.institute.id" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{baseService.getLocalInitMap()}" /></td>
				<td class="table_td4"></td>
				<s:hidden id="deptInstFlag" name="teInstFlag" />
			</tr>
		</s:else>
		<s:if test="flag == 0"><%-- 添加 --%>
			<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@MINISTRY) >= 0 "><!-- 部级以上 -->
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title5"><s:text name="i18n_ProjectYear" />：</span></td>
					<td class="table_td3"><s:textfield name="application1.year" cssClass="input_css_other"/></td>
					<td class="table_td4"></td>
				</tr>
			</s:if>
			<s:else>
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title5"><s:text name="i18n_ProjectYear" />：</span></td>
					<td class="table_td3"><s:textfield name="year" cssClass="input_css_other" readonly="true"/></td>
					<td class="table_td4"></td>
				</tr>
			</s:else>
		</s:if>
		<s:elseif test="flag == 1">
			<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@MINISTRY) >= 0"><!-- 部级以上 -->
				<tr class="table_tr2">
					<td class="table_td2"><span class="table_title5"><s:text name="i18n_ProjectYear" />：</span></td>
					<td class="table_td3"><s:textfield name="application1.year" cssClass="input_css_other"/></td>
					<td class="table_td4"></td>
				</tr>
			</s:if>
		</s:elseif>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5"><s:text name="i18n_ProjectSubtype" />：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="application1.subtype.id" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{baseService.getSystemOptionMap('projectType', '01')}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_ProjectTopic" />：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="application1.topic.id" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{baseService.getSystemOptionMap('projectTopic', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2">
				<span>
					<s:text name="i18n_LastProductType" />：<br />
					<span class="select_all_box"><s:text name="i18n_SelectAll"/>&nbsp;<input id="checkAllProductTypeItem" name="check" type="checkbox"  title="<s:text name='i18n_ClickSelectAll' />" /></span>
				</span>
			</td>
			<td class="table_td3">
				<s:checkboxlist name="application1.productType" list="%{baseService.getSystemOptionMapAsName('productType', null)}" />
				<span id="productTypeOtherSpan" style="display:none;">
					<s:textfield name="application1.productTypeOther" cssClass="input_css_other"/>
					<br/><span class="tip"><s:text name="i18n_MoreWarn"/></span>
				</span>
				<s:hidden value="%{application1.productType}" id="productType" />
			</td>
			<td class="table_td4" id="profuctTypeOtherTd"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_ApplyDate" />：</span></td>
			<td class="table_td3">
				<input type="text" id="applicantSubmitDate" name="application1.applicantSubmitDate" class="input_css_other" readonly="true" value="<s:date name='%{application1.applicantSubmitDate}' format='yyyy-MM-dd' />" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_PlanEndDate" />：</span></td>
			<td class="table_td3">
				<input type="text" id="planEndDate" name="application1.planEndDate" class="input_css_other" readonly="true" value="<s:date name='%{application1.planEndDate}' format='yyyy-MM-dd' />" />
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5"><s:text name="i18n_ApplyFee" />：</span></td>
			<td class="table_td3"><s:textfield name="application1.applyFee" id="applyFee" cssClass="input_css_other" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_OtherFee" />：</span></td>
			<td class="table_td3"><s:textfield name="application1.otherFee" id="otherFee" cssClass="input_css_other" /></td>
			<td class="table_td4"></td>
		</tr>
		
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="申请经费概算" />：</span></td>
				<s:if test="flag == 0"><%-- 添加 --%>
					<td class="table_td3">
						<input type="button" id="addApplyFee" class="btn1 select_btn" value="<s:text name="添加" />" />
						<div id="totalFee" class="choose_show"><s:property value="projectFeeApply.totalFee"/></div>
					</td>
				</s:if>
				<s:elseif test="flag == 1"><%-- 修改 --%>
					<td class="table_td3">
						<input type="button" id="modifyApplyFee" class="btn1 select_btn" value="<s:text name="修改" />" />
						<div id="totalFee" class="choose_show"><s:property value="projectFeeApply.totalFee"/></div>
					</td>
				</s:elseif>
				<s:hidden id="bookFee" name="projectFeeApply.bookFee" />
				<s:hidden id="bookNote" name="projectFeeApply.bookNote"/>
				<s:hidden id="dataFee" name="projectFeeApply.dataFee" />
				<s:hidden id="dataNote" name="projectFeeApply.dataNote"/>
				<s:hidden id="travelFee" name="projectFeeApply.travelFee" />
				<s:hidden id="travelNote" name="projectFeeApply.travelNote"/>
				<s:hidden id="conferenceFee" name="projectFeeApply.conferenceFee" />
				<s:hidden id="conferenceNote" name="projectFeeApply.conferenceNote"/>
				<s:hidden id="internationalFee" name="projectFeeApply.internationalFee" />
				<s:hidden id="internationalNote" name="projectFeeApply.internationalNote"/>
				<s:hidden id="deviceFee" name="projectFeeApply.deviceFee" />
				<s:hidden id="deviceNote" name="projectFeeApply.deviceNote"/>
				<s:hidden id="consultationFee" name="projectFeeApply.consultationFee" />
				<s:hidden id="consultationNote" name="projectFeeApply.consultationNote"/>
				<s:hidden id="laborFee" name="projectFeeApply.laborFee" />
				<s:hidden id="laborNote" name="projectFeeApply.laborNote"/>
				<s:hidden id="printFee" name="projectFeeApply.printFee" />
				<s:hidden id="printNote" name="projectFeeApply.printNote"/>
				<s:hidden id="indirectFee" name="projectFeeApply.indirectFee" />
				<s:hidden id="indirectNote" name="projectFeeApply.indirectNote"/>
				<s:hidden id="otherFeeD" name="projectFeeApply.otherFee" />
				<s:hidden id="otherNote" name="projectFeeApply.otherNote"/>
				<s:hidden id="totalFeeD" name="projectFeeApply.totalFee"/>
				<s:hidden id="feeNote" name="projectFeeApply.feeNote"/>
			<td class="table_td4"></td>
		</tr>
		
		
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_ResearchType" />：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="application1.researchType.id" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{baseService.getSystemOptionMap('researchType', null)}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5"><s:text name="i18n_DisciplineType" />：</span></td>
			<td class="table_td3">
				<input type="button" id="selectDisciplineType" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
				<s:hidden name="application1.disciplineType" id="discipline" />
				<div id="disptr" class="choose_show" style="display:none"></div>
				</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_Discipline" />：</span></td>
			<td class="table_td3">
				<input type="button" id="selectDiscipline" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
				<s:hidden name="application1.discipline" id="disciplineId" />
				<div id="disp" class="choose_show" style="display:none" ></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_RelativeDiscipline" />：</span></td>
			<td class="table_td3">
				<input type="button" id="selectRelativeDiscipline" class="btn1 select_btn" value="<s:text name="i18n_Select" />" />
				<s:hidden name="application1.relativeDiscipline" id="relativeDisciplineId" />
				<div id="rdsp" class="choose_show" style="display:none" ></div>
			</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_Keywords" />：</span></td>
			<td class="table_td3"><s:textfield name="application1.keywords" cssClass="input_css" />
					<br/><span class="tip"><s:text name="i18n_MoreWarn"/></span></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span><s:text name="i18n_Summary" />：<br/><s:text name='i18n_LimitWordTwo'/></span></td>
			<td class="table_td3"><s:textarea name="application1.summary" rows="6"  cssClass="textarea_css" /></td>
			<td class="table_td4"></td>
		</tr>
		<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@INSTITUTE)>0">
			<tr class="table_tr2">
				<td class="table_td2"><span><s:text name="i18n_Note" />：<br/><s:text name="i18n_LimitWordTwo"/></span></td>
				<td class="table_td3"><s:textarea name="application1.note" rows="2" cssClass="textarea_css"/></td>
				<td class="table_td4"></td>
			</tr>
		</s:if>
	</table>
</div>
<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">