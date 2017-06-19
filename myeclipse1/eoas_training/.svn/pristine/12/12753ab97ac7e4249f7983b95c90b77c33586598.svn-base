<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>" />
	<title>人员设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="script/jquery.js"></script>
	<script type="text/javascript" src="script/pageCommon.js" charset="utf-8"></script>
	<script type="text/javascript" src="script/PageUtils.js" charset="utf-8"></script>
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
				职员账号分配设置
			</div>
			<div id="Title_End"></div>
		</div>
	</div>

	<!--显示表单内容-->
	<div id="MainArea">

		<s:form action="staffAction_add">
			<div class="ItemBlock_Title1">
				<!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="style/blue/images/item_point.gif" /> 岗位信息 </DIV>  -->
			</div>
			<div class="ItemBlockBorder">
			<h3>人员信息</h3>
			<hr>
				<tr>
					<td width="100px">职员姓名:</td>
					<td> <s:property value="realname"/></td></br>
					<td>注册邮箱:</td>
					<td> <s:property value="personemail"/></td>
					<s:hidden name="id" ></s:hidden>
				</tr>
			</div>

			<!-- Staff内容显示 -->
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table cellpadding="0" cellspacing="0" class="mainForm">
						<tr>
							<td width="100">分配账号</td>
							<td><s:textfield name="email" cssClass="InputStyle"/>
							</td>
							<td width="100">初始密码</td>
							<td><s:textfield name="idcardnumber" value="123456" cssClass="InputStyle" />
							</td>
						</tr>
						<tr>
							<td width="100" >员工编号</td>
							<td><s:textfield name="staffnum" cssClass="InputStyle" />
							</td>
							<td width="100">工资卡号</td>
							<td><s:textfield name="banknum" cssClass="InputStyle" />
							</td>
						</tr>
						<tr>
							<td width="100" >入职时间</td>
							<td><s:textfield name="intime" cssClass="InputStyle" />
							</td>
							<td width="100">人员类型</td>
							<td><s:textfield name="accounttype" cssClass="InputStyle" />
							</td>
						</tr>
						
						
					</table>
				</div>
			</div>

			<!-- 表单操作 -->
			<div id="InputDetailBar">
				<input type="image"	src="style/images/save.png" /> 
				<a href="javascript:history.go(-1);">
					<img src="style/images/goBack.png" />
				</a>

			</div>
		</s:form>
	</div>
	<s:debug></s:debug>
</body>
</html>
