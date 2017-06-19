// ========================================================================
// 文件名：selfspaceValidate.js
//
// 文件说明：
//     本文件主要实现个人资料模块中的修改密码验证。
//
// 历史记录：
// 2010-03-11  周中坚     修改文件。
// ========================================================================

$(document).ready(function(){
	var tmp = document.getElementById("form_selfspace_username")
	$("#form_selfspace").validate({
		event: "blur",
		rules:{
			"opassword":{required:true, isPassWord:true},
			"password":{required:true, isPassWord:true},
			"repassword":{required:true, isPassWord:true, equalTo:"#form_selfspace_password"}//此处要取id			
			}
	});
});
