define(function(require, exports, module) {
	var datepick = require("datepick-init");

	exports.init = function() {
		datepick.init();
		$("input[type='radio'][name='finalAuditResultPublish'][value=" + $("#finalAuditResultPublish").val() + "]").attr("checked", true);
		$("#submit").live("click", function(){
			if(!confirm("是否确定保存修改？")){
				return false;
			}
		});
		$("#startYear, #endYear").change(function(){
			if($("#startYear").val() > $("#endYear").val()) {
				$("#endYear").val($("#startYear").val());
			}
		});
	};

});
