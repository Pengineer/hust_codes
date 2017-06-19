define(function(require, exports, module) {
	require('validate');
	module.exports = {
		valid: function() {
			$("#template").validate({
				errorElement: "span",
				event: "blur",
				rules: {
					"taskConfigName": {
						required: true
					},
					"taskType": {
						required: true
					}

				},
				errorPlacement: function(error, element) {
					var obj = $(".tr_error")
						.parents(".edit_info:visible");
					var comb = element.parent("td").parent("tr")
						.find("td.comb-error");
					if (comb.length) {
						error.appendTo(comb);
					} else if (obj.length) {
						var idx = element.parent().eq(0).index();
						$(".tr_error td:eq(" + idx + ")", obj).empty();
						error.appendTo($(".tr_error td", obj).eq(idx));
					} else {
						error.appendTo(element.parent("td").next("td"));
					}
				}
			});
		}
	}
})