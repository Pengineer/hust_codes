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
		<title><s:text name="个人信息" /></title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="tool/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="tool/jquery.datepick.package-4.0.5/flora.datepick.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="tool/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
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
				<s:form action="add" namespace="/person" theme="simple" method="post">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tdbg1"><s:text name="基本信息" /></td>
						</tr>
					</table>
					<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
						<tr>
							<td width="100" align="right"  bgcolor="#b3c6d9"><s:text name="真实姓名" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.realName" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="身份证号" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.idCardNumber" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="生日" />：</td>
							<td class="txtpadding" colspan="3"><input type="text" name="person.birthday" id="personBirthday" 
							value="<s:date name="person.birthday" format="yyyy-MM-dd" />" format="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="性别" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.sex" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="政治面貌" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.membership" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="民族" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.ethnic" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="籍贯" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.birthplace" /></td>
						</tr>
						
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.note" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="家庭地址" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.homeAddress" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="办公地址" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.officeAddress" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="邮箱" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.personEmail" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="手机号码" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.mobilePhone" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="办公电话" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.officePhone" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="QQ号码" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.qq" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="英文名" />：</td>
							<td class="txtpadding" colspan="3"><s:textfield name="person.englishName" /></td>
						</tr>
						<tr>
							<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="部门" />： </td>
							<td>
								<button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">请选择所属部门</button>
								<span id="depSelected" style="display:none;"></span>
								<input id = "departmentInfo" type = "hidden" class = "btn btn-data" />
								<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									<div class="modal-dialog modal-lg">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
												<h4 class="modal-title">部门</h4>
											</div>
											<div class="modal-body">
												<ul id="depTree" class="ztree"></ul>
											</div>
											<div class="modal-footer">
												<button id="save" type="button" class="btn btn-default" data-dismiss="modal">Save</button>
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>

					<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="90"><input type="submit" class ="btn1" value="<s:text name='确定' />" /></td>
								<td><input type="button" class="btn1" value="<s:text name="取消" />" onclick="history.back();" /></td>
							</tr>
					</table>
				</s:form>
			</div>
		</div>
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick.js"></script>
		<script type="text/javascript" src="tool/jquery.datepick.package-4.0.5/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.datepick.self.js"></script>
		<script type="text/javascript" src="tool/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="tool/zTree/js/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="javascript/oa/department/department.js"></script>
	</body>
</html>