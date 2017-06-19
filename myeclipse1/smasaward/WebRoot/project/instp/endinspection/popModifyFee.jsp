<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<base href="<%=basePath%>" />
			<title>基地项目结项审核</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="tool/jquery-ui-1.8.16.custom/css/redmond/jquery-ui-1.8.16.custom.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
		</head>

		<body>
			<div style="width: 500px;">
				<s:form>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr1"><th class="wd17" colspan="3" style="text-align:center;border-top:0px;">结项经费结算明细</th></tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="width:40%;text-align:right;">经费科目</td>
							<td class="wd17 table_td2" style="width:20%;text-align:center;">经费支出金额（万元）</td>
							<td class="wd17 table_td2" style="width:40%;text-align:center;">开支说明</td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">图书资料费</td>
							<td class="wd18 table_td2"><input type="text" name="bookFee" value="${bookFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="bookNote" value="${bookNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">数据采集费</td>
							<td class="wd18 table_td2"><input type="text" name="dataFee" value="${dataFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="dataNote" value="${dataNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">调研差旅费</td>
							<td class="wd18 table_td2"><input type="text" name="travelFee" value="${travelFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="travelNote" value="${travelNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">会议费</td>
							<td class="wd18 table_td2"><input type="text" name="conferenceFee" value="${conferenceFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="conferenceNote" value="${conferenceNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">国际合作交流费</td>
							<td class="wd18 table_td2"><input type="text" name="internationalFee" value="${internationalFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="internationalNote" value="${internationalNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">设备购置和使用费</td>
							<td class="wd18 table_td2"><input type="text" name="deviceFee" value="${deviceFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="deviceNote" value="${deviceNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">专家咨询和评审费</td>
							<td class="wd18 table_td2"><input type="text" name="consultationFee" value="${consultationFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="consultationNote" value="${consultationNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">助研津贴和劳务费</td>
							<td class="wd18 table_td2"><input type="text" name="laborFee" value="${laborFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="laborNote" value="${laborNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">印刷和出版费</td>
							<td class="wd18 table_td2"><input type="text" name="printFee" value="${printFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="printNote" value="${printNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">间接费用</td>
							<td class="wd18 table_td2"><input type="text" name="indirectFee" value="${indirectFee}"/></td>
							<td class="wd18 table_td2"><input type="text" name="indirectNote" value="${indirectNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">其他</td>
							<td class="wd18 table_td2"><input type="text" name="otherFee" value="${otherFeeD}"/></td>
							<td class="wd18 table_td2"><input type="text" name="otherNote" value="${otherNote}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">合计</td>
							<td class="table_td2" colspan="2"><input type="text" name="totalFee" value="${totalFee}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">结余</td>
							<td class="table_td2" colspan="2"><input type="text" id="surplusFee" name="surplusFee" value="${surplusFee}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="wd17 table_td2" style="text-align:right;">相关说明</td>
							<td class="table_td2" colspan="2" class="wd18"><input type="text" name="feeNote" value="${feeNote}"/></td>
						</tr>
					</table>
					<div class="btn_div">
						<input type="button" class="confirmFee btn" value="提交" />
						<input type="button" class="cancel btn" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>		
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/endinspection/popModifyFee.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>