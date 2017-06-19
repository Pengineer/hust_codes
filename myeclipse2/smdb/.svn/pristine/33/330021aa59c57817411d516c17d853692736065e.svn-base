<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body >
			<div style="width:480px;">
				<s:form id="end_review_audit">
					<s:hidden id="endId" name="endId"/>
					<s:hidden id="reviewAuditStatus" name="reviewAuditStatus"/>
					<s:hidden id="isApplyNoevaluation" name="isApplyNoevaluation"/>
					<s:hidden id="isApplyExcellent" name="isApplyExcellent"/>
					<s:hidden id="type" name="type" value='1'/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="130"><span class="table_title5" ><s:text name='i18n_EndinspectionResult' />：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResultEnd" cssClass="j_showNumber" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}" /></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<s:if test="isApplyNoevaluation == 1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5"><s:text name='i18n_NoEvalResult' />：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResultNoevalu" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}" /></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<s:if test ="isApplyExcellent == 1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5"><s:text name='i18n_ExcellentResult' />：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResultExcelle" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}" /></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name="i18n_AuditOpinion" />：<br /><s:text name="i18n_LimitWordTwo"/></span></td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name="i18n_AuditOpinionFeedback" />：<br /><s:text name='i18n_FeedbackToDirector'/><br/><s:text name="i18n_LimitWordTwo"/></span></td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinionFeedback" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2" id="number_info" style="display:none;">
							<td class="table_td11"><span class="table_title5"><s:text name="i18n_EndNumber" />：</span></td>
							<td class="table_td12"><s:textfield id="endCertificate" name="certificate"  cssClass="input_css"/></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_submitOrNotEndReviewAudit" data="2" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_submitOrNotEndReviewAudit" data="3" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/entrust/endinspection/review/audit.js', function(audit) {
					audit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>