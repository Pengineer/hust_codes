/*//异步文件上传swfupload配置
var swfu;
function initSwf() {
	var uploadKey = document.getElementById("aaaaaaaaaaaaaa").value;
	var settings = {
		flash_url : "tool/swfupload/swfupload.swf",
		upload_url: "tool/uploadify/uploadify.php",
		post_params: {"uploadKey" : uploadKey},
		file_size_limit : "10 MB",
		file_types : "*.jpg;*.png;*.bmp",
		file_types_description : "Picture",
		file_upload_limit : 1000,
		file_queue_limit : 0,
		file_post_name : "file",
		custom_settings : {
			progressTarget : "fsUploadProgress",
			cancelButtonId : "btnCancel"
		},
		debug: false,

		// Button settings
		button_image_url: "tool/swfupload/images/btn_bg.gif",
		button_width: "65",
		button_height: "20",
		button_placeholder_id: "spanButtonPlaceHolder",
		button_text: '选择文件',
		button_text_style: ".theFont { font-size: 16; }",
		button_text_left_padding: 7,
		button_text_top_padding: 2,

		// The event handler functions are defined in handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete	// Queue plugin event

	};
	swfu = new SWFUpload(settings);

	swfu.customSettings["my_queue_limit"] = 1;
	$(".progressWrapper").remove();
}
*/




//$(function() {
//	$("#aaaaaaaaaaaaaa").uploadify({
//		 'fileObjName': 'file', //提交时候的字段名，和struts2里面用来接收File的字段名一致
//         'method': 'get',            //以get方式上传        
//         'buttonText':'上传',        //上传按钮的文字
//         'fileTypeDesc':'Image Files',    //可上传文件格式的描述
//         'fileTypeExts':'*.gif; *.jpg; *.png',    //可上传的文件格式 
//         'auto':true,    //选择一个文件是否自动上传
//         'multi':true,    //是否允许多选(这里多选是指在弹出对话框中是否允许一次选择多个，但是可以通过每次只上传一个的方法上传多个文件)
//         'swf': 'tool/uploadify/uploadify.swf',//指定swf文件的，默认是uploadify.swf
//         'uploader': '/eoas/upload/upload.action',//服务器响应地址     
//         'onUploadStart' : function(file) {    //上传前触发的事件
//         
//            //在这里添加  $('#imageify').uploadify('cancel'); 可以取消上传
//             //$("#imageify").uploadify("settings","formData",{'name':'lmy1'}); //动态指定参数
//         } ,
//         'onUploadSuccess' : function(file, data, response) {    //上传成功后触发的事件
//             alert("上传成功");
//         }
//	});
//	
//});



$(document).ready(function() {
	$("#file_add").uploadify({
		 'fileObjName': 'file', //提交时候的字段名，和struts2里面用来接收File的字段名一致
         'method': 'get',//以get方式上传        
         'buttonText':'上传',//上传按钮的文字
         'fileTypeDesc':'Image Files',//可上传文件格式的描述
         'fileTypeExts':'*.gif; *.jpg; *.png',//可上传的文件格式 
         'auto':true,//选择一个文件是否自动上传
         'multi':true,//是否允许多选(这里多选是指在弹出对话框中是否允许一次选择多个，但是可以通过每次只上传一个的方法上传多个文件)
         'swf': 'tool/uploadify/uploadify.swf',//指定swf文件的，默认是uploadify.swf       
         'uploader': '/eoas/upload/upload.action',//服务器响应地址   
         'removeCompleted' : false,
		'onInit' : function(instance) {
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("/eoas/upload/fetchGroupFilesSummary.action", {"groupId" : groupId}, function(data){
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
			console.log("end photo init");
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
			$("#" + file.id).append('<img class="photo" src="temp/'+$.parseJSON(data).sessionId+'/'+file.name+'"></img>');
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
	});
	
	$("#eleresume_add").uploadify({
		'fileSizeLimit' : '10MB',
		'fileObjName': 'file', //提交时候的字段名，和struts2里面用来接收File的字段名一致
        'method': 'get',//以get方式上传        
        'buttonText':'上传',//上传按钮的文字
        'fileTypeDesc':'Image Files',//可上传文件格式的描述
        'fileTypeExts':'*.pdf; *.doc; *.docx',//可上传的文件格式 
        'auto':true,//选择一个文件是否自动上传
        'multi':true,//是否允许多选(这里多选是指在弹出对话框中是否允许一次选择多个，但是可以通过每次只上传一个的方法上传多个文件)
        'swf': 'tool/uploadify/uploadify.swf',//指定swf文件的，默认是uploadify.swf       
        'uploader': '/eoas/upload/upload.action',//服务器响应地址   
        'removeCompleted' : false,
		'onInit' : function(instance) {
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("/eoas/upload/fetchGroupFilesSummary.action", {"groupId" : groupId}, function(data){
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
			console.log("end photo init");
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
			$("#" + file.id).append('<img class="photo" src="temp/'+$.parseJSON(data).sessionId+'/'+file.name+'"></img>');
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
	});
	
	$("#production_add").uploadify({
		'fileSizeLimit' : '10MB',
		'fileObjName': 'file', //提交时候的字段名，和struts2里面用来接收File的字段名一致
        'method': 'get',//以get方式上传        
        'buttonText':'上传',//上传按钮的文字
        'fileTypeDesc':'Image Files',//可上传文件格式的描述
        'fileTypeExts':'*.pdf; *.doc; *.docx',//可上传的文件格式 
        'auto':true,//选择一个文件是否自动上传
        'multi':true,//是否允许多选(这里多选是指在弹出对话框中是否允许一次选择多个，但是可以通过每次只上传一个的方法上传多个文件)
        'swf': 'tool/uploadify/uploadify.swf',//指定swf文件的，默认是uploadify.swf       
        'uploader': '/eoas/upload/upload.action',//服务器响应地址   
        'removeCompleted' : false,
		'onInit' : function(instance) {
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("/eoas/upload/fetchGroupFilesSummary.action", {"groupId" : groupId}, function(data){
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
			console.log("end photo init");
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
			$("#" + file.id).append('<img class="photo" src="temp/'+$.parseJSON(data).sessionId+'/'+file.name+'"></img>');
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
	});
	
	
	$("#otherAttachment_add").uploadify({
		'fileSizeLimit' : '10MB',
		'fileObjName': 'file', //提交时候的字段名，和struts2里面用来接收File的字段名一致
        'method': 'get',//以get方式上传        
        'buttonText':'上传',//上传按钮的文字
        'fileTypeDesc':'Image Files',//可上传文件格式的描述
        'fileTypeExts':'*.pdf; *.doc; *.docx',//可上传的文件格式 
        'auto':true,//选择一个文件是否自动上传
        'multi':true,//是否允许多选(这里多选是指在弹出对话框中是否允许一次选择多个，但是可以通过每次只上传一个的方法上传多个文件)
        'swf': 'tool/uploadify/uploadify.swf',//指定swf文件的，默认是uploadify.swf       
        'uploader': '/eoas/upload/upload.action',//服务器响应地址   
        'removeCompleted' : false,
		'onInit' : function(instance) {
			var groupId = instance.settings.id;
			var queueId = instance.settings.queueID;
			$.get("/eoas/upload/fetchGroupFilesSummary.action", {"groupId" : groupId}, function(data){
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
			console.log("end photo init");
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
			$("#" + file.id).append('<img class="photo" src="temp/'+$.parseJSON(data).sessionId+'/'+file.name+'"></img>');
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
	});
});