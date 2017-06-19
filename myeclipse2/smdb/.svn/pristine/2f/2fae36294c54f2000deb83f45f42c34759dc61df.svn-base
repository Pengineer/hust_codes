<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_InstpProject" /></title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>
		
		<body>
			<div style="width:480px;">
				<s:include value="/validateError.jsp" />
				<s:form id="annFile">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="100"><span class="table_title2"><s:text name='i18n_UploadAnnApply' />：</span></td>
							<td class="table_td12">
								<input type="file" id="file_add" />
								<s:hidden name="ann_file"/>
							</td>
							<td class="table_td13" width="80"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span><s:text name="年检经费结算" />：</span></td>
							<td class="table_td3">
								<input type="button" id="addAnnFeeApply" class="btn1 select_btn" value="<s:text name="添加" />" />
								<div id="totalFee" class="choose_show"><s:property value="projectFeeAnn.totalFee"/></div>
							</td>
								<s:hidden id="feeNote" name="projectFeeAnn.feeNote" />
								<s:hidden id="bookFee" name="projectFeeAnn.bookFee" />
								<s:hidden id="bookNote" name="projectFeeAnn.bookNote"/>
								<s:hidden id="dataFee" name="projectFeeAnn.dataFee" />
								<s:hidden id="dataNote" name="projectFeeAnn.dataNote"/>
								<s:hidden id="travelFee" name="projectFeeAnn.travelFee" />
								<s:hidden id="travelNote" name="projectFeeAnn.travelNote"/>
								<s:hidden id="conferenceFee" name="projectFeeAnn.conferenceFee" />
								<s:hidden id="conferenceNote" name="projectFeeAnn.conferenceNote"/>
								<s:hidden id="internationalFee" name="projectFeeAnn.internationalFee" />
								<s:hidden id="internationalNote" name="projectFeeAnn.internationalNote"/>
								<s:hidden id="deviceFee" name="projectFeeAnn.deviceFee" />
								<s:hidden id="deviceNote" name="projectFeeAnn.deviceNote"/>
								<s:hidden id="consultationFee" name="projectFeeAnn.consultationFee" />
								<s:hidden id="consultationNote" name="projectFeeAnn.consultationNote"/>
								<s:hidden id="laborFee" name="projectFeeAnn.laborFee" />
								<s:hidden id="laborNote" name="projectFeeAnn.laborNote"/>
								<s:hidden id="printFee" name="projectFeeAnn.printFee" />
								<s:hidden id="printNote" name="projectFeeAnn.printNote"/>
								<s:hidden id="indirectFee" name="projectFeeAnn.indirectFee" />
								<s:hidden id="indirectNote" name="projectFeeAnn.indirectNote"/>
								<s:hidden id="otherFeeD" name="projectFeeAnn.otherFee" />
								<s:hidden id="otherNote" name="projectFeeAnn.otherNote"/>
								<s:hidden id="totalFeeD" name="projectFeeAnn.totalFee"/>
								<s:hidden id="fundedFee" name="projectFeeAnn.fundedFee"/>
								<s:hidden id="surplusFee" name="surplusFee"/>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span class="table_title2"><s:text name="i18n_AnninspectionYear" />：</span></td>
							<td class="table_td12"><s:textfield id="annYear" name="annYear" cssClass="input_css" readonly="true"/></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><s:text name='i18n_Note' />：<br/><s:text name="i18n_LimitWordTwo"/></td>
							<td class="table_td12"><s:textarea name="note" id="annnote" rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
					</table>
					<s:hidden id="ann_flag" value="0"/>
					<s:hidden id="annFileProjectid" name="projectid"/>
					<s:hidden id="annId" name="annId" value=""/>
					<s:hidden id="annApplicantSubmitStatus" name="annApplicantSubmitStatus"/>
					<s:hidden id="deadline" name="deadline" />
					<s:hidden id="timeFlag" name="timeFlag" />
					<s:hidden id="annStatus" name="annStatus" />
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addSave" type="button" value="<s:text name='i18n_Save' />" />
					<input id="submit" class="btn1 j_addSubmit" type="button" value="<s:text name='i18n_Submit' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				<s:if test="timeFlag == 0">
					(function(){
						alert("当前时间已经超过个人申请截止时间！");
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.destroy();
					})();
				</s:if>
				<s:if test="null != annFlag && annFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer);
					})();
				</s:if>
				seajs.use(['javascript/project/instp/anninspection/edit.js','javascript/project/project_share/anninspection/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	</s:i18n>
</html>