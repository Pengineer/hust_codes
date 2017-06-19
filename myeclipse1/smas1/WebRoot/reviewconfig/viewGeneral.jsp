<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:head/>
		<title>业务设置</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/main/page.css" />
	</head>
	<body>
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							业务设置&nbsp;&gt;&gt;
							<span class="text_red">一般项目</span>
						</td>
						<td class="wd_right">
							<a href="main/toConfigGeneral.action">
								<img title="修改" src="image/modify_entry.gif" /></a>&nbsp;
						</td>
					</tr>
				</table>
			</div>
			
			<div id="bottom">
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
							<s:property value="general1.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general2.description" />
						</td>
						<td class="wd13">
							<s:property value="general2.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general4.description" />
						</td>
						<td class="wd13">
							<s:property value="general4.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general5.description" />
						</td>
						<td class="wd13">
							<s:property value="general5.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general3.description" />
						</td>
						<td class="wd13">
							<s:property value="general3.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general32.description" />
						</td>
						<td class="wd13">
							<s:property value="general32.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="generalReviewerImportedStartDate.description" />
						</td>
						<td class="wd13">
							<s:property value="generalReviewerImportedStartDate.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="generalReviewerImportedEndDate.description" />
						</td>
						<td class="wd13">
							<s:property value="generalReviewerImportedEndDate.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="generalReviewerBirthdayStartDate.description" />
						</td>
						<td class="wd13">
							<s:property value="generalReviewerBirthdayStartDate.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="generalReviewerBirthdayEndDate.description" />
						</td>
						<td class="wd13">
							<s:property value="generalReviewerBirthdayEndDate.value" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>