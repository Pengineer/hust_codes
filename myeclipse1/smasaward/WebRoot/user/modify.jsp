<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>基本信息</title>
			<s:include value="/innerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/user/user.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
		</head>

		<body>
			<div id="container">
				<div id="top">
					<table class="table_bar">
						<tr>
							<td>
								系统管理&nbsp;&gt;&gt;
								<s:if test="selflabel == 0">
									用户管理&nbsp;&gt;&gt;
									<s:if test="userstatus == 1">
										已启用用户&nbsp;&gt;&gt;
									</s:if>
									<s:elseif test="userstatus == -1">
										未启用用户&nbsp;&gt;&gt;
									</s:elseif>
									<s:elseif test="userstatus == 0">
										未审批用户&nbsp;&gt;&gt;
									</s:elseif>
								</s:if>
								<s:else>
									个人空间&nbsp;&gt;&gt;
								</s:else>
								<span class="text_red">修改个人信息</span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="bottom">
					<s:form action="modify" id="form_user_modifyUser" namespace="/user" enctype="multipart/form-data" theme="simple" method="post">
						<s:include value="/validateError.jsp" />
						<input type="hidden" name="selflabel" value="${selflabel}" />
						<input type="hidden" name="userstatus" value="${userstatus}" />
						<table class="table_center">
							<tr>
								<td class="td_right">
									<span class="table_title">中文名</span>
								</td>
								<td class="td_value">
									<div class="input0">
										<s:textfield name="user.chinesename"
											value="%{user.chinesename}" cssClass="input2" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;出生日期
								</td>
								<td class="td_value">
									<div class="input0">
<%--										<s:if test="#session.locale.equals(\"zh_CN\")">--%>
<%--											<input type="text" name="user.birthday" class="input2"--%>
<%--												id="datepick"--%>
<%--												value="<s:date name="user.birthday" format='yyyy-MM-dd' />" />--%>
<%--										</s:if>--%>
<%--										<s:elseif test="#session.locale.equals(\"en_US\")">--%>
<%--											<input type="text" name="user.birthday" class="input2"--%>
<%--												id="datepick"--%>
<%--												value="<s:date name="user.birthday" format='MM/dd/yyyy' />" />--%>
<%--										</s:elseif>--%>
										<input type="text" name="user.birthday" class="input2" id="datepick" value="<s:date name="user.birthday" format='yyyy-MM-dd' />" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;
									性别
								</td>
								<td class="td_value">
									<div class="input0">
										<s:select name="user.gender.id" value="%{user.gender.id}" headerKey=""
											headerValue="请选择" list="#application.gender" listKey="id"  listValue="name" cssClass="select" />
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="td_right">
									&nbsp;民族
								</td>
								<td class="td_value">
									<div class="input0">
										<s:select name="user.ethnic.id" value="%{user.ethnic.id}" headerKey="" headerValue="请选择"
											list="#application.ethnic" listKey="id" listValue="name" cssClass="select" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;手机
								</td>
								<td class="td_value">
									<div class="input0">
										<s:textfield name="user.mobilephone"
											value="%{user.mobilephone}" cssClass="input2" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									<span class="table_title">邮箱</span>
								</td>
								<td class="td_value">
									<div class="input0">
										<s:textfield name="user.email" value="%{user.email}" cssClass="input2" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_right">
									&nbsp;上传照片
								</td>
								<td class="td_value">
									<div class="input0">
										<s:file name="pic" cssClass="input2" />
									</div>
								</td>
							</tr>
						</table>
						
						<div class="btn_div">
							<input type="submit" class="btn" value="确定" />
							<input type="button" class="btn" value="取消" onclick="history.back();" />
						</div>
						
					</s:form>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/user_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>
