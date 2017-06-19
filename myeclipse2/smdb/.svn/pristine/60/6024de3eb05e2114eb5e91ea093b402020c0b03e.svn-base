<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_ann_template" style="display:none;">
	<s:hidden value="${annInfo[1]}" id="deadlineAnn" />
	{for anninspection in annList}
		{if anninspection_index == 0}<!-- 最近一次年检 -->
			<!-- 研究人员申请 -->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if isDirector == 1 && endPassAlready != 1 && endPending != 1 && annPending != 1 && granted != null && granted.status == 1}
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_ADD'>
						<div class="p_box_b_2">
							<a href="javascript:void(0);" class="j_downloadAnnTemplate" ><s:text name="i18n_DownloadAnnFileTemplate"/></a>
						</div>
						<div class="p_box_b_1" id="applyAnn">
							<input type="button" class="btn1 j_toAddAnnApplyPop" value="<s:text name='i18n_Apply'/>" />
						</div>
					</sec:authorize>
				{/if}
			</s:if>
			<!-- 管理人员录入 -->
			<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_DATA_ADD'><!-- 录入结项 -->
					{if accountType == "MINISTRY" && annPending==0 && endPassAlready != 1 && granted != null && granted.status == 1}
						<div><input class="btn1 j_addAnnResultPop" type="button" value="<s:text name='i18n_AddResult'/>" /><br/></div>
					{elseif granted != null && granted.status == 3}
						<div><s:text name="i18n_CanNotAddResForStop" /></div>
					{elseif granted != null && granted.status == 4}
						<div><s:text name="i18n_CanNotAddResForWithdraw" /></div>
					{elseif endPassAlready == 1}
						<div><s:text name="i18n_CanNotAddEndResForPass" /></div>
					{elseif annPending!=0}
						<div><s:text name="i18n_CanNotAddAnnResForInDeal" /></div>
					{/if}
				</sec:authorize>
			</s:elseif>
		{/if}
		<div class="p_box_t">
			<div class="p_box_t_t">${anninspection.year}<s:text name='i18n_ItemAnninspection' />{if anninspection_index == 0}&nbsp;<s:text name="i18n_Current"/>{/if}</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div>
			<div class="p_box_b">
				<div class="p_box_b_1">
					{if anninspection.isImported == 0}
						{if anninspection.applicantSubmitStatus == 3}<s:text name="i18n_SubmitDate"/>：
						{else}<s:text name="i18n_SaveDate"/>：
						{/if}
						${anninspection.applicantSubmitDate}
					{elseif anninspection.isImported == 1}
						<span class="import_css"><s:text name='i18n_ImportData' /></span>
						{if anninspection_index == 0 && accountType == "MINISTRY" && anninspection.finalAuditStatus == 2 && granted != null && granted.status == 1}
							<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_DATA_MODIFY'>
								<input class="btn1 j_modifyAnnResultPop" type="button" value="<s:text name='i18n_Modify'/>"/>
							</sec:authorize>
							<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_DATA_ADD'>
								<input class="btn1 j_submitAnnResult" type="button" value="<s:text name='i18n_Submit'/>"/>
							</sec:authorize>
						{/if}
					{else}
					{/if}
				</div>
				<div class="p_box_b_2">
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_VIEW'>
					{if anninspection.file != null}
						<div style="float: right;"><img src="image/ico09.gif" />
							<a href="" id="${anninspection.id}" class="download_instp_6"><s:text name='i18n_DownloadAnninspection' /></a>
							({if annFileSizeList[anninspection_index] != null}
								${annFileSizeList[anninspection_index]}
							{else}附件不存在
							{/if})
						</div>
					{/if}
					{if anninspection.finalAuditStatus==3 && granted != null}
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_DATA_UPLOAD'>
						<div style="float: right;"><img src="image/ico10.gif" /><a href="javascript:void(0);" class="j_uploadAnnPop" annId="${anninspection.id}"><s:text name='i18n_UploadAnnApply'/></a>&nbsp;&nbsp;</div>
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
							<td class="key" width="60"><s:text name="i18n_Note" />：</td>
							<td class="value" >{if anninspection.note != null && anninspection.note != ""}<pre>${anninspection.note}</pre>{/if}</td>
						</tr>
					</table>
				</div>
			</div>
			
			<%--	经费明细		--%>
			{if projectAnnFees[anninspection_index].feeFlag == 1 }
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr3">
						<td width="16%" class="key"><s:text name='批准经费' /></td>
						<td width="16%" class="value">${projectAnnFees[anninspection_index].approveFee}</td>
						<td width="16%" class="key"><s:text name='已拨经费' /></td>
						<td width="16%" class="value">${projectAnnFees[anninspection_index].fundedFee}</td>
						<td width="16%" class="key"><s:text name='未拨经费' /></td>
						<td width="16%" class="value">${projectAnnFees[anninspection_index].toFundFee}</td>
					</tr>
					<tr class="table_tr3">
						<td class="key" colspan="6"><s:text name='年检经费结算明细' /></td>
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
							<td class="value">${projectAnnFees[anninspection_index].bookFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="数据采集费" /></td>
							<td class="value">${projectFeeGranted.dataFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].dataFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="调研差旅费" /></td>
							<td class="value">${projectFeeGranted.travelFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].travelFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="会议费" /></td>
							<td class="value">${projectFeeGranted.conferenceFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].conferenceFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="国际合作交流费" /></td>
							<td class="value">${projectFeeGranted.internationalFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].internationalFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="设备购置和使用费" /></td>
							<td class="value">${projectFeeGranted.deviceFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].deviceFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="专家咨询和评审费" /></td>
							<td class="value">${projectFeeGranted.consultationFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].consultationFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="助研津贴和劳务费" /></td>
							<td class="value">${projectFeeGranted.laborFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].laborFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="印刷和出版费" /></td>
							<td class="value">${projectFeeGranted.printFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].printFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="间接费用" /></td>
							<td class="value">${projectFeeGranted.indirectFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].indirectFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="其他" /></td>
							<td class="value">${projectFeeGranted.otherFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].otherFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="合计" /></td>
							<td class="value">${projectFeeGranted.totalFee}</td>
							<td class="value" colspan="2">${projectAnnFees[anninspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费" /></td>
							<td class="value" colspan="3">${projectAnnFees[anninspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费支出说明" /></td>
							<td class="value" colspan="3">${projectAnnFees[anninspection_index].feeNote}</td>
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
							<td class="value">${projectAnnFees[anninspection_index].bookFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="数据采集费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].dataFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="调研差旅费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].travelFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="会议费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].conferenceFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="国际合作交流费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].internationalFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="设备购置和使用费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].deviceFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="专家咨询和评审费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].consultationFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="助研津贴和劳务费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].laborFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="印刷和出版费" /></td>
							<td class="value">${projectAnnFees[anninspection_index].printFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="间接费用" /></td>
							<td class="value">${projectAnnFees[anninspection_index].indirectFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="其他" /></td>
							<td class="value">${projectAnnFees[anninspection_index].otherFee}</td>
							<td class="value">${projectAnnFees[anninspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="合计" /></td>
							<td class="value" colspan="2">${projectAnnFees[anninspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费" /></td>
							<td class="value" colspan="2">${projectAnnFees[anninspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="结余经费支出说明" /></td>
							<td class="value" colspan="2">${projectAnnFees[anninspection_index].feeNote}</td>
						</tr>
					</tbody>
					{/if}
				</table>
			</div>
			{/if}
			<!-- 研究人员 -->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if anninspection_index == 0 && (anninspection.applicantSubmitStatus == 1 || anninspection.applicantSubmitStatus == 2)}<!-- 年检申请暂存或退回 -->
					<div id="addProductAnn" class="p_box_body">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr height="36">
								<td width="150">
									<s:text name='i18n_SubmitStatus' />：{if anninspection.applicantSubmitStatus == 1}<s:text name='i18n_SendBack'/>{else}<s:text name="i18n_Save"/>{/if}
								</td>
								<td align="right">
									{if isDirector == 1 && granted != null && granted.status == 1}
										{if endPassAlready != 1 && endPending != 1}
											<sec:authorize ifAllGranted="ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_MODIFY">
												<input type="button" value="<s:text name='i18n_Modify' />" class="btn1 j_toModifyAnnApplyPop"/>&nbsp;
											</sec:authorize>
											<sec:authorize ifAllGranted="ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_ADD">
												<input type="button" value="<s:text name='i18n_Submit' />" class="btn1 j_submitAnnApply"/>&nbsp;
											</sec:authorize>
										{/if}
										<sec:authorize ifAllGranted="ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_DELETE">
											<input type="button" value="<s:text name='i18n_Delete' />" class="btn1 j_deleteAnnApply"/>
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</table>
					</div>
				{/if}
			</s:if>
			<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 "><!-- 部门及以上管理人员 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_VIEW'>
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
									<td width="30%"><s:text name='i18n_AnninspectionResult' /></td>
									<td width="300"><s:text name='i18n_ViewMoreDetail'/></td>
								</tr>
								<!-- 部门审核信息 -->
								<tr class="table_tr4">
									<td><s:text name='i18n_DeptInstOpinion' /></td>
									<td>
										{if anninspection.deptInstAuditResult == 1}
											{if anninspection.deptInstAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
											{elseif anninspection.deptInstAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
											{elseif anninspection.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif anninspection.deptInstAuditResult == 2}
											{if anninspection.deptInstAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
											{elseif anninspection.deptInstAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
											{elseif anninspection.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif anninspection.deptInstAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、部门账号、最近一次年检处在部门审核级别 -->
										{if anninspection.isImported == 0 && anninspection_index == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && anninspection.status == 2 && anninspection.finalAuditStatus != 3}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if anninspection.deptInstAuditStatus == 0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_addAnnAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
														<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
													</sec:authorize>
												{elseif anninspection.deptInstAuditStatus < 3}
													<span class="ico_txt2"><a annId="${anninspection.id}" alt="1" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
													{if (isPrincipal == 1 && (annAuditorInfo[1] == belongId || annAuditorInfo[2] == belongId)) || (isPrincipal == 0 && annAuditorInfo[0] == belongId)}
													<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_MODIFY'>
														<input class="btn1 j_modifyAnnAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_submitAnnAudit" type="button" value="<s:text name='i18n_Submit' />" />
														<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
													</sec:authorize>
													{/if}
												{/if}
											{/if}	
										{else}
											<span class="ico_txt2"><a annId="${anninspection.id}" alt="1" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										{/if}
									</td>
								</tr>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0"><!-- 校级及以上管理人员 -->
									<tr class="table_tr4">
										<td><s:text name='i18n_UniversityOpinion'/></td>
										<td>
											{if anninspection.universityAuditResult == 1}
												{if anninspection.universityAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
												{elseif anninspection.universityAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
												{elseif anninspection.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
												{/if}
											{elseif anninspection.universityAuditResult == 2}
												{if anninspection.universityAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
												{elseif anninspection.universityAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
												{elseif anninspection.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
												{/if}
											{elseif anninspection.universityAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
											{/if}
										</td>
										<td>
											<!-- 非导入数据、高校账号、最近一次年检处在高校审核级别 -->
											{if anninspection.isImported == 0 && anninspection_index == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && anninspection.status == 3 && anninspection.finalAuditStatus != 3}
												{if endPassAlready != 1 && granted != null && granted.status == 1}
													{if anninspection.universityAuditStatus == 0}
														<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_addAnnAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
															<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
														</sec:authorize>
													{elseif anninspection.universityAuditStatus < 3}
														<span class="ico_txt2"><a annId="${anninspection.id}" alt="2" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
														{if (isPrincipal == 1 && annAuditorInfo[4] == belongId) || (isPrincipal == 0 && annAuditorInfo[3] == belongId)}
														<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_MODIFY'>
															<input class="btn1 j_modifyAnnAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
														</sec:authorize>
														<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_submitAnnAudit" type="button" value="<s:text name='i18n_Submit' />" />
															<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
														</sec:authorize>
														{/if}
													{/if}
												{/if}	
											{else}
												<span class="ico_txt2"><a annId="${anninspection.id}" alt="2" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											{/if}
										</td>
									</tr>
								</s:if>
								{if utypeNew == 0}<!-- 地方高校 -->
									<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
										<tr class="table_tr4">
											<td><s:text name='i18n_ProvinceOpinion'/></td>
											<td>
												{if anninspection.provinceAuditResult == 1}
													{if anninspection.provinceAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
													{elseif anninspection.provinceAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
													{elseif anninspection.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
													{/if}
												{elseif anninspection.provinceAuditResult == 2}
													{if anninspection.provinceAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
													{elseif anninspection.provinceAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
													{elseif anninspection.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
													{/if}
												{elseif anninspection.provinceAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
												{/if}
											</td>
											<td>
												<!-- 非导入数据、省厅账号、最近一次年检处在省厅审核级别 -->
												{if anninspection.isImported == 0 && anninspection_index == 0 && accountType == "PROVINCE" && anninspection.status == 4 && anninspection.finalAuditStatus != 3}
													{if endPassAlready != 1 && granted != null && granted.status == 1}
														{if anninspection.provinceAuditStatus == 0}
															<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1" type="button j_addAnnAuditPop" value="<s:text name='i18n_Audit'/>" />
																<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
															</sec:authorize>
														{elseif anninspection.provinceAuditStatus < 3}
															<span class="ico_txt2"><a annId="${anninspection.id}" alt="3" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
															{if (isPrincipal == 1 && annAuditorInfo[6] == belongId) || (isPrincipal == 0 && annAuditorInfo[5] == belongId)}
															<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_MODIFY'>
																<input class="btn1 j_modifyAnnAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
															</sec:authorize>
															<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1 j_submitAnnAudit" type="button" value="<s:text name='i18n_Submit' />" />
																<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
															</sec:authorize>
															{/if}
														{/if}
													{/if}	
												{else}
													<span class="ico_txt2"><a annId="${anninspection.id}" alt="3" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
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
								<td colspan="3"><s:text name="i18n_AnnResultTable"/></td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td><s:text name='i18n_AuditDate' /></td>
								<td width="30%"><s:text name='i18n_AnninspectionResult' /></td>
								<td width="300"><s:text name='i18n_ViewMoreDetail'/></td>
							</tr>
							<tr class="table_tr4">
								<td>
									${anninspection.finalAuditDate}
								</td>
								<td>
									{if anninspection.finalAuditResult == 1}
										{if anninspection.finalAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
										{elseif anninspection.finalAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
										{elseif anninspection.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif anninspection.finalAuditResult == 2}
										{if anninspection.finalAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
										{elseif anninspection.finalAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
										{elseif anninspection.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif anninspection.finalAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、教育部账号、最近一次年检处在教育部审核级别 -->
									{if anninspection.isImported == 0 && anninspection_index == 0 && accountType == "MINISTRY" && anninspection.status == 5 && anninspection.finalAuditStatus != 3}
										{if endPassAlready != 1 && granted != null && granted.status == 1}
											{if anninspection.finalAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
													<input class="btn1 j_addAnnAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
													<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
												</sec:authorize>
											{elseif anninspection.finalAuditStatus < 3}
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_VIEW'>
													<span class="ico_txt2"><a annId="${anninspection.id}" alt="4" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
												</sec:authorize>
												{if (isPrincipal == 1 && annAuditorInfo[8] == belongId) || (isPrincipal == 0 && annAuditorInfo[7] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_MODIFY'>
													<input class="btn1 j_modifyAnnAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_ADD'>
													<input class="btn1 j_submitAnnAudit" type="button" value="<s:text name='i18n_Submit' />" />
													<input class="btn1 j_backAnnApply" type="button" value="<s:text name='i18n_SendBack'/>" />
												</sec:authorize>
												{/if}
											{/if}
										{/if}	
									{else}
										<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLYAUDIT_VIEW'>
											<span class="ico_txt2"><a annId="${anninspection.id}" alt="4" class="j_viewAnnAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</tbody>
					</s:if>
					<s:else><!-- 部级以下所有人员 -->
						{if anninspection.isImported == 1 || anninspection.applicantSubmitStatus == 3}
							<thead>
								<tr class="head_title">
									<td colspan="2"><s:text name="i18n_AnnResultTable"/></td>
								</tr>
							</thead>
							<tbody>
								<tr class="table_tr3">
									<td width="50%"><s:text name='i18n_AuditDate'/></td>
									<td width="50%"><s:text name="i18n_AnninspectionResult"/></td>
								</tr>
								<tr class="table_tr4">
									<td>{if anninspection.finalAuditStatus == 3}${anninspection.finalAuditDate}{/if}</td>
									<td>
										{if anninspection.finalAuditStatus == 3 && anninspection.finalAuditResult == 2}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
										{elseif anninspection.finalAuditStatus == 3 && anninspection.finalAuditResult == 1}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
										{else}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
										{/if}
									</td>
								</tr>
							</tbody>
						{/if}
					</s:else>
				</table>
			</div>
			{if anninspection.finalAuditStatus == 3}
				<div class="p_box_body">
					<div class="p_box_body_t">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr class="table_tr7">
								<td class="key" width="160"><s:text name="i18n_AuditOpinionFeedback" />：<s:text name='i18n_FeedbackToDirector'/></span></td>
								<td class="value">{if anninspection.finalAuditOpinionFeedback != null && anninspection.finalAuditOpinionFeedback != ""}<pre>${anninspection.finalAuditOpinionFeedback}</pre>{/if}</td>
							</tr>
						</table>
					</div>
				</div>
			{/if}
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
			<!-- 对应年检成果 -->
			<div class="p_box_body">
				<s:i18n name="csdc.resources.i18n_Product">
					<div style="margin-top:10px;margin-bottom:5px;"><s:text name="i18n_AnninspectionProduct"/>
						{if anninspection_index == 0}<s:hidden id="anninspectionId" value="${anninspection.id}"/>{/if}
						<span id="ann_total">${annProdInfo[anninspection_index].productSize}</span>
						<span style="margin-right:20px;"><s:text name="i18n_Item"/>
							{if annProdInfo[anninspection_index].productSize!=0}(&nbsp;
								{if annProdInfo[anninspection_index].paperSize != null && annProdInfo[anninspection_index].paperSize != 0}<s:text name="i18n_Paper"/>${annProdInfo[anninspection_index].paperSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if annProdInfo[anninspection_index].bookSize != null && annProdInfo[anninspection_index].bookSize != 0}<s:text name="i18n_Book"/>${annProdInfo[anninspection_index].bookSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if annProdInfo[anninspection_index].consultationSize != null && annProdInfo[anninspection_index].consultationSize != 0}<s:text name="i18n_Consultation"/>${annProdInfo[anninspection_index].consultationSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if annProdInfo[anninspection_index].electronicSize != null && annProdInfo[anninspection_index].electronicSize != 0}<s:text name="i18n_Electronic"/>${annProdInfo[anninspection_index].electronicSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if annProdInfo[anninspection_index].patentSize != null && annProdInfo[anninspection_index].patentSize != 0}<s:text name="i18n_Patent"/>${annProdInfo[anninspection_index].patentSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								{if annProdInfo[anninspection_index].otherProductSize != null && annProdInfo[anninspection_index].otherProductSize != 0}<s:text name="i18n_OtherProduct"/>${annProdInfo[anninspection_index].otherProductSize}<s:text name="i18n_Item"/>&nbsp;{/if}
								)
							{/if}
						</span>
						{if isDirector == 1 && anninspection.applicantSubmitStatus != 3 && anninspection_index == 0 && anninspection.isImported == 0}
							<s:hidden id="anninspectionId" value="${anninspection.id}"/>
							<input id="view_add_product" class="btn1" type="button" value="<s:text name='i18n_AddProduct'/>" name="ann_prod"/>
							<input id="view_mod_product" class="btn1" type="button" value="<s:text name='i18n_Modify'/>" name="ann_prod"/>
							<span class="text_red">&nbsp;<s:text name="i18n_AddAnnProductWarn"/></span>
						{/if}
						<s:hidden id="canAuditAnnProduct" value="${canAuditAnnProduct}"/>
					</div>
					<table id="list_table_ann_${anninspection_index}" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr4">
							
								{if isDirector == 1 && anninspection.applicantSubmitStatus != 3 && anninspection_index == 0 && anninspection.isImported == 0}
									<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllAccountsOrNot'/>"/></td>
									<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								{/if}
								
								<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
									{if anninspection_index == 0 && canAuditAnnProduct}
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
							{for item in annProdInfo[anninspection_index].productList}
								<tr class="table_txt_tr3">
									{if isDirector == 1 && anninspection.applicantSubmitStatus != 3 && anninspection_index == 0 && anninspection.isImported == 0}
										<td><input type="checkbox" name="entityIds" value="${item[0]}" class="selectProduct" alt="${item[2]}"/></td>
										<td></td>
									{/if}
								
									{if anninspection_index == 0 && canAuditAnnProduct}
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
								{if anninspection_index == 0}
									{if canAuditAnnProduct}
										<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
											<input id="view_aud_product" class="btn1" type="button" value="<s:text name='i18n_Audit'/>" name="ann_prod"/>
										</sec:authorize>
									{/if}
									{if isDirector == 1 && anninspection.applicantSubmitStatus != 3 && anninspection.isImported == 0}
										<s:hidden id="anninspectionId" value="${anninspection.id}"/>
										<input id="view_del_product" class="btn1" type="button" value="<s:text name='i18n_Delete'/>" name="ann_prod"/>
									{/if}
								{/if}
							</td>
						</tr>
					</table>
				</s:i18n>
			</div>
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
		</div>
	{forelse}<!-- 无年检信息 -->
		<!-- 研究人员 -->
		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
			{if isDirector == 1 && endPassAlready != 1 && endPending != 1 && annPending != 1 && granted != null && granted.status == 1}
				<div id="applyAnn">
					<div class="p_box_b_2"><a class="j_downloadAnnTemplate" style="cursor: pointer;"><s:text name='i18n_DownloadAnnFileTemplate'/></a></div>
					<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_APPLY_ADD'>
						<div class="p_box_b_1"><input type="button" class="btn1 j_toAddAnnApplyPop" value="<s:text name='i18n_Apply' />" /></div>
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
			<sec:authorize ifAllGranted='ROLE_PROJECT_INSTP_ANNINSPECTION_DATA_ADD'>
				{if annPending==0 && endPassAlready != 1 && granted != null && granted.status == 1}
					<div><input class="btn1 j_addAnnResultPop" type="button" value="<s:text name='i18n_AddResult'/>" /><br/></div>
				{elseif granted != null && granted.status == 3}
					<div><s:text name="i18n_CanNotAddResForStop" /></div>
				{elseif granted != null && granted.status == 4}
					<div><s:text name="i18n_CanNotAddResForWithdraw" /></div>
				{elseif endPassAlready == 1}
					<div><s:text name="i18n_CanNotAddEndResForPass" /></div>
				{elseif annPending!=0}
					<div><s:text name="i18n_CanNotAddAnnResForInDeal" /></div>
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
<div id="view_ann" style="display:none;"></div>
<s:hidden id="annProAudAlr" value='1'/>
<s:form id="annForm" theme="simple">
	<s:hidden name="projectid" id="annFormProjectid" />
	<s:hidden id="annYear" name="annYear"/>
	<s:hidden id="annAuditStatus" name="annAuditStatus"/>
	<s:hidden id="annAuditOpinion" name="annAuditOpinion"/>
	<s:hidden id="annAuditOpinionFeedback" name="annAuditOpinionFeedback"/>
	<s:hidden id="annAuditResult" name="annAuditResult"/>
</s:form>
<s:form id="uploadAnnFile_form" theme="simple">
	<s:hidden id="annId" name="annId"/>
	<s:hidden id="annFileId" name="annFileId" />
</s:form>