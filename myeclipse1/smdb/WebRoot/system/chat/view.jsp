<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main">
	<textarea id="view_template" style="display:none;">
		<div class="main_content">
			{for item in chats}
				<table class="txtlineheight" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1">${item.title}</td>
						<td align="right" class="tdbg1 buttonpadding">
							<ul>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_ADD">
								<li class="message_bar"><a class="message_reply" href="javascript:void(0)" alt="${item.id}"><img src="image/g01.gif" title="回复" /></a></li>
								<li class="message_bar"><a class="message_quoto" href="javascript:void(0)" alt="${item.id}"><img src="image/g02.gif" title="引用" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_SYSTEM_MAIL_ADD">
								<li class="message_bar"><a class="message_mail" href="javascript:void(0)" alt="${item.email}"><img src="image/g05.gif" title="Email" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_MODIFY">
								<li class="message_bar"><a class="message_modify" href="javascript:void(0)" alt="${item.id}"><img src="image/g03.gif" title="修改" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_DELETE">
								<li class="message_bar"><a class="message_delete" href="javascript:void(0)" alt="${item.id}"><img src="image/g04.gif" title="删除" /></a></li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_MODIFY">
								<li class="message_bar"><a class="toggleOpen" href="javascript:void(0)" alt="${item.id}" isopen="${item.isOpen}">
									{if item.isOpen == 0}<img src="image/g07.gif" title="设为公开" />
									{else}<img src="image/g06.gif" title="取消公开" /></a>
									{/if}
								</li>
								</sec:authorize>
								<sec:authorize ifAllGranted="ROLE_INFORMATION_MESSAGE_AUDIT">
								<li class="message_bar"><a class="message_audit" href="javascript:void(0)" alt="${item.id}"><img src="image/g08.gif" title="审核" /></a></li>
								</sec:authorize>
							</ul>
						</td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse; bordercolor:#253d56; background:#f7f7f7;">
					<tr>
						<td width="200" rowspan="2" valign="top" bgcolor="#e3cfeb">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td width="36%" align="right" valign="top" style="padding-top:2px">
										{if item_index == 0}楼主:
										{elseif item_index == 1}沙发:
										{elseif item_index == 2}板凳:
										{elseif item_index == 3}地毯:
										{elseif item_index == 4}地板:
										{elseif item_index == 5}地下室:
										{elseif item_index == 6}地基:
										{elseif item_index == 7}地壳:
										{elseif item_index == 8}地幔:
										{elseif item_index == 9}地核:
										{else}${item_index}楼:
										{/if}
									</td>
									<td width="64%" style="font-weight:bold;">
										${item.authorName}<br />[<span style="color:#FF7400;">{if innerOrOuter[item_index] == 1}认证用户{elseif}匿名用户{/if}</span>]
									</td>
								</tr>
								<tr>
									<td colspan="2">
										{if item.isOpen == 0}
											<div name="open" style="display:none;color:#FF7400;" align="center">
												<br />
												<span>该贴当前对外可见!</span>
											</div>
										{else}
											<div name="open" style="color:#FF7400;" align="center">
												<br />
												<span>该贴当前对外可见!</span>
											</div>
										{/if}
									</td>
								</tr>
							</table>
						</td>
						<td height="60px" class="txtfckpadding">
							${item.content}
							{if item.log != null}
								<div class="g_txt2 log_txt"><i>${item.log}</i></div>
							{/if}
						</td>
					</tr>
					<tr>
						<td class="txtpadding">
							<span style="float:left;">发表于：
								${item.createDate}
								&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;IP:&nbsp;${item.ip}&nbsp;]
								&nbsp;&nbsp;&nbsp;&nbsp;{if item.auditStatus == 0}未审核{elseif item.auditStatus == 1}审核未通过{else}审核通过{/if}
							</span>
							<span style="float:right;">#${item_index}</span>
						</td>
					</tr>
				</table>
				<br />
			{/for}
		</div>
	</textarea>

	<div id="view_container" style="display:none; clear:both;"></div>
</div>
