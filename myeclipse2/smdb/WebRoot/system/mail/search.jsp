<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	seajs.use('javascript/system/mail/search.js', function(adv_search) {
		$(function(){
			adv_search.init();
		})
	});
</script>
			
<div id="simple_search"><!-- 初级检索 -->			
	<s:form id="search" theme="simple" action="list" namespace="/mail">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
						list="#{'1':getText('邮件主题'),'2':getText('邮件正文'),'3':getText('创建账号'),'4':getText('创建者')}" />
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
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/mail">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="createDate1" name="createDate1" value="%{searchQuery.startDate}"/>
		<s:hidden id="createDate2" name="createDate2" value="%{searchQuery.endDate}" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">邮件主题：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword1" value="%{searchQuery.keyword1}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">邮件正文：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword2" value="%{searchQuery.keyword2}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">创建账号：</td>
					<td class="adv_td1"><s:textfield name="keyword3" value="%{searchQuery.keyword3}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">创建者：</td>
					<td class="adv_td1"><s:textfield name="keyword4" value="%{searchQuery.keyword4}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">创建时间：</td> 
					<td class="adv_td2"><s:textfield id="createDate3" value="%{searchQuery.createDate1}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="createDate4" value="%{searchQuery.createDate2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" /></td>
					<td class="adv_td2"></td>
					<td class="adv_td2" align="right">状态：</td>
					<td class="adv_td2"><s:select cssClass="select" name="status" value="%{searchQuery.status}" headerKey="-1" headerValue="--%{getText('不限')}--" list="#{'3':getText('已完成'),'1':getText('发送中'),'2':getText('已暂停')}" /></td>
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