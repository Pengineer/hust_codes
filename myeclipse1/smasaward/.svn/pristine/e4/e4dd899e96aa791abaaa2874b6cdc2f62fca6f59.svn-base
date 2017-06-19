<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<div id="midinspection">
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
				<!-- 中检基本情况信息 -->
				<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top: 0px;border-top: 0px;">
				
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
							{if item.finalAuditResult==0}
								<input class="btn midAudit" type="button" projectid="${granted.id}" midId="${item.id}" value="审核" />
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
			</div>
		{/for}	
	</textarea>

	<div id="view_midinspection" style="display:none"></div>
</div>