<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
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
							<td class="table_td11" width="130"><span class="table_title5">是否同意立项：</span></td>
							<td class="table_td12"><s:radio name="reviewAuditResult" theme="simple" list="#{'2':'同意','1':'不同意'}" cssClass="j_showNumberAndFee"/></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title5">批准时间：</span></td>
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
							<td class="table_td11"><span class="table_title5">批准号：</span></td>
							<td class="table_td12"><s:textfield name="number" id="number" cssClass="input_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2 granted" style="display:none;">
							<td class="table_td11"><span>批准经费（万）：</span></td>
							<td class="table_td12"><s:textfield name="approveFee" cssClass="input_css"/></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：<br />（限2000字）</span></td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：<br />（反馈给负责人）<br/>（限200字）</span></td>
							<td class="table_td12"><s:textarea name="reviewAuditOpinionFeedback" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addOrModifySave" type="button" value="保存" />
					<input id="submit" class="btn1 j_addOrModifySubmit" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/key/application/review/audit.js', function(audit) {
					audit.init();
				});
			</script>
		</body>
	
</html>