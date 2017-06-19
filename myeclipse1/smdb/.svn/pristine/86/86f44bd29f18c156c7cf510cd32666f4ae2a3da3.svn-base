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
			<div style="width:1000px;">
				<s:include value="/validateError.jsp" />
				<div style="overflow-y:scroll;height:500px; *margin-right:20px; _margin-right:20px;">
					<s:form id="form_apply">
						<s:hidden id="selectedTab" name="selectedTab"/>
						<s:hidden id="listType" name="listType" />
						<s:hidden id="projectid" name="projectid" />
						<s:hidden id="varImportedStatus" name="varImportedStatus"/>
						<s:hidden id="varId" name="varId" value="111"/>
						<s:hidden id="defaultSelectCode" name="defaultSelectCodeCode" />
						<s:hidden id="defaultSelectProductTypeCode" name="defaultSelectProductTypeCode" />
						<s:hidden id="defaultSelectApproveVarCode" name="defaultSelectApproveVarCode"/>
						<s:hidden id="oldProductTypeCode" name="oldProductTypeCode"/>
						<s:hidden id="oldProductTypeOther" name="oldProductTypeOther"/>
						<s:hidden id="editFlag" name="editFlag" value="2"/>
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
								<td class="table_td11"><span class="table_title6">上传变更申请书：</span></td>
								<td class="table_td12">
									<input type="file" id="file_add" />
									<s:hidden name="file"/>
								</td>
								<td class="table_td13"></td>
							</tr>
						
							<tr id="cD" class="table_tr2" style="display:none;">
								<td class="table_td11"><span class="table_title6">变更项目成员：<br>（含负责人）&nbsp;&nbsp;</span></td>
								<td class="table_td12">
									<div id="member">
										<div>
											<a class="add_member" href="javascript:void(0);" title="添加" style="margin-right:5px;"><img src="image/btn_add.png" /></a>
											
											<%--
											<a class="up_row" href="javascript:void(0);" title="上移" style="margin-right:5px;"><img src="image/btn_up.png" /></a>
											<a class="down_row" href="javascript:void(0);" title="下移" style="margin-right:5px;"><img src="image/btn_down.png" /></a>
											<a class="delete_row" href="javascript:void(0);" title="删除" style="margin-right:5px;"><img src="image/btn_delete.png" /></a>
											--%>
										</div>
										<table class="table_valid" width="100%" border="1" cellspacing="0" cellpadding="2" bordercolor="#CCCCCC" style="border-collapse:collapse;table-layout:fixed;">
											<thead class="table_tr3">
												<tr>
													<!-- <td width="20">选择</td> -->
													<td width="20">序号</td>
													<td width="60">类型<img src="image/ico04.gif"></td>
													<td width="60">姓名<img src="image/ico04.gif"></td>
													<td width="20">关联/新建</td>
													<td width="130">所在单位及部门</td>
													<td width="90">是否为负责人<img src="image/ico04.gif"></td>
													<!-- <td width="20">操作</td> -->
													<td width="60">操作</td>
												</tr>
											</thead>
											<tbody>
												<s:iterator value="members" status="stat">
												<tr class="table_tr3">
													<!-- <td><input name="selectMember" type="radio"></td> -->
													<td><span class="memberSpan"><s:property value="#stat.index+1" /></span></td>
													<td><s:select cssClass="select" name="%{'members['+#stat.index+'].memberType'}" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家','3':'学生'}" /></td>
													<td style="position:relative;">
														<s:if test="%{type==1}"><s:textfield cssClass="keyword" name="%{'members['+#stat.index+'].memberName'}" size="6" /></s:if>
														<s:else><s:textfield cssClass="keyword" name="%{'members['+#stat.index+'].memberName'}" size="6" readonly="true"/></s:else>
														<div class="info">
															<div class="info-item"></div>
															<div class="info-more"><a class="selectPerson" href="javascript:void(0);">更多</a></div>
														</div>
														<s:hidden name="%{'members['+#stat.index+'].member.id'}" /><!--对应人员personId-->
														<s:hidden name="%{'members['+#stat.index+'].id'}" /><!--对应项目成员id-->
													</td>
													<td>
														<s:hidden name="%{'members['+#stat.index+'].type'}" />
														<span class="type">
															<s:if test="%{type==1}">新建</s:if>
															<s:else>关联</s:else>
														</span>
													</td>
													<td>
														<s:if test="!members[#stat.index].agencyName &&!members[#stat.index].divisionName">
															<s:if test="memberType==2">
															<div class="choose_expert">
																<s:textfield name="%{'members['+#stat.index+'].agencyName'}" size="15" placeholder="单位" />
																<s:textfield name="%{'members['+#stat.index+'].divisionName'}" size="15" placeholder="部门" />
															</div>
															</s:if>
															<s:else>
															<div class="choose_division">
																<input type="button" class="btn1 select_dep_btn_1" value="院系" />
																<input type="button" class="btn1 select_ins_btn_1" value="基地" />
															</div>
															</s:else>
														</s:if>
														<div class="choose_division" style="display:none;">
															<input type="button" class="btn1 select_dep_btn_1" value="院系" />
															<input type="button" class="btn1 select_ins_btn_1" value="基地" />
														</div>
														<div class="choose_expert" style="display:none;">
															<s:textfield name="%{'members['+#stat.index+'].agencyName'}" size="15" placeholder="单位" />
															<s:textfield name="%{'members['+#stat.index+'].divisionName'}" size="15" placeholder="部门" />
														</div>
														<span class="divisionName">
															<s:property value="%{members[#stat.index].agencyName}"/>
															<s:property value="%{members[#stat.index].divisionName}"/>
														</span>
														<s:hidden class="universityId" name="%{'members['+#stat.index+'].university.id'}"/>
														<s:hidden class="departmentId" name="%{'members['+#stat.index+'].department.id'}" /><!-- 院系 -->
														<s:hidden class="instituteId" name="%{'members['+#stat.index+'].institute.id'}" /><!-- 研究机构-->
														<s:hidden class="divisionType" name="%{'members['+#stat.index+'].divisionType'}" /><!-- 1：研究机构，2:院系 -->
													</td>
													<td>
														<s:select cssClass="select" name="%{'members['+#stat.index+'].isDirector'}" headerKey='-1' headerValue="--请选择--" list="#{'1':'是','0':'否'}" />
														<s:select cssClass="display-none" name="%{'members['+#stat.index+'].idcardType'}" headerKey="-1" headerValue="--请选择--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}" /><!-- 证件类型 -->
														<s:select cssClass="display-none" name="%{'members['+#stat.index+'].gender'}" headerKey="-1" headerValue="--请选择--" list="#application.sexList" /><!-- 性别 -->
														<s:select cssClass="display-none" name="%{'members['+#stat.index+'].specialistTitle'}" headerKey="-1" headerValue="--请选择--" list="%{projectService.getChildrenMapByParentIAndStandard()}" /><!-- 职称 -->
													</td>
													<td>
														<a class="moreDetail" href="javascript:void(0);" title="更多"><img src="image/btn_more.png" /></a>
														<a class="up_row" href="javascript:void(0);" title="上移"><img src="image/btn_up.png" /></a>
														<a class="down_row" href="javascript:void(0);" title="下移"><img src="image/btn_down.png" /></a>
														<a class="delete_row" href="javascript:void(0);" title="删除"><img src="image/btn_delete.png" /></a>
													</td>
													<s:hidden name="%{'members['+#stat.index+'].idcardNumber'}"></s:hidden><!-- 证件号 -->
													<s:hidden name="%{'members['+#stat.index+'].major'}"></s:hidden><!-- 专业 -->
													<s:hidden name="%{'members['+#stat.index+'].workMonthPerYear'}"></s:hidden><!-- 每年工作时间（月） -->
													<s:hidden name="%{'members['+#stat.index+'].workDivision'}"></s:hidden><!-- 分工情况 -->
													<s:hidden name="%{'members['+#stat.index+'].position'}"></s:hidden><!-- 职务 -->
													<s:hidden name="%{'members['+#stat.index+'].birthday'}"></s:hidden><!-- 出生日期 -->
													<s:hidden name="%{'members['+#stat.index+'].education'}"></s:hidden><!-- 学历 -->
												</tr>
												</s:iterator>
											</tbody>
										</table>
										<span class="tips"><img src="image/ico04.gif">人员类型、姓名和是否为负责人为必填项，若为负责人，则需填写所在单位及部门</span>
										</td>
								<td class="table_td13" style="color:red"></td>
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
											<td width="110" valign="top">
												<s:property value="#application.pdtItems[#stat.index][1]" />
											</td>
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
									中文名称：<s:textfield id="variation30" name="chineseName" cssClass="input_css" />
									</br></br>
									英文名称：<s:textfield name="englishName" cssClass="input_css" />
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
									<input type="file" id="file_postponementPlan_add" />
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
								<td class="table_td11"><span>变更项目经费预算：</span></td>
								<td class="table_td12">
									<input type="button" id="addVarFee" class="btn1 select_btn" value="添加" />
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
								<td class="table_td12"><s:radio name="varResult" cssClass="j_showResultDetail" theme="simple" list="#{'2':'同意','1':'不同意'}"/></td>
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
											<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='#application.varItems[#stat.index][0]' />" type="checkbox" name="varSelectIssue" value="<s:property value='#application.varItems[#stat.index][0]' />"/></td>
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
								<td class="table_td11">变更原因：</td>
								<td class="table_td12"><s:textarea name='variationReason' rows="2" cssClass="textarea_css"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11">审核意见：</td>
								<td class="table_td12"><s:textarea name='varImportedOpinion' rows="2" cssClass="textarea_css"/></td>
								<td class="table_td13"></td>
							</tr>
							<tr class="table_tr2">
								<td class="table_td11"><span>审核意见：<br /></span><span>（反馈给负责人）</span></td>
								<td class="table_td12"><s:textarea name="varOpinionFeedback" rows="2" cssClass="textarea_css" /><br/><span class="warning">此意见将会反馈给该项目负责人，请慎重填写！</span></td>
								<td class="table_td13"></td>
							</tr>
						</table>
					</s:form>
					<table id="table_member" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
						<tr>
							<!-- <td><input name="selectMember" type="radio"></td> -->
							<td><span class="memberSpan"></span></td>
							<td><s:select cssClass="select" name="members[].memberType" headerKey='-1' headerValue="--请选择--" list="#{'1':'教师','2':'外部专家','3':'学生'}" /></td>
							<td style="position:relative;">
								<s:textfield cssClass="keyword" name="members[].memberName" size="6" />
								<div class="info">
									<div class="info-item"></div>
									<div class="info-more"><a class="selectPerson" href="javascript:void(0);">更多</a></div>
								</div>
								<s:hidden name="members[].id" /><!--对应人员personId-->
								<s:hidden name="members[].member.id" /><!--对应项目成员id-->
							</td>
							<td><s:hidden name="members[].type" value="1"/><span class="type">新建</span></td>
							<td>
								<div class="choose_division">
									<input type="button" class="btn1 select_dep_btn_1" value="院系" />
									<input type="button" class="btn1 select_ins_btn_1" value="基地" />
								</div>
								<div class="choose_expert" style="display:none;padding:0;">
									<input type="text" name="members[].agencyName" size="15" placeholder="单位"/><br />
									<input type="text" name="members[].divisionName" size="15" placeholder="部门" />
								</div>
								<span class="divisionName">
									<s:property value="members[].agencyName"/>
									<s:property value="members[].divisionName"/>
								</span>
								<s:hidden class="universityId" name="members[].university.id" />
								<s:hidden class="departmentId" name="members[].department.id" /><!-- 院系 -->
								<s:hidden class="instituteId" name="members[].institute.id" /><!-- 研究机构-->
								<s:hidden class="divisionType" name="members[].divisionType" /><!-- 1：研究机构，2:院系 -->
							</td>
							<td>
								<s:select cssClass="select" name="members[].isDirector" headerKey="-1" headerValue="--请选择--" list="#{'0':'否','1':'是'}"></s:select>
								<s:select cssClass="display-none" name="members[].idcardType" headerKey="-1" headerValue="--请选择--" list="#{'身份证':'身份证','军官证':'军官证','护照':'护照'}" /><!-- 证件类型 -->
								<s:select cssClass="display-none" name="members[].gender" headerKey="-1" headerValue="--请选择--" list="#application.sexList" /><!-- 性别 -->
								<s:select cssClass="display-none" name="members[].specialistTitle" headerKey="-1" headerValue="--请选择--" list="%{projectService.getChildrenMapByParentIAndStandard()}" /><!-- 职称 -->
							</td>
							<td>
								<a class="moreDetail" href="javascript:void(0);" title="更多"><img src="image/btn_more.png" /></a>
								<a class="up_row" href="javascript:void(0);" title="上移"><img src="image/btn_up.png" /></a>
								<a class="down_row" href="javascript:void(0);" title="下移"><img src="image/btn_down.png" /></a>
								<a class="delete_row" href="javascript:void(0);" title="删除"><img src="image/btn_delete.png" /></a>
							</td>
							<s:hidden name="members[].idcardNumber"></s:hidden><!-- 证件号 -->
							<s:hidden name="members[].major"></s:hidden><!-- 专业 -->
							<s:hidden name="members[].workMonthPerYear"></s:hidden><!-- 每年工作时间（月） -->
							<s:hidden name="members[].workDivision"></s:hidden><!-- 分工情况 -->
							<s:hidden name="members[].position"></s:hidden><!-- 职务 -->
							<s:hidden name="members[].birthday"></s:hidden><!-- 出生日期 -->
							<s:hidden name="members[].education"></s:hidden><!-- 学历 -->
						</tr>
					</table>
				</div>
				<div class="btn_div_view">
					<input id="save" type="button" class="btn1 j_addResultSave" value="保存" />
					<input id="submit" type="button" class="btn1 j_addResultSubmit" value="提交" />
					<input id="cancel" type="button" class="btn1" value="取消"/>
				</div>
			</div>
			<script type="text/javascript" src="javascript/project/project_share/handlers_variation.js"></script>
			<script type="text/javascript">
				seajs.use(['javascript/project/entrust/variation/edit.js','javascript/project/project_share/variation/validate.js'], function(edit, validate) {
					validate.valid();
					edit.init();
				});
			</script>
		</body>
	
</html>