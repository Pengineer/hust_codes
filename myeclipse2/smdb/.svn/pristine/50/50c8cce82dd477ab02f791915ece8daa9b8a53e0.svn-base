define(function(require, exports, module) {
	var list = require('javascript/list');
	var listAccount = require('javascript/security/account/list')
	
	var nameSpace = "account/teacher";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5","sortcolumn6","sortcolumn7"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5,"sortcolumn6":6,"sortcolumn7":7}
		});
		$(".linkP").live("click", function() {
			popPerson(this.id, 5);
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
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		listAccount.initListButton(nameSpace);
	};
});