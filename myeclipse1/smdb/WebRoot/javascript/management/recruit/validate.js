define(function(require, exports, module) {
	require('validate');
	
	var attrRules = {
		"basic1":[
			["name", {required:true, maxlength:40}],
			["number", {maxlength:4}],
			["degree", {required:true}],
			["endDate", {required:true}],
			["selectedTemplateIds", {required:true}]
		],
		"detail":[
			["requirement", {required: true}]
		]
	};
	
	exports.valid_basic1 = function(){
		for(i = 0; i < attrRules.basic1.length; i++){
			$("':input[name=" + attrRules.basic1[i][0] + "]'").rules("add", attrRules.basic1[i][1] );
		}
	};
	exports.valid_detail = function(){
		for(i = 0; i < attrRules.detail.length; i++){
			$("':input[name=" + attrRules.detail[i][0] + "]'").rules("add", attrRules.detail[i][1] );
		}
	};

	exports.valid = function() {
		$("#form_job").validate({
			errorElement: "span",
			event: "blur",
			rules:{
					
				},
			errorPlacement: function(error, element) {
				var obj = $(".tr_error").parents(".edit_info:visible");
				var comb= element.parent("td").parent("tr").find("td.comb-error");
				if(comb.length){
					error.appendTo(comb);
				}else if(obj.length){
					var idx = element.parent().eq(0).index();
					$(".tr_error td:eq(" + idx + ")", obj).empty();
					error.appendTo( $(".tr_error td", obj).eq(idx) );
				}
				else {error.appendTo( element.parent("td").next("td") );}
			}
		});
	};
});
