<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title></title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="tool/uploadify/css/uploadify.css">
		</head>
		
		<body>
			<div style="width:700px;">
				<s:include value="/validateError.jsp" />
				<div style="overflow-y:scroll;height:500px; *margin-right:20px; _margin-right:20px;">
					<s:form id="form_apply">
						<s:hidden id="selectedTab" name="selectedTab"/>
						<s:hidden id="listType" name="listType" />
						<s:hidden id="projectid" name="projectid" />
						<s:hidden id="varId" name="varId"/>
						<s:hidden id="varImportedStatus" name="varImportedStatus"/>
						<s:hidden id="defaultSelectCode" name="defaultSelectCode" />
						<s:hidden id="defaultSelectProductTypeCode" name="defaultSelectProductTypeCode" />
						<s:hidden id="defaultSelectApproveVarCode" name="defaultSelectApproveVarCode"/>
						<s:hidden id="oldProductTypeCode" name="oldProductTypeCode"/>
						<s:hidden id="oldProductTypeOther" name="oldProductTypeOther"/>
						<s:hidden id="editFlag" name="editFlag" value="2"/>
						<s:hidden id="modifyFlag" name="editFlag" value="1"/>
						<s:hidden id="memberFlag" name="memberFlag" />
						<s:hidden id="personId" name="personId"></s:hidden>
						<s:hidden id="uploadKey" name="uploadKey" value="%{#session.uploadKey}" />
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr>
								<td colspan="2">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" style="line-height:28px; border:solid #999; border-width:0 0 1px 0;">
										<td align="left" style="padding-left:15px; font-weight:bold;">第<span id="num"><s:property value="times" /></span>次变更</td>
									</table>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11" width="150">
									<span class="table_title6">
										变更事项：<br />
										<span class="select_all_box">全选&nbsp;<input id="checkAllVarItem" name="check" type="checkbox"  title="点击全选"/>
									</span>
								</td>
								<td class="table_td12">
<%--									<table id="var_item_list" width="100%" border="0" cellspacing="0" cellpadding="2">--%>
<%--										<s:iterator value="#application.varItems" status="stat">--%>
<%--											<s:if test="(#stat.index)%4 == 0"><tr></s:if>--%>
<%--											<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='#application.varItems[#stat.index][0]' />" type="checkbox" name="selectIssue" value="<s:property value='#application.varItems[#stat.index][0]' />" class="var_item" /></td>--%>
<%--											<td width="100" valign="top"><s:property value="#application.varItems[#stat.index][1]" /></td>--%>
<%--											<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>--%>
<%--										</s:iterator>--%>
<%--									</table>--%>
									<table id="var_item_list" width="100%" border="0" cellspacing="0" cellpadding="2">
										<s:iterator value="varListForSelect" status="stat">
											<s:if test="(#stat.index)%4 == 0"><tr></s:if>
											<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='varListForSelect[#stat.index][0]' />" type="checkbox" name="selectIssue" value="<s:property value='varListForSelect[#stat.index][0]' />" class="var_item" /></td>
											<td width="100" valign="top"><s:property value="varListForSelect[#stat.index][1]" /></td>
											<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>
										</s:iterator>
									</table>
								</td>
								<td class="table_td13" width="70"></td>
							</tr>
							
							<tr class="table_tr2">
								<td class="table_td11"><span>上传变更申请书：</span></td>
								<td class="table_td12">
									<input type="file" id="file_${varId}" />
									<s:hidden name="file"/>
								</td>
								<td class="table_td13"></td>
							</tr>
							
							<tr id="cD" style="display:none;">
								<td colspan = '3'>
									<div id="member">
										<s:iterator value="members" status="stat">
											<table class="table_valid" width="100%" border="0" cellspacing="0" cellpadding="2">
												<tr>
													<td class="table_td8" width="150"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
													<td class="table_td9">
														<input type="button" class="add_member btn1"  value="添加" />&nbsp;
														<input type="button" class="delete_row btn1" value="删除" />&nbsp;
														<input type="button" class="up_row btn1" value="上移" />&nbsp;
														<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
														<span class="tip">可拖拽空白区域对成员排序</span>
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class="table_tr2">
													<td class="table_td11"><span  class="table_title6"><s:text name="检索或新建成员" />：</span></td>
													<td class="table_td12"><s:radio name="%{'members['+#stat.index+'].type'}" cssClass="j_showMembertDetail" theme="simple" list="#{'0':getText('检索库中已有成员'),'1':getText('新建成员')}" /></td>
													<td class="table_td13"></td>
												</tr>
												<tr>
													<td class="table_td8"><span class="table_title6">成员类型：</span></td>
													<td class="table_td9">
														<s:select cssClass="select" name="%{'members['+#stat.index+'].memberType'}" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('教师'),'2':getText('外部专家'),'3':getText('学生')}" />
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_choseMember">
													<td class="table_td8" width="150"><span class="table_title6">姓名：</span></td>
													<td class="table_td9">
														<input name="selectMember" type="button" class="btn1 select_btn" value="选择" />
														<div name="memberNameDiv" class="choose_show"><s:property value="%{members[#stat.index].memberName}"/></div>
														<s:hidden cssClass="newDirectorId" name="%{'members['+#stat.index+'].member.id'}" />
													</td>
													<s:hidden name="%{'members['+#stat.index+'].id'}" /><!-- 项目成员Id -->
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_choseMember">
													<td class="table_td8"><span>所在单位与部门：</span></td>
													<td class="table_td9">
														<div name="memberUnitDiv" class="unit_show"><s:property value="%{members[#stat.index].agencyName}"/>&nbsp;<s:property value="%{members[#stat.index].divisionName}"/></div>
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8" width="150"><span class="table_title6"><s:text name='i18n_Name'/>：</span></td>
													<td class="table_td9">
														<s:textfield name="%{'members['+#stat.index+'].memberName'}" value="%{members[#stat.index].memberName}"/>
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8"><span class="table_title6"><s:text name='所在单位'/>：</span></td>
													<td class="table_td9" style="line-height:22px;">
														<input type="button" class="btn1 select_btn select_uni_btn_1" value="<s:text name='高校'/>" />
														<div id="university" class="choose_show"><s:property value="%{members[#stat.index].agencyName}"/></div>
														<s:hidden name="%{'members['+#stat.index+'].university.id'}"/>
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8"><span class="table_title6"><s:text name='所在部门'/>：</span></td>
													<td class="table_td9" style="line-height:22px;">
														<input type="button" class="btn1 select_btn select_dep_btn_1" value="<s:text name='i18n_SelectDepartment'/>" />
														<input type="button" class="btn2 select_btn select_ins_btn_1" value="<s:text name='i18n_SelectInstitute'/>" />
														<div id="unit" class="choose_show"><s:property value="%{members[#stat.index].divisionName}"/></div>
														<s:hidden id="departmentId" name="%{'members['+#stat.index+'].department.id'}" /><!-- 院系 -->
														<s:hidden id="instituteId" name="%{'members['+#stat.index+'].institute.id'}" /><!-- 研究机构-->
														<s:hidden id="divisionType" name="%{'members['+#stat.index+'].divisionType'}" /><!-- 1：研究机构，2:院系 -->
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8"><span class="table_title6"><s:text name='所在单位'/>：</span></td>
													<td class="table_td9">
														<s:textfield name="%{'members['+#stat.index+'].agencyName'}" value="%{members[#stat.index].agencyName}" />
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8"><span class="table_title6"><s:text name='所在部门'/>：</span></td>
													<td class="table_td9">
														<s:textfield name="%{'members['+#stat.index+'].divisionName'}" value="%{members[#stat.index].divisionName}"/>
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8" ><span><s:text name='证件类型' />：</span></td>
													<td class="table_td9">
														<s:select cssClass="select" name="%{'members['+#stat.index+'].idcardType'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8" ><span><s:text name='证件号码' />：</span></td>
													<td class="table_td9">
														<s:textfield name="%{'members['+#stat.index+'].idcardNumber'}" value="%{members[#stat.index].idcardNumber}" cssClass="input_css2"/></td>
													<td class="table_td10"></td>
												</tr>
												<tr class = "j_newMember" style="display: none">
													<td class="table_td8" ><span><s:text name='i18n_Gender'/>：</span></td>
													<td class="table_td9">
														<s:select cssClass="select" name="%{'members['+#stat.index+'].gender'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#application.sexList" />
													<td class="table_td10"></td>
												</tr>
												<tr>
													<td class="table_td8"><span class="table_title6">职称：</span></td>
													<td class="table_td9">
														<s:select cssClass="select" name="%{'members['+#stat.index+'].specialistTitle'}" headerKey="-1" headerValue="--%{getText('请选择')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr>
													<td class="table_td8"><span>专业：</span></td>
													<td class="table_td9">
														<s:textfield name="%{'members['+#stat.index+'].major'}"></s:textfield>
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr>
													<td class="table_td8"><span class="table_title6">每年工作时间（月）：</span></td>
													<td class="table_td9">
														<s:textfield name="%{'members['+#stat.index+'].workMonthPerYear'}" /></td>
													<td class="table_td10"></td>
												</tr>
												<tr>
													<td class="table_td8"><span>分工情况：</span></td>
													<td class="table_td9">
														<s:textfield name="%{'members['+#stat.index+'].workDivision'}" />
													</td>
													<td class="table_td10"></td>
												</tr>
												<tr>
													<td class="table_td8"><span class="table_title6">是否负责人：</span></td>
													<td class="table_td9">
														<s:select cssClass="select" name="%{'members['+#stat.index+'].isDirector'}" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('是'),'0':getText('否')}" />
													</td>
													<td class="table_td10"></td>
												</tr>
											</table>
										</s:iterator>
									</div>
									<div id="last_add_div" style="display:none;">
										<table  width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td class="table_td8" width="150"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"></span></b></div>&nbsp;</td>
												<td class="table_td9">
													<input type="button" class="add_last_table btn1" value="添加" />
												</td>
												<td class="table_td10"></td>
												<td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr id="cA" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">变更项目管理机构：</span></td>
								<td class="table_td12" style="line-height:22px;">
									<input type="button" id="select_dep_btn" class="btn1 select_btn" value="院系" />
									<input type="button" id="select_ins_btn" class="btn2 select_btn" value="研究基地" />
									<div id="varUnit" class="choose_show"><s:property value="ageDeptInst"/></div>
									<s:hidden id="deptInstFlag" name="deptInstFlag" /><!-- 1：研究机构，2:院系 -->
									<s:hidden id="deptInstId" name="deptInstId" />
									<s:hidden id="oldDeptInstId" name="oldDeptInstId"/>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr id="cPF" class="table_tr2" style="display:none;">
								<td class="table_td11" valign="top">
									<span class="table_title6">
										变更成果形式：<br />
										<span class="select_all_box">全选&nbsp;<input id="checkAllProductTypeItem" name="check" type="checkbox"  title="点击全选" />
									</span>
								</td>
								<td class="table_td12">
									<table border="0" cellspacing="0" cellpadding="2">
										<s:iterator value="#application.pdtItems" status="stat">
											<s:if test="(#stat.index)%4 == 0"><tr></s:if>
											<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='#application.pdtItems[#stat.index][0]' />" type="checkbox" name="selectProductType" value="<s:property value='#application.pdtItems[#stat.index][0]' />" /></td>
											<td width="110" valign="top"><s:property value="#application.pdtItems[#stat.index][1]" /></td>
											<s:if test="#stat.last">
												<td colspan='3'>
													<span id="productTypeOtherSpan" style="display:none;">
														<s:textfield name="productTypeOther" cssClass="input_css_other"/>
														<br/><span class="tip">多个以分号（即;或；）隔开</span>
													</span>
												</td>
												<td></td>
											</s:if>
											<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>
										</s:iterator>
									</table>
								</td>
								<td class="table_td13"></td>
							</tr>
							<s:hidden id="oldProductTypeCode" name="oldProductTypeCode"/>
							<s:hidden id="oldProductTypeOther" name="oldProductTypeOther"/>
							<tr id="cN" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">变更项目名称：</span></td>
								<td class="table_td12">
									中文名称：<s:textfield name="chineseName" cssClass="input_css1"/>
									</br></br>
									英文名称：<s:textfield name="englishName" cssClass="input_css1"/>
								</td>
								<td class="table_td13"></td>
							</tr>
							<s:hidden id="projectName" name="projectName"/>
							<tr id="cC" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">研究内容有重大调整：</span></td>
								<td class="table_td12">是</td>
								<td class="table_td13"></td>
							</tr>
							<tr id="pO" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">延期：</span></td>
								<td class="table_td12">
									<s:textfield id="variation5" name="newDate1" cssClass="FloraDatepick" readonly="true">
										<s:param name="value">
											<s:date name="newDate1" format="yyyy-MM-dd" />
										</s:param>
									</s:textfield>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span>上传延期项目计划书：</span>
									<input type="file" id="file_postponementPlan_${varId}" />
									<s:hidden name="postponementPlanFile"/>
								</td>
								<td class="table_td13"></td>
							</tr>
							<s:hidden id="planEndDate" name="planEndDate"/>
							<%--<s:hidden id="limitedDate" value="%{#request.limitedDate}" name="limitedDate"/>--%>
							<tr id="sBO" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">自行中止项目：</span></td>
								<td class="table_td12">是</td>
								<td class="table_td13"></td>
							</tr>
							<tr id="aW" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">申请撤项：</span></td>
								<td class="table_td12">是</td>
								<td class="table_td13"></td>
							</tr>
							<tr id="o" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">其他：</span></td>
								<td class="table_td12"><s:textfield name="otherInfo" cssClass="input_css"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr id="fEE" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6"><s:text name="变更项目经费预算"/>：</span></td>
								<td class="table_td12">
									<input type="button" id="modifyVarFee" class="btn1 select_btn" value="<s:text name="修改" />" />
									<div id="totalFee" class="choose_show"><s:property value="newFee.totalFee"/></div>
								</td>
									<s:hidden id="feeNote" name="newFee.feeNote" />
									<s:hidden id="bookFee" name="newFee.bookFee" />
									<s:hidden id="bookNote" name="newFee.bookNote" />
									<s:hidden id="dataFee" name="newFee.dataFee" />
									<s:hidden id="dataNote" name="newFee.dataNote" />
									<s:hidden id="travelFee" name="newFee.travelFee" />
									<s:hidden id="travelNote" name="newFee.travelNote" />
									<s:hidden id="conferenceFee" name="newFee.conferenceFee" />
									<s:hidden id="conferenceNote" name="newFee.conferenceNote" />
									<s:hidden id="internationalFee" name="newFee.internationalFee" />
									<s:hidden id="internationalNote" name="newFee.internationalNote" />
									<s:hidden id="deviceFee" name="newFee.deviceFee" />
									<s:hidden id="deviceNote" name="newFee.deviceNote" />
									<s:hidden id="consultationFee" name="newFee.consultationFee" />
									<s:hidden id="consultationNote" name="newFee.consultationNote" />
									<s:hidden id="laborFee" name="newFee.laborFee" />
									<s:hidden id="laborNote" name="newFee.laborNote" />
									<s:hidden id="printFee" name="newFee.printFee" />
									<s:hidden id="printNote" name="newFee.printNote" />
									<s:hidden id="indirectFee" name="newFee.indirectFee" />
									<s:hidden id="indirectNote" name="newFee.indirectNote" />
									<s:hidden id="otherFeeD" name="newFee.otherFee" />
									<s:hidden id="otherNote" name="newFee.otherNote" />
									<s:hidden id="totalFeeD" name="newFee.totalFee"/>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span  class="table_title6">是否同意变更：</span></td>
								<td class="table_td12"><s:radio name="varResult" cssClass="j_showResultDetail" theme="simple" list="#{'2':getText('同意'),'1':getText('不同意')}"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2" id="var_result_item_tr" style="display:none">
								<td class="table_td11">
									<span class="table_title6">
										同意变更事项：<br />
										<span class="select_all_box">全选&nbsp;<input id="checkAllVarResultItem" name="check" type="checkbox"  title="点击全选"/></span>
									</span>
								</td>
								<td class="table_td12">
									<table id="var_result_item_list" width="100%" border="0" cellspacing="0" cellpadding="2">
										<s:iterator value="#application.varItems" status="stat">
											<s:if test="(#stat.index)%4 == 0"><tr></s:if>
											<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='#application.varItems[#stat.index][0]' />" type="checkbox" name="varSelectIssue" value="<s:property value='#application.varItems[#stat.index][0]' />" /></td>
											<td width="100" valign="top"><s:property value="#application.varItems[#stat.index][1]" /></td>
											<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>
										</s:iterator>
									</table>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span  class="table_title6">变更时间：</span></td>
								<td class="table_td12">
									<s:textfield name="varDate" cssClass="FloraDatepick" readonly="true">
										<s:param name="value">
											<s:date name="varDate" format="yyyy-MM-dd" />
										</s:param>
									</s:textfield>
								</td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><s:text name='变更原因'/>：</td>
								<td class="table_td12"><s:textarea name='variationReason' rows="2" cssClass="textarea_css"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11">审核意见：</td>
								<td class="table_td12"><s:textarea name='varImportedOpinion' rows="2" cssClass="textarea_css"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11">审核意见：<br /><span>（反馈给负责人）</span></td>
								<td class="table_td12"><s:textarea name="varOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
								<td class="table_td13"></td>
							</tr>
						</table>
					</s:form>
				</div>
				<table id="table_member" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
					<tr>
						<td class="table_td8" width="150"><div class="sort_title"><b><span>项目成员</span><span title="memberSpan"><s:property value="#stat.index+1" /></span></b></div>&nbsp;</td>
						<td class="table_td9">
							<input type="button" class="add_member btn1"  value="添加" />&nbsp;
							<input type="button" class="delete_row btn1" value="删除" />&nbsp;
							<input type="button" class="up_row btn1" value="上移" />&nbsp;
							<input type="button" class="down_row btn1" value="下移" />&nbsp;&nbsp;
							<span class="tip">可拖拽空白区域对成员排序</span>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class="table_tr2">
						<td class="table_td11"><span  class="table_title6"><s:text name="检索或新建成员" />：</span></td>
						<td class="table_td12">
							<input type="radio" name="members[].type" class="j_showMembertDetail" value="0" checked="true" /><span>检索库中已有成员</span>
						<s:radio name="members[].type" cssClass="j_showMembertDetail" theme="simple" list="#{'0':getText('检索库中已有成员'),'1':getText('新建成员')}" />
						</td>
						<td class="table_td13"></td>
					</tr>
					<tr>
						<td class="table_td8"><span class="table_title6">成员类型：</span></td>
						<td class="table_td9">
							<s:select cssClass="select" name="members[].memberType" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('教师'),'2':getText('外部专家'),'3':getText('学生')}" />
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_choseMember">
						<td class="table_td8" width="150"><span class="table_title6">姓名：</span></td>
						<td class="table_td9">
							<input name="selectMember" type="button" class="btn1 select_btn" value="选择" />
							<div name="memberNameDiv" class="choose_show"></div>
							<s:hidden cssClass="newDirectorId" name="members[].member.id" />
						</td>
						<td class="table_td10" width="70"></td>
					</tr>
					<tr class = "j_choseMember">
						<td class="table_td8"><span>所在单位与部门：</span></td>
						<td class="table_td9">
							<div name="memberUnitDiv" class="unit_show"></div>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td8" width="150"><span class="table_title6"><s:text name='i18n_Name'/>：</span></td>
						<td class="table_td9">
							<s:textfield name="members[].memberName"/>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td8"><span class="table_title6"><s:text name='所在单位'/>：</span></td>
						<td class="table_td9" style="line-height:22px;">
							<input type="button" class="btn1 select_btn select_uni_btn_1" value="<s:text name='高校'/>" />
							<div id="university" class="choose_show"><s:property value="members[].agencyName}"/></div>
							<s:hidden name="members[].university.id"/>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td11"><span class="table_title6"><s:text name='所在部门'/>：</span></td>
						<td class="table_td12" style="line-height:22px;">
							<input type="button" class="btn1 select_btn select_dep_btn_1" value="<s:text name='i18n_SelectDepartment'/>" />
							<input type="button" class="btn2 select_btn select_ins_btn_1" value="<s:text name='i18n_SelectInstitute'/>" />
							<div id="unit" class="choose_show"></div>
							<s:hidden id="departmentId" name="members[].department.id" /><!-- 院系 -->
							<s:hidden id="instituteId" name="members[].institute.id" /><!-- 研究机构-->
							<s:hidden id="divisionType" name="members[].divisionType" /><!-- 1：研究机构，2:院系 -->
						</td>
						<td class="table_td13"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td8"><span class="table_title6"><s:text name='所在单位'/>：</span></td>
						<td class="table_td9">
							<s:textfield name="members[].agencyName" cssClass="input_css2"/>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td8"><span class="table_title6"><s:text name='所在部门'/>：</span></td>
						<td class="table_td9">
							<s:textfield name="members[].divisionName" cssClass="input_css2"/>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td8" ><span><s:text name='i18n_IdcardType' />：</span></td>
						<td class="table_td9">
							<s:select cssClass="select" name="members[].idcardType" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}"/>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td8" ><span><s:text name='i18n_IdcardNumber' />：</span></td>
						<td class="table_td9">
							<s:textfield name="members[].idcardNumber" cssClass="input_css2"/></td>
						<td class="table_td10"></td>
					</tr>
					<tr class = "j_newMember" style="display: none">
						<td class="table_td8"><span><s:text name="i18n_Gender" />：</span></td>
						<td class="table_td9">
							<s:select cssClass="select" name="%{'members['+#stat.index+'].gender'}" headerKey="-1" headerValue="--%{getText('i18n_PleaseSelect')}--" list="#application.sexList" />
						</td>
						<td class="table_td10"></td>
					<tr>
					<tr>
						<td class="table_td8"><span class="table_title6">职称：</span></td>
						<td class="table_td9">
							<s:select cssClass="select" name="members[].specialistTitle" headerKey="-1" headerValue="--%{getText('请选择')}--" list="%{projectService.getChildrenMapByParentIAndStandard()}" />
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr>
						<td class="table_td8"><span>专业：</span></td>
						<td class="table_td9">
							<s:textfield name="members[].major"></s:textfield>
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr>
						<td class="table_td8"><span class="table_title6">每年工作时间（月）：</span></td>
						<td class="table_td9">
							<s:textfield name="members[].workMonthPerYear" /></td>
						<td class="table_td10"></td>
					</tr>
					<tr>
						<td class="table_td8"><span>分工情况：</span></td>
						<td class="table_td9">
							<s:textfield name="members[].workDivision" />
						</td>
						<td class="table_td10"></td>
					</tr>
					<tr>
						<td class="table_td8"><span class="table_title6">是否负责人：</span></td>
						<td class="table_td9">
							<s:select cssClass="select" name="members[].isDirector" headerKey='-1' headerValue="--%{getText('请选择')}--" list="#{'1':getText('是'),'0':getText('否')}" />
						</td>
						<td class="table_td10"></td>
					</tr>
				</table>
				<div class="btn_div_view">
					<input id="save" type="button" class="btn1 j_modifyResultSave" value="保存" />
					<input id="submit" type="button" class="btn1 j_modifyResultSubmit" value="提交" />
					<input id="cancel" type="button" class="btn1" value="取消"/>
				</div>
			</div>
			<script type="text/javascript" src="javascript/project/project_share/handlers_variation.js"></script>
			<script type="text/javascript">
				seajs.use(['javascript/project/post/variation/edit.js','javascript/project/project_share/variation/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>