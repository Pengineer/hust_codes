<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>基地项目</title>
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
							<td class="table_td11" width="130"><span class="table_title5" >是否同意结项：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResultEnd" cssClass="j_showNumber" theme="simple" list="#{'2':'同意','1':'不同意'}" /></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<s:if test="isApplyNoevaluation == 1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5">免鉴定成果：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResultNoevalu" theme="simple" list="#{'2':'同意','1':'不同意'}" /></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<s:if test ="isApplyExcellent == 1">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5">优秀成果结果：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResultExcelle" theme="simple" list="#{'2':'同意','1':'不同意'}" /></td>
							<td class="table_td13"></td>
						</tr>
						</s:if>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：<br />（限200字）</span></td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11">审核意见：<br /></span>（反馈给负责人）<br/>（限200字）</td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2" id="number_info" style="display:none;">
							<td class="table_td11"><span class="table_title5">结项证书编号：</span></td>
							<td class="table_td12"><s:textfield id="endCertificate" name="certificate"  cssClass="input_css"/></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_submitOrNotEndReviewAudit" data="2" type="button" value="保存" />
					<input id="submit" class="btn1 j_submitOrNotEndReviewAudit" data="3" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/instp/endinspection/review/audit.js', function(audit) {
					audit.init();
				});
			</script>
		</body>
	
</html>