<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<!-- 项目申请业务标签页 -->

<textarea id="view_application_template" style="display:none;">

	
	<table class="table_edit" cellspacing="0" cellpadding="0">
		<tr>
			<td class="wd17">添加日期</td>
			<td class="wd18">${project.addDate}</td>
			<td class="wd17">审核状态</td>
			<td class="wd18">${project.auditStatus}</td>
		</tr>
		<tr><td class="wd17">是否参与评审</td>
			<td class="wd18">{if project.isReviewable == 1}是{else}否{/if}</td>
			<td class="wd17">申请日期</td>
			<td class="wd18">${project.applyDate}</td>
		</tr>
	</table>

	<s:if test="isReviewable != 0">
		<table class="table_edit" cellspacing="0" cellpadding="0">
			
			<!-- 参评专家 -->
			{if pageList != null && pageList.length!=0}
			<tr>
				<td colspan="4" style="border:0 0 0 1px;">
					<table class="table_view" cellspacing="0" cellpadding="0">
						<tr><th class="thhead" colspan="8" style="border-width:0 0 1px 0;">评审专家</th>
							<th class="thhead" style="text-align:right; border-width:0 0 1px 0;">
								<a class="isCurYear" href="project/general/toSelectExpert.action?entityId=${project.id}&businessType=${businessType}"><img title="选择评审专家" src="image/select_reviewer.gif" /></a>&nbsp;
							</th>
						</tr>
						<tr><td class="wd19" style="width:35px;border-left-style:hidden;">序号</td>
							<td class="wd19" style="width:55px;">姓名</td>
							<td class="wd19" style="width:90px;">高校名称</td>
							<td class="wd19" style="width:60px;">高校类别</td>
							<td class="wd19" style="width:70px;">职称</td>
							<td class="wd19" style="width:500px">学科代码及名称</td>
							<td class="wd19" style="width:50px">分数</td>
							<td class="wd19" style="width:50px">等级</td>
							<td class="wd19" style="">查看详情</td>
						</tr>
						<div style="display: none;">${num = 0}</div>
						{for item in pageList}
							{if item[6] < 6}<tr class="wd22">{else}<tr>{/if}
							<td style="border-bottom-width:0px;border-left-style:hidden;">${num = num + 1}</td>
							<td style="border-bottom-width:0px;"><a href="expert/toView.action?entityId=${item[0]}&listType=1" title="点击查看详细信息">${item[1]}</a></td>
							<td style="border-bottom-width:0px;">${item[2]}</td>
							<td style="border-bottom-width:0px;">{if item[4] == '308' || item[4] =='339'|| item[4] =='360'|| item[4] =='435'}部属高校{else}地方高校{/if}</td>
							<td style="border-bottom-width:0px;">${item[3]}</td>
							<td style="border-bottom-width:0px;">${expertInfo[item[0]]}</td>
							<td style="border-bottom-width:0px;"></td>
							<td style="border-bottom-width:0px;"></td>
							<td style="border-width:1px 0 0 1px;"></td>
							</tr>
						{/for}
							
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="wd20"  style="border-width:0 1px 1px 1px;">
					&nbsp;&nbsp;警告：<span class="text_red">${project.warningReviewer}</span>
				</td>
			</tr>
			{/if}
			
			<!-- 举报信息 -->
			{if project.informTitle != null && project.informTitle != ""}
			<tr><th class="thhead" colspan="4">举报信息</th></tr>
			<tr><td class="wd17">举报标题</td>
				<td class="wd21" colspan="3">${project.informTitle}</td>
			</tr>
			<tr><td class="wd17">举报人</td>
				<td class="wd18">${project.informer}</td>
				<td class="wd17">举报时间</td>
				<td class="wd18">${project.informDate}</td>
			</tr>
			<tr><td class="wd17">举报人电话</td>
				<td class="wd18">${project.informerPhone}</td>
				<td class="wd17">举报人邮箱</td>
				<td class="wd18">${project.informerEmail}</td>
			</tr>
			<tr>
				<td class="wd17">举报事由</td>
				<td class="wd21" colspan="3">${project.informContent}</td>
			</tr>
			{/if}
		</table>
		
		<!-- 申请经费相关 -->
		{if projectFeeApply != null}
			<table class="table_edit" cellspacing="0" cellpadding="0">
				<tr>
					<td class="wd17">批准经费</td>
					<td class="wd18">${projectFeeApply.approveFee}</td>
					<td class="wd17">已拨经费</td>
					<td class="wd18">${projectFeeApply.fundedFee}</td>
					<td class="wd17">未拨经费</td>
					<td class="wd18">${projectFeeApply.toFundFee}</td>
				</tr>
			</table>
			<table class="table_edit" cellspacing="0" cellpadding="0" style="margin-top:0px;">
				<tr><th class="wd17" colspan="3" style="text-align:center;border-top:0px;">申请经费明细</th></tr>
				<tr>
					<td class="wd17" style="width:35%;text-align:center;">经费科目</td>
					<td class="wd17" style="width:15%;text-align:center;">经费支出金额（万元）</td>
					<td class="wd17" style="width:35%;text-align:center;">开支说明</td>
				</tr>
				<tr>
					<td class="wd17">图书资料费</td>
					<td class="wd18">${projectFeeApply.bookFee}</td>
					<td class="wd18">${projectFeeApply.bookNote}</td>
				</tr>
				<tr>
					<td class="wd17">数据采集费</td>
					<td class="wd18">${projectFeeApply.dataFee}</td>
					<td class="wd18">${projectFeeApply.dataNote}</td>
				</tr>
				<tr>
					<td class="wd17">调研差旅费</td>
					<td class="wd18">${projectFeeApply.travelFee}</td>
					<td class="wd18">${projectFeeApply.travelNote}</td>
				</tr>
				<tr>
					<td class="wd17">会议费</td>
					<td class="wd18">${projectFeeApply.conferenceFee}</td>
					<td class="wd18">${projectFeeApply.conferenceNote}</td>
				</tr>
				<tr>
					<td class="wd17">国际合作交流费</td>
					<td class="wd18">${projectFeeApply.internationalFee}</td>
					<td class="wd18">${projectFeeApply.internationalNote}</td>
				</tr>
				<tr>
					<td class="wd17">设备购置和使用费</td>
					<td class="wd18">${projectFeeApply.deviceFee}</td>
					<td class="wd18">${projectFeeApply.deviceNote}</td>
				</tr>
				<tr>
					<td class="wd17">专家咨询和评审费</td>
					<td class="wd18">${projectFeeApply.consultationFee}</td>
					<td class="wd18">${projectFeeApply.consultationNote}</td>
				</tr>
				<tr>
					<td class="wd17">助研津贴和劳务费</td>
					<td class="wd18">${projectFeeApply.laborFee}</td>
					<td class="wd18">${projectFeeApply.laborNote}</td>
				</tr>
				<tr>
					<td class="wd17">印刷和出版费</td>
					<td class="wd18">${projectFeeApply.printFee}</td>
					<td class="wd18">${projectFeeApply.printNote}</td>
				</tr>
				<tr>
					<td class="wd17">间接费用</td>
					<td class="wd18">${projectFeeApply.indirectFee}</td>
					<td class="wd18">${projectFeeApply.indirectNote}</td>
				</tr>
				<tr>
					<td class="wd17">其他</td>
					<td class="wd18">${projectFeeApply.otherFee}</td>
					<td class="wd18">${projectFeeApply.otherNote}</td>
				</tr>
				<tr>
					<td class="wd17">合计</td>
					<td class="wd18" colspan="2">${projectFeeApply.totalFee}</td>
				</tr>
				<tr>
					<td class="wd17">相关说明</td>
					<td colspan="2" class="wd18">${projectFeeApply.feeNote}</td>
				</tr>
			</table>
		{/if}
			
		<%--<!-- 专家评审情况 -->
		{if applicationReviews != null && applicationReviews.length != 0}
			<table class="table_edit table_view" cellspacing="0" cellpadding="0">
				<tr><th class="thhead" colspan="5">专家评审情况</th></tr>
				<tr>
					<td class="wd19" style="width:20%;">序号</td>
					<td class="wd19" style="width:20%;">姓名</td>
					<td class="wd19" style="width:20%;">分数</td>
					<td class="wd19" style="width:20%;">等级</td>
					<td class="wd19" style="width:20%;">查看详情</td>
				</tr>
				<div style="display: none;">${num = 0}</div>
				{for item in applicationReviews}
				<tr>
					<td>${num = num + 1}</td>
					<td>${item[2]}</td>
					<td>${item[3]}</td>
					<td>${item[4]}</td>
					<td>查看详情</td>
				</tr>
				{/for}
			</table>
		{/if}
		
		--%><!-- 最终立项结果 -->
		<table class="table_edit table_view" cellspacing="0" cellpadding="0">
			<tr><th class="thhead" colspan="3">最终立项结果</th></tr>
			<tr>
				<td class="wd19" style="width:33%;">审核时间</td>
				<td class="wd19" style="width:33%;">是否同意立项</td>
				<td class="wd19" style="width:33%;">审核状态</td>
			</tr>
			<tr>
				<td>${project.finalAuditDate}</td>
				<td>
				{if project.finalAuditResult == 1}
					不同意
				{elseif project.finalAuditResult == 2}
					同意
				{elseif project.finalAuditResult == 0}待审
				{/if}
				</td>
				<td>
					{if project.finalAuditResult==0}
						<input class="btn appAudit" type="button" appId="${project.id}" value="审核" />
					{else}
						已审核
					{/if}
				</td>
			</tr>
			<tr>
				<td class="wd19">审核意见：</td>
				<td colspan="2">
					{if project.finalAuditResult==0}
						未审核
					{else}
						${project.finalAuditOpinion}
					{/if}
				</td>
			</tr>
			<tr>
				<td class="wd19">
					审核意见：<br>(反馈给负责人)
				</td>
				<td colspan="2">
					{if project.finalAuditResult==0}
						未审核
					{else}
					${project.finalAuditOpinionFeedback}
					{/if}
				</td>
			</tr>
		</table>
	</s:if>	
</textarea>

<div id="view_application" style="display:none;"></div>
