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
	<s:form id="search" theme="simple" action="simpleSearch" namespace="/projectFund/key">
		<s:hidden id="entityId" name="entityId" value="%{entityId}" />
		<s:hidden id="listForFundList" name="listForFundList" value="%{listForFundList}" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<s:select cssClass="select" name="searchType" id="search_type" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
							list="#{'1':'批准号','2':'项目名称','3':'负责人','4':'依托高校','5':'项目子类','6':'项目年度'}" />
					</s:if>
					<s:else>
						<s:select cssClass="select" name="searchType" id="search_type" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
							list="#{'1':'批准号','2':'项目名称','3':'负责人','5':'项目子类','6':'项目年度'}" />
					</s:else>
				</span><s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
<%--					<s:hidden id="mainFlag" name="mainFlag" />--%>
				</td>
<%--				<td class="type" width="80" style="display:none;">--%>
<%--						<s:select name="type" id="type" headerKey="-1" headerValue="--不限--" --%>
<%--							list="#{'1':'立项拨款','2':'中检拨款','3':'结项拨款'}" cssClass="select"  theme="simple" />--%>
<%--				</td>--%>
<%--				<td class="status" width="80" style="display:none;">--%>
<%--						<s:select name="status" id="status" headerKey="-1" headerValue="--不限--" --%>
<%--							list="#{'0':'未拨款　','1':'已拨款　'}" cssClass="select"  theme="simple" />--%>
<%--				</td>--%>
				<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2"/></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/projectFund/key">
		<s:hidden name="entityId" value="%{entityId}" />
		<s:hidden name="listForFundList" value="%{listForFundList}" />
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden name="listType" value="2" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">批准号：</td>
					<td class="adv_td1" width="185"><s:textfield name="projectNumber" value="%{searchQuery.projectNumber}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right" >项目名称：</td>
					<td class="adv_td1" width="185"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">负责人：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<td class="adv_td2" align="right">依托高校：</td>
						<td class="adv_td2"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">研究类型：</td>
					<td class="adv_td1"><s:select cssClass="select" name="researchType" value="%{searchQuery.researchType}" list="%{baseService.getSystemOptionMap('researchType', '00')}" headerKey="-1" headerValue="--不限--" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">项目年度：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
						至
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
					</td>
					<td class="adv_td1"></td>
				</tr>
				<s:hidden id="expFlag" name="expFlag" value="%{searchQuery.expFlag}"></s:hidden>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			     <tr style="height:36px;">
					  <td align="right"></td>
					  <td width="60"><input id="list_button_advSearch" type="button" value="检索" class="btn1"/></td>
					  <td width="80"><input id="list_search_hide" type="button" value="隐藏条件" class="btn2" /></td>
			     </tr>
			</table>
		</div> 
	</s:form>
</div>