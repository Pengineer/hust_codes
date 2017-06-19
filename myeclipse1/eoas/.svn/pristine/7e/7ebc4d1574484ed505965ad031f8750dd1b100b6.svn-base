$(document).ready(function() {


	//渲染Tab事件，点击不同的标签显示不同的对应标签页并修改对应的样式
	$(".entry-form ul li").click(function(){

		//将所有同辈元素的li背景设浅灰色，下地边界设无；点击的li背景设白，下底框设白；
		$(this).siblings().css("background-color","#F7F7F7");
		$(this).siblings().css("border-bottom","none");
		$(this).css("background-color","#FFF");
		$(this).css("border-bottom","1px solid #FFF");

		//将所有的tab div设置为不可见，然后把当前标签页对应的DIV设置为可见
		$(".tab-div").css("display","none");
		if ($(this).attr("class") == "default-form") {
			$("#login-div").css("display","block");
		}else if ($(this).attr("class") == "register"){
			$("#register-div").css("display","block");
		}else if($(this).attr("class") == "findpass"){
			$("#find-password").css("display","block");
		}

	});
	
	
	/*
	 * 登录前台校验
	*/
	$("#login-form").validate({
		rules: {
			"account.email": {required: true,email: true},
			"account.password": {required: true,minlength: 6},
			"account.validCode": "required"
		},
		messages:{
			"account.email":{required: "邮箱不能为空",email: "请输入正确邮箱地址"},
			"account.password": {required: "密码不能为空",minlength: "密码长度不得小于6位"},
			"validCode":"请输入验证码"
		},
		errorPlacement: function(error, element) { 
			error.appendTo( element.parent("div") ); 
		}
	});
	
	/*
	 * 注册前台校验
	*/
	$("#register-form").validate({
		rules:{
			"account.accountType": "required",
			"account.email": {required: true,email: true},
			"account.password": {required: true,minlength: 6},
			"account.repassword": {required: true,minlength: 6,equalTo:"#password"}
		},
		messages:{
			"account.accountType": "请选择招聘类型",
			"account.email": {required: "请输入注册邮箱",email: "请输入正确邮箱地址"},
			"account.password": {required: "请输入密码",minlength: "密码长度不得小于6位"},
			"account.repassword": {required: "请重新输入密码",minlength: "密码长度不得小于6位",equalTo:"密码重复错误"}
		},
		errorPlacement: function(error, element) { 
			error.appendTo( element.parent("div") ); 
		}
	});
	
	/*
	 * 找回密码前台校验
	*/
	$("#findpass-form").validate({
		rules: {
			email: {required: true,email: true},
			reemail: {required: true,email: true,equalTo:"#email"},
			validCode: "required"
		},
		messages:{
			email:{required: "邮箱不能为空",email: "请输入正确邮箱地址"},
			reemail: {required: "密码不能为空",email: "请输入正确邮箱地址",equalTo: "邮箱重复错误"},
			validCode:"请输入验证码"
		},
		errorPlacement: function(error, element) { 
			error.appendTo( element.parent("div") ); 
		}
	});
	
}); 