<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	<s:form id="search" theme="simple" action="list" namespace="/project/entrust/application/apply">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr style="height:36px;">
				<td align="right">
					<span class="choose_bar">
						<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
							<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
								list="#{'0511':getText('i18n_Received'),'0512':getText('i18n_ToAudit'),'0513':getText('i18n_Submitted')}" />
						</s:if>
						<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0 || #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@INSTITUTE)>0">
							<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
								list="#{'1':getText('i18n_ProjectName'),'2':getText('i18n_Applicant'),'3':getText('i18n_University'),'4':getText('i18n_IssueType'),'5':getText('i18n_DisciplineType'),'6':getText('i18n_ProjectYear')}" />
						</s:if>
						<s:else>
							<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
								list="#{'1':getText('i18n_ProjectName'),'2':getText('i18n_Applicant'),'4':getText('i18n_IssueType'),'5':getText('i18n_DisciplineType'),'6':getText('i18n_ProjectYear')}" />
						</s:else>
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
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/entrust/application/apply">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden name="listType" value="1" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right"><s:text name="i18n_ProjectName" />：</td>
					<td class="adv_td1" width="200"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right"><s:text name="i18n_IssueType" />：</td>
					<td class="adv_td1" width="200"><s:select cssClass="select" name="projectSubtype" value="%{searchQuery.projectSubtype}" list="%{baseService.getSystemOptionMap('projectType', '05')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectTopic" />：</td>
					<td class="adv_td1"><s:select cssClass="select" name="projectTopic" value="%{searchQuery.projectTopic}" list="%{baseService.getSystemOptionMap('projectTopic', null)}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right"><s:text name="i18n_DisciplineType" />：</td>
					<td class="adv_td1">
						<input type="button" class="btn1 select_btn" id="select_disciplineType_btn" value="<s:text name="i18n_Select" />" />
						<div id="disptr" style="display:none"></div>
						<s:hidden id="dispName" name="dtypeNames" value="%{searchQuery.dtypeNames}"/>
					</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectYear" />：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
						<s:text name="i18n_To"/>
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right"><s:text name="i18n_Applicant" />：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_IsGranted" />：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="isEstab" value="%{searchQuery.isEstab}" 
							list="#{'1':getText('i18n_Yes'), '2':getText('i18n_No'), '3':getText('i18n_Pending')}" 
							headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"></s:select>
					</td>
					<td class="adv_td1"></td>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right"><s:text name="依托院系或研究基地" />：</td>
						<td class="adv_td1"><s:textfield name="divisionName" value="%{searchQuery.divisionName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right"><s:text name="i18n_University" />：</td>
						<td class="adv_td1"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right"><s:text name="高校所在省份" />：</td>
						<td class="adv_td1"><s:textfield name="provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td2" align="right"><s:text name="项目成员" />：</td>
						<td class="adv_td2"><s:textfield name="memberName" value="%{searchQuery.memberName}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
					</s:if>
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
