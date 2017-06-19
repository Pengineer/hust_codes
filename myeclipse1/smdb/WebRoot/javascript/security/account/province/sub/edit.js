define(function(require, exports, module) {
	var editAccount = require('javascript/security/account/edit');
	var validate = require('javascript/security/account/validate');
	
	var leaf = function(){ //点击查看机构信息，初始化选择按钮
		$(".linkP").live("click", function() {
			popPerson(this.id, 1);
			return false;
		});
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		$("#selectButton").live("click", function() {
			editAccount.selectEntity(6, 3);
			return false;
		});
	};
	
	exports.init = function() {
		validate.valid();// 校验
		editAccount.init();//公共部分
		leaf();//这个类型账号特有的一些处理
	};
});
