define(function(require, exports, module) {
	var view = require('javascript/view');
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('javascript/template_tool');
	
	exports.init = function(nameSpace, showDetails) {
		view.show(nameSpace, showDetails);
		view.add(nameSpace);
		view.mod(nameSpace);
		view.del(nameSpace, "您确定要删除此机构吗？");
		view.prev(nameSpace, showDetails);
		view.next(nameSpace, showDetails);
		view.back(nameSpace);// 所有查看页面公共部分
		Template_tool.init();
		view.inittabs();
		
		$(".linkA").live("click", function() {
			popAgency(this.name, 1);
			return false;
		});
		$(".link2").live("click", function() {
			popPerson(this.id, 1);
			return false;
		});
		$(".view_account").live("click", function() {
			popAccount(this.id);
			return false;
		});
	};
});