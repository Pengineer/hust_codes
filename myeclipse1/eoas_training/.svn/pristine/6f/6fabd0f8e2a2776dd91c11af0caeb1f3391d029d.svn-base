<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>人员设置</title>
	<base href="<%=basePath%>" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
</head>
<body>

	<!-- 标题显示 -->
	<div id="Title_bar">
		<div id="Title_bar_Head">
			<div id="Title_Head"></div>
			<div id="Title">
				<!--页面标题-->
				<img border="0" width="13" height="13"
					src="style/images/title_arrow.gif" />
				人员设置
			</div>
			<div id="Title_End"></div>
		</div>
	</div>

	<!--显示表单内容-->
	<div id="MainArea">

		<s:form action="personAction_modify">
			<div class="ItemBlock_Title1">
				<!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="style/blue/images/item_point.gif" /> 岗位信息 </DIV>  -->
			</div>
			<s:hidden name="id"></s:hidden>
			<!-- 表单内容显示 -->
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table cellpadding="0" cellspacing="0" class="mainForm">
						<tr>
							<td width="100">注册邮箱</td>
							<td><s:textfield name="personEmail" cssClass="InputStyle" />
							</td>
							<td width="100">真实姓名</td>
							<td><s:textfield name="realName" cssClass="InputStyle" />
							</td>

						</tr>
						<tr>
							<td width="100">英文名</td>
							<td><s:textfield name="englishName" cssClass="InputStyle" />
							</td>
							<td width="100">身份证号</td>
							<td><s:textfield name="idCardNumber" cssClass="InputStyle" />
							</td>
						</tr>
						<tr>
							<td width="100">性别</td>
							<td><s:textfield name="sex" cssClass="InputStyle"/></td>
							<td width="100">组织关系</td>
							<td><s:textfield name="membership" cssClass="InputStyle" /></td>
						</tr>
						<tr>
							<td width="100">民族</td>
							<td><s:textfield name="ethnic" cssClass="InputStyle" /></td>
							<td width="100">籍贯</td>
							<td><s:textfield name="birthplace" cssClass="InputStyle" /></td>
						</tr>
						<tr>
							<td width="100">家庭住址</td>
							<td><s:textfield name="homeAddress" cssClass="InputStyle" /></td>
							<td width="100">办公地点</td>
							<td><s:textfield name="officeAddress" cssClass="InputStyle" /></td>
						</tr>
						<tr>
							<td width="100">办公电话</td>
							<td><s:textfield name="officePhone" cssClass="InputStyle" /></td>
							<td width="100">手机号码</td>
							<td><s:textfield name="mobilePhone" cssClass="InputStyle" /></td>
						</tr>
						<tr>
							<td width="100">qq号码</td>
							<td><s:textfield name="qq" cssClass="InputStyle" /></td>
							<td width="100">出生日期</td>
							<td><s:textfield name="birthday" cssClass="InputStyle" /></td>
						</tr>
						<tr>
							<td width="100">人员状态</td>
							<td><s:textfield name="status" cssClass="InputStyle" /></td>
						</tr>
						<tr>
							<td width="100">照片上传</td>
							<td><s:textfield name="photoFile" cssClass="InputStyle" /></td>
						</tr>
						
						<tr>
							<td>备注说明</td>
							<td><s:textarea name="note" cssClass="TextareaStyle"></s:textarea>
							</td>
							<td>照片</td>
							<td><s:textarea name="photoName"></s:textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<!-- 表单操作 -->
			<div id="InputDetailBar">
				<input type="image"	src="style/images/save.png" /> 
				<a href="javascript:history.go(-1);"> <img src="style/images/goBack.png" />
				</a>

			</div>
		</s:form>
	</div>
	<s:debug></s:debug>
</body>
</html>
