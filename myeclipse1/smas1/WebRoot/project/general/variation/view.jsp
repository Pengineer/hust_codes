<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<!-- 项目变更业务标签页 -->

<textarea id="view_variation_template" style="display:none;">
	{if endPassAlready != 1 && varPending != 1 && granted != null && granted.status == 1}
		<div style="margin:10px 0 0 10px;"><input class="btn varAddResult" type="button" appId="${granted.id}" value="添加" /><br/></div>
	{elseif granted != null && granted.status == 3}
		<div>项目已中止，不能录入数据</div>
	{elseif granted != null && granted.status == 4}
		<div>项目已撤项，不能录入数据</div>
	{elseif endPassAlready!=0}
		<div>项目已结项，不能录入数据</div>
	{elseif varPending!=0}
		<div>变更正在处理中，不能录入数据</div>
	{/if}
	
	<div style="display: none;">${num = 0}</div>
	{for item in varList}
	
	<!-- 变更基本情况信息 -->
	<table class="table_edit" cellspacing="0" cellpadding="0">
		<tr><th class="thhead" colspan="4"><a class="expand" href="javascript:void(0);">第<span class="number" name="${varList.length}">${item_index}</span>次变更</a></th></tr>
		<tr>
			<th class="wd17" style="width:5%;text-align:center;padding-right:0px;">序号</th>
			<th class="wd17" style="width:29%;text-align:center;padding-right:0px;">变更事项</th>
			<th class="wd17" style="width:33%;text-align:center;padding-right:0px;">变更前</th>
			<th class="wd17" style="width:33%;text-align:center;padding-right:0px;">变更后</th>
		</tr>
		{var sn}
		{var sn = 0}
		{var varItems = ''}
		{if item.changeMember == 1}<!--变更项目成员-->
			<s:hidden value="${varItems = varItems + 'changeMember'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">变更项目成员（含负责人）</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">${item.oldMembers}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">${item.newMembers}</td>
			</tr>
		{/if}
		{if item.changeAgency == 1}<!--变更机构-->
			<s:hidden value="${varItems = varItems + ',changeAgency'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">变更项目管理机构</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">${item.oldAgencyName}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">${item.newAgencyName}</td>
			</tr>
		{/if}
		{if item.changeProductType == 1}<!-- 变更成果形式 -->
			<s:hidden value="${varItems = varItems + ',changeProductType'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">变更项目成果形式</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">
					{if item.oldProductType != null && item.oldProductType != ""}
						${item.oldProductType}{if item.oldProductTypeOther != null && item.oldProductTypeOther != ""}; ${item.oldProductTypeOther}{/if}
					{else}
						{if item.oldProductTypeOther != null && item.oldProductTypeOther != ""}${item.oldProductTypeOther}{/if}
					{/if}
				</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">
					{if item.newProductType != null && item.newProductType != ""}
						${item.newProductType}{if item.newProductTypeOther != null && item.newProductTypeOther != ""}; ${item.newProductTypeOther}{/if}
					{else}
						{if item.newProductTypeOther != null && item.newProductTypeOther != ""}${item.newProductTypeOther}{/if}
					{/if}
				</td>
			</tr>
		{/if}
		{if item.changeName == 1}<!-- 变更项目名称 -->
			<s:hidden value="${varItems = varItems + ',changeName'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">变更项目名称</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">中文名称：${item.oldName}<br/>英文名称：${item.oldEnglishName}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">中文名称：${item.newName}<br/>英文名称：${item.newEnglishName}</td>
			</tr>
		{/if}
		{if item.changeContent == 1}<!-- 研究内容有重大调整 -->
			<s:hidden value="${varItems = varItems + ',changeContent'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">研究内容有重大调整</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
			</tr>
		{/if}
		{if item.postponement == 1}<!-- 延期 -->
			<s:hidden value="${varItems = varItems + ',postponement'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">延期</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">${item.oldOnceDate}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">${item.newOnceDate}</td>
			</tr>
		{/if}
		{if item.withdraw == 1}
			<s:hidden value="${varItems = varItems + ',withdraw'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">撤项</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
			</tr>
		{/if}
		{if item.stop == 1} <!-- 中止 -->
			<s:hidden value="${varItems = varItems + ',stop'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">自行中止项目</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
			</tr>
		{/if}
		{if item.changeFee == 1}<!-- 变更经费预算(无经费系统，故暂未显示) -->
			<s:hidden value="${varItems = varItems + ',changeFee'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">变更经费预算</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
			</tr>
		{/if}
		{if item.other == 1}<!-- 变更其他 -->
			<s:hidden value="${varItems = varItems + ',other'}"></s:hidden>
			<tr>
				<td class="wd18" style="text-align:center;padding-left:0px;">${sn = sn + 1}</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">其他</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
				<td class="wd18" style="text-align:center;padding-left:0px;">暂无信息</td>
			</tr>
		{/if}
	</table>
	
	<!-- 变更审核信息 -->
	<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
		<tr><th class="wd17" colspan="3" style="text-align:left;padding-left:5px;">变更原因</th></tr>
		<tr><td colspan="3">${item.variationReason}</td></tr>
		<tr><th class="wd17" colspan="3" style="text-align:left;padding-left:5px;">最终变更结果</th></tr>
		<tr>
			<th class="wd17" style="text-align:center;">审核时间</th>
			<th class="wd17" style="text-align:center;">是否同意变更</th>
			<th class="wd17" style="text-align:center;">审核</th>
		</tr>
		<tr>
			<td class="wd18" style="text-align:center;padding-left:0px;">${item.finalAuditDate}</td>
			<td class="wd18" style="text-align:center;padding-left:0px;">
				{if item.finalAuditResult == 1}
					不同意
				{elseif item.finalAuditResult == 2}
					同意
				{elseif item.finalAuditResult == 0}
					待审
				{/if}
			</td>
			<td class="wd18" style="text-align:center;padding-left:0px;">
				{if endPassAlready != 1 && granted != null && granted.status == 1}
					{if item.finalAuditResult==0}
						<input class="btn varAudit" type="button" appId="${granted.id}" varId="${item.id}" varItems="${varItems}" value="审核" />
					{else}
						已审核
					{/if}
				{else}
					查看详情
				{/if}
			</td>
		</tr>
		<tr>
			<td class="wd19" style="text-align:center;">审核意见：</td>
			<td colspan="2" style="text-align:center;">
				{if endPassAlready != 1 && granted != null && granted.status == 1}
					{if item.finalAuditResult==0}
						未审核
					{else}
						${item.finalAuditOpinion}
					{/if}
				{else}
				
				{/if}
			</td>
		</tr>
		<tr>
			<td class="wd19" style="text-align:center;">
				审核意见：<br>(反馈给负责人)
			</td>
			<td colspan="2" style="text-align:center;">
				{if endPassAlready != 1 && granted != null && granted.status == 1}
					{if item.finalAuditResult==0}
						未审核
					{else}
						${item.finalAuditOpinionFeedback}
					{/if}
				{else}
					
				{/if}
			</td>
		</tr>
	</table>
	{/for}
</textarea>

<div id="view_variation" style="display:none;"></div>
