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
			当前位置：社科机构数据&nbsp;&gt;&nbsp;社科研究机构&nbsp;&gt;&nbsp;添加
		</div>
		
		<s:form id="form_institute" action="add" namespace="/unit/institute" theme="simple">
			<div id="info" class="main" style="display: none">
				<div class="main_content">
					<s:hidden name="institute.disciplineType" id="discipline" />
					<s:hidden id="dispName" />
<%--						<s:hidden name="institute.relyDoctoral" id="doctoral" />--%>
<%--						<s:hidden name="institute.relyDisciplineId" id="relyDisciplineId" />--%>
					
					<div class="step_css">
						<ul>
							<li class="proc" name="basicInfo"><span class="left_step"></span><span class="right_step">基本信息</span></li>
							<li class="proc" name="contactInfo"><span class="left_step"></span><span class="right_step">联系信息</span></li>
							<li class="proc step_oo"><span class="left_step"></span><span class="right_step">完成</span></li>
						</ul>
					</div>
				</div>
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<div id="basicInfo">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr class="table_tr2">
								<td class="table_td2" width="130"><span class="table_title5">研究基地名称：</span></td>
								<td class="table_td3"><s:textfield id="name" name="institute.name" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">英文名称：</td>
								<td class="table_td3"><s:textfield name="institute.englishName" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">研究基地代码：</td>
								<td class="table_td3"><s:textfield name="institute.code" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">名称缩写：</td>
								<td class="table_td3"><s:textfield name="institute.abbr" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">研究基地类型：</span></td>
								<td class="table_td3"><s:select cssClass="select" name="institute.type.id" headerKey="-1" headerValue="--%{getText('请选择')}--" list="%{baseService.getSystemOptionMap('researchAgencyType', null)}" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">上级管理机构：</span></td>
								<td class="table_td3">
									<input type="button" id="select_subjection_btn" class="btn1 select_btn" value="选择"/>
									<span id="subjectionName" name="subjectionName" ></span>
									<s:hidden name="institute.subjection.id" id="subjectionId" />
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">是否是独立机构：</td>
								<td class="table_td3"><s:radio name="institute.isIndependent" list="#{'1':getText('是'),'0':getText('否')}" cssClass="input_css_radio" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">组成方式：</td>
								<td class="table_td3"><s:textfield name="institute.form" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">批准批次：</td>
								<td class="table_td3"><s:textfield name="institute.approveSession" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">批准时间：</td>
								<td class="table_td3"><s:textfield id="datepick" name="institute.approveDate" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">简介：</td>
								<td class="table_td3"><s:textarea name="institute.introduction" rows="6" cssClass="textarea_css" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
					<div id="contactInfo" style="display:none">
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr class="table_tr2">
								<td class="table_td2" width="100">通信地址：</td>
								<td class="table_td3"><s:textfield name="institute.address" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">邮政编码：</td>
								<td class="table_td3"><s:textfield name="institute.postcode" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">电话：</td>
								<td class="table_td3"><s:textfield name="institute.phone" cssClass="input_css" /><br/><span class="tip">电话格式：区号-电话号-分机号</span></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">传真：</td>
								<td class="table_td3"><s:textfield name="institute.fax" cssClass="input_css" /><br/><span class="tip">传真格式：区号-电话号-分机号</span></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">邮箱：</td>
								<td class="table_td3"><s:textfield name="institute.email" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2">主页：</td>
								<td class="table_td3"><s:textfield name="institute.homepage" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
						</table>
					</div>
				</div>
				<div id="optr" class="btn_bar2">
					<input id="prev" class="btn2" type="button" style="display: none" value="上一步" />
					<input id="next" class="btn2" type="button" style="display: none" value="下一步" />
					<input id="save" class="btn1" type="button" style="display: none" value="保存"/>
					<input id="cancel" class="btn1" type="button" style="display: none" value="取消" />
				</div>
			</div>
		</s:form>
		<script type="text/javascript">
			seajs.use('javascript/unit/institute/add.js', function(add) {
				$(function(){
					add.init();
				})
			});
		</script>
	</body>
</html>