define(function(require, exports, module) {
	require('pop-init');
	
	var modifyFlag = function(url) {
		if (confirm("您确定不提醒该备忘吗？")) {
			$.ajax({
				url: url,
				type: "post",
				dataType: "json",
				success: function(result) {
					if (result.errorInfo == null || result.errorInfo == "") {
						$("#memo_count").html(
							parseInt($("#memo_count").html()) - 1
						);
						$("#" + result.entityId).parent().parent().remove();
					} else {
						alert(result.errorInfo); 
					}
				}
			});
		}
	};
	
	exports.init = function() {
		$(".modify_flag").live("click", function() {
			modifyFlag("selfspace/memo/modify.action?modifyFlag=2&entityId=" + this.id);
			return false;
		});
	};
});
