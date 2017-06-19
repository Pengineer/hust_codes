<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>注册</title>
			<s:include value="/outerBase.jsp" />
			<link rel="stylesheet" type="text/css" href="css/flora.datepick.css" />
			<link rel="stylesheet" type="text/css" href="css/user/register.css" />
			<link rel="stylesheet" type="text/css" href="css/user/validate.css" />
		</head>

		<body>
			<div class="container">
				<div class="head">
					<p>社科管理咨询服务系统</p> 
					<div class="menu">
						<a href="main/toIndex.action">首页</a>
					</div>
				</div>
				<div class="main">
					<div class="steplogo"></div>
					<s:include value="/validateError.jsp" />
					<div class="center">
						<div class="register_txt">注册</div>
						<div class="register_form">
							<s:form action="register" id="form_user" namespace="/user"
							enctype="multipart/form-data" theme="simple" method="post">
								<table class="table_center">
									<tr>
										<td class="td_right">
											<span class="text_red">*</span>&nbsp;
											账号
										</td>
										<td class="td_value">
											<div class="input0">
												<s:textfield name="user.username" id="username" cssClass="input2" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											<span class="text_red">*</span>&nbsp;
											密码
										</td>
										<td class="td_value">
											<div class="input0">
												<s:password name="user.password" id="password" cssClass="input2" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											<span class="text_red">*</span>&nbsp;
											重复密码
										</td>
										<td class="td_value">
											<div class="input0">
												<s:password name="repassword" id="repassword" cssClass="input2" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											<span class="text_red">*</span>&nbsp;
											中文名
										</td>
										<td class="td_value">
											<div class="input0">
												<s:textfield name="user.chinesename" cssClass="input2" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											&nbsp;出生日期
										</td>
										<td class="td_value">
											<div class="input0">
												<s:textfield name="user.birthday" id="birthday" cssClass="input2" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											&nbsp;
											性别
										</td>
										<!-- 必选:headerKey="-1", 选填:headerKey="" by zhouzj -->
										<td class="td_value">
											<div class="input0">
												<s:select name="user.gender.id" headerKey="" headerValue="请选择"
													list="#application.gender" listKey="id" listValue="name" cssClass="select" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											&nbsp;民族
										</td>
										<td class="td_value">
											<div class="input0">
												<s:select name="user.ethnic.id" headerKey="" headerValue="请选择" cssClass="select"
													list="#application.ethnic" listKey="id" listValue="name" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											&nbsp;手机
										</td>
										<td class="td_value">
											<div class="input0">
												<s:textfield name="user.mobilephone" cssClass="input2" />
											</div>
										</td>
									</tr>
									<tr>
										<td class="td_right">
											<span class="text_red">*</span>&nbsp;
											邮箱
										</td>
										<td class="td_value">
											<div class="input0">
												<s:textfield name="user.email" cssClass="input2" />
											</div>
										</td>
										<td class="td_tip"><!-- 显示后台验证信息 -->
											<s:property value="mailtip" />
										</td>
									</tr>
									<tr>
										<td class="td_right">
											&nbsp;上传照片
										</td>
										<td class="td_value">
											<div class="input0">
												<s:file name="pic" cssClass="input1" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
										</td>
										<td class="th_button th_center">
											<input type="submit" value="完成" class="btn" />
											<input type="reset" value="重填" class="btn" />
										</td>
									</tr>
								</table>
							</s:form>
						</div>
						
					</div>
				</div>
				<div class="login_footer">
					<div class="footer_r_txt">制作单位：中国高校社会科学数据中心
						<br/>Copyright&copy; 2009-2013 All Rights Reserved.&nbsp; 系统版本：1.0.<%=application.getAttribute("systemVersion")%>					
					</div>
				</div>
			</div>
			<script type="text/javascript" src="javascript/jquery/jquery.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/jquery/jquery.datepick.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<script type="text/javascript" src="javascript/user/user_validate.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			<s:if test="#session.locale.equals(\"zh_CN\")">
				<script type="text/javascript" src="javascript/jquery/jquery.datepick.zh_cn.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
			</s:if>
			<script type="text/javascript" src="javascript/jquery/datepick.mine.js?ver=<%=application.getAttribute("systemVersion")%>"></script>
		</body>
</html>