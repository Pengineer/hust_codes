<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

		<head>
			<title>修改密码</title>
			<s:include value="/outerBase.jsp" />
			<script type="text/javascript">
				$(document).ready(function() {
					$("#modifyPassword").validate({
						errorElement: "span",
						event: "blur",
						rules:{
							"origpassword":{required:true},
							"password":{required:true},
							"repassword":{required:true}
						},
						errorPlacement: function(error, element) {
							error.appendTo( element.parent().next() ); 
						}
					});
					$("#skip").bind("click", function() {
						window.location.href = basePath + "/login/toSelectAccount.action" ;
						return false;
					});
				});
			</script>
			<style type="text/css">
				.login_mod_pw {margin-top:15px; *margin-top:10px; _margin-top:10px; padding-left:0;}
				#errorInfo ul {margin:0;}
				#errorInfo ul li {margin:0; padding:0}
			</style>
		</head>

		<body>
			<div class="login_box">
				<s:include value="/outerHead.jsp" />
				<s:form id="modifyPassword" action="modifyPassword" namespace="/selfspace" theme="simple">
					<div class="login_input_box">
						<div class="login_input_area">
							<div class="login_find_pw_title">您已登录，为了保证账号的安全，请先修改密码。</div>
							<div class="login_find_pw_title1">您的真实密码被高强度单向加密保护，加密字串无法逆向破解，不会泄露。</div>
							<div class="login_find_pw_txt login_mod_pw">
								<table>
									<tr><td class="pw_title">原密码</td><td><s:password cssClass="login_input" name="origpassword" /></td><td class="pw_error"></td></tr>
									<tr><td class="pw_title">新密码</td><td><s:password cssClass="login_input" name="password" /></td><td class="pw_error"></td></tr>
									<tr><td class="pw_title">重复密码</td><td><s:password cssClass="login_input" name="repassword" /></td><td class="pw_error"></td></tr>
								</table>
							</div>
							<div id="errorInfo" style="margin-left:20px; line-height:22px; color:red;"><s:fielderror /></div>
							<div class="login_btn_box" style="margin-top:0; margin-left:50px;">
								<input id="submit" class="login_btn" type="submit" value="确定" />
								<input id="skip" class="login_btn" type="button" value="跳过此步" />
							</div>
						</div>
					</div>
				</s:form>
			</div>
			<s:include value="/outerFoot.jsp" />
		</body>
	
</html>