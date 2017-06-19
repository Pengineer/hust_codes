<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_GeneralProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:480px;">
				<s:form id="gra_audit">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="120"><span class="table_title4"><s:text name='是否同意立项计划' />：</span></td>
							<td class="table_td12"><s:radio cssClass="r_type j_showOpinionFeedbackOrNot" name="graAuditResult" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}" /></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name='i18n_AuditOpinion' />：</span></td>
							<td class="table_td12"><s:textarea name="graAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 系统管理员或部级 -->
							<tr class="table_tr2">
								<td class="table_td11"><span><s:text name="i18n_AuditOpinionFeedback" />：<br /><s:text name='i18n_FeedbackToDirector'/></span></td>
								<td class="table_td12"><s:textarea name="graAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
								<td class="table_td13"></td>
							</tr>
						</s:if>
						<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@INSTITUTE)"> 
							<tr class="table_tr2" id="opinion_feedback" style="display:none;">
							<td class="table_td11"><span><s:text name="i18n_AuditOpinionFeedback" />：<br /><s:text name='i18n_FeedbackToDirector'/></span></td>
								<td class="table_td12"><s:textarea id="opinion_feedback" name="graAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
							</tr>
						</s:elseif>
					</table>
					<s:hidden id="deadline" name="deadline" />
					<s:hidden id="graStatus" name="graStatus" />
					<div class="btn_div_view">
						<input id="save" class="btn1 j_modifySave" type="button" value="<s:text name='i18n_Save' />" />
						<input id="submit" class="btn1 j_modifySubmit" type="button" value="<s:text name='i18n_Submit' />" />
						<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/application/granted/audit.js', function(audit) {
					audit.init();
					audit.valid();
				});
			</script>
		</body>
	</s:i18n>
</html>