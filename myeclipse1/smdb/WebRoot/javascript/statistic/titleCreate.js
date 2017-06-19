define(function(require, exports, module) {
	require('pop-init');
	
	exports.init = function() {
		$("#toCommonSubmit").live("click", function() {
			var url = "statistic/custom/";
			var statisticType = $("#statisticType").val();
			if(statisticType.indexOf("project") > 0){
				url += "project";
			}else if(statisticType.indexOf("product") > 0){
				url += "product";
			}else{
				url += statisticType;
			}
			$.ajax({
				url: basePath + url + "/toCommon.action?title=" + $("#toCommonTitle").val(),
				type: "post",
				dataType: "json",
				success: function(result) {
					if (result.errorInfo == null || result.errorInfo == "") {
						alert("导入成功!");
						thisPopLayer.destroy();
					} else {
						alert(result.errorInfo);
						thisPopLayer.destroy();
					}
				}
			});
			return false;
		});
	};
});