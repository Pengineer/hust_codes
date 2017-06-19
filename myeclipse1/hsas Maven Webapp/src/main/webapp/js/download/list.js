/*
 * @author fengzheqi
 * @date 2015/09/06
 * @description 下载页面
 */

define(function(require, exports, module){
	var list = require('list');
	var nameSpace = 'download';
	require('form');
	exports.init = function(){
		list.pageShow({
			"nameSpace":nameSpace,
			"sortColumnId":["sortcolumn0","sortcolumn1"],
			"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1}
		})
	}
})