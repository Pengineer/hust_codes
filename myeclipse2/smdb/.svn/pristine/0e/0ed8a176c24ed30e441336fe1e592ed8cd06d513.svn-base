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
				当前位置：社科信息发布&nbsp;&gt;&nbsp;留言簿&nbsp;&gt;&nbsp;查看
			</div>

			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="update" name="update" />
				<div class="choose_bar">
					<ul>
						<li id="view_back"><input class="btn1" type="button" value="返回" /></li>
						<li id="view_next"><input class="btn1" type="button" value="下条" /></li>
						<li id="view_prev"><input class="btn1" type="button" value="上条" /></li>
						<li id="view_add"><input class="btn1" type="button" value="添加" /></li>
					</ul>
				</div>

				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						{for item in messages}
							<table class="txtlineheight" width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="tdbg1">${item.title}</td>
									<td align="right" class="tdbg1 txtpadding">
										<ul>
											<li class="message_bar"><a class="message_reply" href="javascript:void(0)" alt="${item.id}"><img src="image/g01.gif" title="回复" /></a></li>
											<li class="message_bar"><a class="message_quoto" href="javascript:void(0)" alt="${item.id}"><img src="image/g02.gif" title="引用" /></a></li>
										</ul>
									</td>
								</tr>
							</table>
							<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse; background:#f7f7f7;" bordercolor="#253d56">
								<tr>
									<td width="200" rowspan="2" valign="top" bgcolor="#e3cfeb">
										<table width="100%" border="0" cellspacing="2" cellpadding="2">
											<tr>
												<td width="36%" align="right">
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
													{else}item_index楼:
													{/if}
												</td>
												<td width="64%" style="font-weight:bold;">
													${item.authorName}<br />[<span style="color:#FF7400;">{if innerOrOuter[item_index] == 1}认证用户{elseif}匿名用户{/if}</span>]
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div name="open" style="display:none;color:#FF7400;" align="center">
														<br />
														<span>该贴当前对外可见!</span>
													</div>
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
											&nbsp;&nbsp;&nbsp;&nbsp;
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
			<script type="text/javascript">
				seajs.use('javascript/system/message/outer/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>
	
</html>
