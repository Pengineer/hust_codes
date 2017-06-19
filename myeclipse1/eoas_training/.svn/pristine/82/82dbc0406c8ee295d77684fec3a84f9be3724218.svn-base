// ========================================================================
// 文件名：account_validate.js
//
// 文件说明：
//     本文件主要实现帐号管理模块中页面的输入验证，包括注册三步、修改用户信息、
// 高级检索和找回密码。
//
// ========================================================================

$(document).ready(function(){	
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
	
	/*
	 * 修改密码前台校验
	*/
	$("#modifypass-form").validate({
		rules: {
			"opassword":{required: true,minlength: 6},
			"password":{required: true,minlength: 6},
			"repassword":{required: true,minlength: 6,equalTo:"#password"}
		},
		messages: {
			"opassword": {required: "请输入当前密码",email: "请输入当前正确的密码"},
			"password": {required: "请输入新密码",minlength: "密码长度不得小于6位"},
			"repassword": {required: "请重新输入密码",minlength: "密码长度不得小于6位",equalTo:"密码重复错误"}
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.parent("div"));
		}
	})
	
	
	
});