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
			<s:form id = "form_record"  name = "form_record" action = "record/add.action"  theme="simple">
				<div class="panel panel-success">
					<div class="panel-heading">基本信息</div>
					<input type="hidden" name="positionResumeId" value="${positionResumeId}" />
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*姓名</span>&nbsp;：</td>
							<td><s:property value="resume.name" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*应聘职位</span>&nbsp;：</td>
							<td><s:property value="position.name" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*手机号</span>&nbsp;：</td>
							<td><s:property value="resume.mobilephone" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">*邮箱</span>&nbsp;：</td>
							<td><s:property value="account.email" /></td>
						</tr>	
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">笔试分数</span>&nbsp;：</td>
							<td><s:textfield name="record.writtenScore" /></td>
						</tr>				
						<div class="row">
							着装打扮：
							<label> 
						        <input type="radio" name="record.dressup" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.dressup" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.dressup" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.dressup" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.dressup" value="2">极差
						    </label> 
						</div>
						<div class="row">
							沟通能力：
							<label> 
						        <input type="radio" name="record.communication" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.communication" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.communication" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.communication" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.communication" value="2">极差
						    </label> 
						</div>
						<div class="row">
							分析能力：
							<label> 
						        <input type="radio" name="record.analysis" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.analysis" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.analysis" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.analysis" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.analysis" value="2">极差
						    </label> 
						</div>
						<div class="row">
							应变能力：
							<label> 
						        <input type="radio" name="record.flexibility" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.flexibility" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.flexibility" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.flexibility" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.flexibility" value="2">极差
						    </label> 
						</div>
						<div class="row">
							人际关系：
							<label> 
						        <input type="radio" name="record.relationships" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.relationships" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.relationships" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.relationships" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.relationships" value="2">极差
						    </label> 
						</div>
						<div class="row">
							责任心：
							<label> 
						        <input type="radio" name="record.responsibility" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.responsibility" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.responsibility" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.responsibility" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.responsibility" value="2">极差
						    </label> 
						</div>
						<div class="row">
							抗压能力：
							<label> 
						        <input type="radio" name="record.antipressure" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.antipressure" value="1">佳
						    </label>
						    <label>
						        <input type="radio" name="record.antipressure" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.antipressure" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.antipressure" value="2">极差
						    </label> 
						</div>
						<div class="row">
							执行力：
							<label> 
						        <input type="radio" name="record.execution" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.execution" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.execution" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.execution" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.execution" value="2">极差
						    </label> 
						</div>
						<div class="row">
							专业知识：
							<label> 
						        <input type="radio" name="record.specialty" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.specialty" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.specialty" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.specialty" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.specialty" value="2">极差
						    </label> 
						</div>
						<div class="row">
							工作意愿：
							<label> 
						        <input type="radio" name="record.willingness" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.willingness" value="1">佳
						    </label>
						    <label> 
						        <input type="radio" name="record.willingness" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.willingness" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.willingness" value="2">极差
						    </label> 
						</div>
						<div class="row">
							个人品质：
							<label> 
						        <input type="radio" name="record.character" value="0">极佳
						    </label>
						    <label> 
						        <input type="radio" name="record.character" value="1">佳
						    </label>
						    <label>
						        <input type="radio" name="record.character" value="2">普通 
						    </label>
						    <label> 
						        <input type="radio" name="record.character" value="2">略差 
						    </label> 
						    <label> 
						        <input type="radio" name="record.character" value="2">极差
						    </label> 
						</div>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" /></td>
							<td><s:textarea name="record.note" cols="80" rows="5"/></td>
						</tr>
					</table>						
					
					<div class="row">
					    <div class="center"><s:submit name="createResume" value="创建笔试面试记录"/></div>
					</div>
				</div>
			</s:form>
		</div>
	</body>
</html>