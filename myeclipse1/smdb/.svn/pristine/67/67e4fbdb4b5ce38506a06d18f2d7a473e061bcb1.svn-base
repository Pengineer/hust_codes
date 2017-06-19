<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>

		<body >
			<div style="width:500px;">
				<s:include value="/validateError.jsp" />
				<div style="overflow-y:scroll;height:500px;">
					<s:form id="end_result">
						<s:hidden id="projectid" name="projectid"/>
						<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
						<s:hidden id="endId" name="endId" value=""/>
						<s:hidden id="endStatus" name="endStatus"/>
						<s:hidden id="type" name="type" value="1"/>
						<s:hidden id="modifyFlag" name="modifyFlag" value="0"/>
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td11"><span>上传结项申请书：</span></td>
								<td class="table_td12">
									<input type="file" id="file_add" />
									<s:hidden name="end_file"/>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span>结项经费结算：</span></td>
								<td class="table_td3">
									<input type="button" id="addEndFee" class="btn1 select_btn" value="添加" />
									<div id="totalFee" class="choose_show"><s:property value="projectFeeEnd.totalFee"/></div>
								</td>
									<s:hidden id="feeNote" name="projectFeeEnd.feeNote" />
									<s:hidden id="bookFee" name="projectFeeEnd.bookFee" />
									<s:hidden id="bookNote" name="projectFeeEnd.bookNote"/>
									<s:hidden id="dataFee" name="projectFeeEnd.dataFee" />
									<s:hidden id="dataNote" name="projectFeeEnd.dataNote"/>
									<s:hidden id="travelFee" name="projectFeeEnd.travelFee" />
									<s:hidden id="travelNote" name="projectFeeEnd.travelNote"/>
									<s:hidden id="conferenceFee" name="projectFeeEnd.conferenceFee" />
									<s:hidden id="conferenceNote" name="projectFeeEnd.conferenceNote"/>
									<s:hidden id="internationalFee" name="projectFeeEnd.internationalFee" />
									<s:hidden id="internationalNote" name="projectFeeEnd.internationalNote"/>
									<s:hidden id="deviceFee" name="projectFeeEnd.deviceFee" />
									<s:hidden id="deviceNote" name="projectFeeEnd.deviceNote"/>
									<s:hidden id="consultationFee" name="projectFeeEnd.consultationFee" />
									<s:hidden id="consultationNote" name="projectFeeEnd.consultationNote"/>
									<s:hidden id="laborFee" name="projectFeeEnd.laborFee" />
									<s:hidden id="laborNote" name="projectFeeEnd.laborNote"/>
									<s:hidden id="printFee" name="projectFeeEnd.printFee" />
									<s:hidden id="printNote" name="projectFeeEnd.printNote"/>
									<s:hidden id="indirectFee" name="projectFeeEnd.indirectFee" />
									<s:hidden id="indirectNote" name="projectFeeEnd.indirectNote"/>
									<s:hidden id="otherFeeD" name="projectFeeEnd.otherFee" />
									<s:hidden id="otherNote" name="projectFeeEnd.otherNote"/>
									<s:hidden id="totalFeeD" name="projectFeeEnd.totalFee"/>
									<s:hidden id="fundedFee" name="projectFeeEnd.fundedFee"/>
									<s:hidden id="surplusFee" name="surplusFee"/>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11" width="150"><span class="table_title6">是否申请免鉴定：</span></td>
								<td class="table_td12"><s:radio name="isApplyNoevaluation" cssClass="j_showNoEval" theme="simple" list="#{'1':'是','0':'否'}" value='2' /></td>
								<td class="table_td13" width="100"></td>
							</tr>
							<tr class="table_tr2" id="no_eval_info" style="display:none;">
								<td class="table_td11"><span class="table_title6">免鉴定结果：</span></td>
								<td class="table_td12"><s:radio name="endNoauditResult" theme="simple" list="#{'2':'同意','1':'不同意'}"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span class="table_title6">是否申请优秀成果：</span></td>
								<td class="table_td12"><s:radio name="isApplyExcellent" cssClass="j_showExcellect" theme="simple" list="#{'1':'是','0':'否'}" value='2' /></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2" id="excellect_info" style="display:none;">
								<td class="table_td11"><span class="table_title6">优秀成果结果：</span></td>
								<td class="table_td12"><s:radio name="endExcellentResult" theme="simple" list="#{'2':'同意','1':'不同意','0':'待定'}" value='-1' /></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span>结项成果数量：</span></td>
								<td class="table_td12"><input type="button" class="btn1 select_btn j_editProductType" value="编辑" />
									<div id="endProductInfo" class="choose_show"><s:property value="endProductInfo"/></div>
									<s:hidden name="endProductInfo" />
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span>主要参加人：<br />（不含负责人）</span></td>
								<td class="table_td12"><s:textfield name="endMember" cssClass="input_css"/><br/><span class="tip">多个以分号（即;或；）隔开</span></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span class="table_title6">是否同意结项：</span></td>
								<td class="table_td12"><s:radio cssClass="j_showNumber" name="endResult" theme="simple" list="#{'2':'同意','1':'不同意'}" /></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2" id="number_info" style="display:none;">
								<td class="table_td11"><span class="table_title6">结项证书编号：</span></td>
								<td class="table_td12"><s:textfield id="endCertificate" name="endCertificate"  cssClass="input_css"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span  class="table_title6">结项时间：</span></td>
								<td class="table_td12">
									<s:textfield name="endDate" cssClass="FloraDatepick" readonly="true">
										<s:param name="value">
											<s:date name="endDate" format="yyyy-MM-dd" />
										</s:param>
									</s:textfield>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span>审核意见：</span></td>
								<td class="table_td12"><s:textarea name="endImportedOpinion" rows="2" cssClass="textarea_css" /></td>
								<td class="table_td13"></td>
							</tr>
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 系统管理员或部级 -->
							<tr class="table_tr2">
								<td class="table_td11">审核意见：<br /></span><span>（反馈给负责人）</span></td>
								<td class="table_td12">
								<s:select cssClass="select" id = "auditOption" style = "width:100%" name="auditOption" list="%{baseService.getSystemOptionMap('endinspectionAuditOpinion', '02')}" />
								<s:textarea name="endOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
								<td class="table_td13"></td>
							</tr>
							</s:if>
							<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@INSTITUTE)"> 
							<tr class="table_tr2">
								<td class="table_td11">审核意见：<br /></span><span>（反馈给负责人）</span></td>
								<td class="table_td12">
								<s:textarea name="endOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
								<td class="table_td13"></td>
							</tr>
							</s:elseif>
						</table>
					</s:form>
				</div>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addEndResult" data="2" types="1" type="button" value="保存" />
					<input id="submit" class="btn1 j_addEndResult" data="3" types="1" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/key/endinspection/apply/edit.js','javascript/project/project_share/endinspection/apply/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>