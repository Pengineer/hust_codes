<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="javascript/lib/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	
<div id="simple_search"><!-- 初级检索 -->				
	<s:form id="search" theme="simple" action="list" namespace="/person/ministryOfficer">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--" 
							list="#{'0':'姓名','1':'所在机构','2':'所在部门','3':'职务'}" />
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
	<s:form id="advSearch" action="advSearch" namespace="/person/ministryOfficer" theme="simple">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>	
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">姓名：</td>
					<td class="adv_td1" width="200"><s:textfield name="name" value="%{searchQuery.name}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">性别：</td>
					<td class="adv_td1" width="200"><s:select cssClass="select" name="gender" value="%{searchQuery.gender}" headerKey="-1" headerValue="--不限--" list="#application.sexList" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">重点人：</td>
					<td class="adv_td1"><s:select cssClass="select" name="isKey" value="%{searchQuery.isKey}" headerKey="-1" headerValue="--不限--" list="#{'1':'是', '0':'否'}" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">年龄：</td>
					<td class="adv_td1"><s:textfield name="age" value="%{searchQuery.age}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">所在机构：</td>
					<td class="adv_td1"><s:textfield name="unitName" value="%{searchQuery.unitName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">所在部门：</td>
					<td class="adv_td1"><s:textfield name="deptName" value="%{searchQuery.deptName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td2" align="right">职务：</td>
					<td class="adv_td2"><s:textfield name="position" value="%{searchQuery.position}" cssClass="input_css"/></td>
					<td class="adv_td2"></td>
					<td class="adv_td2" align="right">工作证号：</td>
					<td class="adv_td2"><s:textfield name="staffCardNumber" value="%{searchQuery.staffCardNumber}" cssClass="input_css" /></td>
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
