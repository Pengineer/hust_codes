<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重大攻关项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>
		
		<body>
			<div style="width:480px;">
				<s:include value="/validateError.jsp" />
				<s:form id="midFile">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="100"><span>上传中检申请书：</span></td>
							<td class="table_td12">
								<input type="file" id="file_${midId}" />
								<s:hidden name="mid_file"/>
							</td>
							<td class="table_td13" width="80"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span>中检经费结算：</span></td>
							<td class="table_td3">
								<input type="button" id="modifyMidFeeApply" class="btn1 select_btn" value="修改" />
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
							<td class="table_td11">备注：<br/>（限200字）</td>
							<td class="table_td12"><s:textarea name="note" id="midnote" rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
					</table>
					<s:hidden id="mid_flag" value="1"/>
					<s:hidden id="midFileProjectid" name="projectid"/>
					<s:hidden id="midApplicantSubmitStatus" name="midApplicantSubmitStatus"/>
					<s:hidden id="deadline" name="deadline"/>
					<s:hidden id="timeFlag" name="timeFlag" />
					<s:hidden id="midStatus" name="midStatus" />
					<s:hidden id = "midId" name="midId"/>
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_modifySave" type="button" value="保存" />
					<input id="submit" class="btn1 j_modifySubmit" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
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
				<s:if test="null != midFlag && midFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer);
					})();
				</s:if>
				seajs.use(['javascript/project/key/midinspection/edit.js','javascript/project/project_share/midinspection/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>