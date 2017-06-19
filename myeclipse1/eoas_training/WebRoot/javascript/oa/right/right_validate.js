// ========================================================================
// 文件名：expert_validate.js
//
// 文件说明：
//     本文件主要实现权限管理模块中页面的输入验证
//
// ========================================================================

$(document).ready(function(){	
	/*
	 * 添加权限前台校验
	*/
	$("#right_add").validationEngine({
		scroll: true,
		isOverflown:true,
		validateNonVisibleFields: true,
		promptPosition: 'centerRight',
		addPromptClass: 'formError-noArrow formError-text formError-custom',
	})
});