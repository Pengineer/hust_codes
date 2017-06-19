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
				<li class="m"><s:text name="公文管理" /></li>
				<li class="text_red"><s:text name="公文审核" /></li>
			</ul>
		</div>
		<div class="div_table">
			<s:form theme="simple" action = "audit" namespace = "/document">

					<input type="hidden" name="document.id" value="<s:property value = 'document.id' />" />
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="发文编号" />：</td>
						<td><s:property value = "document.serialNumber"  /></td>
					</tr>

					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="发文类型" />：</td>
						<td class="table_td3">
						<s:select headerValue = "--请选择--" headerKey = "0" name = "document.type" list = "#{'1':'函','2':'上行公文','3':'下行公文','4':'平行公文','5':'会议纪要'}" />
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="紧急程度" />：</td>
						<td class="table_td3">
						<s:select headerValue = "--请选择--" headerKey = "0" name = "document.priority" list = "#{'1':'普通','2':'紧急','3':'非常紧急'}" />
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="秘密等级" />：</td>
						<td class="table_td3">
						<s:select headerValue = "--请选择--" headerKey = "0" name = "document.level" list = "#{'1':'绝密','2':'机密','3':'秘密'}" />
						</td>
					</tr>					
					
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="发文标题" />：</td>
						<td class="table_td3"><s:property value ="document.title" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="正文" />：</td>
						<td>
							${document.subject}
						</td>
					</tr>
					
				<table>
					<tr>
						<td><s:text name = "审核意见" /></td>
						<td><s:textfield name = "docResult.opinion" /></td>
					</tr>
					<tr>
						<td><s:text name = "审核结果 " /></td>
						<td><s:radio list="#{'0':'不同意','1':'同意'}" name="docResult.status"/></td>
					</tr>
					<tr>
						<td align="right" bgcolor="#b3c6d9">&nbsp;</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<input id="submit" class="btn1" type="submit" value="<s:text name='确定' />" />
								<input id="cancel" class="btn1" type="button" value="<s:text name='取消' />" onclick="history.back();" />
							</table>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="javascript/oa/account/list.js"></script>
</html>