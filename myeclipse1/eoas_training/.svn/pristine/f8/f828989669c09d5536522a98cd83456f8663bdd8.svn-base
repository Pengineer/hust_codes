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
				<li class="active">角色管理</li>
				<li class="active">修改角色</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="modify"  namespace="/role" id = "role_add" theme="simple">	
					<div class="m-main-content">
						<!-- 基本信息 -->
						<div id="basic">
							<input type="hidden" name="role.id" value="${role.id}" />
							<s:hidden id="rightNodeValues" name="rightNodeValues" />
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">角色名称</label>
								<div class="col-sm-6">
									<s:textfield name="role.name" cssClass="form-control validate[required, custom[chinese]]" placeholder="角色名称"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">角色描述</label>
								<div class="col-sm-6">
									<s:textfield  name="role.description" cssClass="form-control validate[required]" placeholder="角色描述" theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">权限信息</label>
								<div class="col-sm-6">
									<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">	
										<tr>
											<td><div id="treeId" style="border:solid 1px #aaa;"></div></td>
										</tr>
									</table>
								</div>
								<div class="col-sm-3"></div>
							</div>
						</div>
					</div>
					<div id="optr" class="btn_bar2 text-center">
						<input id="submit" class="btn btn-sm btn-default" type="submit" value="确定" />
						<input id="cancel" class="btn btn-sm btn-default" type="button" value="取消" />
					</div>
				</s:form>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="javascript/common.js"></script>
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="tool/jQuery-Validation-Engine-2.6.2/js/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="javascript/oa/role/role_validate.js"></script>
	<script type="text/javascript" src="tool/tree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="tool/tree/js/dhtmlxtree.js"></script>
	<script type="text/javascript" src="javascript/oa/role/role.js"></script>
</html>