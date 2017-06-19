<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	seajs.use('javascript/other/nssf/granted/search.js', function(adv_search) {
		$(function(){
			adv_search.init();
		})
	});
</script>

<div id="simple_search"><!-- 初级检索 -->	
	<s:form id="search" theme="simple" action="list" namespace="/other/nssf/granted">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right"><span class="choose_bar">
					<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
						list="#{
							'0':'项目名称',
							'1':'批准号',
							'2':'项目类别',
							'3':'学科门类',
							'4':'立项时间',
							'5':'项目负责人',
							'6':'专业职务',
							'7':'工作单位',
							'8':'单位类别',
							'9':'所在省区市',
							'10':'所属系统',
							'11':'成果名称',
							'12':'成果形式',
							'13':'成果等级',
							'14':'结项时间',
							'15':'结项证书号',
							'16':'出版社',
							'17':'出版时间',
							'18':'作者',
							'19':'获奖情况',
							'20':'单列学科',
							'21':'专项主题',
							'22':'课题子类',
							'23':'备注',
							'24':'开题报告',
							'25':'鉴定组专家',
							'26':'免于鉴定理由',
							'27':'计划完成时间'}"/>
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
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/other/nssf/granted">
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
					<td class="adv_td1" width="200"><s:textfield name="keyword1"  value="%{searchQuery.keyword1}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">项目批准号：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword2"  value="%{searchQuery.keyword2}" cssClass="input_css" /></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">项目类别：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword3"  value="%{searchQuery.keyword3}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">学科分类：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword4"  value="%{searchQuery.keyword4}" cssClass="input_css" /></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">项目负责人：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword6"  value="%{searchQuery.keyword6}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">专业职务：</td>
					<td class="adv_td1"><s:textfield name="keyword7" value="%{searchQuery.keyword7}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">工作单位：</td>
					<td class="adv_td1"><s:textfield name="keyword8" value="%{searchQuery.keyword8}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">单位类别：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword9"  value="%{searchQuery.keyword9}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">所在省市区：</td>
					<td class="adv_td1"><s:textfield name="keyword10" value="%{searchQuery.keyword10}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">所属系统：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword11"  value="%{searchQuery.keyword11}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">成果名称：</td>
					<td class="adv_td1"><s:textfield name="keyword12" value="%{searchQuery.keyword12}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">成果形式：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword13"  value="%{searchQuery.keyword13}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">成果等级：</td>
					<td class="adv_td1"><s:textfield name="keyword14" value="%{searchQuery.keyword14}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" width="100" align="right">结项证书编号：</td>
					<td class="adv_td1" width="200"><s:textfield name="keyword16"  value="%{searchQuery.keyword18}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				
				<tr class="adv_tr">
					<td class="adv_td1" width="100" align="right">立项时间：</td>
					<td class="adv_td1" width="200"><s:textfield id="startDate1" value="%{searchQuery.grantedStartDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="endDate1" value="%{searchQuery.grantedEndDate}" cssClass="input_css_self younger date_hint FloraDatepick" disabled="disabled" size="10" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="100" align="right">结项时间：</td>
					<td class="adv_td1" width="200"><s:textfield id="startDate2" value="%{searchQuery.endStartDate2}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10" />&nbsp;至&nbsp;<s:textfield id="endDate1" value="%{searchQuery.endEndDate}" cssClass="input_css_self younger date_hint FloraDatepick" disabled="disabled" size="10" /></td>					
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