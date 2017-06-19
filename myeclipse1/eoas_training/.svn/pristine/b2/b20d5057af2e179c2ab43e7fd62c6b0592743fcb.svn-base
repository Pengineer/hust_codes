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
		<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="tool/dataTables/css/demo_page.css" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/examples/examples_support/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/css/demo_table_jui.css" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/css/jquery.dataTables_themeroller.css" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/css/jquery.dataTables.css" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/images/back_enabled_hover.png" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/images/forward_enabled.png" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/images/sort_asc.png" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/images/sort_desc.png" />
		<link type="text/css" rel="stylesheet" href="tool/dataTables/images/Sorting icons.psd" />
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
				<li class="m"><s:text name="公文管理" /></li>
				<li class="text_red"><s:text name="公文拟稿" /></li>
			</ul>
		</div>
		<div class="div_table">
			<s:form action="add"  namespace="/document" theme="simple">
				<table width="100%" border="1" cellspacing="0" cellpadding="4" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="发文编号" />：</td>
						<td><s:textfield cssClass="inputcss" name="document.serialNumber" theme="simple" size="60" /></td>
					</tr>

					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="发文类型" />：</td>
						<td class="table_td3">
						<s:select headerValue = "--请选择--" headerKey = "0" name = "document.type" list = "#{'1':'函','2':'上行公文','3':'下行公文','4':'平行公文','5':'会议纪要'}" />
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="紧急程度" />：</td>
						<td class="table_td3">
						<s:select headerValue = "--请选择--" headerKey = "0" name = "document.priority" list = "#{'1':'普通','2':'紧急','3':'非常紧急'}" />
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="秘密等级" />：</td>
						<td class="table_td3">
						<s:select headerValue = "--请选择--" headerKey = "0" name = "document.level" list = "#{'1':'绝密','2':'机密','3':'秘密'}" />
						</td>
					</tr>					
					
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="发文标题" />：</td>
						<td class="table_td3"><s:textarea id="rdesc" name="document.title"  style="height: 20px; width: 400px;" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9" style="padding-top:5px; vertical-align:top;"><s:text name="正文" />：</td>
						<td>
							<FCK:editor instanceName="document.subject" value="${document.subject}" basePath="/tool/fckeditor" width="100%" height="450" toolbarSet="Default"></FCK:editor>
						</td>
					</tr>

<!-- 					<div class = "btn_div_view" style="text-align: center; margin-top: 10px;">
						<button class="btn pull-left" role = "button" data-toggle="modal" data-target = "#myModal" >指定审核人</button>
						<span id = "documentAudit"></span>
						<input id = "auditAccount" type = "hidden" name = "auditAccount" />
					</div> -->
					<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="被审核人" />：</td>
							<td>
								<button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">请选择被审核人</button>
								<span id="personSelected" style="display:none;"></span>
								<input id = "personInfo" name = "personId" type = "hidden" class = "btn btn-data" />
								
							</td>
					
					</tr>

					
					<tr>
						<td align="right" bgcolor="#b3c6d9">&nbsp;</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<input id="submit" class="btn1" type="submit" value="<s:text name='确定' />" />
								<input id="cancel" class="btn1" type="button" value="<s:text name='取消' />" onclick="history.back();" />
							</table>
						</td>
					</tr>
				</table>
			</s:form>
			<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title">人员列表</h4>
						</div>
						<div class="modal-body">
							<div class="panel-heading">人员列表</div>
								<table id="personList" class="table">
									<thead>
									  <tr>
									  	<th>员工姓名</th>
									    <th>性别</th>
									    <th>工作部门</th>
									    <th>员工编号</th>
									    <th>手机号</th>
									    <th>入职时间</th>
									    <th>操作</th>
									  </tr>
									</thead>
						          <tbody>
						            
						          </tbody>
						        </table>
						</div>
						<div class="modal-footer">
							<button id="save" type="button" class="btn btn-default" data-dismiss="modal">Save</button>
						</div>
					</div>
				</div>
			</div>
<!-- 			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
					  	<div class="modal-header">
						      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						      <h4 class="modal-title" id="myModalLabel">部门经理审批</h4>
						</div>
						<div class="modal-body">
							<table id = "accountList" cellspacing = "0">
								<thead>
									<tr>账号</tr>
									<tr>姓名</tr>
									<tr>赋予角色</tr>
									<tr>操作</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<input type = "button" value = "确定" onclick = "selectPerson()" />
						    <input id = "cancel" type = "button" vallue = "取消" onclick = "history.back()" />
						</div>
					</div>
				</div>
			</div> -->
		</div>
	</body>
	<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="javascript/oa/person/person_list.js"></script>
</html>