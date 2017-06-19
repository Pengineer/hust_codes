<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:head/>
		<title>业务设置</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/main/page.css" />
		<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
 		</head>
 
	<body>
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							业务设置&gt;&gt;<span class="text_red">修改一般项目参数</span>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="bottom">
				<s:form action="configGeneral" namespace="/main" theme="simple">
					<s:include value="/validateError.jsp" />
					<table class="table_edit" style="width:420px; margin-left:auto; margin-right:auto;" cellspacing="0" cellpadding="0">
						<tr>
							<th class="thhead" colspan="2">
								一般项目
							</th>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general1.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general1.id" value="${general1.id}" />
								<s:textfield name="general1.value" value="%{general1.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general2.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general2.id" value="${general2.id}" />
								<s:textfield name="general2.value" value="%{general2.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general4.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general4.id" value="${general4.id}" />
								<s:textfield name="general4.value" value="%{general4.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general5.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general5.id" value="${general5.id}" />
								<s:textfield name="general5.value" value="%{general5.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general3.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general3.id" value="${general3.id}" />
								<s:textfield name="general3.value" value="%{general3.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general32.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general32.id" value="${general32.id}" />
								<s:textfield name="general32.value" value="%{general32.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="generalReviewerImportedStartDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="generalReviewerImportedStartDate.id" value="${generalReviewerImportedStartDate.id}" />
								<s:textfield name="generalReviewerImportedStartDate.value" value="%{generalReviewerImportedStartDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="generalReviewerImportedEndDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="generalReviewerImportedEndDate.id" value="${generalReviewerImportedEndDate.id}" />
								<s:textfield name="generalReviewerImportedEndDate.value"  value="%{generalReviewerImportedEndDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="generalReviewerBirthdayStartDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="generalReviewerBirthdayStartDate.id" value="${generalReviewerBirthdayStartDate.id}" />
								<s:textfield name="generalReviewerBirthdayStartDate.value"  value="%{generalReviewerBirthdayStartDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="generalReviewerBirthdayEndDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="generalReviewerBirthdayEndDate.id" value="${generalReviewerBirthdayEndDate.id}" />
								<s:textfield name="generalReviewerBirthdayEndDate.value"  value="%{generalReviewerBirthdayEndDate.value}" />
							</td>
						</tr>
					</table>
					<table class="table_sub" style="width:400px; margin-left:auto; margin-right:auto;">
						<tr>
							<td>&nbsp;</td>
							<td class="td_sub">
								<input type="submit" class ="btn" value = "确定" />
							</td>
							<td class="td_sub">
								<input type="button" class="btn" value="取消" onclick="history.back();" />
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>		
		<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>