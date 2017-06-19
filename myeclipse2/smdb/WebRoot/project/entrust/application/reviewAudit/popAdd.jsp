<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div style="width:480px;">
				<s:form id="app_review_audit">
					<s:hidden id="entityId" name="entityId"/>
					<s:hidden id="reviewAuditStatus" name="reviewAuditStatus"/>
					<s:hidden id="type" name="type" value='1'/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="130"><span class="table_title5"><s:text name="i18n_ApplicationResult"></s:text>：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResult" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}" cssClass="j_showNumberAndFee"/></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5"><s:text name="i18n_ApproveDate" />：</span></td>
							<td class="table_td12">
								<s:textfield name="reviewAuditDate" id="approveDate"cssClass="FloraDatepick" readonly="true" >
									<s:param name="value">
										<s:date name="reviewAuditDate" format="yyyy-MM-dd" />
									</s:param>
								</s:textfield>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2 granted" style="display:none;">
							<td class="table_td11"><span class="table_title5"><s:text name="i18n_ProjectNumber" />：</span></td>
							<td class="table_td12"><s:textfield name="number" id="number" cssClass="input_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2 granted" style="display:none;">
							<td class="table_td11"><span><s:text name="i18n_ApproveFee" />：</span></td>
							<td class="table_td12"><s:textfield name="approveFee" cssClass="input_css"/></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name="i18n_AuditOpinion" />：<br /><s:text name="i18n_LimitWordTwoThousand"/></span></td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name="i18n_AuditOpinionFeedback" />：<br /><s:text name='i18n_FeedbackToDirector'/><br/><s:text name="i18n_LimitWordTwo"/></span></td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinionFeedback" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addOrModifySave" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_addOrModifySubmit" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/entrust/application/review/audit.js', function(audit) {
					audit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>