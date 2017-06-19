<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_awarded_template" style="display:none;">
	<div class="p_box_body">
		<table width="100%" cellspacing="0" cellpadding="2" bordercolor="#CCCCCC" style="border-collapse:collapse;">
			{if awardApplication.finalAuditStatus != 3 && awardApplication.status < 8}
				<tr class="table_tr4"><td>审核中！</td></tr>
			{elseif awardApplication.finalAuditStatus != 3 && awardApplication.status == 8}
				<tr class="table_tr4"><td>公示中！</td></tr>
			{elseif awardApplication.finalAuditStatus == 3 && awardApplication.finalAuditResult == 1}
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="72" align="right">获奖结果</td>
					<td class="title_bar_td" colspan='4'>该成果没有获奖！</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="72" align="right">审核意见：<br>（反馈给负责人）</td>
					<td class="title_bar_td" colspan='4'>{if awardApplication.finalAuditOpinionFeedback != null && awardApplication.finalAuditOpinionFeedback != ""}<pre>${awardApplication.finalAuditOpinionFeedback}</pre>{/if}</td>
				</tr>
			{elseif awardApplication.finalAuditStatus == 3 && awardApplication.finalAuditResult == 2 || awardApplication.reviewAuditResult == 2}
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="72" align="right">证书编号：</td>
					<td class="title_bar_td" width="250">${award.number}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="72" align="right">获奖等级：</td>
					<td class="title_bar_td">${awardGrade}</td>
				</tr>
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="72" align="right">获奖时间：</td>
					<td class="title_bar_td">${award.date}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="72" align="right">获奖年度：</td>
					<td class="title_bar_td">${award.year}</td>
				</tr>
				<tr>
					<!-- 研究人员或者部级管理员 -->
					<s:if test="#session.loginer.currentType.eauals(@csdc.tool.bean.AccountType@MINISTRY) || #session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
						<td class="title_bar_td" width="72" align="right">审核意见：<br><span>（反馈给负责人）</span></td>
						<td class="title_bar_td">{if awardApplication.finalAuditOpinionFeedback != null && awardApplication.finalAuditOpinionFeedback != ""}<pre>${awardApplication.finalAuditOpinionFeedback}</pre>{/if}</td>
					</s:if>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="72" align="right">获奖届次：</td>
					<td class="title_bar_td">${award.session}</td>
				</tr>
			{/if}
		</table>
	</div>
</textarea>
<div id="view_awarded" style="display:none;"></div>