// ========================================================================
// 文件名：reportValidate.js
//
// 文件说明：
//     本文件主要实现角色权限模块中页面的输入验证，包括添加角色、修改角色、添加
// 权限和修改权限。
// 
// 历史记录：
// 2010-01-28  周中坚  创建文件，完成基本功能。
// 2010-02-25  周中坚  添加注释。
// ========================================================================
jQuery.extend(jQuery.validator.messages, {
	required: "This field is required",
	email: "Invalid email address",
	dateISO: "Date format: mm/dd/yyyy",
	number: "Please enter a valid number",
	digits: "You can only enter interger",
	maxlength: jQuery.validator.format("Do not exceed {0} characters"),
	minlength: jQuery.validator.format("Do not be less than {0} characters"),
	rangelength: jQuery.validator.format("Stringlength {0}-{1} characters"),
	range: jQuery.validator.format("Number range {0}-{1}"),
	max: jQuery.validator.format("Maximun number: {0} "),
	min: jQuery.validator.format("Minimun number: {0} ")
});