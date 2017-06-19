<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_InstpProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_InstpProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_GrantedData" />&nbsp;&gt;&nbsp;<s:text name="i18n_Add" />
			</div>

			<div class="main">
				<div class="main_content">
					<s:include value="/validateError.jsp" />
					<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr class="table_tr2">
							<td class="table_td2" width="130"><s:text name="i18n_AddType"/>：</td>
							<td class="table_td3"><s:radio name="addType" list="#{'1':getText('i18n_SelectExist'),'2':getText('i18n_NewItem')}" value="1"/></td>
							<td class="table_td4"></td>
						</tr>
					</table>
				</div>
				<div id="type_select">
					<s:form id="select_form" action="add" namespace="/project/instp/application/granted">
						<s:hidden name="addflag" value="1"/>
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr class="table_tr2">
								<td class="table_td2" width="130"><span class="table_title5"><s:text name="i18n_ProjectName"/>：</span></td>
								<td class="table_td3">
									<input type="button" id="select_ungranted_project" class="btn1 select_btn" value="<s:text name='i18n_Select'/>"/>
									<div id="project_div" class="choose_show"></div>
									<s:hidden name="entityId" id="applicationId"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5"><s:text name="i18n_ProjectNumber" />：</span></td>
								<td class="table_td3"><s:textfield name="granted.number" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span><s:text name="i18n_ProjectSubtype" />：</span></td>
								<td class="table_td3"><s:select cssClass="select" name="granted.subtype.id" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="%{baseService.getSystemOptionMap('projectType', '02')}" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span><s:text name="i18n_ApproveDate" />：</span></td>
								<td class="table_td3">
									<s:textfield name="granted.approveDate" cssClass="FloraDatepick" readonly="true"/>
								</td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5"><s:text name="i18n_ApproveFee" />：</span></td>
								<td class="table_td3"><s:textfield name="granted.approveFee" id="approveFee" cssClass="input_css" /></td>
								<td class="table_td4"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td2"><span class="table_title5"><s:text name="立项经费预算明细" />：</span></td>
										<td class="table_td3">
											<input type="button" id="addGrantedFee2" class="btn1" value="<s:text name="添加" />" />
											<div id="totalFee2" class="choose_show"><s:property value="projectFeeGranted.totalFee"/></div>
										</td>
<%--									<s:elseif test="flag == 1"> 修改 --%>
<%--										<td class="table_td3">--%>
<%--											<input type="button" id="modifyGrantedFee2" class="btn1" value="<s:text name="修改" />" />--%>
<%--											<div id="totalFee2" class="choose_show"><s:property value="projectFeeGranted.totalFee"/></div>--%>
<%--										</td>--%>
<%--									</s:elseif>--%>
									<s:hidden id="bookFee2" name="projectFeeGranted.bookFee" />
									<s:hidden id="bookNote2" name="projectFeeGranted.bookNote"/>
									<s:hidden id="dataFee2" name="projectFeeGranted.dataFee" />
									<s:hidden id="dataNote2" name="projectFeeGranted.dataNote"/>
									<s:hidden id="travelFee2" name="projectFeeGranted.travelFee" />
									<s:hidden id="travelNote2" name="projectFeeGranted.travelNote"/>
									<s:hidden id="conferenceFee2" name="projectFeeGranted.conferenceFee" />
									<s:hidden id="conferenceNote2" name="projectFeeGranted.conferenceNote"/>
									<s:hidden id="internationalFee2" name="projectFeeGranted.internationalFee" />
									<s:hidden id="internationalNote2" name="projectFeeGranted.internationalNote"/>
									<s:hidden id="deviceFee2" name="projectFeeGranted.deviceFee" />
									<s:hidden id="deviceNote2" name="projectFeeGranted.deviceNote"/>
									<s:hidden id="consultationFee2" name="projectFeeGranted.consultationFee" />
									<s:hidden id="consultationNote2" name="projectFeeGranted.consultationNote"/>
									<s:hidden id="laborFee2" name="projectFeeGranted.laborFee" />
									<s:hidden id="laborNote2" name="projectFeeGranted.laborNote"/>
									<s:hidden id="printFee2" name="projectFeeGranted.printFee" />
									<s:hidden id="printNote2" name="projectFeeGranted.printNote"/>
									<s:hidden id="indirectFee2" name="projectFeeGranted.indirectFee" />
									<s:hidden id="indirectNote2" name="projectFeeGranted.indirectNote"/>
									<s:hidden id="otherFeeD2" name="projectFeeGranted.otherFee" />
									<s:hidden id="otherNote2" name="projectFeeGranted.otherNote"/>
									<s:hidden id="totalFeeD2" name="projectFeeGranted.totalFee"/>
									<s:hidden id="feeNote2" name="projectFeeGranted.feeNote"/>
								<td class="table_td4"></td>
							</tr>
						</table>
					</s:form>
					<div class="btn_bar2">
						<input class="btn1 j_submitSelectForm" type="button" value="<s:text name='i18n_Submit' />" />
						<input class="btn1" type="button" value="<s:text name='i18n_Cancel' />" onclick="history.back();" />
					</div>
				</div>
				<div id="type_new" style="display:none">
					<s:form id="application_form" action="add" namespace="/project/instp/application/granted">
						<s:hidden name="addflag" value="2"/>
						<s:hidden id="addOrModify" value='1'/>
						<div id="info" style="display:none">
							<div class="main_content">
								<div id="procedure" class="step_css">
									<ul>
										<li class="proc" name="apply"><span class="left_step"></span><span class="right_step"><s:text name="i18n_ApplyInfo" /></span></li>
										<li class="proc" name="granted"><span class="left_step"></span><span class="right_step"><s:text name="i18n_GrantedInfo" /></span></li>
										<li class="proc" name="member"><span class="left_step"></span><span class="right_step"><s:text name="i18n_RelatedMember" /></span></li>
										<li class="step_oo" name="finish"><span class="left_step"></span><span class="right_step"><s:text name="i18n_Finish" /></span></li>
									</ul>
								</div>
							</div>
	
							<div class="main_content">
								<s:hidden name = "flag"></s:hidden>
								<s:include value="/project/instp/application/granted/editApplyTab.jsp" />
								<s:include value="/project/instp/application/granted/editGrantedTab.jsp" />
								<s:include value="/project/instp/application/granted/editMemberTab.jsp" />
							</div>
						</div>
					</s:form>
					<s:include value="/project/instp/application/granted/memberTemplate.jsp" />
					<div id="optr" class="btn_bar2">
						<input id="prev" class="btn2" type="button" value="<s:text name="i18n_PrevStep" />" /></li>
						<input id="next" class="btn2" type="button" value="<s:text name="i18n_NextStep" />" /></li>
						<input id="finish" class="btn1" type="button" value="<s:text name="i18n_Finish" />" /></li>
						<input id="cancel" class="btn1" type="button" value="<s:text name="i18n_Cancel" />" /></li>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use(['javascript/project/instp/application/granted/add.js','javascript/project/project_share/application/validate.js'], function(add, validate) {
					validate.valid();
					add.init();
				});
			</script>
		</body>
	</s:i18n>
</html>