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
		<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
		<style type="text/css">
			#form_roleright label.error {
				padding-left: 6px;
				padding-bottom: 2px;
				color: rgb(255, 116, 0);
			}
		</style>
	</head>

	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="解决方案管理" /></li>
				<li class="text_red"><s:text name="解决方案添加" /></li>
			</ul>
		</div>
		<div class="div_table">
			<s:form action="add"  namespace="/solution" theme="simple">
				<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="行业类别" />：</td>
						<td><s:textfield cssClass="inputcss" name="solution.category" theme="simple" size="60" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="方案名称" />：</td>
						<td><s:textfield cssClass="inputcss" name="solution.title" theme="simple" size="60" /></td>
					</tr>
					
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;">
							<s:text name="正文" />：
						</td>
 						<td>
							<FCK:editor instanceName="solution.content" value="${solution.content}" basePath="/tool/fckeditor" width="100%" height="450" toolbarSet="Default"></FCK:editor>
						</td> 
					</tr>
					
					<tr>
						<td align="right" bgcolor="#b3c6d9">&nbsp;</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="90"><input type="submit" class ="btn1" value = "<s:text name="确定" />" /></td>
									<td><input type="button" class="btn1" value="<s:text name="取消" />" onclick="history.back();" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
</html>