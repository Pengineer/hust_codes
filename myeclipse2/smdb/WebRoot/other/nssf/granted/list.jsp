<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Other">
		<head>
			<title><s:text name="i18n_Nssf" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_OtherData" />&nbsp;&gt;&nbsp;<s:text name="i18n_Nssf" />&nbsp;&gt;&nbsp;<s:text name="i18n_GrantedData" />
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/other/nssf/granted">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr">
								<td align="right"><span class="choose_bar">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" value="-1"
										list="#{
											'0':getText('i18n_Name'),
											'1':getText('i18n_ProjectNumber'),
											'2':getText('i18n_Type'),
											'3':getText('i18n_DisciplineType'),
											'4':getText('i18n_StartDate'),
											'5':getText('i18n_Applicant'),
											'6':getText('i18n_SpecialityTitle'),
											'7':getText('i18n_Unit'),
											'8':getText('i18n_UnitType'),
											'9':getText('i18n_Province'),
											'10':getText('i18n_BelongSystem'),
											'11':getText('i18n_ProductName'),
											'12':getText('i18n_ProductType'),
											'13':getText('i18n_ProductLevel'),
											'14':getText('i18n_EndDate'),
											'15':getText('i18n_Certificate'),
											'16':getText('i18n_Press'),
											'17':getText('i18n_PublishDate'),
											'18':getText('i18n_Author'),
											'19':getText('i18n_PrizeObtained'),
											'20':getText('i18n_SingleSubject'),
											'21':getText('i18n_Topic'),
											'22':getText('i18n_Subject'),
											'23':getText('i18n_Description'),
											'24':getText('i18n_Report'),
											'25':getText('i18n_Experts'),
											'26':getText('i18n_NoIdentifyReason'),
											'27':getText('i18n_PlanEndDate')
										}"
									/>
								</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
								</td>
								<td width="66"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
							</tr>
						</table>
					</s:form>

					<textarea id="list_template" style="display:none;"><div style="overflow-x:scroll">
						<table id="list_table" width="2500" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="40"><s:text name="i18n_Number" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td>           <a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByName' />"><s:text name="i18n_Name" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectNumber' />"><s:text name="i18n_ProjectNumber" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByType' />"><s:text name="i18n_Type" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDisciplineType' />"><s:text name="i18n_DisciplineType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByStartDate' />"><s:text name="i18n_StartDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByApplicant' />"><s:text name="i18n_Applicant" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortBySpecialityTitle' />"><s:text name="i18n_SpecialityTitle" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUnit' />"><s:text name="i18n_Unit" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUnitType' />"><s:text name="i18n_UnitType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProvince' />"><s:text name="i18n_Province" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByBelongSystem' />"><s:text name="i18n_BelongSystem" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProductName' />"><s:text name="i18n_ProductName" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProductType' />"><s:text name="i18n_ProductType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn13" href="" class="{if sortColumn == 13}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProductLevel' />"><s:text name="i18n_ProductLevel" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn14" href="" class="{if sortColumn == 14}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByEndDate' />"><s:text name="i18n_EndDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn15" href="" class="{if sortColumn == 15}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByCertificate' />"><s:text name="i18n_Certificate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn16" href="" class="{if sortColumn == 16}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByPress' />"><s:text name="i18n_Press" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn17" href="" class="{if sortColumn == 17}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByPublishDate' />"><s:text name="i18n_PublishDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn18" href="" class="{if sortColumn == 18}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByAuthor' />"><s:text name="i18n_Author" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn19" href="" class="{if sortColumn == 19}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByPrizeObtained' />"><s:text name="i18n_PrizeObtained" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn20" href="" class="{if sortColumn == 20}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortBySingleSubject' />"><s:text name="i18n_SingleSubject" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn21" href="" class="{if sortColumn == 21}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByTopic' />"><s:text name="i18n_Topic" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn22" href="" class="{if sortColumn == 22}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortBySubject' />"><s:text name="i18n_Subject" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn23" href="" class="{if sortColumn == 23}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDescription' />"><s:text name="i18n_Description" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn24" href="" class="{if sortColumn == 24}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByReport' />"><s:text name="i18n_Report" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn25" href="" class="{if sortColumn == 25}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByExperts' />"><s:text name="i18n_Experts" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn26" href="" class="{if sortColumn == 26}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByNoIdentifyReason' />"><s:text name="i18n_NoIdentifyReason" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn27" href="" class="{if sortColumn == 27}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByPlanEndDate' />"><s:text name="i18n_PlanEndDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn28" href="" class="{if sortColumn == 28}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByStatus' />"><s:text name="i18n_Status" /></a></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<td>${item.num}</td>
									<td></td>
									<td>${item.laData[0]}</td>
									<td></td>
									<td>${item.laData[1]}</td>
									<td></td>
									<td>${item.laData[2]}</td>
									<td></td>
									<td>${item.laData[3]}</td>
									<td></td>
									<td>${item.laData[4]}</td>
									<td></td>
									<td>${item.laData[5]}</td>
									<td></td>
									<td>${item.laData[6]}</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[8]}</td>
									<td></td>
									<td>${item.laData[9]}</td>
									<td></td>
									<td>${item.laData[10]}</td>
									<td></td>
									<td>${item.laData[11]}</td>
									<td></td>
									<td>${item.laData[12]}</td>
									<td></td>
									<td>${item.laData[13]}</td>
									<td></td>
									<td>${item.laData[14]}</td>
									<td></td>
									<td>${item.laData[15]}</td>
									<td></td>
									<td>${item.laData[16]}</td>
									<td></td>
									<td>${item.laData[17]}</td>
									<td></td>
									<td>${item.laData[18]}</td>
									<td></td>
									<td>${item.laData[19]}</td>
									<td></td>
									<td>${item.laData[20]}</td>
									<td></td>
									<td>${item.laData[21]}</td>
									<td></td>
									<td>${item.laData[22]}</td>
									<td></td>
									<td>${item.laData[23]}</td>
									<td></td>
									<td>${item.laData[24]}</td>
									<td></td>
									<td>${item.laData[25]}</td>
									<td></td>
									<td>${item.laData[26]}</td>
									<td></td>
									<td>${item.laData[27]}</td>
									<td></td>
									<td>
										{if item.laData[28] == 0}<s:text name="i18n_Default" />
										{elseif item.laData[28] == 1}<s:text name="i18n_InStudy" />
										{elseif item.laData[28] == 2}<s:text name="i18n_Complete" />
										{elseif item.laData[28] == 3}<s:text name="i18n_Suspend" />
										{elseif item.laData[28] == 4}<s:text name="i18n_Revoke" />
										{else}
										{/if}
									</td>
								</tr>
							{forelse}
								<tr>
									<td align="center"><s:text name="i18n_NoRecords" /></td>
								</tr>
							{/for}
							</tbody>
						</table></div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr2">
								<td width="4"></td>
								<sec:authorize ifAllGranted="ROLE_OTHER_NSSF_UPDATE">
									<td width="58"><input id="list_update" type="button" class="btn1" value="<s:text name='i18n_Update' />" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="update" namespace="/other/nssf/granted">
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/other/nssf/granted/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>
