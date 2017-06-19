define(function(require, exports, module) {
	require('validate');
	var attrRules = {
		"basic1": [
			["sourceName", {
				required: true
			}],
			["typeName", {
				required: true
			}],
			["tableName", {
				required: true
			}]
		],
		"basic2": [
			["xmlKey", {
				required: true
			}],
			["xmlValue", {
				required: true
			}]
		]
	};
	module.exports = {
		valid_basic2: function(div) {
			$("form",div).validate({
				errorElement: "span",
				event: "blur",
				rules: {},
				errorPlacement: function(error, element) {
					var obj = $(".tr_error").parents(".edit_info:visible");
					var comb = element.parent("td").parent("tr").find("td.comb-error");
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
			for (i = 0; i < attrRules.basic2.length; i++) {
				$("'[name^=" + attrRules.basic2[i][0] + "]'",div).each(function() {
					$(this).rules("add", attrRules.basic2[i][1]);
				});
			}
		}
	}
})