<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>您已登录，请选择前往的服务器</title>
	    <s:include value="/outerBase.jsp" />
	    <script type="text/javascript">
	    	$(document).ready(function() {
	    		$("#back").bind("click", function() {
	    			window.location.href = basePath + "logout";
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
					<div class="login_select_server_title">您已登录，请选择前往的服务器</div>
					<div class="login_select_server_txt"><p style="text-indent:2em;">服务器暂时无法访问，给您带来的不便敬请谅解。出现此现象的原因可能是因为服务器正在维护，或者此账号尚未授权。</p></div>
					<div class="login_btn_box2"><input id="back" class="login_btn" type="button" value="返回首页" /></div>
				</div>
			</div>
		</div>
		<s:include value="/outerFoot.jsp" />
	</body>
</html>