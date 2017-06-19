define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	
	var validate = require('javascript/system/notice/inner/validate');
	exports.init = function() {
		validate.valid();
		window.onload = initSwf;
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
		}
		
		$("textarea[name=mail.sendTo]").hide();
		$("input#recieverType-16").click(function(){
			if (this.checked == true){
				$("textarea[name=mail.sendTo]").show();
			} else {
				$("textarea[name=mail.sendTo]").hide();
			}
		});
		$("textarea[name=mail.sendTo]").click(function(){
			if (this.style.color == 'gray'){
				this.style.color = 'black';
				this.value = "";
			}
		});
		$("#submit").live('click',function(){
			return ValidFCK('notice.content');
		});
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 10,
				fileSizeLimit : '30MB',
				fileTypeDesc : '附件'
			});
			var noticeId = $("#noticeId").val();
			$("#file_" +　noticeId).uploadifyExt({
				uploadLimitExt : 10,
				fileSizeLimit : '10MB',
				fileTypeDesc : '附件'
			});
		});
	};
});

