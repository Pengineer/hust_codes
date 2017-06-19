<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>查看个人信息</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/user/user.css" />
		</head>
		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								<s:if test="selflabel == 0">
									系统管理&nbsp;&gt;&gt;
									用户管理&nbsp;&gt;&gt;
									<s:if test="userstatus == 1">已启用用户&nbsp;&gt;&gt;</s:if>
									<s:elseif test="userstatus == -1">未启用用户&nbsp;&gt;&gt;</s:elseif>
									<s:elseif test="userstatus == 0">未审批用户&nbsp;&gt;&gt;</s:elseif>
									<span class="text_red">查看个人信息</span>
								</s:if>
								<s:else>
									个人空间&nbsp;&gt;&gt;
									<span class="text_red">个人信息</span>
								</s:else>
							</td>
							<td class="wd_right">
								<s:if test="selflabel == 0">
									<a id="view_mod" href="javascript:void(0);"><img title="修改" src="image/modify_entry.gif" /></a>&nbsp;
									<a id="view_del" href="javascript:void(0);"><img title="删除" src="image/del_entry.gif" /></a>&nbsp;
									<a id="view_prev" href="javascript:void(0);"><img title="上条" src="image/prev_record.gif" /></a>&nbsp;
									<a id="view_next" href="javascript:void(0);"><img title="下条" src="image/next_record.gif" /></a>&nbsp;
									<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>&nbsp;
								</s:if>
								<s:else>
									<a href="selfspace/loadInfo.action"><img title="修改" src="image/modify_entry.gif" /></a>&nbsp;
								</s:else> 
							</td>
						</tr>
					</table>
				</div>
				
				<div class="main">
					<s:hidden id="entityId" name="entityId" />
					<s:hidden id="update" name="update" />
					<s:hidden id="userstatus" name="userstatus" />
					
					<textarea id="view_template" style="display:none;">
					<div class="main_content">
					<table class="table_edit4" cellspacing="0" cellpadding="0">
						<tr><th class="thhead" colspan="4">基本信息</th></tr>
						<tr><td class="wd14">账号</td>
							<td class="wd15">
								<span id="username">${user.username}</span>
								<span id="retrieveCode">[<a style="color:#0085FC;" title ="点击重置密码" href="">重置密码</a>]</span>
								<span id="modifyPassword">[<a style="color:#0085FC;" title ="点击修改密码" href="">修改密码</a>]</span>
							</td>
							<td rowspan="7" class="showpic">
								<s:if test="user.photofile != null">
									<img src="#applicationScope.UserPictureUploadPath/${user.photofile}" />
								</s:if>
								<s:else>
									<img src="image/photo.gif" />
								</s:else>
							</td>
						</tr>
						<tr><td class="wd14">中文名</td>
							<td class="wd15">${user.chinesename}</td>
						</tr>
						<tr><td class="wd14">性别</td>
							<td class="wd15">${gender}</td>
						</tr>
						<tr><td class="wd14">出生日期</td>
							<td class="wd15">${user.birthday}</td>
						</tr>
						<tr><td class="wd14">民族</td>
							<td class="wd15">${ethnic}</td>
						</tr>
						<tr><td class="wd14 bgcolor1">邮箱</td>
							<td class="wd15">${user.email}</td>
						</tr>
						<tr><td class="wd14 bgcolor1">手机</td>
							<td class="wd15">${user.mobilephone}</td>
						</tr>
					</table>
					<div></div>
					</div>
					</textarea>
					
					<div id="view_container" style="display:none; clear:both;"></div>
					
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/popLayer.self.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>