// ========================================================================
// 文件名：sysconfig_validate_en.js
//
// 文件说明：
//     本文件主要实现用户管理模块中页面的输入验证，包括注册三步、修改用户信息、
// 高级检索和找回密码。
//
// 历史记录：
// ========================================================================
jQuery.extend(jQuery.validator.messages, {
	required: "This field is required",
	remote: "This field has been used",
	remRetrievePassword: "This username doesn't exist",
	email: "Invalid email address",
	url: "Invalid url",
	date: "Invalid date format",
	dateISO: "Date format: mm/dd/yyyy",
	number: "Please enter a valid number",
	digits: "You can only enter interger",
	creditcard: "Invalid creitcard",
	equalTo: "Please enter the same password",
	accept: "Suffix is illegal",
	maxlength: jQuery.validator.format("Do not exceed {0} characters"),
	minlength: jQuery.validator.format("Do not be less than {0} characters"),
	rangelength: jQuery.validator.format("Stringlength {0}-{1} characters"),
	range: jQuery.validator.format("Number range {0}-{1}"),
	max: jQuery.validator.format("Maximun number: {0} "),
	min: jQuery.validator.format("Minimun number: {0} ")
});