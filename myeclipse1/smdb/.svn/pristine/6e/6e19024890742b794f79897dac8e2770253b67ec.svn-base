<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored ="true"%> 
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
	<s:form id="search" theme="simple" action="list" namespace="/award/moesocial/application/review">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
						<s:select cssClass="select" name="keyword1" headerKey="0" headerValue="--所有届次--" list="{}" id="session1" />
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{'不限'}--"
							list="#{'1':'成果名称','2':'申请人','3':'依托高校','4':'学科门类','5':'成果类型'}" 
							/></span>
					<span id="selectOrNot"><s:textfield id="keyword" value="%{searchQuery.keyword}" name="keyword" cssClass="keyword" size="10" /></span>
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
					<s:hidden id="listflag" name="listflag"/>
				</td>
				<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2"/></td>
			</tr>
		</table>
	</s:form>
</div>		
<div id="adv_search" style="display:none;">
	<s:form action="advSearch" namespace="/award/moesocial/application/review" theme="simple">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" /> 
		<s:hidden name="listflag"/>
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">成果名称：</td>
					<td class="adv_td1" width="210"><s:textfield name="productName" value="%{searchQuery.productName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="70"></td>
					<td class="adv_td1" width="65" align="right">成果类型：</td>
					<td class="adv_td1" width="210"><s:select cssClass="select" name="ptypeid" value="%{searchQuery.ptypeid}" headerKey="-1" headerValue="--%{'请选择'}--" list="%{baseService.getSystemOptionMapAsName('productType', null)}" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">学科门类：</td>
					<td class="adv_td1">
						<input type="button" id="select_disciplineType_btn" class="btn1" value="选择"/>
						<s:hidden name="dtypeNames" value="%{searchQuery.dtypeNames}" id="discipline" />
						<div id="disptr" style="display:none"></div>
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right" >申请人：</td>
					<td class="adv_td1"><s:textfield name="applicantName" value="%{searchQuery.applicantName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">依托高校：</td>
					<td class="adv_td1"><s:textfield name="universityName" value="%{searchQuery.universityName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">申请届次：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="session3" value="%{searchQuery.session3}" list="{}" id="session3" headerKey="-1" headerValue="--%{'请选择'}--"/>
			   		 	至
			   			<s:select cssClass="select" name="session2" value="%{searchQuery.session2}" list="{}" id="session2" headerKey="-1" headerValue="--%{'请选择'}--"/>
			   		</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">评审状态：</td>
					<td class="adv_td1">
						   <s:select cssClass="select" name="reviewStatus" value="%{searchQuery.reviewStatus}" list="#{'3':'已提交','2':'保存','0':'待审'}" headerKey="-1" headerValue="--%{'请选择'}--" />
			   		</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">评审时间：</td>
					<td class="adv_td1">
						<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
						至
						<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">小组评审状态：</td>
					<td class="adv_td2">
						   <s:select cssClass="select" name="groupReviewStatus" value="%{searchQuery.groupReviewStatus}" list="#{'32':'同意','31':'不同意','22':'同意（暂存）','21':'不同意（暂存）','0':'待审'}" headerKey="-1" headerValue="--%{'请选择'}--" />
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