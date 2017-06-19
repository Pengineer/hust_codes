<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/jsp/innerBase.jsp" />
	</head>

	<body class="g-pageRight">
		<div class="g-wrapper">
			<ol class="breadcrumb mybreadcrumb">当前位置：
				<li><a href="#"></a></li>
				<li class="active">考勤管理</li>
				<li class="active">签到</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="add"  namespace="/attendance" id = "attendance_add" theme="simple">
					<div><s:hidden name="tag" /></div>
					<div class="m-main-content">
						<div id="basic">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">真实姓名</label>
								<div class="col-sm-6">
									<s:textfield name="person.realName" cssClass="form-control validate[required]"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right">备注</label>
								<div class="col-sm-6">
									<s:textfield  name="attendance.description" cssClass="form-control" placeholder="签到备注" theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>

						</div>
					</div>
					<div id="optr" class="btn_bar2 text-center">
						<input id="confirm" class="btn btn-sm btn-default" type="submit" value="确定" />
						<input id="cancel" class="btn btn-sm btn-default" type="button" value="取消" />
					</div>
				</s:form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
</html>