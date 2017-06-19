<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN">
<html>
	<head>
	    <base href="<%=basePath%>" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<s:include value="/jsp/innerBase.jsp" />
		<link href="css/login.css" type="text/css" rel="stylesheet" />
	</head>
	
	<body leftmargin=0 topmargin=0 marginwidth=0 marginheight=0 class=PageBody>
	<input id="login_error" type="hidden" name="login_error" value="${param.error}" />
		<div class="sign-in-up__wrapper">
		   <div class="container">
		      <div class="row">
		         <div class="col-xs-12">
		            <h4 class="heading sign-in-up__heading">登陆到EOAS</h4>
		            <p class="heading__sub sign-in-up-heading__sub"></p>
		            <form class="form_alt sign-in-up__form" role="form" method="post" action="account/login.action">
		               <div class="form-group">
		                  <label for="sign-in__username" class="sr-only">用户名</label>
		                  <input type="text" name="j_username" class="form-control" id="sign-in_username" placeholder="用户名">
		               </div>
		               <div class="form-group">
		                  <label for="sign-in__password" class="sr-only">密码</label>
		                  <input type="password" name="j_password" class="form-control" id="sign-in_password" placeholder="密码">
		               </div>
		                <div class="form-group row">
		                <div class="col-xs-8">
		                	<label for="sign-in__password" class="sr-only">密码</label>
		                  	<input type="text" name="rand" class="form-control" id="sign-in_rand" placeholder="验证码">
		                </div>
	                  	<div class="col-xs-4" style="padding-left:0">
		                	<img style="margin-top:2px;padding-left:2px;" id="rand" src="account/rand.action" title="<s:text name='点击刷新' />
										"onclick="this.src='account/rand.action?time='+Math.random();">
	                	</div>
		               </div>
		               <button type="submit" class="btn btn-block btn-primary">登陆</button>
		            </form>
		
		            <!-- Sign Up link -->
		            <div class="sign-in-up__footer">
		               没有注册? <a href="/signup">立即注册</a>
		            </div>
		
		         </div>
		      </div>
		   </div> <!-- / .container -->
		</div> <!-- / .sign-in-up__wrapper -->
		<textarea id="err_info_template" style="display:none">
			<div class="alert alert-dismissible alert-custom" role="alert">
   			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
   			<i class="fa fa-exclamation-circle fa-3" style="color:#d44950"></i> <strong style="color:#d44950">$message</strong>
			</div>
		</textarea>
		<script type="text/javascript" src="javascript/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="javascript/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="javascript/oa/account/login.js"></script>
	</body>
</html>