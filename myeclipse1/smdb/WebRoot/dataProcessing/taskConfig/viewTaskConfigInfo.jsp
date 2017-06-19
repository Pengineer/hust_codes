<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>查看任务配置状况</title>
  	<s:include value="/innerBase.jsp" />
</head>

<body>
    <div class="link_bar">
        当前位置：任务配置模块&nbsp;&gt;&nbsp; 查看任务配置详情
    </div>
    <s:hidden name = "taskConfigId" id = "taskConfigId" value = "%{taskConfigId}"/>
    <div class="main">
    <div class="choose_bar">
				<ul>
					<li id="view_back"><input class="btn1" type="button" value="返回"></li>
					<li id="view_del"><input class="btn1" type="button" value="删除"></li>
				</ul>
			</div>
    	<div class="main_content">
    		<textarea id = "view_template" style = "display:none">
	    		<div class="title_bar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right"> 任务配置名称：</td>
									<td class="title_bar_td" colspan="" id = "taskConfigName">${taskConfigName}</td>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">任务类型：</td>
									<td class="title_bar_td" colspan="" id = "taskType">{if isAuto == "true"} 自动任务 {else} 手动任务 {/if }</td>
								</tr>
								<tr>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">执行时间：</td>
									<td class="title_bar_td" colspan="" id = "executeTime">${executeTime }</td>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">执行时间间隔：</td>
									<td class="title_bar_td" colspan="" id = "interval">${interval } 分钟</td>
								</tr>
								<tr>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">任务开始时间：</td>
									<td class="title_bar_td" colspan="" id = "interval">${beginDate }</td>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">当前状态：</td>
									<td class="title_bar_td" colspan="" id = "interval">${status }</td>
								</tr>
								<tr>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">任务结束时间：</td>
									<td class="title_bar_td" colspan="" id = "interval">${endDate }</td>
									{if isAuto == "true"}
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">已成功执行次数：</td>
									<td class="title_bar_td" colspan="" id = "interval"> ${successExecuteNum }</td>
									{/if}
								</tr>
								{if isAuto == "true"}
								<tr>
									<td class="title_bar_td" width="20" align="right"><img src="image/ico08.gif" width="5" height="9"></td>
									<td class="title_bar_td" width="100" align="right">下一次执行时间：</td>
									<td class="title_bar_td" colspan="" id = "interval">${nextExecuteDate }</td>
								</tr>
								{/if}
							</tbody>
						</table>
					</div>
			</textarea>
			<div id = "view_container"></div>
    	</div>
    </div>
    <script type="text/javascript">
    seajs.use('javascript/dataProcessing/taskConfig/viewTaskConfigInfo.js', function(viewTaskConfigInfo) {
        $(function() {
        	viewTaskConfigInfo.init();
        })
    });
    </script>
</body>

</html>

