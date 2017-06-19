<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<!-- 项目中检业务标签页 -->

<textarea id="view_midinspection_template" style="display:none;">
		{if midPassAlready==0 && midPending==0 && midForbid != 1 && endPassAlready != 1 && granted != null && granted.status == 1}
			<div style="margin:10px 0 0 10px;"><input class="btn midAddResult" type="button" graId="${granted.id}" value="添加" /><br/></div>
		{elseif granted != null && granted.status == 3}
			<div>项目已中止，不能录入数据</div>
		{elseif granted != null && granted.status == 4}
			<div>项目已撤项，不能录入数据</div>
		{elseif endPassAlready == 1}
			<div>结项已通过，不能录入数据</div>
		{elseif midPassAlready!=0}
			<div>中检已通过，不能录入数据</div>
		{elseif midPending!=0}
			<div>
				中检正在处理中，不能录入数据
			</div>
		{elseif midForbid == 1}
			<div>中检已截止，不能录入数据</div>
		{/if}
		
	<div style="display: none;">${num = 0}</div>
	{for item in midList}
	<table class="table_edit" cellspacing="0" cellpadding="0">
		<tr><th class="thhead" colspan="3"><a class="expand" href="javascript:void(0);">第<span class="number" name="${midList.length}">${item_index}</span>次中检</a></th></tr>
	</table>
	<div class="basic_content">
		<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top: 0px;border-top: 0px;">
			
			<!-- 中检基本情况信息 -->
			<tr>
				<td class="wd17" style="text-align: center;padding-right: 0px;" >审核日期</td>
				<td class="wd17" style="text-align: center;padding-right: 0px;" >是否同意中检</td>
				<td class="wd17" style="text-align: center;padding-right: 0px;" >审核状态</td>
			</tr>
			<tr>
				<td class="wd18" style="text-align: center;padding-left: 0px;" >${item.finalAuditDate}</td>
				<td class="wd18" style="text-align: center;padding-left: 0px;" >
					{if item.finalAuditResult == 1}
						不同意
					{elseif item.finalAuditResult == 2}
						同意
					{elseif item.finalAuditResult == 0}
						待审
					{/if}
				</td>
				<td class="wd18" style="text-align: center;padding-left: 0px;" >
				{if endPassAlready != 1 && granted != null && granted.status == 1}
					{if item.finalAuditResult==0}
						<input class="btn midAudit" type="button" projectid="${granted.id}" midId="${item.id}" value="审核" />
					{else}
						已审核
					{/if}
				{else}
					已审核
				{/if}
				</td>
			</tr>
			<tr>
				<td class="wd19" style="text-align:center;">审核意见：</td>
				<td colspan="2" style="text-align:center;">
					{if item.finalAuditResult==0}
						未审核
					{else}
						${item.finalAuditOpinion}
					{/if}
				</td>
			</tr>
			<tr>
				<td class="wd19" style="text-align:center;">
					审核意见：<br>(反馈给负责人)
				</td>
				<td colspan="2" style="text-align:center;">
					{if item.finalAuditResult==0}
						未审核
					{else}
						${item.finalAuditOpinionFeedback}
					{/if}
				</td>
			</tr>
		</table>
		
		<!-- 中检经费情况信息（由于导出来的数据都没有对应信息，为避免页面出错，把对应代码注释掉并移至页面底部） -->
		
	</div>
	{/for}	
</textarea>

<div id="view_midinspection" style="display:none;"></div>

		<!-- 
		 中检经费情况信息 
		<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
			<tr><th class="thhead" colspan="3">中检经费结算明细</th></tr>
			<tr>
				<td class="wd17" style="width:25%;">经费科目</td>
				<td class="wd18" style="width:25%;">金额（万元）</td>
				<td class="wd18" style="width:50%;">开支说明</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">图书资料费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.bookFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.bookNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">数据采集费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.dataFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.dataNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">调研差旅费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.travelFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.travelNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">会议费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.conferenceFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.conferenceNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">国际合作交流费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.internationalFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.internationalNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">设备购置和使用费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.deviceFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.deviceNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">专家咨询和评审费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.consultationFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.consultationNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">助研津贴和劳务费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.laborFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.laborNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">印刷和出版费</td>
				<td class="wd18" style="width:25%;">${item.projectFee.printFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.printNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">间接费用</td>
				<td class="wd18" style="width:25%;">${item.projectFee.indirectFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.indirectNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">其他</td>
				<td class="wd18" style="width:25%;">${item.projectFee.otherFee}</td>
				<td class="wd18" style="width:50%;">${item.projectFee.otherNote}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">合计</td>
				<td colspan="2" class="wd18" style="width:75%;">${item.projectFee.totalFee}</td>
			</tr>
			<tr>
				<td class="wd17" style="width:25%;">相关说明</td>
				<td colspan="2" class="wd18" style="width:75%;">${granted.projectFee.feeNote}</td>
			</tr>
		</table>
		 -->
