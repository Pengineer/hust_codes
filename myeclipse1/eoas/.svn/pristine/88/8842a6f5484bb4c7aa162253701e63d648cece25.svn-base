//异步文件上传swfupload配置
var swfu;
function initSwf() {
	var uploadKey = document.getElementById("uploadKey").value;
	var settings = {
		flash_url : "/tool/swfupload/swfupload.swf",
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
		button_image_url: "/eoas/tool/swfupload/images/btn_bg.gif",
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
