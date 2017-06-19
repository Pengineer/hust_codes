<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<script type="text/javascript">
	seajs.use('javascript/project/project_share/adv_search.js', function(adv_search) {
		$(function(){
			adv_search.init();
			adv_search.valid();
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/project/key/topicSelection/apply">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
				<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
						<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
							list="#{'0461':'已上报','0462':'待审核','0463':'已审核'}" />
					</s:if>
					<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
						list="#{'1':'课题名称','2':'项目课题来源','3':'选题年度'}" />
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
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/key/topicSelection/apply">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" />  
		<s:hidden name="listType" value="12" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="85" align="right">课题名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="topicName" value="%{searchQuery.topicName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="80"></td>
					<td class="adv_td1" width="85" align="right">英文名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="englishName" value="%{searchQuery.englishName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
					<td class="adv_td1" align="right">项目课题来源：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="topicSource" value="%{searchQuery.topicSource}" headerKey="-1" headerValue="--不限--"
							list="#{'0':'教育部','1':'高校','2':'专家'}" />
					</td>
					<td class="adv_td1"></td>
					</s:if>
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@PROVINCE)">
						<td class="adv_td1" align="right">高校：</td>
						<td class="adv_td1"><s:textfield name="universityName" value="%{searchQuery.universityName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
					<td class="adv_td1" align="right">学科代码：</td>
					<td class="adv_td1">
						<input type="button" class="btn1 select_btn" id="select_discipline_btn" value="选择" />
						<div id="discipline" style="display:none"></div>
						<s:hidden id="disciplineId" name="discipline" value="%{searchQuery.discipline}"/>
					</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
						<td class="adv_td1" align="right">专家：</td>
						<td class="adv_td1"><s:textfield name="applicantName" value="%{searchQuery.applicantName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
						<td class="adv_td1" align="right">提交状态：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="topsApplicantSubmitStatus"  value="%{searchQuery.submitStatus}" list="#{'3':'提交','2':'暂存','1':'退回'}" headerKey="-1" headerValue="--不限--" />
				   		</td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<s:else>
					<tr class="adv_tr">
						<td class="adv_td1" align="right"><s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@DEPARTMENT)<0">审核状态</s:if> <s:else>审核状态</s:else>：</td>
						<td class="adv_td1">
					   		<s:select cssClass="select" name="auditStatus" value="%{searchQuery.auditStatus}" list="#{'32':'同意','31':'不同意','22':'同意（暂存）','21':'不同意（暂存）','12':'退回','0':'待审'}" headerKey="-1" headerValue="--不限--" />
				   		</td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right"><s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0">选题时间</s:if> <s:else>审核时间</s:else>：</td>
						<td class="adv_td1">
							<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
							至
							<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
				   		</td>
						<td class="adv_td1"></td>
					</tr>
				</s:else>
				<tr class="adv_tr">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
						<td class="adv_td1" align="right">提交状态：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="topsUniversitySubmitStatus" value="%{searchQuery.submitStatus}" list="#{'3':'提交','2':'暂存','1':'退回'}" headerKey="-1" headerValue="--不限--" />
				   		</td>
						<td class="adv_td1"></td>
					</s:if>
					<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
						<td class="adv_td1" align="right">选题状态：</td>
						<td class="adv_td1">
							<s:select cssClass="select" name="isAdopt" value="%{searchQuery.isAdopt}" list="#{'2':'同意','1':'不同意','0':'待审'}" headerKey="-1" headerValue="--不限--" />
				   		</td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td2" align="right">高校所在省份：</td>
						<td class="adv_td2"><s:textfield name="provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
					</s:if>
					<td class="adv_td2" align="right">选题年度：</td>
					<td class="adv_td2">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
						至
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
					</td>
					<td class="adv_td2"></td>
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
						<td class="adv_td2" align="right">项目课题来源：</td>
						<td class="adv_td2">
							<s:select cssClass="select" name="topicSource" value="%{searchQuery.topicSource}" headerKey="-1" headerValue="--不限--"
								list="#{'1':'高校','2':'专家'}" />
						</td>
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