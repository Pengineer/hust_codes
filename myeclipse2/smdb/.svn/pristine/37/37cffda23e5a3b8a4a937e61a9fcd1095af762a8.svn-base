// ========================================================================
// 文件名：validate.js
//
// 文件说明：
//     本文件主要实现个人空间模块中页面的输入验证，
// 和修改密码。
//
// 历史记录：
// 2011-1-28  周中坚     创建文件。
// ========================================================================

$(document).ready(function(){
	$("#modify_account").validate({
		errorElement: "span",
		event: "blur",
		rules:{
			accountname:{required:true, rangelength:[3,40], userName:true},
			origpassword:{required:true, passWord:true},
			password:{required:true, passWord:true},
			repassword:{required:true, passWord:true, equalTo:"#modify_account_password"}
			},
		// 定义验证信息的显示位置
		errorPlacement: function(error, element) { 
			error.appendTo( element.parent("td").next("td") ); 
		}
	});
	$("#modifyPassword").validate({
		errorElement: "div",
		event: "blur",
		rules:{
			origpassword:{required:true, passWord:true},
			password:{required:true, passWord:true},
			repassword:{required:true, passWord:true, equalTo:"#modifyPassword_password"}
			},
		// 定义验证信息的显示位置
		errorPlacement: function(error, element) { 
			$("#errorInfo").append(error);
		}
	});
});