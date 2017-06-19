<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="csdc.tool.bean.AccountType"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_EntrustSubProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ProjectData" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_ApplyData" />
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/project/entrust/application/apply/search.jsp"/>
					</div>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllProjectOrNot' />" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</sec:authorize>
									<td width="30"><s:text name="i18n_Number" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectName' />"><s:text name="i18n_ProjectName" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="51"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByApplicant' />"><s:text name="i18n_Applicant" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td width="64"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUniversity' />"><s:text name="i18n_University" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:if>
									<td width="54"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectSubtype' />"><s:text name="i18n_IssueType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="54"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDisciplineType' />"><s:text name="i18n_DisciplineType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="54"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectYear' />"><s:text name="i18n_ProjectYear" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)"><!-- 研究人员-->
									<td width="64"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortBySubmitStatus' />"><s:text name="i18n_SubmitStatus" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:if>
								<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@DEPARTMENT, @csdc.tool.bean.AccountType@INSTITUTE)"><!-- 院系或研究基地-->
									<td width="64"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuditStatus' />"><s:text name="i18n_AuditStatus" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn14" href="" class="{if sortColumn == 14}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuditDate' />"><s:text name="i18n_AuditDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
								</s:elseif>
								<s:elseif test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)"><!-- 高校 -->
									<td width="64"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuditStatus' />"><s:text name="i18n_AuditStatus" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn15" href="" class="{if sortColumn == 15}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuditDate' />"><s:text name="i18n_AuditDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW">
										<td width="54"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByReviewStatus' />"><s:text name="i18n_ReviewStatus" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									</sec:authorize>
								</s:elseif>
								<s:elseif test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@PROVINCE)"><!-- 省厅 -->
									<td width="64"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuditStatus' />"><s:text name="i18n_AuditStatus" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn16" href="" class="{if sortColumn == 16}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuditDate' />"><s:text name="i18n_AuditDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW">
										<td width="54"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByReviewStatus' />"><s:text name="i18n_ReviewStatus" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									</sec:authorize>
								</s:elseif>
								<s:else>
									<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
										<td width="54"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuditStatus' />"><s:text name="i18n_AuditStatus" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW">
											<td width="54"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByReviewStatus' />"><s:text name="i18n_ReviewStatus" /></a></td>
											<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										</sec:authorize>
									</s:if>
									<td width="54"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByApplicationStatus' />"><s:text name="i18n_ApplicationStatus" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn17" href="" class="{if sortColumn == 17}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByApplicationDate' />"><s:text name="i18n_ApplicationDate" /></a></td>
								</s:else>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td width="64"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByEndinspectionStatus' />"><s:text name="i18n_ApplicationStatus" /></a></td>
								</s:if>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
								</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}"  class="link1" href="" title="<s:text name='i18n_ViewDetails' />"  type="1">${item.laData[1]}</a></td>
									<td></td>
									<td>
										{if item.laData[2]==""}${item.laData[3]}
										{else}<s:hidden id="directors" value="${item.laData[2]}" name="${item.laData[3]}" cssClass="directors" />
										{/if}
									</td>
									<td></td>
								<s:if test="!#session.loginer.currentType.within(@csdc.tool.bean.AccountType@MINISTRY_UNIVERSITY, @csdc.tool.bean.AccountType@INSTITUTE)">
									<td>
										{if item.laData[4]==""}${item.laData[5]}
										{else}<a id="${item.laData[4]}" class="view_university" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[5]}</a>
										{/if}
									</td>
									<td></td>
								</s:if>
									<td>${item.laData[6]}</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[8]}</td>
									<td></td>
								<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@EXPERT, @csdc.tool.bean.AccountType@STUDENT)">
									<td>
										{if item.laData[13]==3}<s:text name="i18n_Submit"/>
										{elseif item.laData[13]==2}<s:text name="i18n_Saved"/>
										{elseif item.laData[13]==1}<s:text name="i18n_SendBack"/>
										{else}
										{/if}
									</td>
									<td></td>
								</s:if>
								<s:elseif test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td>
										{if item.laData[13]==1 && item.laData[14]==1}<s:text name="i18n_SendBack"/>
										{elseif item.laData[13]==1 && item.laData[14]==2}<s:text name="i18n_SendBack"/>
										{elseif item.laData[13]==2 && item.laData[14]==1}<s:text name="i18n_SaveNotApprove"/>
										{elseif item.laData[13]==2 && item.laData[14]==2}<s:text name="i18n_SaveApprove"/>
										{elseif item.laData[13]==3 && item.laData[14]==1}<s:text name="i18n_NotApprove"/>
										{elseif item.laData[13]==3 && item.laData[14]==2}<s:text name="i18n_Approve"/>
										{else}<s:text name="i18n_Pending"/>
										{/if}
									</td>
									<td></td>
									<td>${item.laData[15]}</td>
									<td></td>
									<s:if test="#session.loginer.currentType.within(@csdc.tool.bean.AccountType@PROVINCE, @csdc.tool.bean.AccountType@LOCAL_UNIVERSITY)">
										<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW">
											<td>
												{if item.laData[16]==2 && item.laData[17]==1}<s:text name="i18n_SaveNotApprove"/>
												{elseif item.laData[16]==2 && item.laData[17]==2}<s:text name="i18n_SaveApprove"/>
												{elseif item.laData[16]==3 && item.laData[17]==2}<s:text name="i18n_Approve"/>
												{elseif item.laData[16]==3 && item.laData[17]==1}<s:text name="i18n_NotApprove"/>
												{else}<s:text name="i18n_Pending"/>
												{/if}
											</td>
											<td></td>
										</sec:authorize>
									</s:if>
								</s:elseif>	
								<s:else>
									<s:if test="#session.loginer.currentType.equals(@csdc.tool.bean.AccountType@MINISTRY)"><!-- 部级 -->
										<td>
											{if item.laData[13]==2 && item.laData[14]==1}<s:text name="i18n_SaveNotApprove"/>
											{elseif item.laData[13]==2 && item.laData[14]==2}<s:text name="i18n_SaveApprove"/>
											{elseif item.laData[13]==3 && item.laData[14]==2}<s:text name="i18n_Approve"/>
											{elseif item.laData[13]==3 && item.laData[14]==1}<s:text name="i18n_NotApprove"/>
											{else}<s:text name="i18n_Pending"/>
											{/if}
										</td>
										<td></td>
										<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_REVIEW_VIEW">
											<td>
												{if item.laData[15]==2 && item.laData[16]==1}<s:text name="i18n_SaveNotApprove"/>
												{elseif item.laData[15]==2 && item.laData[16]==2}<s:text name="i18n_SaveApprove"/>
												{elseif item.laData[15]==3 && item.laData[16]==2}<s:text name="i18n_Approve"/>
												{elseif item.laData[15]==3 && item.laData[16]==1}<s:text name="i18n_NotApprove"/>
												{else}<s:text name="i18n_Pending"/>
												{/if}
											</td>
											<td></td>
										</sec:authorize>
									</s:if>
										<td>
											{if item.laData[12] == 2 && item.laData[9] == 1 }<s:text name="i18n_SaveNotApprove"/>
											{elseif item.laData[12] == 2 && item.laData[9] == 2}<s:text name="i18n_SaveApprove"/>
											{elseif item.laData[12] == 3 && item.laData[9] == 1}<s:text name="i18n_NotApprove"/>
											{elseif item.laData[12] == 3 && item.laData[9] == 2}<s:text name="i18n_Approve"/>
											{else}<s:text name="i18n_Pending"/>
											{/if}	
										</td>
										<td></td>
										<td>${item.laData[17]}</td>
								</s:else>
								<s:if test="#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@MINISTRY)>0">
									<td>
										{if item.laData[12] == 3 && item.laData[9] == 1}<s:text name="i18n_NotApprove"/>
										{elseif item.laData[12] == 3 && item.laData[9] == 2}<s:text name="i18n_Approve"/>
										{else}<s:text name="i18n_Pending"/>
										{/if}
									</td>
								</s:if>
								</tr>	
							{forelse}
								<tr>
									<td align="center"><s:text name="i18n_NoRecords" /></td>
								</tr>
							{/for}
							</tbody>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr2">
								<td width="4"></td>
								<s:if test = "#session.loginer.currentType.compareTo(@csdc.tool.bean.AccountType@EXPERT)<0 ">
									<td width="58" align="center"><sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_ADD"><input class="btn1" type="button" id="list_add_result" value="<s:text name='i18n_AddResult' />" /></sec:authorize></td>
								</s:if>
								<s:else>
									<td width="58" align="center"><sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_ADD"><input class="btn1" type="button" id="list_add" value="<s:text name='i18n_Apply' />" /></sec:authorize></td>
									<s:hidden name="deadline" id="deadline" value="%{projectService.checkIfTimeValidate(#session.loginer.currentType, '011')}"/>
									<s:hidden name="appStatus" id="appStatus" value="%{projectService.getBusinessStatus('051')}"/>
								</s:else>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<td width="58"><input id="list_delete" type="button" class="btn1" value="<s:text name='i18n_Delete' />" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_PUBLISH">
									<td width="58"><input id="list_publish" type="button" class="btn1" value="<s:text name='i18n_Publish' />" /></td>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_PUBLISH">
									<td width="70"><input id="list_notPublish" type="button" class="btn2" value="<s:text name='i18n_NotPublish' />" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="delete" namespace="/project/entrust/application/apply">
						<s:hidden id="pagenumber" name="pageNumber" />
						<s:hidden id="type" name="type" value="1" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			 <div id="container" style="width:779px;height:300px;"></div>
			<script type="text/javascript">
				seajs.use('javascript/project/entrust/application/apply/list.js', function(list) {
					list.init();
				});
			</script>
		</body>
	</s:i18n>
</html>