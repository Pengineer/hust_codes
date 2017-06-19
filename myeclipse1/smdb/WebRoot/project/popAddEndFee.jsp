<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
			<script type="text/javascript" >
				seajs.use('javascript/project/project_share/popAddEndFee.js', function(add) {
					add.init();
				});
			</script>
		</head>
		
		<body>
			<div style="width:480px;">
				<s:form id="form_addEndFee">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<tr class="table_tr3">
							<td width="16%" class="key">批准经费</td>
							<td width="16%" class="value"><s:property value="projectGranted.approveFee" /></td>
							<td width="16%" class="key">已拨经费</td>
							<td width="16%" id="fundedFee" class="value"><s:property value="fundedFee" /></td>
							<td width="16%" class="key">未拨经费</td>
							<td width="16%" class="value"><s:property value="toFundFee" /></td>
						</tr>
						<tr class="table_tr3">
							<td class="key" colspan="6">结项经费明细</td>
						</tr>
					</table>	
					<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<tbody>
							<tr class="table_tr3">
								<td width="20%">经费科目</td>
								<td width="15%">预算金额  （万元）</td>
								<td width="15%">支出金额  （万元）</td>
								<td width="40%">开支说明</td>
								<td width="10%"></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">图书资料费</td>
								<td class="value"><s:property value="projectFeeGranted.bookFee" /></td>
								<td class="value"><s:textfield name="bookFee" id="bookFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="bookNote" id="bookNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">数据采集费</td>
								<td class="value"><s:property value="projectFeeGranted.dataFee" /></td>
								<td class="value"><s:textfield name="dataFee" id="dataFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="dataNote" id="dataNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">调研差旅费</td>
								<td class="value"><s:property value="projectFeeGranted.travelFee" /></td>
								<td class="value"><s:textfield name="travelFee" id="travelFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="travelNote" id="travelNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">会议费</td>
								<td class="value"><s:property value="projectFeeGranted.conferenceFee" /></td>
								<td class="value"><s:textfield name="conferenceFee" id="conferenceFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="conferenceNote" id="conferenceNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">国际合作交流费</td>
								<td class="value"><s:property value="projectFeeGranted.internationalFee" /></td>
								<td class="value"><s:textfield name="internationalFee" id="internationalFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="internationalNote" id="internationalNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">设备购置和使用费</td>
								<td class="value"><s:property value="projectFeeGranted.deviceFee" /></td>
								<td class="value"><s:textfield name="deviceFee" id="deviceFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="deviceNote" id="deviceNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">专家咨询和评审费</td>
								<td class="value"><s:property value="projectFeeGranted.consultationFee" /></td>
								<td class="value"><s:textfield name="consultationFee" id="consultationFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="consultationNote" id="consultationNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">助研津贴和劳务费</td>
								<td class="value"><s:property value="projectFeeGranted.laborFee" /></td>
								<td class="value"><s:textfield name="laborFee" id="laborFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="laborNote" id="laborNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">印刷和出版费</td>
								<td class="value"><s:property value="projectFeeGranted.printFee" /></td>
								<td class="value"><s:textfield name="printFee" id="printFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="printNote" id="printNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">间接费用</td>
								<td class="value"><s:property value="projectFeeGranted.indirectFee" /></td>
								<td class="value"><s:textfield name="indirectFee" id="indirectFee" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="indirectNote" id="indirectNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">其他</td>
								<td class="value"><s:property value="projectFeeGranted.otherFee" /></td>
								<td class="value"><s:textfield name="otherFeeD" id="otherFeeD" cssClass="input_css" /></td>
								<td class="value"><s:textfield name="otherNote" id="otherNote" cssClass="input_css" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">合计</td>
								<td class="value"><s:property value="projectFeeGranted.totalFee" /></td>
								<td class="value" id="totalFeeD" colspan="2"><s:property value="totalFee" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="key">结余经费</td>
								<td class="value" id="surplusFee" colspan="3"><s:property value="surplusFee" /></td>
								<td></td>
							</tr>
							<tr class="table_tr4">
								<td class="table_td11">结余经费支出说明</td>
								<td class="table_td12" colspan="3"><s:textarea name="feeNote" id="feeNote" rows="2" cssClass="textarea_css"/></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</s:form>
				<div class="btn_div_view">
					<input id="submit" class="btn1" type="button" value="提交" />
					<input id="cancel" class="btn1" type="button" value="取消" />
				</div>
			</div>
		</body>
	
</html>