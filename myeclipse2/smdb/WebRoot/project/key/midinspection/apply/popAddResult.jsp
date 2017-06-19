<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>

		<body>
			<div style="width:450px;">
				<s:form id="mid_result">
					<s:hidden id="projectid" name="projectid"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11"><span>上传中检申请书：</span></td>
							<td class="table_td12">
								<input type="file" id="file_add" />
								<s:hidden name="mid_file"/>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span><s:text name="中检经费结算" />：</span></td>
							<td class="table_td3">
								<input type="button" id="addMidFee" class="btn1 select_btn" value="<s:text name="添加" />" />
								<div id="totalFee" class="choose_show"><s:property value="projectFeeMid.totalFee"/></div>
							</td>
								<s:hidden id="feeNote" name="projectFeeMid.feeNote" />
								<s:hidden id="bookFee" name="projectFeeMid.bookFee" />
								<s:hidden id="bookNote" name="projectFeeMid.bookNote"/>
								<s:hidden id="dataFee" name="projectFeeMid.dataFee" />
								<s:hidden id="dataNote" name="projectFeeMid.dataNote"/>
								<s:hidden id="travelFee" name="projectFeeMid.travelFee" />
								<s:hidden id="travelNote" name="projectFeeMid.travelNote"/>
								<s:hidden id="conferenceFee" name="projectFeeMid.conferenceFee" />
								<s:hidden id="conferenceNote" name="projectFeeMid.conferenceNote"/>
								<s:hidden id="internationalFee" name="projectFeeMid.internationalFee" />
								<s:hidden id="internationalNote" name="projectFeeMid.internationalNote"/>
								<s:hidden id="deviceFee" name="projectFeeMid.deviceFee" />
								<s:hidden id="deviceNote" name="projectFeeMid.deviceNote"/>
								<s:hidden id="consultationFee" name="projectFeeMid.consultationFee" />
								<s:hidden id="consultationNote" name="projectFeeMid.consultationNote"/>
								<s:hidden id="laborFee" name="projectFeeMid.laborFee" />
								<s:hidden id="laborNote" name="projectFeeMid.laborNote"/>
								<s:hidden id="printFee" name="projectFeeMid.printFee" />
								<s:hidden id="printNote" name="projectFeeMid.printNote"/>
								<s:hidden id="indirectFee" name="projectFeeMid.indirectFee" />
								<s:hidden id="indirectNote" name="projectFeeMid.indirectNote"/>
								<s:hidden id="otherFeeD" name="projectFeeMid.otherFee" />
								<s:hidden id="otherNote" name="projectFeeMid.otherNote"/>
								<s:hidden id="totalFeeD" name="projectFeeMid.totalFee"/>
								<s:hidden id="fundedFee" name="projectFeeMid.fundedFee"/>
								<s:hidden id="surplusFee" name="surplusFee"/>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11" width="120"><span class="table_title4">是否同意中检：</span></td>
							<td class="table_td12"><s:radio name="midResult" theme="simple" list="#{'2':getText('同意'),'1':getText('不同意')}"/></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span  class="table_title4">中检时间：</span></td>
							<td class="table_td12">
								<s:textfield name="midDate" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="midDate" format="yyyy-MM-dd" />
									</s:param>
								</s:textfield>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11">审核意见：</td>
							<td class="table_td12"><s:textarea name='midImportedOpinion' rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：<br /></span><span>（反馈给负责人）</span></td>
							<td class="table_td12"><s:textarea name="midOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
							<td class="table_td13"></td>
						</tr>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addResultSave" type="button" value="保存" />
					<input id="submit" class="btn1 j_addResultSubmit" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/key/midinspection/edit.js','javascript/project/project_share/midinspection/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>
