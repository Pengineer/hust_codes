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
				当前位置：系统管理&nbsp;&gt;&nbsp;系统日志&nbsp;&gt;&nbsp;查看
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
					<sec:authorize ifAllGranted="ROLE_SYSTEM_LOG_DELETE">
						<li id="view_del"><input class="btn1" type="button" value="删除" /></li>
					</sec:authorize>
					</ul>
				</div>
		
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						<div class="title_bar">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">操作者：</td>
									<td class="title_bar_td" width="200">
										{if accountId == null}
											${log.accountName}
										{else}
											<a id="${accountId}" href="" class="link2">${log.accountName}</a>
										{/if}
									</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="80" align="right">操作时间：</td>
									<td class="title_bar_td">${log.date}</td>
								</tr>
							</table>
							
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">操作地点：</td>
									<td class="title_bar_td" width="200">${log.ip}</td>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="80" align="right">事件代码：</td>
									<td class="title_bar_td">${log.eventCode}</td>
								</tr>
							</table>
							
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="title_bar_td" width="30" align="right"><img src="image/ico08.gif" width="5" height="9" /></td>
									<td class="title_bar_td" width="64" align="right">事件描述：</td>
									<td class="title_bar_td">${log.eventDscription}</td>
								</tr>
							</table>
						</div>
					</div>
					{if request != null || response != null}
					<div class="main_content">
						<div id="tabs" class="p_box_bar">
							<ul>
								<li><a href="javascript:void(0)">详细信息</a></li>
							</ul>
						</div>

						<div class="p_box">
							{if data != null}
								<div class="p_box_t">
									<div class="p_box_t_t">操作对象</div>
									<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
								</div>
								<div class="p_box_body">
									<div class="p_box_body_t">
										<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse; table-layout:fixed;">
											<col style="width:100" />  
											<col style="width:200" />  
											<col style="width:100" />
											<col style="width:310" />  
											<tr class="table_tr7">
												<td class="key" width="100">目标对象数据：</td>
												<td class="value" colspan="3" style="word-wrap: break-word; word-break: break-all;">${data}</td>
											</tr>
											<tr class="table_tr7">											
												{if changedData != null}
												<td class="key" width="100">更改后对象数据：</td>
												<td class="value" colspan="3" style="word-wrap: break-word; word-break: break-all;">${changedData}</td>
												{/if}
											</tr>
										</table>
									</div>
								</div>
							{/if}
							{if request != null}
								<div class="p_box_t">
									<div class="p_box_t_t">请求</div>
									<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
								</div>
								<div class="p_box_body">
									<div class="p_box_body_t">
										<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse; table-layout:fixed;">
											<col style="width:100" />  
											<col style="width:200" />  
											<col style="width:100" />
											<col style="width:310" />  
											<tr class="table_tr7">
												<td class="key" width="100">编码：</td>
												<td class="value" width="200">${request.characterEncoding}</td>
												<td class="key" width="100">协议：</td>
												<td class="value">${request.protocol}</td>
											</tr>
											<tr class="table_tr7">
												<td class="key" width="100">服务器：</td>
												<td class="value" width="200">${request.server}</td>
												<td class="key" width="100">请求路径：</td>
												<td class="value">${request.requestURI}</td>
											</tr>
											<tr class="table_tr7">
												<td class="key" width="100">请求方法：</td>
												<td class="value" width="200">${request.requestMethod}</td>
												<td class="key" width="100">浏览器可接受的MIME类型：</td>
												<td class="value">${request.accept}</td>
											</tr>
											<tr class="table_tr7">
												<td class="key" width="100">请求源页面：</td>
												<td class="value" colspan="3" style="word-wrap: break-word; word-break: break-all;">${request.referer}</td>
											</tr>
											<tr class="table_tr7">
												<td class="key" width="100">浏览器：</td>
												<td class="value" colspan="3" style="word-wrap: break-word; word-break: break-all;">${request.userAgent}</td>
											</tr>
											
											{if parameters != null}
											<tr class="table_tr7">
												<td class="key" width="100">参数：</td>
												<td class="value" colspan="3" style="word-wrap: break-word; word-break: break-all;">${parameters}</td>
											</tr>
											{/if}
										</table>
									</div>
								</div>
							{/if}
							
							{if response != null}
								<div class="p_box_t">
									<div class="p_box_t_t">响应</div>
									<div class="p_box_t_b"><img class="image" src="image/open.gif" style="display:none;"/><img class="image" src="image/close.gif" /></div>
								</div>
								<div class="p_box_body">
									<div class="p_box_body_t">
										<table width="100%" border="1" cellspacing="0" cellpadding="0" bordercolor="#CCCCCC" style="border-collapse:collapse;">
											<tr class="table_tr7">
												<td class="key" width="100">编码：</td>
												<td class="value" width="200">${response.characterEncoding}</td>
												<td class="key" width="100">大小：</td>
												<td class="value">${response.bufferSize}</td>
											</tr>
											<tr class="table_tr7">
												<td class="key" width="100">内容类型：</td>
												<td class="value" width="200">${response.contentType}</td>
												{if operationTime != null}
												<td class="key" width="100">响应时间：</td>
												<td class="value" colspan="3">${operationTime}毫秒</td>
												{/if}
											</tr>
											{if response.responseStatus != null}
											<tr class="table_tr7">
												<td class="key" width="100">响应状态：</td>
												<td class="value" width="200">${response.responseStatus}</td>
											</tr>
											{/if}
											{if locale != null}
											<tr class="table_tr7">
												<td class="key" width="100">语言：</td>
												<td class="value" colspan="3">${locale}</td>
											</tr>
											{/if}
										</table>
									</div>
								</div>
							{/if}
						</div>
					</div>
					{/if}
				</textarea>
					
				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
			<script type="text/javascript">
				seajs.use('javascript/system/log/view.js', function(view) {
					$(function(){
						view.init();
					})
				});
			</script>
		</body>

</html>