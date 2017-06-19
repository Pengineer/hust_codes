/**
 * 用于获奖数据列表
 * @author 余潜玉
 */
define(function(require, exports, module) {
	var list = require('javascript/list');
	var listApplication = require('javascript/award/moesocial/application/list')
	
	exports.initClick = function(){
		window.loadSession = function(id){listApplication.loadSession(id)};
	};
	
	exports.init = function() {
		$(function() {
			list.pageShow({
				"nameSpace":"award/moesocial/application/awarded",
				"selectedTab":"awarded",
				"viewParam":{"listflag":$("#listflag").val()},
				"sortColumnId":["sortcolumn0","sortcolumn1","sortcolumn2","sortcolumn3","sortcolumn4","sortcolumn10","sortcolumn26","sortcolumn27"],
				"sortColumnValue":{"sortcolumn0":0,"sortcolumn1":1,"sortcolumn2":2,"sortcolumn3":3,"sortcolumn4":4,"sortcolumn10":10,"sortcolumn26":26,"sortcolumn27":27}
			});
	
			listApplication.init();
			
			//届次更新，则列表更新
			$("#session1").bind("change", function() {
				$("#search").attr("action", "award/moesocial/application/awarded/simpleSearch.action");
				$("#search").submit();
				return false;
			});
			//申请届次
			listApplication.loadSession('session1');
		});
	};
});

