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
			<title>项目清单</title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link rel="stylesheet" type="text/css" href="css/global.css" />
			<link rel="stylesheet" type="text/css" href="css/project/project.css" />
			<link rel="stylesheet" type="text/css" href="tool/jquery-ui-1.8.16.custom/css/redmond/jquery-ui-1.8.16.custom.css" />
		</head>

		<body>
			<div>
				<input type="hidden" name="isReviewable" value="${isReviewable}" />
				<s:hidden name="expertids" />
				<s:form id="disableReviewForm">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr style="height:25px;">
							<td colspan="2"><span class="table_title" style="width:auto;">退评当前年度已参与评审项目：</span></td>
							<td style="width:50px;"><input type="radio" name="isNotReviewAll" value="1" checked="checked"/>是</td>
							<td style="width:50px;"><input type="radio" name="isNotReviewAll" value="0" checked="checked" checked/>否</td>
						</tr>
						<tr style="height:25px;">
							<td colspan="4"><span class="table_title" style="width:auto;">退评原因：</span></td>
						</tr>
						<tr style="">
							<td colspan="4" style="padding-left:15px;"><textarea id="notReviewerReason" style="width: 99%;height:60px;resize:none;"></textarea></td>
						</tr>
					</table>
					<div class="btn_div">
						<input id="confirm" type="button" class="btn" value="确定" />
						<input id="cancel" type="button" class="btn" value="取消" />
					</div>
				</s:form>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/expert/popSetReview.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>