<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="角色信息" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="tool/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<style type="text/css">
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
		</style>
	</head>

	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="系统选项表" /></li>
				<li class="text_red"><s:text name="查看系统选项表" /></li>
			</ul>
		</div>
		<div class="div_table">

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="tdbg1"><s:text name="系统选项表" /></td>
				</tr>
			</table>
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="系统选项表树结构" />：</td>
				</tr>
			</table>
			<ul id="systemOptionTree" class="ztree"></ul>
			<button id="data_submit" class="btn btn-primary btn-sm" type="button">提交</button>
			<button id="data_reset" class="btn btn-success btn-sm" type="button">重置</button>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="tool/zTree/js/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="javascript/oa/systemOption/systemOption.js"></script>
	</body>
</html> --%>



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
				<li class="active">系统选项表管理</li>
				<li class="active">查看</li>
			</ol>
			<div class="m-form">
				
					<div class="m-main-content">
						<div id="basic">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label" align="left">系统选项表</label>
								<div class="col-sm-3"></div>
							</div>
						</div>
						<div>
							<div class="form-group m-form-group">
								<ul id="systemOptionTree" class="ztree"></ul>
								<div class="col-sm-3"></div>
							</div>
						</div>
					</div>
					<button id="data_submit" class="btn btn-primary btn-sm" type="button">提交</button>
					<button id="data_reset" class="btn btn-success btn-sm" type="button">重置</button>
				
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/zTree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="javascript/oa/systemOption/systemOption.js"></script>
</html>