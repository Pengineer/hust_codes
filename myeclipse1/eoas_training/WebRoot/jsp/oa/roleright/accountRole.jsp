<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<link type="text/css" rel="stylesheet" href="tool/dataTables/css/dataTables.bootstrap.min.css" />
			<link href="tool/poplayer/css/ui-dialog.css" rel="stylesheet">
			<base href="<%=basePath%>" />
			<title><s:text name="角色管理" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<s:head />
		</head>

		<body>
			<div class="m-titleBar">
				<ol class="breadcrumb mybreadcrumb">当前位置：
					<li><a href="#"></a></li>
					<li class="active">角色与权限管理</li>
					<li class="active">指定角色</li>
				</ol>
			</div>
			
			
			<div class="div_table">
				<s:form action="accountRole" namespace="/role" theme="simple">
					<div class="errorInfo">
						<s:property value="tip" />
						<s:fielderror />
					</div>
					<input type="hidden" name="accountId" value="${accountId}" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="角色分配" /></td>
						</tr>
					</table>
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9">&nbsp;</td>
							<td>
								<s:optiontransferselect  name="norolesid"
									buttonCssClass="btn1"
									leftTitle="未分配角色"
									rightTitle="已分配角色"
									allowSelectAll="false" allowUpDownOnLeft="false"
									allowUpDownOnRight="false" list="noroles" listKey="id"
									listValue="name" doubleList="roles" doubleListKey="id"
									doubleListValue="name" doubleName="rolesId"
									cssClass="optelement" doubleCssClass="optelement">
								</s:optiontransferselect>
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
			
			
			<script type="text/javascript" src="tool/dataTables/js/jquery.js"></script>
			<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
		</body>
</html>