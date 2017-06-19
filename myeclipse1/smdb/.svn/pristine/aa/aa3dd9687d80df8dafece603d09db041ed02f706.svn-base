<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
		<head>
			<title>找回密码</title>
			<s:include value="/outerBase.jsp" />
			<script type="text/javascript">
				$(document).ready(function() {
					$("#back").bind("click", function() {
						window.location.href = basePath + "toIndex.action";
						return false;
					});
				});
			</script>
		</head>

		<body>
			<div class="login_box">
				<s:include value="/outerHead.jsp" />
				<div class="login_input_box">
					<div class="login_input_area">
						<div class="login_find_pw_title">找回密码</div>
						<div class="login_find_pw_txt">密码重置链接已经发送到该账号对应的邮箱，请登入该邮箱点击其中的链接重新设置密码。</div>
						<div class="login_btn_box2"><input id="back" class="login_btn" type="button" value="返回首页" /></div>
					</div>
					<s:include value="/messageIco.jsp" />
				</div>
			</div>
			<s:include value="/outerFoot.jsp" />
		</body>

</html>