/*
 * 用于年检校验
 */
define(function(require, exports, module) {
	require('validate');
	
	var valid = function(){
		$(function() {
			$("#annFile").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"note":{maxlength: 200},
					"annYear":{required:true, year:true},
					"ann_file":{required:function(){return($(".uploadify-queue-item").size()==0);}}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
			
			$("#ann_result").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"annResult":{required: true},
		 			"annYear":{required:true, year:true},
					"annDate":{required: true, dateISO: true},
					"ann_file":{required:function(){return($(".uploadify-queue-item").size()==0);}},
					"annImportedOpinion":{maxlength: 200},
					"annOpinionFeedback":{maxlength: 200}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
		});
	};
	
	exports.valid = function() {
		valid();
	};
});