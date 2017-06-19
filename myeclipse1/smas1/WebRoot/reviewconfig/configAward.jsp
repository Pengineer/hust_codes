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
							业务设置&gt;&gt;<span class="text_red">修改社科奖励参数</span>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="bottom">
				<s:form action="configAward" namespace="/main" theme="simple">
					<s:include value="/validateError.jsp" />
					<table class="table_edit" style="width:420px; margin-left:auto; margin-right:auto;" cellspacing="0" cellpadding="0">
						<tr>
							<th class="thhead" colspan="2">
								社科奖励
							</th>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general35.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general35.id" value="${general35.id}" />
								<s:textfield name="general35.value" value="%{general35.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general36.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general36.id" value="${general36.id}" />
								<s:textfield name="general36.value" value="%{general36.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general37.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general37.id" value="${general37.id}" />
								<s:textfield name="general37.value" value="%{general37.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general38.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general38.id" value="${general38.id}" />
								<s:textfield name="general38.value" value="%{general38.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general39.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general39.id" value="${general39.id}" />
								<s:textfield name="general39.value" value="%{general39.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general40.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general40.id" value="${general40.id}" />
								<s:textfield name="general40.value" value="%{general40.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="awardReviewerImportedStartDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="awardReviewerImportedStartDate.id" value="${awardReviewerImportedStartDate.id}" />
								<s:textfield name="awardReviewerImportedStartDate.value" value="%{awardReviewerImportedStartDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="awardReviewerImportedEndDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="awardReviewerImportedEndDate.id" value="${awardReviewerImportedEndDate.id}" />
								<s:textfield name="awardReviewerImportedEndDate.value"  value="%{awardReviewerImportedEndDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="awardReviewerBirthdayStartDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="awardReviewerBirthdayStartDate.id" value="${awardReviewerBirthdayStartDate.id}" />
								<s:textfield name="awardReviewerBirthdayStartDate.value"  value="%{awardReviewerBirthdayStartDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="awardReviewerBirthdayEndDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="awardReviewerBirthdayEndDate.id" value="${awardReviewerBirthdayEndDate.id}" />
								<s:textfield name="awardReviewerBirthdayEndDate.value"  value="%{awardReviewerBirthdayEndDate.value}" />
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