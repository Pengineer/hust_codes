<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
		</head>
		
		<body>
			<div style="width:480px;">
				<s:form id="app_audit">
				<s:hidden id="deadline" name="deadline" />
				<s:hidden id="appStatus" name="appStatus" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="130"><span class="table_title5">是否同意立项：</span></td>
							<td class="table_td12"><s:radio cssClass="r_type j_showOpinionFeedbackOrNot" name="appAuditResult" theme="simple" list="#{'2':getText('同意'),'1':getText('不同意')}" /></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：</span></td>
							<td class="table_td12"><s:textarea name="appAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2" id="opinion_feedback" style="display:none;">
							<td class="table_td11"><span>审核意见：<br />（反馈给负责人）<br/>（限200字）</span></td>
							<td class="table_td12"><s:textarea name="appAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
					</table>
					<div class="btn_div_view">
						<input id="save" class="btn1 j_modifySave" type="button" value="保存" />
						<input id="submit" class="btn1 j_modifySubmit" type="button" value="提交" />
						<input id="cancel" class="btn1" type="button" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/application/apply/audit.js', function(audit) {
					audit.init();
					audit.valid();
				});
			</script>
		</body>
	
</html>