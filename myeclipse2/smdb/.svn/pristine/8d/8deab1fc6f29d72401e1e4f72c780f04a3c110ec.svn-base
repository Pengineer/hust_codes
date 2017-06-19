define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	require('uploadify');
	require('tool/uploadify/js/jquery.uploadify-photo');
	var validate = require('javascript/person/validate');
	
	var setting = new Setting({

		id: "basic1",

		buttons: ['retry', 'cancel'],

		out_check: function(){
			return $("#form_person").valid();
		},

		on_in_opt: function(){
			validate.valid_basic1();
		}
	});
	
	var init = function(){
		// 子页面初始化接口
		$(function() {
			$("#photo_person_add").uploadifyExt({
				uploadLimitExt : 1,
				fileSizeLimit : '3MB',
				fileTypeExts : '*.gif; *.jpg; *.png',
				fileTypeDesc : '图片'
			});
		});
	};
	
	module.exports = {
		setting : setting,
		init : function(){init()}
	};
});
