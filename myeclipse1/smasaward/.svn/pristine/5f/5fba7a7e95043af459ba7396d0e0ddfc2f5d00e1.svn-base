<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<!-- 项目申报业务标签页 -->
<div id="application">
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
				<td class="wd17">申报日期</td>
				<td class="wd18">${project.applyDate}</td>
			</tr>
		</table>
	
	
		<s:if test="isReviewable != 0">
		<!-- 评审专家 -->
		<table class="table_edit" cellspacing="0" cellpadding="0">
			<!-- 参评专家 -->
			{if pageList != null && pageList.length!=0}
			<tr>
				<td colspan="4" style="border:0 0 0 1px;">
					<table class="table_view" cellspacing="0" cellpadding="0">
						<tr><th class="thhead" colspan="8" style="border-width:0 0 1px 0;">评审专家</th>
							<th class="thhead" style="text-align:right; border-width:0 0 1px 0;">
								<a href="project/instp/toSelectExpert.action?entityId=${project.id}&businessType=${businessType}"><img title="选择评审专家" src="image/select_reviewer.gif" /></a>&nbsp;
							</th>
						</tr>
						<tr><td class="wd19" style="width:35px;border-left-style:hidden;">序号</td>
							<td class="wd19" style="width:75px;">姓名</td>
							<td class="wd19" style="width:100px;">高校名称</td>
							<td class="wd19" style="width:100px;">高校类别</td>
							<td class="wd19" style="width:80px;">职称</td>
							<td class="wd19" style="width:400px">学科代码及名称</td>
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
							<td style="border-width:1px 0 0 1px;">${expertInfo[item[0]]}</td>
							<td style="border-bottom-width:0px;"></td>
							<td style="border-bottom-width:0px;"></td>
							<td style="border-bottom-width:0px;"></td>
							</tr>
						{/for}
					</table>
				</td>
			</tr>
			{/if}
			<tr>
				<td colspan="4" class="wd20"  style="border-width:0 1px 1px 1px;">
					&nbsp;&nbsp;警告：<span class="text_red">${project.warningReviewer}</span>
				</td>
			</tr>
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
		
		<!-- 最终立项结果 -->
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
	
	<div id="view_application" style="display:none"></div>
</div>
