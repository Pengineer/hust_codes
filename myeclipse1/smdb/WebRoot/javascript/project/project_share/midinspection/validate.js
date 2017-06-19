/*
 * 用于中检校验
 */
define(function(require, exports, module) {
	require('validate');
	
	var valid = function(){
		$(function() {
			$("#midFile").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"note":{maxlength: 200},
					"mid_file":{required:function(){return($(".uploadify-queue-item").size()==0);}}
				},
				errorPlacement: function(error, element){
					error.appendTo( element.parent("td").next("td") );
				}
			});
			
			$("#mid_result").validate({
				errorElement: "span",
				event: "blur",
				rules:{
					"mid_file":{required:function(){return($(".uploadify-queue-item").size()==0);}},
					"midResult":{required: true},
					"midDate":{required: true, dateISO: true},
					"midImportedOpinion":{maxlength: 200},
					"midOpinionFeedback":{maxlength: 200}
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