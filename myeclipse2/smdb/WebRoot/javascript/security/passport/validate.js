
$(document).ready(function(){
	$("#modify_account").validate({
		errorElement: "span",
		event: "blur",
		rules:{
			accountname:{required:true, rangelength:[3,40], userName:true},
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
			password:{required:true, passWord:true},
			repassword:{required:true, passWord:true, equalTo:"#modifyPassword_password"}
			},
		// 定义验证信息的显示位置
		errorPlacement: function(error, element) { 
			$("#errorInfo").append(error);
		}
	});
});