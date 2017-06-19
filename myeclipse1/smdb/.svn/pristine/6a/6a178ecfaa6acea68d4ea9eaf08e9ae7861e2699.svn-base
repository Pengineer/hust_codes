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
				{if isDirector == 1 && (midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 1) && endPassAlready != 1 && endPending != 1 && midPassAlready != 1 && midPending != 1 && midForbid != 1 && granted != null && granted.status == 1}
					<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_ADD'>
						<div class="p_box_b_2">
							<a href="javascript:void(0);" class="j_downloadMidTemplate" >下载中检申请书模板</a>
						</div>
						<div class="p_box_b_1" id="applyMid">
							<input type="button" class="btn1 j_toAddMidApplyPop" value="申请" />
						</div>
					</sec:authorize>
				{/if}
			</s:if>
			<!-- 管理人员录入 -->
			<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_DATA_ADD'><!-- 录入结项 -->
					{if midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 1 && accountType == "MINISTRY" && midPassAlready==0 && midPending==0 && midForbid != 1 && endPassAlready != 1 && granted != null && granted.status == 1}
						<div><input class="btn1 j_addMidResultPop" type="button" value="添加" /><br/></div>
					{elseif granted != null && granted.status == 3}
						<div>项目已中止，不能录入数据</div>
					{elseif granted != null && granted.status == 4}
						<div>项目已撤项，不能录入数据</div>
					{elseif endPassAlready == 1}
						<div>结项已通过，不能录入数据</div>
					{elseif midPassAlready!=0}
						<div>中检已通过，不能录入数据</div>
					{elseif midPending!=0}
						<div>中检正在处理中，不能录入数据</div>
					{elseif midForbid == 1}
						<div>中检已截止，不能录入数据</div>
					{/if}
				</sec:authorize>
			</s:elseif>
		{/if}
		<div class="p_box_t">
			<div class="p_box_t_t">第<span class="number"  name="${midList.length}">${midinspection_index}</span>次中检{if midinspection_index == 0}&nbsp;（当前）{/if}</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div>
			<div class="p_box_b">
				<div class="p_box_b_1">
					{if midinspection.createMode == 0}
						{if midinspection.applicantSubmitStatus == 3}提交时间：
						{else}保存时间：
						{/if}
						${midinspection.applicantSubmitDate}
					{elseif midinspection.createMode == 1 || midinspection.createMode == 2}
						<span class="import_css">该数据为导入数据</span>
						{if midinspection_index == 0 && accountType == "MINISTRY" && midinspection.finalAuditStatus == 2 && midPassAlready==0 && midForbid != 1 && granted != null && granted.status == 1}
							<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_DATA_MODIFY'>
								<input class="btn1 j_modifyMidResultPop" type="button" value="修改" />
							</sec:authorize>
							<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_DATA_ADD'>
								<input class="btn1 j_submitMidResult" type="button" value="提交" />
							</sec:authorize>
						{/if}
					{else}
					{/if}
				</div>
				<div class="p_box_b_2">
				<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_VIEW'>
					{if midinspection.file != null}
						<div style="float: right;"><img src="image/ico09.gif" />
							<a href="" id="${midinspection.id}" class="download_key_2">下载中检申请书</a>
							({if midFileSizeList[midinspection_index] != null}
							${midFileSizeList[midinspection_index]}
							{else}附件不存在
							{/if})
						</div>
					{/if}
					{if midinspection.finalAuditStatus==3 && granted != null}
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
					<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_DATA_UPLOAD'>
						<div style="float: right;"><img src="image/ico10.gif" /><a href="javascript:void(0);" class="j_uploadMidPop" midId="${midinspection.id}">上传中检申请书</a>&nbsp;&nbsp;</div>
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
							<td class="key" width="70">备注：</td>
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
						<td width="16%" class="key">批准经费</td>
						<td width="16%" class="value">${projectMidFees[midinspection_index].approveFee}</td>
						<td width="16%" class="key">已拨经费</td>
						<td width="16%" class="value">${projectMidFees[midinspection_index].fundedFee}</td>
						<td width="16%" class="key">未拨经费</td>
						<td width="16%" class="value">${projectMidFees[midinspection_index].toFundFee}</td>
					</tr>
					<tr class="table_tr3">
						<td class="key" colspan="6">中检经费结算明细</td>
					</tr>
				</table>
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				{if projectFeeGranted != null }
					<tbody>
						<tr class="table_tr3">
							<td>经费科目</td>
							<td width="15%">经费预算金额（万元）</td>
							<td width="15%">经费支出金额（万元）</td>
							<td width="200">开支说明</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">图书资料费</td>
							<td class="value">${projectFeeGranted.bookFee}</td>
							<td class="value">${projectMidFees[midinspection_index].bookFee}</td>
							<td class="value">${projectMidFees[midinspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">数据采集费</td>
							<td class="value">${projectFeeGranted.dataFee}</td>
							<td class="value">${projectMidFees[midinspection_index].dataFee}</td>
							<td class="value">${projectMidFees[midinspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">调研差旅费</td>
							<td class="value">${projectFeeGranted.travelFee}</td>
							<td class="value">${projectMidFees[midinspection_index].travelFee}</td>
							<td class="value">${projectMidFees[midinspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">会议费</td>
							<td class="value">${projectFeeGranted.conferenceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].conferenceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">国际合作交流费</td>
							<td class="value">${projectFeeGranted.internationalFee}</td>
							<td class="value">${projectMidFees[midinspection_index].internationalFee}</td>
							<td class="value">${projectMidFees[midinspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">设备购置和使用费</td>
							<td class="value">${projectFeeGranted.deviceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].deviceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">专家咨询和评审费</td>
							<td class="value">${projectFeeGranted.consultationFee}</td>
							<td class="value">${projectMidFees[midinspection_index].consultationFee}</td>
							<td class="value">${projectMidFees[midinspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">助研津贴和劳务费</td>
							<td class="value">${projectFeeGranted.laborFee}</td>
							<td class="value">${projectMidFees[midinspection_index].laborFee}</td>
							<td class="value">${projectMidFees[midinspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">印刷和出版费</td>
							<td class="value">${projectFeeGranted.printFee}</td>
							<td class="value">${projectMidFees[midinspection_index].printFee}</td>
							<td class="value">${projectMidFees[midinspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">间接费用</td>
							<td class="value">${projectFeeGranted.indirectFee}</td>
							<td class="value">${projectMidFees[midinspection_index].indirectFee}</td>
							<td class="value">${projectMidFees[midinspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">其他</td>
							<td class="value">${projectFeeGranted.otherFee}</td>
							<td class="value">${projectMidFees[midinspection_index].otherFee}</td>
							<td class="value">${projectMidFees[midinspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">合计</td>
							<td class="value">${projectFeeGranted.totalFee}</td>
							<td class="value" colspan="2">${projectMidFees[midinspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费</td>
							<td class="value" colspan="3">${projectMidFees[midinspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费支出说明</td>
							<td class="value" colspan="3">${projectMidFees[midinspection_index].feeNote}</td>
						</tr>
					</tbody>
					{else}
					<tbody>
						<tr class="table_tr3">
							<td>经费科目</td>
							<td width="15%">经费支出金额（万元）</td>
							<td width="300">开支说明</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">图书资料费</td>
							<td class="value">${projectMidFees[midinspection_index].bookFee}</td>
							<td class="value">${projectMidFees[midinspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">数据采集费</td>
							<td class="value">${projectMidFees[midinspection_index].dataFee}</td>
							<td class="value">${projectMidFees[midinspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">调研差旅费</td>
							<td class="value">${projectMidFees[midinspection_index].travelFee}</td>
							<td class="value">${projectMidFees[midinspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">会议费</td>
							<td class="value">${projectMidFees[midinspection_index].conferenceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">国际合作交流费</td>
							<td class="value">${projectMidFees[midinspection_index].internationalFee}</td>
							<td class="value">${projectMidFees[midinspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">设备购置和使用费</td>
							<td class="value">${projectMidFees[midinspection_index].deviceFee}</td>
							<td class="value">${projectMidFees[midinspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">专家咨询和评审费</td>
							<td class="value">${projectMidFees[midinspection_index].consultationFee}</td>
							<td class="value">${projectMidFees[midinspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">助研津贴和劳务费</td>
							<td class="value">${projectMidFees[midinspection_index].laborFee}</td>
							<td class="value">${projectMidFees[midinspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">印刷和出版费</td>
							<td class="value">${projectMidFees[midinspection_index].printFee}</td>
							<td class="value">${projectMidFees[midinspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">间接费用</td>
							<td class="value">${projectMidFees[midinspection_index].indirectFee}</td>
							<td class="value">${projectMidFees[midinspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">其他</td>
							<td class="value">${projectMidFees[midinspection_index].otherFee}</td>
							<td class="value">${projectMidFees[midinspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">合计</td>
							<td class="value" colspan="2">${projectMidFees[midinspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费</td>
							<td class="value" colspan="2">${projectMidFees[midinspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费支出说明</td>
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
									提交状态：{if midinspection.applicantSubmitStatus == 1}退回{else}保存{/if}
								</td>
								<td align="right">
									{if isDirector == 1 && granted != null && granted.status == 1}
										{if endPassAlready != 1 && endPending != 1 && midPassAlready != 1 && midForbid != 1}
											<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_MODIFY">
												<input type="button" value="修改" class="btn1 j_toModifyMidApplyPop"/>&nbsp;
											</sec:authorize>
											<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_ADD">
												<input type="button" value="提交" class="btn1 j_submitMidApply"/>&nbsp;
											</sec:authorize>
										{/if}
										<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_DELETE">
											<input type="button" value="删除" class="btn1 j_deleteMidApply"/>
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</table>
					</div>
				{/if}
			</s:if>
			<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 "><!-- 部门及以上管理人员 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_VIEW'>
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
									<td width="30%">是否同意中检</td>
									<td width="300">查看详情</td>
								</tr>
								<!-- 部门审核信息 -->
								<tr class="table_tr4">
									<td>院系或研究基地意见</td>
									<td>
										{if midinspection.deptInstAuditResult == 1}
											{if midinspection.deptInstAuditStatus == 3}<span class="ico_txt5">不同意</span>
											{elseif midinspection.deptInstAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
											{elseif midinspection.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif midinspection.deptInstAuditResult == 2}
											{if midinspection.deptInstAuditStatus == 3}<span class="ico_txt1">同意</span>
											{elseif midinspection.deptInstAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
											{elseif midinspection.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif midinspection.deptInstAuditResult == 0}<span class="ico_txt3">待审</span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、部门账号、最近一次中检处在部门审核级别 -->
										{if midinspection.createMode == 0 && midinspection_index == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && midinspection.status == 2 && midinspection.finalAuditStatus != 3}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if midinspection.deptInstAuditStatus == 0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_addMidAuditPop" type="button" value="审核"/>
														<input class="btn1 j_backMidApply" type="button" value="退回" />
													</sec:authorize>
												{elseif midinspection.deptInstAuditStatus < 3}
													<span class="ico_txt2"><a midId="${midinspection.id}" alt="1" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
													{if (isPrincipal == 1 && (midAuditorInfo[1] == belongId || midAuditorInfo[2] == belongId)) || (isPrincipal == 0 && midAuditorInfo[0] == belongId)}
													<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_MODIFY'>
														<input class="btn1 j_modifyMidAuditPop" type="button" value="修改" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_submitMidAudit" type="button" value="提交" />
														<input class="btn1 j_backMidApply" type="button" value="退回" />
													</sec:authorize>
													{/if}
												{/if}
											{/if}	
										{else}
											<span class="ico_txt2"><a midId="${midinspection.id}" alt="1" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
										{/if}
									</td>
								</tr>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0"><!-- 校级及以上管理人员 -->
									<tr class="table_tr4">
										<td>高校意见</td>
										<td>
											{if midinspection.universityAuditResult == 1}
												{if midinspection.universityAuditStatus == 3}<span class="ico_txt5">不同意</span>
												{elseif midinspection.universityAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
												{elseif midinspection.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
												{/if}
											{elseif midinspection.universityAuditResult == 2}
												{if midinspection.universityAuditStatus == 3}<span class="ico_txt1">同意</span>
												{elseif midinspection.universityAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
												{elseif midinspection.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
												{/if}
											{elseif midinspection.universityAuditResult == 0}<span class="ico_txt3">待审</span>
											{/if}
										</td>
										<td>
											<!-- 非导入数据、高校账号、最近一次中检处在高校审核级别 -->
											{if midinspection.createMode == 0 && midinspection_index == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && midinspection.status == 3 && midinspection.finalAuditStatus != 3}
												{if endPassAlready != 1 && granted != null && granted.status == 1}
													{if midinspection.universityAuditStatus == 0}
														<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_addMidAuditPop" type="button" value="审核" />
															<input class="btn1 j_backMidApply" type="button" value="退回" />
														</sec:authorize>
													{elseif midinspection.universityAuditStatus < 3}
														<span class="ico_txt2"><a midId="${midinspection.id}" alt="2" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
														{if (isPrincipal == 1 && midAuditorInfo[4] == belongId) || (isPrincipal == 0 && midAuditorInfo[3] == belongId)}
														<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_MODIFY'>
															<input class="btn1 j_modifyMidAuditPop" type="button" value="修改" />
														</sec:authorize>
														<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_submitMidAudit" type="button" value="提交" />
															<input class="btn1 j_backMidApply" type="button" value="退回" />
														</sec:authorize>
														{/if}
													{/if}
												{/if}	
											{else}
												<span class="ico_txt2"><a midId="${midinspection.id}" alt="2" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
											{/if}
										</td>
									</tr>
								</s:if>
								{if utypeNew == 0}<!-- 地方高校 -->
									<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
										<tr class="table_tr4">
											<td>省厅意见</td>
											<td>
												{if midinspection.provinceAuditResult == 1}
													{if midinspection.provinceAuditStatus == 3}<span class="ico_txt5">不同意</span>
													{elseif midinspection.provinceAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
													{elseif midinspection.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
													{/if}
												{elseif midinspection.provinceAuditResult == 2}
													{if midinspection.provinceAuditStatus == 3}<span class="ico_txt1">同意</span>
													{elseif midinspection.provinceAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
													{elseif midinspection.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
													{/if}
												{elseif midinspection.provinceAuditResult == 0}<span class="ico_txt3">待审</span>
												{/if}
											</td>
											<td>
												<!-- 非导入数据、省厅账号、最近一次中检处在省厅审核级别 -->
												{if midinspection.createMode == 0 && midinspection_index == 0 && accountType == "PROVINCE" && midinspection.status == 4 && midinspection.finalAuditStatus != 3}
													{if endPassAlready != 1 && granted != null && granted.status == 1}
														{if midinspection.provinceAuditStatus == 0}
															<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1 j_addMidAuditPop" type="button" value="审核" />
																<input class="btn1 j_backMidApply" type="button" value="退回" />
															</sec:authorize>
														{elseif midinspection.provinceAuditStatus < 3}
															<span class="ico_txt2"><a midId="${midinspection.id}" alt="3" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
															{if (isPrincipal == 1 && midAuditorInfo[6] == belongId) || (isPrincipal == 0 && midAuditorInfo[5] == belongId)}
															<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_MODIFY'>
																<input class="btn1 j_modifyMidAuditPop" type="button" value="修改" />
															</sec:authorize>
															<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1 j_submitMidAudit" type="button" value="提交" />
																<input class="btn1 j_backMidApply" type="button" value="退回" />
															</sec:authorize>
															{/if}
														{/if}
													{/if}	
												{else}
													<span class="ico_txt2"><a midId="${midinspection.id}" alt="3" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
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
								<td colspan="3">最终中检结果</td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td>审核时间</td>
								<td width="30%">是否同意中检</td>
								<td width="300">查看详情</td>
							</tr>
							<tr class="table_tr4">
								<td>
									${midinspection.finalAuditDate}
								</td>
								<td>
									{if midinspection.finalAuditResult == 1}
										{if midinspection.finalAuditStatus == 3}<span class="ico_txt5">不同意</span>
										{elseif midinspection.finalAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
										{elseif midinspection.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif midinspection.finalAuditResult == 2}
										{if midinspection.finalAuditStatus == 3}<span class="ico_txt1">同意</span>
										{elseif midinspection.finalAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
										{elseif midinspection.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif midinspection.finalAuditResult == 0}<span class="ico_txt3">待审</span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、教育部账号、最近一次中检处在教育部审核级别 -->
									{if midinspection.createMode == 0 && midinspection_index == 0 && accountType == "MINISTRY" && midinspection.status == 5 && midinspection.finalAuditStatus != 3}
										{if endPassAlready != 1 && granted != null && granted.status == 1}
											{if midinspection.finalAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
													<input class="btn1 j_addMidAuditPop" type="button" value="审核" />
													<input class="btn1 j_backMidApply" type="button" value="退回" />
												</sec:authorize>
											{elseif midinspection.finalAuditStatus < 3}
												<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_VIEW'>
													<span class="ico_txt2"><a midId="${midinspection.id}" alt="4" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
												</sec:authorize>
												{if (isPrincipal == 1 && midAuditorInfo[8] == belongId) || (isPrincipal == 0 && midAuditorInfo[7] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_MODIFY'>
													<input class="btn1 j_modifyMidAuditPop" type="button" value="修改"/>
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_ADD'>
													<input class="btn1 j_submitMidAudit" type="button" value="提交" />
													<input class="btn1 j_backMidApply" type="button" value="退回" />
												</sec:authorize>
												{/if}
											{/if}
										{/if}	
									{else}
										<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLYAUDIT_VIEW'>
											<span class="ico_txt2"><a midId="${midinspection.id}" alt="4" class="j_viewMidAuditPop" style="cursor: pointer;">查看详情</a></span>
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</tbody>
					</s:if>
					<s:else><!-- 部级以下所有人员 -->
						{if midinspection.createMode == 1 || midinspection.createMode == 2 || midinspection.applicantSubmitStatus == 3}
							<thead>
								<tr class="head_title">
									<td colspan="2">最终中检结果</td>
								</tr>
							</thead>
							<tbody>
								<tr class="table_tr3">
									<td width="50%">审核时间</td>
									<td width="50%">是否同意中检</td>
								</tr>
								<tr class="table_tr4">
									<td>{if midinspection.finalAuditStatus == 3}${midinspection.finalAuditDate}{/if}</td>
									<td>
										{if midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 2}<span class="ico_txt1">同意</span>
										{elseif midinspection.finalAuditStatus == 3 && midinspection.finalAuditResult == 1}<span class="ico_txt5">不同意</span>
										{else}<span class="ico_txt3">待审</span>
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
								<td class="key" width="160">审核意见（反馈给负责人）：</span></td>
								<td class="value">{if midinspection.finalAuditOpinionFeedback != null && midinspection.finalAuditOpinionFeedback != ""}<pre>${midinspection.finalAuditOpinionFeedback}</pre>{/if}</td>
							</tr>
						</table>
					</div>
				</div>
			{/if}
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
			<!-- 对应中检成果 -->
			<div class="p_box_body">
			
					<div style="margin-top:10px;margin-bottom:5px;">附：中检成果
						{if midinspection_index == 0}<s:hidden id="midInspectionId" value="${midinspection.id}"/>{/if}
						<span id="mid_total">${midProdInfo[midinspection_index].productSize}</span>
						<span style="margin-right:20px;">项
							{if midProdInfo[midinspection_index].productSize!=0}(&nbsp;
								{if midProdInfo[midinspection_index].paperSize != null && midProdInfo[midinspection_index].paperSize != 0}论文${midProdInfo[midinspection_index].paperSize}项&nbsp;{/if}
								{if midProdInfo[midinspection_index].bookSize != null && midProdInfo[midinspection_index].bookSize != 0}著作${midProdInfo[midinspection_index].bookSize}项&nbsp;{/if}
								{if midProdInfo[midinspection_index].consultationSize != null && midProdInfo[midinspection_index].consultationSize != 0}研究咨询报告${midProdInfo[midinspection_index].consultationSize}项&nbsp;{/if}
								{if midProdInfo[midinspection_index].electronicSize != null && midProdInfo[midinspection_index].electronicSize != 0}电子出版物${midProdInfo[midinspection_index].electronicSize}项&nbsp;{/if}
								{if midProdInfo[midinspection_index].patentSize != null && midProdInfo[midinspection_index].patentSize != 0}专利${midProdInfo[midinspection_index].patentSize}项&nbsp;{/if}
								{if midProdInfo[midinspection_index].otherProductSize != null && midProdInfo[midinspection_index].otherProductSize != 0}其他成果${midProdInfo[midinspection_index].otherProductSize}项&nbsp;{/if}
								)
							{/if}
						</span>
						{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection_index == 0 && midinspection.createMode == 0}
							<s:hidden id="midInspectionId" value="${midinspection.id}"/>
							<input id="view_add_product" class="btn1" type="button" value="添加" name="mid_prod"/>
							<input id="view_mod_product" class="btn1" type="button" value="修改" name="mid_prod"/>
							<span class="text_red">&nbsp;（中检申请提交后不能再添加中检成果！）</span>
						{/if}
						<s:hidden id="canAuditMidProduct" value="${canAuditMidProduct}"/>
					</div>
					<table id="list_table_mid_${midinspection_index}" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr4">
							
								{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection_index == 0 && midinspection.createMode == 0}
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有账号"/></td>
									<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								{/if}
								
								<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
									{if midinspection_index == 0 && canAuditMidProduct}
										<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
											<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有账号"/></td>
											<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
										</s:if>
									{/if}
								</sec:authorize>
								
								<td width="40">序号</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td>成果名称</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55">成果形式</td>
			
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55">第一作者</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55">所属单位</td>
						
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="55">学科门类</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="95">是否标注教育部社科项目资助</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								<td width="35">初审结果</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								<td width="35">终审结果</td>
							</tr>
						</thead>
						<tbody>
							{for item in midProdInfo[midinspection_index].productList}
								<tr class="table_txt_tr3">
									{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection_index == 0 && midinspection.createMode == 0}
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
										{if item[2] == 'paper'}论文
										{elseif item[2] == 'book'}著作
										{elseif item[2] == 'consultation'}研究咨询报告
										{elseif item[2] == 'electronic'}电子出版物
										{elseif item[2] == 'patent'}专利
										{elseif item[2] == 'otherProduct'}其他成果
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
										{if item[8] == 1}是
										{else}否
										{/if}
									</td>
									<td></td>
									<td>
										{if item[9] == 0}待审
										{elseif item[9] == 1 }不同意
										{elseif item[9] == 2 }同意
										{/if}
									</td>
									<td></td>
									<td>
										{if item[10] == 0}待审
										{elseif item[10] == 1 }不同意
										{elseif item[10] == 2 }同意
										{/if}
									</td>
								</tr>
							{forelse}
								<tr class="table_txt_tr3">
									<td align="center">暂无符合条件的记录</td>
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
											<input id="view_aud_product" class="btn1" type="button" value="审核" name="mid_prod"/>
										</sec:authorize>
									{/if}
									{if isDirector == 1 && midinspection.applicantSubmitStatus != 3 && midinspection.createMode == 0}
										<s:hidden id="midInspectionId" value="${midinspection.id}"/>
										<input id="view_del_product" class="btn1" type="button" value="删除" name="mid_prod"/>
									{/if}
								{/if}
							</td>
						</tr>
					</table>
				
			</div>
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
		</div>
	{forelse}<!-- 无中检信息 -->
		<!-- 研究人员 -->
		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
			{if isDirector == 1 && endPassAlready != 1 && endPending != 1 && midPassAlready != 1 && midPending != 1 && midForbid != 1 && granted != null && granted.status == 1}
				<div id="applyMid">
					<div class="p_box_b_2"><a class="j_downloadMidTemplate" style="cursor: pointer;">下载中检申请书模板</a></div>
					<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_APPLY_ADD'>
						<div class="p_box_b_1"><input type="button" class="btn1 j_toAddMidApplyPop" value="申请" /></div>
					</sec:authorize>
				</div>
			{else}
				<div class="p_box_body">
					<div style="text-align:center;">暂无符合条件的记录</div>
				</div>	
			{/if}
		</s:if>
		<!-- 部级管理人员 -->
		<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
			<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_MIDINSPECTION_DATA_ADD'>
				{if midPassAlready==0 && midPending==0 && endPassAlready != 1 && granted != null && granted.status == 1 && midForbid != 1}
					<div><input class="btn1 j_addMidResultPop" type="button" value="添加" /><br/></div>
				{elseif granted != null && granted.status == 3}
					<div>项目已中止，不能录入数据</div>
				{elseif granted != null && granted.status == 4}
					<div>项目已撤项，不能录入数据</div>
				{elseif endPassAlready == 1}
					<div>结项已通过，不能录入数据</div>
				{elseif midPassAlready!=0}
					<div>中检已通过，不能录入数据</div>
				{elseif midPending!=0}
					<div>中检正在处理中，不能录入数据</div>
				{elseif midForbid == 1}
					<div>中检已截止，不能录入数据</div>
				{/if}
			</sec:authorize>
			<div class="p_box_body">
				<div style="text-align:center;">暂无符合条件的记录</div>
			</div>
		</s:elseif>
		<!-- 其他 -->
		<s:else>
			<div class="p_box_body">
				<div style="text-align:center;">暂无符合条件的记录</div>
			</div>
		</s:else>
	{/for}
</textarea>
<div id="view_mid" style="display:none;"></div>
<s:hidden id="midProAudAlr" value='1'/>
<s:form id="midForm" theme="simple">
	<s:hidden name="projectid" id="midFormProjectid" />
	<s:hidden id="midAuditStatus" name="midAuditStatus"/>
	<s:hidden id="midAuditOpinion" name="midAuditOpinion"/>
	<s:hidden id="midAuditOpinionFeedback" name="midAuditOpinionFeedback"/>
	<s:hidden id="midAuditResult" name="midAuditResult"/>
</s:form>
<s:form id="uploadMidFile_form" theme="simple">
	<s:hidden id="midId" name="midId"/>
	<s:hidden id="midFileId" name="midFileId" />
</s:form>