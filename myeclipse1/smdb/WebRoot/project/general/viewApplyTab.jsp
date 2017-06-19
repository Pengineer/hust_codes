<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_apply_template" style="display:none;">
	<s:hidden value="${appInfo[1]}" id="deadlineApp" />
	<s:hidden value="${appRevInfo[1]}" id="deadlineAppRev" />
	<s:hidden name="appStatus" id="appStatus" value="%{projectService.getBusinessStatus('011')}"/>
	<div class="p_box_b">
		<div class="p_box_b_1">
			{if application.createMode==0}
				{if application.applicantSubmitStatus!=3}保存时间{else}提交时间{/if}：${application.applicantSubmitDate}
				<!-- 部级管理人员最近一次结项走流程录入评审结果 -->
				{if application.finalAuditStatus != 3}
					{if reviewflagFromApp != 22 && reviewflagFromApp != 23 && reviewflagFromApp != 32 && reviewflagFromApp != 33 && reviewflagFromApp != 42 && reviewflagFromApp != 43 && accountType == "MINISTRY" && application.status == 6}
						<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_ADD'>
							{if reviewflagFromApp == -1  && accountType == "MINISTRY" && application.status == 6}
								<span><input class="btn2 j_addAppReviewResultPop" type="button" appId="${application.id}" value="录入评审" /></span>
							{/if}
							<span><input class="btn2 j_addAppReviewExpert" type="button" appId="${application.id}"  value="选择专家" /></span>
						</sec:authorize>
					{/if}
				{/if}
			{elseif application.createMode == 1 || application.createMode==2}
				<span class="import_css">该数据为导入数据</span>
			{else}
			{/if}
		</div>
		<div class="p_box_b_2">
			<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLY_VIEW'>
				<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLY_DOWNLOAD'>
					{if application.file != null}
						<div style="float: right;"><img src="image/ico09.gif" />
							<a href="" id="${application.id}" class="download_general_1">下载申请评审书</a>
							({if appFileSizeList[0] != null}
								${appFileSizeList[0]}
							{else}附件不存在
							{/if})
						</div>
					{/if}
				</sec:authorize>
				<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
					<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLY_UPLOAD'>
						<div style="float: right;"><img src="image/ico10.gif" /><a href="javascript:void(0);" onclick="uploadAppPop();">上传申请评审书</a>&nbsp;&nbsp;</div>
					</sec:authorize>
				</s:if>
			</sec:authorize>
		</div>
	</div>
	<div class="p_box_body">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			<tr class="table_tr7">
				<td class="key" width="100">项目名称：</td>
				<td class="value" colspan='3'>${application.name}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key" width="100">英文名：</td>
				<td class="value" colspan='3'>${application.englishName}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key" width="100">申请人：</td>
				<td class="value" width="200"><s:hidden id="directors" name="${application.applicantName}" value="${application.applicantId}" cssClass="directors"/></td>
				<td class="key" width="120">项目子类：</td>
				<td class="value">${subTypeNameOld}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key">依托高校：</td>
				<td class="value"><a id="${universityIdOld}" class="linkUni" href="">${application.agencyName}</a></td>
				<td class="key">依托院系或研究基地：</td>
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
				<td class="key" width="100">研究类型：</td>
				<td class="value" width="200">${researchTypeName}</td>
				<td class="key" width="120">学科门类：</td>
				<td class="value">${application.disciplineType}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key">学科代码：</td>
				<td class="value">${application.discipline}</td>
				<td class="key">相关学科代码：</td>
				<td class="value">${application.relativeDiscipline }</td>
			</tr>
			<tr class="table_tr7">
				<td class="key">申请时间：</td>
				<td class="value">${application.applicantSubmitDate}</td>
				<td class="key">计划完成时间：</td>
				<td class="value">${application.planEndDate}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key">申请经费（万）：</td>
				<td class="value">${application.applyFee}</td>
				<td class="key">其他来源经费（万）：</td>
				<td class="value">${application.otherFee}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key">最终成果形式：</td>
				<td class="value">
					{if application.productType != null && application.productType != ""}
						${application.productType}{if application.productTypeOther != null && application.productTypeOther != ""}; ${application.productTypeOther}{/if}
					{else}
						{if application.productTypeOther != null && application.productTypeOther != ""}${application.productTypeOther}
						{/if}
					{/if}
				</td>
				<td class="key">关键词：</td>
				<td class="value">${application.keywords}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key">摘要：</td>
				<td class="value" colspan="3">${application.summary}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key">备注：</td>
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
					<td colspan="3" height="15" align="center">申请经费概算明细表</td>
				</tr>
			</thead>
			<tbody>
				<tr class="table_tr3">
					<td>经费科目</td>
					<td width="30%">金额（万元）</td>
					<td width="300">开支说明</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">图书资料费</td>
					<td class="value">${projectFeeApply.bookFee}</td>
					<td class="value">${projectFeeApply.bookNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">数据采集费</td>
					<td class="value">${projectFeeApply.dataFee}</td>
					<td class="value">${projectFeeApply.dataNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">调研差旅费</td>
					<td class="value">${projectFeeApply.travelFee}</td>
					<td class="value">${projectFeeApply.travelNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">会议费</td>
					<td class="value">${projectFeeApply.conferenceFee}</td>
					<td class="value">${projectFeeApply.conferenceNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">国际合作交流费</td>
					<td class="value">${projectFeeApply.internationalFee}</td>
					<td class="value">${projectFeeApply.internationalNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">设备购置和使用费</td>
					<td class="value">${projectFeeApply.deviceFee}</td>
					<td class="value">${projectFeeApply.deviceNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">专家咨询和评审费</td>
					<td class="value">${projectFeeApply.consultationFee}</td>
					<td class="value">${projectFeeApply.consultationNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">助研津贴和劳务费</td>
					<td class="value">${projectFeeApply.laborFee}</td>
					<td class="value">${projectFeeApply.laborNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">印刷和出版费</td>
					<td class="value">${projectFeeApply.printFee}</td>
					<td class="value">${projectFeeApply.printNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">间接费用</td>
					<td class="value">${projectFeeApply.indirectFee}</td>
					<td class="value">${projectFeeApply.indirectNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">其他</td>
					<td class="value">${projectFeeApply.otherFee}</td>
					<td class="value">${projectFeeApply.otherNote}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">合计</td>
					<td class="value" colspan="2">${projectFeeApply.totalFee}</td>
				</tr>
				<tr class="table_tr4">
					<td class="key">相关说明</td>
					<td class="value" colspan="2">${projectFeeApply.feeNote}</td>
				</tr>
			</tbody>
		</table>
	</div>
	{/if}
	
	<!-- 研究人员申请相关操作-->
	<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
		{if application.applicantSubmitStatus == 1 || application.applicantSubmitStatus == 2}<!-- 项目申请申请暂存或退回 -->
			<div class="p_box_body">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr height="36">
						<td width="150">
							提交状态：{if application.applicantSubmitStatus == 1}退回{else}保存{/if}
						</td>
						<td align="right">
							{if isDirector == 1}
								<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_MODIFY">
									<input type="button" value="修改" class="btn1 j_modifyAppApply"/>&nbsp;
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_ADD">
									<input type="button" value="提交" class="btn1 j_submitAppApply"/>&nbsp;
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_DELETE">
									<input type="button" value="删除" class="btn1 j_deleteAppApply"/>
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
		<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_VIEW'>
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead>
						<tr class="head_title">
							<td colspan="3">各级审核情况</td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td>各级管理部门意见</td>
							<td width="30%">是否同意立项</td>
							<td width="300">查看详情</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">院系或研究基地意见</td>
							<td class="value" width="200">
								{if application.deptInstAuditResult == 1}
									{if application.deptInstAuditStatus == 3}<span class="ico_txt5">不同意</span>
									{elseif application.deptInstAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
									{elseif application.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
									{/if}
								{elseif application.deptInstAuditResult == 2}
									{if application.deptInstAuditStatus == 3}<span class="ico_txt1">同意</span>
									{elseif application.deptInstAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
									{elseif application.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
									{/if}
								{elseif application.deptInstAuditResult == 0}<span class="ico_txt3">待审</span>
								{/if}
							</td>
							<td>
								<!-- 非导入数据、部门账号、在部门审核级别 -->
								{if application.createMode == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && application.status == 2 && application.finalAuditStatus != 3}
									{if application.deptInstAuditStatus == 0}
										<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
											<input class="btn1 j_addAppAuditPop" type="button" value="审核" />
											<input class="btn1 j_backAppApply" type="button" value="退回" />
										</sec:authorize>
									{elseif application.deptInstAuditStatus < 3}
										<span class="ico_txt2"><a class="j_viewAppAuditPop" appId="${application.id}" alt="1" style="cursor: pointer;">查看详情</a></span>
										{if (isPrincipal == 1 && (appAuditorInfo[1] == belongId || appAuditorInfo[2] == belongId)) || (isPrincipal == 0 && appAuditorInfo[0] == belongId)}
										<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_MODIFY'>
											<input class="btn1 j_modifyAppAuditPop" type="button" value="修改" />
										</sec:authorize>
										<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
											<input class="btn1 j_submitAppAudit" type="button" value="提交" />
											<input class="btn1 j_backAppApply" type="button" value="退回" />
										</sec:authorize>
										{/if}
									{/if}
								{else}
									<span class="ico_txt2"><a appId="${application.id}" alt="1" class="j_viewAppAuditPop" style="cursor: pointer;">查看详情</a></span>
								{/if}
							</td>
						</tr>
						<!-- 校级及以上管理人员 -->
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0">
							<tr class="table_tr4">
								<td class="key">高校意见</td>
								<td class="value">
									{if application.universityAuditResult == 1}
										{if application.universityAuditStatus == 3}<span class="ico_txt5">不同意</span>
										{elseif application.universityAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
										{elseif application.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif application.universityAuditResult == 2}
										{if application.universityAuditStatus == 3}<span class="ico_txt1">同意</span>
										{elseif application.universityAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
										{elseif application.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif application.universityAuditResult == 0}<span class="ico_txt3">待审</span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、高校账号、在高校审核级别 -->
									{if application.createMode == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && application.status == 3 && application.finalAuditStatus != 3}
										{if application.universityAuditStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_addAppAuditPop" type="button" value="审核" />
												<input class="btn1 j_backAppApply" type="button" value="退回" />
											</sec:authorize>
										{elseif application.universityAuditStatus < 3}
											<span class="ico_txt2"><a appId="${application.id}" alt="2" class="j_viewAppAuditPop" style="cursor: pointer;">查看详情</a></span>
											{if (isPrincipal == 1 && appAuditorInfo[4] == belongId) || (isPrincipal == 0 && appAuditorInfo[3] == belongId)}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_MODIFY'>
												<input class="btn1 j_modifyAppAuditPop" type="button" value="修改" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_submitAppAudit" type="button" value="提交" />
												<input class="btn1 j_backAppApply" type="button" value="退回" />
											</sec:authorize>
											{/if}
										{/if}
									{else}
										<span class="ico_txt2"><a appId="${application.id}" alt="2" class="j_viewAppAuditPop" style="cursor: pointer;">查看详情</a></span>
									{/if}
								</td>
							</tr>
						</s:if>
						{if utypeOld == 0}<!-- 地方高校 -->
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
								<tr class="table_tr4">
									<td class="key">省厅意见</td>
									<td class="value">
										{if application.provinceAuditResult == 1}
											{if application.provinceAuditStatus == 3}<span class="ico_txt5">不同意</span>
											{elseif application.provinceAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
											{elseif application.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif application.provinceAuditResult == 2}
											{if application.provinceAuditStatus == 3}<span class="ico_txt1">同意</span>
											{elseif application.provinceAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
											{elseif application.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif application.provinceAuditResult == 0}<span class="ico_txt3">待审</span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、省厅账号、在省厅审核级别 -->
										{if application.createMode == 0 && accountType == "PROVINCE" && application.status == 4 && application.finalAuditStatus != 3}
											{if application.provinceAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
													<input class="btn1 j_addAppAuditPop" type="button" value="审核" />
													<input class="btn1 j_backAppApply" type="button" value="退回" />
												</sec:authorize>
											{elseif application.provinceAuditStatus < 3}
												<span class="ico_txt2"><a appId="${application.id}" alt="3" class="j_viewAppAuditPop" style="cursor: pointer;">查看详情</a></span>
												{if (isPrincipal == 1 && appAuditorInfo[6] == belongId) || (isPrincipal == 0 && appAuditorInfo[5] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_MODIFY'>
													<input class="btn1 j_modifyAppAuditPop" type="button" value="修改" />
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
													<input class="btn1 j_submitAppAudit" type="button" value="提交" />
													<input class="btn1 j_backAppApply" type="button" value="退回" />
												</sec:authorize>
												{/if}
											{/if}
										{else}
											<span class="ico_txt2"><a appId="${application.id}" alt="3" class="j_viewAppAuditPop" style="cursor: pointer;">查看详情</a></span>
										{/if}
									</td>
								</tr>
							</s:if>
						{/if}
						<!-- 教育部及系统管理员 -->
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0">
							<tr class="table_tr4">
								<td class="key">教育部意见</td>
								<td class="value">
									{if application.ministryAuditResult == 1}
											{if application.ministryAuditStatus == 3}<span class="ico_txt5">不同意</span>
											{elseif application.ministryAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
											{elseif application.ministryAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
									{elseif application.ministryAuditResult == 2}
										{if application.ministryAuditStatus == 3}<span class="ico_txt1">同意</span>
										{elseif application.ministryAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
										{elseif application.ministryAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif application.ministryAuditResult == 0}<span class="ico_txt3">待审</span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、教育部账号、在部级审核级别 -->
									{if application.createMode == 0 && accountType == "MINISTRY" && application.status == 5 && application.finalAuditStatus != 3}
										{if application.ministryAuditStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_addAppAuditPop" type="button" value="审核"/>
												<input class="btn1 j_backAppApply" type="button" value="退回" />
											</sec:authorize>
										{elseif application.ministryAuditStatus < 3}
											<span class="ico_txt2"><a appId="${application.id}" alt="4" class="j_viewAppAuditPop" style="cursor: pointer;">查看详情</a></span>
											{if (isPrincipal == 1 && appAuditorInfo[8] == belongId) || (isPrincipal == 0 && appAuditorInfo[7] == belongId)}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_MODIFY'>
												<input class="btn1 j_modifyAppAuditPop" type="button" value="修改" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_APPLYAUDIT_ADD'>
												<input class="btn1 j_submitAppAudit" type="button" value="提交" />
												<input class="btn1 j_backAppApply" type="button" value="退回" />
											</sec:authorize>
											{/if}
										{/if}
									{else}
										<span class="ico_txt2"><a appId="${application.id}" alt="4" class="j_viewAppAuditPop" style="cursor: pointer;">查看详情</a></span>
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
	<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_VIEW'>
		{if (accountType =="EXPERT" || accountType == "TEACHER") && isAppReviewer > 0 && applicationReview != null && applicationReview.submitStatus < 3}<!-- 是评审专家 -->
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead>
						<tr class="head_title">
							<td colspan="6">专家评审情况</td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td width="90">专家序号</td>
							<td>专家姓名</td>
							<td width="70">分数</td>
							<td width="70">等级</td>
							<td width="90">提交状态</td>
							<td width="250">查看详情</td>
						</tr>
						
						<tr class="table_tr4">
							<td>${applicationReview.reviewerSn}</td>
							<td>${applicationReview.reviewerName}</td>
							<td>${applicationReview.score}</td>
							<td>${applicationPersonalGrade}</td>
							<td>
								{if applicationReview.submitStatus==2}暂存
								{elseif applicationReview.submitStatus==3}提交
								{else}
								{/if}
							</td>
							<td>
								<!-- 非导入数据、申请在评审级别 -->
								{if application.createMode == 0 && application.status == 6 && application.finalAuditStatus != 3 && applicationReview.submitStatus != 3}
									{if appPassAlready != 1}
										{if applicationReview.submitStatus==0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_addAppReviewPop" type="button" appId="${application.id}" value="评审"/>
											</sec:authorize>
										{elseif applicationReview.submitStatus < 3}
											<span class="ico_txt2"><a class="j_viewAppReviewPop" appRevId="${applicationReview.id}" style="cursor: pointer;">查看详情</a></span>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_MODIFY'>
												<input class="btn1 j_modifyAppReviewPop" type="button" appId="${application.id}" appRevId="${applicationReview.id}" value="修改"/>
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_submitAppReview" type="button" appId="${application.id}" value="提交"/>
											</sec:authorize>
										{/if}
									{/if}
								{else}
									<span class="ico_txt2"><a class="j_viewAppReviewPop" appRevId="${applicationReview.id}" style="cursor: pointer;">查看详情</a></span>
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
							<td colspan="6">专家评审情况</td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td width="90">专家序号</td>
							<td>专家姓名</td>
							<td width="70">分数</td>
							<td width="70">等级</td>
							<td width="90">提交状态</td>
							<td width="250">查看详情</td>
						</tr>
						{if  applicationReviews != null}
							{for item in applicationReviews}
								{if item != null}
									<tr class="table_tr4">
										<td>${item[1]}</td>
										<td>${item[2]}</td>
										<td>${item[3]}</td>
										<td>${item[4]}</td>
										<td>
											{if item[5]==0}待审
											{elseif item[5]==2}暂存
											{elseif item[5]==3}提交
											{/if}
										</td>
										<td>
											{if item[5]!=0}
												<span class="ico_txt2"><a class="j_viewAppReviewPop" appRevId="${item[0]}" style="cursor: pointer;">查看详情</a></span>
											{/if}
										</td>
									</tr>
								{/if}
							{/for}
						{else}
							{if applicationReviewExpert != null}
								{for item in applicationReviewExpert}
									{if item != null}
										<tr class="table_tr4">
											<td></td>
											<td><a href="" class="linkDirectors" id="${item[2]}">${item[0]}</a></td>
											<td></td>
											<td></td>
											<td>
												{if item[1]==0}待审
												{elseif item[1]==2}暂存
												{elseif item[1]==3}提交
												{/if}
											</td>
											<td>
											</td>
										</tr>
									{/if}
								{/for}
							{/if}
						{/if}
						<!-- 评审汇总信息 -->
						<tr class="table_tr4">
							<td>汇总意见</td>
							<td>${application.reviewerName}</td>
							<td>${application.reviewAverageScore}</td>
							<td>${applicationReviewGrade}</td>
							<td>
								{if application.reviewStatus == 3 && application.reviewResult == 1}提交(不同意)
								{elseif application.reviewStatus == 3 && application.reviewResult == 2}提交(同意)
								{elseif application.reviewStatus == 2 && application.reviewResult == 1}暂存(不同意)
								{elseif application.reviewStatus == 2 && application.reviewResult == 2}暂存(同意)
								{else}
								{/if}
							</td>
							<td>
								 <!-- 非导入数据、教师或专家账号、申请处在小组评审级别 -->
								{if application.createMode == 0 && (accountType =="EXPERT" || accountType == "TEACHER") && application.status > 5 && application.finalAuditStatus != 3 && allAppReviewSubmit != -1 && reviewNum == 1 }
									{if appPassAlready != 1}
										{if application.reviewStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_addAppGroupReviewPop" appId="${application.id}" type="button" value="小组评审" />
											</sec:authorize>
										{elseif application.reviewStatus == 2}
											<span class="ico_txt2"><a class="j_viewAppGroupReviewPop" appId="${application.id}" style="cursor: pointer;">查看详情</a></span>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_MODIFY'>
												<input class="btn1 j_modifyAppGroupReviewPop" appId="${application.id}" type="button" value="修改" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_ADD'>
												<input class="btn1 j_submitAppGroupReview" appId="${application.id}" type="button" value="提交" />
											</sec:authorize>
										{elseif application.reviewStatus == 3}	
											<span class="ico_txt2"><a class="j_viewAppGroupReviewPop" appId="${application.id}" style="cursor: pointer;">查看详情</a></span>
										{/if}
									{/if}
								{elseif reviewflagFromApp == 22 && accountType == "MINISTRY" && application.status == 6}
									<span class="ico_txt2"><a class="j_viewAppGroupReviewPop" appId="${application.id}" style="cursor: pointer;">查看详情</a></span>
									<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_MODIFY'>
										<span><input class="btn2 j_modifyAppReviewResultPop" type="button" appId="${application.id}" value="修改" /></span>
									</sec:authorize>
									<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEW_ADD'>
										<span><input class="btn2 j_submitAppReviewResult" type="button" appId="${application.id}" value="提交" /></span>
									</sec:authorize>
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
						<td colspan="5">最终立项结果</td>
					</tr>
				</thead>
				<tbody>
					<tr class="table_tr3">
						<td width="120">审核时间</td>
						<td>是否同意立项</td>
						<td>提交状态</td>
						<td width="250">查看详情</td>
						<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_PUBLISH_SWITCH">
							<td width="120">发布状态</td>
						</sec:authorize>
					</tr>
					<tr class="table_tr4">
						<td>
							${application.finalAuditDate}
						</td>
						<td>
							{if application.finalAuditResult == 1}
								{if application.finalAuditStatus == 3}<span class="ico_txt5">不同意</span>
								{elseif application.finalAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
								{elseif application.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
								{/if}
							{elseif application.finalAuditResult == 2}
								{if application.finalAuditStatus == 3}<span class="ico_txt1">同意</span>
								{elseif application.finalAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
								{elseif application.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
								{/if}
							{elseif application.finalAuditResult == 0}<span class="ico_txt3">待审</span>
							{/if}
						</td>
						<td>
							{if application.finalAuditStatus == 3}<span>提交</span>
							{elseif application.finalAuditStatus == 2}<span>暂存</span>
							{elseif application.finalAuditStatus == 1}<span>退回</span>
							{elseif application.finalAuditStatus == 0}<span></span>
							{/if}
						</td>
						<td>
							<!-- 非导入数据、部级账号、申请处在评审审核审核级别 -->
							{if application.createMode == 0 && accountType == "MINISTRY" && application.status == 7 && application.finalAuditStatus != 3}
								<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_VIEW'>
									{if appPassAlready != 1}
										{if application.finalAuditStatus==0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_ADD'>
												<input class="btn1 j_addAppReviewAuditPop" type="button" appId="${application.id}" value="审核" />
											</sec:authorize>
										{elseif application.finalAuditStatus < 3}
											<span class="ico_txt2"><a class="j_viewAppReviewAuditPop" appId="${application.id}" style="cursor: pointer;">查看详情</a></span>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_MODIFY'>
												<input class="btn1 j_modifyAppReviewAuditPop" type="button" appId="${application.id}" value="修改" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_ADD'>
												<input class="btn1 j_submitAppReviewAuditResult" type="button" appId="${application.id}" value="提交" />
											</sec:authorize>
										{/if}
									{/if}
								</sec:authorize>
							{elseif application.finalAuditStatus > 0}
								<sec:authorize ifAllGranted='ROLE_PROJECT_GENERAL_APPLICATION_REVIEWAUDIT_VIEW'>
									<span class="ico_txt2"><a class="j_viewAppReviewAuditPop" appId="${application.id}" style="cursor: pointer;">查看详情</a></span>
								</sec:authorize>
							{/if}
						</td>
						<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_PUBLISH_SWITCH">
						<td>
						{if application.finalAuditResult !=0 }
							<input type="hidden" name="application.finalAuditResultPublish" value="${ application.finalAuditResultPublish}" />
							<input type="hidden" name="application.id" value="${application.id}" />
							{if application.finalAuditResultPublish == 0}
							
							<span id = "application_finalAuditResultPublishStatus">未发布 </span><input class="btn1 j_switchAppPublish" type="button" value="发布">
							{elseif}
							<span id = "application_finalAuditResultPublishStatus">已发布</span> <input class="btn1 j_switchAppPublish" type="button" value="取消发布">
							{/if}
						{elseif}
						<span id = "application_finalAuditResultPublishStatus">未审核，不能发布</span>
						{/if}
						</td>
						</sec:authorize>
					</tr>
				</tbody>
			</s:if>
			<s:else><!-- 部级以下所有人员 -->
				{if application.createMode == 1 || application.createMode == 2 || application.applicantSubmitStatus == 3}
					<thead>
						<tr class="head_title">
							<td colspan="2">最终立项结果</td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td width="50%">审核时间</td>
							<td width="50%">是否同意立项</td>
						</tr>
						<tr class="table_tr4">
							<td>{if application.finalAuditStatus == 3}${application.finalAuditDate}{/if}</td>
							<td>
								{if application.finalAuditStatus == 3 && application.finalAuditResult == 2}<span class="ico_txt1">同意</span>
								{elseif application.finalAuditStatus == 3 && application.finalAuditResult == 1}<span class="ico_txt5">不同意</span>
								{else}<span class="ico_txt3">待审</span>
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
						<td class="key" width="117">审核意见：<br/></span><span>（反馈给负责人）</span></td>
						<td class="value" >{if application.finalAuditOpinionFeedback != null && application.finalAuditOpinionFeedback != ""}<pre>${application.finalAuditOpinionFeedback}</pre>{/if}</td>
					</tr>
				</table>
			</div>
		</div>
	{/if}
</textarea>
<div id="view_apply" style="display:none;"></div>
<s:form id="appForm" theme="simple">
	<s:hidden id="appFormEntityid" name="entityId" />
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
