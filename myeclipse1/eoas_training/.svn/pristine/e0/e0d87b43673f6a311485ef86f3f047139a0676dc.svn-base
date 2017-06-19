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
		<link href = "tool/dataTables/css/jquery.dataTables.css">
		<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="css/recruitment/common.css">
		<style type="text/css" title="currentStyle">
				@import "tool/dataTables/css/demo_page.css";
				@import "tool/dataTables/examples/examples_support/themes/smoothness/jquery-ui-1.8.4.custom.css";
				@import "tool/dataTables/css/demo_table_jui.css";
				@import "tool/dataTables/css/jquery.dataTables_themeroller.css";
				@import "tool/dataTables/css/jquery.dataTables.css";
				@import "tool/dataTables/images/back_enabled_hover.png";
				@import "tool/dataTables/images/forward_enabled.png";
				@import "tool/dataTables/images/sort_asc.png";
				@import "tool/dataTables/images/sort_desc.png";
				@import "tool/dataTables/images/Sorting icons.psd";
		</style>
	</head>
	<body>	
		<div class="kit" style="width:940px;margin-left:0px;padding-left:0px；float:right">
			<p style="font-size:14px;color:#000;font-weight:bold;text-align:right;">简历类型：社招简历名称:hello</p>
		</div>
		<s:include value="/jsp/recruitment/topBottom.jsp" />
		<div class="content">
			<div class="sub_bar">
			<td width="30"><a href="resume/toModify.action?positionId=${position.id}">修改</a></td>
			<s:hidden id="positionId" name="position.id"/>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="20" ><s:text name="职位名称" />：</td>
					<td class="txtpadding" width="300" align="left"><s:property value="position.name" /></td>
				</tr>
			</table>
			<table class="txtlineheight" width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr>	
					<td width="100" align="left" ><s:text name="工作地点" />：</td>
					<td class="txtpadding" width="179"><s:property value="position.place" /></td>
					<td width="100" align="right" ><s:text name="招聘人数" />：</td>
					<td class="txtpadding" width="179"><s:property value="position.number" /></td>
					<td width="100" align="right" ><s:text name="发布时间" />：</td>
					<td class="txtpadding" width="179"><s:property value="position.releaseTime" /></td>
				</tr>
			</table>
			<table class="txtlineheight" width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td width="20" align="left" ><s:text name="工作职责" />：</td>
					<td class="txtpadding" width="300"><s:property value="position.responsibility" /></td>
				</tr>
			</table>
			<table class="txtlineheight" width="100%" border="0" cellspacing="0" cellpadding="0" >
					<td width="20" align="left" ><s:text name="职位要求" />：</td>
					<td class="txtpadding" width="300"><s:property value="position.requirement" /></td>
			</table>
			<div class="btn_div_view" style="text-align: center; margin-top: 10px;">
<!-- 				<input id="apply"  type="button" value="<s:text name='职位申请' />" />&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				<input id="collect"  type="button" value="<s:text name='职位搜藏' />" />  -->
				<a class="btn" role="button" data-toggle="modal" href="#resumeList" >职位申请</a>
		
			</div>
			<div id = "resumeList" class = "modal  fade" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3 id="myModalLabel">请选择您的简历</h3>
				</div>	
				<div class="modal-body">
					<table id="myResumeList" class="display" cellspacing="0" width="100%">
			        	<thead>
			            	<tr>     
				            	<th>简历名称</th>
				                <th>简历类型</th>
				                <th>创建时间</th>
				                <th>修改时间</th>
				                <th>操作</th>
		           		    </tr>
		                </thead>
			        </table>
				</div>
		        <div class="modal-footer"   >  
					<input  id="apply" type="button" value="申请岗位" onclick="applyPosition()">
					<input  class="collect" type="button" value="收藏" onclick="collectPosition()">
				</div>
			 </div>
		</div>
	<script type="text/javascript" src="javascript/recruitment/position/position.js"></script>	
	<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="tool/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="javascript/recruitment/position/position_list.js"></script>	
	</body>
</html>