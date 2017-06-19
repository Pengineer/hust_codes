<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<script type="text/javascript">
	seajs.use('javascript/project/project_share/adv_search.js', function(adv_search) {
		adv_search.init();
		adv_search.valid();
	});
</script>
<div id="simple_search">
	<s:form id="search" theme="simple" action="list" namespace="/project/key/endinspection/apply">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
			<tr class="table_main_tr">
				<td width="80" align="center"></td>
				<td align="right">
					<span class="choose_bar">
					<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 管理人员-->
						<s:select cssClass="select" id="mainFlag" name="mainFlag" headerKey="" headerValue="--所有项目--"
							list="#{'0432':getText('待审核'),'0433':getText('已审核')}" />
					</s:if>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('项目名称'),'2':getText('首席专家'),'3':getText('依托高校'),'4':getText('研究类型'),'5':getText('项目年度')}" />
					</s:if>
					<s:else>
						<s:select cssClass="select" name="searchType" value="%{searchQuery.searchType}" headerKey="-1" headerValue="--%{getText('不限')}--"
							list="#{'1':getText('项目名称'),'2':getText('首席专家'),'4':getText('研究类型'),'5':getText('项目年度')}" />
					</s:else>
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
	<s:form id="advSearch" action="advSearch" theme="simple" namespace="/project/key/endinspection/apply">
		<s:hidden id="advFlag" value="%{searchQuery.advFlag}"/>
		<s:hidden id="startDate" name="startDate" value="%{searchQuery.startDate}"/>
		<s:hidden id="endDate" name="endDate" value="%{searchQuery.endDate}" />
		<s:hidden name="listType" value="4" />
		<div class="adv_content">
			<s:include value="/validateError.jsp" />
			<table class="adv_table" width="100%" border="0" cellspacing="2" cellpadding="0">
				<tr class="adv_tr">
					<td class="adv_td1" width="65" align="right">批准号：</td>
					<td class="adv_td1" width="195"><s:textfield name="projectNumber" value="%{searchQuery.projectNumber}" cssClass="input_css" /></td>
					<td class="adv_td1" width="92"></td>
					<td class="adv_td1" width="85" align="right">结项证书编号：</td>
					<td class="adv_td1" width="195"><s:textfield name="endCertificate" value="%{searchQuery.endCertificate}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">项目名称：</td>
					<td class="adv_td1"><s:textfield name="projectName" value="%{searchQuery.projectName}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<td class="adv_td1" align="right">研究类型：</td>
					<td class="adv_td1"><s:select cssClass="select" name="researchType" value="%{searchQuery.researchType}" list="%{baseService.getSystemOptionMap('researchType', null)}" headerKey="-1" headerValue="--%{getText('不限')}--" /></td>
					<td class="adv_td1"></td>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">首席专家：</td>
					<td class="adv_td1"><s:textfield name="applicant" value="%{searchQuery.applicant}" cssClass="input_css" /></td>
					<td class="adv_td1"></td>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
						<td class="adv_td1" align="right">依托高校：</td>
						<td class="adv_td1"><s:textfield name="university" value="%{searchQuery.university}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<td class="adv_td1" align="right">项目年度：</td>
					<td class="adv_td1">
						<s:select cssClass="select" name="startYear" value="%{searchQuery.startYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('不限')}--" />
						至
						<s:select cssClass="select" name="endYear" value="%{searchQuery.endYear}" list="%{projectService.getYearMap()}" headerKey="-1" headerValue="--%{getText('不限')}--" />
					</td>
					<td class="adv_td1"></td>
					<td class="adv_td1" valign="top" align="right">
						结项成果：<br />
						<span class="select_all_box">全选&nbsp;<input id="checkAllProductTypeItem" name="check" type="checkbox"  title="点击全选" />
					</td>
					<td class="adv_td1">
						<s:checkboxlist name="productType" value="%{searchQuery.productType}" list="%{baseService.getSystemOptionMapAsName('productType', null)}" />
						<span id="productTypeOtherSpan" style="display:none;">
							<s:textfield name="productTypeOther" cssClass="input_css_other"/>
							<br/><span class="tip">多个以分号（即;或；）隔开</span>
						</span>
					</td>
					<td class="adv_td1" id="profuctTypeOtherTd"></td>
					<s:hidden id ="checkedProIssue" value="%{searchQuery.productType}" ></s:hidden>
				</tr>
				<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
					<tr class="adv_tr">
						<td class="adv_td1" align="right">提交状态：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="applicantSubmitStatus" value="%{searchQuery.applicantSubmitStatus}" list="#{'3':getText('提交'),'2':getText('暂存'),'1':getText('退回')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				   		</td>
						<td class="adv_td1"></td>
					</tr>
				</s:if>
				<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
					<tr class="adv_tr">
						<td class="adv_td1" align="right">结项状态：</td>
						<td class="adv_td1">
						   	<s:select cssClass="select" name="isApproved" value="%{searchQuery.isApproved}" list="#{'2':getText('同意'),'1':getText('不同意'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				   		</td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right">项目状态：</td>
						<td class="adv_td1"><s:select cssClass="select" name="projectStatus" value="%{searchQuery.projectStatus}" list="#{'1':getText('在研'), '2':getText('已结项'), '3':getText('已中止'), '4':getText('已撤项')}" headerKey="-1" headerValue="--%{getText('不限')}--" /></td>
						<td class="adv_td1"></td>
					</tr>
					<tr class="adv_tr">
						<td class="adv_td1" align="right">审核状态：</td>
						<td class="adv_td1">
							   <s:select cssClass="select" name="auditStatus" value="%{searchQuery.auditStatus}" list="#{'32':getText('同意'),'31':getText('不同意'),'22':getText('同意（暂存）'),'21':getText('不同意（暂存）'),'12':getText('退回'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				   		</td>
						<td class="adv_td1"></td>
						<td class="adv_td1" align="right">审核时间：</td>
						<td class="adv_td1">
							<s:textfield id="startDate1" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick j_startDate" disabled="disabled" size="10"/>
							至
							<s:textfield id="endDate1" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick j_endDate" disabled="disabled" size="10"/>
				   		</td>
						<td class="adv_td1"></td>
					</tr>
				</s:elseif>
				<s:else>
					<tr class="adv_tr">
						<td class="adv_td1" align="right">审核时间：</td>
						<td class="adv_td1">
							<s:textfield name="startDate" value="%{searchQuery.startDate}" cssClass="input_css_self date_hint FloraDatepick j_startDate" disabled="disabled"/>
							至
							<s:textfield name="endDate" value="%{searchQuery.endDate}" cssClass="input_css_self date_hint FloraDatepick j_startDate" disabled="disabled"/>
				   		</td>
						<td class="adv_td1"></td> 
						<td class="adv_td1" align="right">结项状态：</td>
						<td class="adv_td1">
					   		<s:select cssClass="select" name="finalAuditStatus" value="%{searchQuery.finalAuditStatus}" list="#{'32':getText('同意'),'31':getText('不同意'),'22':getText('同意（暂存）'),'21':getText('不同意（暂存）'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
				   		</td>
						<td class="adv_td1"></td>
					</tr>
					<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
						<tr class="adv_tr">
							<td class="adv_td1" align="right" >审核状态：</td>
							<td class="adv_td1">
						    	<s:select cssClass="select" name="auditStatus" value="%{searchQuery.auditStatus}" list="#{'32':getText('同意'),'31':getText('不同意'),'22':getText('同意（暂存）'),'21':getText('不同意（暂存）'),'12':getText('退回'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
					   		</td>
							<td class="adv_td1"></td>
							<td class="adv_td1" align="right">鉴定状态：</td>
							<td class="adv_td1">
						   		<s:select cssClass="select" name="reviewStatus" value="%{searchQuery.reviewStatus}" list="#{'32':getText('同意'),'31':getText('不同意'),'22':getText('同意（暂存）'),'21':getText('不同意（暂存）'),'0':getText('待审')}" headerKey="-1" headerValue="--%{getText('不限')}--" />
					   		</td>
							<td class="adv_td1"></td>
						</tr>
					</s:if>
					<tr class="adv_tr">
						<td class="adv_td1" align="right">项目状态：</td>
						<td class="adv_td1"><s:select cssClass="select" name="projectStatus" value="%{searchQuery.projectStatus}" list="#{'1':getText('在研'), '2':getText('已结项'), '3':getText('已中止'), '4':getText('已撤项')}" headerKey="-1" headerValue="--%{getText('不限')}--" /></td>
						<td class="adv_td1"></td>
					</tr>
				</s:else>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right"><s:text name="依托院系或研究基地" />：</td>
						<td class="adv_td1"><s:textfield name="divisionName" value="%{searchQuery.divisionName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@STUDENT)">
						<td class="adv_td1" align="right"><s:text name="高校所在省份" />：</td>
						<td class="adv_td1"><s:textfield name="provinceName" value="%{searchQuery.provinceName}" cssClass="input_css" /></td>
						<td class="adv_td1"></td>
					</s:if>
				</tr>
				<tr class="adv_tr">
					<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@STUDENT)">
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