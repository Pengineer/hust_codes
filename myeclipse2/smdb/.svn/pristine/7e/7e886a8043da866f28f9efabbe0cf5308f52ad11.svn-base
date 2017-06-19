<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/project/project_share/adv_search.js', function(adv_search) {
		$(function(){
			adv_search.init();
			adv_search.valid();
			adv_search.initPop();
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/project/general/application/review">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr style="height:36px;">
				<td align="right">
					<span class="choose_bar">
						<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@EXPERT) || #session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER)">
							<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
								list="#{'0114':getText('i18n_ToReview')}" />
						</s:if>
							<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
								list="#{'1':getText('i18n_ProjectName'),'2':getText('i18n_University'),'3':getText('i18n_ProjectSubtype'),'4':getText('i18n_DisciplineType'),'5':getText('i18n_ProjectYear')}" />
					</span>
					<s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
				</td>
				<td width="60"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="<s:text name='i18n_MoreTerms' />" class="btn2" /></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/general/application/review">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" /> 
		<s:hidden name="listType" value="10" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="85" align="right"><s:text name="i18n_ProjectName" />：</td>
					<td class="adv_td1" width="200"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right"><s:text name="i18n_ProjectSubtype" />：</td>
					<td class="adv_td1" width="200"><s:select cssClass="select" name="projectSubtype" value="%{searchQuery.projectSubtype}" list="%{baseService.getSystemOptionMap('projectType', '01')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_DisciplineType" />：</td>
					<td class="adv_td1">
						<input type="button" class="btn1 select_btn" id="select_disciplineType_btn" value="<s:text name="i18n_Select" />" />
						<div id="disptr" style="display:none"></div>
						<s:hidden id="dispName" name="dtypeNames" value="%{searchQuery.dtypeNames}"/>
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectYear" />：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
						<s:text name="i18n_To"/>
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
					</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_Director" />：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right"><s:text name="i18n_University" />：</td>
					<td class="adv_td1"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_ReviewStatus" />：</td>
					<td class="adv_td1">
						   <s:select cssClass="select" name="submitStatus" value="%{searchQuery.submitStatus}" list="#{'3':getText('i18n_Submit'),'2':getText('i18n_Saved'),'1':getText('i18n_SendBack'), '0':getText('i18n_Pending')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
			   		</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right"><s:text name="i18n_ReviewDate" />：</td>
					<td class="adv_td1">
						<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
						<s:text name="i18n_To"/>
						<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
			   		</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right"><s:text name="i18n_GroupReviewStatus" />：</td>
					<td class="adv_td2">
						   <s:select cssClass="select" name="reviewStatus" value="%{searchQuery.reviewStatus}" list="#{'32':getText('i18n_Approve'),'31':getText('i18n_NotApprove'),'22':getText('i18n_SaveApprove'),'21':getText('i18n_SaveNotApprove'),'0':getText('i18n_Pending')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
			   		</td>
					<td class="adv_td2"></td>
					<td class="adv_td2" align="right"><s:text name="i18n_Score" />：</td>
					<td class="adv_td2">
						<s:textfield name="minScore" value="%{searchQuery.minScore}" cssClass="input_css_self" disabled="disabled" style="width:68px;height:18px;border-width:1px;"/>
						<s:text name="i18n_To"/> 
						<s:textfield name="maxScore" value="%{searchQuery.maxScore}" cssClass="input_css_self" disabled="disabled" style="width:68px;height:18px;border-width:1px;"/>
					</td>
					<td class="adv_td2"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr style="height:36px;">
					<td align="right"></td>
					<td width="60"><input id="list_button_advSearch" type="button" value="<s:text name='i18n_Search'/>" class="btn1"/></td>
					<td width="80"><input id="list_search_hide" type="button" value="<s:text name='i18n_HideTerms'/>" class="btn2" /></td>
				</tr>
			</table>
		</div> 
	</s:form>
</div>
