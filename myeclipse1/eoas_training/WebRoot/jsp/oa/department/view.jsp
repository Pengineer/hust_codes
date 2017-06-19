<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/jsp/innerBase.jsp" />
		<link rel="stylesheet" href="tool/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
		</style>
	</head>

	<body>
		<div class="g-wrapper">
			<ol class="breadcrumb mybreadcrumb">当前位置：
				<li><a href="#"></a></li>
				<li class="active">部门管理</li>
				<li class="active">查看部门</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="add"  namespace="/department" id = "department_add" theme="simple">
					<div class="m-main-content">
						<div id="basic">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label" align="left">公司结构图</label>
								<div class="col-sm-3"></div>
							</div>
						</div>
						<div>
							<div class="form-group m-form-group">
								<ul id="depTree" class="ztree"></ul>
								<div class="col-sm-3"></div>
							</div>
						</div>
					</div>
					<button id="data_submit" class="btn btn-primary btn-sm" type="button">提交</button>
					<button id="data_reset" class="btn btn-success btn-sm" type="button">重置</button>
				</s:form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/zTree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="javascript/oa/department/department.js"></script>
</html>