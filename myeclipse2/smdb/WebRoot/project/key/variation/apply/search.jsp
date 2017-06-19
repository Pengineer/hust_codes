<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/project/project_share/adv_search.js', function(adv_search) {
		$(function(){
			adv_search.init();
			adv_search.valid();
			adv_search.initPop();
		})
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/project/key/variation/apply">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td align="right">
					<span class="choose_bar">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
						<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
							list="#{'0441':getText('已上报'),'0442':getText('待审核'),'0443':getText('已审核')}" />
					</s:if>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('项目名称'),'2':getText('首席专家'),'3':getText('依托高校'),'4':getText('研究类型'),'5':getText('学科门类'),'6':getText('项目年度')}" />
					</s:if>
					<s:else>
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('项目名称'),'2':getText('首席专家'),'4':getText('研究类型'),'5':getText('学科门类'),'6':getText('项目年度')}" />
					</s:else>
					</span>
					<s:textfield id="keyword" name="keyword" value="%{searchQuery.keyword}" cssClass="keyword" size="10" />
					<s:hidden id="list_pagenumber" name="pageNumber" />
					<s:hidden id="list_sortcolumn" name="sortColumn" />
					<s:hidden id="list_pagesize" name="pageSize" />
				</td>
				<td width="60"><input id="list_button_query" type="button" value="检索" class="btn1" /></td>
				<td width="80"><input id="list_search_more" type="button" value="更多条件" class="btn2" /></td>
			</tr>
		</table>
	</s:form>
</div>
<div id="adv_search" style="display:none;">
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/key/variation/apply">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" />
		<s:hidden name="listType" value="5" />
		<s:hidden id="expFlag" name="expFlag" value="%{searchQuery.expFlag}"></s:hidden>
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">批准号：</td>
					<td class="adv_td1" width="200"><s:textfield name="projectNumber" value="%{searchQuery.projectNumber}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">项目名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">研究类型：</td>
					<td class="adv_td1"><s:select cssClass="select" name="researchType" value="%{searchQuery.researchType}" list="%{baseService.getSystemOptionMap('researchType', null)}" headerKey="-1" headerValue="--%{getText('不限')}--" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">负责人：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">学科门类：</td>
					<td class="adv_td1">
						<input type="button" class="btn1 select_btn" id="select_disciplineType_btn" value="选择" />
						<div id="disptr" style="display:none"></div>
						<s:hidden id="dispName" name="dtypeNames" value="%{searchQuery.dtypeNames}"/>
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">项目年度：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('不限')}--" />
						至
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('不限')}--" />
					</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">变更事项：</td>
					<td class="adv_td1" colspan="5">
						<table id="var_item_list" width="100%" border="0" cellspacing="0" cellpadding="2">
							<s:iterator value="#application.varItems" status="stat">
								<s:if test="(#stat.index)%4 == 0"><tr></s:if>
								<td width="20" style="text-align:center;" valign="top"><input id="<s:property value='#application.varItems[#stat.index][0]' />" type="checkbox" name="selectIssue" value="<s:property value='#application.varItems[#stat.index][0]' />" class="var_item" /></td>
								<td width="90" valign="top"><s:property value="#application.varItems[#stat.index][1]" /></td>
								<s:if test="(#stat.index+1)%4 == 0"></tr></s:if>
							</s:iterator>
						</table>
					</td>
					<td class="adv_td1"></td>
					<s:hidden id ="checkedIssue" value="%{searchQuery.selectIssue}" ></s:hidden>
				</tr>
				<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
					<tr class="adv_tr">
						<td class="adv_td1" align="right">变更状态：</td>
						<td class="adv_td1">
							<s:select cssClass="select" name="isApproved" value="%{searchQuery.isApproved}" list="#{'2':getText('同意'),'1':getText('不同意'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				   		</td>
						<td class="adv_td1"></td>
					</tr>
				</s:if>
				<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
					<tr class="adv_tr">
						<td class="adv_td1" align="right">提交状态：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="applicantSubmitStatus" value="%{searchQuery.applicantSubmitStatus}" list="#{'3':getText('提交'),'2':getText('暂存'),'1':getText('退回')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				   		</td>
						<td class="adv_td1"></td>
				</s:if>
				<s:else>
					<tr class="adv_tr">
						<td class="adv_td1" align="right"><s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0">变更状态</s:if> <s:else>审核状态</s:else>：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="auditStatus" value="%{searchQuery.auditStatus}" list="#{'32':getText('同意'),'31':getText('不同意'),'22':getText('同意（暂存）'),'21':getText('不同意（暂存）'),'12':getText('退回'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				   		</td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right"><s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@PROVINCE)<0">变更时间</s:if> <s:else>审核时间</s:else>：</td>
						<td class="adv_td1">
							<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
							至
							<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick" disabled="disabled" size="10"/>
				   		</td>
						<td class="adv_td1"></td>
					</tr>
				</s:else>
				<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">	
					<tr class="adv_tr">
						<td class="adv_td1" align="right">项目状态：</td>
						<td class="adv_td1"><s:select cssClass="select" name="projectStatus" value="%{searchQuery.projectStatus}" list="#{'1':getText('在研'), '2':getText('已结项'), '3':getText('已中止'), '4':getText('已撤项')}" headerKey="-1" headerValue="--%{getText('不限')}--" /></td>
						<td class="adv_td1"></td>
					</tr>
				</s:if>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right"><s:text name="依托院系或研究基地" />：</td>
						<td class="adv_td1"><s:textfield name="divisionName" value="%{searchQuery.divisionName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right">依托高校：</td>
						<td class="adv_td1"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td2" align="right"><s:text name="高校所在省份" />：</td>
						<td class="adv_td2"><s:textfield name="provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
						<td class="adv_td2" align="right"><s:text name="项目成员" />：</td>
						<td class="adv_td2"><s:textfield name="memberName" value="%{searchQuery.memberName}" cssClass="input_css" /></td>
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
