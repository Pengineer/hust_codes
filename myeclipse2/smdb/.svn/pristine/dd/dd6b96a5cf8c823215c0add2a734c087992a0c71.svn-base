<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored ="true"%>
<textarea id="view_tops_template" style="display:none;">
	<s:hidden value="${topsInfo[1]}" id="deadlineTops" />
	<s:hidden name="topsStatus" id="topsStatus" />
	<s:hidden name="appFlag" id="appFlag"/>
	<div class="p_box_b">
		<div class="p_box_b_1">
			{if topicSelection.isImported==0}
				{if appFlag == 1}
					{if topicSelection.applicantSubmitStatus!=3}保存时间{else}提交时间{/if}：${topicSelection.applicantSubmitDate}
				{else}
					{if topicSelection.universitySubmitStatus!=3}保存时间{else}提交时间{/if}：${topicSelection.universitySubmitDate}
				{/if}
			{elseif topicSelection.isImported==1}
				<span class="import_css">该数据为导入数据</span>
			{else}
			{/if}
		</div>
	</div>
	<div class="p_box_body">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			<tr class="table_tr7">
				<td class="key" width="100">课题名称：</td>
				<td class="value" colspan='3'>${topicSelection.name}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key" width="100">英文名称：</td>
				<td class="value" colspan='3'>${topicSelection.englishName}</td>
			</tr>
			<tr class="table_tr7">
				<td class="key" width="100">项目课题来源：</td>
				<td class="value" width="200">
					{if topicSelection.topicSource == 0}教育部
					{elseif topicSelection.topicSource == 1}高校
					{elseif topicSelection.topicSource == 2}专家
					{/if}
				</td>
				<td class="key" width="120">申报时间：</td>
				{if null != topicSelection.applicantId && topicSelection.applicantId !=""}
				<td class="value">${topicSelection.applicantSubmitDate}</td>
				{else}<td class="value">${topicSelection.universitySubmitDate}</td>
				{/if}
			</tr>
			{if null != topicSelection.applicantId && topicSelection.applicantId !=""}
			<tr class="table_tr7">
				<td class="key" width="100">申请人：</td>
				<td class="value" colspan='3'><s:hidden id="directors" name="${topicSelection.applicantName}" value="${topicSelection.applicantId}" cssClass="directors"/></td>
			</tr>
			{/if}
			{if null != universityId && universityId !=""}
			<tr class="table_tr7">
				<td class="key" width="100">依托高校：</td>
				<td class="value" width="200"><a id="${universityId}" class="linkUni" href="">${universityName}</a></td>
				<td class="key" width="120">项目名称：</td>
				<td class="value"><a id="${projectId}" class="popProject" href="">${projectName}</a></td>
				<s:hidden id="projectTypeId" value="${projectTypeId}"/>
			</tr>
			{/if}
			<tr class="table_tr7">
				<td class="key">简要论证：</td>
				<td class="value" colspan="3">${topicSelection.summary}</td>
			</tr>
		</table>
	</div>
	<!-- 研究人员申报选题 -->
	<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
		{if (topicSelection.applicantSubmitStatus == 1 || topicSelection.applicantSubmitStatus == 2)}<!-- 选题申报暂存或退回 -->
			<div id="addProductTops" class="p_box_body">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr height="36">
						<td width="150">
							提交状态：{if topicSelection.applicantSubmitStatus == 1}退回{else}保存{/if}
						</td>
						<td align="right">
							<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_MODIFY">
								<input type="button" value="修改" onclick="toModifyTopsApply();" class="btn1"/>&nbsp;
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_ADD">
								<input type="button" value="提交" onclick="submitTopsApply();" class="btn1"/>&nbsp;
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_DELETE">
								<input type="button" value="删除" onclick="deleteTopsApply();" class="btn1"/>
							</sec:authorize>
						</td>
					</tr>
				</table>
			</div>
		{/if}
	</s:if>
	{if appFlag == 1}
	<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 "><!-- 部门及以上管理人员审核专家申报选题 -->
		<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_VIEW'>
			<div class="p_box_body">
				<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<thead>
						<tr class="head_title">
							<td colspan="3">各级审核情况</td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td>各级管理部门意见</td>
							<td width="30%">是否采纳课题</td>
							<td width="300">查看详情</td>
						</tr>
						<!-- 部门审核信息 -->
						<tr class="table_tr4">
							<td>院系或研究基地意见</td>
							<td>
								{if topicSelection.deptInstAuditResult == 1}
									{if topicSelection.deptInstAuditStatus == 3}<span class="ico_txt5">不同意</span>
									{elseif topicSelection.deptInstAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
									{elseif topicSelection.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
									{/if}
								{elseif topicSelection.deptInstAuditResult == 2}
									{if topicSelection.deptInstAuditStatus == 3}<span class="ico_txt1">同意</span>
									{elseif topicSelection.deptInstAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
									{elseif topicSelection.deptInstAuditStatus == 1}<span class="ico_txt7">退回</span>
									{/if}
								{elseif topicSelection.deptInstAuditResult == 0}<span class="ico_txt3">待审</span>
								{/if}
							</td>
							<td>
								<!-- 非导入数据、部门账号、最近一次选题处在部门审核级别 -->
								{if topicSelection.isImported == 0 && topicSelection.status == 2 && topicSelection.finalAuditStatus != 3 && (accountType == "DEPARTMENT" || accountType == "INSTITUTE")}
									{if topicSelection.deptInstAuditStatus == 0}
										<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
											<input class="btn1" type="button" value="审核" onclick="addTopsAuditPop();"/>
											<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
										</sec:authorize>
									{elseif topicSelection.deptInstAuditStatus < 3}
										<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 1);" style="cursor: pointer;">查看详情</a></span>
										{if (isPrincipal == 1 && (topsAuditorInfo[1] == belongId || topsAuditorInfo[2] == belongId)) || (isPrincipal == 0 && topsAuditorInfo[0] == belongId)}
										<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_MODIFY'>
											<input class="btn1" type="button" value="修改" onclick="modifyTopsAuditPop();"/>
										</sec:authorize>
										<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
											<input class="btn1" type="button" value="提交" onclick="submitTopsAudit();"/>
											<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
										</sec:authorize>
										{/if}
									{/if}
								{else}
									<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 1);" style="cursor: pointer;">查看详情</a></span>
								{/if}
							</td>
						</tr>
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0"><!-- 校级及以上管理人员 -->
							<tr class="table_tr4">
								<td>高校意见</td>
								<td>
									{if topicSelection.universityAuditResult == 1}
										{if topicSelection.universityAuditStatus == 3}<span class="ico_txt5">不同意</span>
										{elseif topicSelection.universityAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
										{elseif topicSelection.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif topicSelection.universityAuditResult == 2}
										{if topicSelection.universityAuditStatus == 3}<span class="ico_txt1">同意</span>
										{elseif topicSelection.universityAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
										{elseif topicSelection.universityAuditStatus == 1}<span class="ico_txt7">退回</span>
										{/if}
									{elseif topicSelection.universityAuditResult == 0}<span class="ico_txt3">待审</span>
									{/if}
								</td>
								<td>
									<!-- 非导入数据、高校账号、最近一次选题处在高校审核级别 -->
									{if topicSelection.isImported == 0 && topicSelection.status == 3 && topicSelection.finalAuditStatus != 3 && (accountType =="MINISTRY_UNIVERSITY" || accountType == "LOCAL_UNIVERSITY")}
										{if topicSelection.universityAuditStatus == 0}
											<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
												<input class="btn1" type="button" value="审核" onclick="addTopsAuditPop();"/>
												<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
											</sec:authorize>
										{elseif topicSelection.universityAuditStatus < 3}
											<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 2);" style="cursor: pointer;">查看详情</a></span>
											{if (isPrincipal == 1 && topsAuditorInfo[4] == belongId) || (isPrincipal == 0 && topsAuditorInfo[3] == belongId)}
											<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_MODIFY'>
												<input class="btn1" type="button" value="修改" onclick="modifyTopsAuditPop();"/>
											</sec:authorize>
											<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
												<input class="btn1" type="button" value="提交" onclick="submitTopsAudit();"/>
												<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
											</sec:authorize>
											{/if}
										{/if}
									{else}
										<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 2);" style="cursor: pointer;">查看详情</a></span>
									{/if}
								</td>
							</tr>
						</s:if>
						{if utype == 0}<!-- 地方高校 -->
							<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
								<tr class="table_tr4">
									<td>省厅意见</td>
									<td>
										{if topicSelection.provinceAuditResult == 1}
											{if topicSelection.provinceAuditStatus == 3}<span class="ico_txt5">不同意</span>
											{elseif topicSelection.provinceAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
											{elseif topicSelection.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif topicSelection.provinceAuditResult == 2}
											{if topicSelection.provinceAuditStatus == 3}<span class="ico_txt1">同意</span>
											{elseif topicSelection.provinceAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
											{elseif topicSelection.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
											{/if}
										{elseif topicSelection.provinceAuditResult == 0}<span class="ico_txt3">待审</span>
										{/if}
									</td>
									<td>
										<!-- 非导入数据、省厅账号、最近一次选题处在省厅审核级别 -->
										{if topicSelection.isImported == 0 && topicSelection.status == 4 && topicSelection.finalAuditStatus != 3 && accountType == "PROVINCE"}
											{if topicSelection.provinceAuditStatus == 0}
												<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
													<input class="btn1" type="button" value="审核" onclick="addTopsAuditPop();"/>
													<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
												</sec:authorize>
											{elseif topicSelection.provinceAuditStatus < 3}
												<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 3);" style="cursor: pointer;">查看详情</a></span>
												{if (isPrincipal == 1 && topsAuditorInfo[6] == belongId) || (isPrincipal == 0 && topsAuditorInfo[5] == belongId)}
												<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_MODIFY'>
													<input class="btn1" type="button" value="修改" onclick="modifyTopsAuditPop();"/>
												</sec:authorize>
												<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
													<input class="btn1" type="button" value="提交" onclick="submitTopsAudit();"/>
													<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
												</sec:authorize>
												{/if}
											{/if}
										{else}
											<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 3);" style="cursor: pointer;">查看详情</a></span>
										{/if}
									</td>
								</tr>
							</s:if>
						{/if}
					</tbody>
				</table>
			</div>
		</sec:authorize>
	</s:elseif>
	{else}
		<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
		{if (topicSelection.universitySubmitStatus == 1 || topicSelection.universitySubmitStatus == 2)}<!-- 高校选题申报暂存或退回 -->
			<div class="p_box_body">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
					<tr height="36">
						<td width="150">
							提交状态：{if topicSelection.universitySubmitStatus == 1}退回{else}保存{/if}
						</td>
						<td align="right">
							<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_MODIFY">
								<input type="button" value="修改" onclick="toModifyTopsApply();" class="btn1"/>&nbsp;
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_ADD">
								<input type="button" value="提交" onclick="submitTopsApply();" class="btn1"/>&nbsp;
							</sec:authorize>
							<sec:authorize ifAllGranted="ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLY_DELETE">
								<input type="button" value="删除" onclick="deleteTopsApply();" class="btn1"/>
							</sec:authorize>
						</td>
					</tr>
				</table>
			</div>
		{/if}
		</s:if>
		{if utype == 0}<!-- 地方高校 -->
			<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0"><!-- 省厅及以上管理人员 -->
				<tr class="table_tr4">
					<td>省厅意见</td>
					<td>
						{if topicSelection.provinceAuditResult == 1}
							{if topicSelection.provinceAuditStatus == 3}<span class="ico_txt5">不同意</span>
							{elseif topicSelection.provinceAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
							{elseif topicSelection.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
							{/if}
						{elseif topicSelection.provinceAuditResult == 2}
							{if topicSelection.provinceAuditStatus == 3}<span class="ico_txt1">同意</span>
							{elseif topicSelection.provinceAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
							{elseif topicSelection.provinceAuditStatus == 1}<span class="ico_txt7">退回</span>
							{/if}
						{elseif topicSelection.provinceAuditResult == 0}<span class="ico_txt3">待审</span>
						{/if}
					</td>
					<td>
						<!-- 非导入数据、省厅账号、最近一次选题处在省厅审核级别 -->
						{if topicSelection.isImported == 0 && topicSelection.status == 4 && topicSelection.finalAuditStatus != 3 && accountType == "PROVINCE"}
							{if topicSelection.provinceAuditStatus == 0}
								<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
									<input class="btn1" type="button" value="审核" onclick="addTopsAuditPop();"/>
									<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
								</sec:authorize>
							{elseif topicSelection.provinceAuditStatus < 3}
								<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 3);" style="cursor: pointer;">查看详情</a></span>
								{if (isPrincipal == 1 && topsAuditorInfo[6] == belongId) || (isPrincipal == 0 && topsAuditorInfo[5] == belongId)}
								<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_MODIFY'>
									<input class="btn1" type="button" value="修改" onclick="modifyTopsAuditPop();"/>
								</sec:authorize>
								<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
									<input class="btn1" type="button" value="提交" onclick="submitTopsAudit();"/>
									<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
								</sec:authorize>
								{/if}
							{/if}
						{else}
							<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 3);" style="cursor: pointer;">查看详情</a></span>
						{/if}
					</td>
				</tr>
			</s:if>
		{/if}
	{/if}
	<div class="p_box_body">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0"><!-- 部级及以上管理人员 -->
				<thead>
					<tr class="head_title">
						<td colspan="3">最终选题结果</td>
					</tr>
				</thead>
				<tbody>
					<tr class="table_tr3">
						<td>审核时间</td>
						<td width="30%">是否采纳课题</td>
						<td width="300">查看详情</td>
					</tr>
					<tr class="table_tr4">
						<td>
							${topicSelection.finalAuditDate}
						</td>
						<td>
							{if topicSelection.finalAuditResult == 1}
								{if topicSelection.finalAuditStatus == 3}<span class="ico_txt5">不同意</span>
								{elseif topicSelection.finalAuditStatus == 2}<span class="ico_txt8">不同意（暂存）</span>
								{elseif topicSelection.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
								{/if}
							{elseif topicSelection.finalAuditResult == 2}
								{if topicSelection.finalAuditStatus == 3}<span class="ico_txt1">同意</span>
								{elseif topicSelection.finalAuditStatus == 2}<span class="ico_txt4">同意（暂存）</span>
								{elseif topicSelection.finalAuditStatus == 1}<span class="ico_txt7">退回</span>
								{/if}
							{elseif topicSelection.finalAuditResult == 0}<span class="ico_txt3">待审</span>
							{/if}
						</td>
						<td>
							<!-- 非导入数据、教育部账号、最近一次选题处在教育部审核级别 -->
							{if topicSelection.isImported == 0 && topicSelection.status == 5 && topicSelection.finalAuditStatus != 3 && accountType == "MINISTRY"}
								{if topicSelection.finalAuditStatus == 0}
									<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
										<input class="btn1" type="button" value="审核" onclick="addTopsAuditPop();"/>
										<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
									</sec:authorize>
								{elseif topicSelection.finalAuditStatus < 3}
									<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_VIEW'>
										<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 4);" style="cursor: pointer;">查看详情</a></span>
									</sec:authorize>
									{if (isPrincipal == 1 && topsAuditorInfo[8] == belongId) || (isPrincipal == 0 && topsAuditorInfo[7] == belongId)}
									<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_MODIFY'>
										<input class="btn1" type="button" value="修改" onclick="modifyTopsAuditPop();"/>
									</sec:authorize>
									<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_ADD'>
										<input class="btn1" type="button" value="提交" onclick="submitTopsAudit();"/>
										<input class="btn1" type="button" value="退回" onclick="backTopsApply();"/>
									</sec:authorize>
									{/if}
								{/if}
							{else}
								<sec:authorize ifAllGranted='ROLE_PROJECT_KEY_TOPIC_SELECTION_APPLYAUDIT_VIEW'>
									<span class="ico_txt2"><a onclick="viewTopsAuditPop('${topicSelection.id}', 4);" style="cursor: pointer;">查看详情</a></span>
								</sec:authorize>
							{/if}
						</td>
					</tr>
				</tbody>
			</s:if>
			<s:else><!-- 部级以下所有人员 -->
				{if topicSelection.isImported == 1 || topicSelection.applicantSubmitStatus == 3 || topicSelection.universitySubmitStatus == 3}
					<thead>
						<tr class="head_title">
							<td colspan="2">最终选题结果</td>
						</tr>
					</thead>
					<tbody>
						<tr class="table_tr3">
							<td width="50%">审核时间</td>
							<td width="50%">是否采纳课题</td>
						</tr>
						<tr class="table_tr4">
							<td>{if topicSelection.finalAuditStatus == 3}${topicSelection.finalAuditDate}{/if}</td>
							<td>
								{if topicSelection.finalAuditStatus == 3 && topicSelection.finalAuditResult == 2}<span class="ico_txt1">同意</span>
								{elseif topicSelection.finalAuditStatus == 3 && topicSelection.finalAuditResult == 1}<span class="ico_txt5">不同意</span>
								{else}<span class="ico_txt3">待审</span>
								{/if}
							</td>
						</tr>
					</tbody>
				{/if}
			</s:else>
		</table>
	</div>
</textarea>
<div id="view_tops" style="display:none;"></div>
<s:form id="topsForm" theme="simple">
	<s:hidden id="topsId" name="topsId" />
	<s:hidden id="topsAuditStatus" name="topsAuditStatus"/>
	<s:hidden id="topsAuditOpinion" name="topsAuditOpinion"/>
	<s:hidden id="topsAuditResult" name="topsAuditResult"/>
</s:form>
