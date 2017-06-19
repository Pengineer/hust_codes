<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_Other">
		<head>
			<s:include value="/innerBase.jsp" />
		</head>

		<body>
			<div style="width:300px;">
				<div class="title_bar">
					<textarea class="view_template" style="display:none">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" width="85" align="right"><s:text name="i18n_FinishedTask" />：</td>
								<td class="title_bar_td" width="120">${finishedCount}</td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_UnfinishedTask" />：</td>
								<td class="title_bar_td" >${unfinishedCount}</td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_RunningTask" />：</td>
								<td class="title_bar_td" >${runningCount}</td>
							</tr>
							<tr>
								<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
								<td class="title_bar_td" align="right"><s:text name="i18n_LastUpdateDate" />：</td>
								<td class="title_bar_td" >${lastUpdateDate}</td>
							</tr>
							{if isCrawlerOutdated == true}
							<tr>
								<td class="title_bar_td" align="center" colspan="3" style="color:red"><s:text name="i18n_CrawlerError" /></td>
							</tr>
							{/if}

						</table>
					</textarea>
				</div>
				<div class="btn_div_view">
					<input id="btn_update" class="btn1" type="button" value="<s:text name='i18n_Update' />" />
					<input id="cancel" class="btn1" type="button" value="<s:text name='i18n_Cancel' />" />
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/other/nssf/granted/viewTask.js', function(view) {
					view.init();
				});
			</script>
		</body>
	</s:i18n>
</html>
