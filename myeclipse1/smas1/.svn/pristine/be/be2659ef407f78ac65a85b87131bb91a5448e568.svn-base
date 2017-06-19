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
			<title>基地项目录入中检结果信息</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="tool/jquery-ui-1.8.16.custom/css/redmond/jquery-ui-1.8.16.custom.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css" />
			<script type="text/javascript">
				var basePath = "<%=basePath%>";
			</script>
		</head>

		<body>
			<div style="width: 420px;height:440px;overflow-y: scroll;">
				<s:form>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">上传中检申请书：</td>
							<td class="table_td1" colspan="2"><input id="file_add" type="file" multiple="true"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">中检经费结算：</td>
							<td class="table_td1" colspan="2"><input type="button" class="btn modifyMidFee" value="修改"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">是否同意中检：</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="midAuditResult" value="2" checked="true"/>同意</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="midAuditResult" value="1"/>不同意</td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">批准时间：</td>
							<td class="table_td1" colspan="2"><input id="midDate" name="midDate" style="width:100%;"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">审核意见：</td>
							<td class="table_td1" colspan="2"><textarea id="midAuditOpinion" name="midAuditOpinion" rows="3" style="width:100%;"></textarea></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">审核意见：<br />（反馈给负责人）</td>
							<td class="table_td1" colspan="2"><textarea id="midAuditOpinionFeedback" name="midAuditOpinionFeedback" rows="3" style="width:100%;"></textarea></td>
						</tr>
					</table>
					<s:hidden id="feeNote" name="projectFee.feeNote" />
					<s:hidden id="bookFee" name="projectFee.bookFee" />
					<s:hidden id="bookNote" name="projectFee.bookNote"/>
					<s:hidden id="dataFee" name="projectFee.dataFee" />
					<s:hidden id="dataNote" name="projectFee.dataNote"/>
					<s:hidden id="travelFee" name="projectFee.travelFee" />
					<s:hidden id="travelNote" name="projectFee.travelNote"/>
					<s:hidden id="conferenceFee" name="projectFee.conferenceFee" />
					<s:hidden id="conferenceNote" name="projectFee.conferenceNote"/>
					<s:hidden id="internationalFee" name="projectFee.internationalFee" />
					<s:hidden id="internationalNote" name="projectFee.internationalNote"/>
					<s:hidden id="deviceFee" name="projectFee.deviceFee" />
					<s:hidden id="deviceNote" name="projectFee.deviceNote"/>
					<s:hidden id="consultationFee" name="projectFee.consultationFee" />
					<s:hidden id="consultationNote" name="projectFee.consultationNote"/>
					<s:hidden id="laborFee" name="projectFee.laborFee" />
					<s:hidden id="laborNote" name="projectFee.laborNote"/>
					<s:hidden id="printFee" name="projectFee.printFee" />
					<s:hidden id="printNote" name="projectFee.printNote"/>
					<s:hidden id="indirectFee" name="projectFee.indirectFee" />
					<s:hidden id="indirectNote" name="projectFee.indirectNote"/>
					<s:hidden id="otherFee" name="projectFee.otherFee" />
					<s:hidden id="otherNote" name="projectFee.otherNote"/>
					<s:hidden id="totalFee" name="projectFee.totalFee"/>
					<s:hidden id="surplusFee" name="projectFee.surplusFee"/>
					<div class="btn_div">
						<input type="button" class="confirm btn" value="提交" />
						<input type="button" class="cancel btn" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.cookie.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>		
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/uploadify/js/jquery.uploadify.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/instp/midinspection/popAddResult.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>