<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%> 
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
	seajs.use('javascript/award/moesocial/application/adv_search.js', function(advSearch) {
		$(function(){
			advSearch.init();
	<%--	advSearch.initClick();	--%>
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/award/moesocial/application/publicity">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
						<s:select cssClass="select" name="keyword1" headerKey="0" headerValue="--所有届次--" list="{}" id="session1" />
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('成果名称'),'2':getText('申请人'),'3':getText('依托高校'),'4':getText('学科门类'),'5':getText('成果类型'),'7':getText('获奖等级')}"/>
					</span> 
					<span id="selectOrNot"><s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" /></span>
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
					<s:hidden id="listflag" name="listflag"/>
					<s:hidden id="audflag" name="audflag" value="1"/>
					<s:hidden id="accountType" name="accountType" value="%{#session.loginer.currentType}"/>
					<s:hidden id="searchTypeValue" name="searchTypeValue"/>
				</td>
				<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2"/></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" namespace="/award/moesocial/application/publicity" theme="simple">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" /> 
		<s:hidden name="listflag"/>
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">成果名称：</td>
					<td class="adv_td1" width="205"><s:textfield name="productName" value="%{searchQuery.productName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="80"></td>
					<td class="adv_td1" width="65" align="right">申请人：</td>
					<td class="adv_td1" width="205"><s:textfield name="applicantName" value="%{searchQuery.applicantName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">学科门类：</td>
					<td class="adv_td1">
						<input type="button" id="select_disciplineType_btn" class="btn1 select_btn" value="选择" />
						<div id="disptr" style="display:none"></div>
						<s:hidden name="dtypeNames"  value="%{searchQuery.dtypeNames}" id="discipline" />
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">成果类型：</td>
					<td class="adv_td1"><s:select cssClass="select" name="ptype" value="%{searchQuery.ptype}" headerKey="-1" headerValue="--%{getText('不限')}--" list="%{baseService.getSystemOptionMapAsName('productType', null)}"/></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">依托高校：</td>
					<td class="adv_td1"><s:textfield name="universityName" value="%{searchQuery.universityName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">获奖等级：</td>
					<td class="adv_td1"><s:select cssClass="select" name="awardGradeid" value="%{searchQuery.awardGradeid}" headerKey="-1" headerValue="--%{getText('不限')}--" list="%{baseService.getSystemOptionMap('awardGrade', null)}"/></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@MINISTRY)">
						<td class="adv_td1" align="right">审核状态：</td>
						<td class="adv_td1">
							<s:select cssClass="select" name="status" value="%{searchQuery.status}" list="#{'32':getText('同意'),'31':getText('不同意'),'22':getText('同意（暂存）'),'21':getText('不同意（暂存）'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
						</td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right">审核时间：</td>
						<td class="adv_td1"><s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">获奖届次：</td>
					<td class="adv_td2">
						<s:hidden id="session3Value" value="%{searchQuery.session3}"/>
						<s:hidden id="session2Value" value="%{searchQuery.session2}"/>
						<s:select cssClass="select" name="session3" value="%{searchQuery.session3}" list="{}" id="session3" headerKey="-1" headerValue="--%{getText('不限')}--" />
			   		 	至
			   			<s:select cssClass="select" name="session2" value="%{searchQuery.session2}" list="{}" id="session2" headerKey="-1" headerValue="--%{getText('不限')}--" />
			   		</td>
					<td class="adv_td2"></td>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@STUDENT)">						
						<td class="adv_td2" align="right">高校所在省份：</td>
						<td class="adv_td2"><s:textfield name="provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
					</s:if>
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