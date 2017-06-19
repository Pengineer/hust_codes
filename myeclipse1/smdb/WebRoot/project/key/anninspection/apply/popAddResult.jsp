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
				<s:form id="ann_result">
					<s:hidden id="annId" name="annId" value=""/>
					<s:hidden id="projectid" name="projectid"/>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11"><span>上传年检申请书：</span></td>
							<td class="table_td12">
								<input type="file" id="file_add" />
								<s:hidden name="ann_file"/>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span>年检经费结算：</span></td>
							<td class="table_td3">
								<input type="button" id="addAnnFee" class="btn1 select_btn" value="添加" />
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
							<td class="table_td11" width="120"><span class="table_title4">年检结果：</span></td>
							<td class="table_td12"><s:radio name="annResult" theme="simple" list="#{'2':'合格','1':'不合格'}"/></td>
							<td class="table_td13" width="100"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title4">年检年度：</span></td>
							<td class="table_td3"><s:textfield id="annYear" name="annYear" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span  class="table_title4">年检时间：</span></td>
							<td class="table_td12">
								<s:textfield name="annDate" cssClass="FloraDatepick" readonly="true">
									<s:param name="value">
										<s:date name="annDate" format="yyyy-MM-dd" />
									</s:param>
								</s:textfield>
							</td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11">审核意见：</td>
							<td class="table_td12"><s:textarea name='annImportedOpinion' rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td11"><span>审核意见：<br /></span><span>（反馈给负责人）</span></td>
							<td class="table_td12"><s:textarea name="annOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
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
				seajs.use(['javascript/project/key/anninspection/edit.js','javascript/project/project_share/anninspection/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>
