<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>right</title>
		<s:include value="/jsp/innerBase.jsp" />
	</head>
	<body class="g-pageRight">
		<div id = "container">
			<div id = "todoList">
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="javascript/oa/workflow/runningWorkflow_list.js"></script>
	<script type="text/javascript">
		$( document ).ready(function() {
			$.ajax({
				  url: '/eoas/workflow/todoList.action',
				  dataType: 'json',
				  success: function(o, pio, data) {
						var ct = "<ol>";
						$.each(o.result, function() {
							ct += "<li>" + this.pdname + "->PID:" + this.pid + "-><span class='ui-state-highlight ui-corner-all'>" + this.name + "</span>";
							ct += "<span class='version' title='流程定义版本：" + this.pdversion + "'><b>V:</b>" + this.pdversion + "</span>";
							ct += "<a class='trace' href='#' pid='" + this.pid + "' title='点击查看流程图'>跟踪</a>";
							ct += "<span class='status' title='任务状态'>" + (this.status == 'claim' ? '未签收' : '') + "</span>";
							ct += "</li>";
						});
						document.getElementById('todoList').insertAdjacentHTML('afterbegin', ct + "</ol>");

						return ct + "</ol>";
					}
				  
				});
			
			
		})
	</script>

</html>
 