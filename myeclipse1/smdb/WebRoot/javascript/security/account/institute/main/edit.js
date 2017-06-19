define(function(require, exports, module) {
	var editAccount = require('javascript/security/account/edit');
	var validate = require('javascript/security/account/validate');
	
	var leaf = function(){ //点击查看机构信息，初始化选择按钮
		$(".linkI").live("click", function() {
			popInst(this.id, 3);
			return false;
		});
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$("#selectButton").live("click", function() {
			editAccount.selectEntity(5, 0);
			return false;
		});
	};
	
	exports.init = function() {
		validate.valid();// 校验
		editAccount.init();//公共部分
		leaf();//这个类型账号特有的一些处理
	};
});
