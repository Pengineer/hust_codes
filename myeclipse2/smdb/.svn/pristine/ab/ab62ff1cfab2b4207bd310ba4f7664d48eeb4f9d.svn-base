define(function(require, exports, module) {
	var viewFund = require('javascript/fundList/view');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	var nameSpace = "fundList/key/granted";
	
	exports.init = function() {
		viewFund.init(nameSpace);//公共部分
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$(".linkD").live("click", function() {
			popDept(this.id, 2);
			return false;
		});
		$(".linkI").live("click", function() {
			popInst(this.id, 3);
			return false;
		});
		$(".linkDir").live("click", function() {
			popPerson(this.id, 7);
			return false;
		});
		$(".linkAcc").live("click", function() {
			popAccount(this.id);
			return false;
		});
	};
});
