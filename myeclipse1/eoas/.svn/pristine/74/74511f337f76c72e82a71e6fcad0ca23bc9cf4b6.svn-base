<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>" />
		<title>公司招聘</title>
		<link rel="stylesheet" type="text/css" href="css/recruitment/login.css">
	</head>
	<body>
		<div class="nav">
			<ul>
				<li class="ent-logo"><a href="">EOAS</a></li>
				<li class="social-link"><a href="javascript:void(0);">社会招聘</a></li>
				<li class="campus-link"><a href="javascript:void(0);">校园招聘</a></li>
				<li class="intern-link"><a href="javascript:void(0);">实习生招聘</a></li>
			</ul>
		</div>
		<div class="entry-form">
			<ul>
				<li class="default-form"><a href="" onclick="javascript:return false;">登录</a></li>
				<li class="register"><a href="" onclick="javascript:return false;">注册</a></li>
				<li class="findpass"><a href="" onclick="javascript:return false;">找回密码</a></li>
			</ul>
			<div>
				<s:property value="#request.tip" />
				<s:fielderror />
			</div>
			<div id="login-div" class="tab-div">
				<form action="account/login.action" id="login-form" class="login">
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							邮箱地址：
						</label>
						<input type="text" name="account.email" class="input1">
					</div>
					<div>
						<label class="label1">
						 	<span class="red-alarm">*</span>
						 	登录密码：
						</label>
						<input type="password" name="account.password" class="input1">
					</div>
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							验证码：
						</label>
						<input type="text" name="validCode" class="input2">
						<!-- <img src="image/rand.jpg" height="20px" width="60px" align="middle" class="valid-code"> -->
						<img style="margin-top:2px;" id="rand" src="account/rand.action" title="<s:text name='点击刷新' />" onclick="this.src='account/rand.action?time='+Math.random();">

					</div>
					<div>
						<label class="label2">
							<input type="checkbox" value="true">
							记住密码
						</label>
						<a href="">忘记密码</a>
					</div>
					<div>
						<input type="submit" class="button1" value="登录">
					</div>
				</form>
			</div>
			<div id="register-div" class="tab-div">
				<form action="account/register.action" id="register-form" class="accountAction" >
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							用户类型：
						</label>
						<label class="label3">
							<input type="radio" value="socialRecruitment" name="account.accountType">
							社会招聘
						</label>
						<label class="label3">
							<input type="radio" value="campusRecruitment" name="account.accountType">
							校园招聘
						</label>
						<label class="label3">
							<input type="radio" value="internRecruitment" name="account.accountType">
							实习生招聘
						</label>
					</div>
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							电子邮箱：
						</label>
						<input type="text" name="account.email" class="input1">
					</div>
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							登录密码：
						</label>
						<input type="password" id="password" name="account.password" class="input1">
					</div>
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							确认密码：
						</label>
						<input type="password" name="account.repassword" class="input1">
					</div>
					<div>
						<input type="submit" class="button1" value="注册">
					</div>
				</form>
			</div>
			<div id="find-password" class="tab-div">
				<form id="findpass-form">
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							电子邮箱：
						</label>
						<input type="text" name="email" id="email" class="input1">
					</div>
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							重复邮箱：
						</label>
						<input type="text" name="reemail" class="input1">
					</div>
					<div>
						<label class="label1">
							<span class="red-alarm">*</span>
							验证码：
						</label>
						<input type="text" name="validCode" class="input2">
						<!-- <img src="image/rand.jpg" height="20px" width="60px" align="middle" class="valid-code"> -->
						<img style="margin-top:2px;" id="rand" src="account/rand.action" title="<s:text name='点击刷新' />" onclick="this.src='account/rand.action?time='+Math.random();">
					</div>
					<div>
						<input type="submit" class="button1" value="登录">
					</div>
				</form>
			</div>
		</div>
		<div class="footer">
			©2014 公司|<a href="">关于公司</a>|<a href="">合作伙伴</a>
		</div>
		<script type="text/javascript" src="javascript/recruitment/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="tool/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/recruitment/account/login.js"></script>
		<!-- <script type="text/javascript" src="javascript/account/account_validate.js"></script> -->
	</body>
</html>
