<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>
		
		<body>
			<div style="width:480px;">
				<s:include value="/validateError.jsp" />
				<s:form id="graFile">
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td11" width="100"><span class="table_title6">上传立项计划书：</span></td>
							<td class="table_td12">
								<input type="file" id="file_add" />
								<s:hidden name="gra_file"/>
							</td>
							<td class="table_td13" width="80"></td>
						</tr>
						
						<tr class="table_tr2">
							<td class="table_td2"><span>立项经费预算：</span></td>
							<td class="table_td3">
								<input type="button" id="addGrantedFeeApply" class="btn1 select_btn" value="添加" />
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
							<td class="table_td11">备注：<br/>（限200字）</td>
							<td class="table_td12"><s:textarea name="note" id="midnote" rows="2" cssClass="textarea_css"/></td>
							<td class="table_td13"></td>
						</tr>
					</table>
					<s:hidden id="gra_Flag" value="0"/>
					<s:hidden id="midFileProjectid" name="projectid"/>
					<s:hidden id="garApplicantSubmitStatus" name="garApplicantSubmitStatus"/>
					<s:hidden id="deadline" name="deadline" />
					<s:hidden id="timeFlag" name="timeFlag" />
					<s:hidden id="graStatus" name="graStatus" />
				</s:form>
				<div class="btn_div_view">
					<input id="save" class="btn1 j_addSave" type="button" value="保存"/>
					<input id="submit" class="btn1 j_addSubmit" type="button" value="提交"/>
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
				<s:if test="null != graFlag && graFlag == 1">
					(function(){
						var thisPopLayer = top.PopLayer.instances[top.PopLayer.id];
						thisPopLayer.callBack(thisPopLayer);
					})();
				</s:if>
				seajs.use(['javascript/project/instp/application/granted/edit.js','javascript/project/project_share/application/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>