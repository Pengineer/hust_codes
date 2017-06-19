/*
Uploadify-ext
Copyright (c) 2012 Isun
*/

define(function(require, exports, module) {
	require('uploadify');
	require('cookie');
	
	var default_settings = {
		'buttonImage' : 'image/btn_bg03.gif',
		'buttonText' : '选择文件',
		'swf'	  : 'tool/uploadify/uploadify.swf',
		'uploader' : basePath + '/upload/upload.action;jsessionid=' + $.cookie("JSESSIONID"),
		'fileObjName' : 'file',
		'progressData' : 'speed',
		'width' : 80,
		'height' : 22,
		'removeCompleted' : false,
		'uploadLimitExt' : 999,
		
		'onInit' : function(instance) {
			
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("upload/fetchGroupFilesSummary.action", {"groupId" : groupId}, function(data){
				var summary = data.groupFilesSummary;
				for (var i = 0; i < summary.length; i++) {
					var fakeFile = {
						id : summary[i][0],
						name : summary[i][1],
						size : summary[i][2]
					};
					try {
						instance.settings.file_queued_handler.call(instance, fakeFile);
						instance.settings.upload_progress_handler.call(instance, fakeFile, fakeFile.size, fakeFile.size);
						$("#" + fakeFile.id + " div.uploadify-progress").remove();
						$("#" + fakeFile.id).append('<img class="photo" src="upload/person/photo/'+fakeFile.name+'"></img>');
					} finally {
					}
				}
			});
			$("#" + queueId + " a.discard").live("click", function(){
				var fileId = $(this).parent().parent().attr("fileId");
				var id = $(this).parent().parent().attr("id");
				$.post("upload/discard.action", {fileId : fileId || id, groupId : groupId}, function(){
					$("#" + groupId).uploadify("cancel", id);
				});
			});
			$("#" + groupId + " div.uploadify-button").css("text-indent", "");
		},

		'onUploadStart' : function(file) {
			if ($("#" + this.settings.queueID + " div.uploadify-queue-item").length > this.settings.uploadLimitExt) {
				alert("最多只能上传" + this.settings.uploadLimitExt + "个文件!");
				$("#" + this.settings.id).uploadify("cancel", file.id);
			}
			$("#" + this.settings.id).uploadify("settings", "formData", {groupId : this.settings.id});
		},

		'onUploadSuccess' : function(file, data, response) {
			$("#" + file.id).attr("fileId", $.parseJSON(data).fileId);
			$("#" + file.id + " div.uploadify-progress").remove();
			$("#" + file.id).append('<img class="photo" src="temp/'+$.cookie("JSESSIONID")+'/'+file.name+'"></img>');
		},
		
		'itemTemplate' : 
			'<div id="${fileID}" class="uploadify-queue-item">\
				<div class="cancel">\
					<a class="discard" href="javascript:void(0);">X</a>\
				</div>\
				<span class="fileName">${fileName} (${fileSize})</span><span class="data"></span>\
				<div class="uploadify-progress">\
					<div class="uploadify-progress-bar"><!--Progress Bar--></div>\
				</div>\
			</div>'
	};
	
	$.fn.uploadifyExt = function(settings) {
		return $(this).uploadify($.extend(default_settings, settings));
	}

});