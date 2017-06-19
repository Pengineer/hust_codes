<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored ="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>系统日志</title>
		<s:include value="/innerBase.jsp" />
		<link rel="stylesheet" type="text/css" href="css/autocomplete/jquery.autocomplete.css" />
		<link rel="stylesheet" type="text/css" href="css/log/log.css" />
	</head>
	<body>
		<div id="container">
			<div id="top">
				<table class="table_bar">
					<tr>
						<td>
							<span class="text_red">查看系统日志详情</span>
						</td>
						<td class="wd_right">
							<a id="view_del" href="javascript:void(0);"><img title="删除" src="image/del_entry.gif" /></a>&nbsp;
							<a id="view_prev" href="javascript:void(0);"><img title="上条" src="image/prev_record.gif" /></a>&nbsp;
							<a id="view_next" href="javascript:void(0);"><img title="下条" src="image/next_record.gif" /></a>&nbsp;
							<a id="view_back" href="javascript:void(0);"><img title="返回" src="image/return.gif" /></a>&nbsp;
						</td>
					</tr>
				</table>
			</div>
			
			<div class="main">
				<s:hidden id="entityId" name="entityId" value="%{entityId}" />
				<s:hidden id="entityIds" name="entityIds" value="%{entityId}" />
				<s:hidden id="update" name="update" />
				
				
				<textarea id="view_template" style="display:none;">
					<div class="main_content">
						<table class="table_edit" cellspacing="0" cellpadding="0">
							<tr><th class="thhead" colspan="4">基本信息</th></tr>
							<tr>
								<td class="wd14">操作账号</td>
								<td class="wd15">${log.username}</td>
								<td class="wd14">操作日期</td>
								<td class="wd15">${log.date}</td>
							</tr>
							<tr>
								<td class="wd14">操作地点</td>
								<td class="wd15">${log.ip}</td>
								<td class="wd14">事件代码</td>
								<td class="wd15">${log.eventCode}</td>
							</tr>
							<tr>
								<td class="wd14">操作描述</td>
								<td class="wd15">${log.eventDescription}</td>
								<td class="wd14"></td>
								<td class="wd15"></td>
							</tr>
							<tr><th class="thhead" colspan="4">请求</th></tr>
							<tr>
								<td class="wd14">编码：</td>
								<td class="wd15">${request.characterEncoding}</td>
								<td class="wd14">协议：</td>
								<td class="wd15">${request.protocol}</td>
							</tr>
							<tr>
								<td class="wd14">服务器：</td>
								<td class="wd15">${request.server}</td>
								<td class="wd14">请求路径：</td>
								<td class="wd15">${request.requestURI}</td>
							</tr>
							<tr>
								<td class="wd14">参数</td>
								<td class="wd15">${parameters}</td>
								<td class="wd14"></td>
								<td class="wd15"></td>
							</tr>
							<tr><th class="thhead" colspan="4">响应</th></tr>
							<tr>
								<td class="wd14">编码：</td>
								<td class="wd15">${response.characterEncoding}</td>
								<td class="wd14">大小：</td>
								<td class="wd15">${response.bufferSize}</td>
							</tr>
							<tr>
								<td class="wd14">内容类型：</td>
								<td class="wd15">${response.contentType}</td>
								<td class="wd14">语言：</td>
								<td class="wd15">${locale}</td>
							</tr>
							
						</table>
					</div>
				</textarea>
				
				
				<div id="view_container" style="display:none; clear:both;"></div>
			</div>
			
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/autocomplete/jquery.autocomplete.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.form.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/jquery/template.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/list.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		<script type="text/javascript" src="javascript/log/view.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
	</body>
</html>