<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String basePath2 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="i18n_ViewMail" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	</head>

	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="邮件管理" /></li>
				<li class="text_red"><s:text name="查看邮件" /></li>
			</ul>
		</div>
		<div class="div_table">
			<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="主题" />：</td>
					<td class="txtpadding"><s:property value="mail.subject" /></td>
				</tr>
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="收件人" />：</td>
					<td class="txtfckpadding">${mail.sendTo}</td>
				</tr>

				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="创建时间" />：</td>
					<td class="txtpadding">
						<s:if test="#session.locale == 'zh_CN'"><s:date name="%{mail.createTime}" format="yyyy-MM-dd HH:mm:ss" /></s:if>
						<s:else><s:date name="%{mail.createTime}" format="dd/MM/yyyy HH:mm:ss" /></s:else>
					</td>
				</tr>
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="发送次数" />：</td>
					<td class="txtpadding">${mail.sendTimes gt 0 ? mail.sendTimes : -mail.sendTimes}</td>
				</tr>
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="完成时间" />：</td>
					<td class="txtpadding">
						<s:if test="mail.sendTimes < 0"><img src="image/loading.gif" /><s:text name="i18n_Sending" /></s:if>
						<s:elseif test="mail.sendTo == null && mail.sended != null">
							<s:if test="#session.locale == 'zh_CN'"><s:date name="%{mail.finishTime}" format="yyyy-MM-dd HH:mm:ss" /></s:if>
							<s:elseif test="#session.locale == 'en_US'"><s:date name="%{mail.finishTime}" format="dd/MM/yyyy HH:mm:ss" /></s:elseif>
						</s:elseif>
						<s:else>
							<s:text name="i18n_NotSent" />
						</s:else>
					</td>
				</tr>
				<tr>
					<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:4px; vertical-align:top;"><s:text name="正文" />：</td>
					<td class="txtfckpadding">
						<s:if test="mail.isHtml == 1">${mail.body}</s:if>
						<s:else><s:property value="mail.body" /></s:else>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html> --%>



<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<s:include value="/jsp/innerBase.jsp" />
		<link href="tool/poplayer/css/ui-dialog.css" rel="stylesheet">
	</head>

	<body class="">
		<div class="g-wrapper">
			<div class="m-titleBar">
				<ol class="breadcrumb mybreadcrumb">当前位置：
					<li><a href="#"></a></li>
					<li class="active">邮件管理</li>
					<li class="active">查看</li>
				</ol>
			</div>
			 <div class="btn-group pull-right view-controler" role="group" aria-label="...">
	  			<button type="button" class="btn btn-sm btn-default" id = "view_add">添加</button>
	  			<button type="button" class="btn btn-sm btn-default" id = "view_mod">修改</button>
	  			<button type="button" class="btn btn-sm btn-default" id = "view_del">删除</button>
	  			<!-- <button type="button" class="btn btn-sm btn-default" id = "view_prev">上一条</button>
	  			<button type="button" class="btn btn-sm btn-default" id = "view_next">下一条</button> -->
	  			<button type="button" class="btn btn-sm btn-default" id = "view_back">返回</button>
			</div>
			<span class="clearfix"></span><!-- 重要!! 用于清除按键组浮动 -->
			<div class="m-form">
				<table class="table table-striped view-table">
				<input type="hidden" id="accountId" value='<s:property value = "account.id"/>'/>
			      <tbody>
			      
					<tr>
						<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
						<td class = "text-right" width = "100">主题：</td>
						<td class = "text-left" ><s:property value = "mail.subject"/></td>
					</tr>
					<tr>	
						<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
						<td class = "text-right" width = "100">收件人：</td>
						<td class = "text-left" ><s:property value = "mail.sendTo"/></td>
					</tr>
					<tr>
						<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
						<td class = "text-right" width = "100">创建时间：</td>
						<td class = "text-left" ><s:date name="%{mail.createTime}" format="dd/MM/yyyy HH:mm:ss" /></td>
					</tr>
					<tr>
						<td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>发送次数：</td>
					    <td class='text-left' >
					        ${mail.sendTimes gt 0 ? mail.sendTimes : -mail.sendTimes}
					    </td>
					</tr>
					<tr>
					    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>完成时间：</td>
					    <td class='text-left' >
					        <s:date name="%{mail.finishTime}" format="yyyy-MM-dd HH:mm:ss" />
					    </td>
					</tr>
					<tr>
					    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
					    <td class='text-right' width='100'>正文：</td>
					    <td class='text-left' >
					        <s:property value="mail.body" />
					    </td>
					</tr>

				  </tbody>
	    		</table>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
	<script type="text/javascript" src="javascript/oa/mail/view.js"></script>
</html>