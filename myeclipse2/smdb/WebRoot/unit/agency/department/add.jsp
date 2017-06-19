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
			当前位置：社科机构数据&nbsp;&gt;&nbsp;社科管理机构&nbsp;&gt;&nbsp;院系管理机构&nbsp;&gt;&nbsp;添加
		</div>
		
		<s:form id="form_department" action="add" namespace="/unit/department" theme="simple">
			<div class="main">
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<table width="100%" border="0" cellspacing="2" cellpadding="0">
						<tr class="table_tr2">
							<td class="table_td2" width="100"><span class="table_title2">院系名称：</span></td>
							<td class="table_td3"><s:textfield id="name" name="department.name" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">院系代码：</td>
							<td class="table_td3"><s:textfield name="department.code" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2"><span class="table_title2">所属高校：</span></td>
							<td class="table_td3">
								<input type="button" id="select_university_btn" class="btn1 select_btn" value="选择"/>
								<span id="universityName" name="universityName" ></span>
								<s:hidden name="department.university.id" id="universityId"></s:hidden>
							</td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">通信地址：</td>
							<td class="table_td3"><s:textfield name="department.address" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">邮政编码：</td>
							<td class="table_td3"><s:textfield name="department.postcode" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">电话：</td>
							<td class="table_td3"><s:textfield name="department.phone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">传真：</td>
							<td class="table_td3"><s:textfield name="department.fax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">邮箱：</td>
							<td class="table_td3"><s:textfield name="department.email" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">主页：</td>
							<td class="table_td3"><s:textfield name="department.homepage" cssClass="input_css" /></td>
							<td class="table_td4"></td>
						</tr>
						<tr class="table_tr2">
							<td class="table_td2">简介：</td>
							<td class="table_td3"><s:textarea name="department.introduction" rows="6" cssClass="textarea_css" /></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div> 
				<div class="btn_bar2">
					<input id="save" type="button" class="btn1" value="保存" onclick="doSave('Department')" />
					<input id="cancel" type="button" class="btn1" value="取消" onclick="history.back();" />
				</div>
			</div>
		</s:form>
					<script type="text/javascript">
			seajs.use(['javascript/unit/unit.js', 'javascript/unit/validate.js'], function(unit, validate) {
				$(function(){
					unit.init();
					validate.validDepartment();
					window.doSave = function(objectName) {
						unit.doSave(objectName);
					};
				})
			});
		</script>
	</body>
</html>