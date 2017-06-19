/*
 * @author fengzheqi
 * @date 2015/08/24
 * @description 密码修改
 */

define(function(require, exports, module){
	require('validation');
	require('validationInit');
	require('form');

	/*密码修改校验初始化*/
	$('#modifyPwdForm').validationEngine({
		scroll: false,
		isOverflown:true,
		validateNonVisibleFields: true,
		promptPosition: 'centerRight',
		addPromptClass: 'formError-noArrow formError-text'
	}).ajaxForm({
		url: "selfspace/modifyPassword.action",
		type: "post",
		dataType: "json",
		success: function(data) {
			if (data.tag == "1") {
				dialog({
					title: '提示',
					content: '密码修改成功!',
					okValue: '确定',
					width: 260,
					ok: function() {
						window.location.href = "";
					}
				}).showModal();
			} else {
				dialog({
					title: '提示',
					content: '密码修改失败,请稍后再试!',
					width: 260,
					okValue: '确定',
					ok: function() {
						window.location.reload();
					}
				}).showModal();
			}
		}
	})
})
