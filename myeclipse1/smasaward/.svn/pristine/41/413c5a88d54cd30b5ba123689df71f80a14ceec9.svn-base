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
			<title>基地项目结项最终审核</title>
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
			<div style="width: 420px;height:400px;overflow-y: scroll;">
				<s:form>
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">上传结项申请书：</td>
							<td class="table_td1" colspan="2"><input id="file_add" type="file" multiple="true"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">结项经费结算：</td>
							<td class="table_td1" colspan="2"><input type="button" class="btn modifyEndFee" value="修改"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">是否申请免检定：</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="isApplyNoevaluation" value="1"/>是</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="isApplyNoevaluation" value="0" checked="true"/>否</td>
						</tr>
						<tr class="table_tr1" id="finalAuditResultNovaluationContainer" style="display:none;">
							<td class="table_td1" style="text-align:right;width:40%;">免鉴定结果：</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="finalAuditResultNoevaluation" value="2"/>同意</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="finalAuditResultNoevaluation" value="1" checked="true"/>不同意</td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">是否申优秀成果：</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="isApplyExcellent" value="1"/>是</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="isApplyExcellent" value="0" checked="true"/>否</td>
						</tr>
						<tr class="table_tr1" id="finalAuditResultExcellentContainer" style="display:none;">
							<td class="table_td1" style="text-align:right;width:40%;">优秀成果结果：</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="finalAuditResultExcellent" value="2"/>同意</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="finalAuditResultExcellent" value="1" checked="true"/>不同意</td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">主要参加人：<br />（不含负责人）：</td>
							<td class="table_td1" colspan="2"><textarea id="memberName" name="memberName" rows="3" style="width:99%;"></textarea></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;width:40%;">是否同意结项：</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="reviewAuditResultEnd" value="2"/>同意</td>
							<td class="table_td1" style="width:30%;"><input type="radio" name="reviewAuditResultEnd" value="1" checked="true"/>不同意</td>
						</tr>
						<tr class="table_tr1" id="approveNumDiv" style="display:none;">
							<td class="table_td1" style="text-align:right;">结项证书编号：</td>
							<td class="table_td1" colspan="2"><input id="certificate" name="certificate" type="text" style="width:99%;" value="${certificate}"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">最终审核时间：</td>
							<td class="table_td1" colspan="2"><input id="finalAuditDate" name="finalAuditDate" type="text" style="width:99%;"/></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">审核意见：</td>
							<td class="table_td1" colspan="2"><textarea id="endImportedOpinion" name="endImportedOpinion" rows="3" style="width:100%;"></textarea></td>
						</tr>
						<tr class="table_tr1">
							<td class="table_td1" style="text-align:right;">审核意见：<br/>反馈给负责人</td>
							<td class="table_td1" colspan="2"><textarea id="endAuditOpinionFeedback" name="endAuditOpinionFeedback" rows="3" style="width:100%;"></textarea></td>
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
			<script type="text/javascript" src="javascript/project/instp/endinspection/popFinalResult.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>