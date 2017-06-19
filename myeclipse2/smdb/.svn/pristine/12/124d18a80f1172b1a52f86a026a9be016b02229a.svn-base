<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
			<link href="tool/tree/css/dhtmlxtree.css" rel="stylesheet" type="text/css" />
			<style type="text/css">
				.accountType {border:solid 1px #aaa;}
				.agencyType {border:solid 1px #aaa; padding:5px 10px;}
				.agencyType td {vertical-align:top; padding:5px 2px;}
				#main {margin-left:40px;}
				#sub {margin-left:80px;}
				.tree {border:solid 1px #aaa;}
			</style>
			
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统角色&nbsp;&gt;&nbsp;查看
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update" />
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update" />

				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
					<sec:authorize ifAllGranted="ROLE_SECURITY_ROLE_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_SECURITY_ROLE_MODIFY">
						<li id="view_cop"><input class="btn1" type="button" value="复制" /></li>
						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_SECURITY_ROLE_ADD">
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</sec:authorize>
					</ul>
				</div>
		
				<div class="main_content">
					<textarea id="view_template" style="display:none;">
						<div class="title_bar">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">角色名称：</td>
									<td class="title_bar_td" width="200">${role.name}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">创建者：</td>
									<td class="title_bar_td"><a id="${belongid}" class="link2" href="">${belongname}</a></td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">角色描述：</td>
									<td class="title_bar_td">${role.description}</td>
								</tr>
							</table>
							<table id="accountType" class="checkbox_css" width="100%" border="0" cellspacing="0" cellpadding="0" style="dispaly:none;">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">角色类型：</td>
									<td class="title_bar_td">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td height="20">指定账号类型的默认角色</td>
											</tr>
										</table>
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="accountType">
											<tr>
												<td width="200"><input type="checkbox" name="accountType" disabled="true" />部级主账号</td>
												<td><input type="checkbox" name="accountType" disabled="true" />部级子账号</td>
											</tr>
											<tr>
												<td><input type="checkbox" name="accountType" disabled="true" />省级主账号</td>
												<td><input type="checkbox" name="accountType" disabled="true" />省级子账号</td>
											</tr>
											<tr>
												<td><input type="checkbox" name="accountType" disabled="true" />部属高校主账号</td>
												<td><input type="checkbox" name="accountType" disabled="true" />部属高校子账号</td>
											</tr>
											<tr>
												<td><input type="checkbox" name="accountType" disabled="true" />地方高校主账号</td>
												<td><input type="checkbox" name="accountType" disabled="true" />地方高校子账号</td>
											</tr>
											<tr>
												<td><input type="checkbox" name="accountType" disabled="true" />院系主账号</td>
												<td><input type="checkbox" name="accountType" disabled="true" />院系子账号</td>
											</tr>
											<tr>
												<td><input type="checkbox" name="accountType" disabled="true" />基地主账号</td>
												<td><input type="checkbox" name="accountType" disabled="true" />基地子账号</td>
											</tr>
											<tr>
												<td><input type="checkbox" name="accountType" disabled="true" />外部专家账号</td>
												<td><input type="checkbox" name="accountType" disabled="true" />教师账号</td>
											</tr>
											<tr>
												<td colspan="2"><input type="checkbox" name="accountType" disabled="true" />学生账号</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
							<table id="agencyId" width="100%" border="0" cellspacing="0" cellpadding="0" style="dispaly:none;">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">角色类型：</td>
									<td class="title_bar_td">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td height="20">指定机构的默认角色</td>
											</tr>
										</table>
										<div class="agencyType">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
											{if ministry_length != 0}
												<tr>
													<td width="90">部级管理机构：</td>
													<td>
														{for item in ministry}
															<a id="${item.id}" href="javascript:void(0);" class="link3">${item.name}</a>{if item_index < ministry_length - 1};&nbsp;{/if}
														{/for}
													</td>
												</tr>
											{/if}
											{if province_length != 0}
												<tr>
													<td width="90">省级管理机构：</td>
													<td>
														{for item in province}
															<a id="${item.id}" href="javascript:void(0);" class="link3">${item.name}</a>{if item_index < province_length - 1};&nbsp;{/if}
														{/for}
													</td>
												</tr>
											{/if}
											{if university_length != 0}
												<tr>
													<td width="90">校级管理机构：</td>
													<td>
														{for item in university}
															<a id="${item.id}" href="javascript:void(0);" class="link3">${item.name}</a>{if item_index < university_length - 1};&nbsp;{/if}
														{/for}
													</td>
												</tr>
											{/if}
												<tr>
													<td width="90"></td>
													<td>
														<input id="main" type="checkbox" disabled="true" />主账号
														<input id="sub" type="checkbox" disabled="true" />子账号
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
							<table id="unDefault" width="100%" border="0" cellspacing="0" cellpadding="0" style="dispaly:none;">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">角色类型：</td>
									<td class="title_bar_td">非默认角色</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">角色权限：</td>
									<td class="title_bar_td"><div id="treeparent" class="tree"></div></td>
								</tr>
							</table>
						</div>
					</textarea>
					
					<div id="view_container" style="display:none; clear:both;"></div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/security/role/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	
</html>