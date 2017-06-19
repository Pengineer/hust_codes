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
				<div style="overflow-y:hidden; overflow-x:hidden;">
					<input type="hidden" name="isReviewable" value="${isReviewable}" />
					<s:hidden name="expertids" />
					<s:form id="">
						<table width="100%" theme="simple" border="0" cellspacing="2" cellpadding="0">
							<tr>
								<td class="table_title">退评原因：</td>
							</tr>
							<tr>
								<td style="padding-left:15px;"><textarea id="notReviewReason" rows="3" style="width:100%;"></textarea></td>
							</tr>
						</table>
					</s:form>
					<div class="btn_div">
						<input id="confirm" type="button" class="btn" value="确定" />
						<input id="cancel" type="button" class="btn" value="取消" />
					</div>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="tool/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery-ui-timepicker-addon.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/pop/pop_init.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/project/general/popSetReview.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>