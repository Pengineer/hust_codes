<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>添加</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科信息发布 &nbsp;&gt;&nbsp;站内信&nbsp;&gt;&nbsp;添加
			</div>
			
			<s:form id="form_inBox" action="add" namespace="/inBox" theme="simple">
				<div class="main">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="100"><span class="table_title2">发信方式：</span></td>
								<td class="table_td3">
									<span class="choose_bar">
										<s:select cssClass="select" id="sendType" name="sendType" headerKey="2" headerValue="--广播--"
											list="#{'1':'单播或多播'}" />
									</span>
								</td>
								<td class="table_td4"></td>
							</tr>
						</table>
						<div id='unicast' style="display:none;">
							<table width="100%" border="0" cellspacing="2" cellpadding="0">
								<tr class="table_tr2" id="Unicast" >
									<td class="table_td2" width="100"><span class="table_title2">收信人：</span></td>
									<td class="table_td3">
										<s:textfield name="recNames" cssClass="input_css" />
									</td>
									<td class="table_td4"></td>
								</tr>
							</table>
						</div>
						<div id='multicast' style="display:;">
							<table width="100%" border="0" cellspacing="2" cellpadding="0">
							 <tr class="table_tr2" id="multicast" >
								<td class="table_td2" width="100"><span class="table_title2">收信人：</span></td>
								<td class="table_td3">
									<table class="checkbox_css" width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td><input id="recieverType-1" type="checkbox" cssClass="input_css" name="recName" value="ministry" />部级管理机构</td>
											<td><input id="recieverType-2" type="checkbox" cssClass="input_css" name="recName" value="ministryOfficer" />部级管理人员</td>
											<td><input id="recieverType-3" type="checkbox" cssClass="input_css" name="recName" value="province" />省级管理机构</td>
											<td><input id="recieverType-4" type="checkbox" cssClass="input_css" name="recName" value="provinceOfficer" />省级管理人员</td>
										</tr>
										<tr>
											<td><input id="recieverType-5" type="checkbox" cssClass="input_css" name="recName" value="ministryUniversity" />部属高校管理机构</td>
											<td><input id="recieverType-6" type="checkbox" cssClass="input_css" name="recName" value="ministryUniversityOfficer" />部署高校管理人员</td>
											<td><input id="recieverType-7" type="checkbox" cssClass="input_css" name="recName" value="localUniversity" />地方高校管理机构</td>
											<td><input id="recieverType-8" type="checkbox" cssClass="input_css" name="recName" value="localUniversityOfficer" />地方高校管理人员</td>
										</tr>
										<tr>
											<td><input id="recieverType-9" type="checkbox" cssClass="input_css" name="recName" value="department" />院系管理机构</td>
											<td><input id="recieverType-10" type="checkbox" cssClass="input_css" name="recName" value="departmentOfficer" />院系管理人员</td>
											<td><input id="recieverType-11" type="checkbox" cssClass="input_css" name="recName" value="institute" />社科研究基地</td>
											<td><input id="recieverType-12" type="checkbox" cssClass="input_css" name="recName" value="instituteOfficer" />基地管理人员</td>
										</tr>
										<tr>
											<td><input id="recieverType-13" type="checkbox" cssClass="input_css" name="recName" value="expert" />外部专家</td>
											<td><input id="recieverType-14" type="checkbox" cssClass="input_css" name="recName" value="teacher" />教师</td>
											<td><input id="recieverType-15" type="checkbox" cssClass="input_css" name="recName" value="student" />学生</td>
										</tr>
									</table>
								</td>
								<td class="table_td4"></td>
							</tr>
							</table>
						</div>
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="100"><span class="table_title2">主题：</span></td>
								<td class="table_td3"><s:textfield name="theme" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2" width="100"><span class="table_title2">内容：</span></td>
								<td class="table_td3"><s:textarea id="rdesc" name="message" rows="20" cssClass="textarea_css" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
					<s:include value="/submit.jsp" />
				</div>
			</s:form>
			<script type="text/javascript">
				seajs.use('javascript/system/inBox/edit.js', function(add) {
					$(function(){
						add.init();
					})
				});
			</script>
		</body>
	
</html>