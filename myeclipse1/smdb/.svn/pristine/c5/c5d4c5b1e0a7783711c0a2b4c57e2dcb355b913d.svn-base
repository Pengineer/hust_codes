define(function(require, exports, module) {
	var viewAccount = require('javascript/security/account/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	var nameSpace = "account/student";
	
	exports.init = function() {
		viewAccount.init(nameSpace);//公共部分
		$(".linkP").live("click", function() {
			popPerson(this.id, 5);
			return false;
		});
	};
});
