<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>

		<body >
			<div style="width:500px;">
				<s:include value="/validateError.jsp" />
				<div style="overflow-y:scroll;height:500px; *margin-right:20px; _margin-right:20px;">
					<s:form id="end_result">
						<s:hidden id="projectid" name="projectid"/>
						<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
						<s:hidden id="endId" name="endId" value="%{#request.endId}"/>
						<s:hidden id="endStatus" name="endStatus"/>
						<s:hidden id="type" name="type" value="2"/>
						<s:hidden id="modifyFlag" name="modifyFlag"/>
						<s:hidden id="varPending" name="varPending"/>
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td11"><span class="table_title6"><s:text name='i18n_UploadEndApply' />：</span></td>
								<td class="table_td12">
									<input type="file" id="file_${endId}" />
									<s:hidden name="end_file"/>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title6"><s:text name="结项经费结算" />：</span></td>
								<td class="table_td3">
									<input type="button" id="modifyEndFee" class="btn1 select_btn" value="<s:text name="修改" />" />
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
								<td class="table_td11" width="150"><span class="table_title6"><s:text name="i18n_IsApplyNoEval" />：</span></td>
								<td class="table_td12"><s:radio cssClass="j_showNoEval" name="isApplyNoevaluation" theme="simple" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" /></td>
								<td class="table_td13" width="100"></td>
							</tr>
							<tr class="table_tr2" id="no_eval_info" style="display:none;">
								<td class="table_td11"><span class="table_title6"><s:text name="i18n_NoEvalResult"/>：</span></td>
								<td class="table_td12"><s:radio name="endNoauditResult" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span class="table_title6"><s:text name="i18n_IsApplyExcellent"/>：</span></td>
								<td class="table_td12"><s:radio name="isApplyExcellent" theme="simple" cssClass="j_showExcellect" list="#{'1':getText('i18n_Yes'),'0':getText('i18n_No')}" /></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2" id="excellect_info" style="display:none;">
								<td class="table_td11"><span class="table_title6"><s:text name="i18n_ExcellentResult"/>：</span></td>
								<td class="table_td12"><s:radio name="endExcellentResult" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove'),'0':getText('i18n_Pending2')}"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><s:text name="i18n_EndinspectionProductNumber"/>：</td>
								<td class="table_td12"><input type="button" class="btn1 select_btn j_editProductType" value="<s:text name='i18n_Edit'/>" />
									<div id="endProductInfo" class="choose_show"><s:property value="endProductInfo"/></div>
									<s:hidden name="endProductInfo" /></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><s:text name="i18n_Member" />：<br><s:text name="i18n_NoContainDirector"/></td>
								<td class="table_td12"><s:textfield name="endMember"  cssClass="input_css"/><br/><span class="tip"><s:text name="i18n_MoreWarn"/></span></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span class="table_title6"><s:text name="i18n_EndinspectionResult"/>：</span></td>
								<td class="table_td12"><s:radio name="endResult" cssClass="j_showNumber" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}" /></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2" id="number_info" style="display:none;">
								<td class="table_td11"><span class="table_title6"><s:text name="i18n_EndNumber" />：</span></td>
								<td class="table_td12"><s:textfield id="endCertificate" name="endCertificate"  cssClass="input_css"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span  class="table_title6"><s:text name="i18n_EndinspectionDate" />：</span></td>
								<td class="table_td12">
									<s:textfield name="endDate" cssClass="FloraDatepick" readonly="true">
										<s:param name="value">
											<s:date name="endDate" format="yyyy-MM-dd" />
										</s:param>
									</s:textfield>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span><s:text name='i18n_AuditOpinion' />：</span></td>
								<td class="table_td12"><s:textarea name="endImportedOpinion" rows="2" cssClass="textarea_css" /></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span><s:text name='i18n_AuditOpinionFeedback'/>：</span><br/><span><s:text name='i18n_FeedbackToDirector'/></span></td>
								<td class="table_td12"><s:textarea name="endOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
								<td class="table_td13"></td>
							</tr>
						</table>
					</s:form>
				</div>
				<div class="btn_div_view">
					<s:if test="modifyFlag != 1">
						<input id="save" class="btn1 j_addEndResult" data="2" types="2" type="button" value="<s:text name='i18n_Save' />"  />
					</s:if>
					<s:if test="varPending == 0">
						<input id="submit" class="btn1 j_addEndResult" data="3" types="2" type="button" value="<s:text name='i18n_Submit' />"  />
					</s:if>
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/instp/endinspection/apply/edit.js','javascript/project/project_share/endinspection/apply/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>