// ========================================================================
// 文件名：selfspace_validate_en.js
//
// 文件说明：
//     本文件主要实现个人资料模块中的修改密码验证国际化。
//
// 历史记录：
// 2010-03-11  周中坚     修改文件。
// ========================================================================

jQuery.extend(jQuery.validator.messages, {
	isPassWord: "Password consists of characters on the keyboard",
	required: "This field is required",
	remPassword: "Password input error",
	email: "Invalid email address",
	dateISO: "Date format: mm/dd/yyyy",
	equalTo: "Please enter the same password",
	accept: "Suffix is illegal",
	maxlength: jQuery.validator.format("Do not exceed {0} characters"),
	minlength: jQuery.validator.format("Do not be less than {0} characters"),
	rangelength: jQuery.validator.format("Stringlength {0}-{1} characters"),
	range: jQuery.validator.format("Number range {0}-{1}")
});
