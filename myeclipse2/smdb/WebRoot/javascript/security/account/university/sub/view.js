define(function(require, exports, module) {
	var viewAccount = require('javascript/security/account/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "account/university/sub";
	
	exports.init = function() {
		viewAccount.init(nameSpace);//公共部分
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$(".linkP").live("click", function() {
			popPerson(this.id, 1);
			return false;
		});
	};
});
