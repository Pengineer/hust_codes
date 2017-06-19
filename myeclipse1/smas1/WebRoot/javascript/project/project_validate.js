// ========================================================================
// 文件名：project_validate.js
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
	
	$("#form_project").validate({
		event: "blur",
		rules:{
			"project.projectName":{required:true, maxlength:200},
			"project.projectType":{maxlength:40},
			"project.researchType":{maxlength:40},
			"project.disciplineType":{maxlength:100},
			"project.discipline":{maxlength:40},
			"project.universityCode":{isSelected:true},
			"project.applyDate":{dateISO:true},
			"project.addDate":{dateISO:true},
			"project.auditStatus":{maxlength:40},
			"project.isReviewable":{isSelected:true},
			"project.planEndDate":{dateISO:true},
			"project.otherFee":{digits:true},
			"project.applyFee":{digits:true},
			"project.members":{maxlength:400},
			"project.finalResultType":{maxlength:40},
			
			"project.director":{required:true, maxlength:40},
			"project.gender":{isSelected:true},
			"project.birthday":{dateISO:true},
			"project.idcard":{required:true, isIdCardNumber:true},
			"project.foreign":{maxlength:40},
			"project.education":{maxlength:40},
			"project.degree":{maxlength:40},
			"project.department":{maxlength:40},
			"project.title":{maxlength:40},
			"project.job":{maxlength:40},
			"project.email":{required:true, email:true},
			"project.mobile":{isCellPhone:true},
			"project.phone":{isPhone:true},
			"project.address":{maxlength:200},
			"project.postcode":{isPostCode:true}
			},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		},
		errorPlacement: function(error, element) { 
			error.appendTo( element.parent("td").next("td") ); 
		}
	});
});