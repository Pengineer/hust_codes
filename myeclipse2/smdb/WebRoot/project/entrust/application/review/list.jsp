<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_ReviewProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_EntrustSubProject" />&nbsp;&gt;&nbsp;<s:text name="i18n_ApplyData" />
			</div>
			
			<div class="main">
				<div class="main_content">
					<div class="table_main_tr_adv">
						<s:include value="/project/entrust/application/review/search.jsp"/>
					</div>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<td width="30"><s:text name="i18n_Number" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectName' />"><s:text name="i18n_ProjectName" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="40"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDirector' />"><s:text name="i18n_Director" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn2" href="" class="{if sortColumn == 2}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUniversity' />"><s:text name="i18n_University" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectSubtype' />"><s:text name="i18n_IssueType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="52"><a id="sortcolumn4" href="" class="{if sortColumn == 4}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByDisciplineType' />"><s:text name="i18n_DisciplineType" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="52"><a id="sortcolumn5" href="" class="{if sortColumn == 5}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByProjectYear' />"><s:text name="i18n_ProjectYear" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="52"><a id="sortcolumn6" href="" class="{if sortColumn == 6}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByReviewStatus' />"><s:text name="i18n_ReviewStatus" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="64"><a id="sortcolumn7" href="" class="{if sortColumn == 7}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByReviewDate' />"><s:text name="i18n_ReviewDate" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="51"><a id="sortcolumn9" href="" class="{if sortColumn == 9}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByGroupReviewStatus' />"><s:text name="i18n_GroupReviewStatus" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="38"><a id="sortcolumn8" href="" class="{if sortColumn == 8}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByScore' />"><s:text name="i18n_Score" /></a></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="39"><s:text name="i18n_ApplicationFile" /></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" class="link1" href="" title="<s:text name='i18n_ViewDetails' />" type="10">${item.laData[1]}</a></td>
									<td></td>
									<td><a id="${item.laData[4]}" class="view_applicant" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[5]}</a></td>
									<td></td>
									<td><a id="${item.laData[2]}" class="view_university" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[3]}</a></td>
									<td></td>
									<td>${item.laData[6]}</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>${item.laData[8]}</td>
									<td></td>
									<td>
										{if item.laData[9]==0}<s:text name="i18n_Pending"/>
										{elseif item.laData[9]==2}<s:text name="i18n_Saved"/>
										{elseif item.laData[9]==3}<s:text name="i18n_Submit"/>
										{else}
										{/if}
									</td>
									<td></td>
									<td>${item.laData[10]}</td>
									<td></td>
									<td>
										{if item.laData[13]==0}<s:text name="i18n_Pending"/>
										{elseif item.laData[13]==2 && item.laData[14]==1}<s:text name="i18n_SaveNotApprove"/>
										{elseif item.laData[13]==2 && item.laData[14]==2}<s:text name="i18n_SaveApprove"/>
										{elseif item.laData[13]==3 && item.laData[14]==1}<s:text name="i18n_NotApprove"/>
										{elseif item.laData[13]==3 && item.laData[14]==2}<s:text name="i18n_Approve"/>
										{else}
										{/if}
									</td>
									<td></td>
									<td>${item.laData[11]}</td>
									<td></td>
									<td>
										{if item.laData[12]!=null && item.laData[12]!=""}
											<a id="${item.laData[0]}" name="${item.laData[0]}"  class="download_entrust_3" href="">
												<img src="image/ico03.gif" title="<s:text name='i18n_Download' />" /></a>
										{/if}
									</td>
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
							</tr>
						</table>
					</textarea>
					<s:form id="list" theme="simple" action="" namespace="/project/entrust/application/review">
						<s:hidden id="pagenumber" name="pageNumber" />
						<div id="list_container" style="display:none;"></div>
					</s:form>
				</div>
			</div>
			<div id="container" style="width:779px;height:300px;"></div>
			<script type="text/javascript">
				seajs.use('javascript/project/entrust/application/review/list.js', function(list) {
					list.init();
				});
			</script>
		</body>
	</s:i18n>
</html>