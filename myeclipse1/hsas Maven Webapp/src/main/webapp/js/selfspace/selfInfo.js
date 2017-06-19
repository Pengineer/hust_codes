/*
 * @author fengzheqi
 * @date 2015/08/25
 * @description 个人信息
 */
define(function(require, exports, module){
	require('form');
	var list = require('list');
	var nameSpace = 'selfspace';
	
	exports.init = function(){
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn5"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn5":5}
		});
	}
})