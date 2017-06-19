<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_mid_template" style="display:none;">
	<s:hidden value="${midInfo[1]}" id="deadlineMid" />
	{for midinspection in midList}
		{if midinspection_index == 0}<!-- 最近一次中检 -->
			<!-- 研究人员申请 -->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if isDirector == 1 && (midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 1) && endPassAlready != 1 && endPending != 1 && midPassAlready != 1 && midForbid != 1 && midPending != 1 && granted != null && granted.status == 1}
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_ADD'>
						<div class="p_box_b_2">
							<a href="javascript:void(0);" class="j_downloadMidTemplate" ><s:text name="i18n_DownloadMidFileTemplate"/></a>
						</div>
						<div class="p_box_b_1" id="applyMid">
							<input type="button" class="btn1 j_toAddMidApplyPop" value="<s:text name='i18n_Apply'/>" />
						</div>
					</sec:authorize>
				{/if}
			</s:if>
			<!-- 管理人员录入 -->
			<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_DATA_ADD'><!-- 录入结项 -->
					{if midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 1 && accountType == "MINISTRY" && midPassAlready==0 && midPending==0 && endPassAlready!=1 && midForbid != 1 && granted != null && granted.status == 1}
						<div><input class="btn1 j_addMidResultPop" type="button" value="<s:text name='i18n_AddResult'/>"/><br/></div>
					{elseif granted != null && granted.status == 3}
						<div><s:text name="i18n_CanNotAddResForStop" /></div>
					{elseif granted != null && granted.status == 4}
						<div><s:text name="i18n_CanNotAddResForWithdraw" /></div>
					{elseif endPassAlready == 1}
						<div><s:text name="i18n_CanNotAddEndResForPass" /></div>
					{elseif midPassAlready!=0}
						<div><s:text name="i18n_CanNotAddMidResForPass" /></div>
					{elseif midPending!=0}
						<div><s:text name="i18n_CanNotAddMidResForInDeal" /></div>
					{elseif midForbid == 1}
						<div><s:text name="i18n_CanNotAddResForForbid" /></div>
					{/if}
				</sec:authorize>
			</s:elseif>
		{/if}
		<div class="p_box_t">
			<div class="p_box_t_t"><s:text name='i18n_On' /><span class="number"  name="${midList.length}">${midinspection_index}</span><s:text name='i18n_ItemMidinspection' />{if midinspection_index == 0}&nbsp;<s:text name="i18n_Current"/>{/if}</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div>
			<div class="p_box_b">
				<div class="p_box_b_1">
					{if midinspection.isImported == 0}
						{if midinspection.applicantSubmitStatus == 3}<s:text name="i18n_SubmitDate"/>：
						{else}<s:text name="i18n_SaveDate"/>：
						{/if}
						${midinspection.applicantSubmitDate}
					{elseif midinspection.isImported == 1}
						<span class="import_css"><s:text name='i18n_ImportData' /></span>
						{if midinspection_index == 0 && accountType == "MINISTRY" && midinspection.finalAuditStatus==2 && midPassAlready==0 && midForbid != 1 && granted != null && granted.status == 1}
							<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_DATA_MODIFY'>
								<input class="btn1 j_modifyMidResultPop" type="button" value="<s:text name='i18n_Modify'/>" />
							</sec:authorize>
							<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_DATA_ADD'>
								<input class="btn1 j_submitMidResult" type="button" value="<s:text name='i18n_Submit'/>" />
							</sec:authorize>
						{/if}
					{else}
					{/if}
				</div>
				<div class="p_box_b_2">
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_VIEW'>
					{if midinspection.file != null}
						<div style="float: right;"><img src="image/ico09.gif" />
							<a href="" id="${midinspection.id}" class="download_instp_2"><s:text name='i18n_DownloadMidinspection' /></a>
							({if midFileSizeList[midinspection_index] != null}
							${midFileSizeList[midinspection_index]}
							{else}附件不存在
							{/if})		
						</div>
					{/if}
					{if midinspection.finalAuditStatus==3 && granted != null}
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_DATA_UPLOAD'>
						<div style="float: right;"><img src="image/ico10.gif" /><a href="javascript:void(0);" class="j_uploadMidPop" midId="${midinspection.id}"><s:text name='i18n_UploadMidApply'/></a>&nbsp;&nbsp;</div>
					</sec:authorize>
					</s:if>
					{/if}
					</sec:authorize>
				</div>
			 </div>
			<div class="p_box_body">
				<div class="p_box_body_t">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<tr class="table_tr7">
							<td class="key" width="70"><s:text name="i18n_Note" />：</td>
							<td class="value">{if midinspection.note != null && midinspection.note != ""}<pre>${midinspection.note}</pre>{/if}</td>
						</tr>
					</table>
				</div>
			</div>
			
			<%--    经费明细		--%>
			{if projectMidFees[midinspection_index].feeFlag == 1 }
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr3">
						<td width="16%" class="key"><s:text name='批准经费' /></td>
						<td width="16%" class="value">${projectMidFees[midinspection_index].approveFee}</td>
						<td width="16%" class="key"><s:text name='已拨经费' /></td>
						<td width="16%" class="value">${projectMidFees[midinspection_index].fundedFee}</td>
						<td width="16%" class="key"><s:text name='未拨经费' /></td>
						<td width="16%" class="value">${projectMidFees[midinspection_index].toFundFee}</td>
					</tr>
					<tr class="table_tr3">
						<td class="key" colspan="6"><s:text name='中检经费结算明细' /></td>
					</tr>
				</table>
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				{if projectFeeGranted != null }
					<tbody>
						<tr class="table_tr3">
							<td><s:text name='经费科目' /></td>
							<td width="15%"><s:text name='经费预算金额（万元）' /></td>
							<td width="15%"><s:text name='经费支出金额（万元）' /></td>
							<td width="200"><s:text name='开支说明'/></td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="图书资料费" /></td>
							<td class="value">${projectFeeGranted.bookFee}</td>
							<td class="value">${projectMidFees[midinspection_index].bookFee}</td>
							<td class="value">${projectMidFees[midinspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="数据采集费" /></td>
							<td class="value">${projectFeeGranted.dataFee}</td>
							<td class="value">${projectMidFees[midinspection_index].dataFee}</td>
							<td class="value">${projectMidFees[midinspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="调研差旅费" /></td>
							<td class="value">${projectFeeGranted.travelFee}</td>
							<td class="value">${projectMidFees[midinspection_index].travelFee}</td>
							<td class="value">${projectMidFees[midinspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="会议费" /></td>
							<td class="value">${projectFeeGranted.conferenceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].conferenceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="国际合作交流费" /></td>
							<td class="value">${projectFeeGranted.internationalFee}</td>
							<td class="value">${projectMidFees[midinspection_index].internationalFee}</td>
							<td class="value">${projectMidFees[midinspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="设备购置和使用费" /></td>
							<td class="value">${projectFeeGranted.deviceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].deviceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="专家咨询和评审费" /></td>
							<td class="value">${projectFeeGranted.consultationFee}</td>
							<td class="value">${projectMidFees[midinspection_index].consultationFee}</td>
							<td class="value">${projectMidFees[midinspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="助研津贴和劳务费" /></td>
							<td class="value">${projectFeeGranted.laborFee}</td>
							<td class="value">${projectMidFees[midinspection_index].laborFee}</td>
							<td class="value">${projectMidFees[midinspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="印刷和出版费" /></td>
							<td class="value">${projectFeeGranted.printFee}</td>
							<td class="value">${projectMidFees[midinspection_index].printFee}</td>
							<td class="value">${projectMidFees[midinspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="间接费用" /></td>
							<td class="value">${projectFeeGranted.indirectFee}</td>
							<td class="value">${projectMidFees[midinspection_index].indirectFee}</td>
							<td class="value">${projectMidFees[midinspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="其他" /></td>
							<td class="value">${projectFeeGranted.otherFee}</td>
							<td class="value">${projectMidFees[midinspection_index].otherFee}</td>
							<td class="value">${projectMidFees[midinspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="合计" /></td>
							<td class="value">${projectFeeGranted.totalFee}</td>
							<td class="value" colspan="2">${projectMidFees[midinspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费" /></td>
							<td class="value" colspan="3">${projectMidFees[midinspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费支出说明" /></td>
							<td class="value" colspan="3">${projectMidFees[midinspection_index].feeNote}</td>
						</tr>
					</tbody>
					{else}
					<tbody>
						<tr class="table_tr3">
							<td><s:text name='经费科目' /></td>
							<td width="15%"><s:text name='经费支出金额（万元）' /></td>
							<td width="300"><s:text name='开支说明'/></td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="图书资料费" /></td>
							<td class="value">${projectMidFees[midinspection_index].bookFee}</td>
							<td class="value">${projectMidFees[midinspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="数据采集费" /></td>
							<td class="value">${projectMidFees[midinspection_index].dataFee}</td>
							<td class="value">${projectMidFees[midinspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="调研差旅费" /></td>
							<td class="value">${projectMidFees[midinspection_index].travelFee}</td>
							<td class="value">${projectMidFees[midinspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="会议费" /></td>
							<td class="value">${projectMidFees[midinspection_index].conferenceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="国际合作交流费" /></td>
							<td class="value">${projectMidFees[midinspection_index].internationalFee}</td>
							<td class="value">${projectMidFees[midinspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="设备购置和使用费" /></td>
							<td class="value">${projectMidFees[midinspection_index].deviceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="专家咨询和评审费" /></td>
							<td class="value">${projectMidFees[midinspection_index].consultationFee}</td>
							<td class="value">${projectMidFees[midinspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="助研津贴和劳务费" /></td>
							<td class="value">${projectMidFees[midinspection_index].laborFee}</td>
							<td class="value">${projectMidFees[midinspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="印刷和出版费" /></td>
							<td class="value">${projectMidFees[midinspection_index].printFee}</td>
							<td class="value">${projectMidFees[midinspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="间接费用" /></td>
							<td class="value">${projectMidFees[midinspection_index].indirectFee}</td>
							<td class="value">${projectMidFees[midinspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="其他" /></td>
							<td class="value">${projectMidFees[midinspection_index].otherFee}</td>
							<td class="value">${projectMidFees[midinspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="合计" /></td>
							<td class="value" colspan="2">${projectMidFees[midinspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费" /></td>
							<td class="value" colspan="2">${projectMidFees[midinspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费支出说明" /></td>
							<td class="value" colspan="2">${projectMidFees[midinspection_index].feeNote}</td>
						</tr>
					</tbody>
					{/if}
				</table>
			</div>
			{/if}
			<!-- 研究人员 -->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if midinspection_index == 0 && (midinspection.applicantSubmitStatus == 1 || midinspection.applicantSubmitStatus == 2)}<!-- 中检申请暂存或退回 -->
					<div id="addProductMid" class="p_box_body">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr height="36">
								<td width="150">
									<s:text name='i18n_SubmitStatus' />：{if midinspection.applicantSubmitStatus == 1}<s:text name='i18n_SendBack'/>{else}<s:text name="i18n_Save"/>{/if}
								</td>
								<td align="right">
									{if isDirector == 1 && granted != null && granted.status == 1}
										{if endPassAlready != 1 && endPending != 1 && midPassAlready != 1 && midForbid != 1}
											<sec:authorize ifAllGranted="ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_MODIFY">
												<input type="button" value="<s:text name='i18n_Modify' />" class="btn1 j_toModifyMidApplyPop"/>&nbsp;
											</sec:authorize>
											<sec:authorize ifAllGranted="ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_ADD">
												<input type="button" value="<s:text name='i18n_Submit' />" class="btn1 j_submitMidApply"/>&nbsp;
											</sec:authorize>
										{/if}
										<sec:authorize ifAllGranted="ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_DELETE">
											<input type="button" value="<s:text name='i18n_Delete' />" class="btn1 j_deleteMidApply"/>
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</table>
					</div>
				{/if}
			</s:if>
			<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 "><!-- 部门及以上管理人员 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_VIEW'>
					<div class="p_box_body">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<thead>
								<tr class="head_title">
									<td colspan="3"><s:text name="i18n_AuditTable"/></td>
								</tr>
							</thead>
							<tbody>
								<tr class="table_tr3">
									<td><s:text name='i18n_ManagerOpinion' /></td>
									<td width="30%"><s:text name='i18n_MidinspectionResult' /></td>
									<td width="300"><s:text name='i18n_ViewMoreDetail'/></td>
								</tr>
								<!-- 部门审核信息 -->
								<tr class="table_tr4">
									<td><s:text name='i18n_InstOpinion' /></td>
									<td>
										{if midinspection.deptInstAuditResult == 1}
											{if midinspection.deptInstAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
											{elseif midinspection.deptInstAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
											{elseif midinspection.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif midinspection.deptInstAuditResult == 2}
											{if midinspection.deptInstAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
											{elseif midinspection.deptInstAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
											{elseif midinspection.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif midinspection.deptInstAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、部门账号、最近一次中检处在部门审核级别 -->
										{if midinspection.isImported == 0 && midinspection_index == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && midinspection.status == 2 && midinspection.finalAuditStatus != 3}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if midinspection.deptInstAuditStatus == 0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_addMidAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
														<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
													</sec:authorize>
												{elseif midinspection.deptInstAuditStatus < 3}
													<span class="ico_txt2"><a midId="${midinspection.id}" alt="1" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
													{if (isPrincipal == 1 && (midAuditorInfo[1] == belongId || midAuditorInfo[2] == belongId)) || (isPrincipal == 0 && midAuditorInfo[0] == belongId)}
													<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_MODIFY'>
														<input class="btn1 j_modifyMidAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_submitMidAudit" type="button" value="<s:text name='i18n_Submit' />" />
														<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
													</sec:authorize>
													{/if}
												{/if}
											{/if}	
										{else}
											<span class="ico_txt2"><a midId="${midinspection.id}" alt="1" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										{/if}
									</td>
								</tr>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0"><!-- 校级及以上管理人员 -->
									<tr class="table_tr4">
										<td><s:text name='i18n_UniversityOpinion'/></td>
										<td>
											{if midinspection.universityAuditResult == 1}
												{if midinspection.universityAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
												{elseif midinspection.universityAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
												{elseif midinspection.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
												{/if}
											{elseif midinspection.universityAuditResult == 2}
												{if midinspection.universityAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
												{elseif midinspection.universityAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
												{elseif midinspection.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
												{/if}
											{elseif midinspection.universityAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
											{/if}
										</td>
										<td>
											<!-- 非导入数据、高校账号、最近一次中检处在高校审核级别 -->
											{if midinspection.isImported == 0 && midinspection_index == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && midinspection.status == 3 && midinspection.finalAuditStatus != 3}
												{if endPassAlready != 1 && granted != null && granted.status == 1}
													{if midinspection.universityAuditStatus == 0}
														<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_addMidAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
															<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
														</sec:authorize>
													{elseif midinspection.universityAuditStatus < 3}
														<span class="ico_txt2"><a midId="${midinspection.id}" alt="2" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
														{if (isPrincipal == 1 && midAuditorInfo[4] == belongId) || (isPrincipal == 0 && midAuditorInfo[3] == belongId)}
														<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_MODIFY'>
															<input class="btn1 j_modifyMidAuditPop" type="button" value="<s:text name='i18n_Modify' />"/>
														</sec:authorize>
														<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_submitMidAudit" type="button" value="<s:text name='i18n_Submit' />" />
															<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
														</sec:authorize>
														{/if}
													{/if}
												{/if}	
											{else}
												<span class="ico_txt2"><a midId="${midinspection.id}" alt="2" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											{/if}
										</td>
									</tr>
								</s:if>
								{if utypeNew == 0}<!-- 地方高校 -->
									<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
										<tr class="table_tr4">
											<td><s:text name='i18n_ProvinceOpinion'/></td>
											<td>
												{if midinspection.provinceAuditResult == 1}
													{if midinspection.provinceAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
													{elseif midinspection.provinceAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
													{elseif midinspection.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
													{/if}
												{elseif midinspection.provinceAuditResult == 2}
													{if midinspection.provinceAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
													{elseif midinspection.provinceAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
													{elseif midinspection.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
													{/if}
												{elseif midinspection.provinceAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
												{/if}
											</td>
											<td>
												<!-- 非导入数据、省厅账号、最近一次中检处在省厅审核级别 -->
												{if midinspection.isImported == 0 && midinspection_index == 0 && accountType == "PROVINCE" && midinspection.status == 4 && midinspection.finalAuditStatus != 3}
													{if endPassAlready != 1 && granted != null && granted.status == 1}
														{if midinspection.provinceAuditStatus == 0}
															<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1 j_addMidAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
																<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
															</sec:authorize>
														{elseif midinspection.provinceAuditStatus < 3}
															<span class="ico_txt2"><a midId="${midinspection.id}" alt="3" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
															{if (isPrincipal == 1 && midAuditorInfo[6] == belongId) || (isPrincipal == 0 && midAuditorInfo[5] == belongId)}														
															<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_MODIFY'>
																<input class="btn1 j_modifyMidAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
															</sec:authorize>
															<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1 j_submitMidAudit" type="button" value="<s:text name='i18n_Submit' />" />
																<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
															</sec:authorize>
															{/if}
														{/if}
													{/if}	
												{else}
													<span class="ico_txt2"><a midId="${midinspection.id}" alt="3" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
												{/if}
											</td>
										</tr>
									</s:if>
								{/if}
							</tbody>
						</table>
					</div>
				</sec:authorize>
			</s:elseif>
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0"><!-- 部级及以上管理人员 -->
						<thead>
							<tr class="head_title">
								<td colspan="3"><s:text name="i18n_MidResultTable"/></td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td><s:text name='i18n_AuditDate' /></td>
								<td width="30%"><s:text name='i18n_MidinspectionResult' /></td>
								<td width="300"><s:text name='i18n_ViewMoreDetail'/></td>
							</tr>
							<tr class="table_tr4">
								<td>
									${midinspection.finalAuditDate}
								</td>
								<td>
									{if midinspection.finalAuditResult == 1}
										{if midinspection.finalAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
										{elseif midinspection.finalAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
										{elseif midinspection.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif midinspection.finalAuditResult == 2}
										{if midinspection.finalAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
										{elseif midinspection.finalAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
										{elseif midinspection.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif midinspection.finalAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、教育部账号、最近一次中检处在教育部审核级别 -->
									{if midinspection.isImported == 0 && midinspection_index == 0 && accountType == "MINISTRY" && midinspection.status == 5 && midinspection.finalAuditStatus != 3}
										{if endPassAlready != 1 && granted != null && granted.status == 1}
											{if midinspection.finalAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
													<input class="btn1 j_addMidAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
													<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
												</sec:authorize>
											{elseif midinspection.finalAuditStatus < 3}
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_VIEW'>
													<span class="ico_txt2"><a midId="${midinspection.id}" alt="4" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
												</sec:authorize>
												{if (isPrincipal == 1 && midAuditorInfo[8] == belongId) || (isPrincipal == 0 && midAuditorInfo[7] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_MODIFY'>
													<input class="btn1 j_modifyMidAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_ADD'>
													<input class="btn1 j_submitMidAudit" type="button" value="<s:text name='i18n_Submit' />"/>
													<input class="btn1 j_backMidApply" type="button" value="<s:text name='i18n_SendBack'/>" />
												</sec:authorize>
												{/if}
											{/if}
										{/if}	
									{else}
										<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLYAUDIT_VIEW'>
											<span class="ico_txt2"><a midId="${midinspection.id}" alt="4" class="j_viewMidAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</tbody>
					</s:if>
					<s:else><!-- 部级以下所有人员 -->
						{if midinspection.isImported == 1 || midinspection.applicantSubmitStatus == 3}
							<thead>
								<tr class="head_title">
									<td colspan="2"><s:text name="i18n_MidResultTable"/></td>
								</tr>
							</thead>
							<tbody>
								<tr class="table_tr3">
									<td width="50%"><s:text name='i18n_AuditDate'/></td>
									<td width="50%"><s:text name="i18n_MidinspectionResult"/></td>
								</tr>
								<tr class="table_tr4">
									<td>{if midinspection.finalAuditStatus == 3}${midinspection.finalAuditDate}{/if}</td>
									<td>
										{if midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 2}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
										{elseif midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 1}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
										{else}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
										{/if}
									</td>
								</tr>
							</tbody>
						{/if}
					</s:else>
				</table>
			</div>
			{if midinspection.finalAuditStatus == 3}
				<div class="p_box_body">
					<div class="p_box_body_t">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr class="table_tr7">
								<td class="key" width="160"><s:text name="i18n_AuditOpinionFeedback" /><s:text name='i18n_FeedbackToDirector'/>：</span></td>
								<td class="value">{if midinspection.finalAuditOpinionFeedback != null && midinspection.finalAuditOpinionFeedback != ""}<pre>${midinspection.finalAuditOpinionFeedback}</pre>{/if}</td>
							</tr>
						</table>
					</div>
				</div>
			{/if}	
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
			<!-- 对应中检成果 -->
			<div class="p_box_body">
				<s:i18n name="csdc.resources.i18n_Product">
					<div style="margin-top:10px;margin-bottom:5px;"><s:text name="i18n_MidinspactionProduct"/>
						{if midinspection_index == 0}<s:hidden id="midInspectionId" value="${midinspection.id}"/>{/if}
						<span id="mid_total">${midProdInfo[midinspection_index].productSize}</span>
						<span style="margin-right:20px;"><s:text name="i18n_Item"/>
							{if midProdInfo[midinspection_index].productSize!=0}(&nbsp;
								{if midProdInfo[midinspection_index].paperSize != null && midProdInfo[midinspection_index].paperSize != 0}<s:text name="i18n_Paper"/>${midProdInfo[midinspection_index].paperSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if midProdInfo[midinspection_index].bookSize != null && midProdInfo[midinspection_index].bookSize != 0}<s:text name="i18n_Book"/>${midProdInfo[midinspection_index].bookSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if midProdInfo[midinspection_index].consultationSize != null && midProdInfo[midinspection_index].consultationSize != 0}<s:text name="i18n_Consultation"/>${midProdInfo[midinspection_index].consultationSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if midProdInfo[midinspection_index].electronicSize != null && midProdInfo[midinspection_index].electronicSize != 0}<s:text name="i18n_Electronic"/>${midProdInfo[midinspection_index].electronicSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if midProdInfo[midinspection_index].patentSize != null && midProdInfo[midinspection_index].patentSize != 0}<s:text name="i18n_Patent"/>${midProdInfo[midinspection_index].patentSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if midProdInfo[midinspection_index].otherProductSize != null && midProdInfo[midinspection_index].otherProductSize != 0}<s:text name="i18n_OtherProduct"/>${midProdInfo[midinspection_index].otherProductSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								)
							{/if}
						</span>
						{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection_index == 0 && midinspection.isImported == 0}
							<s:hidden id="midInspectionId" value="${midinspection.id}"/>
							<input id="view_add_product" class="btn1" type="button" value="<s:text name='i18n_AddProduct'/>" name="mid_prod"/>
							<input id="view_mod_product" class="btn1" type="button" value="<s:text name='i18n_Modify'/>" name="mid_prod"/>
							<span class="text_red">&nbsp;<s:text name="i18n_AddMidProductWarn"/></span>
						{/if}
						<s:hidden id="canAuditMidProduct" value="${canAuditMidProduct}"/>
					</div>
					<table id="list_table_mid_${midinspection_index}" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr4">
							
								{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection_index == 0 && midinspection.isImported == 0}
									<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllAccountsOrNot'/>"/></td>
									<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								{/if}
								
								<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
									{if midinspection_index == 0 && canAuditMidProduct}
										<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
											<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllAccountsOrNot'/>"/></td>
											<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
										</s:if>
									{/if}
								</sec:authorize>
								
								<td width="40"><s:text name="i18n_Number" /></td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td><s:text name="i18n_ProductName"/></td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55"><s:text name="i18n_ProductType"/></td>
			
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55"><s:text name="i18n_FirstAuthor"/></td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55"><s:text name="i18n_BelongUnit"/></td>
						
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55"><s:text name="i18n_Dtype"/></td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="95"><s:text name="i18n_IsMarkMoeSupport"/></td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								<td width="35"><s:text name="i18n_FirstAuditResult"/></td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								<td width="35"><s:text name="i18n_FinalAuditResult"/></td>
							</tr>
						</thead>
						<tbody>
							{for item in midProdInfo[midinspection_index].productList}
								<tr class="table_txt_tr3">
									{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection_index == 0 && midinspection.isImported == 0}
										<td><input type="checkbox" name="entityIds" value="${item[0]}" class="selectProduct" alt="${item[2]}"/></td>
										<td></td>
									{/if}
								
									{if midinspection_index == 0 && canAuditMidProduct}
										<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
											<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
												<td>
													<input type="checkbox" name="entityIds" value="${item[0]}" class="selectProduct"/>
													<input type="hidden" name="firstAuditStatus" value="${item[9]}"/>
													<input type="hidden" name="finalAuditStatus" value="${item[10]}"/>
												</td>
												<td><div style="display:none;"><input type="checkbox" name="productTypes" value="${item[2]}"/></div></td>
											</s:if>
										</sec:authorize>
									{/if}
									
									<td class="index">${item_index}</td>
									<td></td>
									<td>
										<a id="${item[0]}" type="${item[2]}" class="view_product" href="">${item[1]}</a>
									</td>
									<td></td>
									<td>
										{if item[2] == 'paper'}<s:text name="i18n_Paper"/>
										{elseif item[2] == 'book'}<s:text name="i18n_Book"/>
										{elseif item[2] == 'consultation'}<s:text name="i18n_Consultation"/>
										{elseif item[2] == 'electronic'}<s:text name="i18n_Electronic"/>
										{elseif item[2] == 'patent'}<s:text name="i18n_Patent"/>
										{elseif item[2] == 'otherProduct'}<s:text name="i18n_OtherProduct"/>
										{/if}
									</td>
									<td></td>
									<td>{if item[4] != null && item[4] != ""}
											<a id="${item[4]}" class="view_author" href="">${item[3]}</a>
										{else}${item[3]}
										{/if}
									</td>
									<td></td>
									<td>{if item[6] != null && item[6] != ""}
											<a id="${item[6]}" class="view_university" href="">${item[5]}</a>
										{else}${item[5]}
										{/if}
									</td>
									<td></td>
									<td>${item[7]}</td>
									<td></td>
									<td>
										{if item[8] == 1}<s:text name="i18n_Yes"/>
										{else}<s:text name="i18n_No"/>
										{/if}
									</td>
									<td></td>
									<td>
										{if item[9] == 0}<s:text name="i18n_UnAudit"/>
										{elseif item[9] == 1 }<s:text name="i18n_UnPass"/>
										{elseif item[9] == 2 }<s:text name="i18n_Pass"/>
										{/if}
									</td>
									<td></td>
									<td>
										{if item[10] == 0}<s:text name="i18n_UnAudit"/>
										{elseif item[10] == 1 }<s:text name="i18n_UnPass"/>
										{elseif item[10] == 2 }<s:text name="i18n_Pass"/>
										{/if}
									</td>
								</tr>
							{forelse}
								<tr class="table_txt_tr3">
									<td align="center"><s:text name="i18n_NoRecords"/></td>
								</tr>
							{/for}
						</tbody>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr4">
							<td width="69" align="left">
								{if midinspection_index == 0}
									{if canAuditMidProduct}
										<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
											<input id="view_aud_product" class="btn1" type="button" value="<s:text name='i18n_Audit'/>" name="mid_prod"/>
										</sec:authorize>
									{/if}
									{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection.isImported == 0}
										<s:hidden id="midInspectionId" value="${midinspection.id}"/>
										<input id="view_del_product" class="btn1" type="button" value="<s:text name='i18n_Delete'/>" name="mid_prod"/>
									{/if}
								{/if}
							</td>
						</tr>
					</table>
				</s:i18n>
			</div>
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
		</div>
	{forelse}<!-- 无中检信息 -->
		<!-- 研究人员 -->
		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
			{if isDirector == 1 && endPassAlready != 1 && endPending != 1 && midPassAlready != 1 && midPending != 1 && midForbid != 1 && granted != null && granted.status == 1}
				<div id="applyMid">
					<div><a class="j_downloadMidTemplate" style="cursor: pointer;"><s:text name='i18n_DownloadMidFileTemplate'/></a></div>
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_APPLY_ADD'>
						<div id="applyMid"><input type="button" class="btn1 j_toAddMidApplyPop" value="<s:text name='i18n_Apply' />" /><br/></div>
					</sec:authorize>
				</div>
			{else}
				<div class="p_box_body">
					<div style="text-align:center;"><s:text name="i18n_NoRecords" /></div>
				</div>
			{/if}
		</s:if>
		<!-- 部级管理人员 -->
		<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
			<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_MIDINSPECTION_DATA_ADD'>
				{if midPassAlready==0 && midPending==0 && endPassAlready!=1 && midForbid != 1 && granted != null && granted.status == 1}
					<div><input class="btn1 j_addMidResultPop" type="button" value="<s:text name='i18n_AddResult'/>" ><br/></div>
				{elseif granted != null && granted.status == 3}
					<div><s:text name="i18n_CanNotAddResForStop" /></div>
				{elseif granted != null && granted.status == 4}
					<div><s:text name="i18n_CanNotAddResForWithdraw" /></div>
				{elseif endPassAlready == 1}
					<div><s:text name="i18n_CanNotAddEndResForPass" /></div>
				{elseif midPassAlready!=0}
					<div><s:text name="i18n_CanNotAddMidResForPass" /></div>
				{elseif midPending!=0}
					<div><s:text name="i18n_CanNotAddMidResForInDeal" /></div>
				{elseif midForbid == 1}
					<div><s:text name="i18n_CanNotAddResForForbid" /></div>					
				{/if}
			</sec:authorize>
			<div class="p_box_body">
				<div style="text-align:center;"><s:text name="i18n_NoRecords" /></div>
			</div>
		</s:elseif>
		<!-- 其他 -->
		<s:else>
			<div class="p_box_body">
				<div style="text-align:center;"><s:text name="i18n_NoRecords" /></div>
			</div>
		</s:else>
	{/for}
</textarea>
<div id="view_mid" style="display:none;"></div>
<s:hidden id="midProAudAlr" value='1'/>
<s:form id="midForm" theme="simple">
	<s:hidden name="projectid" id="midFormProjectid" />
	<s:hidden id="midAuditStatus" name="midAuditStatus" value="%{#request.midAuditStatus}"/>
	<s:hidden id="midAuditOpinion" name="midAuditOpinion"/>
	<s:hidden id="midAuditOpinionFeedback" name="midAuditOpinionFeedback"/>
	<s:hidden id="midAuditResult" name="midAuditResult"/>
</s:form>
<s:form id="uploadMidFile_form" theme="simple">
	<s:hidden id="midId" name="midId"/>
	<s:hidden id="midFileId" name="midFileId" />
</s:form>