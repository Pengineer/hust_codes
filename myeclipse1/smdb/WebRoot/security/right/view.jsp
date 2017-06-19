<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统权限&nbsp;&gt;&nbsp;查看
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update" />
				
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					<sec:authorize ifAllGranted="ROLE_SECURITY_RIGHT_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_SECURITY_RIGHT_MODIFY">
						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_SECURITY_RIGHT_ADD">
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</sec:authorize>
					</ul>
				</div>
		
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						<div class="title_bar">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">权限名称：</td>
									<td class="title_bar_td" width="200">${right.name}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="80" align="right">权限节点值：</td>
									<td class="title_bar_td">${right.nodevalue}</td>
								</tr>
							</table>
							
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">权限代码：</td>
									<td class="title_bar_td">${right.code}</td>
								</tr>
							</table>
							
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">权限描述：</td>
									<td class="title_bar_td">${right.description}</td>
								</tr>
							</table>
						</div>
					</div>
				</textarea>
					
				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/security/right/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	
</html>