<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Other">
		<head>
			<title><s:text name="i18n_NssfApplication" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_OtherData" />&nbsp;&gt;&nbsp;<s:text name="i18n_Nssf" />&nbsp;&gt;&nbsp;<s:text name="i18n_ApplyData" />
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="list" namespace="/other/nssf/application">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<tr class="table_main_tr">
								<td align="right"><span class="choose_bar">
									<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" value="-1"
										list="#{
											'0':getText('i18n_Name'),
											'1':getText('i18n_Type'),
											'2':getText('i18n_Discipline'),
											'3':getText('i18n_Applicant'),
											'4':getText('i18n_Gender'),
											'5':getText('i18n_National'),
											'6':getText('i18n_Birthday'),
											'7':getText('i18n_Unit'),
											'8':getText('i18n_Province'),
											'9':getText('i18n_ProductName'),
											'10':getText('i18n_Year')
										}"
									/>
								</span><s:textfield id="keyword" name="keyword" cssClass="keyword" size="10" />
									<s:hidden id="list_pagenumber" name="pageNumber" />
									<s:hidden id="list_sortcolumn" name="sortColumn" />
									<s:hidden id="list_pagesize" name="pageSize" />
								</td>
								<td width="66"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
							</tr>
						</table>
					</s:form>

					<textarea id="list_template" style="display:none;"><div style="overflow-x:scroll">
						<table id="list_table" width="1200" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="40"><s:text name="i18n_Number" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td>           <a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByName' />"><s:text name="i18n_Name" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByType' />"><s:text name="i18n_Type" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="65"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDiscipline' />"><s:text name="i18n_Discipline" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByApplicant' />"><s:text name="i18n_Applicant" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByGender' />"><s:text name="i18n_Gender" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="90"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByNational' />"><s:text name="i18n_National" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByBirthday' />"><s:text name="i18n_Birthday" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUnit' />"><s:text name="i18n_Unit" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProvince' />"><s:text name="i18n_Province" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProductName' />"><s:text name="i18n_ProductName" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><a id="sortcolumn10" href="" class="{if sortColumn == 10}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByYear' />"><s:text name="i18n_Year" /></a></td>
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
								<sec:authorize ifAllGranted="ROLE_OTHER_NSSF_FIRST_AUDIT">
									<td width="58"><input id="firstAudit" type="button" class="btn1" value="<s:text name='i18n_FirstAudit' />" /></td>
								</sec:authorize>
							</tr>
						</table>
					</textarea>
					<div id="list_container" style="display:none;"></div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/other/nssf/application/list.js', function(list) {
					$(function(){
						list.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>
