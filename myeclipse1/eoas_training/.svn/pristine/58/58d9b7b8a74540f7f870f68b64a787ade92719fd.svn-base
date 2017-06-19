<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="查看通知" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>

	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="通知" /></li>
				<li class="text_red"><s:text name="查看通知" /></li>
			</ul>
			
			<div class="sub_bar">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
					
					</tr>
				</table>
		
		
			</div>
		</div>
		
		<div class="notice_body">
			<s:form theme='simple'>
				<div class="notice_bar">
					<span><s:text name="通知标题" />:<s:property value="notice.title" /></span>
					<span><s:text name="通知类型" />：<s:property value="notice.type" /></span>
					<span><s:text name="作者" />：<s:property value="account.email" /></span>
					<span><s:text name="发布时间" />：<s:date name="notice.createDate" format="yyyy-MM-dd HH:mm:ss" /></span>
					<span><s:text name="阅读次数" />：<s:property value="notice.viewCount" /></span>
					<span><s:text name="信息来源" />：<s:property value="notice.source" /></span>
					<s:if test="notice.isopen == 0">
						<span class="text_red"><s:text name="i18n_ToIntranet" /></span>
					</s:if>				
				</div>
				<div class="notice_txt">${notice.content}</div>
			</s:form>
		
		</div>
		

	</body>
</html>