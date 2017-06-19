<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>
		
		<body>
			<div style="width:580px; height:auto">
				<s:include value="/validateError.jsp" />
				<s:form id="endFile">
					<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td14" width="130"><span class="table_title6"><s:text name='i18n_UploadEndApply' />：</span></td>
							<td class="table_td15">
								<input type="file" id="file_add" />
								<s:hidden name="end_file"/>
							</td>
							<td class="table_td16" width="80"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span><s:text name="结项经费结算" />：</span></td>
							<td class="table_td3">
								<input type="button" id="addEndFeeApply" class="btn1 select_btn" value="<s:text name="添加" />" />
								<div id="totalFee" class="choose_show"><s:property value="projectFeeEnd.totalFee"/></div>
							</td>
								<s:hidden id="feeNote" name="projectFeeEnd.feeNote" />
								<s:hidden id="bookFee" name="projectFeeEnd.bookFee" />
								<s:hidden id="bookNote" name="projectFeeEnd.bookNote"/>
								<s:hidden id="dataFee" name="projectFeeEnd.dataFee" />
								<s:hidden id="dataNote" name="projectFeeEnd.dataNote"/>
								<s:hidden id="travelFee" name="projectFeeEnd.travelFee" />
								<s:hidden id="travelNote" name="projectFeeEnd.travelNote"/>
								<s:hidden id="conferenceFee" name="projectFeeEnd.conferenceFee" />
								<s:hidden id="conferenceNote" name="projectFeeEnd.conferenceNote"/>
								<s:hidden id="internationalFee" name="projectFeeEnd.internationalFee" />
								<s:hidden id="internationalNote" name="projectFeeEnd.internationalNote"/>
								<s:hidden id="deviceFee" name="projectFeeEnd.deviceFee" />
								<s:hidden id="deviceNote" name="projectFeeEnd.deviceNote"/>
								<s:hidden id="consultationFee" name="projectFeeEnd.consultationFee" />
								<s:hidden id="consultationNote" name="projectFeeEnd.consultationNote"/>
								<s:hidden id="laborFee" name="projectFeeEnd.laborFee" />
								<s:hidden id="laborNote" name="projectFeeEnd.laborNote"/>
								<s:hidden id="printFee" name="projectFeeEnd.printFee" />
								<s:hidden id="printNote" name="projectFeeEnd.printNote"/>
								<s:hidden id="indirectFee" name="projectFeeEnd.indirectFee" />
								<s:hidden id="indirectNote" name="projectFeeEnd.indirectNote"/>
								<s:hidden id="otherFeeD" name="projectFeeEnd.otherFee" />
								<s:hidden id="otherNote" name="projectFeeEnd.otherNote"/>
								<s:hidden id="totalFeeD" name="projectFeeEnd.totalFee"/>
								<s:hidden id="fundedFee" name="projectFeeEnd.fundedFee"/>
								<s:hidden id="surplusFee" name="surplusFee"/>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><span class="table_title6"><s:text name='i18n_IsApplyNoEval' />：</span></td> 
							<td class="table_td15">
								<s:radio name="isApplyNoevaluation" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" value='2'/>
							</td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><span class="table_title6"><s:text name='i18n_IsApplyExcellent' />：</span></td>
							<td class="table_td15">
								<s:radio name="isApplyExcellent" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" value='2'/>
							</td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14" valign="top"><s:text name='i18n_Note' />：<br/><s:text name="i18n_LimitWordTwo"/></td>
							<td class="table_td15"><s:textarea name="note" id="endnote" rows="2" cssClass="textarea_css"/></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><span class="table_title6"><s:text name='i18n_UploadEndData' />：</span></td>
							<td class="table_td15">
								<input type="file" id="file_research_add" />
								<s:hidden name="research_file"/>
							</td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><span class="table_title6"><s:text name="i18n_ResearchKeywords"/>：</span></td>
							<td class="table_td15" id="err_keyWord"><s:textfield name="projectData.keywords" cssClass="input_css"/>
									<br/><span class="tip"><s:text name="i18n_MoreWarn"/></span></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14" valign="top"><span class="table_title6"><s:text name='i18n_ResearchSummary' />：<br/><s:text name="i18n_LimitWordTwo"/></span></td>
							<td class="table_td15"><s:textarea name="projectData.summary" rows="2" cssClass="textarea_css"/></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14" valign="top"><span class="table_title6"><s:text name='i18n_ResearchIntroduction' />：</span></td>
							<td class="table_td15"><s:textarea name="projectData.introduction" rows="2" cssClass="textarea_css"/></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><s:text name='i18n_SurveyMethod' />：</td>
							<td class="table_td15"><s:textfield name="projectData.surveyMethod" cssClass="input_css"/></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><s:text name='i18n_SurveyField'/>：</td>
							<td class="table_td15"><s:textfield name="projectData.surveyField" cssClass="input_css"/></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><s:text name='i18n_SurveyStartDate' />：</td>
							<td class="table_td15"><s:textfield name="projectData.startDate" cssClass="FloraDatepick" readonly="true"/></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14"><s:text name='i18n_SurveyEndDate' />：</td>
							<td class="table_td15"><s:textfield name="projectData.endDate" cssClass="FloraDatepick" readonly="true"/></td>
							<td class="table_td16"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td14" valign="top"><s:text name='i18n_ResearchNote' />：<br/><s:text name="i18n_LimitWordTwo"/></td>
							<td class="table_td15"><s:textarea name="projectData.note" rows="2" cssClass="textarea_css" cols="40"/></td>
							<td class="table_td16"></td>
						</tr>
					</table>
					<s:hidden name="uploadEndApply" id="uploadEndApply"/>
					<s:hidden id="end_flag" value="0"/>
					<s:hidden id="endFileProjectid" name="projectid"/>
					<s:hidden id="endApplicantSubmitStatus" name="endApplicantSubmitStatus"/>
					<s:hidden id="deadline" name="deadline"/>
					<s:hidden id="timeFlag" name="timeFlag" />
					<s:hidden id="endStatus" name="endStatus" />
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addEndApply" data="2" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_addEndApply" data="3" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				<s:if test="timeFlag == 0">
					(function(){
						alert("当前时间已经超过个人申请截止时间！");
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.destroy();
					})();
				</s:if>
				<s:if test="null != endFlag && endFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer);
					})();
				</s:if>
				seajs.use(['javascript/project/entrust/endinspection/apply/edit.js','javascript/project/project_share/endinspection/apply/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>