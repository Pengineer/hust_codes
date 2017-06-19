// ========================================================================
// 文件名：account_validate.js
//
// 文件说明：
//     本文件主要实现帐号管理模块中页面的输入验证，包括注册三步、修改用户信息、
// 高级检索和找回密码。
//
// ========================================================================

$(document).ready(function(){	
	$("#form_account").validate({
		errorElement: "label",
		event: "blur",
		rules:{
			"account.accountType":{required:true,  isSelected:true }},			
			"account.password":{required:true, rangelength:[6,16], isPassWord:true},
			"account.repassword":{required:true, rangelength:[6,16], isPassWord:true, equalTo:"#password"},
			"account.email":{required:true, email:true, 
				remote:{url: "account/checkAccount.action", type: "post", dataType: "json"}},			
		}
	});
});