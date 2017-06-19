define(function(require, exports, module) {
	require('pop-init');
	require("javascript/option_transfer_select");
	
	exports.init = function() {
		$("#confirm").unbind("click").click(function(){
			var roleIds = "";
			$("#rightselect option").each(function () {
				roleIds += $(this).val() + ",";
			});
			thisPopLayer.callBack({
				data : {"role" : roleIds}
			});
			thisPopLayer.destroy();
		});
	};
});
