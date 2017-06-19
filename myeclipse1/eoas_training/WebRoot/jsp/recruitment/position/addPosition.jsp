<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司招聘</title>   
		<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="css/recruitment/common.css">
	</head>
	<body>
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div id="wrap" class="content">
			<s:form id = "form_position"  name = "form_position" action = "position/addPosition.action"  theme="simple">
				<div class="panel panel-success">
					<div class="panel-heading">发布职位</div>
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*职位名称</span>&nbsp;：</td>
							<td><input id="position.name"  name="position.name" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*职位类型</span>&nbsp;：</td>
							<td><s:select label="职位类型" name="position.type" list="{'前端','后端','文秘','会计'}"/> </td>
						</tr>						
					 	<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*工作地点</span>&nbsp;：</td>
							<td><input id="position.place"  name="position.place" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*招聘人数</span>&nbsp;：</td>
							<td><input id="position.number"  name="position.number" cssclass="inputcss" placeholder="输入文本"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*工作职责</span>&nbsp;：</td>
							<td><s:textfield name="position.responsibility"  size="100" cssClass="inputcss" /></td>
						</tr>
						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*职位要求</span>&nbsp;：</td>
							<td><s:textfield name="position.requirement"  size="100" cssClass="inputcss" /></td>
						</tr>
					</table>	
				</div>
				<div class="row">
					<div class="center"><s:submit name="createPosition" value="发布职位"/></div>
				</div>
			</s:form>
		</div>
	</body>
 	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script> 
</html>
