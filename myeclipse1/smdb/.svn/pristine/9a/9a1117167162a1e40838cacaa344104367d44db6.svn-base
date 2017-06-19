<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_application_template" style="display:none;">
	<div class="p_box_b">
		<div class="p_box_b_1">
			<img src="image/ico09.gif" />
			<a href="" class="downlaod" name="${awardApplication.file}" id="${awardApplication.id}"><span>下载人文社科奖申请书</span></a>
		</div>
		<div class="p_box_b_2">
			<img src="image/ico09.gif" />
			<a href="" id="download_award_model"><span>下载人文社科奖申请书模板</span></a>
		</div>
	</div>
	<div class="p_box_t">
		<div class="p_box_t_t">申请信息</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<div class="p_box_body_t">
			<ul>
				<li>申请届次：${awardApplication.session}</li>
				<li>申请时间：${awardApplication.applicantSubmitDate}</li>
			</ul>
		</div>
	</div>
	<div class="p_box_t">
		<div class="p_box_t_t">获奖情况</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<div class="p_box_body_t">
			<pre>${awardApplication.prizeObtained}</pre>
		</div>
	</div>
	<div class="p_box_t">
		<div class="p_box_t_t">社会反映</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<div class="p_box_body_t">
			<pre>${awardApplication.response}</pre>
		</div>
	</div>
	<div class="p_box_t">
		<div class="p_box_t_t">引用或被采纳情况</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<div class="p_box_body_t">
			<pre>${awardApplication.adoption}</pre>
		</div>
	</div>
	<div class="p_box_t">
		<div class="p_box_t_t">内容简介</div>
		<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
	</div>
	<div class="p_box_body">
		<div class="p_box_body_t">
			<pre>${awardApplication.introduction}</pre>
		</div>
	</div>
	<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_VIEW">									
		<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0"><!-- 部门及以上管理人员 -->
			<div class="p_box_t">
				<div class="p_box_t_t">审核信息</div>
				<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
			</div>
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<!-- 部门审核信息 -->
					<tr class="table_tr4">
						<td width="%25">院系或研究基地意见</td>
						<td width="%25">
							{if awardApplication.deptInstAuditStatus == 0}<span class="ico_txt3">待审</span>
							{elseif awardApplication.deptInstAuditStatus == 1}<span class="ico_txt7">被退回</span>
							{elseif awardApplication.deptInstAuditStatus == 2 && awardApplication.deptInstAuditResult == 1}<span class="ico_txt8">不同意（暂存）</span>
							{elseif awardApplication.deptInstAuditStatus == 2 && awardApplication.deptInstAuditResult == 2}<span class="ico_txt4">同意（暂存）</span>
							{elseif awardApplication.deptInstAuditStatus == 3 && awardApplication.deptInstAuditResult == 1}<span class="ico_txt5">不同意</span>
							{elseif awardApplication.deptInstAuditStatus == 3 && awardApplication.deptInstAuditResult == 2}<span class="ico_txt1">同意</span>
							{/if}
						</td>
						<td width="%50">
							{if awardApplication.deptInstAuditStatus != 0 || (awardApplication.createMode == 1 || awardApplication.createMode == 2)}
								<span class="ico_txt2"><a id="view_dept_audit" href="">查看详细意见</a></span>&nbsp;&nbsp;
							{/if}
							{if (accountType == 6 || accountType == 7) && awardApplication.status == 2 && awardApplication.deptInstAuditStatus == 0 && awardApplication.finalAuditStatus != 3}
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
									<input class="btn1" type="button" id="audit" value="审核"/>&nbsp;&nbsp;
									<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
								</sec:authorize>
							{elseif (accountType == 6 || accountType == 7) && awardApplication.status == 2 && awardApplication.deptInstAuditStatus != 3 && awardApplication.finalAuditStatus != 3}
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_MODIFY">
									<input class="btn1" type="button" id="modify_audit" value="修改"/>&nbsp;&nbsp;
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
									<input class="btn1" type="button" id="submit_audit" value="提交"/>&nbsp;&nbsp;
									<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
								</sec:authorize>
							{/if}
						</td>
					</tr>
					<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0"><!-- 校级及以上管理人员 -->
						<tr class="table_tr4">
							<td width="%25">高校意见</td>
							<td width="%25">
								{if awardApplication.universityAuditStatus == 0}<span class="ico_txt3">待审</span>
								{elseif awardApplication.universityAuditStatus == 1}<span class="ico_txt7">被退回</span>
								{elseif awardApplication.universityAuditStatus == 2 && awardApplication.universityAuditResult == 1}<span class="ico_txt8">不同意（暂存）</span>
								{elseif awardApplication.universityAuditStatus == 2 && awardApplication.universityAuditResult == 2}<span class="ico_txt4">同意（暂存）</span>
								{elseif awardApplication.universityAuditStatus == 3 && awardApplication.universityAuditResult == 1}<span class="ico_txt5">不同意</span>
								{elseif awardApplication.universityAuditStatus == 3 && awardApplication.universityAuditResult == 2}<span class="ico_txt1">同意</span>
								{/if}
							</td>
							<td width="%50">
								{if awardApplication.universityAuditStatus != 0 || (awardApplication.createMode == 1 || awardApplication.createMode == 2)}
									<span class="ico_txt2"><a id="view_univ_audit" href="">查看详细意见</a></span>&nbsp;&nbsp;
								{/if}
								{if (accountType == "MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && awardApplication.status == 3 && awardApplication.universityAuditStatus == 0 && awardApplication.finalAuditStatus != 3}
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
										<input class="btn1" type="button" id="audit" value="审核"/>&nbsp;&nbsp;
										<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
									</sec:authorize>
								{elseif (accountType == "MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY") && awardApplication.status == 3 && awardApplication.universityAuditStatus != 3 && awardApplication.finalAuditStatus != 3}
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_MODIFY">
										<input class="btn1" type="button" id="modify_audit" value="修改"/>&nbsp;&nbsp;
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
										<input class="btn1" type="button" id="submit_audit" value="提交"/>&nbsp;&nbsp;
										<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
									</sec:authorize>
								{/if}
							</td>
						</tr>
					</s:if>
					{if universityType == 4}<!-- 地方高校 -->
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
							<tr class="table_tr4">
								<td width="%25">省厅意见</td>
								<td width="%25">
									{if awardApplication.provinceAuditStatus == 0}<span class="ico_txt3">待审</span>
									{elseif awardApplication.provinceAuditStatus == 1}<span class="ico_txt7">被退回</span>
									{elseif awardApplication.provinceAuditStatus == 2 && awardApplication.provinceAuditResult == 1}<span class="ico_txt8">不同意（暂存）</span>
									{elseif awardApplication.provinceAuditStatus == 2 && awardApplication.provinceAuditResult == 2}<span class="ico_txt4">同意（暂存）</span>
									{elseif awardApplication.provinceAuditStatus == 3 && awardApplication.provinceAuditResult == 1}<span class="ico_txt5">不同意</span>
									{elseif awardApplication.provinceAuditStatus == 3 && awardApplication.provinceAuditResult == 2}<span class="ico_txt1">同意</span>
									{/if}
								</td>
								<td width="%50">
									{if awardApplication.provinceAuditStatus != 0 || (awardApplication.createMode == 1 || awardApplication.createMode == 2)}
										<span class="ico_txt2"><a id="view_prov_audit" href="">查看详细意见</a></span>&nbsp;&nbsp;
									{/if}
									{if accountType == "PROVINCE" && awardApplication.status == 4 && awardApplication.provinceAuditStatus == 0 && awardApplication.finalAuditStatus != 3}
										<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
											<input class="btn1" type="button" id="audit" value="审核"/>&nbsp;&nbsp;
											<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
										</sec:authorize>
									{elseif accountType == "PROVINCE" && awardApplication.status == 4 && awardApplication.provinceAuditStatus != 3 && awardApplication.finalAuditStatus != 3}
										<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_MODIFY">
											<input class="btn1" type="button" id="modify_audit" value="修改"/>&nbsp;&nbsp;
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
											<input class="btn1" type="button" id="submit_audit" value="提交"/>&nbsp;&nbsp;
											<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
										</sec:authorize>
									{/if}
								</td>
							</tr>
						</s:if>
					{/if}
					<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0"><!-- 部级及以上管理人员 -->
						<tr class="table_tr4">
							<td width="%25">教育部意见</td>
							<td width="%25">
								{if awardApplication.ministryAuditStatus == 0}<span class="ico_txt3">待审</span>
								{elseif awardApplication.ministryAuditStatus == 1}<span class="ico_txt7">被退回</span>
								{elseif awardApplication.ministryAuditStatus == 2 && awardApplication.ministryAuditResult == 1}<span class="ico_txt8">不同意（暂存）</span>
								{elseif awardApplication.ministryAuditStatus == 2 && awardApplication.ministryAuditResult == 2}<span class="ico_txt4">同意（暂存）</span>
								{elseif awardApplication.ministryAuditStatus == 3 && awardApplication.ministryAuditResult == 1}<span class="ico_txt5">不同意</span>
								{elseif awardApplication.ministryAuditStatus == 3 && awardApplication.ministryAuditResult == 2}<span class="ico_txt1">同意</span>
								{/if}
							</td>
							<td width="%50">
								{if awardApplication.ministryAuditStatus != 0 || (awardApplication.createMode == 1 || awardApplication.createMode == 2)}
									<span class="ico_txt2"><a id="view_mini_audit" href="">查看详细意见</a></span>&nbsp;&nbsp;
								{/if}
								{if accountType == "MINISTRY" && awardApplication.status == 5 && awardApplication.ministryAuditStatus == 0 && awardApplication.finalAuditStatus != 3}
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
										<input class="btn1" type="button" id="audit" value="审核"/>&nbsp;&nbsp;
										<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
									</sec:authorize>
								{elseif accountType == "MINISTRY" && awardApplication.status == 5 && awardApplication.ministryAuditStatus != 3 && awardApplication.finalAuditStatus != 3}
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_MODIFY">
										<input class="btn1" type="button" id="modify_audit" value="修改"/>&nbsp;&nbsp;
									</sec:authorize>
									<sec:authorize ifAnyGranted="ROLE_AWARD_MOESOCIAL_APPLICATION_APPLYAUDIT_ADD">
										<input class="btn1" type="button" id="submit_audit" value="提交"/>&nbsp;&nbsp;
										<input class="btn1" type="button" id="back_audit" value="退回"/>&nbsp;&nbsp;
									</sec:authorize>
								{/if}
							</td>
						</tr>
					</s:if>
				</table>
			</div>
		</s:if>
	</sec:authorize>
</textarea>
<div id="view_application" style="display:none;"></div>
