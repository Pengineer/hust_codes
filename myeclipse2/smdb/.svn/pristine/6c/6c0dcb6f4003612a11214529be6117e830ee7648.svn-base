<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Project">
		<head>
			<title><s:text name="i18n_MyProject" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_MyProject" />
			</div>
			
			<div class="main">
				<div class="main_content">
					<s:form id="search" theme="simple" action="%{#request.url}" namespace="/project">
					</s:form>
					
					<textarea id="list_template" style="display:none;">
						<table id="list_table" width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
							<thead id="list_head">
								<tr class="table_title_tr">
									<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_DELETE,ROLE_PROJECT_INSTP_APPLICATION_APPLY_DELETE,ROLE_PROJECT_POST_APPLICATION_APPLY_DELETE,
										ROLE_PROJECT_KEY_APPLICATION_APPLY_DELETE,ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<td width="20"><input id="check" name="check" type="checkbox"  title="<s:text name='i18n_SelectAllProjectOrNot' />" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									</sec:authorize>
									<td width="30"><s:text name="i18n_Number" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td><s:text name="i18n_ProjectName" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><s:text name="i18n_ProjectType" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><s:text name="i18n_ProjectSubtype" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><s:text name="i18n_Director" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><s:text name="i18n_MyOrder" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="100"><s:text name="i18n_University" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><s:text name="i18n_ProjectYear" /></td>
									<td width="2"><img src="image/table_line.gif" width="2" height="24" /></td>
									<td width="70"><s:text name="i18n_ProjectStatus" /></td>
								</tr>
							</thead>
							<tbody>
							{for item in root}
								<tr>
									<sec:authorize ifAllGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_DELETE,ROLE_PROJECT_INSTP_APPLICATION_APPLY_DELETE,ROLE_PROJECT_POST_APPLICATION_APPLY_DELETE,
										ROLE_PROJECT_KEY_APPLICATION_APPLY_DELETE,ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_DELETE">
									<td><input type="checkbox" name="entityIds" value="${item.laData[0]}" /></td>
									<td></td>
									</sec:authorize>
									<td>${item.num}</td>
									<td></td>
									<td class="table_txt_td"><a id="${item.laData[0]}" name="${item.laData[11]}" class="link1" href="" title="<s:text name='i18n_ViewDetails' />" type="7">${item.laData[1]}</a></td>
									<td></td>
									<td>${item.laData[4]}</td>
									<td></td>
									<td>${item.laData[5]}</td>
									<td></td>
									<td>
										{if item.laData[9]==""}${item.laData[10]}
										{else}<a id="${item.laData[9]}" class="view_director" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[10]}</a>
										{/if}
									</td>
									<td></td>
									<td>${item.laData[7]}</td>
									<td></td>
									<td>
										{if item.laData[2]==""}${item.laData[3]}
										{else}<a id="${item.laData[2]}" class="view_university" href="" title="<s:text name='i18n_ViewDetails' />">${item.laData[3]}</a>
										{/if}
									</td>
									<td></td>
									<td>${item.laData[6]}</td>
									<td></td>
									<td>
										{if item.laData[8] == 0}<s:text name="i18n_UnGranted"/>
										{elseif item.laData[8] == 1}<s:text name="i18n_InStudy" />
										{elseif item.laData[8] == 3}<s:text name="i18n_Suspend" />
										{elseif item.laData[8] == 2}<s:text name="i18n_Complete" />
										{elseif item.laData[8] == 4}<s:text name="i18n_Revoke" />
										{elseif item.laData[8] == 5}<s:text name="i18n_Reviewed" />
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
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_td_padding">
						<tr class="table_main_tr2">
							<td width="4"></td>
							<sec:authorize ifAnyGranted="ROLE_PROJECT_GENERAL_APPLICATION_APPLY_ADD,ROLE_PROJECT_ENTRUST_APPLICATION_APPLY_ADD,
														ROLE_PROJECT_INSTP_APPLICATION_APPLY_ADD,ROLE_PROJECT_KEY_APPLICATION_APPLY_ADD,ROLE_PROJECT_POST_APPLICATION_APPLY_ADD">
								<td width="58"><input id="list_apply" type="button" class="btn1" value="<s:text name='申报' />" /></td>
							</sec:authorize>
							<td align="right" style="color:#FFF;"></td></tr>
						</table>
					</textarea>
					<div id="list_container" style="display:none;"></div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/project/project_share/list_my_project.js', function(list) {
					list.init();
				});
			</script>
		</body>
	</s:i18n>
</html>