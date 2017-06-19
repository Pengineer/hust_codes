define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	
	var validate = require('javascript/system/news/inner/validate');
	
	exports.init = function() {
		validate.valid();
		var list = $.parseJSON($("#existingAttachment").html());
		for (file in list){
			var progress = new FileProgress(list[file], swfu.customSettings.progressTarget);
			progress.setComplete();

			var processFileId = document.createElement("input");
			processFileId.type = "hidden";
			processFileId.name = "fileIds";
			processFileId.value = file;
			progress.fileProgressElement.appendChild(processFileId);

			progress.toggleCancel(true, this);
			progress.setStatus("Existing file.");
		};
		$("#submit").live('click',function(){
			return ValidFCK('news.content');
		});
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 10,
				fileSizeLimit : '10MB',
				fileTypeDesc : '附件'
			});
			var newsId = $("#newsId").val();
			$("#file_" +　newsId).uploadifyExt({
				uploadLimitExt : 10,
				fileSizeLimit : '10MB',
				fileTypeDesc : '附件'
			});
		});
	};
});

