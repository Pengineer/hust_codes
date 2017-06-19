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
					<li class="active">帐号管理</li>
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
				<input type="hidden" id="accountId" value='<s:property value = "account.id"/>'/>
			      <tbody>
					<tr>
						<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
						<td class = "text-right" width = "100">用户名：</td>
						<td class = "text-left" width="150"><s:property value = "account.name"/></td>
						<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
						<td class = "text-right" width = "100">邮箱：</td>
						<td class = "text-left"  width="150"><s:property value = "account.email"/></td>
					</tr>
					<tr>
						<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
						<td class = "text-right" width = "100">注册时间：</td>
						<td class = "text-left" ><s:date name = "account.registerDate" format = "yyyy-MM--dd HH:mm:ss"/></td>
						<td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>最近一次登录时间：</td>
					    <td class='text-left' width='150'>
					        <s:date name='account.lastLoginDate' format = "yyyy-MM--dd HH:mm:ss"/>
					    </td>
					</tr>
					<tr>
					    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>最近一次登录ip：</td>
					    <td class='text-left' width='150'>
					        <s:property value='account.lastLoginIp' />
					    </td>
					    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>登录次数：</td>
					    <td class='text-left' width='150'>
					        <s:property value="account.loginCount" />
					    </td>
					</tr>
					<tr>
					    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>邮箱校验状态：</td>
					    <td class='text-left' width='150'>
					    	<s:if test="account.mailboxVerified == 1"><s:text name="已校验" /></s:if>
							<s:elseif test="account.mailboxVerified == 0"><s:text name="未校验" /></s:elseif>
					    </td>
					    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>是否为超级用户：</td>
					    <td class='text-left' width='150'>
					    	<s:if test="account.isSuperUser == 1"><s:text name="是" /></s:if>
							<s:elseif test="account.isSuperUser == 0"><s:text name="否" /></s:elseif>
					    </td>
					</tr>
				  </tbody>
	    		</table>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
	<script type="text/javascript" src="javascript/oa/account/view.js"></script>
</html>