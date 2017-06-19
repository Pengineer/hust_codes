<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_established_template" style="display:none;">
	<div>
	{if granted == null}
		<div style="text-align:center;"><s:text name="i18n_NoRecords" /></div>
	{else}
		{if application.finalAuditResult == 2}
				{if granted.isImported==1}
					<div class="p_box_b">
						<div class="p_box_b_1">
							<span class="import_css"><s:text name='i18n_ImportData' /></span>
						</div>
					</div>
				{/if}
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="100"><s:text name="i18n_ProjectNumber" />：</td>
						<td class="value" width="120">${granted.number}</td>
						<td class="key" width="100"><s:text name="i18n_ApproveFee" />：</td>
						<td class="value" width="120">${granted.approveFee}</td>
						<td class="key" width="100"><s:text name="i18n_ApproveDate" />：</td>
						<td class="value">${granted.approveDate}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key"><s:text name="i18n_PlanEndDate" />：</td>
						<td class="value" >${granted.planEndDate}</td>
						<td class="key" ><s:text name="i18n_LastProductType" />：</td>
						<td class="value" colspan='3'>
							{if granted.productType != null && granted.productType != ""}
								${granted.productType}{if granted.productTypeOther != null && granted.productTypeOther != ""}; ${granted.productTypeOther}{/if}
							{else}
								{if granted.productTypeOther != null && granted.productTypeOther != ""}${granted.productTypeOther}
								{/if}
							{/if}
						</td>
					</tr>
					{if granted.status == 1}
					<tr class="table_tr7">
						<td class="key"><s:text name="i18n_ProjectStatus" />：</td>
						<td class="value" colspan="5">
							<s:text name="i18n_InStudy" />
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 系统管理员或部级 -->
								<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_ADD'>
									[<a href="javascript:void(0);" title="<s:text name='i18n_SetUpProjectStatus' />" class="j_setUpProjectStatusPop"><s:text name='i18n_SetUpProjectStatus' /></a>]
								</sec:authorize>
							</s:if>
						</td>
					</tr>
					{else}
					<tr class="table_tr7">
						<td class="key"><s:text name="i18n_ProjectStatus" />：</td>
						<td class="value">
							{if granted.status == 2}<s:text name="i18n_Complete" />{elseif granted.status == 3}<s:text name="i18n_Suspend" />{elseif granted.status == 4}<s:text name="i18n_Revoke" />{/if}
							<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)"><!-- 系统管理员或部级 -->
								<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_ADD'>
									[<a href="javascript:void(0);" title="<s:text name='i18n_SetUpProjectStatus' />" class="j_setUpProjectStatusPop"><s:text name='i18n_SetUpProjectStatus' /></a>]
								</sec:authorize>
							</s:if>
						</td>
						<td class="key"><s:text name="i18n_EndStopWithdrawDate" />：</td>
						<td class="value" >${granted.endStopWithdrawDate}</td>
						<td class="key"><s:text name="i18n_EndStopWithdrawPerson" />：</td>
						<td class="value" >${granted.endStopWithdrawPerson}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key"><s:text name="i18n_EndStopWithdrawOpinion" />：</td>
						<td class="value" colspan="5">${granted.endStopWithdrawOpinion}</td>
					</tr>
					<tr class="table_tr7">
						<td class="key"><s:text name="i18n_EndStopWithdrawOpinion" />：<br /></span><span><s:text name='i18n_FeedbackToDirector'/></span></td>
						<td class="value" colspan="5">${granted.endStopWithdrawOpinionFeedback}</td>
					</tr>
					{/if}
				</table>
			</div>
			
			<%--	经费明细		--%>
			{if projectFeeGranted != null }
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead>
						<tr height="36">
							<td colspan="3" height="15" align="center"><s:text name="立项经费预算明细表"/></td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td><s:text name='经费科目' /></td>
							<td width="30%"><s:text name='金额（万元）' /></td>
							<td width="300"><s:text name='开支说明'/></td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="图书资料费" /></td>
							<td class="value">${projectFeeGranted.bookFee}</td>
							<td class="value">${projectFeeGranted.bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="数据采集费" /></td>
							<td class="value">${projectFeeGranted.dataFee}</td>
							<td class="value">${projectFeeGranted.dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="调研差旅费" /></td>
							<td class="value">${projectFeeGranted.travelFee}</td>
							<td class="value">${projectFeeGranted.travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="会议费" /></td>
							<td class="value">${projectFeeGranted.conferenceFee}</td>
							<td class="value">${projectFeeGranted.conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="国际合作交流费" /></td>
							<td class="value">${projectFeeGranted.internationalFee}</td>
							<td class="value">${projectFeeGranted.internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="设备购置和使用费" /></td>
							<td class="value">${projectFeeGranted.deviceFee}</td>
							<td class="value">${projectFeeGranted.deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="专家咨询和评审费" /></td>
							<td class="value">${projectFeeGranted.consultationFee}</td>
							<td class="value">${projectFeeGranted.consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="助研津贴和劳务费" /></td>
							<td class="value">${projectFeeGranted.laborFee}</td>
							<td class="value">${projectFeeGranted.laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="印刷和出版费" /></td>
							<td class="value">${projectFeeGranted.printFee}</td>
							<td class="value">${projectFeeGranted.printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="间接费用" /></td>
							<td class="value">${projectFeeGranted.indirectFee}</td>
							<td class="value">${projectFeeGranted.indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="其他" /></td>
							<td class="value">${projectFeeGranted.otherFee}</td>
							<td class="value">${projectFeeGranted.otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="合计" /></td>
							<td class="value" colspan="2">${projectFeeGranted.totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="相关说明" /></td>
							<td class="value" colspan="2">${projectFeeGranted.feeNote}</td>
						</tr>
					</tbody>
				</table>
			</div>
			{/if}
			
		{else}
			<div style="text-align:center;"><s:text name="i18n_NoRecords" /></div>
		{/if}
	{/if}
	</div>
	<div class="p_box_t" style="padding: 0 0 10px 0;">
		<div class="p_box_t_t" ><s:text name="立项计划信息" /></div>
	</div>
	<div>
		{if granted.file != null}
			<div style="float: right; padding: 0 0 10px 0;"><img src="image/ico09.gif" />
				<a href="" id="${granted.id}" class="download_general_8"><s:text name='下载立项计划书' /></a>
				({if graFileSizeList[0] != null}
					${graFileSizeList[0]}
				{else}附件不存在
				{/if})
			</div>
		{/if}
	<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
		{if isDirector == 1 && granted != null && granted.auditstatus ==0 && granted.status == 1 && graPassAlready != 1 && graPending !=1 || (granted.finalAuditStatus == 3 && granted.finalAuditResult == 1)}
			<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_APPLY_ADD">
				<div class="p_box_b_1" id="applyMid">
					<input type="button" class="btn1 j_toAddGraApplyPop" value="<s:text name='i18n_Apply'/>" />
				</div>
			</sec:authorize>
		{elseif granted.auditstatus == 1 &&　granted.applicantSubmitStatus == 2}
			<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_APPLY_MODIFY">
				<input type="button" value="<s:text name='i18n_Modify' />" class="btn1 j_toModifyGraApplyPop"/>&nbsp;
			</sec:authorize>
			<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_APPLY_ADD">
				<input type="button" value="<s:text name='i18n_Submit' />" class="btn1 j_submitGraApply"/>&nbsp;
			</sec:authorize>
			<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_GRANTED_APPLY_DELETE">
				<input type="button" value="<s:text name='i18n_Delete' />" class="btn1 j_deleteGraApply"/>
			</sec:authorize>
		{else}
		{/if}
	</s:if>
	<!-- 管理人员录入 -->
	<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
		<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_GENERAL_PLAN_DATA_ADD'><!-- 录入立项计划 -->
			{if granted.auditstatus == 0 && granted != null && granted.status == 1 && graPassAlready==0 && graPending==0 && granted.auditType == 0 }
				<input class="btn1 j_addGraResultPop" type="button" value="<s:text name='i18n_AddResult'/>"/><br/>
			{elseif granted != null && granted.status == 3}
				 <s:text name="项目已终止，不能再录入数据" />
			{elseif granted != null && granted.status == 4}
				 <s:text name="项目已撤项，不能再录入数据" /> 
			{elseif graPassAlready == 1}
				 <s:text name="立项计划已通过，不能再录入数据" /> 
			{elseif graPending ==1 }
				<s:text name="立项计划正在审核，不能录入数据" />
			{/if}
			{if granted.auditType == 1 && granted.finalAuditStatus != 3}
				{if granted.finalAuditStatus < 3}
					<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_GENERAL_PLAN_DATA_ADD'>
						<input class="btn1 j_modifyGraResultPop" type="button" value="<s:text name='i18n_Modify' />"/>
					</sec:authorize>
					<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_GENERAL_PLAN_DATA_ADD'>
						<input class="btn1 j_submitGraResult" type="button" value="<s:text name='i18n_Submit' />"/>
					</sec:authorize>
				{/if}
			{/if}	
		</sec:authorize>
	</s:elseif>
	</div>
	{if granted != null && granted.status == 1 && (granted.auditstatus != 0 || granted.auditType ==1) }
	<div id = "graAudit" >
		<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 "><!-- 部门及以上管理人员 -->
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
							<td width="30%"><s:text name='是否同意立项计划书申请' /></td>
							<td width="300"><s:text name='i18n_ViewMoreDetail'/></td>
						</tr>
						<!-- 部门审核信息 -->
						<tr class="table_tr4">
							<td><s:text name='i18n_DeptInstOpinion' /></td>
							<td>
								{if granted.deptInstAuditResult == 1}
									{if granted.deptInstAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
									{elseif granted.deptInstAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
									{elseif granted.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
									{/if}
								{elseif granted.deptInstAuditResult == 2}
									{if granted.deptInstAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
									{elseif granted.deptInstAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
									{elseif granted.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
									{/if}
								{elseif granted.deptInstAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
								{/if}
							</td>
							<td>
								<!-- 非导入数据、部门账号、最近一次立项计划处在部门审核级别 -->
								{if granted.isImported == 0 && midinspection_index == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && granted.status == 2 && granted.finalAuditStatus != 3}
									{if endPassAlready != 1 && granted != null && granted.status == 1}
										{if granted.deptInstAuditStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_ADD'>
												<input class="btn1 j_addGraAuditPop" type="button" value="<s:text name='i18n_Audit'/>"/>
												<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>"/>
											</sec:authorize>
										{elseif granted.deptInstAuditStatus < 3}
											<span class="ico_txt2"><a grantedId="${granted.id}" alt="1" class="j_viewGraAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											{if (isPrincipal == 1 && (graAuditorInfo[1] == belongId || graAuditorInfo[2] == belongId)) || (isPrincipal == 0 && graAuditorInfo[0] == belongId)}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_MODIFY'>
												<input class="btn1 j_modifyGraAuditPop" type="button" value="<s:text name='i18n_Modify' />"/>
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_ADD'>
												<input class="btn1 j_submitGraAudit" type="button" value="<s:text name='i18n_Submit' />"/>
												<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>"/>
											</sec:authorize>
											{/if}
										{/if}
									{/if}	
								{else}
									<span class="ico_txt2"><a grantedId="${granted.id}" alt="1" class="j_viewGraAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
								{/if}
							</td>
						</tr>
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0"><!-- 校级及以上管理人员 -->
							<tr class="table_tr4">
								<td><s:text name='i18n_UniversityOpinion'/></td>
								<td>
									{if granted.universityAuditResult == 1}
										{if granted.universityAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
										{elseif granted.universityAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
										{elseif granted.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif granted.universityAuditResult == 2}
										{if granted.universityAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
										{elseif granted.universityAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
										{elseif granted.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif granted.universityAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、高校账号、最近一次立项计划处在高校审核级别 -->
									{if granted.isImported != 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && granted.auditstatus == 3 && granted.finalAuditStatus != 3}
										{if endPassAlready != 1 && granted != null && granted.status == 1}
											{if granted.universityAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_ADD'>
													<input class="btn1 j_addGraAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
													<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>"/>
												</sec:authorize>
											{elseif granted.universityAuditStatus < 3}
												<span class="ico_txt2"><a grantedId="${granted.id}" alt="2" class="j_viewGraAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
												{if (isPrincipal == 1 && graAuditorInfo[4] == belongId) || (isPrincipal == 0 && graAuditorInfo[3] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_MODIFY'>
													<input class="btn1 j_modifyGraAuditPop" type="button" value="<s:text name='i18n_Modify' />"/>
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_ADD'>
													<input class="btn1 j_submitGraAudit" type="button" value="<s:text name='i18n_Submit' />"/>
													<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>"/>
												</sec:authorize>
												{/if}
											{/if}
										{/if}	
									{else}
										<span class="ico_txt2"><a grantedId="${granted.id}" alt="2" class="j_viewGraAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
									{/if}
								</td>
							</tr>
						</s:if>
						{if utypeNew == 0}<!-- 地方高校 -->
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
								<tr class="table_tr4">
									<td><s:text name='i18n_ProvinceOpinion'/></td>
									<td>
										{if granted.provinceAuditResult == 1}
											{if granted.provinceAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
											{elseif granted.provinceAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
											{elseif granted.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif granted.provinceAuditResult == 2}
											{if granted.provinceAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
											{elseif granted.provinceAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
											{elseif granted.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif granted.provinceAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、省厅账号、最近一次立项计划处在省厅审核级别 -->
										{if granted.isImported == 0 && midinspection_index == 0 && accountType == "PROVINCE" && granted.status == 4 && granted.finalAuditStatus != 3}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if granted.provinceAuditStatus == 0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_ADD'>
														<input class="btn1 j_addGraAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
														<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>" />
													</sec:authorize>
												{elseif granted.provinceAuditStatus < 3}
													<span class="ico_txt2"><a grantedId="${granted.id}" alt="3" class="j_viewGraAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
													{if (isPrincipal == 1 && graAuditorInfo[6] == belongId) || (isPrincipal == 0 && graAuditorInfo[5] == belongId)}
													<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_MODIFY'>
														<input class="btn1 j_modifyGraAuditPop" type="button" value="<s:text name='i18n_Modify' />"/>
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_PLAN_APPLYAUDIT_ADD'>
														<input class="btn1 j_submitGraAudit" type="button" value="<s:text name='i18n_Submit' />"/>
														<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>"/>
													</sec:authorize>
													{/if}
												{/if}
											{/if}	
										{else}
											<span class="ico_txt2"><a grantedId="${granted.id}" alt="3" class="j_viewGraAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										{/if}
									</td>
								</tr>
							</s:if>
						{/if}
					</tbody>
				</table>
			</div>
		</s:if>
		<div class="p_box_body">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0"><!-- 部级及以上管理人员 -->
					<thead>
						<tr class="head_title">
							<td colspan="3"><s:text name="最终审核结果"/></td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td><s:text name='i18n_AuditDate' /></td>
							<td width="30%"><s:text name='是否同意立项计划书申请' /></td>
							<td width="300"><s:text name='i18n_ViewMoreDetail'/></td>
						</tr>
						<tr class="table_tr4">
							<td>
								${granted.finalAuditDate}
							</td>
							<td>
								{if granted.finalAuditResult == 1}
									{if granted.finalAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
									{elseif granted.finalAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
									{elseif granted.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
									{/if}
								{elseif granted.finalAuditResult == 2}
									{if granted.finalAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
									{elseif granted.finalAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
									{elseif granted.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
									{/if}
								{elseif granted.finalAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
								{/if}
							</td>
							<td>
								<!-- 非导入数据、教育部账号、最近一次立项计划处在教育部审核级别 -->
								{if accountType == "MINISTRY" && granted.auditstatus == 5 && granted.finalAuditStatus != 3 && granted.auditType == 0}
										{if granted.finalAuditStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLYAUDIT_ADD'>
												<input class="btn1 j_addGraAuditPop" type="button" value="<s:text name='i18n_Audit'/>"/>
												<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>"/>
											</sec:authorize>
										{elseif granted.finalAuditStatus < 3}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLYAUDIT_MODIFY'>
												<input class="btn1 j_modifyGraAuditPop" type="button" value="<s:text name='i18n_Modify' />"/>
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLYAUDIT_ADD'>
												<input class="btn1 j_submitGraAudit" type="button" value="<s:text name='i18n_Submit' />"/>
												<input class="btn1 j_backGraApply" type="button" value="<s:text name='i18n_SendBack'/>"/>
											</sec:authorize>
										{/if}
								{else}
									<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_MIDINSPECTION_APPLYAUDIT_VIEW'>
										<span class="ico_txt2"><a grantedId="${granted.id}" alt="4" class="j_viewGraAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
									</sec:authorize>
								{/if}
							</td>
						</tr>
					</tbody>
				</s:if>
				<s:else><!-- 部级以下所有人员 -->
					{if granted.applicantSubmitStatus == 3}
						<thead>
							<tr class="head_title">
								<td colspan="2"><s:text name="最终审核结果"/></td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td width="50%"><s:text name='i18n_AuditDate'/></td>
								<td width="50%"><s:text name="最终审核结果"/></td>
							</tr>
							<tr class="table_tr4">
								<td>{if granted.finalAuditStatus == 3}${granted.finalAuditDate}{/if}</td>
								<td>
									{if granted.finalAuditStatus == 3 && granted.finalAuditResult == 2}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
									{elseif granted.finalAuditStatus == 3 && granted.finalAuditResult == 1}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
									{else}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
									{/if}
								</td>
							</tr>
						</tbody>
					{/if}
				</s:else>
			</table>
		</div>
	</div>
	{else}
		<div style="text-align:center;"><s:text name="i18n_NoRecords" /></div>
	{/if}
</textarea>
<div id="view_established" style="display:none;"></div>
<s:form id="GraForm" theme="simple">
	<s:hidden name="projectid" id="midFormProjectid" />
	<s:hidden id="graAuditStatus" name="graAuditStatus"/>
	<s:hidden id="graAuditOpinion" name="graAuditOpinion"/>
	<s:hidden id="graAuditOpinionFeedback" name="graAuditOpinionFeedback"/>
	<s:hidden id="graAuditResult" name="graAuditResult"/>
</s:form>