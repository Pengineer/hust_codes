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
		<div class="kit" style="width:940px;margin-left:0px;padding-left:0px；float:right">
			<p style="font-size:14px;color:#000;font-weight:bold;text-align:right;">简历类型：社招简历名称:hello</p>
		</div>
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div class="content">
				<div class="sub_bar">
				<td width="30"><a href="resume/toModify.action?resumeid=${resume.id}">修改</a></td>
				</div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="个人基本信息" /></td>
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="笔试分数" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.writtenScore" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="着装打扮" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.dressup" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="沟通能力" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.communication" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="分析能力" />：</td>
						<td class="txtpadding"><s:property value="record.analysis" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="应变能力" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.flexibility" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="人际关系" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.relationships" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="责任心" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.responsibility" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="抗压能力" />：</td>
						<td class="txtpadding"><s:property value="record.antipressure" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="执行力" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.execution" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="专业知识" />：</td>
						<td class="txtpadding"><s:property value="record.specialty" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="工作意愿" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.willingness" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="个人品质" />：</td>
						<td class="txtpadding"><s:property value="record.character" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
						<td class="txtpadding" width="179"><s:property value="record.note" /></td>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="面试时间" />：</td>
						<td class="txtpadding"><s:property value="record.date" /></td>
					</tr>
				</table>
		</div>
	</body>
</html>