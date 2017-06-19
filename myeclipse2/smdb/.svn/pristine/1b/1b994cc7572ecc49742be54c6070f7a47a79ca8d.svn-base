<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_DM">
		<head>
			<title><s:text name="i18n_UniversityVariation" /></title>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div class="link_bar">
				<s:text name="i18n_CurrentPosition" />：<s:text name="i18n_View" />
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update"/>
				
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="<s:text name='i18n_Return' />" /></li>
						<li id="view_next"><input class="btn1" type="button" value="<s:text name='i18n_NextRecord' />" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="<s:text name='i18n_PrevRecord' />" /></li>
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="<s:text name='i18n_Delete' />" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="<s:text name='i18n_Modify' />" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_DATAMANAGEMENT_UNIVERSITY_RENAME_ADD">
						<li id="view_add"><input class="btn1" type="button" value="<s:text name='i18n_Add' />" /></li>
					</sec:authorize>
					</ul>
				</div>
				
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						<div class="title_bar">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"><s:text name="i18n_NameOld" />：</td>
									<td class="title_bar_td" width="180">${universityVariation.nameOld}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"><s:text name="i18n_CodeOld" />：</td>
									<td class="title_bar_td" width="180">${universityVariation.codeOld}</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"><s:text name="i18n_NameNew" />：</td>
									<td class="title_bar_td" width="180">${universityVariation.nameNew}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"><s:text name="i18n_CodeNew" />：</td>
									<td class="title_bar_td" width="180">${universityVariation.codeNew}</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"><s:text name="变更时间" />：</td>
									<td class="title_bar_td" width="180">${universityVariation.date}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"><s:text name="i18n_UrType" />：</td>
									<td class="title_bar_td" width="180">{if universityVariation.type == 0}
										{elseif universityVariation.type == 1}更名
										{elseif universityVariation.type == 2}合并
										{/if}
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"><s:text name="i18n_Description" />：</td>
									<td class="title_bar_td" width="180">${universityVariation.description}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="120" align="right"></td>
									<td class="title_bar_td" width="180"></td>
								</tr>
							</table>
						</div>
					</div>
				</textarea>
				
				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/dm/universityVariation/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	</s:i18n>
</html>
