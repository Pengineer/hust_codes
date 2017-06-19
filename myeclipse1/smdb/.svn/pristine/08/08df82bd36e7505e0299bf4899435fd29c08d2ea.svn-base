<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	seajs.use('javascript/security/role/validate.js', function(validate) {
		$(function(){
			validate.valid();
		})
	});
</script>

<div id="simple_search"><!-- 初级检索 -->			
	<s:form id="search" theme="simple" action="list" namespace="/role">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
					<s:select cssClass="select" id="accountType" name="keyword4" value="%{searchQuery.keyword4}"   headerKey="0" headerValue="--所有类别角色--"
						list="#{'1':'部级主账号默认角色','2':'部级子账号默认角色','3':'省级主账号默认角色','4':'省级子账号默认角色','5':'部属高校主账号默认角色','6':'部属高校子账号默认角色','7':'地方高校主账号默认角色','8':'地方高校子账号默认角色','9':'院系主账号默认角色','10':'院系子账号默认角色','11':'基地主账号默认角色','12':'基地子账号默认角色','13':'外部专家账号默认角色','14':'教师账号默认角色','15':'学生账号默认角色','-1':'其它角色'}" />
					<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--不限--"
						list="#{'1':'角色名称','2':'角色描述','3':'创建者'}" />
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
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/role">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>	
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">角色名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword1" value="%{searchQuery.keyword1}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">角色描述：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword2" value="%{searchQuery.keyword2}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
			<tr class="adv_tr">
					<td class="adv_td2" align="right">创建者：</td>
					<td class="adv_td2"><s:textfield name="keyword3" value="%{searchQuery.keyword3}" cssClass="input_css" /></td>
					<td class="adv_td2"></td>
					<td class="adv_td2" align="right">默认所属：</td>
					<td class="adv_td2">
						<s:select cssClass="select" name="keyword4" value="%{searchQuery.keyword4}" headerKey="0" headerValue="--所有类别角色--"
							list="#{'1':'部级主账号默认角色','2':'部级子账号默认角色','3':'省级主账号默认角色','4':'省级子账号默认角色','5':'部属高校主账号默认角色','6':'部属高校子账号默认角色','7':'地方高校主账号默认角色','8':'地方高校子账号默认角色','9':'院系主账号默认角色','10':'院系子账号默认角色','11':'基地主账号默认角色','12':'基地子账号默认角色','13':'外部专家账号默认角色','14':'教师账号默认角色','15':'学生账号默认角色','-1':'其它角色'}" />
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