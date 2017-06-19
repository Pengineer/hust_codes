<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>查看</title>
			<s:include value="/innerBase.jsp" />
			<style type="text/css">
				.mailer_list {height:80px; border: 1px solid #E1E1E1; overflow-y:scroll; overflow-x:hidden;}
			</style>
			
		</head>

		<body>
			<div class="link_bar">
				当前位置：系统管理&nbsp;&gt;&nbsp;系统邮件&nbsp;&gt;&nbsp;查看
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
					<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_MODIFY">
						<li id="view_send" style="display:none;"><input class="btn1" type="button" value="发送" /></li>
						<li id="view_pause" style="display:none;"><input class="btn1" type="button" value="暂停" /></li>
						<li id="view_cancel" style="display:none;"><input class="btn1" type="button" value="取消" /></li>
						<li id="view_again" style="display:none;"><input class="btn1" type="button" value="重发" /></li>
						<s:hidden name = "mail.status"></s:hidden>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_ADD">
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
									<td class="title_bar_td" width="70" align="right">邮件主题：</td>
									<td class="title_bar_td">${mail.subject}</td>
								</tr>
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" align="right">收件人：</td>
									<td class="title_bar_td">
										<div class="mailer_list">
											<table>${mail.sendTo }</table>
										</div>
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="70" align="right">创建账号：</td>
									<td class="title_bar_td" width="150">
										<a id="${accountid}" class="link2" href="">${accountname}</a>
										<s:hidden id="entityId" />
										<s:hidden id="accounttype" />
										<s:hidden id="isPrincipal" />
									</td>
									<td class="title_bar_td" width="5" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="90" align="right">创建者：</td>
									<td class="title_bar_td" width="150">${mail.accountBelong}</td>
									<td class="title_bar_td"></td>
								</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="70" align="right">创建时间：</td>
									<td class="title_bar_td" width="150">${mail.createDate}</td>
									<td class="title_bar_td" width="5" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="90" align="right">首次完成时间：</td>
									<td class="title_bar_td">
										{if mail.finishDate == null || mail.finishDate == ""}
										处理中
										{else}
										${mail.finishDate}
										{/if}
									</td>
									<td class="title_bar_td" width="5" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="90" align="right">尝试总次数：</td>
									<td class="title_bar_td">${mail.sendTimes}</td>
								</tr>
							</table>
							
							{if mail.attachmentName != null}
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
										<td class="title_bar_td" width="70" align="right">附件：</td>
										<td class="title_bar_td">
											<ul>
												{for item in mail.attachmentName}
												<li id="${item_index}"><a href="javascript:void(0);" class="attach" id="${mail.id}">${item}</a>
													&nbsp;<span style="color: #A0A0A0;">(
													{if attachmentSizeList[item_index] != null}
														${attachmentSizeList[item_index]}
													{else}附件不存在
													{/if}
													)</span>
												</li>
												{/for}
											</ul>
										</td>
									</tr>
								</table>
							{/if}
						</div>
						<div style="padding:10px 10px 40px 30px; line-height:26px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td">${mail.body}</td>
								</tr>
							</table>
						</div>
						{if mail.log != null}
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"></td>
									<td class="title_bar_td g_txt2 log_txt"><i>${mail.log}<br></i></td>
								</tr>
							</table>
						{/if}
					</textarea>
					
					<div id="view_container" style="display:none; clear:both;"></div>
				</div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/mail/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>

</html>