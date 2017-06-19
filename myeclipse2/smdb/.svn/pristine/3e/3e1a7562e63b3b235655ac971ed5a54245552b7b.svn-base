<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	seajs.use('javascript/system/notice/inner/search.js', function(adv_search) {
		$(function(){
			adv_search.init();
		})
	});
</script>

<div id="simple_search"><!-- 初级检索 -->
	<s:form id="search" theme="simple" action="list" namespace="/notice/inner">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr style="height:36px;">
				<td align="right">
					<span class="choose_bar">
					<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR) == 0">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('通知标题'),'2':getText('通知正文'),'3':getText('发布账号'),'4':getText('发布者')}" />
					</s:if>
					<s:else>
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('通知标题'),'2':getText('通知正文')}" />
					</s:else>
					</span><s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
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

<div id="adv_search" style="display:none;"><!-- 高级检索 -->		
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/notice/inner">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"></s:hidden>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">通知标题：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword1"  value="%{searchQuery.title}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">通知正文：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword2"  value="%{searchQuery.body}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
					<tr class="adv_tr">
					<td class="adv_td1" align="right">通知类型：</td>
					<td class="adv_td1"><s:select cssClass="select" name="keyword3" value="%{searchQuery.type}" headerKey="" headerValue="--%{getText('不限')}--" list="#application.noticeItems" listKey="id" listValue="name" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">是否公开：</td>
					<td class="adv_td1"><s:select cssClass="select" name="isOpen" value="%{searchQuery.isOpen}" headerKey="-1" headerValue="--%{getText('不限')}--" list="#{'1':getText('是'),'0':getText('否')}" /></td>
					<td class="adv_td1"></td>
				</tr>
				<s:if test="#session.loginer.currentType.compareWith(@csdc.tool.bean.AccountType@ADMINISTRATOR) == 0">
					<tr class="adv_tr">
						<td class="adv_td1" align="right">发布账号：</td>
						<td class="adv_td1"><s:textfield name="keyword4" value="%{searchQuery.account}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right">发布者：</td>
						<td class="adv_td1"><s:textfield name="keyword5" value="%{searchQuery.accountBelong}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</tr>
				</s:if>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">是否弹出：</td>
					<td class="adv_td2"><s:select cssClass="select" name="isPop" value="%{searchQuery.isPop}" headerKey="-1" headerValue="--%{getText('不限')}--" list="#{'1':getText('是'),'0':getText('否')}" /></td>
					<td class="adv_td2"></td>
					<td class="adv_td2" align="right">发布时间：</td>
					<td class="adv_td2"><s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="endDate1" value="%{searchQuery.endDate}"  cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" /></td>
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
