/**
 * 依赖于账号模块view.js，初始化公共方法，定义页面特有方法。
 */
define(function(require, exports, module) {
	var viewAccount = require('javascript/security/account/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "account/ministry/main";
	
	exports.init = function() {
		viewAccount.init(nameSpace);// 初始化公共部分
		$(".linkA").live("click", function() {// 弹层查看机构信息
			popAgency(this.id, 1);
			return false;
		});
	};
});
