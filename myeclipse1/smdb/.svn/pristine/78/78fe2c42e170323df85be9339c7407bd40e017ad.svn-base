<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/innerBase.jsp" />
	</head>

	<body>
		<div style="width:350px;">
			<div class="title_bar">
				
				<textarea class="view_template" style="display:none">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="85" align="right">请输入年份：</td>
							<td class="title_bar_td" width="200">
								<select id="startYear" class="select-year">
									<option value="-1">--请选择--</option>
								{if unfinishedCount==0}
									{for item in years}
									<option value="${item}">${item} </option>
									{forelse}
									<option>null</option>
									{/for}
								{/if}
								</select>至
								<select id="endYear" class="select-year">
									<option value="-1">--请选择--</option>
								{if unfinishedCount==0}
									{for item in years}
									<option value="${item}">${item} </option>
									{forelse}
									<option>null</option>
									{/for}
								{/if}
								</select>
								<span style="font-size:10px;display:block;color:#888;">（建议年度区间不大于3）</span>
								<%--<s:select list="{years}" theme="simple" headerKey="-1" headerValue="--请选择--"></s:select>至
								<s:select list="{'1997','1998','1999'}" theme="simple" headerKey="-1" headerValue="--请选择--"></s:select>
							--%>
							
							</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">已完成任务：</td>
							<td class="title_bar_td" id="finishedCount">${finishedCount}</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">未完成任务：</td>
							<td class="title_bar_td" id="unfinishedCount">${unfinishedCount}</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">执行中任务：</td>
							<td class="title_bar_td" id="runningCount">${runningCount}</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">已更新条数：</td>
							<td class="title_bar_td" id="updatedCount">${updatedCount}</td>
						</tr>
						<tr>
							<td class="title_bar_td" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" align="right">最后更新时间：</td>
							<td class="title_bar_td" id="lastUpdateDate">${lastUpdateDate}</td>
						</tr>
						{if isCrawlerOutdated == true}
						<tr>
							<td class="title_bar_td" align="center" colspan="3" style="color:red">页面已改变，爬虫无法工作！</td>
						</tr>
						{/if}
					</table>
				</textarea>
				<div style="color:red; line-height:22px; padding-left:110px;" id="field_error"></div>
			</div>
			<div class="btn_div_view">
				<input id="btn_update" class="btn1" type="button" value="更新" />
				<input id="btn_cancel" class="btn1" type="button" value="取消" />
				<input id="btn_confirm" class="btn1" type="button" value="确定" style="display:none;"/>
			</div>
		</div>
		<script type="text/javascript">
			seajs.use('javascript/other/nsfc/granted/viewTask.js', function(view) {
				view.init();
			});
		</script>
	</body>
</html>