<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/jsp/innerBase.jsp" />
		<link href="tool/bootstrap-datepicker-1.4.0-dist/css/bootstrap-datepicker3.css" rel="stylesheet">
		<link href="tool/poplayer/css/ui-dialog.css" rel="stylesheet">
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<link href="tool/jQuery-Validation-Engine-2.6.2/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
		<link href="tool/step/css/step.css" rel="stylesheet" type="text/css" />
	</head>

	<body class="g-pageRight">
		<div class="g-wrapper">
			<ol class="breadcrumb mybreadcrumb">当前位置：
			  <li><a href="#"></a></li>
			  <li class="active">项目管理</li>
			  <li class="active">项目申请</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="apply"  namespace="/project" id = "project_apply" theme="simple">
					<div class="m-main-content">
						<!-- 基本信息 -->
						<div id="apply">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">项目名称：</label>
								<div class="col-sm-6">
									<s:textfield name="project.name" cssClass="form-control"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">编号：</label>
								<div class="col-sm-6">
									<s:textfield name="project.number" cssClass="form-control"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">类别：</label>
								<div class="col-sm-6">
									<s:textfield name="project.type" cssClass="form-control"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">来源：</label>
								<div class="col-sm-6">
									<s:textfield name="project.source" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">开始时间：</label>
								<div class="col-sm-6">
									<s:textfield id ="startTime" name="project.startTime" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">结束时间：</label>
								<div class="col-sm-6">
									<s:textfield id ="endTime" name="project.endTime" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">项目负责人：</label>
								<div class="col-sm-6">
									<s:textfield name="project.director" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">部门：</label>
								<div class="col-sm-6">
									<s:textfield name="project.department" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">项目阶段：</label>
								<div class="col-sm-6">
									<s:textfield name="project.phase" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">申请年份：</label>
								<div class="col-sm-6">
									<s:textfield name="project.year" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">申请人：</label>
								<div class="col-sm-6">
									<s:textfield name="project.account.name" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">牵头人：</label>
								<div class="col-sm-6">
									<s:textfield name="project.initiator" cssClass="form-control"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">团队参与人员：</label>
								<div class="col-sm-6">
									<s:textfield name="project.projectmember" cssClass="form-control"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">预计参与专家：</label>
								<div class="col-sm-6">
									<s:textfield name="project.expectedexpert" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">实际参与专家：</label>
								<div class="col-sm-6">
									<s:textfield name="project.participatedexpert" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">完成情况：</label>
								<div class="col-sm-6">
									<s:textfield name="project.completion" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">需要资源：</label>
								<div class="col-sm-6">
									<s:textfield name="project.resource" cssClass="form-control "  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">预算经费：</label>
								<div class="col-sm-6">
									<s:textfield name="project.budget" cssClass="form-control"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">执行经费：</label>
								<div class="col-sm-6">
									<s:textfield name="project.fee" cssClass="form-control"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
					        <table>
					        	<center>
						        	<button id = "add_btn" type="submmit" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off">
						        		申请
									</button>					        	
					        	</center>
					        </table>
					</div>
				</s:form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/bootstrap-datepicker-1.4.0-dist/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="javascript/oa/project/apply.js"></script>
</html>