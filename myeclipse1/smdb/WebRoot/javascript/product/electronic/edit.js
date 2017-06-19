define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	var editProduct = require('javascript/product/edit');

	var nameSpace = "prodcut/electronic";
	
	exports.init = function() {
		window.productType = $("#productType").val();
		editProduct.init();
		
		//下载成果文件
		$(".downlaod_product").live("click", function(){
			var validateUrl = "product/electronic/validateFile.action?entityId=" + this.id + "&fileFileName=" + this.name;
			var successUrl =  "product/electronic/download.action?entityId=" + this.id + "&fileFileName=" + this.name;
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
			var electronicId = $("#electronicId").val();
			$("#file_" +electronicId).uploadifyExt({
				uploadLimitExt : 1,
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			
		});
	};	
});