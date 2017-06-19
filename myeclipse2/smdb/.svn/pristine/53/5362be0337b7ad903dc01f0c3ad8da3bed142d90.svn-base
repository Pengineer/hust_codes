define(function(require, exports, module) {
	var list = require('javascript/list');
	var listAccount = require('javascript/security/account/list')
	
	var nameSpace = "account/province/main";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
		$(".linkA").live("click", function() {
			popAgency(this.id, 1);
			return false;
		});
		listAccount.initListButton(nameSpace);
	};
});