<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<s:i18n name="csdc.resources.i18n_RoleRight">
		<head>
			<base href="<%=basePath%>" />
			<title><s:text name="i18n_ModifyRightInfo" /></title>
			<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
			<link href="css/validate.css" rel="stylesheet" type="text/css" />
			<style type="text/css">
				#form_roleright label.error {
					padding-left: 6px;
					padding-bottom: 2px;
					color: rgb(255, 116, 0);
				}
			</style>
		</head>

		<body>
			<div class="title_bar">
				<ul>
					<li class="m"><s:text name="角色与权限管理" /></li>
					<li class="text_red"><s:text name="修改权限信息" /></li>
				</ul>
			</div>
			<div class="div_table">
				<s:form action="modify" id="form_roleright" namespace="/right" theme="simple">
					<div class="errorInfo">
						<s:property value="tip" />
						<s:fielderror />
					</div>
					<input type="hidden" name="right.id" value="${right.id}" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="i18n_BaseInfo" /></td>
						</tr>
					</table>
					<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="i18n_RightName" />：</td>
							<td><s:textfield name="right.name" value="%{right.name}" cssClass="inputcss" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="权限代码" />：</td>
							<td><s:textfield name="right.code" value="%{right.code}" cssClass="inputcss" /></td>
						</tr>
						<tr>
							<td align="right" valign="top" bgcolor="#b3c6d9"><s:text name="i18n_RightDescription" />：</td>
							<td><s:textarea cssStyle="font-size:12px;" name="right.description" value="%{right.description}" cols="80" rows="4" cssClass="inputcss" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="节点值" />：</td>
							<td><s:textfield name="right.nodeValue" value="%{right.nodeValue}" cssClass="inputcss" /></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:2px;">
						<tr>
							<td width="50%" align="right" style="padding-right:10px;"><input type="submit" class ="btn1" value = "<s:text name="i18n_Ok" />" /></td>
							<td style="padding-left:10px;"><input type="button" class="btn1" value="<s:text name="i18n_Cancel" />" onclick="history.back();" /></td>
						</tr>
					</table>
				</s:form>
			</div>
		</body>
		<script type="text/javascript" src="javascript/jquery/jquery.js"></script>
		<script type="text/javascript" src="javascript/common.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
	</s:i18n>
</html> --%>


<%-- <%@ page language="java" import="java.util.*" contentType = "text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">	
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="添加权限" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<style type="text/css">
			#form_roleright label.error {
				padding-left: 6px;
				padding-bottom: 2px;
				color: rgb(255, 116, 0);
			}
		</style>
	</head>

	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="角色与权限管理" /></li>
				<li class="text_red"><s:text name="添加权限" /></li>
			</ul>
		</div>
		<div class="div_table">
			<s:form action="add" id="form_roleright" namespace="/right" method="POST" theme="simple">

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="基本信息" /></td>
					</tr>
				</table>
				<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="权限名称" />：</td>
						<td><s:textfield name="right.name" cssClass="inputcss" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="权限代码" />：</td>
						<td><s:textfield name="right.code" cssClass="inputcss" /></td>
					</tr>
					<tr>
						<td align="right" align="top" bgcolor="#b3c6d9"><s:text name="权限描述" />：</td>
						<td><s:textarea cssStyle="font-size:12px;" name="right.description" cols="80" rows="4" cssClass="inputcss" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="节点值" />：</td>
						<td><s:textfield name="right.nodeValue" cssClass="inputcss" /></td>
					</tr>
				</table>

				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:2px;">
					<tr>
						<td width="50%" align="right" style="padding-right:10px;"><input type="submit" class ="btn1" value = "<s:text name="确定" />" /></td>
						<td style="padding-left:10px;"><input type="button" class="btn1" value="<s:text name="取消" />" onclick="history.back();" /></td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html> --%>


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
			  <li class="active">权限管理</li>
			  <li class="active">修改权限</li>
			</ol>
			<div class="m-form">
				<s:form class="form-horizontal" action="modify"  namespace="/right" id = "right_modify" theme="simple">
					
					<div class="m-main-content">
						<!-- 基本信息 -->
						<div id="basic">
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">权限名称</label>
								<div class="col-sm-6">
									<s:textfield name="right.name" cssClass="form-control validate[required, custom[chinese]]" placeholder="权限名称"  theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">权限代码</label>
								<div class="col-sm-6">
									<s:textfield  name="right.code" cssClass="form-control validate[required]" placeholder="权限代码" theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">权限描述</label>
								<div class="col-sm-6">
									<s:textfield  name="right.description" cssClass="form-control validate[required]" placeholder="权限描述" theme="simple" />
								</div>
								<div class="col-sm-3"></div>
							</div>
							<div class="form-group m-form-group">
								<label class="col-sm-3 control-label u-text-right required">节点值</label>
								<div class="col-sm-6">
									<s:textfield  name="right.nodeValue" cssClass="form-control validate[required]" placeholder="节点值" theme="simple" />
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
	<script type="text/javascript" src="javascript/oa/right/right_validate.js"></script>
</html>