// ========================================================================
// 文件名：expert_validate.js
//
// 文件说明：
//     本文件主要实现用户管理模块中页面的输入验证，包括注册三步、修改用户信息、
// 高级检索和找回密码。
//
// 历史记录：
// 2010-01-28  周中坚     创建文件，完成基本功能。
// 2010-02-24  周中坚     整合文件，添加注释。
// 2014-03-17  徐江伟     专家修改页面中专家承担国社科和教育部项目的字段验证单独放在validateProject函数中（原因：这些字段都是动态生成动态校验）。
// ========================================================================

$(document).ready(function(){
	
	$("#form_expert").validate({
		errorElement: "label",
		event: "blur",
		rules:{
			"expert.name":{required:true, isCnName:true},
			"expert.gender":{isSelected:true},
			"expert.birthday":{dateISO:true},
			"expert.idCardNumber":{required:true, isIdCardNumber:true},
			"expert.universityCode":{isSelected:true},
			"expert.department":{maxlength:50},
			"expert.specialityTitle":{required:true, maxlength:50},
			"expert.job":{maxlength:50},
			"expert.positionLevel":{maxlength:50},
			//"expert.rating":{isSelected:true},
			"expert.degree":{maxlength:50},
			"expert.language":{maxlength:50},
			//"expert.isDoctorTutor":{isSelected:true},
			"expert.isReviewer":{isSelected:true},
			"expert.email":{required:true, email:true},
			"expert.mobilePhone":{isCellPhone:true},
			"expert.homePhone":{isPhone:true},
			"expert.officePhone":{isPhone:true},
			"expert.officeAddress":{maxlength:100},
			"expert.officePostcode":{isPostCode:true},
			"expert.researchField":{maxlength:100},
			"expert.partTime":{maxlength:100},
			"expert.discipline":{required:true,maxlength:50},
			"expert.nationalProject":{maxlength:1000},
			"expert.moeProject":{maxlength:1000},
			"expert.award":{maxlength:1000},
			"expert.remark":{maxlength:100}		
			},
		// set this class to error-labels to indicate valid fields
		success: function(label) {
			// set &nbsp; as text for IE
			label.html("&nbsp;").addClass("checked");
		},
		errorPlacement: function(error, element) { 
			//alert(element.innerHTML);
			error.appendTo( element.parent("td").next("td") ); 
		}
	});
	
	//调用validateProject函数，在表单提交前对新增项目进行前台校验
	$('#form_expert').submit(function(){
		validateProject();
		return true;
	});
});

/*
 * 对新增项目的字段进行校验，不能为空
*/
var validateProject = function(){
	if($('.projectInput').length != 0){
//		alert($('.projectInput').length);
		$('.projectInput').each(function(){
			 $(this).rules('remove');
		});
	};
	if($('.projectInput').length != 0){
//		alert($('.projectInput').length);
		$('.projectInput').each(function(){
			 $(this).rules('add', {
		        required:true,
		        messages: {
		            required: "不能为空."
		        }
		    });
		});
	};
}