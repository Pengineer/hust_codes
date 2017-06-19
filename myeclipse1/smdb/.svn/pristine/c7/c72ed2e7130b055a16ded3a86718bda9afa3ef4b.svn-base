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
	<s:form id="search" theme="simple" action="list" namespace="/project/key/application/apply">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr style="height:36px;">
				<td align="right">
					<span class="choose_bar">
						<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@ADMINISTRATOR, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
							<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
								list="#{'0411':'已上报','0412':'待审核','0413':'已审核'}" />
						</s:if>
						<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
							<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
								list="#{'1':'项目名称','2':'申请人','3':'依托高校','4':'研究类型','5':'学科门类','6':'项目年度'}" />
						</s:if>
						<s:else>
							<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--不限--"
								list="#{'1':'项目名称','2':'申请人','4':'研究类型','5':'学科门类','6':'项目年度'}" />
						</s:else>
					</span>
					<s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
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
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/key/application/apply">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden name="listType" value="1" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">项目名称：</td>
					<td class="adv_td1" width="200"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1" width="100"></td>
					<td class="adv_td1" width="65" align="right">研究类型：</td>
					<td class="adv_td1" width="200"><s:select cssClass="select" name="researchType" value="%{searchQuery.researchType}" list="%{baseService.getSystemOptionMap('researchType', null)}" headerKey="-1" headerValue="--不限--" /></td>
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
					<td class="adv_td1" align="right">学科代码：</td>
					<td class="adv_td1">
						<input type="button" class="btn1 select_btn" id="select_discipline_btn" value="选择" />
						<div id="discipline" style="display:none"></div>
						<s:hidden id="disciplineId" name="discipline" value="%{searchQuery.discipline}"/>
					</td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">项目年度：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
						至
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--不限--" />
					</td>
					<td class="adv_td1"></td>
				</tr>
				<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
					<tr class="adv_tr">
						<td class="adv_td2" align="right">申请人：</td>
						<td class="adv_td2"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
						<td class="adv_td2" align="right">是否立项：</td>
						<td class="adv_td2">
							<s:select cssClass="select" name="isEstab" value="%{searchQuery.isEstab}"
								list="#{'1':'是', '2':'否', '3':'待审'}" 
								headerKey="-1" headerValue="--不限--"></s:select>
						</td>
						<td class="adv_td2"></td>
					</tr>
				</s:if>
				<s:else>
					<tr class="adv_tr">
						<td class="adv_td1" align="right">申请人：</td>
						<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right">是否立项：</td>
						<td class="adv_td1">
							<s:select cssClass="select" name="isEstab" value="%{searchQuery.isEstab}"
								list="#{'1':'是', '2':'否', '3':'待审'}" 
								headerKey="-1" headerValue="--不限--"></s:select>
						</td>
						<td class="adv_td1"></td>
					</tr>
					<tr class="adv_tr">
						<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@STUDENT)">
							<td class="adv_td1" align="right">依托院系或研究基地：</td>
							<td class="adv_td1"><s:textfield name="divisionName" value="%{searchQuery.divisionName}" cssClass="input_css" /></td>
							<td class="adv_td1"></td>
						</s:if>
						<td class="adv_td1" align="right">依托高校：</td>
						<td class="adv_td1"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</tr>
				</s:else>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td2" align="right">高校所在省份：</td>
						<td class="adv_td2"><s:textfield name="provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
						<td class="adv_td2"></td>
						<td class="adv_td2" align="right">项目成员：</td>
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
