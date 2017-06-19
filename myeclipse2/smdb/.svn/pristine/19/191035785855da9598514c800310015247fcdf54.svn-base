define(function(require, exports, module) {
	require('form');
	require('validate');
	require('tool/poplayer/js/pop');
	require('pop-self');
	require('pop-init');
	require('uploadify');
	require('uploadify-ext');
	
	//上传变更申请书
	var uploadFileResult = function(data, type, layer){
		var varFileId = $("#fsUploadProgress_end").find("input[name='fileIds']").val();
		var variId = $("#variId").val();
		var dis = {
			"varFileId":varFileId,
			"variId":variId
		};
		layer.callBack(dis, type);
		layer.destroy();
	};
	
	var initUpload = function() {
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1, //文件上传个数的限制
				fileSizeLimit : '300MB',//文件上传大小的限制
				fileTypeDesc : '附件',//可以不用管
				fileTypeExts : '*.doc; *.docx; *.pdf'
			});
		});
	};
	
	module.exports = {
		initUpload: function() {initUpload()},
		uploadFileResult: function(data, type, layer){uploadFileResult(data, type, layer)}
	};
});