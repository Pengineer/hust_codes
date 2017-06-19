define(function(require, exports, module) {
	var editAccount = require('javascript/security/account/edit');
	var validate = require('javascript/security/account/validate');
	
	exports.init = function() {
		validate.valid();// 校验
		editAccount.init();//公共部分
	};
});
