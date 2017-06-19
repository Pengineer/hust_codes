// ========================================================================
// 文件名：user_validate.js
//
// 文件说明：
//     本文件主要实现用户管理模块中页面的输入验证，包括注册三步、修改用户信息、
// 高级检索和找回密码。
//
// 历史记录：
// 2010-01-28  周中坚     创建文件，完成基本功能。
// 2010-02-24  周中坚     整合文件，添加注释。
// ========================================================================

$(document).ready(function(){
	
	$("#form_user").validate({
		errorElement: "div",
		event: "blur",
		rules:{
			//user
			"user.username":{required:true, rangelength:[2,20], isUserName:true, 
				remote:{url: "user/checkUsername.action", type: "post", dataType: "json"}},			
			"user.password":{required:true, rangelength:[6,16], isPassWord:true},
			"repassword":{required:true, rangelength:[6,16], isPassWord:true, equalTo:"#password"},
			"user.chinesename":{required:true,  isCnName:true},
			"user.gender.id":{},//通过headerKey使它匹配
			"user.birthday":{dateISO:true},
			"user.ethnic.id":{isSelected:true},
			"user.mobilephone":{isCellPhone:true},
			"user.email":{required:true, email:true}		
			},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		}
	});
	$("#form_login").validate({
		event: "blur",
		rules:{
			//user
			"username":{required:true},			
			"password":{required:true},
			"rand":{required:true}
			},
		errorPlacement: function(error, element) { 
			error.appendTo( element.parent("td").next("td") ); 
		},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		}
	});
	//修改和注册不同（username和email）另写验证方式。
	$("#form_user_modifyUser").validate({
		errorElement: "div",
		event: "blur",
		rules:{
			//user
			"user.chinesename":{required:true,  isCnName:true},
			"user.gender.id":{isSelected: true},//通过headerKey使它匹配
			"user.birthday":{dateISO:true},
			"user.ethnic.id":{isSelected:true},
			"user.mobilephone":{isCellPhone:true},
			"user.email":{required:true, email:true}			
			},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		}
	});
	
	//使用label标签显示消息的验证
	//jquery.validation引用name属性；jquery.datepick引用id属性。
	$("#form_user_label").validate({
		event: "submit",
		rules:{
			//keyword:{maxlength:10},暂时不需要
			//gender:{isSelected:true},
			//usertype:{isSelected:true},
			//studenttype:{isSelected:true},
			//teachertype:{isSelected:true},
			validity:{dateISO: true}
			},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		}
	});
	
	//找回密码
	$("#form_user_retrievePassword").validate({
		errorElement: "div",
		event: "blur",
		rules:{
			//retrieve password
			"username":{required:true, rangelength:[4,25], isUserName:true}
			},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		}
	});
});