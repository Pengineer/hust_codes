<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/projectFund/adv_search.js', function(adv_search) {
		$(function(){
			adv_search.init();
			adv_search.valid();
//			adv_search.initPop();
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="simpleSearch" namespace="/projectFund/instp">
		<s:hidden id="entityId" name="entityId" value="%{entityId}" />
		<s:hidden id="listForFundList" name="listForFundList" value="%{listForFundList}" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<s:select cssClass="select" name="searchType" id="search_type" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
							list="#{'1':getText('i18n_ProjectNumber'),'2':getText('i18n_ProjectName'),'3':getText('i18n_Director'),'4':getText('i18n_University'),'5':getText('i18n_ProjectSubtype'),'6':getText('i18n_ProjectYear')}" />
<%--							list="#{'1':getText('i18n_ProjectNumber'),'2':getText('i18n_ProjectName'),'3':getText('i18n_Director'),'4':getText('i18n_University'),'5':getText('i18n_ProjectSubtype'),'6':getText('i18n_ProjectYear'),'7':getText('拨款类型'),'8':getText('拨款状态')}" />--%>
					</s:if>
					<s:else>
						<s:select cssClass="select" name="searchType" id="search_type" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
							list="#{'1':getText('i18n_ProjectNumber'),'2':getText('i18n_ProjectName'),'3':getText('i18n_Director'),'5':getText('i18n_ProjectSubtype'),'6':getText('i18n_ProjectYear')}" />
<%--							list="#{'1':getText('i18n_ProjectNumber'),'2':getText('i18n_ProjectName'),'3':getText('i18n_Director'),'5':getText('i18n_ProjectSubtype'),'6':getText('i18n_ProjectYear'),'7':getText('拨款类型'),'8':getText('拨款状态')}" />--%>
					</s:else>
				</span><s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
<%--					<s:hidden id="mainFlag" name="mainFlag" />--%>
				</td>
<%--				<td class="type" width="80" style="display:none;">--%>
<%--						<s:select name="type" id="type" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" --%>
<%--							list="#{'1':'立项拨款','2':'中检拨款','3':'结项拨款'}" cssClass="select"  theme="simple" />--%>
<%--				</td>--%>
<%--				<td class="status" width="80" style="display:none;">--%>
<%--						<s:select name="status" id="status" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" --%>
<%--							list="#{'0':'未拨款　','1':'已拨款　'}" cssClass="select"  theme="simple" />--%>
<%--				</td>--%>
				<td width="60"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="<s:text name='i18n_MoreTerms'/>" class="btn2"/></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/projectFund/instp">
		<s:hidden name="entityId" value="%{entityId}" />
		<s:hidden name="listForFundList" value="%{listForFundList}" />
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden name="listType" value="2" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right"><s:text name="i18n_ProjectNumber" />：</td>
					<td class="adv_td1" width="185"><s:textfield name="projectNumber" value="%{searchQuery.projectNumber}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right" ><s:text name="i18n_ProjectName" />：</td>
					<td class="adv_td1" width="185"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_Director" />：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<td class="adv_td2" align="right"><s:text name="i18n_University" />：</td>
						<td class="adv_td2"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectSubtype" />：</td>
					<td class="adv_td1"><s:select cssClass="select" name="projectSubtype" value="%{searchQuery.projectSubtype}" list="%{baseService.getSystemOptionMap('projectType', '01')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectYear" />：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
						<s:text name="i18n_To"/>
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
					</td>
					<td class="adv_td1"></td>
				</tr>
<%--				<tr class="adv_tr">--%>
<%--					<td class="adv_td1" align="right"><s:text name="拨款类型" />：</td>--%>
<%--					<td class="adv_td1"><s:select cssClass="select" name="type" value="%{searchQuery.type}" list="#{'grantedfund':getText('立项拨款'),'midfund':getText('中检拨款'),'endfund':getText('结项拨款')}" headerKey="" headerValue="--%{getText('i18n_NoLimit')}--" /></td>--%>
<%--					<td class="adv_td1"></td>--%>
<%--					<td class="adv_td1" align="right"><s:text name="拨款状态" />：</td>--%>
<%--					<td class="adv_td1">--%>
<%--					<s:select cssClass="select" name="status" value="%{searchQuery.status}" list="#{'0':getText('未拨款'),'1':getText('已拨款')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />--%>
<%--					</td>--%>
<%--					<td class="adv_td1"></td>--%>
<%--				</tr>--%>
				<s:hidden id="expFlag" name="expFlag" value="%{searchQuery.expFlag}"></s:hidden>
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