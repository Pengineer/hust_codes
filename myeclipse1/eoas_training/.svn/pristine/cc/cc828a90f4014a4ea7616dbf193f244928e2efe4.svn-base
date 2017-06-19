<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title><s:text name="薪酬管理" /></title>
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
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
		</style>
	</head>
	
	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="个人空间" /></li>
				<li class="text_red"><s:text name="查看用户信息" /></li>			
			</ul>
			<div class="div_table">
				<s:form action="add" namespace="/salary" theme="simple" method="post">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="基本信息" /></td>
						</tr>
					</table>
					<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right"  bgcolor="#b3c6d9"><s:text name="固定工资" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="salary.fixedSalary" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="奖金" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="salary.bonus" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="税金" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="salary.taxes" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="补贴" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="salary.subsidies" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="salary.note" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="被审核人" />：</td>
							<td>
								<button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">请选择被审核人</button>
								<span id="personSelected" style="display:none;"></span>
								<input id = "personInfo" name = "personId" type = "hidden" class = "btn btn-data" />

							</td>
							
							
							
							
<!-- 							<td class="txtpadding" colspan="3">
								<s:textfield name="person.realName" /> "&nbsp"
								<input class = "input_css" type = "button" onclick = "selectObjects()" value = "选择" />
							</td> -->
						</tr>
					</table>
					<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="90"><input type="submit" class ="btn1" value="<s:text name='确定' />" /></td>
								<td><input type="button" class="btn1" value="<s:text name="取消" />" onclick="history.back();" /></td>
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
								
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="tool/dataTables/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.self.js"></script>
		<script type="text/javascript" src="javascript/oa/person/person_list.js"></script>
	</body>
</html>