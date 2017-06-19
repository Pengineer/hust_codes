<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_apply_template" style="display:none;">
	<s:hidden value="${appInfo[1]}" id="deadlineApp" />
	<s:hidden value="${appRevInfo[1]}" id="deadlineAppRev" />
	<s:hidden name="appStatus" id="appStatus" value="%{projectService.getBusinessStatus('051')}"/>
	<div class="p_box_b">
		<div class="p_box_b_1">
			{if application.isImported==0}
				{if application.applicantSubmitStatus!=3}<s:text name='i18n_SaveDate' />{else}<s:text name='i18n_SubmitDate' />{/if}：${application.applicantSubmitDate}
				<!-- 校级及以上管理人员最近一次结项走流程录入评审结果 -->
				{if application.finalAuditStatus != 3}
					{if reviewflagFromApp != 22 && reviewflagFromApp != 23 && reviewflagFromApp != 32 && reviewflagFromApp != 33 && reviewflagFromApp != 42 && reviewflagFromApp != 43 && accountType == "MINISTRY" && application.status == 6}
						<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_ADD'>
							{if reviewflagFromApp == -1  && accountType == "MINISTRY" && application.status == 6}
							<span><input class="btn2 j_addAppReviewResultPop" appId="${application.id}" type="button" value="<s:text name='i18n_AddReviewResult' />" /></span>
							{/if}
							<span><input class="btn2 j_addAppReviewExpert" type="button" appId="${application.id}"  value="<s:text name='i18n_AddExpert' />" /></span>
						</sec:authorize>
					{/if}
				{/if}
			{elseif application.isImported==1}
				<span class="import_css"><s:text name='i18n_ImportData' /></span>
			{else}
			{/if}
		</div>
		<div class="p_box_b_2">
		<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_VIEW'>
			<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DOWNLOAD'>
			{if application.file != null}
				<div style="float: right;"><img src="image/ico09.gif" />
					<a href="" id="${application.id}" class="download_entrust_1"><s:text name='i18n_DownloadApplication' /></a>
					({if appFileSizeList[0] != null}
						${appFileSizeList[0]}
					{else}附件不存在
					{/if})
				</div>
			{/if}
			</sec:authorize>
			{if application.finalAuditStatus==3 && granted != null }
			<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
			<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_UPLOAD'>
				<div style="float: right;"><img src="image/ico10.gif" /><a href="javascript:void(0);" onclick="uploadAppPop();"><s:text name='i18n_UploadAppApply'/></a>&nbsp;&nbsp;</div>
			</sec:authorize>
			</s:if>
			{/if}
		</sec:authorize>
		</div>
	</div>
	<div class="p_box_body">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			<tr class="table_tr7">
				<td class="key" width="100"><s:text name="i18n_ProjectName" />：</td>
				<td class="value" colspan='3'>${application.name}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key" width="100"><s:text name="i18n_EnglishName" />：</td>
				<td class="value" colspan='3'>${application.englishName}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key" width="100"><s:text name="i18n_Applicant" />：</td>
				<td class="value" width="200"><s:hidden id="directors" name="${application.applicantName}" value="${application.applicantId}" cssClass="directors"/></td>
				<td class="key" width="120"><s:text name="i18n_IssueType" />：</td>
				<td class="value">${subTypeNameOld}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key"><s:text name="i18n_University" />：</td>
				<td class="value"><a id="${universityIdOld}" class="linkUni" href="">${application.agencyName}</a></td>
				<td class="key"><s:text name="i18n_DeptInst" />：</td>
				<td class="value">
					{if departmentIdOld !="" && departmentIdOld != "null"}
						<a id="${departmentIdOld }" class="linkDep" href="">${application.divisionName}</a>
					{elseif instituteIdOld != "" && instituteIdOld != "null"}
						<a id="${instituteIdOld }" class="linkIns" href="">${application.divisionName}</a>
					{else}
						${application.divisionName}
					{/if}
				</td>
			</tr>
			<tr class="table_tr7">
				<td class="key" width="100"><s:text name="i18n_ResearchType" />：</td>
				<td class="value" width="200">${researchTypeName}</td>
				<td class="key" width="120"><s:text name="i18n_DisciplineType" />：</td>
				<td class="value">${application.disciplineType}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key"><s:text name="i18n_Discipline" />：</td>
				<td class="value">${application.discipline}</td>
				<td class="key"><s:text name="i18n_RelativeDiscipline" />：</td>
				<td class="value">${application.relativeDiscipline }</td>
			</tr>
			<tr class="table_tr7">
				<td class="key"><s:text name="i18n_ApplyDate" />：</td>
				<td class="value">${application.applicantSubmitDate}</td>
				<td class="key"><s:text name="i18n_PlanEndDate" />：</td>
				<td class="value">${application.planEndDate}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key"><s:text name="i18n_ApplyFee" />：</td>
				<td class="value">${application.applyFee}</td>
				<td class="key"><s:text name="i18n_OtherFee" />：</td>
				<td class="value">${application.otherFee}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key"><s:text name="i18n_LastProductType" />：</td>
				<td class="value">
					{if application.productType != null && application.productType != ""}
						${application.productType}{if application.productTypeOther != null && application.productTypeOther != ""}; ${application.productTypeOther}{/if}
					{else}
						{if application.productTypeOther != null && application.productTypeOther != ""}${application.productTypeOther}
						{/if}
					{/if}
				</td>
				<td class="key"><s:text name="i18n_Keywords" />：</td>
				<td class="value">${application.keywords}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key"><s:text name="i18n_Summary" />：</td>
				<td class="value" colspan="3">${application.summary}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key"><s:text name="i18n_Note" />：</td>
				<td class="value" colspan="3">${application.note}</td>
			</tr>
		</table>
	</div>
	
	<%--	经费明细		--%>
	{if projectFeeApply != null }
	<div class="p_box_body">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			<thead>
				<tr height="36">
					<td colspan="3" height="15" align="center"><s:text name="申请经费概算明细表"/></td>
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
					<td class="value">${projectFeeApply.bookFee}</td>
					<td class="value">${projectFeeApply.bookNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="数据采集费" /></td>
					<td class="value">${projectFeeApply.dataFee}</td>
					<td class="value">${projectFeeApply.dataNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="调研差旅费" /></td>
					<td class="value">${projectFeeApply.travelFee}</td>
					<td class="value">${projectFeeApply.travelNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="会议费" /></td>
					<td class="value">${projectFeeApply.conferenceFee}</td>
					<td class="value">${projectFeeApply.conferenceNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="国际合作交流费" /></td>
					<td class="value">${projectFeeApply.internationalFee}</td>
					<td class="value">${projectFeeApply.internationalNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="设备购置和使用费" /></td>
					<td class="value">${projectFeeApply.deviceFee}</td>
					<td class="value">${projectFeeApply.deviceNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="专家咨询和评审费" /></td>
					<td class="value">${projectFeeApply.consultationFee}</td>
					<td class="value">${projectFeeApply.consultationNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="助研津贴和劳务费" /></td>
					<td class="value">${projectFeeApply.laborFee}</td>
					<td class="value">${projectFeeApply.laborNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="印刷和出版费" /></td>
					<td class="value">${projectFeeApply.printFee}</td>
					<td class="value">${projectFeeApply.printNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="间接费用" /></td>
					<td class="value">${projectFeeApply.indirectFee}</td>
					<td class="value">${projectFeeApply.indirectNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="其他" /></td>
					<td class="value">${projectFeeApply.otherFee}</td>
					<td class="value">${projectFeeApply.otherNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="合计" /></td>
					<td class="value" colspan="2">${projectFeeApply.totalFee}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key"><s:text name="相关说明" /></td>
					<td class="value" colspan="2">${projectFeeApply.feeNote}</td>
				</tr>
			</tbody>
		</table>
	</div>
	{/if}
	
	<!-- 研究人员申请相关操作-->
	<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
		{if application.applicantSubmitStatus == 1 || application.applicantSubmitStatus == 2}<!-- 项目申报申请暂存或退回 -->
			<div class="p_box_body">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr height="36">
						<td width="150">
							<s:text name='i18n_SubmitStatus' />：{if application.applicantSubmitStatus == 1}<s:text name='i18n_SendBack'/>{else}<s:text name="i18n_Save"/>{/if}
						</td>
						<td align="right">
							{if isDirector == 1}
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_MODIFY">
									<input type="button" value="<s:text name='i18n_Modify' />" class="btn1 j_modifyAppApply"/>&nbsp;
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_ADD">
									<input type="button" value="<s:text name='i18n_Submit' />" class="btn1 j_submitAppApply"/>&nbsp;
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<input type="button" value="<s:text name='i18n_Delete' />" class="btn1 j_deleteAppApply"/>
								</sec:authorize>
							{/if}
						</td>
					</tr>
				</table>
			</div>
		{/if}
	</s:if>
	<!-- 部门及以上管理人员审核信息-->
	<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
		<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_VIEW'>
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
							<td width="30%"><s:text name='i18n_GrantedResult' /></td>
							<td width="300"><s:text name='i18n_ViewMoreDetail'/></td>
						</tr>
						<tr class="table_tr4">
							<td class="key"><s:text name="i18n_DeptInstOpinion" /></td>
							<td class="value" width="200">
								{if application.deptInstAuditResult == 1}
									{if application.deptInstAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
									{elseif application.deptInstAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
									{elseif application.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
									{/if}
								{elseif application.deptInstAuditResult == 2}
									{if application.deptInstAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
									{elseif application.deptInstAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
									{elseif application.deptInstAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
									{/if}
								{elseif application.deptInstAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
								{/if}
							</td>
							<td>
								<!-- 非导入数据、部门账号、在部门审核级别 -->
								{if application.isImported == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && application.status == 2 && application.finalAuditStatus != 3}
									{if application.deptInstAuditStatus == 0}
										<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
											<input class="btn1 j_addAppAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
											<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
										</sec:authorize>
									{elseif application.deptInstAuditStatus < 3}
										<span class="ico_txt2"><a appId="${application.id}" alt="1" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										{if (isPrincipal == 1 && (appAuditorInfo[1] == belongId || appAuditorInfo[2] == belongId)) || (isPrincipal == 0 && appAuditorInfo[0] == belongId)}
										<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_MODIFY'>
											<input class="btn1 j_modifyAppAuditPop" type="button" value="<s:text name='i18n_Modify'/>" />
										</sec:authorize>
										<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
											<input class="btn1 j_submitAppAudit" type="button" value="<s:text name='i18n_Submit'/>" />
											<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
										</sec:authorize>
										{/if}
									{/if}
								{else}
									<span class="ico_txt2"><a appId="${application.id}" alt="1" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
								{/if}
							</td>
						</tr>
						<!-- 校级及以上管理人员 -->
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0">
							<tr class="table_tr4">
								<td class="key"><s:text name="i18n_UniversityOpinion" /></td>
								<td class="value">
									{if application.universityAuditResult == 1}
										{if application.universityAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
										{elseif application.universityAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
										{elseif application.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif application.universityAuditResult == 2}
										{if application.universityAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
										{elseif application.universityAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
										{elseif application.universityAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif application.universityAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、高校账号、在高校审核级别 -->
									{if application.isImported == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && application.status == 3 && application.finalAuditStatus != 3}
										{if application.universityAuditStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_addAppAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
												<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
											</sec:authorize>
										{elseif application.universityAuditStatus < 3}
											<span class="ico_txt2"><a appId="${application.id}" alt="2" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											{if (isPrincipal == 1 && appAuditorInfo[4] == belongId) || (isPrincipal == 0 && appAuditorInfo[3] == belongId)}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_MODIFY'>
												<input class="btn1 j_modifyAppAuditPop" type="button" value="<s:text name='i18n_Modify'/>" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_submitAppAudit" type="button" value="<s:text name='i18n_Submit'/>" />
												<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
											</sec:authorize>
											{/if}
										{/if}
									{else}
										<span class="ico_txt2"><a appId="${application.id}" alt="2" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
									{/if}
								</td>
							</tr>
						</s:if>
						{if utypeOld == 0}<!-- 地方高校 -->
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
								<tr class="table_tr4">
									<td class="key"><s:text name="i18n_ProvinceOpinion" /></td>
									<td class="value">
										{if application.provinceAuditResult == 1}
											{if application.provinceAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
											{elseif application.provinceAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
											{elseif application.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif application.provinceAuditResult == 2}
											{if application.provinceAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
											{elseif application.provinceAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
											{elseif application.provinceAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
										{elseif application.provinceAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、省厅账号、在省厅审核级别 -->
										{if application.isImported == 0 && accountType == "PROVINCE" && application.status == 4 && application.finalAuditStatus != 3}
											{if application.provinceAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
													<input class="btn1 j_addAppAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
													<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
												</sec:authorize>
											{elseif application.provinceAuditStatus < 3}
												<span class="ico_txt2"><a appId="${application.id}" alt="3" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
												{if (isPrincipal == 1 && appAuditorInfo[6] == belongId) || (isPrincipal == 0 && appAuditorInfo[5] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_MODIFY'>
													<input class="btn1 j_modifyAppAuditPop" type="button" value="<s:text name='i18n_Modify' />" />
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
													<input class="btn1 j_submitAppAudit" type="button" value="<s:text name='i18n_Submit' />" />
													<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
												</sec:authorize>
												{/if}
											{/if}
										{else}
											<span class="ico_txt2"><a appId="${application.id}" alt="3" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										{/if}
									</td>
								</tr>
							</s:if>
						{/if}
						<!-- 教育部及系统管理员 -->
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0">
							<tr class="table_tr4">
								<td class="key"><s:text name="i18n_MinistryOpinion" /></td>
								<td class="value">
									{if application.ministryAuditResult == 1}
											{if application.ministryAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
											{elseif application.ministryAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
											{elseif application.ministryAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
											{/if}
									{elseif application.ministryAuditResult == 2}
										{if application.ministryAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
										{elseif application.ministryAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
										{elseif application.ministryAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
										{/if}
									{elseif application.ministryAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、教育部账号、在部级审核级别 -->
									{if application.isImported == 0 && accountType == "MINISTRY" && application.status == 5 && application.finalAuditStatus != 3}
										{if application.ministryAuditStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_addAppAuditPop" type="button" value="<s:text name='i18n_Audit'/>" />
												<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
											</sec:authorize>
										{elseif application.ministryAuditStatus < 3}
											<span class="ico_txt2"><a appId="${application.id}" alt="4" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											{if (isPrincipal == 1 && appAuditorInfo[8] == belongId) || (isPrincipal == 0 && appAuditorInfo[7] == belongId)}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_MODIFY'>
												<input class="btn1 j_modifyAppAuditPop" type="button" value="<s:text name='i18n_Modify'/>" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_submitAppAudit" type="button" value="<s:text name='i18n_Submit'/>" />
												<input class="btn1 j_backAppApply" type="button" value="<s:text name='i18n_SendBack'/>" />
											</sec:authorize>
											{/if}
										{/if}
									{else}
										<span class="ico_txt2"><a appId="${application.id}" alt="4" class="j_viewAppAuditPop" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
									{/if}
								</td>
							</tr>
						</s:if>
					</tbody>
				</table>
			</div>
		</sec:authorize>
	</s:elseif>
	<!-- 专家评审信息-->
	<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW'>
		{if (accountType =="EXPERT" || accountType == "TEACHER") && isAppReviewer > 0 && applicationReview != null && applicationReview.submitStatus < 3}<!-- 是评审专家 -->
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead>
						<tr class="head_title">
							<td colspan="6"><s:text name="i18n_ReviewTable"/></td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td width="90"><s:text name='i18n_ExpertNo' /></td>
							<td><s:text name='i18n_ExpertName' /></td>
							<td width="70"><s:text name='i18n_Score' /></td>
							<td width="70"><s:text name='i18n_Grade' /></td>
							<td width="90"><s:text name='i18n_SubmitStatus' /></td>
							<td width="250"><s:text name='i18n_ViewMoreDetail'/></td>
						</tr>
						<tr class="table_tr4">
							<td>${applicationReview.reviewerSn}</td>
							<td>${applicationReview.reviewerName}</td>
							<td>${applicationReview.score}</td>
							<td>${applicationPersonalGrade}</td>
							<td>
								{if applicationReview.submitStatus==2}<s:text name='i18n_Saved' />
								{elseif applicationReview.submitStatus==3}<s:text name='i18n_Submit' />
								{else}
								{/if}
							</td>
							<td>
								<!-- 非导入数据、申报在评审级别 -->
								{if application.isImported == 0 && application.status == 6 && application.finalAuditStatus != 3 && applicationReview.submitStatus != 3}
									{if appPassAlready != 1}
										{if applicationReview.submitStatus==0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_addAppReviewPop" appId="${application.id}" type="button" value="<s:text name='i18n_Review' />" />
											</sec:authorize>
										{elseif applicationReview.submitStatus < 3}
											<span class="ico_txt2"><a class="j_viewAppReviewPop" appRevId="${applicationReview.id}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_MODIFY'>
												<input class="btn1 j_modifyAppReviewPop" appId="${application.id}" appRevId="${applicationReview.id}" type="button" value="<s:text name='i18n_Modify' />" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_submitAppReview" appId="${application.id}" type="button" value="<s:text name='i18n_Submit'/>" />
											</sec:authorize>
										{/if}
									{/if}
								{else}
									<span class="ico_txt2"><a class="j_viewAppReviewPop" appRevId="${applicationReview.id}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
								{/if}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		{/if}
		<!-- 所有专家评审信息及小组评审信息 -->
		{if (isAppReviewer == 2 && applicationReviewStatus == 3) || (accountType != "EXPERT" && accountType != "TEACHER" && accountType != "STUDENT")}
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead>
						<tr class="head_title">
							<td colspan="6"><s:text name="i18n_ReviewTable"/></td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td width="90"><s:text name='i18n_ExpertNo' /></td>
							<td><s:text name='i18n_ExpertName' /></td>
							<td width="70"><s:text name='i18n_Score' /></td>
							<td width="70"><s:text name='i18n_Grade' /></td>
							<td width="90"><s:text name='i18n_SubmitStatus' /></td>
							<td width="250"><s:text name='i18n_ViewMoreDetail'/></td>
						</tr>
						
						{if applicationReviews != null}
							{for item in applicationReviews}
								{if item != null}
									<tr class="table_tr4">
										<td>${item[1]}</td>
										<td>${item[2]}</td>
										<td>${item[3]}</td>
										<td>${item[4]}</td>
										<td>
											{if item[5]==0}<s:text name='i18n_Pending' />
											{elseif item[5]==2}<s:text name='i18n_Saved' />
											{elseif item[5]==3}<s:text name='i18n_Submit' />
											{/if}
										</td>
										<td>
											{if item[5]!=0}
												<span class="ico_txt2"><a class="j_viewAppReviewPop" appRevId="${item[0]}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											{/if}
										</td>
									</tr>
								{/if}
							{/for}
						{/if}
						<!-- 评审汇总信息 -->
						<tr class="table_tr4">
							<td><s:text name='i18n_GroupReview' /></td>
							<td>${application.reviewerName}</td>
							<td>${application.reviewAverageScore}</td>
							<td>${applicationReviewGrade}</td>
							<td>
								{if application.reviewStatus == 3 && application.reviewResult == 1}<s:text name='i18n_Submit' />(<s:text name='i18n_NotApprove' />)
								{elseif application.reviewStatus == 3 && application.reviewResult == 2}<s:text name='i18n_Submit' />(<s:text name='i18n_Approve' />)
								{elseif application.reviewStatus == 2 && application.reviewResult == 1}<s:text name='i18n_Saved' />(<s:text name='i18n_NotApprove' />)
								{elseif application.reviewStatus == 2 && application.reviewResult == 2}<s:text name='i18n_Saved' />(<s:text name='i18n_Approve' />)
								{else}
								{/if}
							</td>
							<td>
								 <!-- 非导入数据、教师或专家账号、申报处在小组评审级别 -->
								{if application.isImported == 0 && (accountType =="EXPERT" || accountType == "TEACHER") && application.status == 6 && application.finalAuditStatus != 3 && allAppReviewSubmit != -1 && reviewNum == 1 }
									{if appPassAlready != 1}
										{if application.reviewStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_addAppGroupReviewPop" appId="${application.id}" type="button" value="<s:text name='i18n_Review' />" />
											</sec:authorize>
										{elseif application.reviewStatus == 2}
											<span class="ico_txt2"><a class="j_viewAppGroupReviewPop" appId="${application.id}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_MODIFY'>
												<input class="btn1 j_modifyAppGroupReviewPop" appId="${application.id}" type="button" value="<s:text name='i18n_Modify' />" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_submitAppGroupReview" appId="${application.id}" type="button" value="<s:text name='i18n_Submit'/>" />
											</sec:authorize>
										{elseif application.reviewStatus == 3}	
											<span class="ico_txt2"><a class="j_viewAppGroupReviewPop" appId="${application.id}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
										{/if}
									{/if}
								{elseif reviewflagFromApp == 22 && accountType == "MINISTRY" && application.status > 5}
									<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_MODIFY'>
										<span><input class="btn2 j_modifyAppReviewResultPop" appId="${application.id}" type="button" value="<s:text name='i18n_Modify' />" /></span>
									</sec:authorize>
									<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_ADD'>
										<span><input class="btn2 j_submitAppReviewResult" appId="${application.id}" type="button" value="<s:text name='i18n_Submit' />" /></span>
									</sec:authorize>
									<span class="ico_txt2"><a class="j_viewAppGroupReviewPop" appId="${application.id}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
								{/if}
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		{/if}
	</sec:authorize>
	<div class="p_box_body"><!-- 最终审核结果 -->
	 	<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0"><!-- 部级及以上管理人员 -->
				<thead>
					<tr class="head_title">
						<td colspan="4"><s:text name="i18n_GrantedResultTable"/></td>
					</tr>
				</thead>
				<tbody>
					<tr class="table_tr3">
						<td width="120"><s:text name='i18n_AuditDate' /></td>
						<td><s:text name='i18n_GrantedResult' /></td>
						<td><s:text name='i18n_SubmitStatus' /></td>
						<td width="250"><s:text name='i18n_ViewMoreDetail'/></td>
					</tr>
					<tr class="table_tr4">
						<td>
							${application.finalAuditDate}
						</td>
						<td>
							{if application.finalAuditResult == 1}
								{if application.finalAuditStatus == 3}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
								{elseif application.finalAuditStatus == 2}<span class="ico_txt8"><s:text name='i18n_SaveNotApprove' /></span>
								{elseif application.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
								{/if}
							{elseif application.finalAuditResult == 2}
								{if application.finalAuditStatus == 3}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
								{elseif application.finalAuditStatus == 2}<span class="ico_txt4"><s:text name='i18n_SaveApprove' /></span>
								{elseif application.finalAuditStatus == 1}<span class="ico_txt7"><s:text name='i18n_SendBack'/></span>
								{/if}
							{elseif application.finalAuditResult == 0}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
							{/if}
						</td>
						<td>
							{if application.finalAuditStatus == 3}<span><s:text name='i18n_Submit'/></span>
							{elseif application.finalAuditStatus == 2}<span><s:text name='i18n_Saved' /></span>
							{elseif application.finalAuditStatus == 1}<span><s:text name='i18n_SendBack' /></span>
							{elseif application.finalAuditStatus == 0}<span></span>
							{/if}
						</td>
						<td>
							<!-- 非导入数据、部级账号、申报处在评审审核审核级别 -->
							{if application.isImported == 0 && accountType == "MINISTRY" && application.status == 7 && application.finalAuditStatus != 3}
								<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEWAUDIT_VIEW'>
									{if appPassAlready != 1}
										{if application.finalAuditStatus==0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEWAUDIT_ADD'>
												<input class="btn1 j_addAppReviewAuditPop" appId="${application.id}" type="button" value="<s:text name='i18n_Audit'/>" />
											</sec:authorize>
										{elseif application.finalAuditStatus < 3}
											<span class="ico_txt2"><a class="j_viewAppReviewAuditPop" appId="${application.id}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEWAUDIT_MODIFY'>
												<input class="btn1 j_modifyAppReviewAuditPop" appId="${application.id}" type="button" value="<s:text name='i18n_Modify' />" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEWAUDIT_ADD'>
												<input class="btn1 j_submitAppReviewAuditResult" appId="${application.id}" type="button" value="<s:text name='i18n_Submit'/>" />
											</sec:authorize>
										{/if}
									{/if}
								</sec:authorize>
							{elseif application.finalAuditStatus > 0}
								<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_APPLICATION_REVIEWAUDIT_VIEW'>
									<span class="ico_txt2"><a class="j_viewAppReviewAuditPop" appId="${application.id}" style="cursor: pointer;"><s:text name='i18n_ViewMoreDetail'/></a></span>
								</sec:authorize>
							{/if}
						</td>
					</tr>
				</tbody>
			</s:if>
			<s:else><!-- 部级以下所有人员 -->
				{if application.isImported == 1 || application.applicantSubmitStatus == 3}
					<thead>
						<tr class="head_title">
							<td colspan="2"><s:text name="i18n_GrantedResultTable"/></td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td width="50%"><s:text name='i18n_AuditDate'/></td>
							<td width="50%"><s:text name="i18n_GrantedResult"/></td>
						</tr>
						<tr class="table_tr4">
							<td>{if application.finalAuditStatus == 3}${application.finalAuditDate}{/if}</td>
							<td>
								{if application.finalAuditStatus == 3 && application.finalAuditResult == 2}<span class="ico_txt1"><s:text name='i18n_Approve' /></span>
								{elseif application.finalAuditStatus == 3 && application.finalAuditResult == 1}<span class="ico_txt5"><s:text name='i18n_NotApprove' /></span>
								{else}<span class="ico_txt3"><s:text name='i18n_Pending' /></span>
								{/if}
							</td>
						</tr>
					</tbody>
				{/if}
			</s:else>
		</table>
	</div>
	{if application.finalAuditStatus == 3}
		<div class="p_box_body">
			<div class="p_box_body_t">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td class="key" width="117"><span><s:text name="i18n_AuditOpinionFeedback" />：<br/></span><span><s:text name='i18n_FeedbackToDirector'/></span></td>
						<td class="value" >{if application.finalAuditOpinionFeedback != null && application.finalAuditOpinionFeedback != ""}<pre>${application.finalAuditOpinionFeedback}</pre>{/if}</td>
					</tr>
				</table>
			</div>
		</div>
	{/if}
</textarea>
<div id="view_apply" style="display:none;"></div>
<s:form id="appForm" theme="simple">
	<s:hidden id="appAuditStatus" name="appAuditStatus"/>
	<s:hidden id="appAuditOpinion" name="appAuditOpinion"/>
	<s:hidden id="appAuditOpinionFeedback" name="appAuditOpinionFeedback"/>
	<s:hidden id="appAuditResult" name="appAuditResult"/>
</s:form>
<s:form id="review_appform" theme="simple">
	<s:hidden id="appSubmitStatus" name="submitStatus"/>
	<s:hidden id="appInnovationScore" name="innovationScore"/>
	<s:hidden id="appScientificScore" name="scientificScore"/>
	<s:hidden id="appBenefitScore" name="benefitScore"/>
	<s:hidden id="appOpinion" name="opinion"/>
	<s:hidden id="appQualitativeOpinion" name="qualitativeOpinion"/>
</s:form>
<s:form id="group_review_appform" theme="simple">
	<s:hidden id="appReviewWay" name="reviewWay"/>
	<s:hidden id="appReviewStatus" name="reviewStatus"/>
	<s:hidden id="appReviewResult" name="reviewResult"/>
	<s:hidden id="appReviewOpinion" name="reviewOpinion"/>
	<s:hidden id="appReviewOpinionQualitative" name="reviewOpinionQualitative"/>
</s:form>
<s:form id="review_appAudit_form" theme="simple">
	<s:hidden id="reviewAppAuditStatus" name="reviewAuditStatus"/>
	<s:hidden id="reviewAppAuditResult" name="reviewAuditResult"/>
	<s:hidden id="reviewAppAuditOpinion" name="reviewAuditOpinion"/>
	<s:hidden id="reviewAppAuditOpinionFeedback" name="reviewAuditOpinionFeedback"/>
	<s:hidden name="number"/>
	<s:hidden name="approveFee"/>
	<s:hidden name="reviewAuditDate"/>
</s:form>
<s:form id="uploadAppFile_form" theme="simple">
	<s:hidden id="appFileId" name="appFileId" />
</s:form>