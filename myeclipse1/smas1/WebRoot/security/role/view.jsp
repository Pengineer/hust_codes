<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>查看角色</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/roleright/roleright.css" />
		</head>
		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								系统管理&nbsp;&gt;&gt;
								角色权限&nbsp;&gt;&gt;
								角色列表&nbsp;&gt;&gt;
								<span class="text_red">查看角色</span>
							</td>
							<td class="wd_right">
								<a id="view_mod" href="javascript:void(0);"><img title="修改" src="image/modify_entry.gif" /></a>&nbsp;
								<a id="view_del" href="javascript:void(0);"><img title="删除" src="image/del_entry.gif" /></a>&nbsp;
								<a id="view_prev" href="javascript:void(0);"><img title="上条" src="image/prev_record.gif" /></a>&nbsp;
								<a id="view_next" href="javascript:void(0);"><img title="下条" src="image/next_record.gif" /></a>&nbsp;
								<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				
				<div id="mid"></div>
				
				<div class="main">
					<s:hidden id="entityId" name="entityId" />
					<s:hidden id="update" name="update" />
					
					<textarea id="view_template" style="display:none;">
					<div class="main_content">
					<table class="table_edit" cellspacing="0" cellpadding="0">
						<tr><th class="thhead" colspan="2">基本信息</th></tr>
						<tr><td class="wd12">角色名称</td>
							<td class="wd13">${role.name}</td>
						</tr>
						<tr><td class="wd12">角色描述</td>
							<td class="wd13">${role.description}</td>
						</tr>
						<tr><th class="thhead" colspan="2">查看权限</th></tr>
					</table>
					<table class="table_view">
						<tr><th>权限名称</th>
							<th>权限描述</th>
						</tr>
						{for item in pageList}
							<tr>
								<td>${item.name}</td>
								<td>${item.description}</td>
							</tr>
						{/for}
					</table>
					<div style="height:5px;"></div>
					</div>
					</textarea>
					
					<div id="view_container" style="display:none; clear:both;"></div>
					
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/security/role/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>