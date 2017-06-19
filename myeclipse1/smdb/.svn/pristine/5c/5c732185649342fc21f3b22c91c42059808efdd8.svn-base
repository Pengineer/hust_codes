<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title2">收件人：</span></td>
				<td class="table_td3">
					<table class="checkbox_css" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><input id="recieverType-1" type="checkbox" cssClass="input_css" name="recieverType" value="ministry" />部级管理机构</td>
							<td><input id="recieverType-2" type="checkbox" cssClass="input_css" name="recieverType" value="ministryOfficer" />部级管理人员</td>
							<td><input id="recieverType-3" type="checkbox" cssClass="input_css" name="recieverType" value="province" />省级管理机构</td>
							<td><input id="recieverType-4" type="checkbox" cssClass="input_css" name="recieverType" value="provinceOfficer" />省级管理人员</td>
						</tr>
						<tr>
							<td><input id="recieverType-5" type="checkbox" cssClass="input_css" name="recieverType" value="ministryUniversity" />部属高校管理机构</td>
							<td><input id="recieverType-6" type="checkbox" cssClass="input_css" name="recieverType" value="ministryUniversityOfficer" />部署高校管理人员</td>
							<td><input id="recieverType-7" type="checkbox" cssClass="input_css" name="recieverType" value="localUniversity" />地方高校管理机构</td>
							<td><input id="recieverType-8" type="checkbox" cssClass="input_css" name="recieverType" value="localUniversityOfficer" />地方高校管理人员</td>
						</tr>
						<tr>
							<td><input id="recieverType-9" type="checkbox" cssClass="input_css" name="recieverType" value="department" />院系管理机构</td>
							<td><input id="recieverType-10" type="checkbox" cssClass="input_css" name="recieverType" value="departmentOfficer" />院系管理人员</td>
							<td><input id="recieverType-11" type="checkbox" cssClass="input_css" name="recieverType" value="institute" />社科研究基地</td>
							<td><input id="recieverType-12" type="checkbox" cssClass="input_css" name="recieverType" value="instituteOfficer" />基地管理人员</td>
						</tr>
						<tr>
							<td><input id="recieverType-13" type="checkbox" cssClass="input_css" name="recieverType" value="expert" />外部专家</td>
							<td><input id="recieverType-14" type="checkbox" cssClass="input_css" name="recieverType" value="teacher" />教师</td>
							<td><input id="recieverType-15" type="checkbox" cssClass="input_css" name="recieverType" value="student" />学生</td>
							<td><input id="recieverType-16" type="checkbox" cssClass="input_css" name="recieverType" value="others" />其他</td>
						</tr>
					</table>
					<s:textarea name="mail.sendTo" cssStyle="width:500px;height:40px;color:gray;font-size:12px;display:none;" value="请填写地址，用分号隔开" />
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">邮件主题：</span></td>
				<td class="table_td3"><s:textfield name="mail.subject" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">附件：</td>
				<td class="table_td3">
					<input type="file" id="file_add" />
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">邮件正文：</span></td>
				<td class="table_td3"><s:textarea id="rdesc" name="mail.body" rows="20" cssClass="textarea_css" /></td>
				<td class="table_td4"></td>
			</tr>
		</table>
	</div>
	<s:include value="/submit.jsp" />
</div>
<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
