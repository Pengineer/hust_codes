<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_review_template" style="display:none;">
	<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_VIEW,ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEWAUDIT_VIEW">
		<div class="p_box_t">
			<div class="p_box_t_t">评审信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>				
		<div class="p_box_body">
			<span>{if awardApplication.applicantSubmitStatus != 3}保存时间{else}点击按提交时间排序{/if}：${awardApplication.applicantSubmitDate}</span>
			{if (reviewflag == 0 || reviewflag == -1) && accountType == "MINISTRY" && awardApplication.status > 5 && awardApplication.status > 3}
				<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_ADD">
					<span><input class="btn2 j_addReviewResult" type="button" value="录入评审" awardApplicationId="${awardApplication.id}"/></span>
				</sec:authorize>
				<span><input class="btn2 j_expertReviewResul" type="button" value="选择专家" awardApplicationId="${awardApplication.id}"/></span>
				
			{elseif reviewflag == 22 && accountType == "MINISTRY" && awardApplication.status > 5 && awardApplication.status > 3}
				<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_MODIFY">	
					<span><input class="btn2 j_modifyReviewResul" type="button" value="修改评审" awardApplicationId="${awardApplication.id}"/></span>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_ADD">	
					<span><input class="btn2 j_submitReviewResult" type="button" value="提交评审" awardApplicationId="${awardApplication.id}"/></span>
				</sec:authorize>
			{/if}	
		</div>
		<div>
			{if awardApplication.status == 6 && isReviewer > 0 && awardReview != null && (awardReview.submitStatus < 3 || isReviewer != 2)}
				<div class="p_box_body">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<thead>
							<tr class="head_title">
								<td colspan="6">专家评审情况</td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td >专家序号</td>
								<td >专家姓名</td>
								<td >评审分数</td>
								<td >建议等级</td>
								<td >提交状态</td>
								<td width="250">查看详细意见 </td>
							</tr>
							<tr class="table_tr4">
								<td>${awardReview.reviewerSn}</td>
								<td>${awardReview.reviewerName}</td>
								<td>${awardReview.score}</td>
								<td>${adviceGrade}</td>
								<td>
									{if awardReview.submitStatus == 0}待审
									{elseif awardReview.submitStatus == 2}保存
									{elseif awardReview.submitStatus == 3}已提交
									{/if}
								</td>
								<td width="250" align="center" >
									{if awardReview.submitStatus != 0}
										<span class="ico_txt2"><a class="view_review" href="" id="${awardReview.id}">查看详细意见</a></span>&nbsp;&nbsp;
									{/if}
									{if awardReview.submitStatus == 0 && awardApplication.finalAuditStatus != 3}
										<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_ADD">
											<input  class="btn1" type="button" id="add_review" value="评审"/>&nbsp;&nbsp;
										</sec:authorize>
									{elseif awardReview.submitStatus != 3 && awardApplication.finalAuditStatus != 3}
										<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_MODIFY">
											<input class="btn1" type="button" id="modify_review" value="修改" alt="${awardReview.id}" />&nbsp;&nbsp;
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_ADD">
											<input class="btn1" type="button" id="submit_review" value="提交" alt="${awardReview.id}"/>&nbsp;&nbsp;
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			{/if}
			{if (isReviewer == 2 && awardReview.submitStatus == 3) || accountType < 3}
				<div class="p_box_body">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<thead>
							<tr class="head_title">
								<td colspan="6">专家评审情况</td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td >专家序号</td>
								<td >专家姓名</td>
								<td >评审分数</td>
								<td >建议等级</td>
								<td >提交状态</td>
								<td width="250">查看详细意见 </td>
							</tr>
							{if awardReviews != null}
								{for item in awardReviews}
									<tr class="table_tr4">
										<td>${item[1]}</td>
										<td>${item[2]}</td>
										<td>${item[3]}</td>
										<td>${item[4]}</td>
										<td>{if item[5]==0}待审
											{elseif item[5]==2}保存
											{elseif item[5]==3}已提交{/if}</td>
										<td>
											{if item[5]!=0}
												<span class="ico_txt2"><a class="view_review" href="" id="${item[0]}">查看详细意见</a></span>
											{/if}
										</td>
									</tr>
								{/for}
							{/if}
							<!-- 评审汇总信息 -->
							<tr class="table_tr4">
								<td>小组评审结果</td>
								<td>${awardApplication.reviewerName}</td>
								<td>${awardApplication.reviewAverageScore}</td>
								<td>${adviceGroupGrade}</td>
								<td>
									{if awardApplication.reviewStatus == 0}待审
									{elseif awardApplication.reviewStatus == 2 && awardApplication.reviewResult == 1}不同意（暂存）
									{elseif awardApplication.reviewStatus == 2 && awardApplication.reviewResult == 2}同意（暂存）
									{elseif awardApplication.reviewStatus == 3 && awardApplication.reviewResult == 1}不同意
									{elseif awardApplication.reviewStatus == 3 && awardApplication.reviewResult == 2}同意
									{/if}
								</td>
								<td>
									{if awardApplication.reviewStatus != 0 || awardApplication.isImported == 1}
										<span class="ico_txt2"><a id="view_group_review" href="">查看详细意见</a></span>
									{/if}
									{if allReviewSubmit == 0 && awardApplication.status == 6 && awardApplication.finalAuditStatus != 3}
										{if awardApplication.reviewStatus == 0}
											<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_ADD">
												<input  class="btn1" type="button" id="add_group_review" value="评审"/>
											</sec:authorize>
										{elseif awardApplication.reviewStatus != 3}
											<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_MODIFY">
												<input class="btn1" type="button" id="modify_group_review" value="修改"/>
											</sec:authorize>
											<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEW_ADD">
												<input class="btn1" type="button" id="submit_group_review" value="提交"/>
											</sec:authorize>
										{/if}
									{/if}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			{/if}
			<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEWAUDIT_VIEW">
				<div class="p_box_body">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<thead>
							<tr class="head_title">
								<td colspan="6">评审审核情况</td>
							</tr>
						</thead>
						<tbody>
							<tr class="table_tr3">
								<td >审核结果</td>
								<td width="250">查看详细意见 </td>
							</tr>
							<tr class="table_tr4">
								<td align="center">
									{if awardApplication.reviewAuditStatus == 0}待审
									{elseif awardApplication.reviewAuditStatus == 2 && awardApplication.reviewAuditResult == 1}不同意（暂存）
									{elseif awardApplication.reviewAuditStatus == 2 && awardApplication.reviewAuditResult == 2}同意（暂存）
									{elseif awardApplication.reviewAuditStatus == 3 && awardApplication.reviewAuditResult == 1}不同意
									{elseif awardApplication.reviewAuditStatus == 3 && awardApplication.reviewAuditResult == 2}同意
									{/if}
								</td>
								<td width="250"  align="center" >
									{if awardApplication.reviewAuditStatus != 0 ||  awardApplication.isImported == 1}
										<span class="ico_txt2"><a id="view_review_audit" href="">查看详细意见</a></span>&nbsp;&nbsp;
									{/if}
									{if awardApplication.status == 7 && awardApplication.finalAuditStatus != 3}
										{if awardApplication.reviewAuditResult == 0}
											<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEWAUDIT_ADD">
												<input class="btn1" type="button" id="add_review_audit" value="审核"/>&nbsp;&nbsp;
											</sec:authorize>
										{elseif awardApplication.reviewAuditStatus != 3}
											<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEWAUDIT_MODIFY">
												<input class="btn1" type="button" id="modify_review_audit" value="修改"/>&nbsp;&nbsp;
											</sec:authorize>
											<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_REVIEWAUDIT_ADD">
												<input class="btn1" type="button" id="submit_review_audit" value="提交"/>&nbsp;&nbsp;
											</sec:authorize>
										{/if}
									{/if}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</sec:authorize>
		</div>
	</sec:authorize>		 	
	<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_VIEW">
		<div class="p_box_t">
			<div class="p_box_t_t">公示信息</div>
			<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
		</div>
		<div class="p_box_body">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
				<thead>
					<tr class="head_title">
						<td colspan="6">公示情况</td>
					</tr>
				</thead>
				<tbody>
					<tr class="table_tr3">
						<td>审核结果</td>
						<td width="250">查看详细意见 </td>
					</tr>
					<tr class="table_tr4">
						<td align="center">
							{if awardApplication.finalAuditStatus == 0}待审
							{elseif awardApplication.finalAuditStatus == 2 && awardApplication.finalAuditResult == 1}不同意（暂存）
							{elseif awardApplication.finalAuditStatus == 2 && awardApplication.finalAuditResult == 2}同意（暂存）
							{elseif awardApplication.finalAuditStatus == 3 && awardApplication.finalAuditResult == 1}不同意
							{elseif awardApplication.finalAuditStatus == 3 && awardApplication.finalAuditResult == 2}同意
							{/if}
						</td>
						<td width="250" align="center">
							{if awardApplication.finalAuditStatus != 0}
								<span class="ico_txt2"><a id="view_awarded_audit" href="">查看详细意见</a></span>&nbsp;&nbsp;
							{/if}
							{if awardApplication.status == 8 && awardApplication.finalAuditStatus == 0 }
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_ADD">
									<input class="btn1" type="button" id="add_awarded_audit" value="审核"/>&nbsp;&nbsp;
								</sec:authorize>
							{elseif awardApplication.status == 8 && awardApplication.finalAuditStatus != 3}
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_MODIFY">
									<input class="btn1" type="button" id="modify_awarded_audit" value="修改"/>&nbsp;&nbsp;
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_PUBLICITYAUDIT_ADD">
									<input class="btn1" id="submit_awarded_audit" type="button" value="提交" finalAuditResult="${awardApplication.finalAuditResult}" number="${awardApplication.number}" />&nbsp;&nbsp;
								</sec:authorize>
							{/if}
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</sec:authorize>
</textarea>
<div id="view_review" style="display:none;"></div>
