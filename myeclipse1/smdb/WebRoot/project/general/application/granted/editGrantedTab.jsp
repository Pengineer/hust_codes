<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="edit_info" id="granted">
	<s:hidden name="granted.id" />
	<table width="100%" border="0" cellspacing="2" cellpadding="2">
		<tr class="table_tr2">
			<td class="table_td2" width="130"><span class="table_title5">批准号：</span></td>
			<td class="table_td3"><s:textfield name="number" cssClass="input_css" /></td>
			<td class="table_td4" width="90"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>项目子类：</span></td>
			<td class="table_td3"><s:select cssClass="select" name="subtypeId" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMap('projectType', '01')}" /></td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span>批准时间：</span></td>
			<td class="table_td3">
				<input type="text" id="approveDate" name="approveDate" cssClass="FloraDatepick" readonly="true" value="<s:date name='%{granted.approveDate}' format='yyyy-MM-dd' />" />
				</td>
			<td class="table_td4"></td>
		</tr>
		<tr class="table_tr2">
			<td class="table_td2"><span class="table_title5">批准经费（万）：</span></td>
			<td class="table_td3"><s:textfield name="approveFee" id="approveFee" cssClass="input_css" /></td>
			<td class="table_td4"></td>
		</tr>
		
<%--		<tr class="table_tr2">--%>
<%--			<td class="table_td2"><span class="table_title5">立项经费预算：</span></td>--%>
<%--				<s:if test="flag == 0"> 添加 --%>
<%--					<td class="table_td3">--%>
<%--						<input type="button" id="addGrantedFee" class="btn1 select_btn" value="添加" />--%>
<%--						<div id="totalFee1" class="choose_show"><s:property value="projectFeeGranted.totalFee"/></div>--%>
<%--					</td>--%>
<%--				</s:if>--%>
<%--				<s:elseif test="flag == 1"> 修改 --%>
<%--					<td class="table_td3">--%>
<%--						<input type="button" id="modifyGrantedFee" class="btn1 select_btn" value="修改" />--%>
<%--						<div id="totalFee1" class="choose_show"><s:property value="projectFeeGranted.totalFee"/></div>--%>
<%--					</td>--%>
<%--				</s:elseif>--%>
<%--				<s:hidden id="bookFee1" name="projectFeeGranted.bookFee" />--%>
<%--				<s:hidden id="bookNote1" name="projectFeeGranted.bookNote"/>--%>
<%--				<s:hidden id="dataFee1" name="projectFeeGranted.dataFee" />--%>
<%--				<s:hidden id="dataNote1" name="projectFeeGranted.dataNote"/>--%>
<%--				<s:hidden id="travelFee1" name="projectFeeGranted.travelFee" />--%>
<%--				<s:hidden id="travelNote1" name="projectFeeGranted.travelNote"/>--%>
<%--				<s:hidden id="conferenceFee1" name="projectFeeGranted.conferenceFee" />--%>
<%--				<s:hidden id="conferenceNote1" name="projectFeeGranted.conferenceNote"/>--%>
<%--				<s:hidden id="internationalFee1" name="projectFeeGranted.internationalFee" />--%>
<%--				<s:hidden id="internationalNote1" name="projectFeeGranted.internationalNote"/>--%>
<%--				<s:hidden id="deviceFee1" name="projectFeeGranted.deviceFee" />--%>
<%--				<s:hidden id="deviceNote1" name="projectFeeGranted.deviceNote"/>--%>
<%--				<s:hidden id="consultationFee1" name="projectFeeGranted.consultationFee" />--%>
<%--				<s:hidden id="consultationNote1" name="projectFeeGranted.consultationNote"/>--%>
<%--				<s:hidden id="laborFee1" name="projectFeeGranted.laborFee" />--%>
<%--				<s:hidden id="laborNote1" name="projectFeeGranted.laborNote"/>--%>
<%--				<s:hidden id="printFee1" name="projectFeeGranted.printFee" />--%>
<%--				<s:hidden id="printNote1" name="projectFeeGranted.printNote"/>--%>
<%--				<s:hidden id="indirectFee1" name="projectFeeGranted.indirectFee" />--%>
<%--				<s:hidden id="indirectNote1" name="projectFeeGranted.indirectNote"/>--%>
<%--				<s:hidden id="otherFeeD1" name="projectFeeGranted.otherFee" />--%>
<%--				<s:hidden id="otherNote1" name="projectFeeGranted.otherNote"/>--%>
<%--				<s:hidden id="totalFeeD1" name="projectFeeGranted.totalFee"/>--%>
<%--				<s:hidden id="feeNote1" name="projectFeeGranted.feeNote"/>--%>
<%--			<td class="table_td4"></td>--%>
<%--		</tr>--%>
	</table>
</div>