define(function(require, exports, module) {
	var viewAccount = require('javascript/security/account/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "account/department/main";
	
	exports.init = function() {
		viewAccount.init(nameSpace);//公共部分
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$(".linkD").live("click", function() {
			popDept(this.id, 2);
			return false;
		});
	};
});
