<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<s:include value="/innerBase.jsp" />
		</head>
  
		<body>
			<div style="width:450px;">
						<div class="p_box_body">
							<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
								<thead>
									<tr height="36">
										<td colspan="3" height="15" align="center">经费预算明细表</td>
									</tr>
								</thead>
								<tbody>
									<tr class="table_tr3">
										<td>经费科目</td>
										<td width="20%">金额（万元）</td>
										<td width="40%">开支说明</td>
									</tr>
									<tr class="table_tr4">
										<td class="key">图书资料费</td>
										<td class="value"><s:property value="oldFee.bookFee" /></td>
										<td class="value"><s:property value="oldFee.bookNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">数据采集费</td>
										<td class="value"><s:property value="oldFee.dataFee" /></td>
										<td class="value"><s:property value="oldFee.dataNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">调研差旅费</td>
										<td class="value"><s:property value="oldFee.travelFee" /></td>
										<td class="value"><s:property value="oldFee.travelNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">会议费</td>
										<td class="value"><s:property value="oldFee.conferenceFee" /></td>
										<td class="value"><s:property value="oldFee.conferenceNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">国际合作交流费</td>
										<td class="value"><s:property value="oldFee.internationalFee" /></td>
										<td class="value"><s:property value="oldFee.internationalNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">设备购置和使用费</td>
										<td class="value"><s:property value="oldFee.deviceFee" /></td>
										<td class="value"><s:property value="oldFee.deviceNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">专家咨询和评审费</td>
										<td class="value"><s:property value="oldFee.consultationFee" /></td>
										<td class="value"><s:property value="oldFee.consultationNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">助研津贴和劳务费</td>
										<td class="value"><s:property value="oldFee.laborFee" /></td>
										<td class="value"><s:property value="oldFee.laborNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">印刷和出版费</td>
										<td class="value"><s:property value="oldFee.printFee" /></td>
										<td class="value"><s:property value="oldFee.printNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">间接费用</td>
										<td class="value"><s:property value="oldFee.indirectFee" /></td>
										<td class="value"><s:property value="oldFee.indirectNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">其他</td>
										<td class="value"><s:property value="oldFee.otherFee" /></td>
										<td class="value"><s:property value="oldFee.otherNote" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">合计</td>
										<td class="value" colspan="2"><s:property value="oldFee.totalFee" /></td>
									</tr>
									<tr class="table_tr4">
										<td class="key">备注</td>
										<td class="value" colspan="2"><s:property value="oldFee.feeNote" /></td>
									</tr>
								</tbody>
							</table>
						</div>
				<div class="btn_div_view">
					<input id="okclosebutton" class="btn1" type="button" value="确定" />
				</div>
			</div>
		</body>
		<script type="text/javascript">
			seajs.use('javascript/pop/view/view.js', function(view) {
				
			});
		</script>
	
</html>
