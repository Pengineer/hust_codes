/*
 * @author fengzheqi
 * @date 2015/08/29
 * @description 邮件添加
 */
define(function(require, exports, module){
	require('form');
	require('validation');
	require('validationInit');
	require("uploadify");
	require("uploadify-ext");
	
	/*选在其它时弹出输入框*/
	$(function(){
		$('input[type=checkbox][value=others]').on('click', function(){
			if(this.checked == true) {
				$('textarea[name="mail.sendTo"]').show(500);
			} else {
				$('textarea[name="mail.sendTo"]').hide(500);
			}
		})
	});
	
	/* 初始化校验并设置表单为AJAX提交方式. */
	$('#mailAdd').validationEngine({
		scroll: false,
		isOverflown:true,
		validateNonVisibleFields: true,
		promptPosition: 'centerRight',
		addPromptClass: 'formError-noArrow formError-text',
	}).ajaxForm({
		type: 'post',
		dataType: 'json',
		success: function(data) {
			if (data.tag == "1") {
				dialog({
					title: '提示',
					content: '添加邮件成功!',
					okValue: '确定',
					width: 260,
					ok: function() {
						window.location.href = "";
					}
				}).showModal();
			} else {
				dialog({
					title: '提示',
					content: '添加邮件失败,请稍后再试!',
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