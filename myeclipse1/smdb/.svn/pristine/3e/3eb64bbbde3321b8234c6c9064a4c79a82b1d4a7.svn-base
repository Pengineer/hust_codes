<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="FCK" uri="http://java.fckeditor.net"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>中国高校社会科学管理数据库</title>
	    <s:include value="/outerBase.jsp" />
	    <script type="text/javascript">
			function s_login_error () {
				var info = [
					"登录失败，原因不明",
					"请输入用户名",
					"请输入密码",
					"请输入验证码",
					"验证码错误",
					"用户名或者密码错误",
					"此用户已过期",
					"此用户无法使用当前IP登录",
					"此用户已达到同时最大使用数",
					"只支持POST方式登录",
					"您尚未登录，请先登录"
					];
				var label = $("#login_error").val();
				if (label != undefined && label != null && label != "") {
					$("#errorInfo").html(info[parseInt(label)]);
				}
			}
			//避免画中画
			if (parent.window.location != window.location){
				parent.window.location = window.location;
			}
			$(document).ready(function() {
				$("#form_login").validate({
					errorElement: "span",
					event: "blur",
					rules:{
						"j_username":{required:true},
						"j_password":{required:true},
						"j_code":{required:true}
					},
					errorPlacement: function(error, element) {
						error.appendTo( element.parent().parent() ); 
					}
				});
				s_login_error();
				
			});
		</script>
	</head>

	<body>
		<div class="login_box">
			<s:include value="/outerHead.jsp" />
			<form id="form_login" name="login" action="login" method="post">
				<div class="login_input_box">
					<div class="login_input_area">
						<ul>
							<li><img src="image/input_ico1.gif" /><span>用户名</span><span><input class="login_input" name="j_username" type="text" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" /></span></li>
							<li><img src="image/input_ico2.gif" /><span>密&nbsp;&nbsp;&nbsp;码</span><span><input class="login_input" name="j_password" type="password" /></span></li>
							<li><img src="image/input_ico3.gif" /><span>验证码</span><span><input class="login_input" style="width:105px;" name="j_code" type="text" /></span><span style="cursor:pointer;"><img style="margin:0; width:60px; height:20px;" align="absmiddle" src="rand.action" title="点击刷新" onclick="this.src='rand.action?time='+Math.random();" /></span></li>
						</ul>
						<div id="errorInfo" style="margin-left:70px; height:22px; line-height:22px; color:red;"></div>
						<div class="login_btn_box" style="margin-top:0;">
							<input class="login_btn" type="submit" value="登&nbsp;&nbsp;&nbsp;录" />
							<input class="login_btn" type="button" value="忘记密码" onclick="window.location.href=basePath+'selfspace/toRetrievePassword.action'" />
							<s:if test="#application.teacherRegister == 1"><input class="login_btn" type="button" value="教师注册" onclick="window.location.href=basePath+'account/teacher/toRegister.action'" /></s:if>
						</div>
					</div>
					<s:include value="/messageIco.jsp" />
				</div>
			</form>
		</div>
		<s:include value="/outerFoot.jsp" />
		<input id="login_error" type="hidden" name="login_error" value="${param.error}" />
	</body>
</html>