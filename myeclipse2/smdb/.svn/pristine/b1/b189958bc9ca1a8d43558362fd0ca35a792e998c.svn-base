<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>查看
		</title>
		<s:include value="/innerBase.jsp" />
	</head>

		<body>
			<div class="link_bar">
				当前位置：社科信息发布 &nbsp;&gt;&nbsp;<s:text name="站内信" />&nbsp;&gt;&nbsp;查看
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="update" name="update" />
				
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
<!-- 						<li id="view_del"><input class="btn1" type="button" value="删除" /></li> -->
<%--						<li id="view_mod"><input class="btn1" type="button" value="修改" /></li>--%>
						<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_ADD">
							<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
						</sec:authorize>
					</ul>
				</div>
		
				<textarea id="view_template" style="display:none;">
				<div class="title_bar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="50" align="right"><s:text name="主题" />：</td>
							<td class="title_bar_td" width="200">${inBox.theme}
							</td>
							<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="70" align="right"><s:text name="发信者" />：</td>
							<td class="title_bar_td" width="200">${inBox.sendName}
							</td>
						</tr>
						<tr>
						{if inBox.sendType == 1}
							<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="70" align="right"><s:text name="类型" />：</td>
							<td class="title_bar_td" width="200"><s:text name="单播" /></td>
							<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="70" align="right"><s:text name="收信者" />：</td>
							<td class="title_bar_td" width="200">${inBox.recName}
							</td>
						{elseif inBox.sendType == 2}
							<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="70" align="right"><s:text name="类型" />：</td>
							<td class="title_bar_td" width="200"><s:text name="多播" /></td>
							<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="70" align="right"><s:text name="收信者" />：</td>
							<td class="title_bar_td" width="200">
								{if inBox.accountType == 'MINISTRY'}部级账号
								{elseif inBox.accountType == 'PROVINCE'}省级账号
								{elseif inBox.accountType == 'MINISTRY_UNIVERSITY'}部属高校账号
								{elseif inBox.accountType == 'LOCAL_UNIVERSITY'}地方高校账号
								{elseif inBox.accountType == 'INSTITUTE'}研究基地账号
								{elseif inBox.accountType == 'DEPARTMENT'}高校院系账号
								{elseif inBox.accountType == 'EXPERT'}外部专家账号
								{elseif inBox.accountType == 'TEACHER'}教师账号
								{elseif inBox.accountType == 'STUDENT'}学生账号
								{elseif inBox.accountType == 'ADMINISTRATOR'}系统管理员账号
								{else}未知账号类型  UNDEFINED
								{/if}
							</td>
						{elseif inBox.sendType == 3}
							<td class="title_bar_td" width="10" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
							<td class="title_bar_td" width="70" align="right"><s:text name="类型" />：</td>
							<td class="title_bar_td" width="200"><s:text name="广播" /></td>
						{/if}
						</tr>
						</table>
					</div>
						<div style="padding:10px 10px 40px 30px; line-height:26px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td">${inBox.message}</td>
								</tr>
							</table>
						</div>
				</textarea>
					
				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/inBox/view.js', function(view) {
					$(function() {
						view.init();
					})
				});
			</script>
		</body>
	
</html>