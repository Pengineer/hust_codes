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
							<span class="text_red">基地项目</span>
						</td>
						<td class="wd_right">
							<a href="main/toConfigInstp.action">
								<img title="修改" src="image/modify_entry.gif" /></a>&nbsp;
						</td>
					</tr>
				</table>
			</div>
			
			<div id="bottom">
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
							<s:property value="general16.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general17.description" />
						</td>
						<td class="wd13">
							<s:property value="general17.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general19.description" />
						</td>
						<td class="wd13">
							<s:property value="general19.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general20.description" />
						</td>
						<td class="wd13">
							<s:property value="general20.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general18.description" />
						</td>
						<td class="wd13">
							<s:property value="general18.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="general33.description" />
						</td>
						<td class="wd13">
							<s:property value="general33.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="instpReviewerImportedStartDate.description" />
						</td>
						<td class="wd13">
							<s:property value="instpReviewerImportedStartDate.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="instpReviewerImportedEndDate.description" />
						</td>
						<td class="wd13">
							<s:property value="instpReviewerImportedEndDate.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="instpReviewerBirthdayStartDate.description" />
						</td>
						<td class="wd13">
							<s:property value="instpReviewerBirthdayStartDate.value" />
						</td>
					</tr>
					<tr>
						<td class="wd15">
							<s:property value="instpReviewerBirthdayEndDate.description" />
						</td>
						<td class="wd13">
							<s:property value="instpReviewerBirthdayEndDate.value" />
						</td>
					</tr>
				</table>
			</div>		
		</div>
	</body>
</html>