<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<!-- 项目结项业务标签页 -->

<div id="endinspection">
	<textarea id="view_endinspection_template" style="display:none;">
		{if midPending== 0 && (midPassAlready == 1 || endAllow == 1) && endPassAlready==0 && endPending==0 && varPending==0 && granted != null && granted.status == 1}
		<div style="margin:10px 0 0 10px;"><input class="btn endAddFinalResult" type="button" appId="${granted.id}" value="添加" /><br/></div>
		{elseif granted != null && granted.status == 3}
			<div>项目已中止，不能录入数据</div>
		{elseif granted != null && granted.status == 4}
			<div>项目已撤项，不能录入数据</div>
		{elseif endPassAlready!=0}
			<div>结项已通过，不能录入数据</div>
		{elseif midPending!=0}
			<div>中检正在处理中，不能录入数据</div>
		{elseif midPassAlready == 0 && endAllow == 0}
			<div>中检未通过且项目立项未超期，不能录入数据</div>
		{elseif varPending!=0}
			<div>变更正在处理中，不能录入数据</div>
		{elseif endPending!=0}
			<div>结项正在处理，不能录入数据</div>
		{/if}
			
		<div style="display: none;">${num = 0}</div>
		{for endinspection in endList}
			<!-- 结项情况基本信息 -->
			<table class="table_edit" cellspacing="0" cellpadding="0">
				<tr><th class="thhead" colspan="4"><a class="expand" href="javascript:void(0);">第<span class="number" name="${endList.length}">${endinspection_index}</span>次结项</a></th></tr>
				<tr>
					<td class="wd17">是否申请免鉴定：</td>
					<td class="wd18">
						{if endinspection.isApplyNoevaluation == 1}是
						{elseif endinspection.isApplyNoevaluation == 0}否
						{/if}
					</td>
					<td class="wd17">是否申请优秀成果：</td>
					<td class="wd18">
						{if endinspection.isApplyExcellent == 1}是
						{elseif endinspection.isApplyExcellent == 0}否
						{/if}
					</td>
				</tr>
				<tr>
					<td class="wd17">>结项成果数量：<br/>（成果形式/总数/合格数）</td>
					<td class="wd18" colspan="3">${endinspection.importedProductInfo}</td>
				</tr>
				<tr>
					<td class="wd17">主要参加人：</td>
					<td class="wd18" colspan="3">
						{if endinspection.memberName == null || endinspection.memberName == ""}无
						{else}${endinspection.memberName}
						{/if}
					</td>
				</tr>
				<tr>
					<td class="wd17">备注：</td>
					<td class="wd18" colspan="3">{if endinspection.note != null && endinspection.note != ""}<pre>${endinspection.note}</pre>{/if}</td>
				</tr>
			</table>
			
			<!--结项情况经费明细-->
			{if projectEndFees[endinspection_index].feeFlag == 1 }
				<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
					<tr>
						<td class="wd17" style="border-top:0px;">批准经费</td>
						<td class="wd18" style="border-top:0px;">${projectEndFees[endinspection_index].approveFee}</td>
						<td class="wd17" style="border-top:0px;">已拨经费</td>
						<td class="wd18" style="border-top:0px;">${projectEndFees[endinspection_index].fundedFee}</td>
						<td class="wd17" style="border-top:0px;">未拨经费</td>
						<td class="wd18" style="border-top:0px;">${projectEndFees[endinspection_index].toFundFee}</td>
					</tr>
				</table>
				<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
				{if projectFeeGranted != null }
					<tbody>
						<tr><th class="wd17" colspan="4" style="text-align:center;border-top:0px;">结项经费结算明细</th></tr>
						<tr>
							<td class="wd17" style="width:35%;text-align:center;">经费科目</td>
							<td class="wd17" style="width:15%;text-align:center;">经费预算金额（万元）</td>
							<td class="wd17" style="width:15%;text-align:center;">经费支出金额（万元）</td>
							<td class="wd17" style="width:35%;text-align:center;">开支说明</td>
						</tr>
						<tr>
							<td class="wd17">图书资料费</td>
							<td class="wd18">${projectFeeGranted.bookFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].bookFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].bookNote}</td>
						</tr>
						<tr>
							<td class="wd17">数据采集费</td>
							<td class="wd18">${projectFeeGranted.dataFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].dataFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].dataNote}</td>
						</tr>
						<tr>
							<td class="wd17">调研差旅费</td>
							<td class="wd18">${projectFeeGranted.travelFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].travelFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].travelNote}</td>
						</tr>
						<tr>
							<td class="wd17">会议费</td>
							<td class="wd18">${projectFeeGranted.conferenceFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].conferenceFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].conferenceNote}</td>
						</tr>
						<tr>
							<td class="wd17">国际合作交流费</td>
							<td class="wd18">${projectFeeGranted.internationalFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].internationalFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].internationalNote}</td>
						</tr>
						<tr>
							<td class="wd17">设备购置和使用费</td>
							<td class="wd18">${projectFeeGranted.deviceFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].deviceFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].deviceNote}</td>
						</tr>
						<tr>
							<td class="wd17">专家咨询和评审费</td>
							<td class="wd18">${projectFeeGranted.consultationFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].consultationFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].consultationNote}</td>
						</tr>
						<tr>
							<td class="wd17">助研津贴和劳务费</td>
							<td class="wd18">${projectFeeGranted.laborFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].laborFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].laborNote}</td>
						</tr>
						<tr>
							<td class="wd17">印刷和出版费</td>
							<td class="wd18">${projectFeeGranted.printFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].printFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].printNote}</td>
						</tr>
						<tr>
							<td class="wd17">间接费用</td>
							<td class="wd18">${projectFeeGranted.indirectFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].indirectFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].indirectNote}</td>
						</tr>
						<tr>
							<td class="wd17">其他</td>
							<td class="wd18">${projectFeeGranted.otherFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].otherFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].otherNote}</td>
						</tr>
						<tr>
							<td class="wd17">合计</td>
							<td class="wd18">${projectFeeGranted.totalFee}</td>
							<td class="wd18" colspan="2">${projectEndFees[endinspection_index].totalFee}</td>
						</tr>
						<tr>
							<td class="wd17">相关说明</td>
							<td class="wd18">${projectFeeGranted.feeNote}</td>
							<td colspan="2" class="wd18">${projectEndFees[endinspection_index].feeNote}</td>
						</tr>
					</tbody>
				{else}
					<tbody>
						<tr><th class="wd17" colspan="3" style="text-align:center;border-top:0px;">结项经费结算明细</th></tr>
						<tr>
							<td class="wd17" style="width:40%;text-align:center;">经费科目</td>
							<td class="wd17" style="width:20%;text-align:center;">经费支出金额（万元）</td>
							<td class="wd17" style="width:40%;text-align:center;">开支说明</td>
						</tr>
						<tr>
							<td class="wd17">图书资料费</td>
							<td class="wd18">${projectEndFees[endinspection_index].bookFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].bookNote}</td>
						</tr>
						<tr>
							<td class="wd17">数据采集费</td>
							<td class="wd18">${projectEndFees[endinspection_index].dataFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].dataNote}</td>
						</tr>
						<tr>
							<td class="wd17">调研差旅费</td>
							<td class="wd18">${projectEndFees[endinspection_index].travelFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].travelNote}</td>
						</tr>
						<tr>
							<td class="wd17">会议费</td>
							<td class="wd18">${projectEndFees[endinspection_index].conferenceFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].conferenceNote}</td>
						</tr>
						<tr>
							<td class="wd17">国际合作交流费</td>
							<td class="wd18">${projectEndFees[endinspection_index].internationalFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].internationalNote}</td>
						</tr>
						<tr>
							<td class="wd17">设备购置和使用费</td>
							<td class="wd18">${projectEndFees[endinspection_index].deviceFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].deviceNote}</td>
						</tr>
						<tr>
							<td class="wd17">专家咨询和评审费</td>
							<td class="wd18">${projectEndFees[endinspection_index].consultationFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].consultationNote}</td>
						</tr>
						<tr>
							<td class="wd17">助研津贴和劳务费</td>
							<td class="wd18">${projectEndFees[endinspection_index].laborFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].laborNote}</td>
						</tr>
						<tr>
							<td class="wd17">印刷和出版费</td>
							<td class="wd18">${projectEndFees[endinspection_index].printFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].printNote}</td>
						</tr>
						<tr>
							<td class="wd17">间接费用</td>
							<td class="wd18">${projectEndFees[endinspection_index].indirectFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].indirectNote}</td>
						</tr>
						<tr>
							<td class="wd17">其他</td>
							<td class="wd18">${projectEndFees[endinspection_index].otherFee}</td>
							<td class="wd18">${projectEndFees[endinspection_index].otherNote}</td>
						</tr>
						<tr>
							<td class="wd17">合计</td>
							<td colspan="2">${projectEndFees[endinspection_index].totalFee}</td>
						</tr>
						<tr>
							<td class="wd17">相关说明</td>
							<td colspan="2" class="wd18">${projectEndFees[endinspection_index].feeNote}</td>
						</tr>
					</tbody>
					{/if}
				</table>
			{/if}
			
			<!-- 教育部审核情况 -->
			<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
				<tr>
					{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}
						<th class="wd17" colspan="5" style="text-align:center;border-top:0px;">教育部审核情况</th>
					{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}
						<th class="wd17" colspan="4" style="text-align:center;border-top:0px;">教育部审核情况</th>
					{else}
						<th class="wd17" colspan="3" style="text-align:center;border-top:0px;">教育部审核情况</th>
					{/if}
				</tr>
				<tr>
					<td class="wd17" style="text-align:center;">审核日期</td>
					<td class="wd17" style="text-align:center;">是否同意结项</td>
					{if endinspection.isApplyNoevaluation == 1}
						<td class="wd17" style="text-align:center;">是否同意免鉴定</td>
					{/if}
					{if endinspection.isApplyExcellent == 1}
						<td class="wd17" style="text-align:center;">是否同意优秀成果</td>
					{/if}
					<td class="wd17" style="text-align:center;">审核情况</td>
				</tr>
				<tr>
					<td class="wd18" style="text-align:center;padding-left:0px;">
						${endinspection.ministryAuditDate}
					</td>
					<td class="wd18" style="text-align:center;padding-left:0px;">
						{if endinspection.ministryResultEnd == 0}待审
						{elseif endinspection.ministryResultEnd == 1}不同意
						{elseif endinspection.ministryResultEnd == 2}同意
						{/if}
					</td>
					{if endinspection.isApplyNoevaluation == 1}
						<td class="wd18" style="text-align:center;padding-left:0px;">
							{if endinspection.ministryResultEnd == 0}待审
							{elseif endinspection.ministryResultNoevaluation == 1}不同意
							{elseif endinspection.ministryResultNoevaluation == 2}同意
							{/if}
						</td>
					{/if}
					{if endinspection.isApplyExcellent == 1}
						<td class="wd18" style="text-align:center;padding-left:0px;">
							{if endinspection.ministryResultEnd == 0}待审
							{elseif endinspection.ministryResultExcellent == 1}不同意
							{elseif endinspection.ministryResultExcellent == 2}同意
							{/if}
						</td>
					{/if}
					<td class="wd18" style="text-align:center;padding-left:0px;">
						{if endPassAlready != 1 && granted != null && granted.status == 1 && endinspection.ministryResultEnd == 0}
							<input class="btn endAudit" type="button" appId="${granted.id}" endId="${endinspection.id}" isAppExce="${endinspection.isApplyExcellent}" isAppNoeval="${endinspection.isApplyNoevaluation}" value="审核" />
						{elseif}
							查看详情
						{/if}
					</td>
				</tr>
			</table>
			
			<!-- 结项情况专家鉴定信息 -->
			<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
				<tr><th class="wd17" colspan="6" style="text-align:center;border-top:0px;">专家鉴定情况</th></tr>
				<tr>
					<td class="wd17" style="width:15%;text-align:center;">专家序号</td>
					<td class="wd17" style="width:15%;text-align:center;">专家姓名</td>
					<td class="wd17" style="width:20%;text-align:center;">分数</td>
					<td class="wd17" style="width:20%;text-align:center;">等级</td>
					<td class="wd17" style="width:30%;text-align:center;">查看详情</td>
				</tr>
				
			{if endinspectionReviews[endinspection_index] != null}
				{for item1 in endinspectionReviews[endinspection_index]}
				<tr>
					<td class="wd18" style="text-align:center;">${item1[0]}</td>
					<td class="wd18" style="text-align:center;">${item1[1]}</td>
					<td class="wd18" style="text-align:center;">${item1[2]}</td>
					<td class="wd18" style="text-align:center;">${item1[3]}</td>
					<td class="wd18" style="text-align:center;">
						{if item1[4]!=0}
							查看详情
						{/if}
					</td>
				</tr>
				{/for}
			{/if}
			<tr>
				<td class="wd18" style="text-align:center;">汇总意见</td>
				<td class="wd18" style="text-align:center;">${endinspection.reviewerName}</td>
				<td class="wd18" style="text-align:center;">${endinspection.reviewAverageScore}</td>
				<td class="wd18" style="text-align:center;">评审等级</td>
				<td class="wd18" style="text-align:center;">
					查看详情
				</td>
			</tr>
			</table>
			
			<!-- 项目结项审核情况信息/最终结项结果 -->
			<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
				<tr>
					{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}
						<th colspan="5" class="wd17" style="text-align:center;border-top:0px;">最终结项结果</th>
					{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}
						<th colspan="4" class="wd17" style="text-align:center;border-top:0px;">最终结项结果</th>
					{else}
						<th colspan="3" class="wd17" style="text-align:center;border-top:0px;">最终结项结果</th>
					{/if}	
				</tr>
				<tr>
					<td class="wd17" style="text-align:center;">审核时间</td>
					<td class="wd17" style="text-align:center;">是否同意结项</td>
					{if endinspection.isApplyNoevaluation == 1}
						<td class="wd17" style="text-align:center;">免鉴定成果</td>
					{/if}
					{if endinspection.isApplyExcellent == 1}
						<td class="wd17" style="text-align:center;">优秀成果结果</td>
					{/if}
					<td class="wd17" style="text-align:center;">查看详情</td>
				</tr>
				<tr>
					<td class="wd17" style="text-align:center;">${endinspection.finalAuditDate}</td>
					<td class="wd17" style="text-align:center;">
						{if endinspection.finalAuditResultEnd == 1}不同意
						{elseif endinspection.finalAuditResultEnd == 2}同意
						{else}待审
						{/if}
					</td>
					{if endinspection.isApplyNoevaluation == 1}
						<td class="wd17" style="text-align:center;">
							{if endinspection.finalAuditResultNoevaluation == 1}不同意
							{elseif endinspection.finalAuditResultNoevaluation == 2}同意
							{else}待审
							{/if}
						</td>
					{/if}
					{if endinspection.isApplyExcellent == 1}
						<td class="wd17" style="text-align:center;">
							{if endinspection.finalAuditResultExcellent == 1}不同意
							{elseif endinspection.finalAuditResultExcellent == 2}同意
							{else}待审
							{/if}
						</td>
					{/if}
					<td class="wd17" style="text-align:center;">
					{if endPassAlready != 1 && granted != null && granted.status != 3 && granted.status != 4}
						{if endinspection.finalAuditResultEnd == 0}
							<input class="btn endFinalAudit" type="button" appId="${granted.id}" endId="${endinspection.id}" value="审核" />
						{else}
							已审核
						{/if}
					{else}
						查看详情
					{/if}
					</td>
				</tr>
				<tr>
					<td class="wd17" colspan="{if endinspection.isApplyNoevaluation == 1 && endinspection.isApplyExcellent == 1}5{elseif endinspection.isApplyNoevaluation == 1 || endinspection.isApplyExcellent == 1}4{else}3{/if}" style="text-align:center;">结项证书编号：${endinspection.certificate}</td>
				</tr>
			</table>			
		{/for}
	</textarea>

	<div id="view_endinspection" style="display:none"></div>
</div>