define(function(require, exports, module) {
	require('uploadify');
	require('uploadify-ext');
	var editProduct = require('javascript/product/edit');

	var leaf = function() {
		//切换著作类型【注：译著、译需要选择原著语言】
		$("select[name='book.type.id']").change(function() {
			var type = $.trim($("option[value='" + $(this).val() + "']").html());
			if(type == '译著' || type == '译') {
				$("select", $(this).next()).rules("add", {selected: true});
				$(this).next().show();
			} else {
				$("select", $(this).next()).val("-1").rules("remove");
				$(this).next().hide();
			}
		});
	};
	
	var nameSpace = "prodcut/book";
	
	exports.init = function() {
		window.productType = $("#productType").val();
		leaf();
		editProduct.init();
		
		//下载成果文件
		$(".downlaod_product").live("click", function(){
			var validateUrl = "product/book/validateFile.action?entityId=" + this.id + "&fileFileName=" + this.name;
			var successUrl =  "product/book/download.action?entityId=" + this.id + "&fileFileName=" + this.name;
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
			var bookId = $("#bookId").val();
			$("#file_" +bookId).uploadifyExt({
				uploadLimitExt : 1,
				fileSizeLimit : '300MB',
				fileTypeExts:'*.doc; *.docx; *.pdf',
				fileTypeDesc : '附件'
			});
			
		});
	};	
});
