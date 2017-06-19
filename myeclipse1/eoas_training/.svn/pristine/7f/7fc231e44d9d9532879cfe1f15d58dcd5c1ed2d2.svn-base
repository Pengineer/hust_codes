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
		<s:include value="/jsp/innerBase.jsp" />
	</head>
	<body class="g-pageRight">
		<div class="g-wrapper">
			<ol class="breadcrumb mybreadcrumb">当前位置：
				<li><a href="#"></a></li>
				<li class="active">信息发布管理</li>
				<li class="active">添加企业新闻</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="add"  namespace="/article" id = "article_add" theme="simple">
					<table class="form-group m-form-group">
						<tr>
							<td class="col-sm-1 control-label u-text-right required"><s:text name="新闻标题" />：</td>
							<td class="col-sm-12"><s:textfield name="article.title" cssClass="form-control validate[required, custom[chinese], maxSize[4]]" placeholder="标题" theme="simple" size="60" /></td>
						</tr>
<%-- 						<tr>
							<td class="col-sm-1 control-label u-text-right required"><s:text name="作者" />：</td>
							<td class="col-sm-12"><s:textfield name="person.realName" cssClass="form-control validate[required, custom[chinese], maxSize[4]]"  placeholder="主题" theme="simple" size="60" /></td>
						</tr>
						<tr>
							<td class="col-sm-1 control-label u-text-right required"><s:text name="来源" />：</td>
							<td class="col-sm-12"><s:textfield name="article.source" cssClass="form-control validate[required, custom[chinese], maxSize[4]]"  placeholder="主题" theme="simple" size="60" /></td>
						</tr>
						<tr>
							<td class="col-sm-1 control-label u-text-right required"><s:text name="正文" />：</td>
							<td class="col-sm-12"><FCK:editor instanceName="article.content" basePath="/tool/fckeditor" width="100%" height="450" toolbarSet="Default"></FCK:editor></td>
						</tr> --%>
						<tr>
							<td></td>
							<td align="center">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><input type="submit" class ="btn btn-sm btn-default" value = "确定" /></td>
										<td><input type="button" class="btn btn-sm btn-default" value="取消" onclick="history.back();" /></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine-zh_CN.js"></script>
</html>