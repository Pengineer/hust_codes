function getUrlParam(name) { 
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) 
				return unescape(r[2]); 
			return null; 
		} 
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
	var label = getUrlParam('error');
	if (label != undefined && label != null && label != "") {
		$("#error-info").html(info[parseInt(label)]);
	}
}
//避免画中画
if (parent.window.location != window.location){
	parent.window.location = window.location;
}
$(document).ready(function() {	
	
	$("#login-form").submit(function(){
		if($("input[name='j_username']").val().length==0){
			$("#error-info").html("请输入用户名");
			return false;
		}else if( $("input[name='j_password']").val().length ==0){
			$("#error-info").html("请输入验证码");
			return false;
		}else{
			return true;
		}
	});
	s_login_error();
}); 