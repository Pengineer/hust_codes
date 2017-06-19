<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/system/business/adv_search.js', function(adv_search) {
		$(function(){
			adv_search.init();
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/business">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('业务类型'),'2':getText('业务设置')}" />
					</span>
					<s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
				</td>
				<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2"/></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/business">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" />
		<s:hidden id="applicantDeadline1" name="applicantDeadline1" value="%{searchQuery.applicantDeadline1}"/>
		<s:hidden id="applicantDeadline2" name="applicantDeadline2" value="%{searchQuery.applicantDeadline2}" />
		<s:hidden id="deptInstDeadline1" name="deptInstDeadline1" value="%{searchQuery.deptInstDeadline1}"/>
		<s:hidden id="deptInstDeadline2" name="deptInstDeadline2" value="%{searchQuery.deptInstDeadline2}"/>
		<s:hidden id="univDeadline1" name="univDeadline1" value="%{searchQuery.univDeadline1}"/>
		<s:hidden id="univDeadline2" name="univDeadline2" value="%{searchQuery.univDeadline2}"/>
		<s:hidden id="provDeadline1" name="provDeadline1" value="%{searchQuery.provDeadline1}"/>
		<s:hidden id="provDeadline2" name="provDeadline2" value="%{searchQuery.provDeadline2}"/>
		<s:hidden id="reviewDeadline1" name="reviewDeadline1" value="%{searchQuery.reviewDeadline1}"/>
		<s:hidden id="reviewDeadline2" name="reviewDeadline2" value="%{searchQuery.reviewDeadline2}"/>
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="110" align="right">业务类型：</td>
					<td class="adv_td1" width="200">
						<s:select cssClass="select" list="%{#request.businessType}" name="business.subType.id" value="%{searchQuery.type}" listKey="id" listValue="name"
							headerKey="-1" headerValue="--%{getText('不限')}--"/>
						<s:hidden name="entityId" id="business.id"/>
					</td>
					<td class="adv_td1" width="70"></td>
					<td class="adv_td1" width="90" align="right">业务设置：</td>
					<td class="adv_td1" width="200">
						<s:select cssClass="select" name="businessStatus" value="%{searchQuery.businessStatus}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('业务激活中'),'0':getText('业务停止')}" />
					</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">业务起始时间：</td>
					<td class="adv_td1">
						<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
						至
						<s:textfield id="endDate1"  value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
			   		</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">申报截止时间：</td>
					<td class="adv_td1">
						<s:textfield id="applicantDeadline11" value="%{searchQuery.applicantDeadline1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
						至
						<s:textfield id="applicantDeadline12" value="%{searchQuery.applicantDeadline2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
			   		</td>
					<td class="adv_td1"></td>
				</tr>
				<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)">
				<tr class="adv_tr">
					<td class="adv_td1" align="right">部门截止时间：</td>
					<td class="adv_td1">
						<s:textfield id="deptInstDeadline11" value="%{searchQuery.deptInstDeadline1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
						至
						<s:textfield id="deptInstDeadline12" value="%{searchQuery.deptInstDeadline2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
			   		</td>
					<td class="adv_td1"></td>
				</s:if>
				<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
					<td class="adv_td1" align="right">高校截止时间：</td>
					<td class="adv_td1">
						<s:textfield id="univDeadline11" value="%{searchQuery.univDeadline1}" cssClass="input_css_self date_hint FloraDatepick " disabled="disabled"/>
						至
						<s:textfield id="univDeadline12" value="%{searchQuery.univDeadline2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
			   		</td>
					<td class="adv_td1"></td>
				</tr>
				</s:if>
				<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@PROVINCE)">
				<tr class="adv_tr">
					<td class="adv_td1" align="right">省厅截止时间：</td>
					<td class="adv_td1">
						<s:textfield id="provDeadline11" value="%{searchQuery.provDeadline1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
						至
						<s:textfield id="provDeadline12" value="%{searchQuery.provDeadline2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
			   		</td>
					<td class="adv_td1"></td>
				</s:if>
				<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY) || #session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@TEACHER)">
					<td class="adv_td1" align="right">专家评审截止时间：</td>
					<td class="adv_td1">
						<s:textfield id="reviewDeadline11" value="%{searchQuery.reviewDeadline1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
						至
						<s:textfield id="reviewDeadline12" value="%{searchQuery.reviewDeadline2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled"/>
			   		</td>
					<td class="adv_td1"></td>
				</tr>
				</s:if>
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