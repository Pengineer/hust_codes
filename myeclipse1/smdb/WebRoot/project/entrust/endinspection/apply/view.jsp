<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_end_template" style="display:none;">
	<s:hidden value="${endInfo[1]}" id="deadlineEnd" />
	<s:hidden value="${endRevInfo[1]}" id="deadlineEndRev" />
	<s:hidden name="endStatus" id="endStatus" value="%{projectService.getBusinessStatus('053', entityId)}"/>
	{for endinspection in endList}
		{if endinspection_index == 0}<!-- 最近一次结项 -->
			<!-- 研究人员申请 -->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if isDirector == 1 && endinspection.finalAuditResultEnd == 1 && endinspection.finalAuditStatus == 3 && endPassAlready != 1 && endPending != 1 && varPending==0 && granted != null && granted.status == 1}
					<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_ADD'>
						<div class="p_box_b_2">
							<a href="javascript:void(0);" class="j_downloadEndTemplate"  >下载结项申请书模板</a>
						</div>
						<div class="p_box_b_1">
							<input type="button" class="btn1 j_toAddEndApplyPop"  value="申请" />
						</div>
					</sec:authorize>
				{/if}
			</s:if>
			<!-- 管理人员录入 -->
			<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
				<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_ADD'>
					{if endinspection.finalAuditResultEnd==1 && endinspection.finalAuditStatus==3 && endPassAlready==0 && endPending==0 && varPending==0 && granted != null && granted.status == 1}
						<div><input class="btn1 j_addEndResultPop" type="button" value="添加" /><br/></div>
					{elseif granted != null && granted.status == 3}
						<div>项目已中止，不能录入数据</div>
					{elseif granted != null && granted.status == 4}
						<div>项目已撤项，不能录入数据</div>
					{elseif endPassAlready!=0}
						<div>结项已通过，不能录入数据</div>
					{elseif varPending!=0}
						<div>变更正在处理中，不能录入数据</div>
					{elseif endPending!=0}
						<div>结项正在处理，不能录入数据</div>
					{/if}
				</sec:authorize>
			</s:elseif>
		{/if}
		<div class="p_box_t">
			<div class="p_box_t_t">第<span class="number" name="${endList.length}">${endinspection_index}</span>次结项{if endinspection_index==0}&nbsp;（当前）{/if}</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div>
			<div class="p_box_b">
				<div class="p_box_b_1">
					{if endinspection.createMode == 0 }
						<span>{if endinspection.applicantSubmitStatus != 3}保存时间{else}提交时间{/if}：${endinspection.applicantSubmitDate}</span>
						<!-- 校级及以上管理人员最近一次结项走流程录入评审结果 -->
						{if endinspection_index == 0 && endinspection.finalAuditStatus != 3}
							{if reviewflag == -1 && ((accountType == "MINISTRY" && endinspection.status > 5) || (accountType == "PROVINCE" && endinspection.status > 4) || ((accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && endinspection.status > 3))}
								<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_ADD'>
									<span><input class="btn2 j_addEndReviewResultPop" endId="${endinspection.id}" type="button" value="录入鉴定" /></span>
									<span><input class="btn2 j_addEndReviewExpert" type="button" endId = "${endinspection.id}" value="选择专家" /></span>
								</sec:authorize>
							{elseif (reviewflag == 22 && accountType == "MINISTRY" && endinspection.status > 5) || (reviewflag == 32 && accountType == "PROVINCE" && endinspection.status > 4) || (reviewflag == 42 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && endinspection.status > 3)}
								<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_MODIFY'>
									<span><input class="btn2 j_modifyEndReviewResultPop" endId="${endinspection.id}" type="button" value="修改鉴定" /></span>
								</sec:authorize>
								<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_ADD'>
									<span><input class="btn2 j_submitEndReviewResult" endId="${endinspection.id}" type="button" value="提交鉴定" /></span>
								</sec:authorize>
							{/if}
						{/if}
					{elseif endinspection.createMode == 1 || endinspection.createMode == 2}
						<span class="import_css">该数据为导入数据</span>
						<!-- 部级最近一次录入结项结果 -->
						{if endinspection_index == 0 && endinspection.finalAuditStatus==2 && granted != null && granted.status == 1}
							{if endPassAlready == 0 && accountType == "MINISTRY"}
								<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_MODIFY'>
									<input class="btn1 j_modifyEndResultPop" modifyFlags="0" type="button" value="修改" />
								</sec:authorize>
								{if varPending==0}
									<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_ADD'>
										<input class="btn1 j_submitEndResult" type="button" value="提交" />
									</sec:authorize>
								{/if}
							{/if}
						{/if}
					{else}
					{/if}
				</div>
				<div class="p_box_b_2">
				<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_VIEW'>
					{if endinspection.file != null}
						<div style="float: right;"><img src="image/ico09.gif" />
							<a href="" id="${endinspection.id}" class="download_entrust_3">下载结项申请书</a>
							({if endiFileSizeList[endinspection_index] != null}
							${endiFileSizeList[endinspection_index]}
							{else}附件不存在
							{/if})
						</div>
					{/if}
					{if endinspection.finalAuditStatus==3 && granted != null}
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
					<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_UPLOAD'>
						<div style="float: right;"><img src="image/ico10.gif" /><a href="javascript:void(0);" class="j_uploadEndPop" endId="${endinspection.id}" >上传结项申请书</a>&nbsp;&nbsp;</div>
					</sec:authorize>
					</s:if>
					{/if}
				</sec:authorize>
				</div>
			</div>
			<!-- 结项基本信息 -->
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr7">
						<td width="120" class="key">是否申请免鉴定：</td>
						<td class="value">
							{if endinspection.isApplyNoevaluation == 1}是
							{elseif endinspection.isApplyNoevaluation == 0}否
							{/if}
						</td>
						<td width="120" class="key">是否申请优秀成果：</td>
						<td class="value">
							{if endinspection.isApplyExcellent == 1}是
							{elseif endinspection.isApplyExcellent == 0}否
							{/if}
						</td>
					</tr>
					{if endinspection.createMode == 1 || endinspection.createMode == 2}
						<tr class="table_tr7">
							<td width="150" class="key">结项成果数量：<br/>（成果形式/总数/合格数）</td>
							<td colspan="5" style="text-align:left">${endinspection.importedProductInfo}</td>
						</tr>
						<tr class="table_tr7">
							<td width="150" class="key">主要参加人：</td>
							<td colspan="5" style="text-align:left">
								{if endinspection.memberName == null || endinspection.memberName == ""}无
								{else}${endinspection.memberName}
								{/if}
							</td>
						</tr>
					{/if}
					<tr class="table_tr7">
						<td width="150" class="key">备注：</td>
						<td colspan="5" style="text-align:left">{if endinspection.note != null && endinspection.note != ""}<pre>${endinspection.note}</pre>{/if}</td>
					</tr>
				</table>
			</div>
			
			<%--    经费明细		--%>
			{if projectEndFees[endinspection_index].feeFlag == 1 }
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr class="table_tr3">
						<td width="16%" class="key">批准经费</td>
						<td width="16%" class="value">${projectEndFees[endinspection_index].approveFee}</td>
						<td width="16%" class="key">已拨经费</td>
						<td width="16%" class="value">${projectEndFees[endinspection_index].fundedFee}</td>
						<td width="16%" class="key">未拨经费</td>
						<td width="16%" class="value">${projectEndFees[endinspection_index].toFundFee}</td>
					</tr>
					<tr class="table_tr3">
						<td class="key" colspan="6">结项经费结算明细</td>
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
							<td class="value">${projectEndFees[endinspection_index].bookFee}</td>
							<td class="value">${projectEndFees[endinspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">数据采集费</td>
							<td class="value">${projectFeeGranted.dataFee}</td>
							<td class="value">${projectEndFees[endinspection_index].dataFee}</td>
							<td class="value">${projectEndFees[endinspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">调研差旅费</td>
							<td class="value">${projectFeeGranted.travelFee}</td>
							<td class="value">${projectEndFees[endinspection_index].travelFee}</td>
							<td class="value">${projectEndFees[endinspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">会议费</td>
							<td class="value">${projectFeeGranted.conferenceFee}</td>
							<td class="value">${projectEndFees[endinspection_index].conferenceFee}</td>
							<td class="value">${projectEndFees[endinspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">国际合作交流费</td>
							<td class="value">${projectFeeGranted.internationalFee}</td>
							<td class="value">${projectEndFees[endinspection_index].internationalFee}</td>
							<td class="value">${projectEndFees[endinspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">设备购置和使用费</td>
							<td class="value">${projectFeeGranted.deviceFee}</td>
							<td class="value">${projectEndFees[endinspection_index].deviceFee}</td>
							<td class="value">${projectEndFees[endinspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">专家咨询和评审费</td>
							<td class="value">${projectFeeGranted.consultationFee}</td>
							<td class="value">${projectEndFees[endinspection_index].consultationFee}</td>
							<td class="value">${projectEndFees[endinspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">助研津贴和劳务费</td>
							<td class="value">${projectFeeGranted.laborFee}</td>
							<td class="value">${projectEndFees[endinspection_index].laborFee}</td>
							<td class="value">${projectEndFees[endinspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">印刷和出版费</td>
							<td class="value">${projectFeeGranted.printFee}</td>
							<td class="value">${projectEndFees[endinspection_index].printFee}</td>
							<td class="value">${projectEndFees[endinspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">间接费用</td>
							<td class="value">${projectFeeGranted.indirectFee}</td>
							<td class="value">${projectEndFees[endinspection_index].indirectFee}</td>
							<td class="value">${projectEndFees[endinspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">其他</td>
							<td class="value">${projectFeeGranted.otherFee}</td>
							<td class="value">${projectEndFees[endinspection_index].otherFee}</td>
							<td class="value">${projectEndFees[endinspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">合计</td>
							<td class="value">${projectFeeGranted.totalFee}</td>
							<td class="value" colspan="2">${projectEndFees[endinspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费</td>
							<td class="value" colspan="3">${projectEndFees[endinspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费支出说明</td>
							<td class="value" colspan="3">${projectEndFees[endinspection_index].feeNote}</td>
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
							<td class="value">${projectEndFees[endinspection_index].bookFee}</td>
							<td class="value">${projectEndFees[endinspection_index].bookNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">数据采集费</td>
							<td class="value">${projectEndFees[endinspection_index].dataFee}</td>
							<td class="value">${projectEndFees[endinspection_index].dataNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">调研差旅费</td>
							<td class="value">${projectEndFees[endinspection_index].travelFee}</td>
							<td class="value">${projectEndFees[endinspection_index].travelNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">会议费</td>
							<td class="value">${projectEndFees[endinspection_index].conferenceFee}</td>
							<td class="value">${projectEndFees[endinspection_index].conferenceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">国际合作交流费</td>
							<td class="value">${projectEndFees[endinspection_index].internationalFee}</td>
							<td class="value">${projectEndFees[endinspection_index].internationalNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">设备购置和使用费</td>
							<td class="value">${projectEndFees[endinspection_index].deviceFee}</td>
							<td class="value">${projectEndFees[endinspection_index].deviceNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">专家咨询和评审费</td>
							<td class="value">${projectEndFees[endinspection_index].consultationFee}</td>
							<td class="value">${projectEndFees[endinspection_index].consultationNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">助研津贴和劳务费</td>
							<td class="value">${projectEndFees[endinspection_index].laborFee}</td>
							<td class="value">${projectEndFees[endinspection_index].laborNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">印刷和出版费</td>
							<td class="value">${projectEndFees[endinspection_index].printFee}</td>
							<td class="value">${projectEndFees[endinspection_index].printNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">间接费用</td>
							<td class="value">${projectEndFees[endinspection_index].indirectFee}</td>
							<td class="value">${projectEndFees[endinspection_index].indirectNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">其他</td>
							<td class="value">${projectEndFees[endinspection_index].otherFee}</td>
							<td class="value">${projectEndFees[endinspection_index].otherNote}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">合计</td>
							<td class="value" colspan="2">${projectEndFees[endinspection_index].totalFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费</td>
							<td class="value" colspan="2">${projectEndFees[endinspection_index].surplusFee}</td>
						</tr>
						<tr class="table_tr4">
							<td class="key">结余经费支出说明</td>
							<td class="value" colspan="2">${projectEndFees[endinspection_index].feeNote}</td>
						</tr>
					</tbody>
					{/if}
				</table>
			</div>
			{/if}
			
			<!-- 研究数据包信息 -->
			{if projectDatas[endinspection_index] != null}
				<div class="p_box_body">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<thead>
							<tr class="head_title">
								<td colspan="8">研究数据信息</td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr7">
								<td width="60" class="key">关键字：</td>
								<td class="value">${projectDatas[endinspection_index].keywords}</td>
								<td width="70" class="key">提交时间：</td>
								<td class="value">${projectDatas[endinspection_index].submitDate}</td>
								<td colspan="4" style="text-align:center">
									<img style="vertical-align:middle;" src="image/ico09.gif" /><span>
									<a href="" id="${projectDatas[endinspection_index].id}" name="${projectDatas[endinspection_index].file}" class="download_entrust_5">下载研究数据包</span></a>
									({if resFileSizeList[endinspection_index] != null}
									${resFileSizeList[endinspection_index]}
									{else}附件不存在
									{/if})
								</td>
							</tr>
							<tr class="table_tr7">
								<td width="60" class="key">调查方法：</td>
								<td class="value">${projectDatas[endinspection_index].surveyMethod}</td>
								<td width="70" class="key">调查范围：</td>
								<td width="105" class="value">${projectDatas[endinspection_index].surveyField}</td>
								<td width="90" class="key">调查起始时间：</td>
								<td width="65" class="value">${projectDatas[endinspection_index].startDate}</td>
								<td width="90" class="key">调查终止时间：</td>
								<td width="65" class="value">${projectDatas[endinspection_index].endDate}</td>
							</tr>
							<tr class="table_tr7">
								<td width="60" class="key">摘要：</td>
								<td colspan="7" class="value">
									{if projectDatas[endinspection_index].summary != null && projectDatas[endinspection_index].summary != ""}
										<pre>${projectDatas[endinspection_index].summary}</pre>
									{/if}
								</td>
							</tr>
							<tr class="table_tr7">
								<td width="60" class="key">简介：</td>
								<td colspan="7" class="value">
									{if projectDatas[endinspection_index].introduction != null && projectDatas[endinspection_index].introduction != ""}
										<pre>${projectDatas[endinspection_index].introduction}</pre>
									{/if}
								</td>
							</tr>
							<tr class="table_tr7">
								<td width="60" class="key">备注：</td>
								<td colspan="7" class="value">								<td colspan="7" class="value">
									{if projectDatas[endinspection_index].note != null && projectDatas[endinspection_index].note != ""}
										<pre>${projectDatas[endinspection_index].note}</pre>
									{/if}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			{/if}
			<!-- 研究人员申请相关操作-->
			<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
				{if endinspection_index == 0 && (endinspection.applicantSubmitStatus == 1 || endinspection.applicantSubmitStatus == 2)}<!-- 结项申请暂存或退回 -->	
					<div class="p_box_body" id="addProductEnd">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr height="36">
								<td width="150">
									提交状态：{if endinspection.applicantSubmitStatus == 1}退回{else}保存{/if}
								</td>
								<td align="right">
									{if isDirector == 1 && granted != null && granted.status == 1}
										{if endPassAlready != 1}
											<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_MODIFY">
												<input type="button" value="修改" class="btn1 j_toModifyEndApplyPop"/>&nbsp;
											</sec:authorize>
											{if varPending == 0}
												<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_ADD">
													<input type="button" value="提交" class="btn1 j_submitEndApply"/>&nbsp;
												</sec:authorize>
											{/if}
										{/if}
										<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_DELETE">
											<input type="button" value="删除" class="btn1 j_deleteEndApply"/>
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
				<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_VIEW'>
					<div class="p_box_body">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<thead>
								<tr class="head_title">
									{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}
										<td colspan="6">各级审核情况</td>
									{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}
										<td colspan="5">各级审核情况</td>
									{else}
										<td colspan="4">各级审核情况</td>
									{/if}
								</tr>
							</thead>
							<tbody>
								<tr class="table_tr3">
									<td width="120">各级管理部门意见</td>
									<td>是否同意结项</td>
									{if endinspection.isApplyNoevaluation == 1}
										<td>免鉴定成果</td>
									{/if}
									{if endinspection.isApplyExcellent == 1}
										<td>优秀成果结果</td>
									{/if}
									<td>提交状态</td>
									<td width="250">查看详情</td>
								</tr>
								<!-- 部门审核信息 -->
								<tr class="table_tr4">
									<td>院系或研究基地意见</td>
									<td>
										{if endinspection.deptInstResultEnd == 0}<span class="ico_txt3">待审</span>
										{elseif endinspection.deptInstResultEnd == 1}<span class="ico_txt5">不同意</span>
										{elseif endinspection.deptInstResultEnd == 2}<span class="ico_txt1">同意</span>
										{/if}
									</td>
									{if endinspection.isApplyNoevaluation == 1}
										<td>
											{if endinspection.deptInstResultNoevaluation == 0}<span class="ico_txt3">待审</span>
											{elseif endinspection.deptInstResultNoevaluation == 1}<span class="ico_txt5">不同意</span>
											{elseif endinspection.deptInstResultNoevaluation == 2}<span class="ico_txt1">同意</span>
											{/if}
										</td>
									{/if}
									{if endinspection.isApplyExcellent == 1}
										<td>
											{if endinspection.deptInstResultExcellent == 0}<span class="ico_txt3">待审</span>
											{elseif endinspection.deptInstResultExcellent == 1}<span class="ico_txt5">不同意</span>
											{elseif endinspection.deptInstResultExcellent == 2}<span class="ico_txt1">同意</span>
											{/if}
										</td>
									{/if}
									<td>
										{if endinspection.deptInstAuditStatus == 3}<span>提交</span>
										{elseif endinspection.deptInstAuditStatus == 2}<span>暂存</span>
										{elseif endinspection.deptInstAuditStatus == 1}<span>退回</span>
										{elseif endinspection.deptInstAuditStatus == 0}<span></span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、部门账号、最近一次结项处在部门审核级别 -->
										{if endinspection.createMode == 0 && endinspection_index == 0 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE") && endinspection.status == 2 && endinspection.finalAuditStatus != 3}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if endinspection.deptInstAuditStatus == 0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_addEndAuditPop" type="button" value="审核" />
														<input class="btn1 j_backEndApply" type="button" value="退回" />
													</sec:authorize>
												{elseif endinspection.deptInstAuditStatus < 3}
													<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="1" style="cursor: pointer;">查看详情</a></span>
													{if (isPrincipal == 1 && (endAuditorInfo[1] == belongId || endAuditorInfo[2] == belongId)) || (isPrincipal == 0 && endAuditorInfo[0] == belongId)}
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_MODIFY'>
														<input class="btn1 j_modifyEndAuditPop" type="button" value="修改" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
														<input class="btn1 j_submitEndAudit" type="button" value="提交" />
														<input class="btn1 j_backEndApply" type="button" value="退回" />
													</sec:authorize>
													{/if}
												{/if}
											{/if}
										{else}
											<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="1" style="cursor: pointer;">查看详情</a></span>
										{/if}
									</td>
								</tr>
								<!-- 校级及以上管理人员 -->
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0">
									<tr class="table_tr4">
										<td>高校意见</td>
										<td>
											{if endinspection.universityResultEnd == 0}<span class="ico_txt3">待审</span>
											{elseif endinspection.universityResultEnd == 1}<span class="ico_txt5">不同意</span>
											{elseif endinspection.universityResultEnd == 2}<span class="ico_txt1">同意</span>
											{/if}
										</td>
										{if endinspection.isApplyNoevaluation == 1}
											<td>
												{if endinspection.universityResultNoevaluation == 0}<span class="ico_txt3">待审</span>
												{elseif endinspection.universityResultNoevaluation == 1}<span class="ico_txt5">不同意</span>
												{elseif endinspection.universityResultNoevaluation == 2}<span class="ico_txt1">同意</span>
												{/if}
											</td>
										{/if}
										{if endinspection.isApplyExcellent == 1}
											<td>
												{if endinspection.universityResultExcellent == 0}<span class="ico_txt3">待审</span>
												{elseif endinspection.universityResultExcellent == 1}<span class="ico_txt5">不同意</span>
												{elseif endinspection.universityResultExcellent == 2}<span class="ico_txt1">同意</span>
												{/if}
											</td>
										{/if}
										<td>
											{if endinspection.universityAuditStatus == 3}<span>提交</span>
											{elseif endinspection.universityAuditStatus == 2}<span>暂存</span>
											{elseif endinspection.universityAuditStatus == 1}<span>退回</span>
											{elseif endinspection.universityAuditStatus == 0}<span></span>
											{/if}
										</td>
										<td>
											<!-- 非导入数据、高校账号、最近一次结项处在高校审核级别 -->
											{if endinspection.createMode == 0 && endinspection_index == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && endinspection.status == 3 && endinspection.finalAuditStatus != 3}
												{if endPassAlready != 1 && granted != null && granted.status == 1}
													{if endinspection.universityAuditStatus == 0}
														<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_addEndAuditPop" type="button" value="审核" />
															<input class="btn1 j_backEndApply" type="button" value="退回" />
														</sec:authorize>
													{elseif endinspection.universityAuditStatus < 3}
														<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="2" style="cursor: pointer;">查看详情</a></span>
														{if (isPrincipal == 1 && endAuditorInfo[4] == belongId) || (isPrincipal == 0 && endAuditorInfo[3] == belongId)}
														<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_MODIFY'>
															<input class="btn1 j_modifyEndAuditPop" type="button" value="修改" />
														</sec:authorize>
														<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_submitEndAudit" type="button" value="提交" />
															<input class="btn1 j_backEndApply" type="button" value="退回" />
														</sec:authorize>
														{/if}
													{/if}
												{/if}
											{else}
												<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="2" style="cursor: pointer;">查看详情</a></span>
											{/if}
										</td>
									</tr>
								</s:if>
								{if utypeNew == 0}<!-- 地方高校 -->
									<!-- 省厅及以上管理人员 -->
									<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0">
										<tr class="table_tr4">
											<td>省厅意见</td>
											<td>
												{if endinspection.provinceResultEnd == 0}<span class="ico_txt3">待审</span>
												{elseif endinspection.provinceResultEnd == 1}<span class="ico_txt5">不同意</span>
												{elseif endinspection.provinceResultEnd == 2}<span class="ico_txt1">同意</span>
												{/if}
											</td>
											{if endinspection.isApplyNoevaluation == 1}
												<td>
													{if endinspection.provinceResultNoevaluation == 0}<span class="ico_txt3">待审</span>
													{elseif endinspection.provinceResultNoevaluation == 1}<span class="ico_txt5">不同意</span>
													{elseif endinspection.provinceResultNoevaluation == 2}<span class="ico_txt1">同意</span>
													{/if}
												</td>
											{/if}
											{if endinspection.isApplyExcellent == 1}
												<td>
													{if endinspection.provinceResultExcellent == 0}<span class="ico_txt3">待审</span>
													{elseif endinspection.provinceResultExcellent == 1}<span class="ico_txt5">不同意</span>
													{elseif endinspection.provinceResultExcellent == 2}<span class="ico_txt1">同意</span>
													{/if}
												</td>
											{/if}
											<td>
												{if endinspection.provinceAuditStatus == 3}<span>提交</span>
												{elseif endinspection.provinceAuditStatus == 2}<span>暂存</span>
												{elseif endinspection.provinceAuditStatus == 1}<span>退回</span>
												{elseif endinspection.provinceAuditStatus == 0}<span></span>
												{/if}
											</td>
											<td>
												<!-- 非导入数据、省厅账号、最近一次结项处在省厅审核级别 -->
												{if endinspection.createMode == 0 && endinspection_index == 0 && accountType == "PROVINCE" && endinspection.status == 4 && endinspection.finalAuditStatus != 3}
													{if endPassAlready != 1 && granted != null && granted.status == 1}
														{if endinspection.provinceAuditStatus == 0}
															<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1 j_addEndAuditPop" type="button" value="审核" />
																<input class="btn1 j_backEndApply" type="button" value="退回" />
															</sec:authorize>
														{elseif endinspection.provinceAuditStatus < 3}
															<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="3" style="cursor: pointer;">查看详情</a></span>
															{if (isPrincipal == 1 && endAuditorInfo[6] == belongId) || (isPrincipal == 0 && endAuditorInfo[5] == belongId)}
															<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_MODIFY'>
																<input class="btn1 j_modifyEndAuditPop" type="button" value="修改" />
															</sec:authorize>
															<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
																<input class="btn1 j_submitEndAudit" type="button" value="提交" />
																<input class="btn1 j_backEndApply" type="button" value="退回" />
															</sec:authorize>
															{/if}
														{/if}
													{/if}
												{else}
													<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="3" style="cursor: pointer;">查看详情</a></span>
												{/if}
											</td>
										</tr>
									</s:if>
								{/if}
								<!-- 教育部及系统管理员 -->
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0">
									<tr class="table_tr4">
										<td>教育部意见</td>
										<td>
											{if endinspection.ministryResultEnd == 0}<span class="ico_txt3">待审</span>
											{elseif endinspection.ministryResultEnd == 1}<span class="ico_txt5">不同意</span>
											{elseif endinspection.ministryResultEnd == 2}<span class="ico_txt1">同意</span>
											{/if}
										</td>
										{if endinspection.isApplyNoevaluation == 1}
											<td>
												{if endinspection.ministryResultNoevaluation == 0}<span class="ico_txt3">待审</span>
												{elseif endinspection.ministryResultNoevaluation == 1}<span class="ico_txt5">不同意</span>
												{elseif endinspection.ministryResultNoevaluation == 2}<span class="ico_txt1">同意</span>
												{/if}
											</td>
										{/if}
										{if endinspection.isApplyExcellent == 1}
											<td>
												{if endinspection.ministryResultExcellent == 0}<span class="ico_txt3">待审</span>
												{elseif endinspection.ministryResultExcellent == 1}<span class="ico_txt5">不同意</span>
												{elseif endinspection.ministryResultExcellent == 2}<span class="ico_txt1">同意</span>
												{/if}
											</td>
										{/if}
										<td>
											{if endinspection.ministryAuditStatus == 3}<span>提交</span>
											{elseif endinspection.ministryAuditStatus == 2}<span>暂存</span>
											{elseif endinspection.ministryAuditStatus == 1}<span>退回</span>
											{elseif endinspection.ministryAuditStatus == 0}<span></span>
											{/if}
										</td>
										<td>
											<!-- 非导入数据、教育部账号、最近一次结项处在部级审核级别 -->
											{if endinspection.createMode == 0 && endinspection_index == 0 && accountType == "MINISTRY" && endinspection.status == 5 && endinspection.finalAuditStatus != 3}
												{if endPassAlready != 1 && granted != null && granted.status == 1}
													{if endinspection.ministryAuditStatus == 0}
														<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_addEndAuditPop" type="button" value="审核" />
															<input class="btn1 j_backEndApply" type="button" value="退回" />
														</sec:authorize>
													{elseif endinspection.ministryAuditStatus < 3}
														<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="4" style="cursor: pointer;">查看详情</a></span>
														{if (isPrincipal == 1 && endAuditorInfo[8] == belongId) || (isPrincipal == 0 && endAuditorInfo[7] == belongId)}
														<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_MODIFY'>
															<input class="btn1 j_modifyEndAuditPop" type="button" value="修改" />
														</sec:authorize>
														<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLYAUDIT_ADD'>
															<input class="btn1 j_submitEndAudit" type="button" value="提交" />
															<input class="btn1 j_backEndApply" type="button" value="退回" />
														</sec:authorize>
														{/if}
													{/if}
												{/if}
											{else}
												<span class="ico_txt2"><a class="j_viewEndAuditPop" endId="${endinspection.id}" data="4" style="cursor: pointer;">查看详情</a></span>
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
			<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_VIEW'>
				{if endinspection_index == 0 && (accountType =="EXPERT" || accountType == "TEACHER") && isEndReviewer[endinspection_index] > 0 && endinspectionReview[endinspection_index].submitStatus < 3}<!-- 是评审专家 -->
					<div class="p_box_body">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<thead>
								<tr class="head_title">
									<td colspan="6">专家鉴定情况</td>
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
									<td>${endinspectionReview[endinspection_index].reviewerSn}</td>
									<td>${endinspectionReview[endinspection_index].reviewerName}</td>
									<td>${endinspectionReview[endinspection_index].score}</td>
									<td>${endinspectionPersonalGrade[endinspection_index]}</td>
									<td>
										{if endinspectionReview[endinspection_index].submitStatus==2}暂存
										{elseif endinspectionReview[endinspection_index].submitStatus==3}提交
										{else}
										{/if}
									</td>
									<td>
										<!-- 非导入数据、最近一次结项处在评审级别 -->
										{if endinspection.createMode == 0 && endinspection_index == 0 && endinspection.status == 6 && endinspection.finalAuditStatus != 3 && endinspectionReview[endinspection_index].submitStatus != 3}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if endinspectionReview[endinspection_index].submitStatus==0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_ADD'>
														<input class="btn1 j_addEndReviewPop" endId="${endinspection.id}" type="button" value="鉴定" />
													</sec:authorize>
												{elseif endinspectionReview[endinspection_index].submitStatus < 3}
													<span class="ico_txt2"><a class="j_viewEndReviewPop" endId="${endinspectionReview[endinspection_index].id}" style="cursor: pointer;">查看详情</a></span>
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_MODIFY'>
														<input class="btn1 j_modifyEndReviewPop" endId="${endinspection.id}" rendId="${endinspectionReview[endinspection_index].id}" type="button" value="修改" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_ADD'>
														<input class="btn1 j_submitEndReview" endId="${endinspection.id}" type="button" value="提交" />
													</sec:authorize>
												{/if}
											{/if}
										{else}
											<span class="ico_txt2"><a class="j_viewEndReviewPop" endId="${endinspectionReview[endinspection_index].id}" style="cursor: pointer;">查看详情</a></span>
										{/if}
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				{/if}
				<!-- 所有专家评审信息及小组评审信息 -->
				{if ((isEndReviewer[endinspection_index] == 2 && endinspectionReviewStatus[endinspection_index] == 3)|| (accountType != "EXPERT" && accountType != "TEACHER" && accountType != "STUDENT"))}
					<div class="p_box_body">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<thead>
								<tr class="head_title">
									<td colspan="6">专家鉴定情况</td>
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
								{if endinspectionReviews[endinspection_index] != null}
									{for item in endinspectionReviews[endinspection_index]}
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
													<span class="ico_txt2"><a class="j_viewEndReviewPop" endId="${item[0]}" style="cursor: pointer;">查看详情</a></span>
												{/if}
											</td>
										</tr>
									{/for}
								{/if}
								<!-- 评审汇总信息 -->
								<tr class="table_tr4">
									<td>汇总意见</td>
									<td>${endinspection.reviewerName}</td>
									<td>${endinspection.reviewAverageScore}</td>
									<td>${endinspectionReviewGrade[endinspection_index]}</td>
									<td>
										{if endinspection.reviewStatus == 3 && endinspection.reviewResult == 1}提交(不同意)
										{elseif endinspection.reviewStatus == 3 && endinspection.reviewResult == 2}提交(同意)
										{elseif endinspection.reviewStatus == 2 && endinspection.reviewResult == 1}暂存(不同意)
										{elseif endinspection.reviewStatus == 2 && endinspection.reviewResult == 2}暂存(同意)
										{else}
										{/if}
									</td>
									<td>
										 <!-- 非导入数据、教师或专家账号、最近一次结项处在小组评审级别 -->
										{if endinspection.createMode == 0 && endinspection_index == 0 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "TEACHER") && endinspection.status == 6 && endinspection.finalAuditStatus != 3 && allReviewSubmit != -1}
											{if endPassAlready != 1 && granted != null && granted.status == 1}
												{if endinspection.reviewStatus == 0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_ADD'>
														<input class="btn1 j_addEndGroupReviewPop" endId="${endinspection.id}" type="button" value="鉴定" />
													</sec:authorize>
												{elseif endinspection.reviewStatus == 2}
													<span class="ico_txt2"><a class="j_viewEndGroupReviewPop" endId="${endinspection.id}" style="cursor: pointer;">查看详情</a></span>
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_MODIFY'>
														<input class="btn1 j_modifyEndGroupReviewPop" endId=""${endinspection.id} type="button" value="修改" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEW_ADD'>
														<input class="btn1 j_submitEndGroupReview" endId="${endinspection.id}" type="button" value="提交" />
													</sec:authorize>
												{/if}
											{/if}
										{else}
											<span class="ico_txt2"><a class="j_viewEndGroupReviewPop" endId="${endinspection.id}" style="cursor: pointer;">查看详情</a></span>
										{/if}
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				{/if}
			</sec:authorize>
			<div class="p_box_body"><!-- 最终结项结果 -->
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0"><!-- 部级及以上管理人员 -->
						<thead>
							<tr class="head_title">
								{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}
									<td colspan="6">最终结项结果</td>
								{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}
									<td colspan="5">最终结项结果</td>
								{else}
									<td colspan="4">最终结项结果</td>
								{/if}	
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td width="120">审核时间</td>
								<td>是否同意结项</td>
								{if endinspection.isApplyNoevaluation == 1}
									<td>免鉴定成果</td>
								{/if}
								{if endinspection.isApplyExcellent == 1}
									<td>优秀成果结果</td>
								{/if}
								<td>提交状态</td>
								<td width="250">查看详情</td>
							</tr>
							<tr class="table_tr4">
								<td>${endinspection.finalAuditDate}</td>
								<td>
									{if endinspection.finalAuditResultEnd == 1 && endinspection.finalAuditStatus > 0}<span class="ico_txt5">不同意</span>
									{elseif endinspection.finalAuditResultEnd == 2 && endinspection.finalAuditStatus > 0}<span class="ico_txt1">同意</span>
									{else}<span class="ico_txt3">待审</span>
									{/if}
								</td>
								{if endinspection.isApplyNoevaluation == 1}
									<td>
										{if endinspection.finalAuditResultNoevaluation == 1 && endinspection.finalAuditStatus > 0}<span class="ico_txt5">不同意</span>
										{elseif endinspection.finalAuditResultNoevaluation == 2 && endinspection.finalAuditStatus > 0}<span class="ico_txt1">同意</span>
										{else}<span class="ico_txt3">待审</span>
										{/if}
									</td>
								{/if}
								{if endinspection.isApplyExcellent == 1}
									<td>
										{if endinspection.finalAuditResultExcellent == 1 && endinspection.finalAuditStatus > 0}<span class="ico_txt5">不同意</span>
										{elseif endinspection.finalAuditResultExcellent == 2 && endinspection.finalAuditStatus > 0}<span class="ico_txt1">同意</span>
										{else}<span class="ico_txt3">待审</span>
										{/if}
									</td>
								{/if}
								<td>
									{if endinspection.finalAuditStatus == 3}<span>提交</span>
									{elseif endinspection.finalAuditStatus == 2}<span>暂存</span>
									{elseif endinspection.finalAuditStatus == 1}<span>退回</span>
									{elseif endinspection.finalAuditStatus == 0}<span></span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、部级账号、最近一次结项处在评审审核审核级别 -->
									{if endinspection.createMode == 0 && endinspection_index == 0 && accountType == "MINISTRY" && endinspection.status == 7 && endinspection.finalAuditStatus != 3}
										<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEWAUDIT_VIEW'>
											{if endPassAlready != 1 && granted != null && granted.status != 3 && granted.status != 4}
												{if endinspection.finalAuditStatus==0}
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEWAUDIT_ADD'>
														<input class="btn1 j_addEndReviewAuditPop" endId="${endinspection.id}" type="button" value="审核" />
													</sec:authorize>
												{elseif endinspection.finalAuditStatus < 3}
													<span class="ico_txt2"><a class="j_viewEndReviewAuditPop" endId="${endinspection.id}" style="cursor: pointer;">查看详情</a></span>
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEWAUDIT_MODIFY'>
														<input class="btn1 j_modifyEndReviewAuditPop" endId="${endinspection.id}" type="button" value="修改" />
													</sec:authorize>
													<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_REVIEWAUDIT_ADD'>
														<input class="btn1 j_submitEndReviewAuditResult" endId="${endinspection.id}" type="button" value="提交" />
													</sec:authorize>
												{/if}
											{/if}
										</sec:authorize>
									{elseif endinspection.finalAuditStatus > 0}
										<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_VIEW'>
											<span class="ico_txt2"><a class="j_viewEndReviewAuditPop" endId="${endinspection.id}" style="cursor: pointer;">查看详情</a></span>
										</sec:authorize>
										{if endinspection_index == 0 && endinspection.finalAuditStatus==3 && granted != null && granted.status != 3 && granted.status != 4}
											<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_MODIFY'>
												<input class="btn1 j_modifyEndResultPop" modifyFlags="1" type="button" value="修改" />
											</sec:authorize>
										{/if}
									{/if}
								</td>
							</tr>
							<tr class="table_tr4">
								<td colspan="{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}6{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}5{else}4{/if}">结项证书编号：${endinspection.certificate}</td>
							</tr>
						</tbody>
					</s:if>
					<s:else><!-- 部级以下所有人员 -->
						<thead>
							<tr class="head_title">
								{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}
									<td colspan="4">最终结项结果</td>
								{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}
									<td colspan="3">最终结项结果</td>
								{else}
									<td colspan="2">最终结项结果</td>
								{/if}
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td>审核时间</td>
								<td>是否同意结项</td>
								{if endinspection.isApplyNoevaluation == 1}
									<td>免鉴定成果</td>
								{/if}
								{if endinspection.isApplyExcellent == 1}
									<td>优秀成果结果</td>
								{/if}
							</tr>
							<tr class="table_tr4">
								<td>{if endinspection.finalAuditStatus == 3}${endinspection.finalAuditDate}{/if}</td>
								<td>
									{if endinspection.finalAuditResultEnd==2 && endinspection.finalAuditStatus==3}<span class="ico_txt1">同意</span>
									{elseif endinspection.finalAuditResultEnd==1 && endinspection.finalAuditStatus==3}<span class="ico_txt5">不同意</span>
									{else}<span class="ico_txt3">待审</span>
									{/if}
								</td>
								{if endinspection.isApplyNoevaluation == 1}
									<td>
										{if endinspection.finalAuditResultNoevaluation==2 && endinspection.finalAuditStatus==3}<span class="ico_txt1">同意</span>
										{elseif endinspection.finalAuditResultNoevaluation==1 && endinspection.finalAuditStatus==3}<span class="ico_txt5">不同意</span>
										{else}<span class="ico_txt3">待审</span>
										{/if}
									</td>
								{/if}
								{if endinspection.isApplyExcellent == 1}
									<td>
										{if endinspection.finalAuditResultExcellent==2 && endinspection.finalAuditStatus==3}<span class="ico_txt1">同意</span>
										{elseif endinspection.finalAuditResultExcellent==1 && endinspection.finalAuditStatus==3}<span class="ico_txt5">不同意</span>
										{else}<span class="ico_txt3">待审</span>
										{/if}
									</td>
								{/if}
							</tr>
							{if endinspection.finalAuditStatus == 3 && endinspection.finalAuditResultEnd==2}
								<tr class="table_tr4">
									<td colspan="{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}4{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}3{else}2{/if}">结项证书编号：${endinspection.certificate}</td>
								</tr>
							{/if}
						</tbody>
					</s:else>
				</table>
			</div>
			{if endinspection.finalAuditStatus == 3}
				<div class="p_box_body">
					<div class="p_box_body_t">
						<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
							<tr class="table_tr7">
								<td class="key" width="117">审核意见：<br /></span><span>（反馈给负责人）</span></td>
								<td class="value">{if endinspection.finalAuditOpinionFeedback != null && endinspection.finalAuditOpinionFeedback != ""}<pre>${endinspection.finalAuditOpinionFeedback}</pre>{/if}</td>
							</tr>
						</table>
					</div>
				</div>
			{/if}
			<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_PRINT'>
				{if endinspection_index == 0 && endinspection.finalAuditResultEnd==2 && endinspection.finalAuditStatus==3 && granted.status == 2}
					<div style="text-align:center; background:#ECECEC;">
						{if endinspection.printCount == 0}
							<input class="btn1" type="button" value="打印" onclick="printCertificate(this);"/>
						{else}已打印${endinspection.printCount}次
							<input class="btn2" type="button" value="重新打印" onclick="printCertificate(this);"/>
						{/if}
					</div>
				{/if}
			</sec:authorize>
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
			<!-- 对应结项成果 -->
			<div class="p_box_body">
				
					<div style="margin-top:10px;margin-bottom:5px;">附：结项成果
						{if endinspection_index == 0}<s:hidden id="endInspectionId" value="${endinspection.id}"/>{/if}
						<span id="end_total">${endProdInfo[endinspection_index].productSize}</span>
						<span>项
							{if endProdInfo[endinspection_index].productSize!=0}(&nbsp;
								{if endProdInfo[endinspection_index].paperSize != null && endProdInfo[endinspection_index].paperSize != 0}论文${endProdInfo[endinspection_index].paperSize}项&nbsp;{/if}
								{if endProdInfo[endinspection_index].bookSize != null && endProdInfo[endinspection_index].bookSize != 0}著作${endProdInfo[endinspection_index].bookSize}项&nbsp;{/if}
								{if endProdInfo[endinspection_index].consultationSize != null && endProdInfo[endinspection_index].consultationSize!=0}研究咨询报告${endProdInfo[endinspection_index].consultationSize}项&nbsp;{/if}
								{if endProdInfo[endinspection_index].electronicSize != null && endProdInfo[endinspection_index].electronicSize!=0}电子出版物${endProdInfo[endinspection_index].electronicSize}项&nbsp;{/if}
								{if endProdInfo[endinspection_index].patentSize != null && endProdInfo[endinspection_index].patentSize!=0}专利${endProdInfo[endinspection_index].patentSize}项&nbsp;{/if}
								{if endProdInfo[endinspection_index].otherProductSize != null && endProdInfo[endinspection_index].otherProductSize!=0}其他成果${endProdInfo[endinspection_index].otherProductSize}项&nbsp;{/if}
								)
							{/if}
						</span>
						{if isDirector == 1 && endinspection.applicantSubmitStatus != 3 && endinspection_index == 0 && endinspection.createMode == 0}
							<s:hidden id="endInspectionId" value="${endinspection.id}"/>
							<s:hidden id="prodNum" value="${endProdInfo[endinspection_index].productSize}"/>
							<input id="view_add_product" class="btn1" type="button" value="添加" name="end_prod"/>
							<input id="view_mod_product" class="btn1" type="button" value="修改" name="end_prod"/>
							<span class="text_red">&nbsp;（结项申请提交后不能再添加结项成果！）</span>
						{/if}
						<s:hidden id="canAuditEndProduct" value="${canAuditEndProduct}"/>
					</div>
					<table id="list_table_end_${endinspection_index}" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<thead id="list_head">
							<tr class="table_title_tr4">
							
								{if isDirector == 1 && endinspection.applicantSubmitStatus != 3 && endinspection_index == 0 && endinspection.createMode == 0}
									<td width="20"><input id="check" name="check" type="checkbox"  title="点击全选/不选本页所有账号"/></td>
									<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								{/if}
								
								<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
									{if endinspection_index == 0 && canAuditEndProduct}
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
								<td width="35">学科门类</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="91">是否标注教育部社科项目资助</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24" /></td>
								<td width="40">是否最终成果</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								<td width="35">初审结果</td>
								
								<td width="2"><img src="image/table_line2.gif" width="2" height="24"/></td>
								<td width="35">终审结果</td>
							</tr>
						</thead>
						<tbody>
							{for item in endProdInfo[endinspection_index].productList}
								<tr class="table_txt_tr3">
								
									{if isDirector == 1 && endinspection.applicantSubmitStatus != 3 && endinspection_index == 0 && endinspection.createMode == 0}
										<td><input type="checkbox" name="entityIds" value="${item[0]}" class="selectProduct" alt="${item[2]}"/></td>
										<td></td>
									{/if}
								
									{if endinspection_index == 0 && canAuditEndProduct}
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
										{if item[11] == 1}是
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
								{if endinspection_index == 0}
									{if canAuditEndProduct}
										<sec:authorize ifAnyGranted="ROLE_PRODUCT_AUDIT_ADD">
											<input id="view_aud_product" class="btn1" type="button" value="审核" name="end_prod"/>
										</sec:authorize>
									{/if}
									{if isDirector == 1 && endinspection.applicantSubmitStatus != 3 && endinspection.createMode == 0}
										<s:hidden id="endInspectionId" value="${endinspection.id}"/>
										<input id="view_del_product" class="btn1" type="button" value="删除" name="end_prod"/>
									{/if}
								{/if}
							</td>
						</tr>
					</table>
				
			</div>
			<!-- /////////////////////////////////////////////////////////////////////////////////////// -->
		</div>
	{forelse}<!-- 无结项信息 -->
	<!-- 研究人员 -->
		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
			{if isDirector == 1 && endPassAlready != 1 && endPending != 1 && varPending==0 && granted != null && granted.status == 1}
				<div class="p_box_b_2"><a class="j_downloadEndTemplate"  style="cursor: pointer;">下载结项申请书模板</a></div>
				<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_APPLY_ADD'>
					<div class="p_box_b_1"><input class="btn1 j_toAddEndApplyPop" type="button" value="申请" /><br/></div>
				</sec:authorize>
			{else}
				<div class="p_box_body">
					<div style="text-align:center;">暂无符合条件的记录</div>
				</div>
			{/if}
		</s:if>
		<!-- 部级管理人员 -->
		<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
			<sec:authorize ifAllGranted='ROLE_PROJECT_ENTRUST_ENDINSPECTION_DATA_ADD'>	
				{if endPassAlready==0 && endPending==0 && varPending==0 && granted != null && granted.status == 1}
					<div><input class="btn1 j_addEndResultPop" type="button" value="添加" /><br/></div>
				{elseif granted!= null && granted.status == 3}
					<div>项目已中止，不能录入数据</div>
				{elseif granted!= null && granted.status == 4}
					<div>项目已撤项，不能录入数据</div>
				{elseif endPassAlready!=0}
					<div>结项已通过，不能录入数据</div>
				{elseif varPending!=0}
					<div>变更正在处理中，不能录入数据</div>
				{elseif endPending!=0}
					<div>结项正在处理，不能录入数据</div>
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
<div id="view_end" style="display:none;"></div>
<s:hidden id="endProAudAlr" value='1'/>
<s:form id="end_form" theme="simple">
	<s:hidden id="endFormProjectid" name="projectid" />
	<s:hidden id="endIsApplyExcellent" name="isApplyExcellent"/>
	<s:hidden id="endIsApplyNoevaluation" name="isApplyNoevaluation"/>
	<s:hidden id="endAuditOpinion" name="endAuditOpinion"/>
	<s:hidden id="endAuditOpinionFeedback" name="endAuditOpinionFeedback"/>
	<s:hidden id="endAuditResult" name="endAuditResult"/>
	<s:hidden id="endDate" name="endDate"/>
	<s:hidden id="endNoauditResult" name="endNoauditResult"/>
	<s:hidden id="endExcellentResult" name="endExcellentResult"/>
	<s:hidden id="endAuditStatus" name="endAuditStatus"/>
	<s:hidden id="endCertificate" name="endCertificate"/>
</s:form>
<s:form id="end_imported_form" theme="simple">
	<s:hidden id="fileIds" name="fileIds" />
	<s:hidden id="endImportedFormProjectid" name="projectid" />
	<s:hidden id="endImportedIsApplyExcellent" name="isApplyExcellent"/>
	<s:hidden id="endImportedIsApplyNoevaluation" name="isApplyNoevaluation"/>
	<s:hidden id="endImportedResult" name="endResult"/>
	<s:hidden id="endImportedDate" name="endDate"/>
	<s:hidden id="endImportedNoauditResult" name="endNoauditResult"/>
	<s:hidden id="endImportedExcellentResult" name="endExcellentResult"/>
	<s:hidden id="endImportedCertificate" name="endCertificate"/>
	<s:hidden id="endImportedStatus" name="endImportedStatus"/>
	<s:hidden id="endImportedProductInfo" name="endProductInfo"/>
	<s:hidden id="endImportedMember" name="endMember"/>
	<s:hidden id="endImportedOpinion" name="endImportedOpinion"/>
	<s:hidden id="endImportedOpinionFeedback" name="endOpinionFeedback"/>
	<s:hidden id="endModifyFlag" name="modifyFlag"/>
</s:form>
<s:form id="review_endform" theme="simple">
	<s:hidden id="endSubmitStatus" name="submitStatus"/>
	<s:hidden id="endInnovationScore" name="innovationScore"/>
	<s:hidden id="endScientificScore" name="scientificScore"/>
	<s:hidden id="endBenefitScore" name="benefitScore"/>
	<s:hidden id="endOpinion" name="opinion"/>
	<s:hidden id="endQualitativeOpinion" name="qualitativeOpinion"/>
</s:form>
<s:form id="group_review_endform" theme="simple">
	<s:hidden id="endReviewWay" name="reviewWay"/>
	<s:hidden id="endReviewStatus" name="reviewStatus"/>
	<s:hidden id="endReviewResult" name="reviewResult"/>
	<s:hidden id="endReviewOpinion" name="reviewOpinion"/>
	<s:hidden id="endReviewOpinionQualitative" name="reviewOpinionQualitative"/>
</s:form>
<s:form id="review_endAudit_form" theme="simple">
	<s:hidden id="reviewIsApplyExcellent" name="isApplyExcellent"/>
	<s:hidden id="reviewIsApplyNoevaluation" name="isApplyNoevaluation"/>
	<s:hidden id="reviewEndAuditStatus" name="reviewAuditStatus"/>
	<s:hidden id="reviewAuditResultNoevalu" name="reviewAuditResultNoevalu"/> 
	<s:hidden id="reviewAuditResultEnd" name="reviewAuditResultEnd"/>
	<s:hidden id="reviewAuditResultExcelle" name="reviewAuditResultExcelle"/> 
	<s:hidden id="reviewEndAuditOpinion" name="reviewAuditOpinion"/>
	<s:hidden id="reviewEndAuditOpinionFeedback" name="reviewAuditOpinionFeedback"/>
	<s:hidden name="certificate"/>
</s:form>
<s:form id="uploadEndFile_form" theme="simple">
	<s:hidden id="endId" name="endId"/>
	<s:hidden id="endFileId" name="endFileId" />
</s:form>