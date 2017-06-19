/*
 * @author fengzheqi
 * @date 2015/08/24
 * @description 用户注册
 */

define(function(require, exports, module){
	require('validation');
	require('validationInit');
	require('form');
	
	/*初始化验证表单*/
	$('#registerForm').validationEngine({
		scroll: false,
		isOverflown:true,
		validateNonVisibleFields: true,
		promptPosition: 'centerRight',
		addPromptClass: 'formError-noArrow formError-text'
	}).ajaxForm({
		type: "post",
		dataType: "json",
		success: function(data) {
			if (data.tag == "1") {
				dialog({
					title: '提示',
					content: '注册成功!',
					okValue: '确定',
					width: 260,
					ok: function() {
						window.location.href = "";
					}
				}).showModal();
			} else {
				dialog({
					title: '提示',
					content: '注册失败,请稍后再试!',
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