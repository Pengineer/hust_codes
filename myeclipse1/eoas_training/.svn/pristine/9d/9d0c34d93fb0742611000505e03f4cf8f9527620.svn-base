<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="考核管理" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
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
	</head>

	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="考核管理" /></li>
				<li class="text_red"><s:text name="考核申请" /></li>
			</ul>
		</div>
		<div class="div_table">
			<s:form action="add" id="form_assess" namespace="/assess" theme="simple" method="post">
				<table>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="考勤分数" />：</td>
						<td>
							<div class="input0" style="float:left;">
								<s:textfield name = "assess.ascores" />
							</div>
							<div></div>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="工作表现分数" />：</td>
						<td>
							<div class="input0" style="float:left;">
								<s:textfield name="assess.pscores" cssClass="inputcss" />
							</div>
							<div></div>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
						<td>
							<div class="input0" style="float:left;">
								<s:textfield name="assess.note" cssClass="inputcss" />
							</div>
							<div></div>
						</td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="被考核人" />：</td>
						<td>
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">请选择被考核人</button>
							<span id="personSelected" style="display:none;"></span>
							<input id = "personInfo" name = "personId" type = "hidden" class = "btn btn-data" />
						</td>
					</tr>
					<tr>
						<td><input id="submit" class="btn1" type="submit" value="<s:text name='确定' />" /></td>
						<td><input id="cancel" class="btn1" type="button" value="<s:text name='取消' />" onclick="history.back();" /></td>
					</tr>
				</table>
			</s:form>
		</div>
		
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
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="javascript/common.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.self.js"></script>
		<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="javascript/oa/person/person_list.js"></script>	
	</body>
</html>