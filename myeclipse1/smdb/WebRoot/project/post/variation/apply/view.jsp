<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_var_template" style="display:none;">
	<s:hidden value="${varInfo[1]}" id="deadlineVar" />
	{for item in varList}
		{if item_index == 0}
			<!-- 研究人员申请 -->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if isDirector == 1 && item.finalAuditStatus == 3 && endPassAlready != 1 && varPending != 1 && granted != null && granted.status == 1}
					<div id="applyVar">
						<div class="p_box_b_2"><a href="javascript:void(0);" onclick="downloadVarTemplate();" >下载变更申请书模板</a></div>
						<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLY_ADD'>
							<div class="p_box_b_1"><input type="button" class="btn1 j_addVar" value="申请" /></div>
						</sec:authorize>
					</div>
				{/if}
			</s:if>
			<!-- 管理人员录入 -->
			<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_DATA_ADD'>
					{if item.finalAuditStatus == 3 && endPassAlready != 1 && varPending != 1 && granted!= null && granted.status ==1 && reviewPassAlready !=1}
						<div><input class="btn1 j_addVarResultPop" type="button" value="添加" /><br/></div>
					{elseif granted != null && granted.status == 3}
						<div>项目已中止，不能录入数据</div>
					{elseif granted != null && granted.status == 4}
						<div>项目已撤项，不能录入数据</div>
					{elseif granted != null && granted.status == 5}
						<div>项目已鉴定，不能录入数据</div>
					{elseif granted != null && granted.status == 1 && reviewPassAlready ==1}
						<div>项目已鉴定，不能录入数据</div>
					{elseif endPassAlready!=0}
						<div>项目已结项，不能录入数据</div>
					{elseif varPending!=0}
						<div>变更正在处理中，不能录入数据</div>
					{/if}
				</sec:authorize>
			</s:elseif>
		{/if}
		{var sn}
		{var sn = 0}
		<div class="p_box_t">
			<div class="p_box_t_t">第<span class="number" name="${varList.length}">${item_index}</span>次变更{if item_index==0}&nbsp;（当前）{/if}</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div>
			<div class="p_box_b">
				<div class="p_box_b_1">
					{if item.createMode == 0}
						{if item.applicantSubmitStatus == 3}提交时间：
						{else}保存时间：
						{/if}
						${item.applicantSubmitDate}
					{elseif item.createMode == 1 || item.createMode == 2}
						<span class="import_css">该数据为导入数据</span>
						{if item_index == 0 && accountType == "MINISTRY" && item.finalAuditStatus == 2 && endPassAlready != 1 && granted != null && granted.status == 1}
							<sec:authorize ifAnyGranted='ROLE_PROJECT_POST_VARIATION_DATA_MODIFY'>
								<input class="btn1 j_modifyVarResultPop" type="button" value="修改" itemIds="${item.id}"/>
							</sec:authorize>
							<sec:authorize ifAnyGranted='ROLE_PROJECT_POST_VARIATION_DATA_ADD'>
								<input class="btn1 j_submitVarResultPop" type="button" value="提交" itemIds="${item.id}"/>
							</sec:authorize>
						{/if}
					{else}
					{/if}
				</div>
				<div class="p_box_b_2">
				<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLY_VIEW'>
					{if item.file != null}
						<div style="float: right;"><img src="image/ico09.gif" />
							<a href="" id="${item.id}" class="download_post_4">下载变更申请书</a>
							({if varFileSizeList[item_index] != null}
							${varFileSizeList[item_index]}
							{else}附件不存在
							{/if})
						</div>
					{/if}
					{if item.finalAuditStatus==3 && granted != null}
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
					<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_DATA_UPLOAD'>
						<div style="float: right;"><img src="image/ico10.gif" /><a href="javascript:void(0);" onclick="uploadVarPop('${item.id}');">上传变更申请书</a>&nbsp;&nbsp;</div>
					</sec:authorize>
					</s:if>
					{/if}
				</sec:authorize>
				</div>
			</div>
			<div id="varInfo" class="p_box_body">
				<table id="list_post_variation${item_index}" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding" style="text-align:center;">
					<thead>
					<tr class="table_title_tr2">
						<td width="30">序号</td>
						<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
						<td width="150">变更事项</td>
						<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
						<td>变更前</td>
						<td width="2"><img src="image/table_line1.gif" width="2" height="24" /></td>
						<td>变更后</td>
					</tr>
					</thead>
					{if item.changeMember == 1}
						<tr>
							<td id="changeMem">{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>变更项目成员（含负责人）</td>
							<td></td>
							<td><s:hidden id="directors1${item_index}" name="${varRelIdNames[item_index][8]}" value="${varRelIdNames[item_index][0]}" cssClass="var_director"/></td>
							<td></td>
							<td><s:hidden id="directors2${item_index}" name="${varRelIdNames[item_index][9]}" value="${varRelIdNames[item_index][1]}" cssClass="var_director"/></td>
						</tr>
					{/if}
					{if item.changeAgency == 1}
						<tr>
							<td id="changeAgen">{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>变更项目管理机构</td>
							<td></td>
							<td>
								<a href="javascript:void(0);"  class="j_viewUniversity" univId="${varRelIdNames[item_index][2]}">${item.oldAgencyName}</a><br/>
								{if varRelIdNames[item_index][3]!=null}<a href="javascript:void(0);" class="j_viewDepartment" depId="${varRelIdNames[item_index][3]}">${item.oldDivisionName}</a>
								{elseif varRelIdNames[item_index][4]!=null}<a href="javascript:void(0);" class="j_viewInstitute" instId="${varRelIdNames[item_index][4]}">${item.oldDivisionName}</a>
								{else}${item.oldDivisionName}
								{/if}
								</td>
							<td></td>
							<td>
								<a href="javascript:void(0);" class="j_viewUniversity" univId="${varRelIdNames[item_index][5]}">${item.newAgencyName}</a><br/>
								{if varRelIdNames[item_index][6]!=null}<a href="javascript:void(0);" class="j_viewDepartment" depId="${varRelIdNames[item_index][6]}">${item.newDivisionName}</a>
								{elseif varRelIdNames[item_index][7]!=null}<a href="javascript:void(0);" class="j_viewInstitute" instId="${varRelIdNames[item_index][7]}">${item.newDivisionName}</a>
								{else}${item.newDivisionName}
								{/if}
							</td>
						</tr>
					{/if}
					{if item.changeProductType == 1}
						<tr>
							<td>{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>变更成果形式</td>
							<td></td>
							<td>
								{if item.oldProductType != null && item.oldProductType != ""}
									${item.oldProductType}{if item.oldProductTypeOther != null && item.oldProductTypeOther != ""}; ${item.oldProductTypeOther}{/if}
								{else}
									{if item.oldProductTypeOther != null && item.oldProductTypeOther != ""}${item.oldProductTypeOther}{/if}
								{/if}
							</td>
							<td></td>
							<td>
								{if item.newProductType != null && item.newProductType != ""}
									${item.newProductType}{if item.newProductTypeOther != null && item.newProductTypeOther != ""}; ${item.newProductTypeOther}{/if}
								{else}
									{if item.newProductTypeOther != null && item.newProductTypeOther != ""}${item.newProductTypeOther}{/if}
								{/if}
							</td>
						</tr>
					{/if}
					{if item.changeName == 1}
						<tr>
							<td>{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>变更项目名称</td>
							<td></td>
							<td>中文名称：${item.oldName}<br/>英文名称：${item.oldEnglishName}</td>
							<td></td>
							<td>中文名称：${item.newName}<br/>英文名称：${item.newEnglishName}</td>
						</tr>
					{/if}
					{if item.changeContent == 1}
						<tr>
							<td>{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>研究内容有重大调整</td>
							<td></td>
							<td>暂无信息</td>
							<td></td>
							<td>暂无信息</td>
						</tr>
					{/if}
					{if item.postponement == 1}
						<tr>
							<td>{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>延期
								{if item.postponementPlanFile != null}
									<a href="" id="${item.id}" class="download_post_7">[下载延期项目计划书]</a>
									({if postponementPlanFileSizeList[item_index] != null}
									${postponementPlanFileSizeList[item_index]}
									{else}附件不存在
									{/if})
								{/if}
							</td>
							<td></td>
							<td>${item.oldOnceDate}</td>
							<td></td>
							<td>${item.newOnceDate}</td>
						</tr>
					{/if}
					{if item.stop == 1}
						<tr>
							<td>{var sn=sn + 1}${sn}</td>
							<td></td>
							<td>自行中止项目</td>
							<td></td>
							<td>暂无信息</td>
							<td></td>
							<td>暂无信息</td>
						</tr>
					{/if}
					{if item.withdraw == 1}
						<tr>
							<td>{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>申请撤项</td>
							<td></td>
							<td>暂无信息</td>
							<td></td>
							<td>暂无信息</td>
						</tr>
					{/if}
					{if item.other == 1}
						<tr>
							<td>{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>其他</td>
							<td></td>
							<td>暂无信息</td>
							<td></td>
							<td>{if item.otherInfo == null || item.otherInfo == ""}
									暂无信息
								{else}
									<a href="javascript:void(0);" class="j_viewOther" itemIds="${item.id}">[查看详情]</a>
								{/if}
							</td>
						</tr>
					{/if}
					
					{if item.changeFee == 1}<!-- 变更经费预算 -->
						<tr>
							<td>{var sn = sn + 1}${sn}</td>
							<td></td>
							<td>变更经费预算</td>
							<td></td>
							<td>{if varRelIdNames[item_index][10]!=null}
									<a href="javascript:void(0);" class="j_viewFee" feeId="${varRelIdNames[item_index][10]}">查看详情</a>
								{else}
									暂无信息
								{/if}
							</td>
							
							<td></td>
							<td>{if varRelIdNames[item_index][11]!=null}
									<a href="javascript:void(0);" class="j_viewFee" feeId="${varRelIdNames[item_index][11]}">查看详情</a>
								{else}
									暂无信息
								{/if}
							</td>
						</tr>
					{/if}
					
				</table>
			</div>
			<div class="p_box_body">
				<div class="p_box_body_t">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<tr class="table_tr7">
							<td class="key" width="60">变更原因：</td>
							<td class="value" >${item.variationReason}</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="p_box_body">
				<div class="p_box_body_t">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<tr class="table_tr7">
							<td class="key" width="60">备注：</td>
							<td class="value" >{if item.note != null && item.note != ""}<pre>${item.note}</pre>{/if}</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- 研究人员 -->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if item_index == 0 && (item.applicantSubmitStatus == 1 || item.applicantSubmitStatus == 2)}<!-- 变更申请暂存或退回 -->	
					<div id="teaSubmitInfo" class="p_box_body">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr height="36">
								<td width="150">
									提交状态：{if item.applicantSubmitStatus == 1}退回{else}保存{/if}
								</td>
								<td align="right">
									{if isDirector == 1 && granted != null && granted.status == 1}
										{if endPassAlready != 1}
											<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLY_MODIFY'>
												<input type="button" class="btn1 j_modVar" itemIds="${item.id}" value="修改" />
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLY_ADD'>
												<input type="button" class="btn1 j_subVar" itemIds="${item.id}" value="提交" />
											</sec:authorize>
										{/if}
										<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLY_DELETE'>
											<input type="button" class="btn1 j_delVar" itemIds="${item.id}" value="删除" />
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</table>
					</div>
				{/if}
			</s:if>	
			<!-- 管理人员 -->
			<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 "><!-- 部门及以上管理人员 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_VIEW'>
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
									<td width="30%">是否同意变更</td>
									<td width="300">查看详情</td>
								</tr>
								<tr class="table_tr4">
									<td>院系或研究基地意见</td>
									<td>
										{if item.deptInstAuditResult == 1}
											{if item.deptInstAuditStatus == 3}<span class="ico_txt5">不同意</span>
											{elseif item.deptInstAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
											{elseif item.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif item.deptInstAuditResult == 2}
											{if item.deptInstAuditStatus == 3}<span class="ico_txt1">同意</span>
											{elseif item.deptInstAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
											{elseif item.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif item.deptInstAuditResult == 0}<span class="ico_txt3">待审</span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、部门账号、变更处在部门审核级别 -->
										{if item.createMode == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && item.status == 2 && item.finalAuditStatus != 3}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if item.deptInstAuditStatus == 0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
														<input type="button" class="btn1 j_addVarAuditPop" itemIds="${item.id}" value="审核" />
														<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
													</sec:authorize>
												{elseif item.deptInstAuditStatus < 3}
													<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=1>查看详情</a></span>
													{if (isPrincipal == 1 && (varAuditorInfo[1] == belongId || varAuditorInfo[2] == belongId)) || (isPrincipal == 0 && varAuditorInfo[0] == belongId)}
													<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_MODIFY'>
														<input type="button" class="btn1 j_modVarAuditPop" itemIds="${item.id}" value="修改" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
														<input type="button" class="btn1 j_subVarAudit" itemIds="${item.id}" value="提交" />
														<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
													</sec:authorize>
													{/if}
												{/if}
											{/if}	
										{else}
											<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=1>查看详情</a></span>
										{/if}
									</td>
								</tr>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0"><!-- 校级及以上管理人员 -->
									<tr class="table_tr4">
										<td>高校意见</td>
										<td>
											{if item.universityAuditResult == 1}
												{if item.universityAuditStatus == 3}<span class="ico_txt5">不同意</span>
												{elseif item.universityAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
												{elseif item.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
												{/if}
											{elseif item.universityAuditResult == 2}
												{if item.universityAuditStatus == 3}<span class="ico_txt1">同意</span>
												{elseif item.universityAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
												{elseif item.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
												{/if}
											{elseif item.universityAuditResult == 0}<span class="ico_txt3">待审</span>
											{/if}
										</td>
										<td>
											<!-- 非导入数据、高校账号、变更处在高校审核级别 -->
											{if item.createMode == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && item.status == 3 && item.finalAuditStatus != 3}
												{if endPassAlready != 1 && granted != null && granted.status == 1}
													{if item.universityAuditStatus == 0}
														<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
															<input type="button" class="btn1 j_addVarAuditPop" itemIds="${item.id}" value="审核" />
															<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
														</sec:authorize>
													{elseif item.universityAuditStatus < 3}
														<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=2>查看详情</a></span>
														{if (isPrincipal == 1 && varAuditorInfo[4] == belongId) || (isPrincipal == 0 && varAuditorInfo[3] == belongId)}
														<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_MODIFY'>
															<input type="button" class="btn1 j_modVarAuditPop" itemIds="${item.id}" value="修改" />
														</sec:authorize>
														<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
															<input type="button" class="btn1 j_subVarAudit" itemIds="${item.id}" value="提交" />
															<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
														</sec:authorize>
														{/if}
													{/if}
												{/if}	
											{else}
												<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=2>查看详情</a></span>
											{/if}
										</td>
									</tr>
								</s:if>
								{if utypeNew == 0}<!-- 地方高校 -->
									<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
										<tr class="table_tr4">
											<td>省厅意见</td>
											<td>
												{if item.provinceAuditResult == 1}
													{if item.provinceAuditStatus == 3}<span class="ico_txt5">不同意</span>
													{elseif item.provinceAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
													{elseif item.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
													{/if}
												{elseif item.provinceAuditResult == 2}
													{if item.provinceAuditStatus == 3}<span class="ico_txt1">同意</span>
													{elseif item.provinceAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
													{elseif item.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
													{/if}
												{elseif item.provinceAuditResult == 0}<span class="ico_txt3">待审</span>
												{/if}
											</td>
											<td>
												<!-- 非导入数据、省厅账号、变更处在省厅审核级别 -->
												{if item.createMode == 0 && accountType == "PROVINCE" && item.status == 4 && item.finalAuditStatus != 3}
													{if endPassAlready != 1 && granted != null && granted.status == 1}
														{if item.provinceAuditStatus == 0}
															<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
																<input type="button" class="btn1 j_addVarAuditPop" itemIds="${item.id}" value="审核" />
																<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
															</sec:authorize>
														{elseif item.provinceAuditStatus < 3}
															<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=3>查看详情</a></span>
															{if (isPrincipal == 1 && varAuditorInfo[6] == belongId) || (isPrincipal == 0 && varAuditorInfo[5] == belongId)}
															<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_MODIFY'>
																<input type="button" class="btn1 j_modVarAuditPop" itemIds="${item.id}" value="修改" />
															</sec:authorize>
															<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
																<input type="button" class="btn1 j_subVarAudit" itemIds="${item.id}" value="提交" />
																<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
															</sec:authorize>
															{/if}
														{/if}
													{/if}	
												{else}
													<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=3>查看详情</a></span>
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
								<td colspan="3">最终变更结果</td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td>审核时间</td>
								<td width="30%">是否同意变更</td>
								<td width="300">查看详情</td>
							</tr>
							<tr class="table_tr4">
								<td>${item.finalAuditDate}</td>
								<td>
									{if item.finalAuditResult == 1}
										{if item.finalAuditStatus == 3}<span class="ico_txt5">不同意</span>
										{elseif item.finalAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
										{elseif item.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif item.finalAuditResult == 2}
										{if item.finalAuditStatus == 3}<span class="ico_txt1">同意</span>
										{elseif item.finalAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
										{elseif item.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif item.finalAuditResult == 0}<span class="ico_txt3">待审</span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、教育部账号、变更处在教育部审核级别 -->
									{if item.createMode == 0 && accountType == "MINISTRY" && item.status == 5 && item.finalAuditStatus != 3}
										{if endPassAlready != 1 && granted != null && granted.status == 1}
											{if item.finalAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
													<input type="button" class="btn1 j_addVarAuditPop" itemIds="${item.id}" value="审核" />
													<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
												</sec:authorize>
											{elseif item.finalAuditStatus < 3}
												<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_VIEW'>
													<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=4>查看详情</a></span>
												</sec:authorize>
												{if (isPrincipal == 1 && varAuditorInfo[8] == belongId) || (isPrincipal == 0 && varAuditorInfo[7] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_MODIFY'>
													<input type="button" class="btn1 j_modVarAuditPop" itemIds="${item.id}" value="修改" />
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_ADD'>
													<input type="button" class="btn1 j_subVarAudit" itemIds="${item.id}" value="提交" />
													<input type="button" class="btn1 j_backVarAudit" itemIds="${item.id}" value="退回" />
												</sec:authorize>
												{/if}
											{/if}
										{/if}	
									{else}
										<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLYAUDIT_VIEW'>
											<span class="ico_txt2"><a href="javascript:void(0);" class="j_viewVarAuditPop" itemIds="${item.id}" auditViewFlag=4>查看详情</a></span>
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</tbody>
					</s:if>
					<s:else><!-- 部级以下所有人员 -->
						{if item.createMode == 1 || item.createMode == 2 || item.applicantSubmitStatus == 3}
							<thead>
								<tr class="head_title">
									<td colspan="2">最终变更结果</td>
								</tr>
							</thead>
							<tbody>
								<tr class="table_tr3">
									<td width="50%">审核时间</td>
									<td width="50%">是否同意变更</td>
								</tr>
								<tr class="table_tr4">
									<td>{if item.finalAuditStatus == 3}${item.finalAuditDate}{/if}</td>
									<td>
										{if item.finalAuditStatus == 3 && item.finalAuditResult == 2}<span class="ico_txt1">同意</span>
										{elseif item.finalAuditStatus == 3 && item.finalAuditResult == 1}<span class="ico_txt5">不同意</span>
										{else}<span class="ico_txt3">待审</span>
										{/if}
									</td>
								</tr>
							</tbody>
						{/if}
					</s:else>
				</table>
			</div>
			{if item.finalAuditStatus == 3}
				<div class="p_box_body">
					<div class="p_box_body_t">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr class="table_tr7">
								<td class="key" width="160">审核意见（反馈给负责人）：</td>
								<td class="value">{if item.finalAuditOpinionFeedback != null && item.finalAuditOpinionFeedback != ""}<pre>${item.finalAuditOpinionFeedback}</pre>{/if}</td>
							</tr>
						</table>
					</div>
				</div>
			{/if}
		</div>
	{forelse}
		<!-- 研究人员 -->
		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
			{if isDirector == 1 && endPassAlready != 1 && varPending != 1 && granted != null && granted.status == 1}
				<div id="applyVar">
					<div class="p_box_b_2"><a href="javascript:void(0);" onclick="downloadVarTemplate();" >下载变更申请书模板</a></div>
					<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_APPLY_ADD'>
						<div class="p_box_b_1"><input type="button" class="btn1 j_addVar" value="申请" /></div>
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
			<sec:authorize ifAllGranted='ROLE_PROJECT_POST_VARIATION_DATA_ADD'>	
					{if endPassAlready != 1 && varPending != 1 && granted != null && granted.status == 1 && reviewPassAlready !=1}
						<div><input class="btn1 j_addVarResultPop" type="button" value="添加" /><br/></div>
					{elseif granted != null && granted.status == 3}
						<div>项目已中止，不能录入数据</div>
					{elseif granted != null && granted.status == 4}
						<div>项目已撤项，不能录入数据</div>
					{elseif granted != null && granted.status == 5}
						<div>项目已鉴定，不能录入数据</div>
					{elseif granted != null && granted.status == 1 && reviewPassAlready ==1}
						<div>项目已鉴定，不能录入数据</div>
					{elseif endPassAlready!=0}
						<div>项目已结项，不能录入数据</div>
					{elseif varPending!=0}
						<div>变更正在处理中，不能录入数据</div>
					{/if}
				</div>
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
<div id="view_var" style="display:none;"></div>
<s:form id="var_form">
</s:form>
<s:form id="var_audit_form" theme="simple">
	<s:hidden id="varAuditResult" name="varAuditResult"/>
	<s:hidden id="varAuditOpinion" name="varAuditOpinion"/>
	<s:hidden id="varAuditOpinionFeedback" name="varAuditOpinionFeedback"/>
	<s:hidden id="varAuditStatus" name="varAuditStatus"/>
	<s:hidden id="varAuditSelectIssue" name="varAuditSelectIssue"/>
	<s:hidden id="varAuditDate" name="varAuditDate"/>
</s:form>
<s:form id="uploadVarFile_form" theme="simple">
	<s:hidden id="variId" name="variId"/>
	<s:hidden id="varFileId" name="varFileId" />
</s:form>