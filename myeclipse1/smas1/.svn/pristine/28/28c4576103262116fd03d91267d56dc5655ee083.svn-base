<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>邮件查看</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/mail/mail.css" />
	</head>
	<body>
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							系统管理&nbsp;&gt;&gt;&nbsp;邮件管理&nbsp;&gt;&gt;&nbsp;<span class="text_red">邮件查看</span>
						</td>
						<td class="wd_right">
							<a id="view_del" href="javascript:void(0);"><img title="删除" src="image/del_entry.gif" /></a>&nbsp;
							<a id="view_again" href="javascript:void(0);"><img title="重发" src="image/remark.gif" /></a>&nbsp;
							<a id="view_prev" href="javascript:void(0);"><img title="上条" src="image/prev_record.gif" /></a>&nbsp;
							<a id="view_next" href="javascript:void(0);"><img title="下条" src="image/next_record.gif" /></a>&nbsp;
							<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>&nbsp;
						</td>
					</tr>
				</table>
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" />
				<s:hidden id="update" name="update" />
				
				<textarea id="view_template" style="display:none;">
				<div class="main_content">
				<table class="table_edit" cellspacing="0" cellpadding="0">
					<tr><th class="thhead" colspan="2">基本信息</th></tr>
					<tr><td class="wd14">主题</td>
						<td class="wd15">${mail.subject}</td>
					</tr>
					<tr><td class="wd14">收件人</td>
						<td class="wd15">${mail.to}</td>
					</tr>
					<tr><td class="wd14">抄送人</td>
						<td class="wd15">${mail.cc}</td>
					</tr>
					<tr><td class="wd14">密送人</td>
						<td class="wd15">${mail.bcc}</td>
					</tr>
					<tr><td class="wd14">回复邮箱</td>
						<td class="wd15">${mail.replyTo}</td>
					</tr>
					<tr><td class="wd14">创建时间</td>
						<td class="wd15">${mail.createDate}</td>
					</tr>
					<tr><td class="wd14">发送完毕时间</td>
						<td class="wd15">${mail.finishDate}</td>
					</tr>
					<tr><td class="wd14">已尝试发送次数</td>
						<td class="wd15">${mail.sendTimes}</td>
					</tr>
					<tr><td class="wd14">发送状态</td>
						<td class="wd15">
							{if mail.status == 0}未发送[<a id="view_send" href="javascript:void(0);">{if mail.sendTimes > 0}重新{else}{/if}发送</a>]
							{elseif mail.status == 1}准备发送[<a id="view_cancel" href="javascript:void(0);">暂停发送</a>][<a id="view_refresh" href="javascript:void(0);">刷新</a>]
							{elseif mail.status == 2}发送中[<a id="view_refresh" href="javascript:void(0);">刷新</a>]
							{else}已发送{/if}</td>
					</tr>
					{if mail.attachmentName != null}
					<tr><td class="wd14">附件</td>
						<td class="wd15">
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
					{/if}
					<tr><td class="wd14">正文</td>
						<td class="wd15">${mail.body}</td>
					</tr>
					{if mail.log != null}
						<tr>
							<td class="wd14">重发记录</td>
							<td class="wd15"><i>${mail.log}<br></i></td>
						</tr>
					{/if}
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
		<script type="text/javascript" src="javascript/mail/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>