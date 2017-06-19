define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	var editProduct = require('javascript/product/edit');

	var nameSpace = "prodcut/consultation";
	
	exports.init = function() {
		window.productType = $("#productType").val();
		editProduct.init();
		
		//下载成果文件
		$(".downlaod_product").live("click", function(){
			var validateUrl = "product/consultation/validateFile.action?entityId=" + this.id + "&fileFileName=" + this.name;
			var successUrl =  "product/consultation/download.action?entityId=" + this.id + "&fileFileName=" + this.name;
			downloadFile(validateUrl, successUrl);
			return false;
		});
		//上传成果文件
		$(function() {
			$("#file_add").uploadifyExt({
				uploadLimitExt : 1,
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			var consultationId = $("#consultationId").val();
			$("#file_" +consultationId).uploadifyExt({
				uploadLimitExt : 1,
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			
		});
	};	
});
