<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>访问超时</title>
		<s:include value="outerBase.jsp" />
		<script type="text/javascript">
			function checkParent() {
				if (parent.window.location != window.location){
					parent.window.location = window.location;
				}
			}
	    	checkParent();
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
			<s:include value="outerHead.jsp" />
			<div class="login_input_box">
				<div class="login_input_area">
					<div class="login_find_pw_title">访问超时</div>
					<div class="login_find_pw_txt">您的链接已经断开，请重新登录。</div>
					<div class="login_btn_box1"><input id="back" class="login_btn" type="button" value="返回首页" /></div>
				</div>
			</div>
		</div>
		<s:include value="outerFoot.jsp" />
	</body>
</html>