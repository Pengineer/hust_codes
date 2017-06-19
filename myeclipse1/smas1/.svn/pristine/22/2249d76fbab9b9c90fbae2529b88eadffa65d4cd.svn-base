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
							业务设置&gt;&gt;<span class="text_red">修改基地项目参数</span>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="bottom">
				<s:form action="configInstp" namespace="/main" theme="simple">
					<s:include value="/validateError.jsp" />
					<table class="table_edit" style="width:420px; margin-left:auto; margin-right:auto;" cellspacing="0" cellpadding="0">
						<tr>
							<th class="thhead" colspan="2">
								基地项目
							</th>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general16.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general16.id" value="${general16.id}" />
								<s:textfield name="general16.value" value="%{general16.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general17.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general17.id" value="${general17.id}" />
								<s:textfield name="general17.value" value="%{general17.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general19.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general19.id" value="${general19.id}" />
								<s:textfield name="general19.value" value="%{general19.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general20.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general20.id" value="${general20.id}" />
								<s:textfield name="general20.value" value="%{general20.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general18.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general18.id" value="${general18.id}" />
								<s:textfield name="general18.value" value="%{general18.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="general33.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="general33.id" value="${general33.id}" />
								<s:textfield name="general33.value" value="%{general33.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="instpReviewerImportedStartDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="instpReviewerImportedStartDate.id" value="${instpReviewerImportedStartDate.id}" />
								<s:textfield name="instpReviewerImportedStartDate.value" value="%{instpReviewerImportedStartDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="instpReviewerImportedEndDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="instpReviewerImportedEndDate.id" value="${instpReviewerImportedEndDate.id}" />
								<s:textfield name="instpReviewerImportedEndDate.value" value="%{instpReviewerImportedEndDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="instpReviewerBirthdayStartDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="instpReviewerBirthdayStartDate.id" value="${instpReviewerBirthdayStartDate.id}" />
								<s:textfield name="instpReviewerBirthdayStartDate.value" value="%{instpReviewerBirthdayStartDate.value}" />
							</td>
						</tr>
						<tr>
							<td class="wd15">
								<s:property value="instpReviewerBirthdayEndDate.description" />
							</td>
							<td class="wd13">
								<input type="hidden" name="instpReviewerBirthdayEndDate.id" value="${instpReviewerBirthdayEndDate.id}" />
								<s:textfield name="instpReviewerBirthdayEndDate.value" value="%{instpReviewerBirthdayEndDate.value}" />
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