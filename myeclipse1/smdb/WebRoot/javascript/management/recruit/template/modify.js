/**
 * 招聘的模板修改页面
 * @author liujia
 *
 */
define(function(require, exports, modules) {
	require("uploadify");
	require("uploadify-ext");
	var validate = require("./validate.js");
	var nameSpace = "management/recruit/template";
	$("#cancel").click(function() {
		window.location.href = nameSpace + "/toView.action?entityId=" + $("#entityId").val();
	});
	exports.init = function() {
		validate.valid();
		$("#template_" + $("#entityId").val()).uploadifyExt({
			uploadLimitExt: 1, //文件上传个数的限制
			fileSizeLimit: '20MB', //文件上传大小的限制
			fileTypeDesc: '附件', //可以不用管
			fileTypeExts: '*.zip;*.rar;*.doc;*.docx;*.pdf',
			buttonClass: "upload",
			onUploadSuccess: function(file, data, response) {
				$("#file").val("upload success!");
			}
		});
		$(".cancel .discard").live("click", function() {
			$("#file").val("");
		});//重置标志位
		$.ajax({
			url: nameSpace + "/view.action",
			data: "entityId=" + $("#entityId").val(),
			type: "post",
			dataType: "json",
			success: function(json) {
				console.log(json);
				$("#name").val(json.name);
				$("#description").val(json.description);
			}
		})

	};
});