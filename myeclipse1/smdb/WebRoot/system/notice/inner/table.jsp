<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<div class="main">
	<div class="main_content">
		<s:include value="/validateError.jsp" />
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr class="table_tr2">
				<td class="table_td2" width="100"><span class="table_title2">通知标题：</span></td>
				<td class="table_td3"><s:textfield name="notice.title" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">通知类型：</span></td>
				<td class="table_td3"><s:select cssClass="select" name="notice.type.id" value="%{notice.type.id}" list="#application.noticeItems" listKey="id" listValue="name" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">通知来源：</span></td>
				<td class="table_td3"><s:textfield name="notice.source" cssClass="input_css" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span >群发邮件：</span></td>
				<td class="table_td3">
					<table class="checkbox_css" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><input id="recieverType-1" type="checkbox" cssClass="input_css" name="recieverType" value="0" />部级机构</td>
							<td><input id="recieverType-2" type="checkbox" cssClass="input_css" name="recieverType" value="1" />部级管理人员</td>
							<td><input id="recieverType-3" type="checkbox" cssClass="input_css" name="recieverType" value="2" />省级机构</td>
						</tr>
						<tr>	
							<td><input id="recieverType-4" type="checkbox" cssClass="input_css" name="recieverType" value="3" />省级管理人员</td>
							<td><input id="recieverType-5" type="checkbox" cssClass="input_css" name="recieverType" value="4" />部属高校</td>
							<td><input id="recieverType-6" type="checkbox" cssClass="input_css" name="recieverType" value="5" />部属高校管理人员</td>
						</tr>
						<tr>
							<td><input id="recieverType-7" type="checkbox" cssClass="input_css" name="recieverType" value="6" />地方高校</td>
							<td><input id="recieverType-8" type="checkbox" cssClass="input_css" name="recieverType" value="7" />地方高校管理人员</td>
							<td><input id="recieverType-9" type="checkbox" cssClass="input_css" name="recieverType" value="8" />院系</td>
						</tr>
						<tr>
							<td><input id="recieverType-10" type="checkbox" cssClass="input_css" name="recieverType" value="9" />院系管理人员</td>
							<td><input id="recieverType-11" type="checkbox" cssClass="input_css" name="recieverType" value="10" />研究机构</td>
							<td><input id="recieverType-12" type="checkbox" cssClass="input_css" name="recieverType" value="11" />研究机构管理人员</td>
						</tr>
						<tr>
							<td><input id="recieverType-13" type="checkbox" cssClass="input_css" name="recieverType" value="12" />教育系统外部专家</td>
							<td><input id="recieverType-14" type="checkbox" cssClass="input_css" name="recieverType" value="13" />教师</td>
							<td><input id="recieverType-15" type="checkbox" cssClass="input_css" name="recieverType" value="14" />学生</td>
						</tr>
						<tr>
							<td><input id="recieverType-16" type="checkbox" cssClass="input_css" name="recieverType" value="15" />其他</td>
						</tr>
					</table>
					<s:textarea name="mail.sendTo" cssStyle="width:500px;height:40px;color:gray;font-size:12px;display:none;" value="请填写地址，用分号隔开" />
				</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">是否公开：</span></td>
				<td class="table_td3"><s:radio name="notice.isOpen" value="%{notice.isOpen}" list="#{'1':'是','0':'否'}" cssClass="input_css_radio" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">是否弹出：</span></td>
				<td class="table_td3"><s:radio name="notice.isPop" value="notice.isPop" list="#{'1':'是','0':'否'}" cssClass="input_css_radio" /></td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">有效期：</span></td>
				<td class="table_td3"><s:textfield name="notice.validDays" cssClass="input_css_self" size="10" />天</td>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2">附件：</td>
				<s:if test="flag == 0">
					<td class="table_td3">
						<input type="file" id="file_add" />
					</td>
				</s:if>
				<s:elseif test="flag == 1">
					<td class="table_td3">
						<input type="file" id="file_${notice.id}" />
					</td>
					<s:hidden id = "noticeId" name = "notice.id"></s:hidden>
				</s:elseif>
				<td class="table_td4"></td>
			</tr>
			<tr class="table_tr2">
				<td class="table_td2"><span class="table_title2">通知正文：</span></td>
				<td colspan="2">
					<FCK:editor instanceName="notice.content" value="${notice.content}" basePath="/tool/fckeditor" width="100%" height="450" toolbarSet="Default"></FCK:editor>
				</td>
			</tr>
		</table>
	</div>
	<div id="existingAttachment" style="display:none">${existingAttachment}</div>
	<s:include value="/submit.jsp" />
</div>
<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">