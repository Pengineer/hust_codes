define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	var nameSpace = "linkedin";
	
	exports.init = function() {
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2}
		});
		$(".linkCh").live("click", function() {//发消息
			var entityId = this.id;
			window.location.href = basePath + "linkedin/toSend.action?entityId="+entityId;
			return false;
		});
	};
});
