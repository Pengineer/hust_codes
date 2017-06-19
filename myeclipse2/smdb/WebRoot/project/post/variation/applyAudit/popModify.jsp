<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>后期资助项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div style="width:480px;">
				<s:form id="var_audit">
					<s:hidden name="varId"/>
					<s:hidden id="varAuditSelectIssue" name="varAuditSelectIssue"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="120"><span class="table_title4">是否同意变更：</span></td>
							<td class="table_td12"><s:radio cssClass="r_type j_showOpinionFeedbackOrNot" name="varAuditResult" theme="simple" list="#{'2':getText('同意'),'1':getText('不同意')}"/></td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2" id="var_result_item_tr" style="display:none">
							<td class="table_td11" width="120">
								<span class="table_title4">
									同意变更事项：</br>
									<span class="select_all_box">全选&nbsp;<input id="checkAllVarItem" name="check" type="checkbox"  title="点击全选" onclick="checkAll(this.checked, 'varSelectIssue');"/></span>
								</span>
							</td>
							<td class="table_td12">
								<table id="var_result_item_list" width="100%" border="0" cellspacing="0" cellpadding="2">
									<s:iterator value="varItems" status="stat">
										<s:if test="(#stat.index)%2 == 0"><tr></s:if>
										<td width="150" >
											<input id="<s:property value='varItems[#stat.index][0]' />" type="checkbox" name="varSelectIssue" value="<s:property value='varItems[#stat.index][0]' />" onclick="checkAllOrNot(this.checked, 'varSelectIssue', 'checkAllVarItem');"/>
											<s:property value="varItems[#stat.index][1]" />
										</td>
										<s:if test="(#stat.index+1)%2 == 0"></tr></s:if>
									</s:iterator>
								</table>
							</td>
							<td class="table_td13" width="90"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：<br />（限200字）</span></td>
							<td class="table_td12"><s:textarea name="varAuditOpinion" rows="2" cssClass="textarea_css" /></td>
							<td class="table_td13"></td>
						</tr>
						<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 系统管理员或部级 -->
							<tr class="table_tr2">
								<td class="table_td11"><span>审核意见：<br /><span>（反馈给负责人）</span>（限200字）</span></td>
								<td class="table_td12"><s:textarea name="varAuditOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
								<td class="table_td13"></td>
							</tr>
						</s:if>
						<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@INSTITUTE)"> 
							<tr class="table_tr2" id="opinion_feedback" style="display:none;">
							    <td class="table_td11"><span>审核意见：<br /><span>（反馈给负责人）</span>（限200字）</span></td>
								<td class="table_td12"><s:textarea id="opinion_feedback" name="varAuditOpinionFeedback" rows="2" cssClass="textarea_css"  /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							    <td class="table_td13"></td>
							</tr>
						</s:elseif>
					</table>
				<s:hidden id="deadline" name="deadline" />
				<s:hidden id="varStatus" name="varStatus" />
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_modifyVarAudit" data=2 type="button" value="保存" />
					<input id="submit" class="btn1 j_modifyVarAudit" data=3 type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/variation/audit.js', function(audit) {
					audit.init();
					audit.valid();
				});
			</script>
		</body>
	
</html>