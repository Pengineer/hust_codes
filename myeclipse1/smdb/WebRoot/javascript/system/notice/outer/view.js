define(function(require, exports, module) {
	
	exports.init = function() {
		$("#view_back").bind("click", function() {
			window.location.href = basePath + "notice/outer/toList.action?entityId=" + $("#entityId").val() + "&update=" + $("#update").val();
			return false;
		});
		$(".attach").bind("click", function() {
			window.location.href = basePath + "notice/outer/download.action?entityId=" + $("#entityId").val() + "&status=" + this.id;
			return false;
		});
	};
});
