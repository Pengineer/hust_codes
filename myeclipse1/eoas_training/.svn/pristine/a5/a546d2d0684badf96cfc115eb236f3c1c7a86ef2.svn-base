<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv = "Content-Type" content="text/html;charset=utf-8">
    <title>公司招聘</title>
	<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="css/recruitment/common.css">
	<style type="text/css" title="currentStyle">
			@import "tool/dataTables/css/demo_page.css";
			@import "tool/dataTables/examples/examples_support/themes/smoothness/jquery-ui-1.8.4.custom.css";
			@import "tool/dataTables/css/demo_table_jui.css";
			@import "tool/dataTables/css/jquery.dataTables_themeroller.css";
			@import "tool/dataTables/css/jquery.dataTables.css";
			@import "tool/dataTables/images/back_enabled_hover.png";
			@import "tool/dataTables/images/forward_enabled.png";
			@import "tool/dataTables/images/sort_asc.png";
			@import "tool/dataTables/images/sort_desc.png";
			@import "tool/dataTables/images/Sorting icons.psd";
	</style>
  </head>
	<body>
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div id="wrap">
			<div class="content">
				<s:form name ="selectedPositions" action="delete" theme="simple" namespace="/position">
					<div class="panel panel-success">
						<div class="panel-heading">职位申请记录</div>
						<table id="applyPositionList" class="table">
				          <thead>
				            <tr>     
				              <th>姓名</th>
				              <th>简历名称</th>
				              <th>应聘职位</th>
				              <th>职位发布时间</th>
				              <th>职位申请时间</th>
				              <th>笔试面试记录</th>
				            </tr>
				          </thead>
				          <tbody>
				          </tbody>
				        </table>
			        </div>
		        </s:form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/recruitment/positionResume/positionResume_list.js"></script>	
	<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
</html>