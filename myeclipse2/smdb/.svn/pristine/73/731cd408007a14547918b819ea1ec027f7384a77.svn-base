<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	seajs.use('javascript/unit/validate.js', function(validate) {
		$(function(){
			validate.validDepartment();
		})
	});
</script>

<div id="simple_search"><!-- 初级检索 -->	
	<s:form theme="simple" id="search" action="list" namespace="/unit/department">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('所属高校'),'2':getText('院系代码'),'3':getText('院系名称'),'4':getText('院系负责人')}" />
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

<div id="adv_search" style="display:none;"><!-- 高级检索 -->
	<s:form id="advSearch" action="advSearch" namespace="/unit/department" theme="simple">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>	
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">院系名称：</td>
					<td class="adv_td1" width="200"><s:textfield id="name" name="iName" value="%{searchQuery.iName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="90"></td>
					<td class="adv_td1" width="75" align="right">院系代码：</td>
					<td class="adv_td1" width="200"><s:textfield name="iCode" value="%{searchQuery.iCode}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">所属高校：</td>
					<td class="adv_td2"><s:textfield name="iUniversity" value="%{searchQuery.iUniversity}" cssClass="input_css" /></td>
					<td class="adv_td2"></td>
					<td class="adv_td2" align="right">院系负责人：</td>
					<td class="adv_td2"><s:textfield name="iDirectorName" value="%{searchQuery.iDirectorName}" cssClass="input_css" /></td>
					<td class="adv_td2"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right"><s:text name="高校所在省份" />：</td>
					<td class="adv_td2"><s:textfield name="provName" value="%{searchQuery.provName}" cssClass="input_css"/></td>
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
