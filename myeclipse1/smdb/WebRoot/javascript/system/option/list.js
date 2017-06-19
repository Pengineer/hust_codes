define(function(require, exports, module) {
	var list = require('javascript/list');
	require('tool/poplayer/js/pop');
	require('pop-self');
	
	exports.init = function() {
		$("#list_download").live("click", function() {
			popDownloadXML();
		});
	};
});
