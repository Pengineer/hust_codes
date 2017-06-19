function s_login_error () {
	var info = [
			"登录失败，原因不明",
			"请输入用户名",
			"请输入密码",
			"请输入验证码",
			"验证码错误",
			"用户名或者密码错误",
			"此账号邮箱尚未验证",
			"此账号尚未通过审批",
			"此账号已停用",
			"只支持POST方式登录",
			"您尚未登录，请先登录"
			];
		var label = $("#login_error").val();
		if (label != undefined && label != null && label != "") {
			var template = $("#err_info_template").text().replace(/\$message/g, info[parseInt(label)])
			$("body").prepend($(template));
		}
	}
$(function(){
	s_login_error();
	$("body").on("click",".close", function(){
		$(this).parent().fadeOut().remove();
	})
});