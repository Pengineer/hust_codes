<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div style="width:450px;">
			<s:if test="audflag == 1">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td width="15"></td>
						<td class="text_red">此次操作对选中的已提交审核结果的奖励申请无效！</td>
					</tr>
				</table>
			</s:if>
			<s:form id="award_audit">
				<s:hidden name="audflag"/>
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="100"><span class="table_title2">审核结果：</span></td>
						<td class="table_td3"><s:radio name="auditResult" theme="simple" list="#{'2':'同意','1':'不同意'}" /></td>
						<td class="table_td4" width="90"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td2"><span>审核意见：<br />（限200字）</span></td>
						<td class="table_td3"><s:textarea name="auditOpinion" rows="2" cssClass="textarea_css" /></td>
						<td class="table_td4"></td>
					</tr>
					<tr class="table_tr2" id="opinion_feedback" style="display:none;">
						<td class="table_td11"><span>审核意见：<br />（反馈给负责人）<br />（限200字）</span></td>
						<td class="table_td12"><s:textarea name="auditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
						<td class="table_td13"></td>
					</tr>
				</table>
			</s:form>
			<div class="btn_div_view">
				<input id="save" class="btn1 j_addSave" type="button" value="保存"  />
				<input id="submit" class="btn1 j_addSubmit" type="button" value="提交" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/apply/audit.js', function(audit) {
				audit.init();
				audit.initClick();
			});
		</script>
	</body>
</html>