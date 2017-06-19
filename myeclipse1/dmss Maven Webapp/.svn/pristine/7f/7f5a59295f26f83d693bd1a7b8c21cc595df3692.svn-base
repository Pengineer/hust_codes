	//alert($.cookie("JSESSIONID"));
	var default_settings = {
		'buttonImage' : '',
		'buttonText' : '',
		'swf'	  : 'plugins/uploadify/uploadify.swf',
		'uploader' : 'upload/upload;jsessionid=' + $.cookie("JSESSIONID"),
		'fileObjName' : 'file',
		'progressData' : 'speed',
		'width' : 80,
		'height' : 22,
		'removeCompleted' : false,
		'uploadLimitExt' : 999,
		
		'onInit' : function(instance) {
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("upload/fetchGroupFilesSummary", {"groupId" : groupId}, function(data){
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
					} finally {
					}
				}
			});
			$("#" + queueId + " a.discard").live("click", function(){
				var fileId = $(this).parent().parent().attr("fileId");
				var id = $(this).parent().parent().attr("id");
				$.post("upload/discard", {fileId : fileId || id, groupId : groupId}, function(){
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

