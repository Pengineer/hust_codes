<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/project/project_share/adv_search.js', function(adv_search) {
		adv_search.init();
		adv_search.valid();
		adv_search.initPop();
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/project/instp/application/review">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr style="height:36px;">
				<td align="right">
					<span class="choose_bar">
						<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@EXPERT) || #session.loginer.currentType.equals(@csdc.tool.bean.AccountType@TEACHER)">
							<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
								list="#{'0214':'待评审'}" />
						</s:if>
							<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
								list="#{'1':'项目名称','2':'依托高校','3':'项目子类','4':'学科门类','5':'项目年度'}" />
					</span>
					<s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
				</td>
				<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2" /></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/instp/application/review">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" />
		<s:hidden name="listType" value="10" />
		<div class="main_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="85" align="right">项目名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">项目子类：</td>
					<td class="adv_td1" width="200"><s:select cssClass="select" name="projectSubtype" value="%{searchQuery.projectSubtype}" list="%{baseService.getSystemOptionMap('projectType', '03')}" headerKey="-1" headerValue="--不限--" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">学科门类：</td>
					<td class="adv_td1">
						<input type="button" class="btn1 select_btn" id="select_disciplineType_btn" value="选择" />
						<div id="disptr" style="display:none"></div>
						<s:hidden id="dispName" name="dtypeNames" value="%{searchQuery.dtypeNames}"/>
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">项目年度：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
						至
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
					</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">负责人：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">依托高校：</td>
					<td class="adv_td1"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">评审状态：</td>
					<td class="adv_td1">
						   <s:select cssClass="select" name="submitStatus" value="%{searchQuery.submitStatus}" list="#{'3':'提交','2':'暂存','1':'退回', '0':'待审'}" headerKey="-1" headerValue="--不限--" />
			   		</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">评审时间：</td>
					<td class="adv_td1">
						<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"  size="10"/>
						至
						<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
			   		</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">小组评审状态：</td>
					<td class="adv_td2">
						   <s:select cssClass="select" name="reviewStatus" value="%{searchQuery.reviewStatus}" list="#{'32':'同意','31':'不同意','22':'同意（暂存）','21':'不同意（暂存）','0':'待审'}" headerKey="-1" headerValue="--不限--" />
			   		</td>
					<td class="adv_td2"></td>
					<td class="adv_td2" align="right">分数：</td>
					<td class="adv_td2">
						<s:textfield name="minScore" value="%{searchQuery.minScore}" cssClass="input_css_self" disabled="disabled" style="width:68px;height:18px;border-width:1px;"/>
						至 
						<s:textfield name="maxScore" value="%{searchQuery.maxScore}" cssClass="input_css_self" disabled="disabled" style="width:68px;height:18px;border-width:1px;"/>
					</td>
					<td class="adv_td2"></td>
				</tr>
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
