<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	seajs.use('javascript/other/nsfc/granted/search.js', function(adv_search) {
		$(function(){
			adv_search.init();
		})
	});
</script>

<div id="simple_search"><!-- 初级检索 -->	
	<s:form id="search" theme="simple" action="list" namespace="/other/nsfc/granted">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
						list="#{
							'0':'项目名称',
							'1':'年份',
							'2':'批准号',
							'3':'申请编号',
							'4':'项目负责人',
							'5':'依托单位',
							'6':'资助类别',
							'7':'亚类说明',
							'8':'附注说明'}"/>
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
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/other/nsfc/granted">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>	
		<s:hidden id="grantedStartDate" name="grantedStartDate" value="%{searchQuery.grantedStartDate}"/>
		<s:hidden id="grantedEndDate" name="grantedEndDate" value="%{searchQuery.grantedEndDate}" />
		<s:hidden id="endStartDate" name="endStartDate" value="%{searchQuery.endStartDate}"/>
		<s:hidden id="endEndDate" name="endEndDate" value="%{searchQuery.endEndDate}" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">项目名称：</td>
					<td class="adv_td1" width="300"><s:textfield name="keyword1"  value="%{searchQuery.keyword1}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">年份：</td>
					<td class="adv_td1" width="300"><s:textfield name="keyword2"  value="%{searchQuery.keyword2}" cssClass="input_css" /></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">项目负责人：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword5"  value="%{searchQuery.keyword5}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">工作单位：</td>
					<td class="adv_td1"><s:textfield name="keyword6" value="%{searchQuery.keyword6}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr>
					<td class="adv_td1" width="100" align="right">批准金额：</td>
					<td class="adv_td1"><s:textfield name="keyword10_1"  value="%{searchQuery.keyword10_1}" cssClass="input_css_other1" />&nbsp;至&nbsp;<s:textfield name="keyword10_2" value="%{searchQuery.keyword10_2}" cssClass="input_css_other1" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">资助类别：</td>
					<td class="adv_td1"><s:textfield name="keyword7" value="%{searchQuery.keyword7}" cssClass="input_css" /></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">立项时间：</td>
					<td class="adv_td1" width="200"><s:textfield id="startDate1" value="%{searchQuery.grantedStartDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="endDate1" value="%{searchQuery.grantedEndDate}" cssClass="input_css_self younger date_hint FloraDatepick" disabled="disabled" size="10" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="200" align="right">计划完成时间：</td>
					<td class="adv_td1" width="200"><s:textfield id="startDate2" value="%{searchQuery.endStartDate2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="endDate1" value="%{searchQuery.endEndDate}" cssClass="input_css_self younger date_hint FloraDatepick" disabled="disabled" size="10" /></td>
					
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">项目批准号：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword3"  value="%{searchQuery.keyword3}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">项目编号：</td>
					<td class="adv_td1"><s:textfield name="keyword4" value="%{searchQuery.keyword4}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
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