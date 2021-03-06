<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body >
			<div style="width:480px;">
				<s:form id="end_audit">
					<s:hidden name="projectid" />
					<s:hidden id="isApplyNoevaluation" name="isApplyNoevaluation"/>
					<s:hidden id="isApplyExcellent" name="isApplyExcellent"/>
					<s:hidden id="deadline" name="deadline" />
					<s:hidden id="endStatus" name="endStatus" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="130"><span class="table_title5">是否同意结项：</span></td>
							<td class="table_td12"><s:radio name="endAuditResult" cssClass="j_showOpinionFeedbackOrNot" theme="simple" list="#{'2':'同意','1':'不同意'}" /></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<s:if test="isApplyNoevaluation == 1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5">免鉴定成果：</span></td>
							<td class="table_td12"><s:radio name="endNoauditResult" theme="simple" list="#{'2':'同意','1':'不同意'}"/></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<s:if test="isApplyExcellent ==1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5">优秀成果结果：</span></td>
							<td class="table_td12"><s:radio name="endExcellentResult" theme="simple" list="#{'2':'同意','1':'不同意'}"/></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：</span></td>
							<td class="table_td12"><s:textarea name="endAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 系统管理员或部级 -->
						<tr class="table_tr2" id="opinion_feedback">
							<td class="table_td11">审核意见：<br />（反馈给负责人）</span></td>
							<td class="table_td12">
							<s:select cssClass="select" id = "auditOption" name="auditOption" list="%{baseService.getSystemOptionMap('endinspectionAuditOpinion', '02')}" />
							<s:textarea name="endAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@INSTITUTE)"> 
						<tr class="table_tr2" id="opinion_feedback" style="display:none;">
							<td class="table_td11">审核意见：<br />（反馈给负责人）</span></td>
							<td class="table_td12"><s:textarea name="endAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
						</s:elseif>
					</table>
					<div class="btn_div_view">
						<input id="save" class="btn1 j_addEndAudit" type="button" data="2" value="保存"  />
						<input id="submit" class="btn1 j_addEndAudit" type="button" data="3" value="提交" />
<%--						<input id="save" class="btn1" type="button" value="保存" onclick="addEndAudit(2, thisPopLayer);" />--%>
<%--						<input id="submit" class="btn1" type="button" value="提交" onclick="addEndAudit(3, thisPopLayer);" />--%>
						<input id="cancel" class="btn1" type="button" value="取消" />
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
	
</html>