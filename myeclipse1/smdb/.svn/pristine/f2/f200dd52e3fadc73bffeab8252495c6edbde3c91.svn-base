<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div style="width:450px;">
			<s:form id="award_audit">
				<s:hidden id="entityId" name="entityId"/>
				<s:hidden id="audflag" name="audflag"/>
				<s:hidden name="auditStatus"/>
				<s:hidden name="type" value="2"/>
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="100"><span class="table_title2">审核结果：</span></td>
						<td class="table_td3"><s:radio name="auditResult" theme="simple" list="#{'2':'同意','1':'不同意'}"/></td>
						<td class="table_td4" width="90"></td>
					</tr>
				</table>
				<div id="div_awarded" style="display:none">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="100"><span class="table_title2">获奖年度：</span></td>
							<td class="table_td3"><s:textfield cssClass="select" id="year" name="year" cssClass="input_css"/></td>
							<td class="table_td4" width="90"></td>
						</tr>
						<s:if test="audflag == 0">
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title2">证书编号：</span></td>
								<td class="table_td3"><s:textfield id="number" name="number"  cssClass="input_css"/></td>
								<td class="table_td4"></td>
							</tr>
						</s:if>
					</table>
				</div>
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="table_tr2">
						<td class="table_td2" width="100"><span class="table_title2">审核意见：<br />（限200字）</span></td>
						<td class="table_td3"><s:textarea name="auditOpinion" rows="2" cssClass="textarea_css" cols="30" /></td>
						<td class="table_td4" width="90"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td11"><span class="table_title2">审核意见：<br />（反馈给负责人）<br/>（限200字）</span></td>
						<td class="table_td12"><s:textarea name="auditOpinionFeedback" rows="2" cssClass="textarea_css" /></td>
						<td class="table_td13"></td>
					</tr>
				</table>
			</s:form>
			<div class="btn_div_view">
				<input id="awardAuditSave" class="btn1" type="button" value="保存" /></li>
				<input id="awardAuditSubmit" class="btn1" type="button" value="提交" /></li>
				<input id="cancel" class="btn1" type="button" value="取消" /></li>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/award/moesocial/application/publicity/audit.js', function(audit) {
				audit.init();
			});
		</script>
	</body>
</html>