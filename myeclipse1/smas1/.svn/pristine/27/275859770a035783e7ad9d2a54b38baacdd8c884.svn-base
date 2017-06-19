<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<s:head/>
			<title>修改权限信息</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/roleright/roleright.css" />
		</head>
		
		<body>
			<div id="top">
				系统管理&nbsp;&gt;&gt;&nbsp;角色权限&nbsp;&gt;&gt;&nbsp;权限列表&nbsp;&gt;&gt;&nbsp;<span class="text_red">修改权限信息</span>
			</div>
				
			<div id="mid"></div>

			<s:form id="form_right" action="modify" namespace="/right" theme="simple">
				<div class="main">
					<div class="main_content">
						<s:include value="/validateError.jsp" />
						<table class="table_edit" cellspacing="0" cellpadding="0">
							<tr><th class="thhead" colspan="2">基本信息</th></tr>
							<tr>
								<td class="wd12">权限名称</td>
								<td class="wd13"><s:textfield name="right.name" value="%{right.name}" cssClass="input" size="40" /></td>
							</tr>
							<tr>
								<td class="wd12">权限描述</td>
								<td class="wd13"><s:textfield name="right.description" value="%{right.description}" cssClass="input" size="40" /></td>
							</tr>
							<tr><th class="thhead" colspan="2">权限关联</th></tr>
							<table id="groupaction" class="table_add" cellspacing="0" cellpadding="0">
								<tr>
									<th class="wd14">序号</th>
									<th class="wd15">Action名称</th>
									<th class="wd16">Action描述</th>
									<th class="wd14" onclick="addaction();"><img src="image/add_item.gif" /></th>
								</tr>
								<s:iterator value="pageList" status="index">
								<tr>
									<td class="wd14"><s:property value="#index.index+1" /></td>
									<td class="wd15"><s:textfield name="actionUrlArray" value="%{actionurl}" cssClass="input1" /></td>
									<td class="wd16"><s:textfield name="actionDesArray" value="%{description}" cssClass="input1" /></td>
									<td class="wd14"><img src="image/del_item.gif" onclick="delaction(this);" /></td>
								</tr>
								</s:iterator>
							</table>
						</table>
					</div>
					<s:include value="/submit.jsp" />
				</div>
			</s:form>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/common.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/security/right/edit.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/security/right/validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>