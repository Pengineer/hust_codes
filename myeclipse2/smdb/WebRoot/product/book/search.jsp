<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/product/adv_search.js', function(adv_search) {
		$(function(){
			adv_search.init();
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/product/book">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<!-- 研究人员 -->
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1': getText('书籍名称'), '2': getText('书籍类型'), '4': getText('所属单位'), '5': getText('出版单位')}"/>
					</s:if>
					<!-- 校级管理人员 -->
					<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1': getText('书籍名称'), '2': getText('书籍类型'), '3': getText('第一作者'), '6': getText('所属部门'), '5': getText('出版单位')}"/>
					</s:elseif>
					<!-- 其它人员 -->
					<s:else>
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1': getText('书籍名称'), '2': getText('书籍类型'), '3': getText('第一作者'), '4': getText('所属单位'), '5': getText('出版单位')}"/>
					</s:else>
				</span>
				<s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber"/>
					<s:hidden id="list_sortcolumn" name="sortColumn"/>
					<s:hidden id="list_pagesize" name="pageSize"/>
					<s:hidden id="productType" value="2"/>
					<s:hidden id="protype" value="book"/>
				</td>
				<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1"/></td>
				<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2"/></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/product/book">
  		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
  		<s:hidden id="pubDate1" name="pubDate1" value="%{searchQuery.pubDate1}"/>
		<s:hidden id="pubDate2" name="pubDate2" value="%{searchQuery.pubDate2}" />
  		<s:hidden id="audDate1" name="audDate1" value="%{searchQuery.audDate1}"/>
		<s:hidden id="audDate2" name="audDate2" value="%{searchQuery.audDate2}" />
		<div class="adv_content">
			<s:include value="/validateError.jsp"/>
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">书籍名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="book.chineseName" value="%{searchQuery.chineseName}" cssClass="input_css"/></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">书籍类型：</td>
					<td class="adv_td1" width="200">
						<s:select cssClass="select" name="book.type.id" value="%{searchQuery.type}" list="%{baseService.getSystemOptionMap('productType', '02')}" 
								headerKey="-1" headerValue="--%{getText('不限')}--"/>
						</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">学科门类：</td>
					<td class="adv_td1">
						<input type="button" id="select_disciplineType_btn" class="btn1 select_btn" value="检索"/>
						<div id="disptr" style="display:none"></div>
						<s:hidden name="book.disciplineType" value="%{searchQuery.disciplineType}" id="dispName"/>
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">第一作者：</td>
					<td class="adv_td1"><s:textfield name="book.authorName" value="%{searchQuery.authorName}" cssClass="input_css"/></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<!-- 非校级管理员 -->
					<s:if test=" #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY)<0 || #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)>0">
						<td class="adv_td1" align="right">所属单位：</td>
						<td class="adv_td1"><s:textfield name="book.agencyName" value="%{searchQuery.agencyName}" cssClass="input_css"/></td>
						<td class="adv_td1"></td>
					</s:if>
					<!-- 非院系、基地管理员 -->
					<s:if test=" #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0 || #session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@INSTITUTE)>0">
						<td class="adv_td1" align="right">所属部门：</td>
						<td class="adv_td1"><s:textfield name="book.divisionName" value="%{searchQuery.divisionName}" cssClass="input_css"/></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">出版单位：</td>
					<td class="adv_td1"><s:textfield name="book.publishUnit" value="%{searchQuery.publishUnit}" cssClass="input_css"/></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">出版时间：</td>
					<td class="adv_td1"><s:textfield id="pubDateId1" value="%{searchQuery.pubDate1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="pubDateId2" value="%{searchQuery.pubDate2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" /></td>
					<td class="adv_td1"></td>
				</tr>
				<!-- 研究人员 -->
				<s:if test=" #session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
					<tr class="adv_tr">
						<td class="adv_td2" align="right">提交状态：</td>
						<td class="adv_td2">
							<s:select cssClass="select" name="book.submitStatus" value="%{searchQuery.submitStatus}"  list="#{'2': getText('保存'), '3': getText('已提交')}"
								headerKey="-1" headerValue="--%{getText('不限')}--"/></td>
						<td class="adv_td2"></td>
					</tr>
				</s:if>
				<!-- 管理人员 -->
				<s:else>
					<tr class="adv_tr">
						<td class="adv_td1" align="right">审核状态：</td>
						<td class="adv_td1">
							<s:select cssClass="select" name="book.auditResult" value="%{searchQuery.auditResult}" list="#{'0':getText('待审'),'1':getText('不同意'),'2':getText('同意')}"
								headerKey="-1" headerValue="--%{getText('不限')}--"/></td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right">审核时间：</td>
						<td class="adv_td1"><s:textfield id="audDateId1" value="%{searchQuery.audDate1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="audDateId2" value="%{searchQuery.audDate2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" /></td>
						<td class="adv_td1"></td>
					</tr>
				</s:else>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@STUDENT)">						
						<td class="adv_td2" align="right">所在省份：</td>
						<td class="adv_td2"><s:textfield name="book.provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
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
		