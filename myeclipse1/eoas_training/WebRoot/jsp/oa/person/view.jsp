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
	</head>
	
	<body>
		<div class="title_bar">
			<ul>
				<li class="m"><s:text name="个人空间" /></li>
				<li class="text_red"><s:text name="查看用户信息" /></li>			
			</ul>
			<div class="div_table">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdbg1"><s:text name="基本信息" /></td>
						<td class="tdbg2"><s:a id = "modifyInfo" href='person/toModify.action?personId='>修改</s:a></td>
						<input type="hidden" name="personId" id = "personId" value="${person.id}" />
					</tr>
				</table>
				<table class="txtlineheight" width="100%" border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" bordercolor="#253d56">
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="真实姓名" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.realName" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="身份证号" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.idCardNumber" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="生日" />：</td>
						<td class="txtpadding" colspan="3"><s:date name="person.birthday" format="yyyy-MM-dd"/></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="性别" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.sex" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="政治面貌" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.membership" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="民族" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.ethnic" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="籍贯" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.birthplace" /></td>
					</tr>
					
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="备注" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.note" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="家庭地址" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.homeAddress" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="办公地址" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.officeAddress" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="邮箱" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.personEmail" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="手机号码" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.mobilePhone" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="办公电话" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.officePhone" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="QQ号码" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.qq" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="英文名" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.englishName" /></td>
					</tr>
					<tr>
						<td width="100" align="right" bgcolor="#b3c6d9"><s:text name="所属部门" />：</td>
						<td class="txtpadding" colspan="3"><s:property value="person.department.name" /></td>
					</tr>
				</table>
			</div>
		</div>
	</body>
	<script>
	var personId = document.getElementById("personId").value;
	document.getElementById("modifyInfo").href += personId;
	</script>
</html>