<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	seajs.use('javascript/unit/validate.js', function(validate) {
		$(function(){
			validate.validAgency();
		})
	});
</script>
			
<div id="simple_search"><!-- 初级检索 -->	
	<s:form theme="simple" id="search" action="list" namespace="/unit/agency/university">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="0" headerValue="--%{getText('不限')}--"
						list="#{'1':getText('单位代码'),'2':getText('单位名称'),'3':getText('单位负责人'),'4':getText('单位类型')}" />
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
	<s:form id="advSearch" action="advSearch" namespace="/unit/agency/university" theme="simple">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>	
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="75" align="right">单位名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="aName" value="%{searchQuery.aName}" id="name" cssClass="input_css" /></td>
					<td class="adv_td1" width="80"></td>
					<td class="adv_td1" width="85" align="right">单位代码：</td>
					<td class="adv_td1" width="200"><s:textfield name="aCode" value="%{searchQuery.aCode}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">单位负责人：</td>     
					<td class="adv_td1"><s:textfield name="directorName" value="%{searchQuery.directorName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">单位类型：</td>
					<td class="adv_td1"><s:textfield name="type" value="%{searchQuery.type}" cssClass="input_css"/></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">所在省份：</td>
					<td class="adv_td1"><s:select cssClass="select" name="provinceId"  value="%{searchQuery.provinceId}" id="province" headerKey="" headerValue="--请选择省--" list="%{unitService.getProvinceList()}" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">社科管理部门：</td>
					<td class="adv_td1"><s:textfield name="aSname"  value="%{searchQuery.aSname}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">部门负责人：</td>
					<td class="adv_td2"><s:textfield name="sDirectorName" value="%{searchQuery.sDirectorName}" cssClass="input_css" /></td>
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