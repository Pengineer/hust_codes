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
							<td class="table_td2" width="100">通信地址：</td>
							<td class="table_td3" colspan="2">
							<table id="agency-caddr-table" >
								<tbody>
									<tr>
										<td colspan="5"><input id="add-agency-caddr" class="btn1" type="button" value="添加"></td>
										<td></td>
									</tr>
									<tr id="tr_common_addr" class = "address" style="display:none;"><td width="40">地址：</td>
									<td>
										<input name="commonAddress[].address" type="text" size = "21" />
										<input type = "hidden" name="commonAddress[].id" type="text" />
									</td>
									<td width="40">邮编：</td><td><input 	name="commonAddress[].postCode" type="text" size="6" maxlength="6" /></td>
									<td width = "80">
										<label>是否默认：<input type="checkbox" name="commonAddress[].isDefault" value = "0"/></label>
										<input type="hidden" name="commonAddress[].isDefault" class="" value = "0"/>
									</td>
									<td><input class="delete_row btn1" type="button" value="删除" name=""></td>
									<td class="comb-error"></td>
									</tr>
								</tbody>
							</table>
						</td>
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
			seajs.use(['javascript/unit/unit.js', 'javascript/unit/validate.js','javascript/unit/agency/department/add.js'], function(unit, validate, add) {
				$(function(){
					unit.init();
					add.init();
					validate.validDepartment();
					window.doSave = function(objectName) {
						$("#tr_common_addr").remove();
						$("#agency-caddr-table tr").each(function(key, value){
							$("input, select", $(value)).each(function(key1, value1){
								value1.name = value1.name.replace(/\[.*\]/, "[" + (key-1) + "]");
							});
						});
						$(":checkbox[name*='isDefault']").each(function(){
							var name = $(this).attr("name"),
								selector = ":hidden[name='" + name + "']"
							$(selector).val($(this).val());
							$(this).remove();
						});
						unit.doSave(objectName);
					};
				})
			});
		</script>
	</body>
</html>