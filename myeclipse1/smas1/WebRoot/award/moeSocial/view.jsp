<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>

<!-- 项目申请业务标签页 -->

<textarea id="view_application_template" style="display:none;">
	<table class="table_edit" cellspacing="0" cellpadding="0">
		<tr>
			<td class="wd17">添加日期</td>
			<td class="wd18">${awardApplication.createDate}</td>
			<td class="wd17">是否合格</td>
			<td class="wd18">{if awardApplication.isPassed==1}合格{else}不合格{/if}</td>
		</tr>
		<tr><td class="wd17">是否参与评审</td>
			<td class="wd18">{if awardApplication.isReviewable==1}是{else}否{/if}</td>
			<td class="wd17">申请年份</td>
			<td class="wd18">${awardApplication.year}</td>
		</tr>
	</table>
	
	<table class="table_edit" cellspacing="0" cellpadding="0">
		<tr>
			<th class="thhead" colspan="4" style="border-width:1px ;">评审分组</th>
		</tr>
		<tr>
			<td class="wd17">分组名称</td>
			<td class="wd18">${awardApplication.group}</td>
			<td class="wd17">组内编号</td>
			<td class="wd18">${awardApplication.number}</td>
		</tr>
	</table>
	
	<s:if test="isReviewable != 0">
		<table class="table_edit" cellspacing="0" cellpadding="0">
			<!-- 参评专家 -->
			{if pageList != null && pageList.length!=0}
			<tr>
				<td colspan="4" style="border:0 0 0 1px;">
					<table class="table_view" cellspacing="0" cellpadding="0">
						<tr>
							<th class="thhead" colspan="8" style="border-width:0 0 1px 0;">评审专家</th>
							<th class="thhead" style="text-align:right; border-width:0 0 1px 0;">
								<a class="isCurYear" href="award/moeSocial/toSelectExpert.action?entityId=${awardApplication.id}"><img title="选择评审专家" src="image/select_reviewer.gif" /></a>&nbsp;
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
							{if item[6] < 6}
							<tr class="wd22">{else}<tr>{/if}
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
					&nbsp;&nbsp;警告：<span class="text_red">${awardApplication.warningReviewer}</span>
				</td>
			</tr>
			{/if}
		</table>
			
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
		
		--%>
	</s:if>	
</textarea>

<div id="view_application" style="display:none;"></div>