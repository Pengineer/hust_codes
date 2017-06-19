<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>一般项目</title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				当前位置：社科项目数据&nbsp;&gt;&nbsp;一般项目&nbsp;&gt;&nbsp;立项数据&nbsp;&gt;&nbsp;添加
			</div>

			<div class="main">
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="130">添加方式：</td>
							<td class="table_td3"><s:radio name="addType" list="#{'1':'基于已有申请数据添加','2':'新建申请数据后添加'}" value="1"/></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div>
				<div id="type_select">
					<s:form id="select_form" action="add" namespace="/project/general/application/granted">
						<s:hidden name="addflag" value="1"/>
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr class="table_tr2">
								<td class="table_td2" width="130"><span class="table_title5">项目名称：</span></td>
								<td class="table_td3">
									<input type="button" id="select_ungranted_project" class="btn1 select_btn" value="选择"/>
									<div id="project_div" class="choose_show"></div>
									<s:hidden name="entityId" id="applicationId"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">批准号：</span></td>
								<td class="table_td3"><s:textfield name="granted.number" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span>项目子类：</span></td>
								<td class="table_td3"><s:select cssClass="select" name="granted.subtype.id" headerKey="-1" headerValue="--请选择--" list="%{baseService.getSystemOptionMap('projectType', '01')}" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span>批准时间：</span></td>
								<td class="table_td3">
									<s:textfield name="granted.approveDate" cssClass="FloraDatepick" readonly="true"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5">批准经费（万）：</span></td>
								<td class="table_td3"><s:textfield name="granted.approveFee" id="approveFee2" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							
<%--							<tr class="table_tr2">--%>
<%--								<td class="table_td2"><span class="table_title5">立项经费预算：</span></td>--%>
<%--										<td class="table_td3">--%>
<%--											<input type="button" id="addGrantedFee2" class="btn1 select_btn" value="添加" />--%>
<%--											<div id="totalFee2" class="choose_show"><s:property value="projectFeeGranted.totalFee"/></div>--%>
<%--										</td>--%>
<%--									<s:hidden id="bookFee2" name="projectFeeGranted.bookFee" />--%>
<%--									<s:hidden id="bookNote2" name="projectFeeGranted.bookNote"/>--%>
<%--									<s:hidden id="dataFee2" name="projectFeeGranted.dataFee" />--%>
<%--									<s:hidden id="dataNote2" name="projectFeeGranted.dataNote"/>--%>
<%--									<s:hidden id="travelFee2" name="projectFeeGranted.travelFee" />--%>
<%--									<s:hidden id="travelNote2" name="projectFeeGranted.travelNote"/>--%>
<%--									<s:hidden id="conferenceFee2" name="projectFeeGranted.conferenceFee" />--%>
<%--									<s:hidden id="conferenceNote2" name="projectFeeGranted.conferenceNote"/>--%>
<%--									<s:hidden id="internationalFee2" name="projectFeeGranted.internationalFee" />--%>
<%--									<s:hidden id="internationalNote2" name="projectFeeGranted.internationalNote"/>--%>
<%--									<s:hidden id="deviceFee2" name="projectFeeGranted.deviceFee" />--%>
<%--									<s:hidden id="deviceNote2" name="projectFeeGranted.deviceNote"/>--%>
<%--									<s:hidden id="consultationFee2" name="projectFeeGranted.consultationFee" />--%>
<%--									<s:hidden id="consultationNote2" name="projectFeeGranted.consultationNote"/>--%>
<%--									<s:hidden id="laborFee2" name="projectFeeGranted.laborFee" />--%>
<%--									<s:hidden id="laborNote2" name="projectFeeGranted.laborNote"/>--%>
<%--									<s:hidden id="printFee2" name="projectFeeGranted.printFee" />--%>
<%--									<s:hidden id="printNote2" name="projectFeeGranted.printNote"/>--%>
<%--									<s:hidden id="indirectFee2" name="projectFeeGranted.indirectFee" />--%>
<%--									<s:hidden id="indirectNote2" name="projectFeeGranted.indirectNote"/>--%>
<%--									<s:hidden id="otherFeeD2" name="projectFeeGranted.otherFee" />--%>
<%--									<s:hidden id="otherNote2" name="projectFeeGranted.otherNote"/>--%>
<%--									<s:hidden id="totalFeeD2" name="projectFeeGranted.totalFee"/>--%>
<%--									<s:hidden id="feeNote2" name="projectFeeGranted.feeNote"/>--%>
<%--								<td class="table_td4"></td>--%>
<%--							</tr>--%>
						</table>
					</s:form>
					<div class="btn_bar2">
						<input class="btn1 j_submitSelectForm" type="button" value="提交" />
						<input class="btn1" type="button" value="取消" onclick="history.back();" />
					</div>
				</div>
				<div id="type_new" style="display:none">
					<s:form id="application_form" action="add" namespace="/project/general/application/granted">
						<s:hidden name="addflag" value="2"/>
						<s:hidden id="addOrModify" value='1'/>
						<div id="info" style="display:none">
							<div class="main_content">
								<div id="procedure" class="step_css">
									<ul>
										<li class="proc" name="apply"><span class="left_step"></span><span class="right_step">申请信息</span></li>
										<li class="proc" name="granted"><span class="left_step"></span><span class="right_step">立项信息</span></li>
										<li class="proc" name="member"><span class="left_step"></span><span class="right_step">相关成员</span></li>
										<li class="step_oo" name="finish"><span class="left_step"></span><span class="right_step">完成</span></li>
									</ul>
								</div>
							</div>
	
							<div class="main_content">
								<s:hidden name = "flag"></s:hidden>
								<s:include value="/project/general/application/granted/editApplyTab.jsp" />
								<s:include value="/project/general/application/granted/editGrantedTab.jsp" />
								<s:include value="/project/general/application/granted/editMemberTab.jsp" />
							</div>
						</div>
					</s:form>
					<s:include value="/project/general/application/granted/memberTemplate.jsp" />
					<div id="optr" class="btn_bar2">
						<input id="prev" class="btn2" type="button" value="上一步" /></li>
						<input id="next" class="btn2" type="button" value="下一步" /></li>
						<input id="finish" class="btn1" type="button" value="完成" /></li>
						<input id="cancel" class="btn1" type="button" value="取消" /></li>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/general/application/granted/add.js','javascript/project/project_share/application/validate.js'], function(add, validate) {
					validate.valid();
					add.init();
				});
			</script>
		</body>
	
</html>