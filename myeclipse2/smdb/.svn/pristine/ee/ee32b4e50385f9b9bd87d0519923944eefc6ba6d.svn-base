<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_InstpProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body >
			<div style="width:480px;">
				<s:form id="end_audit">
					<s:hidden name="projectid" />
					<s:hidden id="isApplyNoevaluation" name="isApplyNoevaluation"/>
					<s:hidden id="isApplyExcellent" name="isApplyExcellent"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="130"><span class="table_title5"><s:text name='i18n_EndinspectionResult' />：</span></td>
							<td class="table_td12"><s:radio name="endAuditResult" cssClass="j_showOpinionFeedbackOrNot" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}" /></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<s:if test="isApplyNoevaluation == 1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5"><s:text name='i18n_NoEvalResult' />：</span></td>
							<td class="table_td12"><s:radio name="endNoauditResult" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}"/></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<s:if test="isApplyExcellent ==1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5"><s:text name='i18n_ExcellentResult' />：</span></td>
							<td class="table_td12"><s:radio name="endExcellentResult" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}"/></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name='i18n_AuditOpinion' />：</span></td>
							<td class="table_td12"><s:textarea name="endAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2" id="opinion_feedback" style="display:none;">
							<td class="table_td11"><span><s:text name="i18n_AuditOpinionFeedback"/>：<br /></span><br/><s:text name='i18n_FeedbackToDirector'/><br/><s:text name="i18n_LimitWordTwo"/></td>
							<td class="table_td12"><s:textarea id="opinion_feedback" name="endAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
					</table>
					<s:hidden id="deadline" name="deadline" />
					<s:hidden id="endStatus" name="endStatus" />
					<div class="btn_div_view">
						<input id="save" class="btn1 j_addEndAudit" data="2" type="button" value="<s:text name='i18n_Save' />" />
						<input id="submit" class="btn1 j_addEndAudit" data="3" type="button" value="<s:text name='i18n_Submit' />" />
						<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/endinspection/apply/audit.js', function(audit) {
					audit.init();
					audit.valid();
				});
			</script>
		</body>
	</s:i18n>
</html>