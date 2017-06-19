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
		<link href="tool/jQuery-Validation-Engine-2.6.2/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
	</head>

	<body class="g-pageRight">
		<div class="g-wrapper">
			<ol class="breadcrumb mybreadcrumb">当前位置：
				<li><a href="#"></a></li>
				<li class="active">帐号管理</li>
				<li class="active">修改帐号</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="modify"  namespace="/account" id = "account_modify" theme="simple">
					<div class="m-main-content">
						<div id="basic">
							<div class="form-group m-form-group">
								<s:property value="tip" />
								<s:fielderror />
							</div>
							<s:fielderror ></s:fielderror>
							<input type="hidden" name="accountId" value="<s:property value="account.id" />" />
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">用户名</label>
								<div class="col-sm-6">
									<s:property  value="account.name"  />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">邮箱</label>
								<div class="col-sm-6">
									<s:textfield  name="account.email" cssClass="form-control validate[required, custom[email]]" placeholder="邮箱" theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">密码</label>
								<div class="col-sm-6">
									<s:textfield  name="account.password" value = "" type = "password" cssClass="form-control validate[required]" placeholder="密码" theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">是否超级管理员</label>
								<div class="col-sm-6">
									<label><input type="radio" name="account.isSuperUser" value = "1">是</label>
									<label><input type="radio" name="account.isSuperUser" value = "0">否</label>
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
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="javascript/oa/account/account_validate.js"></script>
</html>