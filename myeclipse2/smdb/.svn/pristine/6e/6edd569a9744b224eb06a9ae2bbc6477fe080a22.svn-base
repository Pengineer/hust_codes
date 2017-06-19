<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<textarea id="view_basic_template" style="display:none;">
{if awardApplication.status == 1 && awardApplication.applicantSubmitStatus != 3}
	<div style="height:20px;clear:both;">
	</div>
	<div class="p_box_b">
		<div class="p_box_b_1">
				<img src="image/ico09.gif" />
				<a href=""  class="downlaod" name="${awardApplication.file}" id="${awardApplication.id}">
				<span>下载人文社科奖申请书</span></a>
		</div>
		<div class="p_box_b_2"><img src="image/ico09.gif" /><a href="" id="download_award_model"><span>下载人文社科奖申请书模板</span></a></div>
	</div>
{/if}
	<div class="main_content">
		<div class="title_bar">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">成果名称：</td>
					<td class="title_bar_td" width="120">${awardApplication.productName}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">成果类型：</td>
					<td class="title_bar_td" width="120">${ptypeName}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">学科门类：</td>
					<td class="title_bar_td">${awardApplication.disciplineType}</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">申请人：</td>
					<td class="title_bar_td" width="120">
						{if awardApplication.product != null}
							<a id="${product.id}" class="viewOrganization" href="" title="点击查看详细信息">${product.orgName}</a>
						{elseif applicantId != null && applicantId != ""}
							<a id="${applicantId}" class="viewapplicant" href="" title="点击查看详细信息">${awardApplication.applicantName}</a>
						{else}
							${awardApplication.applicantName}
						{/if}
					</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">依托高校：</td>
					<td class="title_bar_td" width="120">
						{if universityId != null && universityId != ""}
							<a id="${universityId}" class="viewuniversity" href="" title="点击查看详细信息">${awardApplication.agencyName}</a>
						{else}
							${awardApplication.agencyName}
						{/if}
					</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" width="64" align="right">依托院系或研究基地：</td>
					<td class="title_bar_td" >
						{if departmentId != null && departmentId !=""}
							<a id="${departmentId}" class="linkDep" href="">${awardApplication.divisionName }</a>
						{elseif instituteId != null && instituteId !=""}
							<a id="${instituteId}" class="linkIns" href="">${awardApplication.divisionName }</a>
						{else}${awardApplication.divisionName}
						{/if}
					</td>
				</tr>
				{if awardApplication.status == 1 && awardApplication.applicantSubmitStatus != 3}
				<tr>
					<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">申请届次：</td>
					<td class="title_bar_td">${awardApplication.session}</td>
					<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
					<td class="title_bar_td" align="right">保存时间：</td>
					<td class="title_bar_td">${awardApplication.applicantSubmitDate}</td>
				</tr>
				{/if}
			</table>
		</div>
		{if awardApplication.status == 1 && awardApplication.applicantSubmitStatus != 3}
		<div class="p_box">
			<div id="apply">
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
			</div>
		</div>
		{/if}
	</div>
</textarea>
<div id="view_basic" style="display:none;"></div>
