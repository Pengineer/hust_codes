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
		<link href="tool/jQuery-Validation-Engine-2.6.2/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<div class="g-wrapper">
			<div class="m-titleBar">
				<ol class="breadcrumb mybreadcrumb">当前位置：
					<li><a href="#"></a></li>
					<li class="active">个人空间</li>
					<li class="active">密码修改</li>
				</ol>
			</div>
			
			<div class="m-form">
	            <div class="panel-body">
		            <s:form action="account/modify.action" class="form-signin" id="modifyPassword">
		            	<div class="form-group m-form-group">
							<label class="col-sm-3 control-label u-text-right required">当前密码</label>
							<div class="col-sm-6">
								<s:textfield type="password" name="opassword" cssClass="form-control validate[required]]" placeholder="请输入"  theme="simple" />
							</div>
							<div class="col-sm-3"></div>
						</div>
						<div class="form-group m-form-group">
							<label class="col-sm-3 control-label u-text-right required">新密码</label>
							<div class="col-sm-6">
								<s:textfield type="password" name="password" id="password" cssClass="form-control validate[required],minSize[6]]" placeholder="请输入"  theme="simple" />
							</div>
							<div class="col-sm-3"></div>
						</div>
						<div class="form-group m-form-group">
							<label class="col-sm-3 control-label u-text-right required">确认新密码</label>
							<div class="col-sm-6">
								<s:textfield type="password" name="repassword" cssClass="form-control validate[required],equals[password]]" placeholder="请输入"  theme="simple" />
							</div>
							<div class="col-sm-3"></div>
						</div>
						<div style="text-align:center">
						    <input class="btn btn-success" type="submit" value="保存"/>
						</div>
					</s:form>
	            </div>
			</div>
		</div>
			
		
			<!-- <div class="content">
				<div class="panel panel-success">
					<div class="panel-heading">修改密码1</div>
					<s:form action="account/modify.action" class="form-signin" id="modifypass-form">
						<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">邮箱地址</span>&nbsp;：</td>
							<td style="border-right:0;" class="txtpadding"><s:property value="account.email" /></td>
							<td style="border-left:0;"></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">当前密码</span>&nbsp;：</td>
							<td><input name="account.password" type="password" cssclass="inputcss" placeholder="输入文本"></td>
							<td></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">新密码</span>&nbsp;：</td>
							<td><input id="newPassword"  name="account.newPassword" type="password" cssclass="inputcss" placeholder="输入文本"></td>
							<td></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><span class="text_red">确认新密码</span>&nbsp;：</td>
							<td><input name="account.repassword" type="password" cssclass="inputcss" placeholder="输入文本"></td>
							<td></td>
						</tr>
						</table>
						<div class="row center">
						    <input class="btn btn-success" type="submit" value="保存"/>
						</div>
						<div class="errorInfo">
							<s:property value="tip" />
							<s:fielderror />
				   		</div>
						<div class="row center">
							邮箱
							<s:property value="account.email" />
						</div>
						<div class="row center">
							当前密码:
						    <input type="password" name="opassword" class="form-control" placeholder="当前密码" required>
						</div>
						<div class="row center">
							新密码：
							<input type="password" name="password" id="password" class="form-control" placeholder="新密码" required>
						</div>
						<div class="row center">
							确认新密码：
						    <input type="password" name="repassword" class="form-control" placeholder="确认新密码" required>
						</div>
						<div class="row center">
						    <input class="btn btn-success" type="submit" value="保存"/>
						</div>
					</s:form>
		        </div>
			</div> -->
		<!-- </div> -->
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="javascript/recruitment/account/login.js"></script>
	</body>
</html>
