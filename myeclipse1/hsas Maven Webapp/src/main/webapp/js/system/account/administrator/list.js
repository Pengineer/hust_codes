/**
 * @author liujia
 * @date 2015/8/7
 * @descrioption 新闻列表模块
 */
define(function(require, exports, module){
	var list = require("list");
	var nameSpace = "account";
	require("form");
	//初始化函数，初始化列表
	exports.init = function(){
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
	};

});

