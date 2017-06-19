<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/project/project_share/adv_search.js', function(adv_search) {
		$(function(){
			adv_search.init();
			adv_search.valid();
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/project/general/endinspection/apply">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td width="80" align="center"></td>
				<td align="right">
					<span class="choose_bar">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
						<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
							list="#{'0132':getText('i18n_ToAudit'),'0133':getText('i18n_Submitted')}" />
					</s:if>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
							list="#{'1':getText('i18n_ProjectName'),'2':getText('i18n_Director'),'3':getText('i18n_University'),'4':getText('i18n_ProjectSubtype'),'5':getText('i18n_ProjectYear')}" />
					</s:if>
					<s:else>
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--"
							list="#{'1':getText('i18n_ProjectName'),'2':getText('i18n_Director'),'4':getText('i18n_ProjectSubtype'),'5':getText('i18n_ProjectYear')}" />
					</s:else>
					</span>
					<s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
				</td>
				<td width="60"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="<s:text name='i18n_MoreTerms'/>" class="btn2"/></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/general/endinspection/apply">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" />
		<s:hidden name="listType" value="4" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right"><s:text name="i18n_ProjectNumber" />：</td>
					<td class="adv_td1" width="195"><s:textfield name="projectNumber" value="%{searchQuery.projectNumber}" cssClass="input_css" /></td>
					<td class="adv_td1" width="92"></td>
					<td class="adv_td1" width="85" align="right"><s:text name="i18n_EndNumber" />：</td>
					<td class="adv_td1" width="195"><s:textfield name="endCertificate" value="%{searchQuery.endCertificate}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectName" />：</td>
					<td class="adv_td1"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectSubtype" />：</td>
					<td class="adv_td1"><s:select cssClass="select" name="projectSubtype" value="%{searchQuery.projectSubtype}" list="%{baseService.getSystemOptionMap('projectType', '01')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_Director" />：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<td class="adv_td1" align="right"><s:text name="i18n_University" />：</td>
						<td class="adv_td1"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right"><s:text name="i18n_ProjectYear" />：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
						<s:text name="i18n_To"/>
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" valign="top" align="right">
						<s:text name="i18n_EndinspectionProduct"/>：<br />
						<span class="select_all_box"><s:text name="i18n_SelectAll"/>&nbsp;<input id="checkAllProductTypeItem" name="check" type="checkbox" title="<s:text name='i18n_ClickSelectAll' />" />
					</td>
					<td class="adv_td1">
						<s:checkboxlist name="productType" list="%{baseService.getSystemOptionMapAsName('productType', null)}" />
						<span id="productTypeOtherSpan" style="display:none;">
							<s:textfield name="productTypeOther" cssClass="input_css_other"/>
							<br/><span class="tip"><s:text name="i18n_MoreWarn"/></span>
						</span>
					</td>
					<td class="adv_td1" id="profuctTypeOtherTd"></td>
					<s:hidden id ="checkedProIssue" value="%{searchQuery.productType}" ></s:hidden>
				</tr>
				<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
					<tr class="adv_tr">
						<td class="adv_td1" align="right"><s:text name="i18n_SubmitStatus" />：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="applicantSubmitStatus" value="%{searchQuery.applicantSubmitStatus}" list="#{'3':getText('i18n_Submit'),'2':getText('i18n_Saved'),'1':getText('i18n_SendBack')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
				   		</td>
						<td class="adv_td1"></td>
					</tr>
				</s:if>
				<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
					<tr class="adv_tr">
						<td class="adv_td1" align="right"><s:text name="i18n_EndinspectionStatus" />：</td>
						<td class="adv_td1">
						   	<s:select cssClass="select" name="isApproved" value="%{searchQuery.isApproved}" list="#{'2':getText('i18n_Approve'),'1':getText('i18n_NotApprove'),'0':getText('i18n_Pending')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
				   		</td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right"><s:text name="i18n_ProjectStatus" />：</td>
						<td class="adv_td1"><s:select cssClass="select" name="projectStatus" value="%{searchQuery.projectStatus}" list="#{'1':getText('i18n_InStudy'), '2':getText('i18n_Complete'), '3':getText('i18n_Suspend'), '4':getText('i18n_Revoke')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
						<td class="adv_td1"></td>
					</tr>
					<tr class="adv_tr">
						<td class="adv_td1" align="right"><s:text name="i18n_AuditStatus" />：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="auditStatus" value="%{searchQuery.auditStatus}" list="#{'32':getText('i18n_Approve'),'31':getText('i18n_NotApprove'),'22':getText('i18n_SaveApprove'),'21':getText('i18n_SaveNotApprove'),'12':getText('i18n_SendBack'),'0':getText('i18n_Pending')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
				   		</td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right"><s:text name="i18n_AuditDate" />：</td>
						<td class="adv_td1">
							<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick j_startDate" disabled="disabled" size="10"/>
							<s:text name="i18n_To"/>
							<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick j_endDate" disabled="disabled" size="10"/>
				   		</td>
						<td class="adv_td1"></td>
					</tr>
				</s:elseif>
				<s:else>
					<tr class="adv_tr">
						<td class="adv_td1" align="right"><s:text name="i18n_AuditDate" />：</td>
						<td class="adv_td1">
							<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick j_startDate" disabled="disabled" size='10'/>
							<s:text name="i18n_To"/>
							<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick j_endDate" disabled="disabled" size='10'/>
				   		</td>
						<td class="adv_td1"></td> 
						<td class="adv_td1" align="right"><s:text name="i18n_EndinspectionStatus" />：</td>
						<td class="adv_td1">
					   		<s:select cssClass="select" name="finalAuditStatus" value="%{searchQuery.finalAuditStatus}" list="#{'32':getText('i18n_Approve'),'31':getText('i18n_NotApprove'),'22':getText('i18n_SaveApprove'),'21':getText('i18n_SaveNotApprove'),'0':getText('i18n_Pending')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
				   		</td>
						<td class="adv_td1"></td>
					</tr>
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
						<tr class="adv_tr">
							<td class="adv_td1" align="right" ><s:text name="i18n_AuditStatus" />：</td>
							<td class="adv_td1">
						    	<s:select cssClass="select" name="auditStatus" value="%{searchQuery.auditStatus}" list="#{'32':getText('i18n_Approve'),'31':getText('i18n_NotApprove'),'22':getText('i18n_SaveApprove'),'21':getText('i18n_SaveNotApprove'),'12':getText('i18n_SendBack'),'0':getText('i18n_Pending')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
					   		</td>
							<td class="adv_td1"></td>
							<td class="adv_td1" align="right"><s:text name="i18n_ReviewStatus1" />：</td>
							<td class="adv_td1">
						   		<s:select cssClass="select" name="reviewStatus" value="%{searchQuery.reviewStatus}" list="#{'32':getText('i18n_Approve'),'31':getText('i18n_NotApprove'),'22':getText('i18n_SaveApprove'),'21':getText('i18n_SaveNotApprove'),'0':getText('i18n_Pending')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" />
					   		</td>
							<td class="adv_td1"></td>
						</tr>
					</s:if>
					<tr class="adv_tr">
						<td class="adv_td1" align="right"><s:text name="i18n_ProjectStatus" />：</td>
						<td class="adv_td1"><s:select cssClass="select" name="projectStatus" value="%{searchQuery.projectStatus}" list="#{'1':getText('i18n_InStudy'), '2':getText('i18n_Complete'), '3':getText('i18n_Suspend'), '4':getText('i18n_Revoke')}" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" /></td>
						<td class="adv_td1"></td>
					</tr>
				</s:else>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right"><s:text name="依托院系或研究基地" />：</td>
						<td class="adv_td1"><s:textfield name="divisionName" value="%{searchQuery.divisionName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right"><s:text name="高校所在省份" />：</td>
						<td class="adv_td1"><s:textfield name="provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@STUDENT)">
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