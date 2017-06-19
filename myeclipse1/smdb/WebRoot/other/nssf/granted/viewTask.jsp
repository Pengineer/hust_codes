<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
							<td class="title_bar_td" width="85" align="right">已完成任务：</td>
							<td class="title_bar_td" width="120">${finishedCount}</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">未完成任务：</td>
							<td class="title_bar_td" >${unfinishedCount}</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">执行中任务：</td>
							<td class="title_bar_td" >${runningCount}</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">最后更新时间：</td>
							<td class="title_bar_td" >${lastUpdateDate}</td>
						</tr>
						{if isCrawlerOutdated == true}
						<tr>
							<td class="title_bar_td" align="center" colspan="3" style="color:red">页面已改变，爬虫无法工作！</td>
						</tr>
						{/if}

					</table>
				</textarea>
			</div>
			<div class="btn_div_view">
				<input id="btn_update" class="btn1" type="button" value="更新" />
				<input id="cancel" class="btn1" type="button" value="取消" />
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/other/nssf/granted/viewTask.js', function(view) {
				view.init();
			});
		</script>
	</body>
</html>