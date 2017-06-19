<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>

		<body>
			<div style="width:450px;">
				<s:form id="graFile">
					<s:hidden id="graId" name="graId"/>
					<s:hidden id="projectid" name="projectid"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title4"><s:text name='上传立项计划书' />：</span></td>
							<td class="table_td12">
								<input type="file" id="file_add" />
								<s:hidden name="gra_file"/>
							</td>
							<td class="table_td13"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2"><span><s:text name="立项经费预算" />：</span></td>
							<td class="table_td3">
								<input type="button" id="addGrantedFee" class="btn1 select_btn" value="<s:text name="添加" />" />
								<div id="totalFee" class="choose_show"><s:property value="projectFeeGranted.totalFee"/></div>
							</td>
								<s:hidden id="bookFee" name="projectFeeGranted.bookFee" />
								<s:hidden id="bookNote" name="projectFeeGranted.bookNote"/>
								<s:hidden id="dataFee" name="projectFeeGranted.dataFee" />
								<s:hidden id="dataNote" name="projectFeeGranted.dataNote"/>
								<s:hidden id="travelFee" name="projectFeeGranted.travelFee" />
								<s:hidden id="travelNote" name="projectFeeGranted.travelNote"/>
								<s:hidden id="conferenceFee" name="projectFeeGranted.conferenceFee" />
								<s:hidden id="conferenceNote" name="projectFeeGranted.conferenceNote"/>
								<s:hidden id="internationalFee" name="projectFeeGranted.internationalFee" />
								<s:hidden id="internationalNote" name="projectFeeGranted.internationalNote"/>
								<s:hidden id="deviceFee" name="projectFeeGranted.deviceFee" />
								<s:hidden id="deviceNote" name="projectFeeGranted.deviceNote"/>
								<s:hidden id="consultationFee" name="projectFeeGranted.consultationFee" />
								<s:hidden id="consultationNote" name="projectFeeGranted.consultationNote"/>
								<s:hidden id="laborFee" name="projectFeeGranted.laborFee" />
								<s:hidden id="laborNote" name="projectFeeGranted.laborNote"/>
								<s:hidden id="printFee" name="projectFeeGranted.printFee" />
								<s:hidden id="printNote" name="projectFeeGranted.printNote"/>
								<s:hidden id="indirectFee" name="projectFeeGranted.indirectFee" />
								<s:hidden id="indirectNote" name="projectFeeGranted.indirectNote"/>
								<s:hidden id="otherFeeD" name="projectFeeGranted.otherFee" />
								<s:hidden id="otherNote" name="projectFeeGranted.otherNote"/>
								<s:hidden id="totalFeeD" name="projectFeeGranted.totalFee"/>
								<s:hidden id="feeNote" name="projectFeeGranted.feeNote"/>
							<td class="table_td4"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td11" width="120"><span class="table_title4"><s:text name="是否同意立项计划" />：</span></td>
							<td class="table_td12"><s:radio name="graResult" theme="simple" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove')}"/></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span  class="table_title4"><s:text name="审核时间" />：</span></td>
							<td class="table_td12">
								<s:textfield name="graDate" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="graDate" format="yyyy-MM-dd" />
									</s:param>
								</s:textfield>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><s:text name='i18n_AuditOpinion'/>：</td>
							<td class="table_td12"><s:textarea name='graImportedOpinion' rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span><s:text name="i18n_AuditOpinionFeedback" />：<br /></span><span><s:text name='i18n_FeedbackToDirector'/></span></td>
							<td class="table_td12"><s:textarea name="graOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addResultSave" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_addResultSubmit" type="button" value="<s:text name='i18n_Submit' />"/>
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/entrust/application/granted/edit.js','javascript/project/project_share/application/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>
