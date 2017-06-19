<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Other">
		<head>
			<title><s:text name="i18n_FirstAudit" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar" id="view_project_general">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_OtherData" />&nbsp;&gt;&nbsp;<s:text name="i18n_Nssf" />&nbsp;&gt;&nbsp;<s:text name="i18n_ApplyData" />&nbsp;&gt;&nbsp;<s:text name="i18n_FirstAudit" />
			</div>

			<div class="main">
				<div class="p_box_t">
					<div class="p_box_t_t"><s:text name="i18n_FirstAuditConfig" /></div>
					<div class="p_box_t_b">
						<img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" />
					</div>
				</div>
				<div class="p_box_body">
			        <table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
						<tbody>
							<tr class="table_tr7">
								<td class="head_title1" width="80"><s:text name="i18n_ProjectYear" /></td>
								<td class="value"><s:select cssClass="select" id="select_year" name="year" list="%{nssfService.getYearMap()}" /></td>
							</tr>
							<tr class="table_tr7">
								<td class="head_title1"><s:text name="i18n_FirstAuditRule" /></td>
								<td class="value">申请国家社科基金的负责人不能同时申请同年度的教育部一般项目和基地项目。</td>
							</tr>
						</tbody>
					</table>
					<div style="text-align:center; margin-top:10px;">
						<input id="firstAudit" class="btn2" type="button" value="<s:text name='i18n_StartFirstAudit' />" />
						&nbsp;&nbsp;&nbsp;
						<input id="viewResult" class="btn2" type="button" value="<s:text name='i18n_ViewResult' />" />
					</div>
				</div>
			</div>
			<div id="firstAuditResult" class="main">
				<div class="p_box_t">
					<div class="p_box_t_t"><s:text name='i18n_FirstAuditResult' /></div>
					<div class="p_box_t_b">
						<img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" />
					</div>
				</div>
				<div class="p_box_body">
					<div id="main">
						<s:form id="search" theme="simple" action="simpleSearch" namespace="/other/nssf/application">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
								<tr class="table_main_tr">
									<td align="right"><span class="choose_bar">
										<s:select cssClass="select" name="searchType" headerKey="-1" headerValue="--%{getText('i18n_NoLimit')}--" value="-1"
											list="#{
												'0':getText('i18n_Name'),
												'1':getText('i18n_Type'),
												'13':getText('i18n_University'),
												'3':getText('i18n_Applicant'),
												'12':getText('i18n_FirstAuditDate'),
												'11':getText('i18n_FirstAuditResult')
											}"
										/>
									</span><s:textfield  id="keyword" name="keyword" cssClass="keyword" size="10" />
										<s:hidden id="list_pagenumber" name="pageNumber" />
										<s:hidden id="list_sortcolumn" name="sortColumn" />
										<s:hidden id="list_pagesize" name="pageSize" />
										<s:hidden id="search_year" name="year" />
									</td>
									<td width="66"><input id="list_button_query" type="button" value="<s:text name='i18n_Search' />" class="btn1" /></td>
								</tr>
							</table>
						</s:form>
	
						<textarea id="list_template" style="display:none;">
							<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
								<thead id="list_head">
									<tr class="table_title_tr">
										<td width="40"><s:text name="i18n_Number" /></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="150"><a id="sortcolumn0" href="" class="{if sortColumn == 0}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByName' />"><s:text name="i18n_Name" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="90"><a id="sortcolumn1" href="" class="{if sortColumn == 1}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByType' />"><s:text name="i18n_Type" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="90"><a id="sortcolumn13" href="" class="{if sortColumn == 13}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByUniversity' />"><s:text name="i18n_University" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="70"><a id="sortcolumn3" href="" class="{if sortColumn == 3}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByApplicant' />"><s:text name="i18n_Applicant" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td width="70"><a id="sortcolumn12" href="" class="{if sortColumn == 12}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByFirstAuditDate' />"><s:text name="i18n_FirstAuditDate" /></a></td>
										<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
										<td><a id="sortcolumn11" href="" class="{if sortColumn == 11}{if sortColumnLabel == 1}up_css{else}down_css{/if}{/if}" title="<s:text name='i18n_SortByFirstAuditResult' />"><s:text name="i18n_FirstAuditResult" /></a></td>
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
										<td>${item.laData[13]}</td>
										<td></td>
										<td>${item.laData[3]}</td>
										<td></td>
										<td>${item.laData[12]}</td>
										<td></td>
										<td>${item.laData[11]}</td>
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
									<td width="58"><input id="export" type="button" class="btn1" value="<s:text name='i18n_Export' />" /></td>
								</tr>
							</table>
						</textarea>
						<div id="list_container" style="display:none;"></div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/other/nssf/application/firstAuditConfig.js', function(view) {
					view.init();
				});
			</script>
		</body>
	</s:i18n>
</html>