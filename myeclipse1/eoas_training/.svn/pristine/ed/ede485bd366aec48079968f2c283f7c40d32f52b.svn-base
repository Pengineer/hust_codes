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
					<li class="active">专家顾问</li>
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
				<input type="hidden" id="expertId" value='<s:property value = "expert.id"/>'/>
				<div class="panel panel-default panel-view">
					<div class="panel-heading">
					    <strong>基本信息</strong>
					</div>
	                <div class="panel-body">
		               <table class="table table-striped view-table">
					      <tbody>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right" width = "100">中文名：</td>
								<td class = "text-left" width = "200"><s:property value = "expert.name"/></td>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right" width = "100">曾用名：</td>
								<td class = "text-left"  ><s:property value = "expert.usedname"/></td>
							</tr>
							<tr>
								<td width = "50" class = "text-right"><span class="glyphicon glyphicon-triangle-right" aria-hidden="true"></span></td>
								<td class = "text-right" width = "100">性别：</td>
								<td class = "text-left" width = "200"><s:property value = "expert.gender"/></td>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>民族：</td>
							    <td class='text-left' >
							        <s:property value='expert.ethnic' />
							    </td>
							<tr>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>证件号码：</td>
							    <td class='text-left' width = "200" >
							        <s:property value='expert.idcardNumber' />
							    </td>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>出生日期：</td>
							    <td class='text-left' >
							        <s:date name="expert.birthday" format="yyyy-MM-dd" />
							    </td>
							</tr>
							<tr>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>证件类型：</td>
							    <td class='text-left' width = "200">
							        <s:property value='expert.idcardType' />
							    </td>
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>手机：</td>
							    <td class='text-left' >
							        <s:property value='expert.mobilePhone' />
							    </td>
							 </tr>
							<tr>   
							    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>电子邮箱：</td>
							    <td class='text-left' width = "200" >
							        <s:property value='expert.email' />
							    </td>
							   <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
							    <td class='text-right' width='100'>个人主页：</td>
							    <td class='text-left' >
							        <a  href="http://<s:property value='expert.homepage' />" target = "_blank">${expert.homepage}</a>
							    </td>
							</tr>
						  </tbody>
			    		</table>
	               	 </div>
	               </div>

	               <div class="panel panel-default panel-view">
						<div class="panel-heading">
							<strong>联系方式</strong>
						</div>
						<div class="panel-body">
			               <table class="table table-striped view-table">
						      <tbody>
								<tr>    
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>家庭电话：</td>
								    <td class='text-left' width = "200" >
								        <s:property value='expert.homePhone' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>家庭传真：</td>
								    <td class='text-left'>
								        <s:property value='expert.homeFax' />
								    </td>
								</tr>
								<tr>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>家庭地址：</td>
								    <td class='text-left' width = "200">
								        <s:property value='expert.homeAddress' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>家庭邮编：</td>
								    <td class='text-left' >
								        <s:property value='expert.homePostcode' />
								    </td>
								</tr>
								<tr>
									<td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>办公电话：</td>
								    <td class='text-left' width = "200">
								        <s:property value='expert.officePhone' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>办公传真：</td>
								    <td class='text-left' >
								        <s:property value='expert.officeFax' />
								    </td>
								</tr>
								<tr>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>办公地址：</td>
								    <td class='text-left' width = "200">
								        <s:property value='expert.officeAddress' />
								    </td>
								    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
								    <td class='text-right' width='100'>办公邮编：</td>
								    <td class='text-left' >
								        <s:property value='expert.officePostcode' />
								    </td>
								</tr>
							  </tbody>
				    		</table>
		               	</div>	               
	               </div>

					<div class="panel panel-default panel-view">
		               	<div class="panel-heading">
							<strong>社会信息</strong>
						</div>
						<div class="panel-body">
							<table class="table table-striped view-table">
								<tbody>
									<tr>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>所在单位：</td>
									    <td class='text-left' width = "200">
									        <s:property value='expert.company' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>所在部门：</td>
									    <td class='text-left' width = "200">
									        <s:property value='expert.department' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>行政职务：</td>
									    <td class='text-left' >
									        <s:property value='expert.job' />
									    </td>
									</tr>
									<tr>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>专业职称：</td>
									    <td class='text-left' >
									        <s:property value='expert.specialityTitle' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>是否博士后：</td>
									    <td class='text-left' >
									    	<s:if test="expert.postdoctor==0">
													否
											</s:if>
											<s:if test="expert.postdoctor==1">
													是
											</s:if>
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>学术兼职：</td>
									    <td class='text-left' >
									        <s:property value='expert.parttime' />
									    </td>
									</tr>
									<tr>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>最后学历：</td>
									    <td class='text-left' >
									        <s:property value='expert.lastEducation' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>最后学位：</td>
									    <td class='text-left' >
									        <s:property value='expert.degree' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>外语语种：</td>
									    <td class='text-left' >
									        <s:property value='expert.foreignLanguage' />
									    </td>
									</tr>
									<tr>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>研究方向：</td>
									    <td class='text-left' >
									        <s:property value='expert.researchField' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>学科代码：</td>
									    <td class='text-left' >
									        <s:property value='expert.discipline' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>个人简介：</td>
									    <td class='text-left' >
									        <s:property value='expert.introduction' />
									    </td>
									</tr>
								</tbody>
							</table>
		               	</div>					
					</div>

					<div class="panel panel-default panel-view">
		               	<div class="panel-heading">
							<strong>银行信息</strong>
						</div>
						<div class="panel-body">
							<table class="table table-striped view-table">
								<tbody>
									<tr>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>开户银行：</td>
									    <td class='text-left' width='200'>
									        <s:property value='expert.bankName' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>银行支行：</td>
									    <td class='text-left' width='200'>
									        <s:property value='expert.bankBranch' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>银联行号：</td>
									    <td class='text-left' >
									        <s:property value='expert.bankCupNumber' />
									    </td>
									</tr>
									<tr>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>银行户名：</td>
									    <td class='text-left' width='200'>
									        <s:property value='expert.bankAccountName' />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>创建时间：</td>
									    <td class='text-left' width='200'>
									        <s:date name="expert.createDate" format="yyyy-MM-dd HH:mm:ss" />
									    </td>
									    <td width='50' class='text-right'><span class='glyphicon glyphicon-triangle-right' aria-hidden='true'></span></td>
									    <td class='text-right' width='100'>更新时间：</td>
									    <td class='text-left' >
									        <s:date name="expert.updateDate" format="yyyy-MM-dd HH:mm:ss" />
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
	<script type="text/javascript" src="tool/poplayer/js/dialog-plus.js"></script>
	<script type="text/javascript" src="javascript/oa/expert/view.js"></script>
</html>