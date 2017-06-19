<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>重置密码</title>
			<s:include value="/outerBase.jsp" />
			<script type="text/javascript">
				$(document).ready(function() {
					$("#resetPassword").validate({
						errorElement: "span",
						event: "blur",
						rules:{
							"password":{required:true},
							"repassword":{required:true}
						},
						errorPlacement: function(error, element) {
							error.appendTo( element.parent().parent() ); 
						}
					});
					$("#submit").bind("click", function() {
						$("#resetPassword").submit();
					});
					$("#cancel").bind("click", function() {
						window.location.href = basePath + "toIndex.action";
						return false;
					});
				});
			</script>
		</head>

		<body>
			<div class="login_box">
				<s:include value="/outerHead.jsp" />
				<s:form id="resetPassword" action="resetPassword" namespace="/selfspace" theme="simple">
					<s:hidden name="pwRetrieveCode" />
					<div class="login_input_box">
						<div class="login_input_area">
							<div class="login_find_pw_title">重置密码</div>
							<div class="login_find_pw_txt login_reset_pw">
								<ul>
									<li><span class="pw_title">新密码</span><span><s:password cssClass="login_input" name="password" /></span></li>
									<li><span class="pw_title">重复密码</span><span><s:password cssClass="login_input" name="repassword" /></span></li>
								</ul>
							</div>
							<div id="errorInfo" style="margin-left:85px; height:22px; line-height:22px; color:red;"><s:property value="#request.errorInfo" /></div>
							<div class="login_btn_box1" style="margin-top:0;"><input id="submit" class="login_btn" type="submit" value="确定" /><input id="cancel" class="login_btn" type="button" value="返回" /></div>
						</div>
						<s:include value="/messageIco.jsp" />
					</div>
				</s:form>
			</div>
			<s:include value="/outerFoot.jsp" />
		</body>
	
</html>