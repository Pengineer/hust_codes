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
					<li class="active">项目管理</li>
					<li class="active">查看</li>
				</ol>
			</div>

			<div class="m-form">
				<input type="hidden" id="projectId" value='<s:property value = "project.id"/>'/>
				<div class="panel panel-default panel-view">
					<div class="panel-heading">
					    <strong>项目信息</strong>
					</div>
	                <div class="panel-body">
		               <table class="table table-striped view-table">
					      <tbody>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right" width = "100">项目名称：</td>
								<td class = "text-left" width = "200"><s:property value = "project.name"/></td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right" width = "100">项目编号：</td>
								<td class = "text-left"  ><s:property value = "project.number"/></td>
							</tr>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right" width = "100">类别：</td>
								<td class = "text-left" width = "200"><s:property value = "project.type"/></td>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>来源：</td>
							    <td class='text-left' >
							        <s:property value='project.source' />
							    </td>
							<tr>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>开始时间：</td>
							    <td class='text-left' width = "200" >
							        <s:property value='project.startTime' />
							    </td>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>结束时间：</td>
							    <td class='text-left' >
							        <s:date name="project.endTime" format="yyyy-MM-dd" />
							    </td>
							</tr>
							<tr>   
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>附件名称：</td>
							    <td class='text-left' width = "200" >
							        <s:property value='project.attachmentName' />
							    </td>
							   <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>附件：</td>
							    <td class='text-left' >
							    	<s:property value='project.attachment' />
							    </td>
							</tr>
						  </tbody>
			    		</table>
	               	 </div>
	               </div>
	               <div class="panel panel-default panel-view">
						<div class="panel-heading">
							<strong>人员信息</strong>
						</div>
						<div class="panel-body">
			               <table class="table table-striped view-table">
						      <tbody>
								<tr>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>项目负责人：</td>
								    <td class='text-left' width = "200">
								        <s:property value='project.director' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>部门：</td>
								    <td class='text-left' >
								        <s:property value='project.department' />
								    </td>  
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>项目申请人：</td>
								    <td class='text-left' width = "200" >
								        <s:property value='project.account.name' />
								    </td>
								</tr>
								<tr>
								   <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>申请年份：</td>
								    <td class='text-left' >
								        ${project.year}
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>项目阶段：</td>
								    <td class='text-left' >
								        <s:property value='project.phase' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>牵头人：</td>
								    <td class='text-left' width = "200">
								        <s:property value='project.initiator' />
								    </td>
								</tr>			
								<tr>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>团队参与人员：</td>
								    <td class='text-left' >
								        <s:property value='project.projectmember' />
								    </td>  
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>预计参与专家：</td>
								    <td class='text-left' width = "200" >
								        <s:property value='project.expectedexpert' />
								    </td>
								   <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>实际参与专家：</td>
								    <td class='text-left' >
								    	<s:property value='project.participatedexpert' />
								    </td>
								</tr>
							  </tbody>
				    		</table>
		               	</div>	               
	               </div>
	               <div class="panel panel-default panel-view">
						<div class="panel-heading">
							<strong>经费信息</strong>
						</div>
						<div class="panel-body">
			               <table class="table table-striped view-table">
						      <tbody>
								<tr>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>需要资源：</td>
								    <td class='text-left' width = "200">
								        <s:property value='project.resource' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>预算经费：</td>
								    <td class='text-left' >
								        <s:property value='project.budget' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>执行经费：</td>
								    <td class='text-left' width = "200" >
								        <s:property value='project.fee' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>完成情况：</td>
								    <td class='text-left' >
								        <s:property value='project.completion' />
								    </td>
								</tr>
							  </tbody>
				    		</table>
		               	</div>	               
	               </div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
</html>