/*
 *@author: fengzq 
 *
 */
define(function(require, exports, module) {
	require("validate");
	var datepick = require('datepick-init');
	require("uploadify");
	require("uploadify-ext");
	require("javascript/portal/recruit/share")
	exports.init = function(){
		datepick.init();
		
		$("#photo_" + $("#appId").val()).uploadifyExt({
			uploadLimitExt: 1, //文件上传个数的限制
			fileSizeLimit: '1MB', //文件上传大小的限制
			fileTypeDesc: '附件', //可以不用管
			fileTypeExts: '*.jpeg; *.jpg; *.png',
			buttonClass: "upload"
		});
		$("#file_" + $("#appId").val()).uploadifyExt({
			uploadLimitExt: 1, //文件上传个数的限制
			fileSizeLimit: '20MB', //文件上传大小的限制
			fileTypeDesc: '附件', //可以不用管
			fileTypeExts: '*.zip;*.rar;*.doc;*.docx;*.pdf',
			buttonClass: "upload"
		});
	}
})
