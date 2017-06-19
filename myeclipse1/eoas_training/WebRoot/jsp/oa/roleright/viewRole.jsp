<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<s:include value="/jsp/innerBase.jsp" />
		<link href="tool/poplayer/css/ui-dialog.css" rel="stylesheet">
	</head>
	<body class="">
		<div class="g-wrapper">
			<div class="m-titleBar">
				<ol class="breadcrumb mybreadcrumb">当前位置：
					<li><a href="#"></a></li>
					<li class="active">角色管理</li>
					<li class="active">查看</li>
				</ol>
			</div>
			<div class="btn-group pull-right view-controler" role="group" aria-label="...">
					<button type="button" class="btn btn-sm btn-default" id = "view_add">添加</button>
					<button type="button" class="btn btn-sm btn-default" id = "view_mod">修改</button>
					<button type="button" class="btn btn-sm btn-default" id = "view_del">删除</button>
					<!-- <button type="button" class="btn btn-sm btn-default" id = "view_prev">上一条</button>
					<button type="button" class="btn btn-sm btn-default" id = "view_next">下一条</button> -->
					<button type="button" class="btn btn-sm btn-default" id = "view_back">返回</button>
			</div>
			<span class="clearfix"></span><!-- 重要!! 用于清除按键组浮动 -->
			<div class="m-form">
				<table class="table table-striped view-table">
				<input type="hidden" id="roleId" value='<s:property value = "role.id"/>'/>
					<tbody>
						<tr>
							<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
							<td class = "text-right" width = "100">角色名称：</td>
							<td class = "text-left" width="150"><s:property value = "role.name"/></td>
						</tr>
						<tr>
							<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
							<td class = "text-right" width = "100">角色描述：</td>
							<td class = "text-left"  width="150"><s:property value = "role.description"/></td>
						</tr>
						<tr>
							<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
							<td class = "text-right" width = "100">权限信息：</td>
							<td class = "text-left" width="150">
								<div id="treeparent" style="border:solid 1px #aaa;"></div>
								<s:hidden name="roleId" />
							</td>

						</tr>
					</tbody>
	    		</table>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="tool/dhtmlxTree/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="tool/dhtmlxTree/dhtmlxtree.js"></script>
	<script type="text/javascript" src="tool/dhtmlxTree/ext/dhtmlxtree_json.js"></script>
	<script type="text/javascript" src="tool/dhtmlxTree/ext/dhtmlxtree_xw.js"></script>
	<script type="text/javascript" src="javascript/oa/role/view.js"></script>
	<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
</html>